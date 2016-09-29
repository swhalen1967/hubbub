<%-- @elvariable id="users" type="java.util.ArrayList<HubbubUser>" --%>
<%-- @elvariable id="user" type="edu.acc.j2ee.hubbub.models.HubbubUser" --%>
<!DOCTYPE html>
<html>
    <head>
        <title>Hubbub &raquo; Timeline</title>
        <link type="text/css" rel="stylesheet" href="styles/main.css"/>
    </head>
    <body>
        <div class="masthead"><img src="images/hubbub.png"/></div>
        <c:choose>
            <c:when test="${not empty errors}">
                <ul>
                    <c:forEach var="error" items="${errors}">
                        <li class="flash">${error}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:when test="${not empty user}">
                <h1>Welcome, ${user.userName}.</h1>
                <h2>Here is the famous Hubbub&trade; Timeline:</h2>
            </c:when>
            <c:otherwise>
                <h1>Here is a taste of what Hubbub&trade;'s users have to say:</h1>
            </c:otherwise>
        </c:choose>
        <c:if test="${empty errors}">
            <c:forEach var="post" items="${posts}">
                <div class="hubbubPost">
                    Posted by <span class="userName">${post.user.userName}</span>
                    <span class="postDate">${post.postDate}</span>
                    <div class="post"> ${post.content}</div>
                </div>
            </c:forEach>
        </c:if>
        <div class="nav">
            <c:choose>
                <c:when test="${empty user}">
                    <a href="go?action=login">
                        Get me a log-in so I can get bloggin'!
                    </a>
                    <a href="go?action=register">
                        Sign me up for this awesomness!
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="go?action=post">Post something pithy</a>
                    <a href="go?action=logout">Log out for now</a>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
