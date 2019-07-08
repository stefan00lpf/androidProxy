package kkk.proxyapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManger {
    private static PluginManger pluginManger = new PluginManger();
    private DexClassLoader dexClassLoader;
    private Resources resources;
    private Context context;
    private String entryActivityName;
    public String TAG = "kkk";
    public static PluginManger getInstance(){
        return pluginManger;
    }
    private PluginManger() {

    }
    public void loadPath(String path)
    {
        /**
        加载classloader
        */

        File dexOutFile = context.getDir("dex", Context.MODE_PRIVATE);
        dexClassLoader = new DexClassLoader(path, dexOutFile.getAbsolutePath(), null, context.getClassLoader());
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(path, packageManager.GET_ACTIVITIES);
        entryActivityName = packageInfo.activities[0].name;
        Log.d(TAG, "loadPath: " + entryActivityName);
        /**
         *加载资源
         * **/
        AssetManager assets = null;
        try {
            assets = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assets, path);
            resources = new Resources(assets, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }

    public String getEntryActivityName() {
        return entryActivityName;
    }
    public void setContext(Context context)
    {
        this.context = context.getApplicationContext();
    }
}
