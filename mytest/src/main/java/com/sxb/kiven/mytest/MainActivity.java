package com.sxb.kiven.mytest;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UGranting mGranting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View view) {
        Toast.makeText(this, "click", Toast.LENGTH_LONG).show();
        switch (view.getId()) {
            case R.id.button:
                BaseWeb.login("18780296428", "888888", new WebCallBack(){
                    @Override
                    public void onSuccess(String result) {
                        super.onSuccess(result);
                        UserBo userBo = new JsonUtil(result).toObject(UserBo.class);
                        if (userBo == null) {
                            ULog.i("读取数据失败");
                        } else {
                            MyApplication.getInstance().setUser(userBo);
                        }
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        super.onCancelled(cex);
                        Log.i("tag", "onCancelled");
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        super.onError(ex, isOnCallback);
                        Log.i("tag", "onError");
                    }

                    @Override
                    public void onFinished() {
                        super.onFinished();
                        Log.i("tag", "onFinished");
                    }
                });
                break;
            case R.id.button2:

                break;
            case R.id.button3:
                /*Intent imageIntent = new Intent();
                imageIntent.setType("image*//*");
                imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(imageIntent, 22);*/

                if (mGranting == null) {
                    mGranting = new UGranting(this, 233, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new String[]{"通讯录","存储空间"});
                }

                if (mGranting.startCheck()) {
                    Intent intent=new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/jpeg");
                    if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT){
                        startActivityForResult(intent, 345);
                    }else{
                        startActivityForResult(intent, 234);
                    }
                }
                break;
            case R.id.button4:
                ULog.i("imageCount = " + paths.size());
                BaseWeb.uploadImage("", paths, new WebCallBack(){
                    @Override
                    public void onSuccess(String result) {
                        super.onSuccess(result);
                        ULog.i(result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        super.onError(ex, isOnCallback);
                        ULog.e("Error");
                    }

                    @Override
                    public void onFinished() {
                        super.onFinished();
                        ULog.i("onFinished");
                    }
                });
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 233) {
            if (mGranting.checkResult(grantResults)) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/jpeg");
                if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT){
                    startActivityForResult(intent, 345);
                }else{
                    startActivityForResult(intent, 234);
                }
            }
        }
    }

    private List<String> paths = new ArrayList<String>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        ImageView imageView = (ImageView) findViewById(R.id.imageView2);

        if (requestCode == 345) {
            String path = UImage.getPath(this, data.getData());
            ULog.i(path);
            paths.add(path);

            x.image().bind(imageView, path);
        } else if (requestCode == 234) {
            Uri selectedImage = data.getData();
            if(selectedImage!=null){
                String uriStr=selectedImage.toString();
                String path=uriStr.substring(10,uriStr.length());
                if(path.startsWith("com.sec.android.gallery3d")){
                    ULog.e("It's auto backup pic path:"+selectedImage.toString());
                    return;
                }
            }
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ULog.i(picturePath);
            paths.add(picturePath);

            x.image().bind(imageView, picturePath);
        }
    }
}
