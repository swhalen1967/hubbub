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
        <h1>
        <c:choose>
            <c:when test="${empty user}">
                Hubbub&trade; welcomes the following users:
            </c:when>
            <c:otherwise>
                Welcome, ${user.userName}. Here are Hubbub&trade;'s users:
            </c:otherwise>
        </c:choose>
        </h1>
        <c:forEach var="user" items="${users}">
            <div class="hubbubUser">
                User Name: <span class="userName">${user.userName}</span><br/>
                Join Date: <span class="joinDate">${user.joinDate}</span>
            </div>
        </c:forEach>
        <c:if test="${not empty user}">
            <h1>Hubbub&trade;'s users have this to say:</h1>
            <c:forEach var="post" items="${posts}">
                <div class="hubbubPost">
                    User <span class="userName">${post.user.userName}</span>
                    posted <span class="postDate">${post.postDate}</span><br/>
                    <div class="post">${post.content}</div>
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
