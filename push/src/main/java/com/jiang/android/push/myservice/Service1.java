package com.jiang.android.push.myservice;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONObject;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
import de.tavendo.autobahn.WebSocketOptions;

/**
 * This Service is Persistent Service. Do some what you want to do here.<br/>
 * <p/>
 * Created by Mars on 12/24/15.
 */
public class Service1 extends Service {

    private static WebSocketConnection webSocketConnection;

    private static String websocketUrl = "ws://test.im.kai12.cn:9503";
    private static WebSocketOptions options = new WebSocketOptions();

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO do some thing what you want..
        connect();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private static void connect()  {
        if (webSocketConnection == null) {
            webSocketConnection = new WebSocketConnection();
        }
        try {
            webSocketConnection.connect(websocketUrl, new WebSocketHandler() {
                @Override
                public void onOpen() {
                    super.onOpen();
                    sendMsg(buildLoginStr());
                }


                @Override
                public void onTextMessage(String payload) {
                    super.onTextMessage(payload);
                    Log.i("text","payload = "+payload);
                }

                @Override
                public void onClose(int code, String reason) {
                    super.onClose(code, reason);
                }
            }, options);
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }

    private static String buildLoginStr() {
//        {"cmd":"login","ptype":0,"school_code":"k12school_01_test","sid":0,"uid":6,"utype":1,"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBsaWNhdGlvbiI6InRvYiIsImNoaWxkX3VzZXJfaWQiOjAsInNjaG9vbF9jb2RlIjoiazEyc2Nob29sXzAxX3Rlc3QiLCJ1c2VyX2lkIjo2LCJleHAiOjE0Nzc1NDkzNjN9.gqqV9ErfdUITaT6PG3aDPha0RKzoAfkpNEqhohJ4m-4","client_version":"4","loginip":"","equipment_type":2,"system_type":23}

        String loginMsg = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd","login");
            jsonObject.put("ptype", 0);
            jsonObject.put("school_code", "k12school_01_test");
            jsonObject.put("sid", 0);
            jsonObject.put("uid", 6);
            jsonObject.put("utype", 1);
            jsonObject.put("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBsaWNhdGlvbiI6InRvYiIsImNoaWxkX3VzZXJfaWQiOjAsInNjaG9vbF9jb2RlIjoiazEyc2Nob29sXzAxX3Rlc3QiLCJ1c2VyX2lkIjo2LCJleHAiOjE0Nzc1NDkzNjN9.gqqV9ErfdUITaT6PG3aDPha0RKzoAfkpNEqhohJ4m-4");
            jsonObject.put("equipment_type", 2);
            jsonObject.put("system_type", android.os.Build.VERSION.SDK_INT);
            jsonObject.put("client_version", 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loginMsg = jsonObject.toString();
        return loginMsg;
    }

    public static void sendMsg(String s) {
        if (webSocketConnection != null) {
            try {
                if (webSocketConnection.isConnected()) {
                    webSocketConnection.sendTextMessage(s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeConnect(){
        if (webSocketConnection != null && webSocketConnection.isConnected()) {
            webSocketConnection.disconnect();
            webSocketConnection = null;
        }
    }

    public static void openConnect(){
        connect();
    }


}
