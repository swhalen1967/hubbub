<!DOCTYPE html>
<html>
    <head>
        <title>Hubbub &raquo; Login</title>
        <link rel="stylesheet" type="text/css" href="styles/main.css"/>
    </head>
    <body>
        <div class="masthead"><img src="images/hubbub.png"/></div>
        <h1>Log in to access your Hubbub&trade; account.</h1>
        <c:if test="${not empty flash}"><h2 class="flash">${flash}</h2></c:if>
        <form method="POST" action="go">
            <input type="hidden" name="action" value="login"/>
            User Name: <input type="text" name="userName" required/><br/>
            Password: <input type="password" name="password" required><br/>
            <input type="submit" value="I need to get bloggin'!"/>
        </form>
        <div class="nav">
            <a href="go?action=register">I need to sign up for this awesomeness</a>
            <a href="go?action=timeline">Just show me the timeline</a>
        </div>
    </body>
</html>
