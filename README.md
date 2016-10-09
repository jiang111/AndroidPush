# AndroidPush

### 使用(详细使用请看app下的demo)
>1. 调用Push的register()方法即可。在调用该方法之前，请确保已经设置了Const类中小米和魅族的app_key和app_id然后，代码会判断当前手机的类型，如果是小米则启动小米推送，华为则启用华为推送，flyme则启用flyme推送，其他情况下启用极光推送
>2. 实现PushInterface接口，然后根据各个回调方法做相关的任务
>3. 在自己项目下的gradle文件添加
```
 manifestPlaceholders = [
                JPUSH_PKGNAME : applicationId,
                JPUSH_APPKEY : "sdsds",
                JPUSH_CHANNEL : "develop",
                PNAME : applicationId   //其实PNAME字段和JPUSH_PKGNAME都是指应用程序的包名
        ]
```
以用来覆盖掉library中的字段数据。
>4. 

### 各个推送的特殊情况说明 （个人理解）
>1. 小米和极光推送做的都很不错，没什么可说的

>2. 华为推送
如果推送的消息类型为透传消息的话，则无法使用extra字段，只可以用onPushMsg()回调中的 byte[] msg 参数。
通知栏的话,只有点击以后的回调接口

>3. flyme推送
无法对通知栏做任何的控制(除了改改样式),一切都由服务器控制
透传消息的话，在onMessage()的回调中使用。
