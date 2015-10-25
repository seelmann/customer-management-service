Customer Service
================

A minimalistic RESTful service written in Spring Boot that gives access to a 
customer database, backed by a PostgreSQL database.


Build
-----

    mvn clean install

This runs the tests and  creates an executable JAR in `target` folder.


### Profiles

Additional profiles are available.


#### test-hsql

This profile is active by default. It runs the tests against an in-memory HSQL database.


#### test-postgresql

This profile is activated with property `-Dtest-postgresql` and automatically deactivates the test-hsql profile:

    mvn clean install -Dtest-postgresql

When active the tests run against a PostgreSQL database instead of HSQL. 
In `pre-integration-test` phase a Docker container with PostgreSQL is started 
and in `post-integration-test` phase the container is stopped and deleted. 
Docker needs to be installed to run this profile.


#### all-checks

This profile is activated with property `-Dall-checks`:

    mvn clean install -Dall-checks

This profile enables additional checks and lets the build fail on violations:

* JaCoCo test coverage
* FindBugs
* PMD
* Forbidden APIs


Deployment
----------

Continuous integration and deployment is done by Jenkins, see [seed.job](../jenkins/seed.job) for job configuration.

Job `customer-service-build` builds the project. Currently it polls Github because the Jenkins instance is not accessible from Github hence no webhook can be used.

Job `customer-service-deploy` build the project, builds a (local) Docker image, and (re-)starts the customer-service container. Afterwards the service is accessible at [http://customer-service.blue.s12n.de/customers](http://customer-service.blue.s12n.de/customers).


Usage
-----

See [customer-service-client](../customer-service-client).

