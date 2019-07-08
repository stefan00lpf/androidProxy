package d.kkk.mylibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

public interface IProxyActivity {
    String CLASSNAME = "className";
    void onAttach(Activity activity);
    void onCreate(@NonNull Bundle savedInstanceState);
    void startActivity(Intent intent);
    void startActivityForResult(Intent intent, int requestCode);
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();

}
