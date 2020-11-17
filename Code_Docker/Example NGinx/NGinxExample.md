First Method
----------

Add nano to the VM
```
apk install nano
```

Copy the whole Code into the Console to
```
# Create a Directory
mkdir page-contents
# Pipe the Content into the File testMe.html
cat > ./page-contents/test.html << ENDOFFILE
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Docker Nginx Example With Mount</title>
</head>
<body>
  <h2>Hello from Nginx container</h2>
</body>
</html>
ENDOFFILE
```
After to start the container with your linked directory ~  
```docker run -d -p 8080:80 --name web -v ~/page-contents:/usr/share/nginx/html nginx```
| Command          	| Parameter                             	| Description                                        	|
|------------------	|---------------------------------------	|----------------------------------------------------	|
| -d \|\| --detach 	|                                       	| Run Container in Background and print Container ID 	|
| -p \|\| --expose 	| 8080:80                               	| Expose a Port or a Range of Ports                  	|
| --name           	| web                                   	| Assign a name to a container                       	|
| -v \|\| --volume 	| ~/page-contents:/usr/share/nginx/html 	| Bind mount a volume                                	|
|                  	| nginx                                 	| Docker Image from Docker Hub                       	|

Second Method
-------------
Copy the whole Code into the Console to
```
cat > ./dockerfile << ENDOFFILE
FROM nginx:latest
COPY ./index.html /usr/share/nginx/html/index.html
ENDOFFILE
cat > index.html << ENDOFFILE
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Docker Nginx Example without Mount</title>
</head>
<body>
  <h2>Hello from Nginx container</h2>
</body>
</html>
ENDOFFILE
```
Build the Dockerfile into an Image
```
docker build -t webserver .
```

Run this new Image with the Config of : Port 8080 of the Container gets linked to 80, the name "web" and the image "webserver"
```
docker run -it --rm -d -p 8080:80 --name web webserver
```
`Important : we just edit the Command from the First Method to use our Local Docker Image`
