# 🎨 Meme Generator

A full-stack meme generator application built with Spring Boot and vanilla JavaScript. Create, customize, save, and share memes with advanced text styling options.

![Meme Generator Demo](screenshot.png)

## ✨ Features

- 🖼️ Upload custom images
- ✍️ Add customizable top and bottom text
- 🎨 Advanced text controls:
  - Font selection (Impact, Arial, Comic Sans, etc.)
  - Font size adjustment
  - Text and outline color pickers
  - Vertical position control
- 💾 Save memes to server
- 📥 Download memes locally
- 🌐 Share on social media (Twitter, Facebook, Reddit)
- 🖼️ Gallery view of all created memes
- 📱 Responsive design

## 🛠️ Technologies Used

### Backend
- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- MySQL Database
- Maven

### Frontend
- HTML5 Canvas
- Vanilla JavaScript
- CSS3

## 📦 Installation

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Any modern web browser

### Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/meme-generator.git
cd meme-generator
```

### Backend Setup

1. Navigate to the project directory
```bash
cd Project_Meme
```

2. Build the project
```bash
mvn clean install
```

3. Run the application
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Frontend Setup

1. Open the frontend files with a live server (VS Code Live Server recommended)
2. Make sure the server runs on `http://127.0.0.1:5500` (or update CORS settings in `WebConfig.java`)

## 🚀 Usage

1. **Create a Meme:**
   - Upload an image
   - Add top and bottom text
   - Customize font, size, colors, and position
   - Click "Update Preview" to see changes

2. **Save & Share:**
   - Click "Save to Server" to store your meme
   - Use social media buttons to share
   - Or download directly to your device

3. **View Gallery:**
   - Click "Open Gallery" to see all saved memes

## 📁 Project Structure
```
meme-generator/
├── Project_Meme/                  # Backend (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/meme/
│   │   │   │       ├── config/
│   │   │   │       │   └── WebConfig.java
│   │   │   │       ├── controller/
│   │   │   │       │   └── MemeController.java
│   │   │   │       ├── model/
│   │   │   │       │   └── Meme.java
│   │   │   │       ├── repository/
│   │   │   │       │   └── MemeRepository.java
│   │   │   │       └── service/
│   │   │   │           └── MemeService.java
│   │   │   └── resources/
│   │   │       ├── static/
│   │   │       │   └── uploads/      # Uploaded memes stored here
│   │   │       └── application.properties
│   │   └── pom.xml
│   └── frontend/                      # Frontend files
│       ├── index.html
│       ├── gallery.html
│       ├── app.js
│       └── styles.css
└── README.md
```

## ⚙️ Configuration

### Database Configuration
By default, the app uses H2 in-memory database. To use MySQL or PostgreSQL, update `application.properties`:
```properties
# MySQL Example
spring.datasource.url=jdbc:mysql://localhost:3306/meme_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### CORS Configuration
Update allowed origins in `WebConfig.java`:
```java
.allowedOrigins("http://127.0.0.1:5500", "http://localhost:3000")
```

### Upload Directory
Memes are saved to: `src/main/resources/static/uploads/`

To change this, modify `MemeService.java`:
```java
private final String uploadDir = "your/custom/path";
```
