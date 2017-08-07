# IDE
- Using _IntelliJ IDEA™_: File > New > Project from Version Control > GitHub

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