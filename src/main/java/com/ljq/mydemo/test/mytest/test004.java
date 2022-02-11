package com.ljq.mydemo.test.mytest;

/**
 *
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 *
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 *
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 *
 *   2-------------> 4 ----------------->3
 *   5------------->6------------------>4
 *
 *
 *   8------------->0------------------->7
 *
 *
 *   输入：l1 = [2,4,3], l2 = [5,6,4]
 *    输出：[7,0,8]
 *  解释：342 + 465 = 807.
 *
 * @author gino
 * 2022-02-10
 */
public class test004 {


    public static void main(String[] args) {
        ListNode root=new ListNode(1);
        ListNode root2=new ListNode(9,new ListNode(9,new ListNode(9)));

        ListNode listNode = addTwoNumbers(root, root2);
        while (listNode!=null){
            System.out.println(listNode.val);
            listNode=listNode.next;
        }

    }

    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Long num1 = getNum(l1);
        Long num2 = getNum(l2);
        Long sum=num1+num2;
        String sumStr = sum.toString();
        char[] vals = sumStr.toCharArray();
        ListNode root=new ListNode(Integer.parseInt(vals[vals.length-1]+""));
        for (int i = vals.length - 2; i >= 0; i--) {
            ListNode temp=root;
            while (temp.next!=null){
                temp=temp.next;
            }
            ListNode node=new ListNode();
            node.val=Integer.parseInt(vals[i]+"");;
            temp.next=node;
        }
        return root;
    }

    public static Long getNum(ListNode node){
        Long sum=(long)node.val;
        int index=1;
        while (node.next!=null){
            double pow=Math.pow(10,index++);
            sum+=(long)(node.next.val*pow);
            node=node.next;
        }
        return sum;
    }
}
