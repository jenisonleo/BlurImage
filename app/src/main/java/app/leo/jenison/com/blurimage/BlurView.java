package app.leo.jenison.com.blurimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.graphics.Paint.Style.FILL;

public class BlurView extends View {
    private Matrix tranformMatrix;
    private BitmapShader shader;
    private Bitmap image;

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
    Paint paint;
    private void init(){
//        image=MatrixConvolution.generateBlurImage(BitmapFactory.decodeResource(getResources(), R.drawable.image));
        image=Bitmap.createBitmap(1000,1000, Bitmap.Config.ARGB_8888);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        paint=new Paint();
        tranformMatrix=new Matrix();
        shader=new BitmapShader(image, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(FILL);
        paint.setDither(true);
        paint.setAntiAlias(true);
        Log.e("measured"," "+getMeasuredWidth());
        float x = 1000 /(float) image.getWidth();
        float y = 1000/(float) image.getHeight();
        tranformMatrix.postScale(x,y);
        shader.setLocalMatrix(tranformMatrix);
        paint.setShader(shader);
        canvas.drawPaint(paint);
    }
}
