package com.example.fragmentdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class Identify extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "<<<<<<<<<<<<<<<<<< ", 0).show();

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
