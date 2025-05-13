/**
 * The Student class represents a student, extending the Person class.
 * It also includes information about the students lunch preference.
 *
 * @author bmiller38
 */
public class Student extends Person{
    private String lunchPreference; //instance variable to store the students lunch preference

    /**
     * Constructor to create a new Student with name and lunch preference
     * @param name the name of the student
     * @param lunchPreference the lunch preference of the student
     */
    public Student(String name, String lunchPreference){
        super(name);
        this.lunchPreference = lunchPreference;
    }

    /**
     * Get the lunch preference of the student
     * @return <code>String</code> representation of lunch preference
     */
    public String getLunchPreference() {
        return lunchPreference;
    }

    /**
     * Returns a string representation of the student in a formatted string
     * @return <code>String</code> representation of the student with name and lunch preference
     */
    @Override
    public String toString() {
        return "I am " + getName() + ", who prefers " + getLunchPreference() + " lunch.";
    }
}
