package com.tenma.fam.gui.main.account;

import com.tenma.common.TA;
import com.tenma.common.bean.audittrail.TenmaLog;
import com.tenma.common.gui.display.TenmaPanel;
import com.tenma.common.gui.factory.TenmaMasterFormModify;
import com.tenma.common.util.error.CrudCode;
import com.tenma.fam.bean.account.AccountGroupHelper;
import com.tenma.model.fam.AccountGroupModel;
import com.vaadin.data.util.BeanItem;

import java.util.Arrays;
import java.util.List;

/**
 * User: derry.irmansyah
 * Date: 8/29/12
 * Time: 1:51 PM
 */
public class AccountGroupModify extends TenmaMasterFormModify {
    private AccountGroupModel modifyModel;
    private AccountGroupHelper helper = new AccountGroupHelper();

    public AccountGroupModify(TenmaPanel container, int modifyMode) throws Exception {
        super(container, modifyMode);
        setTitle("accountGroup.title");
        String[] s;
        if (modifyMode == ADD_MODE) {
            modifyModel = new AccountGroupModel();
            s = new String[]{"accountGrpName", "accountGrpDesc", "accountGrpType"};
            modifyModel.setAccountGrpId("");
            modifyModel.setAccountGrpType(0);
            modifyModel.setAccountGrpName("");
            modifyModel.setAccountGrpDesc("");

        } else {
            modifyModel = (AccountGroupModel) ((TenmaPanel) container).getSelectedObject();
            AccountGroupModel tmp = new AccountGroupModel();
            s = new String[]{"accountGrpId", "accountGrpName", "accountGrpDesc", "accountGrpType"};
            tmp.setAccountGrpId(modifyModel.getAccountGrpId());
            tmp.setCommunityId(modifyModel.getCommunityId());
            modifyModel = helper.getAccountGroupDetail(tmp);
            if (modifyModel == null)
                throw new Exception(String.valueOf(CrudCode.READ));
        }
        BeanItem<AccountGroupModel> bitem = new BeanItem<AccountGroupModel>(modifyModel);
        List visibleField = Arrays.asList(s);

//        AccountGroupFormField formField = new AccountGroupFormField(param);
//        doPublishUI(bitem, visibleField, formField);
    }

    public void doCancel() {
        doBack2List();
    }

    public void doSave() {
        modifyModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        setAuditTrail(modifyModel);
        String lc = getLogger().openLog();
        int res = -1;
        try {
            if (ADD_MODE == getModifyMode())
                res = helper.insertNewAccountGroup(modifyModel);
            else
                res = helper.updateAccountGroup(modifyModel);

            if (res != 0) {
                if (ADD_MODE == getModifyMode()) {
                    getLogger().log(lc, TenmaLog.LOG_FOR_ADD, "Add AccountGroup | AccountGroupName = " + modifyModel.getAccountGrpName());
                } else {
                    getLogger().log(lc, TenmaLog.LOG_FOR_UPDATE, "Update AccountGroup | AccountGroupID = " + modifyModel.getAccountGrpId());
                }
            }
            AccountGroupList list = new AccountGroupList();
            TA.getCurrent().updateWorkingArea(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doBack2List() {
        AccountGroupList list = (AccountGroupList) getParentContainer();
        TA.getCurrent().updateWorkingArea(list);
        list.refreshTable();
    }
}
