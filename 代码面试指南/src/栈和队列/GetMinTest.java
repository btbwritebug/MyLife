package 栈和队列;

import java.util.Stack;

/**
 * 设计一个有getMin功能的栈
 */
public class GetMinTest {
}

class MyStack1 {
    /**
     * 栈1存放数字,栈2存放最小值
     */
    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;

    public MyStack1() {
        this.stackData = new Stack<>();
        this.stackMin = new Stack<>();
    }

    public void push(int i) {
        if (this.stackMin.empty()) {
            this.stackMin.push(i);
        } else if (i < getMin()) {
            this.stackMin.push(i);
        }
        this.stackData.push(i);
    }

    public int pop() {
        if (this.stackData.empty()) {
            throw new RuntimeException("Your stack is empty!");
        }
        int value = this.stackData.pop();
        if (value == this.getMin()) {
            this.stackMin.pop();
        }
        return value;
    }

    private int getMin() {
        if (stackMin.empty()) {
            throw new RuntimeException("Your stack is empty!");
        }
        return stackMin.peek();
    }


}
