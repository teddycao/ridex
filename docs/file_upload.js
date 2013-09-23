var upload=function (callFunc) {
	var i = 0;
	var currentField;
	function getUploadField(callFun) {//增加一行文件框
		var fileName = "文件 " + (++i) + " :  ";
		var uploadFileTf = new Ext.form.TextField({
					xtype : 'textfield',
					columnWidth : .7,
					name : 'file',
					inputType : 'file',
					allowBlank : false,
					blankText : '请选择上传文件',
					anchor : '90%'
				});
		var lbl = new Ext.form.Label({
					text : fileName,
					columnWidth : .15,
					style : 'font-weight:bold;vertical-align: middle'
				});
		var fieldSet = new Ext.form.FieldSet({
					layout : 'column',
					border : false
				});
		fieldSet.add(lbl);
		fieldSet.add(uploadFileTf);
		if (i != 1) {
			var deleteBtn = new Ext.Button({
						text : '删除',
						icon : MCLONIS + "/js/images/delete.gif",
						columnWidth : .15,
						handler : function() {
							fieldSet.destroy();
							upload_win.setHeight(upload_win.getHeight() - 37);
							uploadForm.doLayout(true);
						}
					});
			fieldSet.add(deleteBtn);
		}
		return fieldSet;
	}
	//上传form
	var uploadForm = new Ext.form.FormPanel({
				baseCls : 'x-plain',
				labelWidth : 80,
//				layout:'fit',
				autoHeight:true,
				style:'margin-top:10',
				frame : true,
				disabledClass : "x-item-disabled",
				tbar : [{
							xtype : 'button',
							text : '添加上传',
							labelAlign : 'right',
							icon : KANGSOFT + "/js/images/add_16.gif",
							handler : function() {
								var uf = getUploadField();
								uploadForm.add(uf);
								uploadForm.doLayout(true);
								upload_win.setHeight(upload_win.getHeight()
										+ 47);
							}
						}, {
							xtype : 'label',
							text : '(单个最大允许上传50M)'
						}],
				url : MCLONIS + '/sm/fileUploadAction!fileUpLoad.action',
				fileUpload : true,
				defaultType : 'textfield',
				items : [getUploadField()]
			});
	var upload_win = new Ext.Window({
				title : '文件上传',
				width : 500,
				layout : 'fit',
				plain : true,
				bodyStyle : 'padding:5px;',
				buttonAlign : 'center',
				items : uploadForm,
				resizable : false,
				closeAction : 'close',
				loadMask : true,
				height:160,
				buttons : [{
					text : '开始上传',
					icon : MCLONIS + "/js/images/upload.png",
					handler : function() {
						if (uploadForm.form.isValid()) {
							Ext.MessageBox.show({
										title : 'Please wait',
										msg : '上传中...',
										progressText : '- - - -上传中- - - -',
										width : 300,
										progress : true,
										closable : false,
										animEl : 'loding'
									});
							uploadForm.getForm().submit({
								success : function(form, action) {
									var result = Ext.util.JSON
											.decode(action.response.responseText);
									Ext.Msg.alert('信息提示', result.message);
									var fn = callFunc.createCallback(result);
									fn();
									upload_win.hide();
								},
								failure : function() {
									Ext.Msg.alert('信息提示', '文件上传失败');
									upload_win.show();
								}
							})
						}
					}
				}, {
					text : '关闭',
					icon : MCLONIS + "/js/images/btn-cancel.png",
					handler : function() {
						upload_win.hide();
					}
				}]
			});
		upload_win.show();
}