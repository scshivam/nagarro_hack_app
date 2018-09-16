package android.support.constraint.solver;

public class ArrayRow
{
  private static final boolean DEBUG;
  float constantValue = 0.0F;
  boolean isSimpleDefinition = false;
  boolean used = false;
  SolverVariable variable = null;
  final ArrayLinkedVariables variables;

  public ArrayRow(Cache paramCache)
  {
    this.variables = new ArrayLinkedVariables(this, paramCache);
  }

  public ArrayRow addError(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2)
  {
    this.variables.put(paramSolverVariable1, 1.0F);
    this.variables.put(paramSolverVariable2, -1.0F);
    return this;
  }

  ArrayRow addSingleError(SolverVariable paramSolverVariable, int paramInt)
  {
    this.variables.put(paramSolverVariable, paramInt);
    return this;
  }

  ArrayRow createRowCentering(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, float paramFloat, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, int paramInt2)
  {
    if (paramSolverVariable2 == paramSolverVariable3)
    {
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable4, 1.0F);
      this.variables.put(paramSolverVariable2, -2.0F);
    }
    do
    {
      while (true)
      {
        return this;
        if (paramFloat != 0.5F)
          break;
        this.variables.put(paramSolverVariable1, 1.0F);
        this.variables.put(paramSolverVariable2, -1.0F);
        this.variables.put(paramSolverVariable3, -1.0F);
        this.variables.put(paramSolverVariable4, 1.0F);
        if ((paramInt1 <= 0) && (paramInt2 <= 0))
          continue;
        this.constantValue = (paramInt2 + -paramInt1);
        return this;
      }
      if (paramFloat <= 0.0F)
      {
        this.variables.put(paramSolverVariable1, -1.0F);
        this.variables.put(paramSolverVariable2, 1.0F);
        this.constantValue = paramInt1;
        return this;
      }
      if (paramFloat >= 1.0F)
      {
        this.variables.put(paramSolverVariable3, -1.0F);
        this.variables.put(paramSolverVariable4, 1.0F);
        this.constantValue = paramInt2;
        return this;
      }
      this.variables.put(paramSolverVariable1, 1.0F * (1.0F - paramFloat));
      this.variables.put(paramSolverVariable2, -1.0F * (1.0F - paramFloat));
      this.variables.put(paramSolverVariable3, -1.0F * paramFloat);
      this.variables.put(paramSolverVariable4, 1.0F * paramFloat);
    }
    while ((paramInt1 <= 0) && (paramInt2 <= 0));
    this.constantValue = (-paramInt1 * (1.0F - paramFloat) + paramFloat * paramInt2);
    return this;
  }

  ArrayRow createRowDefinition(SolverVariable paramSolverVariable, int paramInt)
  {
    this.variable = paramSolverVariable;
    paramSolverVariable.computedValue = paramInt;
    this.constantValue = paramInt;
    this.isSimpleDefinition = true;
    return this;
  }

  ArrayRow createRowDimensionPercent(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, float paramFloat)
  {
    this.variables.put(paramSolverVariable1, -1.0F);
    this.variables.put(paramSolverVariable2, 1.0F - paramFloat);
    this.variables.put(paramSolverVariable3, paramFloat);
    return this;
  }

  public ArrayRow createRowDimensionRatio(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, float paramFloat)
  {
    this.variables.put(paramSolverVariable1, -1.0F);
    this.variables.put(paramSolverVariable2, 1.0F);
    this.variables.put(paramSolverVariable3, paramFloat);
    this.variables.put(paramSolverVariable4, -paramFloat);
    return this;
  }

  public ArrayRow createRowEqualDimension(float paramFloat1, float paramFloat2, float paramFloat3, SolverVariable paramSolverVariable1, int paramInt1, SolverVariable paramSolverVariable2, int paramInt2, SolverVariable paramSolverVariable3, int paramInt3, SolverVariable paramSolverVariable4, int paramInt4)
  {
    if ((paramFloat2 == 0.0F) || (paramFloat1 == paramFloat3))
    {
      this.constantValue = (paramInt4 + (paramInt3 + (-paramInt1 - paramInt2)));
      this.variables.put(paramSolverVariable1, 1.0F);
      this.variables.put(paramSolverVariable2, -1.0F);
      this.variables.put(paramSolverVariable4, 1.0F);
      this.variables.put(paramSolverVariable3, -1.0F);
      return this;
    }
    float f = paramFloat1 / paramFloat2 / (paramFloat3 / paramFloat2);
    this.constantValue = (-paramInt1 - paramInt2 + f * paramInt3 + f * paramInt4);
    this.variables.put(paramSolverVariable1, 1.0F);
    this.variables.put(paramSolverVariable2, -1.0F);
    this.variables.put(paramSolverVariable4, f);
    this.variables.put(paramSolverVariable3, -f);
    return this;
  }

  public ArrayRow createRowEquals(SolverVariable paramSolverVariable, int paramInt)
  {
    if (paramInt < 0)
    {
      this.constantValue = (paramInt * -1);
      this.variables.put(paramSolverVariable, 1.0F);
      return this;
    }
    this.constantValue = paramInt;
    this.variables.put(paramSolverVariable, -1.0F);
    return this;
  }

  public ArrayRow createRowEquals(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt)
  {
    int i = 0;
    if (paramInt != 0)
    {
      int j = paramInt;
      i = 0;
      if (j < 0)
      {
        j *= -1;
        i = 1;
      }
      this.constantValue = j;
    }
    if (i == 0)
    {
      this.variables.put(paramSolverVariable1, -1.0F);
      this.variables.put(paramSolverVariable2, 1.0F);
      return this;
    }
    this.variables.put(paramSolverVariable1, 1.0F);
    this.variables.put(paramSolverVariable2, -1.0F);
    return this;
  }

  public ArrayRow createRowGreaterThan(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, int paramInt)
  {
    int i = 0;
    if (paramInt != 0)
    {
      int j = paramInt;
      i = 0;
      if (j < 0)
      {
        j *= -1;
        i = 1;
      }
      this.constantValue = j;
    }
    if (i == 0)
    {
      this.variables.put(paramSolverVariable1, -1.0F);
      this.variables.put(paramSolverVariable2, 1.0F);
      this.variables.put(paramSolverVariable3, 1.0F);
      return this;
    }
    this.variables.put(paramSolverVariable1, 1.0F);
    this.variables.put(paramSolverVariable2, -1.0F);
    this.variables.put(paramSolverVariable3, -1.0F);
    return this;
  }

  public ArrayRow createRowLowerThan(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, int paramInt)
  {
    int i = 0;
    if (paramInt != 0)
    {
      int j = paramInt;
      i = 0;
      if (j < 0)
      {
        j *= -1;
        i = 1;
      }
      this.constantValue = j;
    }
    if (i == 0)
    {
      this.variables.put(paramSolverVariable1, -1.0F);
      this.variables.put(paramSolverVariable2, 1.0F);
      this.variables.put(paramSolverVariable3, -1.0F);
      return this;
    }
    this.variables.put(paramSolverVariable1, 1.0F);
    this.variables.put(paramSolverVariable2, -1.0F);
    this.variables.put(paramSolverVariable3, 1.0F);
    return this;
  }

  void ensurePositiveConstant()
  {
    if (this.constantValue < 0.0F)
    {
      this.constantValue = (-1.0F * this.constantValue);
      this.variables.invert();
    }
  }

  boolean hasAtLeastOnePositiveVariable()
  {
    return this.variables.hasAtLeastOnePositiveVariable();
  }

  boolean hasKeyVariable()
  {
    return (this.variable != null) && ((this.variable.mType == SolverVariable.Type.UNRESTRICTED) || (this.constantValue >= 0.0F));
  }

  boolean hasVariable(SolverVariable paramSolverVariable)
  {
    return this.variables.containsKey(paramSolverVariable);
  }

  void pickRowVariable()
  {
    SolverVariable localSolverVariable = this.variables.pickPivotCandidate();
    if (localSolverVariable != null)
      pivot(localSolverVariable);
    if (this.variables.currentSize == 0)
      this.isSimpleDefinition = true;
  }

  void pivot(SolverVariable paramSolverVariable)
  {
    if (this.variable != null)
    {
      this.variables.put(this.variable, -1.0F);
      this.variable = null;
    }
    float f = -1.0F * this.variables.remove(paramSolverVariable);
    this.variable = paramSolverVariable;
    if (f == 1.0F)
      return;
    this.constantValue /= f;
    this.variables.divideByAmount(f);
  }

  public void reset()
  {
    this.variable = null;
    this.variables.clear();
    this.constantValue = 0.0F;
    this.isSimpleDefinition = false;
  }

  int sizeInBytes()
  {
    SolverVariable localSolverVariable = this.variable;
    int i = 0;
    if (localSolverVariable != null)
      i = 0 + 4;
    return 4 + (i + 4) + this.variables.sizeInBytes();
  }

  String toReadableString()
  {
    String str1;
    if (this.variable == null)
      str1 = "" + "0";
    String str2;
    int i;
    while (true)
    {
      str2 = str1 + " = ";
      boolean bool = this.constantValue < 0.0F;
      i = 0;
      if (bool)
      {
        str2 = str2 + this.constantValue;
        i = 1;
      }
      int j = this.variables.currentSize;
      int k = 0;
      SolverVariable localSolverVariable;
      while (true)
      {
        if (k >= j)
          break label337;
        localSolverVariable = this.variables.getVariable(k);
        if (localSolverVariable == null)
        {
          k++;
          continue;
          str1 = "" + this.variable;
          break;
        }
      }
      float f = this.variables.getVariableValue(k);
      String str3 = localSolverVariable.toString();
      if (i == 0)
      {
        if (f < 0.0F)
        {
          str2 = str2 + "- ";
          f *= -1.0F;
        }
        label211: if (f != 1.0F)
          break label304;
      }
      label304: for (str2 = str2 + str3; ; str2 = str2 + f + " " + str3)
      {
        i = 1;
        break;
        if (f > 0.0F)
        {
          str2 = str2 + " + ";
          break label211;
        }
        str2 = str2 + " - ";
        f *= -1.0F;
        break label211;
      }
    }
    label337: if (i == 0)
      str2 = str2 + "0.0";
    return str2;
  }

  public String toString()
  {
    return toReadableString();
  }

  void updateClientEquations()
  {
    this.variables.updateClientEquations(this);
  }

  boolean updateRowWithEquation(ArrayRow paramArrayRow)
  {
    this.variables.updateFromRow(this, paramArrayRow);
    return true;
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.solver.ArrayRow
 * JD-Core Version:    0.6.0
 */