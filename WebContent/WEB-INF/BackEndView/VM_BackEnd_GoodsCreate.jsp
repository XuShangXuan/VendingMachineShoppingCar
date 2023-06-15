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
	
	<script type="text/javascript">
		$(document).ready(function() {
			// 監聽檔案選擇框的變動事件
		    $('#goodsImageName').change(function() {
		        // 獲取選擇的檔案名稱
		        var fileName = $(this).val().split('\\').pop();
				
		    	 // 設定檔案名稱顯示的前綴長度和後綴長度
		        var prefixLength = 20;
		        var suffixLength = 8;
		        
		        // 檢查檔案名稱長度是否超過指定長度
		        if (fileName.length > prefixLength + suffixLength) {
		          // 截斷檔案名稱的前綴和後綴
		          var prefix = fileName.substring(0, prefixLength);
		          var suffix = fileName.substring(fileName.length - suffixLength);
		          
		          // 組合截斷後的檔案名稱
		          fileName = prefix + "....." + suffix;
		        }
		        
		        // 更改 label 文字為選擇的檔案名稱
		        $('#goodsImageNameText').text(fileName);
		    });
	    });
	</script>
</head>
<body>

	<%@ include file="VM_BackEnd_FunMenu.jsp" %>
	
	<div class="container">
		<h2>商品新增上架</h2>
		<p style="color:blue;">${sessionScope.createMsg}</p>
		<% session.removeAttribute("createMsg"); %>
		<form class="needs-validation" name="addGood" action="BackEndAction.do?action=addGoods" enctype="multipart/form-data" method="post" novalidate>
			<div class="form-row">
	  			<div class="col-4">
			      <label for="goodsName">商品名稱</label>
			      <input class="form-control" type="text" id="goodsName" name="goodsName" size="10" placeholder="Enter text" required/>
			      <div class="invalid-feedback">
			        商品名稱必填!
			      </div>
			    </div>
			</div>
			<br/>
			<div class="form-row">
	  			<div class="col-3">
			      <label for="goodsPrice">商品價格</label>
			      <input class="form-control" type="number" id="goodsPrice" name="goodsPrice" size="5" value="0" min="0" max="1000" placeholder="Enter number" required/>
			      <div class="invalid-feedback">
			        商品價格必填!
			      </div>
			    </div>
			</div>
			<br/>
			<div class="form-row">
	  			<div class="col-3">
			      <label for="goodsQuantity">上架數量</label>
			      <input class="form-control" type="number" id="goodsQuantity" name="goodsQuantity" size="5" value="0" min="0" max="1000" placeholder="Enter number" required/>
			      <div class="invalid-feedback">
			        上架數量必填!
			      </div>
			    </div>
			</div>
			<br/>
			<div class="custom-file col-4">
		      <input type="file" class="custom-file-input" id="goodsImageName" name="goodsImageName" aria-describedby="goodsImageName" required/>
		      <label id="goodsImageNameText" class="custom-file-label" for="goodsImageName"><span>選擇要上傳的商品圖片</span></label>
		      <div class="invalid-feedback">商品進貨量必填!</div>
		    </div>
		    <br/>
			<br/>
			<div class="form-row">
			    <div class="col-3">
			      <label for="status">商品狀態</label>
			      <select class="form-control" name="status" id="status" required>
			      	<option <c:if test="${modifyGoods.status eq 1}">selected</c:if> value="1">上架</option>
					<option <c:if test="${modifyGoods.status eq 0}">selected</c:if> value="0">下架</option>	
				  </select>
			    </div>
	  		</div>
			<br/>
	  		<button type="submit" class="btn btn-primary" onclick="submitFunction()">送出</button>
		</form>
	</div>
</body>
</html>