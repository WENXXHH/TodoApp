```markdown
# 待办事项APP项目设计文档（传统XML布局 + Kotlin方案）

根据文件中的要求，考虑到你已经学习了《第一行代码》的基础知识，我为你设计了一个基于传统XML布局
和Kotlin开发的Android项目结构。这个方案结合了传统布局的熟悉度和Kotlin的现代语法，非常适合
初学者和比赛项目。

## 📁 项目目录结构

```
TodoApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/todoapp/
│   │   │   │   ├── **data/**
│   │   │   │   │   ├── database/
│   │   │   │   │   │   ├── AppDatabase.kt           # Room数据库定义和初始化
│   │   │   │   │   │   ├── TodoDao.kt               # 待办事项数据访问接口
│   │   │   │   │   │   └── UserDao.kt               # 用户数据访问接口
│   │   │   │   │   ├── model/
│   │   │   │   │   │   ├── TodoItem.kt              # 待办事项数据模型
│   │   │   │   │   │   ├── User.kt                  # 用户数据模型
│   │   │   │   │   │   └── Reminder.kt              # 提醒数据模型
│   │   │   │   │   └── repository/
│   │   │   │   │       ├── TodoRepository.kt        # 待办事项业务逻辑封装
│   │   │   │   │       └── UserRepository.kt        # 用户业务逻辑封装
│   │   │   │   │
│   │   │   │   ├── **utils/**
│   │   │   │   │   ├── NotificationHelper.kt       # 通知管理工具类
│   │   │   │   │   ├── DateTimeUtils.kt            # 日期时间格式化工具
│   │   │   │   │   ├── NetworkUtils.kt             # 网络状态检查工具
│   │   │   │   │   └── PreferenceManager.kt         # SharedPreferences封装管理
│   │   │   │   │
│   │   │   │   ├── **adapter/**
│   │   │   │   │   ├── TodoAdapter.kt              # 待办事项列表适配器
│   │   │   │   │   ├── CategoryAdapter.kt          # 分类列表适配器
│   │   │   │   │   └── UserAdapter.kt               # 用户列表适配器
│   │   │   │   │
│   │   │   │   ├── **base/**
│   │   │   │   │   ├── BaseActivity.kt             # 基础Activity封装（通用功能）
│   │   │   │   │   └── BaseFragment.kt             # 基础Fragment封装（通用功能）
│   │   │   │   │
│   │   │   │   ├── **activity/**
│   │   │   │   │   ├── MainActivity.kt             # 主界面Activity（底部导航容器）
│   │   │   │   │   ├── LoginActivity.kt            # 登录界面Activity
│   │   │   │   │   ├── RegisterActivity.kt         # 注册界面Activity
│   │   │   │   │   ├── AddTodoActivity.kt          # 添加待办事项Activity
│   │   │   │   │   ├── EditTodoActivity.kt         # 编辑待办事项Activity
│   │   │   │   │   └── ProfileActivity.kt          # 用户信息界面Activity
│   │   │   │   │
│   │   │   │   ├── **fragment/**
│   │   │   │   │   ├── HomeFragment.kt             # 首页Fragment（待办事项列表）
│   │   │   │   │   ├── CategoryFragment.kt         # 分类管理Fragment
│   │   │   │   │   ├── ProfileFragment.kt          # 个人中心Fragment
│   │   │   │   │   └── SettingsFragment.kt         # 设置管理Fragment
│   │   │   │   │
│   │   │   │   ├── **widget/**
│   │   │   │   │   ├── TodoItemView.kt             # 自定义待办事项视图组件
│   │   │   │   │   └── CategoryChip.kt             # 自定义分类标签组件
│   │   │   │   │
│   │   │   │   ├── **service/**
│   │   │   │   │   ├── ReminderService.kt          # 后台提醒服务（本地通知）
│   │   │   │   │   └── BootReceiver.kt             # 开机启动广播接收器
│   │   │   │   │
│   │   │   │   └── **application/**
│   │   │   │       └── TodoApplication.kt          # Application类（全局初始化）
│   │   │   │
│   │   │   ├── res/
│   │   │   │   ├── **layout/**                      # XML布局文件目录
│   │   │   │   │   ├── activity_main.xml            # 主界面布局（底部导航框架）
│   │   │   │   │   ├── activity_login.xml           # 登录界面布局
│   │   │   │   │   ├── activity_register.xml        # 注册界面布局
│   │   │   │   │   ├── activity_add_todo.xml        # 添加待办事项布局
│   │   │   │   │   ├── activity_edit_todo.xml       # 编辑待办事项布局
│   │   │   │   │   ├── activity_profile.xml         # 用户信息界面布局
│   │   │   │   │   ├── fragment_home.xml            # 首页Fragment布局
│   │   │   │   │   ├── fragment_category.xml        # 分类Fragment布局
│   │   │   │   │   ├── fragment_profile.xml         # 个人中心Fragment布局
│   │   │   │   │   ├── fragment_settings.xml        # 设置Fragment布局
│   │   │   │   │   ├── item_todo.xml                # 待办事项列表项布局
│   │   │   │   │   ├── item_category.xml            # 分类列表项布局
│   │   │   │   │   ├── dialog_reminder.xml          # 提醒设置对话框布局
│   │   │   │   │   ├── dialog_category.xml          # 分类选择对话框布局
│   │   │   │   │   └── toolbar_main.xml             # 通用工具栏布局
│   │   │   │   │
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml                  # 字符串资源（多语言支持）
│   │   │   │   │   ├── colors.xml                   # 颜色资源定义
│   │   │   │   │   ├── dimens.xml                   # 尺寸资源定义
│   │   │   │   │   ├── styles.xml                   # 样式资源定义
│   │   │   │   │   ├── themes.xml                   # 主题资源定义
│   │   │   │   │   └── arrays.xml                   # 数组资源定义
│   │   │   │   │
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── ic_launcher_background.xml   # 启动图标背景
│   │   │   │   │   ├── ic_add.xml                   # 添加图标
│   │   │   │   │   ├── ic_delete.xml                # 删除图标
│   │   │   │   │   ├── ic_edit.xml                  # 编辑图标
│   │   │   │   │   ├── ic_check.xml                 # 完成图标
│   │   │   │   │   ├── ic_calendar.xml              # 日历图标
│   │   │   │   │   ├── ic_time.xml                  # 时间图标
│   │   │   │   │   ├── ic_user.xml                  # 用户图标
│   │   │   │   │   ├── ic_logout.xml                # 退出图标
│   │   │   │   │   ├── bg_button_primary.xml        # 主要按钮背景样式
│   │   │   │   │   ├── bg_todo_item.xml             # 待办事项项背景样式
│   │   │   │   │   └── bg_category_chip.xml         # 分类标签背景样式
│   │   │   │   │
│   │   │   │   ├── mipmap/                          # 应用图标资源
│   │   │   │   │   ├── ic_launcher.png              # 启动图标
│   │   │   │   │   └── ic_launcher_round.png        # 圆形启动图标
│   │   │   │   │
│   │   │   │   ├── menu/
│   │   │   │   │   ├── bottom_nav_menu.xml          # 底部导航菜单定义
│   │   │   │   │   ├── toolbar_menu.xml             # 工具栏菜单定义
│   │   │   │   │   └── todo_item_menu.xml           # 待办事项项菜单定义
│   │   │   │   │
│   │   │   │   └── anim/
│   │   │   │       ├── slide_in_right.xml           # 右滑进入动画
│   │   │   │       ├── slide_out_left.xml           # 左滑退出动画
│   │   │   │       └── fade_in.xml                  # 淡入动画
│   │   │   │
│   │   │   ├── assets/                              # 静态资源文件
│   │   │   │   └── categories.json                  # 默认分类数据
│   │   │   │
│   │   │   └── AndroidManifest.xml                  # 应用清单文件（权限和组件注册）
│   │   │
│   │   └── java/com/example/todoapp/worker/        # 后台任务处理
│   │       └── ReminderWorker.kt                    # 提醒后台任务处理
│   │
│   ├── build.gradle.kts                             # 模块构建脚本（Kotlin DSL）
│   └── ...
│
├── build.gradle.kts                                 # 项目构建脚本（Kotlin DSL）
├── settings.gradle.kts                              # 项目设置脚本（Kotlin DSL）
└── README.md                                        # 项目说明文档
```

## 📋 实现顺序和文件功能

### 🚀 第一阶段：基础框架搭建（1-2天）  ✔

1. **`build.gradle.kts`** (项目级) - 配置项目构建环境  ✔
   - 配置Kotlin插件和Android Gradle插件版本
   - 设置仓库地址（Google、Maven Central）
   - 定义项目级依赖管理

2. **`app/build.gradle.kts`** - 配置模块依赖  ✔
   - 配置Android SDK版本和编译选项
   - 添加核心依赖：AndroidX、Material Design、Room、WorkManager
   - 配置Kotlin Android扩展插件

3. **`AndroidManifest.xml`** - 应用基础配置   ✔
   - 声明应用名称、图标、主题
   - 配置必要权限：通知权限、网络权限、开机启动权限
   - 注册Activity、Service、BroadcastReceiver组件

4. **`res/values/strings.xml`** - 基础字符串资源  ✔
   - 定义应用名称、通用按钮文本
   - 配置界面标题、提示信息
   - 为多语言支持预留基础

5. **`res/values/colors.xml`** - 应用颜色主题  ✔
   - 定义主色、辅色、强调色
   - 配置状态栏、导航栏颜色
   - 定义完成状态的特殊颜色

6. **`TodoApplication.kt`** - 全局初始化  ✔
   - 初始化Room数据库实例
   - 创建通知渠道
   - 设置全局异常处理
   - 初始化第三方库

### 🔐 第二阶段：用户认证系统（2-3天）  ✔

7. **`User.kt`** - 用户数据模型  ✔
   - 定义用户基本信息字段
   - 实现数据验证逻辑
   - 提供数据转换方法

8. **`UserDao.kt`** - 用户数据访问接口  ✔
   - 定义用户增删改查操作
   - 实现登录验证查询
   - 用户名唯一性检查

9. **`UserRepository.kt`** - 用户业务逻辑  ✔
   - 封装用户注册流程
   - 实现登录认证逻辑
   - 处理用户信息更新

10. **`activity_login.xml`** - 登录界面布局  ✔
    - 设计用户名密码输入区域
    - 布局登录按钮和注册跳转
    - 添加输入验证提示

11. **`LoginActivity.kt`** - 登录界面逻辑  ✔
    - 处理用户输入验证
    - 调用Repository进行认证
    - 成功后跳转主界面
    - 失败时显示错误提示

12. **`activity_register.xml`** - 注册界面布局  ✔
    - 设计多字段输入表单
    - 布局密码确认区域
    - 添加注册按钮

13. **`RegisterActivity.kt`** - 注册界面逻辑  ✔
    - 实现表单验证（邮箱格式、密码强度）
    - 处理重复密码确认
    - 调用Repository完成注册
    - 注册成功后自动登录

### 🏠 第三阶段：主界面和底部导航（2天）

14. **`activity_main.xml`** - 主界面布局
    - 设计底部导航栏容器
    - 布局Fragment容器区域
    - 集成工具栏

15. **`bottom_nav_menu.xml`** - 底部导航菜单
    - 定义三个导航项：首页、分类、我的
    - 配置图标和文字
    - 设置默认选中项

16. **`MainActivity.kt`** - 主界面逻辑
    - 初始化底部导航
    - 管理Fragment切换
    - 处理返回键逻辑
    - 检查登录状态

17. **`fragment_home.xml`** - 首页Fragment布局
    - 设计RecyclerView列表
    - 添加FloatingActionButton
    - 集成下拉刷新
    - 空数据提示布局

18. **`HomeFragment.kt`** - 首页Fragment逻辑
    - 初始化RecyclerView适配器
    - 加载待办事项数据
    - 处理添加事项点击
    - 监听数据变化更新UI

### ✅ 第四阶段：待办事项核心功能（3-4天）

19. **`TodoItem.kt`** - 待办事项模型
    - 定义事项核心字段
    - 实现状态管理（完成/未完成）
    - 提供时间格式化方法
    - 支持置顶功能标记

20. **`TodoDao.kt`** - 待办事项数据访问
    - 定义CRUD操作接口
    - 实现按时间排序查询
    - 添加置顶事项优先查询
    - 获取需要提醒的事项

21. **`TodoRepository.kt`** - 待办实事逻辑
    - 封装数据操作
    - 处理事项状态更新
    - 管理提醒设置
    - 实现搜索过滤功能

22. **`item_todo.xml`** - 事项列表项布局
    - 设计卡片式UI
    - 布局标题、描述、时间
    - 集成完成状态复选框
    - 添加编辑删除按钮

23. **`TodoAdapter.kt`** - 事项列表适配器
    - 绑定数据到视图
    - 处理事项完成状态切换
    - 实现项点击和长按
    - 支持动态更新数据

24. **`activity_add_todo.xml`** - 添加事项布局
    - 设计表单输入区域
    - 集成日期时间选择器
    - 布局分类选择下拉
    - 添加置顶开关和保存按钮

25. **`AddTodoActivity.kt`** - 添加事项逻辑
    - 初始化表单字段
    - 处理日期时间选择
    - 验证输入数据
    - 保存事项到数据库

26. **`EditTodoActivity.kt`** - 编辑事项逻辑
    - 加载现有事项数据
    - 支持修改所有字段
    - 处理事项删除
    - 保存更新到数据库

### 🔔 第五阶段：通知和提醒功能（1-2天）

27. **`NotificationHelper.kt`** - 通知管理
    - 创建通知渠道
    - 构建提醒通知
    - 处理通知点击跳转
    - 管理通知ID

28. **`ReminderService.kt`** - 后台提醒服务
    - 检查当前需要提醒的事项
    - 调用NotificationHelper发送通知
    - 处理通知点击Intent
    - 管理服务生命周期

29. **`ReminderWorker.kt`** - 定时提醒任务
    - 使用WorkManager调度
    - 定期检查提醒事项
    - 处理失败重试逻辑
    - 适配不同Android版本

30. **`BootReceiver.kt`** - 开机启动接收器
    - 监听系统启动完成广播
    - 重新调度提醒任务
    - 恢复后台服务
    - 处理权限检查

### 👤 第六阶段：用户信息页（1天）

31. **`activity_profile.xml`** - 用户信息布局
    - 设计个人信息展示区域
    - 布局头像上传区域
    - 添加功能选项列表
    - 集成退出登录按钮

32. **`ProfileActivity.kt`** - 用户信息逻辑
    - 加载当前用户信息
    - 处理头像上传
    - 支持修改个人信息
    - 实现退出登录功能

33. **`fragment_profile.xml`** - 个人中心布局
    - 设计快捷功能入口
    - 显示统计信息
    - 集成设置选项
    - 添加版本信息

34. **`ProfileFragment.kt`** - 个人中心逻辑
    - 展示用户统计数据
    - 处理功能项点击
    - 跳转到详细设置
    - 刷新用户信息

### 🎨 第七阶段：UI优化和细节完善（2-3天）

35. **`CategoryAdapter.kt`** - 分类适配器
    - 管理分类数据展示
    - 处理分类选择
    - 支持动态更新
    - 优化UI交互

36. **`item_category.xml`** - 分类项布局
    - 设计标签式UI
    - 集成颜色标记
    - 添加选中状态
    - 优化触控反馈

37. **`DateTimeUtils.kt`** - 日期时间工具
    - 格式化日期显示
    - 计算相对时间
    - 处理时区转换
    - 优化时间显示

38. **`styles.xml`** - 应用样式
    - 定义文本样式
    - 配置组件样式
    - 统一UI规范
    - 适配不同屏幕

39. **`anim/`目录** - 动画资源
    - 实现界面切换动画
    - 优化列表项动画
    - 添加交互反馈动画
    - 提升用户体验

### 📱 第八阶段：测试和优化（2天）

40. **`README.md`** - 项目文档
    - 编写项目概述
    - 记录功能说明
    - 提供截图展示
    - 添加使用说明

41. **错误处理机制** - 全局异常处理
    - 捕获未处理异常
    - 提供友好的错误提示
    - 记录错误日志
    - 安全的数据恢复

42. **性能优化** - 应用性能提升
    - 优化数据库查询
    - 减少内存占用
    - 加速界面加载
    - 优化后台任务

## 💡 开发建议

1. **版本控制策略**：
   - 使用Git进行版本管理
   - 每天提交代码并编写有意义的提交信息
   - 为重要功能创建分支
   - 定期推送到远程仓库

2. **渐进式开发流程**：
   - 优先完成核心功能（用户认证、事项管理）
   - 再实现辅助功能（提醒、分类）
   - 最后进行UI优化和性能调优
   - 每个阶段完成后进行测试

3. **代码质量保证**：
   - 遵循Kotlin编码规范
   - 为关键类和方法添加文档注释
   - 保持类和函数的单一职责
   - 使用有意义的命名

4. **测试策略**：
   - 每完成一个Activity/Fragment进行UI测试
   - 重点测试数据持久化功能
   - 验证边界条件（空数据、异常输入）
   - 在不同屏幕尺寸上测试布局适配

## 🎯 优先级建议

### 🥇 第一优先级（必须完成）
- ✅ 用户注册/登录功能
- ✅ 待办事项列表展示
- ✅ 添加/编辑/删除待办事项
- ✅ 基本的UI布局和导航

### 🥈 第二优先级（建议完成）
- ✅ 截止时间设置和显示
- ✅ 事项完成状态切换
- ✅ 用户信息页面
- ✅ 基础的通知提醒功能

### 🥉 第三优先级（时间充足时完成）
- ✅ 事项置顶功能
- ✅ 分类管理功能
- ✅ 头像上传功能
- ✅ 高级UI动画效果
- ✅ 多语言支持

这个项目结构结合了传统XML布局的直观性和Kotlin语言的现代特性，代码组织清晰，职责分明。按照这个计划逐步实现，可以确保在比赛中获得基础分数，同时为进阶功能预留空间。每个文件都有明确的功能定义，便于团队协作和后续维护。
```