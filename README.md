# javamicroservice
There are three projects. Master, slave & core. Slave project will hold information on all running microservices local to it.
For this to setup core projects will have to be integrated with microservices.

Maven dependency:
```xml
<dependency>
    <groupId>com.javamicroservice</groupId>
    <artifactId>cloud-cluster-core</artifactId>
    <version>1.0.0</version>
</dependency>

One sample can be found at test project.

The concept is:
1. Microservice instances will call local slave instance to register.
2. Slave instances from different systems will update one master instance.
3. Master instance will show the registered microservices.
4. From master/slave instance of any registered microservice can be started/stopped.
5. From master/slave we can craete more instances of registered microservices on the fly.
