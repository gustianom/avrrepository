package com.tenma.auth.util;

import java.text.NumberFormat;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: anom
 * Date: 8/12/13
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */
class NumericWording {
    public static String[] LANGUAGE_CODE = new String[]{"en", "in"};
    private static String descriptionEnglish[] = {
            "", "teen", "ty", "hundred", "thousand", "million", "billion", "trillion"
    };
    private static String descriptionIndonesia[] = {
            "", "belas", "puluh", "ratus", "ribu", "juta", "milyar", "triliun"
    };
    private static String englishPuluhanSetting[] =
            {"", // 0
                    "", //1
                    "twen", //2
                    "Thir", //3
                    "four", //4
                    "fif", //5
                    "six", //6
                    "seven", //7
                    "eigh", //8
                    "nine" //9
            };
    private static String[] indonesianNumber = {
            "", "satu", "dua", "tiga", "empat", "lima", "enam", "tujuh", "delapan", "sembilan", "sepuluh",
            "sebelas", "dua belas", "tiga belas", "empat belas", "lima belas", "enam belas", "tujuh belas",
            "delapan belas", "sembilan belas"
    };
    private static String[] englishNumber = {
            "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
            "eighteen", "nineteen"
    };
    private static String numericDescription[][] = {indonesianNumber, englishNumber};
    private static String[][] puluhanSetting = {{}, englishPuluhanSetting};
    private static String decimalPoint[] = {"nol", "zero"};
    private static String komma[] = {"koma", "and"};
    private static String cent[] = {"sen", "cent"};
    private static String description[][] = {descriptionIndonesia, descriptionEnglish};

    /**
     * get numeric description<br>
     * 1 -> satu, if not found, the result is -1;
     *
     * @param num
     * @return String
     */
    private static String getNumDescription(String num, LANGUAGE_TYPE languageType) {
        int inum = 0;
        try {
            inum = Integer.parseInt(num);
        } catch (Exception e) {
        }
        return getNumDescription(inum, languageType);
    }

    /**
     * get numeric description<br>
     * 1 -> satu, if not found, the result is -1;
     *
     * @param inum
     * @return String
     */
    private static String getNumDescription(int inum, LANGUAGE_TYPE languageType) {
        String desNum = "-1";
        if (numericDescription[languageType.getValue()].length > inum) {
            desNum = numericDescription[languageType.getValue()][inum];
        }
        return desNum;
    }

    /**
     * separated a string by a delimeter
     *
     * @param snum
     * @param delimeter
     * @return Vector
     */
    private static Vector separatedStringBy(String snum, String delimeter) {
        StringTokenizer tz = new StringTokenizer(snum, delimeter);
        Vector vec = new Vector();
        while (tz.hasMoreTokens()) {
            vec.addElement(tz.nextToken());
        }
        return vec;
    }

    /**
     * processing number
     *
     * @param snum
     * @return String
     */
    private static String processingNumber(String snum, LANGUAGE_TYPE languageType) {
        String sbilangan = "";
        if (snum.length() == 3) {
            int itmplen = snum.length();
            String s1 = String.valueOf(snum.charAt(0));

            if ((languageType.getValue() == LANGUAGE_TYPE.INDONESIAN.getValue()) && (s1.equalsIgnoreCase("1"))) {
                sbilangan = "seratus";
            } else {
                sbilangan = getNumDescription(s1, languageType).trim().length() == 0 ? "" : (getNumDescription(s1, languageType) + " " + description[languageType.getValue()][itmplen]);
            }
            snum = snum.substring(1);
        }

        String tmp = getNumDescription(snum, languageType);
        if (tmp.equalsIgnoreCase("-1")) {
            int itmplen = snum.length();
            String s1 = String.valueOf(snum.charAt(0));
            String s2 = String.valueOf(snum.charAt(1));
            String delimeter = " ";
            String numdesc = "";
            if (languageType.getValue() == LANGUAGE_TYPE.ENGLISH.getValue()) {
                delimeter = "";
                numdesc = puluhanSetting[languageType.getValue()][Integer.parseInt(s1)];
            } else {
                numdesc = getNumDescription(s1, languageType);
            }
            tmp = numdesc + delimeter + description[languageType.getValue()][itmplen] + " " + getNumDescription(s2, languageType);
        }
        sbilangan += " " + tmp;
        return sbilangan;
    }

    /**
     * processing nilai dibelakang komma
     *
     * @param sDecimal
     * @return String
     */
    private static String processingDecimal(String sDecimal, LANGUAGE_TYPE languageType) {
        sDecimal = sDecimal.trim();
        String sdes = "";
        for (int i = 0; i < sDecimal.length(); i++) {
            int in = Integer.parseInt(String.valueOf(sDecimal.charAt(i)));
            if (in == 0) {
                sdes += " " + decimalPoint[languageType.getValue()];
            } else {
                sdes += " " + getNumDescription(in, languageType);
            }
        }

        if (sdes.trim().length() != 0) {
            int ilog = stringToInteger(sdes.trim());
            if (ilog > 0) {
                String desdec = " " + komma[languageType.getValue()] + " ";
                if (sDecimal.charAt(0) == '0') {
                    desdec += processingDecimal(sdes, languageType);
                } else {
                    desdec += processingNotDecimal(sdes, languageType);
                }
                sdes += desdec + " " + cent[languageType.getValue()];
            }
        }
        return sdes.trim();
    }

    public static int stringToInteger(String number) {
        int i = 0;

        try {
            NumberFormat format = NumberFormat.getInstance();
            i = format.parse(number).intValue();
        } catch (Exception ex) {
        }

        return i;
    }

    /**
     * processing nilai desimal (bagian didepan komma)
     *
     * @param sBilangan
     * @return String
     */
    private static String processingNotDecimal(String sBilangan, LANGUAGE_TYPE languageType) {
        Vector tmpvec = separatedStringBy(sBilangan, ","); // separated by comma
        sBilangan = "";
        String tmpbil = "";

        for (int i = 0; i < tmpvec.size(); i++) {
            tmpbil = (String) tmpvec.elementAt(i);
            tmpbil = tmpbil.trim();
            if (tmpbil.equals("1")) {
                if (i == tmpvec.size() - 2) {
                    if (LANGUAGE_TYPE.INDONESIAN.equals(languageType)) {
                        sBilangan += "se";
                    } else {
                        sBilangan = processingNumber(tmpbil, languageType).trim() + " ";
                    }
                } else {
                    sBilangan += processingNumber(tmpbil, languageType).trim() + " ";
                }
            } else {
                sBilangan += processingNumber(tmpbil, languageType).trim() + " ";
            }

            if (i < (tmpvec.size() - 1)) {
                if (Converter.stringToInteger(tmpbil) != 0)
                    sBilangan += description[languageType.getValue()][tmpvec.size() - i + 2];
            }
            sBilangan = sBilangan.trim();
            sBilangan += " ";
        }
        return sBilangan.trim();
    }

    /**
     * translating a numeric value<br>
     * example : <br>
     * 1178 -> seribu seratus tujuh puluh delapan
     *
     * @param numeric
     * @return String
     */
    public static String translateNumber(double numeric, LANGUAGE_TYPE languageType, boolean isWithDecimal) {
        String s = String.valueOf(numeric);
        return translateNumber(s, languageType, isWithDecimal);
    }

    /**
     * translating a numeric value<br>
     * example : <br>
     * 1178 -> seribu seratus tujuh puluh delapan
     *
     * @param numeric
     * @return String
     */
    public static String translateNumber(double numeric, LANGUAGE_TYPE languageType) {
        String s = String.valueOf(numeric);
        return translateNumber(s, languageType, true);
    }

    /**
     * translating a numeric value<br>
     * example : <br>
     * 1178 -> seribu seratus tujuh puluh delapan
     *
     * @param numeric
     * @return String
     */
    public static String translateNumber(String numeric, LANGUAGE_TYPE languageType) {
        return translateNumber(numeric, languageType, true);
    }

    /**
     * translating a numeric value<br>
     * example : <br>
     * 1178 -> seribu seratus tujuh puluh delapan komma nol nol
     *
     * @param numeric
     * @return String
     */
    public static String translateNumber(String numeric, LANGUAGE_TYPE languageType, boolean isWithDecimal) {
        numeric = numeric.trim();
        String sconvert = Converter.formatNumber(numeric);
        Vector tmpvec = separatedStringBy(sconvert, "."); // separated decimal and non decimal
        String sBilangan = (String) tmpvec.elementAt(0);
        String sDecimal = (String) tmpvec.elementAt(1);

        String hasil = "";
        if ((sBilangan.trim().length() == 1) && sBilangan.equals("0")) {
            hasil = decimalPoint[languageType.getValue()];
        } else {
            hasil = processingNotDecimal(sBilangan, languageType);
        }
        if (isWithDecimal) {
            //hasil +=  " " + processingDecimal(sDecimal, languageType);
            if (sDecimal.trim().length() != 0) {
                int ilog = Converter.stringToInteger(sDecimal.trim());
                if (ilog > 0) {
                    String desdec = " " + komma[languageType.getValue()] + " ";
                    if (sDecimal.charAt(0) == '0') {
                        desdec += processingDecimal(sDecimal, languageType);
                    } else {
                        desdec += processingNotDecimal(sDecimal, languageType);
                    }
                    hasil += desdec + " " + cent[languageType.getValue()];
                }
            }
        }
        return hasil;
    }

    public static void main(String[] args) {
//        String arg0 = "0";
//        String arg1 = "0";
//        if ( args.length == 2 )
//        {
//            arg0 = args[ 0 ] == null ? "0" : args[ 0 ];
//            arg1 = args[ 1 ] == null ? "0" : args[ 1 ];
//        } else if ( args.length == 1 )
//        {
//            arg0 = args[ 0 ] == null ? "0" : args[ 0 ];
//        }
        System.out.println("translateNumber(args[0]) = " + translateNumber(81792.2678, LANGUAGE_TYPE.INDONESIAN, true));
    }

//    public static String translateNumber(long number, LANGUAGE_TYPE languageType) {
//        String nm = String.valueOf(number);
//        return translateNumber(nm, languageType);
//    }

    public static enum LANGUAGE_TYPE {
        INDONESIAN(0), ENGLISH(1),;

        int methodvalue = 0;

        private LANGUAGE_TYPE(int value) {
            methodvalue = value;
        }

        public int getValue() {
            return methodvalue;
        }
    }
}