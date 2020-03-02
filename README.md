#### BroadcastReceiver 广播接收器

BroadcastReceiver 默认运行在主线程,你也可以通过指定一个Handler使BroadcastReceiver运行在不同的线程

举个例子
```java
private Handler handler;
private Thread thread;
private MyReceiver receiver;
    
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        receiver = new MyReceiver();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler = new Handler(Looper.myLooper());
                Looper.loop();
            }
        });
        thread.start();
    }
    
    /**
         * 发送有序广播
         *
         * @param view
         */
        public void sendOrderBroadcast(View view) {
            Intent intent = new Intent();
            intent.setAction("com.brotherd.broadcastdemo.BROADCAST");
            IntentFilter filter = new IntentFilter("com.brotherd.broadcastdemo.BROADCAST");
            //指定BroadcastReceiver的工作线程
            registerReceiver(receiver, filter,null,handler);
            sendOrderedBroadcast(intent, null);
        }

```

在MyReceiver的
```java
public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: this is " + this.toString() + "in thread " + Thread.currentThread().getName());
        Toast.makeText(context.getApplicationContext(), "MyReceiver onReceive", Toast.LENGTH_SHORT).show();
    }
}

```
打印出来的结果
```
MyReceiver: onReceive: this is com.brotherd.broadcastdemo.MyReceiver@8c8c76ein thread Thread-1186
```

*当BroadcastReceiver在主线程工作的话，不能在onReceive()方法中做耗时操作，如果超过10秒会出现ANR。
如果要执行比较耗时的操作，则要考虑由当前 BroadcastReceiver 启动新的 Service 来完成操作也不能在onReceive()方法中弹出一个 popup dialog

广播的种类
1. 标准广播:异步执行的广播，在广播发出之后，所有的广播接收器几乎会在同一时刻收到这条广播消息。这种广播效率比较高，但同时意味着它是无法被截断的。
2. 有序广播：同步执行的广播，在广播发出之后，同一时刻只有一个广播接收器能够收到这条广播消息，当这个广播接收器中的逻辑执行完毕后，广播才会继续传递。
优先级高的广播接收器先收到广播消息，并且前面的广播接收器可以阶段正再传递的广播，这样后面的广播接收器就无法收到广播消息了。
3. 本地广播:本地广播只能在应用程序内部进行传递。本地广播通过一个LocalBroadcastManager来对广播进行管理，并提供了发送广播和注册广播接收器的方法。
LocalBroadcastManager发送的广播只有通过LocalBroadcastManager进行注册广播接收器才能接收到广播。
4. 粘性广播已经不推荐使用了。

* 静态注册 :每次广播事件到来的时候，系统会创建新的BroadcastReceiver实例，
并且自动触发他的 onReceive() 方法，onReceive() 方法执行完后，BroadcastReceiver 的实例就会被销毁。
```
<receiver
        android:name=".MyReceiver"
        android:enabled="true"
        android:exported="true">
        <!--指定优先级-->
        <intent-filter android:priority="100">
            <action android:name="com.brotherd.broadcastdemo.BROADCAST" />
        </intent-filter>
</receiver>
```

*动态注册 只会创建一个BroadcastReceiver实例，要记得在适当的时候解除注册
```
Intent intent = new Intent();
intent.setAction("com.brotherd.broadcastdemo.BROADCAST");
IntentFilter filter = new IntentFilter("com.brotherd.broadcastdemo.BROADCAST");
registerReceiver(receiver, filter);
```

### 广播接收器里面是否可以弹出dialog?

1. 如果广播接收器里面的Context是Activity，是可以正常弹出的。

2. 如果不是Activity，我测试的结果是不可以。



