package com.cuboid.gettingstarted.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cuboid.gettingstarted.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {


    public static final String ARG_POSITION = "com.cuboid.gettingstarted.fragment.ARG_POSITION";

    public ArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() == null) {
            return;
        }
        int pos = getArguments().getInt(ARG_POSITION);
        updateArticleView(pos);

    }

    public void updateArticleView(int position) {
        TextView textView = (TextView) getView().findViewById(R.id.fragment_article_text);
        textView.setText("Position=[" + position + "]");
    }
}