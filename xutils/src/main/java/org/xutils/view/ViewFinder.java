package org.xutils.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private Object fragment;
    private Activity activity;

    public ViewFinder(View view) {
        this.view = view;
    }

    public ViewFinder(View view, Object fragment) {
        this.view = view;
        this.fragment = fragment;
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
        Intent intent = null;
        if (activity == null) {
            if (fragment == null) {
                if (view != null) {
                    Context context = view.getContext();
                    if (context instanceof Activity) {
                        intent = ((Activity) context).getIntent();
                    }
                }
            } else {
                try {
                    Method method = fragment.getClass().getMethod("getActivity");
                    if (method != null) {
                        Activity activity = (Activity) method.invoke(fragment);
                        if (activity != null) {
                            intent = activity.getIntent();
                        }
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } else {
            intent = activity.getIntent();
        }


        if (intent != null && intent.hasExtra(name)) {

            Class tClass = field.getType();

            if (int.class.isAssignableFrom(tClass) || Integer.class.isAssignableFrom(tClass)) {
                return intent.getIntExtra(name, 0);
            } else if (long.class.isAssignableFrom(tClass) || Long.class.isAssignableFrom(tClass)) {
                return intent.getLongExtra(name, 0);
            } else if (float.class.isAssignableFrom(tClass) || Float.class.isAssignableFrom(tClass)) {
                return intent.getFloatExtra(name, 0);
            } else if (double.class.isAssignableFrom(tClass) || Double.class.isAssignableFrom(tClass)) {
                return intent.getDoubleExtra(name, 0);
            } else if (boolean.class.isAssignableFrom(tClass) || Boolean.class.isAssignableFrom(tClass)) {
                return intent.getBooleanExtra(name, false);
            } else if (String.class.isAssignableFrom(tClass)) {
                return intent.getStringExtra(name);
            } else if (Bundle.class.isAssignableFrom(tClass)) {
                return intent.getBundleExtra(name);
            } else if (Parcelable.class.isAssignableFrom(tClass)) {
                return intent.getParcelableExtra(name);
            } else if (Serializable.class.isAssignableFrom(tClass)) {
                return intent.getSerializableExtra(name);
            } else if (ArrayList.class.isAssignableFrom(tClass)) {//arraylist
                    /*return intent.getParcelableArrayListExtra(name);*/
                Type t = field.getGenericType();

                if (ParameterizedType.class.isAssignableFrom(t.getClass())) {
                    Type[] types = ((ParameterizedType) t).getActualTypeArguments();
                    if (types != null && types.length > 0) {
                        Class gClass = (Class) types[0];

                        if (String.class.isAssignableFrom(gClass)) {
                            return intent.getStringArrayListExtra(name);
                        } else if (Parcelable.class.isAssignableFrom(gClass)) {
                            return intent.getParcelableArrayListExtra(name);
                        } else if (Serializable.class.isAssignableFrom(gClass)) {
                            return intent.getSerializableExtra(name);
                        } else if (Integer.class.isAssignableFrom(gClass)) {
                            return intent.getIntegerArrayListExtra(name);
                        } else if (CharSequence.class.isAssignableFrom(gClass)) {
                            return intent.getCharSequenceArrayListExtra(name);
                        }
                    }
                }

            } else if (int[].class.isAssignableFrom(tClass)) {
                return intent.getIntArrayExtra(name);
            }// todo 还有一些，用时再加
        }

        return null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object getArgu(String name, Field field) {
        Bundle intent = null;
        if (fragment != null) {
            try {
                Method method = fragment.getClass().getMethod("getArguments");
                if (method != null) {
                    intent = (Bundle) method.invoke(fragment);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        if (intent != null && intent.containsKey(name)) {

            Class tClass = field.getType();

            if (int.class.isAssignableFrom(tClass) || Integer.class.isAssignableFrom(tClass)) {
                return intent.getInt(name, 0);
            } else if (long.class.isAssignableFrom(tClass) || Long.class.isAssignableFrom(tClass)) {
                return intent.getLong(name, 0);
            } else if (float.class.isAssignableFrom(tClass) || Float.class.isAssignableFrom(tClass)) {
                return intent.getFloat(name, 0);
            } else if (double.class.isAssignableFrom(tClass) || Double.class.isAssignableFrom(tClass)) {
                return intent.getDouble(name, 0);
            } else if (boolean.class.isAssignableFrom(tClass) || Boolean.class.isAssignableFrom(tClass)) {
                return intent.getBoolean(name, false);
            } else if (String.class.isAssignableFrom(tClass)) {
                return intent.getString(name);
            } else if (Bundle.class.isAssignableFrom(tClass)) {
                return intent.getBundle(name);
            } else if (Parcelable.class.isAssignableFrom(tClass)) {
                return intent.getParcelable(name);
            } else if (Serializable.class.isAssignableFrom(tClass)) {
                return intent.getSerializable(name);
            } else if (ArrayList.class.isAssignableFrom(tClass)) {//arraylist
                Type t = field.getGenericType();

                if (ParameterizedType.class.isAssignableFrom(t.getClass())) {
                    Type[] types = ((ParameterizedType) t).getActualTypeArguments();
                    if (types != null && types.length > 0) {
                        Class gClass = (Class) types[0];

                        if (String.class.isAssignableFrom(gClass)) {
                            return intent.getStringArrayList(name);
                        } else if (Parcelable.class.isAssignableFrom(gClass)) {
                            return intent.getParcelableArrayList(name);
                        } else if (Serializable.class.isAssignableFrom(gClass)) {
                            return intent.getSerializable(name);
                        } else if (Integer.class.isAssignableFrom(gClass)) {
                            return intent.getIntegerArrayList(name);
                        } else if (CharSequence.class.isAssignableFrom(gClass)) {
                            return intent.getCharSequenceArrayList(name);
                        }
                    }
                }

            } else if (int[].class.isAssignableFrom(tClass)) {
                return intent.getIntArray(name);
            }// todo 还有一些，用时再加
        }

        return null;
    }
}
