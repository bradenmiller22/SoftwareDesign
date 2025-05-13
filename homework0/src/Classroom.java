import java.util.ArrayList; //utilize arrays for the student
/**
 * The Classroom class is a representation of a classroom using a Teacher, students, and a grade level for the class.
 * It provides methods for getting the information of students and the classroom, and a way to do roll call.
 *
 * @author bmiller38
 */
public class Classroom {
    private Teacher teacher; //instance of Teacher to store the classes teacher
    private ArrayList<Student> students; //instance of student to store students
    private int gradeLevel; //instance variable for grade level of class

    /**
     * Construct a new classroom with the specified teacher and grade level
     * @param teacher the teacher for the class
     * @param gradeLevel the grade level for the class
     */
    public Classroom(Teacher teacher, int gradeLevel){
        this.teacher = teacher;
        this.gradeLevel = gradeLevel;
        this.students = new ArrayList<>(); //create a new arrayList
    }

    /**
     * Get the grade level of the classroom
     * @return <code>integer</code> representation of the grade level
     */
    public int getGradeLevel() {
        return gradeLevel;
    }

    /**
     * Generates a roll call of all students in the classroom
     * @return <code>String</code> containing all the students in roll call
     */
    public String rollCall(){
        String roll = ""; //empty string
        for(Student individual : students){ //go through all students
            roll += individual.toString()+ " "; //add students together by utilizing the toString of Student
        }
        return roll;
    }

    /**
     * Generates a lunch report showing the amount of students who prefer cold vs hot lunch
     * @return <code>String</code> Representation of cold vs hot count
     */
    public String generateLunchReport(){
        int hotLunch = 0;
        int coldLunch = 0;
        for(Student individual : students){//go through all students
            if(individual.getLunchPreference().equals("Hot")){ //if the lunch preference is Hot lunch
                hotLunch++;
            } else{//otherwise cold lunch
                coldLunch++;
            }
        }
        return "Hot lunch count: " + hotLunch + ", Cold lunch count: " + coldLunch; //formatted string to return
    }

    /**
     * Get the teachers first initial and grade level taught
     * @return <code>String</code> representation of the initial and grade
     */
    public String getId(){
        return teacher.getName().charAt(0) + String.valueOf(gradeLevel);
    }

    /**
     * Add a student to the classroom
     * @param individual the student to add
     */
    public void addStudent(Student individual){
        students.add(individual);
    }

    /**
     * Returns a string representation of the classroom.
     * Contains grade level, who it is taught by and how many students there are
     * @return <code>String</code> formatted into a sentence
     */
    public String toString() {
        return "This classroom has grade " + getGradeLevel() + " students. It is taught by " + teacher.getName() + ". There are " + students.size() + " students.";
    }
}
