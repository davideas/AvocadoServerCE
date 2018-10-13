# Database
- Install and run MySql 5.7.x as service and MySql Workbench app.
- In Workbench, access as _root_ user and create a new user:
- From menu: Server > Users and Privileges > _button_ Add Account:
  - login: avocado_ce
  - password: avocado_ce
- Now, we want to restrict access for the new schema only, so in same window under tab: Schema Privileges > _button_ Add Entry:
  - In Schema matching pattern add 'avocado_ce' (this will be the schema name)
  - Press OK
  - Select all Object Rights and DDL Rights, deselect Other Rights
  - Press Apply
- Close root window and create a new connection by pressing '+' button:
  - Give a connection name, for instance: 'Local (avocado_ce)'
  - Specify username: avocado_ce
  - Leave all others fields as they are (we want localhost access)
  - Test Connection (and specify the password above) > Close
- In the left window side, Right click with mouse > Create schema... (use character set _utf8_) OR simply run the following command in a SQL sheet:
  ``` sql
  CREATE SCHEMA `avocado_ce` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
  ```

# Tomcat Servlet Container
Despite many website and people like and suggest an embedded Tomcat, it's always preferable to use a standalone Tomcat.
We have always to keep in mind that our application will be deployed in production, I believe that is a custom environment all the time.
Having a .jar does not actually help when we know since the beginning we will need a .war. So, why having different deployment approach from dev to prod?
Installation is easy:
- Install tomcat & copy MySql connector jar library in server lib folder
- Compile the project
- Deploy the war (preferable via IDE)

Advantages are evident since the development phase!! Especially if it's a web application.
If requested I can list them here but also Internet is full of questions and articles to compare why is better a standalone server.

- JNDI connection pool in Tomcat is configured in META-INF/context.xml
- Logs will be generated under server logs folder via Log4J2 configuration, while console is displayed in a sub window of the IDE (another advantage!).

# SSL
This must be done once for the server configuration.
For the purpose of this project we only create a _self-signed_ certificate.
For a real certificate you must buy it from a certification authority.

Full article here: https://tomcat.apache.org/tomcat-9.0-doc/ssl-howto.html

##### Part I
1. Execute `"%JAVA_HOME%/bin/keytool" -genkey -alias tomcat -keyalg RSA
   -keystore [/preferred/keystore/path]` (-keystore is optional, see point 4).
2. The default password used by Tomcat is `changeit`.
3. You must fill all the requested fields from the keytool.
4. If you didn't specify a keystore path, the file `".keystore"` will be generated under
   user home (in Windows `C:\Users\<user>\.keystore"`).
##### Part II
1. Edit the file `%CATALINA_BASE%/conf/server.xml`.
2. Identify the property `<Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol" .../>`
3. Uncomment it and modify the full element as following, then restart Tomcat:
``` xml
<Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
    	   maxThreads="150" scheme="https" secure="true" SSLEnabled="true"
    	   keystoreFile="path/to/your/keystore" keystorePass="YourKeystorePassword"
    	   clientAuth="false" keyAlias="tomcat" sslProtocol="TLS">
</Connector>
```

# IDE
- Using _IntelliJ IDEA™_: File > New > Project from Version Control > GitHub.
- Associate DDL data source to the IDE, so all SQL scripts can reference the tables and fields names in real time.
- Configure the Tomcat Server in _Run/Debug Configurations_ so that you can start and stop Tomcat from the IDE.
  - You associate the generated exploded folder (use `avocado-spring-boot/build/libs/exploded`) from .war file as deployment Artifact.
  - In the deployment tab, use Application context "/avocado".
  - Use Run Gradle task (skip tests with: -x test) so local configuration can be copied into the spring properties.
  
# A note on Spring Security _vs_ JwtInterceptor
The project has both profiles, currently _Spring Security_ is commented out. To activate it, uncomment relevant parts
and comment the class exclusion in `Application.java` as well as the `JwtInterceptor` invocation.<br>
Pojo classes already extend from _Spring Security_ interfaces.

At the moment the complexity and the over head of _Spring Security_ doesn't convince me
and I suggest to adopt a good custom `JwtInterceptor` and few checks in the `LoginUseCase`.
