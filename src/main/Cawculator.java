package main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Stack;

public class Cawculator {

    public static String expressionToRPN(String expression) {
        String extendedExpression = extendingExpression(expression);
        StringBuilder rpnResult = new StringBuilder();
        Stack<Character> operationStack = new Stack<>();
        for (int i = 0; i < extendedExpression.length(); i++) {
            int priority = getPriority(extendedExpression.charAt(i));

            if (priority == 0) {
                rpnResult.append(extendedExpression.charAt(i));
            }
            if (priority == 1) {
                operationStack.push(extendedExpression.charAt(i));
            }
            if (priority > 1) {
                rpnResult.append(" ");

                while (!operationStack.empty()) {
                    if (getPriority(operationStack.peek()) >= priority) {
                        rpnResult.append(operationStack.pop());
                    } else {
                        break;
                    }
                }
                operationStack.push(extendedExpression.charAt(i));
            }
            if (priority == -1) {
                rpnResult.append(" ");
                while (getPriority(operationStack.peek()) != 1) {
                    rpnResult.append(operationStack.pop());
                }
                operationStack.pop();
            }
        }
        while (!operationStack.empty()) {
            rpnResult.append(operationStack.pop());
        }
        return rpnResult.toString();
    }

    public static double rpnToAnswer(String rpn) {
        StringBuilder operand = new StringBuilder();

        Stack<Double> resultStuck = new Stack<>();


        for (int i = 0; i < rpn.length(); i++) {

            if (rpn.charAt(i) == ' ') {
                continue;
            }
            if (getPriority(rpn.charAt(i)) == 0) {
                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
                    operand.append(rpn.charAt(i++));
                    if(i == rpn.length()) {
                        break;
                    }
                }
                resultStuck.push(Double.parseDouble(operand.toString()));
                operand = new StringBuilder();
            }
            if (getPriority(rpn.charAt(i)) > 1) {
                double a = resultStuck.pop();
                double b = resultStuck.pop();

                switch (rpn.charAt(i)) {
                    case '+':
                        resultStuck.push(a + b);
                        break;
                    case '*':
                        resultStuck.push(a * b);
                        break;
                    case '/':
                        resultStuck.push(a / b);
                        break;
                    case '-':
                        resultStuck.push(a - b);
                        break;
                }

            }


        }

        return resultStuck.pop();
    }

    private static String extendingExpression (String expression) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '-') {
                if (i == 0) {
                    result.append('0');
                } else if ( expression.charAt(i - 1) == '(') {
                    result.append('0');
                }
            }
            result.append(expression.charAt(i));
        }
        return result.toString();
    }

    private static int getPriority(Character character) {
        switch (character) {
            case ')':
                return -1;
            case '(':
                return 1;
            case '-':
            case '+':
                return 2;
            case '*':
            case '/':
                return 3;
            default:
                return 0;
        }
    }

}
