package com.bawei.demo.lianxi3.okhttp;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.bawei.demo.lianxi3.model.IModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttp {
    private static OkHttp interceptor;
    private final OkHttpClient build;

    Handler handler = new Handler();
    private OkHttpClient client;

    //创建单例
    public static OkHttp getokHttp(){
        if(interceptor == null){
            synchronized (OkHttp.class){
                if(null == interceptor){
                    interceptor = new OkHttp();
                }
            }
        }
        return interceptor;
    }

    public Interceptor getAppInterceptor(){
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.i("wwwww","拦截前");
                Response proceed = chain.proceed(request);
                Log.i("wwwww","拦截后");
                return proceed;
            }
        };
        return interceptor;
    }

    public OkHttp(){
        File file = new File(Environment.getExternalStorageDirectory(), "ws");
        build = new OkHttpClient().newBuilder()
                .readTimeout(5000,TimeUnit.SECONDS)
                .connectTimeout(5000,TimeUnit.SECONDS)
                .addInterceptor(getAppInterceptor())
                .cache(new Cache(file,1024*10))
                .build();
    }

    //get
    public void onGet(String url, final Class clazz, final NetCallBack netCallBack){
       Request request = new Request.Builder()
               .url(url)
               .get()
               .build();
        Call call = build.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(string, clazz);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        netCallBack.loadSuccess(o);
                    }
                });
            }
        });
    }
    //onPost
    public void onPost(String url, final Class clazz, String name, String pwd, final NetCallBack netCallBack){
        client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("phone",name)
                .add("pwd",pwd)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = build.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(string, clazz);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        netCallBack.loadSuccess(o);
                    }
                });
            }
        });
    }

    public interface NetCallBack{
        void loadSuccess(Object obj);
        void loadFiled();
    }
}
