package android.support.constraint.solver;

import java.io.PrintStream;
import java.util.Arrays;

public class ArrayLinkedVariables
{
  private static final boolean DEBUG = false;
  private static final int NONE = -1;
  private int ROW_SIZE = 8;
  private SolverVariable candidate = null;
  int currentSize = 0;
  private int[] mArrayIndices = new int[this.ROW_SIZE];
  private int[] mArrayNextIndices = new int[this.ROW_SIZE];
  private float[] mArrayValues = new float[this.ROW_SIZE];
  private final Cache mCache;
  private boolean mDidFillOnce = false;
  private int mHead = -1;
  private int mLast = -1;
  private final ArrayRow mRow;

  ArrayLinkedVariables(ArrayRow paramArrayRow, Cache paramCache)
  {
    this.mRow = paramArrayRow;
    this.mCache = paramCache;
  }

  public final void add(SolverVariable paramSolverVariable, float paramFloat)
  {
    if (paramFloat == 0.0F);
    while (true)
    {
      return;
      if (this.mHead != -1)
        break;
      this.mHead = 0;
      this.mArrayValues[this.mHead] = paramFloat;
      this.mArrayIndices[this.mHead] = paramSolverVariable.id;
      this.mArrayNextIndices[this.mHead] = -1;
      this.currentSize = (1 + this.currentSize);
      if (this.mDidFillOnce)
        continue;
      this.mLast = (1 + this.mLast);
      return;
    }
    int i = this.mHead;
    int j = -1;
    for (int k = 0; ; k++)
    {
      if ((i == -1) || (k >= this.currentSize))
        break label253;
      int i1 = this.mArrayIndices[i];
      if (i1 == paramSolverVariable.id)
      {
        float[] arrayOfFloat = this.mArrayValues;
        arrayOfFloat[i] = (paramFloat + arrayOfFloat[i]);
        if (this.mArrayValues[i] != 0.0F)
          break;
        if (i == this.mHead)
          this.mHead = this.mArrayNextIndices[i];
        while (true)
        {
          this.mCache.mIndexedVariables[i1].removeClientEquation(this.mRow);
          if (this.mDidFillOnce)
            this.mLast = i;
          this.currentSize = (-1 + this.currentSize);
          return;
          this.mArrayNextIndices[j] = this.mArrayNextIndices[i];
        }
      }
      if (this.mArrayIndices[i] < paramSolverVariable.id)
        j = i;
      i = this.mArrayNextIndices[i];
    }
    label253: int m = 1 + this.mLast;
    label287: int n;
    if (this.mDidFillOnce)
    {
      if (this.mArrayIndices[this.mLast] == -1)
        m = this.mLast;
    }
    else
    {
      if ((m >= this.mArrayIndices.length) && (this.currentSize < this.mArrayIndices.length))
      {
        n = 0;
        label312: if (n < this.mArrayIndices.length)
        {
          if (this.mArrayIndices[n] != -1)
            break label536;
          m = n;
        }
      }
      if (m >= this.mArrayIndices.length)
      {
        m = this.mArrayIndices.length;
        this.ROW_SIZE = (2 * this.ROW_SIZE);
        this.mDidFillOnce = false;
        this.mLast = (m - 1);
        this.mArrayValues = Arrays.copyOf(this.mArrayValues, this.ROW_SIZE);
        this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
        this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
      }
      this.mArrayIndices[m] = paramSolverVariable.id;
      this.mArrayValues[m] = paramFloat;
      if (j == -1)
        break label542;
      this.mArrayNextIndices[m] = this.mArrayNextIndices[j];
      this.mArrayNextIndices[j] = m;
    }
    while (true)
    {
      this.currentSize = (1 + this.currentSize);
      if (!this.mDidFillOnce)
        this.mLast = (1 + this.mLast);
      if (this.mLast < this.mArrayIndices.length)
        break;
      this.mDidFillOnce = true;
      this.mLast = (-1 + this.mArrayIndices.length);
      return;
      m = this.mArrayIndices.length;
      break label287;
      label536: n++;
      break label312;
      label542: this.mArrayNextIndices[m] = this.mHead;
      this.mHead = m;
    }
  }

  public final void clear()
  {
    this.mHead = -1;
    this.mLast = -1;
    this.mDidFillOnce = false;
    this.currentSize = 0;
  }

  final boolean containsKey(SolverVariable paramSolverVariable)
  {
    if (this.mHead == -1);
    while (true)
    {
      return false;
      int i = this.mHead;
      for (int j = 0; (i != -1) && (j < this.currentSize); j++)
      {
        if (this.mArrayIndices[i] == paramSolverVariable.id)
          return true;
        i = this.mArrayNextIndices[i];
      }
    }
  }

  public void display()
  {
    int i = this.currentSize;
    System.out.print("{ ");
    int j = 0;
    if (j < i)
    {
      SolverVariable localSolverVariable = getVariable(j);
      if (localSolverVariable == null);
      while (true)
      {
        j++;
        break;
        System.out.print(localSolverVariable + " = " + getVariableValue(j) + " ");
      }
    }
    System.out.println(" }");
  }

  void divideByAmount(float paramFloat)
  {
    int i = this.mHead;
    for (int j = 0; (i != -1) && (j < this.currentSize); j++)
    {
      float[] arrayOfFloat = this.mArrayValues;
      arrayOfFloat[i] /= paramFloat;
      i = this.mArrayNextIndices[i];
    }
  }

  public final float get(SolverVariable paramSolverVariable)
  {
    int i = this.mHead;
    for (int j = 0; (i != -1) && (j < this.currentSize); j++)
    {
      if (this.mArrayIndices[i] == paramSolverVariable.id)
        return this.mArrayValues[i];
      i = this.mArrayNextIndices[i];
    }
    return 0.0F;
  }

  SolverVariable getPivotCandidate()
  {
    if (this.candidate == null)
    {
      int i = this.mHead;
      int j = 0;
      localObject = null;
      while ((i != -1) && (j < this.currentSize))
      {
        if (this.mArrayValues[i] < 0.0F)
        {
          SolverVariable localSolverVariable = this.mCache.mIndexedVariables[this.mArrayIndices[i]];
          if ((localObject == null) || (((SolverVariable)localObject).strength < localSolverVariable.strength))
            localObject = localSolverVariable;
        }
        i = this.mArrayNextIndices[i];
        j++;
      }
    }
    Object localObject = this.candidate;
    return (SolverVariable)localObject;
  }

  final SolverVariable getVariable(int paramInt)
  {
    int i = this.mHead;
    for (int j = 0; (i != -1) && (j < this.currentSize); j++)
    {
      if (j == paramInt)
        return this.mCache.mIndexedVariables[this.mArrayIndices[i]];
      i = this.mArrayNextIndices[i];
    }
    return null;
  }

  final float getVariableValue(int paramInt)
  {
    int i = this.mHead;
    for (int j = 0; (i != -1) && (j < this.currentSize); j++)
    {
      if (j == paramInt)
        return this.mArrayValues[i];
      i = this.mArrayNextIndices[i];
    }
    return 0.0F;
  }

  boolean hasAtLeastOnePositiveVariable()
  {
    int i = this.mHead;
    for (int j = 0; (i != -1) && (j < this.currentSize); j++)
    {
      if (this.mArrayValues[i] > 0.0F)
        return true;
      i = this.mArrayNextIndices[i];
    }
    return false;
  }

  void invert()
  {
    int i = this.mHead;
    for (int j = 0; (i != -1) && (j < this.currentSize); j++)
    {
      float[] arrayOfFloat = this.mArrayValues;
      arrayOfFloat[i] = (-1.0F * arrayOfFloat[i]);
      i = this.mArrayNextIndices[i];
    }
  }

  SolverVariable pickPivotCandidate()
  {
    Object localObject1 = null;
    Object localObject2 = null;
    int i = this.mHead;
    int j = 0;
    if ((i != -1) && (j < this.currentSize))
    {
      float f = this.mArrayValues[i];
      if (f < 0.0F)
        if (f > -0.001F)
        {
          this.mArrayValues[i] = 0.0F;
          f = 0.0F;
        }
      SolverVariable localSolverVariable;
      while (f != 0.0F)
      {
        localSolverVariable = this.mCache.mIndexedVariables[this.mArrayIndices[i]];
        if (localSolverVariable.mType != SolverVariable.Type.UNRESTRICTED)
          break label145;
        if (f < 0.0F)
        {
          return localSolverVariable;
          if (f >= 0.001F)
            continue;
          this.mArrayValues[i] = 0.0F;
          f = 0.0F;
          continue;
        }
        if (localObject2 != null)
          break;
        localObject2 = localSolverVariable;
      }
      while (true)
      {
        i = this.mArrayNextIndices[i];
        j++;
        break;
        label145: if ((f >= 0.0F) || ((localObject1 != null) && (localSolverVariable.strength >= localObject1.strength)))
          continue;
        localObject1 = localSolverVariable;
      }
    }
    if (localObject2 != null)
      return localObject2;
    return localObject1;
  }

  public final void put(SolverVariable paramSolverVariable, float paramFloat)
  {
    if (paramFloat == 0.0F)
      remove(paramSolverVariable);
    while (true)
    {
      return;
      if (this.mHead != -1)
        break;
      this.mHead = 0;
      this.mArrayValues[this.mHead] = paramFloat;
      this.mArrayIndices[this.mHead] = paramSolverVariable.id;
      this.mArrayNextIndices[this.mHead] = -1;
      this.currentSize = (1 + this.currentSize);
      if (this.mDidFillOnce)
        continue;
      this.mLast = (1 + this.mLast);
      return;
    }
    int i = this.mHead;
    int j = -1;
    for (int k = 0; (i != -1) && (k < this.currentSize); k++)
    {
      if (this.mArrayIndices[i] == paramSolverVariable.id)
      {
        this.mArrayValues[i] = paramFloat;
        return;
      }
      if (this.mArrayIndices[i] < paramSolverVariable.id)
        j = i;
      i = this.mArrayNextIndices[i];
    }
    int m = 1 + this.mLast;
    label196: int n;
    if (this.mDidFillOnce)
    {
      if (this.mArrayIndices[this.mLast] == -1)
        m = this.mLast;
    }
    else
    {
      if ((m >= this.mArrayIndices.length) && (this.currentSize < this.mArrayIndices.length))
      {
        n = 0;
        label221: if (n < this.mArrayIndices.length)
        {
          if (this.mArrayIndices[n] != -1)
            break label434;
          m = n;
        }
      }
      if (m >= this.mArrayIndices.length)
      {
        m = this.mArrayIndices.length;
        this.ROW_SIZE = (2 * this.ROW_SIZE);
        this.mDidFillOnce = false;
        this.mLast = (m - 1);
        this.mArrayValues = Arrays.copyOf(this.mArrayValues, this.ROW_SIZE);
        this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
        this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
      }
      this.mArrayIndices[m] = paramSolverVariable.id;
      this.mArrayValues[m] = paramFloat;
      if (j == -1)
        break label440;
      this.mArrayNextIndices[m] = this.mArrayNextIndices[j];
      this.mArrayNextIndices[j] = m;
    }
    while (true)
    {
      this.currentSize = (1 + this.currentSize);
      if (!this.mDidFillOnce)
        this.mLast = (1 + this.mLast);
      if (this.currentSize < this.mArrayIndices.length)
        break;
      this.mDidFillOnce = true;
      return;
      m = this.mArrayIndices.length;
      break label196;
      label434: n++;
      break label221;
      label440: this.mArrayNextIndices[m] = this.mHead;
      this.mHead = m;
    }
  }

  public final float remove(SolverVariable paramSolverVariable)
  {
    if (this.candidate == paramSolverVariable)
      this.candidate = null;
    if (this.mHead == -1);
    while (true)
    {
      return 0.0F;
      int i = this.mHead;
      int j = -1;
      for (int k = 0; (i != -1) && (k < this.currentSize); k++)
      {
        int m = this.mArrayIndices[i];
        if (m == paramSolverVariable.id)
        {
          if (i == this.mHead)
            this.mHead = this.mArrayNextIndices[i];
          while (true)
          {
            this.mCache.mIndexedVariables[m].removeClientEquation(this.mRow);
            this.currentSize = (-1 + this.currentSize);
            this.mArrayIndices[i] = -1;
            if (this.mDidFillOnce)
              this.mLast = i;
            return this.mArrayValues[i];
            this.mArrayNextIndices[j] = this.mArrayNextIndices[i];
          }
        }
        j = i;
        i = this.mArrayNextIndices[i];
      }
    }
  }

  int sizeInBytes()
  {
    return 36 + (0 + 3 * (4 * this.mArrayIndices.length));
  }

  public String toString()
  {
    String str1 = "";
    int i = this.mHead;
    for (int j = 0; (i != -1) && (j < this.currentSize); j++)
    {
      String str2 = str1 + " -> ";
      String str3 = str2 + this.mArrayValues[i] + " : ";
      str1 = str3 + this.mCache.mIndexedVariables[this.mArrayIndices[i]];
      i = this.mArrayNextIndices[i];
    }
    return str1;
  }

  void updateClientEquations(ArrayRow paramArrayRow)
  {
    int i = this.mHead;
    for (int j = 0; (i != -1) && (j < this.currentSize); j++)
    {
      this.mCache.mIndexedVariables[this.mArrayIndices[i]].addClientEquation(paramArrayRow);
      i = this.mArrayNextIndices[i];
    }
  }

  void updateFromRow(ArrayRow paramArrayRow1, ArrayRow paramArrayRow2)
  {
    int i = this.mHead;
    int j = 0;
    while ((i != -1) && (j < this.currentSize))
    {
      if (this.mArrayIndices[i] == paramArrayRow2.variable.id)
      {
        float f = this.mArrayValues[i];
        remove(paramArrayRow2.variable);
        ArrayLinkedVariables localArrayLinkedVariables = paramArrayRow2.variables;
        int k = localArrayLinkedVariables.mHead;
        for (int m = 0; (k != -1) && (m < localArrayLinkedVariables.currentSize); m++)
        {
          add(this.mCache.mIndexedVariables[localArrayLinkedVariables.mArrayIndices[k]], f * localArrayLinkedVariables.mArrayValues[k]);
          k = localArrayLinkedVariables.mArrayNextIndices[k];
        }
        paramArrayRow1.constantValue += f * paramArrayRow2.constantValue;
        paramArrayRow2.variable.removeClientEquation(paramArrayRow1);
        i = this.mHead;
        j = 0;
        continue;
      }
      i = this.mArrayNextIndices[i];
      j++;
    }
  }

  void updateFromSystem(ArrayRow paramArrayRow, ArrayRow[] paramArrayOfArrayRow)
  {
    int i = this.mHead;
    int j = 0;
    while ((i != -1) && (j < this.currentSize))
    {
      SolverVariable localSolverVariable = this.mCache.mIndexedVariables[this.mArrayIndices[i]];
      if (localSolverVariable.definitionId != -1)
      {
        float f = this.mArrayValues[i];
        remove(localSolverVariable);
        ArrayRow localArrayRow = paramArrayOfArrayRow[localSolverVariable.definitionId];
        if (!localArrayRow.isSimpleDefinition)
        {
          ArrayLinkedVariables localArrayLinkedVariables = localArrayRow.variables;
          int k = localArrayLinkedVariables.mHead;
          for (int m = 0; (k != -1) && (m < localArrayLinkedVariables.currentSize); m++)
          {
            add(this.mCache.mIndexedVariables[localArrayLinkedVariables.mArrayIndices[k]], f * localArrayLinkedVariables.mArrayValues[k]);
            k = localArrayLinkedVariables.mArrayNextIndices[k];
          }
        }
        paramArrayRow.constantValue += f * localArrayRow.constantValue;
        localArrayRow.variable.removeClientEquation(paramArrayRow);
        i = this.mHead;
        j = 0;
        continue;
      }
      i = this.mArrayNextIndices[i];
      j++;
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.solver.ArrayLinkedVariables
 * JD-Core Version:    0.6.0
 */