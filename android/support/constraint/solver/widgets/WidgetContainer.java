package android.support.constraint.solver.widgets;

import android.support.constraint.solver.Cache;
import java.util.ArrayList;

public class WidgetContainer extends ConstraintWidget
{
  protected ArrayList<ConstraintWidget> mChildren = new ArrayList();

  public WidgetContainer()
  {
  }

  public WidgetContainer(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2);
  }

  public WidgetContainer(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public static Rectangle getBounds(ArrayList<ConstraintWidget> paramArrayList)
  {
    Rectangle localRectangle = new Rectangle();
    if (paramArrayList.size() == 0)
      return localRectangle;
    int i = 2147483647;
    int j = 0;
    int k = 2147483647;
    int m = 0;
    int n = 0;
    int i1 = paramArrayList.size();
    while (n < i1)
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)paramArrayList.get(n);
      if (localConstraintWidget.getX() < i)
        i = localConstraintWidget.getX();
      if (localConstraintWidget.getY() < k)
        k = localConstraintWidget.getY();
      if (localConstraintWidget.getRight() > j)
        j = localConstraintWidget.getRight();
      if (localConstraintWidget.getBottom() > m)
        m = localConstraintWidget.getBottom();
      n++;
    }
    localRectangle.setBounds(i, k, j - i, m - k);
    return localRectangle;
  }

  public void add(ConstraintWidget paramConstraintWidget)
  {
    this.mChildren.add(paramConstraintWidget);
    if (paramConstraintWidget.getParent() != null)
      ((WidgetContainer)paramConstraintWidget.getParent()).remove(paramConstraintWidget);
    paramConstraintWidget.setParent(this);
  }

  public ConstraintWidget findWidget(float paramFloat1, float paramFloat2)
  {
    int i = getDrawX();
    int j = getDrawY();
    int k = i + getWidth();
    int m = j + getHeight();
    boolean bool1 = paramFloat1 < i;
    Object localObject = null;
    if (!bool1)
    {
      boolean bool2 = paramFloat1 < k;
      localObject = null;
      if (!bool2)
      {
        boolean bool3 = paramFloat2 < j;
        localObject = null;
        if (!bool3)
        {
          boolean bool4 = paramFloat2 < m;
          localObject = null;
          if (!bool4)
            localObject = this;
        }
      }
    }
    int n = 0;
    int i1 = this.mChildren.size();
    if (n < i1)
    {
      ConstraintWidget localConstraintWidget1 = (ConstraintWidget)this.mChildren.get(n);
      ConstraintWidget localConstraintWidget2;
      if ((localConstraintWidget1 instanceof WidgetContainer))
      {
        localConstraintWidget2 = ((WidgetContainer)localConstraintWidget1).findWidget(paramFloat1, paramFloat2);
        if (localConstraintWidget2 == null);
      }
      for (localObject = localConstraintWidget2; ; localObject = localConstraintWidget1)
      {
        int i2;
        int i3;
        int i4;
        int i5;
        do
        {
          n++;
          break;
          i2 = localConstraintWidget1.getDrawX();
          i3 = localConstraintWidget1.getDrawY();
          i4 = i2 + localConstraintWidget1.getWidth();
          i5 = i3 + localConstraintWidget1.getHeight();
        }
        while ((paramFloat1 < i2) || (paramFloat1 > i4) || (paramFloat2 < i3) || (paramFloat2 > i5));
      }
    }
    return (ConstraintWidget)localObject;
  }

  public ArrayList<ConstraintWidget> findWidgets(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    ArrayList localArrayList = new ArrayList();
    Rectangle localRectangle1 = new Rectangle();
    localRectangle1.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    int i = 0;
    int j = this.mChildren.size();
    while (i < j)
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)this.mChildren.get(i);
      Rectangle localRectangle2 = new Rectangle();
      localRectangle2.setBounds(localConstraintWidget.getDrawX(), localConstraintWidget.getDrawY(), localConstraintWidget.getWidth(), localConstraintWidget.getHeight());
      if (localRectangle1.intersects(localRectangle2))
        localArrayList.add(localConstraintWidget);
      i++;
    }
    return localArrayList;
  }

  public ArrayList<ConstraintWidget> getChildren()
  {
    return this.mChildren;
  }

  public ConstraintWidgetContainer getRootConstraintContainer()
  {
    ConstraintWidget localConstraintWidget1 = getParent();
    boolean bool = this instanceof ConstraintWidgetContainer;
    ConstraintWidgetContainer localConstraintWidgetContainer = null;
    if (bool)
      localConstraintWidgetContainer = (ConstraintWidgetContainer)this;
    while (localConstraintWidget1 != null)
    {
      ConstraintWidget localConstraintWidget2 = localConstraintWidget1;
      localConstraintWidget1 = localConstraintWidget2.getParent();
      if (!(localConstraintWidget2 instanceof ConstraintWidgetContainer))
        continue;
      localConstraintWidgetContainer = (ConstraintWidgetContainer)localConstraintWidget2;
    }
    return localConstraintWidgetContainer;
  }

  public void layout()
  {
    updateDrawPosition();
    if (this.mChildren == null);
    while (true)
    {
      return;
      int i = this.mChildren.size();
      for (int j = 0; j < i; j++)
      {
        ConstraintWidget localConstraintWidget = (ConstraintWidget)this.mChildren.get(j);
        if (!(localConstraintWidget instanceof WidgetContainer))
          continue;
        ((WidgetContainer)localConstraintWidget).layout();
      }
    }
  }

  public void remove(ConstraintWidget paramConstraintWidget)
  {
    this.mChildren.remove(paramConstraintWidget);
    paramConstraintWidget.setParent(null);
  }

  public void removeAllChildren()
  {
    this.mChildren.clear();
  }

  public void reset()
  {
    this.mChildren.clear();
    super.reset();
  }

  public void resetGroups()
  {
    super.resetGroups();
    int i = this.mChildren.size();
    for (int j = 0; j < i; j++)
      ((ConstraintWidget)this.mChildren.get(j)).resetGroups();
  }

  public void resetSolverVariables(Cache paramCache)
  {
    super.resetSolverVariables(paramCache);
    int i = this.mChildren.size();
    for (int j = 0; j < i; j++)
      ((ConstraintWidget)this.mChildren.get(j)).resetSolverVariables(paramCache);
  }

  public void setOffset(int paramInt1, int paramInt2)
  {
    super.setOffset(paramInt1, paramInt2);
    int i = this.mChildren.size();
    for (int j = 0; j < i; j++)
      ((ConstraintWidget)this.mChildren.get(j)).setOffset(getRootX(), getRootY());
  }

  public void updateDrawPosition()
  {
    super.updateDrawPosition();
    if (this.mChildren == null);
    while (true)
    {
      return;
      int i = this.mChildren.size();
      for (int j = 0; j < i; j++)
      {
        ConstraintWidget localConstraintWidget = (ConstraintWidget)this.mChildren.get(j);
        localConstraintWidget.setOffset(getDrawX(), getDrawY());
        if ((localConstraintWidget instanceof ConstraintWidgetContainer))
          continue;
        localConstraintWidget.updateDrawPosition();
      }
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.solver.widgets.WidgetContainer
 * JD-Core Version:    0.6.0
 */