# Student Management System

![Language](https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Linux%20%7C%20macOS-lightgrey?style=for-the-badge)
![Paradigm](https://img.shields.io/badge/Paradigm-OOP-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen?style=for-the-badge)

A Java console application for managing university student records. Supports two student types — coursework and research — with CSV file loading, weighted grade calculation, insertion sort, and CSV export.

---

## Overview

The system reads student data from a CSV file, calculates grades using weighted formulas, and provides a menu-driven interface for managing, analysing, and exporting records.

Built around a two-level inheritance hierarchy:

```
Student
├── Student_Course
└── Student_Research

Unit  (abstract)
├── Unit_Course
└── Unit_Research
```

---

## File Structure

```
student-management-system/
│
├── Client.java             # Main program — menu, CSV I/O, sorting, export
├── Student.java            # Base class — name, ID, polymorphic reportGrade()
├── Student_Course.java     # Coursework student — extends Student
├── Student_Research.java   # Research student — extends Student
├── Unit.java               # Abstract base — finalGrade(), abstract overall()
├── Unit_Course.java        # Coursework unit — 4 assignments + exam
├── Unit_Research.java      # Research unit — proposal + dissertation
├── student.csv             # Input data file
└── README.md
```

---

## CSV Input Format

Place `student.csv` in the same folder as the compiled classes.

**Coursework student** — 11 fields:
```
C,FirstName,LastName,StudentID,UnitID,Level,A1,A2,A3,A4,Exam
C,John,Smith,12345678,ICT167,1,85.5,78.0,92.5,88.0,76.5
```

**Research student** — 6 fields:
```
R,FirstName,LastName,StudentID,Proposal,Dissertation
R,Jane,Doe,87654321,82.0,89.5
```

Lines starting with `#` are treated as comments and skipped. Duplicate student IDs are also skipped automatically.

---

## Grade Calculation

**Coursework (Unit_Course):**
```
Overall = (A1 + A2 + A3 + A4) × 15% + Exam × 40%
```

**Research (Unit_Research):**
```
Overall = Proposal × 40% + Dissertation × 60%
```

**Grade scale:**

| Grade | Mark Range |
|---|---|
| HD — High Distinction | ≥ 80 |
| D — Distinction | ≥ 70 |
| C — Credit | ≥ 60 |
| P — Pass | ≥ 50 |
| N — Fail | < 50 |

---

## How to Compile and Run

**Compile all files:**
```bash
javac Student.java Unit.java Student_Course.java Student_Research.java Unit_Course.java Unit_Research.java Client.java
```

**Run:**
```bash
java Client
```

Make sure `student.csv` is in the same directory before running.

---

## Menu Options

```
1. Quit                        Exit the program
2. Remove student by ID        Delete a record (asks for confirmation)
3. Output all details          Display full info for every student
4. Coursework >= / < average   Count how many are above and below the average mark
5. Report grade by ID          Show grade for one student (uses polymorphism)
6. Sort by student ID          Insertion sort — ascending order
7. Export sorted list to CSV   Save to sorted_students.csv (must sort first)
```

---

## Sample Output

```
Loaded OK=12, Duplicates=0, Skipped=0.

C | John Smith  | ID: 12345678 | Unit: ICT167 | Overall: 82.33 | Final Grade: HD
R | Jane Doe    | ID: 87654321 | Overall: 86.60 | Final Grade: HD
```

---

## OOP Concepts Demonstrated

| Concept | How It Is Applied |
|---|---|
| Inheritance | `Student_Course` and `Student_Research` extend `Student`; `Unit_Course` and `Unit_Research` extend abstract `Unit` |
| Polymorphism | `reportGrade()` behaves differently per student type — resolved at runtime |
| Abstract Classes | `Unit` declares abstract `overall()` — each subclass must implement it |
| Dynamic Binding | `finalGrade()` in `Unit` calls `overall()`, which resolves to the correct subclass version at runtime |
| Encapsulation | All fields are private, accessed only through getters and setters |
| ArrayList | Stores mixed student types using the parent `Student` reference |
| Insertion Sort | Manually implemented — no Java library sort methods used |
| File I/O | CSV reading with `BufferedReader`; writing with `PrintWriter` |

---

## Author

**Bibek Chaudhary**
📧 bibekchaudhary6362@gmail.com
🐙 [github.com/bibekchaudhary6362-hash](https://github.com/bibekchaudhary6362-hash)

---

*Built in Java · Shared for portfolio and educational purposes*
