/**
 * The driver class is the general part of the program that tests classes.
 * It shows the functionality of Teacher, Student, Person, and Classroom classes
 *
 * @author bmiller38
 */
public class Driver {
    public static void main(String[] args) {
        //create two teachers with specific parameters
        Teacher teacher1 = new Teacher("Reinhard Beichel", "Electrical Engineering", "PhD","Graz University");
        Teacher teacher2 = new Teacher("Ken Johnson", "Physics", "PhD", "University of Iowa");
        //create first classroom and add students to that class
        Classroom class1 = new Classroom(teacher1, 11);
        class1.addStudent(new Student("Braden Miller", "Cold"));
        class1.addStudent(new Student("Isaac Johnson", "Hot"));
        class1.addStudent(new Student("Ted Williams", "Hot"));
        //print the results to the terminal to see the class
        System.out.println("Classroom 1: " + class1);
        System.out.print("Roll Call: " + class1.rollCall());
        System.out.println("\nLunch Report: " + class1.generateLunchReport());
        System.out.println("Teacher id: " + teacher1.getId());
        //create the second classroom and add students
        Classroom class2 = new Classroom(teacher2, 2);
        class2.addStudent(new Student("Sage Blue", "Cold"));
        class2.addStudent(new Student("Jon Few", "Hot"));
        class2.addStudent(new Student("Ted Rand", "Cold"));
        class2.addStudent(new Student("Hannah Brase", "Hot"));
        //print the results to the terminal to see class 2
        System.out.println("\nClassroom 2: " + class2);
        System.out.print("Roll Call: " + class2.rollCall());
        System.out.println("\nLunch Report: " + class2.generateLunchReport());
        System.out.println("Teacher id: " + teacher2.getId());
        //verify what the next teachers ID would be if another teacher was added.
        System.out.println("Next teacher ID: " + Teacher.getNextId());
    }
}
