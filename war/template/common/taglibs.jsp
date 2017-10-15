<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
    response.setHeader("cache-control","max-age=5,public,must-revalidate"); //one day
    response.setDateHeader("expires", -1);
%>
<c:set value="${pageContext.request.contextPath}" var="webRoot" />