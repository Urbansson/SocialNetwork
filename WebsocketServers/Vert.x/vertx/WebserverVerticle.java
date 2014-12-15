package vertx;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;
import org.vertx.java.core.json.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.DriverManager;

public class WebserverVerticle extends Verticle {

	@Override
	public void start() {
		

		final EventBus eventBus = vertx.eventBus();

		vertx.eventBus().registerHandler("db-handler",
				new Handler<Message<String>>() {
					@Override
					public void handle(Message<String> message) {
						// Reply to it
						System.out.println("Received message: " + message.body());

						Connection con = null;
						CallableStatement cStmt = null;
						String toReturn = "false";
						
						JsonObject parsedJSON = new JsonObject(
								message.body());
							
							
							try {
								con = DBManager.getInstance().getConnection();
								con.setAutoCommit(false);
								
								
								cStmt = con.prepareCall("{call newMessage(?,?,?)}");
								cStmt.setString(1, parsedJSON.getString("sender"));
								cStmt.setString(2, parsedJSON.getString("reciver"));
								cStmt.setString(3, parsedJSON.getString("message"));

								ResultSet rs = cStmt.executeQuery();
								con.commit();
	
								
								if (rs.first()){
									
									System.out.println(rs.getBoolean("success"));
									if(rs.getBoolean("success")){
										toReturn = "true";
									}	
								}			

							} catch (SQLException e) {
								try {
									con.rollback();
								} catch (SQLException e1) {}	
								System.out.println("Error: "+ e);
							}finally{
								try {
									con.setAutoCommit(true);
								} catch (SQLException e) {}
								DBManager.getInstance().freeConnection(con);
							}
		
						// database stuff here then send

						message.reply(toReturn);
					}
				});

		vertx.createHttpServer()
				.websocketHandler(new Handler<ServerWebSocket>() {
					@Override
					public void handle(final ServerWebSocket ws) {

						System.out.println("Received connection from: " + ws.path());

						final String user = ws.path().substring(1);

						final String id = ws.textHandlerID();
						vertx.sharedData().getSet("chat.user." + user).add(id);

						ws.closeHandler(new Handler<Void>() {
							@Override
							public void handle(final Void event) {
								vertx.sharedData().getSet("chat.room." + user)
										.remove(id);
							}
						});

						ws.dataHandler(new Handler<Buffer>() {
							@Override
							public void handle(final Buffer data) {

								try {
									String incoming = data.toString();
									JsonObject parsedJSON = new JsonObject(
											incoming);

									String sender = parsedJSON
											.getString("sender");
									String reciver = parsedJSON
											.getString("reciver");

									vertx.eventBus().send("db-handler",
											incoming,
											new Handler<Message<String>>() {
												@Override
												public void handle(
														Message<String> reply) {
													System.out.println("Received reply: "
															+ reply.body());
													if(reply.body().equals("true")){

													for (Object user : vertx
															.sharedData()
															.getSet("chat.user."
																	+ sender)) {
														eventBus.send(
																(String) user,
																incoming);
													}
													for (Object user : vertx
															.sharedData()
															.getSet("chat.user."
																	+ reciver)) {
														eventBus.send(
																(String) user,
																incoming);
													}
													}
												}
											});

									System.out.println("After eventBus");

								} catch (Exception e) {
									ws.reject();
								}
							}
						});

					}
				}).listen(8090);

	}
}