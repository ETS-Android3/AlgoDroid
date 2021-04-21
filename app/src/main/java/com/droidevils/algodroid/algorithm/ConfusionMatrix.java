package com.droidevils.algodroid.algorithm;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ConfusionMatrix {

    double tp, fp, fn, tn;
    double sensitivity, specificity, precision, negativePredictive, accuracy;

    public ConfusionMatrix(double tp, double fp, double fn, double tn) {
        this.tp = tp;
        this.fp = fp;
        this.fn = fn;
        this.tn = tn;
    }

    public String formatValue(double value){
        DecimalFormat df = new DecimalFormat("0.000");
        df.setRoundingMode(RoundingMode.UP);
        return df.format(value);
    }

    public void computeConfusionMatrix() {
        sensitivity = tp / (tp + fn);
        specificity = tn / (fp + tn);
        precision = tp / (tp + fp);
        negativePredictive = tn / (tn + fn);
        accuracy = (tp + tn) / (tp + fp + fn + tn);
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public double getSpecificity() {
        return specificity;
    }

    public double getPrecision() {
        return precision;
    }

    public double getNegativePredictive() {
        return negativePredictive;
    }

    public double getAccuracy() {
        return accuracy;
    }
}
