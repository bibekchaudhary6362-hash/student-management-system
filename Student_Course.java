/**
 * File: Student_Course.java
 * Purpose: Represents a coursework student (a student enrolled in coursework units).
 *          Extends the Student class and adds coursework-specific functionality.
 * 
 * Inheritance Concepts:
 *  - This class extends Student (the base class).
 *  - It inherits firstName, lastName, and studentID fields (accessed via getters/setters).
 *  - It inherits getFullName() and other methods from Student.
 *  - It overrides the reportGrade() method to provide coursework-specific behavior.
 *  - The super() call in constructors invokes the parent class constructor.
 * 
 * Polymorphism Concepts:
 *  - The reportGrade() method is overridden to display coursework student details.
 *  - When called on a Student reference pointing to a Student_Course object,
 *    this version of reportGrade() executes (runtime polymorphism).
 *  - Example: Student s = new Student_Course(...); s.reportGrade();
 *    This will call Student_Course.reportGrade(), not Student.reportGrade().
 * 
 * Assumptions:
 *  - Enrolment type is always 'C' for coursework students.
 *  - Each coursework student is enrolled in exactly one Unit_Course.
 *  - Unit_Course cannot be null.
 * 
 * @see Student
 * @see Unit_Course
 */
public class Student_Course extends Student {
    // Private instance variables
    private final char enrolmentType = 'C'; // Constant for coursework type
    private Unit_Course unit;               // The coursework unit this student is enrolled in

    /**
     * Default constructor.
     * Creates a Student_Course with default student details and a default Unit_Course.
     * Calls the parent Student() constructor to initialize inherited fields.
     */
    public Student_Course() {
        super(); // Call parent default constructor (Student)
        this.unit = new Unit_Course(); // Create default coursework unit
    }

    /**
     * Parameterized constructor with validation.
     * Creates a Student_Course with specified details.
     * 
     * @param first the student's first name
     * @param last the student's last name
     * @param id the student's ID number
     * @param unit the Unit_Course this student is enrolled in
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Student_Course(String first, String last, long id, Unit_Course unit) {
        super(first, last, id); // Call parent parameterized constructor
        setUnit(unit);          // Validate and set the unit
    }

    // ==================== ACCESSOR METHODS (Getters) ====================
    
    /**
     * Gets the enrolment type for this student.
     * @return 'C' indicating this is a coursework student
     */
    public char getEnrolmentType() { 
        return enrolmentType; 
    }

    /**
     * Gets the coursework unit this student is enrolled in.
     * @return the Unit_Course object
     */
    public Unit_Course getUnit() { 
        return unit; 
    }

    // ==================== MUTATOR METHODS (Setters) ====================
    
    /**
     * Sets the coursework unit after validation.
     * The unit cannot be null.
     * 
     * @param u the Unit_Course to set
     * @throws IllegalArgumentException if u is null
     */
    public void setUnit(Unit_Course u) {
        if (u == null) 
            throw new IllegalArgumentException("Unit_Course required.");
        this.unit = u;
    }

    // ==================== OVERRIDDEN METHODS ====================
    
    /**
     * Reports the grade information for this coursework student.
     * This method overrides the reportGrade() method in the Student class.
     * 
     * Output Format:
     *  C | [Full Name] | ID: [Student ID] | Unit: [Unit ID] | Overall: [Overall Mark] | Final Grade: [Grade]
     * 
     * Polymorphism Example:
     *  - In the Student class, reportGrade() prints "There is no grade here."
     *  - This overridden version provides coursework-specific grade information.
     *  - When called polymorphically (Student s = new Student_Course(...); s.reportGrade()),
     *    this version executes, not the parent's version.
     * 
     * Dynamic Binding:
     *  - The JVM determines at runtime which reportGrade() to call based on
     *    the actual object type, not the reference type.
     *  - This is late binding (resolved during program execution, not compilation).
     */
    @Override
    public void reportGrade() {
        // Print grade report with 'C' prefix to identify coursework student
        System.out.println("C | " + getFullName() +
                " | ID: " + getStudentID() +
                " | Unit: " + unit.getUnitID() +
                " | Overall: " + String.format("%.2f", unit.overall()) +
                " | Final Grade: " + unit.finalGrade());
    }

    /**
     * Returns a string representation of this Student_Course.
     * Includes student type indicator, ID, name, and unit details.
     * 
     * @return formatted string with student and unit information
     */
    @Override
    public String toString() {
        return "C-Student[" + getStudentID() + ", " + getFullName() + ", " + unit + "]";
    }
}