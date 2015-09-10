# CrashCatcher


一个简单的Crash捕获工具,会将崩溃日志保存到本地.


## Usage

### AndroidStudio
1. add dependency

    `debugCompile 'yifeiyuan.library.crashcatcher:crashcatcher:0.0.2'`   
    
    `releaseCompile 'yifeiyuan.library.crashcatcher:crashcatcher-no-op:0.0.2'`
    
2. 在Application里添加代码:

```
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashCatcher.ready().toCatch(this);
    }
}
```

### Eclipse

![](http://ww2.sinaimg.cn/large/98900c07gw1evxry47ngtj20dw09a3yu.jpg)

That's all!

还在开发中,还比较简陋!~

## Thanks
@drakeet 

