Prerequisites

The Gateway has dependencies a few other applications. These applications will, in general, only need to be installed once. In some cases it will be necessary to update these applications due to security fixes or updated Gateway requirements.
Before Getting Started

NOTE

These directions are for installing the prerequisite software required by the Gateway on a Unix or Linux environment.
Familiarity with Unix or Linux system administration skills are required.
Root access or sudo permissions are most likely required.

 
Postrgres Database

The Gateway uses a Postgres database to store most of the data it needs to operate. Please refer to the Postgres website for documentation, downloading, and installation instructions.

In summary, the installation procedure follows these steps:

    Download: download the appropriate Postgres binary for your operating system. The Gateway requires Postgres version 9.4.
    Installation: follow the installation instructions detailed on the Postgres website.
    Additional modules: the "table_func" and "uuid-ossp" modules are required.
    Start/Stop: make sure you can start and stop the database, and connect to it. To connect to the database, you can use the standard Postgres client tool psql, which gets installed on your system as part of the standard installation procedure, or a more sophisticated GUI client, like pgadmin.

    Create Gateway Database Roles/Users: there are three database roles/users (admin_role, user_role, and read_only_role) that need to be created for the Gateway. The three roles/users can be named however you like, but please remember the roles and their respective passwords. You can use the following commands to create the roles/users.
    CREATE USER <admin_role> WITH ENCRYPTED PASSWORD '<password>';
    CREATE USER <user_role> WITH ENCRYPTED PASSWORD '<password>';
    CREATE USER <read_only_role> WITH ENCRYPTED PASSWORD '<password>';

    Create the Gateway Database: Using a postgres superuser, use the following command to create the new database that the Gateway will use. The admin_role from above will be set as the owner of the new database.
    createdb -U <postgres_superuser> -O <admin_role> <gateway_database>

    Add UUID Generation Functions:  The Gateway requires the use of the Postgres uuid-ossp extension to create UUIDs in the database.
    CREATE EXTENSION "uuid-ossp";

    Revoke public use on the public schema: Most likely, there is public access on the default public schema in the newly created database. It is reasonable to remove this default public access. Run the following command as either a postgres superuser or the <admin_role> defined above.
    REVOKE ALL ON SCHEMA public FROM public;

TODO:  Add these comments somehow...

Must use root user to move a specific database language script over.

esg_admin must be owner of the database.

Must add the following for uuids to work properly:
CREATE EXTENSION "uuid-ossp";

TODO:  This comment cannot occur here, because until the database schema is created (which will happen when the gateway is first started), this commend will not work.
TODO:  We should also add sql for updating the rootAdmin password since the password is most likely 'publicly well known' at this point.
Update temporary datacenter or add a new datacenter:
insert into metadata.data_center

(id, short_name, version, long_name, url, first_name, last_name, address1, address2, city, state, postal_code, country)

values

('e95b0bb0-a5e8-11e2-9e96-0800200c9a66', 'ESG', 1, 'Earth System Grid', 'http://www.earthsystemgrid.org/&#39;, 'ESG', 'User Services', 'NCAR/CISL', 'P.O. Box 3000', 'Boulder', 'CO', '80307', 'USA') 
 
Postgres Database Backup

It is strongly recommended that appropriate backup procedures are in place in case of any unforeseen circumstances.

Please consult the Postgres documentation on how to best configure your system for periodic backup.

 
Java

The Gateway is a Java web application and requires a Java Development Kit (JDK) to be installed on your system. If not installed on your system, download and install a JDK from the Java SE Downloads - Sun Developer Network (SDN) website. The Gateway requires a version of the JDK 1.8.

We will refer to the Java installation directory as <JAVA_HOME>.

 
Tomcat

NOTE

It is recommended that Tomcat 7 is used. The Gateway has not been fully tested on other versions of Tomcat.

The Gateway is a web application that runs within a Tomcat servlet container. Please refer to the Tomcat website for documentation, download and installation instructions.

Note that even if you already have Tomcat in use for other web applications, you may want to consider setting up a dedicated Tomcat server just to run the Gateway. The Gateway requires some setup of shared XML libraries and SSL certificates configuration that may affect other applications running within the same servlet container. If you have split the Tomcat installation into the two separate directories CATALINA_HOME and CATALINA_BASE (as recommended), all web application can share the same CATALINA_HOME, and you need only create a dedicated CATALINA_BASE to run the Gateway with a specific setup.

In summary, the installation procedure follows these steps:

    Download: download the appropriate Tomcat binary for your operating system. The Gateway requires a version of Tomcat 7.
    Installation: follow the Tomcat installation instructions. When the installation is completed, the core Tomcat software will be installed in a directory called <CATALINA_HOME>, while the web application will run from a directory called <CATALINA_BASE>. If you haven't taken any special steps to separate the two directories, <CATALINA_HOME> and <CATALINA_BASE> will reference the same location.
    Start/Stop: start the Tomcat server and make sure you can access the default web application by pointing a browser to http://<HOST_NAME>:8080/, where <HOST_NAME> is your server's internet domain name (8080 is the Tomcat standard port for non-SSL connections). Also make sure that you can stop the server.
    SSL Configuration: specific SSL configuration instructions are explained in the Gateway Installation/Upgrade instructions.

 
Running Tomcat on ports 80, 443 (optional)

Many people access the web from behind institutional or corporate firewalls, which often do not allow http communication on any ports but the standard http ports (80 and 443).

Consequently, in order to avoid firewall problems, consideration must be given to configure Tomcat to listen on other two ports (80 and 443), which are typically enabled in all firewalls, since they are the standard ports used by web servers throughout the internet. Unfortunately, because of security restrictions on most operating systems, any process that uses ports below 1000 must be started by the root user, so you can't just change the port configuration in server.xml and expect to start Tomcat as a non-root user.

Our recommendation is to use the Apache HTTP Web Server. An Apache web server can be installed on the server machine, which listens to ports 80, 443 and is configured to forward HTTP requests to the Tomcat servlet container. The Apache distribution contains all functionality to start the server as root and run it as a non-privileged user. Please refer to the Apache Web Server and Tomcat documentation for detailed instructions.

TODO: Also explain Nginx and/or display our apache configuration settings.


Gateway Installation Instructions (version 2.0.X)

NOTE

These directions are for installing the Gateway software on a Unix or Linux environment using Tomcat 7.
Simple Unix or Linux system administration skills are required.
Familiarity with deploying applications with Tomcat 7 is required.
Root access or sudo permissions are possibly required.

There are several directories that will be referenced throughout these instructions.

<INSTALLATION_PACKAGE_BASE>
	

Directory where the Gateway installation package and ESG Federation Trust Roots Java Truststore are initially downloaded.

<JAVA_HOME>
	

Root directory of your Java installation. Tomcat requires this value point to a JDK.

<CATALINA_HOME>
	

Root directory of your Tomcat installation.

<CATALINA_BASE>
	

Root directory of your webapp instance.

<CONTEXT_PATH>
	

The context path that the Gateway instance will run under.

<GATEWAY_HOME>
	

Root directory of the Gateway's home directory. Data that needs to persist between upgrades of different Gateway versions will reside in this directory.

NOTE

Please read/review the Prerequisites documentation prior to installing your Gateway installation.


Download Installation Package

Download the gateway-2.0.X.war version of the Gateway Installation Package from the following link into your <INSTALLATION_PACKAGE_BASE> directory.

https://vets.development.ucar.edu/nexus/content/repositories/releases/sgf/gateway/


After the download is complete, verify the completeness of the download using the md5sum command.
$ md5sum gateway-2.0.X.war

The resulting md5 hash should match what is on the nexus site.


TO DO:  Remove this section?
Download ESG Federation Trust Roots Java Truststore

The ESG Federation Trust Roots Truststore contains all the Publicly Known Identities (PKI) for the different ESG Federation MyProxy Certificate Authority instances throughout the ESG Federation. This specific Java Truststore is required for federation to work properly between Gateway instances.

http://esgf.org/esg-certs/index.html

You will want to download the truststore file (esg-gateway.ts) that is found under the 'PKI Trust Roots' heading.


After the download is complete, verify the completeness of the download using the md5sum command.
$ md5sum esg-truststore.ts

The resulting md5 hash should match what is on the esgf.org site.


TODO:  Explain how to create the home directory and what files need to exist there...

TODO:  Remove this section and replace with the future text above.
Copy gateway-home Directory to Permanent Location

The contents of the gateway-home directory are used to hold non-database persistent data that needs to survive between Gateway upgrades.

The final location of the contents of the gateway-home directory should be placed in a user agnostic location, i.e., not in a specific user's home directory.

The final location of the contents of the gateway-home will now be referenced as <GATEWAY_HOME>.
$ cp -R /2.0.0-xx/gateway-home/* <GATEWAY_HOME>

Change ownership permissions on <GATEWAY_HOME> to the tomcat user.
$ chown -R tomcat:nobody <GATEWAY_HOME>


Edit Gateway Properties

Edit the two properties files (gateway.properties and messages.properties) found in the <GATEWAY_HOME>/conf directory. All the values unless specifically noted are required. If a property is preset, e.g., gateway.name, please leave the default value.

The files are self-explanatory on what specific values need to be.


Copy gateway.war

Move the /2.0.0-xx/gateway.war file directly into the <CATALINA_BASE>/webapps/ directory.
$ cd <INSTALLATION_PACKAGE_BASE>/2.0.0-xx/
 
$ cp gateway.war /<CATALINA_BASE>/webapps/<CONTEXT_PATH>.war

Change ownership permissions on the newly copied file to tomcat.
$ cd <CATALINA_BASE>/webapps/
 
$ chown -R tomcat:nobody <CONTEXT_PATH>.war


TODO:  Explain the certificates better and explain why we need them.  Perhaps this section is optional?
Move SSL Java Keystore

WARNING

Proper management of Keystores and Truststores is crucially important.

Although not required, it is recommended that you keep your Gateway's Java Keystore (used for SSL connections) in the <GATEWAY_HOME>/certs directory.

If you do not have a Java Keystore specific for SSL connections you will need to contact a well-known Certificate Authority (CA) to purchase a CA Signed Certificate to import to a Java Keystore.

For testing purposes only, a self-signed certificate can be imported into a Java Keystore and used instead.

Change ownership permissions on the Java Keystore to tomcat.
$ cd <GATEWAY_HOME>/certs/
 
$ chown -R tomcat:nobody <YOUR JAVA KEYSTORE>


Copy Downloaded ESG Federation Trust Roots Java Truststore

WARNING

Proper management of Keystores and Truststores is crucially important.

Copy the ESG Federation Trust Roots Java Truststore to
$ cp <INSTALLATION_PACKAGE_BASE>/esg-truststore.ts <GATEWAY_HOME>/certs


Change ownership permissions on the Java Truststore to tomcat.
$ cd <GATEWAY_HOME>/certs/
 
$ chown -R tomcat:nobody esg-truststore.ts


TODO:  Remove this section?  If we are using apache as a proxy, why does the gateway need to server its own certificate?
Modify server.xml

For SSL communication to work, the <CATALINA_BASE>/conf/server.xml file needs to be updated to enable the SSL Connector.

Inside the Tomcat server.xml file look for the following text:
<!-- Define a SSL HTTP/1.1 Connector on port 8443
     This connector uses the JSSE configuration, when using APR, the
     connector should be using the OpenSSL style configuration
     described in the APR documentation -->
<!--
<Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
           maxThreads="150" scheme="https" secure="true"
           clientAuth="false" sslProtocol="TLS" />
-->

Uncomment the SSL connector and add/update the following attributes:
port=443
keystoreFile="<GATEWAY_HOME>/certs/<YOUR SSL KEYSTORE FILE>"
keystorePass="<YOUR SSL KEYSTORE PASSWORD>"
clientAuth="want"

Example:
<!-- Define a SSL HTTP/1.1 Connector on port 8443
     This connector uses the JSSE configuration, when using APR, the
     connector should be using the OpenSSL style configuration
     described in the APR documentation -->
<Connector port="443" protocol="HTTP/1.1" SSLEnabled="true"
           maxThreads="150" scheme="https" secure="true"
           keystoreFile="/usr/local/certificates/star_prototype_ucar_edu.jks"
           keypass="********"
           clientAuth="want" sslProtocol="TLS" />


Modify Start Up Script

There are a few Java VM system property arguments that need to be set on the tomcat startup script for the Gateway.

NOTE

The arguments below are each on a separate line for readability. When adding these arguments to the startup script, they must all be on one line with one space between each argument.
-Djavax.net.ssl.trustStore=<GATEWAY_HOME>/certs/esg-truststore.ts
-Djavax.net.ssl.trustStorePassword=<truststore password>
-Dgateway.home=<GATEWAY_HOME>


Start Server

At this time the configuration of the Gateway instance is complete and should be ready to run.

Attempt to start the Gateway instance using the startup script.

Watch the logs located in <CATALINA_BASE>/logs directory for any errors.
