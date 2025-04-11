
---

# Water Quality Monitoring System

## Overview


A scalable solution for water quality monitoring across villages, cities, or localities — featuring REST APIs for data ingestion and retrieval, and a chatbot for user-friendly access. Designed for nationwide deployment

## Technologies Used

- **Backend**: Java 17, Spring Boot
- **Database**: H2 (in-memory)
- **Frontend**: Thymeleaf for rendering the chatbot interface
- **JSON Processing**: Jackson
- **Testing**: JUnit, Mockito

## API Endpoints

### 1. Ingest Water Quality Data

- **Endpoint**: `POST /api/water-quality`
- **Description**: Ingest water quality data.
- **Request Body**:
  ```json
    {
  "locationCode": "village1",
  "dateCollected": "2024-01-01",
  "qualityMetrics": {
    "pH": 7.2,
    "chlorine": 0.4,
    "turbidity": 3.1
  }
}
  ```
### Ingest Water Quality Data

You can use the following `curl` command to ingest water quality data into the system:

```bash
curl --location 'http://localhost:8080/api/water-quality' \
  --header 'Content-Type: application/json' \
  --data '{
  "locationCode": "village1",
  "dateCollected": "2024-01-01",
  "qualityMetrics": {
    "pH": 7.2,
    "chlorine": 0.4,
    "turbidity": 3.1
  }
}'
````
- **Response**:
  - Status: `201 Created`
  - Body: Confirmation message

### 2. Get Water Quality Data

- **Endpoint**: `GET /api/metrics/{locationID}`
- **Description**: Retrieve water quality data for a specific location.
- **path Parameter**:
  - `locationID`: The ID of the location
- **Response**:
  - Status: `200 OK`
  - Body: List of water quality records

### 3. Chatbot Query

- **Endpoint**: `GET /api/assistant`
- **Description**: Interact with the chatbot to retrieve water quality information.
- **query Parameter**:
  - `query`: The user's question (e.g., "What is the pH value of water in village1?")
- **Response**:
  - Status: `200 OK`
  - Body: Chatbot response with requested data

## Chatbot URL

- **Chatbot Interface**: `http://localhost:8080/assistant`
- Sample Image added on the Project Source Folder

## Build Instructions

### Prerequisites

- **Java JDK 17**: Make sure Java 17 is installed and configured on your machine.
- **Maven**: Install Maven for dependency management.

### Steps to Build and Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/deepvijay7/water-quality-monitoring.git
   cd water-quality-monitoring
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the chatbot**:
   Open your web browser and navigate to `http://localhost:8080/assistant`.

## Testing

Unit tests can be executed using the following command:

```bash
mvn test
```


## License

This project is licensed under the MIT License. See the LICENSE file for details.

---

Feel free to customize any sections according to your specific implementation details or any additional features you've added! If you need further modifications or additional sections, let me know!
