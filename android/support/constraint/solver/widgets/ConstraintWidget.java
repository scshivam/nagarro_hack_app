package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.Cache;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import java.util.ArrayList;

public class ConstraintWidget
{
  private static final boolean AUTOTAG_CENTER = false;
  public static final int CHAIN_PACKED = 2;
  public static final int CHAIN_SPREAD = 0;
  public static final int CHAIN_SPREAD_INSIDE = 1;
  public static float DEFAULT_BIAS = 0.0F;
  protected static final int DIRECT = 2;
  public static final int GONE = 8;
  public static final int HORIZONTAL = 0;
  public static final int INVISIBLE = 4;
  public static final int MATCH_CONSTRAINT_SPREAD = 0;
  public static final int MATCH_CONSTRAINT_WRAP = 1;
  protected static final int SOLVER = 1;
  public static final int UNKNOWN = -1;
  public static final int VERTICAL = 1;
  public static final int VISIBLE;
  protected ArrayList<ConstraintAnchor> mAnchors = new ArrayList();
  ConstraintAnchor mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
  int mBaselineDistance = 0;
  ConstraintAnchor mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
  boolean mBottomHasCentered;
  ConstraintAnchor mCenter = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
  ConstraintAnchor mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
  ConstraintAnchor mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
  private Object mCompanionWidget;
  private int mContainerItemSkip = 0;
  private String mDebugName = null;
  protected float mDimensionRatio = 0.0F;
  protected int mDimensionRatioSide = -1;
  int mDistToBottom;
  int mDistToLeft;
  int mDistToRight;
  int mDistToTop;
  private int mDrawHeight = 0;
  private int mDrawWidth = 0;
  private int mDrawX = 0;
  private int mDrawY = 0;
  int mHeight = 0;
  float mHorizontalBiasPercent = DEFAULT_BIAS;
  boolean mHorizontalChainFixedPosition;
  int mHorizontalChainStyle = 0;
  DimensionBehaviour mHorizontalDimensionBehaviour = DimensionBehaviour.FIXED;
  ConstraintWidget mHorizontalNextWidget = null;
  public int mHorizontalResolution = -1;
  float mHorizontalWeight = 0.0F;
  boolean mHorizontalWrapVisited;
  ConstraintAnchor mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
  boolean mLeftHasCentered;
  int mMatchConstraintDefaultHeight = 0;
  int mMatchConstraintDefaultWidth = 0;
  int mMatchConstraintMaxHeight = 0;
  int mMatchConstraintMaxWidth = 0;
  int mMatchConstraintMinHeight = 0;
  int mMatchConstraintMinWidth = 0;
  protected int mMinHeight;
  protected int mMinWidth;
  protected int mOffsetX = 0;
  protected int mOffsetY = 0;
  ConstraintWidget mParent = null;
  ConstraintAnchor mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
  boolean mRightHasCentered;
  private int mSolverBottom = 0;
  private int mSolverLeft = 0;
  private int mSolverRight = 0;
  private int mSolverTop = 0;
  ConstraintAnchor mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
  boolean mTopHasCentered;
  private String mType = null;
  float mVerticalBiasPercent = DEFAULT_BIAS;
  boolean mVerticalChainFixedPosition;
  int mVerticalChainStyle = 0;
  DimensionBehaviour mVerticalDimensionBehaviour = DimensionBehaviour.FIXED;
  ConstraintWidget mVerticalNextWidget = null;
  public int mVerticalResolution = -1;
  float mVerticalWeight = 0.0F;
  boolean mVerticalWrapVisited;
  private int mVisibility = 0;
  int mWidth = 0;
  private int mWrapHeight;
  private int mWrapWidth;
  protected int mX = 0;
  protected int mY = 0;

  public ConstraintWidget()
  {
    addAnchors();
  }

  public ConstraintWidget(int paramInt1, int paramInt2)
  {
    this(0, 0, paramInt1, paramInt2);
  }

  public ConstraintWidget(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mX = paramInt1;
    this.mY = paramInt2;
    this.mWidth = paramInt3;
    this.mHeight = paramInt4;
    addAnchors();
    forceUpdateDrawPosition();
  }

  private void addAnchors()
  {
    this.mAnchors.add(this.mLeft);
    this.mAnchors.add(this.mTop);
    this.mAnchors.add(this.mRight);
    this.mAnchors.add(this.mBottom);
    this.mAnchors.add(this.mCenterX);
    this.mAnchors.add(this.mCenterY);
    this.mAnchors.add(this.mBaseline);
  }

  private void applyConstraints(LinearSystem paramLinearSystem, boolean paramBoolean1, boolean paramBoolean2, ConstraintAnchor paramConstraintAnchor1, ConstraintAnchor paramConstraintAnchor2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, boolean paramBoolean3, boolean paramBoolean4, int paramInt5, int paramInt6, int paramInt7)
  {
    SolverVariable localSolverVariable1 = paramLinearSystem.createObjectVariable(paramConstraintAnchor1);
    SolverVariable localSolverVariable2 = paramLinearSystem.createObjectVariable(paramConstraintAnchor2);
    SolverVariable localSolverVariable3 = paramLinearSystem.createObjectVariable(paramConstraintAnchor1.getTarget());
    SolverVariable localSolverVariable4 = paramLinearSystem.createObjectVariable(paramConstraintAnchor2.getTarget());
    int i = paramConstraintAnchor1.getMargin();
    int j = paramConstraintAnchor2.getMargin();
    if (this.mVisibility == 8)
    {
      paramInt3 = 0;
      paramBoolean2 = true;
    }
    if ((localSolverVariable3 == null) && (localSolverVariable4 == null))
    {
      paramLinearSystem.addConstraint(paramLinearSystem.createRow().createRowEquals(localSolverVariable1, paramInt1));
      if (!paramBoolean3)
      {
        if (!paramBoolean1)
          break label116;
        paramLinearSystem.addConstraint(LinearSystem.createRowEquals(paramLinearSystem, localSolverVariable2, localSolverVariable1, paramInt4, true));
      }
    }
    label116: label631: 
    do
    {
      while (true)
      {
        return;
        if (paramBoolean2)
        {
          paramLinearSystem.addConstraint(LinearSystem.createRowEquals(paramLinearSystem, localSolverVariable2, localSolverVariable1, paramInt3, false));
          return;
        }
        paramLinearSystem.addConstraint(paramLinearSystem.createRow().createRowEquals(localSolverVariable2, paramInt2));
        return;
        if ((localSolverVariable3 != null) && (localSolverVariable4 == null))
        {
          paramLinearSystem.addConstraint(paramLinearSystem.createRow().createRowEquals(localSolverVariable1, localSolverVariable3, i));
          if (paramBoolean1)
          {
            paramLinearSystem.addConstraint(LinearSystem.createRowEquals(paramLinearSystem, localSolverVariable2, localSolverVariable1, paramInt4, true));
            return;
          }
          if (paramBoolean3)
            continue;
          if (paramBoolean2)
          {
            paramLinearSystem.addConstraint(paramLinearSystem.createRow().createRowEquals(localSolverVariable2, localSolverVariable1, paramInt3));
            return;
          }
          paramLinearSystem.addConstraint(paramLinearSystem.createRow().createRowEquals(localSolverVariable2, paramInt2));
          return;
        }
        if ((localSolverVariable3 == null) && (localSolverVariable4 != null))
        {
          paramLinearSystem.addConstraint(paramLinearSystem.createRow().createRowEquals(localSolverVariable2, localSolverVariable4, j * -1));
          if (paramBoolean1)
          {
            paramLinearSystem.addConstraint(LinearSystem.createRowEquals(paramLinearSystem, localSolverVariable2, localSolverVariable1, paramInt4, true));
            return;
          }
          if (paramBoolean3)
            continue;
          if (paramBoolean2)
          {
            paramLinearSystem.addConstraint(paramLinearSystem.createRow().createRowEquals(localSolverVariable2, localSolverVariable1, paramInt3));
            return;
          }
          paramLinearSystem.addConstraint(paramLinearSystem.createRow().createRowEquals(localSolverVariable1, paramInt1));
          return;
        }
        if (!paramBoolean2)
          break;
        if (paramBoolean1)
          paramLinearSystem.addConstraint(LinearSystem.createRowEquals(paramLinearSystem, localSolverVariable2, localSolverVariable1, paramInt4, true));
        while (paramConstraintAnchor1.getStrength() != paramConstraintAnchor2.getStrength())
        {
          if (paramConstraintAnchor1.getStrength() == ConstraintAnchor.Strength.STRONG)
          {
            paramLinearSystem.addConstraint(paramLinearSystem.createRow().createRowEquals(localSolverVariable1, localSolverVariable3, i));
            SolverVariable localSolverVariable6 = paramLinearSystem.createSlackVariable();
            ArrayRow localArrayRow2 = paramLinearSystem.createRow();
            localArrayRow2.createRowLowerThan(localSolverVariable2, localSolverVariable4, localSolverVariable6, j * -1);
            paramLinearSystem.addConstraint(localArrayRow2);
            return;
            paramLinearSystem.addConstraint(paramLinearSystem.createRow().createRowEquals(localSolverVariable2, localSolverVariable1, paramInt3));
            continue;
          }
          SolverVariable localSolverVariable5 = paramLinearSystem.createSlackVariable();
          ArrayRow localArrayRow1 = paramLinearSystem.createRow();
          localArrayRow1.createRowGreaterThan(localSolverVariable1, localSolverVariable3, localSolverVariable5, i);
          paramLinearSystem.addConstraint(localArrayRow1);
          paramLinearSystem.addConstraint(paramLinearSystem.createRow().createRowEquals(localSolverVariable2, localSolverVariable4, j * -1));
          return;
        }
        if (localSolverVariable3 == localSolverVariable4)
        {
          paramLinearSystem.addConstraint(LinearSystem.createRowCentering(paramLinearSystem, localSolverVariable1, localSolverVariable3, 0, 0.5F, localSolverVariable4, localSolverVariable2, 0, true));
          return;
        }
        if (paramBoolean4)
          continue;
        boolean bool1;
        if (paramConstraintAnchor1.getConnectionType() != ConstraintAnchor.ConnectionType.STRICT)
        {
          bool1 = true;
          paramLinearSystem.addConstraint(LinearSystem.createRowGreaterThan(paramLinearSystem, localSolverVariable1, localSolverVariable3, i, bool1));
          if (paramConstraintAnchor2.getConnectionType() == ConstraintAnchor.ConnectionType.STRICT)
            break label631;
        }
        for (boolean bool2 = true; ; bool2 = false)
        {
          paramLinearSystem.addConstraint(LinearSystem.createRowLowerThan(paramLinearSystem, localSolverVariable2, localSolverVariable4, j * -1, bool2));
          paramLinearSystem.addConstraint(LinearSystem.createRowCentering(paramLinearSystem, localSolverVariable1, localSolverVariable3, i, paramFloat, localSolverVariable4, localSolverVariable2, j, false));
          return;
          bool1 = false;
          break;
        }
      }
      if (!paramBoolean3)
        continue;
      paramLinearSystem.addGreaterThan(localSolverVariable1, localSolverVariable3, i, 3);
      paramLinearSystem.addLowerThan(localSolverVariable2, localSolverVariable4, j * -1, 3);
      paramLinearSystem.addConstraint(LinearSystem.createRowCentering(paramLinearSystem, localSolverVariable1, localSolverVariable3, i, paramFloat, localSolverVariable4, localSolverVariable2, j, true));
      return;
    }
    while (paramBoolean4);
    if (paramInt5 == 1)
    {
      if (paramInt6 > paramInt3)
        paramInt3 = paramInt6;
      if (paramInt7 > 0)
      {
        if (paramInt7 >= paramInt3)
          break label783;
        paramInt3 = paramInt7;
      }
      while (true)
      {
        paramLinearSystem.addEquality(localSolverVariable2, localSolverVariable1, paramInt3, 3);
        paramLinearSystem.addGreaterThan(localSolverVariable1, localSolverVariable3, i, 2);
        paramLinearSystem.addLowerThan(localSolverVariable2, localSolverVariable4, -j, 2);
        paramLinearSystem.addCentering(localSolverVariable1, localSolverVariable3, i, paramFloat, localSolverVariable4, localSolverVariable2, j, 4);
        return;
        label783: paramLinearSystem.addLowerThan(localSolverVariable2, localSolverVariable1, paramInt7, 3);
      }
    }
    if ((paramInt6 == 0) && (paramInt7 == 0))
    {
      paramLinearSystem.addConstraint(paramLinearSystem.createRow().createRowEquals(localSolverVariable1, localSolverVariable3, i));
      paramLinearSystem.addConstraint(paramLinearSystem.createRow().createRowEquals(localSolverVariable2, localSolverVariable4, j * -1));
      return;
    }
    if (paramInt7 > 0)
      paramLinearSystem.addLowerThan(localSolverVariable2, localSolverVariable1, paramInt7, 3);
    paramLinearSystem.addGreaterThan(localSolverVariable1, localSolverVariable3, i, 2);
    paramLinearSystem.addLowerThan(localSolverVariable2, localSolverVariable4, -j, 2);
    paramLinearSystem.addCentering(localSolverVariable1, localSolverVariable3, i, paramFloat, localSolverVariable4, localSolverVariable2, j, 4);
  }

  public void addToSolver(LinearSystem paramLinearSystem)
  {
    addToSolver(paramLinearSystem, 2147483647);
  }

  public void addToSolver(LinearSystem paramLinearSystem, int paramInt)
  {
    SolverVariable localSolverVariable1;
    if (paramInt != 2147483647)
    {
      int i23 = this.mLeft.mGroup;
      localSolverVariable1 = null;
      if (i23 != paramInt);
    }
    else
    {
      localSolverVariable1 = paramLinearSystem.createObjectVariable(this.mLeft);
    }
    SolverVariable localSolverVariable2;
    if (paramInt != 2147483647)
    {
      int i22 = this.mRight.mGroup;
      localSolverVariable2 = null;
      if (i22 != paramInt);
    }
    else
    {
      localSolverVariable2 = paramLinearSystem.createObjectVariable(this.mRight);
    }
    SolverVariable localSolverVariable3;
    if (paramInt != 2147483647)
    {
      int i21 = this.mTop.mGroup;
      localSolverVariable3 = null;
      if (i21 != paramInt);
    }
    else
    {
      localSolverVariable3 = paramLinearSystem.createObjectVariable(this.mTop);
    }
    SolverVariable localSolverVariable4;
    if (paramInt != 2147483647)
    {
      int i20 = this.mBottom.mGroup;
      localSolverVariable4 = null;
      if (i20 != paramInt);
    }
    else
    {
      localSolverVariable4 = paramLinearSystem.createObjectVariable(this.mBottom);
    }
    SolverVariable localSolverVariable5;
    if (paramInt != 2147483647)
    {
      int i19 = this.mBaseline.mGroup;
      localSolverVariable5 = null;
      if (i19 != paramInt);
    }
    else
    {
      localSolverVariable5 = paramLinearSystem.createObjectVariable(this.mBaseline);
    }
    ConstraintWidget localConstraintWidget = this.mParent;
    boolean bool1 = false;
    boolean bool2 = false;
    if (localConstraintWidget != null)
    {
      if ((this.mLeft.mTarget == null) || (this.mLeft.mTarget.mTarget != this.mLeft))
      {
        ConstraintAnchor localConstraintAnchor5 = this.mRight.mTarget;
        bool1 = false;
        if (localConstraintAnchor5 != null)
        {
          ConstraintAnchor localConstraintAnchor9 = this.mRight.mTarget.mTarget;
          ConstraintAnchor localConstraintAnchor10 = this.mRight;
          bool1 = false;
          if (localConstraintAnchor9 != localConstraintAnchor10);
        }
      }
      else
      {
        ((ConstraintWidgetContainer)this.mParent).addChain(this, 0);
        bool1 = true;
      }
      if ((this.mTop.mTarget == null) || (this.mTop.mTarget.mTarget != this.mTop))
      {
        ConstraintAnchor localConstraintAnchor6 = this.mBottom.mTarget;
        bool2 = false;
        if (localConstraintAnchor6 != null)
        {
          ConstraintAnchor localConstraintAnchor7 = this.mBottom.mTarget.mTarget;
          ConstraintAnchor localConstraintAnchor8 = this.mBottom;
          bool2 = false;
          if (localConstraintAnchor7 != localConstraintAnchor8);
        }
      }
      else
      {
        ((ConstraintWidgetContainer)this.mParent).addChain(this, 1);
        bool2 = true;
      }
      if ((this.mParent.getHorizontalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT) && (!bool1))
      {
        if ((this.mLeft.mTarget != null) && (this.mLeft.mTarget.mOwner == this.mParent))
          break label1183;
        SolverVariable localSolverVariable24 = paramLinearSystem.createObjectVariable(this.mParent.mLeft);
        ArrayRow localArrayRow4 = paramLinearSystem.createRow();
        SolverVariable localSolverVariable25 = paramLinearSystem.createSlackVariable();
        localArrayRow4.createRowGreaterThan(localSolverVariable1, localSolverVariable24, localSolverVariable25, 0);
        paramLinearSystem.addConstraint(localArrayRow4);
        break label1182;
        label452: if ((this.mRight.mTarget != null) && (this.mRight.mTarget.mOwner == this.mParent))
          break label1223;
        SolverVariable localSolverVariable26 = paramLinearSystem.createObjectVariable(this.mParent.mRight);
        ArrayRow localArrayRow5 = paramLinearSystem.createRow();
        SolverVariable localSolverVariable27 = paramLinearSystem.createSlackVariable();
        localArrayRow5.createRowGreaterThan(localSolverVariable26, localSolverVariable2, localSolverVariable27, 0);
        paramLinearSystem.addConstraint(localArrayRow5);
      }
      label523: if ((this.mParent.getVerticalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT) && (!bool2))
      {
        if ((this.mTop.mTarget != null) && (this.mTop.mTarget.mOwner == this.mParent))
          break label1263;
        SolverVariable localSolverVariable20 = paramLinearSystem.createObjectVariable(this.mParent.mTop);
        ArrayRow localArrayRow2 = paramLinearSystem.createRow();
        SolverVariable localSolverVariable21 = paramLinearSystem.createSlackVariable();
        localArrayRow2.createRowGreaterThan(localSolverVariable3, localSolverVariable20, localSolverVariable21, 0);
        paramLinearSystem.addConstraint(localArrayRow2);
        label612: if ((this.mBottom.mTarget != null) && (this.mBottom.mTarget.mOwner == this.mParent))
          break label1303;
        SolverVariable localSolverVariable22 = paramLinearSystem.createObjectVariable(this.mParent.mBottom);
        ArrayRow localArrayRow3 = paramLinearSystem.createRow();
        SolverVariable localSolverVariable23 = paramLinearSystem.createSlackVariable();
        localArrayRow3.createRowGreaterThan(localSolverVariable22, localSolverVariable4, localSolverVariable23, 0);
        paramLinearSystem.addConstraint(localArrayRow3);
      }
    }
    label683: int i = this.mWidth;
    int j = this.mMinWidth;
    if (i < j)
      i = this.mMinWidth;
    int k = this.mHeight;
    int m = this.mMinHeight;
    if (k < m)
      k = this.mMinHeight;
    boolean bool3;
    label746: boolean bool4;
    label759: int n;
    float f1;
    int i1;
    label957: boolean bool6;
    label976: boolean bool7;
    if (this.mHorizontalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT)
    {
      bool3 = true;
      if (this.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT)
        break label1349;
      bool4 = true;
      if ((!bool3) && (this.mLeft != null) && (this.mRight != null) && ((this.mLeft.mTarget == null) || (this.mRight.mTarget == null)))
        bool3 = true;
      if ((!bool4) && (this.mTop != null) && (this.mBottom != null) && ((this.mTop.mTarget == null) || (this.mBottom.mTarget == null)) && ((this.mBaselineDistance == 0) || ((this.mBaseline != null) && ((this.mTop.mTarget == null) || (this.mBaseline.mTarget == null)))))
        bool4 = true;
      n = this.mDimensionRatioSide;
      f1 = this.mDimensionRatio;
      boolean bool5 = this.mDimensionRatio < 0.0F;
      i1 = 0;
      if (bool5)
      {
        int i18 = this.mVisibility;
        i1 = 0;
        if (i18 != 8)
        {
          if ((this.mHorizontalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT) || (this.mVerticalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT))
            break label1385;
          i1 = 1;
          if ((!bool3) || (bool4))
            break label1355;
          n = 0;
        }
      }
      if ((i1 == 0) || ((n != 0) && (n != -1)))
        break label1476;
      bool6 = true;
      if ((this.mHorizontalDimensionBehaviour != DimensionBehaviour.WRAP_CONTENT) || (!(this instanceof ConstraintWidgetContainer)))
        break label1482;
      bool7 = true;
      label996: if ((this.mHorizontalResolution != 2) && ((paramInt == 2147483647) || ((this.mLeft.mGroup == paramInt) && (this.mRight.mGroup == paramInt))))
      {
        if ((!bool6) || (this.mLeft.mTarget == null) || (this.mRight.mTarget == null))
          break label1488;
        SolverVariable localSolverVariable16 = paramLinearSystem.createObjectVariable(this.mLeft);
        SolverVariable localSolverVariable17 = paramLinearSystem.createObjectVariable(this.mRight);
        SolverVariable localSolverVariable18 = paramLinearSystem.createObjectVariable(this.mLeft.getTarget());
        SolverVariable localSolverVariable19 = paramLinearSystem.createObjectVariable(this.mRight.getTarget());
        paramLinearSystem.addGreaterThan(localSolverVariable16, localSolverVariable18, this.mLeft.getMargin(), 3);
        paramLinearSystem.addLowerThan(localSolverVariable17, localSolverVariable19, -1 * this.mRight.getMargin(), 3);
        if (!bool1)
          paramLinearSystem.addCentering(localSolverVariable16, localSolverVariable18, this.mLeft.getMargin(), this.mHorizontalBiasPercent, localSolverVariable19, localSolverVariable17, this.mRight.getMargin(), 4);
      }
      label1174: if (this.mVerticalResolution != 2)
        break label1545;
    }
    label1182: label1183: label1223: label1263: ArrayRow localArrayRow1;
    label1303: label1349: label1355: label1488: label2014: label2278: 
    while (true)
    {
      return;
      if ((this.mLeft.mTarget == null) || (this.mLeft.mTarget.mOwner != this.mParent))
        break label452;
      this.mLeft.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
      break label452;
      if ((this.mRight.mTarget == null) || (this.mRight.mTarget.mOwner != this.mParent))
        break label523;
      this.mRight.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
      break label523;
      if ((this.mTop.mTarget == null) || (this.mTop.mTarget.mOwner != this.mParent))
        break label612;
      this.mTop.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
      break label612;
      if ((this.mBottom.mTarget == null) || (this.mBottom.mTarget.mOwner != this.mParent))
        break label683;
      this.mBottom.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
      break label683;
      bool3 = false;
      break label746;
      bool4 = false;
      break label759;
      if ((bool3) || (!bool4))
        break label957;
      n = 1;
      if (this.mDimensionRatioSide != -1)
        break label957;
      f1 = 1.0F / f1;
      break label957;
      label1385: if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT)
      {
        i = (int)(f1 * this.mHeight);
        bool3 = true;
        n = 0;
        i1 = 0;
        break label957;
      }
      DimensionBehaviour localDimensionBehaviour1 = this.mVerticalDimensionBehaviour;
      DimensionBehaviour localDimensionBehaviour2 = DimensionBehaviour.MATCH_CONSTRAINT;
      i1 = 0;
      if (localDimensionBehaviour1 != localDimensionBehaviour2)
        break label957;
      n = 1;
      if (this.mDimensionRatioSide == -1)
        f1 = 1.0F / f1;
      k = (int)(f1 * this.mWidth);
      bool4 = true;
      i1 = 0;
      break label957;
      bool6 = false;
      break label976;
      bool7 = false;
      break label996;
      applyConstraints(paramLinearSystem, bool7, bool3, this.mLeft, this.mRight, this.mX, i + this.mX, i, this.mMinWidth, this.mHorizontalBiasPercent, bool6, bool1, this.mMatchConstraintDefaultWidth, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth);
      break label1174;
      boolean bool8;
      boolean bool9;
      ConstraintAnchor localConstraintAnchor3;
      int i11;
      if ((this.mVerticalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) && ((this instanceof ConstraintWidgetContainer)))
      {
        bool8 = true;
        if ((i1 == 0) || ((n != 1) && (n != -1)))
          break label1909;
        bool9 = true;
        if (this.mBaselineDistance <= 0)
          break label2014;
        localConstraintAnchor3 = this.mBottom;
        if ((paramInt == 2147483647) || ((this.mBottom.mGroup == paramInt) && (this.mBaseline.mGroup == paramInt)))
        {
          int i10 = getBaselineDistance();
          paramLinearSystem.addEquality(localSolverVariable5, localSolverVariable3, i10, 5);
        }
        i11 = k;
        if (this.mBaseline.mTarget != null)
        {
          k = this.mBaselineDistance;
          localConstraintAnchor3 = this.mBaseline;
        }
        if ((paramInt == 2147483647) || ((this.mTop.mGroup == paramInt) && (localConstraintAnchor3.mGroup == paramInt)))
        {
          if ((!bool9) || (this.mTop.mTarget == null) || (this.mBottom.mTarget == null))
            break label1915;
          SolverVariable localSolverVariable12 = paramLinearSystem.createObjectVariable(this.mTop);
          SolverVariable localSolverVariable13 = paramLinearSystem.createObjectVariable(this.mBottom);
          SolverVariable localSolverVariable14 = paramLinearSystem.createObjectVariable(this.mTop.getTarget());
          SolverVariable localSolverVariable15 = paramLinearSystem.createObjectVariable(this.mBottom.getTarget());
          paramLinearSystem.addGreaterThan(localSolverVariable12, localSolverVariable14, this.mTop.getMargin(), 3);
          paramLinearSystem.addLowerThan(localSolverVariable13, localSolverVariable15, -1 * this.mBottom.getMargin(), 3);
          if (!bool2)
            paramLinearSystem.addCentering(localSolverVariable12, localSolverVariable14, this.mTop.getMargin(), this.mVerticalBiasPercent, localSolverVariable15, localSolverVariable13, this.mBottom.getMargin(), 4);
        }
      }
      while (true)
      {
        if (i1 == 0)
          break label2278;
        localArrayRow1 = paramLinearSystem.createRow();
        if ((paramInt != 2147483647) && ((this.mLeft.mGroup != paramInt) || (this.mRight.mGroup != paramInt)))
          break;
        if (n != 0)
          break label2280;
        paramLinearSystem.addConstraint(localArrayRow1.createRowDimensionRatio(localSolverVariable2, localSolverVariable1, localSolverVariable4, localSolverVariable3, f1));
        return;
        bool8 = false;
        break label1565;
        label1909: bool9 = false;
        break label1585;
        label1915: ConstraintAnchor localConstraintAnchor4 = this.mTop;
        int i12 = this.mY;
        int i13 = k + this.mY;
        int i14 = this.mMinHeight;
        float f3 = this.mVerticalBiasPercent;
        int i15 = this.mMatchConstraintDefaultHeight;
        int i16 = this.mMatchConstraintMinHeight;
        int i17 = this.mMatchConstraintMaxHeight;
        applyConstraints(paramLinearSystem, bool8, bool4, localConstraintAnchor4, localConstraintAnchor3, i12, i13, k, i14, f3, bool9, bool2, i15, i16, i17);
        paramLinearSystem.addEquality(localSolverVariable4, localSolverVariable3, i11, 5);
        continue;
        if ((paramInt != 2147483647) && ((this.mTop.mGroup != paramInt) || (this.mBottom.mGroup != paramInt)))
          continue;
        if ((bool9) && (this.mTop.mTarget != null) && (this.mBottom.mTarget != null))
        {
          SolverVariable localSolverVariable8 = paramLinearSystem.createObjectVariable(this.mTop);
          SolverVariable localSolverVariable9 = paramLinearSystem.createObjectVariable(this.mBottom);
          SolverVariable localSolverVariable10 = paramLinearSystem.createObjectVariable(this.mTop.getTarget());
          SolverVariable localSolverVariable11 = paramLinearSystem.createObjectVariable(this.mBottom.getTarget());
          paramLinearSystem.addGreaterThan(localSolverVariable8, localSolverVariable10, this.mTop.getMargin(), 3);
          paramLinearSystem.addLowerThan(localSolverVariable9, localSolverVariable11, -1 * this.mBottom.getMargin(), 3);
          if (bool2)
            continue;
          paramLinearSystem.addCentering(localSolverVariable8, localSolverVariable10, this.mTop.getMargin(), this.mVerticalBiasPercent, localSolverVariable11, localSolverVariable9, this.mBottom.getMargin(), 4);
          continue;
        }
        ConstraintAnchor localConstraintAnchor1 = this.mTop;
        ConstraintAnchor localConstraintAnchor2 = this.mBottom;
        int i2 = this.mY;
        int i3 = k + this.mY;
        int i4 = this.mMinHeight;
        float f2 = this.mVerticalBiasPercent;
        int i5 = this.mMatchConstraintDefaultHeight;
        int i6 = this.mMatchConstraintMinHeight;
        int i7 = this.mMatchConstraintMaxHeight;
        applyConstraints(paramLinearSystem, bool8, bool4, localConstraintAnchor1, localConstraintAnchor2, i2, i3, k, i4, f2, bool9, bool2, i5, i6, i7);
      }
    }
    label1476: label1482: label2280: if (n == 1)
    {
      paramLinearSystem.addConstraint(localArrayRow1.createRowDimensionRatio(localSolverVariable4, localSolverVariable3, localSolverVariable2, localSolverVariable1, f1));
      return;
    }
    label1545: label1565: label1585: if (this.mMatchConstraintMinWidth > 0)
    {
      int i9 = this.mMatchConstraintMinWidth;
      paramLinearSystem.addGreaterThan(localSolverVariable2, localSolverVariable1, i9, 3);
    }
    if (this.mMatchConstraintMinHeight > 0)
    {
      int i8 = this.mMatchConstraintMinHeight;
      paramLinearSystem.addGreaterThan(localSolverVariable4, localSolverVariable3, i8, 3);
    }
    localArrayRow1.createRowDimensionRatio(localSolverVariable2, localSolverVariable1, localSolverVariable4, localSolverVariable3, f1);
    SolverVariable localSolverVariable6 = paramLinearSystem.createErrorVariable();
    SolverVariable localSolverVariable7 = paramLinearSystem.createErrorVariable();
    localSolverVariable6.strength = 4;
    localSolverVariable7.strength = 4;
    localArrayRow1.addError(localSolverVariable6, localSolverVariable7);
    paramLinearSystem.addConstraint(localArrayRow1);
  }

  public void connect(ConstraintAnchor.Type paramType1, ConstraintWidget paramConstraintWidget, ConstraintAnchor.Type paramType2)
  {
    connect(paramType1, paramConstraintWidget, paramType2, 0, ConstraintAnchor.Strength.STRONG);
  }

  public void connect(ConstraintAnchor.Type paramType1, ConstraintWidget paramConstraintWidget, ConstraintAnchor.Type paramType2, int paramInt)
  {
    connect(paramType1, paramConstraintWidget, paramType2, paramInt, ConstraintAnchor.Strength.STRONG);
  }

  public void connect(ConstraintAnchor.Type paramType1, ConstraintWidget paramConstraintWidget, ConstraintAnchor.Type paramType2, int paramInt, ConstraintAnchor.Strength paramStrength)
  {
    connect(paramType1, paramConstraintWidget, paramType2, paramInt, paramStrength, 0);
  }

  public void connect(ConstraintAnchor.Type paramType1, ConstraintWidget paramConstraintWidget, ConstraintAnchor.Type paramType2, int paramInt1, ConstraintAnchor.Strength paramStrength, int paramInt2)
  {
    int i;
    int j;
    if (paramType1 == ConstraintAnchor.Type.CENTER)
      if (paramType2 == ConstraintAnchor.Type.CENTER)
      {
        ConstraintAnchor localConstraintAnchor16 = getAnchor(ConstraintAnchor.Type.LEFT);
        ConstraintAnchor localConstraintAnchor17 = getAnchor(ConstraintAnchor.Type.RIGHT);
        ConstraintAnchor localConstraintAnchor18 = getAnchor(ConstraintAnchor.Type.TOP);
        ConstraintAnchor localConstraintAnchor19 = getAnchor(ConstraintAnchor.Type.BOTTOM);
        if (localConstraintAnchor16 != null)
        {
          boolean bool4 = localConstraintAnchor16.isConnected();
          i = 0;
          if (bool4);
        }
        else
        {
          if (localConstraintAnchor17 == null)
            break label162;
          boolean bool3 = localConstraintAnchor17.isConnected();
          i = 0;
          if (!bool3)
            break label162;
        }
        if (localConstraintAnchor18 != null)
        {
          boolean bool2 = localConstraintAnchor18.isConnected();
          j = 0;
          if (bool2);
        }
        else
        {
          if (localConstraintAnchor19 == null)
            break label200;
          boolean bool1 = localConstraintAnchor19.isConnected();
          j = 0;
          if (!bool1)
            break label200;
        }
        label130: if ((i != 0) && (j != 0))
          getAnchor(ConstraintAnchor.Type.CENTER).connect(paramConstraintWidget.getAnchor(ConstraintAnchor.Type.CENTER), 0, paramInt2);
      }
    label162: label200: ConstraintAnchor localConstraintAnchor1;
    ConstraintAnchor localConstraintAnchor2;
    do
    {
      do
      {
        do
        {
          return;
          connect(ConstraintAnchor.Type.LEFT, paramConstraintWidget, ConstraintAnchor.Type.LEFT, 0, paramStrength, paramInt2);
          connect(ConstraintAnchor.Type.RIGHT, paramConstraintWidget, ConstraintAnchor.Type.RIGHT, 0, paramStrength, paramInt2);
          i = 1;
          break;
          connect(ConstraintAnchor.Type.TOP, paramConstraintWidget, ConstraintAnchor.Type.TOP, 0, paramStrength, paramInt2);
          connect(ConstraintAnchor.Type.BOTTOM, paramConstraintWidget, ConstraintAnchor.Type.BOTTOM, 0, paramStrength, paramInt2);
          j = 1;
          break label130;
          if (i == 0)
            continue;
          getAnchor(ConstraintAnchor.Type.CENTER_X).connect(paramConstraintWidget.getAnchor(ConstraintAnchor.Type.CENTER_X), 0, paramInt2);
          return;
        }
        while (j == 0);
        getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(paramConstraintWidget.getAnchor(ConstraintAnchor.Type.CENTER_Y), 0, paramInt2);
        return;
        if ((paramType2 != ConstraintAnchor.Type.LEFT) && (paramType2 != ConstraintAnchor.Type.RIGHT))
          continue;
        connect(ConstraintAnchor.Type.LEFT, paramConstraintWidget, paramType2, 0, paramStrength, paramInt2);
        connect(ConstraintAnchor.Type.RIGHT, paramConstraintWidget, paramType2, 0, paramStrength, paramInt2);
        getAnchor(ConstraintAnchor.Type.CENTER).connect(paramConstraintWidget.getAnchor(paramType2), 0, paramInt2);
        return;
      }
      while ((paramType2 != ConstraintAnchor.Type.TOP) && (paramType2 != ConstraintAnchor.Type.BOTTOM));
      connect(ConstraintAnchor.Type.TOP, paramConstraintWidget, paramType2, 0, paramStrength, paramInt2);
      connect(ConstraintAnchor.Type.BOTTOM, paramConstraintWidget, paramType2, 0, paramStrength, paramInt2);
      getAnchor(ConstraintAnchor.Type.CENTER).connect(paramConstraintWidget.getAnchor(paramType2), 0, paramInt2);
      return;
      if ((paramType1 == ConstraintAnchor.Type.CENTER_X) && ((paramType2 == ConstraintAnchor.Type.LEFT) || (paramType2 == ConstraintAnchor.Type.RIGHT)))
      {
        ConstraintAnchor localConstraintAnchor13 = getAnchor(ConstraintAnchor.Type.LEFT);
        ConstraintAnchor localConstraintAnchor14 = paramConstraintWidget.getAnchor(paramType2);
        ConstraintAnchor localConstraintAnchor15 = getAnchor(ConstraintAnchor.Type.RIGHT);
        localConstraintAnchor13.connect(localConstraintAnchor14, 0, paramInt2);
        localConstraintAnchor15.connect(localConstraintAnchor14, 0, paramInt2);
        getAnchor(ConstraintAnchor.Type.CENTER_X).connect(localConstraintAnchor14, 0, paramInt2);
        return;
      }
      if ((paramType1 == ConstraintAnchor.Type.CENTER_Y) && ((paramType2 == ConstraintAnchor.Type.TOP) || (paramType2 == ConstraintAnchor.Type.BOTTOM)))
      {
        ConstraintAnchor localConstraintAnchor12 = paramConstraintWidget.getAnchor(paramType2);
        getAnchor(ConstraintAnchor.Type.TOP).connect(localConstraintAnchor12, 0, paramInt2);
        getAnchor(ConstraintAnchor.Type.BOTTOM).connect(localConstraintAnchor12, 0, paramInt2);
        getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(localConstraintAnchor12, 0, paramInt2);
        return;
      }
      if ((paramType1 == ConstraintAnchor.Type.CENTER_X) && (paramType2 == ConstraintAnchor.Type.CENTER_X))
      {
        getAnchor(ConstraintAnchor.Type.LEFT).connect(paramConstraintWidget.getAnchor(ConstraintAnchor.Type.LEFT), 0, paramInt2);
        getAnchor(ConstraintAnchor.Type.RIGHT).connect(paramConstraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT), 0, paramInt2);
        getAnchor(ConstraintAnchor.Type.CENTER_X).connect(paramConstraintWidget.getAnchor(paramType2), 0, paramInt2);
        return;
      }
      if ((paramType1 == ConstraintAnchor.Type.CENTER_Y) && (paramType2 == ConstraintAnchor.Type.CENTER_Y))
      {
        getAnchor(ConstraintAnchor.Type.TOP).connect(paramConstraintWidget.getAnchor(ConstraintAnchor.Type.TOP), 0, paramInt2);
        getAnchor(ConstraintAnchor.Type.BOTTOM).connect(paramConstraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM), 0, paramInt2);
        getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(paramConstraintWidget.getAnchor(paramType2), 0, paramInt2);
        return;
      }
      localConstraintAnchor1 = getAnchor(paramType1);
      localConstraintAnchor2 = paramConstraintWidget.getAnchor(paramType2);
    }
    while (!localConstraintAnchor1.isValidConnection(localConstraintAnchor2));
    if (paramType1 == ConstraintAnchor.Type.BASELINE)
    {
      ConstraintAnchor localConstraintAnchor10 = getAnchor(ConstraintAnchor.Type.TOP);
      ConstraintAnchor localConstraintAnchor11 = getAnchor(ConstraintAnchor.Type.BOTTOM);
      if (localConstraintAnchor10 != null)
        localConstraintAnchor10.reset();
      if (localConstraintAnchor11 != null)
        localConstraintAnchor11.reset();
      paramInt1 = 0;
    }
    while (true)
    {
      localConstraintAnchor1.connect(localConstraintAnchor2, paramInt1, paramStrength, paramInt2);
      localConstraintAnchor2.getOwner().connectedTo(localConstraintAnchor1.getOwner());
      return;
      if ((paramType1 == ConstraintAnchor.Type.TOP) || (paramType1 == ConstraintAnchor.Type.BOTTOM))
      {
        ConstraintAnchor localConstraintAnchor3 = getAnchor(ConstraintAnchor.Type.BASELINE);
        if (localConstraintAnchor3 != null)
          localConstraintAnchor3.reset();
        ConstraintAnchor localConstraintAnchor4 = getAnchor(ConstraintAnchor.Type.CENTER);
        if (localConstraintAnchor4.getTarget() != localConstraintAnchor2)
          localConstraintAnchor4.reset();
        ConstraintAnchor localConstraintAnchor5 = getAnchor(paramType1).getOpposite();
        ConstraintAnchor localConstraintAnchor6 = getAnchor(ConstraintAnchor.Type.CENTER_Y);
        if (!localConstraintAnchor6.isConnected())
          continue;
        localConstraintAnchor5.reset();
        localConstraintAnchor6.reset();
        continue;
      }
      if ((paramType1 != ConstraintAnchor.Type.LEFT) && (paramType1 != ConstraintAnchor.Type.RIGHT))
        continue;
      ConstraintAnchor localConstraintAnchor7 = getAnchor(ConstraintAnchor.Type.CENTER);
      if (localConstraintAnchor7.getTarget() != localConstraintAnchor2)
        localConstraintAnchor7.reset();
      ConstraintAnchor localConstraintAnchor8 = getAnchor(paramType1).getOpposite();
      ConstraintAnchor localConstraintAnchor9 = getAnchor(ConstraintAnchor.Type.CENTER_X);
      if (!localConstraintAnchor9.isConnected())
        continue;
      localConstraintAnchor8.reset();
      localConstraintAnchor9.reset();
    }
  }

  public void connect(ConstraintAnchor paramConstraintAnchor1, ConstraintAnchor paramConstraintAnchor2, int paramInt)
  {
    connect(paramConstraintAnchor1, paramConstraintAnchor2, paramInt, ConstraintAnchor.Strength.STRONG, 0);
  }

  public void connect(ConstraintAnchor paramConstraintAnchor1, ConstraintAnchor paramConstraintAnchor2, int paramInt1, int paramInt2)
  {
    connect(paramConstraintAnchor1, paramConstraintAnchor2, paramInt1, ConstraintAnchor.Strength.STRONG, paramInt2);
  }

  public void connect(ConstraintAnchor paramConstraintAnchor1, ConstraintAnchor paramConstraintAnchor2, int paramInt1, ConstraintAnchor.Strength paramStrength, int paramInt2)
  {
    if (paramConstraintAnchor1.getOwner() == this)
      connect(paramConstraintAnchor1.getType(), paramConstraintAnchor2.getOwner(), paramConstraintAnchor2.getType(), paramInt1, paramStrength, paramInt2);
  }

  public void connectedTo(ConstraintWidget paramConstraintWidget)
  {
  }

  public void disconnectUnlockedWidget(ConstraintWidget paramConstraintWidget)
  {
    ArrayList localArrayList = getAnchors();
    int i = 0;
    int j = localArrayList.size();
    while (i < j)
    {
      ConstraintAnchor localConstraintAnchor = (ConstraintAnchor)localArrayList.get(i);
      if ((localConstraintAnchor.isConnected()) && (localConstraintAnchor.getTarget().getOwner() == paramConstraintWidget) && (localConstraintAnchor.getConnectionCreator() == 2))
        localConstraintAnchor.reset();
      i++;
    }
  }

  public void disconnectWidget(ConstraintWidget paramConstraintWidget)
  {
    ArrayList localArrayList = getAnchors();
    int i = 0;
    int j = localArrayList.size();
    while (i < j)
    {
      ConstraintAnchor localConstraintAnchor = (ConstraintAnchor)localArrayList.get(i);
      if ((localConstraintAnchor.isConnected()) && (localConstraintAnchor.getTarget().getOwner() == paramConstraintWidget))
        localConstraintAnchor.reset();
      i++;
    }
  }

  public void forceUpdateDrawPosition()
  {
    int i = this.mX;
    int j = this.mY;
    int k = this.mX + this.mWidth;
    int m = this.mY + this.mHeight;
    this.mDrawX = i;
    this.mDrawY = j;
    this.mDrawWidth = (k - i);
    this.mDrawHeight = (m - j);
  }

  public ConstraintAnchor getAnchor(ConstraintAnchor.Type paramType)
  {
    switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[paramType.ordinal()])
    {
    default:
      return null;
    case 1:
      return this.mLeft;
    case 2:
      return this.mTop;
    case 3:
      return this.mRight;
    case 4:
      return this.mBottom;
    case 5:
      return this.mBaseline;
    case 6:
      return this.mCenterX;
    case 7:
      return this.mCenterY;
    case 8:
    }
    return this.mCenter;
  }

  public ArrayList<ConstraintAnchor> getAnchors()
  {
    return this.mAnchors;
  }

  public int getBaselineDistance()
  {
    return this.mBaselineDistance;
  }

  public int getBottom()
  {
    return getY() + this.mHeight;
  }

  public Object getCompanionWidget()
  {
    return this.mCompanionWidget;
  }

  public int getContainerItemSkip()
  {
    return this.mContainerItemSkip;
  }

  public String getDebugName()
  {
    return this.mDebugName;
  }

  public float getDimensionRatio()
  {
    return this.mDimensionRatio;
  }

  public int getDimensionRatioSide()
  {
    return this.mDimensionRatioSide;
  }

  public int getDrawBottom()
  {
    return getDrawY() + this.mDrawHeight;
  }

  public int getDrawHeight()
  {
    return this.mDrawHeight;
  }

  public int getDrawRight()
  {
    return getDrawX() + this.mDrawWidth;
  }

  public int getDrawWidth()
  {
    return this.mDrawWidth;
  }

  public int getDrawX()
  {
    return this.mDrawX + this.mOffsetX;
  }

  public int getDrawY()
  {
    return this.mDrawY + this.mOffsetY;
  }

  public int getHeight()
  {
    if (this.mVisibility == 8)
      return 0;
    return this.mHeight;
  }

  public float getHorizontalBiasPercent()
  {
    return this.mHorizontalBiasPercent;
  }

  public ConstraintWidget getHorizontalChainControlWidget()
  {
    boolean bool = isInHorizontalChain();
    Object localObject1 = null;
    Object localObject2;
    if (bool)
      localObject2 = this;
    while (true)
    {
      ConstraintAnchor localConstraintAnchor1;
      ConstraintAnchor localConstraintAnchor2;
      if ((localObject1 == null) && (localObject2 != null))
      {
        localConstraintAnchor1 = ((ConstraintWidget)localObject2).getAnchor(ConstraintAnchor.Type.LEFT);
        if (localConstraintAnchor1 != null)
          break label59;
        localConstraintAnchor2 = null;
        if (localConstraintAnchor2 != null)
          break label69;
      }
      label59: label69: for (ConstraintWidget localConstraintWidget = null; ; localConstraintWidget = localConstraintAnchor2.getOwner())
      {
        if (localConstraintWidget != getParent())
          break label79;
        localObject1 = localObject2;
        return localObject1;
        localConstraintAnchor2 = localConstraintAnchor1.getTarget();
        break;
      }
      label79: if (localConstraintWidget == null);
      for (ConstraintAnchor localConstraintAnchor3 = null; ; localConstraintAnchor3 = localConstraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).getTarget())
      {
        if ((localConstraintAnchor3 == null) || (localConstraintAnchor3.getOwner() == localObject2))
          break label122;
        localObject1 = localObject2;
        break;
      }
      label122: localObject2 = localConstraintWidget;
    }
  }

  public int getHorizontalChainStyle()
  {
    return this.mHorizontalChainStyle;
  }

  public DimensionBehaviour getHorizontalDimensionBehaviour()
  {
    return this.mHorizontalDimensionBehaviour;
  }

  public int getInternalDrawBottom()
  {
    return this.mDrawY + this.mDrawHeight;
  }

  public int getInternalDrawRight()
  {
    return this.mDrawX + this.mDrawWidth;
  }

  int getInternalDrawX()
  {
    return this.mDrawX;
  }

  int getInternalDrawY()
  {
    return this.mDrawY;
  }

  public int getLeft()
  {
    return getX();
  }

  public int getMinHeight()
  {
    return this.mMinHeight;
  }

  public int getMinWidth()
  {
    return this.mMinWidth;
  }

  public int getOptimizerWrapHeight()
  {
    int i = this.mHeight;
    if (this.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT)
    {
      if (this.mMatchConstraintDefaultHeight != 1)
        break label54;
      i = Math.max(this.mMatchConstraintMinHeight, i);
    }
    while (true)
    {
      if ((this.mMatchConstraintMaxHeight > 0) && (this.mMatchConstraintMaxHeight < i))
        i = this.mMatchConstraintMaxHeight;
      return i;
      label54: if (this.mMatchConstraintMinHeight > 0)
      {
        i = this.mMatchConstraintMinHeight;
        this.mHeight = i;
        continue;
      }
      i = 0;
    }
  }

  public int getOptimizerWrapWidth()
  {
    int i = this.mWidth;
    if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT)
    {
      if (this.mMatchConstraintDefaultWidth != 1)
        break label54;
      i = Math.max(this.mMatchConstraintMinWidth, i);
    }
    while (true)
    {
      if ((this.mMatchConstraintMaxWidth > 0) && (this.mMatchConstraintMaxWidth < i))
        i = this.mMatchConstraintMaxWidth;
      return i;
      label54: if (this.mMatchConstraintMinWidth > 0)
      {
        i = this.mMatchConstraintMinWidth;
        this.mWidth = i;
        continue;
      }
      i = 0;
    }
  }

  public ConstraintWidget getParent()
  {
    return this.mParent;
  }

  public int getRight()
  {
    return getX() + this.mWidth;
  }

  public WidgetContainer getRootWidgetContainer()
  {
    for (ConstraintWidget localConstraintWidget = this; localConstraintWidget.getParent() != null; localConstraintWidget = localConstraintWidget.getParent());
    if ((localConstraintWidget instanceof WidgetContainer))
      return (WidgetContainer)localConstraintWidget;
    return null;
  }

  protected int getRootX()
  {
    return this.mX + this.mOffsetX;
  }

  protected int getRootY()
  {
    return this.mY + this.mOffsetY;
  }

  public int getTop()
  {
    return getY();
  }

  public String getType()
  {
    return this.mType;
  }

  public float getVerticalBiasPercent()
  {
    return this.mVerticalBiasPercent;
  }

  public ConstraintWidget getVerticalChainControlWidget()
  {
    boolean bool = isInVerticalChain();
    Object localObject1 = null;
    Object localObject2;
    if (bool)
      localObject2 = this;
    while (true)
    {
      ConstraintAnchor localConstraintAnchor1;
      ConstraintAnchor localConstraintAnchor2;
      if ((localObject1 == null) && (localObject2 != null))
      {
        localConstraintAnchor1 = ((ConstraintWidget)localObject2).getAnchor(ConstraintAnchor.Type.TOP);
        if (localConstraintAnchor1 != null)
          break label59;
        localConstraintAnchor2 = null;
        if (localConstraintAnchor2 != null)
          break label69;
      }
      label59: label69: for (ConstraintWidget localConstraintWidget = null; ; localConstraintWidget = localConstraintAnchor2.getOwner())
      {
        if (localConstraintWidget != getParent())
          break label79;
        localObject1 = localObject2;
        return localObject1;
        localConstraintAnchor2 = localConstraintAnchor1.getTarget();
        break;
      }
      label79: if (localConstraintWidget == null);
      for (ConstraintAnchor localConstraintAnchor3 = null; ; localConstraintAnchor3 = localConstraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).getTarget())
      {
        if ((localConstraintAnchor3 == null) || (localConstraintAnchor3.getOwner() == localObject2))
          break label122;
        localObject1 = localObject2;
        break;
      }
      label122: localObject2 = localConstraintWidget;
    }
  }

  public int getVerticalChainStyle()
  {
    return this.mVerticalChainStyle;
  }

  public DimensionBehaviour getVerticalDimensionBehaviour()
  {
    return this.mVerticalDimensionBehaviour;
  }

  public int getVisibility()
  {
    return this.mVisibility;
  }

  public int getWidth()
  {
    if (this.mVisibility == 8)
      return 0;
    return this.mWidth;
  }

  public int getWrapHeight()
  {
    return this.mWrapHeight;
  }

  public int getWrapWidth()
  {
    return this.mWrapWidth;
  }

  public int getX()
  {
    return this.mX;
  }

  public int getY()
  {
    return this.mY;
  }

  public boolean hasAncestor(ConstraintWidget paramConstraintWidget)
  {
    ConstraintWidget localConstraintWidget = getParent();
    if (localConstraintWidget == paramConstraintWidget)
      return true;
    if (localConstraintWidget == paramConstraintWidget.getParent())
      return false;
    do
    {
      localConstraintWidget = localConstraintWidget.getParent();
      if (localConstraintWidget == null)
        break label46;
      if (localConstraintWidget == paramConstraintWidget)
        break;
    }
    while (localConstraintWidget != paramConstraintWidget.getParent());
    return true;
    label46: return false;
  }

  public boolean hasBaseline()
  {
    return this.mBaselineDistance > 0;
  }

  public void immediateConnect(ConstraintAnchor.Type paramType1, ConstraintWidget paramConstraintWidget, ConstraintAnchor.Type paramType2, int paramInt1, int paramInt2)
  {
    getAnchor(paramType1).connect(paramConstraintWidget.getAnchor(paramType2), paramInt1, paramInt2, ConstraintAnchor.Strength.STRONG, 0, true);
  }

  public boolean isInHorizontalChain()
  {
    return ((this.mLeft.mTarget != null) && (this.mLeft.mTarget.mTarget == this.mLeft)) || ((this.mRight.mTarget != null) && (this.mRight.mTarget.mTarget == this.mRight));
  }

  public boolean isInVerticalChain()
  {
    return ((this.mTop.mTarget != null) && (this.mTop.mTarget.mTarget == this.mTop)) || ((this.mBottom.mTarget != null) && (this.mBottom.mTarget.mTarget == this.mBottom));
  }

  public boolean isInsideConstraintLayout()
  {
    ConstraintWidget localConstraintWidget = getParent();
    if (localConstraintWidget == null)
      return false;
    do
    {
      localConstraintWidget = localConstraintWidget.getParent();
      if (localConstraintWidget == null)
        break;
    }
    while (!(localConstraintWidget instanceof ConstraintWidgetContainer));
    return true;
  }

  public boolean isRoot()
  {
    return this.mParent == null;
  }

  public boolean isRootContainer()
  {
    return ((this instanceof ConstraintWidgetContainer)) && ((this.mParent == null) || (!(this.mParent instanceof ConstraintWidgetContainer)));
  }

  public void reset()
  {
    this.mLeft.reset();
    this.mTop.reset();
    this.mRight.reset();
    this.mBottom.reset();
    this.mBaseline.reset();
    this.mCenterX.reset();
    this.mCenterY.reset();
    this.mCenter.reset();
    this.mParent = null;
    this.mWidth = 0;
    this.mHeight = 0;
    this.mDimensionRatio = 0.0F;
    this.mDimensionRatioSide = -1;
    this.mX = 0;
    this.mY = 0;
    this.mDrawX = 0;
    this.mDrawY = 0;
    this.mDrawWidth = 0;
    this.mDrawHeight = 0;
    this.mOffsetX = 0;
    this.mOffsetY = 0;
    this.mBaselineDistance = 0;
    this.mMinWidth = 0;
    this.mMinHeight = 0;
    this.mWrapWidth = 0;
    this.mWrapHeight = 0;
    this.mHorizontalBiasPercent = DEFAULT_BIAS;
    this.mVerticalBiasPercent = DEFAULT_BIAS;
    this.mHorizontalDimensionBehaviour = DimensionBehaviour.FIXED;
    this.mVerticalDimensionBehaviour = DimensionBehaviour.FIXED;
    this.mCompanionWidget = null;
    this.mContainerItemSkip = 0;
    this.mVisibility = 0;
    this.mDebugName = null;
    this.mType = null;
    this.mHorizontalWrapVisited = false;
    this.mVerticalWrapVisited = false;
    this.mHorizontalChainStyle = 0;
    this.mVerticalChainStyle = 0;
    this.mHorizontalChainFixedPosition = false;
    this.mVerticalChainFixedPosition = false;
    this.mHorizontalWeight = 0.0F;
    this.mVerticalWeight = 0.0F;
    this.mHorizontalResolution = -1;
    this.mVerticalResolution = -1;
  }

  public void resetAllConstraints()
  {
    resetAnchors();
    setVerticalBiasPercent(DEFAULT_BIAS);
    setHorizontalBiasPercent(DEFAULT_BIAS);
    if ((this instanceof ConstraintWidgetContainer));
    label83: label104: 
    do
      while (true)
      {
        return;
        if (getHorizontalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT)
        {
          if (getWidth() != getWrapWidth())
            break label83;
          setHorizontalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
        }
        while (getVerticalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT)
        {
          if (getHeight() != getWrapHeight())
            break label104;
          setVerticalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
          return;
          if (getWidth() <= getMinWidth())
            continue;
          setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
        }
      }
    while (getHeight() <= getMinHeight());
    setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
  }

  public void resetAnchor(ConstraintAnchor paramConstraintAnchor)
  {
    if ((getParent() != null) && ((getParent() instanceof ConstraintWidgetContainer)) && (((ConstraintWidgetContainer)getParent()).handlesInternalConstraints()))
      return;
    ConstraintAnchor localConstraintAnchor1 = getAnchor(ConstraintAnchor.Type.LEFT);
    ConstraintAnchor localConstraintAnchor2 = getAnchor(ConstraintAnchor.Type.RIGHT);
    ConstraintAnchor localConstraintAnchor3 = getAnchor(ConstraintAnchor.Type.TOP);
    ConstraintAnchor localConstraintAnchor4 = getAnchor(ConstraintAnchor.Type.BOTTOM);
    ConstraintAnchor localConstraintAnchor5 = getAnchor(ConstraintAnchor.Type.CENTER);
    ConstraintAnchor localConstraintAnchor6 = getAnchor(ConstraintAnchor.Type.CENTER_X);
    ConstraintAnchor localConstraintAnchor7 = getAnchor(ConstraintAnchor.Type.CENTER_Y);
    if (paramConstraintAnchor == localConstraintAnchor5)
    {
      if ((localConstraintAnchor1.isConnected()) && (localConstraintAnchor2.isConnected()) && (localConstraintAnchor1.getTarget() == localConstraintAnchor2.getTarget()))
      {
        localConstraintAnchor1.reset();
        localConstraintAnchor2.reset();
      }
      if ((localConstraintAnchor3.isConnected()) && (localConstraintAnchor4.isConnected()) && (localConstraintAnchor3.getTarget() == localConstraintAnchor4.getTarget()))
      {
        localConstraintAnchor3.reset();
        localConstraintAnchor4.reset();
      }
      this.mHorizontalBiasPercent = 0.5F;
      this.mVerticalBiasPercent = 0.5F;
    }
    while (true)
    {
      paramConstraintAnchor.reset();
      return;
      if (paramConstraintAnchor == localConstraintAnchor6)
      {
        if ((localConstraintAnchor1.isConnected()) && (localConstraintAnchor2.isConnected()) && (localConstraintAnchor1.getTarget().getOwner() == localConstraintAnchor2.getTarget().getOwner()))
        {
          localConstraintAnchor1.reset();
          localConstraintAnchor2.reset();
        }
        this.mHorizontalBiasPercent = 0.5F;
        continue;
      }
      if (paramConstraintAnchor == localConstraintAnchor7)
      {
        if ((localConstraintAnchor3.isConnected()) && (localConstraintAnchor4.isConnected()) && (localConstraintAnchor3.getTarget().getOwner() == localConstraintAnchor4.getTarget().getOwner()))
        {
          localConstraintAnchor3.reset();
          localConstraintAnchor4.reset();
        }
        this.mVerticalBiasPercent = 0.5F;
        continue;
      }
      if ((paramConstraintAnchor == localConstraintAnchor1) || (paramConstraintAnchor == localConstraintAnchor2))
      {
        if ((!localConstraintAnchor1.isConnected()) || (localConstraintAnchor1.getTarget() != localConstraintAnchor2.getTarget()))
          continue;
        localConstraintAnchor5.reset();
        continue;
      }
      if (((paramConstraintAnchor != localConstraintAnchor3) && (paramConstraintAnchor != localConstraintAnchor4)) || (!localConstraintAnchor3.isConnected()) || (localConstraintAnchor3.getTarget() != localConstraintAnchor4.getTarget()))
        continue;
      localConstraintAnchor5.reset();
    }
  }

  public void resetAnchors()
  {
    ConstraintWidget localConstraintWidget = getParent();
    if ((localConstraintWidget != null) && ((localConstraintWidget instanceof ConstraintWidgetContainer)) && (((ConstraintWidgetContainer)getParent()).handlesInternalConstraints()));
    while (true)
    {
      return;
      int i = 0;
      int j = this.mAnchors.size();
      while (i < j)
      {
        ((ConstraintAnchor)this.mAnchors.get(i)).reset();
        i++;
      }
    }
  }

  public void resetAnchors(int paramInt)
  {
    ConstraintWidget localConstraintWidget = getParent();
    if ((localConstraintWidget != null) && ((localConstraintWidget instanceof ConstraintWidgetContainer)) && (((ConstraintWidgetContainer)getParent()).handlesInternalConstraints()))
      return;
    int i = 0;
    int j = this.mAnchors.size();
    label41: ConstraintAnchor localConstraintAnchor;
    if (i < j)
    {
      localConstraintAnchor = (ConstraintAnchor)this.mAnchors.get(i);
      if (paramInt == localConstraintAnchor.getConnectionCreator())
      {
        if (!localConstraintAnchor.isVerticalAnchor())
          break label95;
        setVerticalBiasPercent(DEFAULT_BIAS);
      }
    }
    while (true)
    {
      localConstraintAnchor.reset();
      i++;
      break label41;
      break;
      label95: setHorizontalBiasPercent(DEFAULT_BIAS);
    }
  }

  public void resetGroups()
  {
    int i = this.mAnchors.size();
    for (int j = 0; j < i; j++)
      ((ConstraintAnchor)this.mAnchors.get(j)).mGroup = 2147483647;
  }

  public void resetSolverVariables(Cache paramCache)
  {
    this.mLeft.resetSolverVariable(paramCache);
    this.mTop.resetSolverVariable(paramCache);
    this.mRight.resetSolverVariable(paramCache);
    this.mBottom.resetSolverVariable(paramCache);
    this.mBaseline.resetSolverVariable(paramCache);
    this.mCenter.resetSolverVariable(paramCache);
    this.mCenterX.resetSolverVariable(paramCache);
    this.mCenterY.resetSolverVariable(paramCache);
  }

  public void setBaselineDistance(int paramInt)
  {
    this.mBaselineDistance = paramInt;
  }

  public void setCompanionWidget(Object paramObject)
  {
    this.mCompanionWidget = paramObject;
  }

  public void setContainerItemSkip(int paramInt)
  {
    if (paramInt >= 0)
    {
      this.mContainerItemSkip = paramInt;
      return;
    }
    this.mContainerItemSkip = 0;
  }

  public void setDebugName(String paramString)
  {
    this.mDebugName = paramString;
  }

  public void setDebugSolverName(LinearSystem paramLinearSystem, String paramString)
  {
    this.mDebugName = paramString;
    SolverVariable localSolverVariable1 = paramLinearSystem.createObjectVariable(this.mLeft);
    SolverVariable localSolverVariable2 = paramLinearSystem.createObjectVariable(this.mTop);
    SolverVariable localSolverVariable3 = paramLinearSystem.createObjectVariable(this.mRight);
    SolverVariable localSolverVariable4 = paramLinearSystem.createObjectVariable(this.mBottom);
    localSolverVariable1.setName(paramString + ".left");
    localSolverVariable2.setName(paramString + ".top");
    localSolverVariable3.setName(paramString + ".right");
    localSolverVariable4.setName(paramString + ".bottom");
    if (this.mBaselineDistance > 0)
      paramLinearSystem.createObjectVariable(this.mBaseline).setName(paramString + ".baseline");
  }

  public void setDimension(int paramInt1, int paramInt2)
  {
    this.mWidth = paramInt1;
    if (this.mWidth < this.mMinWidth)
      this.mWidth = this.mMinWidth;
    this.mHeight = paramInt2;
    if (this.mHeight < this.mMinHeight)
      this.mHeight = this.mMinHeight;
  }

  public void setDimensionRatio(float paramFloat, int paramInt)
  {
    this.mDimensionRatio = paramFloat;
    this.mDimensionRatioSide = paramInt;
  }

  public void setDimensionRatio(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
      this.mDimensionRatio = 0.0F;
    while (true)
    {
      return;
      int i = -1;
      int j = paramString.length();
      int k = paramString.indexOf(',');
      String str4;
      label67: int m;
      label73: String str2;
      String str3;
      float f1;
      if ((k > 0) && (k < j - 1))
      {
        str4 = paramString.substring(0, k);
        if (str4.equalsIgnoreCase("W"))
        {
          i = 0;
          m = k + 1;
          int n = paramString.indexOf(':');
          if ((n < 0) || (n >= j - 1))
            break label267;
          str2 = paramString.substring(m, n);
          str3 = paramString.substring(n + 1);
          int i2 = str2.length();
          f1 = 0.0F;
          if (i2 > 0)
          {
            int i3 = str3.length();
            f1 = 0.0F;
            if (i3 <= 0);
          }
        }
      }
      try
      {
        float f3 = Float.parseFloat(str2);
        float f4 = Float.parseFloat(str3);
        boolean bool1 = f3 < 0.0F;
        f1 = 0.0F;
        if (bool1)
        {
          boolean bool2 = f4 < 0.0F;
          f1 = 0.0F;
          if (bool2)
          {
            if (i != 1)
              break label246;
            float f5 = Math.abs(f4 / f3);
            f1 = f5;
          }
        }
        while (f1 > 0.0F)
        {
          this.mDimensionRatio = f1;
          this.mDimensionRatioSide = i;
          return;
          if (!str4.equalsIgnoreCase("H"))
            break label67;
          i = 1;
          break label67;
          m = 0;
          break label73;
          label246: float f6 = f3 / f4;
          float f7 = Math.abs(f6);
          f1 = f7;
          continue;
          label267: String str1 = paramString.substring(m);
          int i1 = str1.length();
          f1 = 0.0F;
          if (i1 <= 0)
            continue;
          try
          {
            float f2 = Float.parseFloat(str1);
            f1 = f2;
          }
          catch (NumberFormatException localNumberFormatException1)
          {
            f1 = 0.0F;
          }
        }
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        while (true)
          f1 = 0.0F;
      }
    }
  }

  public void setDrawHeight(int paramInt)
  {
    this.mDrawHeight = paramInt;
  }

  public void setDrawOrigin(int paramInt1, int paramInt2)
  {
    this.mDrawX = (paramInt1 - this.mOffsetX);
    this.mDrawY = (paramInt2 - this.mOffsetY);
    this.mX = this.mDrawX;
    this.mY = this.mDrawY;
  }

  public void setDrawWidth(int paramInt)
  {
    this.mDrawWidth = paramInt;
  }

  public void setDrawX(int paramInt)
  {
    this.mDrawX = (paramInt - this.mOffsetX);
    this.mX = this.mDrawX;
  }

  public void setDrawY(int paramInt)
  {
    this.mDrawY = (paramInt - this.mOffsetY);
    this.mY = this.mDrawY;
  }

  public void setFrame(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt3 - paramInt1;
    int j = paramInt4 - paramInt2;
    this.mX = paramInt1;
    this.mY = paramInt2;
    if (this.mVisibility == 8)
    {
      this.mWidth = 0;
      this.mHeight = 0;
    }
    do
    {
      return;
      if ((this.mHorizontalDimensionBehaviour == DimensionBehaviour.FIXED) && (i < this.mWidth))
        i = this.mWidth;
      if ((this.mVerticalDimensionBehaviour == DimensionBehaviour.FIXED) && (j < this.mHeight))
        j = this.mHeight;
      this.mWidth = i;
      this.mHeight = j;
      if (this.mHeight >= this.mMinHeight)
        continue;
      this.mHeight = this.mMinHeight;
    }
    while (this.mWidth >= this.mMinWidth);
    this.mWidth = this.mMinWidth;
  }

  public void setGoneMargin(ConstraintAnchor.Type paramType, int paramInt)
  {
    switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[paramType.ordinal()])
    {
    default:
      return;
    case 1:
      this.mLeft.mGoneMargin = paramInt;
      return;
    case 2:
      this.mTop.mGoneMargin = paramInt;
      return;
    case 3:
      this.mRight.mGoneMargin = paramInt;
      return;
    case 4:
    }
    this.mBottom.mGoneMargin = paramInt;
  }

  public void setHeight(int paramInt)
  {
    this.mHeight = paramInt;
    if (this.mHeight < this.mMinHeight)
      this.mHeight = this.mMinHeight;
  }

  public void setHorizontalBiasPercent(float paramFloat)
  {
    this.mHorizontalBiasPercent = paramFloat;
  }

  public void setHorizontalChainStyle(int paramInt)
  {
    this.mHorizontalChainStyle = paramInt;
  }

  public void setHorizontalDimension(int paramInt1, int paramInt2)
  {
    this.mX = paramInt1;
    this.mWidth = (paramInt2 - paramInt1);
    if (this.mWidth < this.mMinWidth)
      this.mWidth = this.mMinWidth;
  }

  public void setHorizontalDimensionBehaviour(DimensionBehaviour paramDimensionBehaviour)
  {
    this.mHorizontalDimensionBehaviour = paramDimensionBehaviour;
    if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT)
      setWidth(this.mWrapWidth);
  }

  public void setHorizontalMatchStyle(int paramInt1, int paramInt2, int paramInt3)
  {
    this.mMatchConstraintDefaultWidth = paramInt1;
    this.mMatchConstraintMinWidth = paramInt2;
    this.mMatchConstraintMaxWidth = paramInt3;
  }

  public void setHorizontalWeight(float paramFloat)
  {
    this.mHorizontalWeight = paramFloat;
  }

  public void setMinHeight(int paramInt)
  {
    if (paramInt < 0)
    {
      this.mMinHeight = 0;
      return;
    }
    this.mMinHeight = paramInt;
  }

  public void setMinWidth(int paramInt)
  {
    if (paramInt < 0)
    {
      this.mMinWidth = 0;
      return;
    }
    this.mMinWidth = paramInt;
  }

  public void setOffset(int paramInt1, int paramInt2)
  {
    this.mOffsetX = paramInt1;
    this.mOffsetY = paramInt2;
  }

  public void setOrigin(int paramInt1, int paramInt2)
  {
    this.mX = paramInt1;
    this.mY = paramInt2;
  }

  public void setParent(ConstraintWidget paramConstraintWidget)
  {
    this.mParent = paramConstraintWidget;
  }

  public void setType(String paramString)
  {
    this.mType = paramString;
  }

  public void setVerticalBiasPercent(float paramFloat)
  {
    this.mVerticalBiasPercent = paramFloat;
  }

  public void setVerticalChainStyle(int paramInt)
  {
    this.mVerticalChainStyle = paramInt;
  }

  public void setVerticalDimension(int paramInt1, int paramInt2)
  {
    this.mY = paramInt1;
    this.mHeight = (paramInt2 - paramInt1);
    if (this.mHeight < this.mMinHeight)
      this.mHeight = this.mMinHeight;
  }

  public void setVerticalDimensionBehaviour(DimensionBehaviour paramDimensionBehaviour)
  {
    this.mVerticalDimensionBehaviour = paramDimensionBehaviour;
    if (this.mVerticalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT)
      setHeight(this.mWrapHeight);
  }

  public void setVerticalMatchStyle(int paramInt1, int paramInt2, int paramInt3)
  {
    this.mMatchConstraintDefaultHeight = paramInt1;
    this.mMatchConstraintMinHeight = paramInt2;
    this.mMatchConstraintMaxHeight = paramInt3;
  }

  public void setVerticalWeight(float paramFloat)
  {
    this.mVerticalWeight = paramFloat;
  }

  public void setVisibility(int paramInt)
  {
    this.mVisibility = paramInt;
  }

  public void setWidth(int paramInt)
  {
    this.mWidth = paramInt;
    if (this.mWidth < this.mMinWidth)
      this.mWidth = this.mMinWidth;
  }

  public void setWrapHeight(int paramInt)
  {
    this.mWrapHeight = paramInt;
  }

  public void setWrapWidth(int paramInt)
  {
    this.mWrapWidth = paramInt;
  }

  public void setX(int paramInt)
  {
    this.mX = paramInt;
  }

  public void setY(int paramInt)
  {
    this.mY = paramInt;
  }

  public String toString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    String str1;
    StringBuilder localStringBuilder2;
    if (this.mType != null)
    {
      str1 = "type: " + this.mType + " ";
      localStringBuilder2 = localStringBuilder1.append(str1);
      if (this.mDebugName == null)
        break label196;
    }
    label196: for (String str2 = "id: " + this.mDebugName + " "; ; str2 = "")
    {
      return str2 + "(" + this.mX + ", " + this.mY + ") - (" + this.mWidth + " x " + this.mHeight + ")" + " wrap: (" + this.mWrapWidth + " x " + this.mWrapHeight + ")";
      str1 = "";
      break;
    }
  }

  public void updateDrawPosition()
  {
    int i = this.mX;
    int j = this.mY;
    int k = this.mX + this.mWidth;
    int m = this.mY + this.mHeight;
    this.mDrawX = i;
    this.mDrawY = j;
    this.mDrawWidth = (k - i);
    this.mDrawHeight = (m - j);
  }

  public void updateFromSolver(LinearSystem paramLinearSystem)
  {
    updateFromSolver(paramLinearSystem, 2147483647);
  }

  public void updateFromSolver(LinearSystem paramLinearSystem, int paramInt)
  {
    if (paramInt == 2147483647)
      setFrame(paramLinearSystem.getObjectVariableValue(this.mLeft), paramLinearSystem.getObjectVariableValue(this.mTop), paramLinearSystem.getObjectVariableValue(this.mRight), paramLinearSystem.getObjectVariableValue(this.mBottom));
    do
    {
      return;
      if (paramInt == -2)
      {
        setFrame(this.mSolverLeft, this.mSolverTop, this.mSolverRight, this.mSolverBottom);
        return;
      }
      if (this.mLeft.mGroup == paramInt)
        this.mSolverLeft = paramLinearSystem.getObjectVariableValue(this.mLeft);
      if (this.mTop.mGroup == paramInt)
        this.mSolverTop = paramLinearSystem.getObjectVariableValue(this.mTop);
      if (this.mRight.mGroup != paramInt)
        continue;
      this.mSolverRight = paramLinearSystem.getObjectVariableValue(this.mRight);
    }
    while (this.mBottom.mGroup != paramInt);
    this.mSolverBottom = paramLinearSystem.getObjectVariableValue(this.mBottom);
  }

  public static enum ContentAlignment
  {
    static
    {
      END = new ContentAlignment("END", 2);
      TOP = new ContentAlignment("TOP", 3);
      VERTICAL_MIDDLE = new ContentAlignment("VERTICAL_MIDDLE", 4);
      BOTTOM = new ContentAlignment("BOTTOM", 5);
      LEFT = new ContentAlignment("LEFT", 6);
      RIGHT = new ContentAlignment("RIGHT", 7);
      ContentAlignment[] arrayOfContentAlignment = new ContentAlignment[8];
      arrayOfContentAlignment[0] = BEGIN;
      arrayOfContentAlignment[1] = MIDDLE;
      arrayOfContentAlignment[2] = END;
      arrayOfContentAlignment[3] = TOP;
      arrayOfContentAlignment[4] = VERTICAL_MIDDLE;
      arrayOfContentAlignment[5] = BOTTOM;
      arrayOfContentAlignment[6] = LEFT;
      arrayOfContentAlignment[7] = RIGHT;
      $VALUES = arrayOfContentAlignment;
    }
  }

  public static enum DimensionBehaviour
  {
    static
    {
      MATCH_CONSTRAINT = new DimensionBehaviour("MATCH_CONSTRAINT", 2);
      MATCH_PARENT = new DimensionBehaviour("MATCH_PARENT", 3);
      DimensionBehaviour[] arrayOfDimensionBehaviour = new DimensionBehaviour[4];
      arrayOfDimensionBehaviour[0] = FIXED;
      arrayOfDimensionBehaviour[1] = WRAP_CONTENT;
      arrayOfDimensionBehaviour[2] = MATCH_CONSTRAINT;
      arrayOfDimensionBehaviour[3] = MATCH_PARENT;
      $VALUES = arrayOfDimensionBehaviour;
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.solver.widgets.ConstraintWidget
 * JD-Core Version:    0.6.0
 */