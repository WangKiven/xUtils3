package com.sxb.kiven.mytest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kiven on 2017/3/10.
 */

public class ImageActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    List<String> imgUrls;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = new RecyclerView(this);
        setContentView(recyclerView);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        imgUrls = new ArrayList<>();
        myAdapter = new MyAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        loadImage();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadImage() {
        RequestParams params = new RequestParams("http://image.baidu.com/data/imgs?col=美女&tag=小清新&sort=0&pn=0&rn=30&p=channel&from=1");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                imgUrls.addAll(getImgSrcList(/*"http", "jpg", */result));
                /*imgUrls.addAll(getImgSrcList("https", "jpg", result));
                imgUrls.addAll(getImgSrcList("http", "png", result));
                imgUrls.addAll(getImgSrcList("https", "png", result));*/
                myAdapter.notifyDataSetChanged();

                Toast.makeText(ImageActivity.this, "加载完成", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("tag", "onCancelled");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("tag", "onError");
            }

            @Override
            public void onFinished() {
                Log.i("tag", "onFinished");
            }
        });
    }

    /**
     * 得到网页中图片的地址
     */
    public List<String> getImgSrcList(/*String start, String end, */String htmlStr) {
        List<String> pics = new ArrayList<String>();

        String regEx_img = /*start + */"http(s{0,1})://(.*?).(jpg|png)"/* + end*/; // 图片链接地址
        Pattern p_image = Pattern.compile(regEx_img);
        Matcher m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            String src = m_image.group(0);
            if (src.length() < 100) {
                pics.add(src);
                //pics.add("http://f.hiphotos.baidu.com/zhidao/pic/item/2fdda3cc7cd98d104cc21595203fb80e7bec907b.jpg");
            }
        }
        return pics;
    }



    class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(ImageActivity.this);
            imageView.setScaleType(ImageView.ScaleType.MATRIX);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            params.gravity = Gravity.CENTER;
            imageView.setLayoutParams(params);
            return new MyHolder(imageView);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            ImageView imageView = (ImageView) holder.itemView;
            x.image().bind(imageView, imgUrls.get(position));
        }

        @Override
        public int getItemCount() {
            return imgUrls.size();
        }
    }
}
