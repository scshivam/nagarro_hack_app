package android.support.constraint;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import java.util.ArrayList;

public class ConstraintLayout extends ViewGroup
{
  static final boolean ALLOWS_EMBEDDED = false;
  private static final boolean SIMPLE_LAYOUT = true;
  private static final String TAG = "ConstraintLayout";
  public static final String VERSION = "ConstraintLayout-1.0.0";
  SparseArray<View> mChildrenByIds = new SparseArray();
  private ConstraintSet mConstraintSet = null;
  private boolean mDirtyHierarchy = true;
  ConstraintWidgetContainer mLayoutWidget = new ConstraintWidgetContainer();
  private int mMaxHeight = 2147483647;
  private int mMaxWidth = 2147483647;
  private int mMinHeight = 0;
  private int mMinWidth = 0;
  private int mOptimizationLevel = 2;
  private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList(100);

  public ConstraintLayout(Context paramContext)
  {
    super(paramContext);
    init(null);
  }

  public ConstraintLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramAttributeSet);
  }

  public ConstraintLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramAttributeSet);
  }

  private final ConstraintWidget getTargetWidget(int paramInt)
  {
    if (paramInt == 0)
      return this.mLayoutWidget;
    View localView = (View)this.mChildrenByIds.get(paramInt);
    if (localView == this)
      return this.mLayoutWidget;
    if (localView == null)
      return null;
    return ((LayoutParams)localView.getLayoutParams()).widget;
  }

  private final ConstraintWidget getViewWidget(View paramView)
  {
    if (paramView == this)
      return this.mLayoutWidget;
    if (paramView == null)
      return null;
    return ((LayoutParams)paramView.getLayoutParams()).widget;
  }

  private void init(AttributeSet paramAttributeSet)
  {
    this.mLayoutWidget.setCompanionWidget(this);
    this.mChildrenByIds.put(getId(), this);
    this.mConstraintSet = null;
    if (paramAttributeSet != null)
    {
      TypedArray localTypedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_Layout);
      int i = localTypedArray.getIndexCount();
      int j = 0;
      if (j < i)
      {
        int k = localTypedArray.getIndex(j);
        if (k == R.styleable.ConstraintLayout_Layout_android_minWidth)
          this.mMinWidth = localTypedArray.getDimensionPixelOffset(k, this.mMinWidth);
        while (true)
        {
          j++;
          break;
          if (k == R.styleable.ConstraintLayout_Layout_android_minHeight)
          {
            this.mMinHeight = localTypedArray.getDimensionPixelOffset(k, this.mMinHeight);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_android_maxWidth)
          {
            this.mMaxWidth = localTypedArray.getDimensionPixelOffset(k, this.mMaxWidth);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_android_maxHeight)
          {
            this.mMaxHeight = localTypedArray.getDimensionPixelOffset(k, this.mMaxHeight);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_optimizationLevel)
          {
            this.mOptimizationLevel = localTypedArray.getInt(k, this.mOptimizationLevel);
            continue;
          }
          if (k != R.styleable.ConstraintLayout_Layout_constraintSet)
            continue;
          int m = localTypedArray.getResourceId(k, 0);
          this.mConstraintSet = new ConstraintSet();
          this.mConstraintSet.load(getContext(), m);
        }
      }
      localTypedArray.recycle();
    }
    this.mLayoutWidget.setOptimizationLevel(this.mOptimizationLevel);
  }

  private void internalMeasureChildren(int paramInt1, int paramInt2)
  {
    int i = getPaddingTop() + getPaddingBottom();
    int j = getPaddingLeft() + getPaddingRight();
    int k = getChildCount();
    int m = 0;
    if (m < k)
    {
      View localView = getChildAt(m);
      if (localView.getVisibility() == 8);
      LayoutParams localLayoutParams;
      ConstraintWidget localConstraintWidget;
      do
      {
        m++;
        break;
        localLayoutParams = (LayoutParams)localView.getLayoutParams();
        localConstraintWidget = localLayoutParams.widget;
      }
      while (localLayoutParams.isGuideline);
      int n = localLayoutParams.width;
      int i1 = localLayoutParams.height;
      int i2;
      label171: int i4;
      int i6;
      label206: int i7;
      if ((localLayoutParams.horizontalDimensionFixed) || (localLayoutParams.verticalDimensionFixed) || ((!localLayoutParams.horizontalDimensionFixed) && (localLayoutParams.matchConstraintDefaultWidth == 1)) || (localLayoutParams.width == -1) || ((!localLayoutParams.verticalDimensionFixed) && ((localLayoutParams.matchConstraintDefaultHeight == 1) || (localLayoutParams.height == -1))))
      {
        i2 = 1;
        i3 = 0;
        i4 = 0;
        if (i2 != 0)
        {
          if ((n != 0) && (n != -1))
            break label327;
          i6 = getChildMeasureSpec(paramInt1, j, -2);
          i4 = 1;
          if ((i1 != 0) && (i1 != -1))
            break label343;
          i7 = getChildMeasureSpec(paramInt2, i, -2);
        }
      }
      for (int i3 = 1; ; i3 = 0)
      {
        localView.measure(i6, i7);
        n = localView.getMeasuredWidth();
        i1 = localView.getMeasuredHeight();
        localConstraintWidget.setWidth(n);
        localConstraintWidget.setHeight(i1);
        if (i4 != 0)
          localConstraintWidget.setWrapWidth(n);
        if (i3 != 0)
          localConstraintWidget.setWrapHeight(i1);
        if (!localLayoutParams.needsBaseline)
          break;
        int i5 = localView.getBaseline();
        if (i5 == -1)
          break;
        localConstraintWidget.setBaselineDistance(i5);
        break;
        i2 = 0;
        break label171;
        label327: i6 = getChildMeasureSpec(paramInt1, j, n);
        i4 = 0;
        break label206;
        label343: i7 = getChildMeasureSpec(paramInt2, i, i1);
      }
    }
  }

  private void setChildrenConstraints()
  {
    if (this.mConstraintSet != null)
      this.mConstraintSet.applyToInternal(this);
    int i = getChildCount();
    this.mLayoutWidget.removeAllChildren();
    int j = 0;
    if (j < i)
    {
      View localView1 = getChildAt(j);
      ConstraintWidget localConstraintWidget1 = getViewWidget(localView1);
      if (localConstraintWidget1 == null);
      LayoutParams localLayoutParams1;
      label200: 
      do
        while (true)
        {
          j++;
          break;
          localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
          localConstraintWidget1.reset();
          localConstraintWidget1.setVisibility(localView1.getVisibility());
          localConstraintWidget1.setCompanionWidget(localView1);
          this.mLayoutWidget.add(localConstraintWidget1);
          if ((!localLayoutParams1.verticalDimensionFixed) || (!localLayoutParams1.horizontalDimensionFixed))
            this.mVariableDimensionsWidgets.add(localConstraintWidget1);
          if (!localLayoutParams1.isGuideline)
            break label200;
          android.support.constraint.solver.widgets.Guideline localGuideline = (android.support.constraint.solver.widgets.Guideline)localConstraintWidget1;
          if (localLayoutParams1.guideBegin != -1)
            localGuideline.setGuideBegin(localLayoutParams1.guideBegin);
          if (localLayoutParams1.guideEnd != -1)
            localGuideline.setGuideEnd(localLayoutParams1.guideEnd);
          if (localLayoutParams1.guidePercent == -1.0F)
            continue;
          localGuideline.setGuidePercent(localLayoutParams1.guidePercent);
        }
      while ((localLayoutParams1.resolvedLeftToLeft == -1) && (localLayoutParams1.resolvedLeftToRight == -1) && (localLayoutParams1.resolvedRightToLeft == -1) && (localLayoutParams1.resolvedRightToRight == -1) && (localLayoutParams1.topToTop == -1) && (localLayoutParams1.topToBottom == -1) && (localLayoutParams1.bottomToTop == -1) && (localLayoutParams1.bottomToBottom == -1) && (localLayoutParams1.baselineToBaseline == -1) && (localLayoutParams1.editorAbsoluteX == -1) && (localLayoutParams1.editorAbsoluteY == -1) && (localLayoutParams1.width != -1) && (localLayoutParams1.height != -1));
      int k = localLayoutParams1.resolvedLeftToLeft;
      int m = localLayoutParams1.resolvedLeftToRight;
      int n = localLayoutParams1.resolvedRightToLeft;
      int i1 = localLayoutParams1.resolvedRightToRight;
      int i2 = localLayoutParams1.resolveGoneLeftMargin;
      int i3 = localLayoutParams1.resolveGoneRightMargin;
      float f = localLayoutParams1.resolvedHorizontalBias;
      if (Build.VERSION.SDK_INT < 17)
      {
        k = localLayoutParams1.leftToLeft;
        m = localLayoutParams1.leftToRight;
        n = localLayoutParams1.rightToLeft;
        i1 = localLayoutParams1.rightToRight;
        i2 = localLayoutParams1.goneLeftMargin;
        i3 = localLayoutParams1.goneRightMargin;
        f = localLayoutParams1.horizontalBias;
        if ((k == -1) && (m == -1))
        {
          if (localLayoutParams1.startToStart == -1)
            break label1092;
          k = localLayoutParams1.startToStart;
        }
        label451: if ((n == -1) && (i1 == -1))
        {
          if (localLayoutParams1.endToStart == -1)
            break label1111;
          n = localLayoutParams1.endToStart;
        }
      }
      label479: if (k != -1)
      {
        ConstraintWidget localConstraintWidget10 = getTargetWidget(k);
        if (localConstraintWidget10 != null)
          localConstraintWidget1.immediateConnect(ConstraintAnchor.Type.LEFT, localConstraintWidget10, ConstraintAnchor.Type.LEFT, localLayoutParams1.leftMargin, i2);
        if (n == -1)
          break label1172;
        ConstraintWidget localConstraintWidget9 = getTargetWidget(n);
        if (localConstraintWidget9 != null)
          localConstraintWidget1.immediateConnect(ConstraintAnchor.Type.RIGHT, localConstraintWidget9, ConstraintAnchor.Type.LEFT, localLayoutParams1.rightMargin, i3);
        if (localLayoutParams1.topToTop == -1)
          break label1214;
        ConstraintWidget localConstraintWidget8 = getTargetWidget(localLayoutParams1.topToTop);
        if (localConstraintWidget8 != null)
          localConstraintWidget1.immediateConnect(ConstraintAnchor.Type.TOP, localConstraintWidget8, ConstraintAnchor.Type.TOP, localLayoutParams1.topMargin, localLayoutParams1.goneTopMargin);
        if (localLayoutParams1.bottomToTop == -1)
          break label1265;
        ConstraintWidget localConstraintWidget7 = getTargetWidget(localLayoutParams1.bottomToTop);
        if (localConstraintWidget7 != null)
          localConstraintWidget1.immediateConnect(ConstraintAnchor.Type.BOTTOM, localConstraintWidget7, ConstraintAnchor.Type.TOP, localLayoutParams1.bottomMargin, localLayoutParams1.goneBottomMargin);
        if (localLayoutParams1.baselineToBaseline != -1)
        {
          View localView2 = (View)this.mChildrenByIds.get(localLayoutParams1.baselineToBaseline);
          ConstraintWidget localConstraintWidget6 = getTargetWidget(localLayoutParams1.baselineToBaseline);
          if ((localConstraintWidget6 != null) && (localView2 != null) && ((localView2.getLayoutParams() instanceof LayoutParams)))
          {
            LayoutParams localLayoutParams2 = (LayoutParams)localView2.getLayoutParams();
            localLayoutParams1.needsBaseline = true;
            localLayoutParams2.needsBaseline = true;
            localConstraintWidget1.getAnchor(ConstraintAnchor.Type.BASELINE).connect(localConstraintWidget6.getAnchor(ConstraintAnchor.Type.BASELINE), 0, -1, ConstraintAnchor.Strength.STRONG, 0, true);
            localConstraintWidget1.getAnchor(ConstraintAnchor.Type.TOP).reset();
            localConstraintWidget1.getAnchor(ConstraintAnchor.Type.BOTTOM).reset();
          }
        }
        if ((f >= 0.0F) && (f != 0.5F))
          localConstraintWidget1.setHorizontalBiasPercent(f);
        if ((localLayoutParams1.verticalBias >= 0.0F) && (localLayoutParams1.verticalBias != 0.5F))
          localConstraintWidget1.setVerticalBiasPercent(localLayoutParams1.verticalBias);
        if ((isInEditMode()) && ((localLayoutParams1.editorAbsoluteX != -1) || (localLayoutParams1.editorAbsoluteY != -1)))
          localConstraintWidget1.setOrigin(localLayoutParams1.editorAbsoluteX, localLayoutParams1.editorAbsoluteY);
        if (localLayoutParams1.horizontalDimensionFixed)
          break label1333;
        if (localLayoutParams1.width != -1)
          break label1316;
        localConstraintWidget1.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
        localConstraintWidget1.getAnchor(ConstraintAnchor.Type.LEFT).mMargin = localLayoutParams1.leftMargin;
        localConstraintWidget1.getAnchor(ConstraintAnchor.Type.RIGHT).mMargin = localLayoutParams1.rightMargin;
        if (localLayoutParams1.verticalDimensionFixed)
          break label1371;
        if (localLayoutParams1.height != -1)
          break label1354;
        localConstraintWidget1.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
        localConstraintWidget1.getAnchor(ConstraintAnchor.Type.TOP).mMargin = localLayoutParams1.topMargin;
        localConstraintWidget1.getAnchor(ConstraintAnchor.Type.BOTTOM).mMargin = localLayoutParams1.bottomMargin;
      }
      while (true)
      {
        label518: label557: label605: if (localLayoutParams1.dimensionRatio != null)
          localConstraintWidget1.setDimensionRatio(localLayoutParams1.dimensionRatio);
        label653: label934: localConstraintWidget1.setHorizontalWeight(localLayoutParams1.horizontalWeight);
        localConstraintWidget1.setVerticalWeight(localLayoutParams1.verticalWeight);
        localConstraintWidget1.setHorizontalChainStyle(localLayoutParams1.horizontalChainStyle);
        localConstraintWidget1.setVerticalChainStyle(localLayoutParams1.verticalChainStyle);
        localConstraintWidget1.setHorizontalMatchStyle(localLayoutParams1.matchConstraintDefaultWidth, localLayoutParams1.matchConstraintMinWidth, localLayoutParams1.matchConstraintMaxWidth);
        localConstraintWidget1.setVerticalMatchStyle(localLayoutParams1.matchConstraintDefaultHeight, localLayoutParams1.matchConstraintMinHeight, localLayoutParams1.matchConstraintMaxHeight);
        break;
        label1092: if (localLayoutParams1.startToEnd == -1)
          break label451;
        m = localLayoutParams1.startToEnd;
        break label451;
        label1111: if (localLayoutParams1.endToEnd == -1)
          break label479;
        i1 = localLayoutParams1.endToEnd;
        break label479;
        if (m == -1)
          break label518;
        ConstraintWidget localConstraintWidget2 = getTargetWidget(m);
        if (localConstraintWidget2 == null)
          break label518;
        localConstraintWidget1.immediateConnect(ConstraintAnchor.Type.LEFT, localConstraintWidget2, ConstraintAnchor.Type.RIGHT, localLayoutParams1.leftMargin, i2);
        break label518;
        label1172: if (i1 == -1)
          break label557;
        ConstraintWidget localConstraintWidget3 = getTargetWidget(i1);
        if (localConstraintWidget3 == null)
          break label557;
        localConstraintWidget1.immediateConnect(ConstraintAnchor.Type.RIGHT, localConstraintWidget3, ConstraintAnchor.Type.RIGHT, localLayoutParams1.rightMargin, i3);
        break label557;
        label1214: if (localLayoutParams1.topToBottom == -1)
          break label605;
        ConstraintWidget localConstraintWidget4 = getTargetWidget(localLayoutParams1.topToBottom);
        if (localConstraintWidget4 == null)
          break label605;
        localConstraintWidget1.immediateConnect(ConstraintAnchor.Type.TOP, localConstraintWidget4, ConstraintAnchor.Type.BOTTOM, localLayoutParams1.topMargin, localLayoutParams1.goneTopMargin);
        break label605;
        label1265: if (localLayoutParams1.bottomToBottom == -1)
          break label653;
        ConstraintWidget localConstraintWidget5 = getTargetWidget(localLayoutParams1.bottomToBottom);
        if (localConstraintWidget5 == null)
          break label653;
        localConstraintWidget1.immediateConnect(ConstraintAnchor.Type.BOTTOM, localConstraintWidget5, ConstraintAnchor.Type.BOTTOM, localLayoutParams1.bottomMargin, localLayoutParams1.goneBottomMargin);
        break label653;
        label1316: localConstraintWidget1.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
        localConstraintWidget1.setWidth(0);
        break label934;
        label1333: localConstraintWidget1.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        localConstraintWidget1.setWidth(localLayoutParams1.width);
        break label934;
        label1354: localConstraintWidget1.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
        localConstraintWidget1.setHeight(0);
        continue;
        label1371: localConstraintWidget1.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        localConstraintWidget1.setHeight(localLayoutParams1.height);
      }
    }
  }

  private void setSelfDimensionBehaviour(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt1);
    int k = View.MeasureSpec.getMode(paramInt2);
    int m = View.MeasureSpec.getSize(paramInt2);
    int n = getPaddingTop() + getPaddingBottom();
    int i1 = getPaddingLeft() + getPaddingRight();
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour1 = ConstraintWidget.DimensionBehaviour.FIXED;
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED;
    getLayoutParams();
    int i2 = 0;
    int i3;
    switch (i)
    {
    default:
      i3 = 0;
      switch (k)
      {
      default:
      case -2147483648:
      case 0:
      case 1073741824:
      }
    case -2147483648:
    case 0:
    case 1073741824:
    }
    while (true)
    {
      this.mLayoutWidget.setMinWidth(0);
      this.mLayoutWidget.setMinHeight(0);
      this.mLayoutWidget.setHorizontalDimensionBehaviour(localDimensionBehaviour1);
      this.mLayoutWidget.setWidth(i2);
      this.mLayoutWidget.setVerticalDimensionBehaviour(localDimensionBehaviour2);
      this.mLayoutWidget.setHeight(i3);
      this.mLayoutWidget.setMinWidth(this.mMinWidth - getPaddingLeft() - getPaddingRight());
      this.mLayoutWidget.setMinHeight(this.mMinHeight - getPaddingTop() - getPaddingBottom());
      return;
      localDimensionBehaviour1 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
      i2 = j;
      break;
      localDimensionBehaviour1 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
      i2 = 0;
      break;
      i2 = Math.min(this.mMaxWidth, j) - i1;
      break;
      localDimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
      i3 = m;
      continue;
      localDimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
      i3 = 0;
      continue;
      i3 = Math.min(this.mMaxHeight, m) - n;
    }
  }

  private void updateHierarchy()
  {
    int i = getChildCount();
    for (int j = 0; ; j++)
    {
      int k = 0;
      if (j < i)
      {
        if (!getChildAt(j).isLayoutRequested())
          continue;
        k = 1;
      }
      if (k != 0)
      {
        this.mVariableDimensionsWidgets.clear();
        setChildrenConstraints();
      }
      return;
    }
  }

  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    super.addView(paramView, paramInt, paramLayoutParams);
    if (Build.VERSION.SDK_INT < 14)
      onViewAdded(paramView);
  }

  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }

  protected LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-2, -2);
  }

  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }

  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new LayoutParams(paramLayoutParams);
  }

  public int getMaxHeight()
  {
    return this.mMaxHeight;
  }

  public int getMaxWidth()
  {
    return this.mMaxWidth;
  }

  public int getMinHeight()
  {
    return this.mMinHeight;
  }

  public int getMinWidth()
  {
    return this.mMinWidth;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getChildCount();
    boolean bool = isInEditMode();
    int j = 0;
    if (j < i)
    {
      View localView = getChildAt(j);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      if ((localView.getVisibility() == 8) && (!localLayoutParams.isGuideline) && (!bool));
      while (true)
      {
        j++;
        break;
        ConstraintWidget localConstraintWidget = localLayoutParams.widget;
        int k = localConstraintWidget.getDrawX();
        int m = localConstraintWidget.getDrawY();
        localView.layout(k, m, k + localConstraintWidget.getWidth(), m + localConstraintWidget.getHeight());
      }
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = getPaddingLeft();
    int j = getPaddingTop();
    this.mLayoutWidget.setX(i);
    this.mLayoutWidget.setY(j);
    setSelfDimensionBehaviour(paramInt1, paramInt2);
    if (this.mDirtyHierarchy)
    {
      this.mDirtyHierarchy = false;
      updateHierarchy();
    }
    internalMeasureChildren(paramInt1, paramInt2);
    if (getChildCount() > 0)
      solveLinearSystem();
    int k = this.mVariableDimensionsWidgets.size();
    int m = j + getPaddingBottom();
    int n = i + getPaddingRight();
    int i1 = 0;
    if (k > 0)
    {
      int i10 = 0;
      int i11;
      int i12;
      label136: int i13;
      label139: ConstraintWidget localConstraintWidget;
      if (this.mLayoutWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
      {
        i11 = 1;
        if (this.mLayoutWidget.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
          break label180;
        i12 = 1;
        i13 = 0;
        if (i13 >= k)
          break label536;
        localConstraintWidget = (ConstraintWidget)this.mVariableDimensionsWidgets.get(i13);
        if (!(localConstraintWidget instanceof android.support.constraint.solver.widgets.Guideline))
          break label186;
      }
      label180: label186: View localView;
      do
      {
        i13++;
        break label139;
        i11 = 0;
        break;
        i12 = 0;
        break label136;
        localView = (View)localConstraintWidget.getCompanionWidget();
      }
      while ((localView == null) || (localView.getVisibility() == 8));
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      int i14;
      if (localLayoutParams.width == -2)
      {
        i14 = getChildMeasureSpec(paramInt1, n, localLayoutParams.width);
        label244: if (localLayoutParams.height != -2)
          break label520;
      }
      label520: for (int i15 = getChildMeasureSpec(paramInt2, m, localLayoutParams.height); ; i15 = View.MeasureSpec.makeMeasureSpec(localConstraintWidget.getHeight(), 1073741824))
      {
        localView.measure(i14, i15);
        int i16 = localView.getMeasuredWidth();
        int i17 = localView.getMeasuredHeight();
        if (i16 != localConstraintWidget.getWidth())
        {
          localConstraintWidget.setWidth(i16);
          if ((i11 != 0) && (localConstraintWidget.getRight() > this.mLayoutWidget.getWidth()))
          {
            int i20 = localConstraintWidget.getRight() + localConstraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin();
            this.mLayoutWidget.setWidth(Math.max(this.mMinWidth, i20));
          }
          i10 = 1;
        }
        if (i17 != localConstraintWidget.getHeight())
        {
          localConstraintWidget.setHeight(i17);
          if ((i12 != 0) && (localConstraintWidget.getBottom() > this.mLayoutWidget.getHeight()))
          {
            int i19 = localConstraintWidget.getBottom() + localConstraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin();
            this.mLayoutWidget.setHeight(Math.max(this.mMinHeight, i19));
          }
          i10 = 1;
        }
        if (localLayoutParams.needsBaseline)
        {
          int i18 = localView.getBaseline();
          if ((i18 != -1) && (i18 != localConstraintWidget.getBaselineDistance()))
          {
            localConstraintWidget.setBaselineDistance(i18);
            i10 = 1;
          }
        }
        if (Build.VERSION.SDK_INT < 11)
          break;
        i1 = combineMeasuredStates(i1, localView.getMeasuredState());
        break;
        i14 = View.MeasureSpec.makeMeasureSpec(localConstraintWidget.getWidth(), 1073741824);
        break label244;
      }
      label536: if (i10 != 0)
        solveLinearSystem();
    }
    int i2 = n + this.mLayoutWidget.getWidth();
    int i3 = m + this.mLayoutWidget.getHeight();
    if (Build.VERSION.SDK_INT >= 11)
    {
      int i4 = resolveSizeAndState(i2, paramInt1, i1);
      int i5 = resolveSizeAndState(i3, paramInt2, i1 << 16);
      int i6 = Math.min(this.mMaxWidth, i4);
      int i7 = Math.min(this.mMaxHeight, i5);
      int i8 = i6 & 0xFFFFFF;
      int i9 = i7 & 0xFFFFFF;
      if (this.mLayoutWidget.isWidthMeasuredTooSmall())
        i8 |= 16777216;
      if (this.mLayoutWidget.isHeightMeasuredTooSmall())
        i9 |= 16777216;
      setMeasuredDimension(i8, i9);
      return;
    }
    setMeasuredDimension(i2, i3);
  }

  public void onViewAdded(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 14)
      super.onViewAdded(paramView);
    ConstraintWidget localConstraintWidget = getViewWidget(paramView);
    LayoutParams localLayoutParams;
    if (((paramView instanceof Guideline)) && (!(localConstraintWidget instanceof android.support.constraint.solver.widgets.Guideline)))
    {
      localLayoutParams = (LayoutParams)paramView.getLayoutParams();
      localLayoutParams.widget = new android.support.constraint.solver.widgets.Guideline();
      localLayoutParams.isGuideline = true;
      ((android.support.constraint.solver.widgets.Guideline)localLayoutParams.widget).setOrientation(localLayoutParams.orientation);
    }
    this.mChildrenByIds.put(paramView.getId(), paramView);
    this.mDirtyHierarchy = true;
  }

  public void onViewRemoved(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 14)
      super.onViewRemoved(paramView);
    this.mChildrenByIds.remove(paramView.getId());
    this.mLayoutWidget.remove(getViewWidget(paramView));
    this.mDirtyHierarchy = true;
  }

  public void removeView(View paramView)
  {
    super.removeView(paramView);
    if (Build.VERSION.SDK_INT < 14)
      onViewRemoved(paramView);
  }

  public void requestLayout()
  {
    super.requestLayout();
    this.mDirtyHierarchy = true;
  }

  public void setConstraintSet(ConstraintSet paramConstraintSet)
  {
    this.mConstraintSet = paramConstraintSet;
  }

  public void setId(int paramInt)
  {
    this.mChildrenByIds.remove(getId());
    super.setId(paramInt);
    this.mChildrenByIds.put(getId(), this);
  }

  public void setMaxHeight(int paramInt)
  {
    if (paramInt == this.mMaxHeight)
      return;
    this.mMaxHeight = paramInt;
    requestLayout();
  }

  public void setMaxWidth(int paramInt)
  {
    if (paramInt == this.mMaxWidth)
      return;
    this.mMaxWidth = paramInt;
    requestLayout();
  }

  public void setMinHeight(int paramInt)
  {
    if (paramInt == this.mMinHeight)
      return;
    this.mMinHeight = paramInt;
    requestLayout();
  }

  public void setMinWidth(int paramInt)
  {
    if (paramInt == this.mMinWidth)
      return;
    this.mMinWidth = paramInt;
    requestLayout();
  }

  public void setOptimizationLevel(int paramInt)
  {
    this.mLayoutWidget.setOptimizationLevel(paramInt);
  }

  protected void solveLinearSystem()
  {
    this.mLayoutWidget.layout();
  }

  public static class LayoutParams extends ViewGroup.MarginLayoutParams
  {
    public static final int BASELINE = 5;
    public static final int BOTTOM = 4;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    public static final int END = 7;
    public static final int HORIZONTAL = 0;
    public static final int LEFT = 1;
    public static final int MATCH_CONSTRAINT = 0;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    public static final int PARENT_ID = 0;
    public static final int RIGHT = 2;
    public static final int START = 6;
    public static final int TOP = 3;
    public static final int UNSET = -1;
    public static final int VERTICAL = 1;
    public int baselineToBaseline = -1;
    public int bottomToBottom = -1;
    public int bottomToTop = -1;
    public String dimensionRatio = null;
    int dimensionRatioSide = 1;
    float dimensionRatioValue = 0.0F;
    public int editorAbsoluteX = -1;
    public int editorAbsoluteY = -1;
    public int endToEnd = -1;
    public int endToStart = -1;
    public int goneBottomMargin = -1;
    public int goneEndMargin = -1;
    public int goneLeftMargin = -1;
    public int goneRightMargin = -1;
    public int goneStartMargin = -1;
    public int goneTopMargin = -1;
    public int guideBegin = -1;
    public int guideEnd = -1;
    public float guidePercent = -1.0F;
    public float horizontalBias = 0.5F;
    public int horizontalChainStyle = 0;
    boolean horizontalDimensionFixed = true;
    public float horizontalWeight = 0.0F;
    boolean isGuideline = false;
    public int leftToLeft = -1;
    public int leftToRight = -1;
    public int matchConstraintDefaultHeight = 0;
    public int matchConstraintDefaultWidth = 0;
    public int matchConstraintMaxHeight = 0;
    public int matchConstraintMaxWidth = 0;
    public int matchConstraintMinHeight = 0;
    public int matchConstraintMinWidth = 0;
    boolean needsBaseline = false;
    public int orientation = -1;
    int resolveGoneLeftMargin = -1;
    int resolveGoneRightMargin = -1;
    float resolvedHorizontalBias = 0.5F;
    int resolvedLeftToLeft = -1;
    int resolvedLeftToRight = -1;
    int resolvedRightToLeft = -1;
    int resolvedRightToRight = -1;
    public int rightToLeft = -1;
    public int rightToRight = -1;
    public int startToEnd = -1;
    public int startToStart = -1;
    public int topToBottom = -1;
    public int topToTop = -1;
    public float verticalBias = 0.5F;
    public int verticalChainStyle = 0;
    boolean verticalDimensionFixed = true;
    public float verticalWeight = 0.0F;
    ConstraintWidget widget = new ConstraintWidget();

    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }

    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_Layout);
      int i = localTypedArray.getIndexCount();
      int j = 0;
      if (j < i)
      {
        int k = localTypedArray.getIndex(j);
        if (k == R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf)
        {
          this.leftToLeft = localTypedArray.getResourceId(k, this.leftToLeft);
          if (this.leftToLeft == -1)
            this.leftToLeft = localTypedArray.getInt(k, -1);
        }
        while (true)
        {
          j++;
          break;
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf)
          {
            this.leftToRight = localTypedArray.getResourceId(k, this.leftToRight);
            if (this.leftToRight != -1)
              continue;
            this.leftToRight = localTypedArray.getInt(k, -1);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf)
          {
            this.rightToLeft = localTypedArray.getResourceId(k, this.rightToLeft);
            if (this.rightToLeft != -1)
              continue;
            this.rightToLeft = localTypedArray.getInt(k, -1);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf)
          {
            this.rightToRight = localTypedArray.getResourceId(k, this.rightToRight);
            if (this.rightToRight != -1)
              continue;
            this.rightToRight = localTypedArray.getInt(k, -1);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf)
          {
            this.topToTop = localTypedArray.getResourceId(k, this.topToTop);
            if (this.topToTop != -1)
              continue;
            this.topToTop = localTypedArray.getInt(k, -1);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf)
          {
            this.topToBottom = localTypedArray.getResourceId(k, this.topToBottom);
            if (this.topToBottom != -1)
              continue;
            this.topToBottom = localTypedArray.getInt(k, -1);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf)
          {
            this.bottomToTop = localTypedArray.getResourceId(k, this.bottomToTop);
            if (this.bottomToTop != -1)
              continue;
            this.bottomToTop = localTypedArray.getInt(k, -1);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf)
          {
            this.bottomToBottom = localTypedArray.getResourceId(k, this.bottomToBottom);
            if (this.bottomToBottom != -1)
              continue;
            this.bottomToBottom = localTypedArray.getInt(k, -1);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf)
          {
            this.baselineToBaseline = localTypedArray.getResourceId(k, this.baselineToBaseline);
            if (this.baselineToBaseline != -1)
              continue;
            this.baselineToBaseline = localTypedArray.getInt(k, -1);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_editor_absoluteX)
          {
            this.editorAbsoluteX = localTypedArray.getDimensionPixelOffset(k, this.editorAbsoluteX);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_editor_absoluteY)
          {
            this.editorAbsoluteY = localTypedArray.getDimensionPixelOffset(k, this.editorAbsoluteY);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintGuide_begin)
          {
            this.guideBegin = localTypedArray.getDimensionPixelOffset(k, this.guideBegin);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintGuide_end)
          {
            this.guideEnd = localTypedArray.getDimensionPixelOffset(k, this.guideEnd);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintGuide_percent)
          {
            this.guidePercent = localTypedArray.getFloat(k, this.guidePercent);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_android_orientation)
          {
            this.orientation = localTypedArray.getInt(k, this.orientation);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf)
          {
            this.startToEnd = localTypedArray.getResourceId(k, this.startToEnd);
            if (this.startToEnd != -1)
              continue;
            this.startToEnd = localTypedArray.getInt(k, -1);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf)
          {
            this.startToStart = localTypedArray.getResourceId(k, this.startToStart);
            if (this.startToStart != -1)
              continue;
            this.startToStart = localTypedArray.getInt(k, -1);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf)
          {
            this.endToStart = localTypedArray.getResourceId(k, this.endToStart);
            if (this.endToStart != -1)
              continue;
            this.endToStart = localTypedArray.getInt(k, -1);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf)
          {
            this.endToEnd = localTypedArray.getResourceId(k, this.endToEnd);
            if (this.endToEnd != -1)
              continue;
            this.endToEnd = localTypedArray.getInt(k, -1);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_goneMarginLeft)
          {
            this.goneLeftMargin = localTypedArray.getDimensionPixelSize(k, this.goneLeftMargin);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_goneMarginTop)
          {
            this.goneTopMargin = localTypedArray.getDimensionPixelSize(k, this.goneTopMargin);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_goneMarginRight)
          {
            this.goneRightMargin = localTypedArray.getDimensionPixelSize(k, this.goneRightMargin);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_goneMarginBottom)
          {
            this.goneBottomMargin = localTypedArray.getDimensionPixelSize(k, this.goneBottomMargin);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_goneMarginStart)
          {
            this.goneStartMargin = localTypedArray.getDimensionPixelSize(k, this.goneStartMargin);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_goneMarginEnd)
          {
            this.goneEndMargin = localTypedArray.getDimensionPixelSize(k, this.goneEndMargin);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias)
          {
            this.horizontalBias = localTypedArray.getFloat(k, this.horizontalBias);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintVertical_bias)
          {
            this.verticalBias = localTypedArray.getFloat(k, this.verticalBias);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio)
          {
            this.dimensionRatio = localTypedArray.getString(k);
            this.dimensionRatioValue = (0.0F / 0.0F);
            this.dimensionRatioSide = -1;
            if (this.dimensionRatio == null)
              continue;
            int m = this.dimensionRatio.length();
            int n = this.dimensionRatio.indexOf(',');
            String str4;
            if ((n > 0) && (n < m - 1))
            {
              str4 = this.dimensionRatio.substring(0, n);
              if (str4.equalsIgnoreCase("W"))
                this.dimensionRatioSide = 0;
            }
            label1333: float f1;
            float f2;
            for (int i1 = n + 1; ; i1 = 0)
            {
              while (true)
              {
                int i2 = this.dimensionRatio.indexOf(':');
                if ((i2 < 0) || (i2 >= m - 1))
                  break label1506;
                String str2 = this.dimensionRatio.substring(i1, i2);
                String str3 = this.dimensionRatio.substring(i2 + 1);
                if ((str2.length() <= 0) || (str3.length() <= 0))
                  break;
                try
                {
                  f1 = Float.parseFloat(str2);
                  f2 = Float.parseFloat(str3);
                  if ((f1 <= 0.0F) || (f2 <= 0.0F))
                    break;
                  if (this.dimensionRatioSide != 1)
                    break label1487;
                  this.dimensionRatioValue = Math.abs(f2 / f1);
                }
                catch (NumberFormatException localNumberFormatException2)
                {
                }
              }
              break;
              if (!str4.equalsIgnoreCase("H"))
                break label1333;
              this.dimensionRatioSide = 1;
              break label1333;
            }
            label1487: float f3 = f1 / f2;
            this.dimensionRatioValue = Math.abs(f3);
            continue;
            label1506: String str1 = this.dimensionRatio.substring(i1);
            if (str1.length() <= 0)
              continue;
            try
            {
              this.dimensionRatioValue = Float.parseFloat(str1);
            }
            catch (NumberFormatException localNumberFormatException1)
            {
            }
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight)
          {
            this.horizontalWeight = localTypedArray.getFloat(k, 0.0F);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintVertical_weight)
          {
            this.verticalWeight = localTypedArray.getFloat(k, 0.0F);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle)
          {
            this.horizontalChainStyle = localTypedArray.getInt(k, 0);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle)
          {
            this.verticalChainStyle = localTypedArray.getInt(k, 0);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintWidth_default)
          {
            this.matchConstraintDefaultWidth = localTypedArray.getInt(k, 0);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default)
          {
            this.matchConstraintDefaultHeight = localTypedArray.getInt(k, 0);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintWidth_min)
          {
            this.matchConstraintMinWidth = localTypedArray.getDimensionPixelSize(k, this.matchConstraintMinWidth);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintWidth_max)
          {
            this.matchConstraintMaxWidth = localTypedArray.getDimensionPixelSize(k, this.matchConstraintMaxWidth);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintHeight_min)
          {
            this.matchConstraintMinHeight = localTypedArray.getDimensionPixelSize(k, this.matchConstraintMinHeight);
            continue;
          }
          if (k == R.styleable.ConstraintLayout_Layout_layout_constraintHeight_max)
          {
            this.matchConstraintMaxHeight = localTypedArray.getDimensionPixelSize(k, this.matchConstraintMaxHeight);
            continue;
          }
          if ((k == R.styleable.ConstraintLayout_Layout_layout_constraintLeft_creator) || (k == R.styleable.ConstraintLayout_Layout_layout_constraintTop_creator) || (k == R.styleable.ConstraintLayout_Layout_layout_constraintRight_creator) || (k == R.styleable.ConstraintLayout_Layout_layout_constraintBottom_creator) || (k != R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator))
            continue;
        }
      }
      localTypedArray.recycle();
      validate();
    }

    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
      this.guideBegin = paramLayoutParams.guideBegin;
      this.guideEnd = paramLayoutParams.guideEnd;
      this.guidePercent = paramLayoutParams.guidePercent;
      this.leftToLeft = paramLayoutParams.leftToLeft;
      this.leftToRight = paramLayoutParams.leftToRight;
      this.rightToLeft = paramLayoutParams.rightToLeft;
      this.rightToRight = paramLayoutParams.rightToRight;
      this.topToTop = paramLayoutParams.topToTop;
      this.topToBottom = paramLayoutParams.topToBottom;
      this.bottomToTop = paramLayoutParams.bottomToTop;
      this.bottomToBottom = paramLayoutParams.bottomToBottom;
      this.baselineToBaseline = paramLayoutParams.baselineToBaseline;
      this.startToEnd = paramLayoutParams.startToEnd;
      this.startToStart = paramLayoutParams.startToStart;
      this.endToStart = paramLayoutParams.endToStart;
      this.endToEnd = paramLayoutParams.endToEnd;
      this.goneLeftMargin = paramLayoutParams.goneLeftMargin;
      this.goneTopMargin = paramLayoutParams.goneTopMargin;
      this.goneRightMargin = paramLayoutParams.goneRightMargin;
      this.goneBottomMargin = paramLayoutParams.goneBottomMargin;
      this.goneStartMargin = paramLayoutParams.goneStartMargin;
      this.goneEndMargin = paramLayoutParams.goneEndMargin;
      this.horizontalBias = paramLayoutParams.horizontalBias;
      this.verticalBias = paramLayoutParams.verticalBias;
      this.dimensionRatio = paramLayoutParams.dimensionRatio;
      this.dimensionRatioValue = paramLayoutParams.dimensionRatioValue;
      this.dimensionRatioSide = paramLayoutParams.dimensionRatioSide;
      this.horizontalWeight = paramLayoutParams.horizontalWeight;
      this.verticalWeight = paramLayoutParams.verticalWeight;
      this.horizontalChainStyle = paramLayoutParams.horizontalChainStyle;
      this.verticalChainStyle = paramLayoutParams.verticalChainStyle;
      this.matchConstraintDefaultWidth = paramLayoutParams.matchConstraintDefaultWidth;
      this.matchConstraintDefaultHeight = paramLayoutParams.matchConstraintDefaultHeight;
      this.matchConstraintMinWidth = paramLayoutParams.matchConstraintMinWidth;
      this.matchConstraintMaxWidth = paramLayoutParams.matchConstraintMaxWidth;
      this.matchConstraintMinHeight = paramLayoutParams.matchConstraintMinHeight;
      this.matchConstraintMaxHeight = paramLayoutParams.matchConstraintMaxHeight;
      this.editorAbsoluteX = paramLayoutParams.editorAbsoluteX;
      this.editorAbsoluteY = paramLayoutParams.editorAbsoluteY;
      this.orientation = paramLayoutParams.orientation;
      this.horizontalDimensionFixed = paramLayoutParams.horizontalDimensionFixed;
      this.verticalDimensionFixed = paramLayoutParams.verticalDimensionFixed;
      this.needsBaseline = paramLayoutParams.needsBaseline;
      this.isGuideline = paramLayoutParams.isGuideline;
      this.resolvedLeftToLeft = paramLayoutParams.resolvedLeftToLeft;
      this.resolvedLeftToRight = paramLayoutParams.resolvedLeftToRight;
      this.resolvedRightToLeft = paramLayoutParams.resolvedRightToLeft;
      this.resolvedRightToRight = paramLayoutParams.resolvedRightToRight;
      this.resolveGoneLeftMargin = paramLayoutParams.resolveGoneLeftMargin;
      this.resolveGoneRightMargin = paramLayoutParams.resolveGoneRightMargin;
      this.resolvedHorizontalBias = paramLayoutParams.resolvedHorizontalBias;
      this.widget = paramLayoutParams.widget;
    }

    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }

    @TargetApi(17)
    public void resolveLayoutDirection(int paramInt)
    {
      int i = 1;
      super.resolveLayoutDirection(paramInt);
      this.resolvedRightToLeft = -1;
      this.resolvedRightToRight = -1;
      this.resolvedLeftToLeft = -1;
      this.resolvedLeftToRight = -1;
      this.resolveGoneLeftMargin = -1;
      this.resolveGoneRightMargin = -1;
      this.resolveGoneLeftMargin = this.goneLeftMargin;
      this.resolveGoneRightMargin = this.goneRightMargin;
      this.resolvedHorizontalBias = this.horizontalBias;
      if (i == getLayoutDirection())
      {
        if (i == 0)
          break label252;
        if (this.startToEnd == -1)
          break label233;
        this.resolvedRightToLeft = this.startToEnd;
        label89: if (this.endToStart != -1)
          this.resolvedLeftToRight = this.endToStart;
        if (this.endToEnd != -1)
          this.resolvedLeftToLeft = this.endToEnd;
        if (this.goneStartMargin != -1)
          this.resolveGoneRightMargin = this.goneStartMargin;
        if (this.goneEndMargin != -1)
          this.resolveGoneLeftMargin = this.goneEndMargin;
        this.resolvedHorizontalBias = (1.0F - this.horizontalBias);
        label163: if ((this.endToStart == -1) && (this.endToEnd == -1))
        {
          if (this.rightToLeft == -1)
            break label351;
          this.resolvedRightToLeft = this.rightToLeft;
        }
        label195: if ((this.startToStart == -1) && (this.startToEnd == -1))
        {
          if (this.leftToLeft == -1)
            break label370;
          this.resolvedLeftToLeft = this.leftToLeft;
        }
      }
      label233: label252: 
      do
      {
        return;
        i = 0;
        break;
        if (this.startToStart == -1)
          break label89;
        this.resolvedRightToRight = this.startToStart;
        break label89;
        if (this.startToEnd != -1)
          this.resolvedLeftToRight = this.startToEnd;
        if (this.startToStart != -1)
          this.resolvedLeftToLeft = this.startToStart;
        if (this.endToStart != -1)
          this.resolvedRightToLeft = this.endToStart;
        if (this.endToEnd != -1)
          this.resolvedRightToRight = this.endToEnd;
        if (this.goneStartMargin != -1)
          this.resolveGoneLeftMargin = this.goneStartMargin;
        if (this.goneEndMargin == -1)
          break label163;
        this.resolveGoneRightMargin = this.goneEndMargin;
        break label163;
        if (this.rightToRight == -1)
          break label195;
        this.resolvedRightToRight = this.rightToRight;
        break label195;
      }
      while (this.leftToRight == -1);
      label351: label370: this.resolvedLeftToRight = this.leftToRight;
    }

    public void validate()
    {
      this.isGuideline = false;
      this.horizontalDimensionFixed = true;
      this.verticalDimensionFixed = true;
      if ((this.width == 0) || (this.width == -1))
        this.horizontalDimensionFixed = false;
      if ((this.height == 0) || (this.height == -1))
        this.verticalDimensionFixed = false;
      if ((this.guidePercent != -1.0F) || (this.guideBegin != -1) || (this.guideEnd != -1))
      {
        this.isGuideline = true;
        this.horizontalDimensionFixed = true;
        this.verticalDimensionFixed = true;
        if (!(this.widget instanceof android.support.constraint.solver.widgets.Guideline))
          this.widget = new android.support.constraint.solver.widgets.Guideline();
        ((android.support.constraint.solver.widgets.Guideline)this.widget).setOrientation(this.orientation);
      }
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.ConstraintLayout
 * JD-Core Version:    0.6.0
 */