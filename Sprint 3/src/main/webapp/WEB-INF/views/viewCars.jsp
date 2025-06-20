<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>View Cars</title></head>
<body>
    <h2>List of Cars</h2>

    <a href="/cars/add">➕ Add New Car</a>
    <br/><br/>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Brand</th>
            <th>Model</th>
            <th>Price</th>
        </tr>
        <c:forEach var="car" items="${cars}">
            <tr>
                <td>${car.id}</td>
                <td>${car.brand}</td>
                <td>${car.model}</td>
                <td>${car.price}</td>
            </tr>
        </c:forEach>
    </table>

    <br/>
    <a href="/">⬅ Home</a>
</body>
</html>
