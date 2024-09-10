<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CNT 4714 Summer 2024 - Enterprise System</title>

    <link rel="stylesheet" href="styles.css">
</head>

<body>
    <div id="root-header" class="header">
        <h1 style="color: yellow;">Welcome to the Summer 2024 Project 3 Enterprise System</h1>
        <h2 style="color: rgb(99, 255, 47);">A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat
            Container</h2>
    </div>
    <hr>

    <div class="user-message">
        <p>You are connected to the Project 3 Enterprise System database as <span class="user-level">root-level</span>
            user.</p>
        <p>Please enter any SQL Query or update command in the box below.</p>
    </div>

    <div>
        <form action="rootservlet" method="post" class="input-area">
            <div class="form-group">
                <textarea id="command" name="command" required>${sessionScope.sqlStatement}</textarea>
            </div>
            <div class="button-group">
                <input type="submit" name="btnPress" class="enter" value="Execute Command">
                <input type="submit" name="btnPress" class="reset" value="Reset Form">
                <input type="submit" name="btnPress" class="clear" value="Clear Result">
            </div>
        </form>
    </div>

    <p>All execution results will appear below this line</p>

    <hr>

    <div class="results">
        <p>Execution Results:</p>
        <br>
        <div class="message">${sessionScope.message}</div>
    </div>

    <div>
        <form action="redirect" method="post">
            <div class="button-group">
                <input type="submit" name="btnPress" class="logout" value="Logout">
            </div>
        </form>
    </div>

</body>

</html>