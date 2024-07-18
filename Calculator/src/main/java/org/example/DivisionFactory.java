package org.example;

public class DivisionFactory extends OperationFactory{
    @Override
    protected Operation createOperation() {
        return new Division();
    }
}
