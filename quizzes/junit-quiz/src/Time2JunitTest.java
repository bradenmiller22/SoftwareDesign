import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Time2JunitTest {


    //Invalid Constructor Tests, Should All Throw Illegal Argument Exceptions

    @ParameterizedTest(name="run #{index} with [{arguments}]")
    @CsvSource({"-1,10,10", "24,10,10", "10,-1,10", "10,70,10", "10,50,-1","10,50,70"})
    void invalidConstructor3Arguments(int hour, int minute, int second){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Time2(hour, minute, second));
    }

    @ParameterizedTest(name="run #{index} with [{arguments}]")
    @CsvSource({"-1,10", "24,10", "10,-1", "10,70"})
    void invalidConstructor2Args(int hour, int minute){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Time2(hour, minute));
    }

    @ParameterizedTest(name="run #{index} with [{arguments}]")
    @CsvSource({"-1", "24"})
    void invalidConstructor1Arg(int hour){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Time2(hour));
    }


    // This also tests toUniversalString
    @Test
    void validConstructors(){
        Time2 t0 = new Time2();
        Time2 t1 = new Time2(1);
        Time2 t2 = new Time2(1,1);
        Time2 t3 = new Time2(1,1,1);

        assertEquals(t0.toUniversalString(), "00:00:00");
        assertEquals(t1.toUniversalString(), "01:00:00");
        assertEquals(t2.toUniversalString(), "01:01:00");
        assertEquals(t3.toUniversalString(), "01:01:01");
    }

    @Test
    void testToString(){
        Time2 t5 = new Time2();

        assertEquals(t5.toString(), "12:00:00 AM");
    }

    @Test
    void copyConstructor(){
        Time2 original = new Time2(1,2,3);
        Time2 copy = new Time2(original);

        //deep copy not shallow
        original.setHour(10);

        assertEquals(copy.toUniversalString(),"01:02:03");

    }

    @ParameterizedTest(name="run #{index} with [{arguments}]")
    @CsvSource({"-1", "24"})
    void invalidSetHour(int hour){
        Time2 t1 = new Time2();
        Assertions.assertThrows(IllegalArgumentException.class, () -> t1.setHour(hour));

    }

    @ParameterizedTest(name="run #{index} with [{arguments}]")
    @CsvSource({"-1", "60"})
    void invalidSetMinute(int minute){
        Time2 t1 = new Time2();
        Assertions.assertThrows(IllegalArgumentException.class, () -> t1.setMinute(minute));

    }

    @ParameterizedTest(name="run #{index} with [{arguments}]")
    @CsvSource({"-1", "60"})
    void invalidSetSecond(int second){
        Time2 t1 = new Time2();
        Assertions.assertThrows(IllegalArgumentException.class, () -> t1.setSecond(second));

    }

    @Test
    public void validSetters(){
        Time2 t1 = new Time2();
        assertEquals(t1.toUniversalString(), "00:00:00");

        t1.setHour(10);
        t1.setMinute(20);
        t1.setSecond(30);

        assertEquals(t1.toUniversalString(), "10:20:30");
    }

    @ParameterizedTest(name="run #{index} with [{arguments}]")
    @CsvSource({"0,0,0,12:00:00 AM", "12,00,00,12:00:00 PM", "15,00,00,3:00:00 PM"})
    void toString(int hour, int minute, int second, String correctTime){
        assertEquals(new Time2(hour, minute, second).toString(), correctTime);
    }
}
