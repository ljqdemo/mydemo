package com.ljq.mydemo.test.mytest;

/**
 * abc       a.*
 * abcc      abc./*
 * ab c d      ab..
 *
 * "mississippi"
 * "mis*is*ip*."
 *
 * "ab"
 * ".*"
 * @author gino
 * 2022-02-17
 */
public class test010 {
    public static void main(String[] args) {
        String s = "ab";
        String p = ".*";

        boolean match = isMatch(s, p);
        System.out.println(match);

    }

    public static boolean isMatch(String s, String p) {
        //直接包含则返回true
        if (p.contains(s)) {
            return true;
        }
        if(p.contains(".*")){
            return true;
        }
        //不直接包含则需要计算最长s的最长子串出现在p的位置
        for (int i = s.length()-1 ; i > 0; i--) {
            String child = s.substring(0, i);
            int index = p.indexOf(child)+1;
            if (index >0) {
                index+=child.length()-1;
                //如果找最长子串位置，需要挨个匹配值
                if(index==p.length()){
                    return false;
                }
                int start=i;
                for (int j = index; j < p.length() && start<s.length(); j++) {
                    //获取要匹配的字符
                    char c = p.charAt(index);
                    if (c == '.') {
                        //如果是. 可以代表任何数则继续匹配
                        index++;
                        start++;
                       // break;
                    } else if (c == '*') {
                        if (s.charAt(start) == s.charAt(start - 1)) {
                            //如果当前字符是于上一个字符相等则继续匹配
                            index++;
                            start++;
                        }
                    }else{
                        if(p.charAt(index)==s.charAt(start)){
                            index++;
                            start++;
                        }
                    }

                    if(start==s.length()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isMatch2(String s, String p) {
        return s.matches(p);
    }
}
