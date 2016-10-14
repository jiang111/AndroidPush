# AndroidPush
[![](https://jitpack.io/v/jiang111/AndroidPush.svg)](https://jitpack.io/#jiang111/AndroidPush) <br />

更方便的将各个rom厂商自己的推送服务进行集成,并统一管理,使用前还是需要熟悉各个平台的相关文档

### 各个平台的说明
>1. MIUI系统将使用[小米推送](http://dev.xiaomi.com/doc/?page_id=1670)
>2. Flyme系统将使用[魅族推送](https://open.flyme.cn/open-web/views/push.html?t=1476262864945)
>3. EMUI系统将使用[华为推送](http://developer.huawei.com/push)
>4. 其他系统将使用[JPush推送](https://www.jiguang.cn)

### 集成(请看app下的demo)
>1. 将项目中的aar文件夹的四个包导入到自己的项目并添加依赖，在自己项目下的gradle文件添加
```
   //项目最外层的gradle
   allprojects {
   		repositories {
   			...
   			maven { url "https://jitpack.io" }
   		}
   	}
   	//app下的gradle
    defaultConfig {
        ...
        ndk {
            abiFilters 'armeabi', 'armeabi-v7a', 'armeabi-v8a'
        }
        manifestPlaceholders = [
                JPUSH_PKGNAME : applicationId,
                JPUSH_APPKEY : "3df7e06ec9bf5e79abdc4a6a",
                JPUSH_CHANNEL : "develop",
                PNAME : applicationId
        ]
    }
    dependencies {
       		compile 'com.github.jiang111:AndroidPush:v1.1.0'
       	}
```

>2. 在自己项目下的manifest文件中添加如下代码:
```
 <!-- manifest节点下 -->
     <uses-permission android:name="${JPUSH_PKGNAME}.permission.JPUSH_MESSAGE" />
     <uses-permission android:name="android.permission.RECEIVE_USER_PRESENT" />
     <uses-permission android:name="android.permission.INTERNET" />
     <uses-permission android:name="android.permission.WAKE_LOCK" />
     <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
     <uses-permission android:name="android.permission.VIBRATE" />
     <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
     <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     <uses-permission android:name="android.permission.WRITE_SETTINGS" />
     <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
      <uses-permission android:name="android.permission.GET_TASKS"/>
 <permission
        android:name="${PNAME}.permission.MIPUSH_RECEIVE"
        android:protectionLevel="signature" />
    <uses-permission android:name="${PNAME}.permission.MIPUSH_RECEIVE" />
```

```
 <!-- application节点下 -->
 <meta-data
            android:name="JPUSH_CHANNEL"
            android:value="${JPUSH_CHANNEL}"
            tools:replace="android:value" />
        <!-- Required. AppKey copied from Portal -->
        <meta-data
            android:name="JPUSH_APPKEY"
            android:value="${JPUSH_APPKEY}"
            tools:replace="android:value" />
        <receiver
            android:name="com.jiang.android.push.jpush.JPushReceiver"
            android:enabled="true">
            <intent-filter>
                <!--Required 用户注册SDK的intent-->
                <action android:name="cn.jpush.android.intent.REGISTRATION" />
                <!--Required 用户接收SDK消息的intent-->
                <action android:name="cn.jpush.android.intent.MESSAGE_RECEIVED" />
                <!--Required 用户接收SDK通知栏信息的intent-->
                <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED" />
                <!--Required 用户打开自定义通知栏的intent-->
                <action android:name="cn.jpush.android.intent.NOTIFICATION_OPENED" />
                <!--Optional 用户接受Rich Push Javascript 回调函数的intent-->
                <action android:name="cn.jpush.android.intent.ACTION_RICHPUSH_CALLBACK" />
                <!-- 接收网络变化 连接/断开 since 1.6.3 -->
                <action android:name="cn.jpush.android.intent.CONNECTION" />
                <category android:name="${JPUSH_PKGNAME}" />
            </intent-filter>
        </receiver>
        <receiver android:name="com.jiang.android.push.emui.EMHuaweiPushReceiver">
            <intent-filter>
                <!-- 必须,用于接收token-->
                <action android:name="com.huawei.android.push.intent.REGISTRATION" />
                <action android:name="com.huawei.intent.action.PUSH_STATE" /> <!-- 可选，标签、地理位置上报回应，不上报则不需要 -->
                <action android:name="com.huawei.android.push.plugin.RESPONSE" />
            </intent-filter>
            <meta-data
                android:name="CS_cloud_ablitity"
                android:value="@string/hwpush_ability_value" />
        </receiver>
        <receiver
            android:name="com.jiang.android.push.miui.MiuiReceiver"
            android:exported="true">
            <!--这里com.xiaomi.mipushdemo.DemoMessageRreceiver改成app中定义的完整类名-->
            <intent-filter>
                <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
            </intent-filter>
            <intent-filter>
                <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
            </intent-filter>
            <intent-filter>
                <action android:name="com.xiaomi.mipush.ERROR" />
            </intent-filter>
        </receiver>
        <!-- push应用定义消息receiver声明 -->
        <receiver android:name="com.jiang.android.push.flyme.FlymeReceiver">
            <intent-filter>
                <!-- 接收push消息 -->
                <action android:name="com.meizu.flyme.push.intent.MESSAGE" />
                <!-- 接收register消息 -->
                <action android:name="com.meizu.flyme.push.intent.REGISTER.FEEDBACK" />
                <!-- 接收unregister消息-->
                <action android:name="com.meizu.flyme.push.intent.UNREGISTER.FEEDBACK" />
                <!-- 兼容低版本Flyme3推送服务配置 -->
                <action android:name="com.meizu.c2dm.intent.REGISTRATION" />
                <action android:name="com.meizu.c2dm.intent.RECEIVE" />
                <category android:name="${PNAME}"></category>
            </intent-filter>
        </receiver>
        <activity
            android:name="com.huawei.android.pushselfshow.richpush.RichPushActivity"
            android:configChanges="orientation|screenSize|locale|layoutDirection"
            android:process=":pushservice"
            android:screenOrientation="portrait"
            android:theme="@style/hwpush_NoActionBar">
            <meta-data
                android:name="hwc-theme"
                android:value="androidhwext:style/Theme.Emui" />
            <intent-filter>
                <action android:name="com.huawei.android.push.intent.RICHPUSH" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>
        <activity
            android:name="com.huawei.android.pushselfshow.permission.RequestPermissionsActivity"
            android:configChanges="orientation|screenSize|locale|layoutDirection"
            android:exported="false"
            android:launchMode="singleTop"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.DeviceDefault.Light.Dialog.NoActionBar"></activity>

```

### 使用
>1. 在使用推送之前,请在gradle中配置JPUSH_APPKEY字段为jpush平台的key,在小米和魅族开放平台申请的id和key进行配置,小米和魅族的配置方法:
```
Const.setMiUI_APP("APP_MIUI_ID", "APP_MIUI_KEY");
Const.setFlyme_APP("APP_FLYME_ID", "APP_FLYME_KEY");
```

>2. 创建PushInterface接口的子类,并在相关的方法里实现自己的业务逻辑,并在Push类中进行配置。
```
Push.setPushInterface(pushInterface);
```

>3. 注册推送服务,这里会根据自己的rom型号自动配置相关的推送服务:
```
Push.register(this, BuildConfig.DEBUG); //BuildConfig.DEBUG代表是否开启各个推送服务的debug功能.
```

>4.获取当前rom平台:
```
RomUtil.rom();
```

### 各个推送平台的特点
>1. 小米和极光推送做的都差不多，通知栏和透传消息都可以自己控制，没什么可说的.

>2. flyme推送
无法对通知栏做任何的控制(除了改改样式),一切都由后台控制
透传消息的话,在Receiver的onMessage()的回调中使用.

>3. 华为推送
如果推送的消息类型为透传消息的话，则无法使用extra字段,只可以用onPushMsg()回调中的 byte[] msg 参数.
通知栏的话,由于点击动作和魅族一样，都是由后台控制.

### 混淆
在library中已经配置混淆,不需要再配置

### 常见rom的区分
参考自: http://www.jianshu.com/p/6e6828755667


### License

    Copyright 2016 NewTab

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
