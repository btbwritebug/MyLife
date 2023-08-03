package com.founder.xy.sensitive;

import com.founder.sif.Sifilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Set;

/**
 * 敏感词的相关操作
 */
public class SensitiveControl {
    private SensitiveControl() {
    	throw new IllegalStateException("Utility class");
	}

    /**
     * status：状态位: 0:异常; 1:检测到敏感词; 2:没有敏感词;
     * inform: 出现异常的时候的异常信息;
     * keyNum: 命中的规则在文本中对应的命中的敏感词的个数
     * keywords: 命中的敏感词
     * ruleId: 规则的id
     * position: 命中的敏感词在文中的位置，index从0开始
     */
    private static JSONObject handleRusult(Set<SensitiveWord> set) {
        JSONObject json = new JSONObject();
        JSONArray keywords = new JSONArray();
        if (set.isEmpty()) {
            json.put("keywords", keywords);
            return json;
        }
        JSONArray position = new JSONArray();
        JSONArray ruleId = new JSONArray();
        JSONArray keyNum = new JSONArray();
        for (SensitiveWord sw : set) {
            for (int i = 0; i < sw.getLength(); i++) {
                keywords.add(sw.getKeyword().get(i));
                position.add(sw.getPosition().get(i));
            }
            ruleId.add(sw.getId());
            keyNum.add(sw.getLength());
        }
        json.put("keywords", keywords);
        json.put("position", position);
        json.put("ruleId", ruleId);
        json.put("keyNum", keyNum);
        return json;
    }

    public static String getSensiWord(String title, String content, Sifilter sif) {
        JSONObject json=new JSONObject();
        JSONObject resultJson = new JSONObject();
        //需要检查的文本（标题及正文）
        String titleCont = title + content;
        try {
            //查找敏感词
            String res = sif.docPatternFilter(title, content);
            //处理命中的敏感词
            SensitiveResult sr = new SensitiveResult(titleCont, res);
            //遍历敏感词列表
            if (sr.getSenSize() == 0 && sr.getIllSize() == 0) {
                resultJson.put("status", "2");
            } else {
                Set<SensitiveWord> set;
                set = sr.getResultIllegalSet();
                json.put("illegalWord", handleRusult(set));
                set = sr.getResultSensitiveSet();
                json.put("sensitiveWord", handleRusult(set));
                resultJson.put("status", "1");
            }
            resultJson.put("data", json);
        }catch (Exception e) {
            resultJson.put("status", "0");
            resultJson.put("inform", e.toString());
        }
        return resultJson.toString();
    }
}