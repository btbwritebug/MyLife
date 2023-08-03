package com.founder.xy.common;

import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.summary.BM25;

import java.util.*;

/**
 * TextRank 自动摘要 - 重写了 HanLP中的这个类
 *
 * @author hankcs
 */
public class TextRankSentence {
    /**
     * 阻尼系数（ＤａｍｐｉｎｇＦａｃｔｏｒ），一般取值为0.85
     */
    static final double DAMPING_FACTOR = 0.85;
    /**
     * 最大迭代次数
     */
    static final int MAX_ITER = 200;
    static final double MIN_DIFF = 0.001;
    /**
     * 文档句子的个数
     */
    int count;
    /**
     * 拆分为[句子[单词]]形式的文档
     */
    List<List<String>> docs;
    /**
     * 排序后的最终结果 score <-> index
     */
    TreeMap<Double, Integer> top;

    /**
     * 句子和其他句子的相关程度
     */
    double[][] weight;
    /**
     * 该句子和其他句子相关程度之和
     */
    double[] weightSum;
    /**
     * 迭代之后收敛的权重
     */
    double[] vertex;

    /**
     * BM25相似度
     */
    BM25 bm25;

    public TextRankSentence(List<List<String>> docs) {
        this.docs = docs;
        bm25 = new BM25(docs);
        count = docs.size();
        weight = new double[count][count];
        weightSum = new double[count];
        vertex = new double[count];
        top = new TreeMap<>(Collections.reverseOrder());
        solve();
    }

    private void solve() {
        int cnt = 0;
        for (List<String> sentence : docs) {
            double[] scores = bm25.simAll(sentence);
            weight[cnt] = scores;
            weightSum[cnt] = sum(scores) - scores[cnt]; // 减掉自己，自己跟自己肯定最相似
            vertex[cnt] = 1.0;
            ++cnt;
        }
        for (int index = 0; index < MAX_ITER; ++index) {
            double[] m = new double[count];
            double maxDiff = 0;
            for (int i = 0; i < count; ++i) {
                m[i] = 1 - DAMPING_FACTOR;
                for (int j = 0; j < count; ++j) {
                    if (j == i || weightSum[j] == 0) continue;
                    m[i] += (DAMPING_FACTOR * weight[j][i] / weightSum[j] * vertex[j]);
                }
                double diff = Math.abs(m[i] - vertex[i]);
                if (diff > maxDiff) {
                    maxDiff = diff;
                }
            }
            vertex = m;
            if (maxDiff <= MIN_DIFF) break;
        }
        // 我们来排个序吧
        for (int i = 0; i < count; ++i) {
            top.put(vertex[i], i);
        }
    }

    /**
     * 获取前几个关键句子
     *
     * @param size 要几个
     * @return 关键句子的下标
     */
    public int[] getTopSentence(int size) {
        Collection<Integer> values = top.values();
        size = Math.min(size, values.size());
        int[] indexArray = new int[size];
        Iterator<Integer> it = values.iterator();
        for (int i = 0; i < size; ++i) {
            indexArray[i] = it.next();
        }
        return indexArray;
    }

    /**
     * 简单的求和
     *
     * @param array
     * @return
     */
    private static double sum(double[] array) {
        double total = 0;
        for (double v : array) {
            total += v;
        }
        return total;
    }

    public static void main(String[] args) {

        /*String document = "算法可大致分为基本算法、数据结构的算法、数论算法、计算几何的算法、图的算法、动态规划以及数值分析、加密算法、排序算法、检索算法、随机化算法、并行算法、厄米变形模型、随机森林算法。\n" +
                "算法可以宽泛的分为三类，\n" +
                "一，有限的确定性算法，这类算法在有限的一段时间内终止。他们可能要花很长时间来执行指定的任务，但仍将在一定的时间内终止。这类算法得出的结果常取决于输入值。\n" +
                "二，有限的非确定算法，这类算法在有限的时间内终止。然而，对于一个（或一些）给定的数值，算法的结果并不是唯一的或确定的。\n" +
                "三，无限的算法，是那些由于没有定义终止定义条件，或定义的条件无法由输入的数据满足而不终止运行的算法。通常，无限算法的产生是由于未能确定的定义终止条件。";
        System.out.println(TextRankSentence.getTopSentenceList(document, 3));*/

    }

    /**
     * 将文章分割为句子
     *
     * @param document
     * @return
     */
    static List<String> spiltSentence(String document) {
        List<String> sentences = new ArrayList<>();
        for (String line : document.split("[\r\n]")) {
            line = line.trim();
            if (line.length() == 0) continue;
            for (String sent : line.split("[。？?！!；;]")) {
                sent = sent.trim();
                if (sent.length() == 0) continue;
                sentences.add(sent);
            }
        }

        return sentences;
    }

    /**
     * 一句话调用接口
     *
     * @param document 目标文档
     * @param size     需要的关键句的个数
     * @return 关键句列表
     */
    public static List<String> getTopSentenceList(String document, int size) {
        List<String> sentenceList = spiltSentence(document);
        List<List<String>> docs = new ArrayList<>();
        for (String sentence : sentenceList) {
            List<Term> termList = NShortSegment.parse(sentence);
            List<String> wordList = new LinkedList<>();
            for (Term term : termList) {
                if (CoreStopWordDictionary.shouldInclude(term)) {
                    wordList.add(term.word);
                }
            }
            docs.add(wordList);
        }
        TextRankSentence textRank = new TextRankSentence(docs);
        int[] topSentence = textRank.getTopSentence(size);
        List<String> resultList = new LinkedList<>();
        for (int i : topSentence) {
            resultList.add(sentenceList.get(i));
        }
        return resultList;
    }
}
