
更方便的将各个rom厂商自己的推送服务进行集成,并统一管理,使用前还是需要熟悉各个平台的相关文档

## 注意千万不要直接将库引用到自己的项目，而是要将代码拷贝到项目里，如果推送不成功，请仔细查看官网的说明文档

### 各个平台的说明
>1. MIUI系统将使用[小米推送](http://dev.xiaomi.com/doc/?page_id=1670) 

>2. Flyme系统将使用[魅族推送](https://open.flyme.cn/open-web/views/push.html?t=1476262864945)

>3. EMUI系统将使用[华为推送](http://developer.huawei.com/push)

>4. 其他系统将使用[JPush推送](https://www.jiguang.cn)

### 集成(请看app下的demo)
* 将项目中的aar文件夹的四个包导入到自己的项目并添加依赖，在自己项目下的gradle文件添加
```
  //app下的gradle
    defaultConfig {
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

* 在自己项目下的manifest文件中添加的代码请参考项目中的demo和push库中的相关文件
```
code...
```

### 使用
* 在使用推送之前,请在gradle中配置JPUSH_APPKEY字段为jpush平台的key,在小米和魅族开放平台申请的id和key进行配置,小米和魅族的配置方法:
```
Const.setMiUI_APP("APP_MIUI_ID", "APP_MIUI_KEY");
Const.setFlyme_APP("APP_FLYME_ID", "APP_FLYME_KEY");
```

* 创建PushInterface接口的子类,并在相关的方法里实现自己的业务逻辑,并在Push类中进行配置。
```
Push.setPushInterface(pushInterface);
```

* 注册推送服务,这里会根据自己的rom型号自动配置相关的推送服务:
```
Push.register(this, BuildConfig.DEBUG); //BuildConfig.DEBUG代表是否开启各个推送服务的debug功能.
```

* 获取当前rom平台:
```
RomUtil.rom();
```

### 各个推送平台的特点
1. 小米和极光推送做的都差不多，通知栏和透传消息都可以自己控制，没什么可说的.
2. flyme推送
无法对通知栏做任何的控制(除了改改样式),一切都由后台控制
透传消息的话,在Receiver的onMessage()的回调中使用.

3. 华为推送
如果推送的消息类型为透传消息的话，则无法使用extra字段,只可以用onPushMsg()回调中的 byte[] msg 参数.
通知栏的话,由于点击动作和魅族一样，都是由后台控制.

### 混淆
请参考 https://github.com/jiang111/AndroidPush/blob/master/push/proguard-rules.pro

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
