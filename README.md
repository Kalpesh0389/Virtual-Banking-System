<div align="center">

# 🏦 VaultX : Virtual Banking System

![Status](https://img.shields.io/badge/Status-Active-success)
![Tech](https://img.shields.io/badge/Java%20Swing-Desktop%20App-blue)
![License](https://img.shields.io/badge/License-Educational-orange)

<h3 align="center">
📂 GitHub:
<a href="https://github.com/Kalpesh0389">View Repository</a>
</h3>

</div>

---

## 📌 About VaultX

**VaultX** is a desktop-based Virtual Banking System that simulates real-world banking operations in a secure and intuitive environment. Designed for both users and administrators, it brings together core banking workflows — from account login and fund management to transaction tracking — all within a clean, responsive Java Swing interface.

VaultX focuses on **secure authentication**, **real-time balance management**, and **role-based dashboards**, making it a practical, real-world simulation of modern banking software.

---

## ✨ Key Highlights

* 🔐 Secure login system for users and admins
* 💰 Deposit, withdrawal & live balance update functionality
* 📊 Intuitive user dashboard for personal account management
* 🛡️ Admin control panel to manage users & transactions
* 🗄️ MySQL database for reliable and persistent data storage
* 🖥️ Java Swing UI for a responsive desktop experience

---

## 🚀 Features

### 👤 User Features
* Secure login with credential verification
* View live account balance in real time
* Deposit funds into account
* Withdraw funds with balance validation
* View personal transaction history

### 🛡️ Admin Features
* Admin dashboard with full platform overview
* View and manage all registered user accounts
* Monitor all transactions across accounts
* Track deposits and withdrawals system-wide
* Account management controls

### 🖥️ Application Features
* Java Swing desktop UI with clean, intuitive layout
* Role-based access control (User / Admin)
* Real-time balance updates after every transaction
* MySQL-backed persistent storage for all records
* Lightweight standalone desktop application

---

## 🏗️ Tech Stack

| Layer        | Technologies                     |
| ------------ | -------------------------------- |
| UI Framework | Java Swing                       |
| Language     | Java                             |
| Database     | MySQL                            |
| Connectivity | JDBC (Java Database Connectivity)|

---

## 📁 Project Structure

```
VirtualBankingSystem/
│
├── src/
│   ├── auth/
│   │   ├── Login.java
│   │   └── Register.java
│   ├── user/
│   │   ├── Dashboard.java
│   │   ├── Deposit.java
│   │   └── Withdraw.java
│   ├── admin/
│   │   ├── AdminDashboard.java
│   │   ├── ManageUsers.java
│   │   └── TransactionLog.java
│   ├── db/
│   │   └── DBConnection.java
│   └── Main.java
│
├── database/
│   └── banking_system.sql
│
└── README.md
```

---

## ⚙️ Installation & Setup

### Prerequisites
* Java JDK (v8+)
* MySQL Server
* MySQL Connector/J (JDBC Driver)
* Any Java IDE (IntelliJ IDEA / Eclipse / NetBeans)

### Clone Repository
```bash
git clone https://github.com/Kalpesh0389/virtual-banking-system.git
cd virtual-banking-system
```

### Database Setup
```sql
-- Open MySQL and run the provided SQL file
source database/banking_system.sql;
```

### Configure Database Connection
Update the credentials in `src/db/DBConnection.java`:
```java
String url = "jdbc:mysql://localhost:3306/banking_system";
String user = "your_mysql_username";
String password = "your_mysql_password";
```

### Run the Application
```bash
# Compile
javac -cp .;mysql-connector-java.jar src/**/*.java

# Run
java -cp .;mysql-connector-java.jar src.Main
```

Or simply open the project in your IDE and run `Main.java`.

---

## 🗄️ Database Schema

| Table          | Description                            |
| -------------- | -------------------------------------- |
| `users`        | Stores user credentials & account info |
| `accounts`     | Holds balance and account details       |
| `transactions` | Logs all deposits and withdrawals       |
| `admins`       | Admin login credentials                |

---

## 🔮 Future Enhancements

* 💸 Fund transfer between user accounts
* 📧 Email notifications for transactions
* 📈 Graphical transaction analytics for admin
* 🔒 Password encryption with BCrypt
* 🌐 Web version using Spring Boot & React

---

## 👨‍💻 Author

**Kalpesh Remje**

Full Stack Developer

📧 Email: [remjekalpesh486@gmail.com](mailto:remjekalpesh486@gmail.com)

🔗 GitHub: [https://github.com/Kalpesh0389](https://github.com/Kalpesh0389)

---

## 📜 License

This project is developed for **educational purposes** and is open for learning and improvement.

---

<div align="center">
⭐ <em>If you like this project, don't forget to star the repository!</em> ⭐
</div>
