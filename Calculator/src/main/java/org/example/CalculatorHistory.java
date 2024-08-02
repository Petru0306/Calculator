package org.example;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "calculatorHistory")
public class CalculatorHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public int getId() {
        return id;
    }

    public double getFirstOperator() {
        return firstOperator;
    }

    public String getSign() {
        return sign;
    }

    public double getSecondOperator() {
        return secondOperator;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "CalculatorHistory{" +
                "id=" + id +
                ", firstOperator=" + firstOperator +
                ", sign='" + sign + '\'' +
                ", secondOperator=" + secondOperator +
                ", date=" + date +
                ", result=" + result +
                '}';
    }
}

