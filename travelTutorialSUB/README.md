# TravelTutorial

An example on how to automate a simple process with Embedded Sub-processes using the AgileKip Platform.

The purpose of this example is to demonstrate the use of a subprocess, which allows us to group elements of a process, when we start the process and arrive at the book a hotel task we can use a message boundary event to enter the subprocess to cancel the trip, if not use the message boundary event to cancel the trip the flow will follow normally until the end.

![Model](/MODELS/travel-SUB/travel_SUB.png)

To use the message boundary event it is necessary to use an api client like postman, Insomnia or whatever you are used to.

In the request url must be inserted http://localhost:8080/engine-rest/message and in the body of the message in json format insert the following information:
```json
{
     "messageName": "Insert your message name",
     "processInstance": "Insert your processInstanceId"
}
```

Fig1. Process Model
