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

    FrameLayout flFork;
    View titleBar;
    View titleDivider;
    View headerDivider;
    TextView tvTitle;
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

    private int mSelectColor = R.color.ff5000;
    private int mBottomLineColor = R.color.ff5000;
    private int mSelectLevel = 3;
    private int mBackgroundColor = R.color.white;
    private int mNormalTextColor = R.color.v666666;
    private int mTitleTextColor = R.color.v333333;
    private int mDividerColor = R.color.veeeeee;

    public RegionPopupWindow(Context context) {
        this(context, null);
    }

    public RegionPopupWindow(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RegionPopupWindow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.region_popupwindow, this, true);
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

        super.setBackgroundColor(mBackgroundColor);
        titleBar.setBackgroundColor(mBackgroundColor);
        tvTitle.setTextColor(mTitleTextColor);
        titleDivider.setBackgroundColor(mDividerColor);
        headerDivider.setBackgroundColor(mDividerColor);

        bottomLineProvince.setBackgroundColor(mBottomLineColor);
        bottomLineArea.setBackgroundColor(mBottomLineColor);
        bottomLineCity.setBackgroundColor(mBottomLineColor);
        mRregionAdapter.setSelectColor(mSelectColor);
        mRregionAdapter.setBackgroundColor(mBackgroundColor);
        mRregionAdapter.setNormalTextColor(mNormalTextColor);
        mRregionAdapter.setDividerColor(mDividerColor);

        if (mType == AdType.EDIT) {
            tvProvince.setTextColor(mNormalTextColor);
            tvProvince.setText(checkProvince);
            bottomLineProvince.setVisibility(GONE);

            tvCity.setTextColor(mNormalTextColor);
            tvCity.setText(checkCity);
            bottomLineCity.setVisibility(GONE);

            tvArea.setText(checkArea);
            tvArea.setTextColor(mSelectColor);
            bottomLineArea.setVisibility(VISIBLE);

            mRregionAdapter.refreshData(provinceDatas, RegionAdapter.DATA_AREA, checkProvince, checkCity, checkArea);
            int targetPosition = mRregionAdapter.getAreaPosition(checkProvince, checkCity, checkArea);
            scrollToPosition(targetPosition);
        } else if (mType == AdType.ADD) {
            if (TextUtils.isEmpty(checkProvince) && TextUtils.isEmpty(checkCity) && TextUtils.isEmpty(checkArea)) {
                tvProvince.setTextColor(mSelectColor);
                tvProvince.setText("请选择");
                bottomLineProvince.setVisibility(VISIBLE);

                tvCity.setText("");
                bottomLineCity.setVisibility(GONE);

                tvArea.setText("");
                bottomLineArea.setVisibility(GONE);

                mRregionAdapter.refreshData(provinceDatas, RegionAdapter.DATA_PROVINCE, checkProvince, checkCity, checkArea);
            } else {
                tvProvince.setTextColor(mNormalTextColor);
                tvProvince.setText(checkProvince);
                bottomLineProvince.setVisibility(GONE);

                tvCity.setTextColor(mNormalTextColor);
                tvCity.setText(checkCity);
                bottomLineCity.setVisibility(GONE);

                tvArea.setTextColor(mSelectColor);
                tvArea.setText(checkArea);
                bottomLineArea.setVisibility(VISIBLE);

                if (mSelectLevel == 3) {
                    mRregionAdapter.refreshData(provinceDatas, RegionAdapter.DATA_AREA, checkProvince, checkCity, checkArea);
                } else if (mSelectLevel == 2) {
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
        titleBar = findViewById(R.id.titleBar);
        titleDivider = findViewById(R.id.titleDivider);
        headerDivider = findViewById(R.id.headerDivider);
        tvTitle = findViewById(R.id.tvTitle);
        tvProvince = findViewById(R.id.tvProvince);
        tvCity = findViewById(R.id.tvCity);
        tvArea = findViewById(R.id.tvArea);
        bottomLineProvince = findViewById(R.id.bottomLineProvince);
        bottomLineCity = findViewById(R.id.bottomLineCity);
        bottomLineArea = findViewById(R.id.bottomLineArea);
        recycleView = findViewById(R.id.recycleView);

        recycleManager = new LinearLayoutManager(getContext());
        mRregionAdapter = new RegionAdapter(getContext());
        recycleView.setLayoutManager(recycleManager);
        recycleView.setAdapter(mRregionAdapter);
        mRregionAdapter.setSelectColor(mSelectColor);
    }

    private void bindListeners() {
        tvProvince.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvProvince.setTextColor(mSelectColor);
                bottomLineProvince.setVisibility(VISIBLE);

                tvCity.setTextColor(mNormalTextColor);
                bottomLineCity.setVisibility(GONE);

                tvArea.setTextColor(mNormalTextColor);
                bottomLineArea.setVisibility(GONE);

                mRregionAdapter.refreshData(provinceDatas, RegionAdapter.DATA_PROVINCE, checkProvince, checkCity, checkArea);
                int targetPosition = mRregionAdapter.getProvincePisition(checkProvince);
                scrollToPosition(targetPosition);
            }
        });

        tvCity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvProvince.setTextColor(mNormalTextColor);
                bottomLineProvince.setVisibility(GONE);

                tvCity.setTextColor(mSelectColor);
                bottomLineCity.setVisibility(VISIBLE);

                tvArea.setTextColor(mNormalTextColor);
                bottomLineArea.setVisibility(GONE);

                mRregionAdapter.refreshData(provinceDatas, RegionAdapter.DATA_CITY, checkProvince, checkCity, checkArea);
                int targetPosition = mRregionAdapter.getCityPosition(checkProvince, checkCity);
                scrollToPosition(targetPosition);
            }
        });

        tvArea.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvProvince.setTextColor(mNormalTextColor);
                bottomLineProvince.setVisibility(GONE);

                tvCity.setTextColor(mNormalTextColor);
                bottomLineCity.setVisibility(GONE);

                tvArea.setTextColor(mSelectColor);
                bottomLineArea.setVisibility(VISIBLE);

                mRregionAdapter.refreshData(provinceDatas, RegionAdapter.DATA_AREA, checkProvince, checkCity, checkArea);
                int targetPosition = mRregionAdapter.getAreaPosition(checkProvince, checkCity, checkArea);
                scrollToPosition(targetPosition);
            }
        });

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
                if (lastDataType == RegionAdapter.DATA_PROVINCE) {
                    newDataType = RegionAdapter.DATA_CITY;
                    checkProvince = checkedProvince;
                    checkCity = checkedCity;
                    checkArea = checkedArea;

                    tvProvince.setTextColor(mNormalTextColor);
                    tvProvince.setText(checkProvince);
                    bottomLineProvince.setVisibility(GONE);

                    tvCity.setTextColor(mSelectColor);
                    tvCity.setText("请选择");
                    bottomLineCity.setVisibility(VISIBLE);

                    tvArea.setText(checkArea);
                    bottomLineArea.setVisibility(GONE);

                    checkCity = "";
                    checkArea = "";
                    if (mSelectLevel == 1) {
                        onRpwItemClickListener.onRpwItemClick(replace(checkProvince), replace(checkCity), replace(checkArea));
                    }
                } else if (lastDataType == RegionAdapter.DATA_CITY) {
                    newDataType = RegionAdapter.DATA_AREA;
                    checkProvince = checkedProvince;
                    checkCity = checkedCity;
                    checkArea = checkedArea;

                    tvCity.setTextColor(mNormalTextColor);
                    tvCity.setText(checkCity);
                    bottomLineCity.setVisibility(GONE);

                    tvArea.setText("请选择");
                    tvArea.setTextColor(mSelectColor);
                    bottomLineArea.setVisibility(VISIBLE);

                    checkArea = "";
                    if (mSelectLevel == 2) {
                        onRpwItemClickListener.onRpwItemClick(replace(checkProvince), replace(checkCity), replace(checkArea));
                    }
                } else if (lastDataType == RegionAdapter.DATA_AREA) {
                    checkProvince = checkedProvince;
                    checkCity = checkedCity;
                    checkArea = checkedArea;

                    tvArea.setText(checkArea);
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

    private String replace(String text) {
        if (text.equals("请选择")) {
            return "";
        }
        return text;
    }

    public void setSelectLevel(int mSelectLevel) {
        this.mSelectLevel = mSelectLevel;
    }

    public void setColor(int color) {
        mSelectColor = color;
    }

    public void setBottomLineColor(int color) {
        mBottomLineColor = color;
    }

    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
        super.setBackgroundColor(color);
    }

    public void setNormalTextColor(int color) {
        mNormalTextColor = color;
    }

    public void setTitleTextColor(int color) {
        mTitleTextColor = color;
        if (tvTitle != null) {
            tvTitle.setTextColor(color);
        }
    }

    public void setDividerColor(int color) {
        mDividerColor = color;
        if (titleDivider != null) {
            titleDivider.setBackgroundColor(color);
        }
        if (headerDivider != null) {
            headerDivider.setBackgroundColor(color);
        }
        if (mRregionAdapter != null) {
            mRregionAdapter.setDividerColor(color);
        }
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

    public interface OnForkClickListener {
        void onForkClick();
    }

    public interface OnRpwItemClickListener {
        void onRpwItemClick(String selectedProvince, String selectedCity, String selectedArea);
    }
}
