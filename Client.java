/**
 * File: Client.java
 * Purpose: Client program for managing student records with grade analysis.
 *          Loads students from CSV file, provides menu-driven interface for
 *          data management, analysis, sorting, and export functionality.
 * 
 * Features:
 *  1. Load student data from CSV file (student.csv)
 *  2. Remove student by ID with validation
 *  3. Display all student details
 *  4. Analyze coursework student performance vs. average
 *  5. Report grade by student ID (demonstrates polymorphism)
 *  6. Sort students by ID using insertion sort algorithm
 *  7. Export sorted data to CSV file
 * 
 * Data Structure:
 *  - Uses ArrayList<Student> to store all students (both coursework and research)
 *  - No arrays are used; only ArrayList as per assignment requirements
 * 
 * ArrayList vs Array:
 *  - ArrayList is a dynamic data structure that can grow/shrink automatically
 *  - Arrays have fixed size that cannot change after creation
 *  - ArrayList provides methods like add(), remove(), get(), size()
 *  - Arrays use array[index] syntax and have a fixed length property
 *  - ArrayList is part of Java Collections Framework (java.util.ArrayList)
 *  - ArrayList internally uses an array but handles resizing automatically
 *  - For this assignment, ArrayList is more suitable as we don't know how many
 *    students will be in the CSV file beforehand
 * 
 * Polymorphism Demonstration:
 *  - The ArrayList stores Student references (parent type)
 *  - It can hold both Student_Course and Student_Research objects (child types)
 *  - When reportGrade() is called on a Student reference, the appropriate
 *    child class method executes based on the actual object type
 *  - This is runtime polymorphism (dynamic binding)
 * 
 * Sorting Algorithm (Insertion Sort):
 *  - Builds sorted list one element at a time
 *  - Takes each element and inserts it in correct position in sorted portion
 *  - Time Complexity: O(n²) worst/average case, O(n) best case (already sorted)
 *  - Space Complexity: O(1) - sorts in place
 *  - Stable: maintains relative order of equal elements
 *  - Efficient for small datasets or nearly sorted data
 * 
 * CSV File Format:
 *  Coursework: C,FirstName,LastName,StudentID,UnitID,Level,A1,A2,A3,A4,Exam
 *  Research: R,FirstName,LastName,StudentID,Proposal,Dissertation
 *  Lines starting with # or empty lines are ignored
 * 
 * Input: student.csv file in program directory
 * Output: Console-based menu interface, sorted_students.csv for export
 * 
 * Assumptions:
 *  - student.csv exists in the same directory as the program
 *  - CSV format follows the specification above
 *  - Student IDs are unique (duplicates are skipped during loading)
 *  - All validation is performed as per assignment requirements
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Client {
    // Instance variables
    private final ArrayList<Student> students = new ArrayList<>(); // Stores all students
    private boolean isSorted = false;                               // Tracks sort status
    private final Scanner kb = new Scanner(System.in);              // For user input

    /**
     * Main method - program entry point.
     * Execution flow:
     *  1. Create Client instance
     *  2. Display student information header
     *  3. Load data from CSV file
     *  4. Display menu and process user choices
     *  5. Exit when user selects Quit option
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Client app = new Client();
        app.studentInfo();                    // Display header
        app.loadFromCsv("./student.csv");     // Load data
        app.menu();                           // Run main menu loop
        System.out.println("\nProgram terminated.");
        System.out.println("\nThank you for using the App");
    }

    /**
     * Displays student information header.
     * Shows author details, student number, enrolment mode, tutor, and tutorial time.
     * This method satisfies the requirement to output student details at program start.
     */
    private void studentInfo() {
        System.out.println("================================================");
        System.out.println("Student Name: Bibek Chaudhary Tharu");
        System.out.println("Student Number: 35581249");
        System.out.println("Mode of Enrolment: Internal");
        System.out.println("Tutor Name: Rajasree Rajamohanan");
        System.out.println("Tutorial Day/Time: Thursday 10:00 AM");
        System.out.println("================================================\n");
    }

    // ======================= MAIN MENU ==========================
    
    /**
     * Displays main menu and processes user choices in a loop.
     * The loop continues until user selects option 1 (Quit).
     * 
     * Menu Options:
     *  1. Quit - exits the program
     *  2. Remove student by ID - deletes a student record
     *  3. Output all details - displays all student information
     *  4. Coursework stats - analyzes coursework student performance
     *  5. Report grade by ID - displays grade for specific student (polymorphism)
     *  6. Sort by ID - sorts ArrayList using insertion sort
     *  7. Export to CSV - writes sorted data to file
     * 
     * Input validation: Invalid menu choices display error message and re-prompt
     */
    private void menu() {
        while (true) {
            // Display menu options
            System.out.println("\n================= MAIN MENU =================");
            System.out.println("1. Quit");
            System.out.println("2. Remove student by ID");
            System.out.println("3. Output all details");
            System.out.println("4. Coursework >= / < average report");
            System.out.println("5. Report grade by ID");
            System.out.println("6. Sort by student ID (Insertion Sort)");
            System.out.println("7. Output sorted list to CSV");
            System.out.println("=============================================");
            System.out.print("Enter choice (1-7): ");
            
            String ch = kb.nextLine().trim();

            // Process user choice using switch statement
            switch (ch) {
                case "1": 
                    return; // Exit the menu loop and terminate program
                case "2": 
                    removeById(); 
                    break;
                case "3": 
                    printAll(); 
                    break;
                case "4": 
                    courseworkStats(); 
                    break;
                case "5": 
                    reportById(); 
                    break;
                case "6": 
                    insertionSortById(); 
                    break;
                case "7": 
                    exportSorted("sorted_students.csv"); 
                    break;
                default: 
                    System.out.println("Invalid option.");
            }
        }
    }

    // ==================== CSV LOADER =====================
    
    /**
     * Loads student data from a CSV file into the ArrayList.
     * Parses each line, validates data, and creates appropriate student objects.
     * 
     * CSV Format:
     *  - Coursework: C,FirstName,LastName,ID,UnitID,Level,A1,A2,A3,A4,Exam
     *  - Research: R,FirstName,LastName,ID,Proposal,Dissertation
     *  - Lines starting with # are comments (ignored)
     *  - Empty lines are ignored
     * 
     * Error Handling:
     *  - Invalid data: Line is skipped, error message displayed
     *  - Duplicate IDs: Record is skipped, duplicate count incremented
     *  - File not found: Error message displayed, program continues with empty list
     * 
     * Statistics Tracking:
     *  - ok: Successfully loaded records
     *  - dup: Duplicate student IDs (skipped)
     *  - skip: Invalid records (parsing/validation errors)
     * 
     * @param path the file path to the CSV file (e.g., "./student.csv")
     */
    private void loadFromCsv(String path) {
        int ok = 0, dup = 0, skip = 0, lineNo = 0;
        
        // Try-with-resources ensures BufferedReader is closed automatically
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            
            // Read file line by line
            while ((line = br.readLine()) != null) {
                lineNo++;
                String t = line.trim();
                
                // Skip empty lines and comments
                if (t.isEmpty() || t.startsWith("#")) continue;
                
                try {
                    // Split line into fields (keep empty trailing fields)
                    String[] f = t.split(",", -1);
                    
                    // Get enrolment type (first character, uppercase)
                    char type = f[0].trim().toUpperCase().charAt(0);

                    if (type == 'C') {
                        // Process Coursework student
                        // Format: C,First,Last,ID,UnitID,Level,A1,A2,A3,A4,Exam
                        if (f.length != 11)
                            throw new IllegalArgumentException("C row requires 11 fields.");
                        
                        // Extract and validate fields
                        String first = mustName(f[1]);
                        String last  = mustName(f[2]);
                        long id      = mustPosLong(f[3], "StudentID");
                        
                        // Check for duplicate ID
                        if (existsId(id)) { 
                            dup++; 
                            continue; 
                        }
                        
                        String unitId= mustUnitId(f[4]);
                        int level    = mustPosInt(f[5], "Level");
                        double a1    = mustMark(f[6], "A1");
                        double a2    = mustMark(f[7], "A2");
                        double a3    = mustMark(f[8], "A3");
                        double a4    = mustMark(f[9], "A4");
                        double exam  = mustMark(f[10], "Exam");

                        // Create Unit_Course and Student_Course objects
                        Unit_Course uc = new Unit_Course(unitId, level, a1, a2, a3, a4, exam);
                        students.add(new Student_Course(first, last, id, uc));
                        ok++;

                    } else if (type == 'R') {
                        // Process Research student
                        // Format: R,First,Last,ID,Proposal,Dissertation
                        if (f.length != 6)
                            throw new IllegalArgumentException("R row requires 6 fields.");
                        
                        // Extract and validate fields
                        String first = mustName(f[1]);
                        String last  = mustName(f[2]);
                        long id      = mustPosLong(f[3], "StudentID");
                        
                        // Check for duplicate ID
                        if (existsId(id)) { 
                            dup++; 
                            continue; 
                        }
                        
                        double prop  = mustMark(f[4], "Proposal");
                        double diss  = mustMark(f[5], "Dissertation");

                        // Create Unit_Research and Student_Research objects
                        Unit_Research ur = new Unit_Research(prop, diss);
                        students.add(new Student_Research(first, last, id, ur));
                        ok++;

                    } else {
                        // Invalid type (not 'C' or 'R')
                        throw new IllegalArgumentException("Type must be 'C' or 'R'.");
                    }
                } catch (Exception ex) {
                    // Catch any parsing or validation errors
                    skip++;
                    System.out.println("Line " + lineNo + " skipped: " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            // File not found or cannot be read
            System.out.println("CSV not found: " + path + ". Place student.csv in program folder.");
        }
        
        // Reset sort flag since new data is loaded
        isSorted = false;
        
        // Display loading statistics
        System.out.println("Loaded OK=" + ok + ", Duplicates=" + dup + ", Skipped=" + skip + ".");
    }

    /**
     * Checks if a student with the given ID already exists in the ArrayList.
     * Used to prevent duplicate student IDs during CSV loading.
     * 
     * @param id the student ID to check
     * @return true if a student with this ID exists, false otherwise
     */
    private boolean existsId(long id) {
        for (Student s : students)
            if (s.getStudentID() == id) return true;
        return false;
    }

    // =================== MENU ACTIONS ======================

    /**
     * Removes a student by ID with comprehensive validation and confirmation.
     * 
     * Process:
     *  1. Check if ArrayList is empty
     *  2. Prompt for student ID (with validation loop)
     *  3. Search for student with that ID
     *  4. Display found student and ask for confirmation
     *  5. Remove if confirmed, cancel if not
     * 
     * Input Validation:
     *  - ID must be numeric and positive
     *  - ID must exist in the ArrayList
     *  - Confirmation must be 'y' or 'n' (case insensitive)
     *  - Invalid inputs cause re-prompting (not program termination)
     * 
     * Side Effects:
     *  - Removes student from ArrayList if confirmed
     *  - Sets isSorted to false after removal
     */
    private void removeById() {
        // Check if there are any students to remove
        if (students.isEmpty()) {
            System.out.println("No records available to remove.");
            return;
        }

        long id;
        int idx;
        
        // Input validation loop for student ID
        while (true) {
            System.out.print("Enter student ID to remove: ");
            String input = kb.nextLine().trim();
            
            // Try to parse ID as long integer
            try {
                id = Long.parseLong(input);
                if (id <= 0) {
                    System.out.println("Invalid input. ID must be positive.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric ID.");
                continue;
            }

            // Search for student with this ID
            idx = -1;
            for (int i = 0; i < students.size(); i++)
                if (students.get(i).getStudentID() == id) { 
                    idx = i; 
                    break; 
                }

            // Check if student was found
            if (idx == -1)
                System.out.println("Student with ID " + id + " not found. Please try again.");
            else 
                break; // Valid ID found, exit loop
        }

        // Display found student details
        Student s = students.get(idx);
        System.out.println("Found: " + s.getFullName() + " (ID: " + s.getStudentID() + ")");
        
        // Confirmation loop
        while (true) {
            System.out.print("Are you sure you want to remove this student? (y/n): ");
            String confirm = kb.nextLine().trim().toLowerCase();
            
            if (confirm.equals("y") || confirm.equals("yes")) {
                // User confirmed - remove the student
                students.remove(idx);
                isSorted = false; // List is no longer sorted
                System.out.println("Student removed successfully.");
                break;
            } else if (confirm.equals("n") || confirm.equals("no")) {
                // User cancelled
                System.out.println("Removal cancelled.");
                break;
            } else {
                // Invalid confirmation input
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    /**
     * Outputs all details for all students in the ArrayList.
     * Displays both coursework and research students with full information.
     * 
     * Display Format:
     *  - Coursework students: Name, ID, Unit ID, Level, Assignment marks, Exam, Overall, Grade
     *  - Research students: Name, ID, Proposal, Dissertation, Overall, Grade
     * 
     * Uses instanceof operator to determine student type and cast appropriately.
     * This is necessary because ArrayList<Student> holds both Student_Course and
     * Student_Research objects, and we need to access their specific fields.
     */
    private void printAll() {
        // Check if there are any students
        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        System.out.println("\n==================== STUDENT LIST ====================");
        int count = 1;
        
        // Iterate through all students
        for (Student s : students) {
            System.out.println("------------------------------------------------------");
            System.out.printf("%d) ", count++);

            // Check if student is coursework type
            if (s instanceof Student_Course) {
                // Downcast to access Student_Course specific methods
                Student_Course sc = (Student_Course) s;
                Unit_Course uc = sc.getUnit();

                System.out.println("[Coursework Student]");
                System.out.printf("Name: %s %s | ID: %d%n",
                        sc.getFirstName(), sc.getLastName(), sc.getStudentID());
                System.out.printf("Unit ID: %s | Level: %d%n",
                        uc.getUnitID(), uc.getLevel());
                System.out.printf("Assessment Marks -> A1: %.1f, A2: %.1f, A3: %.1f, A4: %.1f, Exam: %.1f%n",
                        uc.getA1(), uc.getA2(), uc.getA3(), uc.getA4(), uc.getExam());
                System.out.printf("Overall Mark: %.2f | Final Grade: %s%n",
                        uc.overall(), uc.finalGrade());

            } else if (s instanceof Student_Research) {
                // Downcast to access Student_Research specific methods
                Student_Research sr = (Student_Research) s;
                Unit_Research ur = sr.getUnit();

                System.out.println("[Research Student]");
                System.out.printf("Name: %s %s | ID: %d%n",
                        sr.getFirstName(), sr.getLastName(), sr.getStudentID());
                System.out.printf("Marks -> Proposal: %.1f, Dissertation: %.1f%n",
                        ur.getProposal(), ur.getDissertation());
                System.out.printf("Overall Mark: %.2f | Final Grade: %s%n",
                        ur.overall(), ur.finalGrade());
            }
        }
        System.out.println("======================================================\n");
    }

    /**
     * Computes statistics for coursework students.
     * Calculates average overall mark and counts students above/below average.
     * 
     * Process:
     *  1. Filter out only coursework students from the ArrayList
     *  2. Calculate average of their overall marks
     *  3. Count how many are >= average and how many are < average
     *  4. Display results
     * 
     * Note: Only coursework students are included in this analysis.
     * Research students are excluded as they have different assessment structure.
     */
    private void courseworkStats() {
        // Create temporary list of only coursework students
        ArrayList<Student_Course> cs = new ArrayList<>();
        for (Student s : students)
            if (s instanceof Student_Course) 
                cs.add((Student_Course) s);
        
        // Check if there are any coursework students
        if (cs.isEmpty()) {
            System.out.println("No coursework students.");
            return;
        }

        // Calculate average overall mark
        double sum = 0;
        for (Student_Course sc : cs)
            sum += sc.getUnit().overall();
        double avg = sum / cs.size();

        // Count students above and below average
        int ge = 0, lt = 0; // ge = greater than or equal, lt = less than
        for (Student_Course sc : cs) {
            if (sc.getUnit().overall() >= avg) 
                ge++;
            else 
                lt++;
        }

        // Display statistics
        System.out.printf("Coursework average overall = %.2f%n", avg);
        System.out.println("Count >= average: " + ge);
        System.out.println("Count <  average: " + lt);
    }

    /**
     * Finds a student by ID and reports their grade using polymorphism.
     * 
     * Polymorphism Demonstration:
     *  - The ArrayList holds Student references (parent type)
     *  - Each reference may point to Student_Course or Student_Research (child types)
     *  - When reportGrade() is called on the Student reference, the JVM determines
     *    at runtime which version to execute based on the actual object type
     *  - If object is Student_Course -> Student_Course.reportGrade() executes
     *  - If object is Student_Research -> Student_Research.reportGrade() executes
     *  - This is runtime polymorphism (dynamic binding)
     * 
     * Dynamic Binding:
     *  - The method to call is determined at runtime, not compile time
     *  - The compiler only checks that reportGrade() exists in Student class
     *  - At runtime, the JVM looks at the actual object and calls the appropriate version
     *  - This allows the same code (found.reportGrade()) to behave differently
     *    depending on the object type
     * 
     * Input Validation:
     *  - ID must be numeric and positive
     *  - ID must exist in the ArrayList
     */
    private void reportById() {
        // Check if there are any students
        if (students.isEmpty()) {
            System.out.println("No records.");
            return;
        }
        
        // Prompt for student ID
        System.out.print("Enter student ID: ");
        String in = kb.nextLine().trim();
        long id;
        
        // Validate input
        try {
            id = Long.parseLong(in);
            if (id <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }

        // Search for student with this ID
        Student found = null;
        for (Student s : students)
            if (s.getStudentID() == id) { 
                found = s; 
                break; 
            }

        // Display result
        if (found == null)
            System.out.println("Student not found.");
        else 
            found.reportGrade(); // POLYMORPHIC CALL - executes child class method
    }

     /**
     * Sorts all students in ascending order of Student ID using the Insertion Sort algorithm.
     *
     * Algorithm Description:
     * Insertion Sort builds a sorted list one element at a time by taking the next unsorted element
     * and inserting it into its correct position in the already sorted portion.
     * It repeatedly shifts larger elements one step to the right until the correct position
     * for the current element (key) is found.
     *
     * Steps:
     * 1. Assume the first element is already sorted.
     * 2. Pick the next element (key) from the unsorted portion.
     * 3. Compare the key with elements in the sorted portion (on its left).
     * 4. Shift all larger elements one position to the right.
     * 5. Insert the key into its correct position.
     * 6. Repeat until all elements are sorted.
     *
     * Example:
     * IDs before sorting: [5, 2, 8, 1]
     * Pass 1 → [2, 5, 8, 1]
     * Pass 2 → [2, 5, 8, 1] (8 stays)
     * Pass 3 → [1, 2, 5, 8]
     * Final result → Sorted ascending by ID.
     *
     * Complexity:
     * • Best Case: O(n) — when list is already sorted.
     * • Average Case: O(n²) — random order.
     * • Worst Case: O(n²) — reverse order.
     * • Space Complexity: O(1) — in-place (no extra memory).
     * • Stable Sort — maintains relative order of equal elements.
     *
     * Why Insertion Sort:
     * • Required by the assignment specification (no Java library sort).
     * • Simple to understand and implement.
     * • Works efficiently for small datasets (10–20 records).
     * • Ideal for demonstrating core algorithmic concepts.
     *
     * Program Behaviour:
     * After sorting, this method prints the list in a clean, human-readable format showing
     * each student’s full name and ID on a single line. This provides a quick visual verification
     * that the data has been successfully sorted.
     *
     * Output Example:
     * ==================== SORTED LIST ====================
     * Emily Johnson (ID: 33198765)
     * Sophia Taylor (ID: 33232109)
     * David Jones (ID: 33234567)
     * William Moore (ID: 33243210)
     * Michael Williams (ID: 33245678)
     * =====================================================
     */
     private void insertionSortById() {
      if (students.isEmpty()) {
          System.out.println("No records to sort.");
          return;
     }

      // Insertion sort by student ID
      for (int i = 1; i < students.size(); i++) {
         Student key = students.get(i);
         long keyId = key.getStudentID();
         int j = i - 1;
         while (j >= 0 && students.get(j).getStudentID() > keyId) {
             students.set(j + 1, students.get(j));
             j--;
         }
         students.set(j + 1, key);
     }

       isSorted = true;
       System.out.println("Sorted by student ID using Insertion Sort.");
       // Display the sorted names and IDs
       System.out.println("\n==================== SORTED LIST ====================");
       for (Student s : students) {
          System.out.printf("%s (ID: %d)%n", s.getFullName(), s.getStudentID());
      }
     System.out.println("=====================================================\n");
}
    /**
     * Exports sorted ArrayList data to a CSV file.
     * 
     * Output Format:
     *  - Coursework: C,FirstName,LastName,StudentID,UnitID,Level,A1,A2,A3,A4,Exam
     *  - Research: R,FirstName,LastName,StudentID,Proposal,Dissertation
     * 
     * Precondition: ArrayList must be sorted (checked by isSorted flag)
     * 
     * Error Handling:
     *  - If not sorted: displays error message, does not create file
     *  - If file cannot be created: displays error message with exception details
     * 
     * Note: No library methods are used for CSV writing - manual string concatenation only
     * 
     * @param outPath the file path for output (e.g., "sorted_students.csv")
     */
    private void exportSorted(String outPath) {
        // Check if data is sorted
        if (!isSorted) {
            System.out.println("ArrayList not sorted. Use option 6 first.");
            return;
        }
        
        // Try-with-resources ensures PrintWriter is closed automatically
        try (PrintWriter pw = new PrintWriter(new FileWriter(outPath))) {
            // Write each student to file
            for (Student s : students) {
                if (s instanceof Student_Course) {
                    // Write coursework student
                    Student_Course sc = (Student_Course) s;
                    Unit_Course uc = sc.getUnit();
                    pw.println("C," + sc.getFirstName() + "," + sc.getLastName() + "," + 
                            sc.getStudentID() + "," + uc.getUnitID() + "," + uc.getLevel() + "," +
                            uc.getA1() + "," + uc.getA2() + "," + uc.getA3() + "," + 
                            uc.getA4() + "," + uc.getExam());
                    
                } else if (s instanceof Student_Research) {
                    // Write research student
                    Student_Research sr = (Student_Research) s;
                    Unit_Research ur = sr.getUnit();
                    pw.println("R," + sr.getFirstName() + "," + sr.getLastName() + "," + 
                            sr.getStudentID() + "," + ur.getProposal() + "," + ur.getDissertation());
                }
            }
            System.out.println("Exported to " + outPath);
            
        } catch (IOException e) {
            // File writing error
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    // =================== VALIDATION HELPERS ===================
    
    /**
     * Validates a name string.
     * Names must start with a letter and contain only letters, spaces, apostrophes, or hyphens.
     * 
     * @param s the name string to validate
     * @return the trimmed valid name
     * @throws IllegalArgumentException if name is invalid
     */
    private String mustName(String s) {
        if (s == null || !s.matches("[A-Za-z][A-Za-z '\\-]*"))
            throw new IllegalArgumentException("Invalid name: " + s);
        return s.trim();
    }

    /**
     * Validates a unit ID string.
     * Unit IDs must contain only alphanumeric characters.
     * 
     * @param s the unit ID to validate
     * @return the trimmed valid unit ID
     * @throws IllegalArgumentException if unit ID is invalid
     */
    private String mustUnitId(String s) {
        if (s == null || !s.matches("[A-Za-z0-9]+"))
            throw new IllegalArgumentException("Invalid UnitID: " + s);
        return s.trim();
    }

    /**
     * Parses and validates a positive long integer.
     * Used for student IDs.
     * 
     * @param s the string to parse
     * @param label descriptive label for error messages
     * @return the parsed positive long value
     * @throws IllegalArgumentException if value is not a positive integer
     */
    private long mustPosLong(String s, String label) {
        try {
            long v = Long.parseLong(s.trim());
            if (v <= 0) throw new NumberFormatException();
            return v;
        } catch (Exception e) {
            throw new IllegalArgumentException(label + " must be a positive integer.");
        }
    }

    /**
     * Parses and validates a positive integer.
     * Used for unit levels.
     * 
     * @param s the string to parse
     * @param label descriptive label for error messages
     * @return the parsed positive int value
     * @throws IllegalArgumentException if value is not a positive integer
     */
    private int mustPosInt(String s, String label) {
        try {
            int v = Integer.parseInt(s.trim());
            if (v <= 0) throw new NumberFormatException();
            return v;
        } catch (Exception e) {
            throw new IllegalArgumentException(label + " must be a positive integer.");
        }
    }

    /**
     * Parses and validates a mark (0-100).
     * Used for all assessment marks (assignments, exams, proposals, dissertations).
     * 
     * @param s the string to parse
     * @param label descriptive label for error messages
     * @return the parsed valid mark
     * @throws IllegalArgumentException if mark is not in range 0-100
     */
    private double mustMark(String s, String label) {
        try {
            double v = Double.parseDouble(s.trim());
            if (v < 0 || v > 100) throw new NumberFormatException();
            return v;
        } catch (Exception e) {
            throw new IllegalArgumentException(label + " must be 0..100.");
        }
    }
}