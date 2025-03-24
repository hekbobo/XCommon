package com.b.xcommon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonObject;
import com.nightonke.boommenu.BoomMenuButton;
import com.shoot.common.AppActivityMgr;
import com.shoot.common.XToastUtil;
import com.yingjie.addressselector.api.AdType;
import com.yingjie.addressselector.api.CYJAdSelector;
import com.yingjie.addressselector.api.OnSelectorListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String MAIN_ACTIVITY = "MainActivity";
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppActivityMgr.get().add(this);

        new BoomMenuDemo().init((BoomMenuButton) findViewById(R.id.fab));

        findViewById(R.id.btn_address).setOnClickListener(this);
        findViewById(R.id.btn_test).setOnClickListener(this);
    }



    void address(){
        new CYJAdSelector().
                setSelectColor(R.color.colorPrimary).
                setBottomLineColor(R.color.colorPrimary).
                build()
                .showSelector(this, AdType.ADD, "", "", "", new OnSelectorListener() {
                    @Override
                    public void onSelector(String province, String city, String area) {
                        XToastUtil.showShortToast(province+city+area, MainActivity.this);
                    }
                });
    }

    void testxxx(){
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


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_address){
            address();
        }
        if (view.getId() == R.id.btn_test){
            testNet();
        }
    }
    OkHttpClient mOkHttpClient = null;
    public void initHtop(){
        OkHttpClient.Builder builder  = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        mOkHttpClient = builder.build();
        OkHttpUtils.initClient(mOkHttpClient);
    }
    String URL = "https://client.qzhuli.com/user/login_by_password";
    private  void testNet() {
        MapBuild map = new MapBuild();
                map
                .add("phone", "18666971111")
                .add("password", "qwert123456")
                .build();

        String content = mapToJsonString(map.getParam());

//        Post(OkHttpUtils
//                .post()
//                .params(map.getParam())
//                .url(URL)
//                .build());

        Post(OkHttpUtils
                .postString()
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(content)
                .url(URL)
                .build());
    }

    private void Post(RequestCall requestCall) {
        requestCall
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, response);
                    }
                });
    }

    public static String mapToJsonString(Map<String, String> map){
        JsonObject jsonArray = new JsonObject();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            jsonArray.addProperty(entry.getKey(), entry.getValue());
        }
        return jsonArray.toString();
    }
}
