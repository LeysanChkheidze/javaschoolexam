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


}
