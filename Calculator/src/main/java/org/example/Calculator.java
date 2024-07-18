package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class Calculator {

    public double executeOperation(Operation operation, double a, double b) {
        double result = operation.calculate(a, b);
        if (result == (int) (result)) {
            System.out.println("Result --> " + (int) (result));
            return result;
        }
        System.out.println("=" + result);
        return result;
    }


    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-hibernate-postgresql");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CalculatorHistory calculatorHistory = new CalculatorHistory();
        calculatorHistory.setFirstOperator(2);
        calculatorHistory.setSign("+");
        calculatorHistory.setSecondOperator(2);
        calculatorHistory.setResult(4);
        entityManager.getTransaction().begin();
        entityManager.persist(calculatorHistory);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();


        enum OperatorSign {
            SUM("+"),
            SUB("-"),
            MUL("*"),
            DIV("/"),
            POW("p");

            private final String sign;

            OperatorSign(String sign) {
                this.sign = sign;
            }

            public static OperatorSign getSign(String sign) throws InvalidOperationException {
                for (OperatorSign op : OperatorSign.values()) {
                    if (sign.equals(op.sign)) {
                        return op;
                    }
                }
                throw new InvalidOperationException("Error --> Invalid sign!");
//                return SUM
            }
        }
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        String operator;
        String sign;
        System.out.println("------------Controls------------\n" + "1. c -- > resets calculator" + "\n" + "2. exit --> ends the program" + "\n" + "--------------------------------");
        menu:
        while (true) {
            System.out.print("Number1 --> ");
            operator = scanner.nextLine();
            if (operator.equals("c")) {
                continue;
            } else if (operator.equals("exit")) {
                break menu;
            }
            double a;
            try {
                a = Double.parseDouble(operator);
            } catch (NumberFormatException e) {
                System.out.println("Error --> Invalid number");
                continue menu;
            }


            while (true) {
                System.out.print("Sign -->  ");
                sign = scanner.nextLine();
                if (sign.equals("c")) {
                    break;
                } else if (sign.equals("exit")) {
                    break menu;
                }

                OperatorSign result = OperatorSign.SUM;
                try {
                    result = OperatorSign.getSign(sign);
                } catch (InvalidOperationException e) {
                    System.out.println("Error --> Invalid sign");
                    continue menu;
                }

                System.out.print("Number 2 -->  ");
                operator = scanner.nextLine();
                System.out.println("--------------------------------");
                if (operator.equals("c")) {
                    break;
                } else if (operator.equals("exit")) {
                    break menu;
                }

                double b;
                try {
                    b = Double.parseDouble(operator);
                } catch (NumberFormatException e) {
                    System.out.println("Error --> Invalid number");
                    continue menu;
                }

                switch (result) {
                    case SUM -> {
                        OperationFactory additionFactory = new AdditionFactory();
                        Operation additionOperation = additionFactory.createOperation();
                        a = calculator.executeOperation(additionOperation, a, b);
                    }
                    case SUB -> {
                        OperationFactory subtractionFactory = new SubtractionFactory();
                        Operation subtractionOperation = subtractionFactory.createOperation();
                        a = calculator.executeOperation(subtractionOperation, a, b);
                    }
                    case MUL -> {
                        OperationFactory multiplicationFactory = new MultiplicationFactory();
                        Operation multiplicationOperation = multiplicationFactory.createOperation();
                        a = calculator.executeOperation(multiplicationOperation, a, b);
                    }
                    case DIV -> {
                        if (b == 0) {
                            System.out.println("Error");
                            continue menu;
                        }
                        OperationFactory divisionFactory = new DivisionFactory();
                        Operation divisionOperation = divisionFactory.createOperation();
                        a = calculator.executeOperation(divisionOperation, a, b);
                    }
                    case POW -> {
                        OperationFactory powerFactory = new PowerFactory();
                        Operation powerOperation = powerFactory.createOperation();
                        a = calculator.executeOperation(powerOperation, a, b);
                    }
                    default -> {
                        System.out.println("Error");
                        continue menu;
                    }
                }
            }
        }
    }
}

