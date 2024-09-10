<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CNT 4714 Summer 2024 - Enterprise System</title>
    <link rel="stylesheet" href="styles.css">
</head>

<body class="dataentryhome">
    <div id="root-header" class="header">
        <h1 style="color: red;">Welcome to the Summer 2024 Project 3 Enterprise System</h1>
        <h2 style="color: cyan;">Data Entry Application</h2>
    </div>

    <hr>
    <div class="user-message">
        <p>You are connected to the Project 3 Enterprise System database as <span
                class="user-level">data-entry-user</span> user.</p>
        <p>Enter the data values in a form below to add a new record to the corresponding database table.</p>
    </div>
    <hr>

    <div class="data-tables">
        <!-- Suppliers Table -->
        <form action="supplierrecordservlet" method="post">
            <fieldset class="table">
                <legend>Suppliers Record Insert</legend>
                <table id="suppliers">
                    <thead>
                        <tr>
                            <td><label for="snum1">snum</label></td>
                            <td><label for="sname1">sname</label></td>
                            <td><label for="status1">status</label></td>
                            <td><label for="city1">city</label></td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input type="text" name="snum" id="snum1" class="textarea" required>${requestScope.snum}
                            </td>
                            <td><input type="text" name="sname" id="sname1" class="textarea"
                                    required>${requestScope.sname}</td>
                            <td><input type="text" name="status" id="status1" class="textarea"
                                    required>${requestScope.status}</td>
                            <td><input type="text" name="city" id="city1" class="textarea" required>${requestScope.city}
                            </td>
                        </tr>
                    </tbody>
                </table>

                <!-- Table Buttons -->
                <div class="button-group">
                    <input type="submit" name="btnPress" class="enter" value="Enter Supplier Record Into Database">
                    <input type="reset" name="btnPress" class="reset" value="Clear Data and Results">
                </div>
            </fieldset>
        </form>
        <br>

        <!-- Parts Table -->
        <form action="partrecordservlet" method="post">
            <fieldset class="table">
                <legend>Parts Record Insert</legend>
                <table id="parts">
                    <thead>
                        <tr>
                            <td><label for="pnum1">pnum</label></td>
                            <td><label for="pname1">pname</label></td>
                            <td><label for="color1">color</label></td>
                            <td><label for="weight1">weight</label></td>
                            <td><label for="city2">city</label></td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input type="text" name="pnum" id="pnum1" class="textarea" required>${requestScope.pnum}
                            </td>
                            <td><input type="text" name="pname" id="pname1" class="textarea"
                                    required>${requestScope.pname}</td>
                            <td><input type="text" name="color" id="color1" class="textarea"
                                    required>${requestScope.color}</td>
                            <td><input type="text" name="weight" id="weight1" class="textarea"
                                    required>${requestScope.weight}</td>
                            <td><input type="text" name="city" id="city2" class="textarea" required>${requestScope.city}
                            </td>
                        </tr>
                    </tbody>
                </table>

                <!-- Table Buttons -->
                <div class="button-group">
                    <input type="submit" name="btnPress" class="enter" value="Enter Part Record Into Database">
                    <input type="reset" name="btnPress" class="reset" value="Clear Data and Results">
                </div>
            </fieldset>
        </form>
        <br>

        <!-- Jobs Table -->
        <form action="jobrecordservlet" method="post">
            <fieldset class="table">
                <legend>Jobs Record Insert</legend>
                <table id="jobs">
                    <thead>
                        <tr>
                            <td><label for="jnum1">jnum</label></td>
                            <td><label for="jname1">jname</label></td>
                            <td><label for="numworkers1">numworkers</label></td>
                            <td><label for="city3">city</label></td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input type="text" name="jnum" id="jnum1" class="textarea" required>${requestScope.jnum}
                            </td>
                            <td><input type="text" name="jname" id="jname1" class="textarea"
                                    required>${requestScope.jname}</td>
                            <td><input type="text" name="numworkers" id="numworkers1" class="textarea"
                                    required>${requestScope.numworkers}</td>
                            <td><input type="text" name="city" id="city3" class="textarea" required>${requestScope.city}
                            </td>
                        </tr>
                    </tbody>
                </table>

                <!-- Table Buttons -->
                <div class="button-group">
                    <input type="submit" name="btnPress" class="enter" value="Enter Job Record Into Database">
                    <input type="reset" name="btnPress" class="reset" value="Clear Data and Results">
                </div>
            </fieldset>
        </form>
        <br>

        <!-- Shipments Table -->
        <form action="shipmentrecordservlet" method="post">
            <fieldset class="table">
                <legend>Shipments Record Insert</legend>
                <table id="shipments">
                    <thead>
                        <tr>
                            <td><label for="snum2">snum</label></td>
                            <td><label for="pnum2">pnum</label></td>
                            <td><label for="jnum2">jnum</label></td>
                            <td><label for="quantity1">quantity</label></td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input type="text" name="snum" id="snum2" class="textarea" required>${requestScope.snum}
                            </td>
                            <td><input type="text" name="pnum" id="pnum2" class="textarea" required>${requestScope.pnum}
                            </td>
                            <td><input type="text" name="jnum" id="jnum2" class="textarea" required>${requestScope.jnum}
                            </td>
                            <td><input type="text" name="quantity" id="quantity1" class="textarea"
                                    required>${requestScope.quantity}</td>
                        </tr>
                    </tbody>
                </table>

                <!-- Table Buttons -->
                <div class="button-group">
                    <input type="submit" name="btnPress" class="enter" value="Enter Shipment Record Into Database">
                    <input type="reset" name="btnPress" class="reset" value="Clear Data and Results">
                </div>
            </fieldset>
        </form>
    </div>

    <br><br>

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