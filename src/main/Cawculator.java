package main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Stack;

public class Cawculator {

    public static String expressionToRPN(String expression) {
        StringBuilder rpnResult = new StringBuilder();
        Stack<Character> operationStack = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            int priority = getPriority(expression.charAt(i));

            if (priority == 0) {
                rpnResult.append(expression.charAt(i));
            }
            if (priority == 1) {
                operationStack.push(expression.charAt(i));
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
                operationStack.push(expression.charAt(i));
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
//                    case '^':
                }

            }


        }

        return resultStuck.pop();
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
//            case '^':
//                return 4;
            default:
                return 0;
        }
    }

}
