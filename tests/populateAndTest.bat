curl -X GET localhost:8081/Consumers 
 
curl -X POST localhost:8081/Consumers -H Content-type:application/json -d @customer1.json

curl -X POST localhost:8081/Consumers -H Content-type:application/json -d @customer2.json

curl -X GET localhost:8081/Consumers 


curl  -X DELETE localhost:8081/Consumers/3
curl  -X DELETE localhost:8081/Consumers/3
curl -X GET localhost:8081/Consumers 

curl  -X DELETE localhost:8081/Consumers/2
curl -X GET localhost:8081/Consumers 

curl -X POST localhost:8081/Consumers/1/AddBillingAddress -H Content-type:application/json -d @address1.json

curl -X POST localhost:8081/Consumers/1/AddShippingAddress -H Content-type:application/json -d @address2.json


curl -X GET localhost:8081/Consumers/1/BillingAddresses

curl -X GET localhost:8081/Consumers/1/ShippingAddresses


curl -X GET localhost:8081/Addresses/1

curl -X GET localhost:8081/Addresses/3



curl  -X DELETE localhost:8081/Consumers/1
curl -X GET localhost:8081/Consumers 
