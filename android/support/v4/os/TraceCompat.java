package android.support.v4.os;

import android.os.Build.VERSION;
import android.os.Trace;

public final class TraceCompat
{
  public static void beginSection(String paramString)
  {
    if (Build.VERSION.SDK_INT >= 18)
      Trace.beginSection(paramString);
  }

  public static void endSection()
  {
    if (Build.VERSION.SDK_INT >= 18)
      Trace.endSection();
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v4.os.TraceCompat
 * JD-Core Version:    0.6.0
 */