package info.p445m.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



/**
 * Created by pmg on 7/22/2017.
 */

public class AccView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    public  Runnable runner;
    public Paint mypaint;
    private Paint black;
    private Paint red = new Paint();


    private String acc;
    private String grav;
    private String linAcc;


    public AccView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        holder = getHolder();
        red.setARGB(255,255,0,0);
        holder.addCallback(this);

        black = new Paint();
        black.setARGB(255,0,0,0);

        mypaint = new Paint();
        mypaint.setTextSize((float)22.0);
        mypaint.setTypeface(Typeface.MONOSPACE);
        mypaint.setARGB(255,100,150,55);
        runner = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(34);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Canvas can  = holder.lockCanvas();
                    if (can != null) {
                        can.drawPaint(black);
                        if (acc != null)
                            can.drawText("acc:  " + acc, 20, 20, mypaint);
                        if (grav != null)
                            can.drawText("grav: "+grav, 20, 40, mypaint);
                        if (linAcc != null)
                            can.drawText("lAcc: "+linAcc, 20, 60, mypaint);
                        holder.unlockCanvasAndPost(can);
                    }

                }


            }
        };
        Thread thread = new Thread(runner);
        thread.start();

    }


    public void setText(CharSequence messgae) {

    }
    public void append(CharSequence message) {


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

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getGrav() {
        return grav;
    }

    public void setGrav(String grav) {
        this.grav = grav;
    }

    public String getLinAcc() {
        return linAcc;
    }

    public void setLinAcc(String linAcc) {
        this.linAcc = linAcc;
    }
}
