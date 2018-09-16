package android.support.constraint.solver;

import android.support.constraint.solver.widgets.ConstraintAnchor;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LinearSystem
{
  private static final boolean DEBUG;
  private static int POOL_SIZE = 1000;
  private int TABLE_SIZE = 32;
  private boolean[] mAlreadyTestedCandidates = new boolean[this.TABLE_SIZE];
  final Cache mCache;
  private Goal mGoal = new Goal();
  private int mMaxColumns = this.TABLE_SIZE;
  private int mMaxRows = this.TABLE_SIZE;
  int mNumColumns = 1;
  private int mNumRows = 0;
  private SolverVariable[] mPoolVariables = new SolverVariable[POOL_SIZE];
  private int mPoolVariablesCount = 0;
  private ArrayRow[] mRows = null;
  private HashMap<String, SolverVariable> mVariables = null;
  int mVariablesID = 0;
  private ArrayRow[] tempClientsCopy = new ArrayRow[this.TABLE_SIZE];

  public LinearSystem()
  {
    releaseRows();
    this.mCache = new Cache();
  }

  private SolverVariable acquireSolverVariable(SolverVariable.Type paramType)
  {
    SolverVariable localSolverVariable = (SolverVariable)this.mCache.solverVariablePool.acquire();
    if (localSolverVariable == null)
      localSolverVariable = new SolverVariable(paramType);
    while (true)
    {
      if (this.mPoolVariablesCount >= POOL_SIZE)
      {
        POOL_SIZE = 2 * POOL_SIZE;
        this.mPoolVariables = ((SolverVariable[])Arrays.copyOf(this.mPoolVariables, POOL_SIZE));
      }
      SolverVariable[] arrayOfSolverVariable = this.mPoolVariables;
      int i = this.mPoolVariablesCount;
      this.mPoolVariablesCount = (i + 1);
      arrayOfSolverVariable[i] = localSolverVariable;
      return localSolverVariable;
      localSolverVariable.reset();
      localSolverVariable.setType(paramType);
    }
  }

  private void addError(ArrayRow paramArrayRow)
  {
    paramArrayRow.addError(createErrorVariable(), createErrorVariable());
  }

  private void addSingleError(ArrayRow paramArrayRow, int paramInt)
  {
    paramArrayRow.addSingleError(createErrorVariable(), paramInt);
  }

  private void computeValues()
  {
    for (int i = 0; i < this.mNumRows; i++)
    {
      ArrayRow localArrayRow = this.mRows[i];
      localArrayRow.variable.computedValue = localArrayRow.constantValue;
    }
  }

  public static ArrayRow createRowCentering(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, float paramFloat, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, int paramInt2, boolean paramBoolean)
  {
    ArrayRow localArrayRow = paramLinearSystem.createRow();
    localArrayRow.createRowCentering(paramSolverVariable1, paramSolverVariable2, paramInt1, paramFloat, paramSolverVariable3, paramSolverVariable4, paramInt2);
    if (paramBoolean)
    {
      SolverVariable localSolverVariable1 = paramLinearSystem.createErrorVariable();
      SolverVariable localSolverVariable2 = paramLinearSystem.createErrorVariable();
      localSolverVariable1.strength = 4;
      localSolverVariable2.strength = 4;
      localArrayRow.addError(localSolverVariable1, localSolverVariable2);
    }
    return localArrayRow;
  }

  public static ArrayRow createRowDimensionPercent(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, float paramFloat, boolean paramBoolean)
  {
    ArrayRow localArrayRow = paramLinearSystem.createRow();
    if (paramBoolean)
      paramLinearSystem.addError(localArrayRow);
    return localArrayRow.createRowDimensionPercent(paramSolverVariable1, paramSolverVariable2, paramSolverVariable3, paramFloat);
  }

  public static ArrayRow createRowEquals(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt, boolean paramBoolean)
  {
    ArrayRow localArrayRow = paramLinearSystem.createRow();
    localArrayRow.createRowEquals(paramSolverVariable1, paramSolverVariable2, paramInt);
    if (paramBoolean)
      paramLinearSystem.addSingleError(localArrayRow, 1);
    return localArrayRow;
  }

  public static ArrayRow createRowGreaterThan(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt, boolean paramBoolean)
  {
    SolverVariable localSolverVariable = paramLinearSystem.createSlackVariable();
    ArrayRow localArrayRow = paramLinearSystem.createRow();
    localArrayRow.createRowGreaterThan(paramSolverVariable1, paramSolverVariable2, localSolverVariable, paramInt);
    if (paramBoolean)
      paramLinearSystem.addSingleError(localArrayRow, (int)(-1.0F * localArrayRow.variables.get(localSolverVariable)));
    return localArrayRow;
  }

  public static ArrayRow createRowLowerThan(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt, boolean paramBoolean)
  {
    SolverVariable localSolverVariable = paramLinearSystem.createSlackVariable();
    ArrayRow localArrayRow = paramLinearSystem.createRow();
    localArrayRow.createRowLowerThan(paramSolverVariable1, paramSolverVariable2, localSolverVariable, paramInt);
    if (paramBoolean)
      paramLinearSystem.addSingleError(localArrayRow, (int)(-1.0F * localArrayRow.variables.get(localSolverVariable)));
    return localArrayRow;
  }

  private SolverVariable createVariable(String paramString, SolverVariable.Type paramType)
  {
    if (1 + this.mNumColumns >= this.mMaxColumns)
      increaseTableSize();
    SolverVariable localSolverVariable = acquireSolverVariable(paramType);
    localSolverVariable.setName(paramString);
    this.mVariablesID = (1 + this.mVariablesID);
    this.mNumColumns = (1 + this.mNumColumns);
    localSolverVariable.id = this.mVariablesID;
    if (this.mVariables == null)
      this.mVariables = new HashMap();
    this.mVariables.put(paramString, localSolverVariable);
    this.mCache.mIndexedVariables[this.mVariablesID] = localSolverVariable;
    return localSolverVariable;
  }

  private void displayRows()
  {
    displaySolverVariables();
    String str1 = "";
    for (int i = 0; i < this.mNumRows; i++)
    {
      String str2 = str1 + this.mRows[i];
      str1 = str2 + "\n";
    }
    if (this.mGoal.variables.size() != 0)
      str1 = str1 + this.mGoal + "\n";
    System.out.println(str1);
  }

  private void displaySolverVariables()
  {
    String str1 = "Display Rows (" + this.mNumRows + "x" + this.mNumColumns + ") :\n\t | C | ";
    for (int i = 1; i <= this.mNumColumns; i++)
    {
      SolverVariable localSolverVariable = this.mCache.mIndexedVariables[i];
      String str3 = str1 + localSolverVariable;
      str1 = str3 + " | ";
    }
    String str2 = str1 + "\n";
    System.out.println(str2);
  }

  private int enforceBFS(Goal paramGoal)
    throws Exception
  {
    int i = 0;
    int j = this.mNumRows;
    int k = 0;
    if (i < j)
    {
      if (this.mRows[i].variable.mType == SolverVariable.Type.UNRESTRICTED);
      do
      {
        i++;
        break;
      }
      while (this.mRows[i].constantValue >= 0.0F);
      k = 1;
    }
    int m = 0;
    if (k != 0)
    {
      int i1 = 0;
      m = 0;
      while (i1 == 0)
      {
        m++;
        float f1 = 3.4028235E+38F;
        int i2 = 0;
        int i3 = -1;
        int i4 = -1;
        int i5 = 0;
        if (i5 < this.mNumRows)
        {
          ArrayRow localArrayRow2 = this.mRows[i5];
          if (localArrayRow2.variable.mType == SolverVariable.Type.UNRESTRICTED);
          do
          {
            i5++;
            break;
          }
          while (localArrayRow2.constantValue >= 0.0F);
          int i7 = 1;
          label146: SolverVariable localSolverVariable;
          float f2;
          if (i7 < this.mNumColumns)
          {
            localSolverVariable = this.mCache.mIndexedVariables[i7];
            f2 = localArrayRow2.variables.get(localSolverVariable);
            if (f2 > 0.0F)
              break label192;
          }
          while (true)
          {
            i7++;
            break label146;
            break;
            label192: for (int i8 = 0; i8 < 6; i8++)
            {
              float f3 = localSolverVariable.strengthVector[i8] / f2;
              if (((f3 >= f1) || (i8 != i2)) && (i8 <= i2))
                continue;
              f1 = f3;
              i3 = i5;
              i4 = i7;
              i2 = i8;
            }
          }
        }
        if (i3 != -1)
        {
          ArrayRow localArrayRow1 = this.mRows[i3];
          localArrayRow1.variable.definitionId = -1;
          localArrayRow1.pivot(this.mCache.mIndexedVariables[i4]);
          localArrayRow1.variable.definitionId = i3;
          for (int i6 = 0; i6 < this.mNumRows; i6++)
            this.mRows[i6].updateRowWithEquation(localArrayRow1);
          paramGoal.updateFromSystem(this);
          continue;
        }
        i1 = 1;
      }
    }
    int n = 0;
    if (n < this.mNumRows)
    {
      if (this.mRows[n].variable.mType == SolverVariable.Type.UNRESTRICTED);
      do
      {
        n++;
        break;
      }
      while (this.mRows[n].constantValue >= 0.0F);
    }
    return m;
  }

  private String getDisplaySize(int paramInt)
  {
    int i = paramInt * 4 / 1024 / 1024;
    if (i > 0)
      return "" + i + " Mb";
    int j = paramInt * 4 / 1024;
    if (j > 0)
      return "" + j + " Kb";
    return "" + paramInt * 4 + " bytes";
  }

  private void increaseTableSize()
  {
    this.TABLE_SIZE = (2 * this.TABLE_SIZE);
    this.mRows = ((ArrayRow[])Arrays.copyOf(this.mRows, this.TABLE_SIZE));
    this.mCache.mIndexedVariables = ((SolverVariable[])Arrays.copyOf(this.mCache.mIndexedVariables, this.TABLE_SIZE));
    this.mAlreadyTestedCandidates = new boolean[this.TABLE_SIZE];
    this.mMaxColumns = this.TABLE_SIZE;
    this.mMaxRows = this.TABLE_SIZE;
    this.mGoal.variables.clear();
  }

  private int optimize(Goal paramGoal)
  {
    int i = 0;
    int j = 0;
    for (int k = 0; k < this.mNumColumns; k++)
      this.mAlreadyTestedCandidates[k] = false;
    int m = 0;
    while (i == 0)
    {
      j++;
      SolverVariable localSolverVariable = paramGoal.getPivotCandidate();
      float f1;
      int n;
      int i1;
      label83: ArrayRow localArrayRow2;
      if (localSolverVariable != null)
      {
        if (this.mAlreadyTestedCandidates[localSolverVariable.id] != 0)
          localSolverVariable = null;
      }
      else
      {
        if (localSolverVariable == null)
          break label309;
        f1 = 3.4028235E+38F;
        n = -1;
        i1 = 0;
        if (i1 >= this.mNumRows)
          break label208;
        localArrayRow2 = this.mRows[i1];
        if (localArrayRow2.variable.mType != SolverVariable.Type.UNRESTRICTED)
          break label149;
      }
      while (true)
      {
        i1++;
        break label83;
        this.mAlreadyTestedCandidates[localSolverVariable.id] = true;
        m++;
        if (m < this.mNumColumns)
          break;
        i = 1;
        break;
        label149: if (!localArrayRow2.hasVariable(localSolverVariable))
          continue;
        float f2 = localArrayRow2.variables.get(localSolverVariable);
        if (f2 >= 0.0F)
          continue;
        float f3 = -localArrayRow2.constantValue / f2;
        if (f3 >= f1)
          continue;
        f1 = f3;
        n = i1;
      }
      label208: if (n > -1)
      {
        ArrayRow localArrayRow1 = this.mRows[n];
        localArrayRow1.variable.definitionId = -1;
        localArrayRow1.pivot(localSolverVariable);
        localArrayRow1.variable.definitionId = n;
        for (int i2 = 0; i2 < this.mNumRows; i2++)
          this.mRows[i2].updateRowWithEquation(localArrayRow1);
        paramGoal.updateFromSystem(this);
        try
        {
          enforceBFS(paramGoal);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        continue;
      }
      i = 1;
      continue;
      label309: i = 1;
    }
    return j;
  }

  private void releaseRows()
  {
    for (int i = 0; i < this.mRows.length; i++)
    {
      ArrayRow localArrayRow = this.mRows[i];
      if (localArrayRow != null)
        this.mCache.arrayRowPool.release(localArrayRow);
      this.mRows[i] = null;
    }
  }

  private void updateRowFromVariables(ArrayRow paramArrayRow)
  {
    if (this.mNumRows > 0)
    {
      paramArrayRow.variables.updateFromSystem(paramArrayRow, this.mRows);
      if (paramArrayRow.variables.currentSize == 0)
        paramArrayRow.isSimpleDefinition = true;
    }
  }

  public void addCentering(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, float paramFloat, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, int paramInt2, int paramInt3)
  {
    ArrayRow localArrayRow = createRow();
    localArrayRow.createRowCentering(paramSolverVariable1, paramSolverVariable2, paramInt1, paramFloat, paramSolverVariable3, paramSolverVariable4, paramInt2);
    SolverVariable localSolverVariable1 = createErrorVariable();
    SolverVariable localSolverVariable2 = createErrorVariable();
    localSolverVariable1.strength = paramInt3;
    localSolverVariable2.strength = paramInt3;
    localArrayRow.addError(localSolverVariable1, localSolverVariable2);
    addConstraint(localArrayRow);
  }

  public void addConstraint(ArrayRow paramArrayRow)
  {
    if (paramArrayRow == null);
    int i;
    do
    {
      do
      {
        return;
        if ((1 + this.mNumRows >= this.mMaxRows) || (1 + this.mNumColumns >= this.mMaxColumns))
          increaseTableSize();
        if (paramArrayRow.isSimpleDefinition)
          break;
        updateRowFromVariables(paramArrayRow);
        paramArrayRow.ensurePositiveConstant();
        paramArrayRow.pickRowVariable();
      }
      while (!paramArrayRow.hasKeyVariable());
      if (this.mRows[this.mNumRows] != null)
        this.mCache.arrayRowPool.release(this.mRows[this.mNumRows]);
      if (!paramArrayRow.isSimpleDefinition)
        paramArrayRow.updateClientEquations();
      this.mRows[this.mNumRows] = paramArrayRow;
      paramArrayRow.variable.definitionId = this.mNumRows;
      this.mNumRows = (1 + this.mNumRows);
      i = paramArrayRow.variable.mClientEquationsCount;
    }
    while (i <= 0);
    while (this.tempClientsCopy.length < i)
      this.tempClientsCopy = new ArrayRow[2 * this.tempClientsCopy.length];
    ArrayRow[] arrayOfArrayRow = this.tempClientsCopy;
    for (int j = 0; j < i; j++)
      arrayOfArrayRow[j] = paramArrayRow.variable.mClientEquations[j];
    int k = 0;
    label213: ArrayRow localArrayRow;
    if (k < i)
    {
      localArrayRow = arrayOfArrayRow[k];
      if (localArrayRow != paramArrayRow)
        break label237;
    }
    while (true)
    {
      k++;
      break label213;
      break;
      label237: localArrayRow.variables.updateFromRow(localArrayRow, paramArrayRow);
      localArrayRow.updateClientEquations();
    }
  }

  public ArrayRow addEquality(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, int paramInt2)
  {
    ArrayRow localArrayRow = createRow();
    localArrayRow.createRowEquals(paramSolverVariable1, paramSolverVariable2, paramInt1);
    SolverVariable localSolverVariable1 = createErrorVariable();
    SolverVariable localSolverVariable2 = createErrorVariable();
    localSolverVariable1.strength = paramInt2;
    localSolverVariable2.strength = paramInt2;
    localArrayRow.addError(localSolverVariable1, localSolverVariable2);
    addConstraint(localArrayRow);
    return localArrayRow;
  }

  public void addEquality(SolverVariable paramSolverVariable, int paramInt)
  {
    int i = paramSolverVariable.definitionId;
    if (paramSolverVariable.definitionId != -1)
    {
      ArrayRow localArrayRow2 = this.mRows[i];
      if (localArrayRow2.isSimpleDefinition)
      {
        localArrayRow2.constantValue = paramInt;
        return;
      }
      ArrayRow localArrayRow3 = createRow();
      localArrayRow3.createRowEquals(paramSolverVariable, paramInt);
      addConstraint(localArrayRow3);
      return;
    }
    ArrayRow localArrayRow1 = createRow();
    localArrayRow1.createRowDefinition(paramSolverVariable, paramInt);
    addConstraint(localArrayRow1);
  }

  public void addGreaterThan(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, int paramInt2)
  {
    ArrayRow localArrayRow = createRow();
    SolverVariable localSolverVariable = createSlackVariable();
    localSolverVariable.strength = paramInt2;
    localArrayRow.createRowGreaterThan(paramSolverVariable1, paramSolverVariable2, localSolverVariable, paramInt1);
    addConstraint(localArrayRow);
  }

  public void addLowerThan(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, int paramInt2)
  {
    ArrayRow localArrayRow = createRow();
    SolverVariable localSolverVariable = createSlackVariable();
    localSolverVariable.strength = paramInt2;
    localArrayRow.createRowLowerThan(paramSolverVariable1, paramSolverVariable2, localSolverVariable, paramInt1);
    addConstraint(localArrayRow);
  }

  public SolverVariable createErrorVariable()
  {
    if (1 + this.mNumColumns >= this.mMaxColumns)
      increaseTableSize();
    SolverVariable localSolverVariable = acquireSolverVariable(SolverVariable.Type.ERROR);
    this.mVariablesID = (1 + this.mVariablesID);
    this.mNumColumns = (1 + this.mNumColumns);
    localSolverVariable.id = this.mVariablesID;
    this.mCache.mIndexedVariables[this.mVariablesID] = localSolverVariable;
    return localSolverVariable;
  }

  public SolverVariable createObjectVariable(Object paramObject)
  {
    SolverVariable localSolverVariable;
    if (paramObject == null)
      localSolverVariable = null;
    do
    {
      boolean bool;
      do
      {
        return localSolverVariable;
        if (1 + this.mNumColumns >= this.mMaxColumns)
          increaseTableSize();
        bool = paramObject instanceof ConstraintAnchor;
        localSolverVariable = null;
      }
      while (!bool);
      localSolverVariable = ((ConstraintAnchor)paramObject).getSolverVariable();
      if (localSolverVariable != null)
        continue;
      ((ConstraintAnchor)paramObject).resetSolverVariable(this.mCache);
      localSolverVariable = ((ConstraintAnchor)paramObject).getSolverVariable();
    }
    while ((localSolverVariable.id != -1) && (localSolverVariable.id <= this.mVariablesID) && (this.mCache.mIndexedVariables[localSolverVariable.id] != null));
    if (localSolverVariable.id != -1)
      localSolverVariable.reset();
    this.mVariablesID = (1 + this.mVariablesID);
    this.mNumColumns = (1 + this.mNumColumns);
    localSolverVariable.id = this.mVariablesID;
    localSolverVariable.mType = SolverVariable.Type.UNRESTRICTED;
    this.mCache.mIndexedVariables[this.mVariablesID] = localSolverVariable;
    return localSolverVariable;
  }

  public ArrayRow createRow()
  {
    ArrayRow localArrayRow = (ArrayRow)this.mCache.arrayRowPool.acquire();
    if (localArrayRow == null)
      return new ArrayRow(this.mCache);
    localArrayRow.reset();
    return localArrayRow;
  }

  public SolverVariable createSlackVariable()
  {
    if (1 + this.mNumColumns >= this.mMaxColumns)
      increaseTableSize();
    SolverVariable localSolverVariable = acquireSolverVariable(SolverVariable.Type.SLACK);
    this.mVariablesID = (1 + this.mVariablesID);
    this.mNumColumns = (1 + this.mNumColumns);
    localSolverVariable.id = this.mVariablesID;
    this.mCache.mIndexedVariables[this.mVariablesID] = localSolverVariable;
    return localSolverVariable;
  }

  void displayReadableRows()
  {
    displaySolverVariables();
    String str1 = "";
    for (int i = 0; i < this.mNumRows; i++)
    {
      String str2 = str1 + this.mRows[i].toReadableString();
      str1 = str2 + "\n";
    }
    if (this.mGoal != null)
      str1 = str1 + this.mGoal + "\n";
    System.out.println(str1);
  }

  void displaySystemInformations()
  {
    int i = 0;
    for (int j = 0; j < this.TABLE_SIZE; j++)
    {
      if (this.mRows[j] == null)
        continue;
      i += this.mRows[j].sizeInBytes();
    }
    int k = 0;
    for (int m = 0; m < this.mNumRows; m++)
    {
      if (this.mRows[m] == null)
        continue;
      k += this.mRows[m].sizeInBytes();
    }
    System.out.println("Linear System -> Table size: " + this.TABLE_SIZE + " (" + getDisplaySize(this.TABLE_SIZE * this.TABLE_SIZE) + ") -- row sizes: " + getDisplaySize(i) + ", actual size: " + getDisplaySize(k) + " rows: " + this.mNumRows + "/" + this.mMaxRows + " cols: " + this.mNumColumns + "/" + this.mMaxColumns + " " + 0 + " occupied cells, " + getDisplaySize(0));
  }

  public void displayVariablesReadableRows()
  {
    displaySolverVariables();
    String str1 = "";
    for (int i = 0; i < this.mNumRows; i++)
    {
      if (this.mRows[i].variable.mType != SolverVariable.Type.UNRESTRICTED)
        continue;
      String str2 = str1 + this.mRows[i].toReadableString();
      str1 = str2 + "\n";
    }
    if (this.mGoal.variables.size() != 0)
      str1 = str1 + this.mGoal + "\n";
    System.out.println(str1);
  }

  public Cache getCache()
  {
    return this.mCache;
  }

  Goal getGoal()
  {
    return this.mGoal;
  }

  public int getMemoryUsed()
  {
    int i = 0;
    for (int j = 0; j < this.mNumRows; j++)
    {
      if (this.mRows[j] == null)
        continue;
      i += this.mRows[j].sizeInBytes();
    }
    return i;
  }

  public int getNumEquations()
  {
    return this.mNumRows;
  }

  public int getNumVariables()
  {
    return this.mVariablesID;
  }

  public int getObjectVariableValue(Object paramObject)
  {
    SolverVariable localSolverVariable = ((ConstraintAnchor)paramObject).getSolverVariable();
    if (localSolverVariable != null)
      return (int)(0.5F + localSolverVariable.computedValue);
    return 0;
  }

  ArrayRow getRow(int paramInt)
  {
    return this.mRows[paramInt];
  }

  float getValueFor(String paramString)
  {
    SolverVariable localSolverVariable = getVariable(paramString, SolverVariable.Type.UNRESTRICTED);
    if (localSolverVariable == null)
      return 0.0F;
    return localSolverVariable.computedValue;
  }

  SolverVariable getVariable(String paramString, SolverVariable.Type paramType)
  {
    if (this.mVariables == null)
      this.mVariables = new HashMap();
    SolverVariable localSolverVariable = (SolverVariable)this.mVariables.get(paramString);
    if (localSolverVariable == null)
      localSolverVariable = createVariable(paramString, paramType);
    return localSolverVariable;
  }

  public void minimize()
    throws Exception
  {
    minimizeGoal(this.mGoal);
  }

  void minimizeGoal(Goal paramGoal)
    throws Exception
  {
    paramGoal.updateFromSystem(this);
    enforceBFS(paramGoal);
    optimize(paramGoal);
    computeValues();
  }

  void rebuildGoalFromErrors()
  {
    this.mGoal.updateFromSystem(this);
  }

  public void reset()
  {
    for (int i = 0; i < this.mCache.mIndexedVariables.length; i++)
    {
      SolverVariable localSolverVariable = this.mCache.mIndexedVariables[i];
      if (localSolverVariable == null)
        continue;
      localSolverVariable.reset();
    }
    this.mCache.solverVariablePool.releaseAll(this.mPoolVariables, this.mPoolVariablesCount);
    this.mPoolVariablesCount = 0;
    Arrays.fill(this.mCache.mIndexedVariables, null);
    if (this.mVariables != null)
      this.mVariables.clear();
    this.mVariablesID = 0;
    this.mGoal.variables.clear();
    this.mNumColumns = 1;
    for (int j = 0; j < this.mNumRows; j++)
      this.mRows[j].used = false;
    releaseRows();
    this.mNumRows = 0;
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.solver.LinearSystem
 * JD-Core Version:    0.6.0
 */