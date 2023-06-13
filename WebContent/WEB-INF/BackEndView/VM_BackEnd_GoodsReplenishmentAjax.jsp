<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/" var="WEB_PATH"/>
<c:url value="/js" var="JS_PATH"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>販賣機-後臺ShoppingCar</title>
<script src="${JS_PATH}/jquery-1.11.1.min.js"></script>
	
	<script>
	// Example starter JavaScript for disabling form submissions if there are invalid fields
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
	
	<script type="text/javascript">
	
	$(document).ready(function(){
		
		$("#goodsID").bind("change",function(){
			
			const goodsID = $("#goodsID option:selected").val();
			if(""!= goodsID){
				
				const goodsObj = {
					id : goodsID //網址後街參數 BackEndAction.do?action=getReplenishmentGoods?id=XXX(goodsID)
				};
				
				$.ajax({
				  url: '${WEB_PATH}BackEndAction.do?action=getReplenishmentGoods', // 指定要進行呼叫的位址
				  type: "GET", // 請求方式 POST/GET
				  data: goodsObj, // 傳送至 Server的請求資料(物件型式則為 Key/Value pairs)
				  dataType : 'JSON', // Server回傳的資料類型
				  success: function(goodsInfo) { // 請求成功時執行函式
				  	$("#goodsPrice").val(goodsInfo.goodsPrice);
				  	$("#stockID").html(goodsInfo.goodsQuantity);
				  	$("#statusID").val(goodsInfo.status);
				  },
				  error: function(error) { // 請求發生錯誤時執行函式
				  	alert("Ajax Error!");
				  }
				});
			}else{
			  	$("#goodsPrice").val('');
			  	$("#stockID").html('');
			}
		});
		
	});
	
	</script>
</head>
<body>

	<%@ include file="VM_BackEnd_FunMenu.jsp" %>
	
	<div class="container">
		<h2>商品維護作業</h2><br/>
		<p style="color:blue;">${sessionScope.modifyMsg}</p>
		<form class="needs-validation" name="updateGoodsForm" action="BackEndAction.do" method="post" novalidate>
			<input type="hidden" name="action" value="updateGoods"/>
			<div class="form-row">
			    <div class="col-4">
			      <label for="goodsID">商品列表</label>
			      <select class="form-control" name="goodsID" id="goodsID" required>
			      	<option value="">請選擇...</option>
				 	<c:forEach items="${goods}" var="good">
						<option <c:if test="${good.goodsID eq modifyGoods.goodsID}">selected</c:if> value="${good.goodsID}">
							${good.goodsName}
						</option>
					</c:forEach>
				  </select>
			    </div>
	  		</div>
	  		<br/>
	  		<div class="form-row">
	  			<div class="col-3">
			      <label for="goodsPrice">商品價格</label>
			      <input type="number" class="form-control" name="goodsPrice" value="${modifyGoods.goodsPrice}" id="goodsPrice" min="1" required>
			    </div>
			</div>
			<br/>
			<div class="form-row">
	  			<div class="col-3">
			      <label for="goodsQuantity">商品庫存量</label>
			      <input type="number" class="form-control" value="0" name="goodsQuantity" id="goodsQuantity" min="0" required>
			      (庫存:<span id="stockID">${modifyGoods.goodsQuantity}</span>件)
			    </div>
			</div>
			<br/>
			<div class="form-row">
			    <div class="col-4">
			      <label for="statusID">商品狀態</label>
			      <select class="form-control" name="status" id="statusID" required>
			      	<option <c:if test="${modifyGoods.status eq 1}">selected</c:if> value="1">上架</option>
					<option <c:if test="${modifyGoods.status eq 0}">selected</c:if> value="0">下架</option>	
				  </select>
			    </div>
	  		</div>
	  		<br/>
	  		<button type="submit" class="btn btn-primary" onclick="submitFunction()">送出</button>
  		</form>
	</div>
	<%
		session.removeAttribute("modifyMsg");
		session.removeAttribute("modifyGoodsID");
	%>
</body>
</html>