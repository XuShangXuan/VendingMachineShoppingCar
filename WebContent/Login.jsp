<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="bootstrap4.0/bootstrap.min.css"/>
<link type="text/css" rel="stylesheet" href="bootstrap4.0/signin.css"/>
<title>VendingMachineLogin</title>
<script type="text/javascript" src="bootstrap4.0/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="bootstrap4.0/popper.min.js"></script>
<script type="text/javascript" src="bootstrap4.0/bootstrap.min.js"></script>
	<c:if test="${not empty requestScope.loginMsg}">
		<script>
	      $(window).ready(() => {
	        $('#myModal').modal('show');
	      })
	    </script>
	</c:if>
	<script>
	// Example starter JavaScript for disabling form submissions if there are invalid fields
	(function() {
	  'use strict';
	  window.addEventListener('load', function() {
	    // Fetch all the forms we want to apply custom Bootstrap validation styles to
	    var forms = document.getElementsByClassName('form-signin');
	    // Loop over them and prevent submission
	    var validation = Array.prototype.filter.call(forms, function(form) {
	      form.addEventListener('submit', function(event) {
	        if (form.checkValidity() === false) {
	          event.preventDefault();
	          event.stopPropagation();
	        }
	        form.classList.add('was-validated');
	      }, false);
	    });
	  }, false);
	})();
	</script>
</head>
<body class="text-center">
		
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">系統回應</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        ${requestScope.loginMsg}
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<form name="LoginForm" class="form-signin" action="LoginAction.do" method="post" novalidate>
		<img class="mb-4" style="border-radius:50%;" src="DrinksImage/coffee.jpg" alt="" width="72" height="72">
		<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
		<input type="hidden" name="action" value="login"/>
		<input type="text" id="id" name="id" class="form-control" placeholder="ID" required autofocus>
	    <input type="password" id="pwd" name="pwd" class="form-control" placeholder="Password" required>
	    <br/>
	    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
	    <p class="mt-5 mb-3 text-muted">&copy; 2023-2023</p>
	</form>
</body>
</html>

<!-- http:localhost:8085/VendingMachineShoppingCar/Login.jsp -->