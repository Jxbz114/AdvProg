package org.university.digitreader;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.*;

@SuppressWarnings("Duplicates")
public class Main extends Application {

    Scene scene1,scene2,scene3;

//    private WritableImage numberComp;
//    private final Random r = new Random();
//    private MultiLayerNetwork nn;
//    private INDArray xyOut;

    private File TRNImagePath;
    private File TRNLabelPath;
    private File TSTImagePath;
    private File TSTLabelPath;
    private Path path;

   final int nChannels = 1; // Number of input channels
   final int iterations = 1;
    final int outputNum = 10; // number of output classes
    final int batchSize = 64; // batch size for each epoch
    final int rngSeed = 123; // random number seed for reproducibility
    final int numEpochs = 15; // number of epochs to perform

   private  DataInputStream image_dataStream = null;
   private  DataInputStream label_dataStream = null;



    private StartMenu startMenu;
    private TrainingMenu trainMenu;
    private CanvasMenu canvasMenu;
    private Stage stage;

   private ImageView trainingView;
   private ImageView tesingView;


    public void start (Stage primaryStage){



        this.stage = primaryStage;
        Pane menuPane = new Pane();
        Pane trainPane = new Pane();
        Pane canvasPane = new Pane();




        startMenu = new StartMenu();
        trainMenu = new TrainingMenu();
        canvasMenu = new CanvasMenu();

        /*      ADD ALL CHILDREN HERE  & set stage layout     */
        menuPane.getChildren().addAll(startMenu); //imgView
        trainPane.getChildren().addAll(trainMenu);
        canvasPane.getChildren().addAll(canvasMenu);

        scene1 = new Scene(menuPane);
        scene2 = new Scene(trainPane);
        scene3 = new Scene(canvasPane);



        primaryStage.setWidth(700);
        primaryStage.setHeight(450);
        primaryStage.setTitle("Digitizer.");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene1);
        primaryStage.setOnCloseRequest(we -> System.exit(0));
        primaryStage.show();





    }
    private class TrainingMenu extends Parent {


        public TrainingMenu() {


                HBox optionHolder = new HBox(20);
                HBox optionHolder2 = new HBox(20);
                optionHolder.setTranslateX(140);
                optionHolder.setTranslateY(30);

                optionHolder2.setTranslateX(140);
                optionHolder2.setTranslateY(100);


                HBox imageHolder = new HBox(150);
                imageHolder.setTranslateX(150);
                imageHolder.setTranslateY(300);

                HBox trainer = new HBox();
                trainer.setTranslateX(490);
                trainer.setTranslateY(70);


                int seed = 43;
                double learningRate = 1e-2;
                int nEpochs = 50;
                int batchSize = 500;
                int channel = 1;




                AppButton loadImages = new AppButton("Training Images");
                loadImages.text.setFill(Color.WHITE);
                loadImages.setOnMousePressed(e -> {
                    //On click
                    FileChooser loadTrainingImg = new FileChooser();
                    loadTrainingImg.setTitle("Load Training Resource");
                    File selectedFileOne = loadTrainingImg.showOpenDialog(null);
                    selectedFileOne.getName().toString();
                    System.out.println(selectedFileOne.getAbsolutePath());
                    TRNImagePath = selectedFileOne;

                    //0.0000001 - precision number

                });


                AppButton loadLabel = new AppButton("Training Labels");
                loadLabel.text.setFill(Color.WHITE);
                loadLabel.setOnMousePressed(e -> {
                    //On click
                    FileChooser loadTrainingLabels = new FileChooser();
                    loadTrainingLabels.setTitle("Load Training Resource");
                    File selectedFileTwo = loadTrainingLabels.showOpenDialog(null);
                    selectedFileTwo.getName().toString();
                    System.out.println(selectedFileTwo.getAbsolutePath());
                    TRNLabelPath = selectedFileTwo;

                });


            AppButton TestImages = new AppButton("Test Images");
            TestImages.text.setFill(Color.WHITE);
            TestImages.setOnMousePressed(e -> {
                //On click
                FileChooser loadTestImg = new FileChooser();
                loadTestImg.setTitle("Load Testing Resource");
                File selectedFileThree = loadTestImg.showOpenDialog(null);
                selectedFileThree.getName().toString();
                System.out.println(selectedFileThree.getAbsolutePath());
                TSTImagePath = selectedFileThree;

                //0.0000001 - precision number

            });


            AppButton TestLabel = new AppButton("Test Labels");
            TestLabel.text.setFill(Color.WHITE);
            TestLabel.setOnMousePressed(e -> {
                //On click
                FileChooser loadTrainingLabels = new FileChooser();
                loadTrainingLabels.setTitle("Load Testing Resource");
                File selectedFileFour = loadTrainingLabels.showOpenDialog(null);
                selectedFileFour.getName().toString();
                System.out.println(selectedFileFour.getAbsolutePath());
                TSTLabelPath = selectedFileFour;

            });



                AppButton TRAIN = new AppButton("TRAIN");
                TRAIN.text.setFill(Color.WHITE);
                TRAIN.setOnMousePressed(e -> {
                    //On click
                    try {
                        MnistReader loadMnist = new MnistReader(TRNLabelPath.toString(),TRNImagePath.toString());
                        TestReader testMnist = new TestReader(TSTLabelPath.toString(),TSTImagePath.toString());

                        testMnist.getTestingArr();
                        loadMnist.getTrainingArr();

                    } catch (Exception ioEx){
                        ioEx.printStackTrace();
                        System.out.println("NOT WORKING");

                    }

                });

                final int offset = 400;


                //String imgPath = "res/image.png";
            Image image = new Image("/image.png");
            Image image1 = new Image("/image.png");
                ImageView trainingView = new ImageView();
                ImageView testingView = new ImageView();

                trainingView.setImage(image);
                trainingView.setFitWidth(100);
                trainingView.setFitHeight(100);
                testingView.setImage(image);
                testingView.setFitWidth(100);
                testingView.setFitHeight(100);



                optionHolder2.getChildren().addAll(TestImages,TestLabel);
                optionHolder.getChildren().addAll(loadImages, loadLabel);
                imageHolder.getChildren().addAll(trainingView, testingView);
                trainer.getChildren().addAll(TRAIN);


                Rectangle bg = new Rectangle(852, 480);
                bg.setFill(Color.GRAY);
                bg.setOpacity(0.4);
                getChildren().addAll(bg, optionHolder, optionHolder2, trainer, imageHolder);

        }
    }





    /**
     *
     *
     *
     *
     * * * * * * S T A R T M E N U   * * * * * *
     *
     *
     *
     */


    private class StartMenu extends Parent {


        public StartMenu(){
            VBox optionHolder = new VBox(10);
            optionHolder.setTranslateX(100);
            optionHolder.setTranslateY(50);

            MenuButton btnTraining = new MenuButton("Training Ground");
            btnTraining.text.setFill(Color.WHITE);
            btnTraining.setOnMousePressed (e->{
                //On click
                stage.setScene(scene2);
            });

            MenuButton btnDraw = new MenuButton("Canvas Mode");
            btnDraw.text.setFill(Color.WHITE);
            btnDraw.setOnMousePressed(e->{

                stage.setScene(scene3);

            });


            MenuButton btnInfo = new MenuButton("Info");
            btnInfo.text.setFill(Color.WHITE);


            MenuButton btnExit = new MenuButton("Exit");
            btnExit.text.setFill(Color.WHITE);
            btnExit.setOnMousePressed (e->{
                System.exit(0);
            });

            final int offset = 400;
            optionHolder.getChildren().addAll(btnTraining, btnDraw, btnInfo, btnExit);
            Rectangle bg = new Rectangle(852,480);
            bg.setFill(Color.GRAY);
            bg.setOpacity(0.4);
            getChildren().addAll(bg, optionHolder);
        }
    }

    /**
     *
     *
     *
     *
     * * * * * * C A N V A S M E N U   * * * * * *
     *
     *
     *
     */


    private class CanvasMenu extends Parent {

        public CanvasMenu(){

            Canvas canvas = new Canvas(28, 28);

            VBox optionHolder = new VBox(10);
            optionHolder.setAlignment(Pos.BOTTOM_CENTER);
            VBox canvasHolder = new VBox(10);
            canvasHolder.setTranslateX(150);
            canvasHolder.setTranslateY(100);



            final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            initDraw(graphicsContext);

            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        graphicsContext.beginPath();
                        graphicsContext.moveTo(event.getX(), event.getY());
                        graphicsContext.stroke();
                    }
                });

            canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        graphicsContext.lineTo(event.getX(), event.getY());
                        graphicsContext.stroke();
                    }
                });

            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {

                    }
                });


            ClearButton snapShotBtn = new ClearButton("RENDER");
            snapShotBtn.setOnKeyPressed(e->{

                try {

                    SnapshotParameters parameters = new SnapshotParameters();
                    WritableImage wi = new WritableImage(28, 28);
                    WritableImage snapshot = canvas.snapshot(new SnapshotParameters(), wi);
                    File output = new File("test" + ".png");
                    ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);

                }catch(IOException ex){
                    ex.printStackTrace();
                }

            });




            ClearButton btnCLEAR = new ClearButton("CLEAR");
            btnCLEAR.text.setFill(Color.RED);
            btnCLEAR.setOnMousePressed(e->{
                canvas.getGraphicsContext2D().clearRect(0, 0, 200, 200);
                canvas.getGraphicsContext2D().fillRect(0,0,200,200);
            });


            final int offset = 400;
            optionHolder.getChildren().addAll(btnCLEAR, snapShotBtn);
            canvasHolder.getChildren().addAll(canvas);          //canvasHolder
            Rectangle bg = new Rectangle(852,480);

            bg.setFill(Color.GRAY);
            bg.setOpacity(0.4);
            getChildren().addAll(bg, optionHolder, canvasHolder);
        }
    }

    private void initDraw(GraphicsContext gc) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.BLACK);
        //gc.setStroke(Color.WHITE);
        gc.setLineWidth(100);

        gc.fill();
        gc.strokeRect(
            0,              //x of the upper left corner
            0,              //y of the upper left corner
            canvasWidth,    //width of the rectangle
            canvasHeight);  //height of the rectangle

        gc.setFill(Color.BLACK);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);



    }



    //Canvas canvas = new Canvas();

//            Path path = new Path();
//            path.setStrokeWidth(4);
//            path.setStroke(Color.BLACK);
//
//            EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent mouseEvent) {
//                    if(mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED){
//                        path.getElements().clear();
//                        path.getElements().add(new MoveTo(mouseEvent.getX(), mouseEvent.getY()));
//                    }else if(mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED){
//                        path.getElements().add(new LineTo(mouseEvent.getX(), mouseEvent.getY()));
//                    }
//
//                }
//
//            };
//
//           canvas.setOnMouseClicked(mouseHandler);
//           canvas.setOnMouseDragged(mouseHandler);
//           canvas.setOnMouseEntered(mouseHandler);
//           canvas.setOnMouseExited(mouseHandler);
//           canvas.setOnMouseMoved(mouseHandler);
//           canvas.setOnMousePressed(mouseHandler);
//           canvas.setOnMouseReleased(mouseHandler);
//
//           canvas.setHeight(110);
//           canvas.setWidth(110);






    //  FUNCTIONS

//    public void displayFirstMNIST(MnistReader reader, int count){
//        for (int i = 4 ; i < count ; i ++){
//            System.out.println("========" + ArrayUtil.indexOfLargest(reader.getData().get(i).getLabels()));
//
//        }
//    }
//
//    public static void dumpMNISTDigit(INDArray data){
//        int idx = 0;
//        for (int i = 0 ; i < 28 ; i++){
//            StringBuilder line = new StringBuilder();
//            for (int j = 0 ; j < 28 ; j++){
//                line.append(data.getDouble(idx++)>0.0000001?"*":" ");
//            }
//            System.out.println(line.toString());
//        }
//    }









    /**
     *
     *
     *
     * * * * * * M E N U -- B U T T O N  * * * * * *
     *
     *
     *
     */


    private static class MenuButton extends StackPane {

        private Text text;
        public MenuButton(String name){


            DropShadow drop = new DropShadow(50, Color.WHITE);
            drop.setInput(new Glow());
            text = new Text(name);
            text.setFont(text.getFont().font(18));
            Rectangle bgBox = new Rectangle(150,30);
            bgBox.setOpacity(0.5);
            bgBox.setFill(Color.BLACK);
            bgBox.setEffect(new GaussianBlur(3.5));
            setAlignment(Pos.CENTER_LEFT);
            setRotate(0);
            getChildren().addAll(bgBox,text);

            setOnMouseEntered(e->{
                bgBox.setTranslateX(10);
                text.setTranslateX(10);
                bgBox.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });

            setOnMouseExited(e->{
                bgBox.setTranslateX(0);
                text.setTranslateX(0);
                bgBox.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });

            setOnMousePressed(e-> setEffect(drop));
            setOnMouseReleased(e-> setEffect(null));
        }
    }

    /**
     *
     *
     *
     *
     * * * * * * TRAINING BUTTONS   * * * * * *
     *
     *
     *
     */

    private static class AppButton extends StackPane{
        private Text text;
        public AppButton(String name){
            DropShadow drop = new DropShadow(50, Color.WHITE);
            drop.setInput(new Glow());
            text = new Text(name);
            text.setFont(text.getFont().font(18));
            Rectangle bgBox = new Rectangle(150,30);
            bgBox.setOpacity(0.5);
            bgBox.setFill(Color.BLACK);
            bgBox.setEffect(new GaussianBlur(3.5));
            setAlignment(Pos.CENTER_LEFT);
            setRotate(0);
            getChildren().addAll(bgBox,text);

            setOnMouseEntered(e->{
                bgBox.setTranslateX(10);
                text.setTranslateX(10);
                bgBox.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });

            setOnMouseExited(e->{
                bgBox.setTranslateX(0);
                text.setTranslateX(0);
                bgBox.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });

            setOnMousePressed(e-> setEffect(drop));
            setOnMouseReleased(e-> setEffect(null));
        }
    }

    private static class ClearButton extends StackPane{
        private Text text;
        public ClearButton(String name){
            DropShadow drop = new DropShadow(50, Color.RED);
            drop.setInput(new Glow());
            text = new Text(name);
            text.setFont(text.getFont().font(18));
            Rectangle bgBox = new Rectangle(150,30);
            bgBox.setOpacity(0.5);
            bgBox.setFill(Color.BLACK);
            bgBox.setEffect(new GaussianBlur(3.5));
            setAlignment(Pos.CENTER);
            setRotate(0);
            getChildren().addAll(bgBox,text);

            setOnMouseEntered(e->{
                bgBox.setTranslateX(10);
                text.setTranslateX(10);
                bgBox.setFill(Color.RED);
                text.setFill(Color.WHITE);
            });

            setOnMouseExited(e->{
                bgBox.setTranslateX(0);
                text.setTranslateX(0);
                bgBox.setFill(Color.BLACK);
                text.setFill(Color.RED);
            });

            setOnMousePressed(e-> setEffect(drop));
            setOnMouseReleased(e-> setEffect(null));
        }
    }

    public static void main( String[] args )
    {
        launch(args);
    }


}

//DataSet trainingSet = loadMnist.getData();
//DataSetIterator trainingSetIterator = new ListDataSetIterator(trainingSet.asList(), batchSize);
//
//                         int imageHeight = loadMnist.getNumCols();
//                        int imageWidth = loadMnist.getNumRows();
//                        int imageSize = imageHeight * imageWidth;
//                        //byte [] imageByteData = new byte [imageSize * loadMnist.getNumImages()];
//                        //byte [] labelByteData = new byte[loadMnist.getNumLabels()];
//                        DataInputStream dataInputStream = loadMnist.getInputStreamImages();

//label, image,  variable distance

//                        for (int pixel = 0 ; pixel < imageSize ; pixel ++ ){
//                            int greyValue = loadMnist.getInputStreamImages().read();
//                            int rgb_value = 0xFF000000 | (greyValue << 16) | (greyValue << 8) | (greyValue);
//                            imageData[pixel] = rgb_value;
// displayImg.setImage(img);
// displayImg.setImage(numberComp);

//                        displayFirstMNIST(loadMnist, numOutputs);
//                        System.out.println("Cols: " + loadMnist.getData());

