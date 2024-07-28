set path=C:\Program Files\Java\jdk-14.0.2\bin;.;
set classpath=lib/logback-core-1.2.3.jar;lib/logback-classic-1.2.3.jar;lib/bcprov-jdk15on-1.61.jar;lib/slf4j-api-1.7.25.jar;lib/liquidlnf.jar;lib/Panel.jar;.;
javac -d . *.java
java -Xmx1000M com.Login
pause