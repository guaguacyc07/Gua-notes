package List;

/* 链表节点类 */
public class ListNode<T> {
    public T val;        // 节点值
    public ListNode<T> next;  // 指向下一节点的引用
    
    public ListNode() {}   // 构造函数
    public ListNode(T x) { val = x; }
}