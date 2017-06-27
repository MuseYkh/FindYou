package cn.muse.findyou.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import cn.muse.findyou.MyContacts;
import cn.muse.findyou.recevier.OutComingRecevier;
import cn.muse.findyou.recevier.SMSRecevier;

/**
 * Created by Sun on 2017/6/26.
 */

public class PhoneService extends Service{

    private TelephonyManager telephonyManager;
    private PhoneStateListener phoneStateListener;

    private IntentFilter smsIntentFilter;
    private IntentFilter outComingFilter;
    private SMSRecevier smsRecevier;
    private OutComingRecevier outComingRecevier;


    @Override
    public void onCreate() {
        super.onCreate();
        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if(MyContacts.isInComingOpen){
            phoneStateListener = new PhoneStateListener(){
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    super.onCallStateChanged(state, incomingNumber);
                    if (state == TelephonyManager.CALL_STATE_RINGING){
                        SmsManager smsManager = SmsManager.getDefault();
                        String msg = incomingNumber + "call he";
                        smsManager.sendTextMessage(MyContacts.Number,null,msg,null,null);
                    }
                }
            };
        }
        if (MyContacts.isSmsComingOpen){
            smsIntentFilter = new IntentFilter();
            smsRecevier = new SMSRecevier();
        }
        if (MyContacts.isOutComingOpen){
            outComingFilter = new IntentFilter();
            outComingRecevier = new OutComingRecevier();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(MyContacts.isInComingOpen){
            telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);
            Log.e("PhoneService", "来电监听成功");
        }

        if (MyContacts.isOutComingOpen){
            Log.e("PhoneService", "去电监听成功");
            outComingFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
            registerReceiver(outComingRecevier,outComingFilter);
        }

        if(MyContacts.isSmsComingOpen){
            Log.e("PhoneService", "短信监听成功");
            smsIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
            registerReceiver(smsRecevier,smsIntentFilter);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (MyContacts.isInComingOpen){
            Log.e("PhoneService", "来电监听解绑");
            telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_NONE);
        }
        if (MyContacts.isOutComingOpen){
            Log.e("PhoneService", "去电监听解绑");
            unregisterReceiver(outComingRecevier);
        }
        if (MyContacts.isSmsComingOpen){
            Log.e("PhoneService", "短信监听解绑");
            unregisterReceiver(smsRecevier);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
