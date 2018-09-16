package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public final class LocalBroadcastManager
{
  private static final boolean DEBUG = false;
  static final int MSG_EXEC_PENDING_BROADCASTS = 1;
  private static final String TAG = "LocalBroadcastManager";
  private static LocalBroadcastManager mInstance;
  private static final Object mLock = new Object();
  private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap();
  private final Context mAppContext;
  private final Handler mHandler;
  private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList();
  private final HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> mReceivers = new HashMap();

  private LocalBroadcastManager(Context paramContext)
  {
    this.mAppContext = paramContext;
    this.mHandler = new Handler(paramContext.getMainLooper())
    {
      public void handleMessage(Message paramMessage)
      {
        switch (paramMessage.what)
        {
        default:
          super.handleMessage(paramMessage);
          return;
        case 1:
        }
        LocalBroadcastManager.this.executePendingBroadcasts();
      }
    };
  }

  private void executePendingBroadcasts()
  {
    while (true)
    {
      int j;
      synchronized (this.mReceivers)
      {
        int i = this.mPendingBroadcasts.size();
        if (i <= 0)
          return;
        BroadcastRecord[] arrayOfBroadcastRecord = new BroadcastRecord[i];
        this.mPendingBroadcasts.toArray(arrayOfBroadcastRecord);
        this.mPendingBroadcasts.clear();
        j = 0;
        if (j >= arrayOfBroadcastRecord.length)
          continue;
        BroadcastRecord localBroadcastRecord = arrayOfBroadcastRecord[j];
        int k = localBroadcastRecord.receivers.size();
        int m = 0;
        if (m < k)
        {
          ReceiverRecord localReceiverRecord = (ReceiverRecord)localBroadcastRecord.receivers.get(m);
          if (localReceiverRecord.dead)
            continue;
          localReceiverRecord.receiver.onReceive(this.mAppContext, localBroadcastRecord.intent);
          m++;
        }
      }
      j++;
    }
  }

  public static LocalBroadcastManager getInstance(Context paramContext)
  {
    synchronized (mLock)
    {
      if (mInstance == null)
        mInstance = new LocalBroadcastManager(paramContext.getApplicationContext());
      LocalBroadcastManager localLocalBroadcastManager = mInstance;
      return localLocalBroadcastManager;
    }
  }

  public void registerReceiver(BroadcastReceiver paramBroadcastReceiver, IntentFilter paramIntentFilter)
  {
    synchronized (this.mReceivers)
    {
      ReceiverRecord localReceiverRecord = new ReceiverRecord(paramIntentFilter, paramBroadcastReceiver);
      ArrayList localArrayList1 = (ArrayList)this.mReceivers.get(paramBroadcastReceiver);
      if (localArrayList1 == null)
      {
        localArrayList1 = new ArrayList(1);
        this.mReceivers.put(paramBroadcastReceiver, localArrayList1);
      }
      localArrayList1.add(localReceiverRecord);
      for (int i = 0; i < paramIntentFilter.countActions(); i++)
      {
        String str = paramIntentFilter.getAction(i);
        ArrayList localArrayList2 = (ArrayList)this.mActions.get(str);
        if (localArrayList2 == null)
        {
          localArrayList2 = new ArrayList(1);
          this.mActions.put(str, localArrayList2);
        }
        localArrayList2.add(localReceiverRecord);
      }
      return;
    }
  }

  public boolean sendBroadcast(Intent paramIntent)
  {
    int i;
    label162: int j;
    int m;
    ArrayList localArrayList2;
    synchronized (this.mReceivers)
    {
      String str1 = paramIntent.getAction();
      String str2 = paramIntent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
      Uri localUri = paramIntent.getData();
      String str3 = paramIntent.getScheme();
      Set localSet = paramIntent.getCategories();
      if ((0x8 & paramIntent.getFlags()) == 0)
        break label500;
      i = 1;
      if (i != 0)
        Log.v("LocalBroadcastManager", "Resolving type " + str2 + " scheme " + str3 + " of intent " + paramIntent);
      ArrayList localArrayList1 = (ArrayList)this.mActions.get(paramIntent.getAction());
      if (localArrayList1 == null)
        break label481;
      if (i == 0)
        break label485;
      Log.v("LocalBroadcastManager", "Action list: " + localArrayList1);
      break label485;
      if (j >= localArrayList1.size())
        break label534;
      ReceiverRecord localReceiverRecord = (ReceiverRecord)localArrayList1.get(j);
      if (i != 0)
        Log.v("LocalBroadcastManager", "Matching against filter " + localReceiverRecord.filter);
      if (localReceiverRecord.broadcasting)
      {
        if (i == 0)
          break label494;
        Log.v("LocalBroadcastManager", "  Filter's target already added");
        break label494;
      }
      m = localReceiverRecord.filter.match(str1, str2, str3, localUri, localSet, "LocalBroadcastManager");
      if (m >= 0)
      {
        if (i != 0)
          Log.v("LocalBroadcastManager", "  Filter matched!  match=0x" + Integer.toHexString(m));
        if (localArrayList2 == null)
          localArrayList2 = new ArrayList();
        localArrayList2.add(localReceiverRecord);
        localReceiverRecord.broadcasting = true;
      }
    }
    String str4;
    if (i != 0)
      switch (m)
      {
      default:
        str4 = "unknown reason";
        label380: Log.v("LocalBroadcastManager", "  Filter did not match: " + str4);
        break;
      case -3:
      case -4:
      case -2:
      case -1:
      }
    while (true)
    {
      if (k < localArrayList2.size())
      {
        ((ReceiverRecord)localArrayList2.get(k)).broadcasting = false;
        k++;
        continue;
      }
      this.mPendingBroadcasts.add(new BroadcastRecord(paramIntent, localArrayList2));
      if (!this.mHandler.hasMessages(1))
        this.mHandler.sendEmptyMessage(1);
      monitorexit;
      return true;
      label481: label485: label494: label500: 
      do
      {
        monitorexit;
        return false;
        j = 0;
        localArrayList2 = null;
        break label162;
        j++;
        break label162;
        i = 0;
        break;
        str4 = "action";
        break label380;
        str4 = "category";
        break label380;
        str4 = "data";
        break label380;
        str4 = "type";
        break label380;
      }
      while (localArrayList2 == null);
      label534: int k = 0;
    }
  }

  public void sendBroadcastSync(Intent paramIntent)
  {
    if (sendBroadcast(paramIntent))
      executePendingBroadcasts();
  }

  public void unregisterReceiver(BroadcastReceiver paramBroadcastReceiver)
  {
    while (true)
    {
      int i;
      int j;
      int k;
      synchronized (this.mReceivers)
      {
        ArrayList localArrayList1 = (ArrayList)this.mReceivers.remove(paramBroadcastReceiver);
        if (localArrayList1 == null)
          return;
        i = -1 + localArrayList1.size();
        if (i < 0)
          continue;
        ReceiverRecord localReceiverRecord1 = (ReceiverRecord)localArrayList1.get(i);
        localReceiverRecord1.dead = true;
        j = 0;
        if (j >= localReceiverRecord1.filter.countActions())
          break label200;
        String str = localReceiverRecord1.filter.getAction(j);
        ArrayList localArrayList2 = (ArrayList)this.mActions.get(str);
        if (localArrayList2 == null)
          break label194;
        k = -1 + localArrayList2.size();
        if (k < 0)
          continue;
        ReceiverRecord localReceiverRecord2 = (ReceiverRecord)localArrayList2.get(k);
        if (localReceiverRecord2.receiver == paramBroadcastReceiver)
        {
          localReceiverRecord2.dead = true;
          localArrayList2.remove(k);
          break label188;
          if (localArrayList2.size() > 0)
            break label194;
          this.mActions.remove(str);
          break label194;
          return;
        }
      }
      label188: k--;
      continue;
      label194: j++;
      continue;
      label200: i--;
    }
  }

  private static final class BroadcastRecord
  {
    final Intent intent;
    final ArrayList<LocalBroadcastManager.ReceiverRecord> receivers;

    BroadcastRecord(Intent paramIntent, ArrayList<LocalBroadcastManager.ReceiverRecord> paramArrayList)
    {
      this.intent = paramIntent;
      this.receivers = paramArrayList;
    }
  }

  private static final class ReceiverRecord
  {
    boolean broadcasting;
    boolean dead;
    final IntentFilter filter;
    final BroadcastReceiver receiver;

    ReceiverRecord(IntentFilter paramIntentFilter, BroadcastReceiver paramBroadcastReceiver)
    {
      this.filter = paramIntentFilter;
      this.receiver = paramBroadcastReceiver;
    }

    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder(128);
      localStringBuilder.append("Receiver{");
      localStringBuilder.append(this.receiver);
      localStringBuilder.append(" filter=");
      localStringBuilder.append(this.filter);
      if (this.dead)
        localStringBuilder.append(" DEAD");
      localStringBuilder.append("}");
      return localStringBuilder.toString();
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v4.content.LocalBroadcastManager
 * JD-Core Version:    0.6.0
 */