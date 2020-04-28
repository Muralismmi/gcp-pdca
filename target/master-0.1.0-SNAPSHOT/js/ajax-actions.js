function doFetch(type1, url1, data1) {

	let optionGet = {
        method: type1,
        async:false,
        headers: {
            "Content-Type": "application/json; charset=utf-8",
        },
    };
	let optionPost = {
	        method: type1,
	        async:false,
	        headers: {
	            "Content-Type": "application/json; charset=utf-8",
	        },
	        body: JSON.stringify(data1),
	    }

	/*
	 * var asnycVal = true; try { $.ajax({ type : type1, url : url1, async :
	 * asnycVal,// Nanda Changes data : JSON.stringify(data1), contentType :
	 * "application/json; charset=utf-8", success : function(data) {
	 * handleFunction(data); }, complete : function() { }, error : function(e,
	 * url, data) { } }); } catch (i) { console.error(i); }
	 */
	if(type1 === 'GET'){
		return fetch(url1,optionGet )
	    .then(response => response.json());
	}else if(type1 === 'POST'){
		return fetch(url1,optionPost )
	    .then(response => response.json());
	}
	
}