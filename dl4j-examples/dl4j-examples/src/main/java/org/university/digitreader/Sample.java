package org.university.digitreader;

import java.awt.*;
import java.util.ArrayList;

public class Sample {

    //Create object to add image from arraylist to label from for loop then add object to array list and store array list for KNN access

    private int imgLabel;
    private int Number;
    private int rowNum;
    private int colNum;
    private int imageData;
    //private ArrayList<int[][]> imageList = new ArrayList<>();




    //Set Fields for newly created object

    public int setNumber(int number){
        this.Number = number;
        return number;
    }

    public int setLabel(int label){
        this.imgLabel = label;
        return label;
    }

    public int setXDimension(int xDim){

        this.rowNum = xDim;
        return xDim;
    }
    public int setYDimension(int yDim){

        this.colNum = yDim;
        return yDim;
    }

    public int setimageData(int data){
        this.imageData = data;
        return data;
    }


//    public ArrayList<int[][]> setImagesList(ArrayList<int[][]> ImageData) {
//
//        this.imageList = ImageData;
//        return ImageData;
//    }


    //Return Fields from newly created object image

    public int getNumber(){
        return this.Number;
    }

    public int getLabel(){
        return this.imgLabel;
    }

    public int getRowNum(){

        return this.rowNum;
    }
    public int getColNum(){

        return this.colNum;
    }

    public int getimageData(){
        return this.imageData;
    }
//
//    public ArrayList<int[][]> getImagesList() {
//        return this.imageList;
//    }


}
