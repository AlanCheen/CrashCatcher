package yifeiyuan.library.crashcatcher;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alanchen on 15/9/6.
 */
public class CrashCatcher implements Thread.UncaughtExceptionHandler{

    public static final String TAG = "CrashCatcher";

    private Thread.UncaughtExceptionHandler mRootHandler;

    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    public static CrashCatcher ready(){
        return new CrashCatcher();
    }

    private CrashCatcher() {
        mRootHandler = Thread.currentThread().getUncaughtExceptionHandler();
        Thread.currentThread().setUncaughtExceptionHandler(this);
    }

    private Context mContext ;
    public void toCatch(Context context){
        mContext = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.d(TAG, "uncaughtException() called with: " + "thread = [" + thread + "], ex = [" + ex + "]");
//        Intent i = new Intent(mContext, CrachCatcherActivity.class);
//        i.putExtra("log", ex.getMessage());
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        mContext.startActivity(i);

        ex.getStackTrace();
        ex.printStackTrace();
        Trace trace = new Trace(getStackTrace(ex));
        trace.date = mSimpleDateFormat.format(new Date());
        trace.save(getFile());
        Log.d(TAG, "log:" + trace.trace);

        if (null != mRootHandler) {
            mRootHandler.uncaughtException(thread,ex);
        }

    }


    private File getFile(){
//        String rootPath = Environment.getExternalStorageDirectory().getPath()+"/CrashCracher/";
        String rootPath = mContext.getCacheDir().getPath()+"/CrashCracher/";
        String fileName  = "Crash_"+System.currentTimeMillis()+".log";
//        String filePath = rootPath+"/"+fileName;
        File file = new File(rootPath);
        file.mkdirs();

        File target = new File(rootPath, fileName);
        try {
            if (target.exists()) {
                target.delete();
            }
            target.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target;
    }

    private String getStackTrace(Throwable ex){
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        printWriter.close();
        return writer.toString();
    }

}
