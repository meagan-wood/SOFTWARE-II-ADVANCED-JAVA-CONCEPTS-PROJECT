#C195: Task 1 Java Application Development
### This is a GUI based scheduling application where the user can view, add, update, and delete customer and appointment information.

* Author: Meagan Wood
* Contact: mwoo195@wgu.edu
* Version: 1.0.1
* Date: 10/19/2022

###Versions
- IntelliJ IDEA 2021.1.3(Community Edition)
- VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.  
- javafx-sdk-11.0.2
- mysql-connector-java:8.0.22

###Running the application
When the application runs, the first display seen will be for login. The user will need to enter a
valid username and password to gain access to the scheduling application. After successful login, the
home screen will appear. Here the user will see all scheduled appointments and have the ability to 
navigate throughout the program where the user can add, update, and delete customer information and
appointments. 

###Additional report
In the reports section you will find a 3rd report. For this report I chose to filter appointments
by a selected month and by the user who scheduled it. This report is useful to view the total
amount of appointments scheduled by each user for each month. This data can be used to ensure users
are meeting an appointment quota and/or for sales purposes. (ex: if users receive commission for each
scheduled appointment, or a bonus if they schedule over a set amount.) 


###Lambdas
- 1 - Navigate to the "addNewCustomer" in the controller package. Find the "onCountryMethod" (Line 84)
- 2 - Navigate to the "AppointmentQueries" in the database package. Find the "appointmentsByContactId"
method (Line 86)