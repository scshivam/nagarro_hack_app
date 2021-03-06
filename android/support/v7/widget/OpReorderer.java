package android.support.v7.widget;

import java.util.List;

class OpReorderer
{
  final Callback mCallback;

  OpReorderer(Callback paramCallback)
  {
    this.mCallback = paramCallback;
  }

  private int getLastMoveOutOfOrder(List<AdapterHelper.UpdateOp> paramList)
  {
    int i = 0;
    for (int j = -1 + paramList.size(); j >= 0; j--)
      if (((AdapterHelper.UpdateOp)paramList.get(j)).cmd == 8)
      {
        if (i != 0)
          return j;
      }
      else
        i = 1;
    return -1;
  }

  private void swapMoveAdd(List<AdapterHelper.UpdateOp> paramList, int paramInt1, AdapterHelper.UpdateOp paramUpdateOp1, int paramInt2, AdapterHelper.UpdateOp paramUpdateOp2)
  {
    int i = paramUpdateOp1.itemCount;
    int j = paramUpdateOp2.positionStart;
    int k = 0;
    if (i < j)
      k = 0 - 1;
    if (paramUpdateOp1.positionStart < paramUpdateOp2.positionStart)
      k++;
    if (paramUpdateOp2.positionStart <= paramUpdateOp1.positionStart)
      paramUpdateOp1.positionStart += paramUpdateOp2.itemCount;
    if (paramUpdateOp2.positionStart <= paramUpdateOp1.itemCount)
      paramUpdateOp1.itemCount += paramUpdateOp2.itemCount;
    paramUpdateOp2.positionStart = (k + paramUpdateOp2.positionStart);
    paramList.set(paramInt1, paramUpdateOp2);
    paramList.set(paramInt2, paramUpdateOp1);
  }

  private void swapMoveOp(List<AdapterHelper.UpdateOp> paramList, int paramInt1, int paramInt2)
  {
    AdapterHelper.UpdateOp localUpdateOp1 = (AdapterHelper.UpdateOp)paramList.get(paramInt1);
    AdapterHelper.UpdateOp localUpdateOp2 = (AdapterHelper.UpdateOp)paramList.get(paramInt2);
    switch (localUpdateOp2.cmd)
    {
    case 3:
    default:
      return;
    case 2:
      swapMoveRemove(paramList, paramInt1, localUpdateOp1, paramInt2, localUpdateOp2);
      return;
    case 1:
      swapMoveAdd(paramList, paramInt1, localUpdateOp1, paramInt2, localUpdateOp2);
      return;
    case 4:
    }
    swapMoveUpdate(paramList, paramInt1, localUpdateOp1, paramInt2, localUpdateOp2);
  }

  void reorderOps(List<AdapterHelper.UpdateOp> paramList)
  {
    while (true)
    {
      int i = getLastMoveOutOfOrder(paramList);
      if (i == -1)
        break;
      swapMoveOp(paramList, i, i + 1);
    }
  }

  void swapMoveRemove(List<AdapterHelper.UpdateOp> paramList, int paramInt1, AdapterHelper.UpdateOp paramUpdateOp1, int paramInt2, AdapterHelper.UpdateOp paramUpdateOp2)
  {
    AdapterHelper.UpdateOp localUpdateOp = null;
    int i;
    int m;
    if (paramUpdateOp1.positionStart < paramUpdateOp1.itemCount)
    {
      int i5 = paramUpdateOp2.positionStart;
      int i6 = paramUpdateOp1.positionStart;
      i = 0;
      m = 0;
      if (i5 == i6)
      {
        int i7 = paramUpdateOp2.itemCount;
        int i8 = paramUpdateOp1.itemCount - paramUpdateOp1.positionStart;
        i = 0;
        m = 0;
        if (i7 == i8)
          m = 1;
      }
      if (paramUpdateOp1.itemCount >= paramUpdateOp2.positionStart)
        break label219;
      paramUpdateOp2.positionStart = (-1 + paramUpdateOp2.positionStart);
      label98: if (paramUpdateOp1.positionStart > paramUpdateOp2.positionStart)
        break label288;
      paramUpdateOp2.positionStart = (1 + paramUpdateOp2.positionStart);
      label122: if (m == 0)
        break label374;
      paramList.set(paramInt1, paramUpdateOp2);
      paramList.remove(paramInt2);
      this.mCallback.recycleUpdateOp(paramUpdateOp1);
    }
    label646: label656: 
    while (true)
    {
      return;
      i = 1;
      int j = paramUpdateOp2.positionStart;
      int k = 1 + paramUpdateOp1.itemCount;
      m = 0;
      if (j != k)
        break;
      int n = paramUpdateOp2.itemCount;
      int i1 = paramUpdateOp1.positionStart - paramUpdateOp1.itemCount;
      m = 0;
      if (n != i1)
        break;
      m = 1;
      break;
      label219: if (paramUpdateOp1.itemCount >= paramUpdateOp2.positionStart + paramUpdateOp2.itemCount)
        break label98;
      paramUpdateOp2.itemCount = (-1 + paramUpdateOp2.itemCount);
      paramUpdateOp1.cmd = 2;
      paramUpdateOp1.itemCount = 1;
      if (paramUpdateOp2.itemCount != 0)
        continue;
      paramList.remove(paramInt2);
      this.mCallback.recycleUpdateOp(paramUpdateOp2);
      return;
      label288: int i2 = paramUpdateOp1.positionStart;
      int i3 = paramUpdateOp2.positionStart + paramUpdateOp2.itemCount;
      localUpdateOp = null;
      if (i2 >= i3)
        break label122;
      int i4 = paramUpdateOp2.positionStart + paramUpdateOp2.itemCount - paramUpdateOp1.positionStart;
      localUpdateOp = this.mCallback.obtainUpdateOp(2, 1 + paramUpdateOp1.positionStart, i4, null);
      paramUpdateOp2.itemCount = (paramUpdateOp1.positionStart - paramUpdateOp2.positionStart);
      break label122;
      label374: if (i != 0)
      {
        if (localUpdateOp != null)
        {
          if (paramUpdateOp1.positionStart > localUpdateOp.positionStart)
            paramUpdateOp1.positionStart -= localUpdateOp.itemCount;
          if (paramUpdateOp1.itemCount > localUpdateOp.positionStart)
            paramUpdateOp1.itemCount -= localUpdateOp.itemCount;
        }
        if (paramUpdateOp1.positionStart > paramUpdateOp2.positionStart)
          paramUpdateOp1.positionStart -= paramUpdateOp2.itemCount;
        if (paramUpdateOp1.itemCount > paramUpdateOp2.positionStart)
          paramUpdateOp1.itemCount -= paramUpdateOp2.itemCount;
        paramList.set(paramInt1, paramUpdateOp2);
        if (paramUpdateOp1.positionStart == paramUpdateOp1.itemCount)
          break label646;
        paramList.set(paramInt2, paramUpdateOp1);
      }
      while (true)
      {
        if (localUpdateOp == null)
          break label656;
        paramList.add(paramInt1, localUpdateOp);
        return;
        if (localUpdateOp != null)
        {
          if (paramUpdateOp1.positionStart >= localUpdateOp.positionStart)
            paramUpdateOp1.positionStart -= localUpdateOp.itemCount;
          if (paramUpdateOp1.itemCount >= localUpdateOp.positionStart)
            paramUpdateOp1.itemCount -= localUpdateOp.itemCount;
        }
        if (paramUpdateOp1.positionStart >= paramUpdateOp2.positionStart)
          paramUpdateOp1.positionStart -= paramUpdateOp2.itemCount;
        if (paramUpdateOp1.itemCount < paramUpdateOp2.positionStart)
          break;
        paramUpdateOp1.itemCount -= paramUpdateOp2.itemCount;
        break;
        paramList.remove(paramInt2);
      }
    }
  }

  void swapMoveUpdate(List<AdapterHelper.UpdateOp> paramList, int paramInt1, AdapterHelper.UpdateOp paramUpdateOp1, int paramInt2, AdapterHelper.UpdateOp paramUpdateOp2)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    if (paramUpdateOp1.itemCount < paramUpdateOp2.positionStart)
    {
      paramUpdateOp2.positionStart = (-1 + paramUpdateOp2.positionStart);
      if (paramUpdateOp1.positionStart > paramUpdateOp2.positionStart)
        break label177;
      paramUpdateOp2.positionStart = (1 + paramUpdateOp2.positionStart);
      label54: paramList.set(paramInt2, paramUpdateOp1);
      if (paramUpdateOp2.itemCount <= 0)
        break label265;
      paramList.set(paramInt1, paramUpdateOp2);
    }
    while (true)
    {
      if (localObject1 != null)
        paramList.add(paramInt1, localObject1);
      if (localObject2 != null)
        paramList.add(paramInt1, localObject2);
      return;
      int i = paramUpdateOp1.itemCount;
      int j = paramUpdateOp2.positionStart + paramUpdateOp2.itemCount;
      localObject1 = null;
      if (i >= j)
        break;
      paramUpdateOp2.itemCount = (-1 + paramUpdateOp2.itemCount);
      localObject1 = this.mCallback.obtainUpdateOp(4, paramUpdateOp1.positionStart, 1, paramUpdateOp2.payload);
      break;
      label177: int k = paramUpdateOp1.positionStart;
      int m = paramUpdateOp2.positionStart + paramUpdateOp2.itemCount;
      localObject2 = null;
      if (k >= m)
        break label54;
      int n = paramUpdateOp2.positionStart + paramUpdateOp2.itemCount - paramUpdateOp1.positionStart;
      localObject2 = this.mCallback.obtainUpdateOp(4, 1 + paramUpdateOp1.positionStart, n, paramUpdateOp2.payload);
      paramUpdateOp2.itemCount -= n;
      break label54;
      label265: paramList.remove(paramInt1);
      this.mCallback.recycleUpdateOp(paramUpdateOp2);
    }
  }

  static abstract interface Callback
  {
    public abstract AdapterHelper.UpdateOp obtainUpdateOp(int paramInt1, int paramInt2, int paramInt3, Object paramObject);

    public abstract void recycleUpdateOp(AdapterHelper.UpdateOp paramUpdateOp);
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.OpReorderer
 * JD-Core Version:    0.6.0
 */