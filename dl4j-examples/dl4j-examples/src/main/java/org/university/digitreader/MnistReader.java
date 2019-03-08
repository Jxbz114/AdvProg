package org.university.digitreader;

import javafx.scene.control.Alert;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MnistReader {
    //private final DataSet dataSet;
    //    private ArrayList<int [][]> imagesList = new ArrayList<>();
    //   private ArrayList<Integer> labelList = new ArrayList<>();
    private DataInputStream images;
    private DataInputStream labels;
    private  int numLabels;
    private  int numImages;
    private  int numrows;
    private  int numcols;
    private int epoch = 10;
    private int imagesData [];
    private BufferedImage bi;
    private int [][] pixels;
    private ArrayList <TrainingImage> TrainingArr;
    private TrainingImage trained;
    private Random rand;

    public MnistReader(String labelFileName, String imageFileName) throws IOException {


        try {
            DataInputStream labels;
            DataInputStream images;


                TrainingArr = new ArrayList<>();

                labels = new DataInputStream(new FileInputStream(labelFileName));
                images = new DataInputStream(new FileInputStream(imageFileName));

                int magicNumber = labels.readInt();
                if (magicNumber !=  2049){
                    throw new IOException("The Label File magic number is not valid");
                }
                magicNumber = images.readInt();
                if (magicNumber != 2051){
                    throw  new IOException("The Image File magic number is not valid");
                }

                this.numLabels = labels.readInt();
                this.numImages = images.readInt();
                this.numrows = images.readInt();
                this.numcols = images.readInt();

                if (this.numImages != this.numLabels){
                    throw new IOException("There are not the same amount of Image Files --> Label Files");
                }

                //byte[] labelsData = new byte [this.numLabels];
                imagesData = new int [numrows * numcols];
                int imageVectorSize = numrows * numcols;

            System.out.println("Training Images: " + getNumImages());
            System.out.println("Training Labels: " + getNumLabels());
            System.out.println("Rows: " + getNumRows());
            System.out.println("Cols: " + getNumCols());



            int [] label_Data = new int[numLabels];

            //Go throgh image data and assign to new object
            for (int i = 0 ; i < epoch ; i++){
                System.out.println("Loading New Image..");
                trained = new TrainingImage();
                label_Data[i] = labels.read();
                int label = label_Data[i];
                trained.setLabelID(label);
                System.out.println(trained.getLabelID());
                int pixels [][] = new int[getNumRows()][getNumCols()];

                // Get pixel data
                for(int row = 0; row < numrows ; row++){
                    for (int col = 0; col < numcols; col++){
                        pixels [row][col] = images.read() & 0xff;
                    }//System.out.println("");

                }System.out.println("New Image done.. Now writing image:");

                for (int render = 0 ; render < imageVectorSize ; render++  ){
                    int greyValue = images.read() & 0xff;
                    int rgb_value = 0xFF000000 | (greyValue << 16) | (greyValue << 8) | (greyValue);
                    imagesData[render] = rgb_value;
                }
                BufferedImage bi  = new BufferedImage(numrows, numcols, BufferedImage.TYPE_INT_ARGB);
                bi.setRGB(0, 0, numrows,numcols, imagesData, 0, numrows );

                Random rand = new Random();
                String PATH = "dl4j-examples/src/main/java/org/university/digitreader/Images_TRN/";
                String dirName = PATH.concat(String.valueOf(label));
                String fileName = "Image_" + (i) + ".png";

                File directory = new File(dirName);
                if (!directory.exists()) {
                    directory.mkdir();
                }

                File outputFile = new File(dirName + "/" + fileName);
                ImageIO.write(bi, "png", outputFile);
                trained.setTrainImage(dirName + "/" + fileName);
                TrainingArr.add(trained);




//                File outputFile =  new File ("dl4j-examples/src/main/java/org/university/digitreader/Images_TRN/"
//                                               +  "Image_"   + i + ".png" );
//                ImageIO.write(bi, "png", outputFile);

                //if label is == 0 create mkdir 0


            }

            System.out.println("Training size: " + TrainingArr.size());

            //while (TrainingArr.iterator().hasNext()){

            for (int i = 0; i < TrainingArr.size() ; i++){


                    String imgPath = trained.getTrainImage();
                    System.out.println(i + ":    " + imgPath);
                    //Image temp = new Image(imgPath);
                    //trainingView.setImage(temp);
                }

            //}






            }catch (IOException ex){
            ex.printStackTrace();
            throw ex;

        }

    }

    public static void printMatrix(int matrix[][]){
        for (int[] row : matrix)
            System.out.println(Arrays.toString(matrix));
    }

    public int getNumLabels() {
        return this.numLabels;
    }

    public int getNumImages() {
        return this.numImages;
    }


    public int getNumRows() {
        return this.numrows;
    }

    public int getNumCols() {
        return this.numcols;
    }

//    public DataSet getData() {
//        return this.dataSet;
//    }

    public DataInputStream getInputStreamImages(){
        return  this.images;
    }

    public  DataInputStream getInputStreamLabels(){
        return this.labels;
    }

    public int getEpoch(){
        return this.epoch;
    }

    public ArrayList<TrainingImage> getTrainingArr() {
        return TrainingArr;
    }

    public TrainingImage getTrained() {
        return trained;
    }
}


//    Sample sample = new Sample(); //New sample object
//  labels.read(); //read label
//  sample.setNumber(i);    //set index number of new object
//  sample.setLabel(labels.read());  //set label ID
//imagesList.add(new int[numrows][numcols]); //Add new image dimennsions
//   sample.setYDimension(numrows);  // set object height
//  sample.setXDimension(numcols); // set object width

//                System.out.println("Number index: " + sample.getNumber());
//                System.out.println("Unique label: " +sample.getLabel());
//                System.out.println("Y Dimension: " +sample.getRowNum());
//                System.out.println("X Dimension: " +sample.getColNum());




// sample.setimageData(pixels[row][col]);
//System.out.print(sample.getimageData());

//printMatrix(pixels[row][col]);
//                            int rgb = pixels[row][col];
//                            rgb = (rgb<<16)|(rgb<<8)|(rgb);
//                            bi.setRGB(row, col, rgb);

//pixels = images.read() & 0xff;
//sample.setImagesList(imagesList.get(i)[row][col]);


//int mat [][] =
// sample.setImagesList(imagesList.get(i)[row][col]);
//printMatrix(imagesList.get(i)[row][col]);

//BufferedImage image = ImageIO.re ad(new ByteArrayInputStream(imagesList.get(i)[row][col]));
//bi.setRGB(i-1, i-1, images.read() & 0xff);


//                                WritableImage img = new WritableImage(imageWidth, imageHeight);
//                                PixelReader reader = img.getPixelReader();
//                                PixelWriter pw = img.getPixelWriter();
//                                reader.getPixels(row, col, imageWidth, imageHeight, PixelFormat.getIntArgbInstance(), imageData, 0, imageWidth);
//                                pw.setPixels(0, 0, imageWidth, imageHeight, PixelFormat.getIntArgbInstance(), imageData, 0, imageWidth);
//                                imageView.setImage(img);

//displayFirstMNIST(loadMnist,1);
//create temp buffered image to show each cycle
//BufferedImage tempBuff
// WritableImage tempimage
//tempimage.getPixelWriter().setPixels(0, 0, 256, 256, PixelFormat.getByteBgraInstance(),
//    imageInArray, 0, 256 * 4);



//
//            INDArray training = Nd4j.create(this.numLabels, imageVectorSize);
//            INDArray ideal = Nd4j.create(this.numLabels, 10);
//
//            int imageIndex = 0;
//
//            for (int i = 0 ; i < imageIndex ; i ++){
//                int label = labelsData[i];
//                for (int j = 0 ; j < imageVectorSize ; j++){
//                    training.put(i, j, ((double) (imagesData[imageIndex++] & 0xff )) / 255.0);
//                }
//                for (int j = 0 ; j < 10 ; j++){
//                    ideal.put(i, j, j==label?1:0);
//                }
//            }

//            images.close();
//           labels.close();

//this.dataSet = new DataSet(training,ideal);

//imagesList.get(i)[row][col] = images.read() & 0xff;
//sample.setimageData(imagesList.get(i)[row][col]);
// System.out.print(sample.getimageData());
