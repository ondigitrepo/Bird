package ru.ondigit.bird;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Flappee {
    private static final float COLLISION_RADIUS =  48f;
    private final Circle collisionCircle;

    private  float x = 0;
    private  float y = 0;

    private static final float DIVE_ACCELL = 0.3F;
    private float ySpeed = 0;
    private static final float FLY_ACCELL = 5F;

    public Flappee() {
        collisionCircle = new Circle(x,y,COLLISION_RADIUS);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(collisionCircle.x, collisionCircle.y, collisionCircle.radius);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateCollisionCircle();
    }

    private void updateCollisionCircle() {
        collisionCircle.setY(this.y);
        collisionCircle.setX(this.x);
    }

    public void update() {
        ySpeed -= DIVE_ACCELL;
        setPosition(x,y + ySpeed);
    }

    public void flyUp() {
        ySpeed = FLY_ACCELL;
        setPosition(x,y+ySpeed);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
}
