package android.support.constraint.solver;

import java.util.ArrayList;

public class Goal
{
  ArrayList<SolverVariable> variables = new ArrayList();

  private void initFromSystemErrors(LinearSystem paramLinearSystem)
  {
    this.variables.clear();
    int i = 1;
    if (i < paramLinearSystem.mNumColumns)
    {
      SolverVariable localSolverVariable = paramLinearSystem.mCache.mIndexedVariables[i];
      for (int j = 0; j < 6; j++)
        localSolverVariable.strengthVector[j] = 0.0F;
      localSolverVariable.strengthVector[localSolverVariable.strength] = 1.0F;
      if (localSolverVariable.mType != SolverVariable.Type.ERROR);
      while (true)
      {
        i++;
        break;
        this.variables.add(localSolverVariable);
      }
    }
  }

  SolverVariable getPivotCandidate()
  {
    int i = this.variables.size();
    Object localObject = null;
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      SolverVariable localSolverVariable = (SolverVariable)this.variables.get(k);
      for (int m = 5; m >= 0; m--)
      {
        float f = localSolverVariable.strengthVector[m];
        if ((localObject == null) && (f < 0.0F) && (m >= j))
        {
          j = m;
          localObject = localSolverVariable;
        }
        if ((f <= 0.0F) || (m <= j))
          continue;
        j = m;
        localObject = null;
      }
    }
    return localObject;
  }

  public String toString()
  {
    String str = "Goal: ";
    int i = this.variables.size();
    for (int j = 0; j < i; j++)
    {
      SolverVariable localSolverVariable = (SolverVariable)this.variables.get(j);
      str = str + localSolverVariable.strengthsToString();
    }
    return str;
  }

  void updateFromSystem(LinearSystem paramLinearSystem)
  {
    initFromSystemErrors(paramLinearSystem);
    int i = this.variables.size();
    for (int j = 0; j < i; j++)
    {
      SolverVariable localSolverVariable1 = (SolverVariable)this.variables.get(j);
      if (localSolverVariable1.definitionId == -1)
        continue;
      ArrayLinkedVariables localArrayLinkedVariables = paramLinearSystem.getRow(localSolverVariable1.definitionId).variables;
      int k = localArrayLinkedVariables.currentSize;
      int m = 0;
      if (m < k)
      {
        SolverVariable localSolverVariable2 = localArrayLinkedVariables.getVariable(m);
        if (localSolverVariable2 == null);
        while (true)
        {
          m++;
          break;
          float f = localArrayLinkedVariables.getVariableValue(m);
          for (int n = 0; n < 6; n++)
          {
            float[] arrayOfFloat = localSolverVariable2.strengthVector;
            arrayOfFloat[n] += f * localSolverVariable1.strengthVector[n];
          }
          if (this.variables.contains(localSolverVariable2))
            continue;
          this.variables.add(localSolverVariable2);
        }
      }
      localSolverVariable1.clearStrengths();
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.solver.Goal
 * JD-Core Version:    0.6.0
 */