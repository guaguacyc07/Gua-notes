> 用于复习开发常用且容易遗忘的知识
>
> 笔记由ai生成, 哪里缺了后面看的时候我再补上

# 1. 抽象类与接口

## 1.1 核心定义

**抽象类（abstract class）**

包含抽象方法的类叫抽象类，它是**不完整的类**，不能实例化，只能被继承。抽象类本质是「模板」，在继承体系中充当基类，把多个子类的共性逻辑抽取出来，共性方法直接实现，差异方法声明为抽象方法，由子类各自实现。

**接口（interface）**

接口是**行为的规范/契约**，只定义「能做什么」，不定义「怎么做」。接口中全部是抽象方法（Java 8 前），一个类可以实现多个接口，相当于承诺遵守多份行为契约。

------

## 1.2 语法对比

| 特性       | 抽象类 abstract class              | 接口 interface                                               |
| ---------- | ---------------------------------- | ------------------------------------------------------------ |
| 实例化     | 不能 new，只能被继承               | 不能 new，只能被类实现                                       |
| 构造方法   | 有构造方法，供子类调用             | 没有构造方法                                                 |
| 成员变量   | 可以有普通成员变量、常量           | 只能有 public static final 常量                              |
| 方法       | 可以有抽象方法、普通方法、静态方法 | Java 8 前：只能有 public abstract 抽象方法 Java 8+：可以有 default 默认方法、static 静态方法 |
| 继承/实现  | 单继承：一个类只能继承一个抽象类   | 多实现：一个类可以同时实现多个接口                           |
| 访问修饰符 | 四种权限都可以                     | 方法默认 public abstract，属性默认 public static final       |

------

## 1.3 代码示例

**场景：动物体系**

抽象类做动物的基类（模板），接口定义额外的行为能力。

```java
// 抽象类：动物基类（模板）
abstract class Animal {
    // 普通成员变量：共性属性
    protected String name;

    // 构造方法
    public Animal(String name) {
        this.name = name;
    }

    // 普通方法：共性行为，直接实现
    public void breath() {
        System.out.println(name + " 在呼吸");
    }

    // 抽象方法：差异行为，只声明不实现，子类必须重写
    public abstract void eat();
}

// 接口：飞行能力（行为契约）
interface Flyable {
    void fly();
}

// 接口：游泳能力
interface Swimable {
    void swim();
}
// 子类：狗，继承动物，实现游泳接口
class Dog extends Animal implements Swimable {
    public Dog(String name) {
        super(name);
    }

    // 必须重写抽象方法
    @Override
    public void eat() {
        System.out.println(name + " 啃骨头");
    }

    // 实现接口承诺的行为
    @Override
    public void swim() {
        System.out.println(name + " 狗刨式游泳");
    }
}

// 子类：鸽子，继承动物，实现飞行接口
class Pigeon extends Animal implements Flyable {
    public Pigeon(String name) {
        super(name);
    }

    @Override
    public void eat() {
        System.out.println(name + " 啄谷物");
    }

    @Override
    public void fly() {
        System.out.println(name + " 展翅飞翔");
    }
}

// 测试
public class Test {
    public static void main(String[] args) {
        Animal dog = new Dog("旺财");
        dog.breath(); // 旺财 在呼吸
        dog.eat();    // 旺财 啃骨头
        ((Swimable) dog).swim(); // 旺财 狗刨式游泳

        Animal pigeon = new Pigeon("小白");
        pigeon.breath();
        pigeon.eat();
        ((Flyable) pigeon).fly();
    }
}
```

------

## 1.4 核心区别与选型

- **抽象类是「是不是」的关系**：狗 **是** 动物，属于同一体系，用继承
- **接口是「有没有」的关系**：狗 **有** 游泳能力，鸽子 **有** 飞行能力，属于额外行为，用实现

> 选型原则：
>
> - 抽取模板、复用共性代码 → 用抽象类
> - 定义行为规范、支持多实现 → 用接口
> - 两者不冲突，可以组合使用

------

# 2. 枚举（enum）

## 2.1 本质

枚举是**特殊的 Java 类**，用来定义一组固定的常量实例。它比普通常量更安全、更强大，所有枚举类都隐式继承自 `java.lang.Enum`。

> 为什么不用 `public static final int` 常量？
>
> - 常量没有类型约束，随便传一个数字都能编译通过
> - 枚举是类型安全的，只能传入枚举中定义的实例，编译期就能检查错误

------

## 2.2 基础用法

最简单的枚举：只定义常量列表

```java
// 季节枚举
public enum Season {
    // 枚举实例，必须写在最前面，默认 public static final
    SPRING, SUMMER, AUTUMN, WINTER
}
```

使用：

```java
public class Test {
    public static void main(String[] args) {
        Season s = Season.SUMMER;
        System.out.println(s); // SUMMER

        // 常用方法1：获取所有枚举实例
        Season[] values = Season.values();

        // 常用方法2：字符串转枚举
        Season winter = Season.valueOf("WINTER");

        // 常用方法3：序号，从0开始
        System.out.println(s.ordinal()); // 1
    }
}
```

------

## 2.3 进阶用法：带属性、方法的枚举

枚举是类，可以有成员变量、构造方法、普通方法、甚至抽象方法。

**示例：订单状态枚举**

```java
public enum OrderStatus {
    // 枚举实例，调用构造方法传参
    WAIT_PAY(1, "待支付"),
    PAID(2, "已支付"),
    SHIPPED(3, "已发货"),
    FINISHED(4, "已完成"),
    CANCELLED(5, "已取消");

    // 成员变量
    private final int code;
    private final String desc;

    // 构造方法，默认 private
    OrderStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 普通方法：根据code找对应枚举
    public static OrderStatus getByCode(int code) {
        for (OrderStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }

    // getter
    public int getCode() { return code; }
    public String getDesc() { return desc; }
}
```

使用：

```java
public class Test {
    public static void main(String[] args) {
        OrderStatus status = OrderStatus.PAID;
        System.out.println(status.getCode()); // 2
        System.out.println(status.getDesc()); // 已支付

        OrderStatus finished = OrderStatus.getByCode(4);
        System.out.println(finished.getDesc()); // 已完成
    }
}
```

------

## 2.4 枚举 + 抽象方法

每个枚举实例可以各自实现抽象方法，实现不同的行为策略。

```java
public enum Operation {
    ADD {
        @Override
        public int apply(int a, int b) {
            return a + b;
        }
    },
    SUB {
        @Override
        public int apply(int a, int b) {
            return a - b;
        }
    },
    MUL {
        @Override
        public int apply(int a, int b) {
            return a * b;
        }
    };

    // 抽象方法
    public abstract int apply(int a, int b);
}
```

------

## 2.5 核心特点

1. **类型安全**：编译期检查，不能随便传值
2. **不可实例化**：构造方法默认 private，不能 new
3. **天然单例**：每个枚举实例全局只有一个，可以直接用 `==` 比较
4. **线程安全**：枚举实例由 JVM 保证初始化时线程安全
5. **推荐用法**：状态、类型、分类等固定常量集合，优先用枚举替代 int/String 常量

------

# 3. 反射（Reflection）

## 3.1 核心定义

反射是 Java 提供的一种**运行时动态操作类的能力**：在运行时，可以获取任意一个类的所有信息（构造方法、字段、方法），并且可以直接调用对象的方法、修改字段，哪怕是 private 的。

> 一句话：正常情况是「知道类名 → new 对象 → 调用方法」；反射是「运行时才拿到类 → 动态拆解调用」。 这是所有框架（Spring、MyBatis）的底层核心技术。

------

## 3.2 获取 Class 对象

一切反射操作的起点是 `java.lang.Class` 对象，它代表一个类的字节码信息。获取方式有三种：

```java
public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        // 方式1：类名.class（编译期就确定）
        Class<User> c1 = User.class;

        // 方式2：对象.getClass()
        User user = new User();
        Class<? extends User> c2 = user.getClass();

        // 方式3：Class.forName("全类名")（最常用，框架用）
        Class<?> c3 = Class.forName("com.example.User");
    }
}
```

> 三种方式同一个类只会生成一份 Class 对象，`c1 == c2 == c3` 结果为 true。

------

## 3.3 反射的核心操作

准备一个测试用的 User 类：

```java
public class User {
    private String name;
    public int age;

    public User() {}

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void sayHello() {
        System.out.println("大家好，我是" + name);
    }

    private void secretMethod() {
        System.out.println("这是私有方法");
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

### **1.反射创建对象**

```java
import java.lang.reflect.Constructor;

public class Test {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("com.example.User");

        // 1. 调用无参构造创建对象
        Object user1 = clazz.newInstance();

        // 2. 获取指定参数的构造方法
        Constructor<?> constructor = clazz.getConstructor(String.class, int.class);
        Object user2 = constructor.newInstance("张三", 20);
    }
}
```

### **2.反射操作字段**

```java
import java.lang.reflect.Field;

public class Test {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = User.class;
        Object user = clazz.getConstructor(String.class, int.class)
                          .newInstance("李四", 22);

        // 操作public字段
        Field ageField = clazz.getField("age");
        ageField.set(user, 25); // 修改值
        System.out.println(ageField.get(user)); // 25

        // 操作private字段（暴力反射）
        Field nameField = clazz.getDeclaredField("name");
        nameField.setAccessible(true); // 取消访问权限检查
        nameField.set(user, "王五");
        System.out.println(nameField.get(user)); // 王五
    }
}
```

### 3. 反射调用方法

```java
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = User.class;
        Object user = clazz.getConstructor(String.class, int.class)
                          .newInstance("赵六", 24);

        // 调用public方法
        Method sayHello = clazz.getMethod("sayHello");
        sayHello.invoke(user); // 执行方法：大家好，我是赵六

        // 调用private方法
        Method secret = clazz.getDeclaredMethod("secretMethod");
        secret.setAccessible(true);
        secret.invoke(user); // 这是私有方法
    }
}
```

------

## 3.4 反射的优缺点

| 优点                                     | 缺点                         |
| ---------------------------------------- | ---------------------------- |
| 运行时动态操作，灵活性极强，是框架的基础 | 性能比直接调用差，有解析开销 |
| 可以突破权限限制，操作私有成员           | 破坏了封装性，有安全风险     |
| 解耦，代码不用硬编码类名                 | 代码可读性差，调试困难       |

## 3.5 典型应用场景

- Spring IOC：根据配置的类名，反射创建 Bean 对象
- MyBatis：映射结果集时，反射给对象字段赋值
- 注解解析：运行时读取方法/类上的注解
- 各种通用工具类

------

# 4. 注解（Annotation）

## 4.1 本质

注解是贴在类、方法、字段上的**元数据标签**，本身不具备任何功能，只是标记信息。具体功能由解析代码（通常用反射实现）来赋予。

> 一句话：注解 = 标记。贴了标记，解析器看到标记就做对应的处理。

------

## 4.2 五大元注解

元注解是「用来定义注解的注解」，用来规定自定义注解的特性。

| 元注解        | 作用                       | 常用取值                                                     |
| ------------- | -------------------------- | ------------------------------------------------------------ |
| `@Target`     | 注解能贴在什么地方         | `TYPE`（类/接口）、`METHOD`（方法）、`FIELD`（字段）、`PARAMETER`（参数） |
| `@Retention`  | 注解保留到什么时候         | `SOURCE`（源码级，编译后丢弃） `CLASS`（字节码级，默认） `RUNTIME`（运行时保留，可反射读取，最常用） |
| `@Documented` | 生成 javadoc 时包含注解    | -                                                            |
| `@Inherited`  | 子类可以继承父类的注解     | -                                                            |
| `@Repeatable` | 同一个位置可以重复贴该注解 | -                                                            |

------

## 4.3 自定义注解完整示例

**步骤1：定义注解**

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 自定义注解：日志标记
@Target(ElementType.METHOD)       // 只能贴在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时保留，反射才能读到
public @interface Log {
    // 注解属性，类似方法
    String value() default "";   // 操作描述，默认空字符串
    boolean printTime() default true; // 是否打印耗时
}
```

**步骤2：使用注解**

```java
public class UserService {
    // 贴注解，传属性值
    @Log(value = "新增用户", printTime = true)
    public void addUser(String name) {
        System.out.println("新增用户：" + name);
    }

    @Log("删除用户")
    public void deleteUser(int id) {
        System.out.println("删除用户：" + id);
    }
}
```

**步骤3：反射解析注解（注解的功能来源）**

```java
import java.lang.reflect.Method;

public class AnnotationParser {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = UserService.class;
        Object service = clazz.newInstance();

        // 遍历所有方法
        for (Method method : clazz.getDeclaredMethods()) {
            // 判断方法上有没有 @Log 注解
            if (method.isAnnotationPresent(Log.class)) {
                // 获取注解对象
                Log logAnnotation = method.getAnnotation(Log.class);

                long start = System.currentTimeMillis();

                // 执行原方法
                method.invoke(service, "张三");

                long end = System.currentTimeMillis();

                // 注解的功能：打印日志
                System.out.println("【日志】操作：" + logAnnotation.value());
                if (logAnnotation.printTime()) {
                    System.out.println("耗时：" + (end - start) + "ms");
                }
            }
        }
    }
}
```

------

## 4. 常见内置注解

| 注解                   | 作用                             |
| ---------------------- | -------------------------------- |
| `@Override`            | 标记方法重写父类方法，编译期检查 |
| `@Deprecated`          | 标记方法/类已过时，调用会警告    |
| `@SuppressWarnings`    | 抑制编译器警告                   |
| `@FunctionalInterface` | 标记函数式接口                   |

------

## 5. 核心总结

- 注解本身只是标记，没有任何逻辑
- 功能由解析器（反射读取注解 + 执行对应逻辑）提供
- `@Retention(RUNTIME)` 是运行时能读取注解的前提
- Spring 中大量使用注解（`@Autowired`、`@RequestMapping`、`@Transactional`），底层都是这个原理

------

# 5. 泛型（Generics）

## 5.1 本质

泛型就是**参数化类型**：把类型也变成一个参数，在使用时再指定具体类型。让同一份代码可以适配多种类型，同时保证类型安全。

> 没有泛型之前，用 Object 存任意类型，取出来要强转，运行时才会报错； 有了泛型，编译期就能检查类型，不需要强转，更安全。

------

## 5.2 三种泛型形式

### 1. 泛型类

```java
// 泛型类，T是类型参数
public class Box<T> {
    private T data;

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
```

使用：

```java
public class Test {
    public static void main(String[] args) {
        // 存字符串
        Box<String> strBox = new Box<>();
        strBox.setData("hello");
        String s = strBox.getData(); // 不用强转

        // 存整数
        Box<Integer> intBox = new Box<>();
        intBox.setData(123);
        Integer i = intBox.getData();
    }
}
```

### 2. 泛型方法

```java
public class ArrayUtil {
    // 泛型方法，泛型参数定义在返回值前
    public static <T> void printArray(T[] array) {
        for (T t : array) {
            System.out.println(t);
        }
    }
}
```

使用：

```java
String[] strs = {"a", "b", "c"};
Integer[] ints = {1, 2, 3};

ArrayUtil.printArray(strs);
ArrayUtil.printArray(ints);
```

### 3. 泛型接口

```java
public interface Dao<T> {
    void add(T t);
    T getById(int id);
}

// 实现时指定具体类型
class UserDao implements Dao<User> {
    @Override
    public void add(User user) { ... }

    @Override
    public User getById(int id) { ... }
}
```

------

## 5.3 通配符 `?`

当不确定泛型类型，或者需要支持多种类型时，使用通配符 `?`。

### 1. 无界通配符 `?`

可以接收任意泛型类型

```java
public static void printBox(Box<?> box) {
    // 只能读，不能写（因为不知道具体类型）
    System.out.println(box.getData());
}
```

### 2. 上界通配符 `? extends T`

只能接收 T 或 T 的子类，**生产者场景（读）**

```java
// 只能接收 Number 及其子类
public static double sum(Box<? extends Number> box) {
    Number num = box.getData();
    return num.doubleValue();
}
```

### 3. 下界通配符 `? super T`

只能接收 T 或 T 的父类，**消费者场景（写）**

```java
// 只能接收 Integer 及其父类
public static void setValue(Box<? super Integer> box) {
    box.setData(100);
}
```

> 记忆口诀：PECS 原则
>
> - Producer Extends：生产（读取数据）用 extends
> - Consumer Super：消费（写入数据）用 super

------

## 5.4 类型擦除

泛型是**编译期现象**，编译成字节码后，泛型参数会被擦掉，替换成：

- 无界泛型 → 替换成 Object
- 有上界 → 替换成上界类型

```java
// 源码
public class Box<T> {
    private T data;
}

// 编译擦除后
public class Box {
    private Object data;
}
```

> 这就是为什么「泛型不能 new T()」「不能用 instanceof 判断泛型」「基本类型不能做泛型参数」的根本原因——运行时根本没有 T 这个类型。

------

## 5.5 泛型的好处

1. **类型安全**：编译期检查类型错误，不用等到运行时
2. **代码复用**：一套逻辑适配多种类型，不用重复写
3. **避免强转**：代码更简洁，减少 ClassCastException
4. **可读性好**：一眼就能看出集合/容器存的是什么类型

------

## 5.6 常见误区

- ❌ 泛型是运行时技术 → ✅ 是编译期技术，运行时已擦除
- ❌ `List<String>` 是 `List<Object>` 的子类 → ✅ 没有继承关系，完全不同的类型
- ❌ 可以 `new T()` → ✅ 类型擦除后不知道 T 是什么，无法实例化

---

