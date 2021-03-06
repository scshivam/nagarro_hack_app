package okhttp3.internal.http;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import okhttp3.internal.Util;

public final class HttpDate
{
  private static final DateFormat[] BROWSER_COMPATIBLE_DATE_FORMATS;
  private static final String[] BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;
  public static final long MAX_DATE = 253402300799999L;
  private static final ThreadLocal<DateFormat> STANDARD_DATE_FORMAT = new ThreadLocal()
  {
    protected DateFormat initialValue()
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
      localSimpleDateFormat.setLenient(false);
      localSimpleDateFormat.setTimeZone(Util.UTC);
      return localSimpleDateFormat;
    }
  };

  static
  {
    BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS = new String[] { "EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z" };
    BROWSER_COMPATIBLE_DATE_FORMATS = new DateFormat[BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length];
  }

  public static String format(Date paramDate)
  {
    return ((DateFormat)STANDARD_DATE_FORMAT.get()).format(paramDate);
  }

  public static Date parse(String paramString)
  {
    Date localDate1;
    if (paramString.length() == 0)
      localDate1 = null;
    ParsePosition localParsePosition;
    do
    {
      return localDate1;
      localParsePosition = new ParsePosition(0);
      localDate1 = ((DateFormat)STANDARD_DATE_FORMAT.get()).parse(paramString, localParsePosition);
    }
    while (localParsePosition.getIndex() == paramString.length());
    String[] arrayOfString = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;
    monitorenter;
    for (int i = 0; ; i++)
      try
      {
        int j = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length;
        if (i >= j)
          break;
        Object localObject2 = BROWSER_COMPATIBLE_DATE_FORMATS[i];
        if (localObject2 == null)
        {
          localObject2 = new SimpleDateFormat(BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS[i], Locale.US);
          ((DateFormat)localObject2).setTimeZone(Util.UTC);
          BROWSER_COMPATIBLE_DATE_FORMATS[i] = localObject2;
        }
        localParsePosition.setIndex(0);
        Date localDate2 = ((DateFormat)localObject2).parse(paramString, localParsePosition);
        if (localParsePosition.getIndex() != 0)
          return localDate2;
      }
      finally
      {
        monitorexit;
      }
    monitorexit;
    return (Date)null;
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     okhttp3.internal.http.HttpDate
 * JD-Core Version:    0.6.0
 */