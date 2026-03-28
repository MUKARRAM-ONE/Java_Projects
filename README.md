# Real-Time Currency Converter Web Application

This is a high-performance, responsive web application built with **Java 23** and **Spring Boot 4.0.5**. It provides real-time currency exchange rates, interactive market trend graphs, and a secure user environment.

## 🚀 Key Features

*   **User Authentication**: Secure Registration and Login system powered by Spring Security.
*   **Real-Time Dashboard**: Displays live exchange rates for top global currencies (USD, EUR, GBP, PKR, etc.).
*   **Interactive Charts**: Visualizes market trends using **Chart.js** for a modern, data-driven experience.
*   **Surgical Converter**: A dedicated tool for instant currency conversion with a one-click "Swap" feature.
*   **High Performance Caching**: Implements **Caffeine Cache** with a 6-hour TTL (Time-To-Live) to ensure lightning-fast responses and resilient API usage.
*   **Responsive Design**: Fully mobile-friendly UI built with Vanilla CSS and Thymeleaf fragments.

## 🛠️ Technology Stack

*   **Backend**: Java 23, Spring Boot 4.0.5, Spring Data JPA.
*   **Security**: Spring Security (Form-based Auth).
*   **Database**: H2 (In-memory, for rapid development and user management).
*   **External API**: [ExchangeRate-API (v6)](https://www.exchangerate-api.com/).
*   **Frontend**: Thymeleaf, Vanilla CSS, Chart.js.
*   **Client**: Spring's modern `RestClient`.

## 📂 Project Structure

```text
src/main/java/com/example/realtime_currency_converter/
├── config/        # Security and App configuration
├── controller/    # Web Controllers (Auth, Dashboard, Converter)
├── model/         # JPA Entities and API DTOs (Records)
├── repository/    # Database access layers
└── service/       # Business logic & External API Client
```

## ⚙️ How It Works

### 1. Security Flow
Users must register and log in to access the dashboard. We use `BCryptPasswordEncoder` for secure password storage in the H2 database.

### 2. API Integration & Caching
The `ExchangeRateClient` uses Spring's `RestClient` to fetch JSON data from the ExchangeRate-API. To avoid hitting API rate limits and to ensure < 100ms response times, we use `@Cacheable`.
- **Cache Strategy**: Data is stored in-memory for 6 hours. If the API is down, the system remains stable by serving cached data.

### 3. Data Visualization
On the Dashboard, we fetch the top 10 world currencies. This data is passed to the frontend and rendered as a **Line Chart** using Chart.js, allowing users to see the relative strength of currencies at a glance.

## 🚦 Getting Started

### Prerequisites
- JDK 23
- Maven 3.9+

### Configuration
The API key is already configured in `src/main/resources/application.yaml`:
```yaml
app:
  exchange-rate-api:
    key: ede3c629785053608ea8887a
```

### Running the App
1.  Open your terminal in the project root.
2.  Run: `./mvnw spring-boot:run`
3.  Access the app at: `http://localhost:8080`
4.  **H2 Console**: Access the database at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:currency_db`).

## 🛡️ Future Enhancements
- [ ] Historical data persistence for 30-day trend analysis.
- [ ] Multi-currency conversion (convert to 5 currencies at once).
- [ ] Geolocation to automatically detect local currency.

---
*Developed as a modern utility for travelers, expatriates, and business professionals.*
