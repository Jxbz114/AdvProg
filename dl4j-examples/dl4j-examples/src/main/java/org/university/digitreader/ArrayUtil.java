package org.university.digitreader;

import org.nd4j.linalg.api.ndarray.INDArray;

public class ArrayUtil {
    public static int indexOfLargest(double[] data){
        int result = -1;

        for (int i = 0 ; i < data.length ; i++){
            if (result == -1 || data[i] > data [result])
                result = i;
        }
        return result;
    }



    public static int indexOfLargest(INDArray data){
        int result = -1;
        double m = 0;
        int idx = 0;

        for (int row = 0 ; row < data.size(0) ; row++ ){
            for (int col = 0 ; col < data.size(1) ; col++){
                if (result == -1 || m < data.getDouble(row,col)){
                    result = idx;
                    m = data.getDouble(row,col);
                }
                idx++;
            }
        }
        return result;
    }
}
