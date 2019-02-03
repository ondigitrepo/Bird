package ru.ondigit.bird;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Flower {
    private static final float COLLISION_RECTANGLE_WIDTH = 13F;
    private static final float COLLISION_RECTANGLE_HEIGHT = 447F;
    private static final float COLLISION_CIRCLE_RADIUS = 33F;
    private final Circle collisionCircle;
    private final Rectangle collisionRectangle;

    private float x = 0;
    private float y = 0;

    private static final float MAX_SPEED_PER_SECOND = 100F;
    public static final float WIDTH = COLLISION_CIRCLE_RADIUS * 2;

    public  Flower() {
        this.collisionRectangle = new Rectangle(x,y,COLLISION_RECTANGLE_WIDTH,COLLISION_RECTANGLE_HEIGHT);
        this.collisionCircle = new Circle(x+collisionRectangle.width /2, y+collisionRectangle.height, COLLISION_CIRCLE_RADIUS);
    }

    public void setPosition(float x) {
        this.x = x;
        updateCollisionCircle();
        updateCollisionRectangle();
    }

    private void updateCollisionCircle() {
        collisionCircle.setX(x + collisionRectangle.width / 2);
        collisionCircle.setY(y + collisionRectangle.height);
    }

    private void updateCollisionRectangle() {
        collisionRectangle.setX(x);
        collisionRectangle.setY(y);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(collisionCircle.x,collisionCircle.y,collisionCircle.radius);
        shapeRenderer.rect(collisionRectangle.x,collisionRectangle.y,collisionRectangle.width,collisionRectangle.height);
    }

    public void update(float delta) {
        setPosition(x - (MAX_SPEED_PER_SECOND * delta));
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

}

