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
import org.vertx.java.core.shareddata.*;
import java.util.concurrent.ConcurrentMap;
/*
	Recieve a JSON String "
	Check if recv is in listen
	
*/

public class WebsocketsExample extends Verticle {
	private HashMap<String, ServerWebSocket> sender;
	private ArrayList<ConnectedUser> connectedusers = new ArrayList<ConnectedUser>();
	private ConcurrentSharedMap<String, ServerWebSocket> userMap;
	//Called at start
	
	public void start() {
		System.out.println("Start");
		userMap = vertx.sharedData().getMap("chat.users");
		System.out.println("Map created");
		System.out.println("Deploying");
		container.deployVerticle("WebSocket.java");
		System.out.println("Done");
		System.out.println("Listening");
		//container.deployVerticle("Sender.java");
		//container.deployVerticle("Receiver.java");
  }
  
  public void stop(){
	System.out.println("Stop");
  }
  
}