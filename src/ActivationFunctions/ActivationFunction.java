/**
 * @author Marcus Jonsson Ewerbring @ Jonas Johansson
 * @verion 1.0
 * @since 2019-01-03
 */
package ActivationFunctions;

import java.io.Serializable;

public interface ActivationFunction extends Serializable {
    double calculateActivation(double x);
    double getDerivative(double x);
}
