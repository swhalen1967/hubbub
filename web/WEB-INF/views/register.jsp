<!DOCTYPE html>
<html>
    <head>
        <title>Hubbub &raquo; Registration</title>
        <link rel="stylesheet" type="text/css" href="styles/main.css"/>
    </head>
    <body>
        <div class="masthead"><img src="images/hubbub.png"/></div>
        <h1>Sign up, then log-in to get bloggin'!</h1>
        <c:if test="${not empty flash}">
            <h2 class="flash">${flash}</h2>
            <ul>
                <c:forEach var="error" items="${errors}">
                    <li class="flash">${error}</li>
                    </c:forEach>
            </ul>
        </c:if>
        <form method="POST" action="go">
            <input type="hidden" name="action" value="register"/>
            <table>
                <tr>
                    <td>Pick a user name:</td>
                    <td><input id="firstField" type="text" name="user"
                               placeholder="6 to 12 letters, numbers, and/or underscores"
                               required/>
                </tr>
                <tr>
                    <td>Pick a password:</td>
                    <td><input type="password" name="pass1" 
                               placeholder="8 to 16 letters, numbers, underscores, dots, and/or hyphens"
                               required />
                </tr>
                <tr>
                    <td>Repeat the password:</td>
                    <td><input type="password" name ="pass2"
                               placeholder="same as preceding password field"
                               required/></td>
                </tr>
                <tr><td colspan="2"><input class="submit" type="submit" value="Sign me up!"/></td>
            </table>
        </form>
        <div class="nav">
            <a href="go?action=timeline">Just take me to the timeline right now</a>
            <a href="go?action=login">I think I already have a user name for Hubbub&trade;</a>
        </div>
        <script type="text/javascript">
            document.getElementById("firstField").focus();
        </script>
    </body>
</html>
