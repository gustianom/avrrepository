package com.tenma.core.util.menu;

import java.lang.String;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ndwijaya on 6/27/2016.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //on class level
public @interface TenmaGroupMenu {
    int groupId();

    String groupName() default "";

    String groupDesc() default "";

    int groupOrder() default 0;

    int parentId() default 0;

    boolean autoUpdate() default false;

//    "MENU_GRP_ID" integer NOT NULL,
//    "PARENT_GRP_ID" integer,
//    "MENU_GRP_NAME" character varying(50) NOT NULL,
//    "MENU_GRP_DESC" character varying(50) NOT NULL,
//    "MENU_GRP_IMG" character varying(50),
//    "MENU_ORDER" smallint NOT NULL DEFAULT 0,
//    "CREATED_DATE" timestamp(6) with time zone NOT NULL,
//    "CREATED_BY" character(16) NOT NULL,
//    "CREATED_FROM" character(16) NOT NULL,
//    "UPDATED_DATE" timestamp(6) with time zone NOT NULL,
//    "UPDATED_BY" character(16) NOT NULL,
//    "UPDATED_FROM" character(16) NOT NULL,
//    "RECORD_STATUS" integer NOT NULL DEFAULT 0,
//    CONSTRAINT "M_MENU_GRP_pkey" PRIMARY KEY ("MENU_GRP_ID")
}
