In Eclipse, New->Maven Project, in "Select an Archetype" dialog, select "maven-archetype-webapp".

After the project is created, if no "src/main/java" there, create it via New->Folder, then input 
"src/main/java". (Not via New->Source Folder)

Create model and controller classes from [this][2].

Modify web.xml. ([reference][3])

Controller is a bean. You need tell spring how to find bean definition/configuration. In this example,
it's in hello-servlet.xml.

Run it using jetty. ([reference][5])
If "mvn jetty:run" does not work, add
  <pluginGroups>
    <pluginGroup>org.mortbay.jetty</pluginGroup>
  </pluginGroups>
in settings.xml. [reference][6]

Logging
Using logback for logging.
TODO: add logback-access


## reference
[1]: http://www.mkyong.com/maven/how-to-create-a-web-application-project-with-maven/
[2]: https://spring.io/guides/gs/rest-service/
[3]: http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html
[5]: http://www.eclipse.org/jetty/documentation/current/maven-and-jetty.html
[6]: https://maven.apache.org/settings.html