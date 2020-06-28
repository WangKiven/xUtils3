package org.xutils.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.IntentInject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by kiven on 2017/7/24.
 */

@ContentView(R.layout.activity_inject)
public class InjectActivity extends AppCompatActivity {
    @ViewInject(R.id.text)
    TextView tv;

    @IntentInject("text")
    String text;

    String haha = "hahaha";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);

        Bundle bundle = new Bundle();
        bundle.putString("fragment_str", "Hello fragment");

        Fragment fragment = new InjectFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.ll_content, fragment).commit();

        tv.setText("这个界面是测试补充注解的：" +
                "\n1.上个界面传过来的Intent参数，在activity中通过注解解析" +
                "\n2.上个界面传过来的Intent参数，在fragment中通过注解解析" +
                "\n3.activity传递arguments参数给fragment,fragment通过注解解析" +
                "\n\n使用注解：@IntentInject和@ArguInject");
    }

    @Event(R.id.btn_test1)
    private void clickTest1(View view) {
        new AlertDialog.Builder(this).setMessage("text = " + text + "\nhaha = " + haha).show();
    }
}
