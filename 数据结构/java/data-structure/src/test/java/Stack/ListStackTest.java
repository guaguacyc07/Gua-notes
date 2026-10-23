package Stack;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class ListStackTest {
    @Test
    public void testListStack() {
        /* 初始化一个栈 */
        ListStack<Integer> stack = new ListStack<>();
        /* 入栈 */
        stack.push(1);
        stack.push(2);
        stack.push(3);
        /* 出栈 */
        log.info("栈顶元素: {}", stack.pop());
        /* 访问栈顶元素 */
        log.info("栈顶元素: {}", stack.peek());
        /* 获取栈的长度 */
        log.info("栈的长度: {}", stack.size());
        /* 判断栈是否为空 */
        log.info("栈是否为空: {}", stack.isEmpty());
    }
}
