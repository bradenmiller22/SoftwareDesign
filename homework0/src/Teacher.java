/**
 * The Teacher class represents a teacher which extends the Person class.
 * It has the teachers subject, degree, university, and id.
 *
 * @author bmiller38
 */
public class Teacher extends Person{
    private String subject;//instance variable to store teacher subject
    private String degree;//instance variable to store teachers degree
    private String university; //instance variable to store teachers university
    private int id; //instance variable to store the id of teacher
    private static int nextId; //static variable to generate unique ids

    /**
     * Constructor to create a new teacher with specified name, subject, degree, university
     * @param name the name of teacher
     * @param subject the subject taught by teacher
     * @param degree the degree held by teacher
     * @param university university where the teacher teaches at
     */
    public Teacher(String name, String subject, String degree,String university){
        super(name);//call to person constructor for name
        this.subject = subject;
        this.degree = degree;
        this.university = university;
        this.id = nextId++; //give teacher an id utilizing static variable nextId to keep a static count
    }

    /**
     *Get the subject taught by teacher
     * @return <code>String</code> representation of subject taught
     */
    public String getSubject() {
        return subject;
    }

    /**
     *Get the degree held by teacher
     * @return <code>String</code> representation of degree held
     */
    public String getDegree(){
        return degree;
    }

    /**
     * Get the university the teacher teaches at
     * @return <code>String</code> representation of university
     */
    public String getUniversity(){
        return university;
    }

    /**
     * Get the ID held by the teacher
     * @return <code>integer</code> representation of the id
     */
    public int getId() {
        return id;
    }

    /**
     * Get the nextId for the next teacher
     * @return <code>integer</code> representation of next id
     */
    public static int getNextId() { return nextId; }

    /**
     * Get a formatted string to show the teacher, what they teach, what degree they have and from where
     * @return <code>String</code> representation of the formatted sentence
     */
    @Override
    public String toString() {
        return "I am " + getName() + ", a teacher of " + getSubject() + ". I hold a " + getDegree() + " degree from " + getUniversity() + ", and my ID is " + getId() + ".";
    }
}
