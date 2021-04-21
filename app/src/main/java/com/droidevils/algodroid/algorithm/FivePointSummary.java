package com.droidevils.algodroid.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FivePointSummary {

    private double median;
    private double firstQuartile;
    private double thirdQuartile;
    private double max;
    private double min;
    private double range;
    private double IQR;


    private double lowerFence = 0;
    private double upperFence = 0;

    private List<Double> data = null;
    private ArrayList<Double> outlierList = new ArrayList<Double>();

    public FivePointSummary(ArrayList<Double> data) {
        this.data = data;
    }

    public void fiveNumberSummary() {
        int n = data.size() - 1;

        if (!data.isEmpty() && n != 0) {
            Double[] numbers = data.toArray(new Double[data.size()]);
            Arrays.sort(numbers);

            // Max and Min
            max = numbers[n];
            min = numbers[0];

            // Middle Number (Q2)
            median = getMiddleNum(numbers);

            // Q1
            firstQuartile = getMiddleNum(Arrays.copyOfRange(numbers, 0, (numbers.length) / 2));

            // Q3
            if (numbers.length % 2 == 0) {
                thirdQuartile = getMiddleNum(Arrays.copyOfRange(numbers, (numbers.length) / 2, numbers.length));
            } else {
                thirdQuartile = getMiddleNum(Arrays.copyOfRange(numbers, ((numbers.length) / 2) + 1, numbers.length));
            }
        } else {
            max = min = median = firstQuartile = thirdQuartile = data.get(0);
        }
        range = max - min;
        IQR = thirdQuartile - firstQuartile;

        lowerFence = firstQuartile - 1.5*(IQR);
        upperFence = thirdQuartile + 1.5*(IQR);
    }

    /**
     * Finds all the strays and outliers in the dataset.
     */
    public void findOutliers() {
        for (Double dataPoint : data) {
            if (dataPoint > upperFence || dataPoint < lowerFence) {
                outlierList.add(dataPoint);
            }
        }
    }

    /**
     * Finds and returns the middle index of a 1D Double array.
     *
     * @param num Double [] is the array that is going to have its middle index
     *            returned.
     * @return double index from the middle of the Double array.
     */
    private double getMiddleNum(Double[] num) {
        int middle = (num.length - 1) / 2;

        if (num.length % 2 == 0) {
            double num1 = num[middle];
            double num2 = num[middle + 1];
            return (num1 + num2) / 2;
        } else {
            return num[middle];
        }
    }

    public double getMedian() {
        return median;
    }

    public double getFirstQuartile() {
        return firstQuartile;
    }

    public double getThirdQuartile() {
        return thirdQuartile;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public double getRange() {
        return range;
    }

    public double getIQR() {
        return IQR;
    }

    public double getLowerFence() {
        return lowerFence;
    }

    public double getUpperFence() {
        return upperFence;
    }

    public List<Double> getData() {
        return data;
    }

    public ArrayList<Double> getOutlierList() {
        return outlierList;
    }
}
