This folder contains two applications:
-embedded - the application that uses embedded H2 database
-server - the application the connects to remote db4free MySQL database. Since it relies on remote database, this application might not be working in future

The application uses Swing as Graphic User Interface and JDBC as library to connect both MySQL and H2 database.
It runs on both JDK 1.7 and 1.8