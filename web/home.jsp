<%-- 
    Document   : index
    Created on : Jul 6, 2017, 4:24:31 PM
    Author     : ntdung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
        
        <script src="${pageContext.request.contextPath}/resources/js/jquery-2.2.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery-2.2.3.min.js"></script>
    </head>
    <body>
        <a href="#luuphuluc" onclick="return changeContent();">Lưu phụ lục</a>
        <div id="content"></div>
        <script>
            function changeContent() {
                    $('#content').load('luuphuluc.jsp');
            }
        </script>
    </body>
</html>
