package com.bouncythings.test_bouncing_things;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by kevin on 1/26/15.
 */
public class AnimatedView extends ImageView {
    private Context mContext;
    private int xVelocity = 20;
    private int yVelocity = 10;

    // nts
    // Allows you to send and process (in this case) Runnable obj associated with thread's MessageQueue
    // Each handler instance associated with single thread and that thread's message queue
    // Create new Handler --> bound to thread creating it
    // Will deliver messages and runnables to that queue and execute them as they come out of message queue
    // In this case, it's used to enqueue the drawing of the circles on a different thread than its own

    private Handler h;
    private final int FRAME_RATE =20;
    TaskBall circ1;
    TaskBall circ2;

    public AnimatedView(Context context, AttributeSet attrs)  {
        super(context, attrs);
        mContext = context;
        h = new Handler();
        circ1 = new TaskBall("Sample", "sam", 10, 0x709EF4, mContext);
        circ2 = new TaskBall("Simple", "sam", 8, 0x709EF4, mContext);

    }
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            //Force the view to draw
            invalidate();
        }
    };
    protected void onDraw(Canvas c) {

//        drawBouncy(circ, c, 0, 0);
//        drawBouncy(circ2, c, 100, 200);

//        Drawable circle = mContext.getResources().getDrawable(R.drawable.circle);
//        circle.setBounds(0, 0, 300, 300);
//        circle.draw(c);
//        circle.getBounds


        //You need to set the bounds of all the circ objects before drawing them
        //Need to adjust the placeDrawables such that the x and y are properties of objects
        placeDrawables(circ1);
        //placeDrawables(circ2);


        //Draw them and watch them bounce
        Log.d("OMGBOUNDSLOLOLOL", String.valueOf(circ1.getCirc().getBounds()));
        circ1.getCirc().draw(c);
        //circ2.getCirc().draw(c);

        //Ensure proper placements so there are no overlaps

        //



        //Causes runnable r to be added to message queue (run after specified amount of time elapses,
        // Which is frame rate)

        h.postDelayed(r, FRAME_RATE);
    }

    private void placeDrawables(TaskBall circ){
//        if (x<0 && y <0) {
//            x = startx;
//            y = starty;
//        } else {
//            x += xVelocity;
//            y += yVelocity;
//            if ((x > this.getWidth() || (x < 0))) {
//                xVelocity = xVelocity*-1;
//            }
//            if ((y > this.getHeight()  || (y < 0))) {
//                yVelocity = yVelocity*-1;
//            }
//        }
        Log.d("XLEFT", String.valueOf(circ.getLeftX()));
        circ.setLeftX(circ.getLeftX() + circ.getxVelocity());
        circ.setTopY(circ.getTopY() + circ.getyVelocity());
        if (circ.getLeftX() < 0 || circ.getRightX() > this.getWidth() ){
            circ.setxVelocity(circ.getxVelocity() * -1);
        }

        if (circ.getTopY() < 0 || circ.getBottomY() > this.getHeight()){
            circ.setyVelocity(circ.getyVelocity() * -1);
        }
        Log.d("Dname", circ.getTaskDesc());
        Log.d("XLEFT", String.valueOf(circ.getLeftX()));
        Log.d("XRIGHT", String.valueOf(circ.getRightX()));
        Log.d("YTOP", String.valueOf(circ.getTopY()));
        Log.d("YBOT", String.valueOf(circ.getBottomY()));
        Log.d("RAD ", String.valueOf(circ.getRadius()));
        Log.d("Xvelo", String.valueOf(circ.getxVelocity()));


        circ.getCirc().setBounds(circ.getLeftX(), circ.getTopY(), circ.getRightX(), circ.getBottomY());
    }


}
