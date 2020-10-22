<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello Page</title>
</head>
<body>
<h1>Heloooooo!!!</h1>

<div>
    <table>
        <tr>
            <td>Car id</td>
            <td>Model</td>
            <td>Guarantee expirationdate</td>
            <td>Price</td>
            <td>Dealer id</td>
            <td>User id</td>
            <td>Country</td>
            <td>Edit</td>
            <td>Delete</td>
        </tr>
        <c:forEach var="car" items=" ${cars}">
            <tr>
                <td>${car.id}</td>
                <td>${car.model}</td>
                <td>${car.guarantee_expiration_date}</td>
                <td>${car.price}</td>
                <td>${car.dealer_id}</td>
                <td>${car.user_id}</td>
                <td>${car.country}</td>
                <td><button>Edit</button></td>
                <td><button>Delete</button></td>
            </tr>
        </c:forEach>
    </table>
</div>

<div>
    ${singleCar}
</div>
</body>
</html>
