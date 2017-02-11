package com.tenma.common.util.sequence;


import com.tenma.tools.util.TenmaConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Copyright@2013 - PT Tenma Bright Sky
 * User: ndwijaya
 * Date: 7/9/13
 * Time: 8:56 PM
 */
public class SequenceTool {
    public static final int YEAR_4_DIGIT = 4;
    public static final int YEAR_2_DIGIT = 2;

    private static SequenceTool instance;

    private SequenceTool() {
    }

    public static SequenceTool getInstance() {
        if (instance == null) {
            instance = new SequenceTool();
        }
        return instance;
    }

    public static void main(String[] args) {

        (new SequenceTool()).test();
    }

    public int getLastSequence(String communityId, String key, boolean year) {
        SeqModel m = new SeqModel();
        m.setCommunityId(communityId);
        m.setSeqId(key);
        if (year) {
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat fmt = new SimpleDateFormat("YYYY");
            String sDate = fmt.format(date);
            m.setSeqYear(sDate);
        }
        SeqHelper seqHelper = new SeqHelper();
        HashMap map = new HashMap();
        map.put("seqId", m.getSeqId());
        map.put("seqYear", m.getSeqYear());
        map.put(TenmaConstants.COMMUNITY_ID, m.getCommunityId());
        int rst = seqHelper.getLastSeq(map);

        return rst;
    }

    public synchronized int getNewCounter(String communityId, String key, boolean year) {
        SeqHelper seqHelper = new SeqHelper();
        int result = getLastSequence(communityId, key, year);
        SeqModel m = new SeqModel();
        m.setCommunityId(communityId);
        m.setSeqId(key);
        if (year) {
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat fmt = new SimpleDateFormat("YYYY");
            String sDate = fmt.format(date);
            m.setSeqYear(sDate);
            m.setSeqYear(sDate);
        }
        if (result == 0) {
            result = 1;
            m.setLastSeq(result);

            try {
                seqHelper.insertNewSeq(m);
            } catch (Exception e) {
                e.printStackTrace();
                result = 0;
            } finally {

            }
        } else {
            result = result + 1;
            m.setLastSeq(result);
            try {
                seqHelper.updateSeq(m);
            } catch (Exception e) {
                e.printStackTrace();
                result = 0;
            } finally {
            }
        }
//        System.out.println("NEW COUNTER " + key + " = " + result);
        return result;
    }

    public synchronized String getNewCounterStringWith2Year(String communityId, String key, int ctrlength) {
        SeqHelper seqHelper = new SeqHelper();
        int result = getLastSequence(communityId, key, true);
        SeqModel m = new SeqModel();
        m.setCommunityId(communityId);
        m.setSeqId(key);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat fmt = new SimpleDateFormat("YYYY");
        String sDate = fmt.format(date);
        m.setSeqYear(sDate);
        m.setSeqYear(sDate);

        if (result == 0) {
            result = 1;
            m.setLastSeq(result);

            try {
                seqHelper.insertNewSeq(m);
            } catch (Exception e) {
                e.printStackTrace();
                result = 0;
            } finally {

            }
        } else {
            result = result + 1;
            m.setLastSeq(result);
            try {
                seqHelper.updateSeq(m);
            } catch (Exception e) {
                e.printStackTrace();
                result = 0;
            } finally {
            }
        }
//        System.out.println("YEAR2 - NEW COUNTER " + key + " = " + result);
        return addZeroPadWithYear(result, ctrlength, YEAR_2_DIGIT);
    }

    private int getGlobalNewSequence(String key, boolean year) {
        SeqModel m = new SeqModel();
        m.setCommunityId("0");
        m.setSeqId(key);
        if (year) {
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat fmt = new SimpleDateFormat("YYYY");
            String sDate = fmt.format(date);
            m.setSeqYear(sDate);
        }
        SeqHelper seqHelper = new SeqHelper();
        HashMap map = new HashMap();
        map.put("seqId", m.getSeqId());
        map.put("seqYear", m.getSeqYear());

        map.put(TenmaConstants.COMMUNITY_ID, m.getCommunityId());

        int rst = seqHelper.getLastSeq(map);

        return rst;
    }

    public synchronized int getGlobalNewCounter(String key, boolean year) {
        SeqHelper seqHelper = new SeqHelper();
        int result = getGlobalNewSequence(key, year);
        SeqModel m = new SeqModel();
        m.setCommunityId("0");
        m.setSeqId(key);
        if (year) {
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat fmt = new SimpleDateFormat("YYYY");
            String sDate = fmt.format(date);
            m.setSeqYear(sDate);
            m.setSeqYear(sDate);
        }
        if (result == 0) {
            result = 1;
            m.setLastSeq(result);

            try {
                seqHelper.insertNewSeq(m);
            } catch (Exception e) {
                e.printStackTrace();
                result = 0;
            } finally {

            }
        } else {
            result = result + 1;
            m.setLastSeq(result);
            try {
                seqHelper.updateSeq(m);
            } catch (Exception e) {
                e.printStackTrace();
                result = 0;
            } finally {
            }
        }
        System.out.println("NEW COUNTER " + key + " = " + result);
        return result;
    }

    public synchronized String addZeroPadWithYear(int counter, int counterLength, int yearLength) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat fmt = new SimpleDateFormat("YYYY");
        if (yearLength == YEAR_2_DIGIT) {
            fmt = new SimpleDateFormat("YY");
        }
        String sDate = fmt.format(date);
        String sctr = String.valueOf(counter);
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(sDate);

        int index = sctr.length();
        if (index < counterLength) {
            while (index < counterLength) {
                sbuf.append("0");
                index++;
            }
        }
        sbuf.append(sctr);

        return sbuf.toString();
    }

    public synchronized String addZeroPad(int counter, int counterLength) {
        String sctr = String.valueOf(counter);
        StringBuffer sbuf = new StringBuffer();

        int index = sctr.length();
        if (index < counterLength) {
            while (index < counterLength) {
                sbuf.append("0");
                index++;
            }
        }
        sbuf.append(sctr);

        return sbuf.toString();
    }

    private void test() {
        System.out.println(addZeroPadWithYear(5, 3, 2));

    }

}
