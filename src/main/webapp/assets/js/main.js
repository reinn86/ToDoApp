'use strict';

let taskListHtml = document.querySelector('#task_list').children;
let isClickArray = new Array(taskListHtml.length); //タスクがクリックされているかのフラグ

for(let i = 0; i < taskListHtml.length; i++) {	
	isClickArray[i] = false;
	document.getElementById('tasklist-contents' + i).addEventListener('click',function() {
		if(!isClickArray[i]) {
			let contentsText = document.getElementById('tasklist-contents' + i).innerText;
			
			isClickArray[i] = true;
			document.getElementsByClassName('tasklist-contents')[i].innerHTML 
			= '<form action=\"\"' + ' method=\"post\">'
			+ '<input type=\"text\"' 
			+ 'value=\"' + contentsText + '\"' 
			+ 'name=\"MODIFICATION\"></input>'
			+ '<input type=\"submit\"' 
			+ 'value=\"修正\"'
			+ 'name=\"MODIFICATION\"></input>'
			+ '<input type=\"submit\" value=\"取消\"></input>'
			+ '<input type=\"hidden\" value=\"' + i + '\"name=\"index\"></input>'
			+ '</form>';
		}
	});
}