package com.droidevils.algodroid.algorithm;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Point {

    private DecimalFormat df;

    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double EuclideanDistance(Point p) {
        return roundDecimal(Math.pow(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2), 0.5));
    }

    public double ManhattanDistance(Point p) {
        return roundDecimal(Math.abs(x - p.x) + Math.abs(y - p.y));
    }

    public double CosineDistance(Point p) {
        double productXY = x * p.x + y * p.y;
        double lenghtX = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double lenghtY = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2));
        return roundDecimal(productXY / (lenghtX * lenghtY));

    }

    public Point computeCentroid(Point p) {
        return new Point(roundDecimal((x + p.x) / 2), roundDecimal((y + p.y) / 2));
    }

    public double roundDecimal(double d) {
        df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(d));
    }
}
