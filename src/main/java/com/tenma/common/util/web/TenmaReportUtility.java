package com.tenma.common.util.web;

import com.lowagie.text.pdf.PdfWriter;
import com.tenma.common.util.Constants;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Notification;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.*;
import java.lang.Boolean;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by gustianom on 21/11/14.
 */
public class TenmaReportUtility {

    public final StreamResource.StreamSource processingReport(final ReportInterface[] reportInterfaces, final Constants.REPORT_MIME_TYPES mimeTypes, final boolean isDirectPrint) {
        final AtomicReference<StreamResource.StreamSource> source = new AtomicReference<StreamResource.StreamSource>(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                byte[] b = null;

                try {
                    List<JasperPrint> jasperPrintList = generateJasperPrintList(reportInterfaces, mimeTypes);
                    b = generateMediaReport(jasperPrintList, mimeTypes);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return new ByteArrayInputStream(b);
            }
        });

        return source.get();
    }

    public final byte[] generateJasperOutputStream(final ReportInterface[] reportInterfaces, final Constants.REPORT_MIME_TYPES mimeTypes) throws Exception {
        byte[] b = null;
        List<JasperPrint> jasperPrintList = generateJasperPrintList(reportInterfaces, mimeTypes);
        b = generateMediaReport(jasperPrintList, mimeTypes);
        return b;
    }

    public final byte[] generateJasperOutputStream(List<JasperPrint> jasperPrintList, Constants.REPORT_MIME_TYPES mimeTypes) throws Exception {
        byte[] b = null;
        b = generateMediaReport(jasperPrintList, mimeTypes);
        return b;
    }

    public final List<JasperPrint> generateJasperPrintList(final ReportInterface[] reportInterfaces, final Constants.REPORT_MIME_TYPES mimeTypes) throws Exception {
        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        for (ReportInterface reportInterface : reportInterfaces) {
            InputStream rep = getClass().getClassLoader().getResourceAsStream(new StringBuffer().append("report/").append(reportInterface.getFullPathReportName()).toString());
            if (rep == null) {
                Notification.show("No report ! on " + reportInterface.getCaption());
            } else {

                BufferedInputStream bufferedInputStream = new BufferedInputStream(rep);
                JasperReport report = (JasperReport) JRLoader.loadObject(bufferedInputStream);


                JasperPrint jasperPrint = null;
                List data = reportInterface.getListData();
                HashMap parameter = reportInterface.getParameters();
                if (data != null) {
                    if (data.size() == 0)
                        jasperPrint = JasperFillManager.fillReport(report, parameter, new JREmptyDataSource());
                    else {
                        JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(data);
                        jasperPrint = JasperFillManager.fillReport(report, parameter, jrbcds);
                    }
                } else {
                    jasperPrint = JasperFillManager.fillReport(report, parameter, new JREmptyDataSource());
                }
                jasperPrintList.add(jasperPrint);
            }
        }
        return jasperPrintList;
    }

    public final byte[] generateMediaReport(List<JasperPrint> jasperPrintList, Constants.REPORT_MIME_TYPES mimeTypes) throws JRException {
        if (Constants.REPORT_MIME_TYPES.PDF.equals(mimeTypes))
            return generateMediaPDFReport(jasperPrintList);
        else
            return generateMediaHTMLReport(jasperPrintList);
    }

    public final byte[] generateMediaHTMLReport(List<JasperPrint> jasperPrintList) throws JRException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRHtmlExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
        exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
        exporter.setParameter(JRPdfExporterParameter.IS_COMPRESSED, Boolean.TRUE);
        exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING, "UTF-8");
        exporter.setParameter(JRPdfExporterParameter.PERMISSIONS, new Integer(PdfWriter.ALLOW_SCREENREADERS));
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//                    exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,"image?image=");
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
        exporter.exportReport();
        return baos.toByteArray();
    }

    private byte[] generateMediaPDFReport(List<JasperPrint> jasperPrintList) throws JRException {
        JRPdfExporter exporter = new JRPdfExporter();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
        exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
        exporter.setParameter(JRPdfExporterParameter.IS_COMPRESSED, Boolean.TRUE);
        exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING, "UTF-8");
        exporter.setParameter(JRPdfExporterParameter.PERMISSIONS, new Integer(PdfWriter.ALLOW_SCREENREADERS));
        exporter.setParameter(JRPdfExporterParameter.PERMISSIONS, new Integer(PdfWriter.ALLOW_SCREENREADERS));
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

        exporter.exportReport();
        return baos.toByteArray();
    }

    public static class ReportInterface {
        private String caption;
        private String fullPathReportName;
        private HashMap parameters;
        private List listData;

        public ReportInterface(String caption, String fullPathReportName, HashMap parameters, List listData) {
            this.caption = caption;
            this.fullPathReportName = fullPathReportName;
            this.parameters = parameters;
            this.listData = listData;
        }

        public String getCaption() {
            return caption;
        }

        public String getFullPathReportName() {
            return fullPathReportName;
        }

        public HashMap getParameters() {
            return parameters;
        }

        public List getListData() {
            return listData;
        }
    }
}
