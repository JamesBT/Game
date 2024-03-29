package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MainMenuScreen extends ApplicationAdapter implements Screen {
    final BaseGame game;

    OrthographicCamera camera;

    Texture playButtonActive, playButtonInactive;
    Texture exitButtonInactive, exitButtonActive;
    Texture judul;
    Music sound;
    Sound click;
    BitmapFont bitmapFont;

    public MainMenuScreen(BaseGame game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);

        playButtonInactive = new Texture("labels/starthover.png");
        playButtonActive = new Texture("labels/start.png");
        exitButtonInactive = new Texture("labels/exithover.png");
        exitButtonActive = new Texture("labels/exit.png");
        judul = new Texture("labels/title.png");

        sound = Gdx.audio.newMusic(Gdx.files.internal("sound & music/stranger-things-menu.ogg"));
        sound.setVolume(0.1f);
        sound.play();

        click = Gdx.audio.newSound(Gdx.files.internal("sound & music/click.ogg"));

        bitmapFont = new BitmapFont();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        bitmapFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        float x = camera.viewportWidth / 2 + 170;
        float y = camera.viewportHeight / 2 + 120;
        float x2 = x - 300 / 2;
        float y2 = camera.viewportHeight / 2 + 10;

        game.batch.draw(judul, camera.viewportWidth / 2 + 95, camera.viewportHeight / 2 + 160, 450, 450);

        //play button
        if(Gdx.input.getX() < x2 + 450 &&  Gdx.input.getX() > x2 + 150 && Gdx.input.getY() < y + 165 && Gdx.input.getY() > y+65)
        {
            game.batch.draw(playButtonActive, x, y, 300,250);
            if (Gdx.input.isTouched()) {
                click.play();
                sound.stop();
                game.setScreen(new GameScreen(game));
                dispose();
            }
        } else {
            game.batch.draw(playButtonInactive, x, y, 300,250);
        }

        //exit
        if(Gdx.input.getX() < x2 + 450 &&  Gdx.input.getX() > x2 + 150 && Gdx.input.getY() < y2 + 385 && Gdx.input.getY() > y2 + 285 )
        {
            game.batch.draw(exitButtonActive,x ,y2 , 300,250);

            if (Gdx.input.isTouched()) {
                click.play();
                Gdx.app.exit();
                dispose();
            }
        } else
        {
            game.batch.draw(exitButtonInactive,x ,y2 , 300,250);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        click.dispose();
        sound.stop();
    }
}
