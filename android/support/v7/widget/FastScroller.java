package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@VisibleForTesting
class FastScroller extends RecyclerView.ItemDecoration
  implements RecyclerView.OnItemTouchListener
{
  private static final int ANIMATION_STATE_FADING_IN = 1;
  private static final int ANIMATION_STATE_FADING_OUT = 3;
  private static final int ANIMATION_STATE_IN = 2;
  private static final int ANIMATION_STATE_OUT = 0;
  private static final int DRAG_NONE = 0;
  private static final int DRAG_X = 1;
  private static final int DRAG_Y = 2;
  private static final int[] EMPTY_STATE_SET;
  private static final int HIDE_DELAY_AFTER_DRAGGING_MS = 1200;
  private static final int HIDE_DELAY_AFTER_VISIBLE_MS = 1500;
  private static final int HIDE_DURATION_MS = 500;
  private static final int[] PRESSED_STATE_SET = { 16842919 };
  private static final int SCROLLBAR_FULL_OPAQUE = 255;
  private static final int SHOW_DURATION_MS = 500;
  private static final int STATE_DRAGGING = 2;
  private static final int STATE_HIDDEN = 0;
  private static final int STATE_VISIBLE = 1;
  private int mAnimationState = 0;
  private int mDragState = 0;
  private final Runnable mHideRunnable = new Runnable()
  {
    public void run()
    {
      FastScroller.this.hide(500);
    }
  };

  @VisibleForTesting
  float mHorizontalDragX;
  private final int[] mHorizontalRange = new int[2];

  @VisibleForTesting
  int mHorizontalThumbCenterX;
  private final StateListDrawable mHorizontalThumbDrawable;
  private final int mHorizontalThumbHeight;

  @VisibleForTesting
  int mHorizontalThumbWidth;
  private final Drawable mHorizontalTrackDrawable;
  private final int mHorizontalTrackHeight;
  private final int mMargin;
  private boolean mNeedHorizontalScrollbar = false;
  private boolean mNeedVerticalScrollbar = false;
  private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener()
  {
    public void onScrolled(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
    {
      FastScroller.this.updateScrollPosition(paramRecyclerView.computeHorizontalScrollOffset(), paramRecyclerView.computeVerticalScrollOffset());
    }
  };
  private RecyclerView mRecyclerView;
  private int mRecyclerViewHeight = 0;
  private int mRecyclerViewWidth = 0;
  private final int mScrollbarMinimumRange;
  private final ValueAnimator mShowHideAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F });
  private int mState = 0;

  @VisibleForTesting
  float mVerticalDragY;
  private final int[] mVerticalRange = new int[2];

  @VisibleForTesting
  int mVerticalThumbCenterY;
  private final StateListDrawable mVerticalThumbDrawable;

  @VisibleForTesting
  int mVerticalThumbHeight;
  private final int mVerticalThumbWidth;
  private final Drawable mVerticalTrackDrawable;
  private final int mVerticalTrackWidth;

  static
  {
    EMPTY_STATE_SET = new int[0];
  }

  FastScroller(RecyclerView paramRecyclerView, StateListDrawable paramStateListDrawable1, Drawable paramDrawable1, StateListDrawable paramStateListDrawable2, Drawable paramDrawable2, int paramInt1, int paramInt2, int paramInt3)
  {
    this.mVerticalThumbDrawable = paramStateListDrawable1;
    this.mVerticalTrackDrawable = paramDrawable1;
    this.mHorizontalThumbDrawable = paramStateListDrawable2;
    this.mHorizontalTrackDrawable = paramDrawable2;
    this.mVerticalThumbWidth = Math.max(paramInt1, paramStateListDrawable1.getIntrinsicWidth());
    this.mVerticalTrackWidth = Math.max(paramInt1, paramDrawable1.getIntrinsicWidth());
    this.mHorizontalThumbHeight = Math.max(paramInt1, paramStateListDrawable2.getIntrinsicWidth());
    this.mHorizontalTrackHeight = Math.max(paramInt1, paramDrawable2.getIntrinsicWidth());
    this.mScrollbarMinimumRange = paramInt2;
    this.mMargin = paramInt3;
    this.mVerticalThumbDrawable.setAlpha(255);
    this.mVerticalTrackDrawable.setAlpha(255);
    this.mShowHideAnimator.addListener(new AnimatorListener(null));
    this.mShowHideAnimator.addUpdateListener(new AnimatorUpdater(null));
    attachToRecyclerView(paramRecyclerView);
  }

  private void cancelHide()
  {
    this.mRecyclerView.removeCallbacks(this.mHideRunnable);
  }

  private void destroyCallbacks()
  {
    this.mRecyclerView.removeItemDecoration(this);
    this.mRecyclerView.removeOnItemTouchListener(this);
    this.mRecyclerView.removeOnScrollListener(this.mOnScrollListener);
    cancelHide();
  }

  private void drawHorizontalScrollbar(Canvas paramCanvas)
  {
    int i = this.mRecyclerViewHeight - this.mHorizontalThumbHeight;
    int j = this.mHorizontalThumbCenterX - this.mHorizontalThumbWidth / 2;
    this.mHorizontalThumbDrawable.setBounds(0, 0, this.mHorizontalThumbWidth, this.mHorizontalThumbHeight);
    this.mHorizontalTrackDrawable.setBounds(0, 0, this.mRecyclerViewWidth, this.mHorizontalTrackHeight);
    paramCanvas.translate(0.0F, i);
    this.mHorizontalTrackDrawable.draw(paramCanvas);
    paramCanvas.translate(j, 0.0F);
    this.mHorizontalThumbDrawable.draw(paramCanvas);
    paramCanvas.translate(-j, -i);
  }

  private void drawVerticalScrollbar(Canvas paramCanvas)
  {
    int i = this.mRecyclerViewWidth - this.mVerticalThumbWidth;
    int j = this.mVerticalThumbCenterY - this.mVerticalThumbHeight / 2;
    this.mVerticalThumbDrawable.setBounds(0, 0, this.mVerticalThumbWidth, this.mVerticalThumbHeight);
    this.mVerticalTrackDrawable.setBounds(0, 0, this.mVerticalTrackWidth, this.mRecyclerViewHeight);
    if (isLayoutRTL())
    {
      this.mVerticalTrackDrawable.draw(paramCanvas);
      paramCanvas.translate(this.mVerticalThumbWidth, j);
      paramCanvas.scale(-1.0F, 1.0F);
      this.mVerticalThumbDrawable.draw(paramCanvas);
      paramCanvas.scale(1.0F, 1.0F);
      paramCanvas.translate(-this.mVerticalThumbWidth, -j);
      return;
    }
    paramCanvas.translate(i, 0.0F);
    this.mVerticalTrackDrawable.draw(paramCanvas);
    paramCanvas.translate(0.0F, j);
    this.mVerticalThumbDrawable.draw(paramCanvas);
    paramCanvas.translate(-i, -j);
  }

  private int[] getHorizontalRange()
  {
    this.mHorizontalRange[0] = this.mMargin;
    this.mHorizontalRange[1] = (this.mRecyclerViewWidth - this.mMargin);
    return this.mHorizontalRange;
  }

  private int[] getVerticalRange()
  {
    this.mVerticalRange[0] = this.mMargin;
    this.mVerticalRange[1] = (this.mRecyclerViewHeight - this.mMargin);
    return this.mVerticalRange;
  }

  private void horizontalScrollTo(float paramFloat)
  {
    int[] arrayOfInt = getHorizontalRange();
    float f = Math.max(arrayOfInt[0], Math.min(arrayOfInt[1], paramFloat));
    if (Math.abs(this.mHorizontalThumbCenterX - f) < 2.0F)
      return;
    int i = scrollTo(this.mHorizontalDragX, f, arrayOfInt, this.mRecyclerView.computeHorizontalScrollRange(), this.mRecyclerView.computeHorizontalScrollOffset(), this.mRecyclerViewWidth);
    if (i != 0)
      this.mRecyclerView.scrollBy(i, 0);
    this.mHorizontalDragX = f;
  }

  private boolean isLayoutRTL()
  {
    return ViewCompat.getLayoutDirection(this.mRecyclerView) == 1;
  }

  private void requestRedraw()
  {
    this.mRecyclerView.invalidate();
  }

  private void resetHideDelay(int paramInt)
  {
    cancelHide();
    this.mRecyclerView.postDelayed(this.mHideRunnable, paramInt);
  }

  private int scrollTo(float paramFloat1, float paramFloat2, int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramArrayOfInt[1] - paramArrayOfInt[0];
    int k;
    if (i == 0)
      k = 0;
    int j;
    int m;
    do
    {
      return k;
      float f = (paramFloat2 - paramFloat1) / i;
      j = paramInt1 - paramInt3;
      k = (int)(f * j);
      m = paramInt2 + k;
    }
    while ((m < j) && (m >= 0));
    return 0;
  }

  private void setState(int paramInt)
  {
    if ((paramInt == 2) && (this.mState != 2))
    {
      this.mVerticalThumbDrawable.setState(PRESSED_STATE_SET);
      cancelHide();
    }
    if (paramInt == 0)
    {
      requestRedraw();
      if ((this.mState != 2) || (paramInt == 2))
        break label80;
      this.mVerticalThumbDrawable.setState(EMPTY_STATE_SET);
      resetHideDelay(1200);
    }
    while (true)
    {
      this.mState = paramInt;
      return;
      show();
      break;
      label80: if (paramInt != 1)
        continue;
      resetHideDelay(1500);
    }
  }

  private void setupCallbacks()
  {
    this.mRecyclerView.addItemDecoration(this);
    this.mRecyclerView.addOnItemTouchListener(this);
    this.mRecyclerView.addOnScrollListener(this.mOnScrollListener);
  }

  private void verticalScrollTo(float paramFloat)
  {
    int[] arrayOfInt = getVerticalRange();
    float f = Math.max(arrayOfInt[0], Math.min(arrayOfInt[1], paramFloat));
    if (Math.abs(this.mVerticalThumbCenterY - f) < 2.0F)
      return;
    int i = scrollTo(this.mVerticalDragY, f, arrayOfInt, this.mRecyclerView.computeVerticalScrollRange(), this.mRecyclerView.computeVerticalScrollOffset(), this.mRecyclerViewHeight);
    if (i != 0)
      this.mRecyclerView.scrollBy(0, i);
    this.mVerticalDragY = f;
  }

  public void attachToRecyclerView(@Nullable RecyclerView paramRecyclerView)
  {
    if (this.mRecyclerView == paramRecyclerView);
    do
    {
      return;
      if (this.mRecyclerView != null)
        destroyCallbacks();
      this.mRecyclerView = paramRecyclerView;
    }
    while (this.mRecyclerView == null);
    setupCallbacks();
  }

  @VisibleForTesting
  Drawable getHorizontalThumbDrawable()
  {
    return this.mHorizontalThumbDrawable;
  }

  @VisibleForTesting
  Drawable getHorizontalTrackDrawable()
  {
    return this.mHorizontalTrackDrawable;
  }

  @VisibleForTesting
  Drawable getVerticalThumbDrawable()
  {
    return this.mVerticalThumbDrawable;
  }

  @VisibleForTesting
  Drawable getVerticalTrackDrawable()
  {
    return this.mVerticalTrackDrawable;
  }

  public void hide()
  {
    hide(0);
  }

  @VisibleForTesting
  void hide(int paramInt)
  {
    switch (this.mAnimationState)
    {
    default:
      return;
    case 1:
      this.mShowHideAnimator.cancel();
    case 2:
    }
    this.mAnimationState = 3;
    ValueAnimator localValueAnimator = this.mShowHideAnimator;
    float[] arrayOfFloat = new float[2];
    arrayOfFloat[0] = ((Float)this.mShowHideAnimator.getAnimatedValue()).floatValue();
    arrayOfFloat[1] = 0.0F;
    localValueAnimator.setFloatValues(arrayOfFloat);
    this.mShowHideAnimator.setDuration(paramInt);
    this.mShowHideAnimator.start();
  }

  public boolean isDragging()
  {
    return this.mState == 2;
  }

  @VisibleForTesting
  boolean isHidden()
  {
    return this.mState == 0;
  }

  @VisibleForTesting
  boolean isPointInsideHorizontalThumb(float paramFloat1, float paramFloat2)
  {
    return (paramFloat2 >= this.mRecyclerViewHeight - this.mHorizontalThumbHeight) && (paramFloat1 >= this.mHorizontalThumbCenterX - this.mHorizontalThumbWidth / 2) && (paramFloat1 <= this.mHorizontalThumbCenterX + this.mHorizontalThumbWidth / 2);
  }

  @VisibleForTesting
  boolean isPointInsideVerticalThumb(float paramFloat1, float paramFloat2)
  {
    if (isLayoutRTL())
    {
      if (paramFloat1 > this.mVerticalThumbWidth / 2);
    }
    else
      do
      {
        if ((paramFloat2 < this.mVerticalThumbCenterY - this.mVerticalThumbHeight / 2) || (paramFloat2 > this.mVerticalThumbCenterY + this.mVerticalThumbHeight / 2))
          break;
        return true;
      }
      while (paramFloat1 >= this.mRecyclerViewWidth - this.mVerticalThumbWidth);
    return false;
  }

  @VisibleForTesting
  boolean isVisible()
  {
    return this.mState == 1;
  }

  public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    if ((this.mRecyclerViewWidth != this.mRecyclerView.getWidth()) || (this.mRecyclerViewHeight != this.mRecyclerView.getHeight()))
    {
      this.mRecyclerViewWidth = this.mRecyclerView.getWidth();
      this.mRecyclerViewHeight = this.mRecyclerView.getHeight();
      setState(0);
    }
    do
    {
      do
        return;
      while (this.mAnimationState == 0);
      if (!this.mNeedVerticalScrollbar)
        continue;
      drawVerticalScrollbar(paramCanvas);
    }
    while (!this.mNeedHorizontalScrollbar);
    drawHorizontalScrollbar(paramCanvas);
  }

  public boolean onInterceptTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent)
  {
    if (this.mState == 1)
    {
      boolean bool1 = isPointInsideVerticalThumb(paramMotionEvent.getX(), paramMotionEvent.getY());
      boolean bool2 = isPointInsideHorizontalThumb(paramMotionEvent.getX(), paramMotionEvent.getY());
      if ((paramMotionEvent.getAction() == 0) && ((bool1) || (bool2)))
      {
        if (bool2)
        {
          this.mDragState = 1;
          this.mHorizontalDragX = (int)paramMotionEvent.getX();
        }
        while (true)
        {
          setState(2);
          return true;
          if (!bool1)
            continue;
          this.mDragState = 2;
          this.mVerticalDragY = (int)paramMotionEvent.getY();
        }
      }
      return false;
    }
    return this.mState == 2;
  }

  public void onRequestDisallowInterceptTouchEvent(boolean paramBoolean)
  {
  }

  public void onTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent)
  {
    if (this.mState == 0);
    do
    {
      do
      {
        while (true)
        {
          return;
          if (paramMotionEvent.getAction() != 0)
            break;
          boolean bool1 = isPointInsideVerticalThumb(paramMotionEvent.getX(), paramMotionEvent.getY());
          boolean bool2 = isPointInsideHorizontalThumb(paramMotionEvent.getX(), paramMotionEvent.getY());
          if ((!bool1) && (!bool2))
            continue;
          if (bool2)
          {
            this.mDragState = 1;
            this.mHorizontalDragX = (int)paramMotionEvent.getX();
          }
          while (true)
          {
            setState(2);
            return;
            if (!bool1)
              continue;
            this.mDragState = 2;
            this.mVerticalDragY = (int)paramMotionEvent.getY();
          }
        }
        if ((paramMotionEvent.getAction() != 1) || (this.mState != 2))
          continue;
        this.mVerticalDragY = 0.0F;
        this.mHorizontalDragX = 0.0F;
        setState(1);
        this.mDragState = 0;
        return;
      }
      while ((paramMotionEvent.getAction() != 2) || (this.mState != 2));
      show();
      if (this.mDragState != 1)
        continue;
      horizontalScrollTo(paramMotionEvent.getX());
    }
    while (this.mDragState != 2);
    verticalScrollTo(paramMotionEvent.getY());
  }

  public void show()
  {
    switch (this.mAnimationState)
    {
    case 1:
    case 2:
    default:
      return;
    case 3:
      this.mShowHideAnimator.cancel();
    case 0:
    }
    this.mAnimationState = 1;
    ValueAnimator localValueAnimator = this.mShowHideAnimator;
    float[] arrayOfFloat = new float[2];
    arrayOfFloat[0] = ((Float)this.mShowHideAnimator.getAnimatedValue()).floatValue();
    arrayOfFloat[1] = 1.0F;
    localValueAnimator.setFloatValues(arrayOfFloat);
    this.mShowHideAnimator.setDuration(500L);
    this.mShowHideAnimator.setStartDelay(0L);
    this.mShowHideAnimator.start();
  }

  void updateScrollPosition(int paramInt1, int paramInt2)
  {
    int i = this.mRecyclerView.computeVerticalScrollRange();
    int j = this.mRecyclerViewHeight;
    boolean bool1;
    int k;
    int m;
    boolean bool2;
    if ((i - j > 0) && (this.mRecyclerViewHeight >= this.mScrollbarMinimumRange))
    {
      bool1 = true;
      this.mNeedVerticalScrollbar = bool1;
      k = this.mRecyclerView.computeHorizontalScrollRange();
      m = this.mRecyclerViewWidth;
      if ((k - m <= 0) || (this.mRecyclerViewWidth < this.mScrollbarMinimumRange))
        break label117;
      bool2 = true;
      label78: this.mNeedHorizontalScrollbar = bool2;
      if ((this.mNeedVerticalScrollbar) || (this.mNeedHorizontalScrollbar))
        break label123;
      if (this.mState != 0)
        setState(0);
    }
    label117: label123: 
    do
    {
      return;
      bool1 = false;
      break;
      bool2 = false;
      break label78;
      if (this.mNeedVerticalScrollbar)
      {
        this.mVerticalThumbCenterY = (int)((paramInt2 + j / 2.0F) * j / i);
        this.mVerticalThumbHeight = Math.min(j, j * j / i);
      }
      if (!this.mNeedHorizontalScrollbar)
        continue;
      this.mHorizontalThumbCenterX = (int)((paramInt1 + m / 2.0F) * m / k);
      this.mHorizontalThumbWidth = Math.min(m, m * m / k);
    }
    while ((this.mState != 0) && (this.mState != 1));
    setState(1);
  }

  @Retention(RetentionPolicy.SOURCE)
  private static @interface AnimationState
  {
  }

  private class AnimatorListener extends AnimatorListenerAdapter
  {
    private boolean mCanceled = false;

    private AnimatorListener()
    {
    }

    public void onAnimationCancel(Animator paramAnimator)
    {
      this.mCanceled = true;
    }

    public void onAnimationEnd(Animator paramAnimator)
    {
      if (this.mCanceled)
      {
        this.mCanceled = false;
        return;
      }
      if (((Float)FastScroller.this.mShowHideAnimator.getAnimatedValue()).floatValue() == 0.0F)
      {
        FastScroller.access$302(FastScroller.this, 0);
        FastScroller.this.setState(0);
        return;
      }
      FastScroller.access$302(FastScroller.this, 2);
      FastScroller.this.requestRedraw();
    }
  }

  private class AnimatorUpdater
    implements ValueAnimator.AnimatorUpdateListener
  {
    private AnimatorUpdater()
    {
    }

    public void onAnimationUpdate(ValueAnimator paramValueAnimator)
    {
      int i = (int)(255.0F * ((Float)paramValueAnimator.getAnimatedValue()).floatValue());
      FastScroller.this.mVerticalThumbDrawable.setAlpha(i);
      FastScroller.this.mVerticalTrackDrawable.setAlpha(i);
      FastScroller.this.requestRedraw();
    }
  }

  @Retention(RetentionPolicy.SOURCE)
  private static @interface DragState
  {
  }

  @Retention(RetentionPolicy.SOURCE)
  private static @interface State
  {
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.FastScroller
 * JD-Core Version:    0.6.0
 */