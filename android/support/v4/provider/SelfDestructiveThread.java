package android.support.v4.provider;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.GuardedBy;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@RestrictTo({android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP})
public class SelfDestructiveThread
{
  private static final int MSG_DESTRUCTION = 0;
  private static final int MSG_INVOKE_RUNNABLE = 1;
  private Handler.Callback mCallback = new Handler.Callback()
  {
    public boolean handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
        return true;
      case 1:
        SelfDestructiveThread.this.onInvokeRunnable((Runnable)paramMessage.obj);
        return true;
      case 0:
      }
      SelfDestructiveThread.this.onDestruction();
      return true;
    }
  };
  private final int mDestructAfterMillisec;

  @GuardedBy("mLock")
  private int mGeneration;

  @GuardedBy("mLock")
  private Handler mHandler;
  private final Object mLock = new Object();
  private final int mPriority;

  @GuardedBy("mLock")
  private HandlerThread mThread;
  private final String mThreadName;

  public SelfDestructiveThread(String paramString, int paramInt1, int paramInt2)
  {
    this.mThreadName = paramString;
    this.mPriority = paramInt1;
    this.mDestructAfterMillisec = paramInt2;
    this.mGeneration = 0;
  }

  private void onDestruction()
  {
    synchronized (this.mLock)
    {
      if (this.mHandler.hasMessages(1))
        return;
      this.mThread.quit();
      this.mThread = null;
      this.mHandler = null;
      return;
    }
  }

  private void onInvokeRunnable(Runnable paramRunnable)
  {
    paramRunnable.run();
    synchronized (this.mLock)
    {
      this.mHandler.removeMessages(0);
      this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0), this.mDestructAfterMillisec);
      return;
    }
  }

  private void post(Runnable paramRunnable)
  {
    synchronized (this.mLock)
    {
      if (this.mThread == null)
      {
        this.mThread = new HandlerThread(this.mThreadName, this.mPriority);
        this.mThread.start();
        this.mHandler = new Handler(this.mThread.getLooper(), this.mCallback);
        this.mGeneration = (1 + this.mGeneration);
      }
      this.mHandler.removeMessages(0);
      this.mHandler.sendMessage(this.mHandler.obtainMessage(1, paramRunnable));
      return;
    }
  }

  @VisibleForTesting
  public int getGeneration()
  {
    synchronized (this.mLock)
    {
      int i = this.mGeneration;
      return i;
    }
  }

  @VisibleForTesting
  public boolean isRunning()
  {
    while (true)
    {
      synchronized (this.mLock)
      {
        if (this.mThread != null)
        {
          i = 1;
          return i;
        }
      }
      int i = 0;
    }
  }

  public <T> void postAndReply(Callable<T> paramCallable, ReplyCallback<T> paramReplyCallback)
  {
    post(new Runnable(paramCallable, new Handler(), paramReplyCallback)
    {
      public void run()
      {
        try
        {
          Object localObject2 = this.val$callable.call();
          localObject1 = localObject2;
          this.val$callingHandler.post(new Runnable(localObject1)
          {
            public void run()
            {
              SelfDestructiveThread.2.this.val$reply.onReply(this.val$result);
            }
          });
          return;
        }
        catch (Exception localException)
        {
          while (true)
            Object localObject1 = null;
        }
      }
    });
  }

  // ERROR //
  public <T> T postAndWait(Callable<T> paramCallable, int paramInt)
    throws java.lang.InterruptedException
  {
    // Byte code:
    //   0: new 133	java/util/concurrent/locks/ReentrantLock
    //   3: dup
    //   4: invokespecial 134	java/util/concurrent/locks/ReentrantLock:<init>	()V
    //   7: astore_3
    //   8: aload_3
    //   9: invokevirtual 138	java/util/concurrent/locks/ReentrantLock:newCondition	()Ljava/util/concurrent/locks/Condition;
    //   12: astore 4
    //   14: new 140	java/util/concurrent/atomic/AtomicReference
    //   17: dup
    //   18: invokespecial 141	java/util/concurrent/atomic/AtomicReference:<init>	()V
    //   21: astore 5
    //   23: new 143	java/util/concurrent/atomic/AtomicBoolean
    //   26: dup
    //   27: iconst_1
    //   28: invokespecial 146	java/util/concurrent/atomic/AtomicBoolean:<init>	(Z)V
    //   31: astore 6
    //   33: aload_0
    //   34: new 148	android/support/v4/provider/SelfDestructiveThread$3
    //   37: dup
    //   38: aload_0
    //   39: aload 5
    //   41: aload_1
    //   42: aload_3
    //   43: aload 6
    //   45: aload 4
    //   47: invokespecial 151	android/support/v4/provider/SelfDestructiveThread$3:<init>	(Landroid/support/v4/provider/SelfDestructiveThread;Ljava/util/concurrent/atomic/AtomicReference;Ljava/util/concurrent/Callable;Ljava/util/concurrent/locks/ReentrantLock;Ljava/util/concurrent/atomic/AtomicBoolean;Ljava/util/concurrent/locks/Condition;)V
    //   50: invokespecial 127	android/support/v4/provider/SelfDestructiveThread:post	(Ljava/lang/Runnable;)V
    //   53: aload_3
    //   54: invokevirtual 154	java/util/concurrent/locks/ReentrantLock:lock	()V
    //   57: aload 6
    //   59: invokevirtual 157	java/util/concurrent/atomic/AtomicBoolean:get	()Z
    //   62: ifne +17 -> 79
    //   65: aload 5
    //   67: invokevirtual 160	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
    //   70: astore 16
    //   72: aload_3
    //   73: invokevirtual 163	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   76: aload 16
    //   78: areturn
    //   79: getstatic 169	java/util/concurrent/TimeUnit:MILLISECONDS	Ljava/util/concurrent/TimeUnit;
    //   82: iload_2
    //   83: i2l
    //   84: invokevirtual 173	java/util/concurrent/TimeUnit:toNanos	(J)J
    //   87: lstore 8
    //   89: lload 8
    //   91: lstore 10
    //   93: aload 4
    //   95: lload 10
    //   97: invokeinterface 178 3 0
    //   102: lstore 14
    //   104: lload 14
    //   106: lstore 10
    //   108: aload 6
    //   110: invokevirtual 157	java/util/concurrent/atomic/AtomicBoolean:get	()Z
    //   113: ifne +17 -> 130
    //   116: aload 5
    //   118: invokevirtual 160	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
    //   121: astore 13
    //   123: aload_3
    //   124: invokevirtual 163	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   127: aload 13
    //   129: areturn
    //   130: lload 10
    //   132: lconst_0
    //   133: lcmp
    //   134: ifgt -41 -> 93
    //   137: new 131	java/lang/InterruptedException
    //   140: dup
    //   141: ldc 180
    //   143: invokespecial 183	java/lang/InterruptedException:<init>	(Ljava/lang/String;)V
    //   146: athrow
    //   147: astore 7
    //   149: aload_3
    //   150: invokevirtual 163	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   153: aload 7
    //   155: athrow
    //   156: astore 12
    //   158: goto -50 -> 108
    //
    // Exception table:
    //   from	to	target	type
    //   57	72	147	finally
    //   79	89	147	finally
    //   93	104	147	finally
    //   108	123	147	finally
    //   137	147	147	finally
    //   93	104	156	java/lang/InterruptedException
  }

  public static abstract interface ReplyCallback<T>
  {
    public abstract void onReply(T paramT);
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v4.provider.SelfDestructiveThread
 * JD-Core Version:    0.6.0
 */