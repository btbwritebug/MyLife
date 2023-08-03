package 链表问题;

import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

/**
 * 打印两个有序链表的公共部分
 */
public class Demo1 {
    public static void main(String[] args) {
        Node head1 = new Node(1);
        Node head2 = new Node(2);
        Node node1 = new Node(2);
        Node node2 = new Node(3);
        Node node3 = new Node(4);
        Node node4 = new Node(5);
        head1.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        Node node5 = new Node(3);
        Node node6 = new Node(4);
        Node node7 = new Node(5);
        Node node8 = new Node(6);
        head2.next = node5;
        node5.next = node6;
        node6.next = node7;
        node7.next = node8;

        printCommonPart(head1, head2);

        Node node = reverseList(head1);
        System.out.print(node.value + "->");
        while (node.next != null) {
            System.out.print(node.next.value + "->");
            node = node.next;
        }
    }



    public static void printCommonPart(Node head1, Node head2) {
        System.out.println("相同部分:");
        while (head1 != null && head2 != null) {
            if (head1.value < head2.value) {
                head1 = head1.next;
            } else if (head2.value < head1.value) {
                head2 = head2.next;
            } else {
                System.out.println(head1.value);
                head1 = head1.next;
                head2 = head2.next;
            }
        }
    }

    public static Node reverseList(Node head) {
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
}

class Node {
    public int value;
    public Node next;

    public Node() {

    }

    public Node(int value) {
        this.value = value;
    }
}
