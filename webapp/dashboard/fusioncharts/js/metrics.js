
// Build an empty URL structure in which we will store
// the individual query values by key.
var objURL = new Object();
  
  
// Use the String::replace method to iterate over each
// name-value pair in the query string. Location.search
// gives us the query string (if it exists).
window.location.search.replace(
new RegExp( "([^?=&]+)(=([^&]*))?", "g" ),
 
// For each matched query string pair, add that
// pair to the URL struct using the pre-equals
// value as the key.
function( $0, $1, $2, $3 ){
	objURL[ $1.toLowerCase() ] = $3;
}
);
