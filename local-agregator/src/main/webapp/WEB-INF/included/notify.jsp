<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp"%>
<%--
 =======================================
      全局提示信息,使用jquery pnotify插件
 =======================================
--%>
 <script>
     $(function(){
        /**全局提示信息,使用jquery pnotify插件**/
        if('${$message}' && '${$title}'){
           lework.notify('${$title}' ,'${$message}' , '${$type}');
        }
     });
 </script>