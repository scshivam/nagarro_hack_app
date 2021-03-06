package rx.internal.util.unsafe;

import java.util.Iterator;
import rx.internal.util.SuppressAnimalSniffer;
import rx.internal.util.atomic.LinkedQueueNode;

@SuppressAnimalSniffer
abstract class BaseLinkedQueue<E> extends BaseLinkedQueueConsumerNodeRef<E>
{
  long p00;
  long p01;
  long p02;
  long p03;
  long p04;
  long p05;
  long p06;
  long p07;
  long p30;
  long p31;
  long p32;
  long p33;
  long p34;
  long p35;
  long p36;
  long p37;

  public final boolean isEmpty()
  {
    return lvConsumerNode() == lvProducerNode();
  }

  public final Iterator<E> iterator()
  {
    throw new UnsupportedOperationException();
  }

  public final int size()
  {
    Object localObject = lvConsumerNode();
    LinkedQueueNode localLinkedQueueNode1 = lvProducerNode();
    for (int i = 0; (localObject != localLinkedQueueNode1) && (i < 2147483647); i++)
    {
      LinkedQueueNode localLinkedQueueNode2;
      do
        localLinkedQueueNode2 = ((LinkedQueueNode)localObject).lvNext();
      while (localLinkedQueueNode2 == null);
      localObject = localLinkedQueueNode2;
    }
    return i;
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.internal.util.unsafe.BaseLinkedQueue
 * JD-Core Version:    0.6.0
 */