MyComponent = Ext.extend(Ext.some.component, {
    //ȱʡ����������ɱ��Զ������ø���
    propA: 1,
 
    initComponent: function(){
       //�������ʼ���ڼ���õĴ���
 
        //��Ϊ���ö���Ӧ�õ��ˡ�this�����������Կ��������ﱻ���ǣ���������µ�����
        //����items,tools,buttons��
        Ext.apply(this, {
            propA: 3
        });
 
       //���ø������֮ǰ        
 
        //���ø��๹�캯�������룩
        MyComponent.superclass.initComponent.apply(this, arguments);
 
       //���ø������֮��
        //�磺�����¼��������Ⱦ���
    },
 
    //�����������෽��
    onRender: function(){
 
        //���ø������֮ǰ
 
        //���ø�����Ӧ���������룩
        MyScope.superclass.onRender.apply(this, arguments);
 
        //���ø������֮��
 
    }
});
 
//ע���xtype�Ա��ܹ��ӳټ���
Ext.reg('mycomponentxtype', MyComponent);

