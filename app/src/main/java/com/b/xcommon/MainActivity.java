package com.b.xcommon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.shoot.XBase64Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String rest =  XBase64Util.decodeToString("eyJ0eXBlIjo2LCJleHRyYSI6IiIsImRsZyI6eyJ0aXRsZSI6IuaPkOekuiIsIm1lc3NhZ2UiOiLlvZPliY3mmK/mvJTnpLrotKblj7fvvIzor7flhYjms6jlhowiLCJsZWZ0QnRuIjoi55m75b2VIiwicmlnaHRCdG4iOiLnq4vljbPms6jlhowiLCJhY3Rpb24iOjF9fQ==");
        Log.e("a", rest);

    }
}
