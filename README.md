## Discuz-Android
IDE:Android Studio 1.2.1.1
SDK:21
JDK:1.7
网络请求包:OKHTTP，OKhttp-android-support,okhttp-urlconnection,okio
图片加载缓存：picasso(基于okhttp)


## 更改内容注意事项
1. app/build.gradle
    1. applicationId
    2. buildTypes > release 打包时的名称
3. 网络API
    1. AppNetConfig > BASE_URL
    2. 全局搜索替换，因为本地JavaScript中也使用了地址
3. UI
    1. 主题色blue
    2. 相关图标替换，包含ic_launcher, splash在内
4. string.xml > app_name


## 问题反馈
https://bbs.comsenz-service.com/forum-58-1.html
