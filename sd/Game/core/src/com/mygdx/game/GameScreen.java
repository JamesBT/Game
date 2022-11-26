package com.mygdx.game;

public class GameScreen extends BaseScreen{
    private Player player;

    public GameScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void create(){
        //buat player
        player = new Player();
        //buat enemy
        //buat music
        //animasi player
        //animasi enemy

        //buat map dan kamera
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
