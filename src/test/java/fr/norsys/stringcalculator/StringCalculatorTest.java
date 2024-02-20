package fr.norsys.stringcalculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringCalculatorTest {

    private StringCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new StringCalculator();
    }

    @Test
    void shouldReturnZeroIfEmptyString() {
        Assertions.assertEquals(0, calculator.add(""));
    }

    @Test
    void shouldReturnTheSameNumberIfItIsOne() {
        int value = 5;
        String stringValue = "5";
        Assertions.assertEquals(value, calculator.add(stringValue));
    }

    @Test
    void shouldReturnTheSomeIfTwoNumbers() {
        int expected = 8;
        String stringValue = "5,3";
        Assertions.assertEquals(expected, calculator.add(stringValue));
    }

    @Test
    void shouldReturnTheSomeIfMoreThanTwoNumbers() {
        int expected = 14;
        String stringValue = "5,3,1,1,4";
        Assertions.assertEquals(expected, calculator.add(stringValue));
    }

    @Test
    void shouldHandleNewLinesAsSeparator() {
        int expected = 14;
        String stringValue = "5\n3,1,1\n4";
        Assertions.assertEquals(expected, calculator.add(stringValue));
    }

    @Test
    void shouldSupportDifferentDelimiters() {
        int expected = 14;
        String stringValue = "//;\n5;3;1;1;4";
        Assertions.assertEquals(expected, calculator.add(stringValue));
    }

    @Test
    void shouldThrowsExceptionIfANumberIsNegative() {
        String stringValue = "5,-3,1,1,4";
        Exception exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> calculator.add(stringValue)
                );
        String expectedMessage = "negatives not allowed [-3]";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldThrowsExceptionWithAllNegativeIfANumberIsNegative() {
        String stringValue = "5,-3,1,-1,-4";
        Exception exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> calculator.add(stringValue)
        );
        String expectedMessage = "negatives not allowed [-3, -1, -4]";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldIgnoreNumbersGreaterThan1000() {
        int expectedValue = 16;
        String stringValue = "5,2000,10,1000,1";
        Assertions.assertEquals(expectedValue, calculator.add(stringValue));
    }

    @Test
    void shouldHandleDelimitersOfAnyLength() {
        int expectedValue = 16;
        String stringValue = "//[***]\n5***2000***10***1000***1";
        Assertions.assertEquals(expectedValue, calculator.add(stringValue));
    }

    @Test
    void shouldHandleMultipleDelimiters() {
        int expectedValue = 16;
        String stringValue = "//[*][%]\n5*2000%10*1000%1";
        Assertions.assertEquals(expectedValue, calculator.add(stringValue));
    }

    @Test
    void shouldHandleMultipleDelimitersOfAnyLength() {
        int expectedValue = 16;
        String stringValue = "//[***][%*]\n5***2000%*10***1000%*1";
        Assertions.assertEquals(expectedValue, calculator.add(stringValue));
    }
}
