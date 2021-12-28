# CryptoWallet_p1
Built by Andrew Aslakson and Cole Paris.

Servlet based project created in revature batch 211101 java-react-enterprise curriculum.

This project was built to showcase a custom ORM implementation with custom annotations and
object level mapping into our database.

Github Link: https://github.com/211101-java-react-enterprise/CryptoORM_p1

--------------------------------------------

### Necessary Setup

Before running this application it is necessary to first download the CryptoORM_p1 project and install it using maven.

You must also add a file called "db.properties" to "/src/main/resources/" with the following information about the database to be used:
<pre>
url={URL to database here}
username={username}
password={password}
</pre>

Included in the "/src/main/resources/" is a file called "crypto_wallet_table_instantiation.sql" which is a script that can be run on a database schema to set up the database tables and create some testing data for the application.

This application was built to run on a tomcat server so you will need a tomcat installation and you will need to install the war after starting the tomcat by running the command "mvn tomcat7:deploy"

--------------------------------------------

## Exposed Endpoints

### localhost:8080/crypto/register

POST: Registers a new user, expects Json in the following format:
<pre>
{
    "username":"",
    "password":"",
    "firstName":"",
    "lastName":""
}
</pre>

DELETE: Deletes a currently logged in users account.

------------

### localhost:8080/crypto/login

POST: Logs a user into the application, expects Json in the following format:
<pre>
{
    "username":"",
    "password":""
}
</pre>

DELETE: Logs out a logged in user

### localhost:8080/crypto/view

GET:
