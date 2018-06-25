package app.leo.jenison.com.blurimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.graphics.Paint.Style.FILL;

public class BlurView extends View {


    /**
     * @SIGMA  is the radius of blur
     */
    public static final int SIGMA=5;
    /**
     * @N_TAP id the normalization matix's length
     */
    public static final int N_TAP=15;


    public BlurView(Context context) {
        super(context);
        init();
    }

    public BlurView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BlurView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    Paint originalPaint;
    Paint blurPaint;
    Rect originalRect;
    Rect blurRect;
    private void init(){
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        Bitmap originalImage = BitmapFactory.decodeResource(getResources(), R.drawable.autumn);
        Bitmap blurImage=MatrixConvolution.generateBlurImage(BitmapFactory.decodeResource(getResources(), R.drawable.autumn),MatrixConvolution.getGaussianKernel(SIGMA, N_TAP));
        originalPaint=new Paint();
        blurPaint=new Paint();
        originalRect=new Rect(0,0, blurImage.getWidth(), blurImage.getHeight());
        blurRect=new Rect(blurImage.getWidth(),0, blurImage.getWidth()*2, blurImage.getHeight());

        Matrix tranformMatrix = new Matrix();
        BitmapShader shader = new BitmapShader(originalImage, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        originalPaint.setStyle(FILL);
        originalPaint.setDither(true);
        originalPaint.setAntiAlias(true);
        shader.setLocalMatrix(tranformMatrix);
        originalPaint.setShader(shader);

        Matrix blurTranformMatrix = new Matrix();
        blurTranformMatrix.postTranslate(originalImage.getWidth(),0);
        BitmapShader blurShader = new BitmapShader(blurImage, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        blurPaint.setStyle(FILL);
        blurPaint.setDither(true);
        blurPaint.setAntiAlias(true);
        blurShader.setLocalMatrix(blurTranformMatrix);
        blurPaint.setShader(blurShader);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(originalRect,originalPaint);
        canvas.drawRect(blurRect,blurPaint);
    }

}
