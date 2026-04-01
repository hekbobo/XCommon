package com.yingjie.addressselector.inner;

/**
 * create by chenyingjie on 2020/6/10
 * desc
 */
public class SelectorFactory {

    private static ISelector iSelector = new SelectorImp();

    public SelectorFactory() {}

    public ISelector create() {
        return iSelector;
    }

    private static class SingletonHolder {
        private static final SelectorFactory instance = new SelectorFactory();
    }

    public static SelectorFactory get() {
        return SingletonHolder.instance;
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
