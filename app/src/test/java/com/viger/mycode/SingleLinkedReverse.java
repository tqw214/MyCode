package com.viger.mycode;

public class SingleLinkedReverse {

    class Node{
        int data;
        Node next;

        public Node(int data){
            this.data = data;
        }
    }

    private static Node reverse1(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;

    }

//    private static Node reverse1(Node head) {
//        Node pre = null;
//        Node next = null;
//        while(head != null) {
//            next = head.next;
//            head.next = pre;
//            pre = head;
//            head = next;
//        }
//        return pre;
//    }

    //@Test
    public static void main(String[] args) {
        SingleLinkedReverse slr = new SingleLinkedReverse();
        Node head = slr.new Node(0);
        Node node1 = slr.new Node(1);
        Node node2 = slr.new Node(2);
        Node node3 = slr.new Node(3);
        Node node4 = slr.new Node(4);
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        Node temp = head;
        while(temp !=null) {
            System.out.print(temp.data+"  ");
            temp = temp.next;
        }

        Node h = reverse1(head);

        System.out.println();

        Node temp1 = h;
        while(temp1 !=null) {
            System.out.print(temp1.data+"  ");
            temp1 = temp1.next;
        }

    }

    private static Node reverse(Node head) {
        Node p1, p2 = null;
        p1 = head;

        while (head.next != null) {
            p2 = head.next;
            head.next = p2.next;
            p2.next = p1;
            p1 = p2;
        }
        return p2;
    }

}
