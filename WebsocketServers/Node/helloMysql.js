var mysql = require('mysql');
var pool  = mysql.createPool({
  connectionLimit : 10,
  host     : '130.237.84.126',
  user     : 'root',
  password : 'ninjakick',
  database : 'socialnetwork'
});

var queryString2 = 'call newMessage('+ pool.escape('urbansson') + ',' + pool.escape('dolphan')+ ',' +pool.escape('Hejsan från node') + ')';


console.log(queryString2);
pool.query(queryString2, function(err, rows, fields) {
  if (err) throw err;

  console.log('The solution is: ', rows[0][0].success);

/*
  for (var i = 0; i < rows.length; i++) {
      console.log(rows[i]);
  };
*/
    pool.end(function (err) {
    // all connections in the pool have ended
    console.log("closed");
    });
});
/*
pool.getConnection(function(err, connection) {
  // Use the connection
  connection.query( 'SELECT something FROM sometable', function(err, rows) {
    // And done with the connection.
    connection.release();

    // Don't use the connection here, it has been returned to the pool.
  });

*/