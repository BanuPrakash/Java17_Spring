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


