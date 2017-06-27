package cn.muse.findyou;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import cn.muse.findyou.service.PhoneService;
import cn.muse.findyou.view.MyButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{



    private MyButton inComingBtn;
    private MyButton smsComingBtn;
    private MyButton outComingBtn;
    private MyButton swichBtn;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.READ_PHONE_STATE)
                .permission(Manifest.permission.SEND_SMS)
                .permission(Manifest.permission.RECEIVE_SMS)
                .permission(Manifest.permission.PROCESS_OUTGOING_CALLS)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        Toast.makeText(MainActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        Toast.makeText(MainActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();

        inComingBtn = (MyButton) findViewById(R.id.btn_inComing);
        smsComingBtn = (MyButton) findViewById(R.id.btn_smsComing);
        outComingBtn = (MyButton) findViewById(R.id.btn_outComing);
        swichBtn = (MyButton) findViewById(R.id.btn_switch);
        editText = (EditText) findViewById(R.id.edit);

    }



    @Override
    protected void onResume() {
        super.onResume();
        inComingBtn.setOnClickListener(this);
        smsComingBtn.setOnClickListener(this);
        outComingBtn.setOnClickListener(this);
        swichBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MyContacts.Number = editText.getText().toString().trim();
        switch (v.getId()){
            case R.id.btn_inComing : {
                if(!MyContacts.isInComingOpen){
                    MyContacts.isInComingOpen = true;
                    inComingBtn.setBackgroundResource(R.drawable.bg_edit_selected);
                    inComingBtn.setText("电话监听 已选择");
                }else {
                    MyContacts.isInComingOpen = false;
                    inComingBtn.setBackgroundResource(R.drawable.bg_edit_normal);
                    inComingBtn.setText("电话监听");
                }
                break;
            }
            case R.id.btn_smsComing:{
                if (!MyContacts.isSmsComingOpen){
                    MyContacts.isSmsComingOpen = true;
                    smsComingBtn.setBackgroundResource(R.drawable.bg_edit_selected);
                    smsComingBtn.setText("短信监听 已选择");
                }
                else {
                    MyContacts.isSmsComingOpen = false;
                    smsComingBtn.setBackgroundResource(R.drawable.bg_edit_normal);
                    smsComingBtn.setText("短信监听");
                }
                break;
            }
            case R.id.btn_outComing:{
                if (!MyContacts.isOutComingOpen ){
                    MyContacts.isOutComingOpen = true;
                    outComingBtn.setBackgroundResource(R.drawable.bg_edit_selected);
                    outComingBtn.setText("去电监听 已选择");
                }
                else {
                    MyContacts.isOutComingOpen = false;
                    outComingBtn.setBackgroundResource(R.drawable.bg_edit_normal);
                    outComingBtn.setText("去电监听");
                }
                break;
            }
            case R.id.btn_switch:{
                if (MyContacts.isSwitchOpen ){
                    MyContacts.isSwitchOpen = false;
                    inComingBtn.setEnabled(true);
                    outComingBtn.setEnabled(true);
                    smsComingBtn.setEnabled(true);
                    swichBtn.setBackgroundResource(R.drawable.bg_edit_normal);
                    swichBtn.setText("开启监控");
                    Intent intent = new Intent(MainActivity.this,PhoneService.class);
                    stopService(intent);
                }else {
                    if (TextUtils.isEmpty(MyContacts.Number)){
                        Toast.makeText(this, "请输入监听的手机号", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        MyContacts.isSwitchOpen = true;
                        inComingBtn.setEnabled(false);
                        outComingBtn.setEnabled(false);
                        smsComingBtn.setEnabled(false);
                        swichBtn.setBackgroundResource(R.drawable.bg_edit_selected);
                        swichBtn.setText("关闭监控");



                        Intent intent = new Intent(MainActivity.this, PhoneService.class);
                        startService(intent);

                    }
                }
                break;
            }
        }
    }
}
