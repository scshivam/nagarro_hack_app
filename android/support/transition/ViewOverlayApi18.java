package android.support.transition;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewOverlay;

@RequiresApi(18)
class ViewOverlayApi18
  implements ViewOverlayImpl
{
  private final ViewOverlay mViewOverlay;

  ViewOverlayApi18(@NonNull View paramView)
  {
    this.mViewOverlay = paramView.getOverlay();
  }

  public void add(@NonNull Drawable paramDrawable)
  {
    this.mViewOverlay.add(paramDrawable);
  }

  public void clear()
  {
    this.mViewOverlay.clear();
  }

  public void remove(@NonNull Drawable paramDrawable)
  {
    this.mViewOverlay.remove(paramDrawable);
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.transition.ViewOverlayApi18
 * JD-Core Version:    0.6.0
 */