package com.makemoney.tienhiep.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.makemoney.tienhiep.activity.MainActivity;
import com.makemoney.tienhiep.viewmodel.MainViewModel;
import com.makemoney.tienhiep.model.Chapter;
import com.makemoney.tienhiep.R;
import com.google.android.gms.ads.AdView;

public class FragmentRead extends Fragment {
    private TextView mTitleTv, mContentTv;
    private Button mNextChapBtn, mPreviousChapBtn;
    private ImageView mSetting_btn, mHome_btn, mBackBtn;
    private Toolbar toolbar;
    private MainViewModel mViewModel;
    private ScrollView mScrollView;
    private AdView mAdView;
    private int mMinDistance = 150;
    private float x1 = 0, x2 = 0;
    private static FragmentRead mFragmentRead;

    public static FragmentRead getInstance() {
        if (mFragmentRead == null) {
            mFragmentRead = new FragmentRead();
        }
        return mFragmentRead;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        if (toolbar != null) {
            ((MainActivity) getActivity()).getSupportActionBar();
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mapping(view);
        setView();
        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                System.out.println("right to left");
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                System.out.println("left to right");
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
        return view;
    }

    private void mapping(View view) {
        mAdView = view.findViewById(R.id.adView);
        mScrollView = view.findViewById(R.id.scrollView);
        mTitleTv = view.findViewById(R.id.title);
        mContentTv = view.findViewById(R.id.content);
        mNextChapBtn = view.findViewById(R.id.next);
        mPreviousChapBtn = view.findViewById(R.id.back);
        mSetting_btn = view.findViewById(R.id.setting_btn);
        mHome_btn = view.findViewById(R.id.home_btn);
        mBackBtn = view.findViewById(R.id.back_arrow);
        mNextChapBtn.setOnClickListener(this::buttonClicked);
        mPreviousChapBtn.setOnClickListener(this::buttonClicked);
        mSetting_btn.setOnClickListener(this::buttonClicked);
        mHome_btn.setOnClickListener(this::buttonClicked);
        mBackBtn.setOnClickListener(this::buttonClicked);
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    private void nextChapter() {
        int chapNum = mViewModel.getmReadingChapter();
        mViewModel.setmReadingChapter(Math.min(chapNum + 1, 1323));
        updateContent();
    }

    private void previousChapter() {
        int chapNum = mViewModel.getmReadingChapter();
        mViewModel.setmReadingChapter(Math.max(chapNum - 1, 0));
        updateContent();
    }

    public void buttonClicked(View view) {
        int chapNum = mViewModel.getmReadingChapter();
        switch (view.getId()) {
            case R.id.back:
                if (chapNum > 0) {
                    previousChapter();
                }
                break;

            case R.id.next:
                if (chapNum < 1323) {
                    nextChapter();
                }
                break;

            case R.id.setting_btn:
                mViewModel.setFragment(FragmentSetting.getInstance());
                break;

            case R.id.home_btn:
                mViewModel.setFragment(FragmentMain.getInstance());
                break;

            case R.id.back_arrow:
                getFragmentManager().popBackStack();
        }
    }

    private void updateContent() {
        int readingChap = mViewModel.getmReadingChapter();
        Chapter chapter = mViewModel.getmListChapter().get(readingChap);
        mTitleTv.setText(chapter.getTitle());
        mContentTv.setText(chapter.getContent());
        mPreviousChapBtn.setVisibility(View.VISIBLE);
        mNextChapBtn.setVisibility(View.VISIBLE);
        mScrollView.fullScroll(View.FOCUS_UP);//if you move at the end of the scroll
        mScrollView.smoothScrollTo(0, 0);
        mScrollView.pageScroll(View.FOCUS_UP);//if
        if (readingChap == 0) {
            mPreviousChapBtn.setVisibility(View.GONE);
        } else if (readingChap == 1323) {
            mNextChapBtn.setVisibility(View.GONE);
        }
        System.out.println(readingChap);
    }

    private void setView() {
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                mViewModel.getmFont());
        mContentTv.setTextSize(mViewModel.getmTextSize());
        mContentTv.setTypeface(tf, mViewModel.getmTextType());
        mContentTv.setTextColor(mViewModel.getmTextColor());
        mTitleTv.setTextSize(mViewModel.getmTextSize());
        mTitleTv.setTypeface(tf, mViewModel.getmTextType());
        mTitleTv.setTextColor(mViewModel.getmTextColor());
        updateContent();
    }


}
