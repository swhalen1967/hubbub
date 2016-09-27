<!DOCTYPE html>
<html>
    <head>
        <title>Hubbub &raquo; Add a Post</title>
        <link rel="stylesheet" type="text/css" href="styles/main.css"/>
    </head>
    <body>
        <div class="masthead"><img src="images/hubbub.png"/></div>
        <h1>Sign up, then log-in to get bloggin'!</h1>
        <c:if test="${not empty flash}"><h2 class="flash">${flash}</h2></c:if>
        <form method="POST" action="go">
            <input type="hidden" name="action" value="post"/>
            <div class="postArea">
                <textarea id="content" name="content" placeholder="Up to 200 characters"
                          cols="40" rows="5">
                </textarea>
                <p><input class="submit" type="submit" value="Submit this post"/></p>
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
