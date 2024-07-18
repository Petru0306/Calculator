package org.example;

public class PowerFactory extends OperationFactory{
    @Override
    protected  Operation createOperation() {
        return new Power();
    }
}
