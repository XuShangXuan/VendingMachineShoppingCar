<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>販賣機-後臺ShoppingCar</title>
	<script type="text/javascript">
		
		(function() {
		  'use strict';
		  window.addEventListener('load', function() {
		    // Fetch all the forms we want to apply custom Bootstrap validation styles to
		    var forms = document.getElementsByClassName('needs-validation');
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
<body>

	<%@ include file="VM_BackEnd_FunMenu.jsp" %>
	
	<div class="container">
	
		<h2>銷售報表</h2>
		<br/>
		<form class="needs-validation" name="querySalesReport" action="BackEndAction.do" method="get" novalidate>
			<input type="hidden" name="action" value="querySalesReport"/>
			<div class="form-row">
			    <div class="col-4">
			    	<span>起始日期</span>
			    </div>
			    <div class="col-4">
			    	<span>結束日期</span>
			    </div>
	  		</div>
	  		<div class="form-row">
			    <div class="col-4">
			    	<input class="form-control" type="date" id="startDateID" name="queryStartDate" required/>
			    	<div class="invalid-feedback">
			          尚未選擇起始日期(起)!
			        </div>
			    </div>
			    <div class="col-4">
			    	<input class="form-control" type="date" id="endDateID" name="queryEndDate" required/>
			    	<div class="invalid-feedback">
			          尚未選擇結束日期(訖)!
			        </div>
			    </div>
			    <div class="col-4">
			    	<button type="submit" class="btn form-control" style="border:1px solid #ced4da;">查詢</button>
			    </div>
	  		</div>
	  		<br/>
		</form>
		<br/>
		<table class="table table-striped table table-hover" border="1">
			<thead>
				<tr>
					<th scope="col">訂單編號</th>
					<th scope="col">顧客姓名</th>
					<th scope="col">購買日期</th>
					<th scope="col">飲料名稱</th> 
					<th scope="col">購買單價</th>
					<th scope="col">購買數量</th>
					<th scope="col">購買金額</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${reports}" var="report">
					<tr>
						<td>${report.orderID}</td>
						<td>${report.customerName}</td>
						<td>${report.orderDate}</td>
						<td>${report.goodsName}</td>
						<td>${report.goodsBuyPrice}</td> 
						<td>${report.buyQuantity}</td>
						<td>${report.buyAmount}</td>	
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>