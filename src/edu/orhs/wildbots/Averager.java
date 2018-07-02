package edu.orhs.wildbots;

public class Averager {
    
    double sum = 0.0;
    int position = 0;
    int size = 10;
    double[] dataPoints;
    public Averager(int size){
        this.size = size;
        dataPoints = new double[size];
    }
    public void addSample(double data){
        sum -= dataPoints[position];
        sum += data;
        dataPoints[position] = data;
        position++;
        if(position >= size){
            position = 0;
        }
    }
    public double getAverage(){
        return sum/size;
    }
}
