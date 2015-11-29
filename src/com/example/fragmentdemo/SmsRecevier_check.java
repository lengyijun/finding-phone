package com.example.fragmentdemo;

  import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.AvoidXfermode.Mode;
import android.media.MediaPlayer;
import android.net.Uri;
import android.sax.StartElementListener;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.*;

import javax.xml.datatype.Duration;
  
  public class SmsRecevier_check extends BroadcastReceiver {
		DevicePolicyManager policyManager;
		ComponentName componentName;
   private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
		
    private SQLiteDatabase db;
    private DBopenhelper helper;
    private Cursor c;
    private String msg="hello";
    
    private SharedPreferences sp;
		
      public SmsRecevier_check() {
          Log.v("TAG", "SmsRecevier create");
      }
  
      @Override
      public void onReceive(Context context, Intent intent) {
          
//    	  获取答案
    	  sp = context.getSharedPreferences("answer", Context.MODE_PRIVATE);
    	  msg=sp.getString("db_answ", "");
          Pattern p_msg=Pattern.compile(msg);
    	  
        Toast.makeText(context, "this is msg "+msg, 0).show();

   		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
         if (pdus != null && pdus.length > 0) {
             SmsMessage[] messages = new SmsMessage[pdus.length];
             for (int i = 0; i < pdus.length; i++) {
                 byte[] pdu = (byte[]) pdus[i];
                 messages[i] = SmsMessage.createFromPdu(pdu);
             }
             
             for (SmsMessage message : messages) {
                 String content = message.getMessageBody();// 得到短信内容
                 String sender = message.getOriginatingAddress();// 得到发信息的号码
                
//                匹配
                 Matcher m_msg=p_msg.matcher(content);
                 boolean result_msg=m_msg.find();
                 if(result_msg){
                	 
//                	 Intent service=new Intent(context,control_service.class);
//                	 context.startService(service);
                	 
//                	 Intent mainIntent = new Intent();
//               		mainIntent.setClass(context, Text.class);
//               		mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//               		mainIntent.putExtra("phonenum", sender);
//               		mainIntent.putExtra("mess_content",content);
//               		context.startActivity(mainIntent);
                	 
                	 SmsManager smsmanager1=SmsManager.getDefault();
                	 smsmanager1.sendTextMessage(sender, null, "right", null, null);

                	Identify recevier = new Identify();
         	        IntentFilter filter = new IntentFilter();
         	        filter.addAction(ACTION);
         	        filter.setPriority(1000);//设置优先级最大
         	        context.registerReceiver(recevier, filter);

            		 context.unregisterReceiver(this);
                 }
             }
             
         }
     }
 }
