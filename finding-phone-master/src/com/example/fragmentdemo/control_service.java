package com.example.fragmentdemo;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class control_service extends Service {

	private DevicePolicyManager policyManager;
	private ComponentName componentName;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "this is service", 0).show();
		  AudioManager am=(AudioManager)getSystemService(Context.AUDIO_SERVICE);  
		am.setStreamVolume(AudioManager.STREAM_MUSIC,am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),AudioManager.FLAG_PLAY_SOUND);

        MediaPlayer  mediaPlayer = MediaPlayer.create(this,  R.raw.abc);
        mediaPlayer.start();
        
        Intent broadcast=new Intent();

        broadcast.setAction("register.lock");

        sendBroadcast(broadcast);   
        
		super.onCreate();
	}

}