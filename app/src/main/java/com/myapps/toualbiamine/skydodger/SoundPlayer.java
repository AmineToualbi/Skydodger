package com.myapps.toualbiamine.skydodger;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by toualbiamine on 1/16/18.
 */

public class SoundPlayer {

    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 1; //1 seul son a la fois
    private static SoundPool soundPool;
    private static int overSound;

    public SoundPlayer (Context context){

        //SoundPool deprecated in API 21
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();

            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX).build();

        }

        soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        overSound = soundPool.load(context, R.raw.gameover, 1);

    }

    public void playOverSound(){

        soundPool.play(overSound, 1.0f, 1.0f, 1, 0, 1.0f);

    }

}
