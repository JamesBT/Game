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
    private Nodes node5;
    private Nodes node6;
    private Nodes node7;
    private Nodes node8;
    private Nodes node9;
    private Nodes node10;
    private Nodes node11;
    private int tujuan;
    private int nodeawalenemy1;
    private int enemy1kenode;
    private int nodeawalenemy2;
    private int enemy2kenode;
    private ArrayList<Integer> pathenemy1;
    private ArrayList<Integer> pathenemy2;
    private Graph graf;
    //logic win
    private boolean playerenemy1;
    private boolean playerenemy2;
    private boolean enemy1player;
    private boolean enemy1enemy2;
    private boolean enemy2player;
    //buat score
    private String targetplayer;
    private String targetenemy1;
    private String targetenemy2;

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
    boolean reachnode1 = false;
    boolean reachnode2 = false;

    //buat asset tambahan
    BitmapFont bitmapFont;

    public GameScreen(BaseGame g) {
        super(g);
    }

    @Override
    public void create() {
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
        node5 = new Nodes();
        node6 = new Nodes();
        node7 = new Nodes();
        node8 = new Nodes();
        node9 = new Nodes();
        node10 = new Nodes();
        node11 = new Nodes();
        pathenemy1 = new ArrayList<>();
        pathenemy2 = new ArrayList<>();

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
                    node0.setY(96);
                    break;
                case "node1":
                    node1.setX(96);
                    node1.setY(320);
                    break;
                case "node2":
                    node2.setX(320);
                    node2.setY(96);
                    break;
                case "node3":
                    node3.setX(320);
                    node3.setY(320);
                    break;
                case "node4":
                    node4.setX(96);
                    node4.setY(512);
                    break;
                case "node5":
                    node5.setX(448);
                    node5.setY(320);
                    break;
                case "node6":
                    node6.setX(448);
                    node6.setY(512);
                    break;
                case "node7":
                    node7.setX(608);
                    node7.setY(320);
                    break;
                case "node8":
                    node8.setX(608);
                    node8.setY(96);
                    break;
                case "node9":
                    node9.setX(832);
                    node9.setY(320);
                    break;
                case "node10":
                    node10.setX(832);
                    node10.setY(96);
                    break;
                case "node11":
                    node11.setX(832);
                    node11.setY(512);
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

        //buat graph
        graf = new Graph();
//        graf.DFS(0,6);
        enemy1kenode=0;
        enemy2kenode=10;
        reachnode1=true;
        reachnode2=true;

        //buat score
        bitmapFont = new BitmapFont();
        targetplayer="Target: enemy1 enemy2";
        targetenemy1="Target musuh1: player enemy2";
        targetenemy2="Target musuh2: player enemy1";

        batch.begin();
        bitmapFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        bitmapFont.draw(batch,targetplayer,30,450);
        bitmapFont.draw(batch,targetenemy1,30,425);
        bitmapFont.draw(batch,targetenemy2,30,400);
        batch.end();

        //buat win/lose
        playerenemy1=false;
        playerenemy2=false;
        enemy1player=false;
        enemy1enemy2=false;
        enemy2player=false;
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
        if(player.overlaps(tesEnemy, false)){
            enemy1player=true;
        }
        if(player.overlaps(tesEnemy2,false)){
            enemy2player=true;
        }
        if(tesEnemy.overlaps(tesEnemy2,false)||tesEnemy2.overlaps(tesEnemy,false)){
            enemy1enemy2=true;
        }
        //jarak ke enemy1
        double jarakplayerenemy1 = Math.sqrt(Math.pow(player.getX()-tesEnemy.getX(),2) + Math.pow(player.getY() - tesEnemy.getY(),2));
        if(jarakplayerenemy1<128){
            playerenemy1=true;
        }
        double jarakplayerenemy2 = Math.sqrt(Math.pow(player.getX()-tesEnemy2.getX(),2) + Math.pow(player.getY() - tesEnemy2.getY(),2));
        if(jarakplayerenemy2<128){
            playerenemy2=true;
        }


        //visible atau tidak
        //hitung jarak antara player dan musuh
        double jrk1 = Math.sqrt(Math.pow(player.getX()-tesEnemy.getX(),2) + Math.pow(player.getY() - tesEnemy.getY(),2));
        double jrk2 = Math.sqrt(Math.pow(player.getX()-tesEnemy2.getX(),2) + Math.pow(player.getY() - tesEnemy2.getY(),2));
        //buat enemy1 keliatan/tidak
//        if(jrk1 < 128){
//            tesEnemy.setVisible(true);
//        }else{
//            tesEnemy.setVisible(false);
//        }
//        //buat enemy2 keliatan/tidak
//        if(jrk2 < 128){
//            tesEnemy2.setVisible(true);
//        }else{
//            tesEnemy2.setVisible(false);
//        }

        //ngeprint score
        if(playerenemy1 && !playerenemy2){
            targetplayer="Target: enemy2";
        }else if(!playerenemy1 && playerenemy2){
            targetplayer="Target: enemy1";
        }
        if(enemy1player && !enemy1enemy2){
            targetenemy1="Target musuh1: enemy2";
        }else if(!enemy1player && enemy1enemy2) {
            targetenemy1="Target musuh1: player";
        }
        if(enemy2player && !enemy1enemy2){
            targetenemy2="Target musuh2: enemy1";
        }else if(!enemy1player && enemy1enemy2){
            targetenemy2="Target musuh2: player";
        }

        //win/lose
        //win
        if(playerenemy1&&playerenemy2){
            game.setScreen(new WinningScreen(game));
            bgm.stop();
        }
        //lose/gameover
        if(enemy1player&&enemy1enemy2){
            game.setScreen(new GameOverScreen(game));
            bgm.stop();
        }
        if(enemy2player&&enemy1enemy2){
            game.setScreen(new GameOverScreen(game));
            bgm.stop();
        }

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
        arr.add(node5);
        arr.add(node6);
        arr.add(node7);
        arr.add(node8);
        arr.add(node9);
        arr.add(node10);
        arr.add(node11);

        //ini buat player
        double jarak = 0;
        double jarakenemy1 = 0;
        double jarakenemy2 = 0;
        for (int i=0; i<arr.size();i++){
            jarak = Math.sqrt(Math.pow(player.getX()-arr.get(i).getX(),2) + Math.pow(player.getY() - arr.get(i).getY(),2));
            jarakenemy1 = Math.sqrt(Math.pow(tesEnemy.getX()-arr.get(i).getX(),2) + Math.pow(tesEnemy.getY() - arr.get(i).getY(),2));
            jarakenemy2 = Math.sqrt(Math.pow(tesEnemy2.getX()-arr.get(i).getX(),2) + Math.pow(tesEnemy2.getY() - arr.get(i).getY(),2));
            if(jarak<64){
                tujuan = i;
            }
            if(jarakenemy1<64){
                nodeawalenemy1=i;
            }
            if(jarakenemy2<64){
                nodeawalenemy2=i;
            }
        }
        //gerak enemy1
        if(nodeawalenemy1 != tujuan){
            pathenemy1 = graf.shortestpath(nodeawalenemy1,tujuan);
            if(Math.abs(tesEnemy.getX()-player.getX())>1 || Math.abs(tesEnemy.getY()-player.getY())>1){
                if(reachnode1) {
                    if(pathenemy1.size()!=0) {
                        enemy1kenode = pathenemy1.get(0);
                    }
                    pathenemy1.remove(0);
                    reachnode1=false;
                }
                //enemy movement
                if(Math.abs(tesEnemy.getX()-arr.get(enemy1kenode).getX())<5){
                    if(tesEnemy.getY()-arr.get(enemy1kenode).getY()>0){
                        //ke bawah
                        tesEnemy.setVelocityXY(0,-tesEnemySpeed);
                    }else if(tesEnemy.getY()-arr.get(enemy1kenode).getY()<0){
                        //ke atas
                        tesEnemy.setVelocityXY(0,tesEnemySpeed);
                    }
                }
                if(Math.abs(tesEnemy.getY()-arr.get(enemy1kenode).getY())<5){
                    if(tesEnemy.getX()-arr.get(enemy1kenode).getX()>0){
                        //ke kiri
                        tesEnemy.setVelocityXY(-tesEnemySpeed,0);
                    }else if(tesEnemy.getX()-arr.get(enemy1kenode).getX()<0){
                        //ke kanan
                        tesEnemy.setVelocityXY(tesEnemySpeed,0);
                    }
                }
                //beda1px dengan tujuan tidak bisa pas 0
                if(Math.abs(tesEnemy.getX()-arr.get(enemy1kenode).getX())<5 && Math.abs(tesEnemy.getY()-arr.get(enemy1kenode).getY())<5){
                    reachnode1=true;
                }
            }
        }else{
            //sesuaikan sama node
//            Math.abs(tesEnemy.getX()-player.getX())
            if(Math.abs(tesEnemy.getX()-player.getX())>1){
                if(tesEnemy.getY()-player.getY()>0){
                    //ke bawah
                    tesEnemy.setVelocityXY(0,-tesEnemySpeed);
                }else if(tesEnemy.getY()-player.getY()<0){
                    //ke atas
                    tesEnemy.setVelocityXY(0,tesEnemySpeed);
                }
            }
            if(Math.abs(tesEnemy.getY()-player.getY())>1){
                if(tesEnemy.getX()-player.getX()>0){
                    //ke kiri
                    tesEnemy.setVelocityXY(-tesEnemySpeed,0);
                }else if(tesEnemy.getX()-player.getX()<0){
                    //ke kanan
                    tesEnemy.setVelocityXY(tesEnemySpeed,0);
                }
            }
        }
        //gerak enemy2
        if(nodeawalenemy2 != tujuan){
            System.out.println(nodeawalenemy2);
            System.out.println(tujuan);
            pathenemy2 = graf.DFS(nodeawalenemy2,tujuan);
            System.out.println(pathenemy2);
            if(Math.abs(tesEnemy2.getX()-player.getX())>1 || Math.abs(tesEnemy2.getY()-player.getY())>1){
                if(reachnode2) {
                    if(pathenemy2.size()!=0) {
                        enemy2kenode = pathenemy2.get(0);
                        pathenemy2.remove(0);
                    }
                    reachnode2=false;
                }
                //enemy movement
                if(Math.abs(tesEnemy2.getX()-arr.get(enemy2kenode).getX())<5){
                    if(tesEnemy2.getY()-arr.get(enemy2kenode).getY()>0){
                        //ke bawah
                        tesEnemy2.setVelocityXY(0,-tesEnemySpeed);
                    }else if(tesEnemy2.getY()-arr.get(enemy2kenode).getY()<0){
                        //ke atas
                        tesEnemy2.setVelocityXY(0,tesEnemySpeed);
                    }
                }
                if(Math.abs(tesEnemy2.getY()-arr.get(enemy2kenode).getY())<5){
                    if(tesEnemy2.getX()-arr.get(enemy2kenode).getX()>0){
                        //ke kiri
                        tesEnemy2.setVelocityXY(-tesEnemySpeed,0);
                    }else if(tesEnemy2.getX()-arr.get(enemy2kenode).getX()<0){
                        //ke kanan
                        tesEnemy2.setVelocityXY(tesEnemySpeed,0);
                    }
                }
                //beda1px dengan tujuan tidak bisa pas 0
                if(Math.abs(tesEnemy2.getX()-arr.get(enemy2kenode).getX())<5 && Math.abs(tesEnemy2.getY()-arr.get(enemy2kenode).getY())<5){
                    reachnode2=true;
                }
            }
        }else{
            //sesuaikan sama node
            if(Math.abs(tesEnemy2.getX()-player.getX())>1){
                if(tesEnemy2.getY()-player.getY()>0){
                    //ke bawah
                    tesEnemy2.setVelocityXY(0,-tesEnemySpeed);
                }else if(tesEnemy2.getY()-player.getY()<0){
                    //ke atas
                    tesEnemy2.setVelocityXY(0,tesEnemySpeed);
                }
            }
            if(Math.abs(tesEnemy2.getY()-player.getY())>1){
                if(tesEnemy2.getX()-player.getX()>0){
                    //ke kiri
                    tesEnemy2.setVelocityXY(-tesEnemySpeed,0);
                }else if(tesEnemy2.getX()-player.getX()<0){
                    //ke kanan
                    tesEnemy2.setVelocityXY(tesEnemySpeed,0);
                }
            }
        }

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
        bitmapFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        bitmapFont.draw(batch,targetplayer,30,450);
        bitmapFont.draw(batch,targetenemy1,30,425);
        bitmapFont.draw(batch,targetenemy2,30,400);
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
