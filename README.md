# glasp-search

`glasp-search` is a backend service designed for address validation and search. The API exposes endpoints for validating addresses based on zip code, street, and optionally city, as well as endpoints for triggering the generation and reload of the address database.

## Features

- **Address Validation/Search**: Validate and search addresses using zip code, street, and city.
- **Database Initialization/Management**: Admin endpoints to generate, initialize, or reload address data.

---

## API Usage

### 1. Address Validation (Search Controller)

All address validation endpoints require authentication via Bearer token.

#### a. Search by Zip Code and Street

- **Endpoint**: `POST /app/search/zipstreet`
- **Request Body** (`ZipStreetRequest`):
  ```json
  {
    "zip": "43032",
    "street": "Strada Cascina",
    "country": "IT",
    "maxErrors": 4,
    "noOfResults": 3
  }
  ```
- **Response**: List of matching addresses or error details.

#### b. Search by Zip Code, Street, and City

- **Endpoint**: `POST /app/search/zipstreetcity`
- **Request Body** (`ZipStreetCityRequest`):
  ```json
  {
    "zip": "43032",
    "street": "Strada Cascina",
    "city": "Buseto",
    "country": "IT",
    "maxErrors": 4,
    "noOfResults": 3
  }
  ```
- **Response**: List of matching addresses or error details.

**Notes:**
- At the moment only Italian addresses (`country: "IT"`) are supported.
- `maxErrors` (allowed typo/Levenshtein errors): 0-6
- `noOfResults` (number of results returned): 1-10
- Input validation and rate limiting are enforced. See error responses for details.

---

### 2. Database Management (Database Controller)

These endpoints are generally for administrators and require appropriate credentials.

#### a. Admin Login

- **Endpoint**: `POST /data/login`
- **Request Body**:
  ```json
  {
    "userName": "admin",
    "userPassword": "password"
  }
  ```
- **Response**: JWT token

#### b. Trigger Database Generation

- **Endpoint**: `POST /data/init/zipstreet`
    - Initializes the address database with zip and street data.

- **Endpoint**: `POST /data/init/city`
    - Initializes the address database with city data.

#### c. Reload Database

- **Endpoint**: `POST /data/reload`
    - Reloads all address data from the primary source.

---

## Getting Started

1. **Clone the repository**
   ```sh
   git clone https://github.com/lukas8920/glasp-search.git
   cd glasp-search
   ```

2. **Configuration & Build**
    - Requires Java 17+ and Maven.
    - Configure your `application.properties` as needed (database, authentication).

3. **Run the application**
   ```sh
   mvn spring-boot:run
   ```

4. **Accessing the API**
    - The API will be available at `http://localhost:<port>/`
    - Use an API client (e.g., Postman) with the above endpoints.

---

## Error Handling

Common response codes:
- `200 OK`: Successful response with results.
- `400 Bad Request`: Input validation failed.
- `404 Not Found`: No matching address found.
- `429 Too Many Requests`: Rate limit exceeded.
- `500 Internal Server Error`: Server-side issue.

---

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

---

## License

This project is licensed under the MIT License.

---

## Contact

For questions, please open an issue on GitHub.