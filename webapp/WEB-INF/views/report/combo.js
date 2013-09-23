Ext.ns('MyNamespace');

MyNamespace.MyComboBox = Ext.extend(Ext.form.ComboBox, {
  displayField: 'displayValue',
  triggerAction: 'all',
  valueField: 'ID',

  initComponent: function () {
    this.store = new Ext.data.JsonStore({
      url: 'url',
      baseParams: {
        //params
      },
      fields: [
        {name:'ID', mapping: 'ID', type: 'int'},
        {name:'displayValue', mapping: 'displayValue', type: 'string'},
      ],
      root: 'root'
    });

    var me = this;
    this.store.on('load',function(store) {
      me.setValue(store.getAt(0).get('ID'));
    })

    MyNamespace.MyComboBox.superclass.initComponent.call(this);

    this.store.load();
  }

});