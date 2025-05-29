##### [Home](Home)
### Problem Statement
Implement a classroom using inheritance and composition. Doing so by creating a class for a person with two subclasses of a teacher and student. Then using these 3 classes create a classroom that can have a teacher with students and create methods to get a roll call, a lunch report, and class information. Finally creating a driver class to test all of these classes.

### Developer Documentation
For this program there are 4 main classes along with a driver class. Here is an overview of the classes along with some main methods/variables.

1. Person Class: This is the base class that provides a person with a name.
- Variable
   - name (string): stores the name of a person
- Main Methods
   - Constructor to initialize name
   - toString: to convert name to a string

2. Teacher Class: This class extends Person and creates a teacher.
- Variables
   - subject (string): stores the subject the teacher teaches
   - university (string): stores the teachers university
   - degree (string): stores the degree the teacher has
   - id (int): unique id for teacher
   - nextId (int): static to track next id for teachers
- Main Methods
   - Constructor to initialize teacher attributes and increment id
   - toString: to return a formatted string of the teachers details 

3. Student Class: This class extends Person and creates a student.
- Variables
   - lunchPreference (string): stores the students lunch preference
- Main Methods
   - Constructor to initialize student attributes
   - toString: to return a formatted string of students details

4. Classroom Class: Represents a classroom with a teacher, students, and grade level
- Variables
   - teacher (Teacher): teacher for the classroom
   - students (ArrayList<Student>): a list of students in classroom
   - gradeLevel (int): grade level for the classroom
- Main Methods
   - Constructor to initialize the classroom with a teacher and grade level 
   - rollCall: to return a string with all the students and lunch preferences for the class
   - generateLunchReport: to get a count of hot lunches and cold lunches in the classroom
   - getId: to get a unique class code of the teachers first initial and id
   - addStudent(Student student): to add a student to the classroom
   - toString: to have a formatted string describing the class

Then a driver class in order to show functionality of all other classes inside of the program.

#### UML Diagram
![uml_diagram](https://class-git.engineering.uiowa.edu/swd2025spring/bmiller38_swd/-/raw/main/homework0/HW0_ElementarySchool/doc/umlhw0.png?ref_type=heads)
#### Java Docs
[View Java Docs](http://localhost:8000/bmiller38_swd)

### User Documentation
Here are the steps to use/run the program,

1. Open the Driver.java class to begin
2. Observe the objects created and how they interact with each other inside of the Driver class
3. Compile and run the program

### Source Code
[View code](https://class-git.engineering.uiowa.edu/swd2025spring/bmiller38_swd/-/tree/main/homework0/HW0_ElementarySchool/src?ref_type=heads)
