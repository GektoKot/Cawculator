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
            //вывод ответа на основе ОПН(Обратная Польская Нотация). Что бы понять магию проваливаемся в метод
            System.out.println("Answer is: " + expression + "=" + Cawculator.cawculateIt(expression));
            //предложение ввести еще выражение
            System.out.print("Write yours expression: ");
            //ввод нового выражения
            expression = reader.readLine();
        }
        System.out.println("CaW-cAw!");
        reader.close();
    }
}
