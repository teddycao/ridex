/**
 * @author wangliang
 * 
 * ��Ҫ Adapter.js ֧��<br>
 * ���ڲ�������δ�޸ģ�<br>
 * 1)�����ɸѡʱ���㶪ʧ���˵����أ��޷�����ѡ�����<br>
 * 2)Ĭ��ѡ�����޷����й�ѡ
 * <p>
 * ����:
 * </p>
 * 
 * <pre><code>
 * var ref = new OECP.ui.GridRefField({
 *             minChars : 2,
 *             mode : 'local',
 *             valueField : 'id',
 *             codeField : 'id',
 *             displayField : 'name',
 *             refUrl : '/warehouse/ref.do',
 *             storeFields : ['id', 'name', {
 *                         name : 'person',
 *                         mapping : 'person.name'
 *                     }, 'address', 'phone', {
 *                         name : 'org',
 *                         mapping : 'org.name'
 *                     }],
 *             refColumns : [{
 *                         header : '����',
 *                         dataIndex : 'id',
 *                         hidden : true,
 *                         width : 30
 *                     }, {
 *                         header : '�ֿ�����',
 *                         dataIndex : 'name',
 *                         width : 80
 *                     }, {
 *                         header : '�ֿ�����',
 *                         dataIndex : 'person',
 *                         width : 80
 *                     }, {
 *                         header : '��ַ',
 *                         dataIndex : 'address',
 *                         width : 80
 *                     }, {
 *                         header : '�绰',
 *                         dataIndex : 'phone',
 *                         width : 80
 *                     }, {
 *                         header : '��������',
 *                         dataIndex : 'org',
 *                         width : 80
 *                     }]
 *         });
 * ref.render('ref');
 * </code></pre>
 */
Ext.ns('OECP.ui');
/**
 * ������ѡ¼��� <br>
 * ���˵�
 * 
 * @class OECP.ui.RefField
 * @extends Ext.form.ComboBox
 */
OECP.ui.GridRefField = Ext.extend(Ext.form.ComboBox, {
            /**
             * @cfg {Number} gridHeight ���߶�
             */
            gridHeight : 200,
            /**
             * @cfg {Number} gridWeight �����
             */
            gridWidth : 300,
            /**
             * @cfg {String} title ����
             */
            /**
             * @cfg {Ext.data.Stroe} store ���ݲֿ�
             */
            /**
             * @cfg {Ext.grid.GridPanel} grid ���
             */
            /**
             * @cfg {Ext.menu.Menu} menu �����˵�
             */
            /**
             * @cfg {String} refUrl ���ݼ���Url
             */
            /**
             * @cfg {Array} storeFields ���ݲֿ��ֶ��б�
             */
            /**
             * @cfg {Array} refColumns �����ģ��
             */
            /**
             * @cfg {Boolean} multiple �Ƿ��ѡ,Ĭ��Ϊfalse��ѡ
             */
            multiple : false,
            emptyText : '��ѡ��һ������',
            /**
             * 
             * @cfg {Boolean} showCheckBox �Ƿ���ʾ��ѡ��,Ĭ����ʾ
             */
            showCheckBox : true,
            /**
             * @cfg {String} codeField �����ֶ�<br>
             *      ҵ����Ա¼������ʱͨ����¼������ݱ��룬�ֶ������ֹ�¼�����ݹ���ʹ��
             */
            codeField : '',
            /**
             * @cfg {Boolean} showRowNum �Ƿ���ʾ�к�,Ĭ����ʾ
             */
            showRowNum : true,
            /**
             * @type {Number} ÿҳ����ʾ����
             */
            pageSize : 25,
            /**
             * @cfg {Boolean} autoLoad �Զ�����
             */
            autoLoad : true,
            // private ��ѡ�е�record����
            refRecords : [],
            onTriggerClick : function() {
                if (this.menu) {
                    this.menu.show(this.el, "tl-bl?");
                }
            },
            // private ���˫���¼�
            onRowclick : function(grid, rowIndex, e, scope) {
                if (!scope) {
                    scope = this;
                }
                var refval = '';
                scope.refRecords = [];
                scope.refRecords = scope.grid.getSelectionModel()
                        .getSelections();
                if (scope.refRecords.length > 0) {
                    if (!scope.multiple) {// ��ѡ
                        refval = scope.refRecords[0][scope.valueField]
                    } else {// ��ѡ
                        for (var i = 0; i < scope.refRecords.length; i++) {
                            refval = refval
                                    + scope.refRecords[i][scope.valueField]
                                    + ',';
                        }
                        refval = refval.substring(0, refval.length - 1);
                    }
                }
                scope.setValue(refval);// ��ֵ
                this.fireEvent('refselect', this);
                scope.menu.hide();
            },
            initComponent : function() {
                this.initMenu();
                this.addEvents(
                        /**
                         * @event refselect ����ѡ���¼��������ȷ������ť��˫��������ʱ����
                         * @param {OECP.ui.GirdRefField}
                         *            refselect ������ this
                         */
                        'refselect');
                OECP.ui.GridRefField.superclass.initComponent.call(this);
            },
            /**
             * ����Combo����������ҵ��¼��ϰ�ߣ���ӱ�����չ���
             */
            doQuery : function(q, forceAll) {
                q = Ext.isEmpty(q) ? '' : q;
                var qe = {
                    query : q,
                    forceAll : forceAll,
                    combo : this,
                    cancel : false
                };
                if (this.fireEvent('beforequery', qe) === false || qe.cancel) {
                    return false;
                }
                q = qe.query;
                forceAll = qe.forceAll;
                if (forceAll === true || (q.length >= this.minChars)) {
                    if (this.lastQuery !== q) {
                        this.lastQuery = q;
                        if (this.mode == 'local') {
                            this.selectedIndex = -1;
                            if (forceAll) {
                                this.store.clearFilter();
                            } else {
                                this.store.filter(this.codeField
                                                || this.displayField, q);
                            }
                            this.onLoad();
                        } else {
                            this.store.baseParams[this.queryParam] = q;
                            this.store.load({
                                        params : this.getParams(q)
                                    });
                            this.expand();
                        }
                    } else {
                        this.selectedIndex = -1;
                        this.onLoad();
                    }
                }
            },
            // private ��ʼ���˵�
            initMenu : function() {
                var scope = this;
                if (!this.store) {
                    this.store = new Ext.data.JsonStore({
                                autoLoad : scope.autoLoad,
                                remoteSort : true,
                                url : scope.refUrl,
                                root : 'result',
                                totalProperty : 'totalCounts',
                                remoteSort : true,
                                fields : scope.storeFields
                            });
                }
                if (!this.grid) {
                    var cols = [];
                    // TODO �����󽹵㶪ʧ���������¼�
                    // ���ѡ����
                    // var sm = new Ext.grid.CheckboxSelectionModel({
                    // hidden : !scope.showCheckBox,
                    // singleSelect : !scope.multiple
                    // });
                    // cols.push(sm);
                    // ��ʾ�к�
                    if (this.showRowNum) {
                        cols.push(new Ext.grid.RowNumberer());
                    }
                    // ׷�ӱ���ֶ�
                    for (var i = 0; i < scope.refColumns.length; i++) {
                        cols.push(scope.refColumns[i]);
                    }
                    var bbar;
                    if (this.page) {
                        bbar = new Ext.PagingToolbar({
                                    pageSize : scope.pageSize,
                                    store : scope.store,
                                    displayInfo : true,
                                    displayMsg : "��ǰҳ��¼����{0}-{1}�� ��{2}����¼",
                                    emptyMsg : "��ǰû�м�¼" 
                                })
                    }
                    this.grid = new Ext.grid.GridPanel({
                                title : scope.title || '',
                                height : this.gridHeight,
                                width : this.gridWidth,
                                region : 'center',
                                store : this.store,
                                remoteSort : false,
                                cm : new Ext.grid.ColumnModel(cols),
                                listeners : {
                                    'rowclick' : scope.onRowclick
                                            .createDelegate(scope, [scope],
                                                    true)
                                },
                                bbar : bbar
                            })
                } else {
                    this.grid.on('rowclick', scope.onRowclick, scope);
                }
                if (!this.menu) {
                    this.menu = new Ext.menu.Menu({
                                items : [new Ext.menu.Adapter(scope.grid)]
                            })
                }
            },
            onDestroy : function() {
                Ext.destroy(this.menu);
                OECP.ui.RefField.superclass.onDestroy.call(this);
            }
        });
Ext.reg('gridreffield', OECP.ui.GridRefField);
