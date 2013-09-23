    //�Զ�����չһ����Tree��comboBox
      Ext.ux.LovTreeCombo = Ext.extend(Ext.form.ComboBox, {
      initList: function() {
          this.list = new Ext.tree.TreePanel({
              floating: true,
              autoHeight: false,
              autoExpand: true,
              height: 240,
              rootVisible: false,
              containerScroll: true,
              dataUrl: this.url,
              root: {
                  nodeType: 'async',
                  text: 'root',
                  draggable: false,
                  id: 'root'
              }
       ,

              listeners: {
                  checkchange: this.onNodeCheckChange,
                  scope: this

              },
              useArrows: true, 
              alignTo: function(el, pos) {
                  this.setPagePosition(this.el.getAlignToXY(el, pos));
              }
          });

      },

      expand: function() {
          if (!this.list.rendered) {
              this.list.render(document.body);
              this.list.setWidth("660px"); 
              this.innerList = this.list.body;
              this.list.hide();
          }
          this.el.focus();
          Ext.ux.LovTreeCombo.superclass.expand.apply(this, arguments);
      },

      doQuery: function(q, forceAll) {
          this.expand();
      },

      collapseIf: function(e) {
          if (!e.within(this.wrap) && !e.within(this.list.el)) {
              this.collapse();
          }
      },

      valueList: [],
      textList: [],

      getvalueList: function() {
          return this.valueList;
      },

      onNodeCheckChange: function(node, e) {
          if (!node.leaf) {
              node.expand(true, false, function() {
                  node.eachChild(function(child) {
                      child.ui.toggleCheck(node.attributes.checked);
                      child.attributes.checked = node.attributes.checked;
                      child.fireEvent('checkchange', child, node.attributes.checked);
                  });

              });

          }
          else {
              //alert(1)
              var nodeValue = node.id;
              var test = this.valueList.indexOf(nodeValue);

              if (test == -1 && node.attributes.checked) {
                  this.valueList.push(nodeValue)
                  this.textList.push(node.text);
              }

              if (test != -1 && !node.attributes.checked) {
                  this.valueList.remove(nodeValue);
                  this.textList.remove(node.text);
              }

              //if(window.console){console.log(this.valueList.toString())}��ѡ����'+this.valueList.length.toString()+'�˵���'+
              var str = this.textList.toString();
              this.setRawValue(str);


              if (this.hiddenField) {
                  this.hiddenField.value = node.id;
              }
          }
          //this.collapse();
      },
      url: '',
      reset: function() {

          this.valueList.length = 0;
          this.textList.length=0;
          this.applyEmptyText();

      },


      resetNode: function(node) {
          this.collapseNode(node);
          this.uncheckNode(node);
      },

      collapseNode: function(node) {
          if (node.isExpanded()) {
              node.collapse();
          }
      },

      uncheckNode: function(node) {

          if (node.getUI().isChecked()) {
              if (window.console) { console.log("δ��ѡ�д˽ڵ�ID " + node.attributes.id) }

              node.getUI().toggleCheck(false);
          }
      }


  });
  
  //���ע��һ����
  Ext.reg('treecombo', Ext.ux.LovTreeCombo);


  //�����ʹ�÷���
  /**
    var lovTreeCombo2 = new Ext.ux.LovTreeCombo({
    id:'cmb',
    renderTo: 'cmb',
    url: 'LoadTreeMenu.aspx ',
    emptyText: 'ѡ������',
    width: 660,
    listWidth: 180
    });
    
    })
 */