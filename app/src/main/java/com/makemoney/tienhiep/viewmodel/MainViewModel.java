package com.makemoney.tienhiep.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;

import com.makemoney.tienhiep.R;
import com.makemoney.tienhiep.model.Chapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {
    private int mTextSize = 20;
    private int mReadingChapter = 0;
    private int mTextColor = Color.BLACK;
    private int mTextType = Typeface.NORMAL;
    private String[] mFont = {"font/benne.ttf", "font/OtomanopeeOne.ttf", "font/secondfont.ttf", "font/thirdfont.ttf", "font/Uchen.ttf", "font/title.ttf"};
    private int mFontNumber;
    private ArrayList<Chapter> mListChapter = new ArrayList<>();
    private FragmentManager mFramentManager;
    private Context mContext;
    private SharedPreferences sharedPreferences;

    public MainViewModel(@NonNull Application application) throws JSONException {
        super(application);
        this.sharedPreferences = application.getSharedPreferences("DLDL", Context.MODE_PRIVATE);
        this.mContext = application.getApplicationContext();
        GetData();
    }

    public int getmTextSize() {
        return mTextSize;
    }

    public void setmTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public int getmTextType() {
        return mTextType;
    }

    public void setmTextType(int mTextType) {
        this.mTextType = mTextType;
    }

    public String getmFont() {
        return mFont[mFontNumber];
    }

    public int getmFontNumber() {
        return mFontNumber;
    }

    public void setmFontNumber(int mFontNumber) {
        this.mFontNumber = mFontNumber;
    }

    public FragmentManager getmFramentManager() {
        return mFramentManager;
    }

    public void setmFramentManager(FragmentManager mFramentManager) {
        this.mFramentManager = mFramentManager;
    }

    public ArrayList<Chapter> getmListChapter() {
        return mListChapter;
    }

    public void setmListChapter(ArrayList<Chapter> mListChapter) {
        this.mListChapter = mListChapter;
    }

    public int getmReadingChapter() {
        return mReadingChapter;
    }

    public void setmReadingChapter(int mReadingChapter) {
        this.mReadingChapter = mReadingChapter;
    }

    private void GetData(){
        try {
            ReadJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(sharedPreferences != null){
            getSaveData();
        }
    }

    public void getSaveData(){
        mReadingChapter = sharedPreferences.getInt("reading", 0);
        mTextSize = sharedPreferences.getInt("size", 20);
        mTextColor = sharedPreferences.getInt("color", Color.BLACK);
        mTextType = sharedPreferences.getInt("type", Typeface.NORMAL);
        mFontNumber = sharedPreferences.getInt("font", 0);
    }

    public void saveData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("reading", getmReadingChapter());
        editor.putInt("size", getmTextSize());
        editor.putInt("color", getmTextColor());
        editor.putInt("type", getmTextType());
        editor.putInt("font", getmFontNumber());
        editor.commit();
    }

    private void ReadJson() throws JSONException {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("DLDL.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ParseJson(json);
    }

    private void ParseJson(String json) throws JSONException {
        JSONObject mainObject = new JSONObject(json);
        JSONArray array= mainObject.getJSONArray("chapter");
        for(int i=0;i<array.length();i++){
            JSONObject chapter= array.getJSONObject(i);
            int id= chapter.getInt("field1");
            String title= chapter.getString("field2");
            String content= chapter.getString("field3");
            mListChapter.add(new Chapter(id,title,content));
        }
    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getmFramentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainview, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
