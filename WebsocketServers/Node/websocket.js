var clients = [];
var clientNames = [];

var WebSocketServer = require('ws').Server
  , wss = new WebSocketServer({port: 8090});

wss.on('connection', function(ws) {
    
    //Do the first connection stuff here add client ad such
    ws.send('Connected');

	//What to do when we get a message(check with db and send message)
    ws.on('message', function(message) {
		var json = JSON.parse(message);

		if (clients.indexOf(ws) > -1) {
    	//In the array!
         //broadcast(message);
        sendTo(json.reciver, json.message)

		} else {
    	//Not in the array adding client
    	console.log("Adding client nr: " +clients.length);
    	clients.push(ws);
    	clientNames.push(json.sender);

        ws.send("You have been connected!");
         //broadcast(json.message);
        sendTo(json.reciver, json.message)

		}	

    });

    //What to do when a socket is closed(remove client and such)
    ws.on('close', function(message) {
        console.log('connection was closed ' + message);
        var index = clients.indexOf(ws);

        clients.splice(index,1);
        clientNames.splice(index, 1);

    });

});

function broadcast(message) {
    for (var i = 0; i < clients.length; i++) {
        client = clients[i];
        client.send(message);
    }
}

function sendTo(reciver, message){

	console.log("Trying to send to: " + reciver);
	console.log("Connected: " + clientNames.toString());


	var reciverId = clientNames.indexOf(reciver);
		if (reciverId > -1) {
			clients[reciverId].send(message);
		}else{
			console.log("No client with that name");
		}
}

function addToDB(){

	
}

