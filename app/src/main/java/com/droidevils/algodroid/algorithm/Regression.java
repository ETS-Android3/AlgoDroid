package com.droidevils.algodroid.algorithm;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Regression {
    int n;
    double[] X, Y, Xm, Ym, Xm_Ym, Xm_2;
    double x_sum, y_sum, x_mean, y_mean;
    double xm_ym_sum, xm_2_sum;
    double w0, w1;
    String equation;

    public Regression(double[] x, double[] y, int n) {
        this.X = x;
        this.Y = y;
        this.n = n;
    }

    public void computeRegression() {
        Xm = new double[n];
        Ym = new double[n];
        Xm_Ym = new double[n];
        Xm_2 = new double[n];
        x_sum = 0;
        y_sum = 0;
        xm_ym_sum = 0;
        xm_2_sum = 0;

        // Sum & Mean
        for (int i = 0; i < n; i++) {
            x_sum += X[i];
            y_sum += Y[i];
        }
        x_mean = x_sum / n;
        y_mean = y_sum / n;

        for (int i = 0; i < n; i++) {
            Xm[i] = X[i] - x_mean;
            Ym[i] = Y[i] - y_mean;
            Xm_Ym[i] = Xm[i] * Ym[i];
            Xm_2[i] = Xm[i] * Xm[i];
            xm_ym_sum += Xm_Ym[i];
            xm_2_sum += Xm_2[i];
        }

        w1 = xm_ym_sum / xm_2_sum;
        w0 = y_mean - w1 * x_mean;

        equation = String.format("y = %.2f + (%.2f)x", w0, w1);
    }

    public int getN() {
        return n;
    }

    public double[] getX() {
        return X;
    }

    public double[] getY() {
        return Y;
    }

    public double[] getXm() {
        return Xm;
    }

    public double[] getYm() {
        return Ym;
    }

    public double[] getXm_Ym() {
        return Xm_Ym;
    }

    public double[] getXm_2() {
        return Xm_2;
    }

    public double getX_sum() {
        return x_sum;
    }

    public double getY_sum() {
        return y_sum;
    }

    public double getX_mean() {
        return x_mean;
    }

    public double getY_mean() {
        return y_mean;
    }

    public double getXm_ym_sum() {
        return xm_ym_sum;
    }

    public double getXm_2_sum() {
        return xm_2_sum;
    }

    public double getW0() {
        return w0;
    }

    public double getW1() {
        return w1;
    }

    public String getEquation() {
        return equation;
    }
}
