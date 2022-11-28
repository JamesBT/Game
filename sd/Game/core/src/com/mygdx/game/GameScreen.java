package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.Characters.*;


public class GameScreen extends BaseScreen{
    //buat player + enemy
    private Player player;
    private boolean status = true;
    private Enemy tesEnemy;
    private Enemy tesEnemy2;

    //buat peta
    private TiledMap tiledMap;
    private OrthographicCamera tiledCamera;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    //buat asset tambahan

    public GameScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void create(){
        //buat player
        player = new Player();
        float t=0.15f;
        player.storeAnimation("down", GameUtils.parseSpriteSheet(
                "sprites/down.png", 4, 2, new int[] {0,1}, t, Animation.PlayMode.LOOP_PINGPONG));
        player.storeAnimation("left", GameUtils.parseSpriteSheet(
                "sprites/left.png", 4, 2, new int[] {0,1 }, t, Animation.PlayMode.LOOP_PINGPONG));
        player.storeAnimation("right", GameUtils.parseSpriteSheet(
                "sprites/right.png", 4, 2, new int[] {0,1 }, t, Animation.PlayMode.LOOP_PINGPONG));
        player.storeAnimation("up", GameUtils.parseSpriteSheet(
                "sprites/up.png", 4, 2, new int[] { 0,1 }, t, Animation.PlayMode.LOOP_PINGPONG));
        player.setSize(48, 48);
        player.setEllipseBoundary(status);
        mainStage.addActor(player);
        //buat enemy
        tesEnemy = new Enemy1();
        tesEnemy.storeAnimation("down", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {48,49,50}, t, Animation.PlayMode.LOOP_PINGPONG));
        tesEnemy.storeAnimation("left", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {60,61,62}, t, Animation.PlayMode.LOOP_PINGPONG));
        tesEnemy.storeAnimation("right", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {72,73,74}, t, Animation.PlayMode.LOOP_PINGPONG));
        tesEnemy.storeAnimation("up", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {84,85,86}, t, Animation.PlayMode.LOOP_PINGPONG));
        tesEnemy.setSize(48, 48);

        tesEnemy.setEllipseBoundary(status);
        mainStage.addActor(tesEnemy);

        tesEnemy2 = new Enemy2();
        tesEnemy2.storeAnimation("down", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {51,52,53}, t, Animation.PlayMode.LOOP_PINGPONG));
        tesEnemy2.storeAnimation("left", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {63,64,65}, t, Animation.PlayMode.LOOP_PINGPONG));
        tesEnemy2.storeAnimation("right", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {75,76,77}, t, Animation.PlayMode.LOOP_PINGPONG));
        tesEnemy2.storeAnimation("up", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {87,88,89}, t, Animation.PlayMode.LOOP_PINGPONG));
        tesEnemy2.setSize(48, 48);

        tesEnemy2.setEllipseBoundary(status);
        mainStage.addActor(tesEnemy2);


        //buat music
        //animasi player
        //animasi enemy

        //buat map dan kamera
        tiledMap = new TmxMapLoader().load("map/map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false,viewWidth,viewHeight);
        tiledCamera.update();
        //buat layer walls/physic
        //buat node?

    }

    @Override
    public void update(float delta){

    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void dispose(){
//        bgm.dispose();
//        batch.dispose();
    }

}
