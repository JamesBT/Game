package com.mygdx.game;

public class GameScreen extends BaseScreen{
    public GameScreen(BaseGame game) {
        super(game);
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
