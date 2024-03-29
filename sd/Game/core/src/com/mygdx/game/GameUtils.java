package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class GameUtils {
    // Creates an Animation from a single sprite sheet
    public static Animation parseSpriteSheet(String fileName, int frameCols, int frameRows,
                                             float frameDuration, PlayMode mode) {
        Texture t = new Texture(Gdx.files.internal(fileName), true);
        t.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        int frameWidth = t.getWidth() / frameCols;
        int frameHeight = t.getHeight() / frameRows;

        TextureRegion[][] temp = TextureRegion.split(t, frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];

        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index] = temp[i][j];
                index++;
            }
        }

        Array<TextureRegion> framesArray = new Array<TextureRegion>(frames);

        return new Animation(frameDuration, framesArray, mode);
    }

    // Creates an animation from a single sprite sheet
    public static Animation parseSpriteSheet(String fileName, int frameCols, int frameRows,
                                             int[] frameIndices, float frameDuration, PlayMode mode) {
        Texture t = new Texture(Gdx.files.internal(fileName), true);
        t.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        int frameWidth = t.getWidth() / frameCols;
        int frameHeight = t.getHeight() / frameRows;

        TextureRegion[][] temp = TextureRegion.split(t, frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];

        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index] = temp[i][j];
                index++;
            }
        }

        Array<TextureRegion> framesArray = new Array<TextureRegion>();
        for (int n = 0; n < frameIndices.length; n++) {
            int i = frameIndices[n];
            framesArray.add(frames[i]);
        }

        return new Animation(frameDuration, framesArray, mode);
    }

    // Creates an Animation from a set of image files.
    public static Animation parseImageFiles(String fileNamePrefix, String fileNameSuffix,
                                            int frameCount, float frameDuration, PlayMode mode) {
        TextureRegion[] frames = new TextureRegion[frameCount];

        for (int n = 0; n < frameCount; n++) {
            String fileName = fileNamePrefix + n + fileNameSuffix;
            Texture t = new Texture(Gdx.files.internal(fileName));
            t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            frames[n] = new TextureRegion(t);
        }
        Array<TextureRegion> framesArray = new Array<TextureRegion>(frames);

        return new Animation(frameDuration, framesArray, mode);
    }
}

