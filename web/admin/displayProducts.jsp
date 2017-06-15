<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Product Maintenance</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>

        <h1>Products</h1>

        <table>
            <tr>
                <th>Code</th>
                <th>Description</th>
                <th class="right">Price</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="product" items="${sessionScope.products}">
                <tr>
                    <td>${product.code}</td>
                    <td>${product.description}</td>
                    <td class="right">${product.price}</td>
                    <td class="right">
                        <p><a href="ProductMaint?action=addProduct&productCode=${product.code}"  >Edit</a></p>
                    </td>
                    <td class="right"><a href="ProductMaint?action=confirmProduct&productCode=${product.code}"  >Delete</a></td>
                </tr>
            </c:forEach>
        </table>
        <form name="goToProductView" action="ProductMaint">
            <input type="submit" value="Add Product" />
            <input type="hidden" name="action" value="addProduct" />
        </form>
    </body>
</html>