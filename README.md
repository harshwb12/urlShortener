# 🚀 URL Shortener Service

A scalable URL shortener built using **Spring Boot, Redis, and PostgreSQL**, designed to handle high read traffic with efficient caching and analytics.

---

## 🌍 Features

* 🔗 Generate short URLs from long URLs
* ⚡ Fast redirection using Redis caching
* 🧠 Base62 encoding for unique short codes
* 📊 Click tracking (analytics)
* ⏳ Expiry support for links
* 🔄 Cache-aside pattern for performance optimization

---

## 🏗️ Architecture

Client → Spring Boot → Redis (cache) → PostgreSQL (persistent storage)

* **PostgreSQL**: Stores URL mappings
* **Redis**: Caches frequently accessed URLs and tracks clicks
* **Service Layer**: Handles business logic

---

## ⚙️ Tech Stack

* Backend: Spring Boot
* Database: PostgreSQL
* Cache: Redis
* Build Tool: Maven

---

## 🔥 API Endpoints

### 1. Create Short URL

POST /shorten?url=https://example.com

Response:
http://localhost:8080/abc123

---

### 2. Redirect

GET /{shortCode}

Redirects to original URL

---

## 🧠 How It Works

1. Long URL is stored in PostgreSQL
2. Unique ID is generated
3. ID is converted to Base62 short code
4. Short code maps to long URL
5. Redis caches results for faster access

---

## 📊 Click Tracking (Advanced)

* Clicks are tracked using Redis atomic counters
* Periodically synced to PostgreSQL
* Reduces database load and improves scalability

---

## 🚀 Future Improvements

* Custom short URLs
* Rate limiting
* Dashboard analytics
* Distributed deployment

---

## 👨‍💻 Author

Harsh Gupta
Backend Developer
