package org.example;

public class AdditionFactory extends OperationFactory {
    @Override
    protected Operation createOperation() {
        return new Addition();
    }
}
