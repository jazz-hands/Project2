<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Confirm</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>
    
<h3>Are you sure you want to delete this product?</h3>
<p><b>Code:        ${sessionScope.product.code}</b></p>
<p><b>Description: ${sessionScope.product.description}</b></p>
<p><b>Price:       ${sessionScope.product.price}</b></p>
<div class="buttonController">
<form name="deleteProduct" action="ProductMaint">
            <input type="submit" value="Yes" />
            <input type="hidden" name="action" value="deleteProduct"/>
            <input type="hidden" name="productCode" value="${product.code}"/>
</form>
<form name="goToProductView" action="ProductMaint">
            <input type="submit" value="No" />
            <input type="hidden" name="action" value="viewProduct" />
</form>
</div>        
</body>
</html>