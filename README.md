# JAppStream
Java application to stream frame data from a remote windows application.

Please check 'JAppStream/out/artifacts/' directory for the client and server JAR files.

To run these programs you will need to have a minimum of Java 8 installed on your machine.

You can run each program with or without command line arguments:

C:\dev\JAppStream>java -jar out\artifacts\JAppStreamClient\JAppStreamClient.jar [HOST] [PORT]
C:\dev\JAppStream>java -jar out\artifacts\JAppStreamServer\JAppStreamServer.jar [APP_QUERY] [HOST] [PORT]

HOST = Hostname/IP of the remote host as client or local computer as server

PORT = Port number of the remote host as client or local computer as server

APP_QUERY = Substring of the window title as shown at the top of the window or task manager.

NOTE: It is recommended that you increase your default max RAM allocation 
with -Xmx?G and replace '?' with the desired amount of GBs from RAM
Google search 'Java Memory Settings' for more info. (I will be adding a .bat later on...)

When cloning this repository for development and opening in IntelliJ: 
1) Ensure that the root of the application is at 'C:\dev\JAppStream'.
2) For Run/Debug Configurations, the main classes are com.app.stream.server.core.Main and com.app.stream.client.core.Main

If you end up using or liking any of this code a star on this repo would be highly appreciated and may give me more of a drive to continue :) 