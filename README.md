## Rental-Property-Management-system
A Rental Property Management System that allows users to add/remove, search, or manage properties depending on their permission level. It supports functionality for 3 different user types: Manager, Landlord, and Renter.

## Setting Up the MySQL Database
1. Log in to the MySQL server through the command prompt using the credentials for the "root" user.
2. Enter the following commands:
```
mysql> CREATE DATABASE rpms;
mysql> USE rpms;
mysql> SOURCE <PathOfSQLFile>;
```
 
## Installation
1. Clone the repository from github using the following command:
```
git clone https://github.com/janhaviSonawane/Rental-Property-Manager.git
```

## How to Run:
1. Navigate to the root directory of the local repository.
2. Compile using: 
```
javac -cp ".;./lib/mysql-connector-java-8.0.23.jar" MainClass.java
```
in MacOS use:
```
javac -cp ".:./lib/mysql-connector-java-8.0.23.jar" -Xlint:unchecked MainClass.java

```
3. Run using:
```
java -cp ".;./lib/mysql-connector-java-8.0.23.jar" MainClass
```
in MacOS use:
```
java -cp ".:./lib/mysql-connector-java-8.0.23.jar" MainClass

```
4. Enter password through the command line once prompted to connect to the MySQL Server as shown below:
```
Please enter the password to your MySQL Server.
<Insert Password Here>
```

## Login
The user can log in as a Manager using:
```
Username: jan
Password: 123
```

The user can log in as a Landlord using:
```
Username: manjiri
Password: 123
```

The user can log in as a Renter using:
```
Username: john
Password: 123
```

##Demonstration
### Login Page
![LoginView](Login.gif)

### Manager View
![ManagerView](Manager.gif)

### Landlord View
![LandlordView](Landlord.gif)

### Renter View
![RenterView](Renter.gif)
