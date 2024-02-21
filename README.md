# Java17_Spring

Banuprakash C

Full Stack Architect, Corporate Trainer

Co-founder & CTO: Lucida Technologies Pvt Ltd., 

Email: banuprakashc@yahoo.co.in


https://www.linkedin.com/in/banu-prakash-50416019/

https://github.com/BanuPrakash/Java17_Spring

===================================

Softwares Required:
1)  openJDK 17
https://jdk.java.net/java-se-ri/17

2) IntelliJ Ultimate edition 
https://www.jetbrains.com/idea/download/?section=mac

OR

Eclipse for JEE  
	https://www.eclipse.org/downloads/packages/release/2022-09/r/eclipse-ide-enterprise-java-and-web-developers

3) MySQL  [ Prefer on Docker]

Install Docker Desktop

Docker steps:

```
a) docker pull mysql

b) docker run --name local-mysql –p 3306:3306 -e MYSQL_ROOT_PASSWORD=Welcome123 -d mysql

container name given here is "local-mysql"

For Mac:
docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql


c) CONNECT TO A MYSQL RUNNING CONTAINER:

$ docker exec -t -i local-mysql bash

d) Run MySQL client:

bash terminal> mysql -u "root" -p

mysql> exit

```

Spring Boot 3.0 needs Java 17+

=====================

Java 9 - Java 17 new features

1) Project Jigsaw -- JPMS --> Java Platform Module System --> Java 9

Modularity
* Loose coupling between components
* clear contracts and dependencies between components
* hidden implementation using Strong encapsulation

```
	mylib.jar
	package pkg1;
	public class SampleService {
		Some Repo;

		@Secure("ADMIN") // ROLE is ADMIN
		public void deleteProduct() {
			SomeRepo.delete();
		}
	}

	package pkg2;
	public class SomeRepo {
		// code
		public delete()
		public insert()
		public update()
		public select()
	}

```

Any developer using mylib.jar can access SampleService & SomeRepo [ Problem ]

Prior to JPMS we had OSGi [ Open Service Gateway Initiative] ==> Framework providing Container for loading modules.
OSGi different classloaders for differnt modules.
Dynamically add / remove modules

MANIFEST.MF
EXPORT-Packges: pkg1
IMPORT-Packages: util

=====

Dockerfile
FROM:openjdk8:alphine
COPY target/app.jar app.jar
CMD java -jar app.jar

JDK has many modules --> "rt.jar" and "jce.jar"
javax.xml
java.sql
java.logging
...

```
import java.util.List;
public class MainClass {
	public static void main(String[] args) {
		List<Product> products  = ...
	}
}
```

with JPMS with create JLink
You can use the jlink tool to assemble and optimize a set of modules and their dependencies into a custom runtime image.

Module Types:

1) System modules
	modules included in Java SE 
	java --list-modules ==> 70+ modules as of java 9

```
java --describe-module java.sql
java.sql@17.0.5
exports java.sql // exporting package
exports javax.sql // exporting
requires java.xml transitive // needs
requires java.base mandated // needs
requires java.logging transitive // needs
requires java.transaction.xa transitive // needs
uses java.sql.Driver // interface
```

If i use java.sql module 
my app:
requires java.sql
requires javax.xml // no required

2) Named / Application Modules: these modules are what we build
--> needs module-info.class <-- module-info.java

module com.adobe.security {
	export com.adobe.service;
	requires com.adobe.internal; // JWT Token, ....
}

3) Automatic Modules
	--> adding unofficial modules [ without module-info.java] by adding "JAR" files to "module path" [not classpath]
	--> most of the 3rd party libraries existing now are not "named modules" like Spring Framework
	--> treats "jar" file name as "module" name --> spring.core.jar ==> module "spring.core"

	module myapp {
		requires spring.core;
		requires spring.context;
		requires spring.beans;
	}

4) Unnamed modules --> "Jar" file added to classpath, not to "module path". Backward compatibility


```
Automatic Modules:
common.jar ==> built without "named module" --> doesn't contain "module-info.java"

When this jar is added to "module-path" in our project created as named module --> "common" becomes "Automatic modules"
```


Maven Modules and JPMS Module system and ServiceLocator

Step 1:
```
MultiModuleServiceProject --> Java -> Maven Project
pom.xml -->  <packaging>pom</packaging>
```
Step 2:
```
Right click --> New Module --> api
module api {
    exports com.adobe.api;
}
```
Step 3:
Right click --> New Module --> impl
```
mvn package

java -p client/target/client-1.0.0.jar:api/target/api-1.0.0.jar:impl/target/impl-1.0.0.jar -m client/client.Main

jlink --module-path client/target/client-1.0.0.jar:api/target/api-1.0.0.jar:impl/target/impl-1.0.0.jar --add-modules client,api,impl --output myimage --launcher MYAPP=client/client.Main

"myimage" is self-contained with necessary "java module" --> Docker

MultiModuleServiceProject % ./myimage/bin/MYAPP
STDOUT: Log written by class com.adobe.impl.LogServiceStdOut

```


Prior to Java 9 after Java 7

com.example.CodecSet --> interface
com.example.impl.StandardCodecs --> implementation

create a file "fully qualifed interface name" 

META-INF/services/com.example.CodecSet
com.example.impl.StandardCodecs    # Standard codecs

ServiceLoader<CodecSet> loader
     = ServiceLoader.load(CodecSet.class);



CQRS

insertProduct() --> Write Database impl and Read Database thro Queue


---

Feature 2: use typecasted variable directly
```

public static void main(String[] args) {
	Object obj = "Hello World";
	if(obj instanceof String) {
		String s = (String) obj;
		System.out.println(s.length()); 
	}
}

With Java 12:

public static void main(String[] args) {
	Object obj = "Hello World";
	if(obj instanceof String s) {
		//String s = (String) obj; no need to explicit typecast once again
		System.out.println(s.length()); 
	}
}
```

Feature 3: Arrow Switch Statements, yield
```
public class SwitchExample1 {
    public static void main(String[] args) {
        System.out.println(getValue("c"));

    }

    private static int getValue(String mode) {
        // java 12 introduced Arrow
        int result = switch (mode) {
            case "a", "b" -> 1;
            case "c" -> 2;
//            case "d", "e" -> {
//                System.out.println("d and e");
//                return 3; // won't work
//            }
            default -> 0;
        };
        return  result;
    }
}

```

Feature 4: java 13 introduced "yield" to solve above problem

```
public class SwitchExample1 {
    public static void main(String[] args) {
        System.out.println(getValue("c"));

    }

    private static int getValue(String mode) {
        // java 13 introduced yield instead of using Arrow
        int result = switch (mode) {
            case "a", "b": yield  1;
            case "c": yield 2;
            case "d", "e": {
                System.out.println("d and e");
                yield 3; // instead of return use yield
            }
            default: yield  0;
        };
        return  result;
    }
}

```

Feature 5: sealed --> Java 15 version
Main Goal: a sealed type has a fixed set of direct subtypes
* The direct subtypes of a sealed type must be listed in a "permits" clause or, if there is no such clause it should be in a single source file
* direct subtypes of a sealed type must be final, sealed or non-sealed
```

sealed interface JSONValue permits JSONObject, JSONArray, JSONPrimitive {

}

// valid
final class JSONObject implements JSONValue {

}

sealed class JSONPrimitive implements JSONValue permits JSONString, JSONNumber, JSONBoolean, JSONNull {

}

```

use case of "non-sealed"

sealed class Node permits Element, Text, Comment, CDATASection {

}

we have many Element types --> for example HTML elements div, p, ...
We can have our own web-components ==> Adobe Web components
<card></card>

non-sealed class Element extends Node {

}

<person>
	<age>33</age>
	<!-- -->
</person>

=====

Feature 6: Record

record --> final , immutable data object --> DTOs --> Similar to @Value of lombok

record ProductDTO(String name, double price) {}

--> generate constructor, getters, toString, equals
--> no setters

new ProductDTO("iPhone", 89000.00);

```
public sealed interface TrafficLight permits RedLight{
}

record RedLight() implements  TrafficLight {}

```

Patterns in switch are not supported at language level '17'

 javac --source 17 --enable-preview -Xlint:preview *.java

 =======

Day 2

Recap:
Java 9 - 17 features
1) Jigsaw Project ==> JPMS, JLink
2) sealed, permits, final, non-sealed
3) new way to  typecast in conditional statement
4) arrow in switch statement
5) "yield"
6) record

Record --> immutable objects --> to reduce bilierplate code in DTOs

```

public record Person(String name, int age) {}

public class Person(String name, int age) {
	constructor
	getters
	hashCode
	equals
	string
}

override the default behaviour

public record Person(String name, int age) {
	// constructor
	public Person {
		if(age < 0) {
			throw new IllegalArgumentException("Age should be positive value");
		}
	}
}

public record Person(String name, int age) {
	// getter
	@Override
	public String name() {
		return name.toUpperCase();
	}
}
```

Records has temporary tuples

public static void main(String[] args) {
	System.out.println(longest(Stream.of("Potter", "Angelina", "Brad", "George"));
}
// Brad, 4
// Potter, 6
private static String longest(Stream<String> stream) {
	record StrLen(String s, int length) {}
	return stream.map(s -> new StrLen(s, s.length()))
		.max(Comparator.comparingInt(StrLen:length))
		.map(strRecord -> strRecord.s);
}

====

Feature JShell --> Java 9 --> REPL
The Java Shell tool (JShell) is an interactive tool for learning the Java programming language and prototyping Java code.

jshell
jshell> write java statements
jshell> /exit

----

Local variable type inference --> Java 10
Java 10 introcuded a "var" keyword

String text = "Hello Java";
var text = "Hello Java";

variables declared with "var" are still statically typed
// ArrayList<Map<String,List<Integer>>>() myData = ...
var myData = new ArrayList<Map<String,List<Integer>>>();
for(data : myData) {
	
}
// Cannot infer type:
1) var data; // not valid
2) var nothing = null; // not valid
3) var lambda = () -> System.out.println("Not Valid");

Predicate<Integer> pred = (var t) -> true; // valid

================

CDS and Application CDS

Class Data Sharing --> Java 10
list of classes --> only JDK classes

JRE --> ClassLoader
	* findLoadedClass(), loadClass() / findSystemClass(), verifyClass(), defineClass() ==> JVM Class Metaspace

java -Xshare:dump
uses
/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/lib/classlist
and 
create shared archive file /Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/lib/server/classes.jsa


Java 12 --> Application Class data Sharing

codes/
java -jar AppCDS.jar 
Started Demo1Application in 0.874 seconds

java -XX:ArchiveClassesAtExit=appCDS.jsa -jar AppCDS.jar

java -XX:SharedArchiveFile=appCDS.jsa -jar AppCDS.jar
Started Demo1Application in 0.703 seconds

javac Sample.java
% java -XX:ArchiveClassesAtExit=sampleCDS.jsa -XX:DumpLoadedClassList=hello.lst Sample

% java -Xshare:on -XX:SharedArchiveFile=sampleCDS.jsa Sample

% java -verbose -Xshare:on -XX:SharedArchiveFile=sampleCDS.jsa Sample

====================

Garbage Collection:
1) EpsilonGC --> Java 11
A No-Op Experimental Garbage Collector
Garbage Collector only for memory allocation and doesn't clear memory

* MemoryLeak 
* Short running application -> Finapp -> Start app @ 9:00 and shutdown by @5:00

---

Java 9 Feature : run single file without compilation
java MemoryPolluter.java 

We are having GC Running

 java -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC MemoryPolluter.java

======

2) Java 9 --> CMS old GC is no longer supported

G1GC --> Garbage First Garbage Collector --> recommended for Heap size > 4GB

Partitions of heap area of equal size (1MB to 32MB)


======================

Java 12: Multiline String

jshell> String s  ="""
   ...> Hello Wordl
   ...> 123
   ...> Good Day"""

================================

Spring and Spring Boot Framework

SOLID Desing Principles:
D --> Dependency Injection

OOP --> in Real world, objects works based on DI [IOC]

Spring Framework is a light weight container which provides DI thro; IOC
Spring Framework --> like Service Providers

Spring Framework has many modules:
1) Core Module --> provides DI support and life cycle management of beans
2) WEb module
3) AOP module
4) EAI module
...

Bean --> Java 1.2 version --> Any reusable software component

Spring bean --> any object which is managed by Spring Container

==========

```
public interface EmployeeDao {
	addEmployee();
}

public class EmployeeDaoJdbcImpl implements EmployeeDao {
	addEmployee() {..}
}


public class EmployeeDaoMongoImpl implements EmployeeDao {
	addEmployee() {..}
}

public class AppService {
	EmployeeDao empDao ; //dependency --> No tight coupling

	public void setDao(EmployeeDao dao) {
		this.empDao = dao;
	}

	public void insert(){
		this.empDao.addEmployee(){..}
	}
}
```

xml as metadata
beans.xml
<beans>
	<bean id="mongo" class="pkg.EmployeeDaoMongoImpl" />
	<bean id="jdbc" class="pkg.EmployeeDaoJdbcImpl" />

	<bean id="service" class="pkg.AppService">
		<property name="dao" ref="jdbc" />
	</bean>
</beans>

```

public class AppService {
	EmployeeDao empDao ; //dependency --> No tight coupling

	public AppService(EmployeeDao dao) {
		this.empDao = dao;
	}

	public void insert(){
		this.empDao.addEmployee(){..}
	}
}

```
<beans>
	<bean id="mongo" class="pkg.EmployeeDaoMongoImpl" />
	<bean id="jdbc" class="pkg.EmployeeDaoJdbcImpl" />

	<bean id="service" class="pkg.AppService">
		<constructor arg="0" ref="jdbc" />
	</bean>
</beans>

ApplicationContext ctx = new ClassPathApplicationContext("beans.xml");

ApplicationContext --> interface to the Spring Container
BeanFactory is other interface

// get bean from Container
ctx.getBean("service");
ctx.getBean("jdbc");

======================================================

Annotation as metadata 

DI Framework: Spring, Gooogle Guice, Play, ...

Spring Frameworks instantiates classes which contain one of these annotations:
1) @Component
2) @Repository
3) @Service
4) @Configuration
5) @Controller
6) @RestController
7) @ControllerAdvice

Wiring is done using:
1) @Autowired
2) @Inject
3) use constructor
```
public interface EmployeeDao {
	addEmployee();
}

package com.adobe.prj.dao;

@Repository
public class EmployeeDaoJdbcImpl implements EmployeeDao {
	addEmployee() {..}
}

@Repository
public class EmployeeDaoMongoImpl implements EmployeeDao {
	addEmployee() {..}
}


package com.adobe.prj.service;

@Service
public class AppService {
	@Autowired
	EmployeeDao empDao ; 

	public void insert(){
		this.empDao.addEmployee(){..}
	}
}

ApplicationContext ctx = new AnnotationConfigApplicationContext();
ctx.scan("com.adobe.prj"); // scan package and sub-packages
ctx.refresh();

ctx.getBean("...");


```

@Repository(name="jdbc")
public class EmployeeDaoJdbcImpl implements EmployeeDao {


Spring uses "JavaAssist" / "ByteBuddy" for byte code instrumentation
and uses "CGLib" for creating "proxies"

@Autowired is a setter injection based on "type"

employeeDaoMongoImpl

@Repository advantage
```
https://github.com/spring-projects/spring-framework/blob/main/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml

public class EmployeeDaoJdbcImpl implements EmployeeDao {

	addEmployee(Employee e) {
		try {

		} catch(SQLException ex) {
			if(ex.getErrorCode() === 1521) {

			} else if(ex.getErrorCode() == 90001) {

			}
		}
	}
}

```

Spring Boot Framework on top of Spring Framework
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
  </dependency>

Spring Boot 3.x is built on top of Spring Framework 6.x

Advantages of Spring Boot:
1) highly opinated framework
2) lots of configuration comes out of the box
Example:
1) If we decide to use Web application, Spring boot provides Tomcat as Embedded Servlet Container
2) if we decide to use database, Database connection pool is done out of box
3) If we are building RESTful web services it provides Jackson library configures to convert Java <--> JSON

```
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

SpringApplication.run ==> similar to new AnnotationConfigApplicationContext()

@SpringBootApplication has 3 parts:
1) @ComponentScan
2) @EnableAutoConfiguration ==> required built-in beans based on type of application [beans for spring "jar"]
3) @Configuration
```

Could not autowire. There is more than one bean of 'EmployeeDao' type.
Beans:
employeeDaoDbImpl   (EmployeeDaoDbImpl.java) 
employeeDaoMongoImpl   (EmployeeDaoMongoImpl.java)

Solution 1: @Primary
```
@Primary
@Repository
public class EmployeeDaoMongoImpl implements  EmployeeDao{

@Repository
public class EmployeeDaoDbImpl implements  EmployeeDao{
```

Solution 2: @Qualifier
```
remove @Primary

and add
 @Autowired
 @Qualifier("employeeDaoMongoImpl")
 private EmployeeDao employeeDao; // autowire by type
```

Solution 3: @Profile
```
@Repository
@Profile("dev")
public class EmployeeDaoMongoImpl implements  EmployeeDao{

@Repository
@Profile("prod")
public class EmployeeDaoDbImpl implements  EmployeeDao{

application.properties
spring.profiles.active=dev

OR

program arguments:
java --spring.profiles.active=dev pck.DemoApplication

```

Solution 4: Custom properties
```
application.properties
dao=JDBC

@ConditionalOnProperty(name="dao", havingValue = "MONGO")
public class EmployeeDaoMongoImpl implements  EmployeeDao{

@ConditionalOnProperty(name="dao", havingValue = "JDBC")
public class EmployeeDaoDbImpl implements  EmployeeDao{
```

Solution 5:
```
@ConditionalOnMissingBean(EmployeeDaoMongoImpl.class)
public class EmployeeDaoDbImpl implements  EmployeeDao{
```
============

Day 3

Recap: 
* Record
* CDS and Application CDS
* Garbage Collection
* Spring Framework and Spring Boot
Metadata --> XML / Annotation
BeanFactory and ApplicationContext interfaces to access Spring Container
new ClassPathXmlApplicationContext()
new FilePathXmlApplicationContext()
new AnnotationConfigApplicationContext()
SpringApplication.run()

ApplicationContext --> prefer this if your application is using multiple contexts [ EAI ]
a) need interaction between SpringContainer and ServletContainer/ServletContext 
b) need interaction between SpringContainer with PersistenceContext [ RDBMS ]

BeanFactory --> use it only for small applications where DI and lifecycle management of a bean is required.

@Autowired --> wires based on type

@Primary, @Qualifier, @Profile, @ConditionalOnProperty, @ConditionalOnMissingBean, @ConditionOnBean

==================
Scope of bean: By default a bean is singleton
@Scope
1) singleton [default]
2) prototype
```
@Service
@Scope("prototype")
public class SampleService {

}


@Service
public class AService {
	@Autowired
	SampleService s;
}

@Service
public class BService {
	@Autowired
	SampleService s;
}

ctx.getBean("sampleService"); // get different bean instance

```
3) request @Scope("request") --> is available only in WebContext

@Service
@RequestScope // prefer
public class SampleService {

}

request.setAttribute("sampleService", new SampleService());

4) session 

@Service
@SessionScope // prefer
public class CartService {

}

these beans are attached to HttpSession, and is available for conversational state of a client
on Session Creation bean is attached
session.setAttribute("cartService", new CartService());

session.invalidate(); // bean is destroyed

5) application 
similar to Singleton

==========================================

Factory method --> @Bean

1) 3rd party classes as beans
2) Spring uses it's own way of intializing object

<dependency>
	<groupId>com.mchange</groupId>
	<artifactId>c3p0<artificatId>
	<version>0.9.5.5</version>
</dependency>

provides ComboPooledDataSource

```
@Service
public class AppService {
	@Autowired
	DataSource ds; 
}
```
Solution:

```
@Configuration
public class AppConfig {
	// factory method
	@Bean
	public DataSource dataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
		cpds.setUser("swaldman");                                  
		cpds.setPassword("test-password");                                  
	
		// the settings below are optional -- c3p0 can work with defaults
		cpds.setMinPoolSize(5);                                     
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);

		return cpds; // returned object is managed by spring container
	}

	@Bean
	public LocalContainerEntityManagerFactory emf(DataSource ds) {
		LocalContainerEntityManagerFactory emf = new LocalContainerEntityManagerFactory();
		emf.setDatSource(ds);
		emf.setJpaVendor(new HibernateJpaVendor());
		emf.setPackagesToScan("com.adobe.prj.entity");
		...
		return emf;
	}
}

@Service
public class AppService {
	@PersistenceContext
	EntityManager em;

	 public void addProduct(Product product) throws PersistenceException {
			em.persist(product);
	 }
}
```

Building RESTful WS with Spring Data JPA

JPA --> Java Persistence API is a specification to use ORM (Object Relational Mapping)

Entity class <---> Relational database table

@Entity
@Table(name="books")
public class Book {
	@Id
	private String isbn; // column is isbn

	private String title; // column title

	@Column(name="amount")
	private double price; // column amount

	private int quantity;
}

ORM frameworks are going to generate CRUD code

ORM Frameworks:
1) Hibernate
2) TopLink
3) KODO
4) JDO
5) ...


```
JDBC Code:

    @Override
    public void addProduct(Product product) throws PersistenceException {
        String SQL = "INSERT INTO products(id, name, price, quantity) VALUES(0, ? ,? ,?)";
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getQuantity());
            ps.executeUpdate();
        } catch (SQLException e) {
           // log
            throw  new PersistenceException("unable to add product", e);
        } finally {
            DBUtil.closeConnection(con);
        }
    }

JPA/ ORM Code:
   @Override
    public void addProduct(Product product) throws PersistenceException {
		session.persist(product); // HIBERNATE code
		OR
		entityManager.save(product); // JPA code
	}

```

Spring Boot by default uses Spring Data JPA
Spring Data JPA is opiniated:
1) provides HikariCP data connection pool
2) uses Hiberante as JPA Vendor
3) provides interfaces which generates code for default CRUD operation

JpaRepository<T, ID> 

public interface BookDao extends JpaRepository<Book, String> {

}
 S save(S entity)
List<T> findAll()

bookDao.findAll();
bookDao.save(book);
Optional<T> findById(ID id)

=========================================================
Orderapp:
Spring boot Application with MySQLDriver and Spring Data JPA dependencies

https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html

1) spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

bookDao.save(book); ==> has to generate SQL to match MySQL

2) 
spring.jpa.hibernate.ddl-auto=create

create table and drop table on application exit

spring.jpa.hibernate.ddl-auto=update
--> use table if it exists
--> create table if not exists for entity
--> alter table if required [ adding columns, change length ]

spring.jpa.hibernate.ddl-auto=verify
--> use existing table if it matches else throw exception

Resume @ 11:30

