package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import java.util.ArrayList;

public class ConstraintHorizontalLayout extends ConstraintWidgetContainer
{
  private ContentAlignment mAlignment = ContentAlignment.MIDDLE;

  public ConstraintHorizontalLayout()
  {
  }

  public ConstraintHorizontalLayout(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2);
  }

  public ConstraintHorizontalLayout(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void addToSolver(LinearSystem paramLinearSystem, int paramInt)
  {
    if (this.mChildren.size() != 0)
    {
      Object localObject = this;
      int i = 0;
      int j = this.mChildren.size();
      if (i < j)
      {
        ConstraintWidget localConstraintWidget = (ConstraintWidget)this.mChildren.get(i);
        if (localObject != this)
        {
          localConstraintWidget.connect(ConstraintAnchor.Type.LEFT, (ConstraintWidget)localObject, ConstraintAnchor.Type.RIGHT);
          ((ConstraintWidget)localObject).connect(ConstraintAnchor.Type.RIGHT, localConstraintWidget, ConstraintAnchor.Type.LEFT);
        }
        while (true)
        {
          localConstraintWidget.connect(ConstraintAnchor.Type.TOP, this, ConstraintAnchor.Type.TOP);
          localConstraintWidget.connect(ConstraintAnchor.Type.BOTTOM, this, ConstraintAnchor.Type.BOTTOM);
          localObject = localConstraintWidget;
          i++;
          break;
          ConstraintAnchor.Strength localStrength2 = ConstraintAnchor.Strength.STRONG;
          if (this.mAlignment == ContentAlignment.END)
            localStrength2 = ConstraintAnchor.Strength.WEAK;
          localConstraintWidget.connect(ConstraintAnchor.Type.LEFT, (ConstraintWidget)localObject, ConstraintAnchor.Type.LEFT, 0, localStrength2);
        }
      }
      if (localObject != this)
      {
        ConstraintAnchor.Strength localStrength1 = ConstraintAnchor.Strength.STRONG;
        if (this.mAlignment == ContentAlignment.BEGIN)
          localStrength1 = ConstraintAnchor.Strength.WEAK;
        ConstraintAnchor.Type localType1 = ConstraintAnchor.Type.RIGHT;
        ConstraintAnchor.Type localType2 = ConstraintAnchor.Type.RIGHT;
        ((ConstraintWidget)localObject).connect(localType1, this, localType2, 0, localStrength1);
      }
    }
    super.addToSolver(paramLinearSystem, paramInt);
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
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.solver.widgets.ConstraintHorizontalLayout
 * JD-Core Version:    0.6.0
 */