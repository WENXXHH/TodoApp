# 一，java/com/example/todoapp/ ✔

## **data/**

### database/

#### AppDatabase.kt           # Room数据库定义和初始化

#### TodoDao.kt # 待办事项数据访问接口  ✔
    - 去学习有关Dao有关的room知识
    - 定义CRUD操作接口
    - 实现按时间排序查询
    - 添加置顶事项优先查询
    - 获取需要提醒的事项

#### UserDao.kt # 用户数据访问接口 ✔
    - 去学习有关Dao有关的room知识
    - 定义用户增删改查操作
    - 实现登录验证查询
    - 用户名唯一性检查

### model/

#### TodoItem.kt  # 待办事项数据模型 ✔
    - 依旧欠缺room方面的知识等待学习，依旧待办事项相关数据
    - 定义事项核心字段
    - 实现状态管理（完成/未完成）
    - 提供时间格式化方法
    - 支持置顶功能标记

#### User.kt   # 用户数据模型  ✔
    - 学会用与数据库储存的实体类创建，
    - 定义基本信息字段
    - 设置数据验证逻辑

#### Reminder.kt              # 提醒数据模型

###  repository/

#### TodoRepository.kt # 待办事项业务逻辑封装  ✔
    - 依旧神秘封装
    - 封装数据操作
    - 处理事项状态更新
    - 管理提醒设置
    - 实现搜索过滤功能

#### UserRepository.kt  # 用户业务逻辑封装  ✔
    - 学习有关repository的room知识
    - 封装用户注册流程
    - 实现登录认证逻辑
    - 处理用户信息更新

## **utils/**

### NotificationHelper.kt  # 通知管理工具类  ✔
    - 通知管理相关广播知识复习
    - 创建通知渠道
    - 构建提醒通知
    - 处理通知点击跳转
    - 管理通知ID

### DateTimeUtils.kt            # 日期时间格式化工具

###  NetworkUtils.kt             # 网络状态检查工具

### PreferenceManager.kt         # SharedPreferences封装管理

## **adapter/**

###  TodoAdapter.kt # 待办事项列表适配器  ✔
    - 依旧神奇难实现的适配器 ， 重新学习这一部分的内容是非常有必要的了

###  CategoryAdapter.kt          # 分类列表适配器

###  UserAdapter.kt               # 用户列表适配器

##  **base/**

###  BaseActivity.kt             # 基础Activity封装（通用功能）

###  BaseFragment.kt             # 基础Fragment封装（通用功能）

##  **activity/**  ✔

### MainActivity.kt # 主界面Activity（底部导航容器） ✔
    - 依旧优雅，依旧条理清晰
    - 学会点击事件逻辑优雅处理
    - 学会处理Fragment与Activity的交互逻辑

###  LoginActivity.kt # 登录界面Activity  ✔
    - 学会优雅的Activity代码编辑方式，也就是精通函数式编程，处理控件id和点击事件
    - 学会编写登录的逻辑

###  RegisterActivity.kt  # 注册界面Activity  ✔
    - 学会优雅的Activity代码编辑方式，也就是精通函数式编程，处理控件id和点击事件
    - 学会编写注册的逻辑

###  AddTodoActivity.kt  # 添加待办事项Activity
    - 依旧优雅，但是有一点，就是实现点击事件的代码有点复杂，等待继续钻研

###  EditTodoActivity.kt   # 编辑待办事项Activity ✔
    - 依旧是优雅以及复杂的实现逻辑，等待模仿实现

###  ProfileActivity.kt  # 用户信息界面Activity  ✔
    - 依旧精美个人界面逻辑处理
    - 加载当前用户信息
    - 处理头像上传
    - 支持修改个人信息
    - 实现退出登录功能

## **fragment/**

###  HomeFragment.kt # 首页Fragment（待办事项列表） ✔
    - 学会处理Fragment的相关逻辑

###  CategoryFragment.kt         # 分类管理Fragment

###  ProfileFragment.kt  # 个人中心Fragment  ✔
    - 展示用户统计数据
    - 处理功能项点击
    - 跳转到详细设置
    - 刷新用户信息

###  SettingsFragment.kt         # 设置管理Fragment

## **widget/**

###  TodoItemView.kt             # 自定义待办事项视图组件

###  CategoryChip.kt             # 自定义分类标签组件

## **service/**  ✔

###  ReminderService.kt  # 后台提醒服务（本地通知） ✔
    - 复习Service相关的内容
    - 检查当前需要提醒的事项
    - 调用NotificationHelper发送通知
    - 处理通知点击Intent
    - 管理服务生命周期

### BootReceiver.kt  # 开机启动广播接收器  ✔
    - 依旧广播接收实例
    - 监听系统启动完成广播
    - 重新调度提醒任务
    - 恢复后台服务
    - 处理权限检查

##  **application/**  ✔

###  TodoApplication.kt # Application类（全局初始化） ✔
    - 学习使用 companion object
    - 学习如何去创建通知渠道，复习广播组件
    - 学习设置全局异常处理，
    - 学习初始化第三方库

##  **worker/**  ✔

###  ReminderWorker.kt ✔
    - WorkerManager相关的知识最好学习实践
    - 使用WorkManager调度
    - 定期检查提醒事项
    - 处理失败重试逻辑
    - 适配不同Android版本

#  二，res/ ✔

##  **layout/** # XML布局文件目录

###  activity_main.xml # 主界面布局（底部导航框架） ✔
    - 复习Materia Design布局设置知识
    - 学会使用Toolbar设置集成工具栏布局
    - 学会设置Fragment容器布局
    - 学会设置底部导航栏布局

### activity_login.xml # 登录界面布局  ✔
    - 精美的登录界面的代码实例

###  activity_register.xml # 注册界面布局 ✔
    - 优美的注册页面布局示例

###  activity_todo_form.xml # 添加待办事项布局  ✔
    - 精美添加待办事项的布局

### activity_profile.xml  # 用户信息界面布局  ✔
    - 精美个人信息页示例

###  fragment_home.xml  # 首页Fragment布局 ✔
    - 学会处理recyclerview在fragment的使用方法，以前实现过，回去复习
    - 处理空数据时的显示逻辑
    - 悬浮添加按钮的使用，依旧Materia Design 

### │   │   │   │   │   ├── fragment_category.xml        # 分类Fragment布局

###  fragment_profile.xml  # 个人中心Fragment布局  ✔
    - 设计快捷功能入口
    - 显示统计信息
    - 集成设置选项
    - 添加版本信息

### │   │   │   │   │   ├── fragment_settings.xml        # 设置Fragment布局

### item_todo.xml  # 待办事项列表项布局  ✔
    - 神奇精美待办事项列表，超强实现

### │   │   │   │   │   ├── item_category.xml            # 分类列表项布局

### │   │   │   │   │   ├── dialog_reminder.xml          # 提醒设置对话框布局

### │   │   │   │   │   ├── dialog_category.xml          # 分类选择对话框布局

### │   │   │   │   │   └── toolbar_main.xml             # 通用工具栏布局

## values/

### strings.xml # 字符串资源（多语言支持）  ✔
    - 统一管理字符串，有利于规范维护和管理
    - 规范添加 ； 例如：<string name="app_name">TodoApp</string>
    - 规范引用 ： 例如：( xml文件：android:text="@string/button_save" /> 
      java/kotlin :String appName = getString(R.string.app_name);
      TextView titleView = findViewById(R.id.title_text);
      titleView.setText(R.string.title_login);// 将TextView的文本设为 "用户登录" )

### colors.xml  # 颜色资源定义  ✔
    - 依旧统一管理方便修改
    - 语法与用法同 string

### │   │   │   │   │   ├── dimens.xml                   # 尺寸资源定义

### │   │   │   │   │   ├── styles.xml                   # 样式资源定义

### │   │   │   │   │   ├── themes.xml                   # 主题资源定义

### │   │   │   │   │   └── arrays.xml                   # 数组资源定义

##  drawable/

### │   │   │   │   │   ├── ic_launcher_background.xml   # 启动图标背景

### │   │   │   │   │   ├── ic_add.xml                   # 添加图标

### │   │   │   │   │   ├── ic_delete.xml                # 删除图标

### │   │   │   │   │   ├── ic_edit.xml                  # 编辑图标

### │   │   │   │   │   ├── ic_check.xml                 # 完成图标

### │   │   │   │   │   ├── ic_calendar.xml              # 日历图标

### │   │   │   │   │   ├── ic_time.xml                  # 时间图标

### │   │   │   │   │   ├── ic_user.xml                  # 用户图标

### │   │   │   │   │   ├── ic_logout.xml                # 退出图标

### │   │   │   │   │   ├── bg_button_primary.xml        # 主要按钮背景样式

### │   │   │   │   │   ├── bg_todo_item.xml             # 待办事项项背景样式

### │   │   │   │   │   └── bg_category_chip.xml         # 分类标签背景样式

## mipmap/  # 应用图标资源

### │   │   │   │   │   ├── ic_launcher.png              # 启动图标

### │   │   │   │   │   └── ic_launcher_round.png        # 圆形启动图标

## menu/

###  bottom_nav_menu.xml # 底部导航菜单定义  ✔
    - 三个底部导航按键的定义设置

### │   │   │   │   │   ├── toolbar_menu.xml             # 工具栏菜单定义

### │   │   │   │   │   └── todo_item_menu.xml           # 待办事项项菜单定义

## anim/

### │   │   │   │       ├── slide_in_right.xml           # 右滑进入动画

### │   │   │   │       ├── slide_out_left.xml           # 左滑退出动画

### │   │   │   │       └── fade_in.xml                  # 淡入动画

## assets/    # 静态资源文件

### │   │   │   │   └── categories.json                  # 默认分类数据

# AndroidManifest.xml      # 应用清单文件（权限和组件注册）
    - 学会权限声明
    - 学会组件注册