# easy-generator代码生成器

## 项目概述

本项目是一个基于Spring Boot的代码生成器，旨在帮助开发人员快速生成常见的Java代码，包括Controller、Mapper、Model、Service等，从而提高开发效率，减少重复劳动。

## 技术栈

- **Spring Boot**: 项目的基础框架，提供了便捷的配置和开发环境。
- **Freemarker模板引擎**: 用于生成代码的模板引擎，支持灵活的模板定义。
- **数据库连接**: 支持多种数据库连接，使项目适用于不同的数据库系统。
- **代码生成策略**: 支持自定义的代码生成策略，以满足不同项目的需求。

  

## 主要功能

### 1. 自动生成Controller

通过配置数据表信息，代码生成器可以自动生成RESTful风格的Controller，包括常见的增删改查操作，并提供了可扩展的自定义操作接口。

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 自动生成的GET请求处理方法
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        // ...
    }

    // 自动生成的POST请求处理方法
    @PostMapping("/")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        // ...
    }

    // 自动生成的PUT请求处理方法
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        // ...
    }

    // 自动生成的DELETE请求处理方法
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        // ...
    }
}
```

### 2. 自动生成Mapper

根据数据表结构，生成MyBatis或其他ORM框架的Mapper接口，包括常见的CRUD操作方法。

```java
@Mapper
public interface UserMapper {

    // 自动生成的查询方法
    UserDTO findById(Long id);

    // 自动生成的插入方法
    void insert(UserDTO userDTO);

    // 自动生成的更新方法
    void update(UserDTO userDTO);

    // 自动生成的删除方法
    void deleteById(Long id);
}
```

### 3. 自动生成Model

根据数据表结构，生成实体类模型，包括字段属性、Getter和Setter方法。

```java
public class UserDTO {

    private Long id;
    private String username;
    private String email;

    // 自动生成的Getter和Setter方法
}
```

### 4. 自动生成Service

生成Service接口和实现类，提供业务逻辑的封装和处理。

```java
public interface UserService {

    UserDTO findById(Long id);

    void createUser(UserDTO userDTO);

    void updateUser(UserDTO userDTO);

    void deleteUser(Long id);
}

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO findById(Long id) {
        // ...
    }

    // 其他自定义业务逻辑
}
```

## 使用方法

1. 配置数据库连接信息和数据表信息。
2. 选择生成的代码类型（Controller、Mapper、Model、Service等）。
3. 根据配置执行代码生成任务。
4. 自动生成的代码将保存在指定目录下，开发人员可以根据需要进行进一步的定制和调整。

## 总结

本项目提供了一个强大的代码生成工具，可以大大减少开发人员的工作量，加速项目开发进程。通过简单的配置，即可生成标准化的代码，使开发工作更加高效和规范。如果您正在寻找一个代码生成器来加速Spring Boot项目的开发，那么本项目将是一个不错的选择。

欢迎贡献和反馈，一起不断改进和完善这个工具！