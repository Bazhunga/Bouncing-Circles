package com.bouncythings.test_bouncing_things;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by kevin on 1/26/15.
 */
public class TaskBall {
    private String taskDesc;
    private String taskShort;
    private Context c;
    private int priority; //From 0 to 10
    private int colour;

    private Drawable circ;


    //Determined by the class based on priority
    private int radius;
    private int leftX;
    private int topY;
    private int rightX;
    private int bottomY;

    private int centre;
    private int xVelocity;
    private int yVelocity;

    //Defining behaviour within the window
    //These will be the limits within which the ball can move
    //Each ball will be different


    public TaskBall(String taskDesc, String taskShort, int priority, int colour, Context c) {
        this.taskDesc = taskDesc;
        this.taskShort = taskShort;
        this.priority = priority;
        this.colour = colour;
        circ = c.getResources().getDrawable(R.drawable.circle);
        setRadius(priority);
        setVelocity(priority);
        setCoords();
    }

    private void setVelocity(int priority){
        xVelocity = 30/priority;
        yVelocity = 22/priority;
    }
    private void setCoords(){
        leftX = 0;
        topY = 0;
        rightX = leftX + 2 * radius;
        bottomY = topY + 2 * radius;
    }

    public Drawable getCirc() {
        return circ;
    }

    public void setCirc(Drawable circ) {
        this.circ = circ;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskShort() {
        return taskShort;
    }

    public void setTaskShort(String taskShort) {
        this.taskShort = taskShort;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public int getRadius() {
        return radius;
    }

    private void setRadius(int priority) {
        this.radius = priority * 20;
    }


    public int getLeftX() {
        return leftX;
    }

    public int getTopY() {
        return topY;
    }

    public int getRightX() {
        return rightX;
    }

    public int getBottomY() {
        return bottomY;
    }

    public void setLeftX(int leftX) {
        this.leftX = leftX;
        setRightX();
    }

    public void setTopY(int topY) {
        this.topY = topY;
        setBottomY();
    }

    private void setRightX() {
        rightX = leftX + 2 * radius;
    }

    private void setBottomY() {
        bottomY = topY + 2 * radius;
    }



    public int getCentre() {
        return centre;
    }

    public void setCentre(int centre) {
        this.centre = centre;
    }

    public int getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }
}
