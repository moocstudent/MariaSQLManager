<%--
  Created by IntelliJ IDEA.
  User: SeeClanUkyo
  Date: 2018/07/13
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>展示数据库查询信息--MariaDB的追加使用(实际使用的是Oracle)</title>
</head>
<body>
<p>第一次在linux中安装的是MariaDB,当时MySQL好像闭源了.</p><br>
<p>使用了一段时间后,开始使用其他的数据库,但对MariaDB数据库名称的取名之中的故事有所感想.</p><br>
<p>随后在做一个数据库查询系统的雏形,继续使用了Maria<-+SQLManager,即MariaSQLManager</p>

<form action="/msql" method="post">
    <p>Please input a new query sql statement.</p>
    <input type="text" name="web_sql"><br>
    <input type="submit" value="查询">
</form>

</body>
</html>
