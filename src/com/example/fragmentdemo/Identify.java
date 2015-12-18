package com.example.fragmentdemo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class Identify extends BroadcastReceiver {
      String reboot="reboot";
      Pattern p_reboot=Pattern.compile(reboot);

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "this is identify", 0).show();

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
               Matcher m_reboot=p_reboot.matcher(content);
               boolean result_reboot=m_reboot.matches();
               if(result_reboot) {
            	   context.unregisterReceiver(this);
        SmsManager smsManager = SmsManager.getDefault();// 发信息时需要的
		smsManager.sendTextMessage(sender, null, "you will have to restart the process now", null,null); 

                  Intent mainIntent = new Intent();
          		mainIntent.setClass(context, MainActivity.class);
          		mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          		context.startActivity(mainIntent);
  }
              else{
              // 打开text这个activity
                  Intent mainIntent = new Intent();
          		mainIntent.setClass(context, Text.class);
          		mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          		mainIntent.putExtra("phonenum", sender);
          		mainIntent.putExtra("mess_content",content);
          		context.startActivity(mainIntent);
              }
            }
	}
	}
}
