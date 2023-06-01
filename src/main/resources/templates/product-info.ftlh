<!DOCTYPE html>
<html>
<head>
    <title>OLX</title>
</head>
<body>
<h1>OLX</h1><hr>
<h4>Подробная информация о товаре</h4>
<#list images as img>
    <img src = "/images/${img.id}" height="100px" width="100px"><br><br>
</#list>
<b>Название товара: </b>${product.title}<br>
<b>Описание товара: </b>${product.description}<br>
<b>Цена товара: </b>${product.price}<br>
<b>Город: </b>${product.city}<br>
<b>Автор: </b>${product.author}<br>
<hr>
<h3>Создать новый товар</h3>
<form action = "/product/delete/${product.id}" method="post">
    <input type="submit" value="Удалить товар"/>
</form>
</body>
</html>