package com.psw.park.APISimpleDemos;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Util {
    // Animal 클래스를 가지고 온다.
    static public Animal LoadData(Context context) throws Exception{

        // app내의 전용폴더에서 object.ser라는 파일로 설정
        FileInputStream fis = context.openFileInput("object.ser");

        // inputStream을 설정
        ObjectInputStream objstream2 = new ObjectInputStream(fis);

        // 저장되었던 객체를 읽어온다.
        Object object = objstream2.readObject();
        objstream2.close();

        return (Animal)object;
    }

    // Animal 클래스를 loading 한다.
    static public void SaveData(Context context, Object Items) throws Exception{
        // app내의 전용폴더에서 object.ser라는 파일로 설정
        FileOutputStream fos = context.openFileOutput("object.ser", Context.MODE_PRIVATE);

        // OutnputStream을 설정
        ObjectOutputStream objstream = new ObjectOutputStream(fos);
        objstream.writeObject(Items);
        objstream.close();
    }
}
