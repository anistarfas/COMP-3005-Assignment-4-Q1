import java.sql.*;
import java.util.Scanner;

public class StudentsApp {
    //allow us to establish connection to be used throughout functions when passing in our queries
    private static Connection conn;
    //scanner to allow us to receive user input through our application which is then passed to our database
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        // JDBC & Database credentials
        String url = "jdbc:postgresql://localhost:5432/Students_Data" ;
        String user = "postgres";
        String password = "123456";

        try {
            // Load PostgreSQL JDBC Driver
            Class.forName( "org.postgresql.Driver" );
            // Connect to the database verify with the following credentials

            conn = DriverManager.getConnection(url, user, password);

            // we have a successful connection if the conn!=null
            if (conn != null) {
                //welcome message
                System.out.println("Welcome to the student enrollment application!!!");
                //we must loop the whole process to mimic an application where the user can interact via command line interface
                while (true) {

                    //this is important part of the interface to make users select different functions
                    System.out.println("Choose an action:");
                    System.out.println("1. Show all currently enrolled students");
                    System.out.println("2. Add a student");
                    System.out.println("3. Update student email");
                    System.out.println("4. delete a student");
                    System.out.println("5. Exit");
                    // Get user input for the option selected
                    int userSelection = scanner.nextInt();

                    // Process user choice each choice will trigger a function from the CRUD
                    switch (userSelection) {
                        //user selects action 1 to get all students so this case gets triggered
                        case 1 ->
                            //present all students
                                getAllStudents();
                        //user selects to add a students so this case so it gets triggered. We are adding a student and asking for Fname,Lname,email,and date of enrollment form the user
                        case 2 -> {

                            //print statements to get the information needed for the function
                            System.out.println("Please provide the following information");
                            System.out.print("Enter First Name of the student : ");
                            // Read user input for first_name
                            String first_name = scanner.next();
                            //print statements to get the information needed for the function
                            System.out.println("Enter Last Name of the student : ");
                            // Read user input for last_name
                            String last_name = scanner.next();

                            //print statements to get the information needed for the function
                            System.out.println("Enter email of the student : ");
                            // Read user input for email via the scanner
                            String email = scanner.next();
                            //boolean value to make sure our data is in the correct format
                            boolean validInputDate = false;
                            //initializing the enrollmentDate
                            Date enrollmentDate = null;
                            //loop for constant check of validity of the data
                            while (!validInputDate) {
                                //try and catch is improtant throughout this application as we want to make sure user input is in appropriate format
                                try {
                                    //user input for date of enrollment
                                    System.out.println("Enter date of the enrollment of the student(format: yyyy-MM-dd): ");
                                    //scanner to receive user input specifically enrollment date
                                    String EnrollmentDate = scanner.next();
                                    //switch to date type of date
                                    enrollmentDate = Date.valueOf(EnrollmentDate);
                                    //if this passes the date type switch this means we have the correct user input so we can make our valid boolean to true
                                    validInputDate = true;
                                    //our catch
                                } catch (IllegalArgumentException e) {
                                    //inform the user they have entered incorrect user input
                                    System.out.println("Invalid date format. Please enter a valid date in the(format yyyy-MM-dd):");
                                    scanner.nextLine();
                                }
                            }
                            //call addStudent function as required
                            addStudent(first_name, last_name, email, enrollmentDate);
                        }
                        //this option update a student based of their student id and ask for a new email to be updated
                        case 3 -> {
                            System.out.println("Enter the id of student you want to update: ");
                            // Read user input for id to update the email
                            int student_id = scanner.nextInt();
                            System.out.println("Enter the new email of the student : ");
                            // Read user input for the new email
                            String new_email = scanner.next();
                            //call the update required function
                            updateStudentEmail(student_id, new_email);
                        }
                        //case will allow the user to delete a student bases on user id
                        case 4 -> {
                            //this case is for deleting a user using their student_id
                            System.out.println("Enter the id of the student you want to delete: ");
                            //store the id of the student to be passed into the function
                            int deleteStudentId = scanner.nextInt();
                            //calling the required function
                            deleteStudent(deleteStudentId);
                        }
                        //this case will allow the user to exit the application
                        case 5 -> {
                            //user exit case done with using the system
                            System.out.println("Thank you for using the Student enrollment application. Exiting the application. Goodbye!");
                            conn.close();
                            System.exit(0);
                        }
                        //out of bounds not between the 1-5 selection ask the user for more input
                        default -> System.out.println("Invalid choice. Please choose a number between 1 and 5.");
                    }

                }

            } else {
                //inform the user of a failed connection
                System.out.println( "Failed to establish connection." );
            }
            //close connection done with database and application
            conn.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
        // Close the scanner outside the loop important as we have a lot of scanners so it was hard to keep track of all so just did a final close
        scanner.close();
    }

    }

    /*
    Function: getAllStudents()
    what it does: allow user to retrieve all students in the database
    uses SELECT statement to retrieve from the database
     */
    public static void getAllStudents() throws SQLException {

        Statement stmt = conn.createStatement(); // Execute SQL query
        String SQL = "SELECT student_id,first_name, last_name,email,enrollment_date FROM students";
        ResultSet rs = stmt.executeQuery(SQL); // string the results
        //getting to show the user through the application while as long we have next element to retrieve from the set
        while(rs.next()){
            //getting the various elements to later be printed by a println to show the user
            int student_id = rs.getInt("student_id");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            String email = rs.getString("email");
            String enrollment_date = rs.getString("enrollment_date");
            System.out.println("student_id " + student_id + " ,First Name: " + first_name + ", Last name: " + last_name +  " , email: " + email + " , enrollment_date: " + enrollment_date);
        }
        // Close resources
        rs.close();
        stmt.close();
    }


    /*
    Function: addStudent(String first_name, String last_name, String email, Date enrollmentDate)
    what it does: Inserts a new student record into the students table.
    argument in: first name; Fname of the student inputted but the user
    argument in: last name: Lname of the student inputted but the user
    argument in: email: email of the student inputted but the user
    argument in: enrollmentDate: enrollmentDate of the student inputted but the user
    uses 'INSERT INTO' statement is used to insert into the database
     */
    public static void addStudent(String first_name, String last_name, String email, Date enrollmentDate) throws SQLException {
        //the sql statement to insert
        String insertSQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
        //the connection variable

        //executing a parameterized SQL query within thr try statement
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            //setting all the required parameters to be executed
            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setDate(4, enrollmentDate);
            // executing the set parameters
            pstmt.executeUpdate();
            //showing the user the data has been inserted
            System.out.println("Data inserted.");
        //important to catch the SQLException duplicate key value violates unique constraint which email in this case
        } catch (SQLException e) {
        e.printStackTrace();
            //Retrieves the SQLState for this SQLException object.
            //the SQLState value
            //if the value is 23505 which is a violation of the constraint imposed by a unique index or a unique constraint occurred.(which we have for the email no duplicates)
        if (e.getSQLState().equals("23505")) {
            System.out.println("Duplicate email found. Please enter a different email:");

            // ask the user to enter a new email
            System.out.println("Enter the new email of the student : ");
            String newEmail = scanner.next();

            // call to add the email with the new one
            addStudent( first_name,  last_name,  newEmail,  enrollmentDate);
        } else {
            e.printStackTrace();
        }

    }
    }

    /*
    Function: updateStudentEmail(int student_id, String new_email)
    what it does: Updates the email address for a student with the specified student_id.
    argument in: student_id: used to select which student we want to update their email
    argument in: email: email of the student inputted by the user
    uses UPDATE statement to update info of a student email
     */
    public static void updateStudentEmail(int student_id, String new_email) throws SQLException {
        //update SQL statement which will be passed to database via the connection we established
        String updateSQL = "UPDATE students SET email = ? WHERE student_id = ?";
        //the connection variable

        //executing a parameterized SQL query within the try statement
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, new_email);
            pstmt.setInt(2, student_id);
            //this is very important process we store int of the updated row
            int changed = pstmt.executeUpdate();

            //so we check changed more than zero that means the data have changed
            if (changed > 0) {
                //inform the user the data has been updated
                System.out.println("Data updated.");
            } else {
                //when the user inputs a wrong id the changed<0 since no id is matched
                System.out.println("No student found with the provided ID.");
                //prompt the  user again for a valid student id
                System.out.println(" Enter a valid student ID to Update: ");
                int newStudentId = scanner.nextInt();

                System.out.println("Enter the new email of the student : ");
                // Read user input for the new email
                String new_emails = scanner.next();
                //call the update required function
                updateStudentEmail(newStudentId, new_emails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //Retrieves the SQLState for this SQLException object.
            //the SQLState value
            //if the value is 23505 which is a  violation of the constraint imposed by a unique index or a unique constraint occurred.
            if (e.getSQLState().equals("23505")) {
                System.out.println("Duplicate email found. Please enter a different email:");

                // ask the user to enter a new email
                System.out.println("Enter the new email of the student : ");
                String newEmail = scanner.next();

                // call to update the email with the new one
                updateStudentEmail(student_id, newEmail);
            } else {
                e.printStackTrace();
            }

        }

    }

    /*
   Function: deleteStudent(int student_id)
   what it does:  Deletes the record of the student with the specified student_id.
   student_id: used to select which student we want to delete
   uses the 'DELETE' statement to 'DELETE' info of a student  using their student id to identify who they want to delete
    */
    public static void deleteStudent(int student_id) throws SQLException {
        //DELETE SQL statement which will be passed to database via the connection we established
        String deleteSQL = "DELETE FROM students WHERE student_id = ?";


        try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, student_id);
            //this is very important process we store int of what is updated
            int changed = pstmt.executeUpdate();

            //so we check changed more than zero that means the data have changed
            if (changed > 0) {
                System.out.println("Student deleted.");
            } else {
                System.out.println("No student found with the provided ID.");

                // Prompt the user to enter a valid student ID
                System.out.println(" Enter a valid student ID to delete: ");
                int newStudentId = scanner.nextInt();

                //  call to deleteStudent with the new student ID
                deleteStudent(newStudentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
