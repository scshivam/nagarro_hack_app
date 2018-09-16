package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import java.util.ArrayList;

public class ConstraintTableLayout extends ConstraintWidgetContainer
{
  public static final int ALIGN_CENTER = 0;
  private static final int ALIGN_FULL = 3;
  public static final int ALIGN_LEFT = 1;
  public static final int ALIGN_RIGHT = 2;
  private ArrayList<Guideline> mHorizontalGuidelines = new ArrayList();
  private ArrayList<HorizontalSlice> mHorizontalSlices = new ArrayList();
  private int mNumCols = 0;
  private int mNumRows = 0;
  private int mPadding = 8;
  private boolean mVerticalGrowth = true;
  private ArrayList<Guideline> mVerticalGuidelines = new ArrayList();
  private ArrayList<VerticalSlice> mVerticalSlices = new ArrayList();
  private LinearSystem system = null;

  public ConstraintTableLayout()
  {
  }

  public ConstraintTableLayout(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2);
  }

  public ConstraintTableLayout(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  private void setChildrenConnections()
  {
    int i = this.mChildren.size();
    int j = 0;
    int k = 0;
    if (k < i)
    {
      ConstraintWidget localConstraintWidget1 = (ConstraintWidget)this.mChildren.get(k);
      int m = j + localConstraintWidget1.getContainerItemSkip();
      int n = m % this.mNumCols;
      int i1 = m / this.mNumCols;
      HorizontalSlice localHorizontalSlice = (HorizontalSlice)this.mHorizontalSlices.get(i1);
      VerticalSlice localVerticalSlice = (VerticalSlice)this.mVerticalSlices.get(n);
      ConstraintWidget localConstraintWidget2 = localVerticalSlice.left;
      ConstraintWidget localConstraintWidget3 = localVerticalSlice.right;
      ConstraintWidget localConstraintWidget4 = localHorizontalSlice.top;
      ConstraintWidget localConstraintWidget5 = localHorizontalSlice.bottom;
      localConstraintWidget1.getAnchor(ConstraintAnchor.Type.LEFT).connect(localConstraintWidget2.getAnchor(ConstraintAnchor.Type.LEFT), this.mPadding);
      if ((localConstraintWidget3 instanceof Guideline))
      {
        localConstraintWidget1.getAnchor(ConstraintAnchor.Type.RIGHT).connect(localConstraintWidget3.getAnchor(ConstraintAnchor.Type.LEFT), this.mPadding);
        label169: switch (localVerticalSlice.alignment)
        {
        default:
          label200: localConstraintWidget1.getAnchor(ConstraintAnchor.Type.TOP).connect(localConstraintWidget4.getAnchor(ConstraintAnchor.Type.TOP), this.mPadding);
          if (!(localConstraintWidget5 instanceof Guideline))
            break;
          localConstraintWidget1.getAnchor(ConstraintAnchor.Type.BOTTOM).connect(localConstraintWidget5.getAnchor(ConstraintAnchor.Type.TOP), this.mPadding);
        case 3:
        case 1:
        case 2:
        }
      }
      while (true)
      {
        j = m + 1;
        k++;
        break;
        localConstraintWidget1.getAnchor(ConstraintAnchor.Type.RIGHT).connect(localConstraintWidget3.getAnchor(ConstraintAnchor.Type.RIGHT), this.mPadding);
        break label169;
        localConstraintWidget1.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
        break label200;
        localConstraintWidget1.getAnchor(ConstraintAnchor.Type.LEFT).setStrength(ConstraintAnchor.Strength.STRONG);
        localConstraintWidget1.getAnchor(ConstraintAnchor.Type.RIGHT).setStrength(ConstraintAnchor.Strength.WEAK);
        break label200;
        localConstraintWidget1.getAnchor(ConstraintAnchor.Type.LEFT).setStrength(ConstraintAnchor.Strength.WEAK);
        localConstraintWidget1.getAnchor(ConstraintAnchor.Type.RIGHT).setStrength(ConstraintAnchor.Strength.STRONG);
        break label200;
        localConstraintWidget1.getAnchor(ConstraintAnchor.Type.BOTTOM).connect(localConstraintWidget5.getAnchor(ConstraintAnchor.Type.BOTTOM), this.mPadding);
      }
    }
  }

  private void setHorizontalSlices()
  {
    this.mHorizontalSlices.clear();
    float f1 = 100.0F / this.mNumRows;
    float f2 = f1;
    Object localObject = this;
    int i = 0;
    if (i < this.mNumRows)
    {
      HorizontalSlice localHorizontalSlice = new HorizontalSlice();
      localHorizontalSlice.top = ((ConstraintWidget)localObject);
      if (i < -1 + this.mNumRows)
      {
        Guideline localGuideline = new Guideline();
        localGuideline.setOrientation(0);
        localGuideline.setParent(this);
        localGuideline.setGuidePercent((int)f2);
        f2 += f1;
        localHorizontalSlice.bottom = localGuideline;
        this.mHorizontalGuidelines.add(localGuideline);
      }
      while (true)
      {
        localObject = localHorizontalSlice.bottom;
        this.mHorizontalSlices.add(localHorizontalSlice);
        i++;
        break;
        localHorizontalSlice.bottom = this;
      }
    }
    updateDebugSolverNames();
  }

  private void setVerticalSlices()
  {
    this.mVerticalSlices.clear();
    Object localObject = this;
    float f1 = 100.0F / this.mNumCols;
    float f2 = f1;
    int i = 0;
    if (i < this.mNumCols)
    {
      VerticalSlice localVerticalSlice = new VerticalSlice();
      localVerticalSlice.left = ((ConstraintWidget)localObject);
      if (i < -1 + this.mNumCols)
      {
        Guideline localGuideline = new Guideline();
        localGuideline.setOrientation(1);
        localGuideline.setParent(this);
        localGuideline.setGuidePercent((int)f2);
        f2 += f1;
        localVerticalSlice.right = localGuideline;
        this.mVerticalGuidelines.add(localGuideline);
      }
      while (true)
      {
        localObject = localVerticalSlice.right;
        this.mVerticalSlices.add(localVerticalSlice);
        i++;
        break;
        localVerticalSlice.right = this;
      }
    }
    updateDebugSolverNames();
  }

  private void updateDebugSolverNames()
  {
    if (this.system == null);
    while (true)
    {
      return;
      int i = this.mVerticalGuidelines.size();
      for (int j = 0; j < i; j++)
        ((Guideline)this.mVerticalGuidelines.get(j)).setDebugSolverName(this.system, getDebugName() + ".VG" + j);
      int k = this.mHorizontalGuidelines.size();
      for (int m = 0; m < k; m++)
        ((Guideline)this.mHorizontalGuidelines.get(m)).setDebugSolverName(this.system, getDebugName() + ".HG" + m);
    }
  }

  public void addToSolver(LinearSystem paramLinearSystem, int paramInt)
  {
    super.addToSolver(paramLinearSystem, paramInt);
    int i = this.mChildren.size();
    if (i == 0);
    while (true)
    {
      return;
      setTableDimensions();
      if (paramLinearSystem != this.mSystem)
        continue;
      int j = this.mVerticalGuidelines.size();
      int k = 0;
      if (k < j)
      {
        Guideline localGuideline2 = (Guideline)this.mVerticalGuidelines.get(k);
        if (getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        for (boolean bool2 = true; ; bool2 = false)
        {
          localGuideline2.setPositionRelaxed(bool2);
          localGuideline2.addToSolver(paramLinearSystem, paramInt);
          k++;
          break;
        }
      }
      int m = this.mHorizontalGuidelines.size();
      int n = 0;
      if (n < m)
      {
        Guideline localGuideline1 = (Guideline)this.mHorizontalGuidelines.get(n);
        if (getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        for (boolean bool1 = true; ; bool1 = false)
        {
          localGuideline1.setPositionRelaxed(bool1);
          localGuideline1.addToSolver(paramLinearSystem, paramInt);
          n++;
          break;
        }
      }
      for (int i1 = 0; i1 < i; i1++)
        ((ConstraintWidget)this.mChildren.get(i1)).addToSolver(paramLinearSystem, paramInt);
    }
  }

  public void computeGuidelinesPercentPositions()
  {
    int i = this.mVerticalGuidelines.size();
    for (int j = 0; j < i; j++)
      ((Guideline)this.mVerticalGuidelines.get(j)).inferRelativePercentPosition();
    int k = this.mHorizontalGuidelines.size();
    for (int m = 0; m < k; m++)
      ((Guideline)this.mHorizontalGuidelines.get(m)).inferRelativePercentPosition();
  }

  public void cycleColumnAlignment(int paramInt)
  {
    VerticalSlice localVerticalSlice = (VerticalSlice)this.mVerticalSlices.get(paramInt);
    switch (localVerticalSlice.alignment)
    {
    default:
    case 1:
    case 2:
    case 0:
    }
    while (true)
    {
      setChildrenConnections();
      return;
      localVerticalSlice.alignment = 0;
      continue;
      localVerticalSlice.alignment = 1;
      continue;
      localVerticalSlice.alignment = 2;
    }
  }

  public String getColumnAlignmentRepresentation(int paramInt)
  {
    VerticalSlice localVerticalSlice = (VerticalSlice)this.mVerticalSlices.get(paramInt);
    if (localVerticalSlice.alignment == 1)
      return "L";
    if (localVerticalSlice.alignment == 0)
      return "C";
    if (localVerticalSlice.alignment == 3)
      return "F";
    if (localVerticalSlice.alignment == 2)
      return "R";
    return "!";
  }

  public String getColumnsAlignmentRepresentation()
  {
    int i = this.mVerticalSlices.size();
    String str = "";
    int j = 0;
    if (j < i)
    {
      VerticalSlice localVerticalSlice = (VerticalSlice)this.mVerticalSlices.get(j);
      if (localVerticalSlice.alignment == 1)
        str = str + "L";
      while (true)
      {
        j++;
        break;
        if (localVerticalSlice.alignment == 0)
        {
          str = str + "C";
          continue;
        }
        if (localVerticalSlice.alignment == 3)
        {
          str = str + "F";
          continue;
        }
        if (localVerticalSlice.alignment != 2)
          continue;
        str = str + "R";
      }
    }
    return str;
  }

  public ArrayList<Guideline> getHorizontalGuidelines()
  {
    return this.mHorizontalGuidelines;
  }

  public int getNumCols()
  {
    return this.mNumCols;
  }

  public int getNumRows()
  {
    return this.mNumRows;
  }

  public int getPadding()
  {
    return this.mPadding;
  }

  public String getType()
  {
    return "ConstraintTableLayout";
  }

  public ArrayList<Guideline> getVerticalGuidelines()
  {
    return this.mVerticalGuidelines;
  }

  public boolean handlesInternalConstraints()
  {
    return true;
  }

  public boolean isVerticalGrowth()
  {
    return this.mVerticalGrowth;
  }

  public void setColumnAlignment(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.mVerticalSlices.size())
    {
      ((VerticalSlice)this.mVerticalSlices.get(paramInt1)).alignment = paramInt2;
      setChildrenConnections();
    }
  }

  public void setColumnAlignment(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    if (i < j)
    {
      int k = paramString.charAt(i);
      if (k == 76)
        setColumnAlignment(i, 1);
      while (true)
      {
        i++;
        break;
        if (k == 67)
        {
          setColumnAlignment(i, 0);
          continue;
        }
        if (k == 70)
        {
          setColumnAlignment(i, 3);
          continue;
        }
        if (k == 82)
        {
          setColumnAlignment(i, 2);
          continue;
        }
        setColumnAlignment(i, 0);
      }
    }
  }

  public void setDebugSolverName(LinearSystem paramLinearSystem, String paramString)
  {
    this.system = paramLinearSystem;
    super.setDebugSolverName(paramLinearSystem, paramString);
    updateDebugSolverNames();
  }

  public void setNumCols(int paramInt)
  {
    if ((this.mVerticalGrowth) && (this.mNumCols != paramInt))
    {
      this.mNumCols = paramInt;
      setVerticalSlices();
      setTableDimensions();
    }
  }

  public void setNumRows(int paramInt)
  {
    if ((!this.mVerticalGrowth) && (this.mNumCols != paramInt))
    {
      this.mNumRows = paramInt;
      setHorizontalSlices();
      setTableDimensions();
    }
  }

  public void setPadding(int paramInt)
  {
    if (paramInt > 1)
      this.mPadding = paramInt;
  }

  public void setTableDimensions()
  {
    int i = 0;
    int j = this.mChildren.size();
    for (int k = 0; k < j; k++)
      i += ((ConstraintWidget)this.mChildren.get(k)).getContainerItemSkip();
    int m = j + i;
    if (this.mVerticalGrowth)
    {
      if (this.mNumCols == 0)
        setNumCols(1);
      int i1 = m / this.mNumCols;
      if (i1 * this.mNumCols < m)
        i1++;
      if ((this.mNumRows == i1) && (this.mVerticalGuidelines.size() == -1 + this.mNumCols))
        return;
      this.mNumRows = i1;
      setHorizontalSlices();
    }
    while (true)
    {
      setChildrenConnections();
      return;
      if (this.mNumRows == 0)
        setNumRows(1);
      int n = m / this.mNumRows;
      if (n * this.mNumRows < m)
        n++;
      if ((this.mNumCols == n) && (this.mHorizontalGuidelines.size() == -1 + this.mNumRows))
        break;
      this.mNumCols = n;
      setVerticalSlices();
    }
  }

  public void setVerticalGrowth(boolean paramBoolean)
  {
    this.mVerticalGrowth = paramBoolean;
  }

  public void updateFromSolver(LinearSystem paramLinearSystem, int paramInt)
  {
    super.updateFromSolver(paramLinearSystem, paramInt);
    if (paramLinearSystem == this.mSystem)
    {
      int i = this.mVerticalGuidelines.size();
      for (int j = 0; j < i; j++)
        ((Guideline)this.mVerticalGuidelines.get(j)).updateFromSolver(paramLinearSystem, paramInt);
      int k = this.mHorizontalGuidelines.size();
      for (int m = 0; m < k; m++)
        ((Guideline)this.mHorizontalGuidelines.get(m)).updateFromSolver(paramLinearSystem, paramInt);
    }
  }

  class HorizontalSlice
  {
    ConstraintWidget bottom;
    int padding;
    ConstraintWidget top;

    HorizontalSlice()
    {
    }
  }

  class VerticalSlice
  {
    int alignment = 1;
    ConstraintWidget left;
    int padding;
    ConstraintWidget right;

    VerticalSlice()
    {
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.solver.widgets.ConstraintTableLayout
 * JD-Core Version:    0.6.0
 */