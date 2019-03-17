package com.bawei.demo.lianxi3.presenter;

import com.bawei.demo.lianxi3.model.IModel;
import com.bawei.demo.lianxi3.model.ShowModel;
import com.bawei.demo.lianxi3.view.IView;

import java.lang.ref.SoftReference;

public class ShowPresenter implements IPresenter{
    IView iView;
    private IModel iModel;
    private ShowModel showModel;
    private SoftReference<ShowModel> showModelSoftReference;

    @Override
    public void BingDing(IView iView) {
        this.iView = iView;
        showModel = new ShowModel();
        showModelSoftReference = new SoftReference<>(showModel);
    }

    @Override
    public void JieBang(IView iView) {
        showModelSoftReference.clear();
    }

    @Override
    public void RequestData() {
        showModel.getData(new IModel.CallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.ShowData(data);
            }

            @Override
            public void onFiled(Object data) {

            }
        });
    }
}
