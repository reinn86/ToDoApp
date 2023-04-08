'use strict';

//TODO sqlがクローズされてるから接続できるようにする
//TODO クリック時の処理が常時発動している？（調査中）

function process(name) {
//	let index = document.getElementsByName(name);
	let contentsText = document.getElementById(name).textContent;

	document.getElementById(name)
		.innerHTML = '<input type=\"text\"' 
		+ 'value=\"' +contentsText + '\"' 
		+ '\" name=\"MODIFICATION\"></input>';
}

for(let i = 0; i < 7; i++) {
	let button = document.getElementById('tasklist-contents' + i);
	button.addEventListener('click', process('tasklist-contents' + i));
//	console.log('tasklist-contents' + i);
}