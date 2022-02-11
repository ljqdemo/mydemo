package com.ljq.mydemo.test.mytest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gino
 * 2022-02-10
 *
 *
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 *
 *      p w w k e w f f w
 *        1
 *        1
 *
 * key     p    w
 * value   1    2
 *
 */
public class test005 {

    public static void main(String[] args) {
        int abcabcbb = lengthOfLongestSubstring("abcabcbb");
        System.out.println(abcabcbb);

        int abcabcbb1 = lengthOfLongestSubstring3("abcabcbb");
        System.out.println(abcabcbb1);

    }
    public static int lengthOfLongestSubstring(String s) {
        int length=0;
        if(s.isEmpty()){
           return  length;
        }
        char[] chars = s.toCharArray();
        int pre=0;
        while (pre<s.length()){
            String childrenChar="";
            for (int i = pre; i < chars.length; i++) {
               //如果已经在子串中包含则直接结束这个子串
                if (childrenChar.contains(String.valueOf(chars[i])) ){
                   break;
                }
                childrenChar+=String.valueOf(chars[i]);
            }
            if(childrenChar.length()>length){
                length=childrenChar.length();
            }
            pre++;
        }
        return length;
    }


    public int lengthOfLongestSubstring2(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < n; end++) {
            char alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha), start);
            }
            ans = Math.max(ans, end - start + 1);
            map.put(s.charAt(end), end + 1);
        }
        return ans;
    }

    /**
     * 采用滑动窗口思想
     * 有两个指针，不会回退
     *
     * @param s
     * @return
     */
    public static  int lengthOfLongestSubstring3(String s) {
        //定义最长结束指针值
        int n = s.length();
        //定义最长支持长度
        int ans=0;
        //定义结束指针,开始指针
        int start=0, end=0;
        //定义一个集合，key值存字符，value 存下标
        Map<Character,Integer> map=new HashMap<>();
        for(end=0;end<n;end++){
            //结束指针移动，开始指针不动，开始匹配子串
            char alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                //如果该串包含于集合中，则说明出现过重复的，要将开始指针移动至此处
                start = Math.max(map.get(alpha), start);
            }
            ans = Math.max(ans, end - start + 1);
            map.put(s.charAt(end), end + 1);
        }
        return ans;
    }
}
