# TravelTutorial

An example on how to automate a simple process and add Markers to Tasks using the AgileKip Platform.

In this example we use the Camunda Markers as you can see in the Rent a car that this three lines vertically indicates that the instance will be executed in parallel, and the three lines horizontally as you can see in the Say Cancel indicates that the instance will be executed in sequence.

To test the service task Say cancel you need to send a POST method using Postman or any other similar program, when you reach the user task Book a hotel, you must fill the request url like this http://localhost:8080/engine-rest/message
and in the body of the message in json format insert the following information:
```json
{
"messageName": "example",
"processInstance": "ProcessInstanceId"
}
```

![image](https://user-images.githubusercontent.com/74799845/222742446-cfdacbd8-0c7a-4413-843e-023fdea61501.png)


![Model](/MODELS/travel-MARKER/travel_MARKER.png)
Fig1. Process Model
