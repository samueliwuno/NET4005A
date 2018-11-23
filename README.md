# NET4005A
# Assignment 3
CODE written by Samuel Iwuno and Greg Kingsbury

Class/Interface breakdown
=============================
APIGateway.java - contains code that receives the client requests, and them randomly sends the requests to
the ports where the services live. also contains code the Log the number of services requested and the client info


APIGatewayInterface.java - contains code that makes it possible for the clients to communicate with the APIGateway
without having to generate the stub using wsimport

MyServices.java - contains the configured services A and B. acts as the servers listening on 4 different ports (see publisher)

ServiceInterface.java - contains code that makes it possible for the APIGateway to forward requests to the Servers
without having to generate the stub using wsimport

Publisher.java - contains code to publish Services A and B on multiple ports(9001-9004) 


SoapClient.java - contains the code for the client machine. It contains methods to 
communicate with the server and it also contains methods for a user input menu

A scanner is used for user input. The user is brought to a menu with 3 options. 
Request a Specific service, Print the Log on server Console(publisher), and to exit 


N.B
====================
Problems may occur when importing "com.sun.net.httpserver.HttpExchange" on APIGateway. to fix the issue.
1. rightclick on package 
2. click BuildPath -> configure BuildPath -> Java BuildPath.
3. on Java BuildPath, click the Libraries Tab, and remove the JRE System Library
4. click Add Library, and Add JRE System Library
5. click next, use the Workspace default JRE and click finish
6. this should fix the issue

Process to Run Code
=====================

1. Run Publisher 
2. Run SoapClient
3. follow instructions on client menu.
4. multiple clients can be instantiated
