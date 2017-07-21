package org.xutils.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Author: wyouflf
 * Date: 13-9-9
 * Time: 下午12:29
 */
/*package*/ final class ViewFinder {

    private View view;
    private Activity activity;

    public ViewFinder(View view) {
        this.view = view;
    }

    public ViewFinder(Activity activity) {
        this.activity = activity;
    }

    public View findViewById(int id) {
        if (view != null) return view.findViewById(id);
        if (activity != null) return activity.findViewById(id);
        return null;
    }

    public View findViewByInfo(ViewInfo info) {
        return findViewById(info.value, info.parentId);
    }

    public View findViewById(int id, int pid) {
        View pView = null;
        if (pid > 0) {
            pView = this.findViewById(pid);
        }

        View view = null;
        if (pView != null) {
            view = pView.findViewById(id);
        } else {
            view = this.findViewById(id);
        }
        return view;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object getExtra(String name, Field field) {
        if (activity != null) {
            Intent intent = activity.getIntent();
            if (intent != null && intent.hasExtra(name)) {

                Class tClass = field.getType();

                if (tClass.isAssignableFrom(int.class) || tClass.isAssignableFrom(Integer.class)) {
                    return intent.getIntExtra(name, 0);
                } else if (tClass.isAssignableFrom(long.class) || tClass.isAssignableFrom(Long.class)) {
                    return intent.getLongExtra(name, 0);
                } else if (tClass.isAssignableFrom(float.class) || tClass.isAssignableFrom(Float.class)) {
                    return intent.getFloatExtra(name, 0);
                } else if (tClass.isAssignableFrom(double.class) || tClass.isAssignableFrom(Double.class)) {
                    return intent.getDoubleExtra(name, 0);
                } else if (tClass.isAssignableFrom(boolean.class) || tClass.isAssignableFrom(Boolean.class)) {
                    return intent.getBooleanExtra(name, false);
                } else if (tClass.isAssignableFrom(String.class)) {
                    return intent.getStringExtra(name);
                } else if (tClass.isAssignableFrom(Bundle.class)){
                    return intent.getBundleExtra(name);
                } else if (tClass.isAssignableFrom(Parcelable.class)) {
                    return intent.getParcelableExtra(name);
                } else if (tClass.isAssignableFrom(Serializable.class)) {
                    return intent.getSerializableExtra(name);
                } else if (tClass.isAssignableFrom(ArrayList.class)){//arraylist
                    /*return intent.getParcelableArrayListExtra(name);*/
                    Type t = field.getGenericType();

                    if (ParameterizedType.class.isAssignableFrom(t.getClass())) {
                        Type[] types = ((ParameterizedType) t).getActualTypeArguments();
                        if (types != null && types.length > 0) {
                            Class gClass = (Class) types[0];

                            if (gClass.isAssignableFrom(String.class)) {
                                return intent.getStringArrayListExtra(name);
                            } else if (gClass.isAssignableFrom(Parcelable.class)) {
                                return intent.getParcelableArrayListExtra(name);
                            } else if (gClass.isAssignableFrom(Serializable.class)) {
                                return intent.getSerializableExtra(name);
                            } else if (gClass.isAssignableFrom(Integer.class)) {
                                return intent.getIntegerArrayListExtra(name);
                            } else if (gClass.isAssignableFrom(CharSequence.class)) {
                                return intent.getCharSequenceArrayListExtra(name);
                            }
                        }
                    }

                } else if (tClass.isAssignableFrom(int[].class)) {
                    return intent.getIntArrayExtra(name);
                }// todo 还有一些，用时再加
            }
        }
        return null;
    }

    /*public Context getContext() {
        if (view != null) return view.getContext();
        if (activity != null) return activity;
        return null;
    }*/
}
