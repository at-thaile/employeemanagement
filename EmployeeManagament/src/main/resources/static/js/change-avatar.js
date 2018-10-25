var imageUrl = 'http://localhost:8080/images/'+sessionStorage.getItem("avatar");
$('#ava-left').prop('src',imageUrl);
$('#ava-right').prop('src',imageUrl);