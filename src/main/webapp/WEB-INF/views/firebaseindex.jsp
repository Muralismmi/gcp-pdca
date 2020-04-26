<!DOCTYPE html>
<html>
<body>

<h1>My First Heading</h1>
<p>My first paragraph.</p>
<div id="test1"></div>
 <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="js/firebase.js"></script>
<script type="text/javascript" src="js/gstaticfirebase.js"></script>
<script type="text/javascript" src="js/require.js"></script>
<!-- <script src="https://cdn.firebase.com/js/client/2.3.2/firebase.js"></script>
<script src="https://www.gstatic.com/firebasejs/5.9.0/firebase.js"></script>
<script src="https://requirejs.org/docs/release/2.3.5/minified/require.js"></script> -->
<script type="text/javascript" >
firebase.initializeApp({
	  apiKey: 'AIzaSyBrq4IaqRwX-ws0C13Y2QrGPKCb2JtYSu0',
	  authDomain: 'valeo-cp0877-dev.firebaseapp.com',
	  projectId: 'valeo-cp0877-dev'
	});

	 var db = firebase.firestore();/* .enablePersistence()
	  .catch(function(err) {
	      if (err.code == 'failed-precondition') {
	          // Multiple tabs open, persistence can only be enabled
	          // in one tab at a a time.
	          // ...
	    	  console.log("Multiple tabs open, persistence can only be enabled");
	      } else if (err.code == 'unimplemented') {
	          // The current browser does not support all of the
	          // features required to enable persistence
	          // ...
	          console.log("features required to enable persistence not available");
	      }
	  });  */
/* 	db.collection("users").add({
	    first: "Ada",
	    last: "Lovelace",
	    born: 1815
	})
	.then(function(docRef) {
	    console.log("Document written with ID: ", docRef.id);
	})
	.catch(function(error) {
	    console.error("Error adding document: ", error);
	});

	//Add a second document with a generated ID.
	db.collection("users").add({
	    first: "Alan",
	    middle: "Mathison",
	    last: "Turing",
	    born: 1912
	})
	.then(function(docRef) {
	    console.log("Document written with ID: ", docRef.id);
	})
	.catch(function(error) {
	    console.error("Error adding document: ", error);
	}); */
	
	
	
	/* db.collection("users").get().then((querySnapshot) => {
	    querySnapshot.forEach((doc) => {
	     test =	;
	    });
	}); */
	
	function testaddEntry(){
		db.collection("users").add({
		    first: "Ada",
		    last: "Lovelace",
		    born: 1815
		})
		.then(function(docRef) {
		    console.log("Document written with ID: ", docRef.data.first);
		})
		.catch(function(error) {
		    console.error("Error adding document: ", error);
		});
	}
	
	
</script>
</body>
</html>