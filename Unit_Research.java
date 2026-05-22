/**
 * File: Unit_Research.java
 * Purpose: Represents a research unit with proposal and dissertation marks.
 *          Extends the abstract Unit class and implements the overall() method.
 * 
 * Inheritance Concepts:
 *  - This class extends Unit (the abstract parent class).
 *  - It inherits the enrolmentType field and finalGrade() method from Unit.
 *  - It must implement the abstract overall() method defined in Unit.
 *  - The super('R') call in the constructor invokes the parent class constructor.
 * 
 * Assessment Structure:
 *  - Research proposal worth 40% of the final grade
 *  - Final dissertation worth 60% of the final grade
 *  - Total: 40% + 60% = 100%
 * 
 * Assumptions:
 *  - Both proposal and dissertation marks must be in the range 0-100.
 *  - Research students are assessed differently from coursework students.
 * 
 * Input: Proposal mark and dissertation mark
 * Output: Overall mark (weighted average) and final grade (HD/D/C/P/N)
 * 
 * @see Unit
 * @see Student_Research
 */
public class Unit_Research extends Unit {
    // Private instance variables
    private double proposal;      // Research proposal mark (out of 100)
    private double dissertation;  // Final dissertation mark (out of 100)

    /**
     * Default constructor.
     * Creates a Unit_Research with default values (both marks = 0.0).
     * Calls the parent constructor with 'R' to set enrolment type.
     */
    public Unit_Research() { 
        super('R'); // Call parent constructor with Research type
        // proposal and dissertation default to 0.0
    }

    /**
     * Parameterized constructor with validation.
     * Creates a Unit_Research with specified marks.
     * 
     * @param proposal the mark for the research proposal (0-100)
     * @param dissertation the mark for the dissertation (0-100)
     * @throws IllegalArgumentException if any mark is not in range 0-100
     */
    public Unit_Research(double proposal, double dissertation) {
        super('R'); // Call parent constructor
        setProposal(proposal);
        setDissertation(dissertation);
    }

    // ==================== ACCESSOR METHODS (Getters) ====================
    
    /**
     * Gets the proposal mark.
     * @return the research proposal mark (0-100)
     */
    public double getProposal() { 
        return proposal; 
    }

    /**
     * Gets the dissertation mark.
     * @return the final dissertation mark (0-100)
     */
    public double getDissertation() { 
        return dissertation; 
    }

    // ==================== MUTATOR METHODS (Setters) ====================
    
    /**
     * Sets the proposal mark after validation.
     * The mark must be in the range 0-100.
     * 
     * @param v the proposal mark to set
     * @throws IllegalArgumentException if mark is not in range 0-100
     */
    public void setProposal(double v) {
        if (v < 0 || v > 100)
            throw new IllegalArgumentException("Proposal must be 0..100.");
        this.proposal = v;
    }

    /**
     * Sets the dissertation mark after validation.
     * The mark must be in the range 0-100.
     * 
     * @param v the dissertation mark to set
     * @throws IllegalArgumentException if mark is not in range 0-100
     */
    public void setDissertation(double v) {
        if (v < 0 || v > 100)
            throw new IllegalArgumentException("Dissertation must be 0..100.");
        this.dissertation = v;
    }

    // ==================== OVERRIDDEN METHODS ====================
    
    /**
     * Calculates the overall mark as a weighted average of research assessments.
     * This method implements the abstract overall() method from the Unit class.
     * 
     * Weighting Formula:
     *  - Proposal: 40%
     *  - Dissertation: 60%
     *  - Overall = Proposal × 0.40 + Dissertation × 0.60
     * 
     * Polymorphism Concept:
     *  - This method overrides the abstract overall() method from Unit.
     *  - When finalGrade() in Unit calls overall(), this version executes
     *    if the object is a Unit_Research (dynamic binding).
     *  - This allows research units to calculate marks differently from
     *    coursework units while maintaining a common interface.
     * 
     * Dynamic Binding Example:
     *  - Unit u = new Unit_Research(80, 90);
     *  - String grade = u.finalGrade(); // Calls Unit.finalGrade()
     *  - Inside finalGrade(), overall() is called
     *  - The JVM resolves this to Unit_Research.overall() at runtime
     * 
     * @return the weighted overall mark (0-100)
     */
    @Override
    public double overall() {
        // Calculate weighted average: 40% proposal + 60% dissertation
        return proposal * 0.40 + dissertation * 0.60;
    }

    /**
     * Returns a string representation of this Unit_Research.
     * Includes overall mark and final grade.
     * 
     * @return formatted string with unit details
     */
    @Override
    public String toString() {
        return String.format("Unit_Research[Overall=%.2f Grade=%s]",
                overall(), finalGrade());
    }
}