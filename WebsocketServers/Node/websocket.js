var WebSocketServer = require('ws').Server
var wss = new WebSocketServer({port: 8090});
var mysql = require('mysql');
var clients = [];
var clientNames = [];
var pool  = mysql.createPool({
  connectionLimit : 10,
  host     : '130.237.84.126',
  user     : 'root',
  password : 'ninjakick',
  database : 'socialnetwork'
});

wss.on('connection', function(ws) {
    
    //Do the first connection stuff here add client ad such
    //ws.send('Connected');

    var user = (ws.upgradeReq.url).substring(1);
    console.log("Socket is now open + " + user)

    if (!(clients.indexOf(ws) > -1)) {
        console.log("Adding client nr: " +clients.length);
        clients.push(ws);
        clientNames.push(user);
        ws.send("Connected: "+user);
    }

    ws.on('message', function(message) {
		var json = JSON.parse(message);
        var query = 'call newMessage('+ pool.escape(json.sender) + ',' + pool.escape(json.reciver)+ ',' +pool.escape(json.message) + ')';
        pool.query(query, function(err, rows, fields) {
        if (err) throw err;

        if(rows[0][0].success == 1){
            sendTo(json.reciver, message, ws);
        }

        });

    });

    ws.on('close', function(message) {
        console.log('connection was closed ' + message);
        var index = clients.indexOf(ws);

        clients.splice(index,1);
        clientNames.splice(index, 1);

    });

});

function sendTo(reciver, message, senderSocket){

	console.log("Trying to send to: " + reciver);
	console.log("Connected: " + clientNames.toString());

	var reciverId = clientNames.indexOf(reciver);
		if (reciverId > -1) {
			clients[reciverId].send(message);
            senderSocket.send(message);

		}else{
			console.log("No client with that name ");
            senderSocket.send(message);

		}
}

