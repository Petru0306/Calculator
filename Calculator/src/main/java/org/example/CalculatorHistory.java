package org.example;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
@Table(name = "calculatorHistory")
public class CalculatorHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "firstOperator")
    private double firstOperator;
    @Column(name = "signOperator")
    private String sign;

    @Column(name = "secondOperator")
    private double secondOperator;

    @Column(name = "dateOfOperation")
    LocalDate date = LocalDate.now();

    @Column(name = "result")
    private double result;

    public CalculatorHistory() {
    }

    public void setFirstOperator(double firstOperator) {
        this.firstOperator = firstOperator;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setSecondOperator(double secondOperator) {
        this.secondOperator = secondOperator;
    }

    public void setResult(double result) {
        this.result = result;
    }
}

