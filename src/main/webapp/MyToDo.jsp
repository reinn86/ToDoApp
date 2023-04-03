<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
	<jsp:useBean id="user" class="model.bean.User" scope="session"/>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ToDoアプリ</title>
</head>
<body>
    <h1><jsp:getProperty name="user" property="userName"/>さんのToDo</h1>
 	   
	<form action="" method="post">
   		<p>
  			<label for="caontents">内容</label>
 			<input type="text" name="contents" id="contents">
  			<label for="term">期限</label>
			<input type="date" name="term" id="term">
			<input type="submit" value="作成" name="APPEND">
		</p>
	</form>
	
	<ul>
   		<%= request.getAttribute("task") %>
   	</ul>
   	
   	<p>タスクの内容を変更するには内容の部分をクリックしてください</p>
   	
    <a href="./login">戻る</a>
    <script src="./assets/js/main.js"></script>
</body>
</html>