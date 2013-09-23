http://192.168.140.10:8080/fee/s/hut/list.do?dataHolder.page=1&dataHolder.pageSize=20&name=dictMeta
http://192.168.140.10:8080/fee/s/hut/list.do?name=dictMeta


Ext类
1.  Ext.onReady(Function fn,Object scope,boolean override)
2.  Ext.get(Mixed el)
3.  Ext.select(String/Array selector,[Boolean unique],[HTMLElement/String root])
4.  Ext.query(String path,[Node root])
5. Ext.getCmp(string id)
6.  Ext.getDom(Mixed el)
7.  Ext.isEmpty(Mixed value,[Boolean allowBlank])
8.  Ext.namespace(string namespace1,string namespace2,string etc)
9.  Ext.each(Array/NodeList/Mixed array,Function fn,object scope)
10.     Ext.apply(Object obj,Object config,Object defaults)

Ext.DomHelper
11.     Ext.DomHelper.append(Mixed el,Object/String o,[Boolean returnElement])
12.     Ext.DomHelper.applyStyles(string/HTMLElement el,String/Object/Function styles)
13.     Ext.DomHelper.createTemplate(Object o)
14.     Ext.DomHelper.insertAfter(Mixed el,Object o,[Boolean returnElement])
15.     Ext.DomHelper.insertBefore(Mixed el,Object/string o,[Boolean returnElement])
16.     Ext.DomHelper.insertFirst(Mixed el,Object/string o,[Boolean returnElement])
17.     Ext.DomHelper.insertHtml(string where, HTMLElement el,string html)
18.     Ext.DomHelper.overwrite(Mixed el,object/string o,[Boolean returnElement])

Ext.DomQuery
19.     Ext.DomQuery.compile(string selector,[string type])
20.     Ext.DomQuery.filter(Array el,string selector,Boolean nonMatches)
21.     Ext.DomQuery.is(string/HTMLElement/Array el,string selector)
22.     Ext.DomQuery.select(string selector,[Node root])
23.     Ext.DomQuery.selectNode(string selector,[Node root])

Ext.util.CSS
24.     Ext.util.CSS.createStyleSheet(string cssText,string id)
25.     Ext.util.CSS.getRule(string/Array selector,Boolean refreshCache)
26.     Ext.util.CSS.swapStyleSheet(string id,string url)
27.     Ext.util.CSS.removeStyleSheet(string id)

Ext.util.ClickRepeater
只有三个事件 click()  mousedown()   nouseup()


Ext.util.DelayedTask
canelc()   delay()


Ext.util.Format提供常用的格式化方法
28      Ext.util.Format.ellipsis(string value,Number length) //将指定字符串超长部分用省略号代替并返回
29      Ext.util.Format.capitalize(string value)             //将指定字符串转换成小写并返回
30      Ext.util.Format.date(Mixed value,[string format])    //将日期文本按指定的格式进行转换
31      Ext.util.Format.htmlEncode(string value)             //将参数Value进行html字符的替换并返回
32      Ext.util.Format.htmlDecode(string value)             //htmlEncode的逆过程
33      Ext.util.Format.stripTags(Mixed value)               //将字符串中的html标签进行替换并返回替换后的字符串
34      Ext.util.Format.substr(string value,Number start,Number length) //截取value从start到length间的字符并返回


Ext.util.JSON编码和解码对象
35.     Ext.util.JSON.decode(string json)
36. Ext.util.JSON.encode(Mixed o)
 

Ext.util.MixedCollection
37.     Ext.util.MixedCollection.add(string key,object o)        //将对象加入到集合
38.     Ext.util.MixedCollection.addAll(object/Array objs):void  //将数组中的对象加入到集合中
39.     Ext.util.MixedCollection.clear():void                    //从集合中移除所有对象
40.     Ext.util.MixedCollection.clone()                         //克隆集合
41.     Ext.util.MixedCollection.contains(object o):Boolean      //匹配集合中的关联对象(是否包含对象)
        Ext.util.MixedCollection.containsKey(string key):Boolean //匹配集合中的关联对象(是否包含该key)
42.     Ext.util.MixedCollection.each(Function fn,[object scope]):void  //迭代集合中的对象，调用指定的方法
43.     Ext.util.MixedCollection.first():object                         //获取集合中的第一个对象
        Ext.util.MixedCollection.get(string/Number key):object          //获取集合总与指定的key关联的对象
44. Ext.util.MixedCollection.getCount():Number                      //取得数组数量
45. Ext.util.MixedCollection.getKey(object item):object             //取得指定对象的索引
46.     Ext.util.MixedCollection.indexof(object o):Number               //取得指定对象在集合中的位置下标
47. Ext.util.MixedCollection.indexofkey(string key):Number          //根据索引取得对象在集合中的下标
48. Ext.util.MixedCollection.item(string/Number key):object         //根据在集合中的指定索引或下标取得对象
49.     Ext.util.MixedCollection.itemAt(Number index):object            //根据在集合中的指定下标取得对象
50. Ext.util.MixedCollection.key(string/Number key):object          //根据在集合中的关联时的索引取得对象
51. Ext.util.MixedCollection.remove(object o):object  //移除集合中的关联对象
52. Ext.util.MixedCollection.removeAt(Number index):object  //根据下标移除集中的关联对象
53. Ext.util.MixedCollection.removeKey(string key):object  //根据索引移除集合中关联对象
54. Ext.util.MixedCollection.replace(string key,[o {object}]):object//替换对象


类中重要的事件
55. add(Nimber index,object o,string key)         //当向一个集合中添加一个对象结束并成功后触发此事件
56. clear                                         //移除所有对象时触发此事件
57. remove(object o,[string key])             //移除指定的对象时的事件
58.     replace(string key,object old,object new)     //替换对象时的事件


Ext.util.TaskRunner
59. Ext.util.TaskRunner([Number interval])       //新创建一个定时任务
60.     Ext.util.TaskRunner.start([object task]):object                   //启动一个线程
61. Ext.util.TaskRunner.stop(object task):object        //停止指定的任务线程
62.     Ext.util.TaskRunner.stopAll():void          /停止所有线程的执行


Ext.util.TextMetrics       //得到块状化文本规格
63.     Ext.util.TextMetrics.bind(string/HTMLElement el):void         //将文本块绑定到el元素，同时将el的原有样式应用到文本
64.     Ext.util.TextMetrics.createInstance(string/HTMLElement el,[Number fixedWidth]):Ext.util.TextMetrics.Instance
65.     Ext.util.TextMetrics.getHeight(string text):Number            //获取文本的高度
66.     Ext.util.TextMetrics.getSize(strnig text):object              //获取文本的高、宽
67.     Ext.util.TextMetrics.getWidth(string text):Number       //获取文本的宽度
68. Ext.util.TextMetrics.measure(string/HTMLElement el,string text,[Number fixedWidth]):object //获取指定节点内文本块的宽、高
69. Ext.util.TextMetrics.setFixedWidth(Number width):void         //指定文本块的宽


Ext.KeyNav为元素提供简单的按键处理方法
70.     KeyNav(Mixed el,object config)                                //实例化一个键盘绑定对象
71.     Ext.KeyNav.disable():void                                     //废弃原有的键盘绑定
72. Ext.KeyNav.enable():void          //使废弃的键盘绑定重新生效


Ext.KeyMap提供更灵活强大的按键处理方法
73.     KeyMap(Mixed el,object config,[string eventName])
74.     addBinding(object/Array config):void                          //给对象添加键盘绑定
75.     Ext.KeyMap.disable():void                                     //废弃已绑定到KeyMap的配置
76.     Ext.KeyMap.enable():void                                      //使KeyMap废弃的配置重新生效
77.     Ext.KeyMap.isEnabled():Boolean                                //如果KeyMap配置是有效的，而返回true，否则返回false





	<!-- C3P0数据库配置 -->
	<bean id="c3p0" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="acquireIncrement" value="${c3p0.acquireIncrement}" />
		<property name="acquireRetryAttempts" value="${c3p0.acquireRetryAttempts}" />
		<property name="acquireRetryDelay" value="${c3p0.acquireRetryDelay}" />
		<property name="autoCommitOnClose" value="${c3p0.autoCommitOnClose}" />
		<property name="automaticTestTable" value="${c3p0.automaticTestTable}" />
		<property name="breakAfterAcquireFailure" value="${c3p0.breakAfterAcquireFailure}" />
		<property name="checkoutTimeout" value="${c3p0.checkoutTimeout}" />
		<property name="idleConnectionTestPeriod" value="${c3p0.idleConnectionTestPeriod}" />
		<property name="initialPoolSize" value="${c3p0.initialPoolSize}" />
		<property name="minPoolSize" value="${c3p0.minPoolSize}" />
		<property name="maxPoolSize" value="${c3p0.maxPoolSize}" />
		<property name="maxIdleTime" value="${c3p0.maxIdleTime}" />
		<property name="maxStatements" value="${c3p0.maxStatements}" />
		<property name="maxStatementsPerConnection" value="${c3p0.maxStatementsPerConnection}" />
		<property name="numHelperThreads" value="${c3p0.numHelperThreads}" />
		<property name="testConnectionOnCheckout" value="${c3p0.testConnectionOnCheckout}" />
	</bean>
	<!-- oracle数据库 -->
	<bean id="oracleDataSource" parent="c3p0">
		<property name="driverClass" value="${oracle.jdbc.driverClassName}" />
		<property name="jdbcUrl" value="${oracle.jdbc.url}" />
		<property name="user" value="${oracle.jdbc.username}" />
		<property name="password" value="${oracle.jdbc.password}" />
	</bean>