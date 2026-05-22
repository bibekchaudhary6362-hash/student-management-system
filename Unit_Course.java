/**
 * File: Unit_Course.java
 * Purpose: Represents a coursework unit with assignment and exam marks.
 *          Extends the abstract Unit class and implements the overall() method.
 * 
 * Inheritance Concepts:
 *  - This class extends Unit (the abstract parent class).
 *  - It inherits the enrolmentType field and finalGrade() method from Unit.
 *  - It must implement the abstract overall() method defined in Unit.
 *  - The super('C') call in the constructor invokes the parent class constructor.
 * 
 * Assessment Structure:
 *  - Four assignments (A1, A2, A3, A4), each worth 15% of the final grade
 *  - One final exam worth 40% of the final grade
 *  - Total: 4 × 15% + 40% = 100%
 * 
 * Assumptions:
 *  - Unit ID must contain only alphanumeric characters (letters and numbers).
 *  - Level must be a positive integer (typically 1-4 for year levels).
 *  - All marks (assignments and exam) must be in the range 0-100.
 * 
 * Input: Unit ID, level, marks for 4 assignments and 1 exam
 * Output: Overall mark (weighted average) and final grade (HD/D/C/P/N)
 * 
 * @see Unit
 * @see Student_Course
 */
public class Unit_Course extends Unit {
    // Private instance variables
    private String unitID;          // Unit code (e.g., "ICT167")
    private int level;              // Year level (e.g., 1, 2, 3, 4)
    private double a1, a2, a3, a4;  // Assignment marks (each out of 100)
    private double exam;            // Exam mark (out of 100)

    /**
     * Default constructor.
     * Creates a Unit_Course with default values.
     * Calls the parent constructor with 'C' to set enrolment type.
     */
    public Unit_Course() {
        super('C'); // Call parent constructor with Coursework type
        this.unitID = "ICT000";
        this.level = 1;
        // All marks default to 0.0
    }

    /**
     * Parameterized constructor with validation.
     * Creates a Unit_Course with specified values.
     * 
     * @param unitID the unit code (e.g., "ICT167")
     * @param level the year level of the unit
     * @param a1 mark for assignment 1 (0-100)
     * @param a2 mark for assignment 2 (0-100)
     * @param a3 mark for assignment 3 (0-100)
     * @param a4 mark for assignment 4 (0-100)
     * @param exam mark for final exam (0-100)
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Unit_Course(String unitID, int level, double a1, double a2, 
                       double a3, double a4, double exam) {
        super('C'); // Call parent constructor
        setUnitID(unitID);
        setLevel(level);
        setA1(a1); 
        setA2(a2); 
        setA3(a3); 
        setA4(a4);
        setExam(exam);
    }

    // ==================== ACCESSOR METHODS (Getters) ====================
    
    /**
     * Gets the unit ID/code.
     * @return the unit identifier (e.g., "ICT167")
     */
    public String getUnitID() { 
        return unitID; 
    }

    /**
     * Gets the year level of the unit.
     * @return the unit level (typically 1-4)
     */
    public int getLevel() { 
        return level; 
    }

    /**
     * Gets the mark for assignment 1.
     * @return the A1 mark (0-100)
     */
    public double getA1() { 
        return a1; 
    }

    /**
     * Gets the mark for assignment 2.
     * @return the A2 mark (0-100)
     */
    public double getA2() { 
        return a2; 
    }

    /**
     * Gets the mark for assignment 3.
     * @return the A3 mark (0-100)
     */
    public double getA3() { 
        return a3; 
    }

    /**
     * Gets the mark for assignment 4.
     * @return the A4 mark (0-100)
     */
    public double getA4() { 
        return a4; 
    }

    /**
     * Gets the exam mark.
     * @return the exam mark (0-100)
     */
    public double getExam() { 
        return exam; 
    }

    // ==================== MUTATOR METHODS (Setters) ====================
    
    /**
     * Sets the unit ID after validation.
     * The ID must contain only alphanumeric characters (letters and digits).
     * 
     * @param id the unit ID to set
     * @throws IllegalArgumentException if ID is null or contains invalid characters
     */
    public void setUnitID(String id) {
        if (id == null || !id.matches("[A-Za-z0-9]+"))
            throw new IllegalArgumentException("Invalid UnitID.");
        this.unitID = id.trim();
    }

    /**
     * Sets the unit level after validation.
     * The level must be a positive integer.
     * 
     * @param l the level to set
     * @throws IllegalArgumentException if level is not positive
     */
    public void setLevel(int l) {
        if (l <= 0) 
            throw new IllegalArgumentException("Level must be > 0.");
        this.level = l;
    }

    /**
     * Sets the mark for assignment 1 after validation.
     * @param v the mark to set (must be 0-100)
     * @throws IllegalArgumentException if mark is not in range 0-100
     */
    public void setA1(double v) { 
        this.a1 = checkMark(v, "A1"); 
    }

    /**
     * Sets the mark for assignment 2 after validation.
     * @param v the mark to set (must be 0-100)
     * @throws IllegalArgumentException if mark is not in range 0-100
     */
    public void setA2(double v) { 
        this.a2 = checkMark(v, "A2"); 
    }

    /**
     * Sets the mark for assignment 3 after validation.
     * @param v the mark to set (must be 0-100)
     * @throws IllegalArgumentException if mark is not in range 0-100
     */
    public void setA3(double v) { 
        this.a3 = checkMark(v, "A3"); 
    }

    /**
     * Sets the mark for assignment 4 after validation.
     * @param v the mark to set (must be 0-100)
     * @throws IllegalArgumentException if mark is not in range 0-100
     */
    public void setA4(double v) { 
        this.a4 = checkMark(v, "A4"); 
    }

    /**
     * Sets the exam mark after validation.
     * @param v the mark to set (must be 0-100)
     * @throws IllegalArgumentException if mark is not in range 0-100
     */
    public void setExam(double v) { 
        this.exam = checkMark(v, "Exam"); 
    }

    // ==================== PRIVATE HELPER METHODS ====================
    
    /**
     * Validates that a mark is within the acceptable range (0-100).
     * This is a helper method to avoid code duplication in setter methods.
     * 
     * @param v the mark value to validate
     * @param label descriptive label for error messages (e.g., "A1", "Exam")
     * @return the validated mark
     * @throws IllegalArgumentException if mark is not in range 0-100
     */
    private double checkMark(double v, String label) {
        if (v < 0 || v > 100)
            throw new IllegalArgumentException(label + " must be 0..100.");
        return v;
    }

    // ==================== OVERRIDDEN METHODS ====================
    
    /**
     * Calculates the overall mark as a weighted average of assessments.
     * This method implements the abstract overall() method from the Unit class.
     * 
     * Weighting Formula:
     *  - Each assignment (A1-A4): 15% each = 60% total
     *  - Final exam: 40%
     *  - Overall = (A1 + A2 + A3 + A4) × 0.15 + Exam × 0.40
     * 
     * Polymorphism Concept:
     *  - This method overrides the abstract overall() method from Unit.
     *  - When finalGrade() in Unit calls overall(), this version executes
     *    if the object is a Unit_Course (dynamic binding).
     * 
     * @return the weighted overall mark (0-100)
     */
    @Override
    public double overall() {
        // Calculate weighted average: 15% each for 4 assignments + 40% for exam
        return (a1 + a2 + a3 + a4) * 0.15 + exam * 0.40;
    }

    /**
     * Returns a string representation of this Unit_Course.
     * Includes unit ID, level, overall mark, and final grade.
     * 
     * @return formatted string with unit details
     */
    @Override
    public String toString() {
        return String.format("Unit_Course[%s L%d Overall=%.2f Grade=%s]",
                unitID, level, overall(), finalGrade());
    }
}