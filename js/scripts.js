function loadFunction() {
    alert("Page is loaded");
}

function unloadFunction() {
    alert("Page is unloaded");
}

function clickFunction() {
    alert("Link is clicked");
}


function loadJson() {
	var text = '{"employees":[' +
	'{"firstName":"John","lastName":"Doe" },' +
	'{"firstName":"Anna","lastName":"Smith" },' +
	'{"firstName":"Peter","lastName":"Jones" }]}';

	obj = JSON.parse(text);
	document.getElementById("json").innerHTML =
	obj.employees[1].firstName + " " + obj.employees[1].lastName;
}