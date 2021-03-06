/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-09-21
 * http://code.google.com/p/anewssystem/
 */
 // 闭包，只创建一次
Ext.form.VTypes = function() {
    // 英文字母，空格，下划线
    var alpha = /^[a-zA-Z\ _]+$/;
    // 英文字幕，数字，空格，下划线
    var alphanum = /^[a-zA-Z0-9\ _]+$/;
    // 电子邮件
    var email = /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/;
    // url
    var url = /(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;
    // 中文
    var chn = /[^\xB0-\xF7]+$/; // davi

    // 下列信息和方法都是可配置的
    return {
        // email
        'email' : function(v){
            return email.test(v);
        },
        'emailText' : '请输入有效的电子邮件,格式如 "user@domain.com"',
        'emailMask' : /[a-z0-9_\.\-@]/i,

        // url
        'url' : function(v){
            return url.test(v);
        },
        'urlText' : '请输入有效的URL地址 "http:/'+'/www.domain.com"',

        // 英文字母
        'alpha' : function(v){
            return alpha.test(v);
        },
        'alphaText' : '必须为英文字母及_',
        'alphaMask' : /[a-z\ _]/i,

        // 英文字母和数字和下划线和点
        'alphanum' : function(v){
            return alphanum.test(v);
        },
        'alphanumText' : '请输入英文字母或是数字,其它字符是不允许的.', 

        'alphanumMask' : /[a-z0-9\.\ _]/i,// davi

        // 整数------
        'integer' : function(v){
            return alphanum.test(v);
        },
        'integerText' : '必须为整数, integer',
        'integerMask' : /[0-9]/i,

        // 数值型
        'number' : function(v){
            var sign = v.indexOf('-')>=0 ? '-' : '+';
            return IsNumber(v, sign); // double.test(v);
        },
        'numberText' : 'This field should only contain letters, number',
        'numberMask' : /[0-9\.]/i,

        // 汉字和英文字母
        'chn' : function(v){
            return chn.test(v);
        },
        'chnText' : '必须为汉字和英文字母',
        'chnMask' : /[\xB0-\xF7a-z0-9\.\/\#\,\ _-]/i
    };
}();

// qtip-当鼠标移动到控件上面时显示提示
// title-在浏览器的标题显示，但是测试结果是和qtip一样的
// under-在控件的底下显示错误提示
// side-在控件右边显示一个错误图标，鼠标指向图标时显示错误提示. 默认值.
// id-[element id]错误提示显示在指定id的HTML元件中

Ext.apply(Ext.form.VTypes, {
// 年龄
"age" : function(_v) {
   if (/^\d+$/.test(_v)) {
    var _age = parseInt(_v);
    if (_age < 200)
     return true;
   } else
    return false;
},
'ageText' : '年龄格式出错！！格式例如：20',
'ageMask' : /[0-9]/i,
// 密码验证
"repassword" : function(_v, field) {
   if (field.confirmTo) {
    var psw = Ext.get(field.confirmTo);
    return (_v == psw.getValue());
   }
   return true;
},
"repasswordText" : "密码输入不一致！！",
"repasswordMask" : /[a-z0-9]/i,
// 邮政编码
"postcode" : function(_v) {
   return /^[1-9]\d{5}$/.test(_v);
},
"postcodeText" : "该输入项目必须是邮政编码格式，例如：226001",
"postcodeMask" : /[0-9]/i,

// IP地址验证
"ip" : function(_v) {
   return /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
     .test(_v);

},
"ipText" : "该输入项目必须是IP地址格式，例如：222.192.42.12",
"ipMask" : /[0-9\.]/i,
// 固定电话及小灵通
"telephone" : function(_v) {
   return /(^\d{3}\-\d{7,8}$)|(^\d{4}\-\d{7,8}$)|(^\d{3}\d{7,8}$)|(^\d{4}\d{7,8}$)|(^\d{7,8}$)/
     .test(_v);
},
"telephoneText" : "该输入项目必须是电话号码格式，例如：0513-89500414,051389500414,89500414",
"telephoneMask" : /[0-9\-]/i,
// 手机
"mobile" : function(_v) {
   return /^1[35][0-9]\d{8}$/.test(_v);
},
"mobileText" : "该输入项目必须是手机号码格式，例如：13485135075",
"mobileMask" : /[0-9]/i,
// 身份证
"IDCard" : function(_v) {
   // return /(^[0-9]{17}([0-9]|[Xx])$)|(^[0-9]{17}$)/.test(_v);
   var area = {
    11 : "北京",
    12 : "天津",
    13 : "河北",
    14 : "山西",
    15 : "内蒙古",
    21 : "辽宁",
    22 : "吉林",
    23 : "黑龙江",
    31 : "上海",
    32 : "江苏",
    33 : "浙江",
    34 : "安徽",
    35 : "福建",
    36 : "江西",
    37 : "山东",
    41 : "河南",
    42 : "湖北",
    43 : "湖南",
    44 : "广东",
    45 : "广西",
    46 : "海南",
    50 : "重庆",
    51 : "四川",
    52 : "贵州",
    53 : "云南",
    54 : "西藏",
    61 : "陕西",
    62 : "甘肃",
    63 : "青海",
    64 : "宁夏",
    65 : "新疆",
    71 : "台湾",
    81 : "香港",
    82 : "澳门",
    91 : "国外"
   }
   var Y, JYM;
   var S, M;
   var idcard_array = new Array();
   idcard_array = _v.split("");
   // 地区检验
   if (area[parseInt(_v.substr(0, 2))] == null) {
    this.IDCardText = "身份证号码地区非法!!,格式例如:32";
    return false;
   }
   // 身份号码位数及格式检验
   switch (_v.length) {
    case 15 :
     if ((parseInt(_v.substr(6, 2)) + 1900) % 4 == 0
       || ((parseInt(_v.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(_v
         .substr(6, 2)) + 1900)
         % 4 == 0)) {
      ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// 测试出生日期的合法性
     } else {
      ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// 测试出生日期的合法性
     }
     if (ereg.test(_v))
      return true;
     else {
      this.IDCardText = "身份证号码出生日期超出范围,格式例如:19860817";
      return false;
     }
     break;
    case 18 :
     // 18位身份号码检测
     // 出生日期的合法性检查
     // 闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
     // 平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
     if (parseInt(_v.substr(6, 4)) % 4 == 0
       || (parseInt(_v.substr(6, 4)) % 100 == 0 && parseInt(_v
         .substr(6, 4))
         % 4 == 0)) {
      ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// 闰年出生日期的合法性正则表达式
     } else {
      ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// 平年出生日期的合法性正则表达式
     }
     if (ereg.test(_v)) {// 测试出生日期的合法性
      // 计算校验位
      S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10]))
        * 7
        + (parseInt(idcard_array[1]) + parseInt(idcard_array[11]))
        * 9
        + (parseInt(idcard_array[2]) + parseInt(idcard_array[12]))
        * 10
        + (parseInt(idcard_array[3]) + parseInt(idcard_array[13]))
        * 5
        + (parseInt(idcard_array[4]) + parseInt(idcard_array[14]))
        * 8
        + (parseInt(idcard_array[5]) + parseInt(idcard_array[15]))
        * 4
        + (parseInt(idcard_array[6]) + parseInt(idcard_array[16]))
        * 2
        + parseInt(idcard_array[7])
        * 1
        + parseInt(idcard_array[8])
        * 6
        + parseInt(idcard_array[9]) * 3;
      Y = S % 11;
      M = "F";
      JYM = "10X98765432";
      M = JYM.substr(Y, 1);// 判断校验位
      // alert(idcard_array[17]);
      if (M == idcard_array[17]) {
       return true; // 检测ID的校验位
      } else {
       this.IDCardText = "身份证号码末位校验位校验出错,请注意x的大小写,格式例如:201X";
       return false;
      }
     } else {
      this.IDCardText = "身份证号码出生日期超出范围,格式例如:19860817";
      return false;
     }
     break;
    default :
     this.IDCardText = "身份证号码位数不对,应该为15位或是18位";
     return false;
     break;
   }
},
"IDCardText" : "该输入项目必须是身份证号码格式，例如：32082919860817201x",
"IDCardMask" : /[0-9xX]/i,

//对当前文本框进行预后校验
"afterValidate": function(_v,field){
	var result;
	if(_v.length < 3) return;

	Ext.Ajax.request({
	      url : field.url,
	      method: 'POST',
	      async: false,//false-同步 true-异步
	      success : function(response) {
	         var res = Ext.util.JSON.decode(response.responseText);
	         result = res.success;
	         //return res.success;
	      }, 
	      failure : function() {
	    	  //return false;
	    	  result = false;
	      }, 
	      params: {currentValue:_v}
	});
	return result;
},
"afterValidateText" : "输入值不正确,请核对后重新输入！"
});

