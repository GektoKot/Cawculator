package main;
//все что нам надо для счастья
import java.util.Stack;

//Самый главный класс после Мэйна
public class Cawculator {
    //Обощающий метод
    public static double cawculateIt(String expression) {
        return rpnToAnswer(expressionToRPN(expression));
    }
    //Перевод нормального выражения в ОПН
    private static String expressionToRPN(String expression) {
        //перерабатываем выражение в более дружелюбное к унарным минусам
        String extendedExpression = extendingExpressionForUnaryMinus(expression);
        //стрингбилдер куда будем аппендить результаты проверок
        StringBuilder rpnResult = new StringBuilder();
        //стак для операций
        Stack<Character> operationStack = new Stack<>();
        //пробегаем циклом по всему выражению по каждому символу
        for (int i = 0; i < extendedExpression.length(); i++) {
            //вынесем отдельно переменную приоритета символа
            int priority = getPriority(extendedExpression.charAt(i));
            //если число, то просто добавляем символ к результату
            if (priority == 0) {
                rpnResult.append(extendedExpression.charAt(i));
                continue;
            }
            //если открытая скобка, то добавляем ее в стэк
            if (priority == 1) {
                operationStack.push(extendedExpression.charAt(i));
                continue;
            }
            //если какя-либо операция, то...
            if (priority > 1) {
                //добавляем пробел для разделения операндов
                rpnResult.append(" ");
                //в цикле пока не пустой стэк...
                while (!operationStack.empty()) {
                    //если приоритет последней операции в стэке больше или равно, то добавляем ее в результат
                    if (getPriority(operationStack.peek()) >= priority) {
                        rpnResult.append(operationStack.pop());
                        //в противном случае прерываем цикл
                    } else {
                        break;
                    }
                }
                //засовываем в стэк операцию
                operationStack.push(extendedExpression.charAt(i));
                continue;
            }
            //если нарвались на закрывающуюся скобку, то ...
            if (priority == -1) {
                //добавляем пробел для разделения операндов
                rpnResult.append(" ");
                //в цикле чекаем приоритет последней операции в стэки и если он не равен 1,
                //то добавляем к результату операцию из стэка
                while (getPriority(operationStack.peek()) != 1) {
                    rpnResult.append(operationStack.pop());
                }
                //в конце просто выкидываем скобку
                operationStack.pop();
            }
        }
        //пока стэк с операциями не опустеет забиваем все в результат
        while (!operationStack.empty()) {
            rpnResult.append(operationStack.pop());
        }
        //возвращаем готовенькую ОПН
        return rpnResult.toString();
    }
    //Рассчет на основе ОПН, возвращает ответ в виде дабл
    private static double rpnToAnswer(String rpn) {
        //новый стрингбилдер под операнд
        StringBuilder operand = new StringBuilder();
        //стэк с результатом
        Stack<Double> resultStack = new Stack<>();
        //цикл где бегаем по ОПН по каждому символу
        for (int i = 0; i < rpn.length(); i++) {
            //если пробел, то смело переходим к новой итерации
            if (rpn.charAt(i) == ' ') {
                continue;
            }
            //если число, то...
            if (getPriority(rpn.charAt(i)) == 0) {
                //создаем еще подцикл, который собирает в операнд число из ОПН
                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
                    operand.append(rpn.charAt(i++));
                    if (i == rpn.length()) {
                        break;
                    }
                }
                //засовываем операнд в стэк
                resultStack.push(Double.parseDouble(operand.toString()));
                //чистим переменную для нового значения
                operand = new StringBuilder();
            }
            //самая мякотка. если символ похож на операцию, то...
            if (getPriority(rpn.charAt(i)) > 1) {
                //выдергиваем из стэка операнды
                double b = resultStack.pop();
                double a = resultStack.pop();
                //производим операцию согласно символу и результат запихиваем обратно в стэк
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
    //Костыль для того что вернуть новое выражение в виде строки. Суть в добавлении доп нолей, которые в последстввии
    //помогут распознавать унарные минуса в выражении
    private static String extendingExpressionForUnaryMinus(String expression) {
        //создаем стрингбилдер
        StringBuilder result = new StringBuilder();
        //в цикле пробегаем по всему выражению по каждому символу
        for (int i = 0; i < expression.length(); i++) {
            //в случае нахождения минуса начинаем дополнительные проверки
            if (expression.charAt(i) == '-') {
                //если присутствуем минус в начале выражения, то смело добавляем ноль
                if (i == 0) {
                    result.append('0');
                    //если минус стоит после скобки, то так же, отринув сомнения, добавляем ноль
                } else if (expression.charAt(i - 1) == '(') {
                    result.append('0');
                }
            }
            //собираем строку в порядке очереди
            result.append(expression.charAt(i));
        }
        //возвращаем получившегося мутанта
        return result.toString();
    }
    //Возвращает значение приоритета присущее символам из выражения
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
