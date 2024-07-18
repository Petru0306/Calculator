package org.example;

public class MultiplicationFactory extends OperationFactory{
    @Override
    protected Operation createOperation() {
        return new Multiplication();
    }
}
