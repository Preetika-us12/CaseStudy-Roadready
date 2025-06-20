<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h2>User Login</h2>
<form action="/login-process" method="post">
    Username: <input type="text" name="username" required><br><br>
    Password: <input type="password" name="password" required><br><br>
    <input type="submit" value="Sign In">
</form>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>
</body>
</html>
