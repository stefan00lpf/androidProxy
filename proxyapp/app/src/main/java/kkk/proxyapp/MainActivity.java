package kkk.proxyapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import d.kkk.mylibrary.IProxyActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PluginManger.getInstance().setContext(this);

        /*申请权限*/
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1 );
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            /* 如果申请不到权限就退出 */
            case 1:
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED && grantResults[1] != PackageManager.PERMISSION_GRANTED)
                {
                    finish();
                }
        }
    }

    boolean loadAoked;
    public void loadApk(View v)
    {
        File apkFile = new File(Environment.getExternalStorageDirectory().getPath(), "pluginapp.apk");
        PluginManger.getInstance().loadPath(apkFile.getPath());
        loadAoked = true;
    }

    public void startJump(View v)
    {
        if(!loadAoked)
        {
            Toast.makeText(this, "请先安装插件", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra(IProxyActivity.CLASSNAME, PluginManger.getInstance().getEntryActivityName());
        startActivity(intent);
    }
}
