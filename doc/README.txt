1. 所有的domain数据库对应的字段大写
2. 所有domain需要书写对应字段的静态常量，方便调用
3. 定时调度使用spring的@Scheduled，这里也可以通过表达式获取properties中的配置参数
4. 注意：虽然SysUserDao继承了BaseHibernateDao，拥有save、update等方法，
   但是如果想在dao中将这些方法作为连接点执行切面代码，则需要在相对应的dao中再重新定义，必须存在对应的方法，否则无法被aop拦截
   参考SysUserDaoAop和SysUserDao
5. dao中使用getCurrentSession()获取session，并且由spring管理事务，因此需要在spring配置文件中加上事务管理器


