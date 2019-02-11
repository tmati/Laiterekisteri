(function() {
    var httpRequest;
    var testUrl = 'data/story.json';
    // Use Github Gist when no web server is available
    // var testUrl = 'https://gist.githubusercontent.com/c0lin/571d38b984d41b44aacf/raw/story.json';
    document.getElementById("ajaxButton").onclick = function() { makeRequest(testUrl); };
    document.getElementById("but").onclick = function () { urltesti();};
    function makeRequest(url) {
        if (window.XMLHttpRequest) { // Mozilla, Safari, ...
            httpRequest = new XMLHttpRequest();
        } else if (window.ActiveXObject) { // IE
            try {
                httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
            }
            catch (e) {
                try {
                    httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
                }
                catch (e) {}
            }
        }

        if (!httpRequest) {
            alert('Giving up :( Cannot create an XMLHTTP instance');
            return false;
        }

        // set a callback function for when the httpRequest completes
        httpRequest.onreadystatechange = alertContents, toimiiko;

        // now do the actual AJAX request
        httpRequest.open('GET', url);
        httpRequest.send();
    }

    // the callback
    // this will be run when the request completes (see above)
    function alertContents() {
        if (httpRequest.readyState === 4) {
            if (httpRequest.status === 200) {
                alert(httpRequest.responseText);
            } else {
                alert('There was a problem with the request.');
            }
        }
    }
    function urltesti() {
        var url = document.getElementById('asd').value;
        var req = new XMLHttpRequest();
        req.onreadystatechange = toimiiko;
        req.open('GET', url);
        req.send();


    }
    function toimiiko(){
        if (this.status === 404) {
            alert('SITE DOWN!');
        }
    }
})();