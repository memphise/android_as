package com.csdn.memphise.recycerviewdiy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import listener.IChildOnClickListener;
import listener.IChildOnLongClickListener;
import listener.ItemViewClickListener;

/**
 * Created by memphise on 2016/9/15.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private List<String> list;
    public static final int RAM = 0;
    public static final int AMI = 1;
    private IChildOnClickListener childOnClickListener;
    private IChildOnLongClickListener childOnLongClickListener;
    private ItemViewClickListener itemViewClickListener;

    public void setItemViewClickListener(ItemViewClickListener itemViewClickListener){
        if(itemViewClickListener instanceof  ItemViewClickListener){
            this.itemViewClickListener = itemViewClickListener;
        }
    }

    public void setChildOnClickListener(IChildOnClickListener childOnClickListener){
        if(childOnClickListener instanceof  IChildOnClickListener){
            this.childOnClickListener = childOnClickListener;
        }
    }

    public void setChildOnLongClickListener(IChildOnLongClickListener childOnLongClickListener){
        if(childOnLongClickListener instanceof  IChildOnLongClickListener){
            this.childOnLongClickListener = childOnLongClickListener;
        }
    }

    public MyAdapter(Context context, List<String> list){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount(){
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public int getItemViewType(int position){
        return position % 2 == 0 ? RAM : AMI;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position){
        // TODO Auto-generated method stub
        MyViewHolder viewHolder = (MyViewHolder) vh;
        if(position % 2 == 0){
            viewHolder.iv_avatar.setImageResource(R.drawable.amilia);
        }else{
            viewHolder.iv_avatar.setImageResource(R.drawable.ram);
        }
        viewHolder.tv_desc.setText(list.get(position) + "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int position){
        // TODO Auto-generated method stub
        View itemView = null;
        int viewType = getItemViewType(position);
        Log.e("MyAdapter", "position ----------->:" + position + ",  position %2 :------->" + position % 2);
        if(viewType == RAM){
            itemView = inflater.inflate(R.layout.list_item, parent, false);
        }else{
            itemView = inflater.inflate(R.layout.list_item2, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_avatar;
        private TextView tv_desc;

        public MyViewHolder(final View itemView){
            super(itemView);
            // TODO Auto-generated constructor stub
            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            int layoutPosition = getLayoutPosition();
            viewOnClick(iv_avatar, layoutPosition);
            viewOnLongClick(iv_avatar, layoutPosition);
            itemViewOnClick(itemView,layoutPosition);
        }

        public void viewOnClick(final View view, int position){
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    childOnClickListener.onChildClicked(view,getLayoutPosition());
                }
            });
        }

        public void viewOnLongClick(final View view, int position){
            view.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    childOnLongClickListener.onChildLongClicked(view,getLayoutPosition());
                    return true;
                }
            });
        }

        public void itemViewOnClick(final View view, int position){
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    itemViewClickListener.onItemViewClicked(view,getLayoutPosition());
                }
            });
        }
    }
}
