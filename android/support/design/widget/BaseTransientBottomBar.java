package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.design.R.anim;
import android.support.design.R.layout;
import android.support.design.R.styleable;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTransientBottomBar<B extends BaseTransientBottomBar<B>>
{
  static final int ANIMATION_DURATION = 250;
  static final int ANIMATION_FADE_DURATION = 180;
  public static final int LENGTH_INDEFINITE = -2;
  public static final int LENGTH_LONG = 0;
  public static final int LENGTH_SHORT = -1;
  static final int MSG_DISMISS = 1;
  static final int MSG_SHOW;
  private static final boolean USE_OFFSET_API;
  static final Handler sHandler;
  private final AccessibilityManager mAccessibilityManager;
  private List<BaseCallback<B>> mCallbacks;
  private final ContentViewCallback mContentViewCallback;
  private final Context mContext;
  private int mDuration;
  final SnackbarManager.Callback mManagerCallback = new SnackbarManager.Callback()
  {
    public void dismiss(int paramInt)
    {
      BaseTransientBottomBar.sHandler.sendMessage(BaseTransientBottomBar.sHandler.obtainMessage(1, paramInt, 0, BaseTransientBottomBar.this));
    }

    public void show()
    {
      BaseTransientBottomBar.sHandler.sendMessage(BaseTransientBottomBar.sHandler.obtainMessage(0, BaseTransientBottomBar.this));
    }
  };
  private final ViewGroup mTargetParent;
  final SnackbarBaseLayout mView;

  static
  {
    if ((Build.VERSION.SDK_INT >= 16) && (Build.VERSION.SDK_INT <= 19));
    for (boolean bool = true; ; bool = false)
    {
      USE_OFFSET_API = bool;
      sHandler = new Handler(Looper.getMainLooper(), new Handler.Callback()
      {
        public boolean handleMessage(Message paramMessage)
        {
          switch (paramMessage.what)
          {
          default:
            return false;
          case 0:
            ((BaseTransientBottomBar)paramMessage.obj).showView();
            return true;
          case 1:
          }
          ((BaseTransientBottomBar)paramMessage.obj).hideView(paramMessage.arg1);
          return true;
        }
      });
      return;
    }
  }

  protected BaseTransientBottomBar(@NonNull ViewGroup paramViewGroup, @NonNull View paramView, @NonNull ContentViewCallback paramContentViewCallback)
  {
    if (paramViewGroup == null)
      throw new IllegalArgumentException("Transient bottom bar must have non-null parent");
    if (paramView == null)
      throw new IllegalArgumentException("Transient bottom bar must have non-null content");
    if (paramContentViewCallback == null)
      throw new IllegalArgumentException("Transient bottom bar must have non-null callback");
    this.mTargetParent = paramViewGroup;
    this.mContentViewCallback = paramContentViewCallback;
    this.mContext = paramViewGroup.getContext();
    ThemeUtils.checkAppCompatTheme(this.mContext);
    this.mView = ((SnackbarBaseLayout)LayoutInflater.from(this.mContext).inflate(R.layout.design_layout_snackbar, this.mTargetParent, false));
    this.mView.addView(paramView);
    ViewCompat.setAccessibilityLiveRegion(this.mView, 1);
    ViewCompat.setImportantForAccessibility(this.mView, 1);
    ViewCompat.setFitsSystemWindows(this.mView, true);
    ViewCompat.setOnApplyWindowInsetsListener(this.mView, new OnApplyWindowInsetsListener()
    {
      public WindowInsetsCompat onApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat)
      {
        paramView.setPadding(paramView.getPaddingLeft(), paramView.getPaddingTop(), paramView.getPaddingRight(), paramWindowInsetsCompat.getSystemWindowInsetBottom());
        return paramWindowInsetsCompat;
      }
    });
    this.mAccessibilityManager = ((AccessibilityManager)this.mContext.getSystemService("accessibility"));
  }

  private void animateViewOut(int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 12)
    {
      ValueAnimator localValueAnimator = new ValueAnimator();
      int[] arrayOfInt = new int[2];
      arrayOfInt[0] = 0;
      arrayOfInt[1] = this.mView.getHeight();
      localValueAnimator.setIntValues(arrayOfInt);
      localValueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
      localValueAnimator.setDuration(250L);
      localValueAnimator.addListener(new AnimatorListenerAdapter(paramInt)
      {
        public void onAnimationEnd(Animator paramAnimator)
        {
          BaseTransientBottomBar.this.onViewHidden(this.val$event);
        }

        public void onAnimationStart(Animator paramAnimator)
        {
          BaseTransientBottomBar.this.mContentViewCallback.animateContentOut(0, 180);
        }
      });
      localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        private int mPreviousAnimatedIntValue = 0;

        public void onAnimationUpdate(ValueAnimator paramValueAnimator)
        {
          int i = ((Integer)paramValueAnimator.getAnimatedValue()).intValue();
          if (BaseTransientBottomBar.USE_OFFSET_API)
            ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.mView, i - this.mPreviousAnimatedIntValue);
          while (true)
          {
            this.mPreviousAnimatedIntValue = i;
            return;
            BaseTransientBottomBar.this.mView.setTranslationY(i);
          }
        }
      });
      localValueAnimator.start();
      return;
    }
    Animation localAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.design_snackbar_out);
    localAnimation.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    localAnimation.setDuration(250L);
    localAnimation.setAnimationListener(new Animation.AnimationListener(paramInt)
    {
      public void onAnimationEnd(Animation paramAnimation)
      {
        BaseTransientBottomBar.this.onViewHidden(this.val$event);
      }

      public void onAnimationRepeat(Animation paramAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnimation)
      {
      }
    });
    this.mView.startAnimation(localAnimation);
  }

  @NonNull
  public B addCallback(@NonNull BaseCallback<B> paramBaseCallback)
  {
    if (paramBaseCallback == null)
      return this;
    if (this.mCallbacks == null)
      this.mCallbacks = new ArrayList();
    this.mCallbacks.add(paramBaseCallback);
    return this;
  }

  void animateViewIn()
  {
    if (Build.VERSION.SDK_INT >= 12)
    {
      int i = this.mView.getHeight();
      if (USE_OFFSET_API)
        ViewCompat.offsetTopAndBottom(this.mView, i);
      while (true)
      {
        ValueAnimator localValueAnimator = new ValueAnimator();
        localValueAnimator.setIntValues(new int[] { i, 0 });
        localValueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        localValueAnimator.setDuration(250L);
        localValueAnimator.addListener(new AnimatorListenerAdapter()
        {
          public void onAnimationEnd(Animator paramAnimator)
          {
            BaseTransientBottomBar.this.onViewShown();
          }

          public void onAnimationStart(Animator paramAnimator)
          {
            BaseTransientBottomBar.this.mContentViewCallback.animateContentIn(70, 180);
          }
        });
        localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(i)
        {
          private int mPreviousAnimatedIntValue = this.val$viewHeight;

          public void onAnimationUpdate(ValueAnimator paramValueAnimator)
          {
            int i = ((Integer)paramValueAnimator.getAnimatedValue()).intValue();
            if (BaseTransientBottomBar.USE_OFFSET_API)
              ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.mView, i - this.mPreviousAnimatedIntValue);
            while (true)
            {
              this.mPreviousAnimatedIntValue = i;
              return;
              BaseTransientBottomBar.this.mView.setTranslationY(i);
            }
          }
        });
        localValueAnimator.start();
        return;
        this.mView.setTranslationY(i);
      }
    }
    Animation localAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.design_snackbar_in);
    localAnimation.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    localAnimation.setDuration(250L);
    localAnimation.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnimation)
      {
        BaseTransientBottomBar.this.onViewShown();
      }

      public void onAnimationRepeat(Animation paramAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnimation)
      {
      }
    });
    this.mView.startAnimation(localAnimation);
  }

  public void dismiss()
  {
    dispatchDismiss(3);
  }

  void dispatchDismiss(int paramInt)
  {
    SnackbarManager.getInstance().dismiss(this.mManagerCallback, paramInt);
  }

  @NonNull
  public Context getContext()
  {
    return this.mContext;
  }

  public int getDuration()
  {
    return this.mDuration;
  }

  @NonNull
  public View getView()
  {
    return this.mView;
  }

  final void hideView(int paramInt)
  {
    if ((shouldAnimate()) && (this.mView.getVisibility() == 0))
    {
      animateViewOut(paramInt);
      return;
    }
    onViewHidden(paramInt);
  }

  public boolean isShown()
  {
    return SnackbarManager.getInstance().isCurrent(this.mManagerCallback);
  }

  public boolean isShownOrQueued()
  {
    return SnackbarManager.getInstance().isCurrentOrNext(this.mManagerCallback);
  }

  void onViewHidden(int paramInt)
  {
    SnackbarManager.getInstance().onDismissed(this.mManagerCallback);
    if (this.mCallbacks != null)
      for (int i = -1 + this.mCallbacks.size(); i >= 0; i--)
        ((BaseCallback)this.mCallbacks.get(i)).onDismissed(this, paramInt);
    if (Build.VERSION.SDK_INT < 11)
      this.mView.setVisibility(8);
    ViewParent localViewParent = this.mView.getParent();
    if ((localViewParent instanceof ViewGroup))
      ((ViewGroup)localViewParent).removeView(this.mView);
  }

  void onViewShown()
  {
    SnackbarManager.getInstance().onShown(this.mManagerCallback);
    if (this.mCallbacks != null)
      for (int i = -1 + this.mCallbacks.size(); i >= 0; i--)
        ((BaseCallback)this.mCallbacks.get(i)).onShown(this);
  }

  @NonNull
  public B removeCallback(@NonNull BaseCallback<B> paramBaseCallback)
  {
    if (paramBaseCallback == null);
    do
      return this;
    while (this.mCallbacks == null);
    this.mCallbacks.remove(paramBaseCallback);
    return this;
  }

  @NonNull
  public B setDuration(int paramInt)
  {
    this.mDuration = paramInt;
    return this;
  }

  boolean shouldAnimate()
  {
    return !this.mAccessibilityManager.isEnabled();
  }

  public void show()
  {
    SnackbarManager.getInstance().show(this.mDuration, this.mManagerCallback);
  }

  final void showView()
  {
    if (this.mView.getParent() == null)
    {
      ViewGroup.LayoutParams localLayoutParams = this.mView.getLayoutParams();
      if ((localLayoutParams instanceof CoordinatorLayout.LayoutParams))
      {
        CoordinatorLayout.LayoutParams localLayoutParams1 = (CoordinatorLayout.LayoutParams)localLayoutParams;
        Behavior localBehavior = new Behavior();
        localBehavior.setStartAlphaSwipeDistance(0.1F);
        localBehavior.setEndAlphaSwipeDistance(0.6F);
        localBehavior.setSwipeDirection(0);
        localBehavior.setListener(new SwipeDismissBehavior.OnDismissListener()
        {
          public void onDismiss(View paramView)
          {
            paramView.setVisibility(8);
            BaseTransientBottomBar.this.dispatchDismiss(0);
          }

          public void onDragStateChanged(int paramInt)
          {
            switch (paramInt)
            {
            default:
              return;
            case 1:
            case 2:
              SnackbarManager.getInstance().pauseTimeout(BaseTransientBottomBar.this.mManagerCallback);
              return;
            case 0:
            }
            SnackbarManager.getInstance().restoreTimeoutIfPaused(BaseTransientBottomBar.this.mManagerCallback);
          }
        });
        localLayoutParams1.setBehavior(localBehavior);
        localLayoutParams1.insetEdge = 80;
      }
      this.mTargetParent.addView(this.mView);
    }
    this.mView.setOnAttachStateChangeListener(new OnAttachStateChangeListener()
    {
      public void onViewAttachedToWindow(View paramView)
      {
      }

      public void onViewDetachedFromWindow(View paramView)
      {
        if (BaseTransientBottomBar.this.isShownOrQueued())
          BaseTransientBottomBar.sHandler.post(new Runnable()
          {
            public void run()
            {
              BaseTransientBottomBar.this.onViewHidden(3);
            }
          });
      }
    });
    if (ViewCompat.isLaidOut(this.mView))
    {
      if (shouldAnimate())
      {
        animateViewIn();
        return;
      }
      onViewShown();
      return;
    }
    this.mView.setOnLayoutChangeListener(new OnLayoutChangeListener()
    {
      public void onLayoutChange(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
      {
        BaseTransientBottomBar.this.mView.setOnLayoutChangeListener(null);
        if (BaseTransientBottomBar.this.shouldAnimate())
        {
          BaseTransientBottomBar.this.animateViewIn();
          return;
        }
        BaseTransientBottomBar.this.onViewShown();
      }
    });
  }

  public static abstract class BaseCallback<B>
  {
    public static final int DISMISS_EVENT_ACTION = 1;
    public static final int DISMISS_EVENT_CONSECUTIVE = 4;
    public static final int DISMISS_EVENT_MANUAL = 3;
    public static final int DISMISS_EVENT_SWIPE = 0;
    public static final int DISMISS_EVENT_TIMEOUT = 2;

    public void onDismissed(B paramB, int paramInt)
    {
    }

    public void onShown(B paramB)
    {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface DismissEvent
    {
    }
  }

  final class Behavior extends SwipeDismissBehavior<BaseTransientBottomBar.SnackbarBaseLayout>
  {
    Behavior()
    {
    }

    public boolean canSwipeDismissView(View paramView)
    {
      return paramView instanceof BaseTransientBottomBar.SnackbarBaseLayout;
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout paramCoordinatorLayout, BaseTransientBottomBar.SnackbarBaseLayout paramSnackbarBaseLayout, MotionEvent paramMotionEvent)
    {
      switch (paramMotionEvent.getActionMasked())
      {
      case 2:
      default:
      case 0:
      case 1:
      case 3:
      }
      while (true)
      {
        return super.onInterceptTouchEvent(paramCoordinatorLayout, paramSnackbarBaseLayout, paramMotionEvent);
        if (!paramCoordinatorLayout.isPointInChildBounds(paramSnackbarBaseLayout, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY()))
          continue;
        SnackbarManager.getInstance().pauseTimeout(BaseTransientBottomBar.this.mManagerCallback);
        continue;
        SnackbarManager.getInstance().restoreTimeoutIfPaused(BaseTransientBottomBar.this.mManagerCallback);
      }
    }
  }

  public static abstract interface ContentViewCallback
  {
    public abstract void animateContentIn(int paramInt1, int paramInt2);

    public abstract void animateContentOut(int paramInt1, int paramInt2);
  }

  @Retention(RetentionPolicy.SOURCE)
  @IntRange(from=1L)
  @RestrictTo({android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP})
  public static @interface Duration
  {
  }

  @RestrictTo({android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP})
  static abstract interface OnAttachStateChangeListener
  {
    public abstract void onViewAttachedToWindow(View paramView);

    public abstract void onViewDetachedFromWindow(View paramView);
  }

  @RestrictTo({android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP})
  static abstract interface OnLayoutChangeListener
  {
    public abstract void onLayoutChange(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  }

  @RestrictTo({android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP})
  static class SnackbarBaseLayout extends FrameLayout
  {
    private BaseTransientBottomBar.OnAttachStateChangeListener mOnAttachStateChangeListener;
    private BaseTransientBottomBar.OnLayoutChangeListener mOnLayoutChangeListener;

    SnackbarBaseLayout(Context paramContext)
    {
      this(paramContext, null);
    }

    SnackbarBaseLayout(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.SnackbarLayout);
      if (localTypedArray.hasValue(R.styleable.SnackbarLayout_elevation))
        ViewCompat.setElevation(this, localTypedArray.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, 0));
      localTypedArray.recycle();
      setClickable(true);
    }

    protected void onAttachedToWindow()
    {
      super.onAttachedToWindow();
      if (this.mOnAttachStateChangeListener != null)
        this.mOnAttachStateChangeListener.onViewAttachedToWindow(this);
      ViewCompat.requestApplyInsets(this);
    }

    protected void onDetachedFromWindow()
    {
      super.onDetachedFromWindow();
      if (this.mOnAttachStateChangeListener != null)
        this.mOnAttachStateChangeListener.onViewDetachedFromWindow(this);
    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
      if (this.mOnLayoutChangeListener != null)
        this.mOnLayoutChangeListener.onLayoutChange(this, paramInt1, paramInt2, paramInt3, paramInt4);
    }

    void setOnAttachStateChangeListener(BaseTransientBottomBar.OnAttachStateChangeListener paramOnAttachStateChangeListener)
    {
      this.mOnAttachStateChangeListener = paramOnAttachStateChangeListener;
    }

    void setOnLayoutChangeListener(BaseTransientBottomBar.OnLayoutChangeListener paramOnLayoutChangeListener)
    {
      this.mOnLayoutChangeListener = paramOnLayoutChangeListener;
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.design.widget.BaseTransientBottomBar
 * JD-Core Version:    0.6.0
 */