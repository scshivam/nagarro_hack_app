package android.support.v7.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.text.AllCapsTransformationMethod;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Property;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import java.util.List;

@RequiresApi(14)
public class SwitchCompat extends CompoundButton
{
  private static final String ACCESSIBILITY_EVENT_CLASS_NAME = "android.widget.Switch";
  private static final int[] CHECKED_STATE_SET;
  private static final int MONOSPACE = 3;
  private static final int SANS = 1;
  private static final int SERIF = 2;
  private static final int THUMB_ANIMATION_DURATION = 250;
  private static final Property<SwitchCompat, Float> THUMB_POS = new Property("thumbPos")
  {
    public Float get(SwitchCompat paramSwitchCompat)
    {
      return Float.valueOf(paramSwitchCompat.mThumbPosition);
    }

    public void set(SwitchCompat paramSwitchCompat, Float paramFloat)
    {
      paramSwitchCompat.setThumbPosition(paramFloat.floatValue());
    }
  };
  private static final int TOUCH_MODE_DOWN = 1;
  private static final int TOUCH_MODE_DRAGGING = 2;
  private static final int TOUCH_MODE_IDLE;
  private boolean mHasThumbTint = false;
  private boolean mHasThumbTintMode = false;
  private boolean mHasTrackTint = false;
  private boolean mHasTrackTintMode = false;
  private int mMinFlingVelocity;
  private Layout mOffLayout;
  private Layout mOnLayout;
  ObjectAnimator mPositionAnimator;
  private boolean mShowText;
  private boolean mSplitTrack;
  private int mSwitchBottom;
  private int mSwitchHeight;
  private int mSwitchLeft;
  private int mSwitchMinWidth;
  private int mSwitchPadding;
  private int mSwitchRight;
  private int mSwitchTop;
  private TransformationMethod mSwitchTransformationMethod;
  private int mSwitchWidth;
  private final Rect mTempRect = new Rect();
  private ColorStateList mTextColors;
  private CharSequence mTextOff;
  private CharSequence mTextOn;
  private final TextPaint mTextPaint = new TextPaint(1);
  private Drawable mThumbDrawable;
  private float mThumbPosition;
  private int mThumbTextPadding;
  private ColorStateList mThumbTintList = null;
  private PorterDuff.Mode mThumbTintMode = null;
  private int mThumbWidth;
  private int mTouchMode;
  private int mTouchSlop;
  private float mTouchX;
  private float mTouchY;
  private Drawable mTrackDrawable;
  private ColorStateList mTrackTintList = null;
  private PorterDuff.Mode mTrackTintMode = null;
  private VelocityTracker mVelocityTracker = VelocityTracker.obtain();

  static
  {
    CHECKED_STATE_SET = new int[] { 16842912 };
  }

  public SwitchCompat(Context paramContext)
  {
    this(paramContext, null);
  }

  public SwitchCompat(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.switchStyle);
  }

  public SwitchCompat(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Resources localResources = getResources();
    this.mTextPaint.density = localResources.getDisplayMetrics().density;
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.SwitchCompat, paramInt, 0);
    this.mThumbDrawable = localTintTypedArray.getDrawable(R.styleable.SwitchCompat_android_thumb);
    if (this.mThumbDrawable != null)
      this.mThumbDrawable.setCallback(this);
    this.mTrackDrawable = localTintTypedArray.getDrawable(R.styleable.SwitchCompat_track);
    if (this.mTrackDrawable != null)
      this.mTrackDrawable.setCallback(this);
    this.mTextOn = localTintTypedArray.getText(R.styleable.SwitchCompat_android_textOn);
    this.mTextOff = localTintTypedArray.getText(R.styleable.SwitchCompat_android_textOff);
    this.mShowText = localTintTypedArray.getBoolean(R.styleable.SwitchCompat_showText, true);
    this.mThumbTextPadding = localTintTypedArray.getDimensionPixelSize(R.styleable.SwitchCompat_thumbTextPadding, 0);
    this.mSwitchMinWidth = localTintTypedArray.getDimensionPixelSize(R.styleable.SwitchCompat_switchMinWidth, 0);
    this.mSwitchPadding = localTintTypedArray.getDimensionPixelSize(R.styleable.SwitchCompat_switchPadding, 0);
    this.mSplitTrack = localTintTypedArray.getBoolean(R.styleable.SwitchCompat_splitTrack, false);
    ColorStateList localColorStateList1 = localTintTypedArray.getColorStateList(R.styleable.SwitchCompat_thumbTint);
    if (localColorStateList1 != null)
    {
      this.mThumbTintList = localColorStateList1;
      this.mHasThumbTint = true;
    }
    PorterDuff.Mode localMode1 = DrawableUtils.parseTintMode(localTintTypedArray.getInt(R.styleable.SwitchCompat_thumbTintMode, -1), null);
    if (this.mThumbTintMode != localMode1)
    {
      this.mThumbTintMode = localMode1;
      this.mHasThumbTintMode = true;
    }
    if ((this.mHasThumbTint) || (this.mHasThumbTintMode))
      applyThumbTint();
    ColorStateList localColorStateList2 = localTintTypedArray.getColorStateList(R.styleable.SwitchCompat_trackTint);
    if (localColorStateList2 != null)
    {
      this.mTrackTintList = localColorStateList2;
      this.mHasTrackTint = true;
    }
    PorterDuff.Mode localMode2 = DrawableUtils.parseTintMode(localTintTypedArray.getInt(R.styleable.SwitchCompat_trackTintMode, -1), null);
    if (this.mTrackTintMode != localMode2)
    {
      this.mTrackTintMode = localMode2;
      this.mHasTrackTintMode = true;
    }
    if ((this.mHasTrackTint) || (this.mHasTrackTintMode))
      applyTrackTint();
    int i = localTintTypedArray.getResourceId(R.styleable.SwitchCompat_switchTextAppearance, 0);
    if (i != 0)
      setSwitchTextAppearance(paramContext, i);
    localTintTypedArray.recycle();
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(paramContext);
    this.mTouchSlop = localViewConfiguration.getScaledTouchSlop();
    this.mMinFlingVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
    refreshDrawableState();
    setChecked(isChecked());
  }

  private void animateThumbToCheckedState(boolean paramBoolean)
  {
    float f;
    if (paramBoolean)
      f = 1.0F;
    while (true)
    {
      this.mPositionAnimator = ObjectAnimator.ofFloat(this, THUMB_POS, new float[] { f });
      this.mPositionAnimator.setDuration(250L);
      if (Build.VERSION.SDK_INT >= 18)
        this.mPositionAnimator.setAutoCancel(true);
      this.mPositionAnimator.start();
      return;
      f = 0.0F;
    }
  }

  private void applyThumbTint()
  {
    if ((this.mThumbDrawable != null) && ((this.mHasThumbTint) || (this.mHasThumbTintMode)))
    {
      this.mThumbDrawable = this.mThumbDrawable.mutate();
      if (this.mHasThumbTint)
        DrawableCompat.setTintList(this.mThumbDrawable, this.mThumbTintList);
      if (this.mHasThumbTintMode)
        DrawableCompat.setTintMode(this.mThumbDrawable, this.mThumbTintMode);
      if (this.mThumbDrawable.isStateful())
        this.mThumbDrawable.setState(getDrawableState());
    }
  }

  private void applyTrackTint()
  {
    if ((this.mTrackDrawable != null) && ((this.mHasTrackTint) || (this.mHasTrackTintMode)))
    {
      this.mTrackDrawable = this.mTrackDrawable.mutate();
      if (this.mHasTrackTint)
        DrawableCompat.setTintList(this.mTrackDrawable, this.mTrackTintList);
      if (this.mHasTrackTintMode)
        DrawableCompat.setTintMode(this.mTrackDrawable, this.mTrackTintMode);
      if (this.mTrackDrawable.isStateful())
        this.mTrackDrawable.setState(getDrawableState());
    }
  }

  private void cancelPositionAnimator()
  {
    if (this.mPositionAnimator != null)
      this.mPositionAnimator.cancel();
  }

  private void cancelSuperTouch(MotionEvent paramMotionEvent)
  {
    MotionEvent localMotionEvent = MotionEvent.obtain(paramMotionEvent);
    localMotionEvent.setAction(3);
    super.onTouchEvent(localMotionEvent);
    localMotionEvent.recycle();
  }

  private static float constrain(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 < paramFloat2)
      return paramFloat2;
    if (paramFloat1 > paramFloat3)
      return paramFloat3;
    return paramFloat1;
  }

  private boolean getTargetCheckedState()
  {
    return this.mThumbPosition > 0.5F;
  }

  private int getThumbOffset()
  {
    float f;
    if (ViewUtils.isLayoutRtl(this))
      f = 1.0F - this.mThumbPosition;
    while (true)
    {
      return (int)(0.5F + f * getThumbScrollRange());
      f = this.mThumbPosition;
    }
  }

  private int getThumbScrollRange()
  {
    if (this.mTrackDrawable != null)
    {
      Rect localRect1 = this.mTempRect;
      this.mTrackDrawable.getPadding(localRect1);
      if (this.mThumbDrawable != null);
      for (Rect localRect2 = DrawableUtils.getOpticalBounds(this.mThumbDrawable); ; localRect2 = DrawableUtils.INSETS_NONE)
        return this.mSwitchWidth - this.mThumbWidth - localRect1.left - localRect1.right - localRect2.left - localRect2.right;
    }
    return 0;
  }

  private boolean hitThumb(float paramFloat1, float paramFloat2)
  {
    if (this.mThumbDrawable == null);
    int j;
    int k;
    int m;
    int n;
    do
    {
      return false;
      int i = getThumbOffset();
      this.mThumbDrawable.getPadding(this.mTempRect);
      j = this.mSwitchTop - this.mTouchSlop;
      k = i + this.mSwitchLeft - this.mTouchSlop;
      m = k + this.mThumbWidth + this.mTempRect.left + this.mTempRect.right + this.mTouchSlop;
      n = this.mSwitchBottom + this.mTouchSlop;
    }
    while ((paramFloat1 <= k) || (paramFloat1 >= m) || (paramFloat2 <= j) || (paramFloat2 >= n));
    return true;
  }

  private Layout makeLayout(CharSequence paramCharSequence)
  {
    CharSequence localCharSequence;
    TextPaint localTextPaint;
    if (this.mSwitchTransformationMethod != null)
    {
      localCharSequence = this.mSwitchTransformationMethod.getTransformation(paramCharSequence, this);
      localTextPaint = this.mTextPaint;
      if (localCharSequence == null)
        break label66;
    }
    label66: for (int i = (int)Math.ceil(Layout.getDesiredWidth(localCharSequence, this.mTextPaint)); ; i = 0)
    {
      return new StaticLayout(localCharSequence, localTextPaint, i, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
      localCharSequence = paramCharSequence;
      break;
    }
  }

  private void setSwitchTypefaceByIndex(int paramInt1, int paramInt2)
  {
    Typeface localTypeface = null;
    switch (paramInt1)
    {
    default:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      setSwitchTypeface(localTypeface, paramInt2);
      return;
      localTypeface = Typeface.SANS_SERIF;
      continue;
      localTypeface = Typeface.SERIF;
      continue;
      localTypeface = Typeface.MONOSPACE;
    }
  }

  private void stopDrag(MotionEvent paramMotionEvent)
  {
    this.mTouchMode = 0;
    int i;
    boolean bool1;
    float f;
    boolean bool2;
    if ((paramMotionEvent.getAction() == 1) && (isEnabled()))
    {
      i = 1;
      bool1 = isChecked();
      if (i == 0)
        break label143;
      this.mVelocityTracker.computeCurrentVelocity(1000);
      f = this.mVelocityTracker.getXVelocity();
      if (Math.abs(f) <= this.mMinFlingVelocity)
        break label134;
      if (!ViewUtils.isLayoutRtl(this))
        break label115;
      if (f >= 0.0F)
        break label109;
      bool2 = true;
    }
    while (true)
    {
      if (bool2 != bool1)
        playSoundEffect(0);
      setChecked(bool2);
      cancelSuperTouch(paramMotionEvent);
      return;
      i = 0;
      break;
      label109: bool2 = false;
      continue;
      label115: if (f > 0.0F)
      {
        bool2 = true;
        continue;
      }
      bool2 = false;
      continue;
      label134: bool2 = getTargetCheckedState();
      continue;
      label143: bool2 = bool1;
    }
  }

  public void draw(Canvas paramCanvas)
  {
    Rect localRect1 = this.mTempRect;
    int i = this.mSwitchLeft;
    int j = this.mSwitchTop;
    int k = this.mSwitchRight;
    int m = this.mSwitchBottom;
    int n = i + getThumbOffset();
    if (this.mThumbDrawable != null);
    for (Rect localRect2 = DrawableUtils.getOpticalBounds(this.mThumbDrawable); ; localRect2 = DrawableUtils.INSETS_NONE)
    {
      if (this.mTrackDrawable != null)
      {
        this.mTrackDrawable.getPadding(localRect1);
        n += localRect1.left;
        int i3 = i;
        int i4 = j;
        int i5 = k;
        int i6 = m;
        if (localRect2 != null)
        {
          if (localRect2.left > localRect1.left)
            i3 += localRect2.left - localRect1.left;
          if (localRect2.top > localRect1.top)
            i4 += localRect2.top - localRect1.top;
          if (localRect2.right > localRect1.right)
            i5 -= localRect2.right - localRect1.right;
          if (localRect2.bottom > localRect1.bottom)
            i6 -= localRect2.bottom - localRect1.bottom;
        }
        this.mTrackDrawable.setBounds(i3, i4, i5, i6);
      }
      if (this.mThumbDrawable != null)
      {
        this.mThumbDrawable.getPadding(localRect1);
        int i1 = n - localRect1.left;
        int i2 = n + this.mThumbWidth + localRect1.right;
        this.mThumbDrawable.setBounds(i1, j, i2, m);
        Drawable localDrawable = getBackground();
        if (localDrawable != null)
          DrawableCompat.setHotspotBounds(localDrawable, i1, j, i2, m);
      }
      super.draw(paramCanvas);
      return;
    }
  }

  public void drawableHotspotChanged(float paramFloat1, float paramFloat2)
  {
    if (Build.VERSION.SDK_INT >= 21)
      super.drawableHotspotChanged(paramFloat1, paramFloat2);
    if (this.mThumbDrawable != null)
      DrawableCompat.setHotspot(this.mThumbDrawable, paramFloat1, paramFloat2);
    if (this.mTrackDrawable != null)
      DrawableCompat.setHotspot(this.mTrackDrawable, paramFloat1, paramFloat2);
  }

  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    int[] arrayOfInt = getDrawableState();
    Drawable localDrawable1 = this.mThumbDrawable;
    boolean bool1 = false;
    if (localDrawable1 != null)
    {
      boolean bool2 = localDrawable1.isStateful();
      bool1 = false;
      if (bool2)
        bool1 = false | localDrawable1.setState(arrayOfInt);
    }
    Drawable localDrawable2 = this.mTrackDrawable;
    if ((localDrawable2 != null) && (localDrawable2.isStateful()))
      bool1 |= localDrawable2.setState(arrayOfInt);
    if (bool1)
      invalidate();
  }

  public int getCompoundPaddingLeft()
  {
    int i;
    if (!ViewUtils.isLayoutRtl(this))
      i = super.getCompoundPaddingLeft();
    do
    {
      return i;
      i = super.getCompoundPaddingLeft() + this.mSwitchWidth;
    }
    while (TextUtils.isEmpty(getText()));
    return i + this.mSwitchPadding;
  }

  public int getCompoundPaddingRight()
  {
    int i;
    if (ViewUtils.isLayoutRtl(this))
      i = super.getCompoundPaddingRight();
    do
    {
      return i;
      i = super.getCompoundPaddingRight() + this.mSwitchWidth;
    }
    while (TextUtils.isEmpty(getText()));
    return i + this.mSwitchPadding;
  }

  public boolean getShowText()
  {
    return this.mShowText;
  }

  public boolean getSplitTrack()
  {
    return this.mSplitTrack;
  }

  public int getSwitchMinWidth()
  {
    return this.mSwitchMinWidth;
  }

  public int getSwitchPadding()
  {
    return this.mSwitchPadding;
  }

  public CharSequence getTextOff()
  {
    return this.mTextOff;
  }

  public CharSequence getTextOn()
  {
    return this.mTextOn;
  }

  public Drawable getThumbDrawable()
  {
    return this.mThumbDrawable;
  }

  public int getThumbTextPadding()
  {
    return this.mThumbTextPadding;
  }

  @Nullable
  public ColorStateList getThumbTintList()
  {
    return this.mThumbTintList;
  }

  @Nullable
  public PorterDuff.Mode getThumbTintMode()
  {
    return this.mThumbTintMode;
  }

  public Drawable getTrackDrawable()
  {
    return this.mTrackDrawable;
  }

  @Nullable
  public ColorStateList getTrackTintList()
  {
    return this.mTrackTintList;
  }

  @Nullable
  public PorterDuff.Mode getTrackTintMode()
  {
    return this.mTrackTintMode;
  }

  public void jumpDrawablesToCurrentState()
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      super.jumpDrawablesToCurrentState();
      if (this.mThumbDrawable != null)
        this.mThumbDrawable.jumpToCurrentState();
      if (this.mTrackDrawable != null)
        this.mTrackDrawable.jumpToCurrentState();
      if ((this.mPositionAnimator != null) && (this.mPositionAnimator.isStarted()))
      {
        this.mPositionAnimator.end();
        this.mPositionAnimator = null;
      }
    }
  }

  protected int[] onCreateDrawableState(int paramInt)
  {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 1);
    if (isChecked())
      mergeDrawableStates(arrayOfInt, CHECKED_STATE_SET);
    return arrayOfInt;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    Rect localRect1 = this.mTempRect;
    Drawable localDrawable1 = this.mTrackDrawable;
    int k;
    int m;
    label144: int n;
    Layout localLayout;
    label174: Rect localRect2;
    if (localDrawable1 != null)
    {
      localDrawable1.getPadding(localRect1);
      int i = this.mSwitchTop;
      int j = this.mSwitchBottom;
      k = i + localRect1.top;
      m = j - localRect1.bottom;
      Drawable localDrawable2 = this.mThumbDrawable;
      if (localDrawable1 != null)
      {
        if ((!this.mSplitTrack) || (localDrawable2 == null))
          break label304;
        Rect localRect3 = DrawableUtils.getOpticalBounds(localDrawable2);
        localDrawable2.copyBounds(localRect1);
        localRect1.left += localRect3.left;
        localRect1.right -= localRect3.right;
        int i4 = paramCanvas.save();
        paramCanvas.clipRect(localRect1, Region.Op.DIFFERENCE);
        localDrawable1.draw(paramCanvas);
        paramCanvas.restoreToCount(i4);
      }
      n = paramCanvas.save();
      if (localDrawable2 != null)
        localDrawable2.draw(paramCanvas);
      if (!getTargetCheckedState())
        break label312;
      localLayout = this.mOnLayout;
      if (localLayout != null)
      {
        int[] arrayOfInt = getDrawableState();
        if (this.mTextColors != null)
          this.mTextPaint.setColor(this.mTextColors.getColorForState(arrayOfInt, 0));
        this.mTextPaint.drawableState = arrayOfInt;
        if (localDrawable2 == null)
          break label321;
        localRect2 = localDrawable2.getBounds();
      }
    }
    label304: label312: label321: for (int i1 = localRect2.left + localRect2.right; ; i1 = getWidth())
    {
      int i2 = i1 / 2 - localLayout.getWidth() / 2;
      int i3 = (k + m) / 2 - localLayout.getHeight() / 2;
      paramCanvas.translate(i2, i3);
      localLayout.draw(paramCanvas);
      paramCanvas.restoreToCount(n);
      return;
      localRect1.setEmpty();
      break;
      localDrawable1.draw(paramCanvas);
      break label144;
      localLayout = this.mOffLayout;
      break label174;
    }
  }

  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName("android.widget.Switch");
  }

  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
      paramAccessibilityNodeInfo.setClassName("android.widget.Switch");
      if (!isChecked())
        break label56;
    }
    CharSequence localCharSequence2;
    label56: for (CharSequence localCharSequence1 = this.mTextOn; ; localCharSequence1 = this.mTextOff)
    {
      if (!TextUtils.isEmpty(localCharSequence1))
      {
        localCharSequence2 = paramAccessibilityNodeInfo.getText();
        if (!TextUtils.isEmpty(localCharSequence2))
          break;
        paramAccessibilityNodeInfo.setText(localCharSequence1);
      }
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(localCharSequence2).append(' ').append(localCharSequence1);
    paramAccessibilityNodeInfo.setText(localStringBuilder);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    Drawable localDrawable = this.mThumbDrawable;
    int i = 0;
    int j = 0;
    Rect localRect1;
    int m;
    int k;
    label125: int i1;
    int n;
    if (localDrawable != null)
    {
      localRect1 = this.mTempRect;
      if (this.mTrackDrawable != null)
      {
        this.mTrackDrawable.getPadding(localRect1);
        Rect localRect2 = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
        i = Math.max(0, localRect2.left - localRect1.left);
        j = Math.max(0, localRect2.right - localRect1.right);
      }
    }
    else
    {
      if (!ViewUtils.isLayoutRtl(this))
        break label208;
      m = i + getPaddingLeft();
      k = m + this.mSwitchWidth - i - j;
      switch (0x70 & getGravity())
      {
      default:
        i1 = getPaddingTop();
        n = i1 + this.mSwitchHeight;
      case 16:
      case 80:
      }
    }
    while (true)
    {
      this.mSwitchLeft = m;
      this.mSwitchTop = i1;
      this.mSwitchBottom = n;
      this.mSwitchRight = k;
      return;
      localRect1.setEmpty();
      break;
      label208: k = getWidth() - getPaddingRight() - j;
      m = j + (i + (k - this.mSwitchWidth));
      break label125;
      i1 = (getPaddingTop() + getHeight() - getPaddingBottom()) / 2 - this.mSwitchHeight / 2;
      n = i1 + this.mSwitchHeight;
      continue;
      n = getHeight() - getPaddingBottom();
      i1 = n - this.mSwitchHeight;
    }
  }

  public void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mShowText)
    {
      if (this.mOnLayout == null)
        this.mOnLayout = makeLayout(this.mTextOn);
      if (this.mOffLayout == null)
        this.mOffLayout = makeLayout(this.mTextOff);
    }
    Rect localRect1 = this.mTempRect;
    int j;
    int i;
    int k;
    if (this.mThumbDrawable != null)
    {
      this.mThumbDrawable.getPadding(localRect1);
      j = this.mThumbDrawable.getIntrinsicWidth() - localRect1.left - localRect1.right;
      i = this.mThumbDrawable.getIntrinsicHeight();
      if (!this.mShowText)
        break label292;
      k = Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth()) + 2 * this.mThumbTextPadding;
      label127: this.mThumbWidth = Math.max(k, j);
      if (this.mTrackDrawable == null)
        break label298;
      this.mTrackDrawable.getPadding(localRect1);
    }
    for (int m = this.mTrackDrawable.getIntrinsicHeight(); ; m = 0)
    {
      int n = localRect1.left;
      int i1 = localRect1.right;
      if (this.mThumbDrawable != null)
      {
        Rect localRect2 = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
        n = Math.max(n, localRect2.left);
        i1 = Math.max(i1, localRect2.right);
      }
      int i2 = Math.max(this.mSwitchMinWidth, i1 + (n + 2 * this.mThumbWidth));
      int i3 = Math.max(m, i);
      this.mSwitchWidth = i2;
      this.mSwitchHeight = i3;
      super.onMeasure(paramInt1, paramInt2);
      if (getMeasuredHeight() < i3)
        setMeasuredDimension(getMeasuredWidthAndState(), i3);
      return;
      i = 0;
      j = 0;
      break;
      label292: k = 0;
      break label127;
      label298: localRect1.setEmpty();
    }
  }

  public void onPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onPopulateAccessibilityEvent(paramAccessibilityEvent);
    if (isChecked());
    for (CharSequence localCharSequence = this.mTextOn; ; localCharSequence = this.mTextOff)
    {
      if (localCharSequence != null)
        paramAccessibilityEvent.getText().add(localCharSequence);
      return;
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    this.mVelocityTracker.addMovement(paramMotionEvent);
    switch (paramMotionEvent.getActionMasked())
    {
    default:
    case 0:
    case 2:
    case 1:
    case 3:
    }
    while (true)
    {
      return super.onTouchEvent(paramMotionEvent);
      float f7 = paramMotionEvent.getX();
      float f8 = paramMotionEvent.getY();
      if ((!isEnabled()) || (!hitThumb(f7, f8)))
        continue;
      this.mTouchMode = 1;
      this.mTouchX = f7;
      this.mTouchY = f8;
      continue;
      switch (this.mTouchMode)
      {
      case 0:
      default:
        break;
      case 1:
        float f5 = paramMotionEvent.getX();
        float f6 = paramMotionEvent.getY();
        if ((Math.abs(f5 - this.mTouchX) <= this.mTouchSlop) && (Math.abs(f6 - this.mTouchY) <= this.mTouchSlop))
          continue;
        this.mTouchMode = 2;
        getParent().requestDisallowInterceptTouchEvent(true);
        this.mTouchX = f5;
        this.mTouchY = f6;
        return true;
      case 2:
        float f1 = paramMotionEvent.getX();
        int i = getThumbScrollRange();
        float f2 = f1 - this.mTouchX;
        float f3;
        if (i != 0)
        {
          f3 = f2 / i;
          if (ViewUtils.isLayoutRtl(this))
            f3 = -f3;
          float f4 = constrain(f3 + this.mThumbPosition, 0.0F, 1.0F);
          if (f4 != this.mThumbPosition)
          {
            this.mTouchX = f1;
            setThumbPosition(f4);
          }
          return true;
        }
        if (f2 > 0.0F)
          f3 = 1.0F;
        while (true)
        {
          break;
          f3 = -1.0F;
        }
        if (this.mTouchMode == 2)
        {
          stopDrag(paramMotionEvent);
          super.onTouchEvent(paramMotionEvent);
          return true;
        }
        this.mTouchMode = 0;
        this.mVelocityTracker.clear();
      }
    }
  }

  public void setChecked(boolean paramBoolean)
  {
    super.setChecked(paramBoolean);
    boolean bool = isChecked();
    if ((getWindowToken() != null) && (ViewCompat.isLaidOut(this)))
    {
      animateThumbToCheckedState(bool);
      return;
    }
    cancelPositionAnimator();
    float f;
    if (bool)
      f = 1.0F;
    while (true)
    {
      setThumbPosition(f);
      return;
      f = 0.0F;
    }
  }

  public void setShowText(boolean paramBoolean)
  {
    if (this.mShowText != paramBoolean)
    {
      this.mShowText = paramBoolean;
      requestLayout();
    }
  }

  public void setSplitTrack(boolean paramBoolean)
  {
    this.mSplitTrack = paramBoolean;
    invalidate();
  }

  public void setSwitchMinWidth(int paramInt)
  {
    this.mSwitchMinWidth = paramInt;
    requestLayout();
  }

  public void setSwitchPadding(int paramInt)
  {
    this.mSwitchPadding = paramInt;
    requestLayout();
  }

  public void setSwitchTextAppearance(Context paramContext, int paramInt)
  {
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramInt, R.styleable.TextAppearance);
    ColorStateList localColorStateList = localTintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor);
    if (localColorStateList != null)
    {
      this.mTextColors = localColorStateList;
      int i = localTintTypedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
      if ((i != 0) && (i != this.mTextPaint.getTextSize()))
      {
        this.mTextPaint.setTextSize(i);
        requestLayout();
      }
      setSwitchTypefaceByIndex(localTintTypedArray.getInt(R.styleable.TextAppearance_android_typeface, -1), localTintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, -1));
      if (!localTintTypedArray.getBoolean(R.styleable.TextAppearance_textAllCaps, false))
        break label134;
    }
    label134: for (this.mSwitchTransformationMethod = new AllCapsTransformationMethod(getContext()); ; this.mSwitchTransformationMethod = null)
    {
      localTintTypedArray.recycle();
      return;
      this.mTextColors = getTextColors();
      break;
    }
  }

  public void setSwitchTypeface(Typeface paramTypeface)
  {
    if (((this.mTextPaint.getTypeface() != null) && (!this.mTextPaint.getTypeface().equals(paramTypeface))) || ((this.mTextPaint.getTypeface() == null) && (paramTypeface != null)))
    {
      this.mTextPaint.setTypeface(paramTypeface);
      requestLayout();
      invalidate();
    }
  }

  public void setSwitchTypeface(Typeface paramTypeface, int paramInt)
  {
    if (paramInt > 0)
    {
      Typeface localTypeface;
      int i;
      label28: TextPaint localTextPaint2;
      float f;
      if (paramTypeface == null)
      {
        localTypeface = Typeface.defaultFromStyle(paramInt);
        setSwitchTypeface(localTypeface);
        if (localTypeface == null)
          break label101;
        i = localTypeface.getStyle();
        int j = paramInt & (i ^ 0xFFFFFFFF);
        TextPaint localTextPaint1 = this.mTextPaint;
        int k = j & 0x1;
        boolean bool = false;
        if (k != 0)
          bool = true;
        localTextPaint1.setFakeBoldText(bool);
        localTextPaint2 = this.mTextPaint;
        if ((j & 0x2) == 0)
          break label107;
        f = -0.25F;
      }
      while (true)
      {
        localTextPaint2.setTextSkewX(f);
        return;
        localTypeface = Typeface.create(paramTypeface, paramInt);
        break;
        label101: i = 0;
        break label28;
        label107: f = 0.0F;
      }
    }
    this.mTextPaint.setFakeBoldText(false);
    this.mTextPaint.setTextSkewX(0.0F);
    setSwitchTypeface(paramTypeface);
  }

  public void setTextOff(CharSequence paramCharSequence)
  {
    this.mTextOff = paramCharSequence;
    requestLayout();
  }

  public void setTextOn(CharSequence paramCharSequence)
  {
    this.mTextOn = paramCharSequence;
    requestLayout();
  }

  public void setThumbDrawable(Drawable paramDrawable)
  {
    if (this.mThumbDrawable != null)
      this.mThumbDrawable.setCallback(null);
    this.mThumbDrawable = paramDrawable;
    if (paramDrawable != null)
      paramDrawable.setCallback(this);
    requestLayout();
  }

  void setThumbPosition(float paramFloat)
  {
    this.mThumbPosition = paramFloat;
    invalidate();
  }

  public void setThumbResource(int paramInt)
  {
    setThumbDrawable(AppCompatResources.getDrawable(getContext(), paramInt));
  }

  public void setThumbTextPadding(int paramInt)
  {
    this.mThumbTextPadding = paramInt;
    requestLayout();
  }

  public void setThumbTintList(@Nullable ColorStateList paramColorStateList)
  {
    this.mThumbTintList = paramColorStateList;
    this.mHasThumbTint = true;
    applyThumbTint();
  }

  public void setThumbTintMode(@Nullable PorterDuff.Mode paramMode)
  {
    this.mThumbTintMode = paramMode;
    this.mHasThumbTintMode = true;
    applyThumbTint();
  }

  public void setTrackDrawable(Drawable paramDrawable)
  {
    if (this.mTrackDrawable != null)
      this.mTrackDrawable.setCallback(null);
    this.mTrackDrawable = paramDrawable;
    if (paramDrawable != null)
      paramDrawable.setCallback(this);
    requestLayout();
  }

  public void setTrackResource(int paramInt)
  {
    setTrackDrawable(AppCompatResources.getDrawable(getContext(), paramInt));
  }

  public void setTrackTintList(@Nullable ColorStateList paramColorStateList)
  {
    this.mTrackTintList = paramColorStateList;
    this.mHasTrackTint = true;
    applyTrackTint();
  }

  public void setTrackTintMode(@Nullable PorterDuff.Mode paramMode)
  {
    this.mTrackTintMode = paramMode;
    this.mHasTrackTintMode = true;
    applyTrackTint();
  }

  public void toggle()
  {
    if (!isChecked());
    for (boolean bool = true; ; bool = false)
    {
      setChecked(bool);
      return;
    }
  }

  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    return (super.verifyDrawable(paramDrawable)) || (paramDrawable == this.mThumbDrawable) || (paramDrawable == this.mTrackDrawable);
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.SwitchCompat
 * JD-Core Version:    0.6.0
 */