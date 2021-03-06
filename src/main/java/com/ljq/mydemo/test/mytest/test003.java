package com.ljq.mydemo.test.mytest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gino
 * 2022-02-10
 *
 *
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 *
 * 你可以按任意顺序返回答案
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class test003 {


    public static void main(String[] args) {
        int [] nums={1,2,3,4};
        int target=5;
        int[] ints = twoSum(nums, target);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }
    }

    public  static  int[] twoSum(int[] nums, int target) {
        int [] result=new int [2] ;
        int index=0;
        for (int i = 0; i < nums.length; i++) {
            int num=nums[i];
            for (int j = i+1; j < nums.length; j++) {
                int num2=nums[j];
                if(num+num2==target){
                    result[index++]=i;
                    result[index++]=j;
                }
            }
        }
        return result;
    }

    public  static  int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i< nums.length; i++) {
            if(map.containsKey(target - nums[i])) {
                return new int[] {map.get(target-nums[i]),i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
