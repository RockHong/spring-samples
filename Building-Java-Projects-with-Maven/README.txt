https://spring.io/guides/gs/maven/

## create maven project with eclipse
In eclipse, open File->New->Maven Project, click 'simple maven project'; then input group id
and artifact id. Eclipse will help you create a skeleton of a maven project. You don't have to
input command like "mkdir -p src/main/java/hello" mentioned in the tutorial.

## notes
For maven
<modelVersion>. POM model version (always 4.0.0)
"mvn package" will package your .class files (created from "mvn compile") into package, like
.jar or .war package. 
