# Welcome to Employee Management System!

This is an application was build in Java using Spring Security Framework.
The application provide to the employee a **register** feature, where the employee receives a confirmation email and activate your account. The system also offers a **login** feature and after login she/he can navigate to the personal page and **update** some data and **logout**.
The application also offers a **delete** resource for the ADMIN.

#  Create Maven project with Spring Initializr:

### Dependencies:

-   Spring Security
-   Spring Web
-   Thymeleaf
-   Spring Data JPA
-   Java Mail Sender
-   Postgres Driver
-   Lombok

### 	Database
-   Postgres

## Registering an Employee

The process of registering an employee is built inside the registration service class in the method 
`register(RegistrationRequest request)`where it receives as parameter `RegistrationRequest request` and it needs the following steps:
1. Check if the email is valid using the test method inside the EmailValidator class
2.  With a valid email an employee object will be created with the data inserted inside the signUp method   
    2.1.  The employee service generates and stores the token itself.
3. An email will be sent to the employee where the employee will receive the token for confirmation.
4. Employee clicks on the email link to validate the user.
5. Now employees can log in on the system.

## Employee Update

The update process is allowed to every employee. The `updateEmployee()` method is built inside the `EmployeeService` class and receives `ChangeRequest changeRequest` as parameter, through the `EmployeeRepository` interface you can access the `update()` method and can change the data `firstName` or/and `lastName`. In this particular case, we also received the `email` as a parameter, but in this case a `readonly` attribute is being used in the html, so the employee cannot change it.



## Delete Employee

The `delete` feature is allowed only to the ADMIN. The permission is given in the `WebSecurityConfg` class.

`.antMatchers("/delete").hasAuthority("ADMIN")`  
`.antMatchers("/deleteEmployee").hasAuthority("ADMIN")`

The method `deleteEmployee` is built inside the `EmployeeService` class, receiving `String email` as parameter and the `loadUserByUsername` is used to seek up the employee by email and an employee object is returned. At the end the `delete()`method from JPA is used to delete the employee. 