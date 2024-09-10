<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CNT 4714 Summer 2024 - Enterprise System</title>

    <link rel="stylesheet" href="styles.css">
</head>

<body>
    <div class="header">
        <h1 style="color: yellow;">Welcome to the Summer 2024 Project 3 Enterprise System</h1>
        <h2 style="color: rgb(99, 255, 47);">A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat
            Container</h2>
    </div>
    <hr>

    <div class="user-message">
        <p>You are connected to the Project 3 Enterprise System database as <span
                class="user-level">accounant-level</span>
            user.</p>
        <p>Please select the operation you would like to perform from the list below.</p>
    </div>

    <!-- Radio Button Selection -->
    <div>
        <form action="accountantservlet" method="post" class="operation-selection">
            <div class="options">
                <div class="form-group">
                    <input type="radio" id="max-value" name="cmd" value="1">
                    <label for="max-value">Get the Maximum Status Value of all Supliers</label>
                </div>
                <div class="form-group">
                    <input type="radio" id="sum" name="cmd" value="2">
                    <label for="sum">Get the total weight of all Parts</label>
                </div>
                <div class="form-group">
                    <input type="radio" id="shipments" name="cmd" value="3">
                    <label for="shipments">Get the Total Number of Shipments</label>
                </div>
                <div class="form-group">
                    <input type="radio" id="workers" name="cmd" value="4">
                    <label for="workers">Get the Name and Number of Workers of the Job with the Most Workers</label>
                </div>
                <div class="form-group">
                    <input type="radio" id="supplier" name="cmd" value="5">
                    <label for="supplier">Get the Maximum Status Value of all Supliers</label>
                </div>
            </div>
            <br><br>
            <div class="button-group">
                <input type="submit" name="btnPress" class="enter" value="Execute Command">
                <input type="submit" name="btnPress" class="reset" value="Clear Results">
            </div>
        </form>
    </div>
    <p>All execution results will appear below this line</p>

    <hr>

    <div class="results">
        <p>Execution Results:</p>
        <br>
        <div class="message">${message}</div>
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