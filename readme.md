## Examples

There are examples on how to use the AgileKip Platform to generate and handle different times of Process Aware Information Systems based on the Travel Plan model.

| Example                                                        | Description |
| -------------------------------------------------------------- | ----------- |
| Simple Travel Plan                                             | --          |
| Handling XOR Gateway                                           | --          |
| Handling AND Gateway                                           | --          |
| Handling OR (Inclusive) Gateway                                | --          |
| Handling Service Task                                          | --          |
| Using a Timer                                                  | --          |
| Handling Events                                                | --          |
| Working with Sub-processes                                     | --          |
| Using Markers                                                  | --          |
| Using timer to Cancel a process                                | --          |
| Calling other processes (FAILING TO DECOUPLE PROCESSES)        | --          |
| Handling Loops                                                 | --          |
| Multiple Entities Part I                                       | --          |
| Multiple Entities Part II - Updating Entities programmatically | --          |
| Multiple Entities Part III - Tweaking the UX                   | --          |
| Using Pools & Messages (FAILING TO DEPLOY)                     | --          |
| Adding Validations to Forms (FAILING)                          | --          |
| Adding Validations to Forms Programmatically (WIP)             | --          |
| Canceling Processes Nicely                                     | --          |
| Sending messages programmatically (WIP)                        | --          |
| Making Decisions with DMN (WIP)                                | --          |
| Putting it all together (A complete PAIS) (WIP)                | --          |

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
