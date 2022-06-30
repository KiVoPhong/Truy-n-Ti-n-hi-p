package com.makemoney.tienhiep.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.makemoney.tienhiep.adapter.ListChapAdapter;
import com.makemoney.tienhiep.viewmodel.MainViewModel;
import com.makemoney.tienhiep.R;

public class FragmentListChapter extends Fragment {
    private ListView mListView;
    private ImageView mBackArrowBtn;
    private MainViewModel mViewModel;
    private static FragmentListChapter mFragmentList;

    public static FragmentListChapter getInstance() {
        if(mFragmentList == null){
            mFragmentList = new FragmentListChapter();
        }
        return mFragmentList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_chapter, container, false);
        mapping(view);
        mBackArrowBtn.setOnClickListener(this::actionClick);
        ListChapAdapter listViewAdapter= new ListChapAdapter(mViewModel.getmListChapter());
        mListView.setAdapter(listViewAdapter);
        mListView.setOnItemClickListener(this::clickListItem);
        return view;
    }

    private void clickListItem(AdapterView<?> adapterView, View view, int i, long l) {
        mViewModel.setmReadingChapter(i);
        mViewModel.setFragment(FragmentRead.getInstance());
    }

    public void actionClick(View view){
        switch (view.getId()) {
            case R.id.back_arrow:
                getFragmentManager().popBackStack();
        }
    }

    private void mapping(View view){
        mListView = view.findViewById(R.id.list_title);
        mBackArrowBtn = view.findViewById(R.id.back_arrow);
    }
}
