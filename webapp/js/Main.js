var zving={};
var CONTEXTPATH = '/channel/';

var scripts = document.getElementsByTagName("script");
for(var i=0;i<scripts.length;i++){
	if(/.*Framework\/Main\.js$/g.test(scripts[i].getAttribute("src"))){
		var jsPath = scripts[i].getAttribute("src").replace(/Framework\/Main\.js$/g,'');
		if(jsPath.indexOf("/")==0||jsPath.indexOf("://")>0){
			CONTEXTPATH = jsPath;
			break;
		}
		var arr1 = jsPath.split("/");
		var path = window.location.href;
		if(path.indexOf("?")!=-1){
			path = path.substring(0,path.indexOf("?"));
		}
		var arr2 = path.split("/");
		arr2.splice(arr2.length-1,1);
		for(var i=0;i<arr1.length;i++){
			if(arr1[i]==".."){
				arr2.splice(arr2.length-1,1);
			}
		}
		CONTEXTPATH = arr2.join('/')+'/';
		break;
	}
}
var BLANK_IMAGE_URL=CONTEXTPATH+'Framework/Images/blank.gif';

var isQuirks = document.compatMode == "BackCompat";
var isStrict = document.compatMode == "CSS1Compat";
var isGecko = navigator.userAgent.toLowerCase().indexOf("gecko") != -1;
var isChrome = navigator.userAgent.toLowerCase().indexOf("chrome") != -1;
var isOpera = navigator.userAgent.toLowerCase().indexOf("opera") != -1;
var isIE = navigator.userAgent.toLowerCase().indexOf("msie") != -1 && !isOpera;
var isIE8 = navigator.userAgent.toLowerCase().indexOf("msie 8") != -1 && !!window.XDomainRequest && !!document.documentMode;
var isIE7 = navigator.userAgent.toLowerCase().indexOf("msie 7") != -1 && !isIE8;
var isIE6 = isIE && parseInt(navigator.userAgent.split(";")[1].replace(/(^\s*)|(\s*$)/g,"").split(" ")[1])<7;
var isBorderBox = isIE && isQuirks;

if(!isIE&&HTMLElement){
	var p = HTMLElement.prototype;
	p.__defineSetter__("innerText",function(txt){this.textContent = txt;});
	p.__defineGetter__("innerText",function(){return this.textContent;});
	p.insertAdjacentElement = function(where,parsedNode){
		switch(where){
		  case "beforeBegin":
		    this.parentNode.insertBefore(parsedNode,this);
			return this.previousSibling;
		    break;
		  case "afterBegin":
		    this.insertBefore(parsedNode,this.firstChild);
			return this.firstChild;
		    break;
		  case "beforeEnd":
		    this.appendChild(parsedNode);
			return this.lastChild;
		    break;
		  case "afterEnd":
		    if(this.nextSibling)
		      this.parentNode.insertBefore(parsedNode,this.nextSibling);
		    else
		      this.parentNode.appendChild(parsedNode);
			return this.nextSibling;
		    break;
		  }
	};
	p.insertAdjacentHTML = function(where,htmlStr){
		var r=this.ownerDocument.createRange();
		r.setStartBefore(this);
		var parsedHTML=r.createContextualFragment(htmlStr);
		return this.insertAdjacentElement(where,parsedHTML);
	};
	p.attachEvent = function(evtName,func){
		evtName = evtName.substring(2);
		this.addEventListener(evtName,func,false);
	}
	p.detachEvent = function(evtName,func){
		evtName = evtName.substring(2);
		this.removeEventListener(evtName,func,false);
	}
	p.fireEvent=function (evtName) {
		evtName = evtName.substring(2);
		var evtObj = document.createEvent('HTMLEvents');//HTMLEvents或Events
		evtObj.initEvent(evtName, true, true);
		this.dispatchEvent(evtObj);
	}
	window.attachEvent = document.attachEvent = p.attachEvent;
	window.detachEvent = document.detachEvent = p.detachEvent;
	window.fireEvent = document.fireEvent = p.fireEvent;
	p.__defineGetter__("currentStyle", function(){
		return this.ownerDocument.defaultView.getComputedStyle(this,null);
  });
	p.__defineGetter__("children",function(){
    var tmp=[];
    for(var i=0;i<this.childNodes.length;i++){
    	var n=this.childNodes[i];
      if(n.nodeType==1){
      	tmp.push(n);
      }
    }
    return tmp;
  });
	p.__defineSetter__("outerHTML",function(sHTML){
    var r=this.ownerDocument.createRange();
    r.setStartBefore(this);
    var df=r.createContextualFragment(sHTML);
    this.parentNode.replaceChild(df,this);
    return sHTML;
  });
  p.__defineGetter__("outerHTML",function(){
    var attr;
		var attrs=this.attributes;
		var str="<"+this.tagName.toLowerCase();
		for(var i=0;i<attrs.length;i++){
		    attr=attrs[i];
		    if(attr.specified){
		        str+=" "+attr.name+'="'+attr.value+'"';
		    }
		}
		if(!this.hasChildNodes){
		    return str+">";
		}
		return str+">"+this.innerHTML+"</"+this.tagName.toLowerCase()+">";
  });
	p.__defineGetter__("canHaveChildren",function(){
	  switch(this.tagName.toLowerCase()){
			case "area":
			case "base":
			case "basefont":
			case "col":
			case "frame":
			case "hr":
			case "img":
			case "br":
			case "input":
			case "isindex":
			case "link":
			case "meta":
			case "param":
			return false;
    }
    return true;
  });
  p.__defineGetter__("parentElement",function(){
		if(this.parentNode==this.ownerDocument){
			return null;
		}
		return this.parentNode;
	});
  Event.prototype.__defineGetter__("srcElement",function(){
    var node=this.target;
    while(node&&node.nodeType!=1)node=node.parentNode;
    return node;
  });
  Event.prototype.__defineGetter__("offsetX", function(){
    return this.layerX;
  });
  Event.prototype.__defineGetter__("offsetY", function(){
    return this.layerY;
  });
  Event.prototype.__defineSetter__("returnValue",function(b){//
	if(!b)this.preventDefault();
	return b;
  });
  Event.prototype.__defineSetter__("cancelBubble",function(b){// 设置或者检索当前事件句柄的层次冒泡
	if(b)this.stopPropagation();
	return b;
  });
  document.parentWindow=document.defaultView;
}else{
	try {
		document.documentElement.addBehavior("#default#userdata");
		document.execCommand('BackgroundImageCache', false, true);
	} catch(e) {alert(e)}
}

var _setTimeout = window.setTimeout;
window.setTimeout = function(callback,timeout,param){
	if(typeof callback == 'function'){
		var args = Array.prototype.slice.call(arguments,2);
		var _callback = function(){
			callback.apply(null,args);
		}
		return _setTimeout(_callback,timeout);
	}
	return _setTimeout(callback,timeout);
}

function extra(o, c, defaults) {//复制对象c的成员到对象o
    if(!o){
        o={};
    }
    if (defaults) {
        extra(o, defaults)
    }
    if (o && c && typeof c == 'object') {
        for (var p in c) {
            o[p] = c[p]
        }
    }
    return o
}
function extraIf(o, c) {
    if(!o){
        o={};
    }
	for (var p in c) {
		if (typeof(o[p]) == 'undefined') {
			o[p] = c[p]
		}
	}
    return o
}
function toArray(a,i,j) {//把可枚举的集合转换为数组
	 if(isIE){
		 var res = [];
		 for (var x = 0,
		 len = a.length; x < len; x++) {
			 res.push(a[x])
		 }
		 return res.slice(i || 0, j || res.length)
	 }else{
		 return Array.prototype.slice.call(a, i || 0, j || a.length)
	 }
}

var Core = {};
Core.addToCache = function(ele,id) {//zving.elCache统一存储dom元素相关数据、自定义事件
	if(!ele)
		return;
    if(!(id = id||ele.id))
		return;
	if (!zving.elCache[id]) {
		zving.elCache[id] = {
			el: ele,
			data: {},
			events: {}
		};
    }
}
Core.attachMethod = function(ele){
	if(!ele||ele["$A"]){
		return;
	}
	if(ele.nodeType==9){
		return;
	}
	var win;
	try{
		if(isGecko){
			win = ele.ownerDocument.defaultView;
		}else{
			win = ele.ownerDocument.parentWindow;
		}
		extra(ele,win.ExtendElement);
	}catch(ex){
		//alert("Core.attachMethod:"+ele)//有些对象不能附加属性，如flash
	}
}

var Constant = {};

Constant.Null = "_ZVING_NULL";

zving.idSeed = 0;
zving.elCache={};//统一存储dom元素相关数据、自定义事件
zving.enableGarbageCollector=true;//
function garbageCollect() {//垃圾回收
 if (!zving.enableGarbageCollector) {
	 clearInterval(zving.collectorThreadId)
 } else {
	 var eid, el, d, o;
	 for (eid in zving.elCache) {
		 o = zving.elCache[eid];
		 if (o.skipGC) {
			 continue
		 }
		 el = o.el;
		 d = el.dom?el.dom:el;
		 if (!d || !d.parentNode || (!d.offsetParent && !document.getElementById(eid))) {
			 if (zving.enableListenerCollection) {
				 EventManager.removeAll(d)
			 }
			 delete zving.elCache[eid]
		 }
	 }
	 if (isIE) {
		 var t = {};
		 for (eid in zving.elCache) {
			 t[eid] = zving.elCache[eid]
		 }
		 zving.elCache = t
	 }
 }
}
zving.collectorThreadId = setInterval(garbageCollect, 30000);
window.attachEvent('onunload',function(){zving.elCache={}});//在页面关闭时清除elCache，释放内存

function $(ele) {
  if(typeof(ele) == 'string'){
    ele = document.getElementById(ele)
  }
  if(!ele){
    return null;
  }
  if(ele.tagName&&ele.tagName.indexOf(":")== -1){//如果不为模板标签
	if(!ele.id){
		ele.id="uid_"+(ele.tagName?ele.tagName.toLowerCase():'')+(++ zving.idSeed)
	}
	Core.addToCache(ele);
	Core.attachMethod(ele);
  }
  return ele;
}

function $V(ele){
	var eleId = ele;
	ele = $(ele);
	if(!ele){
		alert("表单元素不存在:"+eleId);
		return null;
	}
  switch (ele.type.toLowerCase()) {
    case 'submit':
    case 'hidden':
    case 'password':
    case 'textarea':
    case 'file':
    case 'image':
    case 'select-one':
    case 'text':
      return ele.value;
    case 'checkbox':
    case 'radio':
      if (ele.checked){
    		return ele.value;
    	}else{
    		return null;
    	}
    default:
    		return "";
  }
}

function $S(ele,v){
	var eleId = ele;
	ele = $(ele);
	if(!v&&v!=0){
		v = "";
	}
	if(!ele){
		alert("表单元素不存在:"+eleId);
		return;
	}
  switch (ele.type.toLowerCase()) {
    case 'submit':
    case 'hidden':
    case 'password':
    case 'textarea':
    case 'button':
    case 'file':
    case 'image':
    case 'select-one':
    case 'text':
      ele.value = v;
      break;
    case 'checkbox':
    case 'radio':
      if(ele.value==""+v){
      	ele.checked = true;
      }else{
      	ele.checked=false;
      }
      break;
   }
}

function $T(tagName,ele){
	ele = $(ele);
	ele = ele || document;
	var ts = ele.getElementsByTagName(tagName);//此处返回的不是数组
	var arr = [];
	var len = ts.length;
	for(var i=0;i<len;i++){
		arr.push($(ts[i]));
	}
	return arr;
}

function $N(ele){
    if (typeof(ele) == 'string'){
      ele = document.getElementsByName(ele)
      if(!ele||ele.length==0){
    		return null;
      }
      var arr = [];
      for(var i=0;i<ele.length;i++){
      	var e = ele[i];
      	if(e.getAttribute("ztype")=="select"){
      	e = e.parentNode;
      	}
      	Core.attachMethod(e);
      	arr.push(e);
      }
      ele = arr;
    }
    return ele;
}

function $NV(ele){
	ele = $N(ele);
	if(!ele){
		return null;
	}
	var arr = [];
	for(var i=0;i<ele.length;i++){
		var v = $V(ele[i]);
		if(v!=null){
			arr.push(v);
		}
	}
	return arr.length==0? null:arr;
}

function $NS(ele,value){
	ele = $N(ele);
	if(!ele){
		return;
	}
	if(!ele[0]){
		return $S(ele,value);
	}
	if(ele[0].type=="checkbox"){
		if(value==null){
			value = new Array(4);
		}
		var arr = value;
		if(!isArray(value)){
			arr = value.split(",");
		}
		for(var i=0;i<ele.length;i++){
			for(var j=0;j<arr.length;j++){
				if(ele[i].value==arr[j]){
					$S(ele[i],arr[j]);
					break;
				}
				$S(ele[i],arr[j]);
			}
		}
		return;
	}
	for(var i=0;i<ele.length;i++){
		$S(ele[i],value);
	}
}

function $F(ele){
	if(!ele)
		return document.forms[0];
	else{
		ele = $(ele);
		if(ele&&ele.tagName.toLowerCase()!="form")
			return null;
		return ele;
	}
}

//多选框全选
function selectAll(ele,eles){
	var flag = $V(ele);
	var arr = $N(eles);
	if(arr){
		for(var i=0;i< arr.length;i++){
			arr[i].checked = flag;
	  }
	}
}

function encodeURL(str){
	return encodeURI(str).replace(/=/g,"%3D").replace(/\+/g,"%2B").replace(/\?/g,"%3F").replace(/\&/g,"%26");
}

function htmlEncode(str) {
	return str.replace(/&/g,"&amp;").replace(/\"/g,"&quot;").replace(/</g,"&lt;").replace(/>/g,"&gt;").replace(/ /g,"&nbsp;");
}

function htmlDecode(str) {
	return str.replace(/\&quot;/g,"\"").replace(/\&lt;/g,"<").replace(/\&gt;/g,">").replace(/\&nbsp;/g," ").replace(/\&amp;/g,"&");
}

function javaEncode(txt) {
	if (!txt) {
		return txt;
	}
	return txt.replace("\\", "\\\\").replace("\r\n", "\n").replace("\n", "\\n").replace("\"", "\\\"").replace("\'", "\\\'");
}

function javaDecode(txt) {
	if (!txt) {
		return txt;
	}
	return txt.replace("\\\\", "\\").replace( "\\n", "\n").replace("\\r", "\r").replace("\\\"", "\"").replace("\\\'", "\'");
}

function isInt(str){
	return /^\-?\d+$/.test(""+str);
}

function isNumber(str){
	var t = ""+str;
	var dotCount = 0;
	for(var i=0;i<str.length;i++){
		var c = str.charAt(i);
		if(c=="."){
			if(i==0||i==str.length-1||dotCount>0){
				return false;
			}else{
				dotCount++;
			}
		}else if(c=='-'){
			if(i!=0){
				return false;
			}
		}else	if(isNaN(parseInt(c))){
			return false;
		}
	}
	return true;
}

function isTime(str){
	if(!str){
		return false;
	}
	var arr = str.split(":");
	if(arr.length!=3){
		return false;
	}
	if(!isNumber(arr[0])||!isNumber(arr[1])||!isNumber(arr[2])){
		return false;
	}
	var date = new Date();
	date.setHours(arr[0]);
	date.setMinutes(arr[1]);
	date.setSeconds(arr[2]);
	return date.toString().indexOf("Invalid")<0;
}

function isDate(str){
	if(!str){
		return false;
	}
	var arr = str.split("-");
	if(arr.length!=3){
		return false;
	}
	if(!isNumber(arr[0])||!isNumber(arr[1])||!isNumber(arr[2])){
		return false;
	}
	var date = new Date();
	date.setFullYear(arr[0]);
	date.setMonth(arr[1]);
	date.setDate(arr[2]);
	return date.toString().indexOf("Invalid")<0;
}

function isDateTime(str){
	if(!str){
		return false;
	}
	if(str.indexOf(" ")<0){
		return isDate(str);
	}
	var arr = str.split(" ");
	if(arr.length<2){
		return false;
	}
	return isDate(arr[0])&&isTime(arr[1]);
}

function isArray(v) {
   return !! v && Object.prototype.toString.apply(v) === '[object Array]'
}

function isFunction(v) {
   return !! v && Object.prototype.toString.apply(v) === '[object Function]'
}

function isObject(v) {
   return !! v && Object.prototype.toString.call(v) === '[object Object]'
}

function isElement(v) {
   return !! v && v.tagName
}

function isEmpty(v, allowBlank) {
	return v === null || v === undefined || ((isArray(v) && !v.length)) || (!allowBlank ? v === '': false)
};

function isNull(v){
	return v===null||typeof(v)=="undefined";
}

function isNotNull(v){
	return !isNull(v);
}

function getEvent(evt){
	evt = evt||window.event;
	if(!evt) {
		var func = getEvent.caller;
		while(func) {
			var evt = func.arguments[0];
			if(evt && (evt.constructor  == Event || evt.constructor == MouseEvent)) {
				break;
			}
			func=func.caller;
		}
	}
	return evt;
}
function fixEvent(event){//在IE下为event对象添加pageX、pageY属性
	if(!event){return null;}
	var doc=document;
	var sl = Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft);
	var st = Math.max(doc.documentElement.scrollTop, doc.body.scrollTop);
	if(typeof event.pageX == "undefined")event.pageX = event.clientX + sl - doc.body.clientLeft;
	if(typeof event.pageY == "undefined")event.pageY = event.clientY + st - doc.body.clientTop;
	return event;
}
function stopEvent(evt){//阻止一切事件执行,包括浏览器默认的事件
	evt = getEvent(evt);
	if(!evt){
		return;
	}
	if(isGecko){
		evt.preventDefault();
		evt.stopPropagation();
	}
	evt.cancelBubble = true;
	evt.returnValue = false;
}

function cancelEvent(evt){//仅阻止用户定义的事件
	evt = getEvent(evt);
	evt.cancelBubble = true;
}

function getEventPosition(evt){//返回相对于最上级窗口的左上角原点的坐标
	evt = getEvent(evt);
	var pos = {x:evt.clientX, y:evt.clientY};
	var win;
	if(isGecko){
		win = evt.srcElement.ownerDocument.defaultView;
	}else{
		win = evt.srcElement.ownerDocument.parentWindow;
	}
	var sw,sh;
	while(win!=win.parent){
		if(win.frameElement&&win.parent.zving){
			pos2 = $E.getPosition(win.frameElement);
			pos.x += pos2.x;
			pos.y += pos2.y;
		}
		sw = Math.max(win.document.body.scrollLeft, win.document.documentElement.scrollLeft);
		sh = Math.max(win.document.body.scrollTop, win.document.documentElement.scrollTop);
		pos.x -= sw;
		pos.y -= sh;
		if(!win.parent.zving){
			break;
		}
		win = win.parent;
	}

	return pos;
}

function getEventPositionLocal(evt){//返回事件在当前页面上的坐标
	evt = getEvent(evt);
	var pos = {x:evt.clientX, y:evt.clientY};
	var win;
	if(isGecko){
		win = evt.srcElement.ownerDocument.defaultView;
	}else{
		win = evt.srcElement.ownerDocument.parentWindow;
	}
	pos.x += Math.max(win.document.body.scrollLeft, win.document.documentElement.scrollLeft);
	pos.y += Math.max(win.document.body.scrollTop, win.document.documentElement.scrollTop);
	return pos;
}

function toXMLDOM(xml){
	var doc;
	if(isIE){
		try{
			doc = new ActiveXObject("Microsoft.XMLDOM");
		}catch(ex){
			doc = new ActiveXObject("Msxml2.DOMDocument");
		}
		doc.loadXML(xml);
	}else{
	   var p =new DOMParser();
	   doc= p.parseFromString(xml,"text/xml");
	}
	return doc;
}

var JSON = {};

JSON.toString=function(O) {
	var string = [];
	var isArray = function(a) {
		var string = [];
		for(var i=0; i< a.length; i++) string.push(JSON.toString(a[i]));
		return string.join(',');
	};
	var isObject = function(obj) {
		var string = [];
		for (var p in obj){
			if(obj.hasOwnProperty(p) && p!='prototype'){
				string.push('"'+p+'":'+JSON.toString(obj[p]));
			}
		};
		return string.join(',');
	};
	if (!O&&typeof O == 'string') return '';
	if (!O) return false;
	if (O instanceof Function) string.push(O);
	else if (O instanceof Array) string.push('['+isArray(O)+']');
	else if (typeof O == 'object') string.push('{'+isObject(O)+'}');
	else if (typeof O == 'string') string.push('"'+O+'"');
	else if (typeof O == 'number' && isFinite(O)) string.push(O);
	return string.join(',');
}

JSON.evaluate=function(str) {
	return (typeof str=="string")?eval('(' + str + ')'):str;
}

String.format = function(str) {
     var args = Array.prototype.slice.call(arguments, 1);
     return str.replace(/\{(\d+)\}/g,
     function(m, i) {
         return args[i]
     })
}
String.templ = function(str, obj, urlencode) {
	return str.replace(/\{([\w_$]+)\}/g,function(c,$1){
		var a = obj[$1];
		if(a === undefined || a === null){
			return '';
		}
		return urlencode?encodeURIComponent(a) : a;
	});
}

String.prototype.startsWith = String.prototype.startWith = function(str) {
  return this.indexOf(str) == 0;
}

String.prototype.endsWith = String.prototype.endWith = function(str) {
	var i = this.lastIndexOf(str);
  return i>=0 && this.lastIndexOf(str) == this.length-str.length;
}

String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g,"");
}

String.prototype.leftPad = function(c,count){
	if(!isNaN(count)){
		var a = "";
		for(var i=this.length;i<count;i++){
			a = a.concat(c);
		}
		a = a.concat(this);
		return a;
	}
	return null;
}

String.prototype.rightPad = function(c,count){
	if(!isNaN(count)){
		var a = this;
		for(var i=this.length;i<count;i++){
			a = a.concat(c);
		}
		return a;
	}
	return null;
}
Array.prototype.clone = function(){
	var len = this.length;
	var r = [];
	for(var i=0;i<len;i++){
		if(typeof(this[i])=="undefined"||this[i]==null){
			r[i] = this[i];
			continue;
		}
		if(this[i].constructor==Array){
			r[i] = this[i].clone();
		}else{
			r[i] = this[i];
		}
	}
	return r;
}

Array.prototype.insert = function(index,data){
	if(isNaN(index) || index<0 || index>this.length) {
		this.push(data);
	}else{
		var temp = this.slice(index);
		this[index]=data;
		for (var i=0; i<temp.length; i++){
			this[index+1+i]=temp[i];
		}
	}
	return this;
}

Array.prototype.remove = function(s,dust){//如果dust为ture，则返回被删除的元素
	var dustArr;
	for(var i=0;i<this.length;i++){
		if(s == this[i]){
			dustArr=this.splice(i, 1)[0];
		}
	}
	if(dust){
	    return dustArr;
	}else{
		dustArr=null;
		return this;
	}
}

Array.prototype.indexOf = function(func){
	var len = this.length;
	for(var i=0;i<len;i++){
		if (this[i]==arguments[0])
			return i;
	}
	return -1;
}

Array.prototype.each = function(func,scope){
	var len = this.length;
	for(var i=len-1;i>=0;i--){
		try{
			func.call(scope || this[i], this[i], i, this)
		}catch(ex){
			//alert("Array.prototype.each:"+ex.message);
		}
	}
}

var Form = {};
Form.setValue = function(dr,ele){
	ele = $F(ele);
	for(var i=0;i<ele.elements.length;i++){
		var c = $(ele.elements[i]);
		if(c.$A("ztype")=="select"){
			c = c.parentElement;
		}
		if(c.type=="checkbox"||c.type=="radio"){
			if(c.name){
				$NS(c.name,dr.get(c.name));
				continue;
			}
		}
		var id = c.id.toLowerCase();
		if(dr.get(id)){
			$S(c,dr.get(id));
		}
	}
}

Form.getData = function(ele){
	ele = $F(ele)
	if(!ele){
		alert("查找表单元素失败!"+ele);
		return;
	}
	var dc = new DataCollection();
	var arr = ele.elements;
	for(var i=0;i<arr.length;i++){
		var c = $(arr[i]);
		var ID = c.id;
		if(!c.type){
			continue;
		}
		if(c.type=="checkbox"||c.type=="radio"){
			if(c.name){
				dc.add(c.name,$NV(c.name));
				continue;
			}
		}
		if(!ID){
			continue;
		}
		if(c.$A("ztype")=="select"){
			c = c.parentElement;
		}
		dc.add(c.id,$V(c));
	}
	return dc;
}

/*---------------------------Server,Page-------------------------*/
var Server = {};
Server.RequestMap = {};
Server.MainServletURL = "MainServlet.jsp";
Server.ContextPath = CONTEXTPATH;
Server.Pool = [];

Server.getXMLHttpRequest = function(){
	for(var i=0;i<Server.Pool.length;i++){
		if(Server.Pool[i][1]=="0"){
			Server.Pool[i][1] = "1";
			return Server.Pool[i][0];
		}
	}
	var request;
	if (window.XMLHttpRequest){
		request = new XMLHttpRequest();
	}else if(window.ActiveXObject){
		for(var i =5;i>1;i--){
      try{
        if(i==2){
					request = new ActiveXObject( "Microsoft.XMLHTTP" );
        }else{
					request = new ActiveXObject( "Msxml2.XMLHTTP." + i + ".0" );
        }
      }catch(ex){}
    }
	}
	Server.Pool.push([request,"1"]);
	return request;
}

Server.loadURL = function(url,func){
	var Request = Server.getXMLHttpRequest();
	Request.open("GET", Server.ContextPath+url, true);
	Request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	Request.onreadystatechange = function(){
		if (Request.readyState==4&&Request.status==200) {
			try{
				if(func){
					func(Request.responseText);
				};
			}finally{
				for(var i=0;i<Server.Pool.length;i++){
					if(Server.Pool[i][0]==Request){
						Server.Pool[i][1] = "0";
						break;
					}
				}
				Request = null;
				func = null;
			}
		}
	}
	Request.send(null);
}

Server.importScript = function(url){
	if(!document.body){
		document.write('<script type="text/javascript" src="' + Server.ContextPath+url + '"><\/script>') ;
	}else{
		Server.loadScript(Server.ContextPath+url);
	}
}
Server.importCSS = function(url){
	if(!document.body){
		document.write('<link rel="stylesheet" type="text/css" href="' + Server.ContextPath+url + '" />') ;
	}else{
		Server.loadCSS(Server.ContextPath+url);
	}
}
Server.loadScript = function(url){//在页面载入后添加script
	var e = document.createElement('SCRIPT') ;
	e.type	= 'text/javascript' ;
	e.src	= url ;
	e.defer	= true ;
	document.getElementsByTagName("HEAD")[0].appendChild(e);
}
Server.loadCSS = function(url){//在页面载入后添加script
	if(isGecko){
		var e = document.createElement('LINK') ;
		e.rel	= 'stylesheet' ;
		e.type	= 'text/css' ;
		e.href	= url ;
		document.getElementsByTagName("HEAD")[0].appendChild(e) ;
	}else{
		document.createStyleSheet(url);
	}
}

Server.getOneValue = function(methodName,dc,func){//dc既可是一个DataCollection，也可以是一个单值
	if(dc&&dc.prototype==DataCollection.prototype){
		Server.sendRequest(methodName,dc,func);
	}else{
		var dc1 = new DataCollection();
		dc1.add("_Param0",dc);
		Server.sendRequest(methodName,dc1,func);
	}
}

Server.sendRequest = function(methodName,dataCollection,func,id,waitMsg){//参数id用来限定id相同的请求同一时间只能有一个
	if(!Server.executeRegisteredEvent(methodName,dataCollection)){
		Console.log(methodName+"的调用被注册事件阻止!");
		return;
	}
	var Request;
	if(id!=null && Server.RequestMap[id]){
		Request = Server.RequestMap[id];
		Request.abort();
	}else{
		Request = Server.getXMLHttpRequest();
	}
	Request.open("POST", Server.ContextPath+Server.MainServletURL, true);
	Request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	var url = "_ZVING_METHOD="+methodName+"&_ZVING_DATA=";
	if(dataCollection){
		url += encodeURL(htmlEncode(dataCollection.toXML()));
	}
	url += "&_ZVING_URL="+encodeURL(window.location.pathname);
	Server._ResponseDC = null;
	Request.onreadystatechange = function(){Server.onRequestComplete(Request,func);};
	Request.send(url);
}

Server.onRequestComplete = function(Request,func){
	if (Request.readyState==4&&Request.status==200) {
		try{
			var xmlDoc = Request.responseXML;
			var dc = new DataCollection();
			if(xmlDoc){
				if(dc.parseXML(xmlDoc)){
					dc["Status"] = dc.get("_ZVING_STATUS");
					dc["Message"] = dc.get("_ZVING_MESSAGE");
					if(dc.get("_ZVING_SCRIPT")){
						eval(dc.get("_ZVING_SCRIPT"));
					}
				}
				Server._ResponseDC = dc;
				xmlDoc = null;
			}else{
					dc["Status"] = 0;
					dc["Message"] = "服务器发生异常,未获取到数据!";
			}
			if(func){
				try{
					func(dc);
				}catch(ex){
					alert("Server.onRequestComplete:"+ex.message+"\t"+ex.lineNumber);
				}
			}
		}finally{
			for(var i=0;i<Server.Pool.length;i++){
				if(Server.Pool[i][0]==Request){
					Server.Pool[i][1] = "0";
					break;
				}
			}
			Request = null;
			func = null;
		}
	}
}

Server.getResponse = function(){
	return Server._ResponseDC;
}

Server.Events = {};
Server.registerEvent = function(methodName,func){//当调用method时执行相应的函数，当该函数值为false退出调用，不向后台发送指令
	var arr = Server.Events[methodName];
	if(!arr){
		arr = [];
	}
	arr.push(func);
}

Server.executeRegisteredEvent = function(methodName,dc){
	var arr = Server.Events[methodName];
	if(!arr){
		return true;
	}
	for(var i=0;i<arr.length;i++){
		if(!arr[i].apply(null,[dc,methodName])){
			return false;
		}
	}
	return true;
}

var Page = {};

Page.wait = function(){//通过在当前页面加入透明层的方式阻止页面继续响应事件，主要用于防止用户两次点击按钮
	var bgdiv = $("_WaitBGDiv");
	if(!bgdiv){
		var bgdiv = document.createElement("div");
		bgdiv.id = "_WaitBGDiv";
		$E.hide(bgdiv);
	 	$T("body")[0].appendChild(bgdiv);
		bgdiv.style.cssText = "background-color:#333;position:absolute;left:0px;top:0px;opacity:0.03;filter:alpha(opacity=3);width:100%;height:100%;z-index:991";
	}
	var mh = Math.max(document.documentElement.scrollHeight, document.body.scrollHeight);
	bgdiv.style.height = mh+"px";
	$E.show(bgdiv);
}

Page.endWait = function(){
	var bgdiv = $("_WaitBGDiv");
	if(bgdiv){
		$E.hide(bgdiv);
	}
}

Page.clickFunctions = [];
Page.click = function(event){
	for(var i=0;i<Page.clickFunctions.length;i++){
		Page.clickFunctions[i](event);
	}
	if(window!=window.parent&&window.parent.Page){
		window.parent.Page.click();
	}
}
Page.onClick = function(f){
	Page.clickFunctions.push(f);
}

Page._Sort = function(a1,a2){
	var i1 = a1[1];
	var i2 = a2[1];
	if(typeof(i1)=="number"){
		if(typeof(i2)=="number"){
			if(i1>i2){
				return 1;
			}else if(i1==i2){
				return 0;
			}else{
				return -1;
			}
		}
		return -1;
	}else{
		if(typeof(i2)=="number"){
			return 1;
		}else{
			return 0;
		}
	}
}
Page.loadComplete = false;
Page.loadFunctions = [];
Page.load = function(){
	Page.loadComplete = true;//在窗口内容载入完成后给window设置一个标记loadComplete
	if(window._OnLoad){//Select控件会用到
		try{window._OnLoad();}catch(ex){alert(ex.message)}
	}
	if(window.frameElement&&window.frameElement._OnLoad){//Select控件会用到
		try{window.frameElement._OnLoad();}catch(ex){alert(ex.message)}
	}
	Page.loadFunctions.sort(Page._Sort);
	for(var i=0;i<Page.loadFunctions.length;i++){
		try{Page.loadFunctions[i][0]();}catch(ex){}
	}
}

Page.onLoad = function(f,index){
	Page.loadFunctions.push([f,index]);
}

Page.readyFunctions = [];
Page.ready = function(){
	if(window._OnReady){
		try{window._OnReady();}catch(ex){}
	}
	Page.readyFunctions.sort(Page._Sort);
	for(var i=0;i<Page.readyFunctions.length;i++){
		try{Page.readyFunctions[i][0]();}catch(ex){}
	}
};

Page.onReady= function(f,index){
	Page.readyFunctions.push([f,index]);
};

Page.mouseDownFunctions = [];
Page.mousedown = function(event){
	for(var i=0;i<Page.mouseDownFunctions.length;i++){
		Page.mouseDownFunctions[i](event);
	}
}

Page.onMouseDown = function(f){
	Page.mouseDownFunctions.push(f);
}

Page.mouseUpFunctions = [];
Page.mouseup = function(event){
	for(var i=0;i<Page.mouseUpFunctions.length;i++){
		Page.mouseUpFunctions[i](event);
	}
}

Page.onMouseUp = function(f){
	Page.mouseUpFunctions.push(f);
}

Page.mouseMoveFunctions = [];
Page.mousemove = function(event){
	for(var i=0;i<Page.mouseMoveFunctions.length;i++){
		Page.mouseMoveFunctions[i](event);
	}
}

Page.onMouseMove = function(f){
	Page.mouseMoveFunctions.push(f);
}

if(document.attachEvent){
	window.attachEvent('onload',Page.load);
	document.attachEvent('onclick',Page.click);
	document.attachEvent('onmousedown',Page.mousedown);
	document.attachEvent('onmouseup',Page.mouseup);
	document.attachEvent('onmousemove',Page.mousemove);
}else{
	alert("Main.js# document.attachEvent方法未实现")
}

Page.isReady=false;
Page.bindReady = function(evt){
	if(Page.isReady) return;
	Page.isReady=true;
	Page.ready.call(window);
	if(document.removeEventListener){
		document.removeEventListener("DOMContentLoaded", Page.bindReady, false);
	}else if(document.attachEvent){
		document.detachEvent("onreadystatechange", Page.bindReady);
		if(window == window.top){
			clearInterval(Page._Timer);
			Page._Timer = null;
		}
	}
};
if(document.addEventListener){
	document.addEventListener("DOMContentLoaded", Page.bindReady, false);
}else if(document.attachEvent){
	document.attachEvent("onreadystatechange", function(){
		if((/loaded|complete/).test(document.readyState))
			Page.bindReady();
	});
	if(window == window.top){
		Page._Timer = setInterval(function(){
			try{
				Page.isReady||document.documentElement.doScroll('left');//在IE下用能否执行doScroll判断dom是否加载完毕
			}catch(e){
				return;
			}
			Page.bindReady();
		},5);
	}
}
Page.onLoad(Page.bindReady,0);//保证onReady一定能够执行

function DataTable(){
	this.Columns = null;
	this.Values = null;
	this.Rows = null;
	this.ColMap = {};

	DataTable.prototype.getRowCount = function(){
		return this.Rows.length;
	}

	DataTable.prototype.getColCount = function(){
		return this.Columns.length;
	}

	DataTable.prototype.getColName = function(i){
		return this.Columns[i];
	}

	DataTable.prototype.get2 = function(i,j){
		return this.Rows[i].get2(j);
	}

	DataTable.prototype.get = function(i,str){
		return this.Rows[i].get(str);
	}

	DataTable.prototype.getDataRow = function(i){
		return this.Rows[i];
	}

	DataTable.prototype.deleteRow = function(i){
		this.Values.splice(i,1);
		this.Rows.splice(i,1);
		for(var k=i;k<this.Rows.length;k++){
			this.Rows[k].Index = k;
		}
	}

	DataTable.prototype.insertRow = function(i,values){
		this.Values.splice(i,0,values);
		this.Rows.splice(i,0,new DataRow(this,i));
		for(var k=i;k<this.Rows.length;k++){
			this.Rows[k].Index = k;
		}
	}

	DataTable.prototype.insertColumn = function(name){
		if(this.hasColumn(name)){
			return;
		}
		var col = {};
		col.Name = name.toLowerCase();
		col.Type = 1;//string
		this.Columns.push(col);
		this.ColMap[col.Name] = this.Columns.length-1;
		for(var i=0;i<this.Values.length;i++){
			this.Values[i].push(null);//置空值
		}
	}

	DataTable.prototype.hasColumn = function(name){
		name = name.toLowerCase();
		for(var j=0;j<this.Columns.length;j++){
			if(this.Columns[j].Name==name){
				return true;
			}
		}
		return false;
	}

	DataTable.prototype.init = function(cols,values){
		this.Values = values;
		this.Columns = [];
		this.Rows = [];
		for(var i=0;i<cols.length;i++){
			var col = {};
			col.Name = cols[i][0].toLowerCase();
			col.Type = cols[i][1];
			this.Columns[i] = col;
			this.ColMap[col.Name] = i;
		}
		for(var i=0;i<values.length;i++){
			var row = new DataRow(this,i);
			this.Rows[i] = row;
		}
	}

	DataTable.prototype.toString = function(){
		var arr = [];
		arr.push("<columns><![CDATA[[");
		for(var i=0;i<this.Columns.length;i++){
			if(i!=0){
				arr.push(",");
			}
			arr.push("[");
			arr.push("\""+this.Columns[i].Name+"\",")
			arr.push(this.Columns[i].Type)
			arr.push("]");
		}
		arr.push("]]]></columns>");
		arr.push("<values><![CDATA[[");
		for(var i=0;i<this.Values.length;i++){
			if(i!=0){
				arr.push(",");
			}
			arr.push("[");
			for(var j=0;j<this.Columns.length;j++){
				if(j!=0){
					arr.push(",");
				}
				if(this.Values[i][j]==null||typeof(this.Values[i][j])=="undefined"){
					arr.push("\"_ZVING_NULL\"");
				}else{
					var v = this.Values[i][j];
					if(typeof(v)=="string"){
						v = javaEncode(v);
					}
					arr.push("\""+v+"\"");
				}
			}
			arr.push("]");
		}
		arr.push("]]]></values>");
		return arr.join('');
	}
}

function DataRow(dt,index){
	this.DT = dt;
	this.Index = index;

	DataRow.prototype.get2 = function(i){
		return this.DT.Values[this.Index][i];
	}

	DataRow.prototype.getColCount = function(){
		return this.DT.Columns.length;
	}

	DataRow.prototype.getColName = function(i){
		return this.DT.Columns[i];
	}

	DataRow.prototype.get = function(str){
		str = str.toLowerCase();
		var c = this.DT.ColMap[str];
		if(typeof(c)=="undefined"){
			return null;
		}
		return this.DT.Values[this.Index][c];
	}

	DataRow.prototype.set = function(str,value){
		str = str.toLowerCase();
		var c = this.DT.ColMap[str];
		if(typeof(c)=="undefined"){
			return;
		}
		this.DT.Values[this.Index][c] = value;
	}

	DataRow.prototype.set2 = function(i,value){
		this.DT.Values[this.Index][i] = value;
	}
}

function DataCollection(){
	this.map = {};
	this.valuetype = {};
	this.keys = [];

	DataCollection.prototype.get = function(ID){
		if(typeof(ID)=="number"){
			return this.map[this.keys[ID]];
		}
		return this.map[ID];
	}

	DataCollection.prototype.getKey = function(index){
		return this.keys[index];
	}

	DataCollection.prototype.size = function(){
		return this.keys.length;
	}

	DataCollection.prototype.remove = function(ID){
		if(typeof(ID)=="number"){
			if(ID<this.keys.length){
				var obj = this.map[this.keys[ID]];
				this.map[this.keys[ID]] = null;
				this.keys.splice(ID);
				return obj;
			}
		}else{
			for(var i=0;i<this.keys.length;i++){
				if(this.keys[i]==ID){
					var obj = this.map[ID];
					this.map[ID] = null;
					this.keys.splice(i);
					return obj;
					break;
				}
			}
		}
		return null;
	}

	DataCollection.prototype.toQueryString = function(){
		var arr = [];
		for(var i=0;i<this.keys.length;i++){
			if(this.map[this.keys[i]]==null||this.map[this.keys[i]]==""){continue;}
			if(i!=0){
				arr.push("&");
			}
			arr.push(this.keys[i]+"="+this.map[this.keys[i]]);
		}
		return arr.join('');
	}

	DataCollection.prototype.parseXML = function(xmlDoc){
		var coll = xmlDoc.documentElement;
		if(!coll){
			return false;
		}
		var nodes = coll.childNodes;
		var len = nodes.length;
		for(var i=0;i<len;i++){
			var node = nodes[i];
			var Type = node.getAttribute("Type");
			var ID = node.getAttribute("ID");
			this.valuetype[ID] = Type;
			if(Type=="String"){
				var v = node.firstChild.nodeValue;
				if(v==Constant.Null){
					v = null;
				}
				this.map[ID] = v;
			}else if(Type=="StringArray"){
				this.map[ID] = eval("["+node.firstChild.nodeValue+"]");
			}else if(Type=="Map"){
				this.map[ID] = eval("("+node.firstChild.nodeValue+")");
			}else if(Type=="DataTable"||Type=="Schema"||Type=="SchemaSet"){
				this.parseDataTable(node,"DataTable");
			}else{
				this.map[ID] = node.getAttribute("Value");
			}
			this.keys.push(ID);
		}
		return true;
	}

	DataCollection.prototype.parseDataTable = function(node,strType){
		var cols = node.childNodes[0].childNodes[0].nodeValue;
		cols = "var _TMP1 = "+cols+"";
		eval(cols);
		cols = _TMP1;
		var values = node.childNodes[1].childNodes[0].nodeValue;
		values = "var _TMP2 = "+values+"";
		eval(values);
		values = _TMP2;
		var obj;
		obj = new DataTable();
		obj.init(cols,values);
		this.add(node.getAttribute("ID"),obj);
	}

	DataCollection.prototype.toXML = function(){
		var arr = [];
		arr.push("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		arr.push("<collection>");
		for(var i=0;i<this.keys.length;i++){
			var ID = this.keys[i];
			try{
				var v = this.map[ID];
				arr.push("<element ID=\""+ID+"\" Type=\""+this.valuetype[ID]+"\">");
				if(this.valuetype[ID]=="DataTable"){
					arr.push(v.toString());
				}else if(this.valuetype[ID]=="String"){
					if(v==null||typeof(v)=="undefined"){
						arr.push("<![CDATA["+Constant.Null+"]]>");
					}else{
						arr.push("<![CDATA["+v+"]]>");
					}
				}else if(this.valuetype[ID]=="Map"){
					if(v==null||typeof(v)=="undefined"){
						arr.push("<![CDATA["+Constant.Null+"]]>");
					}else{
						arr.push("<![CDATA["+JSON.toString(v)+"]]>");
					}
				}else if(this.valuetype[ID]=="StringArray"){
					if(v==null||typeof(v)=="undefined"){
						arr.push("<![CDATA["+Constant.Null+"]]>");
					}else{
						var r = [];
						for(var j=0;j<v.length;j++){
							r.push(javaEncode(v[j]));
						}
						arr.push("<![CDATA["+r.join("\",\"")+"]]>");
					}
				}else{
					arr.push(v);
				}
				arr.push("</element>");
			}catch(ex){alert("DataCollection.toXML():"+ID+","+ex.message);}
		}
		arr.push("</collection>");
		return arr.join('');
	}

	DataCollection.prototype.add = function(ID,Value,Type){
		this.map[ID] = Value;
		this.keys.push(ID);
		if(Type){
			this.valuetype[ID] = Type;
		}else	if( Value && Value.getDataRow){//DataTable可能不是本页面中的
			this.valuetype[ID] = "DataTable";
		}else{
			this.valuetype[ID] = "String";
		}
	}

	DataCollection.prototype.addAll = function(dc){
		if(!dc){
			return;
		}
		if(!dc.valuetype){
			alert("DataCollection.addAll()要求参数必须是一个DataCollection!");
		}
		var size = dc.size();
		for(var i=0;i<size;i++){
			var k = dc.keys[i];
			var v = dc.map[k];
			var t = dc.valuetype[k];
			this.add(k,v,t);
		}
	}
}

var Cookie = {};//Cookie操作类，支持大于4K的Cookie

Cookie.Spliter = "_ZVING_SPLITER_";

Cookie.get = function(name){
  var cs = document.cookie.split("; ");
  for(i=0; i<cs.length; i++){
	  var arr = cs[i].split("=");
	  var n = arr[0].trim();
	  var v = arr[1]?arr[1].trim():"";
	  if(n==name){
	  	return decodeURI(v);
	  }
	}
	return null;
}

Cookie.getAll = function(){
  var cs = document.cookie.split("; ");
  var r = [];
  for(i=0; i<cs.length; i++){
	  var arr = cs[i].split("=");
	  var n = arr[0].trim();
	  var v = arr[1]?arr[1].trim():"";
	  if(n.indexOf(Cookie.Spliter)>=0){
	  	continue;
	  }
	  if(v.indexOf("^"+Cookie.Spliter)==0){
	      var max = v.substring(Cookie.Spliter.length+1,v.indexOf("$"));
	      var vs = [v];
	      for(var j=1;j<max;j++){
	      	vs.push(Cookie.get(n+Cookie.Spliter+j));
	      }
	      v = vs.join('');
	      v = v.substring(v.indexOf("$")+1);
	   }
	   r.push([n,decodeURI(v)]);
	}
	return r;
}

Cookie.set = function(name, value, expires, path, domain, secure, isPart){
	if(!isPart){
		var value = encodeURI(value);
	}
	if(!name || !value){
		return false;
	}
	if(!path){
		path = Server.ContextPath;//特别注意，此处是为了实现不管当前页面在哪个路径下，Cookie中同名名值对只有一份
	}
	path = path.replace(/^\w+:\/\/[.\w]+:?\d*/g, '');//特别注意，此处是为了实现不管当前页面在哪个路径下，Cookie中同名名值对只有一份
	if(expires!=null){
	  if(/^[0-9]+$/.test(expires)){
	    expires = new Date(new Date().getTime()+expires*1000).toGMTString();
		}else{
			var date = DateTime.parseDate(expires);
			if(date){
				expires = date.toGMTString();
			}else{
		  	expires = undefined;
		  }
		}
	}
	if(!isPart){
	  Cookie.remove(name, path, domain);
	}
	var cv = name+"="+value+";"
		+ ((expires) ? " expires="+expires+";" : "")
		+ ((path) ? "path="+path+";" : "")
		+ ((domain) ? "domain="+domain+";" : "")
		+ ((secure && secure != 0) ? "secure" : "");
  if(cv.length < 4096){
		document.cookie = cv;
	}else{
		var max = Math.ceil(value.length*1.0/3800);
		for(var i=0; i<max; i++){
			if(i==0){
				Cookie.set(name, '^'+Cookie.Spliter+'|'+max+'$'+value.substr(0,3800), expires, path, domain, secure, true);
			}else{
				Cookie.set(name+Cookie.Spliter+i, value.substr(i*3800,3800), expires, path, domain, secure, true);
			}
		}
	}
  return true;
}

Cookie.remove = function(name, path, domain){
	var v = Cookie.get(name);
  if(!name||v==null){
  	return false;
  }
  if(encodeURI(v).length > 3800){
		var max = Math.ceil(encodeURI(v).length*1.0/3800);
		for(i=1; i<max; i++){
			document.cookie = name+Cookie.Spliter+i+"=;"
				+ ((path)?"path="+path+";":"")
				+ ((domain)?"domain="+domain+";":"")
				+ "expires=Thu, 01-Jan-1970 00:00:01 GMT;";
		}
	}
	document.cookie = name+"=;"
		+ ((path)?"path="+path+";":"")
		+ ((domain)?"domain="+domain+";":"")
		+ "expires=Thu, 01-Jan-1970 00:00:01 GMT;";
	return true;
}

var Console = {};
Console.info = [];

Console.log = function(str){
	Console.info.push(str);
	if(Console.info.length>1200){
		Console.info.splice(1000,Console.info.length-1000);
	}
	if(Console.isShowing){
		//立即显示信息
	}
}

Console.show = function(){
	var html = [];
	html.push("<textarea class='input_textarea' style='width:600px;height:200px'>");
	for(var i=0;i<Console.info.length;i++){
		html.push(htmlEncode(Console.info[i]));
		html.push("<br>");
	}
	html.push("</textarea>");
	Dialog.alert(html.join('\n'),700,250);
}

var Misc = {};
Misc.setButtonText = function(ele,text){//为z:button设置文本
	$(ele).childNodes[1].innerHTML = text+"&nbsp;";
}

Misc.withinElement = function(event, ele) {//仅适用于Gecko,判断onmouseover,onmouseout是否是一次元素内部重复触发
	return false;
	var parent = event.relatedTarget;
	while(parent&&parent!=ele&&parent!=document.body){
		try{
			parent = parent.parentNode;
		}catch(ex){
			alert("Misc.withinElement:"+ex.message);
			return false;
		}
	}
	return parent == ele;
}

Misc.copyToClipboard = function(text){
	if(text==null){
		return;
	}
	if (window.clipboardData){
		window.clipboardData.setData("Text", text);
	}else if (window.netscape){
		try{
			netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
		}catch(ex){
			Dialog.alert("Firefox自动复制功能未启用！<br>请<a href='about:config' target='_blank'>点击此处</a> 将’signed.applets.codebase_principal_support’设置为’true’");
		}
		var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
		if(!clip){return;}
		var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
		if(!trans){return;}
		trans.addDataFlavor('text/unicode');
		var str = new Object();
		var len = new Object();
		var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
		var copytext=text;
		str.data=copytext;
		trans.setTransferData("text/unicode",str,copytext.length*2);
		var clipid=Components.interfaces.nsIClipboard;
		if(!clip){return;}
		clip.setData(trans,null,clipid.kGlobalClipboard);
	}else{
		alert("该浏览器不支持自动复制功能！");
		return;
	}
	Dialog.alert("己复制文字：<br><br><font color='red'>"+text+"</font><br><br>到剪贴板",null,400,200);
}

Misc.lockSelect = function(ele){
	if(!ele){
		ele = document.body;
	}
	if(isGecko){
		ele.style.MozUserSelect = "none";
		ele.style.MozUserInput = "none";
	}else{
		document.selection.empty();
		ele.onselectstart = stopEvent;
	}
}

Misc.unlockSelect = function(ele){
	if(!ele){
		ele = document.body;
	}
	if(isGecko){
		ele.style.MozUserSelect = "";
		ele.style.MozUserInput = "";
	}else{
		ele.onselectstart = null;
	}
}

Misc.lockScroll = function(win){
	if(!win){
		win = window;
	}
	if(isIE){
		win.document.body.attachEvent("onmousewheel",win.stopEvent);
	}else{
		win.addEventListener("DOMMouseScroll", win.stopEvent, false);
	}
}

Misc.unlockScroll = function(win){
	if(!win){
		win = window;
	}
	if(isIE){
		win.document.body.detachEvent("onmousewheel",win.stopEvent);
		win.document.body.detachEvent("onmousewheel",win.stopEvent);//Select.js中可能会连续attch两次,所以必须detach两次
	}else{
		win.removeEventListener("DOMMouseScroll", win.stopEvent, false);
	}
}

Afloat=function(id,pos){//漂浮元素使之固定在窗口底部或项部，pos值为字符串'top'或'bottom'，默认为'bottom'
	this.pos=pos||'bottom';
	this.dom=$(id);
	if(this.dom.tagName=='TD'){
		var wrap=document.createElement('div');
		var children=this.dom.children;
		children=toArray(children);
		children.each(function(ele){wrap.appendChild(ele);})
		this.dom.appendChild(wrap);
		this.dom=$(wrap);
	}
	this.init();
	var me=this;
	window.attachEvent("onscroll",function(){me.setPosition()})
	//window.attachEvent("onresize",function(){me.setPosition()})
}
Afloat.prototype={
	init:function(){
		this.dom.style.position="static";
		var domPosition=this.dom.getPosition();
		this.fixX=Math.round(domPosition.x);
		this.fixY=Math.round(domPosition.y);//当目标元素处于隐藏状态的该值可能为0
		this.fixWidth=this.dom.offsetWidth;//当目标元素处于隐藏状态的该值可能为0
		this._width=this.dom.style.width;
	    this.fixHeight=this.dom.offsetHeight;
		this.dom.style.left=this.fixX+'px';
	    this.viewportH=$E.getViewport().height;
	},
	setPosition:function(){
		 if(this.fixY==0||this.fixWidth==0){
			this.init();
		 }
		 var st = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
		 if((this.pos=='bottom'&&st+this.viewportH<this.fixY)
			||(this.pos=='top'&&st>this.fixY)
		 ){
			 if(isIE6){
				 this.dom.style.position="absolute";
				 this.dom.style.top = this.pos=='bottom'?
					st+this.viewportH-this.fixHeight+'px' : st+'px';
			 }else{
				 this.dom.style.position="fixed";
				 this.pos=='bottom'?this.dom.style.bottom='0':this.dom.style.top='0';
			 }
			this.dom.style.width=this.fixWidth+'px';
		 }else{
			this.dom.style.position="static";
			this.dom.style.width=this._width;
		 }
	 }
}
/*START_LOADSCRIPT
if(!window.DisableScriptAutoLoad){
	Server.importScript("Framework/Element.js");
	Server.importScript("Framework/Drag.js");
	Server.importScript("Framework/Controls/Tabpage.js");
	Server.importScript("Framework/Controls/Select.js");
	Server.importScript("Framework/Controls/Dialog.js");
	Server.importScript("Framework/Controls/MessagePop.js");
	Server.importScript("Framework/Controls/DataGrid.js");
	Server.importScript("Framework/Controls/DataList.js");
	Server.importScript("Framework/Controls/Menu.js");
	Server.importScript("Framework/Controls/DateTime.js");
	Server.importScript("Framework/Controls/Tree.js");
	Server.importScript("Framework/Controls/Tip.js");
	Server.importScript("Framework/Controls/Progress.js");
	Server.importScript("Framework/Application.js");
	Server.importScript("Framework/Verify.js");
	Server.importScript("Framework/Style.js");
	Server.importScript("Framework/Resize.js");
	Server.importScript("Framework/Console.js");
	
	Server.importScript("Framework/Util.js");
	Server.importScript("Framework/EventManager.js");
	Server.importScript("Framework/Observable.js");
	Server.importScript("Framework/Layout.js");
	Server.importScript("Framework/Controls/SplitBar.js");
	Server.importScript("Framework/Controls/Toolbar.js");
	Server.importScript("Framework/Controls/Button.js");
	Server.importScript("Framework/Controls/Layer.js");
	Server.importScript("Framework/Controls/DropMenu.js");
}
END_LOADSCRIPT*/

