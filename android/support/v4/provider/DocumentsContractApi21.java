package android.support.v4.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.util.ArrayList;

@RequiresApi(21)
class DocumentsContractApi21
{
  private static final String TAG = "DocumentFile";

  private static void closeQuietly(AutoCloseable paramAutoCloseable)
  {
    if (paramAutoCloseable != null);
    try
    {
      paramAutoCloseable.close();
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (Exception localException)
    {
    }
  }

  public static Uri createDirectory(Context paramContext, Uri paramUri, String paramString)
  {
    return createFile(paramContext, paramUri, "vnd.android.document/directory", paramString);
  }

  public static Uri createFile(Context paramContext, Uri paramUri, String paramString1, String paramString2)
  {
    try
    {
      Uri localUri = DocumentsContract.createDocument(paramContext.getContentResolver(), paramUri, paramString1, paramString2);
      return localUri;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static Uri[] listFiles(Context paramContext, Uri paramUri)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    Uri localUri = DocumentsContract.buildChildDocumentsUriUsingTree(paramUri, DocumentsContract.getDocumentId(paramUri));
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = null;
    try
    {
      localCursor = localContentResolver.query(localUri, new String[] { "document_id" }, null, null, null);
      while (localCursor.moveToNext())
        localArrayList.add(DocumentsContract.buildDocumentUriUsingTree(paramUri, localCursor.getString(0)));
    }
    catch (Exception localException)
    {
      Log.w("DocumentFile", "Failed query: " + localException);
      while (true)
      {
        return (Uri[])localArrayList.toArray(new Uri[localArrayList.size()]);
        closeQuietly(localCursor);
      }
    }
    finally
    {
      closeQuietly(localCursor);
    }
    throw localObject;
  }

  public static Uri prepareTreeUri(Uri paramUri)
  {
    return DocumentsContract.buildDocumentUriUsingTree(paramUri, DocumentsContract.getTreeDocumentId(paramUri));
  }

  public static Uri renameTo(Context paramContext, Uri paramUri, String paramString)
  {
    try
    {
      Uri localUri = DocumentsContract.renameDocument(paramContext.getContentResolver(), paramUri, paramString);
      return localUri;
    }
    catch (Exception localException)
    {
    }
    return null;
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v4.provider.DocumentsContractApi21
 * JD-Core Version:    0.6.0
 */