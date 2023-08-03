package 栈和队列;

import java.util.Stack;

public class TwoStacksQueue {
    public Stack<Integer> stackPush;
    public Stack<Integer> stackPop;

    public TwoStacksQueue() {
        this.stackPush = new Stack<>();
        this.stackPop = new Stack<>();
    }

    /**
     * push栈向pop栈倒入队列
     */
    public void pushToPop() {
        if (stackPop.empty()) {
            while (!stackPush.empty()) {
                stackPop.push(stackPush.pop());
            }

        }
    }

    /**
     * 入队
     */
    public void add(int i) {
        stackPush.push(i);
        pushToPop();
    }

    /**
     * 出队
     */
    public int poll() {
        if (stackPush.empty() && stackPop.empty()) {
            throw new RuntimeException("Queue is empty");
        }
        pushToPop();
        return stackPop.pop();
    }

    /**
     * 瞄一眼
     */
    public int peek() {
        if (stackPush.empty() && stackPop.empty()) {
            throw new RuntimeException("Queue is empty");
        }
        pushToPop();
        return stackPop.peek();
    }
}
