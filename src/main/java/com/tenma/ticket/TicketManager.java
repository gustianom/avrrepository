package com.tenma.ticket;

import com.tenma.auth.model.UserModel;
import com.tenma.common.bean.user.UserHelper;
import com.tenma.common.util.TenmaConverter;
import com.tenma.core.bean.ticket.TicketHelper;
import com.tenma.model.core.TicketModel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rizt.
 * Date    : 19-09-2015
 * Time    : 10:33
 * Copyright (c) 2015 Tenma Bright Sky
 */

//TODO URGENT
//    1. define build in timeout thread
//    2. make authlog persistance table that log when the ticket is given, who is the requester(memberId), valid time, grace period info
//    3. make ticket manager smart weakup with persistance load from auth log (note: only reload active ticket for both valid and grace period valid)
//

public class TicketManager {
    private static TicketManager instance;
    private ConcurrentHashMap ticketMap;
    private TicketModel model;
    private Object object;
//    private Boolean isValidate;

    private TicketManager() {
        ticketMap = new ConcurrentHashMap();
//        isValidate = false;
    }

    public static synchronized TicketManager getInstance() {
        if (instance == null) {
            instance = new TicketManager();
        }
        return instance;
    }


    public Boolean IsValid(String ticketId) {
        boolean isValidate = false;
        TicketHelper helper = new TicketHelper();
        TicketModel mdl = new TicketModel();
        mdl.setTicketId(ticketId);
        mdl = helper.getTicketDetail(mdl);
        if (mdl != null) {
            isValidate = true;
        }
//        if (model!=null) {
//            if (model.getValidTime()>0) isValidate = true;
//        }

        System.out.println("isValidate = " + isValidate);
        return isValidate;
    }


    public Boolean IsValid(String ticketId, Integer memberId) {
        boolean isValidate = false;
//        model = (TicketModel) ticketMap.get(ticketId);
        TicketHelper helper = new TicketHelper();
        TicketModel mdl = new TicketModel();
        mdl.setMemberId(memberId);
        mdl.setTicketId(ticketId);
        mdl = helper.getTicketDetail(mdl);
        if (mdl != null) {
            if (mdl.getMemberId().equals(memberId)) {
                isValidate = true;
            }
        }

//        if (model != null) {          // Check from HashMap
//            if (model.getMemberId().equals(memberId) && model.getValidTime() > 0) {
//                isValidate = true;
//            }
//        }
        System.out.println("isValidate = " + isValidate);
        return isValidate;
    }

    //    public synchronized ConcurrentHashMap getNewTicket(Integer memberId) {
    public synchronized TicketModel getNewTicket(Integer memberId) {
        String ticketId = TenmaConverter.generateRandomPassword(false, 20);
        UserHelper userHelper = new UserHelper();
        UserModel userModel = new UserModel();
        userModel.setMemberId(memberId);
        userModel = userHelper.getUserDetail(userModel);
        model = new TicketModel();
        if (userModel != null) {
            model.setTicketId(ticketId);
            model.setCommunityId(userModel.getDefCommunityId());
            model.setMemberId(memberId);
            model.setValidTime(30);
            ticketMap.put(ticketId, model);
            TicketHelper helper = new TicketHelper();
            try {
                int res = helper.insertNewTicket(model);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setObject(ticketId);
//        ticketMap.put(ticketId, model); // don't put NULL model instend of valid ticket !!
//        TicketModel mdl = (TicketModel) ticketMap.get(ticketId); // WHY YOU GET AGAIN SOMETHING THAT YOU ALREADY HAVE ??
        System.out.println("New Ticket ================ " + ticketId);
        System.out.println("model.getCommunityId() = " + model.getCommunityId());
        System.out.println("model.getMemberId() = " + model.getMemberId());
        System.out.println("mdl.getValidTime() = " + model.getValidTime());
        return model;
    }

    public TicketModel renewTicket(String ticketId, Integer memberId) {
        System.out.println("Old Ticket ================ " + ticketId);

        TicketModel ticketModel = (TicketModel) ticketMap.get(ticketId);
        if (ticketModel != null) {
            if (ticketModel.getMemberId().equals(memberId)) {
                ticketModel.setValidTime(30);
            }
        }
//        Iterator<String> it = ticketMap.keySet().iterator();
//        while (it.hasNext()) {
//            String key = it.next();
//            if (key.equals(ticketId)) {
//                model = (TicketModel) ticketMap.get(ticketId);
//                model.setValidTime(30);
//                ticketMap.replace(ticketId, model);
//                System.out.println("model = " + model);
//            } else {
//                ticketMap.put("status", "Ticket Not Found");
//            }
//
//            System.out.println("reNew Ticket ================ " + ticketMap);
//        }
        return ticketModel;
    }

    public String getCommunityId(String ticketId) {
        String communityId = "-1";
        Boolean isValid = IsValid(ticketId);
        if (isValid) {
            TicketHelper helper = new TicketHelper();
            TicketModel mdl = new TicketModel();
            mdl.setTicketId(ticketId);
            mdl = helper.getTicketDetail(mdl);
            if (mdl != null) {
                communityId = mdl.getCommunityId();
            }
        }
        return communityId;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
