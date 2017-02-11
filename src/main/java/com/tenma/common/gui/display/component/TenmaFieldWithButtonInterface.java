package com.tenma.common.gui.display.component;

import com.tenma.common.util.CommonPaging;
import com.tenma.model.common.TenmaTableContainer;

import java.io.Serializable;

/**
 * Created by PT TENMA BRIGHT SKY
 * User: sigit
 * Date: 7/8/13
 * Time: 4:00 PM
 */
public interface TenmaFieldWithButtonInterface extends Serializable {

    public TenmaTableContainer container(int page, int maxPage, CommonPaging commonPaging);

    public int maxTotalSize();


}
