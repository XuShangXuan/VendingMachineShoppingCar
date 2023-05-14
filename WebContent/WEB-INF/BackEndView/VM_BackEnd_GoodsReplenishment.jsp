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
			
			var goodsID = document.getElementById("goodsID").value;
			var goodsPrice = document.getElementById("priceID").value;
			var goodsQuantity = document.getElementById("quantityID").value;
			var statusID = document.getElementById("statusID").value;
			
			if (goodsID == null || goodsID == "") {
				alert("請選擇「商品」");
			} else if (goodsPrice == null || goodsPrice == "") {
				alert("請輸入「商品價格」");
			} else if (goodsQuantity == null || goodsQuantity == "") {
				alert("請輸入「補貨數量」");
			} else if (statusID == null || statusID == "") {
				alert("尚未選擇「是否上架」");
			} else {
				document.updateGoodsForm.submit();
			}

		}

		function goodSelected() {
			document.updateGoodsForm.action.value = "backEndGoodsReplenishmentView";
			document.updateGoodsForm.submit();
		}
	</script>
</head>
<body>

	<%@ include file="VM_BackEnd_FunMenu.jsp" %>
		
	<h2>商品維護作業</h2><br/>
	<div style="margin-left:25px;">
	<p style="color:blue;">${sessionScope.modifyMsg}</p>
	<% session.removeAttribute("modifyMsg"); %>
	<form name="updateGoodsForm" action="BackEndAction.do" method="post">
		<input type="hidden" name="action" value="updateGoods"/>
		<p>
			飲料名稱：
			 <select name="goodsID" id="goodsID" onchange="goodSelected()">
			 	<option value="">---請選擇---</option>
			 	<c:forEach items="${goods}" var="good">
					<option <c:if test="${good.goodsID eq modifyGood.goodsID}">selected</c:if> value="${good.goodsID}">
						${good.goodsName}
					</option>
				</c:forEach>
			</select>
		</p>		
		<p>
			更改價格： 
			<input type="number" id="priceID" name="goodsPrice" size="5" value="${modifyGood.goodsPrice}" min="0" max="1000">
		</p>
		<p>
			補貨數量：
			<input type="number" id="quantityID" name="goodsQuantity" size="5" value="0" min="0" max="1000">
			(庫存:${modifyGood.goodsQuantity}件)
		</p>
		<p>
			商品狀態：
			<select name="status" id="statusID">
				<option value="1">上架</option>
				<option value="0">下架</option>				
			</select>
		</p>
		<p>
			<input type="button" value="送出" onclick="submitFunction()"/>
		</p>
	</form>
	</div>
</body>
</html>