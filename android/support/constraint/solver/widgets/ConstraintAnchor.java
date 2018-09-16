package android.support.constraint.solver.widgets;

import android.support.constraint.solver.Cache;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.SolverVariable.Type;
import java.util.ArrayList;
import java.util.HashSet;

public class ConstraintAnchor
{
  private static final boolean ALLOW_BINARY = false;
  public static final int ANY_GROUP = 2147483647;
  public static final int APPLY_GROUP_RESULTS = -2;
  public static final int AUTO_CONSTRAINT_CREATOR = 2;
  public static final int SCOUT_CREATOR = 1;
  private static final int UNSET_GONE_MARGIN = -1;
  public static final int USER_CREATOR;
  public static final boolean USE_CENTER_ANCHOR;
  private int mConnectionCreator = 0;
  private ConnectionType mConnectionType = ConnectionType.RELAXED;
  int mGoneMargin = -1;
  int mGroup = 2147483647;
  public int mMargin = 0;
  final ConstraintWidget mOwner;
  SolverVariable mSolverVariable;
  private Strength mStrength = Strength.NONE;
  ConstraintAnchor mTarget;
  final Type mType;

  public ConstraintAnchor(ConstraintWidget paramConstraintWidget, Type paramType)
  {
    this.mOwner = paramConstraintWidget;
    this.mType = paramType;
  }

  private boolean isConnectionToMe(ConstraintWidget paramConstraintWidget, HashSet<ConstraintWidget> paramHashSet)
  {
    if (paramHashSet.contains(paramConstraintWidget));
    while (true)
    {
      return false;
      paramHashSet.add(paramConstraintWidget);
      if (paramConstraintWidget == getOwner())
        return true;
      ArrayList localArrayList = paramConstraintWidget.getAnchors();
      int i = 0;
      int j = localArrayList.size();
      while (i < j)
      {
        ConstraintAnchor localConstraintAnchor = (ConstraintAnchor)localArrayList.get(i);
        if ((localConstraintAnchor.isSimilarDimensionConnection(this)) && (localConstraintAnchor.isConnected()) && (isConnectionToMe(localConstraintAnchor.getTarget().getOwner(), paramHashSet)))
          return true;
        i++;
      }
    }
  }

  private String toString(HashSet<ConstraintAnchor> paramHashSet)
  {
    if (paramHashSet.add(this))
    {
      StringBuilder localStringBuilder = new StringBuilder().append(this.mOwner.getDebugName()).append(":").append(this.mType.toString());
      if (this.mTarget != null);
      for (String str = " connected to " + this.mTarget.toString(paramHashSet); ; str = "")
        return str;
    }
    return "<-";
  }

  public boolean connect(ConstraintAnchor paramConstraintAnchor, int paramInt)
  {
    return connect(paramConstraintAnchor, paramInt, -1, Strength.STRONG, 0, false);
  }

  public boolean connect(ConstraintAnchor paramConstraintAnchor, int paramInt1, int paramInt2)
  {
    return connect(paramConstraintAnchor, paramInt1, -1, Strength.STRONG, paramInt2, false);
  }

  public boolean connect(ConstraintAnchor paramConstraintAnchor, int paramInt1, int paramInt2, Strength paramStrength, int paramInt3, boolean paramBoolean)
  {
    if (paramConstraintAnchor == null)
    {
      this.mTarget = null;
      this.mMargin = 0;
      this.mGoneMargin = -1;
      this.mStrength = Strength.NONE;
      this.mConnectionCreator = 2;
      return true;
    }
    if ((!paramBoolean) && (!isValidConnection(paramConstraintAnchor)))
      return false;
    this.mTarget = paramConstraintAnchor;
    if (paramInt1 > 0);
    for (this.mMargin = paramInt1; ; this.mMargin = 0)
    {
      this.mGoneMargin = paramInt2;
      this.mStrength = paramStrength;
      this.mConnectionCreator = paramInt3;
      return true;
    }
  }

  public boolean connect(ConstraintAnchor paramConstraintAnchor, int paramInt1, Strength paramStrength, int paramInt2)
  {
    return connect(paramConstraintAnchor, paramInt1, -1, paramStrength, paramInt2, false);
  }

  public int getConnectionCreator()
  {
    return this.mConnectionCreator;
  }

  public ConnectionType getConnectionType()
  {
    return this.mConnectionType;
  }

  public int getGroup()
  {
    return this.mGroup;
  }

  public int getMargin()
  {
    if (this.mOwner.getVisibility() == 8)
      return 0;
    if ((this.mGoneMargin > -1) && (this.mTarget != null) && (this.mTarget.mOwner.getVisibility() == 8))
      return this.mGoneMargin;
    return this.mMargin;
  }

  public final ConstraintAnchor getOpposite()
  {
    switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()])
    {
    default:
      return null;
    case 2:
      return this.mOwner.mRight;
    case 3:
      return this.mOwner.mLeft;
    case 4:
      return this.mOwner.mBottom;
    case 5:
    }
    return this.mOwner.mTop;
  }

  public ConstraintWidget getOwner()
  {
    return this.mOwner;
  }

  public int getPriorityLevel()
  {
    switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()])
    {
    case 6:
    case 7:
    default:
      return 0;
    case 8:
      return 1;
    case 2:
      return 2;
    case 3:
      return 2;
    case 4:
      return 2;
    case 5:
      return 2;
    case 1:
    }
    return 2;
  }

  public int getSnapPriorityLevel()
  {
    int i = 1;
    switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()])
    {
    default:
      i = 0;
    case 2:
    case 3:
    case 7:
      return i;
    case 6:
      return 0;
    case 4:
      return 0;
    case 5:
      return 0;
    case 8:
      return 2;
    case 1:
    }
    return 3;
  }

  public SolverVariable getSolverVariable()
  {
    return this.mSolverVariable;
  }

  public Strength getStrength()
  {
    return this.mStrength;
  }

  public ConstraintAnchor getTarget()
  {
    return this.mTarget;
  }

  public Type getType()
  {
    return this.mType;
  }

  public boolean isConnected()
  {
    return this.mTarget != null;
  }

  public boolean isConnectionAllowed(ConstraintWidget paramConstraintWidget)
  {
    if (isConnectionToMe(paramConstraintWidget, new HashSet()));
    ConstraintWidget localConstraintWidget;
    do
    {
      return false;
      localConstraintWidget = getOwner().getParent();
      if (localConstraintWidget == paramConstraintWidget)
        return true;
    }
    while (paramConstraintWidget.getParent() != localConstraintWidget);
    return true;
  }

  public boolean isConnectionAllowed(ConstraintWidget paramConstraintWidget, ConstraintAnchor paramConstraintAnchor)
  {
    return isConnectionAllowed(paramConstraintWidget);
  }

  public boolean isSideAnchor()
  {
    switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()])
    {
    default:
      return false;
    case 2:
    case 3:
    case 4:
    case 5:
    }
    return true;
  }

  public boolean isSimilarDimensionConnection(ConstraintAnchor paramConstraintAnchor)
  {
    int i = 1;
    Type localType1 = paramConstraintAnchor.getType();
    int j;
    if (localType1 == this.mType)
      j = i;
    label124: Type localType2;
    do
    {
      Type localType3;
      do
      {
        return j;
        switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()])
        {
        default:
          return false;
        case 1:
          if (localType1 != Type.BASELINE);
          while (true)
          {
            return i;
            i = 0;
          }
        case 2:
        case 3:
        case 6:
          if ((localType1 == Type.LEFT) || (localType1 == Type.RIGHT))
            break label124;
          localType3 = Type.CENTER_X;
          j = 0;
        case 4:
        case 5:
        case 7:
        case 8:
        }
      }
      while (localType1 != localType3);
      return i;
      if ((localType1 == Type.TOP) || (localType1 == Type.BOTTOM) || (localType1 == Type.CENTER_Y))
        break;
      localType2 = Type.BASELINE;
      j = 0;
    }
    while (localType1 != localType2);
    return i;
  }

  public boolean isSnapCompatibleWith(ConstraintAnchor paramConstraintAnchor)
  {
    if (this.mType == Type.CENTER)
      return false;
    if (this.mType == paramConstraintAnchor.getType())
      return true;
    switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()])
    {
    default:
      return false;
    case 2:
      switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[paramConstraintAnchor.getType().ordinal()])
      {
      case 4:
      case 5:
      default:
        return false;
      case 3:
        return true;
      case 6:
      }
      return true;
    case 3:
      switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[paramConstraintAnchor.getType().ordinal()])
      {
      default:
        return false;
      case 2:
        return true;
      case 6:
      }
      return true;
    case 6:
      switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[paramConstraintAnchor.getType().ordinal()])
      {
      default:
        return false;
      case 2:
        return true;
      case 3:
      }
      return true;
    case 4:
      switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[paramConstraintAnchor.getType().ordinal()])
      {
      case 6:
      default:
        return false;
      case 5:
        return true;
      case 7:
      }
      return true;
    case 5:
      switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[paramConstraintAnchor.getType().ordinal()])
      {
      case 5:
      case 6:
      default:
        return false;
      case 4:
        return true;
      case 7:
      }
      return true;
    case 7:
    }
    switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[paramConstraintAnchor.getType().ordinal()])
    {
    default:
      return false;
    case 4:
      return true;
    case 5:
    }
    return true;
  }

  public boolean isValidConnection(ConstraintAnchor paramConstraintAnchor)
  {
    int i = 1;
    if (paramConstraintAnchor == null);
    Type localType;
    while (true)
    {
      return false;
      localType = paramConstraintAnchor.getType();
      if (localType != this.mType)
        break;
      if ((this.mType != Type.CENTER) && ((this.mType != Type.BASELINE) || ((paramConstraintAnchor.getOwner().hasBaseline()) && (getOwner().hasBaseline()))))
        return i;
    }
    switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()])
    {
    default:
      return false;
    case 1:
      if ((localType != Type.BASELINE) && (localType != Type.CENTER_X) && (localType != Type.CENTER_Y));
      while (true)
      {
        return i;
        i = 0;
      }
    case 2:
    case 3:
      if ((localType == Type.LEFT) || (localType == Type.RIGHT))
      {
        k = i;
        if ((paramConstraintAnchor.getOwner() instanceof Guideline))
          if ((k == 0) && (localType != Type.CENTER_X))
            break label189;
      }
      label189: for (int k = i; ; k = 0)
      {
        return k;
        k = 0;
        break;
      }
    case 4:
    case 5:
    }
    if ((localType == Type.TOP) || (localType == Type.BOTTOM))
    {
      j = i;
      if ((paramConstraintAnchor.getOwner() instanceof Guideline))
        if ((j == 0) && (localType != Type.CENTER_Y))
          break label246;
    }
    label246: for (int j = i; ; j = 0)
    {
      return j;
      j = 0;
      break;
    }
  }

  public boolean isVerticalAnchor()
  {
    switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()])
    {
    case 4:
    case 5:
    default:
      return true;
    case 1:
    case 2:
    case 3:
    case 6:
    }
    return false;
  }

  public void reset()
  {
    this.mTarget = null;
    this.mMargin = 0;
    this.mGoneMargin = -1;
    this.mStrength = Strength.STRONG;
    this.mConnectionCreator = 0;
    this.mConnectionType = ConnectionType.RELAXED;
  }

  public void resetSolverVariable(Cache paramCache)
  {
    if (this.mSolverVariable == null)
    {
      this.mSolverVariable = new SolverVariable(SolverVariable.Type.UNRESTRICTED);
      return;
    }
    this.mSolverVariable.reset();
  }

  public void setConnectionCreator(int paramInt)
  {
    this.mConnectionCreator = paramInt;
  }

  public void setConnectionType(ConnectionType paramConnectionType)
  {
    this.mConnectionType = paramConnectionType;
  }

  public void setGoneMargin(int paramInt)
  {
    if (isConnected())
      this.mGoneMargin = paramInt;
  }

  public void setGroup(int paramInt)
  {
    this.mGroup = paramInt;
  }

  public void setMargin(int paramInt)
  {
    if (isConnected())
      this.mMargin = paramInt;
  }

  public void setStrength(Strength paramStrength)
  {
    if (isConnected())
      this.mStrength = paramStrength;
  }

  public String toString()
  {
    HashSet localHashSet = new HashSet();
    StringBuilder localStringBuilder = new StringBuilder().append(this.mOwner.getDebugName()).append(":").append(this.mType.toString());
    if (this.mTarget != null);
    for (String str = " connected to " + this.mTarget.toString(localHashSet); ; str = "")
      return str;
  }

  public static enum ConnectionType
  {
    static
    {
      ConnectionType[] arrayOfConnectionType = new ConnectionType[2];
      arrayOfConnectionType[0] = RELAXED;
      arrayOfConnectionType[1] = STRICT;
      $VALUES = arrayOfConnectionType;
    }
  }

  public static enum Strength
  {
    static
    {
      Strength[] arrayOfStrength = new Strength[3];
      arrayOfStrength[0] = NONE;
      arrayOfStrength[1] = STRONG;
      arrayOfStrength[2] = WEAK;
      $VALUES = arrayOfStrength;
    }
  }

  public static enum Type
  {
    static
    {
      LEFT = new Type("LEFT", 1);
      TOP = new Type("TOP", 2);
      RIGHT = new Type("RIGHT", 3);
      BOTTOM = new Type("BOTTOM", 4);
      BASELINE = new Type("BASELINE", 5);
      CENTER = new Type("CENTER", 6);
      CENTER_X = new Type("CENTER_X", 7);
      CENTER_Y = new Type("CENTER_Y", 8);
      Type[] arrayOfType = new Type[9];
      arrayOfType[0] = NONE;
      arrayOfType[1] = LEFT;
      arrayOfType[2] = TOP;
      arrayOfType[3] = RIGHT;
      arrayOfType[4] = BOTTOM;
      arrayOfType[5] = BASELINE;
      arrayOfType[6] = CENTER;
      arrayOfType[7] = CENTER_X;
      arrayOfType[8] = CENTER_Y;
      $VALUES = arrayOfType;
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.solver.widgets.ConstraintAnchor
 * JD-Core Version:    0.6.0
 */