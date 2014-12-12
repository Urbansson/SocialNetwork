import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.platform.Verticle;
import org.vertx.java.core.json.*;
import org.vertx.java.core.shareddata.*;
import java.util.concurrent.ConcurrentMap;

import snaq.db.ConnectionPool;
public class WebSocket extends Verticle {
	private HashMap<String, ServerWebSocket> userMap = new HashMap<String, ServerWebSocket>();//vertx.sharedData().getMap("chat.users");
	private Connection con;
	private static ConnectionPool POOL = null;
	
    public void start() {
	initializeDatabase();
    vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
      public void handle(final ServerWebSocket ws) {
        if (ws.path().equals("/myapp")) {
          ws.dataHandler(new Handler<Buffer>() {
            public void handle(Buffer data) {
              ws.writeTextFrame(data.toString()); // Echo it back
				  String incoming = data.toString();
				  
				  if(isJsonValid(incoming)){
					
					JsonObject toParse = new JsonObject(incoming);
					userMap.put(toParse.getString("sender"), ws);
					System.out.println("Incoming " + toParse.getString("sender"));
					sendMessage(toParse.getString("reciver"),toParse.getString("message"));

				}
            }
          });
        } else {
          ws.reject();
        }
      }
    }).requestHandler(new Handler<HttpServerRequest>() {
      public void handle(HttpServerRequest req) {
        if (req.path().equals("/")) req.response().sendFile("websockets/ws.html"); // Serve the html
      }
    }).listen(8080);
  }
  
   public boolean isJsonValid(String test) {
		try {
			new JsonObject(test);
		} catch (Exception ex) {
			// edited, to include @Arthur's comment
			// e.g. in case JSONArray is valid as well...
			 return false;
			}
		return true;	
	}
	
		private void initializeDatabase() {
			try {
				Class<?> clazz = Class.forName(CONFIG.getString("driverClass"));
					driver = (Driver)clazz.newInstance();
					DriverManager.registerDriver(driver);
				
				// Configure pool of not already done
				if (POOL == null) {
					int minPool = 10; //CONFIG.getNumber("minPool").intValue();
					int maxPool = 10; //CONFIG.getNumber("maxPool").intValue();
					int maxSize = 10; //CONFIG.getNumber("maxSize").intValue();
					int idleTimeout = 10;  //CONFIG.getNumber("idleTimeout").intValue();
					String url = "130.237.84.126"; //CONFIG.getString("jdbcUrl");
					String username = "root"; //CONFIG.getString("username");
					String password = "ninjakick"; //CONFIG.getString("password");
		
					POOL = new ConnectionPool("LOCALPOOL", minPool, maxPool,
							maxSize, idleTimeout, url, username, password);
					//LOG.debug("created new connection pool instance!");
				}
		
			} catch (Exception ex) {
				//LOG.error("Could not load database driver!", ex);
		}
	}
	
	
	public void sendMessage(String reciever, String message){
			if(reciever != null || userMap.get("reciever") != null)
				userMap.get(reciever).writeTextFrame("message");
	}
}