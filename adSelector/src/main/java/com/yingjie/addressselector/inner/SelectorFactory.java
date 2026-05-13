package com.yingjie.addressselector.inner;

/**
 * create by chenyingjie on 2020/6/10
 * desc 每次展示地址选择器应使用独立实例，避免共享 {@link SelectorImp} 导致样式或状态串扰。
 */
public class SelectorFactory {

    private final ISelector iSelector = new SelectorImp();

    public SelectorFactory() {
    }

    public ISelector create() {
        return iSelector;
    }

    public SelectorFactory setColor(int color) {
        iSelector.setColor(color);
        return this;
    }

    public SelectorFactory setBottomLineColor(int color) {
        iSelector.setBottomLineColor(color);
        return this;
    }

    public SelectorFactory setSelectLevel(int level) {
        iSelector.setSelectLevel(level);
        return this;
    }

    public SelectorFactory setBackgroundColor(int color) {
        iSelector.setBackgroundColor(color);
        return this;
    }

    public SelectorFactory setNormalTextColor(int color) {
        iSelector.setNormalTextColor(color);
        return this;
    }

    public SelectorFactory setTitleTextColor(int color) {
        iSelector.setTitleTextColor(color);
        return this;
    }

    public SelectorFactory setDividerColor(int color) {
        iSelector.setDividerColor(color);
        return this;
    }
}
