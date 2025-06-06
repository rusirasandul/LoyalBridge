# LoyalBridge

A Spring Boot-based loyalty program management system that helps businesses manage their loyalty programs, track customer points, and handle rewards.

## Features

- User authentication and authorization with JWT
- Customer management
- Points tracking and management
- Reward system
- Transaction history
- Admin dashboard
- RESTful API endpoints
- Swagger documentation

## Prerequisites

- Java 17 or higher(Used 23)
- Maven 3.6 or higher
- PostgreSQL 17.4 or higher
- Node.js 14 or higher (for frontend)

## Technology Stack

### Backend
- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT Authentication
- Maven


### Frontend
- React
- Material-UI
- Axios
- React Router

## Setup Instructions

### Database Setup

1. Install PostgreSQL if not already installed
2. Create a new database:
```sql
CREATE DATABASE LoyalBridge;
```

### Backend Setup

1. Clone the repository:
```bash
git clone https://github.com/yourusername/Loyal_Bridge-admin_Rusira.git
cd LoyalBridge
```

2. Configure the database connection in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/loyalbridge
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Build and run the application:
```bash
mvn clean install
mvn spring-boot:run
```

The backend server will start on `http://localhost:8080/api`

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm start
```

The frontend will be available at `http://localhost:3000`

## API Documentation

Once the application is running, you can access the Swagger documentation at:
```
http://localhost:8080/api/swagger-ui.html
```

## Project Structure

```
LoyalBridge/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── loyalbridge/
│   │   │           ├── config/
│   │   │           ├── controller/
│   │   │           ├── dto/
│   │   │           ├── entity/
│   │   │           ├── repository/
│   │   │           ├── security/
│   │   │           ├── service/
│   │   │           └── LoyalBridgeApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── frontend/
    ├── src/
    ├── public/
    └── package.json
```

## Security

The application uses JWT (JSON Web Tokens) for authentication. Make sure to:
1. Keep your JWT secret key secure
2. Use HTTPS in production
3. Regularly update dependencies for security patches

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please open an issue in the GitHub repository or contact the development team.

## Acknowledgments

- Spring Boot Team
- PostgreSQL Team
- React Team
- Material-UI Team
