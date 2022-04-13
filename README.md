# SQL Server + JDBC Sample

Author: Michał Możdżonek

## Requirements

- Java 8
- Maven
- working MS SQL Server instance


## Connection string

In order to connect to the DB instance you will need `connection string`. Usually it looks like this:

```java
"jdbc:sqlserver://192.168.137.180\\WIN-7KGAL3F2ICE:1433;databaseName=master;user=sa;password=my_strong_password"
```
Some explanation:

- `192.168.137.180` - IP address of machine with database instance, could be `localhost`
- `WIN-7KGAL3F2ICE` - (*) name of an instance if you are connecting to named instance
- `1433` - port used for the connection, sometimes it might be necessary to open this port on firewall
- `databaseName=master` - initial database
- `user=sa;password=my_strong_password` - credentials. It is **terrible** practise to keep this in repository as plain text, but this is only example ;) 


## Available examples

### Simple localhost

Source: https://github.com/microsoft/sql-server-samples

Simple Java application with connection to named instance on localhost. 
Shows how to use Statement and PreparedStatement classes to query database \ execute CRUD operations.

### Simple remote

The same as above, but connection string points to remote host.

### SQL injection

Funny story with definitely not funny ending.
