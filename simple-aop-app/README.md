use logback for logging.
since we have no logback.xml, logs go to console.

download spring javadoc/source.
http://stackoverflow.com/questions/44396/how-to-reference-javadocs-to-dependencies-in-mavens-eclipse-plugin-when-javadoc
configure maven settings in eclipse preferences.
http://stackoverflow.com/questions/2059431/get-source-jars-from-maven-repository
download via command line
mvn dependency:sources
mvn dependency:resolve -Dclassifier=javadoc
