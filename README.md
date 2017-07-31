搭建SSH的WEB框架。
1.基于maven创建jar依赖
  各版本版本号：
  <spring.version>4.1.4.RELEASE</spring.version>
  <hibernate.version>4.3.8.Final</hibernate.version>
  <struts2.version>2.3.20</struts2.version>
2.配置spring.xml以及spring-hibernate.xml加载信息以及struct,web,log,jdbc配置
spring.xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
			http://www.springframework.org/schema/beans/spring-beans-4.1.xsd
			http://www.springframework.org/schema/context
			http://www.springframework.org/schema/context/spring-context-4.1.xsd">
	<!-- 加载配置文件 -->
	
	<context:property-placeholder location="classpath:jdbc.properties"/>
	<context:property-placeholder location="classpath:log4j.properties"/>
	<!-- 扫描service自动注入为bean -->
	<context:component-scan base-package="com.zw.service.impl,com.zw.dao.impl" />

</beans>
spring-hibernate.xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="
		http://www.springframework.org/schema/beans 
		http://www.springframework.org/schema/beans/spring-beans-4.1.xsd 
		http://www.springframework.org/schema/tx 
		http://www.springframework.org/schema/tx/spring-tx-4.1.xsd
		http://www.springframework.org/schema/aop 
		http://www.springframework.org/schema/aop/spring-aop-4.1.xsd">

	<!-- 配置数据源 使用的是Druid数据源 --> 
	<bean name="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
		init-method="init" destroy-method="close">
		<property name="url" value="${jdbc.url}" />
		<property name="username" value="${jdbc.username}" />
		<property name="password" value="${jdbc.password}" />

		<!-- 初始化连接大小 -->
		<property name="initialSize" value="0" />
		<!-- 连接池最大使用连接数量 -->
		<property name="maxActive" value="20" />
		
		<!-- 连接池最小空闲 -->
		<property name="minIdle" value="0" />
		<!-- 获取连接最大等待时间 -->
		<property name="maxWait" value="60000" />
		<property name="poolPreparedStatements" value="true" />
		<property name="maxPoolPreparedStatementPerConnectionSize"
			value="33" />
		<!-- 用来检测有效sql -->
		<property name="validationQuery" value="${validationQuery}" />
		<property name="testOnBorrow" value="false" />
		<property name="testOnReturn" value="false" />
		<property name="testWhileIdle" value="true" />
		<!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
		<property name="timeBetweenEvictionRunsMillis" value="60000" />
		<!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
		<property name="minEvictableIdleTimeMillis" value="25200000" />
		<!-- 打开removeAbandoned功能 -->
		<property name="removeAbandoned" value="true" />
		<!-- 1800秒，也就是30分钟 -->
		<property name="removeAbandonedTimeout" value="1800" />
		<!-- 关闭abanded连接时输出错误日志 -->
		<property name="logAbandoned" value="true" />
		<!-- 监控数据库 -->
		<property name="filters" value="mergeStat" />
	</bean>

	<!-- 配置hibernate的SessionFactory -->
	<bean id="sessionFactory"
		class="org.springframework.orm.hibernate4.LocalSessionFactoryBean">
		<!-- 注入数据源 相关信息看源码 -->
		<property name="dataSource" ref="dataSource" />
		<!-- hibernate配置信息 -->
		<property name="hibernateProperties">
			<props>
				<prop key="hibernate.dialect">${hibernate.dialect}</prop>
				<prop key="hibernate.show_sql">${hibernate.show_sql}</prop>
				<prop key="hibernate.format_sql">${hibernate.format_sql}</prop>
				<prop key="hibernate.hbm2ddl.auto">${hibernate.hbm2ddl.auto}</prop>
			    <prop key="hibernate.current_session_context_class">${hibernate.current_session_context_class}</prop>
			</props>
		</property>
		<!-- 扫描hibernate注解配置的entity -->
		<property name="packagesToScan" value="com.zw.entity" />
	</bean>

	<!-- 配置事务管理器 -->
	<bean id="transactionManager"
		class="org.springframework.orm.hibernate4.HibernateTransactionManager">
		<property name="sessionFactory" ref="sessionFactory" />
	</bean>

	<!-- 配置事务增强处理Bean，指定事务管理器 -->
	<tx:advice id="transactionAdvice" transaction-manager="transactionManager">
		<!-- 配置详细事务处理语义 -->
 		<tx:attributes>
			<tx:method name="insert*" propagation="REQUIRED" />
			<tx:method name="update*" propagation="REQUIRED" />
			<tx:method name="delete*" propagation="REQUIRED" />
            <tx:method name="save*" propagation="REQUIRED" />

			<tx:method name="get*" propagation="SUPPORTS" read-only="true" />
			<tx:method name="find*" propagation="SUPPORTS" read-only="true" />
			<tx:method name="select*" propagation="SUPPORTS" read-only="true" />
			<tx:method name="load*" propagation="SUPPORTS" read-only="true" />

			<!-- 其他采用默认事务方式 -->
			<tx:method name="*" />

		</tx:attributes>
	</tx:advice>

	<!-- Spring aop事务管理 -->
	<aop:config>
		<!-- 配置切入点 -->
		<aop:pointcut id="transactionPointcut"
			expression="execution(* com.zw.dao.impl.*Impl.*(..))" />
		<!-- 指定在txAdvice切入点应用txAdvice事务增强处理 -->
		<aop:advisor pointcut-ref="transactionPointcut"
			advice-ref="transactionAdvice" />
	</aop:config>

</beans>
struts.xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE struts PUBLIC "-//Apache Software Foundation//DTD Struts Configuration 2.3//EN" "http://struts.apache.org/dtds/struts-2.3.dtd">
<struts>
	<!-- 指定由spring负责action对象的创建 -->
	<constant name="struts.objectFactory" value="spring" />
	<!-- 所有匹配*.action的请求都由struts2处理 -->
	<constant name="struts.action.extension" value="action" />
	<!-- 是否启用开发模式 -->
	<constant name="struts.devMode" value="true" />
	<!-- struts配置文件改动后，是aa否重新加载 -->
	<constant name="struts.configuration.xml.reload" value="true" />
	<!-- 设置浏览器是否缓存静态内容 -->
	<constant name="struts.serve.static.browserCache" value="false" />
	<!-- 请求参数的编码方式 -->
	<constant name="struts.i18n.encoding" value="utf-8" />
	<!-- 每次HTTP请求系统都重新加载资源文件，有助于开发 -->
	<constant name="struts.i18n.reload" value="true" />
	<!-- 文件上传最大值 -->
	<constant name="struts.multipart.maxSize" value="104857600" />
	<!-- 让struts2支持动态方法调用 -->
	<constant name="struts.enable.DynamicMethodInvocation" value="true" />
	<!-- Action名称中是否还是用斜线 -->
	<constant name="struts.enable.SlashesInActionNames" value="false" />
	<!-- 允许标签中使用表达式语法 -->
	<constant name="struts.tag.altSyntax" value="true" />
	<!-- 对于WebLogic,Orion,OC4J此属性应该设置成true -->
	<constant name="struts.dispatcher.parametersWorkaround" value="false" />

	<!-- 用于CRUD Action的parent package -->
	<package name="crud-default" extends="convention-default">
		<!-- 基于paramsPrepareParamsStack, 增加store interceptor保证actionMessage在redirect后不会丢失 -->
		<interceptors>
			<interceptor-stack name="crudStack">
				<interceptor-ref name="store">
					<param name="operationMode">AUTOMATIC</param>
				</interceptor-ref>
				<interceptor-ref name="paramsPrepareParamsStack" />
			</interceptor-stack>
		</interceptors>
		<default-interceptor-ref name="crudStack" />
	</package>

	<!-- 使用Convention插件,实现约定大于配置的零配置文件风格. 特殊的Result路径在Action类中使用@Result设定. -->

</struts>
web.xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	id="WebApp_ID" version="2.5">

	<display-name>spring_struts2_Hibernate_demo</display-name>

	<!-- spring 和  Hibernate的配置文件 -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>
		     classpath:spring.xml
		     classpath:spring-hibernate.xml
	    </param-value>
	</context-param>

	<!-- 编码设置 -->
	<filter>
		<filter-name>encodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>utf-8</param-value>
		</init-param>
		<init-param>
			<param-name>forceEncoding</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>encodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>

	<!-- openSessionInView配置 作用是延迟session关闭到view层 -->
	<filter>
		<filter-name>openSessionInViewFilter</filter-name>
		<filter-class>org.springframework.orm.hibernate4.support.OpenSessionInViewFilter</filter-class>
		<init-param>
			<param-name>singleSession</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>

	<!-- 监听servletContext，启动contextConfigLocation中的spring配置信息 -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

	<!-- 防止spring内存溢出监听器 -->
	<listener>
		<listener-class>org.springframework.web.util.IntrospectorCleanupListener</listener-class>
	</listener>

	<!-- Struts2 filter -->
	
	<filter>
		<filter-name>struts2</filter-name>
		<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
	</filter>
	
	<filter-mapping>
		<filter-name>struts2</filter-name>
		<url-pattern>*.action</url-pattern>
	</filter-mapping>


	<filter-mapping>
		<filter-name>openSessionInViewFilter</filter-name>
		<url-pattern>*.action</url-pattern>
	</filter-mapping>

	<!-- 配置session超时时间，单位分钟 -->
	<session-config>
		<session-timeout>30</session-timeout>
	</session-config>

	<welcome-file-list>
		<welcome-file>/index.jsp</welcome-file>
	</welcome-file-list>
</web-app>
jdbc.properties
#application configs

#jdbc druid config
validationQuery = SELECT 1
jdbc.url = jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8
jdbc.username = root
jdbc.password = 123456

#hibernate config
hibernate.dialect = org.hibernate.dialect.MySQLDialect
hibernate.show_sql = true
hibernate.format_sql = false
hibernate.hbm2ddl.auto =update
hibernate.current_session_context_class =thread

log4j.properties
### set log levels ###
log4j.rootLogger = INFO , C , D , E 

### console ###
log4j.appender.C = org.apache.log4j.ConsoleAppender
log4j.appender.C.Target = System.out
log4j.appender.C.layout = org.apache.log4j.PatternLayout
log4j.appender.C.layout.ConversionPattern = [spring_struts2_Hibernate_demo][%p] [%-d{yyyy-MM-dd HH:mm:ss}] %C.%M(%L) | %m%n

### log file ###
log4j.appender.D = org.apache.log4j.DailyRollingFileAppender
log4j.appender.D.File = /logs/spring_struts2_Hibernate_log.log
log4j.appender.D.Append = true
log4j.appender.D.Threshold = INFO 
log4j.appender.D.layout = org.apache.log4j.PatternLayout
log4j.appender.D.layout.ConversionPattern = [spring_struts2_Hibernate_demo][%p] [%-d{yyyy-MM-dd HH:mm:ss}] %C.%M(%L) | %m%n

### exception ###
log4j.appender.E = org.apache.log4j.DailyRollingFileAppender
log4j.appender.E.File = /logs/spring_struts2_Hibernate_error.log 
log4j.appender.E.Append = true
log4j.appender.E.Threshold = ERROR 
log4j.appender.E.layout = org.apache.log4j.PatternLayout
log4j.appender.E.layout.ConversionPattern =[spring_struts2_Hibernate_demo][%p] [%-d{yyyy-MM-dd HH\:mm\:ss}] %C.%M(%L) | %m%n


  

