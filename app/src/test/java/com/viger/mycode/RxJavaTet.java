package com.viger.mycode;


import android.graphics.Bitmap;

import com.viger.mycode.rxjava.Disposeble;
import com.viger.mycode.rxjava.ObservableEmitter;
import com.viger.mycode.rxjava.ObservableOnSubscribe;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxJavaTet {

    @Test
    public void test01(){
//        Observable.just("https://www.baidu.com/img/bd_logo1.png")
//                .map(new Function<String, Bitmap>() {
//                    @Override
//                    public Bitmap apply(String url) throws Exception {
//                        return getBitmapFromServer(url);
//                    }
//                })
//                .map(new Function<Bitmap, Bitmap>() {
//                    @Override
//                    public Bitmap apply(Bitmap bitmap) throws Exception {
//                        return createSyBitmap(bitmap);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Bitmap>() {
//                    @Override
//                    public void accept(Bitmap bitmap) throws Exception {
//                        //show bitmap
//                    }
//                });

    }

    private Bitmap createSyBitmap(Bitmap bitmap) {
        return null;
    }

    private Bitmap getBitmapFromServer(String url) {

        Observable.just("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return null;
    }

    @Test
    public void test() {
        Observable.just(1,2,3,4)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return false;
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }
        });

        Observable.just(1).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("just===" + integer);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Test
    public void testMyRxJava(){
        com.viger.mycode.rxjava.Observable observable = com.viger.mycode.rxjava.Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("666");
                emitter.onComplete();
            }
        });
        observable.subscribe(new com.viger.mycode.rxjava.Observer<String>() {
            @Override
            public void onSubscribe(Disposeble d) {
                System.out.println("onSubscribe===>");
            }

            @Override
            public void onNext(String o) {
                System.out.println("onNext===>" + o);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete===>");
            }
        });
    }


}
