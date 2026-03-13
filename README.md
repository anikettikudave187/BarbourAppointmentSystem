# Barber Appointment Booking System

A full-stack microservices-based barber shop appointment booking platform.

## Live Demo

Frontend:
https://bms-frontend-miqy.onrender.com

Central API:
https://central-api-m33q.onrender.com

Auth API:
https://anikettikudave-auth-api.onrender.com

DB API:
https://db-api-lfad.onrender.com


## Architecture

Frontend (React)
        ↓
Central API (Spring Boot)
        ↓
Auth API
        ↓
DB API (Spring Boot + MySQL)

The central API acts as the main gateway between frontend and backend services.


## Tech Stack

Frontend
- React
- Axios
- React Router

Backend
- Spring Boot
- Spring Security
- JWT Authentication
- REST APIs

Database
- MySQL

Deployment
- Docker
- Render


## Features

- User Registration
- Login with JWT authentication
- Shop browsing
- Reviews system
- Microservice communication
- Secure API calls


## How to Run Locally

Clone the repo:

git clone https://github.com/YOUR_USERNAME/barber-appointment-system.git

Run services:

1. Start MySQL
2. Run db-api
3. Run auth-api
4. Run central-api
5. Start frontend

Frontend:

npm install
npm start


## Author

Aniket
