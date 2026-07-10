# VoteSphere

A comprehensive Java-based web application for conducting, managing, and analyzing voting campaigns and surveys.

## Overview

VoteSphere is a full-stack voting platform built with Java, designed to simplify the process of creating, distributing, and analyzing voting campaigns. Whether you're conducting organizational surveys, academic research, or community polls, VoteSphere provides a robust and scalable solution.

## 🎯 Features

- **Campaign Management**: Create and manage multiple voting campaigns
- **Real-time Analytics**: Track voting results with live dashboards
- **Multiple Export Formats**: Export results as PDF, Excel, and other formats
- **Data Visualization**: Interactive charts and graphs powered by XChart
- **User Authentication**: Secure JWT-based authentication with bcrypt password hashing
- **Email Integration**: Send automated campaign notifications and results
- **Google Calendar Integration**: Schedule campaigns with Google Calendar
- **Cloud Storage**: Cloudinary integration for media management
- **RESTful API**: Complete REST API for programmatic access
- **WebSocket Support**: Real-time updates for live voting tracking
- **Responsive Design**: Mobile-friendly web interface

## 🛠️ Tech Stack

### Backend
- **Java 17**: Modern Java development
- **Maven**: Project and dependency management
- **Servlets**: Jakarta Servlet API (6.1.0)
- **WebSocket**: Jakarta WebSocket (2.2.0)

### Database & Authentication
- **MySQL**: Data persistence
- **JWT**: JSON Web Token authentication (jjwt 0.12.6)
- **BCrypt**: Password hashing (0.9.0)

### External Integrations
- **Google APIs**: Calendar API v3 and OAuth2 authentication
- **Cloudinary**: Image and media hosting
- **JavaMail**: Email service via Jakarta Mail

### Data Processing & Export
- **Apache POI**: Excel file generation (5.4.1)
- **iTextPDF**: PDF generation (5.5.13.3)
- **OpenHTML2PDF**: Advanced PDF rendering (1.0.10)
- **XChart**: Data visualization (3.8.8)

### Serialization & JSON
- **Jackson**: JSON processing and databinding (2.19.0)
- **Gson**: Alternative JSON library (2.13.1)

### Logging
- **Log4j**: Comprehensive logging framework (2.24.3)

### Utilities
- **Lombok**: Reduce boilerplate code (1.18.36)

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- MySQL 5.7+ database
- Tomcat or compatible Jakarta EE-compliant application server
- Docker (optional, for containerized deployment)

## 🚀 Getting Started

### Clone the Repository

```bash
git clone https://github.com/dashivam06/votesphere.git
cd votesphere
```

### Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE votesphere;
```

2. Configure database connection in your application properties/config file

### Build the Project

```bash
mvn clean install
```

### Run the Application

#### Using Maven:
```bash
mvn tomcat7:run
```

#### Using Docker:
```bash
docker build -t votesphere .
docker run -p 8080:8080 votesphere
```

#### Deploy WAR file:
1. Build the WAR: `mvn clean package`
2. Copy `target/voteSphere.war` to your Tomcat `webapps` directory
3. Start Tomcat

### Configuration

Create a configuration file with the following properties:
```properties
# Database Configuration
db.url=jdbc:mysql://localhost:3306/votesphere
db.username=root
db.password=your_password

# Google OAuth Configuration
google.client.id=your_client_id
google.client.secret=your_client_secret

# Cloudinary Configuration
cloudinary.name=your_cloudinary_name
cloudinary.api.key=your_api_key
cloudinary.api.secret=your_api_secret

# Email Configuration
mail.smtp.host=smtp.gmail.com
mail.smtp.port=587
mail.username=your_email@gmail.com
mail.password=your_app_password
```

## 📖 Usage

### Creating a Voting Campaign

1. Navigate to the dashboard
2. Click "Create New Campaign"
3. Fill in campaign details (title, description, questions)
4. Set voting parameters (duration, visibility, etc.)
5. Publish and share with voters

### Analyzing Results

1. Open the campaign dashboard
2. View real-time voting statistics
3. Generate visualizations using built-in charts
4. Export results in your preferred format (PDF, Excel, etc.)

## 🏗️ Project Structure

```
votesphere/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/votesphere/
│   │   │       ├── servlets/          # HTTP request handlers
│   │   │       ├── models/            # Data models
│   │   │       ├── services/          # Business logic
│   │   │       ├── dao/               # Database access
│   │   │       ├── utils/             # Utility classes
│   │   │       └── websocket/         # WebSocket handlers
│   │   └── webapp/
│   │       ├── index.jsp              # Home page
│   │       ├── css/                   # Stylesheets
│   │       ├── js/                    # JavaScript files
│   │       └── WEB-INF/
│   │           └── web.xml            # Web deployment descriptor
│   └── test/
│       └── java/                      # Unit tests
├── pom.xml                            # Maven configuration
├── Dockerfile                         # Docker configuration
└── README.md                          # This file
```

## 🔐 Security Features

- **Password Hashing**: BCrypt for secure password storage
- **JWT Tokens**: Stateless authentication
- **HTTPS Ready**: SSL/TLS support
- **Input Validation**: Server-side validation of all inputs
- **CORS Configuration**: Controlled cross-origin requests

## 📊 Supported Export Formats

- **PDF**: Professional PDF reports with charts and analytics
- **Excel**: Detailed spreadsheets with formulas and formatting
- **CSV**: Comma-separated values for data analysis
- **JSON**: Raw data in JSON format for APIs

## 🔄 Real-Time Features

- **WebSocket Support**: Live voting updates without page refresh
- **Live Dashboard**: Real-time result visualization
- **Instant Notifications**: Campaign updates via email and webhooks

## 🧪 Testing

Run unit tests:
```bash
mvn test
```

Run integration tests:
```bash
mvn verify
```

## 📝 API Documentation

### Example Endpoints

```
POST   /api/campaigns           - Create a new campaign
GET    /api/campaigns/:id       - Get campaign details
PUT    /api/campaigns/:id       - Update campaign
DELETE /api/campaigns/:id       - Delete campaign
POST   /api/campaigns/:id/vote  - Submit a vote
GET    /api/campaigns/:id/results - Get voting results
POST   /api/auth/login          - User login
POST   /api/auth/logout         - User logout
```

For detailed API documentation, see [API_DOCS.md](API_DOCS.md) (if available).

## 🐛 Troubleshooting

### Common Issues

**Issue**: Database connection fails
- Solution: Verify MySQL is running and database credentials are correct

**Issue**: Google OAuth not working
- Solution: Ensure OAuth credentials are properly configured and callback URLs match

**Issue**: Email notifications not sending
- Solution: Check SMTP configuration and ensure less secure app access is enabled (for Gmail)

**Issue**: Docker build fails
- Solution: Ensure Docker daemon is running and Java 17 is installed

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is currently not licensed. See the repository for more information.

## 📞 Support

For issues, questions, or suggestions:
- Open an issue on [GitHub Issues](https://github.com/dashivam06/votesphere/issues)
- Contact the maintainer: [dashivam06](https://github.com/dashivam06)

## 🙏 Acknowledgments

- Google APIs for Calendar and OAuth2 integration
- Apache POI for Excel export capabilities
- OpenHTML2PDF for advanced PDF rendering
- XChart for beautiful data visualizations
- The open-source community for all dependencies

---

**Last Updated**: January 2026  
**Version**: 1.0-SNAPSHOT  
**Language**: Java 17
