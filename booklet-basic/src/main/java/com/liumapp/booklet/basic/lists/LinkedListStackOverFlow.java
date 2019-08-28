package com.liumapp.booklet.basic.lists;

import java.util.LinkedList;

/**
 * file LinkedListStackOverFlow.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/8/28
 */
public class LinkedListStackOverFlow {

    /**
     * 复现一个linked list的stack over flow异常
     * ListNode的toString方法，涉及到next节点
     * 这意味着在调用toString方法的时候，当前节点会调用它的next节点，直到回到它自己
     * 这就会造成infinite recursion无限递归，从而造成linkedList抛出stack over flow异常
     */
    public static void main (String[] args) {
        ListNode rootNode = new ListNode(1);
        rootNode.next = new ListNode(2);
        rootNode.next.next = new ListNode(3);
        rootNode.next.next.next = new ListNode(4);

        LinkedList<ListNode> queue = new LinkedList<>();
        queue.add(rootNode);

        while (rootNode.next != null) {
            rootNode = rootNode.next;
            queue.add(rootNode);
        }

        for (int i = 0; i < 2; i++) {
            ListNode tmp = queue.removeLast();
            queue.push(tmp);
        }
        ListNode result = queue.pop();
        ListNode tmp = result;

        //以debug模式时，第二次遍历将会抛出stack over flow异常
        //直接run不会抛出异常
        while (!queue.isEmpty()) {
            tmp.next = queue.pop();
            tmp = tmp.next;
        }

    }



}
