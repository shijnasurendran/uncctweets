/* 
 All your JS code must be here.
 */



 history.pushState(null, null, 'logout.jsp');
    window.addEventListener('popstate', function(event) {
    history.pushState(null, null, 'logout.jsp');
    });


