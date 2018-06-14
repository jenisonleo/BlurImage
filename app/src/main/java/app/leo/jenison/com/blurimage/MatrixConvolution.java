package app.leo.jenison.com.blurimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

public class MatrixConvolution {
    public static final int factor=100;
    public MatrixConvolution(Context context){

    }


    public static Bitmap generateBlurImage(Bitmap image){
        double [][] filter=new double[][]{
                {0.000,0.000,0.001,0.001,0.001,0.000,0.000},
                {0.000,0.002,0.012,0.020,0.012,0.002,0.000},
                {0.001,0.012,0.068,0.109,0.068,0.012,0.001},
                {0.001,0.020,0.109,0.172,0.109,0.020,0.001},
                {0.001,0.012,0.068,0.109,0.068,0.012,0.001},
                {0.000,0.002,0.012,0.020,0.012,0.002,0.000},
                {0.000,0.000,0.001,0.001,0.001,0.000,0.000}
        };
        for(int i=0;i<7;i++){
            for(int y=0;y<7;y++){
                Log.e("value at ", " "+i+" "+y+" is "+filter[i][y]);
            }
        }
        Bitmap blurBitmap=Bitmap.createBitmap(image.getWidth(),image.getHeight(),image.getConfig());
        Log.e("started"," ");
        for(int i=0;i<image.getWidth();i++){
            for(int j=0;j<image.getHeight();j++){
                int pixel = image.getPixel(i, j);
                int a=Color.alpha(pixel);
                int r=Color.red(pixel);
                int g=Color.green(pixel);
                int b=Color.blue(pixel);
                int [][] filterContour=new int[7][7];
                for(int x=i-3,xcount=0;x<i+4;x++,xcount++){
                    for(int y=j-3,ycount=0;y<j+4;y++,ycount++){
                        if(x<0 || x>=image.getWidth() || y<0 || y>=image.getHeight()){
                            filterContour[xcount][ycount]=0;
                        }else {
                            filterContour[xcount][ycount]=image.getPixel(x,y);
                        }
                    }
                }
                double factorR=0,factorG=0,factorB=0;
                for(int x=0;x<7;x++){
                    for(int y=0;y<7;y++){
                        factorR+=(((double)Color.red(filterContour[x][y]))*filter[x][y]);
                        factorG+=(((double)Color.green(filterContour[x][y]))*filter[x][y]);
                        factorB+=(((double)Color.blue(filterContour[x][y]))*filter[x][y]);
                    }
                }
//                factorR/=factor;
//                factorG/=factor;
//                factorB/=factor;
                if(factorR<0){
                    factorR=0;
                }else if(factorR>255){
                    factorR=255;
                }
                if(factorG<0){
                    factorG=0;
                }else if(factorG>255){
                    factorG=255;
                }
                if(factorB<0){
                    factorB=0;
                }else if(factorB>255){
                    factorB=255;
                }
                blurBitmap.setPixel(i,j,Color.argb(Color.alpha(pixel),(int)factorR,(int)factorG,(int)factorB));
                if(i==10){
                    Log.e("comparision"," "+pixel+"v"+Color.argb(Color.alpha(pixel),(int)factorR,(int)factorG,(int)factorB));
                }
            }
            Log.e("x"," "+i);
        }
        Log.e("ended"," ");
        return blurBitmap;
    }

    public static void makeKernel(float radius) {
        int r = (int)Math.ceil(radius);
        int rows = r*2+1;
        float[] matrix = new float[rows];
        float sigma = radius/3;
        float sigma22 = 2*sigma*sigma;
        float sigmaPi2 = (float) (2*Math.PI*sigma);
        float sqrtSigmaPi2 = (float)Math.sqrt(sigmaPi2);
        float radius2 = radius*radius;
        float total = 0;
        int index = 0;
        for (int row = -r; row <= r; row++) {
            float distance = row*row;
            if (distance > radius2)
                matrix[index] = 0;
            else
                matrix[index] = (float)Math.exp(-(distance)/sigma22) / sqrtSigmaPi2;
            total += matrix[index];
            index++;
        }
        for (int i = 0; i < rows; i++) {
            matrix[i] /= total;

        }
        for(int i=0;i<rows;i++){
            Log.e("valkue"," "+i+" "+matrix[i]);
        }
    }
}
