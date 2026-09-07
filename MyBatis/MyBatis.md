# MyBatis

# 1. MyBatis概述

- MyBatis本质上就是对JDBC的封装，通过MyBatis完成CRUD。
- MyBatis在三层架构中负责持久层的，属于持久层框架。
- ORM：对象关系映射
  - **O（Object）**：Java虚拟机中的Java对象
  - **R（Relational）**：关系型数据库
  - **M（Mapping）**：将Java虚拟机中的Java对象映射到数据库表中一行记录，或是将数据库表中一行记录映射成Java虚拟机中的一个Java对象。

![image-20260907131826481](./img/MyBatis.assets/image-20260907131826481.png)

---

# 2. MyBatis 入门程序

1. **resources目录：**

放在这个目录当中的，一般都是资源文件，配置文件。
直接放到resources目录下的资源，等同于放到了类的根路径下。

2. **开发步骤:**

* 第一步: 打包方式jar
* 第二步: 引入依赖
  * **mybatis依赖**
  * **mysql驱动依赖**
* 第三步: 编写 mybatis 核心配置文件: **mybatis-config.xml**

  **注意:**

  - 第一: 这个文件名不是必须叫做**mybatis-config.xml**，可以用其他的名字。只是大家都采用这个名字。
  - 第二: 这个文件存放的位置也不是固定的，可以随意，但一般情况下，会放到类的根路径下。

  **mybatis-config.xml**文件中的配置信息不理解没关系，先把连接数据库的信息修改即可。其他的别动。

* 第四步: 编写**XxxxMapper.xml**文件

  * 在这个配置文件当中编写SQL语句。
  * 这个文件名也不是固定的，放的位置也不是固定，我们这里给它起个名字，叫做：CarMapper.xml
  * 把它暂时放到类的根路径下。

* 第五步: 在**mybatis-config.xml**文件中指定**XxxxMapper.xml**文件的路径：

  ```xml
  <mapper resource="CarMapper.xml"/>
  ```

  注意：**resource**属性会自动从类的根路径下开始查找资源。

* 第六步: 编写**MyBatis**程序。（使用**mybatis**的类库，编写**mybatis**程序，连接数据库，做增删改查就行了。）

  **在MyBatis当中，负责执行SQL语句的那个对象叫做什么呢？**
  
  - **SqlSession**
  
  `SqlSession`是专门用来执行SQL语句的，是一个Java程序和数据库之间的一次会话。
  
  要想获取 `SqlSession` 对象，需要先获取 `SqlSessionFactory` 对象，通过 `SqlSessionFactory` 工厂来生产 `SqlSession` 对象。
  
  **怎么获取 `SqlSessionFactory` 对象呢？**
  
  - 需要首先获取 `SqlSessionFactoryBuilder` 对象。
  - 通过 `SqlSessionFactoryBuilder` 对象的 `build` 方法，来获取一个 `SqlSessionFactory` 对象。
  
  **mybatis的核心对象包括：**
  - `SqlSessionFactoryBuilder`
  - `SqlSessionFactory`
  - `SqlSession`
  
  `SqlSessionFactoryBuilder --> SqlSessionFactory --> SqlSession`

---

3. **从 XML 中构建 SqlSessionFactory**

通过官方的这句话，你能想到什么呢？
- 第一: 在 MyBatis 中一定是有一个很重要的对象，这个对象是: `SqlSessionFactory`对象。
- 第二: `SqlSessionFactory`对象的创建需要 XML。

**XML是什么？**

- 它一定是一个配置文件。

---

4. **mybatis中有两个主要的配置文件：**

其中一个是：**mybatis-config.xml**，这是核心配置文件，主要配置连接数据库的信息等。（一个）

另一个是：**XxxxMapper.xml**，这个文件是专门用来编写SQL语句的配置文件。（一个表一个）
- `t_user`表，一般会对应一个 `UserMapper.xml`
- `t_student` 表，一般会对应一个 `StudentMapper.xml`

---

5. **关于第一个程序的小细节**

* mybatis中sql语句的结尾`;`可以省略。

* **Resources.getResourceAsStream**

  小技巧：以后凡是遇到resource这个单词，大部分情况下，这种加载资源的方式就是从类的根路径下开始加载。（开始查找）

  优点：采用这种方式，从类路径当中加载资源，项目的移植性很强。项目从windows移植到linux，代码不需要修改，因为这个资源文件一直都在类路径当中。

* `InputStream is = new FileInputStream("d:\\mybatis-config.xml");`

  采用这种方式也可以。

  缺点：可移植性太差，程序不够健壮。可能会移植到其他的操作系统当中。导致以上路径无效，还需要修改java代码中的路径。这样违背了OCP原则。

* **已经验证了：**

  mybatis核心配置文件的名字，不一定是：`mybatis-config.xml`。可以是其它名字。

  mybatis核心配置文件存放的路径，也不一定是在类的根路径下。可以放到其它位置。但为了项目的移植性，健壮性，最好将这个配置文件放到类路径下面。

* `InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");`

  `ClassLoader.getSystemClassLoader()` 获取系统的类加载器。

  系统类加载器有一个方法叫做：`getResourceAsStream`

  它就是从类路径当中加载资源的。

  通过源代码分析发现：

  `InputStream is = Resources.getResourceAsStream("mybatis-config.xml");`

  底层的源代码其实就是：

  `InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");`

* **CarMapper.xml文件的名字是固定的吗？CarMapper.xml文件的路径是固定的吗？**

  都不是固定的。

  `<mapper resource="CarMapper.xml"/>` resource属性：这种方式是从类路径当中加载资源。

  `<mapper url="file:///d:/CarMapper.xml"/>` url属性：这种方式是从绝对路径当中加载资源。

---
