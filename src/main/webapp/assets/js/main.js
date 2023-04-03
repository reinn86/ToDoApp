'use strict';

function process() {
	let contentsText = document.getElementById('process').innerText
	
	document.getElementById('process')
		.outerHTML = '<input type=\"text\" value=\"' + contentsText + '\" name=\"MODIFICATION\"></input>';
}

document.getElementById('process').addEventListener('click', process);
document.getElementById('process').addEventListener('content_modification', process);