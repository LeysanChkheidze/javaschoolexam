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
    final static String DECIMAL_ZERO_REGEX = "\\d*\\.0*";
    final static Pattern DECIMAL_ZERO_PATTERN = Pattern.compile(DECIMAL_ZERO_REGEX);
    final static String ALLOWED_SYMBOLS_REGEX = "[\\d\\s().+\\-/*]+";
    final static Pattern ALLOWED_SYMBOLS_PATTERN = Pattern.compile(ALLOWED_SYMBOLS_REGEX);
    final static int DECIMAL_DIGITS = 4;
    final static Set<Character> UNREPEATABLE_SYMBOLS = new HashSet<>(Arrays.asList('+', '-', '/', '*', '.'));

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        //validate if input isn't empty, contains only valid symbols,
        //all braces are closed and arithmetical signs aren't duplicated:
        if (!validateNotEmpty(statement) ||
                !validateStatementSymbols(statement) ||
                !validateClosedBraces(statement) ||
                !validateNoRepeated(statement)) {
            return null;
        }

        // create JS engine to execute the script
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        try {
            String result = engine.eval(statement).toString();
            // JS returns Infinity when dividing to 0, replace it with null:
            if ("Infinity".equals(result)) {
                return null;
            }
            // round to DECIMAL_DIGITS of allowed meaningful decimal digits:
            if (isRoundingNeeded(result)) {
                return round(result);
            }
            return result;
        } catch (ScriptException e) {
            //will be thrown if the script is unexecutable:
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
        for (int i = 0; i < statement.length() - 1; i++) {
            if (UNREPEATABLE_SYMBOLS.contains(statement.charAt(i))) {
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

        String result = String.valueOf(rounded);

        //Check if rounded number has meaningless decimal part .0 and remove it:
        Matcher matcherDecimalZero = DECIMAL_ZERO_PATTERN.matcher(result);
        if (matcherDecimalZero.matches()) {
            result = removeHangingZero(result);
        }
        return result;
    }

    private String removeHangingZero(String numberString) {
        return numberString.substring(0, numberString.indexOf("."));
    }

}
