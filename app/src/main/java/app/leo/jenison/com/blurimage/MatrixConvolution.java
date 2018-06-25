package app.leo.jenison.com.blurimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.security.InvalidParameterException;

public class MatrixConvolution {

    public static Bitmap generateBlurImage(Bitmap image,double[] [] filter){
        Bitmap blurBitmap=Bitmap.createBitmap(image.getWidth(),image.getHeight(),image.getConfig());
        Log.e("started"," ");
        int [][] filterContour=new int[filter.length][filter.length];
        for(int i=0;i<image.getWidth();i++){
            for(int j=0;j<image.getHeight();j++){
                int pixel = image.getPixel(i, j);
                int limit=filter.length/2;
                for(int x=i-limit,xcount=0;x<=i+limit;x++,xcount++){
                    for(int y=j-limit,ycount=0;y<=j+limit;y++,ycount++){
                        if(x<0 || x>=image.getWidth() || y<0 || y>=image.getHeight()){
                            filterContour[xcount][ycount]=0;
                        }else {
                            filterContour[xcount][ycount]=image.getPixel(x,y);
                        }
                    }
                }
                double factorR=0,factorG=0,factorB=0;
                for(int x=0;x<filter.length;x++){
                    for(int y=0;y<filter.length;y++){
                        factorR+=(((double)Color.red(filterContour[x][y]))*filter[x][y]);
                        factorG+=(((double)Color.green(filterContour[x][y]))*filter[x][y]);
                        factorB+=(((double)Color.blue(filterContour[x][y]))*filter[x][y]);
                    }
                }
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
            }
            Log.e("x"," "+i);
        }
        Log.e("ended"," ");
        return blurBitmap;
    }


    public static double[][] getGaussianKernel(int sigma, int kernelSize){
        if(kernelSize%2!=1 || kernelSize<=0){
            throw new InvalidParameterException("KernelSize can only be an odd number and be greater than zero");//NO I18N
        }
        double [] [] kernelMatrix=new double[kernelSize][kernelSize];
        int limit=kernelSize/2;
        int i=0,j=0;
        for(int x=-limit;x<=limit;x++){
            j=0;
            for(int y=-limit;y<=limit;y++){
                kernelMatrix[i][j]=(1.0/(2.0* Math.PI*((double)sigma)*((double)sigma)))*(Math.exp(-(((((double)x*x))+(((double) y*y)))/(2.0*((double)sigma)*((double)sigma)))));
                j++;
            }
            i++;
        }
        return kernelMatrix;
    }
}
