package com.makemoney.tienhiep.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.makemoney.tienhiep.viewmodel.MainViewModel;
import com.makemoney.tienhiep.R;


public class FragmentMain extends Fragment {
    private Button mStartReadBtn;
    private TextView mListChapTv, mContinueReadTv;
    private int mReadingNum;
    private MainViewModel mViewModel;
    private static FragmentMain mFragmentMain;

    public static FragmentMain getInstance() {
        if(mFragmentMain == null){
            mFragmentMain = new FragmentMain();
        }
        return mFragmentMain;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mapping(view);
        if(mReadingNum != 0){
            mContinueReadTv.setVisibility(View.VISIBLE);
        }
        else mContinueReadTv.setVisibility(View.INVISIBLE);
        return view;
    }

//    private void setFragment(Fragment fragment){
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.mainview, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }

    public void buttonClick(View view) {
        Fragment fragment = null;
        switch (view.getId()){
            case R.id.list_chap:
                fragment = FragmentListChapter.getInstance();
                break;

            case R.id.begin_read:
                mViewModel.setmReadingChapter(0);
                fragment = FragmentRead.getInstance();
                break;

            case R.id.continue_read:
                FragmentRead fragmentReadC = new FragmentRead();
                fragment = FragmentRead.getInstance();
                break;
        }
        mViewModel.setFragment(fragment);
    }

    private void mapping(View view){
        mContinueReadTv = view.findViewById(R.id.continue_read);
        mListChapTv = view.findViewById(R.id.list_chap);
        mStartReadBtn = view.findViewById(R.id.begin_read);
        mContinueReadTv.setOnClickListener(this::buttonClick);
        mListChapTv.setOnClickListener(this::buttonClick);
        mStartReadBtn.setOnClickListener(this::buttonClick);
        mReadingNum = mViewModel.getmReadingChapter();
    }
}
