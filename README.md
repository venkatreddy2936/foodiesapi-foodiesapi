# 🍔 FoodiesAPI - Spring Boot Food Ordering System

A Spring Boot-based REST API backend for an online food ordering application.  
Integrated with **AWS S3** for image uploads, **MongoDB** for data storage, and **Razorpay** for payments.

---

## 🚀 Features

- ✅ User & Admin Roles (JWT-based Authentication)
- 📦 Upload food images to AWS S3
- 🛒 Place, update, and cancel orders
- 💳 Razorpay Integration
- 🔐 Secure login, register, OTP & forgot-password

---

## 🛠 Tech Stack

- Java 19
- Spring Boot
- MongoDB
- Razorpay
- AWS S3
- REST APIs
- Postman Testing

---

## 🔐 Environment Variables (.env or application.properties)

Create a `.env` or `application.properties` file and add the following:

```env

# 📧 Forgot Password - Mail Credentials
SPRING_MAIL_USERNAME=your_email@example.com
SPRING_MAIL_PASSWORD=your_email_password

# 💳 Razorpay Configuration
RAZORPAY_KEY=your_razorpay_key
RAZORPAY_SECRET=your_razorpay_secret

# ☁️ AWS S3 Configuration
AWS_ACCESS_KEY=your_aws_access_key
AWS_SECRET_KEY=your_aws_secret_key

--------

## 📦 Installation

```bash
git clone https://github.com/venkatreddy2936/foodiesapi-foodiesapi.git
cd foodiesapi-foodiesapi
./mvnw spring-boot:run
