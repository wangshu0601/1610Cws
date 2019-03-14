package com.bawei.demo.day02;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PullToRefreshListView pxl;
    private List<User.ResultsBean> list;
    private List<Ws> list1;
    int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pxl = findViewById(R.id.pxl);
        initData(i);
        pxl.setMode(PullToRefreshBase.Mode.BOTH);
        pxl.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                initData(i++);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                initData(i++);

            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private void initData(int i) {
        final String path = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/"+ this.i;
        new AsyncTask<String,Void,String>(){
            private String aa;

            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    int code = connection.getResponseCode();
                    if(code == 200){
                        InputStream is = connection.getInputStream();
                        int len;
                        byte[] arr = new byte[1024*10];
                        aa = "";
                        while ((len=is.read(arr))!=-1){
                            String s = new String(arr, 0, len);
                            aa +=s;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return aa;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Gson gson = new Gson();
                User user = gson.fromJson(s, User.class);
                list = user.results;
                Toast.makeText(MainActivity.this, "list.size():" + list.size(), Toast.LENGTH_SHORT).show();
                pxl.setAdapter(new MyAdapter());
                pxl.onRefreshComplete();
                list1 = new ArrayList<>();
                for (int j=1;j<list.size();j++){
                    Ws ws = new Ws();
                    if(j%2==0){
                        ws.setImage(list.get(j).url);
                        ws.setName(list.get(j).who);
                        ws.setType(0);
                    }else if(j%2==1){
                        ws.setImage(list.get(j).url);
                        ws.setName(list.get(j).who);
                        ws.setType(1);
                    }
                    list1.add(ws);
                }
            }
        }.execute();
    }

    class MyAdapter extends BaseAdapter{

        private DisplayImageOptions options;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View conview, ViewGroup viewGroup) {
            int type = getItemViewType(i);
            if(conview == null) switch (type) {
                case 0:
                    conview = View.inflate(MainActivity.this, R.layout.one, null);
                    TextView tv =  conview.findViewById(R.id.tv);
                    tv.setText(list1.get(i).getImage());
                    ImageView iv = conview.findViewById(R.id.iv);
                    options = new DisplayImageOptions.Builder()
                            .showImageOnFail(R.mipmap.ic_launcher)
                            .showImageOnLoading(R.mipmap.ic_launcher)
                            .build();ImageLoader.getInstance().displayImage(list1.get(i).getImage(),iv, options);
                    break;
                case 1:
                    conview = View.inflate(MainActivity.this, R.layout.two, null);
                    TextView tv1 =  conview.findViewById(R.id.tv1);
                    tv1.setText(list1.get(i).getImage());
                    ImageView iv1 = conview.findViewById(R.id.iv1);
                  ImageLoader.getInstance().displayImage(list1.get(i).getImage(),iv1, options);
                    break;
            }
            return conview;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return list1.get(position).getType();
        }
    }
}
