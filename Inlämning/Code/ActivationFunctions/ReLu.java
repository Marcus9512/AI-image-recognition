package ActivationFunctions;

public class ReLu implements ActivationFunction{
    /**
     *
     * Implementation of a ReLu function. This is however not used in this prototype
     *
     */

    @Override
    public double calculateActivation(double x) {
        return Math.max(0, x);
    }

    @Override
    public double getDerivative(double x) {
        return x > 0 ? 1 : 0;
    }
}
