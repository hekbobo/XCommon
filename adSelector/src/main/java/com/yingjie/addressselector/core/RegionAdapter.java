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
        this.checkedProvince = checkedProvince != null ? checkedProvince : "";
        this.checkedCity = checkedCity != null ? checkedCity : "";
        this.checkedArea = checkedArea != null ? checkedArea : "";
        this.positionProvince = getProvincePisition(this.checkedProvince);
        this.positionCity = getCityPosition(this.checkedProvince, this.checkedCity);
        this.positionArea = getAreaPosition(this.checkedProvince, this.checkedCity, this.checkedArea);

        if (dataType == DATA_PROVINCE) {
            isCheckedP.clear();
            isCheckedC.clear();
            isCheckedA.clear();
            if (provinceDatas == null) {
                notifyDataSetChanged();
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
            isCheckedA.clear();
            if (provinceDatas == null ||
                    positionProvince == -1 ||
                    provinceDatas.get(positionProvince) == null ||
                    provinceDatas.get(positionProvince).citys == null) {
                notifyDataSetChanged();
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
                notifyDataSetChanged();
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
        List<Boolean> isChecked;

        if (dataType == DATA_PROVINCE) {
            isChecked = isCheckedP;
            if (provinceDatas == null || position < 0 || position >= provinceDatas.size() || provinceDatas.get(position) == null) {
                holder.itemView.setOnClickListener(null);
                return;
            }
            holder.tvName.setText(provinceDatas.get(position).provinceName);
        } else if (dataType == DATA_CITY) {
            isChecked = isCheckedC;
            if (provinceDatas == null ||
                    positionProvince == -1 ||
                    provinceDatas.get(positionProvince) == null ||
                    provinceDatas.get(positionProvince).citys == null ||
                    position < 0 ||
                    position >= provinceDatas.get(positionProvince).citys.size() ||
                    provinceDatas.get(positionProvince).citys.get(position) == null) {
                holder.itemView.setOnClickListener(null);
                return;
            }
            holder.tvName.setText(provinceDatas.get(positionProvince).citys.get(position).cityName);
        } else {
            isChecked = isCheckedA;
            if (provinceDatas == null ||
                    positionProvince == -1 ||
                    positionCity == -1 ||
                    provinceDatas.get(positionProvince) == null ||
                    provinceDatas.get(positionProvince).citys == null ||
                    provinceDatas.get(positionProvince).citys.get(positionCity) == null ||
                    provinceDatas.get(positionProvince).citys.get(positionCity).areas == null ||
                    position < 0 ||
                    position >= provinceDatas.get(positionProvince).citys.get(positionCity).areas.size() ||
                    provinceDatas.get(positionProvince).citys.get(positionCity).areas.get(position) == null) {
                holder.itemView.setOnClickListener(null);
                return;
            }
            holder.tvName.setText(provinceDatas.get(positionProvince).citys.get(positionCity).areas.get(position).areaName);
        }

        holder.itemView.setBackgroundColor(mBackgroundColor);

        if (isChecked.size() > position && isChecked.get(position)) {
            holder.tvName.setTextColor(mSelectColor);
            holder.tvSelected.setVisibility(View.VISIBLE);
        } else {
            holder.tvName.setTextColor(mNormalTextColor);
            holder.tvSelected.setVisibility(View.GONE);
        }

        if (onItemCheckedListener != null) {
            final List<Boolean> finalIsChecked = isChecked;
            final int clickPosition = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 勿用 holder.getAdapterPosition()：notifyDataSetChanged 后常见为 NO_POSITION(-1)，
                    // 会与 finalIsChecked.size() 比较误判（-1 >= 0），导致整列点击失效。
                    if (clickPosition < 0 || clickPosition >= finalIsChecked.size()) {
                        return;
                    }
                    for (int i = 0; i < finalIsChecked.size(); i++) {
                        finalIsChecked.set(i, false);
                    }
                    finalIsChecked.set(clickPosition, true);
                    notifyDataSetChanged();

                    if (dataType == DATA_PROVINCE) {
                        if (provinceDatas != null && clickPosition < provinceDatas.size()
                                && provinceDatas.get(clickPosition) != null) {
                            checkedProvince = provinceDatas.get(clickPosition).provinceName;
                        }
                    } else if (dataType == DATA_CITY) {
                        if (provinceDatas != null && positionProvince != -1
                                && provinceDatas.get(positionProvince) != null
                                && provinceDatas.get(positionProvince).citys != null
                                && clickPosition < provinceDatas.get(positionProvince).citys.size()
                                && provinceDatas.get(positionProvince).citys.get(clickPosition) != null) {
                            checkedCity = provinceDatas.get(positionProvince).citys.get(clickPosition).cityName;
                        }
                    } else {
                        if (provinceDatas != null && positionProvince != -1 && positionCity != -1
                                && provinceDatas.get(positionProvince) != null
                                && provinceDatas.get(positionProvince).citys != null
                                && provinceDatas.get(positionProvince).citys.get(positionCity) != null
                                && provinceDatas.get(positionProvince).citys.get(positionCity).areas != null
                                && clickPosition < provinceDatas.get(positionProvince).citys.get(positionCity).areas.size()
                                && provinceDatas.get(positionProvince).citys.get(positionCity).areas.get(clickPosition) != null) {
                            checkedArea = provinceDatas.get(positionProvince).citys.get(positionCity).areas.get(clickPosition).areaName;
                        }
                    }
                    onItemCheckedListener.onItemChecked(dataType, checkedProvince, checkedCity, checkedArea);
                }
            });
        } else {
            holder.itemView.setOnClickListener(null);
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
