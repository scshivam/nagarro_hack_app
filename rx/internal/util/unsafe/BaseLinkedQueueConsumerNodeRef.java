package rx.internal.util.unsafe;

import rx.internal.util.SuppressAnimalSniffer;
import rx.internal.util.atomic.LinkedQueueNode;
import sun.misc.Unsafe;

@SuppressAnimalSniffer
abstract class BaseLinkedQueueConsumerNodeRef<E> extends BaseLinkedQueuePad1<E>
{
  protected static final long C_NODE_OFFSET = UnsafeAccess.addressOf(BaseLinkedQueueConsumerNodeRef.class, "consumerNode");
  protected LinkedQueueNode<E> consumerNode;

  protected final LinkedQueueNode<E> lpConsumerNode()
  {
    return this.consumerNode;
  }

  protected final LinkedQueueNode<E> lvConsumerNode()
  {
    return (LinkedQueueNode)UnsafeAccess.UNSAFE.getObjectVolatile(this, C_NODE_OFFSET);
  }

  protected final void spConsumerNode(LinkedQueueNode<E> paramLinkedQueueNode)
  {
    this.consumerNode = paramLinkedQueueNode;
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef
 * JD-Core Version:    0.6.0
 */