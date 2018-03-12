package com.example.belon.project_2018;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.belon.project_2018.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

import static android.R.attr.value;
import static com.example.belon.project_2018.PictureFragment.TAG;

/**
 * Created by zhouyanan on 2018/2/9.
 * RXJava操作符示例
 */

public class RxjavaFragment extends BaseFragment {

    @BindView(R.id.create)
    TextView create;
    @BindView(R.id.defer)
    TextView defer;
    @BindView(R.id.form)
    TextView form;
    @BindView(R.id.interval)
    TextView interval;
    @BindView(R.id.range)
    TextView range;
    @BindView(R.id.map)
    TextView map;
    @BindView(R.id.flatMap)
    TextView flatMap;
    @BindView(R.id.groupBy)
    TextView groupBy;
    @BindView(R.id.threadTest)
    TextView threadTest;
    @BindView(R.id.filter)
    TextView filter;
    @BindView(R.id.zip)
    TextView zip;
    Context mContext;
    Disposable cancel;
    @BindView(R.id.concat)
    TextView concat;
    @BindView(R.id.retryWhen)
    TextView retryWhen;
    Unbinder unbinder;

    public static RxjavaFragment newInstance() {
        return new RxjavaFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mContext = getContext();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rxjava_fragment;
    }

    /**
     * =============创建操作符开始
     * Range操作符发射一个范围内的有序整数序列，你可以指定范围的起始和长度。
     * Timer延时一定时间后发送一个简单的数值。
     */
    @OnClick(R.id.range)
    void range() {
        Observable.range(1, 5).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "accept: " + integer);
            }
        });
    }

    /**
     * 每个一秒按初始值+1一直增长下去。
     */
    @OnClick(R.id.interval)
    void interval() {
        Observable.interval(1, 1, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {
                Log.e(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    /**
     * from操作符可以转换Future、Iterable,array，和数组。对于Iterable,Array和数组，产生的Observable会发射Iterable,Array或数组的每一项数据。
     * 注意不能直接传List，会导致将整个List传过去，应该调用list.toArray转化为数组。
     * just操作符类似，超过三个数自动转换为from
     */
    @OnClick(R.id.form)
    void form() {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            integers.add(i);

        }
        Observable observable = Observable.fromArray(integers.toArray());
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.e(TAG, "onNext: " + value);
            }


            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    /**
     * onSubscribe在订阅成功之后立即调用，我们可以把其参数Disposable 提成全局变量，必要时调用dispose取消订阅。
     * 取消订阅之后和onComplete调用之后上游还是会发送事件，但是下游收不到了。
     */
    @OnClick(R.id.create)
    void create() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                if (!e.isDisposed()) {//判断是否取消订阅
                    for (int i = 1; i < 5; i++) {
                        Log.e(TAG, "subscribe: 调用next");
                        if (i == 1) {
                            Log.e(TAG, "subscribe: 调用compete");
                            e.onComplete();
                        }
                        e.onNext(i);
                    }

                }

            }
        }).subscribe(new Observer<Integer>() {


            @Override
            public void onSubscribe(Disposable d) {
                cancel = d;
                Log.e(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer value) {
                Log.e(TAG, "onNext: " + value);
                if (value == 2) {
                    cancel.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        });
    }

    /**
     * 每个观察者被订阅的时候都重新创建被观察者（一对一）
     * 只有当第一个观察者执行完后才回去创建第二个被观察者然后订阅观察者
     * 然后才开始（第二个被观察者）发送事件消息
     * =========创建操作符结束====/
     */
    @OnClick(R.id.defer)
    void defer() {
        Observable<Integer> defer = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {

                return Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        for (int i = 1; i < 5; i++) {
                            Log.e(TAG, "subscribe: 调用next");
                            e.onNext(i);
                        }
                    }
                });
            }
        });
        defer.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.e(TAG, "onNext-1: " + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        defer.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.e(TAG, "onNext-2: " + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 变换操作开始=======
     * map它的作用就是对上游发送的每一个事件应用一个函数, 使得每一个事件都按照指定的函数去变化
     */
    @OnClick(R.id.map)
    void map() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, s);
            }
        });
    }

    /**
     * 将上游的事件数据转换为Observer,转换的时候是无序的，有序的要使用concatMap
     * 跟map的去区别就是相当于对事件应用的函数放到了Observer中（因为Observer很对操作符可以达到自己写的函数的效果）
     * map将一个事件转换为另一个事件，flatMap将一个事件转换为零或更多事件
     */
    @OnClick(R.id.flatMap)
    void flatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }

    /**
     * 将上游的事件数据按条件进行分组，向下传递
     */
    @OnClick(R.id.groupBy)
    void groupBy() {

        Observable.range(0, 10).groupBy(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                Log.d(TAG, value + Thread.currentThread().getName());
                return integer % 3;////分成0，1，2 三个小组
            }
        }).observeOn(Schedulers.newThread()).subscribe(new Consumer<GroupedObservable<Integer, Integer>>() {
            @Override
            public void accept(final GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) throws Exception {
                integerIntegerGroupedObservable.subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "------>group:" + integerIntegerGroupedObservable.getKey() + "  value:" + value + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

            }
        });
    }

    /**
     * 工作线程的转换测试，subscribeOn只会指定最上游工作线程一次，在调用不会切换线程，而observeOn每调用一次会切换一次。
     */
    @OnClick(R.id.threadTest)
    void threadTest() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "After subscribeOn(newThread), current thread is: " + Thread.currentThread().getName());
                e.onNext(1);
            }
        }).subscribeOn(Schedulers.newThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "After subscribeOn(mainThread), current thread is: " + Thread.currentThread().getName());
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "After subscribeOn(mainThread), current thread is: " + Thread.currentThread().getName());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "After observeOn(mainThread), current thread is: " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "After observeOn(io), current thread is : " + Thread.currentThread().getName());
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

            }
        });

    }

    /**
     * 过滤操作，筛选符合条件的事件
     */
    @OnClick(R.id.filter)
    void filter() {
        Observable.just(1, 2, 1, 2, 3, 1, 4)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 1;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "==========" + integer);
            }
        });
    }

    /**
     * 它使用这个函数按顺序结合两个或多个Observables发射的数据项，然后它发射这个函数返回的结果。
     * 它按照严格的顺序应用这个函数。它只发射与发射数据项最少的那个Observable一样多的数据。
     * 比如登录时既需要密码正确，又需要验证码正确，当两者都正确的时候返回true，提示登录成功。
     */
    @OnClick(R.id.zip)
    void zip() {
        Observable.zip(Observable.just(1, 2, 3, 4), Observable.just("A", "B", "C", "D"), new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "==========" + s);
            }
        });
    }

    /**
     * 此处有四个操作符：concat,firstElement,toObservable,onErrorReturn.
     * 将多个Observable按顺序执行,（出现异常时）调用onError之后会停止之后的事件进行
     * 结合firstElement和toObservable操作符可以实现完成第一个事件就不会继续往下进行了，可以用于三级缓存
     * onErrorReturn操作符重新设置onError的时候的返回值，之前的作废
     */
    @OnClick(R.id.concat)
    void concat() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()) {
                    for (int i = 0; i < 3; i++) {
                        if (i < 1) {
                            e.onNext(i+"");
                        } else if (i==1){
                            try {
                                throw  new NumberFormatException();
                            }catch (Exception error)
                            {
                                e.onError(error);
                            }
                        }else{
                            e.onNext(i+"");
                        }
                    }
                    e.onComplete();
                }
            }
        });
        Observable.concat(observable,Observable.just("A"))
//                .onErrorReturn(new Function<Throwable, String>() {
//                    @Override
//                    public String apply(@NonNull Throwable throwable) throws Exception {
//                        return "出错了，智宝宝";
//                    }
//                })
//              .firstElement()
//              .toObservable()
              .subscribe(new Observer<String>() {
                  @Override
                  public void onSubscribe(@NonNull Disposable d) {
                  }

                  @Override
                  public void onNext(@NonNull String s) {
                      Log.d(TAG, "==========" + s);
                  }

                  @Override
                  public void onError(@NonNull Throwable e) {
                      Log.d(TAG, "==========" + e.toString());
                  }

                  @Override
                  public void onComplete() {

                  }
              });
    }

    /**
     *该操作符可以指定出异常时候（onError调用的时候）重新尝试执行事件的时机（重试次数，延迟时间）
     * 可用于网络的请求失败的重新请求
     * 以下为简单实现，正式使用的时候要将Function单独封装一个，并将属性封装进去。
     */
    @OnClick(R.id.retryWhen)
    void retryWhen() {
        final int[] i = {0};
        final int[] retryCount = {0};
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "==========" + "重试"+i[0]++);
                try {
                    throw  new NumberFormatException();
                }catch (Exception error)
                {
                    e.onError(error);
                }
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                        if (++retryCount[0] <= 3) {
                            // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                            return Observable.timer(1000,
                                    TimeUnit.MILLISECONDS);
                        }
                        // Max retries hit. Just pass the error along.
                        return Observable.error(throwable);
                    }
                });
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
            }

        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

