var express = require('express');
var app = express();
var path = require('path');
var formidable = require('formidable');
var fs = require('fs');
var count = 0;
var indexFileName = '';

app.use(express.static(path.join(__dirname, 'public')));

app.get('/', function(req, res){
  res.sendFile(path.join(__dirname, 'views/index.html'));
});

app.post('/upload', function(req, res){

    // create an incoming form object
    var form = new formidable.IncomingForm();

    // specify that we don't want the user to upload multiple files in a single request
    form.multiples = false;

    // store all uploads in the /uploads directory
    form.uploadDir = path.join(__dirname, '/upload');

    // every time a file has been uploaded successfully,
    // push it to the folder and name it to an index

    form.on('file', function(field, file) {
        fs.rename(file.path, path.join(form.uploadDir, count + '.jpg'));
        indexFileName = file.name;
        count ++;
        // get the json from running the image through the jar
    });

    // log any errors that occur
    form.on('error', function(err) {
      console.log('An error has occured: \n' + err);
    });

    // once all the files have been uploaded, send a response to the client
    form.on('end', function() {
      // sends them to the sources.html where they will see processed sources
      res.sendFile(path.join(__dirname, 'views/sources.html'));
      res.end('success');
    });

    // parse the incoming request containing the form data
    form.parse(req);

});

var server = app.listen(80, function(){
  console.log('Server listening on port 80');
});
