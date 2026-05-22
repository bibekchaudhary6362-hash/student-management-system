/**
 * File: Unit.java
 * Purpose: Abstract superclass for representing academic units (coursework or research).
 *          This class defines the common structure and behavior for all unit types.
 *          Provides abstract method overall() to be implemented by subclasses.
 * 
 * Inheritance Concepts:
 *  - This is an abstract base class that cannot be instantiated directly.
 *  - It defines the common properties (enrolmentType) shared by all units.
 *  - It provides a concrete method (finalGrade) and an abstract method (overall).
 *  - Child classes (Unit_Course, Unit_Research) must implement the abstract overall() method.
 *  - This demonstrates the use of abstraction to enforce a contract on subclasses.
 * 
 * Polymorphism Concepts:
 *  - The abstract overall() method allows different unit types to calculate 
 *    their overall marks differently while maintaining a common interface.
 *  - The finalGrade() method uses the overridden overall() method from child classes,
 *    demonstrating polymorphic behavior.
 * 
 * Assumptions:
 *  - Enrolment type can only be 'C' (Coursework) or 'R' (Research).
 *  - Overall mark is calculated as a percentage (0-100).
 *  - Grade boundaries are: HD≥80, D≥70, C≥60, P≥50, N<50.
 * 
 * @see Unit_Course
 * @see Unit_Research
 */
public abstract class Unit {
    // Final instance variable - cannot be changed after construction
    private final char enrolmentType; // 'C' for Coursework, 'R' for Research

    /**
     * Protected constructor for creating a Unit with specified enrolment type.
     * This constructor is protected because Unit is abstract and can only be
     * called by subclass constructors.
     * 
     * @param enrolmentType the type of enrolment ('C' for Coursework, 'R' for Research)
     * @throws IllegalArgumentException if enrolmentType is not 'C' or 'R'
     */
    protected Unit(char enrolmentType) {
        // Validate enrolment type
        if (enrolmentType != 'C' && enrolmentType != 'R')
            throw new IllegalArgumentException("Type must be 'C' or 'R'.");
        this.enrolmentType = enrolmentType;
    }

    /**
     * Gets the enrolment type of this unit.
     * 
     * @return 'C' for Coursework or 'R' for Research
     */
    public char getEnrolmentType() { 
        return enrolmentType; 
    }

    /**
     * Abstract method to calculate the overall mark for this unit.
     * This method must be implemented by all concrete subclasses (Unit_Course, Unit_Research).
     * Each subclass will define its own calculation method based on its assessment structure.
     * 
     * Polymorphism Concept:
     *  - This abstract method enforces that all subclasses must provide their own
     *    implementation of how to calculate the overall mark.
     *  - Unit_Course calculates based on 4 assignments and an exam.
     *  - Unit_Research calculates based on proposal and dissertation.
     *  - When called polymorphically, the appropriate subclass version executes.
     * 
     * @return the calculated overall mark as a percentage (0-100)
     */
    public abstract double overall();

    /**
     * Determines the final letter grade based on the overall mark.
     * This method uses the abstract overall() method, which will be resolved
     * at runtime to the appropriate subclass implementation (dynamic binding).
     * 
     * Grade Scale:
     *  - HD (High Distinction): 80-100
     *  - D (Distinction): 70-79
     *  - C (Credit): 60-69
     *  - P (Pass): 50-59
     *  - N (Fail): 0-49
     * 
     * Dynamic Binding Concept:
     *  - This method calls overall(), which is abstract in this class.
     *  - At runtime, the JVM determines which version of overall() to call
     *    based on the actual object type (Unit_Course or Unit_Research).
     *  - This is an example of dynamic/late binding in Java.
     * 
     * @return the final grade as a string (HD, D, C, P, or N)
     */
    public String finalGrade() {
        double m = overall(); // Polymorphic call - resolved at runtime
        
        // Determine grade based on mark boundaries
        if (m >= 80) return "HD";  // High Distinction
        if (m >= 70) return "D";   // Distinction
        if (m >= 60) return "C";   // Credit
        if (m >= 50) return "P";   // Pass
        return "N";                // Fail
    }
}