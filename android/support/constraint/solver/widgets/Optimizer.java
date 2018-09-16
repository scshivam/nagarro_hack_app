package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;

public class Optimizer
{
  static void applyDirectResolutionHorizontalChain(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, int paramInt, ConstraintWidget paramConstraintWidget)
  {
    ConstraintWidget localConstraintWidget1 = paramConstraintWidget;
    int i = 0;
    ConstraintWidget localConstraintWidget2 = null;
    int j = 0;
    float f1 = 0.0F;
    label31: label77: label208: label211: 
    while (paramConstraintWidget != null)
    {
      int i1;
      int i3;
      int i5;
      if (paramConstraintWidget.getVisibility() == 8)
      {
        i1 = 1;
        if (i1 == 0)
        {
          j++;
          if (paramConstraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
            break label196;
          int i2 = i + paramConstraintWidget.getWidth();
          if (paramConstraintWidget.mLeft.mTarget == null)
            break label184;
          i3 = paramConstraintWidget.mLeft.getMargin();
          int i4 = i2 + i3;
          if (paramConstraintWidget.mRight.mTarget == null)
            break label190;
          i5 = paramConstraintWidget.mRight.getMargin();
          label103: i = i4 + i5;
        }
        label110: localConstraintWidget2 = paramConstraintWidget;
        if (paramConstraintWidget.mRight.mTarget == null)
          break label208;
      }
      for (paramConstraintWidget = paramConstraintWidget.mRight.mTarget.mOwner; ; paramConstraintWidget = null)
      {
        if ((paramConstraintWidget == null) || ((paramConstraintWidget.mLeft.mTarget != null) && ((paramConstraintWidget.mLeft.mTarget == null) || (paramConstraintWidget.mLeft.mTarget.mOwner == localConstraintWidget2))))
          break label211;
        paramConstraintWidget = null;
        break;
        i1 = 0;
        break label31;
        i3 = 0;
        break label77;
        i5 = 0;
        break label103;
        f1 += paramConstraintWidget.mHorizontalWeight;
        break label110;
      }
    }
    label184: label190: label196: int k = 0;
    if (localConstraintWidget2 != null)
    {
      if (localConstraintWidget2.mRight.mTarget == null)
        break label541;
      k = localConstraintWidget2.mRight.mTarget.mOwner.getX();
      if ((localConstraintWidget2.mRight.mTarget != null) && (localConstraintWidget2.mRight.mTarget.mOwner == paramConstraintWidgetContainer))
        k = paramConstraintWidgetContainer.getRight();
    }
    float f2 = k - 0 - i;
    float f3 = f2 / (j + 1);
    ConstraintWidget localConstraintWidget3 = localConstraintWidget1;
    float f4;
    label313: int m;
    label339: int n;
    label360: float f6;
    float f7;
    label429: label466: ConstraintWidget localConstraintWidget4;
    if (paramInt == 0)
    {
      f4 = f3;
      if (localConstraintWidget3 == null)
        return;
      if (localConstraintWidget3.mLeft.mTarget == null)
        break label560;
      m = localConstraintWidget3.mLeft.getMargin();
      if (localConstraintWidget3.mRight.mTarget == null)
        break label566;
      n = localConstraintWidget3.mRight.getMargin();
      if (localConstraintWidget3.getVisibility() == 8)
        break label613;
      f6 = f4 + m;
      paramLinearSystem.addEquality(localConstraintWidget3.mLeft.mSolverVariable, (int)(0.5F + f6));
      if (localConstraintWidget3.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
        break label599;
      if (f1 != 0.0F)
        break label572;
      f7 = f6 + (f3 - m - n);
      paramLinearSystem.addEquality(localConstraintWidget3.mRight.mSolverVariable, (int)(0.5F + f7));
      if (paramInt == 0)
        f7 += f3;
      f4 = f7 + n;
      localConstraintWidget4 = localConstraintWidget3;
      if (localConstraintWidget3.mRight.mTarget == null)
        break label661;
    }
    label661: for (localConstraintWidget3 = localConstraintWidget3.mRight.mTarget.mOwner; ; localConstraintWidget3 = null)
    {
      if ((localConstraintWidget3 != null) && (localConstraintWidget3.mLeft.mTarget != null) && (localConstraintWidget3.mLeft.mTarget.mOwner != localConstraintWidget4))
        localConstraintWidget3 = null;
      if (localConstraintWidget3 != paramConstraintWidgetContainer)
        break label313;
      localConstraintWidget3 = null;
      break label313;
      label541: k = 0;
      break;
      f3 = f2 / paramInt;
      f4 = 0.0F;
      break label313;
      label560: m = 0;
      break label339;
      label566: n = 0;
      break label360;
      label572: f7 = f6 + (f2 * localConstraintWidget3.mHorizontalWeight / f1 - m - n);
      break label429;
      label599: f7 = f6 + localConstraintWidget3.getWidth();
      break label429;
      label613: float f5 = f4 - f3 / 2.0F;
      paramLinearSystem.addEquality(localConstraintWidget3.mLeft.mSolverVariable, (int)(0.5F + f5));
      paramLinearSystem.addEquality(localConstraintWidget3.mRight.mSolverVariable, (int)(0.5F + f5));
      break label466;
    }
  }

  static void applyDirectResolutionVerticalChain(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, int paramInt, ConstraintWidget paramConstraintWidget)
  {
    ConstraintWidget localConstraintWidget1 = paramConstraintWidget;
    int i = 0;
    ConstraintWidget localConstraintWidget2 = null;
    int j = 0;
    float f1 = 0.0F;
    label31: label77: label208: label211: 
    while (paramConstraintWidget != null)
    {
      int i1;
      int i3;
      int i5;
      if (paramConstraintWidget.getVisibility() == 8)
      {
        i1 = 1;
        if (i1 == 0)
        {
          j++;
          if (paramConstraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
            break label196;
          int i2 = i + paramConstraintWidget.getHeight();
          if (paramConstraintWidget.mTop.mTarget == null)
            break label184;
          i3 = paramConstraintWidget.mTop.getMargin();
          int i4 = i2 + i3;
          if (paramConstraintWidget.mBottom.mTarget == null)
            break label190;
          i5 = paramConstraintWidget.mBottom.getMargin();
          label103: i = i4 + i5;
        }
        label110: localConstraintWidget2 = paramConstraintWidget;
        if (paramConstraintWidget.mBottom.mTarget == null)
          break label208;
      }
      for (paramConstraintWidget = paramConstraintWidget.mBottom.mTarget.mOwner; ; paramConstraintWidget = null)
      {
        if ((paramConstraintWidget == null) || ((paramConstraintWidget.mTop.mTarget != null) && ((paramConstraintWidget.mTop.mTarget == null) || (paramConstraintWidget.mTop.mTarget.mOwner == localConstraintWidget2))))
          break label211;
        paramConstraintWidget = null;
        break;
        i1 = 0;
        break label31;
        i3 = 0;
        break label77;
        i5 = 0;
        break label103;
        f1 += paramConstraintWidget.mVerticalWeight;
        break label110;
      }
    }
    label184: label190: label196: int k = 0;
    if (localConstraintWidget2 != null)
    {
      if (localConstraintWidget2.mBottom.mTarget == null)
        break label541;
      k = localConstraintWidget2.mBottom.mTarget.mOwner.getX();
      if ((localConstraintWidget2.mBottom.mTarget != null) && (localConstraintWidget2.mBottom.mTarget.mOwner == paramConstraintWidgetContainer))
        k = paramConstraintWidgetContainer.getBottom();
    }
    float f2 = k - 0 - i;
    float f3 = f2 / (j + 1);
    ConstraintWidget localConstraintWidget3 = localConstraintWidget1;
    float f4;
    label313: int m;
    label339: int n;
    label360: float f6;
    float f7;
    label429: label466: ConstraintWidget localConstraintWidget4;
    if (paramInt == 0)
    {
      f4 = f3;
      if (localConstraintWidget3 == null)
        return;
      if (localConstraintWidget3.mTop.mTarget == null)
        break label560;
      m = localConstraintWidget3.mTop.getMargin();
      if (localConstraintWidget3.mBottom.mTarget == null)
        break label566;
      n = localConstraintWidget3.mBottom.getMargin();
      if (localConstraintWidget3.getVisibility() == 8)
        break label613;
      f6 = f4 + m;
      paramLinearSystem.addEquality(localConstraintWidget3.mTop.mSolverVariable, (int)(0.5F + f6));
      if (localConstraintWidget3.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
        break label599;
      if (f1 != 0.0F)
        break label572;
      f7 = f6 + (f3 - m - n);
      paramLinearSystem.addEquality(localConstraintWidget3.mBottom.mSolverVariable, (int)(0.5F + f7));
      if (paramInt == 0)
        f7 += f3;
      f4 = f7 + n;
      localConstraintWidget4 = localConstraintWidget3;
      if (localConstraintWidget3.mBottom.mTarget == null)
        break label661;
    }
    label661: for (localConstraintWidget3 = localConstraintWidget3.mBottom.mTarget.mOwner; ; localConstraintWidget3 = null)
    {
      if ((localConstraintWidget3 != null) && (localConstraintWidget3.mTop.mTarget != null) && (localConstraintWidget3.mTop.mTarget.mOwner != localConstraintWidget4))
        localConstraintWidget3 = null;
      if (localConstraintWidget3 != paramConstraintWidgetContainer)
        break label313;
      localConstraintWidget3 = null;
      break label313;
      label541: k = 0;
      break;
      f3 = f2 / paramInt;
      f4 = 0.0F;
      break label313;
      label560: m = 0;
      break label339;
      label566: n = 0;
      break label360;
      label572: f7 = f6 + (f2 * localConstraintWidget3.mVerticalWeight / f1 - m - n);
      break label429;
      label599: f7 = f6 + localConstraintWidget3.getHeight();
      break label429;
      label613: float f5 = f4 - f3 / 2.0F;
      paramLinearSystem.addEquality(localConstraintWidget3.mTop.mSolverVariable, (int)(0.5F + f5));
      paramLinearSystem.addEquality(localConstraintWidget3.mBottom.mSolverVariable, (int)(0.5F + f5));
      break label466;
    }
  }

  static void checkHorizontalSimpleDependency(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, ConstraintWidget paramConstraintWidget)
  {
    if (paramConstraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
      paramConstraintWidget.mHorizontalResolution = 1;
    int i;
    label857: int j;
    label870: Guideline localGuideline;
    float f;
    while (true)
    {
      return;
      if ((paramConstraintWidgetContainer.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (paramConstraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT))
      {
        paramConstraintWidget.mLeft.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mLeft);
        paramConstraintWidget.mRight.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mRight);
        int i14 = paramConstraintWidget.mLeft.mMargin;
        int i15 = paramConstraintWidgetContainer.getWidth() - paramConstraintWidget.mRight.mMargin;
        paramLinearSystem.addEquality(paramConstraintWidget.mLeft.mSolverVariable, i14);
        paramLinearSystem.addEquality(paramConstraintWidget.mRight.mSolverVariable, i15);
        paramConstraintWidget.setHorizontalDimension(i14, i15);
        paramConstraintWidget.mHorizontalResolution = 2;
        return;
      }
      if ((paramConstraintWidget.mLeft.mTarget != null) && (paramConstraintWidget.mRight.mTarget != null))
      {
        if ((paramConstraintWidget.mLeft.mTarget.mOwner == paramConstraintWidgetContainer) && (paramConstraintWidget.mRight.mTarget.mOwner == paramConstraintWidgetContainer))
        {
          int i9 = paramConstraintWidget.mLeft.getMargin();
          int i10 = paramConstraintWidget.mRight.getMargin();
          int i12;
          if (paramConstraintWidgetContainer.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
            i12 = i9;
          for (int i13 = paramConstraintWidgetContainer.getWidth() - i10; ; i13 = i12 + paramConstraintWidget.getWidth())
          {
            paramConstraintWidget.mLeft.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mLeft);
            paramConstraintWidget.mRight.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mRight);
            paramLinearSystem.addEquality(paramConstraintWidget.mLeft.mSolverVariable, i12);
            paramLinearSystem.addEquality(paramConstraintWidget.mRight.mSolverVariable, i13);
            paramConstraintWidget.mHorizontalResolution = 2;
            paramConstraintWidget.setHorizontalDimension(i12, i13);
            return;
            int i11 = paramConstraintWidget.getWidth();
            i12 = i9 + (int)(0.5F + (paramConstraintWidgetContainer.getWidth() - i9 - i10 - i11) * paramConstraintWidget.mHorizontalBiasPercent);
          }
        }
        paramConstraintWidget.mHorizontalResolution = 1;
        return;
      }
      if ((paramConstraintWidget.mLeft.mTarget != null) && (paramConstraintWidget.mLeft.mTarget.mOwner == paramConstraintWidgetContainer))
      {
        int i7 = paramConstraintWidget.mLeft.getMargin();
        int i8 = i7 + paramConstraintWidget.getWidth();
        paramConstraintWidget.mLeft.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mLeft);
        paramConstraintWidget.mRight.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mRight);
        paramLinearSystem.addEquality(paramConstraintWidget.mLeft.mSolverVariable, i7);
        paramLinearSystem.addEquality(paramConstraintWidget.mRight.mSolverVariable, i8);
        paramConstraintWidget.mHorizontalResolution = 2;
        paramConstraintWidget.setHorizontalDimension(i7, i8);
        return;
      }
      if ((paramConstraintWidget.mRight.mTarget != null) && (paramConstraintWidget.mRight.mTarget.mOwner == paramConstraintWidgetContainer))
      {
        paramConstraintWidget.mLeft.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mLeft);
        paramConstraintWidget.mRight.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mRight);
        int i5 = paramConstraintWidgetContainer.getWidth() - paramConstraintWidget.mRight.getMargin();
        int i6 = i5 - paramConstraintWidget.getWidth();
        paramLinearSystem.addEquality(paramConstraintWidget.mLeft.mSolverVariable, i6);
        paramLinearSystem.addEquality(paramConstraintWidget.mRight.mSolverVariable, i5);
        paramConstraintWidget.mHorizontalResolution = 2;
        paramConstraintWidget.setHorizontalDimension(i6, i5);
        return;
      }
      if ((paramConstraintWidget.mLeft.mTarget != null) && (paramConstraintWidget.mLeft.mTarget.mOwner.mHorizontalResolution == 2))
      {
        SolverVariable localSolverVariable2 = paramConstraintWidget.mLeft.mTarget.mSolverVariable;
        paramConstraintWidget.mLeft.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mLeft);
        paramConstraintWidget.mRight.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mRight);
        int i3 = (int)(0.5F + (localSolverVariable2.computedValue + paramConstraintWidget.mLeft.getMargin()));
        int i4 = i3 + paramConstraintWidget.getWidth();
        paramLinearSystem.addEquality(paramConstraintWidget.mLeft.mSolverVariable, i3);
        paramLinearSystem.addEquality(paramConstraintWidget.mRight.mSolverVariable, i4);
        paramConstraintWidget.mHorizontalResolution = 2;
        paramConstraintWidget.setHorizontalDimension(i3, i4);
        return;
      }
      if ((paramConstraintWidget.mRight.mTarget != null) && (paramConstraintWidget.mRight.mTarget.mOwner.mHorizontalResolution == 2))
      {
        SolverVariable localSolverVariable1 = paramConstraintWidget.mRight.mTarget.mSolverVariable;
        paramConstraintWidget.mLeft.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mLeft);
        paramConstraintWidget.mRight.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mRight);
        int i1 = (int)(0.5F + (localSolverVariable1.computedValue - paramConstraintWidget.mRight.getMargin()));
        int i2 = i1 - paramConstraintWidget.getWidth();
        paramLinearSystem.addEquality(paramConstraintWidget.mLeft.mSolverVariable, i2);
        paramLinearSystem.addEquality(paramConstraintWidget.mRight.mSolverVariable, i1);
        paramConstraintWidget.mHorizontalResolution = 2;
        paramConstraintWidget.setHorizontalDimension(i2, i1);
        return;
      }
      if (paramConstraintWidget.mLeft.mTarget == null)
        break;
      i = 1;
      if (paramConstraintWidget.mRight.mTarget == null)
        break label1015;
      j = 1;
      if ((i != 0) || (j != 0))
        break label1019;
      if (!(paramConstraintWidget instanceof Guideline))
        break label1062;
      localGuideline = (Guideline)paramConstraintWidget;
      if (localGuideline.getOrientation() != 1)
        continue;
      paramConstraintWidget.mLeft.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mLeft);
      paramConstraintWidget.mRight.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mRight);
      if (localGuideline.getRelativeBegin() == -1)
        break label1021;
      f = localGuideline.getRelativeBegin();
    }
    while (true)
    {
      int n = (int)(0.5F + f);
      paramLinearSystem.addEquality(paramConstraintWidget.mLeft.mSolverVariable, n);
      paramLinearSystem.addEquality(paramConstraintWidget.mRight.mSolverVariable, n);
      paramConstraintWidget.mHorizontalResolution = 2;
      paramConstraintWidget.mVerticalResolution = 2;
      paramConstraintWidget.setHorizontalDimension(n, n);
      paramConstraintWidget.setVerticalDimension(0, paramConstraintWidgetContainer.getHeight());
      return;
      i = 0;
      break label857;
      label1015: j = 0;
      break label870;
      label1019: break;
      label1021: if (localGuideline.getRelativeEnd() != -1)
      {
        f = paramConstraintWidgetContainer.getWidth() - localGuideline.getRelativeEnd();
        continue;
      }
      f = paramConstraintWidgetContainer.getWidth() * localGuideline.getRelativePercent();
    }
    label1062: paramConstraintWidget.mLeft.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mLeft);
    paramConstraintWidget.mRight.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mRight);
    int k = paramConstraintWidget.getX();
    int m = k + paramConstraintWidget.getWidth();
    paramLinearSystem.addEquality(paramConstraintWidget.mLeft.mSolverVariable, k);
    paramLinearSystem.addEquality(paramConstraintWidget.mRight.mSolverVariable, m);
    paramConstraintWidget.mHorizontalResolution = 2;
  }

  static void checkMatchParent(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, ConstraintWidget paramConstraintWidget)
  {
    if ((paramConstraintWidgetContainer.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (paramConstraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT))
    {
      paramConstraintWidget.mLeft.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mLeft);
      paramConstraintWidget.mRight.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mRight);
      int k = paramConstraintWidget.mLeft.mMargin;
      int m = paramConstraintWidgetContainer.getWidth() - paramConstraintWidget.mRight.mMargin;
      paramLinearSystem.addEquality(paramConstraintWidget.mLeft.mSolverVariable, k);
      paramLinearSystem.addEquality(paramConstraintWidget.mRight.mSolverVariable, m);
      paramConstraintWidget.setHorizontalDimension(k, m);
      paramConstraintWidget.mHorizontalResolution = 2;
    }
    if ((paramConstraintWidgetContainer.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (paramConstraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT))
    {
      paramConstraintWidget.mTop.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mTop);
      paramConstraintWidget.mBottom.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBottom);
      int i = paramConstraintWidget.mTop.mMargin;
      int j = paramConstraintWidgetContainer.getHeight() - paramConstraintWidget.mBottom.mMargin;
      paramLinearSystem.addEquality(paramConstraintWidget.mTop.mSolverVariable, i);
      paramLinearSystem.addEquality(paramConstraintWidget.mBottom.mSolverVariable, j);
      if ((paramConstraintWidget.mBaselineDistance > 0) || (paramConstraintWidget.getVisibility() == 8))
      {
        paramConstraintWidget.mBaseline.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBaseline);
        paramLinearSystem.addEquality(paramConstraintWidget.mBaseline.mSolverVariable, i + paramConstraintWidget.mBaselineDistance);
      }
      paramConstraintWidget.setVerticalDimension(i, j);
      paramConstraintWidget.mVerticalResolution = 2;
    }
  }

  static void checkVerticalSimpleDependency(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, ConstraintWidget paramConstraintWidget)
  {
    if (paramConstraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
      paramConstraintWidget.mVerticalResolution = 1;
    int i;
    label1324: int j;
    label1337: int k;
    label1350: Guideline localGuideline;
    float f;
    while (true)
    {
      return;
      if ((paramConstraintWidgetContainer.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (paramConstraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT))
      {
        paramConstraintWidget.mTop.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mTop);
        paramConstraintWidget.mBottom.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBottom);
        int i18 = paramConstraintWidget.mTop.mMargin;
        int i19 = paramConstraintWidgetContainer.getHeight() - paramConstraintWidget.mBottom.mMargin;
        paramLinearSystem.addEquality(paramConstraintWidget.mTop.mSolverVariable, i18);
        paramLinearSystem.addEquality(paramConstraintWidget.mBottom.mSolverVariable, i19);
        if ((paramConstraintWidget.mBaselineDistance > 0) || (paramConstraintWidget.getVisibility() == 8))
        {
          paramConstraintWidget.mBaseline.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBaseline);
          paramLinearSystem.addEquality(paramConstraintWidget.mBaseline.mSolverVariable, i18 + paramConstraintWidget.mBaselineDistance);
        }
        paramConstraintWidget.setVerticalDimension(i18, i19);
        paramConstraintWidget.mVerticalResolution = 2;
        return;
      }
      if ((paramConstraintWidget.mTop.mTarget != null) && (paramConstraintWidget.mBottom.mTarget != null))
      {
        if ((paramConstraintWidget.mTop.mTarget.mOwner == paramConstraintWidgetContainer) && (paramConstraintWidget.mBottom.mTarget.mOwner == paramConstraintWidgetContainer))
        {
          int i12 = paramConstraintWidget.mTop.getMargin();
          int i13 = paramConstraintWidget.mBottom.getMargin();
          int i16;
          if (paramConstraintWidgetContainer.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
            i16 = i12;
          for (int i17 = i16 + paramConstraintWidget.getHeight(); ; i17 = i16 + paramConstraintWidget.getHeight())
          {
            paramConstraintWidget.mTop.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mTop);
            paramConstraintWidget.mBottom.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBottom);
            paramLinearSystem.addEquality(paramConstraintWidget.mTop.mSolverVariable, i16);
            paramLinearSystem.addEquality(paramConstraintWidget.mBottom.mSolverVariable, i17);
            if ((paramConstraintWidget.mBaselineDistance > 0) || (paramConstraintWidget.getVisibility() == 8))
            {
              paramConstraintWidget.mBaseline.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBaseline);
              paramLinearSystem.addEquality(paramConstraintWidget.mBaseline.mSolverVariable, i16 + paramConstraintWidget.mBaselineDistance);
            }
            paramConstraintWidget.mVerticalResolution = 2;
            paramConstraintWidget.setVerticalDimension(i16, i17);
            return;
            int i14 = paramConstraintWidget.getHeight();
            int i15 = paramConstraintWidgetContainer.getHeight() - i12 - i13 - i14;
            i16 = (int)(0.5F + (i12 + i15 * paramConstraintWidget.mVerticalBiasPercent));
          }
        }
        paramConstraintWidget.mVerticalResolution = 1;
        return;
      }
      if ((paramConstraintWidget.mTop.mTarget != null) && (paramConstraintWidget.mTop.mTarget.mOwner == paramConstraintWidgetContainer))
      {
        int i10 = paramConstraintWidget.mTop.getMargin();
        int i11 = i10 + paramConstraintWidget.getHeight();
        paramConstraintWidget.mTop.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mTop);
        paramConstraintWidget.mBottom.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBottom);
        paramLinearSystem.addEquality(paramConstraintWidget.mTop.mSolverVariable, i10);
        paramLinearSystem.addEquality(paramConstraintWidget.mBottom.mSolverVariable, i11);
        if ((paramConstraintWidget.mBaselineDistance > 0) || (paramConstraintWidget.getVisibility() == 8))
        {
          paramConstraintWidget.mBaseline.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBaseline);
          paramLinearSystem.addEquality(paramConstraintWidget.mBaseline.mSolverVariable, i10 + paramConstraintWidget.mBaselineDistance);
        }
        paramConstraintWidget.mVerticalResolution = 2;
        paramConstraintWidget.setVerticalDimension(i10, i11);
        return;
      }
      if ((paramConstraintWidget.mBottom.mTarget != null) && (paramConstraintWidget.mBottom.mTarget.mOwner == paramConstraintWidgetContainer))
      {
        paramConstraintWidget.mTop.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mTop);
        paramConstraintWidget.mBottom.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBottom);
        int i8 = paramConstraintWidgetContainer.getHeight() - paramConstraintWidget.mBottom.getMargin();
        int i9 = i8 - paramConstraintWidget.getHeight();
        paramLinearSystem.addEquality(paramConstraintWidget.mTop.mSolverVariable, i9);
        paramLinearSystem.addEquality(paramConstraintWidget.mBottom.mSolverVariable, i8);
        if ((paramConstraintWidget.mBaselineDistance > 0) || (paramConstraintWidget.getVisibility() == 8))
        {
          paramConstraintWidget.mBaseline.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBaseline);
          paramLinearSystem.addEquality(paramConstraintWidget.mBaseline.mSolverVariable, i9 + paramConstraintWidget.mBaselineDistance);
        }
        paramConstraintWidget.mVerticalResolution = 2;
        paramConstraintWidget.setVerticalDimension(i9, i8);
        return;
      }
      if ((paramConstraintWidget.mTop.mTarget != null) && (paramConstraintWidget.mTop.mTarget.mOwner.mVerticalResolution == 2))
      {
        SolverVariable localSolverVariable3 = paramConstraintWidget.mTop.mTarget.mSolverVariable;
        paramConstraintWidget.mTop.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mTop);
        paramConstraintWidget.mBottom.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBottom);
        int i6 = (int)(0.5F + (localSolverVariable3.computedValue + paramConstraintWidget.mTop.getMargin()));
        int i7 = i6 + paramConstraintWidget.getHeight();
        paramLinearSystem.addEquality(paramConstraintWidget.mTop.mSolverVariable, i6);
        paramLinearSystem.addEquality(paramConstraintWidget.mBottom.mSolverVariable, i7);
        if ((paramConstraintWidget.mBaselineDistance > 0) || (paramConstraintWidget.getVisibility() == 8))
        {
          paramConstraintWidget.mBaseline.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBaseline);
          paramLinearSystem.addEquality(paramConstraintWidget.mBaseline.mSolverVariable, i6 + paramConstraintWidget.mBaselineDistance);
        }
        paramConstraintWidget.mVerticalResolution = 2;
        paramConstraintWidget.setVerticalDimension(i6, i7);
        return;
      }
      if ((paramConstraintWidget.mBottom.mTarget != null) && (paramConstraintWidget.mBottom.mTarget.mOwner.mVerticalResolution == 2))
      {
        SolverVariable localSolverVariable2 = paramConstraintWidget.mBottom.mTarget.mSolverVariable;
        paramConstraintWidget.mTop.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mTop);
        paramConstraintWidget.mBottom.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBottom);
        int i4 = (int)(0.5F + (localSolverVariable2.computedValue - paramConstraintWidget.mBottom.getMargin()));
        int i5 = i4 - paramConstraintWidget.getHeight();
        paramLinearSystem.addEquality(paramConstraintWidget.mTop.mSolverVariable, i5);
        paramLinearSystem.addEquality(paramConstraintWidget.mBottom.mSolverVariable, i4);
        if ((paramConstraintWidget.mBaselineDistance > 0) || (paramConstraintWidget.getVisibility() == 8))
        {
          paramConstraintWidget.mBaseline.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBaseline);
          paramLinearSystem.addEquality(paramConstraintWidget.mBaseline.mSolverVariable, i5 + paramConstraintWidget.mBaselineDistance);
        }
        paramConstraintWidget.mVerticalResolution = 2;
        paramConstraintWidget.setVerticalDimension(i5, i4);
        return;
      }
      if ((paramConstraintWidget.mBaseline.mTarget != null) && (paramConstraintWidget.mBaseline.mTarget.mOwner.mVerticalResolution == 2))
      {
        SolverVariable localSolverVariable1 = paramConstraintWidget.mBaseline.mTarget.mSolverVariable;
        paramConstraintWidget.mTop.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mTop);
        paramConstraintWidget.mBottom.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBottom);
        int i2 = (int)(0.5F + (localSolverVariable1.computedValue - paramConstraintWidget.mBaselineDistance));
        int i3 = i2 + paramConstraintWidget.getHeight();
        paramLinearSystem.addEquality(paramConstraintWidget.mTop.mSolverVariable, i2);
        paramLinearSystem.addEquality(paramConstraintWidget.mBottom.mSolverVariable, i3);
        paramConstraintWidget.mBaseline.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBaseline);
        paramLinearSystem.addEquality(paramConstraintWidget.mBaseline.mSolverVariable, i2 + paramConstraintWidget.mBaselineDistance);
        paramConstraintWidget.mVerticalResolution = 2;
        paramConstraintWidget.setVerticalDimension(i2, i3);
        return;
      }
      if (paramConstraintWidget.mBaseline.mTarget == null)
        break;
      i = 1;
      if (paramConstraintWidget.mTop.mTarget == null)
        break label1499;
      j = 1;
      if (paramConstraintWidget.mBottom.mTarget == null)
        break label1505;
      k = 1;
      if ((i != 0) || (j != 0) || (k != 0))
        break label1509;
      if (!(paramConstraintWidget instanceof Guideline))
        break label1552;
      localGuideline = (Guideline)paramConstraintWidget;
      if (localGuideline.getOrientation() != 0)
        continue;
      paramConstraintWidget.mTop.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mTop);
      paramConstraintWidget.mBottom.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBottom);
      if (localGuideline.getRelativeBegin() == -1)
        break label1511;
      f = localGuideline.getRelativeBegin();
    }
    while (true)
    {
      int i1 = (int)(0.5F + f);
      paramLinearSystem.addEquality(paramConstraintWidget.mTop.mSolverVariable, i1);
      paramLinearSystem.addEquality(paramConstraintWidget.mBottom.mSolverVariable, i1);
      paramConstraintWidget.mVerticalResolution = 2;
      paramConstraintWidget.mHorizontalResolution = 2;
      paramConstraintWidget.setVerticalDimension(i1, i1);
      paramConstraintWidget.setHorizontalDimension(0, paramConstraintWidgetContainer.getWidth());
      return;
      i = 0;
      break label1324;
      label1499: j = 0;
      break label1337;
      label1505: k = 0;
      break label1350;
      label1509: break;
      label1511: if (localGuideline.getRelativeEnd() != -1)
      {
        f = paramConstraintWidgetContainer.getHeight() - localGuideline.getRelativeEnd();
        continue;
      }
      f = paramConstraintWidgetContainer.getHeight() * localGuideline.getRelativePercent();
    }
    label1552: paramConstraintWidget.mTop.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mTop);
    paramConstraintWidget.mBottom.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBottom);
    int m = paramConstraintWidget.getY();
    int n = m + paramConstraintWidget.getHeight();
    paramLinearSystem.addEquality(paramConstraintWidget.mTop.mSolverVariable, m);
    paramLinearSystem.addEquality(paramConstraintWidget.mBottom.mSolverVariable, n);
    if ((paramConstraintWidget.mBaselineDistance > 0) || (paramConstraintWidget.getVisibility() == 8))
    {
      paramConstraintWidget.mBaseline.mSolverVariable = paramLinearSystem.createObjectVariable(paramConstraintWidget.mBaseline);
      paramLinearSystem.addEquality(paramConstraintWidget.mBaseline.mSolverVariable, m + paramConstraintWidget.mBaselineDistance);
    }
    paramConstraintWidget.mVerticalResolution = 2;
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.solver.widgets.Optimizer
 * JD-Core Version:    0.6.0
 */