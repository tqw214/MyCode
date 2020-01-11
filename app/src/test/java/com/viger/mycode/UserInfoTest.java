package com.viger.mycode;

import com.viger.mycode.net.BaseResult;
import com.viger.mycode.net.HttpCallBack;
import com.viger.mycode.net.UserInfoModel;

import org.junit.Test;

public class UserInfoTest {

    @Test
    public void testUserInfoModel() {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.getUserInfo(1, new HttpCallBack<BaseResult>() {

            @Override
            public void onSuccess(BaseResult result) {
                System.out.println("onSuccess===>"+result.getMessage());
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

}
