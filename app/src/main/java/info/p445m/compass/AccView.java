package info.p445m.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by pmg on 7/22/2017.
 */

public class AccView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    public  Runnable runner;
    public Paint mypaint;
    public AccView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        holder = getHolder();
        holder.addCallback(this);
        mypaint = new Paint();
        mypaint.setARGB(200,100,50,55);
        runner = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Canvas can  = holder.lockCanvas();
                    can.drawText("this ia text", 10,10,mypaint);
                    holder.unlockCanvasAndPost(can);
                }


            }
        };

    }

    public void set_text(String message) {

    }
    public void setText(CharSequence messgae) {

    }
    public void append(CharSequence message) {
        Log.e("AccView", "append called");

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
