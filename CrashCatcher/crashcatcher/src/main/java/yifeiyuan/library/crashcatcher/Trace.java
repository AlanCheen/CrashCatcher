package yifeiyuan.library.crashcatcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by alanchen on 15/9/6.
 */
public class Trace implements Serializable{

    public String trace;

    public String filePath ;

    public String date ;

    public Trace(String trace) {
        this.trace = trace;
    }

    public void save(File file){
        try {
            Trace.saveToLocal(file,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToLocal(File file,Serializable ser) throws IOException{
        FileOutputStream outStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);
        objectOutputStream.writeObject(ser);
        outStream.close();
    }

    public static Object readObject(File file) throws IOException, ClassNotFoundException {
        if(null == file)
            return null;

        if (!file.exists()) {
            return null;
        }

        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj = ois.readObject();
        fis.close();
        return obj;
    }
}
