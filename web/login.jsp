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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/index.css">
        <script src="${pageContext.request.contextPath}/resources/js/jquery-2.2.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery-2.2.3.min.js"></script>
    </head>
    <body>
        <div class = "container">
            <div class="wrapper">
                        <h3 class="form-signin-heading">Đăng nhập</h3>
                              <hr class="colorgraph"><br>

                              Tên đăng nhập: <input type="text" class="form-control" name="Username" placeholder="Username" required="" autofocus="" />
                              <input type="password" class="form-control" name="Password" placeholder="Password" required=""/>     		  

                              <button class="btn btn-lg btn-primary btn-block"  name="Submit" value="Login" type="Submit">Login</button>  					
            </div>
        </div>
    </body>
</html>
