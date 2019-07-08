package kkk.proxyapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Constructor;

import d.kkk.mylibrary.IProxyActivity;

import static d.kkk.mylibrary.IProxyActivity.CLASSNAME;


public class ProxyActivity extends Activity {
    public String entryActivityName;
    private String className;
    private IProxyActivity iProxyActivity;
    public String TAG = "kkk";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        className = getIntent().getStringExtra(CLASSNAME);
        Class activityClass = null;
        try {
            activityClass = getClassLoader().loadClass(className);
            Constructor constructor = activityClass.getConstructor(new Class[]{});
            Log.d(TAG, "onCreate: " + constructor.newInstance(new Object[]{}).getClass());
            iProxyActivity = (IProxyActivity)constructor.newInstance(new Object[]{});
            Bundle bundle = new Bundle();
            iProxyActivity.onAttach(this);
            iProxyActivity.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        iProxyActivity.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iProxyActivity.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        iProxyActivity.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        iProxyActivity.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iProxyActivity.onDestroy();
    }

    @Override
    public Resources getResources() {
        return PluginManger.getInstance().getResources();
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManger.getInstance().getDexClassLoader();
    }

    @Override
    public void startActivity(Intent intent) {
        String toActivityName = intent.getStringExtra(CLASSNAME);
        Intent newIntent = new Intent(this, ProxyActivity.class);
        newIntent.putExtra(CLASSNAME, toActivityName);
        super.startActivity(newIntent);
    }
}
