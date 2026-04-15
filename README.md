# 🏡 Homestay Booking Management System (Java)

A console-based homestay booking application developed using **Java Object-Oriented Programming (OOP) principles**.
The system allows customers to browse homestays and make bookings while enabling owners to manage homestay listings, availability dates, and reservations.

---

## 🎯 Project Objective

This project demonstrates practical implementation of:

* Interfaces
* Abstract classes
* Inheritance
* Composition & Association relationships
* ArrayList data management
* Exception handling
* Date validation using `LocalDate`
* Menu-driven CLI application design

within a homestay reservation workflow simulation.

---

## 👥 System Users

### 🧑 Customer Module

Customers can:

* View available homestays
* Select booking dates
* Validate continuous booking date ranges
* Make reservations
* View personal booking history

Bookings automatically calculate:

* number of nights stayed
* total booking price based on selected dates 

---

### 🧑‍💼 Owner Module

Owners can:

* Add homestay listings
* Remove homestay listings
* Update price per night
* Add available booking dates
* Remove available booking dates
* View all bookings for owned homestays

Owner authentication is required before accessing management features.

Example login credentials:

```
Owner ID: O001
Password: owner1
```

---

## 🧠 Object-Oriented Concepts Implemented

| Concept        | Implementation                              |
| -------------- | ------------------------------------------- |
| Interface      | `DateManager`                               |
| Abstract Class | `User`                                      |
| Inheritance    | `Customer`, `Owner` extend `User`           |
| Composition    | `Homestay` contains `Booking`               |
| Association    | `Customer` interacts with `HomestayManager` |
| Encapsulation  | Private attributes with getters/setters     |
| Polymorphism   | Overridden `displayMenu()` methods          |

---

## 📂 System Workflow

Customer booking flow:

```
View homestays
→ Select homestay
→ Select check-in date
→ Select check-out date
→ Validate continuous availability
→ Booking confirmed
```

Owner management flow:

```
Login
→ Add homestay
→ Manage available dates
→ Update pricing
→ View bookings
```

---

## 📅 Booking Validation Features

The system ensures:

* valid date format (`DD/MM/YYYY`)
* continuous booking date range
* unavailable dates cannot be selected
* checkout date must be later than check-in date

Invalid booking ranges are automatically rejected.

---

## 🛠 Technologies Used

Language:

```
Java
```

Libraries:

```
ArrayList
Scanner
LocalDate
DateTimeFormatter
ChronoUnit
```

---

---

## ▶️ How to Run the Program

Compile:

```
javac HomestayBookingApp.java
```

Run:

```
java HomestayBookingApp
```

---

## 📊 Features Implemented

System supports:

* homestay listing management
* booking creation workflow
* availability date validation
* booking price calculation
* owner-based listing control
* exception-safe user input handling

---

## ⚠️ System Limitation

Current version:

* bookings stored in memory only during runtime
* no database persistence implemented
* console-based interface only

---

---

## 👨‍💻 Author

Mior Danial Hakim
Bachelor of Computer Science (Computer Networks & Security)
Universiti Teknologi Malaysia
