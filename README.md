# C195: Task 1 Java Application Development
## Project Requirements:

### SCENARIO 
You are working for a software company that has been contracted to develop a scheduling desktop user interface application. The contract is with a global consulting organization that conducts business in multiple languages and has main offices in Phoenix, Arizona; New York, New York; and London, England. The consulting organization has provided a MySQL database that your application must pull data from. The database is used for other systems and therefore its structure cannot be modified. 
The organization outlined specific business requirements that must be included as part of the application. From these requirements, a system analyst at your company created solution statements for you to implement in developing the application. These statements are listed in the requirements section. 
### REQUIREMENTS 
Your submission must be your original work. No more than a combined total of 30% of the submission and no more than a 10% match to any one individual source can be directly quoted or closely paraphrased from sources, even if cited correctly. An originality report is provided when you submit your task that can be used as a guide. 
You must use the rubric to direct the creation of your submission because it provides detailed criteria that will be used to evaluate your work. Each requirement below may be evaluated by more than one rubric aspect. The rubric aspect titles may contain hyperlinks to relevant portions of the course. 
You are not allowed to use frameworks or external libraries. The database does not contain data, so it needs to be populated. You must use “test” as the username and password to log in. 

<br>A. Create a log-in form that can determine the user’s location and translate log-in and error control messages (e.g., “The username and password did not match.”) into two languages.

<br>B. Provide the ability to add, update, and delete customer records in the database, including name, address, and phone number. 

<br>C. Provide the ability to add, update, and delete appointments, capturing the type of appointment and a link to the specific customer record in the database. 

<br>D. Provide the ability to view the calendar by month and by week. 

<br>E. Provide the ability to automatically adjust appointment times based on user time zones and daylight saving time. 

<br>F. Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you must incorporate at least two different mechanisms of exception control. 
-  scheduling an appointment outside business hours 
-  scheduling overlapping appointments 
-  entering nonexistent or invalid customer data 
-  entering an incorrect username and password
  
<br>G. Write two or more lambda expressions to make your program more efficient, justifying the use of each lambda expression with an in-line comment. 

<br>H. Write code to provide an alert if there is an appointment within 15 minutes of the user’s log-in. 

<br>I. Provide the ability to generate each of the following reports: 

-  number of appointment types by month 
-  the schedule for each consultant 
-  one additional report of your choice

<br>J. Provide the ability to track user activity by recording timestamps for user log-ins in a .txt file. Each new record should be appended to the log file, if the file already exists. 

<br>K. Demonstrate professional communication in the content and presentation of your submission. 

## 
## The information below was required to be submitted to the evaluator.
### This is a GUI-based scheduling application where the user can view, add, update, and delete customer and appointment information.

* Author: Meagan Wood
* Version: 1.0.1
* Date: 10/19/2022

### Versions
- IntelliJ IDEA 2021.1.3(Community Edition)
- VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.  
- javafx-sdk-11.0.2
- mysql-connector-java:8.0.22

### Running the application:
When the application runs, the first display seen will be for login. The user will need to enter a
valid username and password to gain access to the scheduling application. After successful login, the
home screen will appear. Here the user will see all scheduled appointments and have the ability to 
navigate throughout the program where the user can add, update, and delete customer information and
appointments. 

### Additional report:
In the reports section, you will find a 3rd report. For this report I chose to filter appointments
by a selected month and by the user who scheduled it. This report is useful to view the total
amount of appointments scheduled by each user for each month. This data can be used to ensure users
are meeting an appointment quota and/or for sales purposes. (ex: if users receive commission for each
scheduled appointment, or a bonus if they schedule over a set amount.) 


### Lambdas
- 1 - Navigate to the "addNewCustomer" in the controller package. Find the "onCountryMethod" (Line 84)
- 2 - Navigate to the "AppointmentQueries" in the database package. Find the "appointmentsByContactId"
method (Line 86)
