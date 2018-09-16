package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

@RequiresApi(21)
class RoundRectDrawable extends Drawable
{
  private ColorStateList mBackground;
  private final RectF mBoundsF;
  private final Rect mBoundsI;
  private boolean mInsetForPadding = false;
  private boolean mInsetForRadius = true;
  private float mPadding;
  private final Paint mPaint;
  private float mRadius;
  private ColorStateList mTint;
  private PorterDuffColorFilter mTintFilter;
  private PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN;

  RoundRectDrawable(ColorStateList paramColorStateList, float paramFloat)
  {
    this.mRadius = paramFloat;
    this.mPaint = new Paint(5);
    setBackground(paramColorStateList);
    this.mBoundsF = new RectF();
    this.mBoundsI = new Rect();
  }

  private PorterDuffColorFilter createTintFilter(ColorStateList paramColorStateList, PorterDuff.Mode paramMode)
  {
    if ((paramColorStateList == null) || (paramMode == null))
      return null;
    return new PorterDuffColorFilter(paramColorStateList.getColorForState(getState(), 0), paramMode);
  }

  private void setBackground(ColorStateList paramColorStateList)
  {
    if (paramColorStateList == null)
      paramColorStateList = ColorStateList.valueOf(0);
    this.mBackground = paramColorStateList;
    this.mPaint.setColor(this.mBackground.getColorForState(getState(), this.mBackground.getDefaultColor()));
  }

  private void updateBounds(Rect paramRect)
  {
    if (paramRect == null)
      paramRect = getBounds();
    this.mBoundsF.set(paramRect.left, paramRect.top, paramRect.right, paramRect.bottom);
    this.mBoundsI.set(paramRect);
    if (this.mInsetForPadding)
    {
      float f1 = RoundRectDrawableWithShadow.calculateVerticalPadding(this.mPadding, this.mRadius, this.mInsetForRadius);
      float f2 = RoundRectDrawableWithShadow.calculateHorizontalPadding(this.mPadding, this.mRadius, this.mInsetForRadius);
      this.mBoundsI.inset((int)Math.ceil(f2), (int)Math.ceil(f1));
      this.mBoundsF.set(this.mBoundsI);
    }
  }

  public void draw(Canvas paramCanvas)
  {
    Paint localPaint = this.mPaint;
    if ((this.mTintFilter != null) && (localPaint.getColorFilter() == null))
      localPaint.setColorFilter(this.mTintFilter);
    for (int i = 1; ; i = 0)
    {
      paramCanvas.drawRoundRect(this.mBoundsF, this.mRadius, this.mRadius, localPaint);
      if (i != 0)
        localPaint.setColorFilter(null);
      return;
    }
  }

  public ColorStateList getColor()
  {
    return this.mBackground;
  }

  public int getOpacity()
  {
    return -3;
  }

  public void getOutline(Outline paramOutline)
  {
    paramOutline.setRoundRect(this.mBoundsI, this.mRadius);
  }

  float getPadding()
  {
    return this.mPadding;
  }

  public float getRadius()
  {
    return this.mRadius;
  }

  public boolean isStateful()
  {
    return ((this.mTint != null) && (this.mTint.isStateful())) || ((this.mBackground != null) && (this.mBackground.isStateful())) || (super.isStateful());
  }

  protected void onBoundsChange(Rect paramRect)
  {
    super.onBoundsChange(paramRect);
    updateBounds(paramRect);
  }

  protected boolean onStateChange(int[] paramArrayOfInt)
  {
    int i = this.mBackground.getColorForState(paramArrayOfInt, this.mBackground.getDefaultColor());
    if (i != this.mPaint.getColor());
    for (int j = 1; ; j = 0)
    {
      if (j != 0)
        this.mPaint.setColor(i);
      if ((this.mTint != null) && (this.mTintMode != null))
      {
        this.mTintFilter = createTintFilter(this.mTint, this.mTintMode);
        j = 1;
      }
      return j;
    }
  }

  public void setAlpha(int paramInt)
  {
    this.mPaint.setAlpha(paramInt);
  }

  public void setColor(@Nullable ColorStateList paramColorStateList)
  {
    setBackground(paramColorStateList);
    invalidateSelf();
  }

  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.mPaint.setColorFilter(paramColorFilter);
  }

  void setPadding(float paramFloat, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramFloat == this.mPadding) && (this.mInsetForPadding == paramBoolean1) && (this.mInsetForRadius == paramBoolean2))
      return;
    this.mPadding = paramFloat;
    this.mInsetForPadding = paramBoolean1;
    this.mInsetForRadius = paramBoolean2;
    updateBounds(null);
    invalidateSelf();
  }

  void setRadius(float paramFloat)
  {
    if (paramFloat == this.mRadius)
      return;
    this.mRadius = paramFloat;
    updateBounds(null);
    invalidateSelf();
  }

  public void setTintList(ColorStateList paramColorStateList)
  {
    this.mTint = paramColorStateList;
    this.mTintFilter = createTintFilter(this.mTint, this.mTintMode);
    invalidateSelf();
  }

  public void setTintMode(PorterDuff.Mode paramMode)
  {
    this.mTintMode = paramMode;
    this.mTintFilter = createTintFilter(this.mTint, this.mTintMode);
    invalidateSelf();
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.RoundRectDrawable
 * JD-Core Version:    0.6.0
 */