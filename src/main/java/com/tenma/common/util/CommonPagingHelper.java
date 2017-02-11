package com.tenma.common.util;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ndwijaya on 12/10/14.
 */
public interface CommonPagingHelper {
    public List getCustomListRenderer(HashMap mapList, boolean navigated);

    public int countTotalList(HashMap mapList) throws Exception;

}
