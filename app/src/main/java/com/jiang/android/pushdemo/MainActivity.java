package com.jiang.android.pushdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jiang.android.push.Message;
import com.jiang.android.push.Push;
import com.jiang.android.push.PushInterface;
import com.jiang.android.rvadapter.BaseAdapter;
import com.jiang.android.rvadapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton register;
    private AppCompatButton unregister;
    private AppCompatButton open;
    private AppCompatButton close;
    private RecyclerView recyclerView;

    List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = (AppCompatButton) findViewById(R.id.register);
        register.setOnClickListener(this);
        unregister = (AppCompatButton) findViewById(R.id.unregister);
        unregister.setOnClickListener(this);
        open = (AppCompatButton) findViewById(R.id.open);
        open.setOnClickListener(this);
        close = (AppCompatButton) findViewById(R.id.close);
        close.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BaseAdapter() {
            @Override
            protected void onBindView(BaseViewHolder baseViewHolder, int i) {

                TextView title = baseViewHolder.getView(R.id.tv);
                title.setText(mDatas.get(i));
            }

            @Override
            protected int getLayoutID(int i) {
                return R.layout.item;
            }

            @Override
            public int getItemCount() {
                return mDatas.size();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                register();
                break;
            case R.id.unregister:
                Push.unregister(this);
                break;
            case R.id.open:
                Push.start(this);
                break;
            case R.id.close:
                Push.stop(this);
                break;
        }

    }

    private void register() {
        Push.register(this, true, new PushInterface() {
            @Override
            public void onRegister(Context context, String registerID) {
                addData("onRegister id:" + registerID);
            }

            @Override
            public void onUnRegister(Context context) {
                addData("onUnRegister");
            }

            @Override
            public void onPaused(Context context) {
                addData("onPaused");
            }

            @Override
            public void onResume(Context context) {
                addData("onResume");
            }

            @Override
            public void onMessage(Context context, Message message) {

                addData("onMessage: " + message.toString());
            }

            @Override
            public void onMessageClicked(Context context, Message message) {

                addData("onMessageClicked: " + message.toString());
            }

            @Override
            public void onCustomMessage(Context context, Message message) {

                addData("onCustomMessage: " + message.toString());
            }
        });

    }

    private void addData(String value) {
        mDatas.add(value);
        recyclerView.getAdapter().notifyItemInserted(mDatas.size() - 1);

    }
}
