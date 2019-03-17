package com.bawei.demo.lianxi3.model;

public interface IModel {

    public void getData(CallBack callBack);
    interface  CallBack{
        public void onSuccess(Object data);
        public void onFiled(Object data);
    }
}
