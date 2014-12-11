//package websockets;

/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
 /*
	@author Emil
	inspired by examples on https://github.com/vert-x/vertx-examples/tree/master/src/raw/java
 */
import java.util.ArrayList;
import java.util.HashMap;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.platform.Verticle;
import org.vertx.java.core.json.*;
/*
	Recieve a JSON String "
	Check if recv is in listen
	
*/

public class WebsocketsExample extends Verticle {
	private String datastring = "{\"glossary\": {\"title\": \"example glossary\",\"GlossDiv\": {\"title\": \"S\",\"GlossList\": {\"GlossEntry\": {\"ID\": \"SGML\",\"SortAs\": \"SGML\",\"GlossTerm\": \"Standard Generalized Markup Language\",\"Acronym\": \"SGML\",\"Abbrev\": \"ISO 8879:1986\",\"GlossDef\": {\"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\": [\"GML\", \"XML\"]},\"GlossSee\": \"markup\"}}}}}";
	private String jsonString = "{\"sender\":\"Urbansson\",\"reciver\":\"dolphan\",\"message\":\"Hejsan\"}";
	private HashMap<String, ServerWebSocket> sender;
	private ArrayList<ConnectedUser> connectedusers = new ArrayList<ConnectedUser>();
	//Called at start
	
	public void start() {
		vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
	//Creates websocket??
		public void handle(final ServerWebSocket ws) {
			
			if (ws.path().equals("/myapp")) {
			  ws.dataHandler(new Handler<Buffer>() {
				//Handles data
				public void handle(Buffer data) {
				  String incoming = data.toString();
				  
				  if(isJsonValid(incoming)){
					
					JsonObject toParse = new JsonObject(incoming);
					connectedusers.add(new ConnectedUser(ws, toParse.getString("sender")));
					System.out.println("Incoming " + toParse.getString("sender"));
					

				}
				  sendMessage("Urbansson","Hejsan");
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
  
  public void stop(){
	System.out.println("Stop");
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
	//TODO MOVE TO WORKING VERTICLE
	public void sendMessage(String reciever, String message){
			for(ConnectedUser usr : connectedusers){
				if(usr.getUser().equals(reciever)){
					usr.getConnection().writeTextFrame(message);
				}
		}
	}
}