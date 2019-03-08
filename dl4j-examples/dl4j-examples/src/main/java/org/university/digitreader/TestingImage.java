package org.university.digitreader;

import java.io.File;

public class TestingImage {

    private int LabelID;
    private File TestImage;

    public int setLabelID(int label){
        this.LabelID = label;
        return label;
    }

    public File setTrainImage(File testImage){
        this.TestImage = testImage;
        return testImage;
    }


    public int getLabelID(){
        return this.LabelID;
    }

    public File getTrainImage(){
        return this.TestImage;
    }
}
