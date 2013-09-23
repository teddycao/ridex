Ext.ns('OECP.ui.base');
/**
 * ���ܽ����������
 * @author slx
 * @class OECP.ui.base.ToftPanel
 * @extends Ext.Panel
 */
OECP.ui.base.ToftPanel = Ext.extend(Ext.Panel,{
    layout : 'border',
    defaultType: 'panel',
    height : 600,
    btnHeight : 23, // ��ť���߶�
    btns : [],    // ��ť��
    cpanel : undefined,    // ������panl

    initComponent : function(){
        // ��ť��panel
        var btnpanel = new Ext.Panel({
            defaultType: 'oecpbutton',
            layout:'table',
            baseCls: 'x-plain',
            items : this.btns
        });
        // ������panel
        if(this.cpanel == undefined){
            this.cpanel = new Ext.Panel({
                defaultType: 'panel',
                layout:'fit',
                baseCls: 'x-plain'
            });
        }
        this.items = [
            {// ��ť��
                id:'panl_btns',
                baseCls:'x-plain',
                items:[btnpanel],
                split:false,
                height:this.btnHeight,
                region:'north'
            },{// ������
                id:'panl_center',
                items:[this.cpanel],
                region:'center'
            }
        ];

        OECP.ui.base.ToftPanel.superclass.initComponent.call(this);
    }
})