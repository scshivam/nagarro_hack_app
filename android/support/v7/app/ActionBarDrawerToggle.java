package android.support.v7.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class ActionBarDrawerToggle
  implements DrawerLayout.DrawerListener
{
  private final Delegate mActivityImpl;
  private final int mCloseDrawerContentDescRes;
  boolean mDrawerIndicatorEnabled = true;
  private final DrawerLayout mDrawerLayout;
  private boolean mDrawerSlideAnimationEnabled = true;
  private boolean mHasCustomUpIndicator;
  private Drawable mHomeAsUpIndicator;
  private final int mOpenDrawerContentDescRes;
  private DrawerArrowDrawable mSlider;
  View.OnClickListener mToolbarNavigationClickListener;
  private boolean mWarnedForDisplayHomeAsUp = false;

  public ActionBarDrawerToggle(Activity paramActivity, DrawerLayout paramDrawerLayout, @StringRes int paramInt1, @StringRes int paramInt2)
  {
    this(paramActivity, null, paramDrawerLayout, null, paramInt1, paramInt2);
  }

  public ActionBarDrawerToggle(Activity paramActivity, DrawerLayout paramDrawerLayout, Toolbar paramToolbar, @StringRes int paramInt1, @StringRes int paramInt2)
  {
    this(paramActivity, paramToolbar, paramDrawerLayout, null, paramInt1, paramInt2);
  }

  ActionBarDrawerToggle(Activity paramActivity, Toolbar paramToolbar, DrawerLayout paramDrawerLayout, DrawerArrowDrawable paramDrawerArrowDrawable, @StringRes int paramInt1, @StringRes int paramInt2)
  {
    if (paramToolbar != null)
    {
      this.mActivityImpl = new ToolbarCompatDelegate(paramToolbar);
      paramToolbar.setNavigationOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (ActionBarDrawerToggle.this.mDrawerIndicatorEnabled)
            ActionBarDrawerToggle.this.toggle();
          do
            return;
          while (ActionBarDrawerToggle.this.mToolbarNavigationClickListener == null);
          ActionBarDrawerToggle.this.mToolbarNavigationClickListener.onClick(paramView);
        }
      });
      this.mDrawerLayout = paramDrawerLayout;
      this.mOpenDrawerContentDescRes = paramInt1;
      this.mCloseDrawerContentDescRes = paramInt2;
      if (paramDrawerArrowDrawable != null)
        break label205;
    }
    label205: for (this.mSlider = new DrawerArrowDrawable(this.mActivityImpl.getActionBarThemedContext()); ; this.mSlider = paramDrawerArrowDrawable)
    {
      this.mHomeAsUpIndicator = getThemeUpIndicator();
      return;
      if ((paramActivity instanceof DelegateProvider))
      {
        this.mActivityImpl = ((DelegateProvider)paramActivity).getDrawerToggleDelegate();
        break;
      }
      if (Build.VERSION.SDK_INT >= 18)
      {
        this.mActivityImpl = new JellybeanMr2Delegate(paramActivity);
        break;
      }
      if (Build.VERSION.SDK_INT >= 14)
      {
        this.mActivityImpl = new IcsDelegate(paramActivity);
        break;
      }
      if (Build.VERSION.SDK_INT >= 11)
      {
        this.mActivityImpl = new HoneycombDelegate(paramActivity);
        break;
      }
      this.mActivityImpl = new DummyDelegate(paramActivity);
      break;
    }
  }

  private void setPosition(float paramFloat)
  {
    if (paramFloat == 1.0F)
      this.mSlider.setVerticalMirror(true);
    while (true)
    {
      this.mSlider.setProgress(paramFloat);
      return;
      if (paramFloat != 0.0F)
        continue;
      this.mSlider.setVerticalMirror(false);
    }
  }

  @NonNull
  public DrawerArrowDrawable getDrawerArrowDrawable()
  {
    return this.mSlider;
  }

  Drawable getThemeUpIndicator()
  {
    return this.mActivityImpl.getThemeUpIndicator();
  }

  public View.OnClickListener getToolbarNavigationClickListener()
  {
    return this.mToolbarNavigationClickListener;
  }

  public boolean isDrawerIndicatorEnabled()
  {
    return this.mDrawerIndicatorEnabled;
  }

  public boolean isDrawerSlideAnimationEnabled()
  {
    return this.mDrawerSlideAnimationEnabled;
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (!this.mHasCustomUpIndicator)
      this.mHomeAsUpIndicator = getThemeUpIndicator();
    syncState();
  }

  public void onDrawerClosed(View paramView)
  {
    setPosition(0.0F);
    if (this.mDrawerIndicatorEnabled)
      setActionBarDescription(this.mOpenDrawerContentDescRes);
  }

  public void onDrawerOpened(View paramView)
  {
    setPosition(1.0F);
    if (this.mDrawerIndicatorEnabled)
      setActionBarDescription(this.mCloseDrawerContentDescRes);
  }

  public void onDrawerSlide(View paramView, float paramFloat)
  {
    if (this.mDrawerSlideAnimationEnabled)
    {
      setPosition(Math.min(1.0F, Math.max(0.0F, paramFloat)));
      return;
    }
    setPosition(0.0F);
  }

  public void onDrawerStateChanged(int paramInt)
  {
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if ((paramMenuItem != null) && (paramMenuItem.getItemId() == 16908332) && (this.mDrawerIndicatorEnabled))
    {
      toggle();
      return true;
    }
    return false;
  }

  void setActionBarDescription(int paramInt)
  {
    this.mActivityImpl.setActionBarDescription(paramInt);
  }

  void setActionBarUpIndicator(Drawable paramDrawable, int paramInt)
  {
    if ((!this.mWarnedForDisplayHomeAsUp) && (!this.mActivityImpl.isNavigationVisible()))
    {
      Log.w("ActionBarDrawerToggle", "DrawerToggle may not show up because NavigationIcon is not visible. You may need to call actionbar.setDisplayHomeAsUpEnabled(true);");
      this.mWarnedForDisplayHomeAsUp = true;
    }
    this.mActivityImpl.setActionBarUpIndicator(paramDrawable, paramInt);
  }

  public void setDrawerArrowDrawable(@NonNull DrawerArrowDrawable paramDrawerArrowDrawable)
  {
    this.mSlider = paramDrawerArrowDrawable;
    syncState();
  }

  public void setDrawerIndicatorEnabled(boolean paramBoolean)
  {
    int i;
    if (paramBoolean != this.mDrawerIndicatorEnabled)
    {
      if (!paramBoolean)
        break label54;
      DrawerArrowDrawable localDrawerArrowDrawable = this.mSlider;
      if (!this.mDrawerLayout.isDrawerOpen(8388611))
        break label46;
      i = this.mCloseDrawerContentDescRes;
      setActionBarUpIndicator(localDrawerArrowDrawable, i);
    }
    while (true)
    {
      this.mDrawerIndicatorEnabled = paramBoolean;
      return;
      label46: i = this.mOpenDrawerContentDescRes;
      break;
      label54: setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
    }
  }

  public void setDrawerSlideAnimationEnabled(boolean paramBoolean)
  {
    this.mDrawerSlideAnimationEnabled = paramBoolean;
    if (!paramBoolean)
      setPosition(0.0F);
  }

  public void setHomeAsUpIndicator(int paramInt)
  {
    Drawable localDrawable = null;
    if (paramInt != 0)
      localDrawable = this.mDrawerLayout.getResources().getDrawable(paramInt);
    setHomeAsUpIndicator(localDrawable);
  }

  public void setHomeAsUpIndicator(Drawable paramDrawable)
  {
    if (paramDrawable == null)
      this.mHomeAsUpIndicator = getThemeUpIndicator();
    for (this.mHasCustomUpIndicator = false; ; this.mHasCustomUpIndicator = true)
    {
      if (!this.mDrawerIndicatorEnabled)
        setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
      return;
      this.mHomeAsUpIndicator = paramDrawable;
    }
  }

  public void setToolbarNavigationClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mToolbarNavigationClickListener = paramOnClickListener;
  }

  public void syncState()
  {
    DrawerArrowDrawable localDrawerArrowDrawable;
    if (this.mDrawerLayout.isDrawerOpen(8388611))
    {
      setPosition(1.0F);
      if (this.mDrawerIndicatorEnabled)
      {
        localDrawerArrowDrawable = this.mSlider;
        if (!this.mDrawerLayout.isDrawerOpen(8388611))
          break label61;
      }
    }
    label61: for (int i = this.mCloseDrawerContentDescRes; ; i = this.mOpenDrawerContentDescRes)
    {
      setActionBarUpIndicator(localDrawerArrowDrawable, i);
      return;
      setPosition(0.0F);
      break;
    }
  }

  void toggle()
  {
    int i = this.mDrawerLayout.getDrawerLockMode(8388611);
    if ((this.mDrawerLayout.isDrawerVisible(8388611)) && (i != 2))
      this.mDrawerLayout.closeDrawer(8388611);
    do
      return;
    while (i == 1);
    this.mDrawerLayout.openDrawer(8388611);
  }

  public static abstract interface Delegate
  {
    public abstract Context getActionBarThemedContext();

    public abstract Drawable getThemeUpIndicator();

    public abstract boolean isNavigationVisible();

    public abstract void setActionBarDescription(@StringRes int paramInt);

    public abstract void setActionBarUpIndicator(Drawable paramDrawable, @StringRes int paramInt);
  }

  public static abstract interface DelegateProvider
  {
    @Nullable
    public abstract ActionBarDrawerToggle.Delegate getDrawerToggleDelegate();
  }

  static class DummyDelegate
    implements ActionBarDrawerToggle.Delegate
  {
    final Activity mActivity;

    DummyDelegate(Activity paramActivity)
    {
      this.mActivity = paramActivity;
    }

    public Context getActionBarThemedContext()
    {
      return this.mActivity;
    }

    public Drawable getThemeUpIndicator()
    {
      return null;
    }

    public boolean isNavigationVisible()
    {
      return true;
    }

    public void setActionBarDescription(@StringRes int paramInt)
    {
    }

    public void setActionBarUpIndicator(Drawable paramDrawable, @StringRes int paramInt)
    {
    }
  }

  @RequiresApi(11)
  private static class HoneycombDelegate
    implements ActionBarDrawerToggle.Delegate
  {
    final Activity mActivity;
    ActionBarDrawerToggleHoneycomb.SetIndicatorInfo mSetIndicatorInfo;

    HoneycombDelegate(Activity paramActivity)
    {
      this.mActivity = paramActivity;
    }

    public Context getActionBarThemedContext()
    {
      return this.mActivity;
    }

    public Drawable getThemeUpIndicator()
    {
      return ActionBarDrawerToggleHoneycomb.getThemeUpIndicator(this.mActivity);
    }

    public boolean isNavigationVisible()
    {
      ActionBar localActionBar = this.mActivity.getActionBar();
      return (localActionBar != null) && ((0x4 & localActionBar.getDisplayOptions()) != 0);
    }

    public void setActionBarDescription(int paramInt)
    {
      this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarDescription(this.mSetIndicatorInfo, this.mActivity, paramInt);
    }

    public void setActionBarUpIndicator(Drawable paramDrawable, int paramInt)
    {
      ActionBar localActionBar = this.mActivity.getActionBar();
      if (localActionBar != null)
      {
        localActionBar.setDisplayShowHomeEnabled(true);
        this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarUpIndicator(this.mSetIndicatorInfo, this.mActivity, paramDrawable, paramInt);
        localActionBar.setDisplayShowHomeEnabled(false);
      }
    }
  }

  @RequiresApi(14)
  private static class IcsDelegate extends ActionBarDrawerToggle.HoneycombDelegate
  {
    IcsDelegate(Activity paramActivity)
    {
      super();
    }

    public Context getActionBarThemedContext()
    {
      ActionBar localActionBar = this.mActivity.getActionBar();
      if (localActionBar != null)
        return localActionBar.getThemedContext();
      return this.mActivity;
    }
  }

  @RequiresApi(18)
  private static class JellybeanMr2Delegate
    implements ActionBarDrawerToggle.Delegate
  {
    final Activity mActivity;

    JellybeanMr2Delegate(Activity paramActivity)
    {
      this.mActivity = paramActivity;
    }

    public Context getActionBarThemedContext()
    {
      ActionBar localActionBar = this.mActivity.getActionBar();
      if (localActionBar != null)
        return localActionBar.getThemedContext();
      return this.mActivity;
    }

    public Drawable getThemeUpIndicator()
    {
      TypedArray localTypedArray = getActionBarThemedContext().obtainStyledAttributes(null, new int[] { 16843531 }, 16843470, 0);
      Drawable localDrawable = localTypedArray.getDrawable(0);
      localTypedArray.recycle();
      return localDrawable;
    }

    public boolean isNavigationVisible()
    {
      ActionBar localActionBar = this.mActivity.getActionBar();
      return (localActionBar != null) && ((0x4 & localActionBar.getDisplayOptions()) != 0);
    }

    public void setActionBarDescription(int paramInt)
    {
      ActionBar localActionBar = this.mActivity.getActionBar();
      if (localActionBar != null)
        localActionBar.setHomeActionContentDescription(paramInt);
    }

    public void setActionBarUpIndicator(Drawable paramDrawable, int paramInt)
    {
      ActionBar localActionBar = this.mActivity.getActionBar();
      if (localActionBar != null)
      {
        localActionBar.setHomeAsUpIndicator(paramDrawable);
        localActionBar.setHomeActionContentDescription(paramInt);
      }
    }
  }

  static class ToolbarCompatDelegate
    implements ActionBarDrawerToggle.Delegate
  {
    final CharSequence mDefaultContentDescription;
    final Drawable mDefaultUpIndicator;
    final Toolbar mToolbar;

    ToolbarCompatDelegate(Toolbar paramToolbar)
    {
      this.mToolbar = paramToolbar;
      this.mDefaultUpIndicator = paramToolbar.getNavigationIcon();
      this.mDefaultContentDescription = paramToolbar.getNavigationContentDescription();
    }

    public Context getActionBarThemedContext()
    {
      return this.mToolbar.getContext();
    }

    public Drawable getThemeUpIndicator()
    {
      return this.mDefaultUpIndicator;
    }

    public boolean isNavigationVisible()
    {
      return true;
    }

    public void setActionBarDescription(@StringRes int paramInt)
    {
      if (paramInt == 0)
      {
        this.mToolbar.setNavigationContentDescription(this.mDefaultContentDescription);
        return;
      }
      this.mToolbar.setNavigationContentDescription(paramInt);
    }

    public void setActionBarUpIndicator(Drawable paramDrawable, @StringRes int paramInt)
    {
      this.mToolbar.setNavigationIcon(paramDrawable);
      setActionBarDescription(paramInt);
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v7.app.ActionBarDrawerToggle
 * JD-Core Version:    0.6.0
 */