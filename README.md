# Brokerage Firm API

This project implements a backend API for a brokerage firm, allowing employees to manage stock orders and customer assets.

## Features

- Create, list, and delete stock orders
- Deposit and withdraw money for customers
- List customer assets
- Admin user authorization
- Database storage for assets and orders

## Endpoints

1. **Create Order**
    - Create a new order for a given customer, asset, side, size, and price
    - Side: BUY or SELL

2. **List Orders**
    - List orders for a given customer and date range

3. **Delete Order**
    - Cancel a pending order (other status orders cannot be deleted)

4. **Deposit Money**
    - Deposit TRY for a given customer and amount

5. **Withdraw Money**
    - Withdraw TRY for a given customer, amount, and IBAN

6. **List Assets**
    - List all assets for a given customer

## Requirements

- All endpoints are authorized with an admin user and password
- Information is stored in a database (H2)
- Orders are against TRY asset only
- Customer's TRY asset's usable size is checked and updated when creating or cancelling orders

## Implementation

- Java application using Spring Boot Framework
- H2 database

## Building and Running the Project

There is no need for additional configuration. Run and play.

Swagger can be checked for the API request/response details. (http://localhost:8080/swagger-ui/index.html#/)

## Bonus Features

1. Customer table and per-customer authorization
    - Add a login endpoint
    - Customers can only access and manipulate their own data
    - Admin user can manipulate all customer data

2. Admin user endpoint for matching pending orders
    - Match pending orders
    - Update TRY asset's and bought assets' size and usable size values accordingly
    - Note: Orders are not matched against each other
