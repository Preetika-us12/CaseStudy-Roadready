<html>
<head><title>Add Car</title></head>
<body>
    <h2>Add a New Car</h2>

    <form method="post" action="/cars/save">
        Brand: <input type="text" name="brand" required/><br/>
        Model: <input type="text" name="model" required/><br/>
        Price: <input type="number" name="price" required/><br/>
        Owner ID: <input type="number" name="ownerId" required/><br/>
        <input type="submit" value="Add Car"/>
    </form>

    <br/>
    <a href="/cars">⬅ Back to Car List</a>
</body>
</html>
