package com.tenma.common.util.ws;


import com.google.gson.Gson;
import com.tenma.auth.util.logon.TenmaAuth;
import com.tenma.auth.util.logon.action.LoginTool;
import com.tenma.auth.util.web.AuthInfo;
import com.tenma.common.util.Constants;
import com.tenma.common.util.Params;
import com.tenma.ticket.TicketManager;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;

/**
 * Created by rizt.
 * Date    : 31-08-2015
 * Time    : 14:48
 * Copyright (c) 2015 Tenma Bright Sky
 */
public class LogonService {

    @GET
    @Path("/signin")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getTicket(@Context HttpServletRequest req, @QueryParam("user") String user, @QueryParam("password") String password) {
        com.tenma.model.core.TicketModel ticketModel = new com.tenma.model.core.TicketModel();
        try {
            String userId = URLDecoder.decode(user, "UTF-8");
            String pass = URLDecoder.decode(password, "UTF-8");
            ticketModel = signIn(req.getLocale(), req.getRemoteAddr(), userId, pass);
        } catch (Exception e) {
            Response.status(Response.Status.BAD_REQUEST);
        }
        String mapJson = new Gson().toJson(ticketModel, com.tenma.model.core.TicketModel.class);
        return Response.status(Response.Status.OK).entity(mapJson).build();
    }

    private com.tenma.model.core.TicketModel signIn(Locale locale, String remoteAddr, String user, String password) throws Exception {
        com.tenma.model.core.TicketModel ticketModel = new com.tenma.model.core.TicketModel();
        System.out.println("TicketService.getTicket");
        System.out.println("TicketService.getAuthLogin 1 ");
        String authenticationSource = Params.getInstance(locale).getProperty(TenmaAuth.SYSTEM_AUTHENTICATION_SOURCE);
        authenticationSource = TenmaAuth.AUTHENTICATION_BY_DATABASE;
        LoginTool loginTool = new LoginTool(authenticationSource, locale, remoteAddr);
        AuthInfo authInfo = loginTool.isAuthenticated(user, password);
        System.out.println("TicketService.getAuthLogin 2 ");
//            System.out.println("TicketService.getAuthLogin 3 ");
        if (authInfo != null) {
            System.out.println("authInfo.getUserStatus() = " + authInfo.getUserStatus());
            ticketModel = TicketManager.getInstance().getNewTicket(authInfo.getClientAuthUserModel().getMemberId());
            System.out.println("\n\n\n\nticketId Web APPLICATIOn= " + ticketModel.getTicketId());
        } else {
            ticketModel.setErrorCode(Constants.REST_RESPONSE_CODE.INVALID_CREDENTIAL.getValue());
        }
        return ticketModel;
    }

    public com.tenma.model.core.TicketModel signInDirect(Locale locale, String remoteAddr, String user, String password) throws Exception {
        return signIn(locale, remoteAddr, user, password);
    }

    @GET
    @Path("/renewticket")
    @Consumes("application/json")
    @Produces("application/json")
    public Response renewTicket(@Context HttpServletRequest req, @QueryParam("ticketId") String ticketId, @QueryParam("memberId") Integer memberId) throws UnsupportedEncodingException {
        String ticket = URLDecoder.decode(ticketId, "UTF-8");
        String mapJson;
        com.tenma.model.core.TicketModel ticketmodel = new com.tenma.model.core.TicketModel();
//        HashMap mapResult = new HashMap();
        System.out.println("TicketService.getTicket");
        try {
            System.out.println("TicketService.getAuthLogin 1 ");
            if (TicketManager.getInstance().IsValid(ticket, memberId)) {
//                ConcurrentHashMap newTicketId = TicketManager.getInstance().renewTicket(ticket, Integer.valueOf(memberId));
                ticketmodel = TicketManager.getInstance().renewTicket(ticket, Integer.valueOf(memberId));
                System.out.println("newTicketId = " + ticketmodel.getTicketId() + "\n\n\n\n");
//                model = (TicketModel) newTicketId.get(ticket);
//                System.out.println("model.getTicketId() = " + model.getTicketId());
            } else {
                ticketmodel.setErrorCode(Constants.REST_RESPONSE_CODE.TICKET_NOT_VALID.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ticketmodel.setErrorCode(Constants.REST_RESPONSE_CODE.TICKET_NOT_VALID.getValue());
        }
        mapJson = new Gson().toJson(ticketmodel);
        return Response.status(Response.Status.OK).entity(mapJson).build();
    }


}
