package com.liumapp.booklet.basic.lists;

/**
 * file ListNode.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/8/28
 */
public class ListNode {

    public int value;

    public ListNode next;

    public ListNode(int x) {
        value = x;
        next = null;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + value +
                ", next=" + next +
                '}';
    }

}
