<!DOCTYPE html>
<html>
    <head>
        <title>Hubbub &raquo; Add a Post</title>
        <link rel="stylesheet" type="text/css" href="styles/main.css"/>
    </head>
    <body>
        <div class="masthead"><img src="images/hubbub.png"/></div>
        <c:choose>
            <c:when test="${not empty user}">
                <h1>Add a Post with the Most:</h1>
            </c:when>
            <c:otherwise>
                <h1>Sign up, then log-in to get bloggin'!</h1>
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty errors}">
            <ul>
                <c:forEach var="error" items="${errors}">
                    <li class="flash">${error}</li>
                </c:forEach>
            </ul>
        </c:if>
        <form method="POST" action="go">
            <input type="hidden" name="action" value="post"/>
            <div class="postArea">
                <textarea id="content" name="content"
                          placeholder="Up to 200 characters"
                          cols="40" rows="5">
                </textarea>
                <p>
                    <input class="submit" type="submit" value="Submit this post"/>
                </p>
            </div>
        </form>
        <div class="nav">
            <a href="go?action=timeline">Timeline time</a>
            <a href="go?action=logout">Log me the F out</a>
        </div>
        <script type="text/javascript">
            document.getElementById("content").focus();
        </script>
    </body>
</html>
