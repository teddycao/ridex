http://192.168.140.10:8080/fee/s/hut/list.do?dataHolder.page=1&dataHolder.pageSize=20&name=dictMeta
http://192.168.140.10:8080/fee/s/hut/list.do?name=dictMeta


Ext��
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
ֻ�������¼� click()  mousedown()   nouseup()


Ext.util.DelayedTask
canelc()   delay()


Ext.util.Format�ṩ���õĸ�ʽ������
28      Ext.util.Format.ellipsis(string value,Number length) //��ָ���ַ�������������ʡ�ԺŴ��沢����
29      Ext.util.Format.capitalize(string value)             //��ָ���ַ���ת����Сд������
30      Ext.util.Format.date(Mixed value,[string format])    //�������ı���ָ���ĸ�ʽ����ת��
31      Ext.util.Format.htmlEncode(string value)             //������Value����html�ַ����滻������
32      Ext.util.Format.htmlDecode(string value)             //htmlEncode�������
33      Ext.util.Format.stripTags(Mixed value)               //���ַ����е�html��ǩ�����滻�������滻����ַ���
34      Ext.util.Format.substr(string value,Number start,Number length) //��ȡvalue��start��length����ַ�������


Ext.util.JSON����ͽ������
35.     Ext.util.JSON.decode(string json)
36. Ext.util.JSON.encode(Mixed o)
 

Ext.util.MixedCollection
37.     Ext.util.MixedCollection.add(string key,object o)        //��������뵽����
38.     Ext.util.MixedCollection.addAll(object/Array objs):void  //�������еĶ�����뵽������
39.     Ext.util.MixedCollection.clear():void                    //�Ӽ������Ƴ����ж���
40.     Ext.util.MixedCollection.clone()                         //��¡����
41.     Ext.util.MixedCollection.contains(object o):Boolean      //ƥ�伯���еĹ�������(�Ƿ��������)
        Ext.util.MixedCollection.containsKey(string key):Boolean //ƥ�伯���еĹ�������(�Ƿ������key)
42.     Ext.util.MixedCollection.each(Function fn,[object scope]):void  //���������еĶ��󣬵���ָ���ķ���
43.     Ext.util.MixedCollection.first():object                         //��ȡ�����еĵ�һ������
        Ext.util.MixedCollection.get(string/Number key):object          //��ȡ��������ָ����key�����Ķ���
44. Ext.util.MixedCollection.getCount():Number                      //ȡ����������
45. Ext.util.MixedCollection.getKey(object item):object             //ȡ��ָ�����������
46.     Ext.util.MixedCollection.indexof(object o):Number               //ȡ��ָ�������ڼ����е�λ���±�
47. Ext.util.MixedCollection.indexofkey(string key):Number          //��������ȡ�ö����ڼ����е��±�
48. Ext.util.MixedCollection.item(string/Number key):object         //�����ڼ����е�ָ���������±�ȡ�ö���
49.     Ext.util.MixedCollection.itemAt(Number index):object            //�����ڼ����е�ָ���±�ȡ�ö���
50. Ext.util.MixedCollection.key(string/Number key):object          //�����ڼ����еĹ���ʱ������ȡ�ö���
51. Ext.util.MixedCollection.remove(object o):object  //�Ƴ������еĹ�������
52. Ext.util.MixedCollection.removeAt(Number index):object  //�����±��Ƴ����еĹ�������
53. Ext.util.MixedCollection.removeKey(string key):object  //���������Ƴ������й�������
54. Ext.util.MixedCollection.replace(string key,[o {object}]):object//�滻����


������Ҫ���¼�
55. add(Nimber index,object o,string key)         //����һ�����������һ������������ɹ��󴥷����¼�
56. clear                                         //�Ƴ����ж���ʱ�������¼�
57. remove(object o,[string key])             //�Ƴ�ָ���Ķ���ʱ���¼�
58.     replace(string key,object old,object new)     //�滻����ʱ���¼�


Ext.util.TaskRunner
59. Ext.util.TaskRunner([Number interval])       //�´���һ����ʱ����
60.     Ext.util.TaskRunner.start([object task]):object                   //����һ���߳�
61. Ext.util.TaskRunner.stop(object task):object        //ָֹͣ���������߳�
62.     Ext.util.TaskRunner.stopAll():void          /ֹͣ�����̵߳�ִ��


Ext.util.TextMetrics       //�õ���״���ı����
63.     Ext.util.TextMetrics.bind(string/HTMLElement el):void         //���ı���󶨵�elԪ�أ�ͬʱ��el��ԭ����ʽӦ�õ��ı�
64.     Ext.util.TextMetrics.createInstance(string/HTMLElement el,[Number fixedWidth]):Ext.util.TextMetrics.Instance
65.     Ext.util.TextMetrics.getHeight(string text):Number            //��ȡ�ı��ĸ߶�
66.     Ext.util.TextMetrics.getSize(strnig text):object              //��ȡ�ı��ĸߡ���
67.     Ext.util.TextMetrics.getWidth(string text):Number       //��ȡ�ı��Ŀ��
68. Ext.util.TextMetrics.measure(string/HTMLElement el,string text,[Number fixedWidth]):object //��ȡָ���ڵ����ı���Ŀ���
69. Ext.util.TextMetrics.setFixedWidth(Number width):void         //ָ���ı���Ŀ�


Ext.KeyNavΪԪ���ṩ�򵥵İ���������
70.     KeyNav(Mixed el,object config)                                //ʵ����һ�����̰󶨶���
71.     Ext.KeyNav.disable():void                                     //����ԭ�еļ��̰�
72. Ext.KeyNav.enable():void          //ʹ�����ļ��̰�������Ч


Ext.KeyMap�ṩ�����ǿ��İ���������
73.     KeyMap(Mixed el,object config,[string eventName])
74.     addBinding(object/Array config):void                          //��������Ӽ��̰�
75.     Ext.KeyMap.disable():void                                     //�����Ѱ󶨵�KeyMap������
76.     Ext.KeyMap.enable():void                                      //ʹKeyMap����������������Ч
77.     Ext.KeyMap.isEnabled():Boolean                                //���KeyMap��������Ч�ģ�������true�����򷵻�false





	<!-- C3P0���ݿ����� -->
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
	<!-- oracle���ݿ� -->
	<bean id="oracleDataSource" parent="c3p0">
		<property name="driverClass" value="${oracle.jdbc.driverClassName}" />
		<property name="jdbcUrl" value="${oracle.jdbc.url}" />
		<property name="user" value="${oracle.jdbc.username}" />
		<property name="password" value="${oracle.jdbc.password}" />
	</bean>