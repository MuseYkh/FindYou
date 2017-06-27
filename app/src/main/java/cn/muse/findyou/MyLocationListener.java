package cn.muse.findyou;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

/**
 * Created by Sun on 2017/6/27.
 */

public class MyLocationListener implements BDLocationListener {

    private OnStopListener listener;
    public interface OnStopListener{
        void Stop(String result);
    }
    public void setOnStopListener(OnStopListener listener){
        this.listener = listener;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        MyContacts.loc = bdLocation.getAddrStr();

        listener.Stop(bdLocation.getAddrStr());
    }


    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }
}
