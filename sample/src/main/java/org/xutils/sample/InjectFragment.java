package org.xutils.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.view.annotation.ArguInject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.IntentInject;
import org.xutils.x;

/**
 * Created by kiven on 2017/7/24.
 */
@ContentView(R.layout.fragment_inject)
public class InjectFragment extends Fragment {
    @IntentInject("text")
    String text;
    @ArguInject("fragment_str")
    String fragment_str;

    int haha = 23;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Event(R.id.btn_test2)
    private void onClick(View view) {
        new AlertDialog.Builder(getActivity()).setMessage("intent for activity:\ntext = " + text + "\nhaha = " + haha
        + "\n\narguments for fragment:\nfragment_str = " + fragment_str).show();
    }
}
