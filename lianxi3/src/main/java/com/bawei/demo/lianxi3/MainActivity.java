package com.bawei.demo.lianxi3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bawei.demo.lianxi3.adapter.MyAdapter;
import com.bawei.demo.lianxi3.bean.User;
import com.bawei.demo.lianxi3.presenter.ShowPresenter;
import com.bawei.demo.lianxi3.view.IView;

public class MainActivity extends AppCompatActivity implements IView {


    private ShowPresenter showPresenter;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showPresenter = new ShowPresenter();
        showPresenter.BingDing(this);
        showPresenter.RequestData();
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void ShowData(Object resqonse) {
        if(resqonse instanceof User){
            User  user = (User) resqonse;
            rv.setAdapter(new MyAdapter(MainActivity.this,user));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showPresenter.JieBang(this);
    }
}
