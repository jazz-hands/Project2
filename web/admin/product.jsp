<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mma" uri="/WEB-INF/music.tld" %>
<!DOCTYPE html>
<html>
  <head>
      <meta charset="utf-8">
      <title>Product Maintainence</title>
      <link rel="stylesheet" href="styles/main.css" type="text/css"/>
  </head>
  <body>
<p><i>${message}</i></p>
    <h1>Product</h1>
    <form action="ProductMaint" method="post">
      <strong>Code: </strong>
      
        <c:if test="${sessionScope.product.code!=null}">
            <input disabled="true" type="text" name="productCode" value="${sessionScope.product.code}">
            <mma:MarkEmptyField field="${sessionScope.product.code}"/><br>
        </c:if>
        <c:if test="${sessionScope.product.code==null}">
            <input type="text" name="productCode" value="">
            <mma:MarkEmptyField field="${sessionScope.product.code}"/><br>
        </c:if>
      
      <strong>Description: </strong>
      <input type=text name="description" value="${sessionScope.product.description}" id="description">
      <mma:MarkEmptyField field="${sessionScope.product.description}"/><br>
      
      <strong>Price: </strong>
      <input type=text name="price" value="${sessionScope.product.price}" id="price">
      <mma:MarkEmptyField field="${sessionScope.product.price}"/><br>
      
      <input type="hidden" name="action" value="updateProduct" />
      <input type="submit" value="Update Product">
    </form>
      
    <form name="ViewProductsButton" action="ProductMaint">
        <input type="submit" value="View Products">
    </form>
  </body>
</html>
