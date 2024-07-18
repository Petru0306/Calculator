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
    private int firstOperator;
    @Column(name = "signOperator")
    private String sign;

    @Column(name = "secondOperator")
    private int secondOperator;

    @Column(name = "dateOfOperation")
    LocalDate date = LocalDate.now();

    @Column(name = "result")
    private int result;

    public CalculatorHistory() {
    }

    public void setFirstOperator(int firstOperator) {
        this.firstOperator = firstOperator;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setSecondOperator(int secondOperator) {
        this.secondOperator = secondOperator;
    }

    public void setResult(int result) {
        this.result = result;
    }
}

