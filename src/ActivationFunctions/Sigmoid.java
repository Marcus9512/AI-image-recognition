/**
 *
 * Implementation of the Sigmoid activation function
 *
 * @author Marcus Jonsson Ewerbring @ Jonas Johansson
 * @verion 1.0
 * @since 2019-01-03
 */

package ActivationFunctions;

public class Sigmoid implements ActivationFunction{

    @Override
    public double calculateActivation(double x) {
        return 1.0/(1.0+ Math.exp(-x));
    }

    @Override
    public double getDerivative(double x) {
        return calculateActivation(x)*(1.0-calculateActivation(x));
    }
}
