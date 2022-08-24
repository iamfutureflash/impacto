package com.afdroid.timetracker.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.app.usage.UsageStats;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afdroid.timetracker.R;
import com.afdroid.timetracker.Utils.AppHelper;
import com.afdroid.timetracker.chartformatter.DayAxisValueFormatter;
import com.afdroid.timetracker.preferences.TimeTrackerPrefHandler;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StatsFragment extends Fragment {
    private BarChart barChart;

    private TextView startTime;
    private TextView endTime;

    private View rootView;

    private final int DAILY = AppHelper.DAILY_STATS;
    private final int YESTERDAY = AppHelper.YESTERDAY_STATS;
    private final int WEEKLY = AppHelper.WEEKLY_STATS;
    private final int MONTHLY = AppHelper.MONTHLY_STATS;
    private final int NETWORK_MODE = AppHelper.NETWORK_MODE;

    private int selectedPeriod = 0;
    private int mode = 0;
    private List<String> appList = null;
    private List<String> appNameList = null;

    private Context context;

    private  int count =1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        rootView = inflater.inflate(R.layout.fragment_stats, container, false);
        Bundle args = getArguments();
        selectedPeriod = args.getInt("period", 0);
        barChart = (BarChart) rootView.findViewById(R.id.chart1);
        barChart.setNoDataText(getResources().getString(R.string.no_data));
        startTime = (TextView) rootView.findViewById(R.id.tvStartTime);
        endTime = (TextView) rootView.findViewById(R.id.tvEndTime);
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        mode = TimeTrackerPrefHandler.INSTANCE.getMode(context);
        String serialized = TimeTrackerPrefHandler.INSTANCE.getPkgList(context);
        if (serialized != null) {
            appList = null;
            appNameList = null;
            appList = new LinkedList<String>(Arrays.asList(TextUtils.
                    split(serialized, ",")));
            appNameList = new LinkedList<String>();
        }
        if (mode == 1 && (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)) {
            showAlert();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getStatsInfo();
        }
    }

    private void showAlert() {
        new AlertDialog.Builder(getActivity())
//                .setTitle(R.string.network_stats)
                .setMessage(R.string.networks_stats_na)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getStatsInfo() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startMillis = 0;
        long endMillis = System.currentTimeMillis();

        Date endresultdate = new Date(System.currentTimeMillis());
        switch (selectedPeriod) {
            case DAILY:
                startMillis = calendar.getTimeInMillis();
                break;
            case YESTERDAY:
                calendar.set(Calendar.HOUR_OF_DAY, -24);
                startMillis = calendar.getTimeInMillis();
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 99);
                endMillis = calendar.getTimeInMillis();
                endresultdate = calendar.getTime();
                break;
            case WEEKLY:
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                startMillis = calendar.getTimeInMillis();
                break;
            case MONTHLY:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                startMillis = calendar.getTimeInMillis();
                break;
            default:
                break;
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date startresultdate = new Date(startMillis);
        startTime.setText("From " + sdf.format(startresultdate));
        endTime.setText("To " + sdf.format(endresultdate));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setUsageInfo(startMillis, endMillis);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUsageInfo(long startMillis, long endMillis) {
        if (appList != null) {
            PackageManager packageManager = context.getPackageManager();
            ArrayList<Float> values = new ArrayList<Float>();
            ApplicationInfo info = null;

            for (int i = 0; i < appList.size(); i++) {
                String appPkg = appList.get(i);
                String appname = null;
                int uid = 0;
                try {
                    appname = (String) packageManager.getApplicationLabel(packageManager.
                            getApplicationInfo(appPkg, PackageManager.GET_META_DATA));
                    info = packageManager.getApplicationInfo(appPkg, 0);

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                if (appname != null) {
                    appNameList.add(appname);
                    if (mode == NETWORK_MODE) {
                        uid = info.uid;
                        values.add(fetchNetworkStatsInfo(startMillis, endMillis, uid));

                    } else {
                        values.add(fetchAppStatsInfo(startMillis, endMillis, appPkg));
                    }
//                    values.sort(app);
                } else {
                    appList.remove(appPkg);
                }
            }
            saveAppPreference();
            if (values.size() != 0) {
                setChart(values);
            }
        }
    }

    private float fetchAppStatsInfo(long startMillis, long endMillis, String appPkg) {
        Map<String, UsageStats> lUsageStatsMap = AppHelper.getUsageStatsManager().
                queryAndAggregateUsageStats(startMillis, endMillis);
        float total = 0.0f;
        if (lUsageStatsMap.containsKey(appPkg)) {
            if (selectedPeriod == DAILY || selectedPeriod == YESTERDAY) {
                total = (AppHelper.getMinutes(lUsageStatsMap.get(appPkg).
                        getTotalTimeInForeground()));
            } else {
                total = (AppHelper.getHours(lUsageStatsMap.get(appPkg).
                        getTotalTimeInForeground()));
            }
        }
        return total;
    }

    private float fetchNetworkStatsInfo(long startMillis, long endMillis, int uid) {
        NetworkStatsManager networkStatsManager;
        float total = 0.0f;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            float receivedWifi = 0;
            float sentWifi = 0;
            float receivedMobData = 0;
            float sentMobData = 0;

            networkStatsManager = (NetworkStatsManager) context.getSystemService(Context.NETWORK_STATS_SERVICE);
            NetworkStats nwStatsWifi = networkStatsManager.queryDetailsForUid(ConnectivityManager.TYPE_WIFI, null,
                    startMillis, endMillis, uid);
            NetworkStats.Bucket bucketWifi = new NetworkStats.Bucket();
            while (nwStatsWifi.hasNextBucket()) {
                nwStatsWifi.getNextBucket(bucketWifi);
                receivedWifi = receivedWifi + bucketWifi.getRxBytes();
                sentWifi = sentWifi + bucketWifi.getTxBytes();
            }

            NetworkStats nwStatsMobData = networkStatsManager.queryDetailsForUid(ConnectivityManager.TYPE_MOBILE, null,
                    startMillis, endMillis, uid);
            NetworkStats.Bucket bucketMobData = new NetworkStats.Bucket();
            while (nwStatsMobData.hasNextBucket()) {
                nwStatsMobData.getNextBucket(bucketMobData);
                receivedMobData = receivedMobData + bucketMobData.getRxBytes();
                sentMobData = sentMobData + bucketMobData.getTxBytes();
            }

            if (selectedPeriod == DAILY || selectedPeriod == YESTERDAY) {
                total = (receivedWifi + sentWifi + receivedMobData + sentMobData) / (1024 * 1024);
            } else {
                total = (receivedWifi + sentWifi + receivedMobData + sentMobData) / (1024 * 1024 * 1024);
            }
        }
        return total;
    }

    private void saveAppPreference() {
        TimeTrackerPrefHandler.INSTANCE.savePkgList
                (TextUtils.join(",", appList), context);
    }

    private void setChart(ArrayList<Float> values) {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
//        barChart.setMaxVisibleValueCount(60);
        barChart.setVisibleXRangeMaximum(6);
        barChart.moveViewToX(10);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(true);
//        barChart.setColors(new int[] {Color.RED, Color.GREEN, Color.rgb(255,127,80)});
        barChart.setDrawGridBackground(true);
//         barChart.setDrawYLabels(false);
        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(barChart, appNameList);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(appNameList.size());
        xAxis.setValueFormatter(xAxisFormatter);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(10, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(5f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
//        l.setXEntrySpace(4f);

        setData(values);
    }

    private void setData(ArrayList<Float> values) {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < values.size(); i++) {
            float val = values.get(i);
            yVals1.add(new BarEntry(i, val));
//            Toast.makeText(context, "" + appNameList.get(i).toUpperCase(Locale.ROOT) + " " + yVals1.get(i), Toast.LENGTH_SHORT).show();
        }
        float a=0,c = values.get(0);
        for (int i = 0; i < values.size()-1; i++) {
            a = values.get(i);
            String name = appNameList.get(i);
            if(c>a){
                c=c;
                Toast.makeText(context, "for i " + i + "Usage is  "+ c + " for " + name , Toast.LENGTH_SHORT).show();
            }else if(c<a){
                c=a;
                Toast.makeText(context, "for i " + i + "Usage is  "+ c + " for " + name , Toast.LENGTH_SHORT).show();
            }
        }
        Toast.makeText(context, "final value of c is  "+ c, Toast.LENGTH_SHORT).show();

//        if(c > 120&&count==1){
//                Intent intent = new Intent(StatsFragment.this.getActivity(), QuestionActivity.class);
//                Toast.makeText(context, "started intent StatsFragment.this.getActivity() --> QuestionActivity.class", Toast.LENGTH_LONG).show();
//                startActivity(intent);
//                count++;
//        }else if(c<120){
//            Toast.makeText(context, " All apps set limit is 100 minute " , Toast.LENGTH_LONG).show();
//        }
        BarDataSet set1 = null;


        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            if (selectedPeriod == DAILY || selectedPeriod == YESTERDAY) {
                set1 = new BarDataSet(yVals1, ((mode == 0) ? "App" : "Network") + " usage in " + ((mode == 0) ? "Minutes" : "MB"));
            } else {
                set1 = new BarDataSet(yVals1, ((mode == 0) ? "App" : "Network") + " usage in " + ((mode == 0) ? "Hours" : "GB"));
            }
            set1.setDrawIcons(true);
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(.6f);
            barChart.setData(data);
            barChart.setVisibleXRangeMaximum(4.0f);
//            for (int i = 0; i < values.size(); i++) {
//                Toast.makeText(context, " " +values.get(i), Toast.LENGTH_SHORT).show();
//                if(values.get(i)>120){
//                    sleep(200);
//                    Toast.makeText(context, " for:- " +values.get(i) + " Red is set" , Toast.LENGTH_SHORT).show();
//                    set1.setColors(Color.RED);
//                }
//                if(values.get(i)<60){
//                    sleep(200);
//                    Toast.makeText(context, " for:- " +values.get(i) + " green is set" , Toast.LENGTH_SHORT).show();
//                    set1.setColors(Color.GREEN);
//                }if(values.get(i)>61&&values.get(i)<120){
//                    sleep(200);
//                    Toast.makeText(context, " for:- " +values.get(i) + " orange is set" , Toast.LENGTH_SHORT).show();
//                    set1.setColors(Color.rgb(255,127,80));
//                }
//                if(i==values.size()){
//                    Toast.makeText(context, " value = to i break" , Toast.LENGTH_SHORT).show();
//                    sleep(200);
//                    break;
//               }
//            }
//            for (int i = 0; i < values.size(); i++) {
//                Toast.makeText(context, " " +values.get(i), Toast.LENGTH_SHORT).show();
//                if(values.get(i)>30){
//                    sleep(200);
//                    Toast.makeText(context, " for:- " +values.get(i) + " Red is set" , Toast.LENGTH_SHORT).show();
//                    set1.setColors(Color.RED);
//                }
//                if(values.get(i)<20){
//                    sleep(200);
//                    Toast.makeText(context, " for:- " +values.get(i) + " green is set" , Toast.LENGTH_SHORT).show();
//                    set1.setColors(Color.RED);
//                }if(values.get(i)>20&&values.get(i)<30){
//                    sleep(200);
//                    Toast.makeText(context, " for:- " +values.get(i) + " orange is set" , Toast.LENGTH_SHORT).show();
//                    set1.setColors(Color.rgb(255,127,80));
//                }
//                if(i==values.size()){
//                    Toast.makeText(context, " value = to i break" , Toast.LENGTH_SHORT).show();
//                    sleep(200);
//                    break;
//                }
//            }
        }


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
