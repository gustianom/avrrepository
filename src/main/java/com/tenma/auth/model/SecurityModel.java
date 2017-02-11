package com.tenma.auth.model;

import com.tenma.auth.util.Converter;

import java.util.HashMap;

/**
 * Created by gustianom on 27/12/14.
 */
public class SecurityModel extends AuditModel {
    private Integer authenticationCode;
    private Integer menuItemId;
    private boolean createAccess;
    private boolean readAccess;
    private boolean updateAccess;
    private boolean deleteAccess;
    private boolean printAccess;

    private HashMap customAuthenticationCode;

    public Integer getAuthenticationCode() {
        return authenticationCode;
    }

    public void setAuthenticationCode(Integer authenticationCode) {
        if (authenticationCode == null) authenticationCode = 0;
        this.authenticationCode = authenticationCode;

        final boolean[] authorizationCode = Converter.parseAuthorizationCode(authenticationCode);
        createAccess = authorizationCode[0];
        readAccess = authorizationCode[1];
        updateAccess = authorizationCode[2];
        deleteAccess = authorizationCode[3];
        printAccess = authorizationCode[4];
    }

    public Integer getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public boolean isCreateAccess() {
        return createAccess;
    }

    public void setCreateAccess(boolean createAccess) {
        this.createAccess = createAccess;
    }

    public boolean isReadAccess() {
        return readAccess;
    }

    public void setReadAccess(boolean readAccess) {
        this.readAccess = readAccess;
    }

    public boolean isUpdateAccess() {
        return updateAccess;
    }

    public void setUpdateAccess(boolean updateAccess) {
        this.updateAccess = updateAccess;
    }

    public boolean isDeleteAccess() {
        return deleteAccess;
    }

    public void setDeleteAccess(boolean deleteAccess) {
        this.deleteAccess = deleteAccess;
    }

    public boolean isPrintAccess() {
        return printAccess;
    }

    public void setPrintAccess(boolean printAccess) {
        this.printAccess = printAccess;
    }

    public HashMap getCustomAuthenticationCode() {
        return customAuthenticationCode;
    }

    public void setCustomAuthenticationCode(HashMap customAuthenticationCode) {
        this.customAuthenticationCode = customAuthenticationCode;
    }
}

