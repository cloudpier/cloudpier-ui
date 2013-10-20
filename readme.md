Cloud Pier Frontend modules
==========================

This Repository includes all modules that compose Cloud Pier Web UI

# Current modules:
## frontend-commons
Include common client and server helper classes and services
it should include any code reuse by dashboard and widgets
## frontend-dashboard
Generates the Cloud Pier frontend dashboard, that is, the Cloud Pier Web user interface 
## frontend-widget-user-management
Generates user management widget and related views
## frontend-widget-search
Generates search for PaaS offerings widget and related views
## frontend-login
Generates user login widget and related views
## frontend-widget-user-management
Generates user management widget and related views
## frontend-theme
Generates Cloud Pier theme related code
## frontend-widget-monitoring
Generates monitoring widget and related views
## frontend-widget-sla-management
Generates SLA setup and viewing widgets and related views

# Usage

## Dependencies
Cloud Pier UI requires Cloud Pier Core. See the wiki for more details and technical instructions

## Deployment
Compile, debug and run individual widgets and the dashboard
Place your command line in a concrete widget or dashboard root directory.
Ensure frontend-commons is up to date and installed with mvn install
Compile:

    mvn clean install

When producing the war file for deploying in a servlet container you won't include the MySQL JDBC Connector in the classpath.
Instead, you will manually add the MySQL JDBC Connector in the servlet container.

### Debugging from Eclipse:
In Project Explorer select the widget module, righ-click, select RunAs/Maven build ...
In goals type gwt:debug, give a name, apply changes and run
Eclipse runs a debugging session. To connect to it, create a debug configuration for same
module, as Remote Java Application debug project, and select port 8008 (as configured in pom.xml) to connect to.
Accept changes and debug. Set code breakpoints and from emerging GWT Development Mode window launch the application in default browser.

# Standalone and Full mode
------------------------

Cloud Pier can be started in two modes: standalone and full.
The 'standalone' mode allows users to use only the matchmaking functionality hiding deployment, monitoring, migration
and SLA enforcement.
Furthermore, the 'standalone' mode provide a slightly modified welcome page that avoid referring to the functions not
provided in this mode.

The mode can be decided at startup by mean of a system property (environment variable) 'c4s.mode'. When set to 'standalone'
this property makes the Cloud Pier application to work in standalone mode. When unset, set to 'full' (or any other value)
Cloud Pier works full featured.

When deploying the war file in tomcat, the property can be set using the CATALINA_OPTS variable:

    $export CATALINA_OPTS=-Dc4s.mode=standalone
    $catalina.sh start

In Debian style linux distribution, the variable can be set in /etc/default/tomcat7 file

    JAVA_OPTS="-Djava.awt.headless=true -XX:MaxPermSize=200m -mx512m -XX:+UseConcMarkSweepGC -Dc4s.mode=standalone"



