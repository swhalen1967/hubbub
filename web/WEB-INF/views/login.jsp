<!DOCTYPE html>
<html>
    <head>
        <title>Hubbub &raquo; Login</title>
        <link rel="stylesheet" type="text/css" href="styles/main.css"/>
    </head>
    <body>
        <div class="masthead"><img src="images/hubbub.png"/></div>
        <h1>Log in to access your Hubbub&trade; account.</h1>
        <c:if test="${not empty errors}">
            <ul>
                <c:forEach var="error" items="${errors}">
                    <li class="flash">${error}</li>
                </c:forEach>
            </ul>
        </c:if>
        <form method="POST" action="go">
            <input type="hidden" name="action" value="login"/>
            <table>
                <tr><td>User Name:</td>
                    <td><input id="firstField" type="text" name="userName" required/></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="password" name="password" required></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="I need to get bloggin'!"/>
                    </td>
                </tr>
            </table>
        </form>
        <div class="nav">
            <a href="go?action=register">I need to sign up for this awesomeness</a>
            <a href="go?action=timeline">Just show me the timeline</a>
        </div>
        <script type="text/javascript">
            document.getElementById("firstField").focus();
        </script>
    </body>
</html>
