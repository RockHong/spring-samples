No dependency injection.
No spring dependency.
Not live in container. It's a java SE environment. 
Transaction involved.
Database is mysql.

Simple one-to-many relationship.
Generated primary key.
Use Joda time.
Simple query.


### Maven dependency
http://search.maven.org/
you can search dependency here.

what's the artifactId of jpa dependency?
eclipselink has a dependency of jpa. you can include eclipselink temporarily to find out jpa artifactId.
    <dependency>
      <groupId>org.eclipse.persistence</groupId>
      <artifactId>eclipselink</artifactId>
      <version>2.5.2</version>
    </dependency> 

it seems there is not 'offical' jpa artifact for sun/oracle.
below artifact looks like an 'offical' one.
    <dependency>
      <groupId>javax.persistence</groupId>
      <artifactId>persistence-api</artifactId>
      <version>1.0.2</version>
    </dependency>
but it's too old. for example, it does not support @Convert.

we can use the one from eclipselink group.
    <dependency>
      <groupId>org.eclipse.persistence</groupId>
      <artifactId>javax.persistence</artifactId>
      <version>2.1.0</version>
    </dependency>
  
### references
http://www.javacodegeeks.com/2015/02/jpa-tutorial.html
	an example
http://hibernate.org/orm/documentation/getting-started/
	you can find hibernate artifact there
http://hibernate.org/orm/downloads/
	it's said for jpa, should use hibernate-entitymanager artifact instead of hibernate-core. it's ture.
https://docs.jboss.org/hibernate/entitymanager/3.5/reference/en/html/configuration.html
	persistence.xml configuration for hibernate, very detail
https://gist.github.com/mortezaadi/8619433
https://gist.github.com/halyph/2990769
	persistence xml configurations for major databases and jpa providers
https://dev.mysql.com/doc/refman/5.5/en/connecting.html
	mysql server default port
http://antoniogoncalves.org/2014/12/11/generating-database-schemas-with-jpa-2-1/
	Generating Database Schemas with JPA 2.1
http://www.jakobk.com/2014/03/generate-ddl-script-of-jpa-persistence-unit-in-maven-using-hbm2ddl/
	Generate DDL script of JPA persistence unit in Maven
http://stackoverflow.com/questions/297438/auto-generate-data-schema-from-jpa-annotated-entity-classes
	Generate data schema from JPA
https://blogs.oracle.com/arungupta/entry/jpa_2_1_schema_generation
	JPA 2.1 Schema Generation
http://hantsy.blogspot.jp/2013/12/jpa-21-schema-generation-properties.html
https://github.com/hantsy/ee7-sandbox/wiki/jpa-scripts
	JPA 2.1 Schema generation properties, this one is good
http://www.javabeat.net/eclipselink-jpa-ddl-schema/
	EclipseLink – DDL Schema Generation in JPA 2.1, to read
http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch03.html#configuration-optional-dialects
	Hibernate SQL Dialects
http://stackoverflow.com/questions/24650186/choosing-between-java-util-date-or-java-sql-date
	about type for 'date and time'
https://docs.jboss.org/hibernate/entitymanager/3.5/reference/en/html/configuration.html
	here is reference of persistence.xml, good doc
http://stackoverflow.com/questions/2585641/hibernate-jpa-db-schema-generation-best-practices
	let jpa provider generate schema, or not? a discussions


http://www.javaworld.com/article/2077886/data-storage/custom-schema-generation-with-hibernate-annotations.html
http://stackoverflow.com/questions/306806/hibernate-automatically-creating-updating-the-db-tables-based-on-entity-classes
	

