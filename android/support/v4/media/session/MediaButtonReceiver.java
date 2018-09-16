package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.BroadcastReceiver.PendingResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build.VERSION;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompat.ConnectionCallback;
import android.util.Log;
import android.view.KeyEvent;
import java.util.List;

public class MediaButtonReceiver extends BroadcastReceiver
{
  private static final String TAG = "MediaButtonReceiver";

  public static PendingIntent buildMediaButtonPendingIntent(Context paramContext, long paramLong)
  {
    ComponentName localComponentName = getMediaButtonReceiverComponent(paramContext);
    if (localComponentName == null)
    {
      Log.w("MediaButtonReceiver", "A unique media button receiver could not be found in the given context, so couldn't build a pending intent.");
      return null;
    }
    return buildMediaButtonPendingIntent(paramContext, localComponentName, paramLong);
  }

  public static PendingIntent buildMediaButtonPendingIntent(Context paramContext, ComponentName paramComponentName, long paramLong)
  {
    if (paramComponentName == null)
    {
      Log.w("MediaButtonReceiver", "The component name of media button receiver should be provided.");
      return null;
    }
    int i = PlaybackStateCompat.toKeyCode(paramLong);
    if (i == 0)
    {
      Log.w("MediaButtonReceiver", "Cannot build a media button pending intent with the given action: " + paramLong);
      return null;
    }
    Intent localIntent = new Intent("android.intent.action.MEDIA_BUTTON");
    localIntent.setComponent(paramComponentName);
    localIntent.putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(0, i));
    return PendingIntent.getBroadcast(paramContext, i, localIntent, 0);
  }

  static ComponentName getMediaButtonReceiverComponent(Context paramContext)
  {
    Intent localIntent = new Intent("android.intent.action.MEDIA_BUTTON");
    localIntent.setPackage(paramContext.getPackageName());
    List localList = paramContext.getPackageManager().queryBroadcastReceivers(localIntent, 0);
    if (localList.size() == 1)
    {
      ResolveInfo localResolveInfo = (ResolveInfo)localList.get(0);
      return new ComponentName(localResolveInfo.activityInfo.packageName, localResolveInfo.activityInfo.name);
    }
    if (localList.size() > 1)
      Log.w("MediaButtonReceiver", "More than one BroadcastReceiver that handles android.intent.action.MEDIA_BUTTON was found, returning null.");
    return null;
  }

  private static ComponentName getServiceComponentByAction(Context paramContext, String paramString)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    Intent localIntent = new Intent(paramString);
    localIntent.setPackage(paramContext.getPackageName());
    List localList = localPackageManager.queryIntentServices(localIntent, 0);
    if (localList.size() == 1)
    {
      ResolveInfo localResolveInfo = (ResolveInfo)localList.get(0);
      return new ComponentName(localResolveInfo.serviceInfo.packageName, localResolveInfo.serviceInfo.name);
    }
    if (localList.isEmpty())
      return null;
    throw new IllegalStateException("Expected 1 service that handles " + paramString + ", found " + localList.size());
  }

  public static KeyEvent handleIntent(MediaSessionCompat paramMediaSessionCompat, Intent paramIntent)
  {
    if ((paramMediaSessionCompat == null) || (paramIntent == null) || (!"android.intent.action.MEDIA_BUTTON".equals(paramIntent.getAction())) || (!paramIntent.hasExtra("android.intent.extra.KEY_EVENT")))
      return null;
    KeyEvent localKeyEvent = (KeyEvent)paramIntent.getParcelableExtra("android.intent.extra.KEY_EVENT");
    paramMediaSessionCompat.getController().dispatchMediaButtonEvent(localKeyEvent);
    return localKeyEvent;
  }

  private static void startForegroundService(Context paramContext, Intent paramIntent)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      paramContext.startForegroundService(paramIntent);
      return;
    }
    paramContext.startService(paramIntent);
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ((paramIntent == null) || (!"android.intent.action.MEDIA_BUTTON".equals(paramIntent.getAction())) || (!paramIntent.hasExtra("android.intent.extra.KEY_EVENT")))
    {
      Log.d("MediaButtonReceiver", "Ignore unsupported intent: " + paramIntent);
      return;
    }
    ComponentName localComponentName1 = getServiceComponentByAction(paramContext, "android.intent.action.MEDIA_BUTTON");
    if (localComponentName1 != null)
    {
      paramIntent.setComponent(localComponentName1);
      startForegroundService(paramContext, paramIntent);
      return;
    }
    ComponentName localComponentName2 = getServiceComponentByAction(paramContext, "android.media.browse.MediaBrowserService");
    if (localComponentName2 != null)
    {
      BroadcastReceiver.PendingResult localPendingResult = goAsync();
      Context localContext = paramContext.getApplicationContext();
      MediaButtonConnectionCallback localMediaButtonConnectionCallback = new MediaButtonConnectionCallback(localContext, paramIntent, localPendingResult);
      MediaBrowserCompat localMediaBrowserCompat = new MediaBrowserCompat(localContext, localComponentName2, localMediaButtonConnectionCallback, null);
      localMediaButtonConnectionCallback.setMediaBrowser(localMediaBrowserCompat);
      localMediaBrowserCompat.connect();
      return;
    }
    throw new IllegalStateException("Could not find any Service that handles android.intent.action.MEDIA_BUTTON or implements a media browser service.");
  }

  private static class MediaButtonConnectionCallback extends MediaBrowserCompat.ConnectionCallback
  {
    private final Context mContext;
    private final Intent mIntent;
    private MediaBrowserCompat mMediaBrowser;
    private final BroadcastReceiver.PendingResult mPendingResult;

    MediaButtonConnectionCallback(Context paramContext, Intent paramIntent, BroadcastReceiver.PendingResult paramPendingResult)
    {
      this.mContext = paramContext;
      this.mIntent = paramIntent;
      this.mPendingResult = paramPendingResult;
    }

    private void finish()
    {
      this.mMediaBrowser.disconnect();
      this.mPendingResult.finish();
    }

    public void onConnected()
    {
      try
      {
        new MediaControllerCompat(this.mContext, this.mMediaBrowser.getSessionToken()).dispatchMediaButtonEvent((KeyEvent)this.mIntent.getParcelableExtra("android.intent.extra.KEY_EVENT"));
        finish();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        while (true)
          Log.e("MediaButtonReceiver", "Failed to create a media controller", localRemoteException);
      }
    }

    public void onConnectionFailed()
    {
      finish();
    }

    public void onConnectionSuspended()
    {
      finish();
    }

    void setMediaBrowser(MediaBrowserCompat paramMediaBrowserCompat)
    {
      this.mMediaBrowser = paramMediaBrowserCompat;
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v4.media.session.MediaButtonReceiver
 * JD-Core Version:    0.6.0
 */