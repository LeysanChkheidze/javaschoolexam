package com.tsystems.javaschool.tasks.calculator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    final static String ROUNDING_NEEDED_REGEX = "\\d*\\.\\d*";
    final static Pattern ROUNDING_NEEDED_PATTERN = Pattern.compile(ROUNDING_NEEDED_REGEX);
    final static String ALLOWED_SYMBOLS_REGEX = "[\\d().+\\-/*]+";
    final static Pattern ALLOWED_SYMBOLS_PATTERN = Pattern.compile(ALLOWED_SYMBOLS_REGEX);
    final static int DECIMAL_DIGITS = 4;

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        if (!validateNotEmpty(statement) ||
                !validateStatementSymbols(statement) ||
                !validateClosedBraces(statement) ||
                !validateNoRepeated(statement)) {
            return null;
        }

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        try {
            String result = engine.eval(statement).toString();
            if ("Infinity".equals(result)) {
                return null;
            }
            if (isRoundingNeeded(result)) {
                return round(result);
            }
            return result;
        } catch (ScriptException e) {
            return null;
        }
    }


    private boolean validateStatementSymbols(String statement) {
        Matcher matcherSymbols = ALLOWED_SYMBOLS_PATTERN.matcher(statement);
        return matcherSymbols.matches();
    }

    private boolean validateClosedBraces(String statement) {
        int numOfNotClosed = 0;
        for (int i = 0; i < statement.length(); i++) {
            if (statement.charAt(i) == '(') {
                numOfNotClosed++;
            } else if (statement.charAt(i) == ')') {
                numOfNotClosed--;
            }
        }
        return numOfNotClosed == 0;
    }

    private boolean validateNotEmpty(String statement) {
        return null != statement && statement.length() != 0;
    }

    private boolean validateNoRepeated(String statement) {
        Set<Character> symbols = new HashSet<>(Arrays.asList('+', '-', '/', '*', '.'));
        for (int i = 0; i < statement.length() - 1; i++) {
            if (symbols.contains(statement.charAt(i))) {
                if (statement.charAt(i) == statement.charAt(i + 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isRoundingNeeded(String numberString) {
        Matcher matcherRounding = ROUNDING_NEEDED_PATTERN.matcher(numberString);
        return matcherRounding.matches();
    }

    private String round(String numberString) {
        BigDecimal number = BigDecimal.valueOf(Double.parseDouble(numberString));
        number = number.setScale(DECIMAL_DIGITS, RoundingMode.HALF_UP);

        double rounded = number.doubleValue();
        return String.valueOf(rounded);
    }

}
