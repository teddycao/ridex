Ext.ux.ComboBoxTree = Ext.extend(Ext.form.ComboBox, {
	    realValue: undefined,
		dispText:'',
		editable : false,
        shadow : true,
		triggerAction : 'all',
		rootText : 'root',
        rootId : '-1',
		 mode : 'local',
        forceSelection : false,
        store : new Ext.data.SimpleStore({
              fields : [],
              data : [[]]
            }),
        _onSelect : function() {

        },
        initComponent : function(ct, position) {
          this.divId = 'tree-' + Ext.id();
          if (isNaN(this.maxHeight))
            this.maxHeight = 200;
          Ext.apply(this, {
                tpl : '<tpl>' + '<div style="height:' + this.maxHeight + 'px;">' + '<div id="' + this.divId + '"></div>' + '</div></tpl>'
              });

          var root = new Ext.tree.AsyncTreeNode({
                text : this.rootText,
                id : this.rootId,
                loader : new Ext.tree.TreeLoader({
                      dataUrl : this.treeUrl,
                      clearOnLoad : true
                    })
              });

          this.tree = new Ext.tree.TreePanel({
                border : false,
                root : root,
                rootVisible : false,
                listeners : {
                  scope : this,
                  click : function(node, e) {
                    this.setValue(node.text);
					this.realValue=node.id;
					this.dispText = node.text;
				    this.collapse();
                    this._onSelect();
					 
                  }
                }
              });

          Ext.ux.ComboBoxTree.superclass.initComponent.call(this);
        },	
		getDispText : function() {
			return this.dispText;
		},
		getRealValue : function() {
			return this.realValue;
		},
	    getValue : function(){
			if(this.valueField){
				return typeof this.value != 'undefined' ? this.realValue : '';
			}else{
				return Ext.ux.ComboBoxTree.superclass.getValue.call(this);
			}
		},
        onRender : function(ct, position) {
          Ext.ux.ComboBoxTree.superclass.onRender.call(this, ct, position);
          this.on("expand", function() {
                if (!this.tree.rendered) {
                  this.tree.render(this.divId);
                }
              }, this)

        }
      });

  Ext.reg('combotree', Ext.ux.ComboBoxTree);