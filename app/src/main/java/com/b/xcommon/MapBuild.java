package com.b.xcommon;
import android.text.TextUtils;
import android.util.Log;

import com.shoot.common.XMd5Util;

import java.util.HashMap;
import java.util.Map;

class MapBuild {
    private  HashMap<String, String> map = new HashMap<>();
    private   HashMap<String, String> additionMap;

    MapBuild addAdditionKey(String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            additionMap.put(key, value);
        }
        return this;
    }
    MapBuild add(Map<String,String> p){
        map.putAll(p);
        return this;
    }
    MapBuild add( String name, String value) {
        map.put(name, value);
        return this;
    }

    MapBuild add(String name, int value) {
        map.put(name, ""+value);
        return this;
    }


    MapBuild build() {
        calc();
        return this;
    }


    HashMap<String, String> calc() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!TextUtils.isEmpty(entry.getValue())){
                sb
                .append(entry.getKey())
                .append("=")
                .append(entry.getValue())
                .append("&");
            }
        }
        sb.append("token=");
        Log.d("MapBuild", "sb=" + sb);
        String key = XMd5Util.getStringMd5(sb.toString());
        map.put("key", key);

        return map;
    }

    String get(){
        return "";
    }

//    public fun genBaseInfo():String{
//        val ver = XPackageUtil.getVersionCodeByPkg(KConstValue.PKG(), XApp.get().context)
//        return "{\"platform\":\"android\",\"v\":${ver},\"channel\":${KActivityUtil.getChannel()},\"device_id\": ${invoiceWrap.getDeviceId()}\"}";
//    }

    String getKey(){
        return map.get("key");
    }

    Map<String,String> getParam(){
        return map;
    }
}

