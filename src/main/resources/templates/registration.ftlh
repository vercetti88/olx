<!DOCTYPE html>
<html>
<head>
    <title>OLX</title>
</head>
<body>
<h1>OLX</h1><hr>
<h4>Registration</h4>
<hr>
<h3>Создать новый товар</h3>
<form action = "/api/v1/auth/registration" method="post" >
    Имя: <input type="text" name="firstname"/><br><br>
    Фамилия: <input type="text" name="lastname"/><br><br>
    Email: <input type="text" name="email"/><br><br>
    Пароль: <input type="text" name="password"/><br><br>
    <input type="submit" onclick="" value="Зарегистрироваться">
</form>
</body>
</html>