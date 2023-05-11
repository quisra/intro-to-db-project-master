package project2;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * 
 * @author Alex, Juan, and Richfield
 *
 */
public class ProjectInterface extends JFrame {

    static Scanner in = new Scanner(System.in);
    static Connection rdsConnection;

    /**
     * Gets username and password from user and attempts to connect to the UNF
     * oracle sql server.
     * On success, the connection to the database is stored in the rdsConnection
     * variable which can be used across the class
     * 
     * @throws SQLException on failure such as incorrect credentials
     */
    private static void getRemoteDatabaseConnection() throws SQLException {
        System.out.println("Database username: ");
        String userName = in.nextLine();
        System.out.println("Database password: ");
        String password = in.nextLine();

        String hostname = "cisvm-oracle.unfcsd.unf.edu";
        String port = "1521";

        /* Connection */ rdsConnection = DriverManager.getConnection(
                "jdbc:oracle:thin:@" + hostname + ":" + port + ":orcl",
                userName, password);
    }

    /**
     * Developer method to initialize databases
     */
    private static void createTables() {
        // method used to ask user if they want to create table.
        try (
                Statement stmt = rdsConnection.createStatement();) {

            System.out.print("enter new database table");
            String sql = getString();
            /*
             * example of how input should be
             * CREATE TABLE REGISTRATION " +
             * "(id INTEGER not NULL, " +
             * " first VARCHAR(255), " +
             * " last VARCHAR(255), " +
             * " age INTEGER, " +
             * " PRIMARY KEY ( id ))";
             */

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Forks user off into the specific add data methods based on user input
     * 
     * @throws SQLException
     */
    private static void addData() throws SQLException {
        String userInput = "";

        while (!userInput.equals("q")) {
            System.out.println(
                    "Add data: (st) Student | (d) Department | (i) Instructor | (c) Course | (se) Section | (sc) Student Courses | (g) Grade | (q) Exit");
            do {
                userInput = in.nextLine();
            } while (userInput.equals(""));

            switch (userInput) {
                case "st":
                    System.out.println("Entering Student Data");
                    addStudent();
                    break;

                case "d":
                    System.out.println("Entering Department Data");
                    addDepartment();
                    break;

                case "i":
                    System.out.println("Entering Instructor Data");
                    addInstructor();
                    break;

                case "c":
                    System.out.println("Entering Course Data");
                    addCourse();
                    break;

                case "se":
                    System.out.println("Entering Section Data");
                    addSection();
                    break;

                case "sc":
                    System.out.println("Entering Student Course Data");
                    addStudentToCourse();
                    break;

                case "g":
                    System.out.println("Entering Grade Data");
                    addGrade();
                    break;

                case "q":
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid Menu Input: " + userInput);
                    break;
            }
        }
    }

    // get string method re-usable
    public static String getString() {
        try {
            StringBuffer buffer = new StringBuffer();
            int c = System.in.read();
            while (c != '\n' && c != -1) {
                buffer.append((char) c);
                c = System.in.read();
            }
            return buffer.toString().trim();
        } catch (IOException e) {
            return "";
        }
    }

    // get int method re-useable
    public static int getInt()

    {
        String s = getString();
        return Integer.parseInt(s);
    }

    private static void addStudent() throws SQLException {
        PreparedStatement pstmt = rdsConnection.prepareStatement(
                "INSERT INTO STUDENT(n_number, degree, sex, birthdate, ssn, current_street, current_city, current_state, current_zip, current_phone, first_name, middle_initial, last_name, student_class, permanent_street, permanent_city, permanent_state, permanent_zip, permanent_phone, minor, major) "
                        +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        int done = 1;

        while (done != 0) {
            System.out.println("\nEnter n_number: ");
            int n_number = getInt();
            System.out.println("\nEnter degree: ");
            String degree = getString();
            System.out.println("\nEnter sex: ");
            String sex = getString();
            
            System.out.println("\nEnter birth date year: "); 
            int birthYear = getInt();
            System.out.println("\nEnter birth date month number: ");
            int birthMonth = getInt();
            System.out.println("\nEnter birth date day: "); 
            int birthDay = getInt();
            String myDate = birthYear + "/" + birthMonth + "/" + birthDay;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            java.util.Date date;
			try {
				date = sdf.parse(myDate);
			} catch (ParseException e) {
				System.out.println("Invalid Date");
				return;
			}
            long millis = date.getTime();
            Date birthdate = new Date(millis);
            
            System.out.println("\nEnter ssn: ");
            int ssn = getInt();
            System.out.println("\nEnter current street: ");
            String current_street = getString();
            System.out.println("\nEnter current city: ");
            String current_city = getString();
            // --------------------------------------
            System.out.println("\nEnter current state: ");
            String current_state = getString();
            System.out.println("\nEnter current zip code: ");
            int current_zip = getInt();
            System.out.println("\nEnter current phone number (No dashes, spaces, parenthesis, or country code): ");
            String current_phone = getString();
            // -------------------------------------------
            System.out.println("\nEnter first name: ");
            String first_name = getString();
            System.out.println("\nEnter middle initial: ");
            String middle_initial = getString();
            System.out.println("\nEnter last name: ");
            String last_name = getString();
            System.out.println("\nEnter student's class: ");
            String student_class = getString();
            // ---------------------------------------------
            System.out.println("\nEnter permanent street: ");
            String permanent_street = getString();
            System.out.println("\nEnter permanent city: ");
            String permanent_city = getString();
            System.out.println("\nEnter permanent state: ");
            String permanent_state = getString();
            System.out.println("\nEnter permanent zip code: ");
            int permanent_zip = getInt();
            System.out.println("\nEnter permanent phone number (No dashes, spaces, parenthesis, or country code): ");
            String permanent_phone = getString();
            System.out.println("\nEnter student's major dept code: ");
            String major = getString();
            System.out.println("\nEnter student's minor dept code: ");
            String minor = getString();

            pstmt.setInt(1, n_number);
            pstmt.setString(2, degree);
            pstmt.setString(3, sex);
            pstmt.setDate(4, birthdate);
            pstmt.setInt(5, ssn);
            pstmt.setString(6, current_street);
            pstmt.setString(7, current_city);
            pstmt.setString(8, current_state);
            pstmt.setInt(9, current_zip);
            pstmt.setString(10, current_phone);
            pstmt.setString(11, first_name);
            pstmt.setString(12, middle_initial);
            pstmt.setString(13, last_name);
            pstmt.setString(14, student_class);
            // ------------------------------------------------
            pstmt.setString(15, permanent_street);
            pstmt.setString(16, permanent_city);
            pstmt.setString(17, permanent_state);
            pstmt.setInt(18, permanent_zip);
            pstmt.setString(19, permanent_phone);
            pstmt.setString(20, minor);
            pstmt.setString(21, major);

            int NumRows = pstmt.executeUpdate();
            System.out.println("\n" + NumRows + " row(s) inserted");

            System.out.println("\nHit 0 for exit, " +
                    "or enter any other number for another insert: ");
            done = getInt();
        } // while done
          // rdsConnection.close();
    }

    private static void addDepartment() throws SQLException {
        PreparedStatement pstmt = rdsConnection.prepareStatement(
                "INSERT INTO DEPARTMENT(code, department_name, college, office_number, office_phone) " +
                        "VALUES (?, ?, ?, ?, ?)");
        int done = 1;

        while (done != 0) {
            System.out.println("\nEnter code: ");
            String code = getString();
            System.out.println("\nEnter department name: ");
            String department_name = getString();
            System.out.println("\nEnter college: ");
            String college = getString();
            System.out.println("\nEnter office number (0-4 digits): ");
            int office_number = getInt();
            System.out.println("\nEnter office phone number (No dashes, spaces, parenthesis, or country code): ");
            String office_phone = getString();

            pstmt.setString(1, code);
            pstmt.setString(2, department_name);
            pstmt.setString(3, college);
            pstmt.setInt(4, office_number);
            pstmt.setString(5, office_phone);
            int NumRows = pstmt.executeUpdate();
            System.out.println("\n" + NumRows + " row(s) inserted");

            System.out.println("\nHit 0 for exit, " +
                    "or enter any other number for another insert: ");
            done = getInt();
        } // while done
          // rdsConnection.close();
    }

    private static void addInstructor() throws SQLException {
        PreparedStatement pstmt = rdsConnection.prepareStatement(
                "INSERT INTO INSTRUCTOR(n_number, ssn, instructor_name, age, personal_phone, office_phone, address, department) "
                        +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        int done = 1;

        while (done != 0) {
            System.out.println("\nEnter n-number: ");
            int n_number = getInt();
            System.out.println("\nEnter ssn: ");
            int ssn = getInt();
            System.out.println("\nEnter name: ");
            String instructor_name = getString();
            System.out.println("\nEnter age: ");
            int age = getInt();
            System.out.println("\nEnter personal phone number: ");
            String personal_phone = getString();
            System.out.println("\nEnter office phone number: ");
            String office_phone = getString();
            System.out.println("\nEnter address: ");
            String address = getString();
            System.out.println("\nEnter department: ");
            String department = getString();

            pstmt.setInt(1, n_number);
            pstmt.setInt(2, ssn);
            pstmt.setString(3, instructor_name);
            pstmt.setInt(4, age);
            pstmt.setString(5, personal_phone);
            pstmt.setString(6, office_phone);
            pstmt.setString(7, address);
            pstmt.setString(8, department);

            int NumRows = pstmt.executeUpdate();
            System.out.println("\n" + NumRows + " row(s) inserted");

            System.out.println("\nHit 0 for exit, " +
                    "or enter any other number for another insert: ");
            done = getInt();
        } // while done
          // rdsConnection.close();
    }

    private static void addCourse() throws SQLException {

        PreparedStatement pstmt = rdsConnection.prepareStatement(
                "INSERT INTO COURSE(course_number, description, course_level, course_name, semester_hours, department) "
                        +
                        "VALUES (?, ?, ?, ?, ?, ?)");
        int done = 1;

        while (done != 0) {
            System.out.println("\nEnter course number: ");
            String course_number = getString();
            System.out.println("\nEnter the description: ");
            String description = getString();
            System.out.println("\nEnter course level: ");
            int course_level = getInt();
            System.out.println("\nEnter course name: ");
            String course_name = getString();
            System.out.println("\nEnter semester hours: ");
            float semester_hours = getInt();
            System.out.println("\nEnter department: ");
            String department = getString();

            pstmt.setString(1, course_number);
            pstmt.setString(2, description);
            pstmt.setInt(3, course_level);
            pstmt.setString(4, course_name);
            pstmt.setFloat(5, semester_hours);
            pstmt.setString(6, department);
            int NumRows = pstmt.executeUpdate();
            System.out.println("\n" + NumRows + " row(s) inserted");

            System.out.println("\nHit 0 for exit, " +
                    "or enter any other number for another insert: ");
            done = getInt();
        } // while done
          // rdsConnection.close();
    }

    private static void addSection() throws SQLException {
        PreparedStatement pstmt = rdsConnection
                .prepareStatement("INSERT INTO SECTION(section_number, course, section_year, semester, instructor) " +
                        "VALUES (?, ?, ?, ?, ?)");
        int done = 1;

        while (done != 0) {
            System.out.println("\nEnter section number: ");
            int section_number = getInt();
            System.out.println("\nEnter course: ");
            String course = getString();
            System.out.println("\nEnter year: ");
            int section_year = getInt();
            System.out.println("\nEnter semester: ");
            String semester = getString();
            System.out.println("\nEnter instructer number: ");
            int instructer = getInt();

            pstmt.setInt(1, section_number);
            pstmt.setString(2, course);
            pstmt.setInt(3, section_year);
            pstmt.setString(4, semester);
            pstmt.setInt(5, instructer);

            int NumRows = pstmt.executeUpdate();
            System.out.println("\n" + NumRows + " row(s) inserted");

            System.out.println("\nHit 0 for exit, " +
                    "or enter any other number for another insert: ");
            done = getInt();
        } // while done
          // rdsConnection.close();

    }

    private static void addStudentToCourse() throws SQLException {
        PreparedStatement pstmt = rdsConnection
                .prepareStatement("INSERT INTO ENROLLED_IN(student, course, section, section_year, semester, grade) " +
                        "VALUES (?, ?, ?, ?, ?, ?)");
        int done = 1;

        while (done != 0) {
            System.out.println("\nEnter student: ");
            int student = getInt();
            System.out.println("\nEnter course: ");
            String course = getString();
            System.out.println("\nEnter section: ");
            int section = getInt();
            System.out.println("\nEnter year: ");
            int section_year = getInt();
            System.out.println("\nEnter semester: ");
            String semester = getString();
            System.out.println("\nEnter grade: ");
            String grade = getString();

            pstmt.setInt(1, student);
            pstmt.setString(2, course);
            pstmt.setInt(3, section);
            pstmt.setInt(4, section_year);
            pstmt.setString(5, semester);
            pstmt.setString(6, grade);

            int NumRows = pstmt.executeUpdate();
            System.out.println("\n" + NumRows + " row(s) inserted");

            System.out.println("\nHit 0 for exit, " +
                    "or enter any other number for another insert: ");
            done = getInt();
        } // while done
          // rdsConnection.close();

    }

    private static void addGrade() throws SQLException {
         DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        String grade;
        String course;
        int section;
        int student;

        int done = 1;
        // read in code
        while (done != 0) {
                System.out.print("Enter student number: ");
                student = getInt();
                System.out.print("Enter grade: ");
                grade = getString();
                System.out.print("Enter course: ");
                course = getString();
                System.out.print("Enter section: ");
                section = getInt();
                String query = "UPDATE enrolled_in SET grade = " + grade + "WHERE student = " + student + " AND course =" + course + " AND section = " + section;

                // create a PreparedStatement object
                PreparedStatement statement = rdsConnection.prepareStatement(query);
              
                // set the values for the placeholders in the query
                statement.setString(1, grade);
                statement.setInt(2, student);
                statement.setString(3, course);
                statement.setInt(4, section);
                
              
                // execute the query
                statement.executeUpdate();
        }
    }

    private static void getGradeReport() throws SQLException {
        int studentNnumber = -1;

        // Get Input
        while (studentNnumber < 0) {
            System.out.println("Enter Student N Number or (q) Exit: ");

            try {
                studentNnumber = in.nextInt();
            } catch (InputMismatchException e) {
                if (in.nextLine().equals("q")) {
                    System.out.println("Exiting...");
                    return;
                }
                ;
                System.out.println("Invalid Student Number");

            }
        }

        System.out.println("Getting grade report for student n" + studentNnumber);
        
        String q = "SELECT * from STUDENT " +
                "where STUDENT.n_number = " + studentNnumber;
        Statement stmt = rdsConnection.createStatement();
        ResultSet rset = stmt.executeQuery(q);

        System.out.println("\n");

        while (rset.next()) {
            String studentName = rset.getString("FIRST_NAME")  + " " + rset.getString("MIDDLE_INITIAL") + " " + rset.getString("LAST_NAME");
            int nNumber = rset.getInt("N_NUMBER");
            String degree = rset.getString("DEGREE");
            String sex = rset.getString("SEX");
            Date bdate = rset.getDate("BIRTHDATE");
            int ssn = rset.getInt("SSN");
            String currentAddress = rset.getString("CURRENT_STREET")  + ", " + rset.getString("CURRENT_CITY") + " " + rset.getString("CURRENT_STATE") + " " + rset.getString("CURRENT_ZIP");
            String permanentAddress = rset.getString("PERMANENT_STREET")  + ", " + rset.getString("PERMANENT_CITY") + " " + rset.getString("PERMANENT_STATE") + " " + rset.getString("PERMANENT_ZIP");
            String currentPhone = rset.getString("CURRENT_PHONE");
            String permanentPhone = rset.getString("PERMANENT_PHONE");
            String studentClass = rset.getString("STUDENT_CLASS");
            String major = rset.getString("MAJOR");
            String minor = rset.getString("MINOR");
            
            System.out.println("Information for student: N" + nNumber);
            System.out.println("Name: " + studentName);
            System.out.println("Birth Date: " + bdate);
            System.out.println("Sex: " + sex);
            System.out.println("SSN: " + ssn);

            System.out.println("Current Phone: " + currentPhone);
            System.out.println("Permanent Phone: " + permanentPhone);
            System.out.println("Current Address: " + currentAddress);
            System.out.println("Permanent Address: " + permanentAddress);

            System.out.println("Degree: " + degree + " in " + major);
            System.out.println("Minor: " + minor);
            System.out.println("Class: " + studentClass);
        } // while rset
        
        
        String q2 = "SELECT * from ENROLLED_IN " +
        		"LEFT JOIN COURSE " +
        		"ON ENROLLED_IN.course = COURSE.course_number " +
                "WHERE ENROLLED_IN.student = " + studentNnumber;
        Statement stmt2 = rdsConnection.createStatement();
        ResultSet rset2 = stmt2.executeQuery(q2);

        System.out.println("\n");
        
        double GPAsum = 0;
        double GPAnumValues = 0;
        
//      Course section | Letter grade | Grade Point
//      GPA
        while (rset2.next()) {
            String course = rset2.getString("COURSE_NAME");
            Float courseHours = rset2.getFloat("SEMESTER_HOURS");
            int section = rset2.getInt("SECTION");

            int sectionYear = rset2.getInt("SECTION_YEAR");
            String sectionSemester = rset2.getString("SEMESTER");
            
            String grade = rset2.getString("GRADE");
            
            double gradepoint = gradeToGPA(grade);
            if(gradepoint >= 0) {
            	GPAnumValues += courseHours;
            	GPAsum += gradepoint * courseHours;
            }
            
            System.out.println("Course: " + course + " section " + section + ", Grade: " + grade + ", Semester: " + sectionSemester + " " + sectionYear);
        } // while rset
        
        System.out.println("Student GPA: " + (GPAsum / GPAnumValues));
    }
    
    private static double gradeToGPA(String grade) {
    	switch (grade) {
		case "A":
			return 4.0;
		case "A-":
			return 3.7;
		case "B+":
			return 3.3;
		case "B":
			return 3.0;
		case "B-":
			return 2.7;
		case "C+":
			return 2.3;
		case "C":
			return 2.0;
		case "D":
			return 1.0;
		case "F":
			return 0.0;
		case "FA":
			return 0.0;
		default:
			return -1.0;
		}
    }

    private static void getDepartmentCourses() throws SQLException {
        int departmentCode;
        String departmentName;
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

        String q = "select c.course_name, c.course_number " +
                "from course c " +
                "inner join department d " +
                "on c.department = d.code " +
                "where d.code = ?";

        PreparedStatement pstmt = rdsConnection.prepareStatement(q);

        System.out.println("\nEnter department code, \nThen course name " +
                "and course number " +
                "will be displayed\n");

        String c = "";

        // read in code
        while (c != "q") {
            System.out.print("Code (enter q for exit): ");
            c = getString();
            if (c.equals("q")){
                System.out.println("Exiting...");
                return;
            };

            pstmt.setString(1, c);

            ResultSet rset = pstmt.executeQuery();

            System.out.println("\n");

            // Iterate through the result and print the employee names
            while (rset.next()) {
                String coursename = rset.getString("course_name");
                String coursenumber = rset.getString("course_Number");
                System.out.println(coursename + ":" + coursenumber);
            } // while rset
        }
        System.out.println("\n");
        // while c

    }

    private static void getInstructorCourseSections() throws SQLException {
        int instructorNnumber = -1;

        // Get Input
        while (instructorNnumber < 0) {
            System.out.println("Enter Instructor N Number or (q) Exit: ");
            try {
                instructorNnumber = in.nextInt();
            } catch (InputMismatchException e) {
                if (in.nextLine().equals("q")) {
                    System.out.println("Exiting...");
                    return;
                }
                ;
                System.out.println("Invalid Instructor Number");

            }
        }

        System.out.println("Getting course sections for instructor n" + instructorNnumber);
        while (!in.nextLine().equals("q")) {
            String q = "select * from s.SECTION " +
                    "inner join i.INSTRUCTOR " +
                    "on s.instructor = i.n_number " +
                    "where i.n_number = " + instructorNnumber;
            Statement stmt = rdsConnection.createStatement();
            ResultSet rset = stmt.executeQuery(q);

            System.out.println("\n");

            while (rset.next()) {
                int section = rset.getInt("section_number");
                String course = rset.getString("course");
                int section_year = rset.getInt("section_year");
                String semester = rset.getString("semester");
                int instructor = rset.getInt("instructor");
                System.out.println(section + ":" + course + ":" +
                        section_year + ":" + semester + ":" + instructor);
            } // while rset

            System.out.println("\n");
        }
    }

    /**
     * Gets database connection then allows user to fork into their desired method.
     * All GET methods are stand-alone while ADD methods are further forked using
     * the addData() method
     * 
     */
    public static void main(String[] args) {
        // create gui window
        /*
         * ProjectInterface window = new ProjectInterface();
         * 
         * window.setSize(500, 500);
         * window.setVisible(true);
         * window.setTitle("UNF");
         * // add swing obj here
         * window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         */

        try {
            getRemoteDatabaseConnection();

            String userInput = "";
            while (!userInput.equals("q")) {
                System.out.println(
                        "Menu: (e) Enter Data | (s) Instructor Course Sections | (d) Department Courses | (g) Grade Report | (q) Quit");
                do {
                    userInput = in.nextLine();
                } while (userInput.equals(""));

                switch (userInput) {
                    case "create":
                        System.out.println("You better know what you're doing...");
                        createTables();
                        return;

                    case "e":
                        System.out.println("Entering Data");
                        addData();
                        break;

                    case "s":
                        System.out.println("Getting Instructor Course Section");
                        getInstructorCourseSections();
                        break;

                    case "d":
                        System.out.println("Getting Department Courses");
                        getDepartmentCourses();
                        break;

                    case "g":
                        System.out.println("Getting Student Grade Report");
                        getGradeReport();
                        break;

                    case "q":
                        System.out.println("Quitting...");
                        break;

                    default:
                        System.out.println("Invalid Menu Input: " + userInput);
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        in.close();
    }
}
