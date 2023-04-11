'use strict';

//TODO sqlがクローズされてるから接続できるようにする

let button = document.querySelector('#task_list').children;
let flag = new Array(button.length); //タスクがクリックされているかのフラグ

for(let i = 0; i < button.length; i++) {	
	flag[i] = false;
	button[i].addEventListener('click',function() {
		if(!flag[i]) {
			let contentsText = document.getElementById('tasklist-contents').innerText;
			
			
			console.log(contentsText);
			flag[i] = true;
			document.getElementsByClassName('tasklist-contents')[i].innerHTML 
			= '<form action=\"\"' + ' method=\"post\">'
			+ '<input type=\"text\"' 
			+ 'value=\"' +contentsText + '\"' 
			+ 'name=\"MODIFICATION\"></input>'
			+ '<input type=\"submit\"' 
			+ 'value=\"修正\"'
			+ 'name=\"MODIFICATION\"></input>'
			+ '<input type=\"submit\"' 
			+ 'value=\"取消\"></input>'
			+ '<input type=\"hidden\" value=\"' + i + '\"name=\"index\">'
			+ '</form>';
		}
	});
}