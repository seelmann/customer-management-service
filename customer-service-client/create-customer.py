#!/usr/bin/env python
# -*- coding: utf-8 -*-

import base64
import json
import urllib.request

def __input(text, default):
    return input('%s [%s]: ' % (text, default)) or default

print()
print('Enter HTTP connection params:')
url      = __input('REST service URL', 'http://customer-service.blue.s12n.de/customers')
user     = __input('User', 'user')
password = __input('Password', 'WePbS3wUqH')
print()
print('Enter customer data:')
data = {
    'firstname': __input('First name', 'John'),
    'lastname': __input('Last name', 'Smith'),
    'emailAddress': __input('Email', 'john.smith@example.com'),
    'age': __input('age', '42'),
    'privateAddress': {
        'street': __input('Private address street', 'Marienplatz 1'),
        'city': __input('Private address street', 'München'),
        'zipCode': __input('Private address street', '80331'),
        'country': __input('Private address street', 'DE')
    },
    'companyAddress': {
        'street': __input('Company address street', '10 Downing Street'),
        'city': __input('Company address street', 'London'),
        'zipCode': __input('Company address street', 'SW1A 2AA'),
        'country': __input('Company address street', 'UK')
    }
}

print()
print('Sending request:')
json = json.dumps(data).encode('utf8')
encoded_user_password = base64.b64encode(('%s:%s' % (user, password)).encode('utf8')).decode('utf8')
request = urllib.request.Request(
    url,
    data=json,
    headers = {
        'Content-Type': 'application/json',
        'Authorization': 'Basic %s' % encoded_user_password
    }
)
response = urllib.request.urlopen(request)

print()
print('Response:')
print(response.status)
for header in response.getheaders():
    print(header)
print(response.read().decode('utf8'))

