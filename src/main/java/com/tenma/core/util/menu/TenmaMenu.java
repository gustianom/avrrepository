package com.tenma.core.util.menu;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ndwijaya on 2/20/2016.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //on class level
public @interface TenmaMenu {
    int menuId() default 0;

    int menuGroupId() default 0;

    String menuName() default "";

    String menuImage() default "";

    boolean autoUpdate() default false;

    int order() default 0;

    int maturity() default 0;


//    "MENU_ITEM_ID" integer NOT NULL,
//            "MENU_GRP_ID" integer,
//            "MENU_ITEM_NAME" character varying(50) NOT NULL,
//    "MENU_DESC" character varying(50) DEFAULT NULL::character varying,
//    "MENU_IMG" character varying(50),
//    "MENU_TYPE" integer, -- 0 -> SYSTEM, 1 -> CLIENT
//    "NEXT_MENU_ID" integer,
//            "MENU_ACTION" character varying(100),
//    "MENU_ORDER" smallint NOT NULL DEFAULT 0,
//            "MENU_FAV" smallint DEFAULT 0,
//            "MENU_FLAT" smallint NOT NULL DEFAULT 0,
//            "MENU_FLAT_GROUP" bit varying(30),
//    "CREATED_DATE" timestamp(6) with time zone NOT NULL,
//    "CREATED_BY" character(16) NOT NULL,
//    "CREATED_FROM" character(16) NOT NULL,
//    "UPDATED_DATE" timestamp(6) with time zone NOT NULL,
//    "UPDATED_BY" character(16) NOT NULL,
//    "UPDATED_FROM" character(16) NOT NULL,
//    "RECORD_STATUS" integer NOT NULL DEFAULT 0,
//    CONSTRAINT "M_MENU_pkey" PRIMARY KEY ("MENU_ITEM_ID"),
//    CONSTRAINT "M_MENU_MENU_GRP_ID_fkey" FOREIGN KEY ("MENU_GRP_ID")
//    REFERENCES core."M_MENU_GRP" ("MENU_GRP_ID") MATCH SIMPLE
//    ON UPDATE NO ACTION ON DELETE NO ACTION

}
