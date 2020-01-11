package com.viger.mycode;

import org.junit.Test;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RxjavaRetrofitOKhttp {

    private OkHttpClient okHttpClient;

    @Test
    public void testRxJavaAndOkHttp() {

    }

//    private void postAsynHttp(String url) {
//        getObservable(url).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Sub);
//    }

    private Observable<String> getObservable(String url) {

        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                okHttpClient = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("","").build();
                Request request = new Request.Builder().url(url)
                        .post(formBody).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        emitter.onNext(response.body().string());
                        emitter.onComplete();
                    }
                });
            }
        });
        return observable;
    }

}
