javac --module-source-path src -p mods -m main.app  -d out
java -p out:mods -m main.app/com.adobe.main.Main

-cp ==> classpath
-p ==> module-path adding jars into module path becomes automatic module
-m module