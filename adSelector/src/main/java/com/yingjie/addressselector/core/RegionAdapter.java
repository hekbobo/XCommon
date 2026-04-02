package com.yingjie.addressselector.core;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yingjie.addressselector.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen.yingjie on 2019/3/23
 */
public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.ViewHolder> {

    public static final int DATA_PROVINCE = 1;
    public static final int DATA_CITY = 2;
    public static final int DATA_AREA = 3;

    private List<RegionBean> provinceDatas;
    private final Context mContext;
    private int dataType;
    private int positionProvince;
    private int positionCity;
    private int positionArea;
    private final List<Boolean> isCheckedP;
    private final List<Boolean> isCheckedC;
    private final List<Boolean> isCheckedA;

    private String checkedProvince;
    private String checkedCity;
    private String checkedArea;

    private int mSelectColor = R.color.ff5000;
    private int mBackgroundColor = R.color.white;
    private int mNormalTextColor = R.color.v666666;
    private int mDividerColor = R.color.veeeeee;

    private OnItemCheckedListener onItemCheckedListener;

    public RegionAdapter(Context context) {
        mContext = context;
        isCheckedP = new ArrayList<>();
        isCheckedC = new ArrayList<>();
        isCheckedA = new ArrayList<>();
    }

    public void setSelectColor(int color) {
        mSelectColor = color;
    }

    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
    }

    public void setNormalTextColor(int color) {
        mNormalTextColor = color;
    }

    public void setDividerColor(int color) {
        mDividerColor = color;
    }

    public void refreshData(List<RegionBean> provinceDatas, int dataType, String checkedProvince, String checkedCity, String checkedArea) {
        this.provinceDatas = provinceDatas;
        this.dataType = dataType;
        this.checkedProvince = checkedProvince;
        this.positionProvince = getProvincePisition(checkedProvince);
        this.positionCity = getCityPosition(checkedProvince, checkedCity);
        this.positionArea = getAreaPosition(checkedProvince, checkedCity, checkedArea);

        if (dataType == DATA_PROVINCE) {
            isCheckedP.clear();
            if (provinceDatas == null) {
                return;
            }
            for (int i = 0; i < provinceDatas.size(); i++) {
                isCheckedP.add(false);
            }
            if (positionProvince != -1) {
                isCheckedP.set(positionProvince, true);
            }
        } else if (dataType == DATA_CITY) {
            isCheckedC.clear();
            if (provinceDatas == null ||
                    positionProvince == -1 ||
                    provinceDatas.get(positionProvince) == null ||
                    provinceDatas.get(positionProvince).citys == null) {
                return;
            }
            for (int i = 0; i < provinceDatas.get(positionProvince).citys.size(); i++) {
                isCheckedC.add(false);
            }
            if (positionCity != -1) {
                isCheckedC.set(positionCity, true);
            }
        } else if (dataType == DATA_AREA) {
            isCheckedA.clear();
            if (provinceDatas == null ||
                    positionProvince == -1 ||
                    positionCity == -1 ||
                    provinceDatas.get(positionProvince) == null ||
                    provinceDatas.get(positionProvince).citys == null ||
                    provinceDatas.get(positionProvince).citys.get(positionCity) == null ||
                    provinceDatas.get(positionProvince).citys.get(positionCity).areas == null) {
                return;
            }
            for (int i = 0; i < provinceDatas.get(positionProvince).citys.get(positionCity).areas.size(); i++) {
                isCheckedA.add(false);
            }
            if (positionArea != -1) {
                isCheckedA.set(positionArea, true);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_region_recycleview, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        List<Boolean> isChecked = new ArrayList<>();

        if (dataType == DATA_PROVINCE) {
            isChecked = isCheckedP;
            if (provinceDatas == null || provinceDatas.get(holder.getAdapterPosition()) == null) {
                return;
            }
            holder.tvName.setText(provinceDatas.get(holder.getAdapterPosition()).provinceName);
        } else if (dataType == DATA_CITY) {
            isChecked = isCheckedC;
            if (provinceDatas == null ||
                    positionProvince == -1 ||
                    provinceDatas.get(positionProvince) == null ||
                    provinceDatas.get(positionProvince).citys == null ||
                    provinceDatas.get(positionProvince).citys.get(holder.getAdapterPosition()) == null) {
                return;
            }
            holder.tvName.setText(provinceDatas.get(positionProvince).citys.get(holder.getAdapterPosition()).cityName);
        } else {
            isChecked = isCheckedA;
            if (provinceDatas == null ||
                    positionProvince == -1 ||
                    positionCity == -1 ||
                    provinceDatas.get(positionProvince) == null ||
                    provinceDatas.get(positionProvince).citys == null ||
                    provinceDatas.get(positionProvince).citys.get(positionCity) == null ||
                    provinceDatas.get(positionProvince).citys.get(positionCity).areas == null ||
                    provinceDatas.get(positionProvince).citys.get(positionCity).areas.get(holder.getAdapterPosition()) == null) {
                return;
            }
            holder.tvName.setText(provinceDatas.get(positionProvince).citys.get(positionCity).areas.get(holder.getAdapterPosition()).areaName);
        }

        holder.itemView.setBackgroundColor(mBackgroundColor);

        if (isChecked.get(holder.getAdapterPosition())) {
            holder.tvName.setTextColor(mSelectColor);
            holder.tvSelected.setVisibility(View.VISIBLE);
        } else {
            holder.tvName.setTextColor(mNormalTextColor);
            holder.tvSelected.setVisibility(View.GONE);
        }

        if (onItemCheckedListener != null) {
            final List<Boolean> finalIsChecked = isChecked;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < finalIsChecked.size(); i++) {
                        finalIsChecked.set(i, false);
                    }
                    finalIsChecked.set(holder.getAdapterPosition(), true);
                    notifyDataSetChanged();

                    if (dataType == DATA_PROVINCE) {
                        checkedProvince = holder.tvName.getText().toString();
                    } else if (dataType == DATA_CITY) {
                        checkedCity = holder.tvName.getText().toString();
                    } else {
                        checkedArea = holder.tvName.getText().toString();
                    }
                    onItemCheckedListener.onItemChecked(dataType, checkedProvince, checkedCity, checkedArea);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (dataType == DATA_PROVINCE) {
            if (provinceDatas != null) {
                size = provinceDatas.size();
            }
        } else if (dataType == DATA_CITY) {
            if (provinceDatas != null &&
                    positionProvince != -1 &&
                    provinceDatas.get(positionProvince) != null &&
                    provinceDatas.get(positionProvince).citys != null) {
                size = provinceDatas.get(positionProvince).citys.size();
            }
        } else {
            if (provinceDatas != null &&
                    positionProvince != -1 &&
                    positionCity != -1 &&
                    provinceDatas.get(positionProvince) != null &&
                    provinceDatas.get(positionProvince).citys != null &&
                    provinceDatas.get(positionProvince).citys.get(positionCity) != null &&
                    provinceDatas.get(positionProvince).citys.get(positionCity).areas != null) {
                size = provinceDatas.get(positionProvince).citys.get(positionCity).areas.size();
            }
        }
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvSelected;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSelected = itemView.findViewById(R.id.tvSelected);
        }
    }

    private String normalizeRegionName(String name) {
        if (TextUtils.isEmpty(name)) {
            return "";
        }
        String result = name.trim();
        result = result.replace("特别行政区", "");
        result = result.replace("地区", "");
        result = result.replace("自治州", "");
        result = result.replace("自治县", "");
        result = result.replace("自治区", "");
        result = result.replace("市", "");
        result = result.replace("区", "");
        result = result.replace("县", "");
        result = result.replace("盟", "");
        result = result.replace("州", "");
        return result;
    }

    public int getProvincePisition(String province) {
        if (provinceDatas != null && !TextUtils.isEmpty(province)) {
            String targetProvince = normalizeRegionName(province);
            for (int p = 0; p < provinceDatas.size(); p++) {
                if (normalizeRegionName(provinceDatas.get(p).provinceName).equals(targetProvince)) {
                    return p;
                }
            }
        }
        return -1;
    }

    public int getCityPosition(String province, String city) {
        if (provinceDatas != null && !TextUtils.isEmpty(province) && !TextUtils.isEmpty(city)) {
            int p = getProvincePisition(province);
            if (p == -1) {
                return -1;
            }
            String targetCity = normalizeRegionName(city);
            for (int c = 0; c < provinceDatas.get(p).citys.size(); c++) {
                if (normalizeRegionName(provinceDatas.get(p).citys.get(c).cityName).equals(targetCity)) {
                    return c;
                }
            }
        }
        return -1;
    }

    public int getAreaPosition(String province, String city, String area) {
        if (provinceDatas != null && !TextUtils.isEmpty(province) && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(area)) {
            int p = getProvincePisition(province);
            if (p == -1) {
                return -1;
            }
            int c = getCityPosition(province, city);
            if (c == -1) {
                return -1;
            }
            String targetArea = normalizeRegionName(area);
            for (int a = 0; a < provinceDatas.get(p).citys.get(c).areas.size(); a++) {
                if (normalizeRegionName(provinceDatas.get(p).citys.get(c).areas.get(a).areaName).equals(targetArea)) {
                    return a;
                }
            }
        }
        return -1;
    }

    public interface OnItemCheckedListener {
        void onItemChecked(int lastDataType, String checkedProvince, String checkedCity, String checkedArea);
    }

    public void setOnItemCheckedListener(OnItemCheckedListener onItemCheckedListener) {
        this.onItemCheckedListener = onItemCheckedListener;
    }
}
