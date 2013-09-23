<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ include file="/commons/meta.jsp" %>
 </head>
 <body>
     <form id="form1" runat="server">
     <div id="id1">
     <script type="text/javascript">
     Ext.BLANK_IMAGE_URL="MyImage/dlg-bg.gif";
     function Read1() {
         Ext.Msg.wait("内容","Extjs进度条应用",{text:"正在加载。。。"});
     }
     function Read2() {
         Ext.Msg.show({
         modal:false,
         title:"标题",
         msg:"内容",
         closable:true,
         width:300,
         wait:true
         })
     }
     function Read3() {
         Ext.Msg.show({
         title:"标题",
         msg:"内容",
         modal:true,
         closable:true,
         width:300,
         progress:true,
         progressText:"保存进度"
         })
     }
     function Read4() {
         var progressBar=Ext.Msg.show({
         title:"标题",
         msg:"通过进度的大小来控制进度",
         progress:true,
         width:300
         });
         var count=0;
         var bartext="";
         var curnum=0;
         Ext.TaskMgr.start({
             run:function () {
                 count++;
                 if (count>=10) {
                     progressBar.hide();
                 }
                 curnum=count/10;
                 bartext=curnum*100+"%";
                 progressBar.updateProgress(curnum,bartext);
             },
             interval:1000
         })
     }
     function Read5() {
         var progressBar=Ext.Msg.show({
             title:"标题",
             msg:"通过固定时间完成进度",
             width:300,
             wait:true,
             waitConfig:{interval:500,duration:5000,fn:function () {
                 Ext.Msg.hide();
             }},
             closable:true
         });
 //        setTimeout(function () {
 //            Ext.Msg.hide();
 //        },5000);
     }
     
     function Read7() {
         var msgbox=Ext.Msg.show({
             title:"进度条应用",
             msg:"提示内容",
             closable:true,
             width:300,
             modal:true,
             progress:true
         });
         var count=0;
         var curnum=0;
         var msgtext="";
         Ext.TaskMgr.start({
             run:function () {
                 count++;
                 if (count>10) {
                     msgbox.hide();
                 }
                 curnum=count/10;
                 msgtext="当前加载:"+curnum*100+"%";
                 msgbox.updateProgress(curnum,msgtext,'当前时间:'+new Date().format('Y-m-d g:i:s A'));
             },
             interval:1000
             
         })
         
     }
     function Read8() {
         var progressBar=new Ext.ProgressBar({
             text:'working......',
             width:300,
             applyTo:id2
         });
         var count=0;
         var curnum=0;
         var msgtext="";
         Ext.TaskMgr.start({
             run:function () {
                 count++;
                 if (count>10) {
                     progressBar.hide();
                 }
                 curnum=count/10;
                 msgtext=curnum*100+"%";
                 progressBar.updateProgress(curnum,msgtext);
             },
             interval:1000
         })  
     }
     function Read9() {
         //自动模式进度条
         var progressBar=new Ext.ProgressBar({
             text:'waiting......',
             width:300,
             applyTo:id2
         });
         progressBar.wait({
             interval:1000,
             duration:10000,
             increment:10,
             scope:this,
             fn:function () {
                 alert("更新完毕");
             }
         });
     }
     function Read6() {
         //字定义漂亮进度条
         
     }
     Ext.onReady(Read4);
     </script>
     </div>
     <div id="id2">
     </div>
     
     </form>
 </body>
 </html>
 
