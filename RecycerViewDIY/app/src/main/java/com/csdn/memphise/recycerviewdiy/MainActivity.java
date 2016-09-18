package com.csdn.memphise.recycerviewdiy;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import listener.IChildOnClickListener;
import listener.IChildOnLongClickListener;
import listener.ItemViewClickListener;

public class MainActivity extends AppCompatActivity implements IChildOnLongClickListener, IChildOnClickListener, ItemViewClickListener{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recycerView;
    public static final String TAG = "lhy";
    private List<String> list;
    private boolean isRefreshed = true;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();
    }

    private void initListener(){
        if(isRefreshed){
            // TODO Auto-generated method stub
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

                @Override
                public void onRefresh(){
                    // TODO Auto-generated method stub
                    loadNewData();
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
        recycerView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState){
                // TODO Auto-generated method stub
                if(recyclerView.getLayoutManager() != null && recyclerView.getLayoutManager() instanceof LinearLayoutManager){
                    LinearLayoutManager llManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if(llManager.findLastVisibleItemPosition() == list.size()-1){
                        loadNewData();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        adapter.setItemViewClickListener(this);
        adapter.setChildOnClickListener(this);
        adapter.setChildOnLongClickListener(this);
    }

    protected void loadNewData(){
        // TODO Auto-generated method stub
        for(int i = 0; i < 20; i++){
            list.add((i%2 ==0? "你为什么这么熟练啊":"败犬女主"));
        }
        makeToast("刷新数据成功");
//        Snackbar.make(recycerView,"刷新数据成功",Snackbar.LENGTH_SHORT).show();
    }

    private void initData(){
        list = new ArrayList<String>();
        for(int i = 0; i < 20; i++){
            list.add((i%2 ==0? "明明是我先的，牵手也好，膝枕也好":"计划通，我才是女主"));
        }
    }

    private void initView(){
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        recycerView = (RecyclerView) findViewById(R.id.recycerView);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        recycerView.setLayoutManager(llManager);
        adapter = new MyAdapter(this, list);
        recycerView.setAdapter(adapter);
    }

    public void makeToast(String msg){
        if(!TextUtils.isEmpty(msg)){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChildLongClicked(View view, int position){
        makeToast("不要摸人家的头啦,position----------->"+position);
    }

    @Override
    public void onChildClicked(View view, int position){
        makeToast("你个死宅，离我远点,position----------->"+position);
    }

    @Override
    public void onItemViewClicked(View view, int position){
        makeToast("onItemViewClicked,position:------>"+position);
    }
}
