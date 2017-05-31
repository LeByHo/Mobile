var mysql = require('mysql');
var conn = mysql.createConnection({
    host : 'localhost',
    user : 'root',
    password :'12345',
    database : 'my_db'
});

conn.connect();
/*
var sql = 'SELECT * FROM user';
conn.query(sql, function(err,rows,fields){
  if(err){
    console.log(err);
  }else{
    for(var i=0; i<rows.length;i++){
      console.log(rows[i].phonenum);
    }
  }
});
var sql = 'INSERT INTO user (phonenum) VALUES(?)';
var params = ['010-2222-3333'];
conn.query(sql, params, function(err,rows,fields){
  if(err){
    console.log(err);
  }else{
    console.log(rows);
  }
});
var sql = 'UPDATE user SET phonenum=?WHERE phonenum=?';
var params = ['010-9876-5432','010-1111-2222'];
conn.query(sql, params, function(err,rows,fields){
  if(err){
    console.log(err);
  }else{
    console.log(rows);
  }
});*/
var sql = 'DELETE FROM user';
conn.query(sql, function(err,rows,fields){
  if(err){
    console.log(err);
  }else{
    console.log(rows);
  }
});
conn.end();
