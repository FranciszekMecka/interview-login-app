# Maven Spring Project with Tests

This project is a Maven-based Spring application that includes tests.

## Prerequisites

Before you begin, ensure you have met the following requirements:
- Java Development Kit (JDK) installed, preferably JDK 17 or higher.
- Maven installed on your local machine.
- Docker, or a db of your choice.

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
   
3. Run the database by running
   ```sh
     docker-compose up -d
    ```
   inside the project folder or any other way you may find appropriate.
   
### Running Tests

To run the unit tests, use the following Maven command:
   ```sh
     mvn test
   ```
