Overview
========
The below Belong Phone API is developed using the latest spring-boot v3.4.3. It requires min Java 17 LTS to compile and run. It listens on port 8080 and its context path is defined as /phones & /customers.\ 
When it starts up, the seeding records will be automatically pre-populated for showcasing purpose. UUID is used to uniquely identify customer and phone entries, as UUID is random and hard to guess.\ 
You may find the customer and phone entries from the seeded data files.\ 
For security reason, these endpoints are secured. To access them, you are required to provide X-API-KEY in request header. The secret API-KEY can be found in application.yml.

Phone controller
================
a. Retrieve all phones\
curl --location 'http://localhost:8080/phones' \--header 'x-api-key: hRGkLDWkhSe0aie6q2pKnhd7cx8gvNoY1mfuAW5eYR5llFEqAzq243f6cLlAfvXRzVY7swFVv80jl2qSLxOEIzH0V3NoM75QEWq0UaI3tJmui4KCcriOE5nNMmVqUBFt'

b. Activate phone status
curl --location --request PUT 'http://localhost:8080/phones/9fbd0511-b8fd-4e23-84ba-5ebdca3deb01?status=Activated' \--header 'x-api-key: hRGkLDWkhSe0aie6q2pKnhd7cx8gvNoY1mfuAW5eYR5llFEqAzq243f6cLlAfvXRzVY7swFVv80jl2qSLxOEIzH0V3NoM75QEWq0UaI3tJmui4KCcriOE5nNMmVqUBFt'     

Customer controller
===================
a. Retrieve customer's phones\
curl --location 'http://localhost:8080/customers/e3f5d7ca-fa67-437e-ac22-5c76df846ba5/phones' \--header 'x-api-key: hRGkLDWkhSe0aie6q2pKnhd7cx8gvNoY1mfuAW5eYR5llFEqAzq243f6cLlAfvXRzVY7swFVv80jl2qSLxOEIzH0V3NoM75QEWq0UaI3tJmui4KCcriOE5nNMmVqUBFt'


OpenAPI/SwaggerAPI Doc
=======================
a.  Below openAPI/SwaggerAPI doc tells you what endpoints are available, how to call these endpoints, the expected input and output. To test these endpoints, make sure you provide X-API-KEY.\
    http://localhost:8080/swagger-ui/index.html


Set up the project
==================
1. mvn clean install
2. java -jar target/phone-api-1.0-SNAPSHOT.jar


Further development/enhancement 
===============================
1. containerise/dockerise this web application
2. Improve the API documentation.
