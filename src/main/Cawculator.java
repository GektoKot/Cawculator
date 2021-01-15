package main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Stack;

public class Cawculator {

    public static String expressionToRPN(String expression) {
        String extendedExpression = extendingExpression(expression);
//        StringBuilder rpnResult = new StringBuilder();
        String rpnResult = "";
        Stack<Character> operationStack = new Stack<>();
        for (int i = 0; i < extendedExpression.length(); i++) {
            int priority = getPriority(extendedExpression.charAt(i));

            if (priority == 0) {
//                rpnResult.append(extendedExpression.charAt(i));
                rpnResult += extendedExpression.charAt(i);
                continue;
            }
            if (priority == 1) {
                operationStack.push(extendedExpression.charAt(i));
                continue;
            }
            if (priority > 1) {
//                rpnResult.append(" ");
                rpnResult += " ";

                while (!operationStack.empty()) {
                    if (getPriority(operationStack.peek()) >= priority) {
//                        rpnResult.append(operationStack.pop());
                        rpnResult += operationStack.pop();
                    } else {
                        break;
                    }
                }
                operationStack.push(extendedExpression.charAt(i));
                continue;
            }
            if (priority == -1) {
//                rpnResult.append(" ");
                rpnResult += " ";
                while (getPriority(operationStack.peek()) != 1) {
//                    rpnResult.append(operationStack.pop());
                    rpnResult += operationStack.pop();
                }
                operationStack.pop();
            }
        }
        while (!operationStack.empty()) {
//            rpnResult.append(operationStack.pop());
            rpnResult += operationStack.pop();
        }
        return rpnResult;
//        return rpnResult.toString();
    }

    public static double rpnToAnswer(String rpn) {
//        StringBuilder operand = new StringBuilder();
        String operand = "";

        Stack<Double> resultStack = new Stack<>();


        for (int i = 0; i < rpn.length(); i++) {

            if (rpn.charAt(i) == ' ') {
                continue;
            }
            if (getPriority(rpn.charAt(i)) == 0) {
                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
//                    operand.append(rpn.charAt(i++));
                    operand += rpn.charAt(i++);
                    if(i == rpn.length()) {
                        break;
                    }
                }
//                resultStack.push(Double.parseDouble(operand.toString()));
                resultStack.push(Double.parseDouble(operand));
//                operand = new StringBuilder();
                operand = "";
            }
            if (getPriority(rpn.charAt(i)) > 1) {
                double b = resultStack.pop();
                double a = resultStack.pop();

                switch (rpn.charAt(i)) {
                    case '+':
                        resultStack.push(a + b);
                        break;
                    case '*':
                        resultStack.push(a * b);
                        break;
                    case '/':
                        resultStack.push(a / b);
                        break;
                    case '-':
                        resultStack.push(a - b);
                        break;
                    case '^':
                        double res = a;
                        for (int j = 1; j < b; j++) {
                            res *= a;
                        }
                        resultStack.push(res);
                        break;
                }

            }


        }

        return resultStack.pop();
    }

    private static String extendingExpression (String expression) {
//        StringBuilder result = new StringBuilder();
        String result = "";
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '-') {
                if (i == 0) {
//                    result.append('0');
                    result += '0';
                } else if ( expression.charAt(i - 1) == '(') {
//                    result.append('0');
                    result += '0';
                }
            }
//            result.append(expression.charAt(i));
            result += expression.charAt(i);
        }
//        return result.toString();
        return result;
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
            case '^':
                return 4;
            default:
                return 0;
        }
    }

}
