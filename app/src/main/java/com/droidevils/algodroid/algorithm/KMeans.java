package com.droidevils.algodroid.algorithm;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class KMeans {

    public static final int EUCLIDEAN_DISTANCE = 0;
    public static final int MANHATTAN_DISTANCE = 1;
    public static final int COSINE_DISTANCE = 2;

    private static int DISTANCE_METHOD = 0;

    int pn, cn;
    Point[] points;
    Point[][] centroids;
    double[][] distance;
    int[] cluster;

    private static DecimalFormat df;

    public void setDistanceMethod(int method) {
        DISTANCE_METHOD = method;
    }

    public class Point {

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
            df.setRoundingMode(RoundingMode.UP);
            return Double.parseDouble(df.format(d));
        }
    }


    public void getInput(int pn, int cn, String pointString, String clusterString) {
        this.pn = pn;
        this.cn = cn;
        String[] pointPair = pointString.split("\n");
        String[] clusterPair = clusterString.split("\n");
        points = new Point[pn];
        centroids = new Point[pn + 1][cn];
        distance = new double[pn][cn];
        cluster = new int[pn];
        for (int i = 0; i < pn; i++) {
            String[] temp = pointPair[i].split(",");
            double x = Double.parseDouble(temp[0]);
            double y = Double.parseDouble(temp[1]);
            points[i] = new Point(x, y);
        }
        for (int i = 0; i < cn; i++) {
            String[] temp = clusterPair[i].split(",");
            double x = Double.parseDouble(temp[0]);
            double y = Double.parseDouble(temp[1]);
            centroids[0][i] = new Point(x, y);
        }
    }

    public void readInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of Points : ");
        pn = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter number of centroids : ");
        cn = Integer.parseInt(scanner.nextLine());
        points = new Point[pn];
        centroids = new Point[pn + 1][cn];
        distance = new double[pn][cn];
        cluster = new int[pn];
        System.out.println("Enter Points in form (x y) :");
        for (int i = 0; i < pn; i++) {
            double x = Double.parseDouble(scanner.nextLine());
            double y = Double.parseDouble(scanner.nextLine());
            points[i] = new Point(x, y);
        }
        System.out.println("Enter Centroids in form (x y) :");
        for (int i = 0; i < cn; i++) {
            double x = Double.parseDouble(scanner.nextLine());
            double y = Double.parseDouble(scanner.nextLine());
            centroids[0][i] = new Point(x, y);
        }
        scanner.close();
    }

    public void computeCluster() {
        for (int i = 0; i < pn; i++) {
            Point point = points[i];
            double min = 999;
            for (int j = 0; j < cn; j++) {
                double d;
                switch (DISTANCE_METHOD) {
                    case MANHATTAN_DISTANCE:
                        d = point.ManhattanDistance(centroids[i][j]);
                        break;
                    case COSINE_DISTANCE:
                        d = point.CosineDistance(centroids[i][j]);
                        break;
                    case EUCLIDEAN_DISTANCE:
                    default:
                        d = point.EuclideanDistance(centroids[i][j]);
                        break;
                }

                distance[i][j] = d;
                centroids[i + 1][j] = centroids[i][j];
                if (d < min) {
                    min = d;
                    cluster[i] = j;
                }
            }
            centroids[i + 1][cluster[i]] = point.computeCentroid(centroids[i][cluster[i]]);
        }

    }

    public void displayCluster() {
        System.out.print("\tCl\t( x, y )");
        for (int i = 0; i < cn; i++) {
            System.out.print("\tEd(C" + (i + 1) + ")\tC" + (i + 1) + "( x , y )");
        }
        System.out.println("");
        for (int i = 0; i < pn; i++) {
            String str = "";
            str += "\t" + (cluster[i] + 1);
            str += "\t( " + points[i].x;
            str += " , " + points[i].y + " )";
            for (int j = 0; j < cn; j++) {
                str += "\t" + distance[i][j];
                str += "\t( " + centroids[i][j].x;
                str += " , " + centroids[i][j].y + " )";
            }
            System.out.println(str);
        }
    }

    public String getResultString() {
        String result = "\tCl\t( x, y )";
        for (int i = 0; i < cn; i++) {
            result += "\tEd(C" + (i + 1) + ")\tC" + (i + 1) + "( x , y )";
        }
        result += "\n";
        for (int i = 0; i < pn; i++) {
            String str = "";
            str += "\t" + (cluster[i] + 1);
            str += "\t( " + points[i].x;
            str += " , " + points[i].y + " )";
            for (int j = 0; j < cn; j++) {
                str += "\t" + distance[i][j];
                str += "\t( " + centroids[i][j].x;
                str += " , " + centroids[i][j].y + " )";
            }
            result += str + "\n";
        }
        return result;
    }

    public static void main(String[] args) {

        KMeans kMeans = new KMeans();
        kMeans.readInput();
        kMeans.computeCluster();
        kMeans.displayCluster();
    }

}