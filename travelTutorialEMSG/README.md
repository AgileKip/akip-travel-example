# TravelTutorial

An example on how to automate a simple process with Interrupting Message Events using the AgileKip Platform.

In this example it's tested a way to stop/interrupt the process using one event directly on a user task in this case the Book a hotel task. the event is the message boundary (the circle with the letter inside) 
to execute this event we gonna need to create a POST method to trigger the finish of the process.

![image](https://user-images.githubusercontent.com/74799845/222720648-b8618118-9d5d-4359-9ef9-be6155b15bbc.png)

To create this POST method we use Postman, but you can use any other same program to do this, so you determine the URL that is http://localhost:8080/engine-rest/message, than you just need to select the Body section > raw > JSON and write the body like in the image, just changing the number of the processInstance accordingly to your task. When you reach the task, Book a hotel you send this POST method, then refresh the page to see that the process is finished.

It's important to say that the "messageName" in the POST method must follow the Global message reference from your message boundary in the Camunda BPMN.

![image](https://user-images.githubusercontent.com/74799845/222729033-6b2e007c-891c-408c-b0c7-59ba17654879.png)

![Model](/MODELS/travel-EVENTMSG/travel_EVENTMSG.png)
Fig1. Process Model
