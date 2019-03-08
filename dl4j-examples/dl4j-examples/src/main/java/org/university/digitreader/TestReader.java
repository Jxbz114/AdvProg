package org.university.digitreader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TestReader {

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
        private ArrayList<TestingImage> TestingArr;
        private Random rand;

        public TestReader(String labelFileName, String imageFileName) throws IOException {


            try {
                DataInputStream labels;
                DataInputStream images;


                TestingArr = new ArrayList<>();

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

                System.out.println("Test Images: " + getNumImages());
                System.out.println("Test Labels: " + getNumLabels());
                System.out.println("Rows: " + getNumRows());
                System.out.println("Cols: " + getNumCols());



                int [] label_Data = new int[numLabels];

                //Go throgh image data and assign to new object
                for (int i = 0 ; i < epoch ; i++){
                    System.out.println("Loading New Image..");
                    TestingImage test = new TestingImage();
                    label_Data[i] = labels.read();
                    int label = label_Data[i];
                    test.setLabelID(label);
                    System.out.println(test.getLabelID());
                    int pixels [][] = new int[getNumRows()][getNumCols()];

                    // Get pixel data
                    for(int row = 0; row < numrows ; row++){
                        for (int col = 0; col < numcols; col++){
                            pixels [row][col] = images.read() & 0xff;
                        }//System.out.println("");

                    }System.out.println("IMAGE TESTING IN PROGRESS...");

                    for (int render = 0 ; render < imageVectorSize ; render++  ){
                        int greyValue = images.read() & 0xff;
                        int rgb_value = 0xFF000000 | (greyValue << 16) | (greyValue << 8) | (greyValue);
                        imagesData[render] = rgb_value;
                    }
                    BufferedImage bi  = new BufferedImage(numrows, numcols, BufferedImage.TYPE_INT_ARGB);
                    bi.setRGB(0, 0, numrows,numcols, imagesData, 0, numrows );

                    Random rand = new Random();
                    String PATH = "dl4j-examples/src/main/java/org/university/digitreader/Images_TST/";
                    String dirName = PATH.concat(String.valueOf(label));
                    String fileName = "Image_" + (i) + ".png";

                    File directory = new File(dirName);
                    if (!directory.exists()) {
                        directory.mkdir();
                    }

                    File outputFile = new File(dirName + "/" + fileName);
                    ImageIO.write(bi, "png", outputFile);
                    test.setTrainImage(outputFile);
                    TestingArr.add(test);


//                File outputFile =  new File ("dl4j-examples/src/main/java/org/university/digitreader/Images_TRN/"
//                                               +  "Image_"   + i + ".png" );
//                ImageIO.write(bi, "png", outputFile);

                    //if label is == 0 create mkdir 0






                }

                System.out.println("Training size: " + TestingArr.size());






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

    public ArrayList<TestingImage> getTestingArr() {
        return TestingArr;
    }

}
