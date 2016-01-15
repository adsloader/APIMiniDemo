package com.psw.park.APISimpleDemos;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;

/**
 *  Animal 클래스
 */
public class Animal implements Serializable{
    public String sName;
    public Animal(String sName){
        this.sName = sName;
    }
    public void Crying(Context c){
        Toast.makeText(c, sName + ":> crying!", Toast.LENGTH_LONG).show();
    }
}
