package com.bawei.demo.lianxi3.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.demo.lianxi3.R;
import com.bawei.demo.lianxi3.bean.User;
import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    User user;

    public MyAdapter(Context context, User user) {
        this.context = context;
        this.user = user;
    }
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.aa,viewGroup,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder,final int i) {
        viewHolder.tv.setText(user.getResult().getPzsh().getCommodityList().get(i).getCommodityName());
        Glide.with(context).load(user.getResult().getPzsh().getCommodityList().get(i).getMasterPic()).into(viewHolder.iv);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                delete(i);
                return false;
            }
        });
    }

  

    @Override
    public int getItemCount() {
        return user.getResult().getPzsh().getCommodityList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv;
        private final TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
        }
    }
    private void delete(int i) {
        User.ResultBean.PzshBean pzsh = user.getResult().getPzsh();
        List<User.ResultBean.PzshBean.CommodityListBeanX> list = pzsh.getCommodityList();
        list.remove(i);
        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();

        notifyDataSetChanged();
    }
 
}
