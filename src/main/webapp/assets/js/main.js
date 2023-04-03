'use strict';

function process() {
	document.getElementById('process')
		.innerHTML = "<input type=\"text\" hint=\"aaaa\"></input>";
}

document.getElementById('process').addEventListener('click', process);