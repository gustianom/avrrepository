package com.tenma.core.util.menu;

/**
 * Created by ndwijaya on 6/29/2016.
 */
public class SuperAdmin {
    private static SuperAdmin instance;
    private int superAdminGroup;

    public SuperAdmin() {
    }

    public static SuperAdmin getInstance() {
        if (instance == null) {
            instance = new SuperAdmin();
        }
        return instance;
    }

    public static void setInstance(SuperAdmin instance) {
        SuperAdmin.instance = instance;
    }

    public int getSuperAdminGroup() {
        return superAdminGroup;
    }

    public void setSuperAdminGroup(int superAdminGroup) {
        this.superAdminGroup = superAdminGroup;
    }
}
