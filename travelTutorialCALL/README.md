# TravelTutorial

An example on how to automate a simple process with a CALL Activity using the AgileKip Platform.

In this example, a parallel activity is called to cancel the travel plan, for this it is necessary to complete the task of buying airline tickets and arrive at the task of booking a hotel. There is a two minute timer that the user must complete within the established time, otherwise the flow will go to a parallel activity with the name call cancel process.

![Model](/MODELS/travel-CALL/travel_CALL.png)

Within this subprocess it is possible to see a user task to cancel the confirmation, if it is completed, the running process is completed.

![InnerProcessModel](/MODELS/travel-CALL/InnerProcess.png)

If the task of booking a hotel is completed before the two minutes, the process continues to the gateway to decide whether to rent a car or finish the process.

Fig1. Process Model
