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

This application was built to run on a tomcat server so you will need a tomcat installation and you will need to install the war after starting the tomcat by running the command "mvn tomcat7:deploy" in gitbash from the project directory.

--------------------------------------------

## Exposed Endpoints

### localhost:8080/crypto/register

POST: Registers a new user, expects Json in the following format:
<pre>
{
    "username":"entered_username",
    "password":"entered_password",
    "firstName":"entered_name",
    "lastName":"entered_name"
}
</pre>

DELETE: Deletes a currently logged in users account.

------------

### localhost:8080/crypto/login

POST: Logs a user into the application, expects Json in the following format:
<pre>
{
    "username":"entered_username",
    "password":"entered_password"
}
</pre>

DELETE: Logs out a logged in user

------------

### localhost:8080/crypto/view

GET: Retrieves a logged in users current wallet information.

------------

### localhost:8080/crypto/buy

POST: If user has enough USD then this endpoint will allow the User to purchase crypto-currencies at current market value. It expects a Json body that looks like the following where "id" is the currencyPair (i.e. "BTC-USD") for the currency you are purchasing.
<pre>
{
    "id": "CURR-PAIR",
    "amount": 100
}
</pre>

------------

### localhost:8080/crypto/sell

POST: If user has enough coin of selected currency this endpoint will allow the User to sell their coin at current market value. It expects a Json body that looks like the following where "id" is the currencyPair (i.e. "BTC-USD") for the currency you are purchasing.
<pre>
{
    "id": "CURR-PAIR",
    "amount": 100
}
</pre>
