# open-platform-service

以下为工程结构和规约定义，结构和规约并非一成不变，若理由合理可以改变

工程结构

com.*
  - aop
  - biz
  - common
      - config
      - context
      - enums
      - validation
          - constraint
  - controller
      - dto
      - group
  - dao
      - entity
  - exception
  - filter
  - utils

工程规约

aop
  - aop设计思想和原理
      - https://blog.csdn.net/luanlouis/article/details/51095702
      - http://blog.csdn.net/luanlouis/article/details/51155821
  - aop使用规则
      - 执行顺序 实现org.springframework.core.Ordered接口或注解@Order，显示指定aop的顺序，数值越大越后执行
      - 保留数字 Integer.MIN_VALUE/Integer.MAX_VALUE ± 1 作为spring框架的保留值，最好不要被业务aop占用
  - aop语法参考
      - http://jinnianshilongnian.iteye.com/blog/1420691
  - aop应用
      - 分层设计
          - 所有aop一共分为3类，随着业务复杂度增加，也许可以增加到4类甚至5类
          - 第1类是入口层aop，作用是数据验证、异常处理、打印日志等基础工作，为后面代码的执行奠定基础，在服务里面表现为ControllerAop
              - 目前为了和spring融合，把ControllerAop的数据验证、异常处理都交给了spring，只保留正常日志的打印工作
          - 第2类是公共层aop，作用是对入参/出参做一些公共处理，比如对入参/出参加解密，以免在业务代码里重复此动作
          - 第3类是业务层aop，作用是对业务作个性化处理，比如LoginAop、PermissionAop
      - 执行流程
          - EnterAops -> CommonAops -> BizAops
      - 语义说明
          - 第1类aop是基础，后面的2、3类aop及业务代码不应该再去做第1类aop的工作，比如异常处理，代码执行经过第1类aop后，后续代码就可以放心使用入参，不必担心和处理异常
              - 不必担心和处理异常，此话仅仅指入参经过了验证、异常会有地方统一处理，但并不代表作为攻城狮的我们可以随心所欲，保证代码的鲁棒性是我们的职责
              - 诸如NullPointerException//NumberFormatException/ClassCastException/IndexOutOfBoundsException/ArithmeticException等异常都应该避免出现
          - 第2、3类aop及业务代码可以铺助完成第1类aop的工作，比如数据验证、打印日志
              - javax.validation.Validator并不能完全满足验证需求，它只能处理大部分验证场景，小部分验证场景还得自己来完成
              - 打印日志作为一个普遍需求，在第1类aop中仅仅是记录了入参/出参作为问题的追踪手段，但某些场景可能需要根据自身业务作一些单独打印
          - 第2、3类aop及业务代码更多的应该专注于自身要处理的事情
          
biz
  - 业务逻辑请放在此层，尽量不要分散
  - 过于复杂的业务逻辑可以将其分散成多个类组合完成(分散的类建一个子包放在biz包下面，包名根据业务命名)
  
common
  - config
      - 所有配置请放在此包
  - context
      - 所有ThreadLocal变量请放在此包
  - enums
      - 所有枚举请放在此包
  - validation
      - constraint
          - 所有自定义校验请放在此包
      - validator文档和应用
          - http://docs.jboss.org/hibernate/validator/4.2/reference/zh-CN/html/
          - 将验证交给spring，在ExceptionHandler统一处理验证异常，由ViolationHandler/ValidCodeConvert/ViolationUtils配合完成

controller
  - 服务入口，只参与数据验证和简单的数据转换，不参与任何业务逻辑
  - 访问路径原则上只使用path表达
  - 入参原则上只使用表单表达，出参使用json表达
  - 入参类型只能定义为8个基础类型和基于基础类型封装的pojo，入参个数暂不做要求，多个参数建议使用pojo包装，对于复杂的数据结构可以使用json字符串表达
  - 出参全部用Response包装，具体返回数据自定义，没有返回数据的用Void代替
  - dto
      - 服务入参类，所有setter必须返回this引用，方便链式调用
      - 必须覆盖toString方法并能够表达自己所携带的信息
      - Request/Response，作为入参/出参泛型类，不提供constructor、setter方法，由build等一系列工厂方法代替
  - group
      - 所有分组校验请放在此包

dao
  - 数据读写请放在此层，不参与任何业务逻辑，包括但不限于hibernate、mybatis等orm框架，原则上不允许混用

exception
  - BizException为统一异常类，可以在controller、biz、dao等必要的时候抛出此异常，原则上不派生其他任何子类
  - ExceptionHandler为统一异常处理类

filter
  - 所有Filter请放在此包，如果业务需要，可以实现javax.servlet.Filter 或 继承org.springframework.web.filter.OncePerRequestFilter

utils
  - 所有工具类请放在此包
# open-platform-service
