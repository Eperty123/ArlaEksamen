# Arla Infographic System

![Project Image](https://i.imgur.com/LzeXsuq.png)

> Clean, easy to use Infographic system, Developed for Arla Foods Denmark.

---

### Table of Contents


- [Description](#description)
- [How To Use](#how-to-use)

---

## Description

Arla Infographic System or AIS is a clean, all in one infographic system created and specifically catered for Arla Foods Denmark as part of our first year computer science exam.

#### Technologies used in this project

- Database calls.
- Crud operations to add new employees aswell as edit, view and remove them from the system and database.
- Screen creator for admins or higher ranking users.
- Screen viewing for employees which can contain all JavaFX chart types aswell as a webview, PDF View and a configurable message.
- A messaging system in which a higher ranking employee can set a time based message for each individual screen the higher ranking employee has access to.
- An orginization diagram display which displays all employees sorted after title, with the ability to look up a employees personal information name, mail and phone number.


[Back To The Top](#Arla-Infographic-System)

---

## How To Use
The application can be run directly from the intellij IDE with the following VM options and libraries if they are not already set due to maven.

To login and use the system, you can use the following credentials to access different layers of the application.

#### Login Credentials
- Regular Employee
    - Username: usrtest
    - Password: testu
- Manager
    - Username: mgrtest
    - Password: testm
- IT-Administrator
    - Username: admtest
    - Password: testa

#### VM options
```
--module-path
lib
--add-modules
javafx.controls,javafx.fxml,javafx.media,javafx.web
--add-exports
javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED
--add-opens
"javafx.graphics/javafx.css=ALL-UNNAMED"
--add-opens
javafx.base/com.sun.javafx.runtime=ALL-UNNAMED
--add-opens
javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED
--add-opens
javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED
--add-opens
javafx.base/com.sun.javafx.binding=ALL-UNNAMED
--add-opens
javafx.base/com.sun.javafx.event=ALL-UNNAMED
--add-opens
javafx.graphics/com.sun.javafx.stage=ALL-UNNAMED
```

#### Libraries

- com.jfoenix:jfoenix:9.0.10
- com.microsoft.sqlserver:mssql-jdbc:9.1.1.jre15-preview
- com.opencsv:opencsv:5.4
- com.sun.mail:javax.mail:1.6.1
- commons-io:commons-io:2.8.0
- de.jensd:fontawesomefx-fontawesome:4.7.0-9.1.2
- de.jensd:fontawesomefx-materialdesignfont:2.0.26-9.1.2
- mysql:mysql-connector-java:8.0.23
- org.apache.poi:poi-ooxml:4.1.1
- org.apache.poi:poi:4.1.1
- org.junit.jupiter:junit-jupiter:5.5.2
- org.openjfx:javafx-fxml:16
- org.openjfx:javafx-fxml:16-ea+7
- org.openjfx:javafx-web:16

[Back To The Top](#Arla-Infographic-System)

---
