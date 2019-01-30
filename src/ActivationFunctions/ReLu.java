/**
 * Implementation of a ReLu function. This is however not used in this prototype
 *
 * @author Marcus Jonsson Ewerbring @ Jonas Johansson
 * @verion 1.0
 * @since 2019-01-03
 */

package ActivationFunctions;

public class ReLu implements ActivationFunction{

    @Override
    public double calculateActivation(double x) {
        return Math.max(0, x);
    }

    @Override
    public double getDerivative(double x) {
        return x > 0 ? 1 : 0;
    }
}
