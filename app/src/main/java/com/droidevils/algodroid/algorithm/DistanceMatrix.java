package com.droidevils.algodroid.algorithm;

import android.util.Log;

import java.util.ArrayList;

public class DistanceMatrix {

    public static final int EUCLIDEAN_DISTANCE = 0;
    public static final int MANHATTAN_DISTANCE = 1;
    public static final int COSINE_DISTANCE = 2;
    private static int DISTANCE_METHOD = EUCLIDEAN_DISTANCE;

    private int num;
    private ArrayList<Point> inputPoints;
    private double[][] distanceMatrix;

    public DistanceMatrix(ArrayList<Point> inputPoints) {
        this.inputPoints = inputPoints;
    }

    public void setDistanceMethod(int method) {
        DISTANCE_METHOD = method;
    }

    public void computeDistanceMatrix(){

        num = inputPoints.size();
        Log.i("MESSAGE", ""+num);
        distanceMatrix = new double[num][num];

        for (int i=0; i<num;i++){
            for (int j=0;j<num;j++){
                switch (DISTANCE_METHOD){
                    case EUCLIDEAN_DISTANCE:
                        distanceMatrix[i][j] = inputPoints.get(i).EuclideanDistance(inputPoints.get(j));
                        break;
                    case MANHATTAN_DISTANCE:
                        distanceMatrix[i][j] = inputPoints.get(i).ManhattanDistance(inputPoints.get(j));
                        break;
                    case COSINE_DISTANCE:
                        distanceMatrix[i][j] = inputPoints.get(i).CosineDistance(inputPoints.get(j));
                        break;
                }
                Log.i("MESSAGE", ""+distanceMatrix[i][j]);
            }
        }
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }
}
