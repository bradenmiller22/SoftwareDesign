import static org.junit.jupiter.api.Assertions.*;//import all assertion methods for test validation
import org.junit.jupiter.api.Test;//import test annotation for test methods
import org.junit.jupiter.params.ParameterizedTest;//import for parameterized tests
import org.junit.jupiter.params.provider.ValueSource;//import for value sources in parameterized tests
import org.junit.jupiter.params.provider.CsvSource;//import fo CSV sources in parameterized tests

/**
 * Test class for Check Writer
 * Has tests for validation, formatting, and conversion to words
 * @author bmiller38
 */
public class CheckTest {
    /**
     * Tests validation of amounts without commas
     * All inputs should be valid and not throw exceptions
     * @param input The string representation of the amount to test
     */
    @ParameterizedTest//shows this is a parameterized test
    @ValueSource(strings = {"123.45", "12.34", "999.89"})//test values for valid amounts (no commas)
    void testNoComma(String input){
        assertDoesNotThrow(() -> Check.validateAmount(input));//verify no exception is thrown
    }

    /**
     * Tests validation of amounts with commas
     * All inputs should be valid and not throw exceptions
     * @param input String representation of the amount to test
     */
    @ParameterizedTest //parameterized test
    @ValueSource(strings = {"12,345.45", "1,234.56"})//test values for valid amounts with commas
    void testWithComma(String input){
        assertDoesNotThrow(() -> Check.validateAmount(input));//verify no exception is thrown
    }

    /**
     * Test invalid formats for the decimal point
     * All inputs should be invalid and throw exceptions
     * @param input String representation of the amount to test
     */
    @ParameterizedTest
    @ValueSource(strings = {"12.345", "1.2", ".3", "123", "12"})//test values for invalid amounts (no commas)
    void testInvalidDecimal(String input){
        assertThrows(IllegalArgumentException.class, () -> Check.validateAmount(input));//check that an exception was thrown
    }

    /**
     * Test invalid formats with the comma
     * ALl inputs should be invalid and throw exceptions
     * @param input String representation of the amount to test
     */
    @ParameterizedTest
    @ValueSource(strings = {"12,34.56", ",23.45", "1234,12.78", "12,.34","1234,3.34","1,1,1.23"})//test values for invalid amounts with commas
    void testInvalidCommas(String input){
        assertThrows(IllegalArgumentException.class, () -> Check.validateAmount(input));//check that an exception was thrown
    }

    /**
     * Tests the Protected Amount formatting
     * All tests should equal what is expected on the amount
     */
    @Test
    void testProtectedAmount(){
        assertEquals("*****1.33", Check.protectedAmount(1.33));//smallest amount
        assertEquals("****12.34", Check.protectedAmount(12.34));//two digit amount
        assertEquals("***123.45", Check.protectedAmount(123.45));//three digit amount
        assertEquals("*1,222.34", Check.protectedAmount(1222.34));//four digit amount
        assertEquals("12,122.34", Check.protectedAmount(12122.34));//five digit full amount
        assertThrows(IllegalArgumentException.class, () -> Check.protectedAmount(1231231.89));//check that max amount if greater than will throw an exception
    }

    /**
     * Tests the conversion from an amount to the word conversion
     * Each test case provides an amount and the word representation that should be shown
     * @param num The amount to convert
     * @param expected the expected amount in word representation
     */
    @ParameterizedTest
    //test data pairs: amount value, expected word representation
    @CsvSource({"0, ZERO and 0/100", "1.89, ONE and 89/100","5.34, FIVE and 34/100", "12.10, TWELVE and 10/100", "13.56, THIRTEEN and 56/100", "30.89, THIRTY and 89/100", "32.99, THIRTY TWO and 99/100", "65.00, SIXTY FIVE and 0/100", "100.07, ONE hundred and 7/100", "348.54, THREE hundred FORTY EIGHT and 54/100", "999.99, NINE hundred NINETY NINE and 99/100"})
    void testNumberWords(double num, String expected){
        assertEquals(expected, Check.convertWords(num));//verify all conversion match what they should be
    }

    /**
     * Tests large amounts with valid commas and decimal points
     * All values should be valid and not throw an exception
     * @param input valid amounts to be checked
     */
    @ParameterizedTest
    @ValueSource(strings = {"1,234.00", "12,345.00", "23,678.00"})//valid large amounts greater than 1000
    void testValidComma(String input){
        assertDoesNotThrow(() -> Check.validateAmount(input));//shouldnt throw any errors
    }
}
