package android.support.v4.media.session;

import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(24)
class MediaSessionCompatApi24
{
  private static final String TAG = "MediaSessionCompatApi24";

  public static Object createCallback(Callback paramCallback)
  {
    return new CallbackProxy(paramCallback);
  }

  public static String getCallingPackage(Object paramObject)
  {
    MediaSession localMediaSession = (MediaSession)paramObject;
    try
    {
      String str = (String)localMediaSession.getClass().getMethod("getCallingPackage", new Class[0]).invoke(localMediaSession, new Object[0]);
      return str;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      Log.e("MediaSessionCompatApi24", "Cannot execute MediaSession.getCallingPackage()", localIllegalAccessException);
      return null;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      break label35;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      label35: break label35;
    }
  }

  public static abstract interface Callback extends MediaSessionCompatApi23.Callback
  {
    public abstract void onPrepare();

    public abstract void onPrepareFromMediaId(String paramString, Bundle paramBundle);

    public abstract void onPrepareFromSearch(String paramString, Bundle paramBundle);

    public abstract void onPrepareFromUri(Uri paramUri, Bundle paramBundle);
  }

  static class CallbackProxy<T extends MediaSessionCompatApi24.Callback> extends MediaSessionCompatApi23.CallbackProxy<T>
  {
    public CallbackProxy(T paramT)
    {
      super();
    }

    public void onPrepare()
    {
      ((MediaSessionCompatApi24.Callback)this.mCallback).onPrepare();
    }

    public void onPrepareFromMediaId(String paramString, Bundle paramBundle)
    {
      ((MediaSessionCompatApi24.Callback)this.mCallback).onPrepareFromMediaId(paramString, paramBundle);
    }

    public void onPrepareFromSearch(String paramString, Bundle paramBundle)
    {
      ((MediaSessionCompatApi24.Callback)this.mCallback).onPrepareFromSearch(paramString, paramBundle);
    }

    public void onPrepareFromUri(Uri paramUri, Bundle paramBundle)
    {
      ((MediaSessionCompatApi24.Callback)this.mCallback).onPrepareFromUri(paramUri, paramBundle);
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v4.media.session.MediaSessionCompatApi24
 * JD-Core Version:    0.6.0
 */