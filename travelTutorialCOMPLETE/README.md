# TravelTutorial

An example on how to automate a process with several different modelling elements using the AgileKip Platform.

In this example we are going to use different types of subprocesses to make a travel plan, the first thing we must do is register the airline, car, hotel information in the entities tab, after that we can start the process. In the process after starting, we will enter an expanded subprocess where we will choose a flight, hotel and car, after finishing it, the process will stop in a user task where depending on the choice of checkout, it will return to the beginning or continue to finish the process.

In this process there are also other ways to finish it without the need to follow the standard flow.

The first is through the timer where if you wait the defined amount of time the timeout subprocess will be executed and the process will be finish, and the second is through an event message that is triggered where a cancellation subprocess is executed.

![Model](/MODELS/travel-COMPLETE/travel_COMPLETE.png)

To use an event message it is necessary to use an api client like postman, Insomnia or whatever you are used to.

In the request url must be inserted http://localhost:8080/engine-rest/message and in the body of the message in json format insert the following information:
```json
{
     "messageName": "Insert your message name",
     "processInstance": "Insert your processInstanceId"
}
```

Fig1. Process Model
