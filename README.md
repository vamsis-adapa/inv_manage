# inv_manage

An Inventory management back end microservice.   
It has several features:
- creation and Management of three different types of Users
- Sign in and Sign up functionality
- Register Items into the system
- put up registered Items for sale ( at any price, and any quantity)
- view items listed for sale
- view differences in prices for the same item
- purchase items
- view purchase transactions
- view sell transactions
- rate items ( requires the use of an external api)
- pay for items( requires the use of an external api)  


all the above functionality is split between different users.


# Architecture

The Application was built using Spring Boot.  
However, It uses many other applications/services

## Databases 
The application uses two database providers  

For storing all the information related to users ( name, passwordhash, address, email, details, phn number etc) we use relational postgres databases  

For storing details of orders, and transactions we use mongoDB

## Cacheing
All entities ( user, and items, and transactions, auth tokens) are cached via redis.

## Inter Service Communication
This application was initially built to work together with two other microservices, a product rating service and a payment gateway service.     

The application sends data over kafka regarding item rating details, so the product rating service can pick up the data and add it to the system;

The application sends a HTTP request to the payment gateway to authorize the payment. 

Note: both the aforementioned services are in private repos, so i can't share them here.

# Installation and running
The project was made using spring boot and uses maven
you can install the project by using        
``` ./mvnw clean install ```           
However if you try to install the project without setting up all the required external 

## Run 
To Run the project use :    
```./mvnw spring-boot:run ```

Note: there are multiple ways to install and run the application. the methods given here are just one way. 
Note: to run the application yo uwil have to configure and run all the required databases ( mongo and postgres), cache services( redis), and messaging platforms ( kafka) to get it running. 


# Details of the project
Was made in order to learn about the tech stack used by a company during an internship

Here are some details on the functionality of the project

## Users
There are three types of users:

### Admin
These users have administrative rights in the system.
They  can delete accounts

### Vendor
- These users can register items with the system, and put up items for sale
- they can view all the items that are registerd in the system
- they can view all the items that they've put up for sale, and modify them( add or decrease stock, alter price) 
- view all the transactions linked to thier account

#### Transaction: 
Any thing that affects the items that are put up for sale       
all these count as transactions:
- putting a new item up
- removing an item
- increasing or decreasing stock of an item
- changing the value of an item
- a buyer buying an item


### Buyers
- These users can view all the items put up for sale
- They can buy any of the listed stuff.
- they can view all the orders linked to thier account



All the data that can be viewd in list form ( transactions, items, etc) can be sorted and paginated.

## General user functionality
all users can sign up and sign in. There are separate endpoints for each of them. 

All users can change details regarding thier account ( name, email etc)


# Improvements
This project is far from perfect, and there are many things that I wouldve done differently the second time around.

## Implementation of the Users
Right now each type of user has a separate Object that are in no way related to each other.     
this led to creatign three separate sign up and sign in functionality even though that part is exactly identical for all three of the users.    
This can be used by the proper use of inheritance , and maybe a pattern such as factory pattern.

## Tests
There are no unit tests for this application as of now

## Integration with other applications
The integration with the other services was a bit forced, and not as natural as it couldve been.    
for example in the interaction with the rating service, a better system would have been to push every new item over a kafka, and the rating service handles the user ratings.

## Return values of the controller
Most of the controllers return a string  and a status code. which works. But returning a relevant object would be better. It would reduce the number of call taken to do something trivial like sign in and view your info. ( i.e sign in -> two requests: sign in api, get user data api; instead we can return user data with sign in, and reduce a call)     
Or, creating a generic response wrapper, and attach all the responses to it would also be agreat approach.

## Handling an error
The error handling in this program is a mess.  
This can be attributed to mainly me not deciding at what level to handle errors.    
A more unified error handling approach would definitely make the code more readable and easier to find bugs. 





