package com.founder.xy.common;

import com.founder.sif.Sifilter;

public class SifilterFactory {

    private SifilterFactory() {
        throw new IllegalStateException("Utility class");
    }

    //规则与文中匹配的字符串的相似度，在配置文件中默认为0.7
    private static final float ARTICLE_SIMVALUE = 0.7f;
    private static final float DISCUSS_SIMVALUE = 0.65f;
    private static float simValue;
    private static String rootDir = "";
    private static Sifilter sif;

    public static Sifilter getSifilter(String type, String root) throws Exception {
        rootDir = root;
        checkSifInstance();
        if (type != null)
            setSimValue(type);
        return sif;
    }

    /**
     * 初始化
     */
    private static synchronized Sifilter checkSifInstance() throws Exception {
        if (sif == null) {
            sif = new Sifilter();
            int res = sif.init(rootDir);
            //res == 0 表示初始化成功
            if (res != 0) {
                return null;
            }
            /**设置是否开启模糊音.1:开启.0:关闭
             sif.setFuzzyTone(1);
             //设置非 中文、英文、数字作为干扰项时的最大匹配长度.
             sif.setMaxMatchedLen(15);
             //保存配置信息
             sif.saveConfigInfo();*/

        }
        return sif;
    }

    /**
     * 设置规则与文中匹配的字符串的相似度
     */
    private static void setSimValue(String type) {
        //如果当前是评论模块，而且当前的simValue与评论模块需要的阈值不匹配，则重新办设置相对应得阈值，并更改当前的阈值变量
        if (type.equals("1") && simValue != DISCUSS_SIMVALUE) {
            simValue = DISCUSS_SIMVALUE;
        } else if (type.equals("0") && simValue != ARTICLE_SIMVALUE) {
            simValue = ARTICLE_SIMVALUE;
        }
        sif.setSimValue(simValue);
    }
}
