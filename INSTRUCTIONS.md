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
It's always preferable to use a standalone Tomcat with custom configuration, so we can upgrade, maintain, advanced customize and keep installed the instance on the server.<br>
We will deploy and transfer to the server only the .war file. Also we can easily associate the IDE with the server local instance.

- Configure JNDI connection pool in Tomcat (Note: this is already done in META-INF/context.xml)
- Logs will be generated under project folder via Log4J2 configuration.

# IDE
- Using _IntelliJ IDEA™_: File > New > Project from Version Control > GitHub.
- Associate DDL data source to the IDE, so all SQL scripts can reference the tables and fields names in real time.
- Configure the Tomcat Server in _Run/Debug Configurations_ so that you can start and stop Tomcat from the IDE.
  - You associate the generated exploded folder (use `avocado-spring-boot/build/libs/exploded`) from .war file as deployment Artifact.
  - In the deployment tab, use Application context "/avocado".
  - Use Run Gradle task (skip tests with: -x test) so local configuration can be copied into the spring properties.