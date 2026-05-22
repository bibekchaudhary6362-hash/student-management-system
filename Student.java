/**
 * File: Student.java
 * Purpose: Base class for representing a student with basic information.
 *          This class serves as the parent class for Student_Course and Student_Research.
 *          Provides polymorphic reportGrade() method to be overridden by child classes.
 * 
 * Assumptions:
 *  - First name and last name must contain only alphabetic characters, spaces, 
 *    apostrophes, or hyphens, and must start with a letter.
 *  - Student ID must be a positive long integer (greater than 0).
 *  - All input strings are trimmed of leading/trailing whitespace.
 * 
 * Input: Constructor parameters for firstName, lastName, and studentID.
 * Output: Student object with validated data; toString() method provides formatted output.
 * 
 * Inheritance Concepts:
 *  - This is the base class in the inheritance hierarchy.
 *  - Child classes (Student_Course, Student_Research) extend this class and inherit
 *    all methods and fields (accessed via getters/setters since fields are private).
 *  - The reportGrade() method demonstrates polymorphism - it's overridden in child classes
 *    to provide specific behavior for coursework and research students.
 * 
 * @see Student_Course
 * @see Student_Research
 */
public class Student {
    // Private instance variables (encapsulation principle)
    private String firstName;  // Student's given name
    private String lastName;   // Student's family name/surname
    private long studentID;    // Unique student identification number

    /**
     * Default constructor.
     * Initializes a Student object with default values.
     * Default values: firstName = "NA", lastName = "NA", studentID = 1
     */
    public Student() {
        this.firstName = "NA";
        this.lastName = "NA";
        this.studentID = 1L;
    }

    /**
     * Parameterized constructor with validation.
     * Creates a Student object with specified values after validating all inputs.
     * 
     * @param firstName the student's first name (must be non-empty and contain only letters)
     * @param lastName the student's last name (must be non-empty and contain only letters)
     * @param studentID the student's ID number (must be positive)
     * @throws IllegalArgumentException if any parameter fails validation
     */
    public Student(String firstName, String lastName, long studentID) {
        setFirstName(firstName);
        setLastName(lastName);
        setStudentID(studentID);
    }

    // ==================== ACCESSOR METHODS (Getters) ====================
    
    /**
     * Gets the student's first name.
     * @return the first name of the student
     */
    public String getFirstName() { 
        return firstName; 
    }

    /**
     * Gets the student's last name.
     * @return the last name of the student
     */
    public String getLastName() { 
        return lastName; 
    }

    /**
     * Gets the student's ID number.
     * @return the student ID
     */
    public long getStudentID() { 
        return studentID; 
    }

    // ==================== MUTATOR METHODS (Setters) ====================
    
    /**
     * Sets the student's first name after validation.
     * The name must contain only alphabetic characters, spaces, apostrophes, or hyphens.
     * It must start with a letter and cannot be empty after trimming whitespace.
     * 
     * @param firstName the first name to set
     * @throws IllegalArgumentException if the name is invalid (null, empty, or contains invalid characters)
     */
    public void setFirstName(String firstName) {
        if (!isValidName(firstName))
            throw new IllegalArgumentException("Invalid first name.");
        this.firstName = firstName.trim();
    }

    /**
     * Sets the student's last name after validation.
     * The name must contain only alphabetic characters, spaces, apostrophes, or hyphens.
     * It must start with a letter and cannot be empty after trimming whitespace.
     * 
     * @param lastName the last name to set
     * @throws IllegalArgumentException if the name is invalid (null, empty, or contains invalid characters)
     */
    public void setLastName(String lastName) {
        if (!isValidName(lastName))
            throw new IllegalArgumentException("Invalid last name.");
        this.lastName = lastName.trim();
    }

    /**
     * Sets the student's ID number after validation.
     * The ID must be a positive integer (greater than 0).
     * 
     * @param studentID the student ID to set
     * @throws IllegalArgumentException if the ID is not positive
     */
    public void setStudentID(long studentID) {
        if (studentID <= 0)
            throw new IllegalArgumentException("Student ID must be positive.");
        this.studentID = studentID;
    }

    // ==================== OTHER PUBLIC METHODS ====================
    
    /**
     * Gets the student's full name (first name + space + last name).
     * 
     * @return the full name as a single string
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Reports the grade information for this student.
     * This is a polymorphic method that will be overridden in child classes
     * (Student_Course and Student_Research) to provide specific grade reporting.
     * 
     * Polymorphism Concept:
     *  - This method serves as the base implementation in the parent class.
     *  - Child classes override this method to provide their own specific behavior.
     *  - When called on a Student reference pointing to a child object, the child's
     *    version executes (dynamic binding/runtime polymorphism).
     * 
     * In this base class, there's no specific grade to report, so a default message is printed.
     */
    public void reportGrade() {
        System.out.println("There is no grade here.");
    }

    /**
     * Compares this student with another student object for equality.
     * Two students are considered equal if they have the same student ID.
     * 
     * @param other the other Student object to compare with
     * @return true if both students have the same ID, false otherwise
     */
    public boolean equals(Student other) {
        if (other == null) {
            return false;
        }
        return this.studentID == other.studentID;
    }

    // ==================== PRIVATE HELPER METHODS ====================
    
    /**
     * Validates whether a name string is acceptable.
     * A valid name:
     *  - Cannot be null
     *  - Cannot be empty or only whitespace after trimming
     *  - Must start with a letter
     *  - Can contain only letters, spaces, apostrophes ('), and hyphens (-)
     * 
     * @param s the name string to validate
     * @return true if the name is valid, false otherwise
     */
    private boolean isValidName(String s) {
        if (s == null) return false;
        String t = s.trim();
        // Check: non-empty and matches pattern starting with letter
        return !t.isEmpty() && t.matches("[A-Za-z][A-Za-z '\\-]*");
    }

    // ==================== OVERRIDDEN METHODS ====================
    
    /**
     * Returns a string representation of this Student object.
     * Format: "Student ID: [id], Name: [firstName lastName]"
     * 
     * @return a formatted string containing the student's ID and full name
     */
    @Override
    public String toString() {
        return "Student ID: " + studentID + ", Name: " + getFullName();
    }
}