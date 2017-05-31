var express = require("express");
var mysql = require('mysql');
var bodyParser = require("body-parser");
var conn = mysql.createConnection({
    host : 'localhost',
    user : 'root',
    password :'12345',
    database : 'my_db'
});
var app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
const PORT = 3000;

conn.connect(function(err){
  if(!err){
    console.log("Database is connect");
  }else{
    console.log("Error");
  }
});

app.post("/add",function(req,res){
    console.log(req.body.project);
  conn.query('INSERT INTO schedule VALUES (?,?,?,?,?)',[req.body.phonenum,req.body.project,req.body.meeting,req.body.start,req.body.finish],function(err,rows,fields){
    if(!err){
      console.log("EEEEEEEEEEEEE");
    }else{
      console.log(err);
      console.log('Error');
    };
  });
});

app.get("/phonenum/:num",function(req,res){
  conn.query('SELECT * FROM user WHERE phonenum=?',[req.params.num],function(err,rows,fields){
    if(err){
      console.log("ERRRRRORORORORORO");
    }else{
      console.log(rows);
      if(rows.length==0){
          conn.query('INSERT INTO user (phonenum) VALUES(?)',[req.params.num],function(err,rows,fields){
            if(err){
              console.log("INSERT ERROR");
            }else{
              res.send(rows);
              console.log("INSERT SCCUESS");
            }
          });
      }else{
        conn.query('SELECT * FROM schedule WHERE phonenum=?',[req.params.num],function(err,rows,fields){
          //console.log(rows);
          res.send(rows);
          console.log("EXIST");
        })
      }
    }
  });
});

app.get("/project/:name",function(req,res){
  conn.query('SELECT * FROM schedule WHERE project=?',[req.params.name],function(err,rows,fields){
    if(err){
      console.log("Project Error");
    }else{
      console.log(rows);
      res.send(rows);
    }
  });
});


var server = app.listen(PORT,function(){
  console.log("Server is runnung on port 3000");
});
