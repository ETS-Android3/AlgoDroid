package com.droidevils.algodroid.algorithm;

public class Correlation {

    int n;
    double[] X, Y, X_square, Y_square, XY;
    double sum_X, sum_Y, sum_XY;
    double squareSum_X, squareSum_Y;
    double corr;

    public Correlation(double[] x, double[] y, int n) {
        this.X = x;
        this.Y = y;
        this.n = n;
    }

    // function that returns correlation coefficient.
    public void correlationCoefficient() {

        X_square = new double[n];
        Y_square = new double[n];
        XY = new double[n];

        sum_X = 0;
        sum_Y = 0;
        sum_XY = 0;
        squareSum_X = 0;
        squareSum_Y = 0;

        for (int i = 0; i < n; i++) {
            // sum of elements of array X.
            sum_X = sum_X + X[i];

            // sum of elements of array Y.
            sum_Y = sum_Y + Y[i];

            // sum of X[i] * Y[i].
            XY[i] = X[i] * Y[i];
            sum_XY = sum_XY + X[i] * Y[i];

            // sum of square of array elements.
            X_square[i] = X[i] * X[i];
            Y_square[i] = Y[i] * Y[i];
            squareSum_X = squareSum_X + X[i] * X[i];
            squareSum_Y = squareSum_Y + Y[i] * Y[i];
        }

        // use formula for calculating correlation
        // coefficient.
        corr = (double) (n * sum_XY - sum_X * sum_Y)
                / (double) (Math.sqrt((n * squareSum_X - sum_X * sum_X) * (n * squareSum_Y - sum_Y * sum_Y)));
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

    public double[] getX_square() {
        return X_square;
    }

    public double[] getY_square() {
        return Y_square;
    }

    public double[] getXY() {
        return XY;
    }

    public double getSum_X() {
        return sum_X;
    }

    public double getSum_Y() {
        return sum_Y;
    }

    public double getSum_XY() {
        return sum_XY;
    }

    public double getSquareSum_X() {
        return squareSum_X;
    }

    public double getSquareSum_Y() {
        return squareSum_Y;
    }

    public double getCorr() {
        return corr;
    }

    // Driver function
    public static void main(String args[]) {

        double X[] = { 7, 6, 8, 5, 6, 9 };
        double Y[] = { 12, 8, 12, 10, 11, 13 };

        // Find the size of array.
        int n = X.length;

        Correlation correlation = new Correlation(X,Y,n);

        // Function call to correlationCoefficient.
        System.out.printf("%6f", correlation.getCorr());

    }
}
