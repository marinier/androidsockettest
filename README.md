# Reproducer for socket exception


## Background
I have a complex Gluon Mobile application I can't share that I was previously building with Gluon GraalVM 21.2. It uses Socket.IO to connect to another application. Under Gluon GraalVM 22.0 it fails to create the socket.

This application was created using the Gluon Mobile Eclipse plugin (single view project). The versions of the various dependencies and plugins have been updated to their latest versions. The socket creation occurs in `GluonApplication`'s constructor.

I have tried building with various versions of GraalVM 22:
* Gluon GraalVM 22.0.0.3 JDK 11

This has been reproduced on phones running Android 12. (Other versions not tried.)

## Reproducing the issue
This is a maven project, so in Linux (I used an Ubuntu 20.04 VM) to run do this:

```
mvn -Pandroid gluonfx:build gluonfx:package gluonfx:install gluonfx:nativerun
```

The socket is created during `GluonApplication`'s constructor.

When it works there is no special output.


When it fails it looks like this:

```
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): Exception in thread "OkHttp Dispatcher" java.lang.NoSuchFieldError: java.net.Inet6Address.cached_scope_id
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at com.oracle.svm.jni.functions.JNIFunctions$Support.getFieldID(JNIFunctions.java:1207)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at com.oracle.svm.jni.functions.JNIFunctions.GetFieldID(JNIFunctions.java:434)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at java.net.Inet6Address.init(Inet6Address.java)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at java.net.Inet6Address.<clinit>(Inet6Address.java:369)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at java.lang.Class.ensureInitialized(DynamicHub.java:510)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at com.oracle.svm.jni.functions.JNIFunctions.FindClass(JNIFunctions.java:355)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at java.net.PlainSocketImpl.initProto(PlainSocketImpl.java)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at java.net.PlainSocketImpl.<clinit>(PlainSocketImpl.java:43)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at java.lang.Class.ensureInitialized(DynamicHub.java:510)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at java.net.Socket.setImpl(Socket.java:523)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at java.net.Socket.<init>(Socket.java:88)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at javax.net.DefaultSocketFactory.createSocket(SocketFactory.java:265)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.connection.RealConnection.connectSocket(RealConnection.java:241)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.connection.RealConnection.connect(RealConnection.java:167)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.connection.StreamAllocation.findConnection(StreamAllocation.java:258)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.connection.StreamAllocation.findHealthyConnection(StreamAllocation.java:135)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.connection.StreamAllocation.newStream(StreamAllocation.java:114)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.connection.ConnectInterceptor.intercept(ConnectInterceptor.java:42)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:121)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.cache.CacheInterceptor.intercept(CacheInterceptor.java:93)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:121)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.http.BridgeInterceptor.intercept(BridgeInterceptor.java:93)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.http.RetryAndFollowUpInterceptor.intercept(RetryAndFollowUpInterceptor.java:127)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:121)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.RealCall.getResponseWithInterceptorChain(RealCall.java:257)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.RealCall$AsyncCall.execute(RealCall.java:201)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at okhttp3.internal.NamedRunnable.run(NamedRunnable.java:32)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at java.lang.Thread.run(Thread.java:829)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at com.oracle.svm.core.thread.JavaThreads.threadStartRoutine(JavaThreads.java:597)
[Mon. Feb. 14 12:25:32 EST 2022][INFO] [SUB] D/GraalCompiled( 7241): 	at com.oracle.svm.core.posix.thread.PosixJavaThreads.pthreadStartRoutine(PosixJavaThreads.java:194)

```

Since this looks like it could be reflection related, I added this to the reflection-config.json:

```
{
  "name":"java.net.Inet6Address",
  "allDeclaredConstructors" : true,
  "allPublicConstructors" : true,
  "allDeclaredMethods" : true,
  "allPublicMethods" : true,
  "allDeclaredClasses" : true,
  "allPublicClasses" : true,
  "queryAllPublicConstructors":true
}
```

But that makes no difference. (The rest of the reflection config was generated using the native agent.)
