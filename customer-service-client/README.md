Customer Service Client
=======================

How to interact with the Customer Service?


Python CLI
----------

A small CLI to create new customers, requires Python 3.

Usage:

    python3 create-customer.py

Then follow the instructions. Values in square brackets are defaults and can be confirmed by pressing `Enter`.

Example:

    $ python3 create-customer.py
    
    Enter HTTP connection params:
    REST service URL [http://customer-service.blue.s12n.de/customers]: 
    User [user]: 
    Password [WePbS3wUqH]: 
    
    Enter customer data:
    First name [John]: 
    Last name [Smith]: 
    Email [john.smith@example.com]: 
    age [42]: 
    Private address street [Marienplatz 1]: 
    Private address street [München]: 
    Private address street [80331]: 
    Private address street [DE]: 
    Company address street [10 Downing Street]: 
    Company address street [London]: 
    Company address street [SW1A 2AA]: 
    Company address street [UK]: 
    
    Sending request:
    
    Response:
    201
    ('Server', 'nginx/1.9.5')
    ('Date', 'Sun, 25 Oct 2015 20:52:14 GMT')
    ('Content-Length', '0')
    ('Location', 'http://customer-service.blue.s12n.de/customers/6')
    ('Connection', 'close')


CURL
----

Get all:

    curl -v -X GET -u "user:WePbS3wUqH" http://customer-service.blue.s12n.de/customers

Get one:

    curl -v -X GET -u "user:WePbS3wUqH" http://customer-service.blue.s12n.de/customers/1 | json_pp

Create one:

    curl -v -X POST -u "user:WePbS3wUqH" -H "Content-Type: application/json" -d @customer.json http://customer-service.blue.s12n.de/customers

Delete one:

    curl -v -X DELETE -u "user:WePbS3wUqH" http://customer-service.blue.s12n.de/customers/7


