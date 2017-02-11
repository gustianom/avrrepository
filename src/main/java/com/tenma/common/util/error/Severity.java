package com.tenma.common.util.error;

/**
 * Created by ndwijaya on 8/18/15.
 */
public interface Severity {
    public static final int SEVERITY40 = 40;  // Core-System Error - implication to multi module
    public static final int SEVERITY30 = 30;  // Application error only localized to spesific module/function
    public static final int SEVERITY20 = 20;  // data not set - require setup
    public static final int SEVERITY10 = 10;  // warning or info only
}
