package com.sxb.kiven.mytest;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;

/**
 * Created by kiven on 16/3/31.
 */
@TargetApi(23)
public class UGranting {
    public boolean checkGrant(final Activity activity, String grant, int requestCode) {
        int hasWriteContactsPermission = activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(activity)
                        .setMessage("You need to allow access to Contacts")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                                        23);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create()
                        .show();
                return false;
            }
            activity.requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    23);
            return false;
        }

        return true;
    }
}
