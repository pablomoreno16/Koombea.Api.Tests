# Koombea.Api.Tests

Tools used in this project:
install JDK (java 8)
Install IntelliJ Idea community edition
the project use Maven and TestNG
Create a project from archetype: org.apache.maven.archetypes:maven-archetype-quickstart
install the following dependencies:
- testng
- rest-assured
- json-path
- jackson-databind

External tools:
- http://www.jsonschema2pojo.org/


Test the API https://rickandmortyapi.com/​ covering positive and negative scenarios, checking the Status Code, Response headers and Response payload.
Using Test-Assured library and implemented Page Object Model (POM) with the following folder structure:

- ApiClients: Contains all the classes needed to perform the APIs calls
- ApiResponseObjects: contain all the classes modeling the responses of the APIs
- DataProviders: Contains the data providers for injecting test data to the tests cases
- Tests: Contains the classes with the tests cases.

