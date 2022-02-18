package com.ljq.mydemo.test.mytest;

/**
 *
 * 最长回文串
 * @author gino
 * 2022-02-14
 *
 * 回文串 的定义   :正着读和反正读是一样的
 *
 *思路：  从中心向两边扩散 （分为两种情况：中心是一个的： cabac       中心是偶数的    baab）
 *
 *
 *    cbaabc
 *
 *
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 *
 * 输入：s = "cbbd"
 * 输出："bb"
 *
 *
 *
 *
 */
public class test007 {

    public static void main(String[] args) {
        String s = "baaaab";
        String s1 = longestPalindrome(s);
        System.out.println(s1);

    }

    public static String longestPalindrome(String s) {
        if (s.length() < 1) {
            return s;
        }
        String res="";
        int maxLength=0;
        for (int i = 0; i < s.length(); i++) {
            String stringOne = getStringOne(s, i);
            String stringTwo = getStringTwo(s, i);
            if (stringOne.length()>maxLength) {
                maxLength=stringOne.length();
                res=stringOne;
            }

            if (stringTwo.length()>maxLength) {
                maxLength=stringTwo.length();
                res=stringTwo;
            }
            // 三元运算符：判断为真时取冒号前面的值，为假时取冒号后面的值
            //res = res.length() > stringOne.length() ? res : stringOne;
           // res = res.length() > stringTwo.length() ? res : stringTwo;
        }

        return res;
    }

    /**
     * 获取从某一位置开始的回文串
     * @param rs  字符串
     * @param index  开始索引的位置
     * @return
     */
    public static String getStringOne(String rs, Integer index){
        //中心点是一个情况
        int left = index, right=index;
        while (left>=0  &&  right<rs.length()){
            if (rs.charAt(left)==rs.charAt(right)){
                left--;
                right++;
            }else {
                break;
            }
        }

        return rs.substring(left+1,right);
    }

    /**
     * 获取从某一位置开始的回文串
     * @param rs  字符串
     * @param index  开始索引的位置
     * @return
     */
    public static String getStringTwo(String rs, Integer index){
        //中心点是一个情况
        int left = index, right=index+1;
        while (left>=0  &&  right<rs.length()){
            if (rs.charAt(left)==rs.charAt(right)){
                left--;
                right++;
            }else {
                break;
            }
        }

        return rs.substring(left+1,right);
    }

}