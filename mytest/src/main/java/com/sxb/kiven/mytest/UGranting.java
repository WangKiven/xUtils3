package com.sxb.kiven.mytest;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiven on 16/3/31.
 */
public class UGranting {
    private Activity mActivity;
    private int requestCode;
    private String[] grant;
    private List<String> grantName;

    public UGranting(@NonNull Activity activity, int requestCode, @NonNull String[] tGrant, @NonNull String[] tGrantName) {
        mActivity = activity;
        this.requestCode = requestCode;

        List<String> mGrant = new ArrayList<>(3);
        grantName = new ArrayList<>(3);
        int i = 0;
        for (String g : tGrant) {
            if (ContextCompat.checkSelfPermission(mActivity, g) != PackageManager.PERMISSION_GRANTED) {
                if (!mGrant.contains(g)) {
                    mGrant.add(g);

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(mActivity, g)) {
                        grantName.add(tGrantName[i]);
                    }
                }
            }
            i ++;
        }

        grant = new String[mGrant.size()];
        for (i = 0; i < mGrant.size(); i++) {
            grant[i] = mGrant.get(i);
        }
    }

    public boolean startCheck() {
        /*cursor = 0;

        if (grant == null || grant.size() == 0 || cursor >= grant.size()) {
            return true;
        }

        check();
        return false;*/

        if (grant == null || grant.length == 0) {
            return true;
        }

        if (grantName.size() > 0) {
            String message = "你需要授权访问" + grantName.get(0);
            for (int i = 1; i < grantName.size(); i++) {
                message = message + ", " + grantName.get(i);
            }

            new AlertDialog.Builder(mActivity)
                    .setMessage(message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(mActivity, grant,
                                    requestCode);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(mActivity, grant,
                    requestCode);
        }
        return false;
    }

    public boolean checkResult(@NonNull int[] grantResults) {
        if (grantResults == null || grantResults.length < 1) {
            return false;
        }

        for (int i : grantResults) {
            if (i != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;

        /*if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (cursor >= grant.size()) {
                return true;
            } else {
                check();
                return false;
            }
        } else {
            return false;
        }*/
        /*
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(mActivity)
                        .setMessage("You need to allow access to Contacts")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(mActivity, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE
                                                ,Manifest.permission.WRITE_CONTACTS},
                                        requestCode);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create()
                        .show();
                return false;
            }
            ActivityCompat.requestPermissions(mActivity, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_CONTACTS},
                    requestCode);
            return false;
        }

        return true;*/
    }

    /*private void check() {

        if (!ActivityCompat.shouldShowRequestPermissionRationale(mActivity, grant.get(cursor))) {
            new AlertDialog.Builder(mActivity)
                    .setMessage("You need to allow access to Contacts")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(mActivity, new String[]{grant.get(cursor)},
                                    requestCode);
                            cursor ++;
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(mActivity, new String[] {grant.get(cursor)},
                    requestCode);
            cursor ++;
        }

    }*/
}
