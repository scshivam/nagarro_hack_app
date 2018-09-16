package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import java.util.ArrayList;
import java.util.Arrays;

public class ConstraintWidgetContainer extends WidgetContainer
{
  static boolean ALLOW_ROOT_GROUP = false;
  private static final int CHAIN_FIRST = 0;
  private static final int CHAIN_FIRST_VISIBLE = 2;
  private static final int CHAIN_LAST = 1;
  private static final int CHAIN_LAST_VISIBLE = 3;
  private static final boolean DEBUG = false;
  private static final boolean DEBUG_LAYOUT = false;
  private static final boolean DEBUG_OPTIMIZE = false;
  private static final int FLAG_CHAIN_DANGLING = 1;
  private static final int FLAG_CHAIN_OPTIMIZE = 0;
  private static final int FLAG_RECOMPUTE_BOUNDS = 2;
  private static final int MAX_ITERATIONS = 8;
  public static final int OPTIMIZATION_ALL = 2;
  public static final int OPTIMIZATION_BASIC = 4;
  public static final int OPTIMIZATION_CHAIN = 8;
  public static final int OPTIMIZATION_NONE = 1;
  private static final boolean USE_SNAPSHOT = true;
  private static final boolean USE_THREAD;
  private boolean[] flags = new boolean[3];
  protected LinearSystem mBackgroundSystem = null;
  private ConstraintWidget[] mChainEnds = new ConstraintWidget[4];
  private boolean mHeightMeasuredTooSmall = false;
  private ConstraintWidget[] mHorizontalChainsArray = new ConstraintWidget[4];
  private int mHorizontalChainsSize = 0;
  private ConstraintWidget[] mMatchConstraintsChainedWidgets = new ConstraintWidget[4];
  private int mOptimizationLevel = 2;
  int mPaddingBottom;
  int mPaddingLeft;
  int mPaddingRight;
  int mPaddingTop;
  private Snapshot mSnapshot;
  protected LinearSystem mSystem = new LinearSystem();
  private ConstraintWidget[] mVerticalChainsArray = new ConstraintWidget[4];
  private int mVerticalChainsSize = 0;
  private boolean mWidthMeasuredTooSmall = false;
  int mWrapHeight;
  int mWrapWidth;

  public ConstraintWidgetContainer()
  {
  }

  public ConstraintWidgetContainer(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2);
  }

  public ConstraintWidgetContainer(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  private void addHorizontalChain(ConstraintWidget paramConstraintWidget)
  {
    for (int i = 0; i < this.mHorizontalChainsSize; i++)
      if (this.mHorizontalChainsArray[i] == paramConstraintWidget)
        return;
    if (1 + this.mHorizontalChainsSize >= this.mHorizontalChainsArray.length)
      this.mHorizontalChainsArray = ((ConstraintWidget[])Arrays.copyOf(this.mHorizontalChainsArray, 2 * this.mHorizontalChainsArray.length));
    this.mHorizontalChainsArray[this.mHorizontalChainsSize] = paramConstraintWidget;
    this.mHorizontalChainsSize = (1 + this.mHorizontalChainsSize);
  }

  private void addVerticalChain(ConstraintWidget paramConstraintWidget)
  {
    for (int i = 0; i < this.mVerticalChainsSize; i++)
      if (this.mVerticalChainsArray[i] == paramConstraintWidget)
        return;
    if (1 + this.mVerticalChainsSize >= this.mVerticalChainsArray.length)
      this.mVerticalChainsArray = ((ConstraintWidget[])Arrays.copyOf(this.mVerticalChainsArray, 2 * this.mVerticalChainsArray.length));
    this.mVerticalChainsArray[this.mVerticalChainsSize] = paramConstraintWidget;
    this.mVerticalChainsSize = (1 + this.mVerticalChainsSize);
  }

  private void applyHorizontalChain(LinearSystem paramLinearSystem)
  {
    int i = 0;
    int j = this.mHorizontalChainsSize;
    if (i < j)
    {
      ConstraintWidget localConstraintWidget1 = this.mHorizontalChainsArray[i];
      int k = countMatchConstraintsChainedWidgets(paramLinearSystem, this.mChainEnds, this.mHorizontalChainsArray[i], 0, this.flags);
      Object localObject1 = this.mChainEnds[2];
      if (localObject1 == null);
      label149: label161: label244: label250: float f;
      label256: label438: label759: int i12;
      label788: label817: label987: int i13;
      label905: label911: label915: label917: label921: label923: label1073: label1077: 
      do
      {
        while (true)
        {
          i++;
          break;
          if (this.flags[1] != 0)
          {
            int i27 = localConstraintWidget1.getDrawX();
            while (localObject1 != null)
            {
              paramLinearSystem.addEquality(((ConstraintWidget)localObject1).mLeft.mSolverVariable, i27);
              ConstraintWidget localConstraintWidget8 = ((ConstraintWidget)localObject1).mHorizontalNextWidget;
              i27 += ((ConstraintWidget)localObject1).mLeft.getMargin() + ((ConstraintWidget)localObject1).getWidth() + ((ConstraintWidget)localObject1).mRight.getMargin();
              localObject1 = localConstraintWidget8;
            }
            continue;
          }
          int m;
          int n;
          if (localConstraintWidget1.mHorizontalChainStyle == 0)
          {
            m = 1;
            if (localConstraintWidget1.mHorizontalChainStyle != 2)
              break label244;
            n = 1;
            if (this.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
              break label250;
          }
          for (int i1 = 1; ; i1 = 0)
          {
            if (((this.mOptimizationLevel != 2) && (this.mOptimizationLevel != 8)) || (this.flags[0] == 0) || (!localConstraintWidget1.mHorizontalChainFixedPosition) || (n != 0) || (i1 != 0) || (localConstraintWidget1.mHorizontalChainStyle != 0))
              break label256;
            Optimizer.applyDirectResolutionHorizontalChain(this, paramLinearSystem, k, localConstraintWidget1);
            break;
            m = 0;
            break label149;
            n = 0;
            break label161;
          }
          if ((k == 0) || (n != 0))
          {
            Object localObject2 = null;
            ConstraintWidget localConstraintWidget2 = null;
            Object localObject3 = localObject1;
            int i2 = 0;
            if (localObject1 != null)
            {
              ConstraintWidget localConstraintWidget3 = ((ConstraintWidget)localObject1).mHorizontalNextWidget;
              if (localConstraintWidget3 == null)
              {
                localConstraintWidget2 = this.mChainEnds[1];
                i2 = 1;
              }
              ConstraintAnchor localConstraintAnchor5;
              ConstraintAnchor localConstraintAnchor6;
              if (n != 0)
              {
                localConstraintAnchor5 = ((ConstraintWidget)localObject1).mLeft;
                int i9 = localConstraintAnchor5.getMargin();
                if (localObject2 != null)
                  i9 += localObject2.mRight.getMargin();
                int i10 = 1;
                if (localObject3 != localObject1)
                  i10 = 3;
                paramLinearSystem.addGreaterThan(localConstraintAnchor5.mSolverVariable, localConstraintAnchor5.mTarget.mSolverVariable, i9, i10);
                if (((ConstraintWidget)localObject1).mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
                {
                  localConstraintAnchor6 = ((ConstraintWidget)localObject1).mRight;
                  if (((ConstraintWidget)localObject1).mMatchConstraintDefaultWidth == 1)
                  {
                    int i11 = Math.max(((ConstraintWidget)localObject1).mMatchConstraintMinWidth, ((ConstraintWidget)localObject1).getWidth());
                    paramLinearSystem.addEquality(localConstraintAnchor6.mSolverVariable, localConstraintAnchor5.mSolverVariable, i11, 3);
                  }
                }
                else
                {
                  localObject2 = localObject1;
                  if (i2 == 0)
                    break label923;
                }
              }
              for (localObject1 = null; ; localObject1 = localConstraintWidget3)
              {
                break;
                paramLinearSystem.addGreaterThan(localConstraintAnchor5.mSolverVariable, localConstraintAnchor5.mTarget.mSolverVariable, localConstraintAnchor5.mMargin, 3);
                paramLinearSystem.addLowerThan(localConstraintAnchor6.mSolverVariable, localConstraintAnchor5.mSolverVariable, ((ConstraintWidget)localObject1).mMatchConstraintMinWidth, 3);
                break label438;
                if ((m == 0) && (i2 != 0) && (localObject2 != null))
                {
                  if (((ConstraintWidget)localObject1).mRight.mTarget == null)
                  {
                    paramLinearSystem.addEquality(((ConstraintWidget)localObject1).mRight.mSolverVariable, ((ConstraintWidget)localObject1).getDrawRight());
                    break label438;
                  }
                  int i8 = ((ConstraintWidget)localObject1).mRight.getMargin();
                  paramLinearSystem.addEquality(((ConstraintWidget)localObject1).mRight.mSolverVariable, localConstraintWidget2.mRight.mTarget.mSolverVariable, -i8, 5);
                  break label438;
                }
                if ((m == 0) && (i2 == 0) && (localObject2 == null))
                {
                  if (((ConstraintWidget)localObject1).mLeft.mTarget == null)
                  {
                    paramLinearSystem.addEquality(((ConstraintWidget)localObject1).mLeft.mSolverVariable, ((ConstraintWidget)localObject1).getDrawX());
                    break label438;
                  }
                  int i7 = ((ConstraintWidget)localObject1).mLeft.getMargin();
                  paramLinearSystem.addEquality(((ConstraintWidget)localObject1).mLeft.mSolverVariable, localConstraintWidget1.mLeft.mTarget.mSolverVariable, i7, 5);
                  break label438;
                }
                ConstraintAnchor localConstraintAnchor3 = ((ConstraintWidget)localObject1).mLeft;
                ConstraintAnchor localConstraintAnchor4 = ((ConstraintWidget)localObject1).mRight;
                int i5 = localConstraintAnchor3.getMargin();
                int i6 = localConstraintAnchor4.getMargin();
                paramLinearSystem.addGreaterThan(localConstraintAnchor3.mSolverVariable, localConstraintAnchor3.mTarget.mSolverVariable, i5, 1);
                paramLinearSystem.addLowerThan(localConstraintAnchor4.mSolverVariable, localConstraintAnchor4.mTarget.mSolverVariable, -i6, 1);
                SolverVariable localSolverVariable3;
                if (localConstraintAnchor3.mTarget != null)
                {
                  localSolverVariable3 = localConstraintAnchor3.mTarget.mSolverVariable;
                  if (localObject2 == null)
                  {
                    if (localConstraintWidget1.mLeft.mTarget == null)
                      break label905;
                    localSolverVariable3 = localConstraintWidget1.mLeft.mTarget.mSolverVariable;
                  }
                  if (localConstraintWidget3 == null)
                  {
                    if (localConstraintWidget2.mRight.mTarget == null)
                      break label911;
                    localConstraintWidget3 = localConstraintWidget2.mRight.mTarget.mOwner;
                  }
                  if (localConstraintWidget3 == null)
                    break label915;
                  localSolverVariable4 = localConstraintWidget3.mLeft.mSolverVariable;
                  if (i2 != 0)
                    if (localConstraintWidget2.mRight.mTarget == null)
                      break label917;
                }
                for (SolverVariable localSolverVariable4 = localConstraintWidget2.mRight.mTarget.mSolverVariable; ; localSolverVariable4 = null)
                {
                  if ((localSolverVariable3 == null) || (localSolverVariable4 == null))
                    break label921;
                  paramLinearSystem.addCentering(localConstraintAnchor3.mSolverVariable, localSolverVariable3, i5, 0.5F, localSolverVariable4, localConstraintAnchor4.mSolverVariable, i6, 4);
                  break;
                  localSolverVariable3 = null;
                  break label759;
                  localSolverVariable3 = null;
                  break label788;
                  localConstraintWidget3 = null;
                  break label817;
                  break;
                }
                break label438;
              }
            }
            if (n == 0)
              continue;
            ConstraintAnchor localConstraintAnchor1 = localObject3.mLeft;
            ConstraintAnchor localConstraintAnchor2 = localConstraintWidget2.mRight;
            int i3 = localConstraintAnchor1.getMargin();
            int i4 = localConstraintAnchor2.getMargin();
            SolverVariable localSolverVariable1;
            if (localConstraintWidget1.mLeft.mTarget != null)
            {
              localSolverVariable1 = localConstraintWidget1.mLeft.mTarget.mSolverVariable;
              if (localConstraintWidget2.mRight.mTarget == null)
                break label1073;
            }
            for (SolverVariable localSolverVariable2 = localConstraintWidget2.mRight.mTarget.mSolverVariable; ; localSolverVariable2 = null)
            {
              if ((localSolverVariable1 == null) || (localSolverVariable2 == null))
                break label1077;
              paramLinearSystem.addLowerThan(localConstraintAnchor2.mSolverVariable, localSolverVariable2, -i4, 1);
              paramLinearSystem.addCentering(localConstraintAnchor1.mSolverVariable, localSolverVariable1, i3, localConstraintWidget1.mHorizontalBiasPercent, localSolverVariable2, localConstraintAnchor2.mSolverVariable, i4, 4);
              break;
              localSolverVariable1 = null;
              break label987;
            }
            continue;
          }
          Object localObject4 = null;
          f = 0.0F;
          if (localObject1 != null)
          {
            if (((ConstraintWidget)localObject1).mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
            {
              int i23 = ((ConstraintWidget)localObject1).mLeft.getMargin();
              if (localObject4 != null)
                i23 += localObject4.mRight.getMargin();
              int i24 = 3;
              if (((ConstraintWidget)localObject1).mLeft.mTarget.mOwner.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
                i24 = 2;
              paramLinearSystem.addGreaterThan(((ConstraintWidget)localObject1).mLeft.mSolverVariable, ((ConstraintWidget)localObject1).mLeft.mTarget.mSolverVariable, i23, i24);
              int i25 = ((ConstraintWidget)localObject1).mRight.getMargin();
              if ((((ConstraintWidget)localObject1).mRight.mTarget.mOwner.mLeft.mTarget != null) && (((ConstraintWidget)localObject1).mRight.mTarget.mOwner.mLeft.mTarget.mOwner == localObject1))
                i25 += ((ConstraintWidget)localObject1).mRight.mTarget.mOwner.mLeft.getMargin();
              int i26 = 3;
              if (((ConstraintWidget)localObject1).mRight.mTarget.mOwner.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
                i26 = 2;
              paramLinearSystem.addLowerThan(((ConstraintWidget)localObject1).mRight.mSolverVariable, ((ConstraintWidget)localObject1).mRight.mTarget.mSolverVariable, -i25, i26);
            }
            while (true)
            {
              localObject4 = localObject1;
              localObject1 = ((ConstraintWidget)localObject1).mHorizontalNextWidget;
              break;
              f += ((ConstraintWidget)localObject1).mHorizontalWeight;
              ConstraintAnchor localConstraintAnchor8 = ((ConstraintWidget)localObject1).mRight.mTarget;
              int i22 = 0;
              if (localConstraintAnchor8 != null)
              {
                i22 = ((ConstraintWidget)localObject1).mRight.getMargin();
                ConstraintWidget localConstraintWidget7 = this.mChainEnds[3];
                if (localObject1 != localConstraintWidget7)
                  i22 += ((ConstraintWidget)localObject1).mRight.mTarget.mOwner.mLeft.getMargin();
              }
              paramLinearSystem.addGreaterThan(((ConstraintWidget)localObject1).mRight.mSolverVariable, ((ConstraintWidget)localObject1).mLeft.mSolverVariable, 0, 1);
              paramLinearSystem.addLowerThan(((ConstraintWidget)localObject1).mRight.mSolverVariable, ((ConstraintWidget)localObject1).mRight.mTarget.mSolverVariable, -i22, 1);
            }
          }
          if (k != 1)
            break label1715;
          ConstraintWidget localConstraintWidget6 = this.mMatchConstraintsChainedWidgets[0];
          int i20 = localConstraintWidget6.mLeft.getMargin();
          if (localConstraintWidget6.mLeft.mTarget != null)
            i20 += localConstraintWidget6.mLeft.mTarget.getMargin();
          int i21 = localConstraintWidget6.mRight.getMargin();
          if (localConstraintWidget6.mRight.mTarget != null)
            i21 += localConstraintWidget6.mRight.mTarget.getMargin();
          SolverVariable localSolverVariable10 = localConstraintWidget1.mRight.mTarget.mSolverVariable;
          if (localConstraintWidget6 == this.mChainEnds[3])
            localSolverVariable10 = this.mChainEnds[1].mRight.mTarget.mSolverVariable;
          if (localConstraintWidget6.mMatchConstraintDefaultWidth == 1)
          {
            paramLinearSystem.addGreaterThan(localConstraintWidget1.mLeft.mSolverVariable, localConstraintWidget1.mLeft.mTarget.mSolverVariable, i20, 1);
            paramLinearSystem.addLowerThan(localConstraintWidget1.mRight.mSolverVariable, localSolverVariable10, -i21, 1);
            paramLinearSystem.addEquality(localConstraintWidget1.mRight.mSolverVariable, localConstraintWidget1.mLeft.mSolverVariable, localConstraintWidget1.getWidth(), 2);
            continue;
          }
          paramLinearSystem.addEquality(localConstraintWidget6.mLeft.mSolverVariable, localConstraintWidget6.mLeft.mTarget.mSolverVariable, i20, 1);
          paramLinearSystem.addEquality(localConstraintWidget6.mRight.mSolverVariable, localSolverVariable10, -i21, 1);
        }
        i12 = 0;
        i13 = k - 1;
      }
      while (i12 >= i13);
      label1715: ConstraintWidget localConstraintWidget4 = this.mMatchConstraintsChainedWidgets[i12];
      ConstraintWidget localConstraintWidget5 = this.mMatchConstraintsChainedWidgets[(i12 + 1)];
      SolverVariable localSolverVariable5 = localConstraintWidget4.mLeft.mSolverVariable;
      SolverVariable localSolverVariable6 = localConstraintWidget4.mRight.mSolverVariable;
      SolverVariable localSolverVariable7 = localConstraintWidget5.mLeft.mSolverVariable;
      SolverVariable localSolverVariable8 = localConstraintWidget5.mRight.mSolverVariable;
      if (localConstraintWidget5 == this.mChainEnds[3])
        localSolverVariable8 = this.mChainEnds[1].mRight.mSolverVariable;
      int i14 = localConstraintWidget4.mLeft.getMargin();
      if ((localConstraintWidget4.mLeft.mTarget != null) && (localConstraintWidget4.mLeft.mTarget.mOwner.mRight.mTarget != null) && (localConstraintWidget4.mLeft.mTarget.mOwner.mRight.mTarget.mOwner == localConstraintWidget4))
        i14 += localConstraintWidget4.mLeft.mTarget.mOwner.mRight.getMargin();
      paramLinearSystem.addGreaterThan(localSolverVariable5, localConstraintWidget4.mLeft.mTarget.mSolverVariable, i14, 2);
      int i15 = localConstraintWidget4.mRight.getMargin();
      if ((localConstraintWidget4.mRight.mTarget != null) && (localConstraintWidget4.mHorizontalNextWidget != null))
        if (localConstraintWidget4.mHorizontalNextWidget.mLeft.mTarget == null)
          break label2353;
      label2353: for (int i19 = localConstraintWidget4.mHorizontalNextWidget.mLeft.getMargin(); ; i19 = 0)
      {
        i15 += i19;
        paramLinearSystem.addLowerThan(localSolverVariable6, localConstraintWidget4.mRight.mTarget.mSolverVariable, -i15, 2);
        if (i12 + 1 == k - 1)
        {
          int i16 = localConstraintWidget5.mLeft.getMargin();
          if ((localConstraintWidget5.mLeft.mTarget != null) && (localConstraintWidget5.mLeft.mTarget.mOwner.mRight.mTarget != null) && (localConstraintWidget5.mLeft.mTarget.mOwner.mRight.mTarget.mOwner == localConstraintWidget5))
            i16 += localConstraintWidget5.mLeft.mTarget.mOwner.mRight.getMargin();
          paramLinearSystem.addGreaterThan(localSolverVariable7, localConstraintWidget5.mLeft.mTarget.mSolverVariable, i16, 2);
          ConstraintAnchor localConstraintAnchor7 = localConstraintWidget5.mRight;
          if (localConstraintWidget5 == this.mChainEnds[3])
            localConstraintAnchor7 = this.mChainEnds[1].mRight;
          int i17 = localConstraintAnchor7.getMargin();
          if ((localConstraintAnchor7.mTarget != null) && (localConstraintAnchor7.mTarget.mOwner.mLeft.mTarget != null) && (localConstraintAnchor7.mTarget.mOwner.mLeft.mTarget.mOwner == localConstraintWidget5))
            i17 += localConstraintAnchor7.mTarget.mOwner.mLeft.getMargin();
          SolverVariable localSolverVariable9 = localConstraintAnchor7.mTarget.mSolverVariable;
          int i18 = -i17;
          paramLinearSystem.addLowerThan(localSolverVariable8, localSolverVariable9, i18, 2);
        }
        if (localConstraintWidget1.mMatchConstraintMaxWidth > 0)
          paramLinearSystem.addLowerThan(localSolverVariable6, localSolverVariable5, localConstraintWidget1.mMatchConstraintMaxWidth, 2);
        ArrayRow localArrayRow = paramLinearSystem.createRow();
        localArrayRow.createRowEqualDimension(localConstraintWidget4.mHorizontalWeight, f, localConstraintWidget5.mHorizontalWeight, localSolverVariable5, localConstraintWidget4.mLeft.getMargin(), localSolverVariable6, localConstraintWidget4.mRight.getMargin(), localSolverVariable7, localConstraintWidget5.mLeft.getMargin(), localSolverVariable8, localConstraintWidget5.mRight.getMargin());
        paramLinearSystem.addConstraint(localArrayRow);
        i12++;
        break;
      }
    }
  }

  private void applyVerticalChain(LinearSystem paramLinearSystem)
  {
    int i = 0;
    int j = this.mVerticalChainsSize;
    if (i < j)
    {
      ConstraintWidget localConstraintWidget1 = this.mVerticalChainsArray[i];
      int k = countMatchConstraintsChainedWidgets(paramLinearSystem, this.mChainEnds, this.mVerticalChainsArray[i], 1, this.flags);
      Object localObject1 = this.mChainEnds[2];
      if (localObject1 == null);
      label149: label161: label244: label250: label382: float f;
      label256: label464: label871: label1000: label1004: label1006: int i12;
      label536: label842: label988: label994: label998: int i13;
      label900: label1070: 
      do
      {
        while (true)
        {
          i++;
          break;
          if (this.flags[1] != 0)
          {
            int i27 = localConstraintWidget1.getDrawY();
            while (localObject1 != null)
            {
              paramLinearSystem.addEquality(((ConstraintWidget)localObject1).mTop.mSolverVariable, i27);
              ConstraintWidget localConstraintWidget8 = ((ConstraintWidget)localObject1).mVerticalNextWidget;
              i27 += ((ConstraintWidget)localObject1).mTop.getMargin() + ((ConstraintWidget)localObject1).getHeight() + ((ConstraintWidget)localObject1).mBottom.getMargin();
              localObject1 = localConstraintWidget8;
            }
            continue;
          }
          int m;
          int n;
          if (localConstraintWidget1.mVerticalChainStyle == 0)
          {
            m = 1;
            if (localConstraintWidget1.mVerticalChainStyle != 2)
              break label244;
            n = 1;
            if (this.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
              break label250;
          }
          for (int i1 = 1; ; i1 = 0)
          {
            if (((this.mOptimizationLevel != 2) && (this.mOptimizationLevel != 8)) || (this.flags[0] == 0) || (!localConstraintWidget1.mVerticalChainFixedPosition) || (n != 0) || (i1 != 0) || (localConstraintWidget1.mVerticalChainStyle != 0))
              break label256;
            Optimizer.applyDirectResolutionVerticalChain(this, paramLinearSystem, k, localConstraintWidget1);
            break;
            m = 0;
            break label149;
            n = 0;
            break label161;
          }
          if ((k == 0) || (n != 0))
          {
            Object localObject2 = null;
            ConstraintWidget localConstraintWidget2 = null;
            Object localObject3 = localObject1;
            int i2 = 0;
            if (localObject1 != null)
            {
              ConstraintWidget localConstraintWidget3 = ((ConstraintWidget)localObject1).mVerticalNextWidget;
              if (localConstraintWidget3 == null)
              {
                localConstraintWidget2 = this.mChainEnds[1];
                i2 = 1;
              }
              ConstraintAnchor localConstraintAnchor5;
              int i9;
              SolverVariable localSolverVariable5;
              SolverVariable localSolverVariable6;
              ConstraintAnchor localConstraintAnchor7;
              if (n != 0)
              {
                localConstraintAnchor5 = ((ConstraintWidget)localObject1).mTop;
                i9 = localConstraintAnchor5.getMargin();
                if (localObject2 != null)
                  i9 += localObject2.mBottom.getMargin();
                int i10 = 1;
                if (localObject3 != localObject1)
                  i10 = 3;
                if (localConstraintAnchor5.mTarget != null)
                {
                  localSolverVariable5 = localConstraintAnchor5.mSolverVariable;
                  localSolverVariable6 = localConstraintAnchor5.mTarget.mSolverVariable;
                  if ((localSolverVariable5 != null) && (localSolverVariable6 != null))
                    paramLinearSystem.addGreaterThan(localSolverVariable5, localSolverVariable6, i9, i10);
                  if (((ConstraintWidget)localObject1).mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
                  {
                    localConstraintAnchor7 = ((ConstraintWidget)localObject1).mBottom;
                    if (((ConstraintWidget)localObject1).mMatchConstraintDefaultHeight != 1)
                      break label536;
                    int i11 = Math.max(((ConstraintWidget)localObject1).mMatchConstraintMinHeight, ((ConstraintWidget)localObject1).getHeight());
                    paramLinearSystem.addEquality(localConstraintAnchor7.mSolverVariable, localConstraintAnchor5.mSolverVariable, i11, 3);
                  }
                  localObject2 = localObject1;
                  if (i2 == 0)
                    break label1006;
                }
              }
              for (localObject1 = null; ; localObject1 = localConstraintWidget3)
              {
                break;
                ConstraintAnchor localConstraintAnchor6 = ((ConstraintWidget)localObject1).mBaseline.mTarget;
                localSolverVariable5 = null;
                localSolverVariable6 = null;
                if (localConstraintAnchor6 == null)
                  break label382;
                localSolverVariable5 = ((ConstraintWidget)localObject1).mBaseline.mSolverVariable;
                localSolverVariable6 = ((ConstraintWidget)localObject1).mBaseline.mTarget.mSolverVariable;
                i9 -= localConstraintAnchor5.getMargin();
                break label382;
                paramLinearSystem.addGreaterThan(localConstraintAnchor5.mSolverVariable, localConstraintAnchor5.mTarget.mSolverVariable, localConstraintAnchor5.mMargin, 3);
                paramLinearSystem.addLowerThan(localConstraintAnchor7.mSolverVariable, localConstraintAnchor5.mSolverVariable, ((ConstraintWidget)localObject1).mMatchConstraintMinHeight, 3);
                break label464;
                if ((m == 0) && (i2 != 0) && (localObject2 != null))
                {
                  if (((ConstraintWidget)localObject1).mBottom.mTarget == null)
                  {
                    paramLinearSystem.addEquality(((ConstraintWidget)localObject1).mBottom.mSolverVariable, ((ConstraintWidget)localObject1).getDrawBottom());
                    break label464;
                  }
                  int i8 = ((ConstraintWidget)localObject1).mBottom.getMargin();
                  paramLinearSystem.addEquality(((ConstraintWidget)localObject1).mBottom.mSolverVariable, localConstraintWidget2.mBottom.mTarget.mSolverVariable, -i8, 5);
                  break label464;
                }
                if ((m == 0) && (i2 == 0) && (localObject2 == null))
                {
                  if (((ConstraintWidget)localObject1).mTop.mTarget == null)
                  {
                    paramLinearSystem.addEquality(((ConstraintWidget)localObject1).mTop.mSolverVariable, ((ConstraintWidget)localObject1).getDrawY());
                    break label464;
                  }
                  int i7 = ((ConstraintWidget)localObject1).mTop.getMargin();
                  paramLinearSystem.addEquality(((ConstraintWidget)localObject1).mTop.mSolverVariable, localConstraintWidget1.mTop.mTarget.mSolverVariable, i7, 5);
                  break label464;
                }
                ConstraintAnchor localConstraintAnchor3 = ((ConstraintWidget)localObject1).mTop;
                ConstraintAnchor localConstraintAnchor4 = ((ConstraintWidget)localObject1).mBottom;
                int i5 = localConstraintAnchor3.getMargin();
                int i6 = localConstraintAnchor4.getMargin();
                paramLinearSystem.addGreaterThan(localConstraintAnchor3.mSolverVariable, localConstraintAnchor3.mTarget.mSolverVariable, i5, 1);
                paramLinearSystem.addLowerThan(localConstraintAnchor4.mSolverVariable, localConstraintAnchor4.mTarget.mSolverVariable, -i6, 1);
                SolverVariable localSolverVariable3;
                if (localConstraintAnchor3.mTarget != null)
                {
                  localSolverVariable3 = localConstraintAnchor3.mTarget.mSolverVariable;
                  if (localObject2 == null)
                  {
                    if (localConstraintWidget1.mTop.mTarget == null)
                      break label988;
                    localSolverVariable3 = localConstraintWidget1.mTop.mTarget.mSolverVariable;
                  }
                  if (localConstraintWidget3 == null)
                  {
                    if (localConstraintWidget2.mBottom.mTarget == null)
                      break label994;
                    localConstraintWidget3 = localConstraintWidget2.mBottom.mTarget.mOwner;
                  }
                  if (localConstraintWidget3 == null)
                    break label998;
                  localSolverVariable4 = localConstraintWidget3.mTop.mSolverVariable;
                  if (i2 != 0)
                    if (localConstraintWidget2.mBottom.mTarget == null)
                      break label1000;
                }
                for (SolverVariable localSolverVariable4 = localConstraintWidget2.mBottom.mTarget.mSolverVariable; ; localSolverVariable4 = null)
                {
                  if ((localSolverVariable3 == null) || (localSolverVariable4 == null))
                    break label1004;
                  paramLinearSystem.addCentering(localConstraintAnchor3.mSolverVariable, localSolverVariable3, i5, 0.5F, localSolverVariable4, localConstraintAnchor4.mSolverVariable, i6, 4);
                  break;
                  localSolverVariable3 = null;
                  break label842;
                  localSolverVariable3 = null;
                  break label871;
                  localConstraintWidget3 = null;
                  break label900;
                  break;
                }
                break label464;
              }
            }
            if (n == 0)
              continue;
            ConstraintAnchor localConstraintAnchor1 = localObject3.mTop;
            ConstraintAnchor localConstraintAnchor2 = localConstraintWidget2.mBottom;
            int i3 = localConstraintAnchor1.getMargin();
            int i4 = localConstraintAnchor2.getMargin();
            SolverVariable localSolverVariable1;
            if (localConstraintWidget1.mTop.mTarget != null)
            {
              localSolverVariable1 = localConstraintWidget1.mTop.mTarget.mSolverVariable;
              if (localConstraintWidget2.mBottom.mTarget == null)
                break label1156;
            }
            for (SolverVariable localSolverVariable2 = localConstraintWidget2.mBottom.mTarget.mSolverVariable; ; localSolverVariable2 = null)
            {
              if ((localSolverVariable1 == null) || (localSolverVariable2 == null))
                break label1160;
              paramLinearSystem.addLowerThan(localConstraintAnchor2.mSolverVariable, localSolverVariable2, -i4, 1);
              paramLinearSystem.addCentering(localConstraintAnchor1.mSolverVariable, localSolverVariable1, i3, localConstraintWidget1.mVerticalBiasPercent, localSolverVariable2, localConstraintAnchor2.mSolverVariable, i4, 4);
              break;
              localSolverVariable1 = null;
              break label1070;
            }
            continue;
          }
          Object localObject4 = null;
          f = 0.0F;
          if (localObject1 != null)
          {
            if (((ConstraintWidget)localObject1).mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
            {
              int i23 = ((ConstraintWidget)localObject1).mTop.getMargin();
              if (localObject4 != null)
                i23 += localObject4.mBottom.getMargin();
              int i24 = 3;
              if (((ConstraintWidget)localObject1).mTop.mTarget.mOwner.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
                i24 = 2;
              paramLinearSystem.addGreaterThan(((ConstraintWidget)localObject1).mTop.mSolverVariable, ((ConstraintWidget)localObject1).mTop.mTarget.mSolverVariable, i23, i24);
              int i25 = ((ConstraintWidget)localObject1).mBottom.getMargin();
              if ((((ConstraintWidget)localObject1).mBottom.mTarget.mOwner.mTop.mTarget != null) && (((ConstraintWidget)localObject1).mBottom.mTarget.mOwner.mTop.mTarget.mOwner == localObject1))
                i25 += ((ConstraintWidget)localObject1).mBottom.mTarget.mOwner.mTop.getMargin();
              int i26 = 3;
              if (((ConstraintWidget)localObject1).mBottom.mTarget.mOwner.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
                i26 = 2;
              paramLinearSystem.addLowerThan(((ConstraintWidget)localObject1).mBottom.mSolverVariable, ((ConstraintWidget)localObject1).mBottom.mTarget.mSolverVariable, -i25, i26);
            }
            while (true)
            {
              localObject4 = localObject1;
              localObject1 = ((ConstraintWidget)localObject1).mVerticalNextWidget;
              break;
              f += ((ConstraintWidget)localObject1).mVerticalWeight;
              ConstraintAnchor localConstraintAnchor9 = ((ConstraintWidget)localObject1).mBottom.mTarget;
              int i22 = 0;
              if (localConstraintAnchor9 != null)
              {
                i22 = ((ConstraintWidget)localObject1).mBottom.getMargin();
                ConstraintWidget localConstraintWidget7 = this.mChainEnds[3];
                if (localObject1 != localConstraintWidget7)
                  i22 += ((ConstraintWidget)localObject1).mBottom.mTarget.mOwner.mTop.getMargin();
              }
              paramLinearSystem.addGreaterThan(((ConstraintWidget)localObject1).mBottom.mSolverVariable, ((ConstraintWidget)localObject1).mTop.mSolverVariable, 0, 1);
              paramLinearSystem.addLowerThan(((ConstraintWidget)localObject1).mBottom.mSolverVariable, ((ConstraintWidget)localObject1).mBottom.mTarget.mSolverVariable, -i22, 1);
            }
          }
          if (k != 1)
            break label1798;
          ConstraintWidget localConstraintWidget6 = this.mMatchConstraintsChainedWidgets[0];
          int i20 = localConstraintWidget6.mTop.getMargin();
          if (localConstraintWidget6.mTop.mTarget != null)
            i20 += localConstraintWidget6.mTop.mTarget.getMargin();
          int i21 = localConstraintWidget6.mBottom.getMargin();
          if (localConstraintWidget6.mBottom.mTarget != null)
            i21 += localConstraintWidget6.mBottom.mTarget.getMargin();
          SolverVariable localSolverVariable12 = localConstraintWidget1.mBottom.mTarget.mSolverVariable;
          if (localConstraintWidget6 == this.mChainEnds[3])
            localSolverVariable12 = this.mChainEnds[1].mBottom.mTarget.mSolverVariable;
          if (localConstraintWidget6.mMatchConstraintDefaultHeight == 1)
          {
            paramLinearSystem.addGreaterThan(localConstraintWidget1.mTop.mSolverVariable, localConstraintWidget1.mTop.mTarget.mSolverVariable, i20, 1);
            paramLinearSystem.addLowerThan(localConstraintWidget1.mBottom.mSolverVariable, localSolverVariable12, -i21, 1);
            paramLinearSystem.addEquality(localConstraintWidget1.mBottom.mSolverVariable, localConstraintWidget1.mTop.mSolverVariable, localConstraintWidget1.getHeight(), 2);
            continue;
          }
          paramLinearSystem.addEquality(localConstraintWidget6.mTop.mSolverVariable, localConstraintWidget6.mTop.mTarget.mSolverVariable, i20, 1);
          paramLinearSystem.addEquality(localConstraintWidget6.mBottom.mSolverVariable, localSolverVariable12, -i21, 1);
        }
        i12 = 0;
        i13 = k - 1;
      }
      while (i12 >= i13);
      label1156: label1160: ConstraintWidget localConstraintWidget4 = this.mMatchConstraintsChainedWidgets[i12];
      label1798: ConstraintWidget localConstraintWidget5 = this.mMatchConstraintsChainedWidgets[(i12 + 1)];
      SolverVariable localSolverVariable7 = localConstraintWidget4.mTop.mSolverVariable;
      SolverVariable localSolverVariable8 = localConstraintWidget4.mBottom.mSolverVariable;
      SolverVariable localSolverVariable9 = localConstraintWidget5.mTop.mSolverVariable;
      SolverVariable localSolverVariable10 = localConstraintWidget5.mBottom.mSolverVariable;
      if (localConstraintWidget5 == this.mChainEnds[3])
        localSolverVariable10 = this.mChainEnds[1].mBottom.mSolverVariable;
      int i14 = localConstraintWidget4.mTop.getMargin();
      if ((localConstraintWidget4.mTop.mTarget != null) && (localConstraintWidget4.mTop.mTarget.mOwner.mBottom.mTarget != null) && (localConstraintWidget4.mTop.mTarget.mOwner.mBottom.mTarget.mOwner == localConstraintWidget4))
        i14 += localConstraintWidget4.mTop.mTarget.mOwner.mBottom.getMargin();
      paramLinearSystem.addGreaterThan(localSolverVariable7, localConstraintWidget4.mTop.mTarget.mSolverVariable, i14, 2);
      int i15 = localConstraintWidget4.mBottom.getMargin();
      if ((localConstraintWidget4.mBottom.mTarget != null) && (localConstraintWidget4.mVerticalNextWidget != null))
        if (localConstraintWidget4.mVerticalNextWidget.mTop.mTarget == null)
          break label2436;
      label2436: for (int i19 = localConstraintWidget4.mVerticalNextWidget.mTop.getMargin(); ; i19 = 0)
      {
        i15 += i19;
        paramLinearSystem.addLowerThan(localSolverVariable8, localConstraintWidget4.mBottom.mTarget.mSolverVariable, -i15, 2);
        if (i12 + 1 == k - 1)
        {
          int i16 = localConstraintWidget5.mTop.getMargin();
          if ((localConstraintWidget5.mTop.mTarget != null) && (localConstraintWidget5.mTop.mTarget.mOwner.mBottom.mTarget != null) && (localConstraintWidget5.mTop.mTarget.mOwner.mBottom.mTarget.mOwner == localConstraintWidget5))
            i16 += localConstraintWidget5.mTop.mTarget.mOwner.mBottom.getMargin();
          paramLinearSystem.addGreaterThan(localSolverVariable9, localConstraintWidget5.mTop.mTarget.mSolverVariable, i16, 2);
          ConstraintAnchor localConstraintAnchor8 = localConstraintWidget5.mBottom;
          if (localConstraintWidget5 == this.mChainEnds[3])
            localConstraintAnchor8 = this.mChainEnds[1].mBottom;
          int i17 = localConstraintAnchor8.getMargin();
          if ((localConstraintAnchor8.mTarget != null) && (localConstraintAnchor8.mTarget.mOwner.mTop.mTarget != null) && (localConstraintAnchor8.mTarget.mOwner.mTop.mTarget.mOwner == localConstraintWidget5))
            i17 += localConstraintAnchor8.mTarget.mOwner.mTop.getMargin();
          SolverVariable localSolverVariable11 = localConstraintAnchor8.mTarget.mSolverVariable;
          int i18 = -i17;
          paramLinearSystem.addLowerThan(localSolverVariable10, localSolverVariable11, i18, 2);
        }
        if (localConstraintWidget1.mMatchConstraintMaxHeight > 0)
          paramLinearSystem.addLowerThan(localSolverVariable8, localSolverVariable7, localConstraintWidget1.mMatchConstraintMaxHeight, 2);
        ArrayRow localArrayRow = paramLinearSystem.createRow();
        localArrayRow.createRowEqualDimension(localConstraintWidget4.mVerticalWeight, f, localConstraintWidget5.mVerticalWeight, localSolverVariable7, localConstraintWidget4.mTop.getMargin(), localSolverVariable8, localConstraintWidget4.mBottom.getMargin(), localSolverVariable9, localConstraintWidget5.mTop.getMargin(), localSolverVariable10, localConstraintWidget5.mBottom.getMargin());
        paramLinearSystem.addConstraint(localArrayRow);
        i12++;
        break;
      }
    }
  }

  private int countMatchConstraintsChainedWidgets(LinearSystem paramLinearSystem, ConstraintWidget[] paramArrayOfConstraintWidget, ConstraintWidget paramConstraintWidget, int paramInt, boolean[] paramArrayOfBoolean)
  {
    int i = 0;
    paramArrayOfBoolean[0] = true;
    paramArrayOfBoolean[1] = false;
    paramArrayOfConstraintWidget[0] = null;
    paramArrayOfConstraintWidget[2] = null;
    paramArrayOfConstraintWidget[1] = null;
    paramArrayOfConstraintWidget[3] = null;
    if (paramInt == 0)
    {
      boolean bool2 = true;
      ConstraintWidget localConstraintWidget5 = paramConstraintWidget;
      ConstraintWidget localConstraintWidget6 = null;
      if ((paramConstraintWidget.mLeft.mTarget != null) && (paramConstraintWidget.mLeft.mTarget.mOwner != this))
        bool2 = false;
      paramConstraintWidget.mHorizontalNextWidget = null;
      int m = paramConstraintWidget.getVisibility();
      ConstraintWidget localConstraintWidget7 = null;
      if (m != 8)
        localConstraintWidget7 = paramConstraintWidget;
      ConstraintWidget localConstraintWidget8 = localConstraintWidget7;
      while (true)
      {
        if (paramConstraintWidget.mRight.mTarget != null)
        {
          paramConstraintWidget.mHorizontalNextWidget = null;
          if (paramConstraintWidget.getVisibility() == 8)
            break label362;
          if (localConstraintWidget7 == null)
            localConstraintWidget7 = paramConstraintWidget;
          if ((localConstraintWidget8 != null) && (localConstraintWidget8 != paramConstraintWidget))
            localConstraintWidget8.mHorizontalNextWidget = paramConstraintWidget;
          localConstraintWidget8 = paramConstraintWidget;
          if ((paramConstraintWidget.getVisibility() != 8) && (paramConstraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT))
          {
            if (paramConstraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
              paramArrayOfBoolean[0] = false;
            if (paramConstraintWidget.mDimensionRatio <= 0.0F)
            {
              paramArrayOfBoolean[0] = false;
              if (i + 1 >= this.mMatchConstraintsChainedWidgets.length)
                this.mMatchConstraintsChainedWidgets = ((ConstraintWidget[])Arrays.copyOf(this.mMatchConstraintsChainedWidgets, 2 * this.mMatchConstraintsChainedWidgets.length));
              ConstraintWidget[] arrayOfConstraintWidget2 = this.mMatchConstraintsChainedWidgets;
              int n = i + 1;
              arrayOfConstraintWidget2[i] = paramConstraintWidget;
              i = n;
            }
          }
          if (paramConstraintWidget.mRight.mTarget.mOwner.mLeft.mTarget != null)
            break label410;
        }
        label362: 
        do
        {
          if ((paramConstraintWidget.mRight.mTarget != null) && (paramConstraintWidget.mRight.mTarget.mOwner != this))
            bool2 = false;
          if ((localConstraintWidget5.mLeft.mTarget == null) || (localConstraintWidget6.mRight.mTarget == null))
            paramArrayOfBoolean[1] = true;
          localConstraintWidget5.mHorizontalChainFixedPosition = bool2;
          localConstraintWidget6.mHorizontalNextWidget = null;
          paramArrayOfConstraintWidget[0] = localConstraintWidget5;
          paramArrayOfConstraintWidget[2] = localConstraintWidget7;
          paramArrayOfConstraintWidget[1] = localConstraintWidget6;
          paramArrayOfConstraintWidget[3] = localConstraintWidget8;
          return i;
          paramLinearSystem.addEquality(paramConstraintWidget.mLeft.mSolverVariable, paramConstraintWidget.mLeft.mTarget.mSolverVariable, 0, 5);
          paramLinearSystem.addEquality(paramConstraintWidget.mRight.mSolverVariable, paramConstraintWidget.mLeft.mSolverVariable, 0, 5);
          break;
        }
        while ((paramConstraintWidget.mRight.mTarget.mOwner.mLeft.mTarget.mOwner != paramConstraintWidget) || (paramConstraintWidget.mRight.mTarget.mOwner == paramConstraintWidget));
        label410: paramConstraintWidget = paramConstraintWidget.mRight.mTarget.mOwner;
        localConstraintWidget6 = paramConstraintWidget;
      }
    }
    boolean bool1 = true;
    ConstraintWidget localConstraintWidget1 = paramConstraintWidget;
    ConstraintWidget localConstraintWidget2 = null;
    if ((paramConstraintWidget.mTop.mTarget != null) && (paramConstraintWidget.mTop.mTarget.mOwner != this))
      bool1 = false;
    paramConstraintWidget.mVerticalNextWidget = null;
    int j = paramConstraintWidget.getVisibility();
    ConstraintWidget localConstraintWidget3 = null;
    if (j != 8)
      localConstraintWidget3 = paramConstraintWidget;
    ConstraintWidget localConstraintWidget4 = localConstraintWidget3;
    while (true)
    {
      if (paramConstraintWidget.mBottom.mTarget != null)
      {
        paramConstraintWidget.mVerticalNextWidget = null;
        if (paramConstraintWidget.getVisibility() == 8)
          break label792;
        if (localConstraintWidget3 == null)
          localConstraintWidget3 = paramConstraintWidget;
        if ((localConstraintWidget4 != null) && (localConstraintWidget4 != paramConstraintWidget))
          localConstraintWidget4.mVerticalNextWidget = paramConstraintWidget;
        localConstraintWidget4 = paramConstraintWidget;
        if ((paramConstraintWidget.getVisibility() != 8) && (paramConstraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT))
        {
          if (paramConstraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
            paramArrayOfBoolean[0] = false;
          if (paramConstraintWidget.mDimensionRatio <= 0.0F)
          {
            paramArrayOfBoolean[0] = false;
            if (i + 1 >= this.mMatchConstraintsChainedWidgets.length)
              this.mMatchConstraintsChainedWidgets = ((ConstraintWidget[])Arrays.copyOf(this.mMatchConstraintsChainedWidgets, 2 * this.mMatchConstraintsChainedWidgets.length));
            ConstraintWidget[] arrayOfConstraintWidget1 = this.mMatchConstraintsChainedWidgets;
            int k = i + 1;
            arrayOfConstraintWidget1[i] = paramConstraintWidget;
            i = k;
          }
        }
        if (paramConstraintWidget.mBottom.mTarget.mOwner.mTop.mTarget != null)
          break label840;
      }
      label792: label840: 
      do
      {
        if ((paramConstraintWidget.mBottom.mTarget != null) && (paramConstraintWidget.mBottom.mTarget.mOwner != this))
          bool1 = false;
        if ((localConstraintWidget1.mTop.mTarget == null) || (localConstraintWidget2.mBottom.mTarget == null))
          paramArrayOfBoolean[1] = true;
        localConstraintWidget1.mVerticalChainFixedPosition = bool1;
        localConstraintWidget2.mVerticalNextWidget = null;
        paramArrayOfConstraintWidget[0] = localConstraintWidget1;
        paramArrayOfConstraintWidget[2] = localConstraintWidget3;
        paramArrayOfConstraintWidget[1] = localConstraintWidget2;
        paramArrayOfConstraintWidget[3] = localConstraintWidget4;
        return i;
        paramLinearSystem.addEquality(paramConstraintWidget.mTop.mSolverVariable, paramConstraintWidget.mTop.mTarget.mSolverVariable, 0, 5);
        paramLinearSystem.addEquality(paramConstraintWidget.mBottom.mSolverVariable, paramConstraintWidget.mTop.mSolverVariable, 0, 5);
        break;
      }
      while ((paramConstraintWidget.mBottom.mTarget.mOwner.mTop.mTarget.mOwner != paramConstraintWidget) || (paramConstraintWidget.mBottom.mTarget.mOwner == paramConstraintWidget));
      paramConstraintWidget = paramConstraintWidget.mBottom.mTarget.mOwner;
      localConstraintWidget2 = paramConstraintWidget;
    }
  }

  public static ConstraintWidgetContainer createContainer(ConstraintWidgetContainer paramConstraintWidgetContainer, String paramString, ArrayList<ConstraintWidget> paramArrayList, int paramInt)
  {
    Rectangle localRectangle = getBounds(paramArrayList);
    if ((localRectangle.width == 0) || (localRectangle.height == 0))
    {
      paramConstraintWidgetContainer = null;
      return paramConstraintWidgetContainer;
    }
    if (paramInt > 0)
    {
      int k = Math.min(localRectangle.x, localRectangle.y);
      if (paramInt > k)
        paramInt = k;
      localRectangle.grow(paramInt, paramInt);
    }
    paramConstraintWidgetContainer.setOrigin(localRectangle.x, localRectangle.y);
    paramConstraintWidgetContainer.setDimension(localRectangle.width, localRectangle.height);
    paramConstraintWidgetContainer.setDebugName(paramString);
    ConstraintWidget localConstraintWidget1 = ((ConstraintWidget)paramArrayList.get(0)).getParent();
    int i = 0;
    int j = paramArrayList.size();
    label116: ConstraintWidget localConstraintWidget2;
    if (i < j)
    {
      localConstraintWidget2 = (ConstraintWidget)paramArrayList.get(i);
      if (localConstraintWidget2.getParent() == localConstraintWidget1)
        break label150;
    }
    while (true)
    {
      i++;
      break label116;
      break;
      label150: paramConstraintWidgetContainer.add(localConstraintWidget2);
      localConstraintWidget2.setX(localConstraintWidget2.getX() - localRectangle.x);
      localConstraintWidget2.setY(localConstraintWidget2.getY() - localRectangle.y);
    }
  }

  private boolean optimize(LinearSystem paramLinearSystem)
  {
    int i = this.mChildren.size();
    int j = 0;
    int k;
    int n;
    int i1;
    while (true)
    {
      k = 0;
      m = 0;
      n = 0;
      i1 = 0;
      if (j >= i)
        break;
      ConstraintWidget localConstraintWidget3 = (ConstraintWidget)this.mChildren.get(j);
      localConstraintWidget3.mHorizontalResolution = -1;
      localConstraintWidget3.mVerticalResolution = -1;
      if ((localConstraintWidget3.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (localConstraintWidget3.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT))
      {
        localConstraintWidget3.mHorizontalResolution = 1;
        localConstraintWidget3.mVerticalResolution = 1;
      }
      j++;
      continue;
      if ((n != 0) || (k != 0))
        break label250;
    }
    for (int m = 1; ; m = 1)
    {
      int i5;
      int i6;
      label130: label175: label230: label240: label250: 
      do
      {
        if (m != 0)
          break label270;
        i5 = n;
        i6 = k;
        n = 0;
        k = 0;
        i1++;
        int i7 = 0;
        ConstraintWidget localConstraintWidget2;
        if (i7 < i)
        {
          localConstraintWidget2 = (ConstraintWidget)this.mChildren.get(i7);
          if (localConstraintWidget2.mHorizontalResolution == -1)
          {
            if (this.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
              break label230;
            localConstraintWidget2.mHorizontalResolution = 1;
          }
          if (localConstraintWidget2.mVerticalResolution == -1)
          {
            if (this.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
              break label240;
            localConstraintWidget2.mVerticalResolution = 1;
          }
        }
        while (true)
        {
          if (localConstraintWidget2.mVerticalResolution == -1)
            n++;
          if (localConstraintWidget2.mHorizontalResolution == -1)
            k++;
          i7++;
          break label130;
          break;
          Optimizer.checkHorizontalSimpleDependency(this, paramLinearSystem, localConstraintWidget2);
          break label175;
          Optimizer.checkVerticalSimpleDependency(this, paramLinearSystem, localConstraintWidget2);
        }
      }
      while ((i5 != n) || (i6 != k));
    }
    label270: int i2 = 0;
    int i3 = 0;
    for (int i4 = 0; i4 < i; i4++)
    {
      ConstraintWidget localConstraintWidget1 = (ConstraintWidget)this.mChildren.get(i4);
      if ((localConstraintWidget1.mHorizontalResolution == 1) || (localConstraintWidget1.mHorizontalResolution == -1))
        i2++;
      if ((localConstraintWidget1.mVerticalResolution != 1) && (localConstraintWidget1.mVerticalResolution != -1))
        continue;
      i3++;
    }
    return (i2 == 0) && (i3 == 0);
  }

  private void resetChains()
  {
    this.mHorizontalChainsSize = 0;
    this.mVerticalChainsSize = 0;
  }

  static int setGroup(ConstraintAnchor paramConstraintAnchor, int paramInt)
  {
    int i = paramConstraintAnchor.mGroup;
    if (paramConstraintAnchor.mOwner.getParent() == null)
      i = paramInt;
    do
      return i;
    while (i <= paramInt);
    paramConstraintAnchor.mGroup = paramInt;
    ConstraintAnchor localConstraintAnchor1 = paramConstraintAnchor.getOpposite();
    ConstraintAnchor localConstraintAnchor2 = paramConstraintAnchor.mTarget;
    if (localConstraintAnchor1 != null)
      paramInt = setGroup(localConstraintAnchor1, paramInt);
    if (localConstraintAnchor2 != null)
      paramInt = setGroup(localConstraintAnchor2, paramInt);
    if (localConstraintAnchor1 != null)
      paramInt = setGroup(localConstraintAnchor1, paramInt);
    paramConstraintAnchor.mGroup = paramInt;
    return paramInt;
  }

  void addChain(ConstraintWidget paramConstraintWidget, int paramInt)
  {
    ConstraintWidget localConstraintWidget = paramConstraintWidget;
    if (paramInt == 0)
    {
      while ((localConstraintWidget.mLeft.mTarget != null) && (localConstraintWidget.mLeft.mTarget.mOwner.mRight.mTarget != null) && (localConstraintWidget.mLeft.mTarget.mOwner.mRight.mTarget == localConstraintWidget.mLeft) && (localConstraintWidget.mLeft.mTarget.mOwner != localConstraintWidget))
        localConstraintWidget = localConstraintWidget.mLeft.mTarget.mOwner;
      addHorizontalChain(localConstraintWidget);
    }
    do
      return;
    while (paramInt != 1);
    while ((localConstraintWidget.mTop.mTarget != null) && (localConstraintWidget.mTop.mTarget.mOwner.mBottom.mTarget != null) && (localConstraintWidget.mTop.mTarget.mOwner.mBottom.mTarget == localConstraintWidget.mTop) && (localConstraintWidget.mTop.mTarget.mOwner != localConstraintWidget))
      localConstraintWidget = localConstraintWidget.mTop.mTarget.mOwner;
    addVerticalChain(localConstraintWidget);
  }

  public boolean addChildrenToSolver(LinearSystem paramLinearSystem, int paramInt)
  {
    addToSolver(paramLinearSystem, paramInt);
    int i = this.mChildren.size();
    int j;
    if ((this.mOptimizationLevel == 2) || (this.mOptimizationLevel == 4))
    {
      boolean bool = optimize(paramLinearSystem);
      j = 0;
      if (bool)
        return false;
    }
    else
    {
      j = 1;
    }
    int k = 0;
    if (k < i)
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)this.mChildren.get(k);
      if ((localConstraintWidget instanceof ConstraintWidgetContainer))
      {
        ConstraintWidget.DimensionBehaviour localDimensionBehaviour1 = localConstraintWidget.mHorizontalDimensionBehaviour;
        ConstraintWidget.DimensionBehaviour localDimensionBehaviour2 = localConstraintWidget.mVerticalDimensionBehaviour;
        if (localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
          localConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        if (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
          localConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        localConstraintWidget.addToSolver(paramLinearSystem, paramInt);
        if (localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
          localConstraintWidget.setHorizontalDimensionBehaviour(localDimensionBehaviour1);
        if (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
          localConstraintWidget.setVerticalDimensionBehaviour(localDimensionBehaviour2);
      }
      while (true)
      {
        k++;
        break;
        if (j != 0)
          Optimizer.checkMatchParent(this, paramLinearSystem, localConstraintWidget);
        localConstraintWidget.addToSolver(paramLinearSystem, paramInt);
      }
    }
    if (this.mHorizontalChainsSize > 0)
      applyHorizontalChain(paramLinearSystem);
    if (this.mVerticalChainsSize > 0)
      applyVerticalChain(paramLinearSystem);
    return true;
  }

  public void findHorizontalWrapRecursive(ConstraintWidget paramConstraintWidget, boolean[] paramArrayOfBoolean)
  {
    if ((paramConstraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (paramConstraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (paramConstraintWidget.mDimensionRatio > 0.0F))
    {
      paramArrayOfBoolean[0] = false;
      return;
    }
    int i = paramConstraintWidget.getOptimizerWrapWidth();
    if ((paramConstraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (paramConstraintWidget.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (paramConstraintWidget.mDimensionRatio > 0.0F))
    {
      paramArrayOfBoolean[0] = false;
      return;
    }
    int j = i;
    int k = i;
    paramConstraintWidget.mHorizontalWrapVisited = true;
    Guideline localGuideline;
    if ((paramConstraintWidget instanceof Guideline))
    {
      localGuideline = (Guideline)paramConstraintWidget;
      if (localGuideline.getOrientation() == 1)
      {
        j = 0;
        if (localGuideline.getRelativeBegin() == -1)
          break label165;
        k = localGuideline.getRelativeBegin();
        break label570;
      }
    }
    label125: ConstraintWidget localConstraintWidget1;
    label165: ConstraintWidget localConstraintWidget2;
    label489: boolean bool2;
    while (true)
    {
      if (paramConstraintWidget.getVisibility() == 8)
      {
        k -= paramConstraintWidget.mWidth;
        j -= paramConstraintWidget.mWidth;
      }
      paramConstraintWidget.mDistToLeft = k;
      paramConstraintWidget.mDistToRight = j;
      return;
      int m = localGuideline.getRelativeEnd();
      k = 0;
      j = 0;
      if (m == -1)
        continue;
      j = localGuideline.getRelativeEnd();
      k = 0;
      continue;
      if ((!paramConstraintWidget.mRight.isConnected()) && (!paramConstraintWidget.mLeft.isConnected()))
      {
        k += paramConstraintWidget.getX();
        continue;
      }
      if ((paramConstraintWidget.mRight.mTarget != null) && (paramConstraintWidget.mLeft.mTarget != null) && ((paramConstraintWidget.mRight.mTarget == paramConstraintWidget.mLeft.mTarget) || ((paramConstraintWidget.mRight.mTarget.mOwner == paramConstraintWidget.mLeft.mTarget.mOwner) && (paramConstraintWidget.mRight.mTarget.mOwner != paramConstraintWidget.mParent))))
      {
        paramArrayOfBoolean[0] = false;
        return;
      }
      ConstraintAnchor localConstraintAnchor1 = paramConstraintWidget.mRight.mTarget;
      localConstraintWidget1 = null;
      if (localConstraintAnchor1 != null)
      {
        localConstraintWidget1 = paramConstraintWidget.mRight.mTarget.mOwner;
        j += paramConstraintWidget.mRight.getMargin();
        if ((!localConstraintWidget1.isRoot()) && (!localConstraintWidget1.mHorizontalWrapVisited))
          findHorizontalWrapRecursive(localConstraintWidget1, paramArrayOfBoolean);
      }
      ConstraintAnchor localConstraintAnchor2 = paramConstraintWidget.mLeft.mTarget;
      localConstraintWidget2 = null;
      if (localConstraintAnchor2 != null)
      {
        localConstraintWidget2 = paramConstraintWidget.mLeft.mTarget.mOwner;
        k += paramConstraintWidget.mLeft.getMargin();
        if ((!localConstraintWidget2.isRoot()) && (!localConstraintWidget2.mHorizontalWrapVisited))
          findHorizontalWrapRecursive(localConstraintWidget2, paramArrayOfBoolean);
      }
      if ((paramConstraintWidget.mRight.mTarget != null) && (!localConstraintWidget1.isRoot()))
      {
        if (paramConstraintWidget.mRight.mTarget.mType != ConstraintAnchor.Type.RIGHT)
          break;
        j += localConstraintWidget1.mDistToRight - localConstraintWidget1.getOptimizerWrapWidth();
        if ((!localConstraintWidget1.mRightHasCentered) && ((localConstraintWidget1.mLeft.mTarget == null) || (localConstraintWidget1.mRight.mTarget == null) || (localConstraintWidget1.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)))
          break label758;
        bool2 = true;
        label533: paramConstraintWidget.mRightHasCentered = bool2;
        if (paramConstraintWidget.mRightHasCentered)
        {
          if (localConstraintWidget1.mLeft.mTarget != null)
            break label764;
          label557: j += j - localConstraintWidget1.mDistToRight;
        }
      }
      label570: if ((paramConstraintWidget.mLeft.mTarget == null) || (localConstraintWidget2.isRoot()))
        continue;
      if (paramConstraintWidget.mLeft.mTarget.getType() != ConstraintAnchor.Type.LEFT)
        break label782;
      k += localConstraintWidget2.mDistToLeft - localConstraintWidget2.getOptimizerWrapWidth();
      label620: boolean bool1;
      if (!localConstraintWidget2.mLeftHasCentered)
      {
        ConstraintAnchor localConstraintAnchor3 = localConstraintWidget2.mLeft.mTarget;
        bool1 = false;
        if (localConstraintAnchor3 != null)
        {
          ConstraintAnchor localConstraintAnchor4 = localConstraintWidget2.mRight.mTarget;
          bool1 = false;
          if (localConstraintAnchor4 != null)
          {
            ConstraintWidget.DimensionBehaviour localDimensionBehaviour1 = localConstraintWidget2.mHorizontalDimensionBehaviour;
            ConstraintWidget.DimensionBehaviour localDimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
            bool1 = false;
            if (localDimensionBehaviour1 == localDimensionBehaviour2);
          }
        }
      }
      else
      {
        bool1 = true;
      }
      paramConstraintWidget.mLeftHasCentered = bool1;
      if (!paramConstraintWidget.mLeftHasCentered)
        continue;
      if (localConstraintWidget2.mRight.mTarget != null)
        break label811;
    }
    while (true)
    {
      k += k - localConstraintWidget2.mDistToLeft;
      break label125;
      if (paramConstraintWidget.mRight.mTarget.getType() != ConstraintAnchor.Type.LEFT)
        break label489;
      j += localConstraintWidget1.mDistToRight;
      break label489;
      label758: bool2 = false;
      break label533;
      label764: if (localConstraintWidget1.mLeft.mTarget.mOwner == paramConstraintWidget)
        break;
      break label557;
      label782: if (paramConstraintWidget.mLeft.mTarget.getType() != ConstraintAnchor.Type.RIGHT)
        break label620;
      k += localConstraintWidget2.mDistToLeft;
      break label620;
      label811: if (localConstraintWidget2.mRight.mTarget.mOwner == paramConstraintWidget)
        break label125;
    }
  }

  public void findVerticalWrapRecursive(ConstraintWidget paramConstraintWidget, boolean[] paramArrayOfBoolean)
  {
    if ((paramConstraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (paramConstraintWidget.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (paramConstraintWidget.mDimensionRatio > 0.0F))
    {
      paramArrayOfBoolean[0] = false;
      return;
    }
    int i = paramConstraintWidget.getOptimizerWrapHeight();
    int j = i;
    int k = i;
    paramConstraintWidget.mVerticalWrapVisited = true;
    Guideline localGuideline;
    if ((paramConstraintWidget instanceof Guideline))
    {
      localGuideline = (Guideline)paramConstraintWidget;
      if (localGuideline.getOrientation() == 0)
      {
        k = 0;
        if (localGuideline.getRelativeBegin() == -1)
          break label130;
        j = localGuideline.getRelativeBegin();
        break label690;
      }
    }
    label90: ConstraintWidget localConstraintWidget1;
    label130: ConstraintWidget localConstraintWidget2;
    label579: boolean bool4;
    while (true)
    {
      if (paramConstraintWidget.getVisibility() == 8)
      {
        j -= paramConstraintWidget.mHeight;
        k -= paramConstraintWidget.mHeight;
      }
      paramConstraintWidget.mDistToTop = j;
      paramConstraintWidget.mDistToBottom = k;
      return;
      int i1 = localGuideline.getRelativeEnd();
      k = 0;
      j = 0;
      if (i1 == -1)
        continue;
      k = localGuideline.getRelativeEnd();
      j = 0;
      continue;
      if ((paramConstraintWidget.mBaseline.mTarget == null) && (paramConstraintWidget.mTop.mTarget == null) && (paramConstraintWidget.mBottom.mTarget == null))
      {
        j += paramConstraintWidget.getY();
        continue;
      }
      if ((paramConstraintWidget.mBottom.mTarget != null) && (paramConstraintWidget.mTop.mTarget != null) && ((paramConstraintWidget.mBottom.mTarget == paramConstraintWidget.mTop.mTarget) || ((paramConstraintWidget.mBottom.mTarget.mOwner == paramConstraintWidget.mTop.mTarget.mOwner) && (paramConstraintWidget.mBottom.mTarget.mOwner != paramConstraintWidget.mParent))))
      {
        paramArrayOfBoolean[0] = false;
        return;
      }
      if (paramConstraintWidget.mBaseline.isConnected())
      {
        ConstraintWidget localConstraintWidget5 = paramConstraintWidget.mBaseline.mTarget.getOwner();
        if (!localConstraintWidget5.mVerticalWrapVisited)
          findVerticalWrapRecursive(localConstraintWidget5, paramArrayOfBoolean);
        int m = Math.max(i + (localConstraintWidget5.mDistToTop - localConstraintWidget5.mHeight), i);
        int n = Math.max(i + (localConstraintWidget5.mDistToBottom - localConstraintWidget5.mHeight), i);
        if (paramConstraintWidget.getVisibility() == 8)
        {
          m -= paramConstraintWidget.mHeight;
          n -= paramConstraintWidget.mHeight;
        }
        paramConstraintWidget.mDistToTop = m;
        paramConstraintWidget.mDistToBottom = n;
        return;
      }
      boolean bool1 = paramConstraintWidget.mTop.isConnected();
      localConstraintWidget1 = null;
      if (bool1)
      {
        localConstraintWidget1 = paramConstraintWidget.mTop.mTarget.getOwner();
        j += paramConstraintWidget.mTop.getMargin();
        if ((!localConstraintWidget1.isRoot()) && (!localConstraintWidget1.mVerticalWrapVisited))
          findVerticalWrapRecursive(localConstraintWidget1, paramArrayOfBoolean);
      }
      boolean bool2 = paramConstraintWidget.mBottom.isConnected();
      localConstraintWidget2 = null;
      if (bool2)
      {
        localConstraintWidget2 = paramConstraintWidget.mBottom.mTarget.getOwner();
        k += paramConstraintWidget.mBottom.getMargin();
        if ((!localConstraintWidget2.isRoot()) && (!localConstraintWidget2.mVerticalWrapVisited))
          findVerticalWrapRecursive(localConstraintWidget2, paramArrayOfBoolean);
      }
      if ((paramConstraintWidget.mTop.mTarget != null) && (!localConstraintWidget1.isRoot()))
      {
        if (paramConstraintWidget.mTop.mTarget.getType() != ConstraintAnchor.Type.TOP)
          break;
        j += localConstraintWidget1.mDistToTop - localConstraintWidget1.getOptimizerWrapHeight();
        if ((!localConstraintWidget1.mTopHasCentered) && ((localConstraintWidget1.mTop.mTarget == null) || (localConstraintWidget1.mTop.mTarget.mOwner == paramConstraintWidget) || (localConstraintWidget1.mBottom.mTarget == null) || (localConstraintWidget1.mBottom.mTarget.mOwner == paramConstraintWidget) || (localConstraintWidget1.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)))
          break label922;
        bool4 = true;
        label653: paramConstraintWidget.mTopHasCentered = bool4;
        if (paramConstraintWidget.mTopHasCentered)
        {
          if (localConstraintWidget1.mBottom.mTarget != null)
            break label928;
          label677: j += j - localConstraintWidget1.mDistToTop;
        }
      }
      label690: if ((paramConstraintWidget.mBottom.mTarget == null) || (localConstraintWidget2.isRoot()))
        continue;
      if (paramConstraintWidget.mBottom.mTarget.getType() != ConstraintAnchor.Type.BOTTOM)
        break label946;
      k += localConstraintWidget2.mDistToBottom - localConstraintWidget2.getOptimizerWrapHeight();
      label740: boolean bool3;
      if (!localConstraintWidget2.mBottomHasCentered)
      {
        ConstraintAnchor localConstraintAnchor1 = localConstraintWidget2.mTop.mTarget;
        bool3 = false;
        if (localConstraintAnchor1 != null)
        {
          ConstraintWidget localConstraintWidget3 = localConstraintWidget2.mTop.mTarget.mOwner;
          bool3 = false;
          if (localConstraintWidget3 != paramConstraintWidget)
          {
            ConstraintAnchor localConstraintAnchor2 = localConstraintWidget2.mBottom.mTarget;
            bool3 = false;
            if (localConstraintAnchor2 != null)
            {
              ConstraintWidget localConstraintWidget4 = localConstraintWidget2.mBottom.mTarget.mOwner;
              bool3 = false;
              if (localConstraintWidget4 != paramConstraintWidget)
              {
                ConstraintWidget.DimensionBehaviour localDimensionBehaviour1 = localConstraintWidget2.mVerticalDimensionBehaviour;
                ConstraintWidget.DimensionBehaviour localDimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                bool3 = false;
                if (localDimensionBehaviour1 == localDimensionBehaviour2);
              }
            }
          }
        }
      }
      else
      {
        bool3 = true;
      }
      paramConstraintWidget.mBottomHasCentered = bool3;
      if (!paramConstraintWidget.mBottomHasCentered)
        continue;
      if (localConstraintWidget2.mTop.mTarget != null)
        break label975;
    }
    while (true)
    {
      k += k - localConstraintWidget2.mDistToBottom;
      break label90;
      if (paramConstraintWidget.mTop.mTarget.getType() != ConstraintAnchor.Type.BOTTOM)
        break label579;
      j += localConstraintWidget1.mDistToTop;
      break label579;
      label922: bool4 = false;
      break label653;
      label928: if (localConstraintWidget1.mBottom.mTarget.mOwner == paramConstraintWidget)
        break;
      break label677;
      label946: if (paramConstraintWidget.mBottom.mTarget.getType() != ConstraintAnchor.Type.TOP)
        break label740;
      k += localConstraintWidget2.mDistToBottom;
      break label740;
      label975: if (localConstraintWidget2.mTop.mTarget.mOwner == paramConstraintWidget)
        break label90;
    }
  }

  public void findWrapSize(ArrayList<ConstraintWidget> paramArrayList, boolean[] paramArrayOfBoolean)
  {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = paramArrayList.size();
    paramArrayOfBoolean[0] = true;
    int i3 = 0;
    ConstraintWidget localConstraintWidget2;
    while (true)
      if (i3 < i2)
      {
        localConstraintWidget2 = (ConstraintWidget)paramArrayList.get(i3);
        if (localConstraintWidget2.isRoot())
        {
          i3++;
          continue;
        }
        if (!localConstraintWidget2.mHorizontalWrapVisited)
          findHorizontalWrapRecursive(localConstraintWidget2, paramArrayOfBoolean);
        if (!localConstraintWidget2.mVerticalWrapVisited)
          findVerticalWrapRecursive(localConstraintWidget2, paramArrayOfBoolean);
        if (paramArrayOfBoolean[0] != 0)
          break;
      }
    while (true)
    {
      return;
      int i7 = localConstraintWidget2.mDistToLeft + localConstraintWidget2.mDistToRight - localConstraintWidget2.getWidth();
      int i8 = localConstraintWidget2.mDistToTop + localConstraintWidget2.mDistToBottom - localConstraintWidget2.getHeight();
      if (localConstraintWidget2.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)
        i7 = localConstraintWidget2.getWidth() + localConstraintWidget2.mLeft.mMargin + localConstraintWidget2.mRight.mMargin;
      if (localConstraintWidget2.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)
        i8 = localConstraintWidget2.getHeight() + localConstraintWidget2.mTop.mMargin + localConstraintWidget2.mBottom.mMargin;
      if (localConstraintWidget2.getVisibility() == 8)
      {
        i7 = 0;
        i8 = 0;
      }
      j = Math.max(j, localConstraintWidget2.mDistToLeft);
      k = Math.max(k, localConstraintWidget2.mDistToRight);
      m = Math.max(m, localConstraintWidget2.mDistToBottom);
      i = Math.max(i, localConstraintWidget2.mDistToTop);
      n = Math.max(n, i7);
      i1 = Math.max(i1, i8);
      break;
      int i4 = Math.max(j, k);
      this.mWrapWidth = Math.max(this.mMinWidth, Math.max(i4, n));
      int i5 = Math.max(i, m);
      this.mWrapHeight = Math.max(this.mMinHeight, Math.max(i5, i1));
      for (int i6 = 0; i6 < i2; i6++)
      {
        ConstraintWidget localConstraintWidget1 = (ConstraintWidget)paramArrayList.get(i6);
        localConstraintWidget1.mHorizontalWrapVisited = false;
        localConstraintWidget1.mVerticalWrapVisited = false;
        localConstraintWidget1.mLeftHasCentered = false;
        localConstraintWidget1.mRightHasCentered = false;
        localConstraintWidget1.mTopHasCentered = false;
        localConstraintWidget1.mBottomHasCentered = false;
      }
    }
  }

  public ArrayList<Guideline> getHorizontalGuidelines()
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = this.mChildren.size();
    while (i < j)
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)this.mChildren.get(i);
      if ((localConstraintWidget instanceof Guideline))
      {
        Guideline localGuideline = (Guideline)localConstraintWidget;
        if (localGuideline.getOrientation() == 0)
          localArrayList.add(localGuideline);
      }
      i++;
    }
    return localArrayList;
  }

  public LinearSystem getSystem()
  {
    return this.mSystem;
  }

  public String getType()
  {
    return "ConstraintLayout";
  }

  public ArrayList<Guideline> getVerticalGuidelines()
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = this.mChildren.size();
    while (i < j)
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)this.mChildren.get(i);
      if ((localConstraintWidget instanceof Guideline))
      {
        Guideline localGuideline = (Guideline)localConstraintWidget;
        if (localGuideline.getOrientation() == 1)
          localArrayList.add(localGuideline);
      }
      i++;
    }
    return localArrayList;
  }

  public boolean handlesInternalConstraints()
  {
    return false;
  }

  public boolean isHeightMeasuredTooSmall()
  {
    return this.mHeightMeasuredTooSmall;
  }

  public boolean isWidthMeasuredTooSmall()
  {
    return this.mWidthMeasuredTooSmall;
  }

  public void layout()
  {
    int i = this.mX;
    int j = this.mY;
    int k = Math.max(0, getWidth());
    int m = Math.max(0, getHeight());
    this.mWidthMeasuredTooSmall = false;
    this.mHeightMeasuredTooSmall = false;
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour1;
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour2;
    int i1;
    if (this.mParent != null)
    {
      if (this.mSnapshot == null)
      {
        Snapshot localSnapshot = new Snapshot(this);
        this.mSnapshot = localSnapshot;
      }
      this.mSnapshot.updateFrom(this);
      setX(this.mPaddingLeft);
      setY(this.mPaddingTop);
      resetAnchors();
      resetSolverVariables(this.mSystem.getCache());
      localDimensionBehaviour1 = this.mVerticalDimensionBehaviour;
      localDimensionBehaviour2 = this.mHorizontalDimensionBehaviour;
      int n = this.mOptimizationLevel;
      i1 = 0;
      if (n == 2)
        if (this.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
        {
          ConstraintWidget.DimensionBehaviour localDimensionBehaviour4 = this.mHorizontalDimensionBehaviour;
          ConstraintWidget.DimensionBehaviour localDimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
          i1 = 0;
          if (localDimensionBehaviour4 != localDimensionBehaviour5);
        }
        else
        {
          findWrapSize(this.mChildren, this.flags);
          i1 = this.flags[0];
          if ((k > 0) && (m > 0) && ((this.mWrapWidth > k) || (this.mWrapHeight > m)))
            i1 = 0;
          if (i1 != 0)
          {
            if (this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
            {
              this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
              if ((k <= 0) || (k >= this.mWrapWidth))
                break label373;
              this.mWidthMeasuredTooSmall = true;
              setWidth(k);
            }
            label259: if (this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
            {
              this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
              if ((m <= 0) || (m >= this.mWrapHeight))
                break label391;
              this.mHeightMeasuredTooSmall = true;
              setHeight(m);
            }
          }
        }
    }
    int i2;
    while (true)
    {
      resetChains();
      i2 = this.mChildren.size();
      for (int i3 = 0; i3 < i2; i3++)
      {
        ConstraintWidget localConstraintWidget3 = (ConstraintWidget)this.mChildren.get(i3);
        if (!(localConstraintWidget3 instanceof WidgetContainer))
          continue;
        ((WidgetContainer)localConstraintWidget3).layout();
      }
      this.mX = 0;
      this.mY = 0;
      break;
      label373: setWidth(Math.max(this.mMinWidth, this.mWrapWidth));
      break label259;
      label391: setHeight(Math.max(this.mMinHeight, this.mWrapHeight));
    }
    boolean bool = true;
    int i4 = 0;
    while (bool)
    {
      i4++;
      try
      {
        this.mSystem.reset();
        bool = addChildrenToSolver(this.mSystem, 2147483647);
        if (bool)
          this.mSystem.minimize();
        if (bool)
        {
          updateChildrenFromSolver(this.mSystem, 2147483647, this.flags);
          bool = false;
          if (i4 >= 8)
            break label800;
          int i10 = this.flags[2];
          bool = false;
          if (i10 == 0)
            break label800;
          i11 = 0;
          i12 = 0;
          for (int i13 = 0; i13 < i2; i13++)
          {
            ConstraintWidget localConstraintWidget2 = (ConstraintWidget)this.mChildren.get(i13);
            i11 = Math.max(i11, localConstraintWidget2.mX + localConstraintWidget2.getWidth());
            i12 = Math.max(i12, localConstraintWidget2.mY + localConstraintWidget2.getHeight());
          }
        }
      }
      catch (Exception localException)
      {
        int i11;
        int i12;
        while (true)
          localException.printStackTrace();
        updateFromSolver(this.mSystem, 2147483647);
        for (int i7 = 0; i7 < i2; i7++)
        {
          ConstraintWidget localConstraintWidget1 = (ConstraintWidget)this.mChildren.get(i7);
          if ((localConstraintWidget1.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localConstraintWidget1.getWidth() < localConstraintWidget1.getWrapWidth()))
          {
            this.flags[2] = true;
            break;
          }
          if ((localConstraintWidget1.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (localConstraintWidget1.getHeight() >= localConstraintWidget1.getWrapHeight()))
            continue;
          this.flags[2] = true;
          break;
        }
        int i14 = Math.max(this.mMinWidth, i11);
        int i15 = Math.max(this.mMinHeight, i12);
        ConstraintWidget.DimensionBehaviour localDimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        bool = false;
        if (localDimensionBehaviour2 == localDimensionBehaviour3)
        {
          int i16 = getWidth();
          bool = false;
          if (i16 < i14)
          {
            setWidth(i14);
            this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            i1 = 1;
            bool = true;
          }
        }
        if ((localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (getHeight() < i15))
        {
          setHeight(i15);
          this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
          i1 = 1;
          bool = true;
        }
        label800: int i8 = Math.max(this.mMinWidth, getWidth());
        if (i8 > getWidth())
        {
          setWidth(i8);
          this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
          i1 = 1;
          bool = true;
        }
        int i9 = Math.max(this.mMinHeight, getHeight());
        if (i9 > getHeight())
        {
          setHeight(i9);
          this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
          i1 = 1;
          bool = true;
        }
      }
      if (i1 != 0)
        continue;
      if ((this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (k > 0) && (getWidth() > k))
      {
        this.mWidthMeasuredTooSmall = true;
        i1 = 1;
        this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
        setWidth(k);
        bool = true;
      }
      if ((this.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || (m <= 0) || (getHeight() <= m))
        continue;
      this.mHeightMeasuredTooSmall = true;
      i1 = 1;
      this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
      setHeight(m);
      bool = true;
    }
    if (this.mParent != null)
    {
      int i5 = Math.max(this.mMinWidth, getWidth());
      int i6 = Math.max(this.mMinHeight, getHeight());
      this.mSnapshot.applyTo(this);
      setWidth(i5 + this.mPaddingLeft + this.mPaddingRight);
      setHeight(i6 + this.mPaddingTop + this.mPaddingBottom);
    }
    while (true)
    {
      if (i1 != 0)
      {
        this.mHorizontalDimensionBehaviour = localDimensionBehaviour2;
        this.mVerticalDimensionBehaviour = localDimensionBehaviour1;
      }
      resetSolverVariables(this.mSystem.getCache());
      if (this == getRootConstraintContainer())
        updateDrawPosition();
      return;
      this.mX = i;
      this.mY = j;
    }
  }

  public int layoutFindGroups()
  {
    ConstraintAnchor.Type[] arrayOfType = new ConstraintAnchor.Type[5];
    arrayOfType[0] = ConstraintAnchor.Type.LEFT;
    arrayOfType[1] = ConstraintAnchor.Type.RIGHT;
    arrayOfType[2] = ConstraintAnchor.Type.TOP;
    arrayOfType[3] = ConstraintAnchor.Type.BASELINE;
    arrayOfType[4] = ConstraintAnchor.Type.BOTTOM;
    int i = 1;
    int j = this.mChildren.size();
    int k = 0;
    if (k < j)
    {
      ConstraintWidget localConstraintWidget3 = (ConstraintWidget)this.mChildren.get(k);
      ConstraintAnchor localConstraintAnchor9 = localConstraintWidget3.mLeft;
      label96: ConstraintAnchor localConstraintAnchor10;
      label124: ConstraintAnchor localConstraintAnchor11;
      label152: ConstraintAnchor localConstraintAnchor12;
      label180: ConstraintAnchor localConstraintAnchor13;
      if (localConstraintAnchor9.mTarget != null)
      {
        if (setGroup(localConstraintAnchor9, i) == i)
          i++;
        localConstraintAnchor10 = localConstraintWidget3.mTop;
        if (localConstraintAnchor10.mTarget == null)
          break label225;
        if (setGroup(localConstraintAnchor10, i) == i)
          i++;
        localConstraintAnchor11 = localConstraintWidget3.mRight;
        if (localConstraintAnchor11.mTarget == null)
          break label236;
        if (setGroup(localConstraintAnchor11, i) == i)
          i++;
        localConstraintAnchor12 = localConstraintWidget3.mBottom;
        if (localConstraintAnchor12.mTarget == null)
          break label247;
        if (setGroup(localConstraintAnchor12, i) == i)
          i++;
        localConstraintAnchor13 = localConstraintWidget3.mBaseline;
        if (localConstraintAnchor13.mTarget == null)
          break label258;
        if (setGroup(localConstraintAnchor13, i) == i)
          i++;
      }
      while (true)
      {
        k++;
        break;
        localConstraintAnchor9.mGroup = 2147483647;
        break label96;
        label225: localConstraintAnchor10.mGroup = 2147483647;
        break label124;
        label236: localConstraintAnchor11.mGroup = 2147483647;
        break label152;
        label247: localConstraintAnchor12.mGroup = 2147483647;
        break label180;
        label258: localConstraintAnchor13.mGroup = 2147483647;
      }
    }
    int m = 1;
    int n = 0;
    int i1 = 0;
    while (m != 0)
    {
      m = 0;
      n++;
      for (int i14 = 0; i14 < j; i14++)
      {
        ConstraintWidget localConstraintWidget2 = (ConstraintWidget)this.mChildren.get(i14);
        int i15 = 0;
        if (i15 >= arrayOfType.length)
          continue;
        ConstraintAnchor.Type localType = arrayOfType[i15];
        int i16 = 2.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[localType.ordinal()];
        ConstraintAnchor localConstraintAnchor6 = null;
        label380: ConstraintAnchor localConstraintAnchor7;
        switch (i16)
        {
        default:
          localConstraintAnchor7 = localConstraintAnchor6.mTarget;
          if (localConstraintAnchor7 != null)
            break;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        }
        int i18;
        label492: ConstraintAnchor localConstraintAnchor8;
        do
        {
          i15++;
          break;
          localConstraintAnchor6 = localConstraintWidget2.mLeft;
          break label380;
          localConstraintAnchor6 = localConstraintWidget2.mTop;
          break label380;
          localConstraintAnchor6 = localConstraintWidget2.mRight;
          break label380;
          localConstraintAnchor6 = localConstraintWidget2.mBottom;
          break label380;
          localConstraintAnchor6 = localConstraintWidget2.mBaseline;
          break label380;
          if ((localConstraintAnchor7.mOwner.getParent() != null) && (localConstraintAnchor7.mGroup != localConstraintAnchor6.mGroup))
          {
            if (localConstraintAnchor6.mGroup <= localConstraintAnchor7.mGroup)
              break label580;
            i18 = localConstraintAnchor7.mGroup;
            localConstraintAnchor6.mGroup = i18;
            localConstraintAnchor7.mGroup = i18;
            i1++;
            m = 1;
          }
          localConstraintAnchor8 = localConstraintAnchor7.getOpposite();
        }
        while ((localConstraintAnchor8 == null) || (localConstraintAnchor8.mGroup == localConstraintAnchor6.mGroup));
        if (localConstraintAnchor6.mGroup > localConstraintAnchor8.mGroup);
        for (int i17 = localConstraintAnchor8.mGroup; ; i17 = localConstraintAnchor6.mGroup)
        {
          localConstraintAnchor6.mGroup = i17;
          localConstraintAnchor8.mGroup = i17;
          i1++;
          m = 1;
          break;
          label580: i18 = localConstraintAnchor6.mGroup;
          break label492;
        }
      }
    }
    int[] arrayOfInt = new int[1 + this.mChildren.size() * arrayOfType.length];
    Arrays.fill(arrayOfInt, -1);
    int i2 = 0;
    int i3 = 0;
    ConstraintWidget localConstraintWidget1;
    int i4;
    if (i2 < j)
    {
      localConstraintWidget1 = (ConstraintWidget)this.mChildren.get(i2);
      ConstraintAnchor localConstraintAnchor1 = localConstraintWidget1.mLeft;
      if (localConstraintAnchor1.mGroup == 2147483647)
        break label975;
      int i13 = localConstraintAnchor1.mGroup;
      if (arrayOfInt[i13] != -1)
        break label968;
      i4 = i3 + 1;
      arrayOfInt[i13] = i3;
      label701: localConstraintAnchor1.mGroup = arrayOfInt[i13];
    }
    while (true)
    {
      ConstraintAnchor localConstraintAnchor2 = localConstraintWidget1.mTop;
      if (localConstraintAnchor2.mGroup != 2147483647)
      {
        int i11 = localConstraintAnchor2.mGroup;
        if (arrayOfInt[i11] == -1)
        {
          int i12 = i4 + 1;
          arrayOfInt[i11] = i4;
          i4 = i12;
        }
        localConstraintAnchor2.mGroup = arrayOfInt[i11];
      }
      ConstraintAnchor localConstraintAnchor3 = localConstraintWidget1.mRight;
      if (localConstraintAnchor3.mGroup != 2147483647)
      {
        int i9 = localConstraintAnchor3.mGroup;
        if (arrayOfInt[i9] == -1)
        {
          int i10 = i4 + 1;
          arrayOfInt[i9] = i4;
          i4 = i10;
        }
        localConstraintAnchor3.mGroup = arrayOfInt[i9];
      }
      ConstraintAnchor localConstraintAnchor4 = localConstraintWidget1.mBottom;
      if (localConstraintAnchor4.mGroup != 2147483647)
      {
        int i7 = localConstraintAnchor4.mGroup;
        if (arrayOfInt[i7] == -1)
        {
          int i8 = i4 + 1;
          arrayOfInt[i7] = i4;
          i4 = i8;
        }
        localConstraintAnchor4.mGroup = arrayOfInt[i7];
      }
      ConstraintAnchor localConstraintAnchor5 = localConstraintWidget1.mBaseline;
      if (localConstraintAnchor5.mGroup != 2147483647)
      {
        int i5 = localConstraintAnchor5.mGroup;
        if (arrayOfInt[i5] == -1)
        {
          int i6 = i4 + 1;
          arrayOfInt[i5] = i4;
          i4 = i6;
        }
        localConstraintAnchor5.mGroup = arrayOfInt[i5];
      }
      i2++;
      i3 = i4;
      break;
      return i3;
      label968: i4 = i3;
      break label701;
      label975: i4 = i3;
    }
  }

  public int layoutFindGroupsSimple()
  {
    int i = this.mChildren.size();
    for (int j = 0; j < i; j++)
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)this.mChildren.get(j);
      localConstraintWidget.mLeft.mGroup = 0;
      localConstraintWidget.mRight.mGroup = 0;
      localConstraintWidget.mTop.mGroup = 1;
      localConstraintWidget.mBottom.mGroup = 1;
      localConstraintWidget.mBaseline.mGroup = 1;
    }
    return 2;
  }

  public void layoutWithGroup(int paramInt)
  {
    int i = this.mX;
    int j = this.mY;
    if (this.mParent != null)
    {
      if (this.mSnapshot == null)
        this.mSnapshot = new Snapshot(this);
      this.mSnapshot.updateFrom(this);
      this.mX = 0;
      this.mY = 0;
      resetAnchors();
      resetSolverVariables(this.mSystem.getCache());
    }
    while (true)
    {
      int k = this.mChildren.size();
      for (int m = 0; m < k; m++)
      {
        ConstraintWidget localConstraintWidget = (ConstraintWidget)this.mChildren.get(m);
        if (!(localConstraintWidget instanceof WidgetContainer))
          continue;
        ((WidgetContainer)localConstraintWidget).layout();
      }
      this.mX = 0;
      this.mY = 0;
    }
    this.mLeft.mGroup = 0;
    this.mRight.mGroup = 0;
    this.mTop.mGroup = 1;
    this.mBottom.mGroup = 1;
    this.mSystem.reset();
    int n = 0;
    while (true)
      if (n < paramInt)
        try
        {
          addToSolver(this.mSystem, n);
          this.mSystem.minimize();
          updateFromSolver(this.mSystem, n);
          updateFromSolver(this.mSystem, -2);
          n++;
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
    if (this.mParent != null)
    {
      int i1 = getWidth();
      int i2 = getHeight();
      this.mSnapshot.applyTo(this);
      setWidth(i1);
      setHeight(i2);
    }
    while (true)
    {
      if (this == getRootConstraintContainer())
        updateDrawPosition();
      return;
      this.mX = i;
      this.mY = j;
    }
  }

  public void reset()
  {
    this.mSystem.reset();
    this.mPaddingLeft = 0;
    this.mPaddingRight = 0;
    this.mPaddingTop = 0;
    this.mPaddingBottom = 0;
    super.reset();
  }

  public void setOptimizationLevel(int paramInt)
  {
    this.mOptimizationLevel = paramInt;
  }

  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mPaddingLeft = paramInt1;
    this.mPaddingTop = paramInt2;
    this.mPaddingRight = paramInt3;
    this.mPaddingBottom = paramInt4;
  }

  public void updateChildrenFromSolver(LinearSystem paramLinearSystem, int paramInt, boolean[] paramArrayOfBoolean)
  {
    paramArrayOfBoolean[2] = false;
    updateFromSolver(paramLinearSystem, paramInt);
    int i = this.mChildren.size();
    for (int j = 0; j < i; j++)
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)this.mChildren.get(j);
      localConstraintWidget.updateFromSolver(paramLinearSystem, paramInt);
      if ((localConstraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localConstraintWidget.getWidth() < localConstraintWidget.getWrapWidth()))
        paramArrayOfBoolean[2] = true;
      if ((localConstraintWidget.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (localConstraintWidget.getHeight() >= localConstraintWidget.getWrapHeight()))
        continue;
      paramArrayOfBoolean[2] = true;
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.solver.widgets.ConstraintWidgetContainer
 * JD-Core Version:    0.6.0
 */