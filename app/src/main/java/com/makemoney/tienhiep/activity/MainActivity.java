package com.makemoney.tienhiep.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.makemoney.tienhiep.viewmodel.MainViewModel;
import com.makemoney.tienhiep.R;
import com.makemoney.tienhiep.fragment.FragmentMain;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity extends AppCompatActivity {
    private MainViewModel mViewModel;
    private AdView mAdView;
    private FrameLayout mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initApp();
        mViewModel.setFragment(FragmentMain.getInstance());
    }

    private void initApp(){
        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);
        mViewModel.setmFramentManager(this.getSupportFragmentManager());
        mFragment = findViewById(R.id.mainview);
        if(isNetworkConnected()) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mFragment.getLayoutParams();
            mFragment.setLayoutParams(params);
            params.setMargins(0, 0, 0, 100);
            loadAd();
        }
        else{
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mFragment.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            mFragment.setLayoutParams(params);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.saveData();
    }

    private void loadAd(){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
