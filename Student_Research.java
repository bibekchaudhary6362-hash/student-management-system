/**
 * File: Student_Research.java
 * Purpose: Represents a research student (a student enrolled in research).
 *          Extends the Student class and adds research-specific functionality.
 * 
 * Inheritance Concepts:
 *  - This class extends Student (the base class).
 *  - It inherits firstName, lastName, and studentID fields (accessed via getters/setters).
 *  - It inherits getFullName() and other methods from Student.
 *  - It overrides the reportGrade() method to provide research-specific behavior.
 *  - The super() call in constructors invokes the parent class constructor.
 * 
 * Polymorphism Concepts:
 *  - The reportGrade() method is overridden to display research student details.
 *  - When called on a Student reference pointing to a Student_Research object,
 *    this version of reportGrade() executes (runtime polymorphism).
 *  - Example: Student s = new Student_Research(...); s.reportGrade();
 *    This will call Student_Research.reportGrade(), not Student.reportGrade().
 * 
 * Assumptions:
 *  - Enrolment type is always 'R' for research students.
 *  - Each research student is enrolled in exactly one Unit_Research.
 *  - Unit_Research cannot be null.
 * 
 * @see Student
 * @see Unit_Research
 */
public class Student_Research extends Student {
    // Private instance variables
    private final char enrolmentType = 'R'; // Constant for research type
    private Unit_Research unit;             // The research unit this student is enrolled in

    /**
     * Default constructor.
     * Creates a Student_Research with default student details and a default Unit_Research.
     * Calls the parent Student() constructor to initialize inherited fields.
     */
    public Student_Research() {
        super(); // Call parent default constructor (Student)
        this.unit = new Unit_Research(); // Create default research unit
    }

    /**
     * Parameterized constructor with validation.
     * Creates a Student_Research with specified details.
     * 
     * @param first the student's first name
     * @param last the student's last name
     * @param id the student's ID number
     * @param unit the Unit_Research this student is enrolled in
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Student_Research(String first, String last, long id, Unit_Research unit) {
        super(first, last, id); // Call parent parameterized constructor
        setUnit(unit);          // Validate and set the unit
    }

    // ==================== ACCESSOR METHODS (Getters) ====================
    
    /**
     * Gets the enrolment type for this student.
     * @return 'R' indicating this is a research student
     */
    public char getEnrolmentType() { 
        return enrolmentType; 
    }

    /**
     * Gets the research unit this student is enrolled in.
     * @return the Unit_Research object
     */
    public Unit_Research getUnit() { 
        return unit; 
    }

    // ==================== MUTATOR METHODS (Setters) ====================
    
    /**
     * Sets the research unit after validation.
     * The unit cannot be null.
     * 
     * @param u the Unit_Research to set
     * @throws IllegalArgumentException if u is null
     */
    public void setUnit(Unit_Research u) {
        if (u == null) 
            throw new IllegalArgumentException("Unit_Research required.");
        this.unit = u;
    }

    // ==================== OVERRIDDEN METHODS ====================
    
    /**
     * Reports the grade information for this research student.
     * This method overrides the reportGrade() method in the Student class.
     * 
     * Output Format:
     *  R | [Full Name] | ID: [Student ID] | Overall: [Overall Mark] | Final Grade: [Grade]
     * 
     * Polymorphism Example:
     *  - In the Student class, reportGrade() prints "There is no grade here."
     *  - This overridden version provides research-specific grade information.
     *  - When called polymorphically (Student s = new Student_Research(...); s.reportGrade()),
     *    this version executes, not the parent's version.
     * 
     * Dynamic Binding:
     *  - The JVM determines at runtime which reportGrade() to call based on
     *    the actual object type, not the reference type.
     *  - This is an example of late binding (resolved during execution).
     *  - The method to call is bound to the object at runtime, not compile time.
     * 
     * Difference from Student_Course:
     *  - Research students don't have a unit ID in their report.
     *  - They only have proposal and dissertation marks (stored in Unit_Research).
     */
    @Override
    public void reportGrade() {
        // Print grade report with 'R' prefix to identify research student
        System.out.println("R | " + getFullName() +
                " | ID: " + getStudentID() +
                " | Overall: " + String.format("%.2f", unit.overall()) +
                " | Final Grade: " + unit.finalGrade());
    }

    /**
     * Returns a string representation of this Student_Research.
     * Includes student type indicator, ID, name, and unit details.
     * 
     * @return formatted string with student and unit information
     */
    @Override
    public String toString() {
        return "R-Student[" + getStudentID() + ", " + getFullName() + ", " + unit + "]";
    }
}