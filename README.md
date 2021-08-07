# Organization for a large multithread project

## Aspects
* to focus on business requirements (through the facade design pattern and component-based organization);
* contains full authentication by JWT (confirmation by email, etc);
* role authorization support;
* facilitates migration to microservices;
* covered by tests;
* code best practices;
* this is code with a high maintainability and expressiveness (to make it easier to read and maintain);
* due to the designed architecture, you only need to test the public services and utils of each component. Bringing more productivity to the team. In addition, it is a decision designed to prevent the team from skipping the tests due to lack of time.
* etc.


## Description
Each component/service is able to communicate with all others.

Communication between components happens only through services.
Meanwhile, each component is divided into several parts. Where one party can only communicate with another from the outermost location.

The purpose of this is to be very efficient in extracting the company's business rules from the code (explicit mapping). After all, this is a very important aspect for a startup.

Furthermore, it is an architecture that can be easily evolved into a micro service architecture. Usually, this happens when the startup grows a exponentially.

This model is more efficient for startup and for initial product creation. After all, it is not always necessary to develop based on micro services.

## Importante
This framework is intended for multithreaded systems only. If you need a singlethread system I advise [this structure in node.js](https://github.com/CarlosMacedo/folder-structure-node).