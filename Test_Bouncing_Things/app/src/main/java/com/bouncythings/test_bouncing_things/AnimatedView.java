package com.bouncythings.test_bouncing_things;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by kevin on 1/26/15.
 */
public class AnimatedView extends ImageView {
    private Context mContext;
    private int xVelocity = 20;
    private int yVelocity = 10;
    public static int maxWidth;
    public static int maxHeight;


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
    TaskBall circ3;
    ArrayList<TaskBall> taskBallList = new ArrayList();

    public AnimatedView(Context context, AttributeSet attrs)  {
        super(context, attrs);
        mContext = context;
        h = new Handler();
        circ1 = new TaskBall("Sample", "sam", 10, 0x709EF4, mContext);
        circ2 = new TaskBall("Simple", "sam", 8, 0x709EF4, mContext);
        //circ3 = new TaskBall("Simple", "sam", 6, 0x709EF4, mContext);
        taskBallList.add(circ1);
        taskBallList.add(circ2);
        //taskBallList.add(circ3);

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
        placeDrawables(circ2);

        //Draw them and watch them bounce
        circ1.getCirc().draw(c);
        circ2.getCirc().draw(c);
        detectCollision();

        //Ensure proper placements so there are no overlaps

        //Causes runnable r to be added to message queue (run after specified amount of time elapses,
        // Which is frame rate)

        h.postDelayed(r, FRAME_RATE);

    }

    private void detectCollision(){
        /*
        Distance between 2 points is calculated by d = sqrt((x2 - x1)^2 - (y^2 - y^1)^2)
         */
        double distanceApart = 0;
        double touchingDistance = 0;

        //Coordinates of the ball centres
        int x1, y1, x2, y2;

        for (int i = 0; i < taskBallList.size(); i++){
            for (int j = i + 1; j < taskBallList.size(); j++){
                x1 = taskBallList.get(i).getCentre()[0];
                y1 = taskBallList.get(i).getCentre()[1];
                x2 = taskBallList.get(j).getCentre()[0];
                y2 = taskBallList.get(j).getCentre()[1];
                distanceApart = calcDistanceApart(x1, y1, x2, y2);
                touchingDistance = getTouchingDistance(taskBallList.get(i), taskBallList.get(j));

                if (distanceApart <= touchingDistance && (circ1.isPreviouslyTouching() != true) && (circ1.isPreviouslyTouching() != true)) {
                    calcCollisionSpeed(circ1, circ2);
                    circ1.setPreviouslyTouching(true);
                    circ2.setPreviouslyTouching(true);
                }
                else if (distanceApart <= touchingDistance && (circ1.isPreviouslyTouching() != false) && (circ1.isPreviouslyTouching() != false)){
                    //do nothing
                }
                else if (distanceApart >= touchingDistance) {
                    circ1.setPreviouslyTouching(false);
                    circ2.setPreviouslyTouching(false);
                }

            }
        }
    }
    private double calcDistanceApart(int x1, int y1, int x2, int y2){
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }
    private int getTouchingDistance (TaskBall circ1, TaskBall circ2){
        return circ1.getRadius() + circ2.getRadius();
    }

    public void calcCollisionSpeed (TaskBall circ1, TaskBall circ2) {
        //Perfectly elastic collision model
        //Remember, m1u1 + m2u2 = m1v1 + m2v2 where u is speed before collision and v is speed after collision
        /*
        Do for both x and y
        v1 = ((u1*(m1 - m2) + 2 * m2 * u2)/(m1 + m2))
        v2 = ((u2*(m2 - m1) + 2 * m1 * u1)/(m1 + m2))
         */

        int m1 = circ1.getPriority();
        int m2 = circ2.getPriority();

        //X-coordinates
        int u1x = circ1.getxVelocity();
        int u2x = circ2.getxVelocity();
        int v1x = ((u1x*(m1 - m2) + 2 * m2 * u2x)/(m1 + m2));
        int v2x = ((u2x*(m2 - m1) + 2 * m1 * u1x)/(m1 + m2));
        circ1.setxVelocity(v1x);
        circ2.setxVelocity(v2x);

        //Y-coordinates
        int u1y = circ1.getyVelocity();
        int u2y = circ2.getyVelocity();
        int v1y = ((u1y*(m1 - m2) + 2 * m2 * u2y)/(m1 + m2));
        int v2y = ((u2y*(m2 - m1) + 2 * m1 * u1y)/(m1 + m2));
        circ1.setyVelocity(v1y);
        circ2.setyVelocity(v2y);


    }
    private void placeDrawables(TaskBall circ){
        circ.setLeftX(circ.getLeftX() + circ.getxVelocity());
        circ.setTopY(circ.getTopY() + circ.getyVelocity());
        if (circ.getLeftX() < 0 || circ.getRightX() > this.getWidth() &&  circ.isPreviouslyWallTouching() == false){
            circ.reverseXVelocity();
            circ.setPreviouslyWallTouching(true);
        }
        else if (circ.getLeftX() < 0 || circ.getRightX() > this.getWidth() &&  circ.isPreviouslyWallTouching() == true){
            //do nothing
        }
        else if (circ.getLeftX() > 0 || circ.getRightX() < this.getWidth()){
            circ.setPreviouslyWallTouching(false);
        }

        if (circ.getTopY() < 0 || circ.getBottomY() > this.getHeight() && circ.isPreviouslyWallTouching() == false){

            circ.reverseYVelocity();
            circ.setPreviouslyWallTouching(true);

        }
        else if (circ.getTopY() < 0 || circ.getBottomY() > this.getHeight() &&  circ.isPreviouslyWallTouching() == true){
            //do nothing
        }
        else if (circ.getTopY() > 0 || circ.getBottomY() < this.getHeight()){
            circ.setPreviouslyWallTouching(false);
        }
        circ.findCentre();

//        Log.d("Dname", circ.getTaskDesc());
//        Log.d("XLEFT", String.valueOf(circ.getLeftX()));
//        Log.d("XRIGHT", String.valueOf(circ.getRightX()));
//        Log.d("YTOP", String.valueOf(circ.getTopY()));
//        Log.d("YBOT", String.valueOf(circ.getBottomY()));
//        Log.d("RAD ", String.valueOf(circ.getRadius()));
//        Log.d("Xvelo", String.valueOf(circ.getxVelocity()));


        circ.getCirc().setBounds(circ.getLeftX(), circ.getTopY(), circ.getRightX(), circ.getBottomY());
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        maxWidth = this.getWidth();
        Log.d("MAXWIDTH HASHDKALSHDLK", String.valueOf(maxWidth));
        maxHeight = this.getHeight() ;
    }

}
