package ru.ondigit.bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.tools.javac.comp.Flow;

public class GameScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 800;

    private ShapeRenderer shaeRenderer;
    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;
    private Flappee flapee;
    private Array<Flower> flowers = new Array<Flower>();
    private static final float GAP_BEETWEN_FLOWERS = 200F;

    @Override
    public void render(float delta) {
        clearScreen();
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.end();
        shaeRenderer.begin(ShapeRenderer.ShapeType.Line);
        flapee.drawDebug(shaeRenderer);

        drawDebug();
        shaeRenderer.end();
        update(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2,0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT,camera);
        shaeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        flapee = new Flappee();
        flapee.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT /2);
    }

    private void update(float delta) {
        flapee.update();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) flapee.flyUp();
        blockFlappeeLeavingTheWorld();
        updateFlowers(delta);

    }

    private void updateFlowers(float delta) {
        for (Flower flower : flowers) {
            flower.update(delta);
        }
        checkIfNewFlowerIsNeeded();
        removeFlowersIfPassed();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,Color.BLACK.b,Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void blockFlappeeLeavingTheWorld() {
        flapee.setPosition(flapee.getX(), MathUtils.clamp(flapee.getY(),0,WORLD_HEIGHT));
    }

    private void createNewFlower() {
        Flower flower = new Flower();
        flower.setPosition(WORLD_WIDTH + Flower.WIDTH);
        flowers.add(flower);
    }

    private void checkIfNewFlowerIsNeeded() {
        if (flowers.size == 0) {
            createNewFlower();
        } else {
            Flower flower = flowers.peek();
            if (flower.getX() < WORLD_WIDTH - GAP_BEETWEN_FLOWERS) {
                createNewFlower();
            }
        }
    }

    private void removeFlowersIfPassed() {
        if (flowers.size > 0) {
            Flower firstFlower = flowers.first();
            if (firstFlower.getX() < -Flower.WIDTH) {
                flowers.removeValue(firstFlower,true);
            }
        }
    }

    private void drawDebug() {
        for (Flower flower : flowers) {
            flower.drawDebug(shaeRenderer);
        }
    }

}
