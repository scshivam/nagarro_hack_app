package android.support.constraint.solver;

import java.util.Arrays;

public class SolverVariable
{
  private static final boolean INTERNAL_DEBUG = false;
  static final int MAX_STRENGTH = 6;
  public static final int STRENGTH_EQUALITY = 5;
  public static final int STRENGTH_HIGH = 3;
  public static final int STRENGTH_HIGHEST = 4;
  public static final int STRENGTH_LOW = 1;
  public static final int STRENGTH_MEDIUM = 2;
  public static final int STRENGTH_NONE;
  private static int uniqueId = 1;
  public float computedValue;
  int definitionId = -1;
  public int id = -1;
  ArrayRow[] mClientEquations = new ArrayRow[8];
  int mClientEquationsCount = 0;
  private String mName;
  Type mType;
  public int strength = 0;
  float[] strengthVector = new float[6];

  public SolverVariable(Type paramType)
  {
    this.mType = paramType;
  }

  public SolverVariable(String paramString, Type paramType)
  {
    this.mName = paramString;
    this.mType = paramType;
  }

  private static String getUniqueName(Type paramType)
  {
    uniqueId = 1 + uniqueId;
    switch (1.$SwitchMap$android$support$constraint$solver$SolverVariable$Type[paramType.ordinal()])
    {
    default:
      return "V" + uniqueId;
    case 1:
      return "U" + uniqueId;
    case 2:
      return "C" + uniqueId;
    case 3:
      return "S" + uniqueId;
    case 4:
    }
    return "e" + uniqueId;
  }

  void addClientEquation(ArrayRow paramArrayRow)
  {
    for (int i = 0; i < this.mClientEquationsCount; i++)
      if (this.mClientEquations[i] == paramArrayRow)
        return;
    if (this.mClientEquationsCount >= this.mClientEquations.length)
      this.mClientEquations = ((ArrayRow[])Arrays.copyOf(this.mClientEquations, 2 * this.mClientEquations.length));
    this.mClientEquations[this.mClientEquationsCount] = paramArrayRow;
    this.mClientEquationsCount = (1 + this.mClientEquationsCount);
  }

  void clearStrengths()
  {
    for (int i = 0; i < 6; i++)
      this.strengthVector[i] = 0.0F;
  }

  public String getName()
  {
    return this.mName;
  }

  void removeClientEquation(ArrayRow paramArrayRow)
  {
    for (int i = 0; ; i++)
    {
      if (i < this.mClientEquationsCount)
      {
        if (this.mClientEquations[i] != paramArrayRow)
          continue;
        for (int j = 0; j < -1 + (this.mClientEquationsCount - i); j++)
          this.mClientEquations[(i + j)] = this.mClientEquations[(1 + (i + j))];
        this.mClientEquationsCount = (-1 + this.mClientEquationsCount);
      }
      return;
    }
  }

  public void reset()
  {
    this.mName = null;
    this.mType = Type.UNKNOWN;
    this.strength = 0;
    this.id = -1;
    this.definitionId = -1;
    this.computedValue = 0.0F;
    this.mClientEquationsCount = 0;
  }

  public void setName(String paramString)
  {
    this.mName = paramString;
  }

  public void setType(Type paramType)
  {
    this.mType = paramType;
  }

  String strengthsToString()
  {
    String str1 = this + "[";
    int i = 0;
    if (i < this.strengthVector.length)
    {
      String str2 = str1 + this.strengthVector[i];
      if (i < -1 + this.strengthVector.length);
      for (str1 = str2 + ", "; ; str1 = str2 + "] ")
      {
        i++;
        break;
      }
    }
    return str1;
  }

  public String toString()
  {
    return "" + this.mName;
  }

  public static enum Type
  {
    static
    {
      CONSTANT = new Type("CONSTANT", 1);
      SLACK = new Type("SLACK", 2);
      ERROR = new Type("ERROR", 3);
      UNKNOWN = new Type("UNKNOWN", 4);
      Type[] arrayOfType = new Type[5];
      arrayOfType[0] = UNRESTRICTED;
      arrayOfType[1] = CONSTANT;
      arrayOfType[2] = SLACK;
      arrayOfType[3] = ERROR;
      arrayOfType[4] = UNKNOWN;
      $VALUES = arrayOfType;
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.solver.SolverVariable
 * JD-Core Version:    0.6.0
 */