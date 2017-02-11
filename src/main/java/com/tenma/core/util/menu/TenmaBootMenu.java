package com.tenma.core.util.menu;

import com.tenma.core.bean.menu.MenuGroupHelper;
import com.tenma.core.bean.menu.MenuListHelper;
import com.tenma.model.core.MenuGroupModel;
import com.tenma.model.core.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ndwijaya on 6/27/2016.
 */
public class TenmaBootMenu {
    protected Logger logger = LoggerFactory.getLogger(TenmaBootMenu.class);
    ;

    public TenmaBootMenu() {
        initSuperAdmin();
        initMenuCheck();
    }

    public static void initmenu() {
        new TenmaBootMenu();
    }

    private void initSuperAdmin() {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(TenmaSuperAdmin.class));

        for (BeanDefinition bd : scanner.findCandidateComponents("share.tenma")) {
            System.out.println(bd.getBeanClassName());
            try {
                Class c = Class.forName(bd.getBeanClassName());
                TenmaSuperAdmin tenmaMenu = (TenmaSuperAdmin) c.getAnnotation(TenmaSuperAdmin.class);
                if (tenmaMenu != null) {
                    SuperAdmin.getInstance().setSuperAdminGroup(tenmaMenu.superAdmin());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // checking menu if the menu already in M_MENU
    protected void initMenuCheck() {
        // if menu already in M_MENU do update for autoupdate
        // if menu not in M_MENU do insert for the menu
        List lsDB = MenuListHelper.use().getListRenderer();
        List lsJVM = loadAllMenu();
        List lsGroupDelta = doGroupDBCheck(lsDB, lsJVM); // return menuGroup not exist
        List lsGroupUpdate = doGroupDBCheck(lsDB, lsJVM); // return menuGroup not exist
        doGroupNew(lsGroupDelta); // no group update - must be manually added.
        doGroupUpdate(lsGroupUpdate); // no group update - must be manually added.

        List lsMenuDelta = doMenuDBExistCheck(lsDB, lsJVM); // return menu not exist
        List lsMenuUpdate = doMenuDBUpdateCheck(lsDB, lsJVM); // return menuGroup not exist
        doMenuInsert(lsMenuDelta);
        doMenuUpdate(lsMenuUpdate, lsGroupDelta);
    }

    private void doGroupUpdate(List ls) {
        if (ls.size() > 0) {
            logger.debug("******************* REQUIRED GROUPID IN TABLE core.\"M_MENU_GRP\"");
        }

        ls.stream().forEach(e -> {
            MenuGroupModel mgm = (MenuGroupModel) e;
            if (mgm.isAutoUpdate()) {
                try {
                    mgm.setCreatedBy("SYSTEM");
                    mgm.setUpdatedBy("SYSTEM");
                    mgm.setCreatedFrom("SYSTEM");
                    mgm.setUpdatedFrom("SYSTEM");
                    mgm.setCreatedDate(Calendar.getInstance().getTime());
                    mgm.setUpdatedDate(Calendar.getInstance().getTime());
                    int rs = MenuGroupHelper.use().insertNewMenuGroup(mgm);
                    if (rs > 0) logger.info("Group " + mgm.getMenuGrpName() + " Added ");
                } catch (Exception e1) {
                    logger.error("FAIL ADD GROUP : " + mgm.getMenuGrpName() + e1.getMessage());
                }
            } else {
                logger.debug("Add - \"autoUpdate = true\", Group not exist in DB = " + mgm.getMenuGrpId() + " / " + mgm.getMenuGrpName());
            }
        });
    }

    private void doGroupNew(List ls) {
        if (ls.size() > 0) {
            logger.debug("******************* REQUIRED GROUPID IN TABLE core.\"M_MENU_GRP\"");
        }

        ls.stream().forEach(e -> {
            MenuGroupModel mgm = (MenuGroupModel) e;
            if (mgm.isAutoUpdate()) {
                try {
                    mgm.setCreatedBy("SYSTEM");
                    mgm.setUpdatedBy("SYSTEM");
                    mgm.setCreatedFrom("SYSTEM");
                    mgm.setUpdatedFrom("SYSTEM");
                    mgm.setCreatedDate(Calendar.getInstance().getTime());
                    mgm.setUpdatedDate(Calendar.getInstance().getTime());
                    int rs = MenuGroupHelper.use().insertNewMenuGroup(mgm);
                    if (rs > 0) logger.info("Group " + mgm.getMenuGrpName() + " Added ");
                } catch (Exception e1) {
                    logger.error("FAIL ADD GROUP : " + mgm.getMenuGrpName() + e1.getMessage());
                }
            } else {
                logger.debug("Add - \"autoUpdate = true\", Group not exist in DB = " + mgm.getMenuGrpId() + " / " + mgm.getMenuGrpName());
            }
        });
    }

    private void doMenuInsert(List ls) {
        logger.info("INSERT INTO core.M-MENU");
        MenuListHelper helper = MenuListHelper.use();

        ls.stream().forEach(e -> {
            MenuModel mm = (MenuModel) e;
            try {
                mm.setCreatedBy("SYSTEM");
                mm.setUpdatedBy("SYSTEM");
                mm.setCreatedFrom("SYSTEM");
                mm.setUpdatedFrom("SYSTEM");
                mm.setCreatedDate(Calendar.getInstance().getTime());
                mm.setUpdatedDate(Calendar.getInstance().getTime());
                int rs = helper.insertNewMenuList(mm);
                if (rs > 0) logger.info("Insert " + mm.getMenuName());
            } catch (Exception e1) {
                logger.info("** FAIL Insert -> " + mm.getMenuName() + " -> " + e1.getMessage());
            }
        });

    }

    private void doMenuUpdate(List lsUpdate, List lsDelta) {
        MenuListHelper helper = MenuListHelper.use();
        lsUpdate.stream().forEach(e -> {
            MenuModel mm = (MenuModel) e;
            if (!lsDelta.stream().filter(x -> ((MenuModel) x).getMenuId().equals(mm.getMenuId())).findFirst().isPresent()) {
                try {
                    mm.setCreatedBy("SYSTEM");
                    mm.setUpdatedBy("SYSTEM");
                    mm.setCreatedFrom("SYSTEM");
                    mm.setUpdatedFrom("SYSTEM");
                    mm.setCreatedDate(Calendar.getInstance().getTime());
                    mm.setUpdatedDate(Calendar.getInstance().getTime());
                    int rs = helper.updateMenuList(mm);
                    if (rs > 0) logger.info("Updated " + mm.getMenuName());
                } catch (Exception e1) {
                    logger.info("** FAIL update -> " + mm.getMenuName() + " -> " + e1.getMessage());
                }
            }
        });
    }

    private List doGroupDBUpdateCheck(List lsDb, List lsJVM) {
        List ls = new ArrayList<>();
        lsJVM.stream().forEach(e -> {
            MenuGroupMenuItem menuGroupMenuItem = (MenuGroupMenuItem) e;

            // if the group on JVM  exist on DB & autoupdate than add to the list
            if (menuGroupMenuItem.getMenuGroupModel().isAutoUpdate() && lsDb.stream()
                    .filter(x -> ((MenuModel) x).getMenuGrpId().equals(menuGroupMenuItem.getMenuGroupModel().getMenuGrpId()))
                    .findFirst().isPresent()) {
                ls.add(menuGroupMenuItem.getMenuGroupModel());
            }
        });
        return ls;
    }

    private List doGroupDBCheck(List lsDb, List lsJVM) {
        List ls = new ArrayList<>();
        lsJVM.stream().forEach(e -> {
            MenuGroupMenuItem menuGroupMenuItem = (MenuGroupMenuItem) e;

            // if the group on JVM is NOT exist on DB than add to the list
            if (!lsDb.stream() // NOT
                    .filter(x -> ((MenuModel) x).getMenuGrpId().equals(menuGroupMenuItem.getMenuGroupModel().getMenuGrpId()))
                    .findFirst().isPresent()) {

                ls.add(menuGroupMenuItem.getMenuGroupModel());
            }
        });
        return ls;
    }

    private List doMenuDBUpdateCheck(List lsDb, List lsJVM) {
        List ls = new ArrayList<>();
        lsJVM.stream().forEach(e -> {
                    MenuGroupMenuItem menuGroupMenuItem = (MenuGroupMenuItem) e;
                    // if the menu on JVM is exist on DB and the menu is autoupdate than add to the list
                    if (lsDb.stream()
                            .filter(x -> ((MenuModel) x).getMenuId().equals(menuGroupMenuItem.getMenuModel().getMenuId()))
                            .findFirst().isPresent()) {
                        if (menuGroupMenuItem.getMenuModel().isAutoUpdate()) {
                            ls.add(menuGroupMenuItem.getMenuModel());
                        }
                    }
                }

        );
        return ls;
    }

    private List doMenuDBExistCheck(List lsDb, List lsJVM) {
        List ls = new ArrayList<>();
        lsJVM.stream().forEach(e -> {
            MenuGroupMenuItem menuGroupMenuItem = (MenuGroupMenuItem) e;
            // if the menu on JVM is NOT exist on DB than add to the list
            if (!lsDb.stream() // NOT
                    .filter(x -> ((MenuModel) x).getMenuId().equals(menuGroupMenuItem.getMenuModel().getMenuId()))
                    .findFirst().isPresent()) {
                ls.add(menuGroupMenuItem.getMenuModel());
            }
        });
        return ls;
    }

    private List loadAllMenu() {
        List ls = new ArrayList<>();

        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(TenmaMenu.class));

        for (BeanDefinition bd : scanner.findCandidateComponents("share.tenma")) {
            System.out.println(bd.getBeanClassName());
//            System.out.println(bd.getOriginatingBeanDefinition());
            try {
                Class c = Class.forName(bd.getBeanClassName());
                TenmaMenu tenmaMenu = (TenmaMenu) c.getAnnotation(TenmaMenu.class);
                TenmaGroupMenu tenmaGroupMenu = (TenmaGroupMenu) c.getAnnotation(TenmaGroupMenu.class);
                if (tenmaMenu != null && tenmaGroupMenu != null) {
                    int groupId = tenmaGroupMenu.groupId();
                    String groupName = tenmaGroupMenu.groupName();
                    String groupDesc = tenmaGroupMenu.groupDesc();
                    boolean groupUpdate = tenmaGroupMenu.autoUpdate();
                    MenuGroupModel groupModel = new MenuGroupModel();
                    groupModel.setMenuGrpId(groupId);
                    groupModel.setMenuGrpName(groupName);
                    groupModel.setMenuGrpDesc(groupDesc);
                    groupModel.setAutoUpdate(groupUpdate);

                    int menuId = tenmaMenu.menuId();
                    int menuOrder = tenmaMenu.order();
                    String menuName = tenmaMenu.menuName();
                    String menuImg = tenmaMenu.menuImage();
                    boolean menuUpdate = tenmaMenu.autoUpdate();

                    MenuModel m = new MenuModel();
                    m.setMenuId(menuId);
                    m.setMenuGrpId(groupId);
                    m.setMenuAction(bd.getBeanClassName());
                    m.setMenuName(menuName);
                    m.setMenuImage(menuImg);
                    m.setAutoUpdate(menuUpdate);
                    m.setMenuOrder(menuOrder);

                    MenuGroupMenuItem mm = new MenuGroupMenuItem(m, groupModel);
                    ls.add(mm);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (ls.size() > 0) logger.info("Founded " + ls.size() + " annotated menu.");

//
        return ls;
    }

    class MenuGroupMenuItem {
        private MenuModel menuModel;
        private MenuGroupModel menuGroupModel;

        public MenuGroupMenuItem(MenuModel menuModel, MenuGroupModel menuGroupModel) {
            this.menuModel = menuModel;
            this.menuGroupModel = menuGroupModel;
        }

        public MenuModel getMenuModel() {
            return menuModel;
        }

        public MenuGroupModel getMenuGroupModel() {
            return menuGroupModel;
        }
    }
}
