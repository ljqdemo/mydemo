package com.ljq.mydemo.test.mytest;

/**
 * 回文整数
 * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
 *
 * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 *
 * 例如，121 是回文，而 123 不是。
 *
 * 输入：x = -121
 * 输出：false
 * 解释：从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 *181--->
 *4488
 *1
 * @author gino
 * 2022-02-17
 */
public class test009 {

    public static void main(String[] args) {
        int value=8;
        System.out.println(isPalindrome(value));
    }
    public static boolean isPalindrome(int x) {
        boolean flag=false;
        //负数全不是， 只有正数和0才有可能存在回文数
        if(x>0){
            String sx = String.valueOf(x);
            int start=0;
            int end=sx.length()-1;
            for (int i = 0; i < sx.length(); i++) {
                if(sx.charAt(start)!=sx.charAt(end)){
                    break;
                }else{
                    start++;
                    end--;
                    if(start>=end){
                        flag=true;
                        break;
                    }
                }
            }

        }

        return flag;
    }
}
