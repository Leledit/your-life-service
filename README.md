<h1 align="center">Your-life-service</h1>

![Linguagem mais usada](https://img.shields.io/github/languages/top/Leledit/your-life-service)
![Numero de lingaugens usadas](https://img.shields.io/github/languages/count/Leledit/your-life-service)
![Lincense](https://img.shields.io/github/license/Leledit/your-life-service)
![Tamanho do projeto](https://img.shields.io/github/languages/code-size/Leledit/your-life-service)

# A little about the project

This project consists of a RESTful api, developed in java Spring boot. This project is basically a financial module, where users can register and store data relating to their finances.

The project revolves around the month (current), where the user can register their expenses (fixed or variable), their income, and control the health of their finances.

User purchases can be registered in different categories, so that they can control where their money is going. something like "market shopping", "pharmacy", "car expenses", "education" and so on.

Well, this is a pilot project, which I am developing to test Some ideas that popped into my head, this encompasses around 10% of the total idea, with the rest being implemented in a larger project (separate).

One of the motivations for developing this project is to put into practice knowledge related to using Java with spring boot (something new for me).

# Starting the project

After executing the project, you can go to its documentation, at the address http://127.0.1.1:8080/swagger-ui/index.html


# Related view

This repository consumes this API, and implements a graphic designer for it. This way, it is possible to have a clearer view of the project, how it works and how it is used on a daily basis.

the repository can be found here: https://github.com/Leledit/your-life-webapp


# Project Architecture

The folder structure adopted by the project was as follows:

- config: stores the project settings, they can be related to the execution of the project (execution environment), or they can be related to spring boot customizations. or even additional resources, added to the project
- constants: stores all texts that are used in the application. basically here I store
- controller: all api endPoints are stored here, grouped into two folders "finace" and "user", each folder contains files that deal with a specific type of route
- exceptions: here I have a file that handles the exceptions that are created in the application. Let's say that here I make a configuration, to make the exception more friendly, for whoever uses the application.
- infra: here I store files that are related to the project's infrastructure.
- model: in this folder all information representations that are used in the application are stored
    * dto: all files that are used to transmit information between the api. I use them to send in the body of the request, only the data that is interesting to the user (all data is not sent).
    * entity: used to store our application's entities, these are used to represent the structure that is saved in the database.
    * types: in this folder the types (enuns) used in the application are defined.
    * vo: stores classes that represent the data that is brought in the request composition (each file stores the data that can come in a request).
- repository: here are the interfaces that deal with the database. This interface extends from "MongoRepository" (in the context of this project).
-service: here interfaces are stored that represent the operations that can be performed on the database. Furthermore, we have an "impl" folder that creates classes that implement these interfaces.
- utils: here are stored utility classes that are used in different parts of the project



    