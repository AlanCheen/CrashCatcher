package yifeiyuan.github.crashcatcher;

import android.app.Application;

import yifeiyuan.library.crashcatcher.CrashCatcher;

/**
 * Created by alanchen on 15/9/6.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashCatcher.ready().toCatch(this);
    }
}
