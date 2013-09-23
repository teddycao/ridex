Ext.define('Sparks.models.UserModel', {
    extend   : 'Ext.data.Model',
    fields   : [
        'firstName',
        'lastName',
        'dob',
        'userName'
    ]
});


Ext.define('Sparks.stores.UserStore', {
    extend    : 'Ext.data.Store',
    singleton : true,
    requires  : ['Sparks.models.UserModel'],

    model     : 'Sparks.models.UserModel',
//    storeId   : 'Sparks.stores.UserStore',

    constructor : function() {
        this.callParent(arguments);
        this.loadData([
            {
                firstName : 'Louis',
                lastName  : 'Dobbs',
                dob       : '12/21/34',
                userName  : 'ldobbs'
            },
            {
                firstName : 'Sam',
                lastName  : 'Hart',
                dob       : '03/23/54',
                userName  : 'shart'
            },
            {
                firstName : 'Nancy',
                lastName  : 'Garcia',
                dob       : '01/18/24',
                userName  : 'ngarcia'
            }
        ]);
    }
});