package org.example;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class JavaTest {
    @Test
    public void twoPlusTwoShouldEqualFour() {
        var calculator = new Calculator();
        Operation addition = new Addition();
        assertEquals(4.0, calculator.executeOperation(addition, 2, 2));
    }

}


