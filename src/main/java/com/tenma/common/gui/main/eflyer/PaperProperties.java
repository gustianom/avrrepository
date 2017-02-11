package com.tenma.common.gui.main.eflyer;

import java.util.ArrayList;

/**
 * Created by PT TENMA BRIGHT SKY
 * User    : Rom_Bar
 * Date    : 3/25/2015
 * Time    : 2:03 PM
 * Project : udw
 * Package : share.tenma.common.gui.main.eflyer
 */
public class PaperProperties {
    public static final int A5 = 1;
    public static final int A4 = 2;
    public static final int PORTRAIT = 1;
    public static final int LANDSCAPE = 2;
    public static final int MODEL1 = 1;
    public static final int MODEL2 = 2;
    public static final int MODEL3 = 3;
    public static final int MODEL4 = 4;
    public static final int MODEL5 = 5;
    public final int PANELWIDTH = 0;
    public final int PANELHEIGHT = 1;
    public final int IMG1WIDTH = 2;
    public final int IMG1HEIGHT = 3;
    public final int TEXT1WIDTH = 4;
    public final int TEXT1HEIGHT = 5;
    public final int TEXT2WIDTH = 6;
    public final int TEXT2HEIGHT = 7;
    public final int TABLE1WIDTH = 8;
    public final int TABLE1HEIGHT = 9;
    public final int TABLE2WIDTH = 10;
    public final int TABLE2HEIGHT = 11;
    public final int IMG2WIDTH = 12;
    public final int IMG2HEIGHT = 13;

    public PaperProperties() {

    }

    public ArrayList<Float> getPaperProperties(Integer papersize, Integer orientation, Integer model) {
        return getDataPaperWithModel(papersize, orientation, model);
    }

    private ArrayList<Float> getDataPaperWithModel(Integer papersize, Integer orientation, Integer model) {
        if (papersize == A4) {
            if (orientation == PORTRAIT) {
                switch (model) {
                    case MODEL1:
                        return new ArrayList<Float>() {{
                            add(210.0f);
                            add(297.0f);
                            add(100.0f);
                            add(100.0f);
                            add(100.0f);
                            add(100.0f);
                            add(200.0f);
                            add(180.0f);
                            add(200.0f);
                            add(110.0f);
                            add(200.0f);
                            add(180.0f);
                            add(0f);
                            add(0f);
                        }};
                    case MODEL2:
                        return new ArrayList<Float>() {{
                            add(210.0f);
                            add(297.0f);
                            add(200.0f);
                            add(142.0f);
                            add(200.0f);
                            add(142.0f);
                            add(0f);//no text 2
                            add(0f);
                            add(210.0f);
                            add(150.0f);
                            add(210.0f);
                            add(147.0f);
                            add(0f);//no image 2
                            add(0f);
                        }};
                    case MODEL3:
                        return new ArrayList<Float>() {{
                            add(210.0f);
                            add(297.0f);
                            add(200.0f);
                            add(142.0f);
                            add(200.0f);
                            add(142.0f);
                            add(0f);//no text 2
                            add(0f);
                            add(210.0f);
                            add(150.0f);
                            add(210.0f);
                            add(147.0f);
                            add(0f);//no image 2
                            add(0f);
                        }};
                    case MODEL4:
                        return new ArrayList<Float>() {{
                            add(210.0f);
                            add(297.0f);

                            add(100.0f);
                            add(141.0f);
                            add(100.0f);
                            add(141.0f);
                            add(100.0f);
                            add(141.0f);

                            add(200.0f);
                            add(100.0f);
                            add(200.0f);
                            add(190.0f);

                            add(100.0f);
                            add(141.0f);
                        }};
                    case MODEL5:
                        return new ArrayList<Float>() {{
                            add(210.0f);
                            add(297.0f);
                            add(210.0f);
                            add(285.0f);

                            add(0f);
                            add(0f);
                            add(0f);
                            add(0f);

                            add(210.0f);
                            add(297.0f);

                            add(0f);
                            add(0f);
                            add(0f);
                            add(0f);
                        }};
                    default:
                        return null;
                }
            } else {//LANDSCAPE
                switch (model) {
                    case MODEL1:
                        return new ArrayList<Float>() {{
                            add(297.0f);
                            add(210.0f);
                            add(100.0f);
                            add(100.0f);
                            add(187.0f);
                            add(100.0f);
                            add(287.0f);
                            add(93.0f);
                            add(287.0f);
                            add(93.0f);
                            add(287.0f);
                            add(93.0f);
                            add(0f);
                            add(0f);
                        }};
                    default:
                        return null;
                }
            }
        } else {//A5
            if (orientation == PORTRAIT) {
                switch (model) {
                    case MODEL1:
                        return new ArrayList<Float>() {{
                            add(148.0f);
                            add(218.0f);
                            add(68.0f);
                            add(68.0f);
                            add(68.0f);
                            add(68.0f);
                            add(138.0f);
                            add(134.0f);
                            add(138.0f);
                            add(78.0f);
                            add(138.0f);
                            add(124.0f);
                            add(0f);
                            add(0f);
                        }};
                    case MODEL2:
                        return new ArrayList<Float>() {{
                            add(148.0f);
                            add(218.0f);
                            add(143.0f);
                            add(102.0f);
                            add(138.0f);
                            add(102.0f);

                            add(0f);
                            add(0f);
                            add(148.0f);
                            add(109.0f);
                            add(148.0f);
                            add(109.0f);

                            add(0f);
                            add(0f);
                        }};
                    case MODEL3:
                        return new ArrayList<Float>() {{
                            add(148.0f);
                            add(218.0f);
                            add(143.0f);
                            add(102.0f);
                            add(138.0f);
                            add(102.0f);

                            add(0f);
                            add(0f);
                            add(148.0f);
                            add(109.0f);
                            add(148.0f);
                            add(109.0f);

                            add(0f);
                            add(0f);
                        }};
                    case MODEL4:
                        return new ArrayList<Float>() {{
                            add(148.0f);
                            add(218.0f);

                            add(67.0f);
                            add(101.0f);
                            add(67.0f);
                            add(101.0f);
                            add(67.0f);
                            add(101.0f);

                            add(148.0f);
                            add(109.0f);
                            add(148.0f);
                            add(109.0f);

                            add(67.0f);
                            add(101.0f);
                        }};
                    case MODEL5:
                        return new ArrayList<Float>() {{
                            add(148.0f);
                            add(218.0f);
                            add(148.0f);
                            add(205.0f);

                            add(0f);
                            add(0f);
                            add(0f);
                            add(0f);

                            add(148.0f);
                            add(218.0f);

                            add(0f);
                            add(0f);
                            add(0f);
                            add(0f);
                        }};
                    default:
                        return null;
                }
            } else {//LANDSCAPE
                switch (model) {
                    case MODEL1:
                        return new ArrayList<Float>() {{
                            add(218.0f);
                            add(148.0f);
                            add(68.0f);
                            add(68.0f);
                            add(138.0f);
                            add(68.0f);
                            add(208.0f);
                            add(64.0f);
                            add(208.0f);
                            add(64.0f);
                            add(208.0f);
                            add(64.0f);
                            add(0f);
                            add(0f);
                        }};
                    default:
                        return null;
                }
            }
        }
    }
}
