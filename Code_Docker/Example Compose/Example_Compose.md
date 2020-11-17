- [Example Docker Compose](#example-docker-compose)
  * [TL;DR](#tl-dr)
  * [Adding Angular Container](#adding-angular-container)
    + [Creating the Folder](#creating-the-folder)
    + [Creating the Dockerfile](#creating-the-dockerfile)
  * [Adding Express JS Container](#adding-express-js-container)
    + [Creating the Folder](#creating-the-folder-1)
    + [Creating the Dockerfile](#creating-the-dockerfile-1)
    + [Creating the Packackge.json](#creating-the-packackgejson)
  * [Creating the "Stack"](#creating-the--stack-)
    + [Creating the Compose file](#creating-the-compose-file)
    + [Building the Stack](#building-the-stack)
    + [Running the Stack](#running-the-stack)

# Example Docker Compose 

## TL;DR  
Copy the full code into a File with the ending .sh  
For Example you can use the sequence :  
    1. `vim installMe.sh`  
    2. Insert the Code down Below or use ` wget https://raw.githubusercontent.com/Mueller-Patrick/SE-e-Portfolio/master/Code_Docker/Example%20Compose/installMe.sh`   
    3. `chmod 700 installMe.sh`  
    4. `./installMe`  
    5. `docker-compose build`  
    6. `docker-compose up`  
<details>
    <summary>Click to toggle contents of `code`</summary>

    #!/bin/sh
    mkdir angular-app
    mkdir express-server
    cat > ./package.json << ENDOFFILE
    {
      "name": "frontend",
      "version": "0.0.0",
      "scripts": {
        "ng": "ng",
        "start": "ng serve",
        "build": "ng build",
        "test": "ng test",
        "lint": "ng lint",
        "e2e": "ng e2e"
      },
      "private": true,
      "dependencies": {
        "@angular/animations": "~10.2.0",
        "@angular/common": "~10.2.0",
        "@angular/compiler": "~10.2.0",
        "@angular/core": "~10.2.0",
        "@angular/forms": "~10.2.0",
        "@angular/platform-browser": "~10.2.0",
        "@angular/platform-browser-dynamic": "~10.2.0",
        "@angular/router": "~10.2.0",
        "rxjs": "~6.6.0",
        "tslib": "^2.0.0",
        "zone.js": "~0.10.2"
      },
      "devDependencies": {
        "@angular-devkit/build-angular": "~0.1002.0",
        "@angular/cli": "~10.2.0",
        "@angular/compiler-cli": "~10.2.0",
        "@types/node": "^12.11.1",
        "@types/jasmine": "~3.5.0",
        "@types/jasminewd2": "~2.0.3",
        "codelyzer": "^6.0.0",
        "jasmine-core": "~3.6.0",
        "jasmine-spec-reporter": "~5.0.0",
        "karma": "~5.0.0",
        "karma-chrome-launcher": "~3.1.0",
        "karma-coverage-istanbul-reporter": "~3.0.2",
        "karma-jasmine": "~4.0.0",
        "karma-jasmine-html-reporter": "^1.5.0",
        "protractor": "~7.0.0",
        "ts-node": "~8.3.0",
        "tslint": "~6.1.0",
        "typescript": "~4.0.2"
      }
    }
    ENDOFFILE
    #!/bin/sh
        mkdir angular-app
        mkdir express-server
    
    cp ./package.json ./angular-app
    cp ./package.json ./express-server
    
    rm package.json
    
    cat > ./angular-app/dockerfile << ENDOFFILE
    FROM node
    RUN mkdir -p /usr/src/app
    WORKDIR /usr/src/app
    COPY package.json /usr/src/app
    RUN npm cache clean --force
    RUN npm install
    COPY . /usr/src/app
    EXPOSE 4200
    CMD ["npm","start"]
    ENDOFFILE
    
    cat > ./express-server/dockerfile << ENDOFFILE
    FROM node
    RUN mkdir -p /usr/src/app
    WORKDIR /usr/src/app
    COPY package.json /usr/src/app
    RUN npm cache clean --force
    RUN npm install
    COPY . /usr/src/app
    EXPOSE 4200
    CMD ["npm","start"]
    ENDOFFILE
    
    cat > ./docker-compose.yml << ENDOFFILE
    version: '3.0' # specify docker-compose version
    # Define the services/ containers to be run
    
    services:
     angular: # name of the first service
      build: angular-app # specify the directory of the Dockerfile
      ports:
      - "4200:4200" # specify port mapping
     
     express: # name of the second service
      build: express-server # specify the directory of the Dockerfile
      ports:
      - "3000:3000" #specify ports mapping
      links:
      - database # link this service to the database service
     
     database: # name of the third service
      image: mongo # specify image to build container from
      ports:
      - "27017:27017" # specify port forwarding
    ENDOFFILE
</details>

## Adding Angular Container 
You need to have an Example Angular Project in this Folder!!!! (Your src/app/ Folder)
### Creating the Folder  
```
mkdir angular-app
```
### Creating the Dockerfile  
```
cat > ./angular-app/dockerfile << ENDOFFILE
FROM node
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY package.json /usr/src/app
RUN npm cache clean --force
RUN npm install
COPY . /usr/src/app
EXPOSE 4200
CMD ["npm","start"]
ENDOFFILE
```

## Adding Express JS Container  
### Creating the Folder  
```
mkdir express-server
```
### Creating the Dockerfile  
```
cat > ./express-server/dockerfile << ENDOFFILE
FROM node
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY package.json /usr/src/app
RUN npm cache clean --force
RUN npm install
COPY . /usr/src/app
EXPOSE 4200
CMD ["npm","start"]
ENDOFFILE
```
### Creating the Packackge.json  
```
cat > ./express-server/package.json << ENDOFFILE
{
  "name": "frontend",
  "version": "0.0.0",
  "scripts": {
    "ng": "ng",
    "start": "ng serve",
    "build": "ng build",
    "test": "ng test",
    "lint": "ng lint",
    "e2e": "ng e2e"
  },
  "private": true,
  "dependencies": {
    "@angular/animations": "~10.2.0",
    "@angular/common": "~10.2.0",
    "@angular/compiler": "~10.2.0",
    "@angular/core": "~10.2.0",
    "@angular/forms": "~10.2.0",
    "@angular/platform-browser": "~10.2.0",
    "@angular/platform-browser-dynamic": "~10.2.0",
    "@angular/router": "~10.2.0",
    "rxjs": "~6.6.0",
    "tslib": "^2.0.0",
    "zone.js": "~0.10.2"
  },
  "devDependencies": {
    "@angular-devkit/build-angular": "~0.1002.0",
    "@angular/cli": "~10.2.0",
    "@angular/compiler-cli": "~10.2.0",
    "@types/node": "^12.11.1",
    "@types/jasmine": "~3.5.0",
    "@types/jasminewd2": "~2.0.3",
    "codelyzer": "^6.0.0",
    "jasmine-core": "~3.6.0",
    "jasmine-spec-reporter": "~5.0.0",
    "karma": "~5.0.0",
    "karma-chrome-launcher": "~3.1.0",
    "karma-coverage-istanbul-reporter": "~3.0.2",
    "karma-jasmine": "~4.0.0",
    "karma-jasmine-html-reporter": "^1.5.0",
    "protractor": "~7.0.0",
    "ts-node": "~8.3.0",
    "tslint": "~6.1.0",
    "typescript": "~4.0.2"
  }
}
ENDOFFILE
```

## Creating the "Stack"  
### Creating the Compose file  
```
cat > ./docker-compose.yml << ENDOFFILE
version: '3.0' # specify docker-compose version
# Define the services/ containers to be run

services:
 angular: # name of the first service
  build: angular-app # specify the directory of the Dockerfile
  ports:
  - "4200:4200" # specify port mapping
 
 express: # name of the second service
  build: express-server # specify the directory of the Dockerfile
  ports:
  - "3000:3000" #specify ports mapping
  links:
  - database # link this service to the database service
 
 database: # name of the third service
  image: mongo # specify image to build container from
  ports:
  - "27017:27017" # specify port forwarding
ENDOFFILE
```
### Building the Stack  
``` docker-compose build  ```
### Running the Stack  
``` docker-compose up  ```
