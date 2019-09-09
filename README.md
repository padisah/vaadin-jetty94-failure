# vaadin-jetty94-failure
This brach contains a workaround for the issue. I could successfully configure using jetty9.4 with JSR356, and also Guice.

The base repository Demonstrates incompatiblity between vaadin 8.8.1 and Jetty 9.4.

executable jar:
mvn clean compile package

Or the org.test.Starter has a main class, that starts up the embedded jetty.
Jetty version is 9.4.18.v20190429
Vaadin version is 8.8.1
