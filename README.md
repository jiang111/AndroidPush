
更方便的将各个rom厂商自己的推送服务进行集成,并统一管理,使用前还是需要熟悉各个平台的相关文档

### 各个平台的说明
>1. MIUI系统将使用[小米推送](http://dev.xiaomi.com/doc/?page_id=1670)
>2. Flyme系统将使用[魅族推送](https://open.flyme.cn/open-web/views/push.html?t=1476262864945)
>3. EMUI系统将使用[华为推送](http://developer.huawei.com/push)
>4. 其他系统将使用[JPush推送](https://www.jiguang.cn)

### 集成(请看app下的demo)
>1. 将项目中的aar文件夹的四个包导入到自己的项目并添加依赖，在自己项目下的gradle文件添加
```
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
    
<!--huawei -->
<uses-permission android:name="com.huawei.pushagent.permission.RICHMEDIA_PROVIDER" />
<permission
android:name="${JPUSH_PKGNAME}.permission.JPUSH_MESSAGE"
android:protectionLevel="signature" />
<!-- flyme -->
<uses-permission android:name="com.meizu.flyme.push.permission.RECEIVE"></uses-permission>
<permission
android:name="${PNAME}.push.permission.MESSAGE"
android:protectionLevel="signature" />
<uses-permission android:name="${PNAME}.push.permission.MESSAGE"></uses-permission>
<!-- 兼容flyme3.0配置权限-->
<uses-permission android:name="com.meizu.c2dm.permission.RECEIVE" />
<permission
android:name="${PNAME}.permission.C2D_MESSAGE"
android:protectionLevel="signature"></permission>
<uses-permission android:name="${PNAME}.permission.C2D_MESSAGE" />
<!-- flyme end -->
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
            android:name="package.JPushReceiver"
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
        <receiver android:name="package.EMHuaweiPushReceiver">
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
            android:name="package.MiuiReceiver"
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
        <receiver android:name="package.FlymeReceiver">
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
            
<!-- jpush -->
<!-- Required SDK 核心功能-->
<!-- option since 2.0.5 可配置PushService，DaemonService,PushReceiver,AlarmReceiver的android:process参数 将JPush相关组件设置为一个独立进程 -->
<!-- 如：android:process=":remote" -->
<service
android:name="cn.jpush.android.service.PushService"
android:enabled="true"
android:exported="false">
<intent-filter>
<action android:name="cn.jpush.android.intent.REGISTER" />
<action android:name="cn.jpush.android.intent.REPORT" />
<action android:name="cn.jpush.android.intent.PushService" />
<action android:name="cn.jpush.android.intent.PUSH_TIME" />
</intent-filter>
</service>
<!-- since 1.8.0 option 可选项。用于同一设备中不同应用的JPush服务相互拉起的功能。 -->
<!-- 若不启用该功能可删除该组件，将不拉起其他应用也不能被其他应用拉起 -->
<service
android:name="cn.jpush.android.service.DaemonService"
android:enabled="true"
android:exported="true">
<intent-filter>
<action android:name="cn.jpush.android.intent.DaemonService" />
<category android:name="${JPUSH_PKGNAME}" />
</intent-filter>
</service>
<!-- Required -->
<receiver
android:name="cn.jpush.android.service.PushReceiver"
android:enabled="true">
<intent-filter android:priority="1000">
<action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED_PROXY" />
<category android:name="${JPUSH_PKGNAME}" />
</intent-filter>
<intent-filter>
<action android:name="android.intent.action.USER_PRESENT" />
<action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
</intent-filter>
<!-- Optional -->
<intent-filter>
<action android:name="android.intent.action.PACKAGE_ADDED" />
<action android:name="android.intent.action.PACKAGE_REMOVED" />
<data android:scheme="package" />
</intent-filter>
</receiver>
<!-- Required SDK核心功能-->
<activity
android:name="cn.jpush.android.ui.PushActivity"
android:configChanges="orientation|keyboardHidden"
android:exported="false">
<intent-filter>
<action android:name="cn.jpush.android.ui.PushActivity" />
<category android:name="android.intent.category.DEFAULT" />
<category android:name="${JPUSH_PKGNAME}" />
</intent-filter>
</activity>
<!-- Required SDK核心功能-->
<service
android:name="cn.jpush.android.service.DownloadService"
android:enabled="true"
android:exported="false"></service>
<!-- Required SDK核心功能-->
<receiver android:name="cn.jpush.android.service.AlarmReceiver" />
<!-- User defined. 用户自定义的广播接收器-->
<!-- Required. For publish channel feature -->
<!-- JPUSH_CHANNEL 是为了方便开发者统计APK分发渠道。-->
<!-- 例如: -->
<!-- 发到 Google Play 的APK可以设置为 google-play; -->
<!-- 发到其他市场的 APK 可以设置为 xxx-market。 -->
<!-- 目前这个渠道统计功能的报表还未开放。-->
<meta-data
android:name="JPUSH_CHANNEL"
android:value="${JPUSH_CHANNEL}" />
<!-- Required. AppKey copied from Portal -->
<meta-data
android:name="JPUSH_APPKEY"
android:value="${JPUSH_APPKEY}" />
<!-- jpush end -->
<!-- huawei -->
<!-- 第三方相关 :接收Push消息(注册、Push消息、Push连接状态、标签，LBS上报 结果)广播 -->
<!-- 备注:Push相关的android组件需要添加到业务的AndroidManifest.xml,
Push相关android组件运行在另外一个进程是为了防止Push服务异常而影响主业务
PushSDK:PushSDK接收外部请求事件入口 -->
<receiver
android:name="com.huawei.android.pushagent.PushEventReceiver"
android:process=":pushservice">
<intent-filter>
<action android:name="com.huawei.android.push.intent.REFRESH_PUSH_CHANNEL" />
/>
<action android:name="com.huawei.intent.action.PUSH" />
<action android:name="com.huawei.intent.action.PUSH_ON" />
<action android:name="com.huawei.android.push.PLUGIN" />
</intent-filter>
<intent-filter>
<action android:name="android.intent.action.PACKAGE_ADDED" />
<action android:name="android.intent.action.PACKAGE_REMOVED" />
<data android:scheme="package" />
</intent-filter>
</receiver>
<receiver
android:name="com.huawei.android.pushagent.PushBootReceiver"
android:process=":pushservice">
<intent-filter>
<action android:name="com.huawei.android.push.intent.REGISTER" />
<action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
</intent-filter>
<meta-data
android:name="CS_cloud_version"
android:value="\u0032\u0037\u0030\u0034" />
</receiver>
<!-- PushSDK:Push服务 -->
<service
android:name="com.huawei.android.pushagent.PushService"
android:process=":pushservice"></service>
<!-- PushSDK:富媒体呈现页面，用于呈现服务器下发的富媒体消息 --> <!-- locale|layoutDirection 切换语言后不重新创建activity -->
<!-- huawei end -->
<!-- xiaomi -->
<service
android:name="com.xiaomi.push.service.XMPushService"
android:enabled="true"
android:process=":pushservice" />
<service
android:name="com.xiaomi.push.service.XMJobService"
android:enabled="true"
android:exported="false"
android:permission="android.permission.BIND_JOB_SERVICE"
android:process=":pushservice" />
<!--注：此service必须在3.0.1版本以后（包括3.0.1版本）加入-->
<service
android:name="com.xiaomi.mipush.sdk.PushMessageHandler"
android:enabled="true"
android:exported="true" />
<service
android:name="com.xiaomi.mipush.sdk.MessageHandleService"
android:enabled="true" />
<!--注：此service必须在2.2.5版本以后（包括2.2.5版本）加入-->
<receiver
android:name="com.xiaomi.push.service.receivers.NetworkStatusReceiver"
android:exported="true">
<intent-filter>
<action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
<category android:name="android.intent.category.DEFAULT" />
</intent-filter>
</receiver>
<receiver
android:name="com.xiaomi.push.service.receivers.PingReceiver"
android:exported="false"
android:process=":pushservice">
<intent-filter>
<action android:name="com.xiaomi.push.PING_TIMER" />
</intent-filter>
</receiver>
<!-- xiaomi end -->

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

## 注意千万不要直接将库引用到自己的项目，而是要将代码拷贝到项目里

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
