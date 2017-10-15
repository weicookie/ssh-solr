<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/taglibs.jsp" %>
<jsp:useBean id="now" class="java.util.Date"/>
<html>
  <head>
    <title>welcome</title>
    <script type="text/javascript" src="${webRoot}/template/statics/js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${webRoot}/template/statics/js/index.js"></script>
  </head>
  <body>
    hello world
    <a href="javascript:void(0);" id="requestAdmin">请求后台</a>
    <form action="${webRoot}/user/findUserByUserId.ac">
      <input type="submit" value="提交"/>
    </form>

  </body>
</html>
