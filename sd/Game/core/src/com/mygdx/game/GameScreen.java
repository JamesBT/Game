package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Characters.*;

import javax.xml.soap.Node;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameScreen extends BaseScreen {
    //buat player + enemy
    private Player player;
    private boolean status = true;
    private Enemy tesEnemy;
    private Enemy tesEnemy2;

    //initialize node
    private Nodes node0;
    private Nodes node1;
    private Nodes node2;
    private Nodes node3;
    private Nodes node4;
    private Nodes node6;
    private Nodes node7;
    private Nodes node8;
    private Nodes node9;
    private Nodes node10;
    private Nodes node11;
    private Nodes node12;


    //buat peta
    private TiledMap tiledMap;
    private OrthographicCamera tiledCamera;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private ArrayList<BaseActor> wallList;
    private int tileSize = 32;
    private int tileCountWidth = 30;
    private int tileCountHeight = 30;
    final int mapWidth = tileSize * tileCountWidth;
    final int mapHeight = tileSize * tileCountHeight;

    //buat pause
    private int[] backgroundLayers = { 0, 1};
    private int[] foregroundLayers = { 2 };
    private float elapsedTime = 0f;

    //buat music
    private Music bgm;

    //buat animation
    private SpriteBatch batch;
    private TextureAtlas tALeft;
    private TextureAtlas tARight;
    private TextureAtlas tAUp;
    private TextureAtlas tADown;
    private Animation<TextureRegion> animationLeft;
    private Animation<TextureRegion> animationRight;
    private Animation<TextureRegion> animationUp;
    private Animation<TextureRegion> animationDown;

    //buat asset tambahan
    BitmapFont bitmapFont;

    public GameScreen(BaseGame g) {
        super(g);
    }

    @Override
    public void create() {
        bitmapFont = new BitmapFont();

        //initialize player
        player = new Player();
        float t = 0.15f;
        player.storeAnimation("down", GameUtils.parseSpriteSheet(
                "sprites/down.png", 8, 2, new int[] {0,1,2,3}, t, PlayMode.LOOP_PINGPONG));
        player.storeAnimation("left", GameUtils.parseSpriteSheet(
                "sprites/left.png", 8, 2, new int[] {0,1,2,3}, t, PlayMode.LOOP_PINGPONG));
        player.storeAnimation("right", GameUtils.parseSpriteSheet(
                "sprites/right.png", 8, 2, new int[] {0,1,2,3}, t, PlayMode.LOOP_PINGPONG));
        player.storeAnimation("up", GameUtils.parseSpriteSheet(
                "sprites/up.png", 8, 2, new int[] {0,1,2,3}, t, PlayMode.LOOP_PINGPONG));
        player.setSize(48, 48);
        player.setEllipseBoundary(status);
        mainStage.addActor(player);

        //buat musuh
        tesEnemy = new Enemy1();
        tesEnemy2 = new Enemy2();
        tesEnemy.storeAnimation("down", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {48,49,50}, t, PlayMode.LOOP_PINGPONG));
        tesEnemy.storeAnimation("left", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {60,61,62}, t, PlayMode.LOOP_PINGPONG));
        tesEnemy.storeAnimation("right", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {72,73,74}, t, PlayMode.LOOP_PINGPONG));
        tesEnemy.storeAnimation("up", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {84,85,86}, t, PlayMode.LOOP_PINGPONG));
        tesEnemy.setSize(48, 48);
        tesEnemy.setEllipseBoundary(status);
        mainStage.addActor(tesEnemy);

        tesEnemy2.storeAnimation("down", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {51,52,53}, t, PlayMode.LOOP_PINGPONG));
        tesEnemy2.storeAnimation("left", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {63,64,65}, t, PlayMode.LOOP_PINGPONG));
        tesEnemy2.storeAnimation("right", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {75,76,77}, t, PlayMode.LOOP_PINGPONG));
        tesEnemy2.storeAnimation("up", GameUtils.parseSpriteSheet(
                "enemy/enemy.png", 12, 8, new int[] {87,88,89}, t, PlayMode.LOOP_PINGPONG));
        tesEnemy2.setSize(48, 48);
        tesEnemy2.setEllipseBoundary(status);
        mainStage.addActor(tesEnemy2);

        //buat animasi - player
        batch = new SpriteBatch();
//        idle = new Texture(Gdx.files.internal("sprites/idle.png"));
        tALeft = new TextureAtlas(Gdx.files.internal("sprites/left.pack"));
        tARight = new TextureAtlas(Gdx.files.internal("sprites/right.pack"));
        tAUp = new TextureAtlas(Gdx.files.internal("sprites/up.pack"));
        tADown = new TextureAtlas(Gdx.files.internal("sprites/down.pack"));
        animationLeft = new Animation<TextureRegion>(1/7f,tALeft.getRegions());
        animationRight = new Animation<TextureRegion>(1/7f,tARight.getRegions());
        animationUp = new Animation<TextureRegion>(1/7f,tAUp.getRegions());
        animationDown = new Animation<TextureRegion>(1/7f,tADown.getRegions());

        //buat music
        bgm = Gdx.audio.newMusic(Gdx.files.internal("sound & music/miami-game.ogg"));
        bgm.setVolume(0.1f);
        bgm.setLooping(true);
        bgm.play();

        //buat map dan kamera
        tiledMap = new TmxMapLoader().load("map/testing1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false,viewWidth,viewHeight);
        tiledCamera.update();

        wallList = new ArrayList<BaseActor>();

        //buat node
        node0 = new Nodes();
        node1 = new Nodes();
        node2 = new Nodes();
        node3 = new Nodes();
        node4 = new Nodes();
        node6 = new Nodes();
        node7 = new Nodes();
        node8 = new Nodes();
        node9 = new Nodes();
        node10 = new Nodes();
        node11 = new Nodes();
        node12 = new Nodes();

        //buat object dalam peta
        MapObjects objects = tiledMap.getLayers().get("Object").getObjects();
        for (MapObject object : objects) {
            String name = object.getName();

            RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
            Rectangle r = rectangleMapObject.getRectangle();

            switch (name) {
                case "player":
                    player.setPosition(r.x, r.y);
                    break;
                case "tesEnemy":
                    tesEnemy.setPosition(r.x, r.y);
                    break;
                case "tesEnemy2":
                    tesEnemy2.setPosition(r.x, r.y);
                    break;

                //titik node
                case "node0":
                    node0.setX(96);
                    node0.setY(512);
                    break;
                case "node1":
                    node1.setX(96);
                    node1.setY(288);
                    break;
                case "node2":
                    node2.setX(320);
                    node2.setY(512);
                    break;
                case "node3":
                    node3.setX(320);
                    node3.setY(288);
                    break;
                case "node4":
                    node4.setX(96);
                    node4.setY(96);
                    break;
                case "node6":
                    node6.setX(448);
                    node6.setY(96);
                    break;
                case "node7":
                    node7.setX(608);
                    node7.setY(288);
                    break;
                case "node8":
                    node8.setX(608);
                    node8.setY(512);
                    break;
                case "node9":
                    node9.setX(832);
                    node9.setY(288);
                    break;
                case "node10":
                    node10.setX(832);
                    node10.setY(96);
                    break;
                case "node11":
                    node11.setX(832);
                    node11.setY(512);
                    break;
                case "node12":
                    node12.setX(448);
                    node12.setY(288);
                    break;
                default:
                    System.err.println("Unknown tilemap object " + name);
            }

        }

        //buat tembok
        objects = tiledMap.getLayers().get("Physic").getObjects();
        for (MapObject object : objects) {
            RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
            Rectangle r = rectangleMapObject.getRectangle();

            BaseActor solid = new BaseActor();
            solid.setPosition(r.x, r.y);
            solid.setSize(r.width, r.height);
            solid.setRectangleBoundary();
            wallList.add(solid);
        }

        //matrix nodes
        int[][] matrix = {
                //0 1  2  3  4  5  6  7  8  9 10 11 -->node ke
                {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0},
        };
    }

    @Override
    public void update(float delta) {
        //player movement
        float playerSpeed = 500;
        float tesEnemySpeed = 500;
        player.setVelocityXY(0, 0);
        tesEnemy.setVelocityXY(0,0);
        tesEnemy2.setVelocityXY(0,0);

        //input handling PLAYER
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            player.setVelocityXY(-playerSpeed, 0);
            player.setActiveAnimation("left");
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            player.setVelocityXY(playerSpeed, 0);
            player.setActiveAnimation("right");
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            player.setVelocityXY(0, playerSpeed);
            player.setActiveAnimation("up");
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            player.setVelocityXY(0, -playerSpeed);
            player.setActiveAnimation("down");
        }
        if (player.getSpeed() < 1) {
            player.pauseAnimation();
            player.setAnimationFrame(1);
        } else {
            player.startAnimation();
        }


        //collision detection
        for (BaseActor wall : wallList) {
            player.overlaps(wall, true);
            tesEnemy.overlaps(wall, true);
            tesEnemy2.overlaps(wall, true);
        }

        //Overlaps Player and Enemy
//        if(player.overlaps(tesEnemy, false) || player.overlaps(tesEnemy2, false)){
//            game.setScreen(new GameOver(game));
//            bgm.stop();
//        }

        //camera adjustment
        Camera mainCamera = mainStage.getCamera();

        //center camera on player
        mainCamera.position.x = player.getX() + player.getOriginX();
        mainCamera.position.y = player.getY() + player.getOriginY();

        //bound camera to layout
        mainCamera.position.x = MathUtils.clamp(
                mainCamera.position.x, viewWidth / 2, mapWidth - viewWidth / 2);
        mainCamera.position.y = MathUtils.clamp(
                mainCamera.position.y, viewHeight / 2, mapHeight - viewHeight / 2);
        mainCamera.update();

        //adjust tilemap camera to stay in sync with main camera
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;
        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);

        //detect player dekat node berapa
        ArrayList<Nodes> arr = new ArrayList<Nodes>();
        arr.add(node0);
        arr.add(node1);
        arr.add(node2);
        arr.add(node3);
        arr.add(node4);
        arr.add(node6);
        arr.add(node7);
        arr.add(node8);
        arr.add(node9);
        arr.add(node10);
        arr.add(node11);
        arr.add(node12);
        double shortestx;
        double shortesty;

        Nodes tujuan;
        for (int i=0; i<arr.size();i++){
            double x = player.getX() - arr.get(i).getX();
            double y = player.getY() - arr.get(i).getY();

            shortestx = x;
            shortesty = y;

          if (x<shortestx || y<shortesty){
              shortestx = x;
              shortesty = y;
              System.out.println(arr.get(i));
              tujuan = arr.get(i);
          }
        }

        //hitung jarak atr player dan musuh
        double jrkx1 = Math.abs(player.getX() - tesEnemy.getX());
        double jrky1 = Math.abs(player.getY() - tesEnemy.getY());
        double jrkx2 = Math.abs(player.getX() - tesEnemy2.getX());
        double jrky2 = Math.abs(player.getY() - tesEnemy2.getY());
        //buat musuhe keliatan/tidak
//        if(jrkx1 < 128 && jrky1 < 128){
//            tesEnemy.setVisible(true);
//        }else{
//            tesEnemy.setVisible(false);
//        }
        //buat musuhe keliatan/tidak
//        if(jrkx2 < 128 && jrky2 < 128){
//            tesEnemy2.setVisible(true);
//        }else{
//            tesEnemy2.setVisible(false);
//        }

        //enemy movement
        //ke kiri
        tesEnemy.setVelocityXY(-tesEnemySpeed,0);
        //ke kanan
        tesEnemy.setVelocityXY(tesEnemySpeed,0);
        //ke atas
        tesEnemy.setVelocityXY(0,tesEnemySpeed);
        //ke bawah
        tesEnemy.setVelocityXY(0,-tesEnemySpeed);

//        System.out.println("player x: "+player.getX());
//        System.out.println("player y: "+player.getY());
//        System.out.println("enemy x: "+tesEnemy.getX());
//        System.out.println("enemy y: "+tesEnemy.getY());

    }

    @Override
    public void render(float delta) {
        uiStage.act(delta);
        if (!isPaused()) {
            mainStage.act(delta);
            update(delta);
        }

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.render(backgroundLayers);
        mainStage.draw();
        tiledMapRenderer.render(foregroundLayers);
        uiStage.draw();
        elapsedTime += Gdx.graphics.getDeltaTime();


        batch.begin();
        batch.end();

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.P) togglePaused();
        if (keycode == Keys.R) game.setScreen(new GameScreen(game));
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void dispose(){
        bgm.dispose();
        batch.dispose();
    }
}
