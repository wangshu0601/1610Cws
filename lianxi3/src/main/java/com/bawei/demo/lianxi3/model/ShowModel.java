package com.bawei.demo.lianxi3.model;

import com.bawei.demo.lianxi3.bean.User;
import com.bawei.demo.lianxi3.okhttp.OkHttp;

public class ShowModel implements IModel{
    String url = "http://172.17.8.100/small/commodity/v1/commodityList";
    @Override
    public void getData(final CallBack callBack) {
        OkHttp.getokHttp().onGet(url, User.class, new OkHttp.NetCallBack() {
            @Override
            public void loadSuccess(Object obj) {
                callBack.onSuccess(obj);
            }

            @Override
            public void loadFiled() {

            }
        });
    }
}
