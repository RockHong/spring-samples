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
https://docs.jboss.org/hibernate/entitymanager/3.5/reference/en/html/configuration.html
	persistence.xml configuration for hibernate, very detail
https://gist.github.com/mortezaadi/8619433
https://gist.github.com/halyph/2990769
	persistence xml configurations for major databases and jpa providers
https://dev.mysql.com/doc/refman/5.5/en/connecting.html
	mysql server default port
	
	
	

