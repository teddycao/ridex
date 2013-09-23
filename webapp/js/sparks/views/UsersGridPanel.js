Ext.define('Sparks.views.UsersGridPanel', {
    extend   : 'Ext.grid.Panel',
    alias    : 'widget.UsersGridPanel',
    requires : ['Sparks.stores.UserStore'],
	 border:false,
    initComponent : function() {
        this.store   = Sparks.stores.UserStore;
        this.columns = this.buildColumns();
        this.callParent();
    },
    buildColumns : function() {
        return [
            {
                header    : 'First Name',
                dataIndex : 'firstName',
                width     : 70
            },
            {
                header    : 'Last Name',
                dataIndex : 'lastName',
                width     : 70
            },
            {
                header    : 'DOB',
                dataIndex : 'dob',
                width     : 70
            },
            {
                header    : 'Login',
                dataIndex : 'userName',
                width     : 70
            }
        ];
    }
});
