/*
Create Object that stores websocket and reciever
*/

import java.util.ArrayList;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.platform.Verticle;
import org.vertx.java.core.json.*;


public class ConnectedUser{
	private ServerWebSocket connection;
	private String user;
	
	public ConnectedUser(ServerWebSocket connection, String user){
		this.connection = connection;
		this.user = user;
	}
	
	public ServerWebSocket getConnection(){
		return connection;
	}
	
	public String getUser(){
		return user;
	}

}