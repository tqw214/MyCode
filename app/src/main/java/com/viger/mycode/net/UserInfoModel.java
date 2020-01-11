package com.viger.mycode.net;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserInfoModel {

    public void getUserInfo(int userId, HttpCallBack callBack) {
        RetrofitManager.getApi().getUserInfo(userId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResult<UserInfo>>() {
                    @Override
                    public void accept(BaseResult<UserInfo> userInfoBaseResult) throws Exception {
                        callBack.onSuccess(userInfoBaseResult);
                    }
                });
    }

}
