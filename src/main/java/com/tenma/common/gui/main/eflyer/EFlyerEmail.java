package com.tenma.common.gui.main.eflyer;

import com.google.gson.Gson;
import com.tenma.common.TA;
import com.tenma.common.bean.eflyer.EflyerHelper;
import com.tenma.common.util.Constants;
import com.tenma.model.common.EflyerModel;
import com.tenma.model.common.EflyerSentModel;
import com.tenma.model.email.EmailModel;
import com.tenma.util.email.EmailService;
import com.vaadin.server.VaadinService;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PT TENMA BRIGHT SKY
 * User    : Rom_Bar
 * Date    : 3/27/2015
 * Time    : 10:50 AM
 * Project : udw
 * Package : share.tenma.common.gui.main.eflyer
 */
public class EFlyerEmail {
    private final String domainName;

    public EFlyerEmail() {

        HttpServletRequest request = (HttpServletRequest) VaadinService.getCurrentRequest();

        domainName = new StringBuffer()
                .append(request.getScheme()).append("://")
                .append(request.getServerName())
                .append(request.getServerPort() == 80 ? "" : ":")
                .append(request.getServerPort() == 80 ? "" : request.getServerPort())
                .append(request.getContextPath())
                .toString();

    }

    public void doSendEmail(String approverEmailAddress, Boolean asLink, EflyerModel model) {
        System.out.println("EflyerList.doSendEmail");
        preparingEmail(approverEmailAddress, asLink, model);
    }

    public String getLinkEFlyer(EflyerModel model) {
        System.out.println("EFlyerEmail.getLinkEFlyer");
        return generateLink(model, null);
    }


    public String getLinkEFlyer(EflyerModel eflyerModel, EflyerSentModel modifyModel) {
        System.out.println("EFlyerEmail.getLinkEFlyer");
        return generateLink(eflyerModel, modifyModel);
    }

    public String getBodyEFlyer(EflyerModel model) {
        System.out.println("EFlyerEmail.getBodyEmail");
        return generateBodyEmail(model);
    }

    private void preparingEmail(String approverEmailAddress, Boolean asLink, EflyerModel model) {
        try {
            EmailModel emailModel = new EmailModel();
            emailModel.setEmailTo(approverEmailAddress);
            emailModel.setBodyMessage(asLink ? generateLink(model, null) : generateBodyEmail(model));
            emailModel.setSubjectMessage("E-FLYER UNITED DANCE WORKS");
            emailModel.setEmailFrom("system");
            try {
                EmailService.sendMail(emailModel, EmailService.TO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateLink(EflyerModel model, EflyerSentModel modifyModel) {
        StringBuffer buf = new StringBuffer()
                .append(TA.getCurrent().getCommunityModel().getCommunityId())
                .append("|")
                .append(model.getEflyerId());
        if (modifyModel != null) {
            buf.append("|").append(modifyModel.getSendId());
        }
        StringBuffer linkGenerated = null;
        try {
            linkGenerated = new StringBuffer()
                    .append(domainName)
                    .append("/")
                    .append(Constants.EFLYER_KEY)
                    .append("?code=")
                    .append(URLEncoder.encode(buf.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        StringBuffer sbf = new StringBuffer("<a href=\"")
//                .append(linkGenerated.toString())
//                .append("\">")
//                .append("CLICK THIS TO OPEN E-FLYER")
//                .append("</a>");

        return linkGenerated.toString();
    }

    private String generateBodyEmail(EflyerModel model) {
        Gson gson = new Gson();
        EflyerModel modifyModel = new EflyerModel();
        modifyModel.setCommunityId(TA.getCurrent().getCommunityModel().getCommunityId());
        modifyModel.setEflyerId(model.getEflyerId());
        EflyerHelper helper = new EflyerHelper();
        modifyModel = helper.getEflyerDetail(modifyModel);
        HashMap map = gson.fromJson(modifyModel.getEflyerData(), HashMap.class);
        PaperProperties paper = new PaperProperties();
        ArrayList<Float> list = paper.getPaperProperties(modifyModel.getPaperSize(), modifyModel.getOrientation(), modifyModel.getTypeTemplate());
        StringBuffer sbf;
        if (modifyModel.getTypeTemplate() == PaperProperties.MODEL1) {
            sbf = new StringBuffer()
                    .append("<table style= \"width:")
                    .append(list.get(paper.TABLE1WIDTH).toString())
                    .append("mm;height:")
                    .append(list.get(paper.TABLE1HEIGHT).toString())
                    .append("mm;margin-top: 5mm;margin-left: 5mm;margin-right: 5mm;\"><tr>")
                    .append("<td style= \"width:")
                    .append(list.get(paper.IMG1WIDTH).toString())
                    .append("mm;height:")
                    .append(list.get(paper.IMG1HEIGHT).toString())
                    .append("mm;\">")
                    .append("<img src=\"data:image/png;base64,")
                    .append(map.get("IMAGE1"))
                    .append("\" style= \"width:")
                    .append(list.get(paper.IMG1WIDTH).toString())
                    .append("mm;height: ")
                    .append(list.get(paper.IMG1HEIGHT).toString())
                    .append("mm;\">")
                    .append("</td>")
                    .append("<td style= \"vertical-align:top;width: ")
                    .append(list.get(paper.TEXT1WIDTH).toString())
                    .append("mm;height: ")
                    .append(list.get(paper.TEXT1HEIGHT).toString())
                    .append("mm;\">")
                    .append(map.get("TEXT1"))
                    .append("</td>")
                    .append("</tr></table>")
                    .append("<table style= \"width: ")
                    .append(list.get(paper.TABLE2WIDTH).toString())
                    .append("mm;height: ")
                    .append(list.get(paper.TABLE2HEIGHT).toString())
                    .append("mm;margin-bottom: 5mm;margin-left: 5mm;margin-right: 5mm;\"><tr>")
                    .append("<td style= \"vertical-align:top;width: ")
                    .append(list.get(paper.TEXT2WIDTH).toString())
                    .append("mm;height:")
                    .append(list.get(paper.TEXT2WIDTH).toString())
                    .append("mm;\">")
                    .append(map.get("TEXT2"))
                    .append("</td>")
                    .append("</tr></table>");
        } else if (modifyModel.getTypeTemplate() == PaperProperties.MODEL2) {
            sbf = new StringBuffer()
                    .append("<table style= \"width:")
                    .append(list.get(paper.TABLE1WIDTH).toString())
                    .append("mm;height:")
                    .append(list.get(paper.TABLE1HEIGHT).toString())
                    .append("mm;margin-top: 5mm;margin-left: 5mm;margin-right: 5mm;\"><tr>")

                    .append("<td style= \"width:")
                    .append(list.get(paper.IMG1WIDTH).toString())
                    .append("mm;height:")
                    .append(list.get(paper.IMG1HEIGHT).toString())
                    .append("mm;\">")
                    .append("<img src=\"data:image/png;base64,")
                    .append(map.get("IMAGE1"))
                    .append("\" style= \"width:")
                    .append(list.get(paper.IMG1WIDTH).toString())
                    .append("mm;height: ")
                    .append(list.get(paper.IMG1HEIGHT).toString())
                    .append("mm;\">")
                    .append("</td>")

                    .append("</tr></table>")
                    .append("<table style= \"width: ")
                    .append(list.get(paper.TABLE2WIDTH).toString())
                    .append("mm;height: ")
                    .append(list.get(paper.TABLE2HEIGHT).toString())
                    .append("mm;margin-bottom: 5mm;margin-left: 5mm;margin-right: 5mm;\"><tr>")

                    .append("<td style= \"vertical-align:top;width: ")
                    .append(list.get(paper.TEXT1WIDTH).toString())
                    .append("mm;height: ")
                    .append(list.get(paper.TEXT1HEIGHT).toString())
                    .append("mm;\">")
                    .append(map.get("TEXT1"))
                    .append("</td>")

                    .append("</tr></table>");
        } else if (modifyModel.getTypeTemplate() == PaperProperties.MODEL3) {
            sbf = new StringBuffer()
                    .append("<table style= \"width:")
                    .append(list.get(paper.TABLE1WIDTH).toString())
                    .append("mm;height:")
                    .append(list.get(paper.TABLE1HEIGHT).toString())
                    .append("mm;margin-top: 5mm;margin-left: 5mm;margin-right: 5mm;\"><tr>")

                    .append("<td style= \"vertical-align:top;width: ")
                    .append(list.get(paper.TEXT1WIDTH).toString())
                    .append("mm;height: ")
                    .append(list.get(paper.TEXT1HEIGHT).toString())
                    .append("mm;\">")
                    .append(map.get("TEXT1"))
                    .append("</td>")

                    .append("</tr></table>")
                    .append("<table style= \"width: ")
                    .append(list.get(paper.TABLE2WIDTH).toString())
                    .append("mm;height: ")
                    .append(list.get(paper.TABLE2HEIGHT).toString())
                    .append("mm;margin-bottom: 5mm;margin-left: 5mm;margin-right: 5mm;\"><tr>")

                    .append("<td style= \"width:")
                    .append(list.get(paper.IMG1WIDTH).toString())
                    .append("mm;height:")
                    .append(list.get(paper.IMG1HEIGHT).toString())
                    .append("mm;\">")
                    .append("<img src=\"data:image/png;base64,")
                    .append(map.get("IMAGE1"))
                    .append("\" style= \"width:")
                    .append(list.get(paper.IMG1WIDTH).toString())
                    .append("mm;height: ")
                    .append(list.get(paper.IMG1HEIGHT).toString())
                    .append("mm;\">")
                    .append("</td>")

                    .append("</tr></table>");
        } else if (modifyModel.getTypeTemplate() == PaperProperties.MODEL4) {
            sbf = new StringBuffer()
                    .append("<table style= \"width:")
                    .append(list.get(paper.TABLE1WIDTH).toString())
                    .append("mm;height:")
                    .append(list.get(paper.TABLE1HEIGHT).toString())
                    .append("mm;margin-top: 5mm;margin-left: 5mm;margin-right: 5mm;\"><tr>")


                    .append("<td style= \"vertical-align:top;width: ")
                    .append(list.get(paper.TEXT1WIDTH).toString())
                    .append("mm;height: ")
                    .append(list.get(paper.TEXT1HEIGHT).toString())
                    .append("mm;\">")
                    .append(map.get("TEXT1"))
                    .append("</td>")

                    .append("<td style= \"width:")
                    .append(list.get(paper.IMG1WIDTH).toString())
                    .append("mm;height:")
                    .append(list.get(paper.IMG1HEIGHT).toString())
                    .append("mm;\">")
                    .append("<img src=\"data:image/png;base64,")
                    .append(map.get("IMAGE1"))
                    .append("\" style= \"width:")
                    .append(list.get(paper.IMG1WIDTH).toString())
                    .append("mm;height: ")
                    .append(list.get(paper.IMG1HEIGHT).toString())
                    .append("mm;\">")
                    .append("</td>")

                    .append("</tr></table>")
                    .append("<table style= \"width: ")
                    .append(list.get(paper.TABLE2WIDTH).toString())
                    .append("mm;height: ")
                    .append(list.get(paper.TABLE2HEIGHT).toString())
                    .append("mm;margin-bottom: 5mm;margin-left: 5mm;margin-right: 5mm;\"><tr>")

                    .append("<td style= \"width:")
                    .append(list.get(paper.IMG2WIDTH).toString())
                    .append("mm;height:")
                    .append(list.get(paper.IMG2HEIGHT).toString())
                    .append("mm;\">")
                    .append("<img src=\"data:image/png;base64,")
                    .append(map.get("IMAGE2"))
                    .append("\" style= \"width:")
                    .append(list.get(paper.IMG2WIDTH).toString())
                    .append("mm;height: ")
                    .append(list.get(paper.IMG2HEIGHT).toString())
                    .append("mm;\">")
                    .append("</td>")

                    .append("<td style= \"vertical-align:top;width: ")
                    .append(list.get(paper.TEXT2WIDTH).toString())
                    .append("mm;height:")
                    .append(list.get(paper.TEXT2WIDTH).toString())
                    .append("mm;\">")
                    .append(map.get("TEXT2"))
                    .append("</td>")

                    .append("</tr></table>");
        } else {
            sbf = new StringBuffer()
                    .append("<table style= \"width:")
                    .append(list.get(paper.TABLE1WIDTH).toString())
                    .append("mm;height:")
                    .append(list.get(paper.TABLE1HEIGHT).toString())
                    .append("mm;margin-top: 5mm;margin-left: 5mm;margin-right: 5mm;\"><tr>")

                    .append("<td style= \"width:")
                    .append(list.get(paper.IMG1WIDTH).toString())
                    .append("mm;height:")
                    .append(list.get(paper.IMG1HEIGHT).toString())
                    .append("mm;\">")
                    .append("<img src=\"data:image/png;base64,")
                    .append(map.get("IMAGE1"))
                    .append("\" style= \"width:")
                    .append(list.get(paper.IMG1WIDTH).toString())
                    .append("mm;height: ")
                    .append(list.get(paper.IMG1HEIGHT).toString())
                    .append("mm;\">")
                    .append("</td>")

                    .append("</tr></table>");
        }
        return sbf.toString();
    }
}
