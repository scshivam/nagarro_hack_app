package android.support.constraint.solver;

final class Pools
{
  private static final boolean DEBUG;

  static abstract interface Pool<T>
  {
    public abstract T acquire();

    public abstract boolean release(T paramT);

    public abstract void releaseAll(T[] paramArrayOfT, int paramInt);
  }

  static class SimplePool<T>
    implements Pools.Pool<T>
  {
    private final Object[] mPool;
    private int mPoolSize;

    SimplePool(int paramInt)
    {
      if (paramInt <= 0)
        throw new IllegalArgumentException("The max pool size must be > 0");
      this.mPool = new Object[paramInt];
    }

    private boolean isInPool(T paramT)
    {
      for (int i = 0; i < this.mPoolSize; i++)
        if (this.mPool[i] == paramT)
          return true;
      return false;
    }

    public T acquire()
    {
      if (this.mPoolSize > 0)
      {
        int i = -1 + this.mPoolSize;
        Object localObject = this.mPool[i];
        this.mPool[i] = null;
        this.mPoolSize = (-1 + this.mPoolSize);
        return localObject;
      }
      return null;
    }

    public boolean release(T paramT)
    {
      if (this.mPoolSize < this.mPool.length)
      {
        this.mPool[this.mPoolSize] = paramT;
        this.mPoolSize = (1 + this.mPoolSize);
        return true;
      }
      return false;
    }

    public void releaseAll(T[] paramArrayOfT, int paramInt)
    {
      if (paramInt > paramArrayOfT.length)
        paramInt = paramArrayOfT.length;
      for (int i = 0; i < paramInt; i++)
      {
        T ? = paramArrayOfT[i];
        if (this.mPoolSize >= this.mPool.length)
          continue;
        this.mPool[this.mPoolSize] = ?;
        this.mPoolSize = (1 + this.mPoolSize);
      }
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.constraint.solver.Pools
 * JD-Core Version:    0.6.0
 */