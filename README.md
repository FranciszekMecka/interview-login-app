# Maven Spring Project with Tests

This project is a Maven-based Spring application that includes tests.

## Prerequisites

Before you begin, ensure you have met the following requirements:
- Java Development Kit (JDK) installed, preferably JDK 17 or higher.
- Maven installed on your local machine.
- PostgresDB or any other db of your choice.

## Getting Started

To get a local copy up and running follow these simple steps.

### Installation

1. Clone the repository:

    ```sh
     git clone https://github.com/yourusername/your-repository.git
    ```
2. To build the project using Maven, execute the following command:

    ```sh
     mvn clean install
    ```
   
3. Configure DB settings in application.properties file. Test cases use in memory H2 database.
   
### Running Tests

To run the unit tests, use the following Maven command:
   ```sh
     mvn test
   ```
