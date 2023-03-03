# TravelTutorial

An example on how to automate a simple process with OR gateways using the AgileKip Platform.

This example we use the OR example, this allow us to increase the number of possibilities to follow a flow, as we can see in the BPMN we can only Rent a Car and finish, Book a hotel and finish, Rent a car and Book a hotel and finish or just finish after Buy flight tickets, this depends of the conditions.
For the example if you only want to Rent a car, when you initiate the start form the Name input must contain byCar in some part to trigger only the Rent a car task, in the other hand if you only want the Book a hotel the Name in the start form must contain withHotel. To trigger both of the tasks just inform in the name both of the conditions (byCar and withHotel), but if you don't want any of this user tasks, in the Name just don't write any of the conditions.

![Model](/MODELS/travel-OR/travel_OR.png)
Fig1. Process Model
