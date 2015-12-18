package com.example.fragmentdemo;


import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.ViewDebug.FlagToString;

public class Text extends Activity {
//	被跳转的界面
	
//	全局变量声明
	String msg;
	DevicePolicyManager policyManager;
	ComponentName componentName;

	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.identify);
		super.onCreate(savedInstanceState);
		
		policyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		componentName = new ComponentName(this, MyAdmin.class);

		if (!policyManager.isAdminActive(componentName)) {
			Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
			startActivity(intent);
		}

		
//    	先获取intent传递过来的数据
		String sender=getIntent().getExtras().getString("phonenum");
		String content=getIntent().getExtras().getString("mess_content");
		
	     String wipe="wipe";
         String lock="lock";
         String music="music";
		
     	  Pattern p_wipe=Pattern.compile(wipe);
     	  Pattern p_lock=Pattern.compile(lock);
     	  Pattern p_music=Pattern.compile(music);
     	  
          Matcher m_wipe=p_wipe.matcher(content);
          Matcher m_lock=p_lock.matcher(content);
          Matcher m_music=p_music.matcher(content);
          
          boolean result_wipe=m_wipe.find();  
          boolean result_lock=m_lock.find(); 
          boolean result_music=m_music.find(); 
          
//          改锁定密码
          if(result_lock){
        	  String fqdnId="";
        	  String reg = "[0-9]{4}"; //正则表达式：连续出现4个数字
              Pattern pattern = Pattern.compile(reg);
              Matcher matcher = pattern.matcher(content);
              if (matcher.find()) {// matcher.matchers() {
                  fqdnId = matcher.group();
              }
        	policyManager.resetPassword(fqdnId, 0);
  			policyManager.lockNow();
          }
     	  
//          清除数据
//          慎用！！！
          if(result_wipe){
        	  policyManager.wipeData(0);
          }
		

          if(result_music){
//		调整音量
//		http://blog.csdn.net/chuchu521/article/details/7848706
//		系统服务的调用要放在oncreate中，不能放在前面
		  AudioManager am=(AudioManager)getSystemService(Context.AUDIO_SERVICE);  
		am.setStreamVolume(AudioManager.STREAM_MUSIC,am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),AudioManager.FLAG_PLAY_SOUND);
//        播放声音
        MediaPlayer  mediaPlayer = MediaPlayer.create(Text.this,  R.raw.abc);
        mediaPlayer.start();
          }
		
//        获取gps
//        测试通不过，暂时放弃
      	 final LocationManager mgr = null;
      	 
      	 	GpsStatus.Listener listener = new GpsStatus.Listener() { 
            public void onGpsStatusChanged(int event) { 
            GpsStatus gpsstatus=mgr.getGpsStatus(null); 
                switch(event) 
                { 
                case GpsStatus.GPS_EVENT_FIRST_FIX:gpsstatus.getTimeToFirstFix();   
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS: 
                   //得到所有收到的卫星的信息，包括 卫星的高度角、方位角、信噪比、和伪随机号（及卫星编号） 
            Iterable<GpsSatellite> allSatellites; 
            allSatellites = gpsstatus.getSatellites(); 
            Iterator it=allSatellites.iterator(); 
            String msg="jdksjfslkdflk;"; 
            while(it.hasNext()) 
            { 
                GpsSatellite oSat = (GpsSatellite) it.next() ;  
                 msg="azimuth:"+oSat.getAzimuth(); 
                msg+="\nprn:"+oSat.getPrn(); 
                msg+="\nsnr:"+oSat.getSnr(); 
            } 
            break; 
             
           case GpsStatus.GPS_EVENT_STARTED: 
                   //Event sent when the GPS system has started. 
           break; 
             
           case GpsStatus.GPS_EVENT_STOPPED: 
                  //Event sent when the GPS system has stopped.  
            break; 
             
           default : 
            break; 
                } 
                 
            } 
     }; 
     
     
//		发送短信
		SmsManager smsManager = SmsManager.getDefault();// 发信息时需要的
		smsManager.sendTextMessage(sender, null, "coming from finding_phone", null,null); 
//		smsManager.sendTextMessage(sender, null, msg, null,null);
//		注意短信内容不能为空
	}

}

