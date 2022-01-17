## Examples

These are examples on how to use the AgileKip Platform to generate and handle different types of Process Aware Information Systems based on the Travel Plan model.

| Example                                                                 | Description |
| ----------------------------------------------------------------------- | ----------- |
| Simple Travel Plan                                                      | --          |
| Handling XOR Gateway                                                    | --          |
| Handling AND Gateway                                                    | --          |
| Handling OR (Inclusive) Gateway                                         | --          |
| Handling Service Task                                                   | --          |
| Using a Timer                                                           | --          |
| Handling Events                                                         | --          |
| Working with Sub-processes                                              | --          |
| Using Markers                                                           | --          |
| Using timer to Cancel a process                                         | --          |
| Calling other processes **(FAILING TO DECOUPLE PROCESSES)**             | --          |
| Handling Loops                                                          | --          |
| Multiple Entities Part I                                                | --          |
| Multiple Entities Part II - Updating Entities programmatically          | --          |
| Multiple Entities Part III - Tweaking the UX                            | --          |
| Using Pools & Messages **(FAILING TO DEPLOY)**                          | --          |
| Adding Validations to Forms **(FAILING)**                               | --          |
| Adding Validations to Forms Programmatically (WIP)                      | --          |
| Canceling Processes Nicely **(FAILING - JOB EXECUTOR IS NOT STARTING)** | --          |
| Sending messages programmatically (WIP)                                 | --          |
| Making Decisions with DMN (WIP)                                         | --          |
| Connecting Front and Back for New Features (WIP)                        | --          |
| Putting it all together (A complete PAIS) (WIP)                         | --          |

<p>

## Running the Platform

| Description                  | Command                                                                                                                    |
| ---------------------------- | -------------------------------------------------------------------------------------------------------------------------- |
| **Start the Container**      | docker container run --name agilekip.v0.0.12 -v $PWD:/home/jhipster/app -d -t agilekip/generator-jhipster-agilekip:v0.0.12 |
| **Enter the Container**      | docker container exec -it agilekip.v0.0.12 bash                                                                            |
| **Generate the App**         | jhipster --blueprints agilekip --skip-jhipster-dependencies                                                                |
| **Model the BPMN**           | -----------                                                                                                                |
| **Model/Configure Entities** | -----------                                                                                                                |
| **Generate all Entities**    | jhipster entity TravelPlan --regenerate                                                                                    |
| **Build the System**         | ./mvnw                                                                                                                     |

## Hints

1. Test your Domain Model and check the relationships to verify if they work accordingly. You can do this by generating code for the domain entities.

1. Test your BPMN model using the Token Simulation application at **https://bpmn-io.github.io/bpmn-js-token-simulation/modeler.html?e=1**

1. Test your Process flow before adding any code. Use the DefaultEmailDelegate and DefaultLoggerDelegate to the Message and Service tasks respectively.

1. Sometimes it is useful do delete the whole target folder at YOUR_PROJECT/target. This will eliminate the complied code and reset the database (you'll have to re-deploy your processes).

1. Use MailDev http://maildev.github.io/maildev/ to test your MessageTasks that send emails. Just run **docker run -p 1080:80 -p 25:25 djfarrelly/maildev** and the email server should be running on http://localhost:1080.

<p>
