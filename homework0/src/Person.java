/**
 * The Person class is used to represent a person.
 * The class provides methods to retrieve a persons name and a string format.
 * The class has 2 subclasses that inherit from Person.
 *
 * @author bmiller38
*/
public class Person {
    private String name; //instance variable to store name of person

    /**
     * Constructor to create a new person with specified name
     * @param name the name of the person
     */
    public Person(String name){
        this.name = name;
    }

    /**
     * Gets the name
     * @return a <code>String</code> representation of name
     */
    public String getName() {
        return name;
    }

    /**
     * Convert to a string representation of person name
     *
     * @return a <code>String</code> representation of persons name
     */
    @Override
    public String toString() {
        return name;
    }
}
