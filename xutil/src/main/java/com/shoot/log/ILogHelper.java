package com.shoot.common;

import android.content.Context;

public interface ILogHelper {
    public Context getContext();
    
    public String[] getExtendedLogClass();
    
    public String getNormalOutputPath();
    public String getLogFileName();

}
