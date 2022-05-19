package com.example.gl.data;

import java.util.ArrayList;

public class PointCloud {
    private ArrayList<Float> points;

    public PointCloud(){
        points = new ArrayList<>();
    }

    public float[] getPoints() {
        float [] points = new float[this.points.size()];
        for (int i = 0; i < points.length; i++) {
            points[i]= this.points.get(i);
        }
        return points;
    }

    public ArrayList<Float>getListPoints(){
        return this.points;
    }

    public void setPoints(ArrayList<Float> points){
        this.points = points;
    }

    public void setPoints(float[] points){
        ArrayList<Float> pointsList = new ArrayList<Float>();
        pointsList.addAll(pointsList);
    }
}
