package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.eclipse.persistence.internal.oxm.schema.model.Restriction;

import java.util.Objects;
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

    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-hibernate-postgresql");
    static EntityManager entityManager = entityManagerFactory.createEntityManager();
    static Scanner scanner = new Scanner(System.in);

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

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        String operator;
        String sign;

        System.out.println("""
                ------------Controls------------
                1. c -- > resets calculator
                2. exit --> ends the program
                3. a --> admin acces
                --------------------------------""");
        menu:
        while (true) {
            System.out.print("Number1 --> ");
            operator = scanner.nextLine();
            if (operator.equals("c")) {
                continue;
            } else if (operator.equals("exit")) {
                entityManager.close();
                entityManagerFactory.close();
                break menu;
            } else if (operator.equals("a")) {
                adminMenu();
                continue menu;
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
                    entityManager.close();
                    entityManagerFactory.close();
                    break menu;
                } else if (sign.equals("a")) {
                    adminMenu();
                    continue menu;
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
                    entityManager.close();
                    entityManagerFactory.close();
                    break menu;
                } else if (operator.equals("a")) {
                    adminMenu();
                    continue menu;
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
                        CalculatorHistory calculatorHistory = new CalculatorHistory();
                        calculatorHistory.setFirstOperator(a);
                        calculatorHistory.setSecondOperator(b);
                        calculatorHistory.setSign(result.sign);
                        a = calculator.executeOperation(additionOperation, a, b);
                        calculatorHistory.setResult(a);
                        entityManager.getTransaction().begin();
                        entityManager.persist(calculatorHistory);
                        entityManager.getTransaction().commit();
                    }
                    case SUB -> {
                        OperationFactory subtractionFactory = new SubtractionFactory();
                        Operation subtractionOperation = subtractionFactory.createOperation();
                        CalculatorHistory calculatorHistory = new CalculatorHistory();
                        calculatorHistory.setFirstOperator(a);
                        calculatorHistory.setSecondOperator(b);
                        calculatorHistory.setSign(result.sign);
                        a = calculator.executeOperation(subtractionOperation, a, b);
                        calculatorHistory.setResult(a);
                        entityManager.getTransaction().begin();
                        entityManager.persist(calculatorHistory);
                        entityManager.getTransaction().commit();
                    }
                    case MUL -> {
                        OperationFactory multiplicationFactory = new MultiplicationFactory();
                        Operation multiplicationOperation = multiplicationFactory.createOperation();
                        CalculatorHistory calculatorHistory = new CalculatorHistory();
                        calculatorHistory.setFirstOperator(a);
                        calculatorHistory.setSecondOperator(b);
                        calculatorHistory.setSign(result.sign);
                        a = calculator.executeOperation(multiplicationOperation, a, b);
                        calculatorHistory.setResult(a);
                        entityManager.getTransaction().begin();
                        entityManager.persist(calculatorHistory);
                        entityManager.getTransaction().commit();
                    }
                    case DIV -> {
                        if (b == 0) {
                            System.out.println("Error");
                            continue menu;
                        }
                        OperationFactory divisionFactory = new DivisionFactory();
                        Operation divisionOperation = divisionFactory.createOperation();
                        CalculatorHistory calculatorHistory = new CalculatorHistory();
                        calculatorHistory.setFirstOperator(a);
                        calculatorHistory.setSecondOperator(b);
                        calculatorHistory.setSign(result.sign);
                        a = calculator.executeOperation(divisionOperation, a, b);
                        calculatorHistory.setResult(a);
                        entityManager.getTransaction().begin();
                        entityManager.persist(calculatorHistory);
                        entityManager.getTransaction().commit();
                    }
                    case POW -> {
                        OperationFactory powerFactory = new PowerFactory();
                        Operation powerOperation = powerFactory.createOperation();
                        CalculatorHistory calculatorHistory = new CalculatorHistory();
                        calculatorHistory.setFirstOperator(a);
                        calculatorHistory.setSecondOperator(b);
                        calculatorHistory.setSign(result.sign);
                        a = calculator.executeOperation(powerOperation, a, b);
                        calculatorHistory.setResult(a);
                        entityManager.getTransaction().begin();
                        entityManager.persist(calculatorHistory);
                        entityManager.getTransaction().commit();
                    }
                    default -> {
                        System.out.println("Error");
                        continue menu;
                    }
                }
            }
        }
    }

    private static void adminMenu() {
        String operator;
        String adminPasswordVerifier;
        String adminPassword = "admin1234";
        boolean adminMenu = true;
        adminmenu:
        while (adminMenu) {
            System.out.print("Enter password --> ");
            adminPasswordVerifier = scanner.nextLine();
            if (adminPasswordVerifier.equals(adminPassword)) {
                activeMenu:
                while (true) {
                    System.out.println("""
                            --------------admin--------------
                            1. 'enter' --> acces database entity
                            2. exit --> close admin window
                            ---------------------------------""");

                    operator = scanner.nextLine();
                    if (operator.equals("")) {
                        System.out.print("Enter a number --> ");
                        operator = scanner.nextLine();
                        int number;
                        try {
                            number = Integer.parseInt(operator);
                        } catch (NumberFormatException e) {
                            System.out.println("Error --> invalid id number");
                            continue activeMenu;
                        }
                        CalculatorHistory calculatorHistoryadmin = entityManager.find(CalculatorHistory.class, number);
                        entityMenu:
                        while (true) {
                            System.out.println("""
                                    -----------Actions-----------
                                    1. modify entity
                                    2. show entyty
                                    3. exit
                                    -----------------------------""");
                            operator = scanner.nextLine();
                            if (operator.equals("exit")) {
                                continue activeMenu;
                            }
                            try {
                                number = Integer.parseInt(operator);
                            } catch (NumberFormatException e) {
                                System.out.println("Error --> invalid id number");
                                continue activeMenu;
                            }
                            if (number == 1) {
                                mofyfierMenu:
                                while (true) {
                                    System.out.println("""
                                            -----------modify-----------
                                            1. first operator
                                            2. second operator
                                            3. sign operator
                                            4. result operator
                                            5. date
                                            6. entire entity
                                            7. exit
                                            ----------------------------""");
                                    operator = scanner.nextLine();
                                    if (operator.equals("exit")) {
                                        continue entityMenu;
                                    }
                                    try {
                                        number = Integer.parseInt(operator);
                                    } catch (NumberFormatException e) {
                                        System.out.println("Error --> invalid id number");
                                        continue mofyfierMenu;
                                    }
                                    double doperator = 0;
                                    switch (number) {
                                        case 1 -> {
                                            System.out.print("Enter operator --> ");
                                            operator = scanner.nextLine();
                                            try {
                                                doperator = Double.parseDouble(operator);
                                            } catch (NumberFormatException e) {
                                                System.out.println("Error --> invalid id number");
                                                continue mofyfierMenu;
                                            }
                                            calculatorHistoryadmin.setFirstOperator(doperator);
                                            entityManager.getTransaction().begin();
                                            entityManager.persist(calculatorHistoryadmin);
                                            entityManager.getTransaction().commit();
                                        }
                                        case 2 -> {
                                            System.out.print("Enter operator --> ");
                                            operator = scanner.nextLine();
                                            try {
                                                doperator = Double.parseDouble(operator);
                                            } catch (NumberFormatException e) {
                                                System.out.println("Error --> invalid id number");
                                                continue mofyfierMenu;
                                            }
                                            calculatorHistoryadmin.setSecondOperator(doperator);
                                            entityManager.getTransaction().begin();
                                            entityManager.persist(calculatorHistoryadmin);
                                            entityManager.getTransaction().commit();
                                        }
                                        case 3 -> {
                                            System.out.print("Enter sign --> ");
                                            operator = scanner.nextLine();
                                            OperatorSign result = OperatorSign.SUM;
                                            try {
                                                result = OperatorSign.getSign(operator);
                                            } catch (InvalidOperationException e) {
                                                System.out.println("Error --> Invalid sign");
                                                continue mofyfierMenu;
                                            }
                                            calculatorHistoryadmin.setSign(result.sign);
                                            entityManager.getTransaction().begin();
                                            entityManager.persist(calculatorHistoryadmin);
                                            entityManager.getTransaction().commit();
                                        }
                                        case 4 -> {
                                            System.out.print("Enter operator --> ");
                                            operator = scanner.nextLine();
                                            try {
                                                doperator = Double.parseDouble(operator);
                                            } catch (NumberFormatException e) {
                                                System.out.println("Error --> invalid id number");
                                                continue mofyfierMenu;
                                            }
                                            calculatorHistoryadmin.setResult(doperator);
                                            entityManager.getTransaction().begin();
                                            entityManager.persist(calculatorHistoryadmin);
                                            entityManager.getTransaction().commit();
                                        }
                                    }
                                }
                            } else if (number == 2) {
                                System.out.println(calculatorHistoryadmin.toString());
                            }
                        }
                    } else if (operator.equals("exit")) {
                        return;
                    } else {
                        System.out.println("Error --> invalid action");
                        continue adminmenu;
                    }
                }
            } else if (adminPasswordVerifier.equals("exit")) {
                return;
            } else {
                System.out.println("Error --> invalid password");
                continue adminmenu;
            }
        }
    }
}

