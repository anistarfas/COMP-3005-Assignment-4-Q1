# COMP-3005-Assignment-4-Q1

•	Youtube link video demonstration:
[https://youtu.be/zrxYT2RaNTo](https://youtu.be/zrxYT2RaNTo)


**	•	Setup instructions for the database:**
1.  Start your pgAdmin4 Application or the equivalent of that
2.  Connect to the PostgreSQL Server
	•	In pgAdmin, under the "Browser" panel on the left, expand the "Servers" section.
	•	You'll see "PostgreSQL #" (where # is the version number). Click on it.
	•	It will prompt you for the password. Enter the password you set during the installation.
		
		**Create a New Database**
	•	Right-click on "Databases" under the PostgreSQL server you just expanded.
	•	Choose "Create" -> "Database”.
	•	Name your database, e.g., “Student_Database” and click "Save"
		
		 **Running the SQL Script
		files for the scripts can be found in the GitHub repository when you download the whole repository you can see the files in the folder** 
		•In pgAdmin, select the newly created Student_Database database by clicking on it.
		•Open the Query Tool by right-clicking on Student_Database and selecting Query Tool.
		•Click on the Open File icon (looks like a folder) in the toolbar.
		•Navigate to the location where you saved DDL_Student_Enrollment_Database.sql and open it.
		•The content of DDL_Student_Enrollment_Database.sql should now be displayed in the query editor.
		•Click on the Run button (a green triangle) to execute the SQL commands.
		•repeat the steps for the DML_Student_Enrollment_Database.sql file which will insert the default values 
YOU HAVE NOW CREATED THE DATABASE YOU ARE READY FOR THE APPLICATION SETUP!!
	**•	Steps to compile and run your application **
1. Download the repository from the GitHub(which will download as a zip)
2. once you have the file opened on an IDE (IntelliJ IDEA)
2. You must make sure no errors are shown on your pom.xml and that all dependencies have been imported with no issue 
3. we must make sure that the JBC driver is able to get authenticated so that the driver can access the database 
4. must update the following lines of code usually localhost is fine unless changed by you, and the port is usually the same unless changed and the same for the user, the password must be whatever password was entered when downloaded.
Additionally: to find the port and the hostname/address you can navigate to your pgAdmin4 application enter your server go to properties and then scroll down to connection you should then be able to see the information:
update info below in the application source code guide above in step 4

String url = "jdbc:postgresql://localhost:5432/Students_Data" ;
String user = "postgres";
String password = "123456";

6. once you have updated the JDBC and database credentials, you should be able to run the application with the IntelliJ IDEA you can press the green triangle play button.
	•	A brief explanation of each function in the application 

**Function: getAllStudents(Connection con)**
**what it does:** allows user to retrieve all students in the database
**argument in:** Connection con: the connection variable between the application and the database
**The DML statement used:** SELECT statement to retrieve from the database
 

**Function: addStudent(String first_name, String last_name, String email, Date enrollmentDate, Connection con)**
**what it does:** Insert a new student record into the student’s table.
**argument in:** Connection con: the connection variable between the application and the database
**argument in:** first name; Fname of the student inputted but the user
**argument in:** last name: Lname of the student inputted but the user
**argument in:** email: email of the student inputted but the user
**argument in: ** enrollmentDate: enrollmentDate of the student inputted but the user
**The DML statement used:** INSERT INTO statement to insert into the database


**Function: updateStudentEmail(int student_id, String new_email, Connection con)**
**what it does:** Updates the email address for a student with the specified student_id.
**Connection con:** the connection variable between the application and the database
**argument in:** student_id: used to select which student we want to update their email
**argument in:** email: email of the student inputted by the user
**The DML statement used:** UPDATE SET WHERE statements to update info of a student’s email using their input in the database


**Function: deleteStudent(int student_id, Connection con)**
**what it does:**  Deletes the record of the student with the specified student_id.
**Connection con:** the connection variable between the application and the database
**student_id:** used to select which student we want to delete
**The DML statement used:** DELETE statement to DELETE info of a student using their student id to identify who they want to delete



