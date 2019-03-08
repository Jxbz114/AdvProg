package org.university.digitreader;

import java.io.File;

public class TrainingImage {

    private int LabelID;
    private String TrainImage;

    public int setLabelID(int label){
        this.LabelID = label;
        return label;
    }

    public String setTrainImage(String trainImage){
        this.TrainImage = trainImage;
        return trainImage;
    }


    public int getLabelID(){
        return this.LabelID;
    }

    public String getTrainImage(){
        return this.TrainImage;
    }



}
