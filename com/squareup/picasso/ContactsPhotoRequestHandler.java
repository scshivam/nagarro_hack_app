package com.squareup.picasso;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.ContactsContract.Contacts;
import java.io.IOException;
import java.io.InputStream;

class ContactsPhotoRequestHandler extends RequestHandler
{
  private static final int ID_CONTACT = 3;
  private static final int ID_DISPLAY_PHOTO = 4;
  private static final int ID_LOOKUP = 1;
  private static final int ID_THUMBNAIL = 2;
  private static final UriMatcher matcher = new UriMatcher(-1);
  private final Context context;

  static
  {
    matcher.addURI("com.android.contacts", "contacts/lookup/*/#", 1);
    matcher.addURI("com.android.contacts", "contacts/lookup/*", 1);
    matcher.addURI("com.android.contacts", "contacts/#/photo", 2);
    matcher.addURI("com.android.contacts", "contacts/#", 3);
    matcher.addURI("com.android.contacts", "display_photo/#", 4);
  }

  ContactsPhotoRequestHandler(Context paramContext)
  {
    this.context = paramContext;
  }

  private InputStream getInputStream(Request paramRequest)
    throws IOException
  {
    ContentResolver localContentResolver = this.context.getContentResolver();
    Uri localUri = paramRequest.uri;
    switch (matcher.match(localUri))
    {
    default:
      throw new IllegalStateException("Invalid uri: " + localUri);
    case 1:
      localUri = ContactsContract.Contacts.lookupContact(localContentResolver, localUri);
      if (localUri != null)
        break;
      return null;
    case 3:
      if (Build.VERSION.SDK_INT < 14)
        return ContactsContract.Contacts.openContactPhotoInputStream(localContentResolver, localUri);
      return ContactPhotoStreamIcs.get(localContentResolver, localUri);
    case 2:
    case 4:
    }
    return localContentResolver.openInputStream(localUri);
  }

  public boolean canHandleRequest(Request paramRequest)
  {
    Uri localUri = paramRequest.uri;
    return ("content".equals(localUri.getScheme())) && (ContactsContract.Contacts.CONTENT_URI.getHost().equals(localUri.getHost())) && (matcher.match(paramRequest.uri) != -1);
  }

  public RequestHandler.Result load(Request paramRequest, int paramInt)
    throws IOException
  {
    InputStream localInputStream = getInputStream(paramRequest);
    if (localInputStream != null)
      return new RequestHandler.Result(localInputStream, Picasso.LoadedFrom.DISK);
    return null;
  }

  @TargetApi(14)
  private static class ContactPhotoStreamIcs
  {
    static InputStream get(ContentResolver paramContentResolver, Uri paramUri)
    {
      return ContactsContract.Contacts.openContactPhotoInputStream(paramContentResolver, paramUri, true);
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     com.squareup.picasso.ContactsPhotoRequestHandler
 * JD-Core Version:    0.6.0
 */