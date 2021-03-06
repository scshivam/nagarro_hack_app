package rx.internal.operators;

import java.util.NoSuchElementException;
import rx.Observable;
import rx.Single.OnSubscribe;
import rx.SingleSubscriber;
import rx.Subscriber;

public class OnSubscribeSingle<T>
  implements Single.OnSubscribe<T>
{
  private final Observable<T> observable;

  public OnSubscribeSingle(Observable<T> paramObservable)
  {
    this.observable = paramObservable;
  }

  public static <T> OnSubscribeSingle<T> create(Observable<T> paramObservable)
  {
    return new OnSubscribeSingle(paramObservable);
  }

  public void call(SingleSubscriber<? super T> paramSingleSubscriber)
  {
    1 local1 = new Subscriber(paramSingleSubscriber)
    {
      private T emission;
      private boolean emittedTooMany;
      private boolean itemEmitted;

      public void onCompleted()
      {
        if (this.emittedTooMany)
          return;
        if (this.itemEmitted)
        {
          this.val$child.onSuccess(this.emission);
          return;
        }
        this.val$child.onError(new NoSuchElementException("Observable emitted no items"));
      }

      public void onError(Throwable paramThrowable)
      {
        this.val$child.onError(paramThrowable);
        unsubscribe();
      }

      public void onNext(T paramT)
      {
        if (this.itemEmitted)
        {
          this.emittedTooMany = true;
          this.val$child.onError(new IllegalArgumentException("Observable emitted too many elements"));
          unsubscribe();
          return;
        }
        this.itemEmitted = true;
        this.emission = paramT;
      }

      public void onStart()
      {
        request(2L);
      }
    };
    paramSingleSubscriber.add(local1);
    this.observable.unsafeSubscribe(local1);
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.internal.operators.OnSubscribeSingle
 * JD-Core Version:    0.6.0
 */