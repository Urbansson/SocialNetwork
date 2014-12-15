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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class WebSocket extends Verticle  {
	private HashMap<String, ServerWebSocket> userMap = new HashMap<String, ServerWebSocket>();//vertx.sharedData().getMap("chat.users");
		
    public void start() {
	try {
			System.out.println("Trying");
			Connection con = DriverBridge.getConnection("jdbc:mysql://130.237.84.126:3306/socialnetwork","socialnetwork", "ninjakick");
			System.out.println("Con is" + con);
			Statement st = con.createStatement();
			System.out.println("Statement is" + st);
			ResultSet rs = st.executeQuery("SELECT T_USER");
			System.out.println("Res is" + rs);
			  if (rs.next()) {
                System.out.println(rs.getString(1));
            }
		} catch (Exception e) {
			
		}
	
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
	
	
	
	public void sendMessage(String reciever, String message){
			if(reciever != null || userMap.get("reciever") != null)
				userMap.get(reciever).writeTextFrame("message");
	}
}