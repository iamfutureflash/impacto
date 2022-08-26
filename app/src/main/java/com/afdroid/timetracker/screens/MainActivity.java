package com.afdroid.timetracker.screens;

import static com.afdroid.timetracker.Utils.AppHelper.initAppHelper;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afdroid.timetracker.BlackTimeoutActivity;
import com.afdroid.timetracker.QuestionActivity;
import com.afdroid.timetracker.R;
import com.afdroid.timetracker.Utils.AppHelper;
import com.afdroid.timetracker.adapters.PagerAdapter;
import com.afdroid.timetracker.preferences.TimeTrackerPrefHandler;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Thread for15minutesEyesExcersiseThread;
    Thread for60minutesQuestionRoundThread;
    int countfor15minutesEyesExcersiseThread;
    int countfor60minutesQuestionRoundThread;
//    final long for15minutesEyesExcersise = 900000;
//    final long for60minutesQuestionRound = 3600000;
    final long for15minutesEyesExcersise = 90;
    final long for60minutesQuestionRound = 200;
    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 100;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayout mainTabLayout;
    private static PagerAdapter pagerAdapter;
    private RelativeLayout tutorialView;
    private List<String> prefList = new ArrayList<String>();
    private AdView mAdView;
    private String[] defaultList = {"com.facebook.katana", "com.instagram.android", "com.whatsapp", "com.android.chrome"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimeTrackerPrefHandler.INSTANCE.setMode(0, getApplicationContext());

//        MobileAds.initialize(this, AppHelper.APP_ID);
//        MobileAds.initialize(this,
//                "ca-app-pub-3940256099942544~3347511713"); //add test
//        mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        for15minutesEyesExcersiseThread = null;
        for60minutesQuestionRoundThread = null;
        countfor15minutesEyesExcersiseThread=0;
        countfor60minutesQuestionRoundThread = 0;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tutorialView = (RelativeLayout) findViewById(R.id.tutorial_screen);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        mainTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);

        prefList = new LinkedList<String>();

        setTutorialScreen();
        createLayout();
        RunInbackground();
    }

    private void setAppList() {
        String serialized = TimeTrackerPrefHandler.INSTANCE.getPkgList(getApplicationContext());
        if (serialized != null) {
            prefList = Arrays.asList(TextUtils.split(serialized, ","));
            setViewPager();
        }
    }
    private void RunInbackground(){
        for15minutesEyesExcersiseThread = new Thread(new Runnable() {
            @Override
            public void run(){
                while(true) {
                    try {
                        Thread.sleep(500);
                        countfor15minutesEyesExcersiseThread++;
                        Log.d("Time", "countfor15minutesEyesExcersiseThread " + countfor15minutesEyesExcersiseThread);
                        if(countfor15minutesEyesExcersiseThread==for15minutesEyesExcersise){
                            countfor15minutesEyesExcersiseThread=0;
                            Intent intent = new Intent(MainActivity.this, BlackTimeoutActivity.class);
                            Toast.makeText(getApplicationContext(), "MainActivity.this, BlackTimeoutActivity.class for eyes excercise", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        for15minutesEyesExcersiseThread.start();

        for60minutesQuestionRoundThread = new Thread(new Runnable() {
            @Override
            public void run(){
                while(true) {
                    try {
                        Thread.sleep(1000);
                        countfor60minutesQuestionRoundThread++;
                        Log.d("Time", "countfor60minutesQuestionRoundThread " + countfor60minutesQuestionRoundThread);
                        if(countfor60minutesQuestionRoundThread==for60minutesQuestionRound){
                            countfor60minutesQuestionRoundThread=0;
                            Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                            Toast.makeText(getApplicationContext(), "MainActivity.this, BlackTimeoutActivity.class for eyes excercise", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        for60minutesQuestionRoundThread.start();
    }
    private void forTaskOne() {

    }
    private void createLayout() {
        setMainTabLayout();
        setTabLayout();
        setViewPager();
        fillStats();

    }

    private void setMainTabLayout() {
        mainTabLayout.addTab(mainTabLayout.newTab().setText(getString(R.string.app)));
//        mainTabLayout.addTab(mainTabLayout.newTab().setText(getString(R.string.network)));
//        mainTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mainTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TimeTrackerPrefHandler.INSTANCE.setMode(tab.getPosition(), getApplicationContext());
                setViewPager();
                Log.e("NETWORK_USAGE", "current mode : " + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.daily_stats)));
//        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.yesterday_stats)));
//        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.weekly_stats)));
//        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.monthly_stats)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setViewPager() {
        if (pagerAdapter != null) {
            pagerAdapter = null;
        }
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),
                prefList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void fillStats() {
        if (hasPermission()) {
            initAppHelper(getApplicationContext());
        } else {
            requestPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAppList();
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),
                prefList);
        viewPager.setAdapter(pagerAdapter);
        Log.d("MainActivity", "onResume: ");
        Toast.makeText(this, "on Resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS:
                fillStats();
                break;
        }
    }

    private void requestPermission() {
        Toast.makeText(this, "Need to request permission", Toast.LENGTH_SHORT).show();
        startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
    }

    private boolean hasPermission() {
        AppOpsManager appOps = (AppOpsManager)
                getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //launch settings screen
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_refresh:
                setViewPager();
                pagerAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_info:
                showTutorialView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onClosePressed(View view) {
        hideTutorialView();
    }

    private void hideTutorialView() {
        tutorialView.setVisibility(View.INVISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        mainTabLayout.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
    }

    private void setTutorialScreen() {
        if (TimeTrackerPrefHandler.INSTANCE.getIsFirstTime(getApplicationContext())) {
            showTutorialView();
            setDefaultSelection();
            TimeTrackerPrefHandler.INSTANCE.saveIsFirstTime(false, getApplicationContext());

        }
    }

    private void showTutorialView() {
        tutorialView.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.INVISIBLE);
        mainTabLayout.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
        viewPager.setVisibility(View.INVISIBLE);
    }

    private void setDefaultSelection() {
        PackageManager pm = getPackageManager();
        for (String packageName : defaultList) {
            Intent intent = pm.getLaunchIntentForPackage(packageName);
            if (intent != null) {
                List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.size() > 0) {
                    prefList.add(packageName);
                }
            }
        }
        TimeTrackerPrefHandler.INSTANCE.savePkgList(TextUtils.join(",", prefList), this);
    }
}
