/**
 * login.js Power by YUI-EXT and JSON.
 * 
 * @author seraph
 * @email raidery@gmail.com
 */
 
Ext.ns('Ext.ux');

Ext.ux.TabCloseMenu = function(){
    var tabs, menu, ctxItem;
    this.init = function(tp){
        tabs = tp;
        tabs.on('contextmenu', onContextMenu);
    }

    function onContextMenu(ts, item, e){
        if(!menu){ // create context menu on first right click
            menu = new Ext.menu.Menu([{
                id: tabs.id + '-close',
                text: '关闭当前页签',
                handler : function(){
                    tabs.remove(ctxItem);
                }
            },{
                id: tabs.id + '-close-others',
                text: '关闭其它页签',
                handler : function(){
                    tabs.items.each(function(item){
                        if(item.closable && item != ctxItem){
                            tabs.remove(item);
                        }
                    });
                }
              
            },
				{
                id: tabs.id + '-close-all',
                text: '关闭所有页签',
                handler : function(){
                    tabs.items.each(function(item){
                        if(item.closable){
                            tabs.remove(item);
                        }
                    });
                }
              
            }
			
			]);
        }
        ctxItem = item;
        var items = menu.items;
        items.get(tabs.id + '-close').setDisabled(!item.closable);
        var disableOthers = true;
        tabs.items.each(function(){
            if(this != item && this.closable){
                disableOthers = false;
                return false;
            }
        });
        items.get(tabs.id + '-close-others').setDisabled(disableOthers);
	    items.get(tabs.id + '-close-all').setDisabled(disableOthers);
        menu.showAt(e.getPoint());
    }
};


 
Ext.apply(Ext.layout.FormLayout.prototype, {
    originalRenderItem: Ext.layout.FormLayout.prototype.renderItem,
    renderItem: function(c, position, target){
        if (c && !c.rendered && c.isFormField && c.fieldLabel && !c.allowBlank) {
            c.fieldLabel = c.fieldLabel + " <span " +
            ((c.requiredFieldCls !== undefined) ? 'class="' + c.requiredFieldCls + '"' : 'style="color:red;"') +
            " ext:qtip=\"" +
            ((c.blankText !== undefined) ? c.blankText : "该字段不能为空") +
            "\">*</span>";
        }
        this.originalRenderItem.apply(this, arguments);
    }
});
 
Ext.ux.RequiredFieldInfo = Ext.extend(Ext.form.Label, {
    constructor: function(config){
        Ext.ux.RequiredFieldInfo.superclass.constructor.call(this, Ext.apply({
            html: "<span " +
            ((this.requiredFieldCls !== undefined) ? 'class="' + this.requiredFieldCls + '"' : 'style="color:red;"') +
            '>*</span> ' +
            ((this.requiredFieldText !== undefined) ? this.requiredFieldText : '不能为空')
        }, config));
    }
});
Ext.reg('reqFieldInfo', Ext.ux.RequiredFieldInfo);
