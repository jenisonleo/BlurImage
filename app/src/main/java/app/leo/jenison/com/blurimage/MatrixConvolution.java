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
        int [][] filter=new int[][]{
                {1,2,1},
                {2,4,2},
                {1,2,1}
        };
        for(int i=0;i<3;i++){
            for(int y=0;y<3;y++){
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
                int [][] filterContour=new int[3][3];
                for(int x=i-1,xcount=0;x<i+2;x++,xcount++){
                    for(int y=j-1,ycount=0;y<j+2;y++,ycount++){
                        if(x<0 || x>=image.getWidth() || y<0 || y>=image.getHeight()){
                            filterContour[xcount][ycount]=0;
                        }else {
                            filterContour[xcount][ycount]=image.getPixel(x,y);

                        }
                    }
                }
                int factorR=0,factorG=0,factorB=0;
                for(int x=0;x<3;x++){
                    for(int y=0;y<3;y++){
                        factorR+=Color.red(filterContour[x][y]*filter[x][y]);
                        factorG+=Color.green(filterContour[x][y]*filter[x][y]);
                        factorB+=Color.blue(filterContour[x][y]*filter[x][y]);
                    }
                }
                factorR/=factor;
                factorG/=factor;
                factorB/=factor;
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
                blurBitmap.setPixel(i,j,Color.argb(Color.alpha(pixel),factorR,factorG,factorB));
            }
            Log.e("x"," "+i);
        }
        Log.e("ended"," ");
        return blurBitmap;
    }
}
