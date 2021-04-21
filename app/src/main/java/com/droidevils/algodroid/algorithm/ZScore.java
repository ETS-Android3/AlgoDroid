package com.droidevils.algodroid.algorithm;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ZScore {

    private ArrayList<Double> data;
    private ArrayList<Double> zscore;
    private int length;
    private double sum;
    private double mean;
    private double variance;
    private double sd;

    public ZScore(ArrayList<Double> input) {
        this.data = input;
    }

    public void computeZScore() {
        length = data.size();
        for (double value : data) {
            sum += value;
        }
        sum = roundDecimal(sum);
        mean = roundDecimal(sum / length);
        for (double value : data) {
            variance += Math.pow(value - mean, 2);
        }
        variance = roundDecimal(variance / length);
        sd = roundDecimal(Math.sqrt(variance));
        zscore = new ArrayList<Double>();
        for (int i = 0; i < data.size(); i++) {
            zscore.add(roundDecimal((data.get(i) - mean) / sd));
        }
    }

    public double roundDecimal(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.UP);
        return Double.parseDouble(df.format(d));
    }

    public int getLength() {
        return length;
    }

    public ArrayList<Double> getData() {
        return data;
    }

    public ArrayList<Double> getZscore() {
        return zscore;
    }

    public double getSum() {
        return sum;
    }

    public double getMean() {
        return mean;
    }

    public double getVariance() {
        return variance;
    }

    public double getSd() {
        return sd;
    }
}
