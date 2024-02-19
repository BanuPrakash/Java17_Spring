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

======

