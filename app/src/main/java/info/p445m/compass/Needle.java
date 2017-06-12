package info.p445m.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
//import android.location.GpsSatellite;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pmg on 6/7/2017.
 */

public class Needle extends View {
    public Needle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    float ytext=10;
    float multiplier;
    int width;
    int height;
    float angleDeg;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ytext=10;
        Paint oldcolor = new Paint();
        oldcolor.setARGB(255,0,0,199);
        Paint p = new Paint();
        Paint black = new Paint();
        Paint green = new Paint();
        Paint red = new Paint();
        red.setARGB(255,255,0,0);

        green.setStyle(Paint.Style.STROKE);
        green.setARGB(200, 0, 255, 0);
        black.setARGB(255, 0, 0, 0);
        canvas.drawPaint(black);
        width = getWidth();
        height = getHeight(); // choose the smaller to fit the circle
        multiplier = width < height? width/2: height/2;
        canvas.drawCircle(width/2,width/2,1, green);
        canvas.drawCircle(width/2,width/2,width/2, green);
        canvas.drawLine(0, width/2, width, width/2, green);
        canvas.drawLine(width/2,0, width/2, width, green);

        p.setARGB(255, 255, 255, 255);

        // roate right so west is start of measure.  Or, east... ummm
        float angle = (float) Math.toRadians(angleDeg);
        float xraw = (float)Math.cos(angle) * 1 * multiplier;
        float yraw = (float)Math.sin(angle) * 1 * multiplier;
        float x = xraw + width/2;
        float y = -(yraw - width/2);
        canvas.drawCircle(x, y, (float) 3, p);
        canvas.drawLine(width/2, width/2, x, y, red);
        //canvas.drawText("blah", (float) 1.0, (float) 1.0, p);
        //canvas.drawRect((float)0, (float)0, (float)width, (float)height, p);
/*
        for (GpsSatellite s: satlist) {
            plotsat(canvas, s, p);
            canvas.drawText(String.format("%3.1f,%3.1f",s.getAzimuth(),
                    s.getElevation()), (float) 10,
                    (float) ytext, p);
            ytext=ytext+15;
            //ytext = ytext % height;
            //ditText t = (EditText) findViewById(R.id.r_debug);
            //t.append(String.format("%f, %f%n", xraw, yraw));
        }
        for (GpsSatellite s: old_pos) {
            plotsat(canvas, s, oldcolor);
        }
*/


    }

}


// ===================== end boiler

/*



*/
/**
 * Created by Administrator on 1/9/2016.
 * Needs to know how to draw points given satellite coordinates.  This is because the
 * alternative is having show_sats be aware of the canvas geometry, which would be horrible
 *//*

public class Radar extends View {



    public void add_old_sat(GpsSatellite sat){
        old_pos.add(sat);
    }
    private void plotsat(Canvas canvas, GpsSatellite sat, Paint p) {
        float dist = (90 - sat.getElevation())/90; // 1.00 is max
        // roate right so west is start of measure.  Or, east... ummm
        float angle = (float) Math.toRadians(-sat.getAzimuth()+90);
        float xraw = (float)Math.cos(angle) * dist * multiplier;
        float yraw = (float)Math.sin(angle) * dist * multiplier;
        float x = xraw + width/2;
        float y = -(yraw - width/2);
        canvas.drawCircle(x, y, (float) 3, p);


    }

    public void clearlist() {
        satlist.clear();
    }
    public void addsat(GpsSatellite sat){
        satlist.add(sat);

    }

    public Radar(Context context, AttributeSet attrs) {
        super(context, attrs);
        satlist = (List)new ArrayList<GpsSatellite>();
        old_pos= (List) new ArrayList<GpsSatellite>();
    }

    */
/**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     *//*

    public Radar(Context context) {
        super(context);
        satlist = (List) new ArrayList<GpsSatellite>();
        old_pos= (List) new ArrayList<GpsSatellite>();
    }
}
*/
