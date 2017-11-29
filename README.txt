
How to setup the project:

1.) Install MySQL workbench 
	https://dev.mysql.com/downloads/workbench/

2.) Install Java Development Kit (JDK) version 1.8.0_121
	http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

3.) Install the latest version of Eclipse EE
    https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/neon3
	
	Set it up to use the JDK installed in step 2. 

4.) Within Eclipse, navigate to Help -> Install New Software, select software site,  
    install the "Web, XML, Java EE and OSGi Enterprise Development" package. 

5.) Install Apache Tomcat version 7.0
	
6.) In the MySQL workbench, enter the following command:

		CREATE DATABASE big_data;

	Import SQL text files from project folder into the MySQL workbench by clicking
    "import from disk", "import from self contained file", and enter the file path 
    to the desired SQL text file. Finally, click "start import". 

7.) Create a new Web Project in Eclipse. 
	Right click build folder -> Build path -> configure build path -> libraries ->
    "add external jars", add all of the jar files located in the sub folder "jar files".
	
	In the file system, navigate to Apache Tomcat 7.0's library folder,
	add unity and mysql jar files from the sub folder "jar files". 
	
8.) Set up server:
    Window -> Show View -> Other -> Server -> Servers
    Click "Servers" -> select Tomcat 7.0

9.) In Web Content folder of the project, place: index.jsp, fall.jsp, output.jsp,
    rise.jsp, and image.jpg

10.) Create "com" package in src, add java file WekaTest.java into it.

11.) In the WekaTest.java file, on line 62, change the string inside of the 
     "new FileReader" parentheses to the path of profile.txt on your machine.
     On line 104, change the "password" value to your MySQL password. 

12.) Right click project folder ->  Run as -> Run as server 
