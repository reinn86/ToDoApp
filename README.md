# ToDoApp

## 環境

Tomcat v8.0

javaSE17

sql　local

## SQLテーブル

### tasks

  id Int
  
  user_id Int
  
  contents VARCHAR(255)
  
  term DATE
  
  is_comopleted TINYINT
  
### users

  id Int
  
  name VARCHAR(45)
