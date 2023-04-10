'use strict';

//TODO sqlがクローズされてるから接続できるようにする

function modifyTask(mouseEvent) {
//	if(this.getElementById('tasklist-contents')) {
		let contentsText = mouseEvent.target.textContent;
		let content = mouseEvent.target.getAttribute('tasklist-contents');

		console.log(content);
		this.innerHTML = '<input type=\"text\"' 
		+ 'value=\"' +contentsText + '\"' 
		+ 'name=\"MODIFICATION\"></input>'
		+ '<input type=\"submit\"' 
		+ 'value=\"修正\"'
		+ 'name=\"MODIFICATION\"></input>'
		+ '<input type=\"submit\"' 
		+ 'value=\"取消\"></input>';
	}
//}

let button = document.querySelector('#task_list').children;

for(let i = 0; i < button.length; i++) {
	button[i].addEventListener('click',modifyTask);
	console.log(button[i]);
}