package 栈和队列;

import java.util.Stack;

/**
 * 用递归函数和一个栈反转一个栈
 */
public class Reverse {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack);
        System.out.println("反转后stack");
        Reverse.reverse(stack);
        System.out.println(stack);
    }

    public static int getAndRemoveLastElement(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        } else {
            int last = getAndRemoveLastElement(stack);
            stack.push(result);
            return last;
        }
    }

    public static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int i = getAndRemoveLastElement(stack);
        System.out.println(i);
        reverse(stack);
        stack.push(i);
    }
}
