Ext.ns('OECP.ui.base');
/**
 * 功能界面基本布局
 * @author slx
 * @class OECP.ui.base.ToftPanel
 * @extends Ext.Panel
 */
OECP.ui.base.ToftPanel = Ext.extend(Ext.Panel,{
    layout : 'border',
    defaultType: 'panel',
    height : 600,
    btnHeight : 23, // 按钮区高度
    btns : [],    // 按钮组
    cpanel : undefined,    // 数据区panl

    initComponent : function(){
        // 按钮区panel
        var btnpanel = new Ext.Panel({
            defaultType: 'oecpbutton',
            layout:'table',
            baseCls: 'x-plain',
            items : this.btns
        });
        // 数据区panel
        if(this.cpanel == undefined){
            this.cpanel = new Ext.Panel({
                defaultType: 'panel',
                layout:'fit',
                baseCls: 'x-plain'
            });
        }
        this.items = [
            {// 按钮区
                id:'panl_btns',
                baseCls:'x-plain',
                items:[btnpanel],
                split:false,
                height:this.btnHeight,
                region:'north'
            },{// 数据区
                id:'panl_center',
                items:[this.cpanel],
                region:'center'
            }
        ];

        OECP.ui.base.ToftPanel.superclass.initComponent.call(this);
    }
})