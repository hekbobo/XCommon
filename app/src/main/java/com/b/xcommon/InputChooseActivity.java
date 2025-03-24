package com.b.xcommon;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class InputChooseActivity extends AppCompatActivity {

    public static final String MAIN_ACTIVITY = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x);

//        KLogWrap.init(KLockerLogHelper.getInstance(this));
//        KLogWrap.error("Tag", "XXXX");

//        String rest =  XBase64Util.decodeToString("eyJ0eXBlIjo2LCJleHRyYSI6IiIsImRsZyI6eyJ0aXRsZSI6IuaPkOekuiIsIm1lc3NhZ2UiOiLlvZPliY3mmK/mvJTnpLrotKblj7fvvIzor7flhYjms6jlhowiLCJsZWZ0QnRuIjoi55m75b2VIiwicmlnaHRCdG4iOiLnq4vljbPms6jlhowiLCJhY3Rpb24iOjF9fQ==");
//        Log.e(MAIN_ACTIVITY, rest);
//
//        Log.e(MAIN_ACTIVITY,Environment.getExternalStorageDirectory().getAbsolutePath());
//        Log.e(MAIN_ACTIVITY,Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
//
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
//        XFileUtils.getAllFiles(path+ "/android/data/com.tencent.mm/", "pdf");
//        XFileUtils.getAllFiles(path+ "/android/data/com.tencent.mm/MicroMsg/Download/", "pdf");
    }
}
