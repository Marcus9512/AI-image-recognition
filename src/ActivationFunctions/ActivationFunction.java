package ActivationFunctions;

import java.io.Serializable;

public interface ActivationFunction extends Serializable {
    double calculateActivation(double x);
    double getDerivative(double x);
}
