## "Grade-Book-System" 

This is a Java-based Gradebook Management System integrated with a MySQL database, developed for an institution that specializes in Grade 12 upgrading learners.
The system is designed to support learners who are rewriting matric and can choose specific subjects they want to upgrade rather than following a fixed subject stream.

The application allows administrators to register new students, assign subjects based on the learner’s upgrade choices and manage academic performance (marks) across four academic terms. 
Administartors can insert, update, delete and view assessment and exam marks while the system stores all records securely in a relational database.

The system also supports the generation and printing of academic reports, providing a clear overview of learner performance per subject and per term. 

##  Features

 **Student Registration** <br>
-Capture ID number, name, surname, date of birth, gender. <br>
-Select multiple subjects. <br>
-Data saved to MySQL.

**Existing Students Module** <br>
-JTable displaying all students. <br>
-Select a student → click Next → open results form. <br>
-Auto-display student name & surname at the top.

**Marks Management** <br>
-Choose subject using a combo box (filtered to only show the student’s subjects). <br>
-Capture Test Total, Exam Total and assessment weightings. <br>
-Save, edit or delete marks. <br>
-View full marks table: <br>
| Subject | Term 1 | Term 2 | Term 3 | Term 4 |

**Term & Average Calculations** <br>
-Weighted test + exam calculations. <br>
-Average per term.

**Report Generation** <br>
-Uses Java File Handling <br>
-Generates a printable high school progress report

##  Technologies Framework
- Java JDK 8+. 
- MySQL 5.7 or later. 
- NetBeans IDE (optional).  



##  Screenshots
![Home Page](https://github.com/528hloni/Grade-Book-System/blob/main/Images/Home%20Page.png)
![Create Student](https://github.com/528hloni/Grade-Book-System/blob/main/Images/Create%20Student%20Page.png)
![Existing Students](https://github.com/528hloni/Grade-Book-System/blob/main/Images/Existing%20Student%20Page.png)
![Student Results](https://github.com/528hloni/Grade-Book-System/blob/main/Images/Student%20Result%20Page.png)
![Generated Report](https://github.com/528hloni/Grade-Book-System/blob/main/Images/Generated%20Report.png)

##  Demo
![Demo](https://github.com/528hloni/Grade-Book-System/blob/main/Images/Demo%20vid.gif)
