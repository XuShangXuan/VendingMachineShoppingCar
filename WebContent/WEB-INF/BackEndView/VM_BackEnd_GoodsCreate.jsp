<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>販賣機-後臺ShoppingCar</title>
	<script type="text/javascript">
		function submitFunction() {
			
			var goodName = document.getElementById("nameID").value;
			var goodsPrice = document.getElementById("priceID").value;
			var goodsQuantity = document.getElementById("quantityID").value;
			var imageID = document.getElementById("imageID").value;
			
			if (goodName == null || goodName == "") {
				alert("請輸入「商品名稱」");
			} else if (goodsPrice == null || goodsPrice == "") {
				alert("請輸入「商品價格」");
			} else if (goodsQuantity == null || goodsQuantity == "") {
				alert("請輸入「上架數量」");
			} else if (imageID == null || imageID == "") {
				alert("尚未選擇「商品圖片」");
			} else {
				document.addGood.submit();
			}
	
		}
	</script>
</head>
<body>

	<%@ include file="VM_BackEnd_FunMenu.jsp" %>
		
	<h2>商品新增上架</h2><br/>
	<div style="margin-left:25px;">
	<p style="color:blue;">${sessionScope.createMsg}</p>
	<% session.removeAttribute("createMsg"); %>
	<form name="addGood" action="BackEndAction.do?action=addGoods" enctype="multipart/form-data" method="post">
		<p>
			飲料名稱：
			<input type="text" id="nameID" name="goodsName" size="10"/>
		</p>
		<p>
			設定價格： 
			<input type="number" id="priceID" name="goodsPrice" size="5" value="0" min="0" max="1000"/>
		</p>
		<p>
			初始數量：
			<input type="number" id="quantityID" name="goodsQuantity" size="5" value="0" min="0" max="1000"/>
		</p>
		<p>
			商品圖片：
			<input type="file" id="imageID" name="goodsImageName"/>
		</p>
		<p>
			商品狀態：
			<select name="status">
				<option value="1">上架</option>
				<option value="0">下架</option>				
			</select>
		</p>
		<p>
			<input type="button" value="送出" onclick="submitFunction()">
		</p>
	</form>
	</div>
</body>
</html>