package com.makemoney.tienhiep.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.makemoney.tienhiep.viewmodel.MainViewModel;
import com.makemoney.tienhiep.R;

public class FragmentSetting extends DialogFragment {
    private Button closeSetting;
    private MainViewModel mViewModel;
    private ImageView mIncreaseTs, mDecreaseTs;
    private Button mBoldType, mItalicType, mNormalType;
    private Button mRedColor, mBlueColor, mBlackColor;
    private ImageButton mLeftBtn, mRightBtn;
    private TextView mSettingTv;
    private int mFontNumber;
    private static FragmentSetting mFragmentSetting;

    public static FragmentSetting getInstance(){
        if(mFragmentSetting == null){
            mFragmentSetting = new FragmentSetting();
        }
        return mFragmentSetting;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mappingView(view);
        initView();
        return view;
    }

    private void initView() {
        mSettingTv.setTextColor(mViewModel.getmTextColor());
        mSettingTv.setTypeface(null, mViewModel.getmTextType());
        mSettingTv.setTextSize(mViewModel.getmTextSize());
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                mViewModel.getmFont());
        mSettingTv.setTypeface(tf, mViewModel.getmTextType());
    }

    private void mappingView(View view) {
        mIncreaseTs = (ImageView) view.findViewById(R.id.increase_btn);
        mDecreaseTs = (ImageView) view.findViewById(R.id.decrease_btn);
        mBoldType = (Button) view.findViewById(R.id.stype_bold);
        mItalicType = (Button) view.findViewById(R.id.stype_italic);
        mNormalType = (Button) view.findViewById(R.id.stype_normal);
        mRedColor = (Button) view.findViewById(R.id.textR);
        mBlueColor = (Button) view.findViewById(R.id.textBlu);
        mBlackColor = (Button) view.findViewById(R.id.textB);
        mLeftBtn = (ImageButton) view.findViewById(R.id.left_btn);
        mRightBtn = (ImageButton) view.findViewById(R.id.right_btn);
        mSettingTv = (TextView) view.findViewById(R.id.test_st);
        closeSetting = view.findViewById(R.id.close_btn);

        mIncreaseTs.setOnClickListener(this::ButtonClick);
        mDecreaseTs.setOnClickListener(this::ButtonClick);
        mBoldType.setOnClickListener(this::ButtonClick);
        mItalicType.setOnClickListener(this::ButtonClick);
        mNormalType.setOnClickListener(this::ButtonClick);
        mRedColor.setOnClickListener(this::ButtonClick);
        mBlueColor.setOnClickListener(this::ButtonClick);
        mBlackColor.setOnClickListener(this::ButtonClick);
        closeSetting.setOnClickListener(this::ButtonClick);
        mLeftBtn.setOnClickListener(this::ButtonClick);
        mRightBtn.setOnClickListener(this::ButtonClick);
    }

    public void ButtonClick(View view) {
        int font = mViewModel.getmFontNumber();
        switch (view.getId()) {
            case R.id.close_btn:
                getFragmentManager().popBackStack();
                break;

            case R.id.increase_btn:
                mViewModel.setmTextSize(Math.min(mViewModel.getmTextSize() + 1, 40));
                mSettingTv.setTextSize(mViewModel.getmTextSize());
                break;

            case R.id.decrease_btn:
                mViewModel.setmTextSize(Math.max(mViewModel.getmTextSize() - 1, 12));
                mSettingTv.setTextSize(mViewModel.getmTextSize());
                break;

            case R.id.stype_bold:
                mViewModel.setmTextType(Typeface.BOLD);
                mSettingTv.setTypeface(null, mViewModel.getmTextType());
                break;

            case R.id.stype_italic:
                mViewModel.setmTextType(Typeface.ITALIC);
                mSettingTv.setTypeface(null, mViewModel.getmTextType());
                break;

            case R.id.stype_normal:
                mViewModel.setmTextType(Typeface.NORMAL);
                mSettingTv.setTypeface(null, mViewModel.getmTextType());
                break;

            case R.id.textR:
                mViewModel.setmTextColor(Color.RED);
                mSettingTv.setTextColor(Color.RED);
                break;

            case R.id.textBlu:
                mViewModel.setmTextColor(Color.BLUE);
                mSettingTv.setTextColor(Color.BLUE);
                break;

            case R.id.textB:
                mViewModel.setmTextColor(Color.BLACK);
                mSettingTv.setTextColor(Color.BLACK);
                break;

            case R.id.left_btn:
                if (font == 0) font = 5;
                else font--;
                mViewModel.setmFontNumber(font);
                mSettingTv.setTypeface(null, mViewModel.getmTextType());

                mViewModel.setmFontNumber(font);
                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                        mViewModel.getmFont());
                mSettingTv.setTypeface(tf, mViewModel.getmTextType());
                break;

            case R.id.right_btn:
                if (font == 5) font = 0;
                else font++;
                mViewModel.setmFontNumber(font);
                mSettingTv.setTypeface(null, mViewModel.getmTextType());
                mViewModel.setmFontNumber(font);
                tf = Typeface.createFromAsset(getActivity().getAssets(),
                        mViewModel.getmFont());
                mSettingTv.setTypeface(tf, mViewModel.getmTextType());
                break;
        }
    }
}
