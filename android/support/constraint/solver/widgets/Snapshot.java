package android.support.constraint.solver.widgets;

import java.util.ArrayList;

public class Snapshot
{
  private ArrayList<Connection> mConnections = new ArrayList();
  private int mHeight;
  private int mWidth;
  private int mX;
  private int mY;

  public Snapshot(ConstraintWidget paramConstraintWidget)
  {
    this.mX = paramConstraintWidget.getX();
    this.mY = paramConstraintWidget.getY();
    this.mWidth = paramConstraintWidget.getWidth();
    this.mHeight = paramConstraintWidget.getHeight();
    ArrayList localArrayList = paramConstraintWidget.getAnchors();
    int i = 0;
    int j = localArrayList.size();
    while (i < j)
    {
      ConstraintAnchor localConstraintAnchor = (ConstraintAnchor)localArrayList.get(i);
      this.mConnections.add(new Connection(localConstraintAnchor));
      i++;
    }
  }

  public void applyTo(ConstraintWidget paramConstraintWidget)
  {
    paramConstraintWidget.setX(this.mX);
    paramConstraintWidget.setY(this.mY);
    paramConstraintWidget.setWidth(this.mWidth);
    paramConstraintWidget.setHeight(this.mHeight);
    int i = 0;
    int j = this.mConnections.size();
    while (i < j)
    {
      ((Connection)this.mConnections.get(i)).applyTo(paramConstraintWidget);
      i++;
    }
  }

  public void updateFrom(ConstraintWidget paramConstraintWidget)
  {
    this.mX = paramConstraintWidget.getX();
    this.mY = paramConstraintWidget.getY();
    this.mWidth = paramConstraintWidget.getWidth();
    this.mHeight = paramConstraintWidget.getHeight();
    int i = this.mConnections.size();
    for (int j = 0; j < i; j++)
      ((Connection)this.mConnections.get(j)).updateFrom(paramConstraintWidget);
  }

  static class Connection
  {
    private ConstraintAnchor mAnchor;
    private int mCreator;
    private int mMargin;
    private ConstraintAnchor.Strength mStrengh;
    private ConstraintAnchor mTarget;

    public Connection(ConstraintAnchor paramConstraintAnchor)
    {
      this.mAnchor = paramConstraintAnchor;
      this.mTarget = paramConstraintAnchor.getTarget();
      this.mMargin = paramConstraintAnchor.getMargin();
      this.mStrengh = paramConstraintAnchor.getStrength();
      this.mCreator = paramConstraintAnchor.getConnectionCreator();
    }

    public void applyTo(ConstraintWidget paramConstraintWidget)
    {
      paramConstraintWidget.getAnchor(this.mAnchor.getType()).connect(this.mTarget, this.mMargin, this.mStrengh, this.mCreator);
    }

    public void updateFrom(ConstraintWidget paramConstraintWidget)
    {
      this.mAnchor = paramConstraintWidget.getAnchor(this.mAnchor.getType());
      if (this.mAnchor != null)
      {
        this.mTarget = this.mAnchor.getTarget();
        this.mMargin = this.mAnchor.getMargin();
        this.mStrengh = this.mAnchor.getStrength();
        this.mCreator = this.mAnchor.getConnectionCreator();
        return;
      }
      this.mTarget = null;
      this.mMargin = 0;
      this.mStrengh = ConstraintAnchor.Strength.STRONG;
      this.mCreator = 0;
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.solver.widgets.Snapshot
 * JD-Core Version:    0.6.0
 */