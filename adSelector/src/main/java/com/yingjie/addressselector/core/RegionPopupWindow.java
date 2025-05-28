package com.yingjie.addressselector.core;

import android.content.Context;


import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yingjie.addressselector.R;

import com.yingjie.addressselector.api.AdType;

import java.util.List;

/**
 * Created by chen.yingjie on 2019/3/23
 */
public class RegionPopupWindow extends LinearLayout {

    FrameLayout flFork;// 叉
    TextView tvProvince;
    TextView tvCity;
    TextView tvArea;
    View bottomLineProvince;
    View bottomLineCity;
    View bottomLineArea;
    RecyclerView recycleView;

    private OnForkClickListener onForkClickListener;
    private OnRpwItemClickListener onRpwItemClickListener;

    private RegionAdapter mRregionAdapter;
    private List<RegionBean> provinceDatas;
    private LinearLayoutManager recycleManager;
    private int mType;
    private String checkProvince;
    private String checkCity;
    private String checkArea;

    public RegionPopupWindow(Context context) {
        this(context, null);
    }

    public RegionPopupWindow(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RegionPopupWindow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.region_popupwindow, this, true);
        setBackgroundResource(R.color.white);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViews();
        bindListeners();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
    }

    private void initView() {
        provinceDatas = GsonU.getJsonData(getContext());

        bottomLineProvince.setBackgroundColor(getResources().getColor(mBottomLineColor));
        bottomLineArea.setBackgroundColor(getResources().getColor(mBottomLineColor));
        bottomLineCity.setBackgroundColor(getResources().getColor(mBottomLineColor));
        mRregionAdapter.setSelectColor(mSelectColor);

        if (mType == AdType.EDIT) {// 编辑模式
            // 省显示黑色选定值，底线隐藏
            tvProvince.setTextColor(getContext().getResources().getColor(R.color.v333333));
            tvProvince.setText(checkProvince);
            bottomLineProvince.setVisibility(GONE);
            // 市显示红色"请选择"， 底线显示
            tvCity.setTextColor(getContext().getResources().getColor(R.color.v333333));
            tvCity.setText(checkCity);
            bottomLineCity.setVisibility(GONE);
            // 县/区不显示，底线隐藏
            tvArea.setText(checkArea);
            tvArea.setTextColor(getContext().getResources().getColor(mSelectColor));
            bottomLineArea.setVisibility(VISIBLE);

            mRregionAdapter.refreshData(provinceDatas, RegionAdapter.DATA_AREA, checkProvince, checkCity, checkArea);

            // 定位到已选项
            int targetPosition = mRregionAdapter.getAreaPosition(checkProvince, checkCity, checkArea);
            scrollToPosition(targetPosition);
        } else if (mType == AdType.ADD) {
            //  第一次进来，没有选择地址。
            if (TextUtils.isEmpty(checkProvince) && TextUtils.isEmpty(checkCity) && TextUtils.isEmpty(checkArea)) {
                // 初始状态
                tvProvince.setTextColor(getContext().getResources().getColor(mSelectColor));
                tvProvince.setText("请选择");
                bottomLineProvince.setVisibility(VISIBLE);

                tvCity.setText("");
                bottomLineCity.setVisibility(GONE);

                tvArea.setText("");
                bottomLineArea.setVisibility(GONE);

                mRregionAdapter.refreshData(provinceDatas, RegionAdapter.DATA_PROVINCE, checkProvince, checkCity, checkArea);
            } else {//  选择过地址，再次选择。
                tvProvince.setTextColor(getContext().getResources().getColor(R.color.v333333));
                tvProvince.setText(checkProvince);
                bottomLineProvince.setVisibility(GONE);

                // 市显示红色"请选择"， 底线显示
                tvCity.setTextColor(getContext().getResources().getColor(R.color.v333333));
                tvCity.setText(checkCity);
                bottomLineCity.setVisibility(GONE);

                // 县/区不显示，底线隐藏
                tvArea.setTextColor(getContext().getResources().getColor(mSelectColor));
                tvArea.setText(checkArea);
                bottomLineArea.setVisibility(VISIBLE);

                if (mSelectLevel == 3){
                    mRregionAdapter.refreshData(provinceDatas, RegionAdapter.DATA_AREA, checkProvince, checkCity, checkArea);
                }else if (mSelectLevel == 2){
                    bottomLineCity.setVisibility(VISIBLE);
                    mRregionAdapter.refreshData(provinceDatas, RegionAdapter.DATA_CITY, checkProvince, checkCity, checkArea);
                }

                int targetPosition = mRregionAdapter.getAreaPosition(checkProvince, checkCity, checkArea);
                scrollToPosition(targetPosition);
            }
        }
    }

    private void findViews() {
        flFork = findViewById(R.id.flFork);
        tvProvince = findViewById(R.id.tvProvince);
        tvCity = findViewById(R.id.tvCity);
        tvArea = findViewById(R.id.tvArea);
        bottomLineProvince = findViewById(R.id.bottomLineProvince);
        bottomLineCity = findViewById(R.id.bottomLineCity);
        bottomLineArea = findViewById(R.id.bottomLineArea);
        recycleView = findViewById(R.id.recycleView);

        recycleManager = new LinearLayoutManager(getContext());
        mRregionAdapter= new RegionAdapter(getContext());
        recycleView.setLayoutManager(recycleManager);
        recycleView.setAdapter(mRregionAdapter);
        mRregionAdapter.setSelectColor(mSelectColor);
    }

    private void bindListeners() {
        // 点击省
        tvProvince.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 省显示黑色选定值，底线隐藏
                tvProvince.setTextColor(getContext().getResources().getColor(mSelectColor));
                bottomLineProvince.setVisibility(VISIBLE);

                // 市显示红色"请选择"， 底线显示
                tvCity.setTextColor(getContext().getResources().getColor(R.color.v333333));
                bottomLineCity.setVisibility(GONE);

                // 县/区不显示，底线隐藏
                tvArea.setTextColor(getContext().getResources().getColor(R.color.v333333));
                bottomLineArea.setVisibility(GONE);

                mRregionAdapter.refreshData(provinceDatas, RegionAdapter.DATA_PROVINCE, checkProvince, checkCity, checkArea);

                // 定位到已选项
                int targetPosition = mRregionAdapter.getProvincePisition(checkProvince);
                scrollToPosition(targetPosition);
            }
        });

        // 点击市
        tvCity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 省显示黑色选定值，底线隐藏
                tvProvince.setTextColor(getContext().getResources().getColor(R.color.v333333));
                bottomLineProvince.setVisibility(GONE);

                // 市显示红色"请选择"， 底线显示
                tvCity.setTextColor(getContext().getResources().getColor(mSelectColor));
                bottomLineCity.setVisibility(VISIBLE);

                // 县/区不显示，底线隐藏
                tvArea.setTextColor(getContext().getResources().getColor(R.color.v333333));
                bottomLineArea.setVisibility(GONE);

                mRregionAdapter.refreshData(provinceDatas, RegionAdapter.DATA_CITY, checkProvince, checkCity, checkArea);

                // 定位到已选项
                int targetPosition = mRregionAdapter.getCityPosition(checkProvince, checkCity);
                scrollToPosition(targetPosition);
            }
        });

        // 点击县/区
        tvArea.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvProvince.setTextColor(getContext().getResources().getColor(R.color.v333333));
                bottomLineProvince.setVisibility(GONE);

                // 市显示红色"请选择"， 底线显示
                tvCity.setTextColor(getContext().getResources().getColor(R.color.v333333));
                bottomLineCity.setVisibility(GONE);

                // 县/区不显示，底线隐藏
                tvArea.setTextColor(getContext().getResources().getColor(mSelectColor));
                bottomLineArea.setVisibility(VISIBLE);

                mRregionAdapter.refreshData(provinceDatas, RegionAdapter.DATA_AREA, checkProvince, checkCity, checkArea);

                // 定位到已选项
                int targetPosition = mRregionAdapter.getAreaPosition(checkProvince, checkCity, checkArea);
                scrollToPosition(targetPosition);
            }
        });

        // 点击❌
        flFork.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onForkClickListener.onForkClick();
            }
        });

        mRregionAdapter.setOnItemCheckedListener(new RegionAdapter.OnItemCheckedListener() {
            @Override
            public void onItemChecked(int lastDataType, String checkedProvince, String checkedCity, String checkedArea) {
                int newDataType = 0;
                // 根据上次点击的类型 赋予新值。
                if (lastDataType == RegionAdapter.DATA_PROVINCE) {// 点击了 省，该选择市
                    newDataType = RegionAdapter.DATA_CITY;
                    checkProvince = checkedProvince;
                    checkCity = checkedCity;
                    checkArea = checkedArea;

                    // 省显示黑色选定值，底线隐藏
                    tvProvince.setTextColor(getContext().getResources().getColor(R.color.v333333));
                    tvProvince.setText(checkProvince);
                    bottomLineProvince.setVisibility(GONE);

                    // 市显示红色"请选择"， 底线显示
                    tvCity.setTextColor(getContext().getResources().getColor(mSelectColor));
                    tvCity.setText("请选择");
                    bottomLineCity.setVisibility(VISIBLE);

                    // 县/区不显示，底线隐藏
                    tvArea.setText(checkArea);
                    bottomLineArea.setVisibility(GONE);

                    // 如果选择了省，把已选择市和县/区置空。
                    checkCity = "";
                    checkArea = "";
                    if (mSelectLevel == 1){
                        onRpwItemClickListener.onRpwItemClick(replace(checkProvince), replace(checkCity), replace(checkArea));
                    }
                } else if (lastDataType == RegionAdapter.DATA_CITY) {// 点击了 市，该选择县/区
                    newDataType = RegionAdapter.DATA_AREA;
                    checkProvince = checkedProvince;
                    checkCity = checkedCity;
                    checkArea = checkedArea;

                    // 省显示黑色选定值，底线隐藏

                    // 市显示黑色选定值，底线隐藏
                    tvCity.setTextColor(getContext().getResources().getColor(R.color.v333333));
                    tvCity.setText(checkCity);
                    bottomLineCity.setVisibility(GONE);

                    // 县/区显示红色"请选择，底线显示
                    tvArea.setText("请选择");
                    tvArea.setTextColor(getContext().getResources().getColor(mSelectColor));
                    bottomLineArea.setVisibility(VISIBLE);

                    // 如果选择了市，就把已选择县/区置空。
                    checkArea = "";
                    if (mSelectLevel == 2){
                        onRpwItemClickListener.onRpwItemClick(replace(checkProvince), replace(checkCity), replace(checkArea));
                    }
                } else if (lastDataType == RegionAdapter.DATA_AREA){// 点击了 县/区
                    checkProvince = checkedProvince;
                    checkCity = checkedCity;
                    checkArea = checkedArea;

                    // 省显示黑色选定值，底线隐藏

                    // 市显示黑色选定值，底线隐藏

                    // 县/区显示红色选定值，底线显示
                    tvArea.setText(checkArea);
                    // 回传
                    if (TextUtils.isEmpty(checkProvince)) {
                        checkProvince = tvProvince.getText().toString();
                    }
                    if (TextUtils.isEmpty(checkCity)) {
                        checkCity = tvCity.getText().toString();
                    }
                    if (TextUtils.isEmpty(checkArea)) {
                        checkArea = tvArea.getText().toString();
                    }
                    onRpwItemClickListener.onRpwItemClick(replace(checkProvince), replace(checkCity), replace(checkArea));
                }
                mRregionAdapter.refreshData(provinceDatas, newDataType, checkProvince, checkCity, checkArea);
                scrollToPosition(0);
            }
        });
    }

    private void scrollToPosition(int position) {
        if (position == -1) {
            recycleManager.scrollToPositionWithOffset(0, 0);
        } else {
            recycleManager.scrollToPositionWithOffset(position, 0);
        }
    }

    // "请选择"用""代替
    private String replace(String text) {
        String newText = "";
        if (text.equals("请选择")) {
            return newText;
        }
        return text;
    }
    int mSelectColor =  R.color.ff5000;
    int mBottomLineColor =  R.color.ff5000;
    int mSelectLevel = 3;

    public void setSelectLevel(int mSelectLevel) {
        this.mSelectLevel = mSelectLevel;
    }

    public void setColor(int color) {
        mSelectColor = color;
    }

    public void setBottomLineColor(int color) {
        mBottomLineColor = color;
    }

    public void setHistory(int mType, String province, String city, String area) {
        this.mType = mType;
        this.checkProvince = province;
        this.checkCity = city;
        this.checkArea = area;
    }

    public void setOnForkClickListener(OnForkClickListener onForkClickListener) {
        this.onForkClickListener = onForkClickListener;
    }

    public void setOnRpwItemClickListener(OnRpwItemClickListener onRpwItemClickListener) {
        this.onRpwItemClickListener = onRpwItemClickListener;
    }

    // 叉点击回调
    public interface OnForkClickListener {
        void onForkClick();
    }

    // 城市/县/区列表item点击回调
    public interface OnRpwItemClickListener {
        void onRpwItemClick(String selectedProvince, String selectedCity, String selectedArea);
    }
}
