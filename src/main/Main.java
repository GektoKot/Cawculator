package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        //начало программы. Заводим Баффэрэдридер для чтения из консоли
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //приветствие
        System.out.println("Welcome to CAWculator!");
        System.out.println("For exit write \"CAW\"");
        System.out.print("Write yours expression: ");

        //первичный ввод из консоли
        String expression = reader.readLine();
        //вечный цикл с условием для остановки по желанию
        while (!expression.equalsIgnoreCase("caw")) {
            //перевод введеного выражения в обратную польскую нотацию(ОПН) (что бы понять магию нужно провалится в метод expressionToRPN())
            String rpnExpression = Cawculator.expressionToRPN(expression);
            //чисто для самопроверки. выводим строку в виде ОПН
            System.out.println("RPN expression is: " + rpnExpression);
            //вывод ответа на основе ОПН. так же что бы понять магию проваливаемся в метод
            double d = Cawculator.rpnToAnswer(rpnExpression);
            System.out.println("Answer is: " + expression + "=" + d);
//            System.out.println("Answer is: " + expression + "=" + Cawculator.rpnToAnswer(rpnExpression));
            //предложение ввести еще выражение
            System.out.print("Write yours expression: ");
            //ввод нового выпажения
            expression = reader.readLine();
        }
        System.out.println("CaW-cAw!");
        reader.close();
    }
}
