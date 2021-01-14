package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("For exit write \"stop\"");
        System.out.println("Write yours expression: ");
        String expression = reader.readLine();
        while (!expression.equalsIgnoreCase("stop")) {
            String rpnExpression = Cawculator.expressionToRPN(expression);
            System.out.println("RPN expression is: " + rpnExpression);
            System.out.println("Answer is: " + expression + "=" + Cawculator.rpnToAnswer(rpnExpression));
            expression = reader.readLine();
        }
        reader.close();
    }
}
