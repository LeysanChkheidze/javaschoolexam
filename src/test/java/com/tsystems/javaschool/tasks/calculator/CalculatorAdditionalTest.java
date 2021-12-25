package com.tsystems.javaschool.tasks.calculator;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorAdditionalTest {
    private Calculator calc = new Calculator();

    @Test
    public void evaluate() {
        //given
        String input = "23+";
        String expectedResult = null;

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void evaluateDecimals() {
        //given
        String input = "4/3";
        String expectedResult = "1.3333";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void evaluateDecimals2() {
        //given
        String input = "8/3";
        String expectedResult = "2.6667";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }


    @Test
    public void evaluateRoundToSignificant() {
        //given
        String input = "1.1230";
        String expectedResult = "1.123";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void evaluateRoundingBoundary1() {
        //given
        String input = "100004/100000";
        String expectedResult = "1";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void evaluateRoundingBoundary2() {
        //given
        String input = "100005/100000";
        String expectedResult = "1.0001";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void evaluateInvalidSymbols() {
        //given
        String input = "a+1";
        String expectedResult = null;

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void evaluateInvalidSymbols1() {
        //given
        String input = "8|2";
        String expectedResult = null;

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void evaluateInvalidSymbols2() {
        //given
        String input = "8&2";
        String expectedResult = null;

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void evaluateSpaces() {
        //given
        String input = " (1 + 38) * 4.5 - 1 / 2 ";
        String expectedResult = "175";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void evaluateHangingZero() {
        //given
        String input = "1.00";
        String expectedResult = "1";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }


}
