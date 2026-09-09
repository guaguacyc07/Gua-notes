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

# 2. MyBatis入门程序

+ 步骤1：打包方式：jar（<font style="color:#F5222D;">不需要war，因为mybatis封装的是jdbc。</font>）

```xml
<groupId>com.powernode</groupId>
<artifactId>mybatis-001-introduction</artifactId>
<version>1.0-SNAPSHOT</version>
<packaging>jar</packaging>
```

+ 步骤2：引入依赖（mybatis依赖 + mysql驱动依赖）

```xml
<!--mybatis核心依赖-->
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis</artifactId>
  <version>3.5.10</version>
</dependency>
<!--mysql驱动依赖-->
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.30</version>
</dependency>
```

+ 步骤3：在resources根目录下新建mybatis-config.xml配置文件（可以参考mybatis手册拷贝）

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/powernode"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!--sql映射文件创建好之后，需要将该文件路径配置到这里-->
        <mapper resource=""/>
    </mappers>
</configuration>
```

注意1：mybatis核心配置文件的文件名不一定是mybatis-config.xml，可以是其它名字。

注意2：mybatis核心配置文件存放的位置也可以随意。这里选择放在resources根下，相当于放到了类的根路径下。

+ 步骤4：在resources根目录下新建CarMapper.xml配置文件（可以参考mybatis手册拷贝）

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace先随意写一个-->
<mapper namespace="car">
    <!--insert sql：保存一个汽车信息-->
    <insert id="insertCar">
        insert into t_car
            (id,car_num,brand,guide_price,produce_time,car_type) 
        values
            (null,'102','丰田mirai',40.30,'2014-10-05','氢能源')
    </insert>
</mapper>
```

注意1：**<font style="color:#E8323C;">sql语句最后结尾可以不写“;”</font>**

注意2：CarMapper.xml文件的名字不是固定的。可以使用其它名字。

注意3：CarMapper.xml文件的位置也是随意的。这里选择放在resources根下，相当于放到了类的根路径下。

注意4：<font style="color:#F5222D;">将CarMapper.xml文件路径配置到mybatis-config.xml：</font>

```xml
<mapper resource="CarMapper.xml"/>
```

+ 步骤5：编写MyBatisIntroductionTest代码

```java
package com.powernode.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * MyBatis入门程序
 * @author 老杜
 * @since 1.0
 * @version 1.0
 */
public class MyBatisIntroductionTest {
    public static void main(String[] args) {
        // 1. 创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 2. 创建SqlSessionFactory对象
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        // 3. 创建SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4. 执行sql
        int count = sqlSession.insert("insertCar"); // 这个"insertCar"必须是sql的id
        System.out.println("插入几条数据：" + count);
        // 5. 提交（mybatis默认采用的事务管理器是JDBC，默认是不提交的，需要手动提交。）
        sqlSession.commit();
        // 6. 关闭资源（只关闭是不会提交的）
        sqlSession.close();
    }
}
```

注意1：默认采用的事务管理器是：JDBC。JDBC事务默认是不提交的，需要手动提交。

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

# 3. MyBatis事务管理机制

* **在`mybatis-config.xml`文件中，可以通过以下的配置进行mybatis的事务管理：**

  ```xml
  <transactionManager type="JDBC"/>
  ```

* **type属性的值包括两个：**
  
  * `JDBC`(jdbc)
  * `MANAGED`(managed)
  
  type后面的值，只有以上两个值可选，不区分大小写。
  
* **在mybatis中提供了两种事务管理机制：**
  
  * 第一种：JDBC事务管理器
  
  * 第二种：MANAGED事务管理器
  
- **JDBC事务管理器：**

mybatis框架自己管理事务，自己采用原生的JDBC代码去管理事务：

```java
conn.setAutoCommit(false); // 开启事务
....业务处理...
conn.commit(); // 手动提交事务
```

使用JDBC事务管理器的话，底层创建的事务管理器对象：`JdbcTransaction`对象。

如果你编写的代码是下面的代码：

```java
SqlSession sqlSession = sqlSessionFactory.openSession(true);
```

表示没有开启事务。因为这种方式压根不会执行：`conn.setAutoCommit(false);`

在JDBC事务中，没有执行`conn.setAutoCommit(false);`那么autoCommit就是true。

如果autoCommit是true，就表示没有开启事务。只要执行任意一条DML语句就提交一次。

- **MANAGED事务管理器：**

mybatis不再负责事务的管理了。事务管理交给其它容器来负责。例如：`spring`。

我不管事务了，你来负责吧。

对于当前的单纯的只有mybatis的情况下，如果配置为：`MANAGED`

那么事务这块是没人管的。没有人管理事务表示事务压根没有开启。

- **JDBC中的事务：**

如果你没有在JDBC代码中执行：`conn.setAutoCommit(false);`的话，默认的`autoCommit`是`true`。

- **重点：**

以后注意了，只要你的autoCommit是true，就表示没有开启事务。

只有你的autoCommit是false的时候，就表示开启了事务。

---
