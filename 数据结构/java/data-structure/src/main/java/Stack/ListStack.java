package Stack;

import List.ListNode;

/* 基于链表实现的栈 */
public class ListStack<T> {
    private ListNode<T> stackPeek; // 将头节点作为栈顶
    private int stkSize = 0; // 栈的长度
    
    public ListStack() {
        stackPeek = null;
    }
    /* 获取栈的长度 */
    public int size() {
        return stkSize;
    }
    
    /* 判断栈是否为空 */
    public boolean isEmpty() {
        return size() == 0;
    }
    
    /* 入栈 */
    public void push(T val) {
        ListNode<T> node = new ListNode<>(val);
        node.next = stackPeek;
        stackPeek = node;
        stkSize++;
    }
    
    /* 出栈 */
    public T pop() {
        T val = peek();
        stackPeek = stackPeek.next;
        stkSize--;
        return val;
    }
    
    /* 访问栈顶元素 */
    public T peek() {
        if (isEmpty())
            throw new IndexOutOfBoundsException();
        return stackPeek.val;
    }
    
    /* 将 List 转化为 Array 并返回 */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        ListNode<T> node = stackPeek;
        T[] res = (T[]) new Object[size()];
        for (int i = res.length - 1; i >= 0; i--) {
            res[i] = node.val;
            node = node.next;
        }
        return res;
    }
}
