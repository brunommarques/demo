Example of spring REST service with H2 Database. Intellij IDEA project (used EAP version).

Spring Boot project using JPA/Hibernate , and H2, just for demo.

Database has 3 tables, one for Addresses, one for Customers and one relation table. This relation table also specifies
what kind of address is this, for this customer

Mapping is done with hibernate ORM.

The Server runs on port 8081, and should start serving requests as long as we open the project and hit run inside IDEA
IDE.

This is a simplistic approach - no fiiltering of duplicate addresses, etc, for example.

Some tests that use the curl utility are provided in tests directory, involving: Listing customers, creating new
customers, deleting, adding shipping/billing addresses, getting addresses for customers, etc.

cd tests
populateAndTest.bat

examples:
listing customers
curl -X GET localhost:8081/Consumers

creating 2 new customers

curl -X POST localhost:8081/Consumers -H Content-type:application/json -d @customer1.json

curl -X POST localhost:8081/Consumers -H Content-type:application/json -d @customer2.json

add billing address:
curl -X POST localhost:8081/Consumers/1/AddBillingAddress -H Content-type:application/json -d @address1.json




