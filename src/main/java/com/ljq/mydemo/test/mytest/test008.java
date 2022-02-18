package com.ljq.mydemo.test.mytest;

/**
 * @author gino
 * 2022-02-17
 *
 * 函数 myAtoi(string s) 的算法如下：
 *
 * 读入字符串并丢弃无用的前导空格
 * 检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
 * 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
 * 将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
 * 如果整数数超过 32 位有符号整数范围 [−2^31, 2^31− 1] ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 −231 的整数应该被固定为 −231 ，大于 231 − 1 的整数应该被固定为 231 − 1 。
 * 返回整数作为最终结果。
 *
 *
 * 例子：
 * 输入：s = "4193 with words"
 * 输出：4193
 * 解释：
 * 第 1 步："4193 with words"（当前没有读入字符，因为没有前导空格）
 *          ^
 * 第 2 步："4193 with words"（当前没有读入字符，因为这里不存在 '-' 或者 '+'）
 *          ^
 * 第 3 步："4193 with words"（读入 "4193"；由于下一个字符不是一个数字，所以读入停止）
 *              ^
 * 解析得到整数 4193 。
 * 由于 "4193" 在范围 [-231, 231 - 1] 内，最终结果为 4193
 *

 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/string-to-integer-atoi
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class test008 {
    public static void main(String[] args) {
        String  s="2147483647";
        int result = myAtoi(s);
        System.out.println(result);
       System.out.println((int)Math.pow(2,31)-1);
    }

    public static int myAtoi(String s) {
        int result=0;
        //去除空格
        String trim = s.trim();
        //判断符号
        //-1073741824
        int min=Integer.MIN_VALUE/10;
        //1073741822
        int max=Integer.MAX_VALUE/10-1;

        if (!trim.isEmpty()&&trim.length()>0) {
            char op = trim.charAt(0);

            int start=0;
            int opflage=2;
            //如果带了符号则需要跳过
            if(op=='-' ){
                start++;
                opflage-=1;
            } if(op=='+'){
                start++;
            }

            for (int i = start; i < trim.length(); i++) {
                Character c = trim.charAt(i);
                if (Character.isDigit(c)) {
                    if (result<=min ){
                        return Integer.MIN_VALUE;
                    }else if( result>=max){
                        return  Integer.MAX_VALUE-1;
                    }


                        result = Integer.valueOf(trim.substring(start, i + 1)) ;
                        result=result* (int)Math.pow(-1,opflage);
                        System.out.println(result);

                }else {
                    break;
                }

            }
        }
        return result;
    }

}
