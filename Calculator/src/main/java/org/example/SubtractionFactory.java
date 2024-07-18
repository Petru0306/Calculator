package org.example;

public class SubtractionFactory extends OperationFactory{
    @Override
    protected Operation createOperation() {
        return new Subtraction();
    }
}
