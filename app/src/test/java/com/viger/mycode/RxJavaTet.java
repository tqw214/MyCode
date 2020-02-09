package com.viger.mycode;


import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;

import org.junit.Test;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
//        Observable.just(1,2,3,4)
//                .all(new Predicate<Integer>() {
//                    @Override
//                    public boolean test(Integer integer) throws Exception {
//                        return false;
//                    }
//                }).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//
//            }
//        });
//
//        Observable.just(1).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                System.out.println("just===" + integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//
//            @Override
//            public void onComplete() {
//            }
//        });
//    }
//
//    public void test02(){
//        Observable.just(1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//
//            }
//        });
//
//        Observable.just(2).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//
//            }
//        });

        String[] strings = {"商品类","非商品类"};
        Observable.fromArray(strings).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
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
    public void tests() {
//        List<String> list = new ArrayList();
//        for (int i = 0; i < 3; i++) {
//            String g = new String("名称" + i);
//            list.add(g);
//        }
//        Observable.fromIterable(list).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println(s);
//            }
//        });

//        Observable.just(111).map(new Function<Integer, String>() {
//            @Override
//            public String apply(Integer integer) throws Exception {
//                return integer.toString();
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println(s);
//            }
//        });

        Observable.just(1,2,3)
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        return Observable.just(integer * 10 + "");
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });

    }

    class Model {

        public void te(){
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            Request.Builder builder = new Request.Builder().url("");
            builder.method("GET",null);
            Request request = builder.build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        }

        public void post() {
            OkHttpClient client = new OkHttpClient.Builder().build();
            RequestBody requestBody = new FormBody.Builder().add("key","value").build();
            Request request = new Request.Builder().post(requestBody).url("").addHeader("key","value").build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        }



    }

    public void test08(){

        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
                Request.Builder builder = new Request.Builder().url("").get();
                Request request = builder.build();
                Call call = new OkHttpClient().newCall(request);
                Response response = call.execute();
                emitter.onNext(response);
            }
        }).map(new Function<Response, Model>() {
            @Override
            public Model apply(Response response) throws Exception {
                if(response.isSuccessful()) {
                    ResponseBody body = response.body();
                    Log.d("tag","转换前:"+body);
                    if(body != null) {
                        String json = response.body().string();
                        Model model = new Gson().fromJson(json, Model.class);
                        return model;
                    }
                }
                return null;
            }
        }).doOnNext(new Consumer<Model>() {
            @Override
            public void accept(Model model) throws Exception {
                Log.d("tag","进行数据处理....");
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).
        subscribe(new Consumer<Model>() {
            @Override
            public void accept(Model model) throws Exception {
                Log.d("tag","成功刷新界面");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d("tag","失败"+throwable.getMessage());
            }
        });

    }




}
