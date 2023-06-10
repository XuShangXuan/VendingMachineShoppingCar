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
				  	$("#priceID").val(goodsInfo.goodsPrice);
				  	$("#stockID").html(goodsInfo.goodsQuantity);
				  	$("#statusID").val(goodsInfo.status);
				  },
				  error: function(error) { // 請求發生錯誤時執行函式
				  	alert("Ajax Error!");
				  }
				});
			}else{
			  	$("#priceID").val('');
			  	$("#stockID").html('');
			}
		});
		
	});
	
	//=================================================================================
	
		function submitFunction() {
			
			const goodsID =$("#goodsID").val();
			const goodsPrice = $("#priceID").val();
			const goodsQuantity = $("#quantityID").val();
			const statusID = $("#statusID").val();
			
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
	</script>
</head>
<body>

	<%@ include file="VM_BackEnd_FunMenu.jsp" %>
	
	<div class="container">
		<h2>商品維護作業</h2><br/>
		<div style="margin-left:25px;">
		<p style="color:blue;">${sessionScope.modifyMsg}</p>
		<form name="updateGoodsForm" action="BackEndAction.do" method="post">
			<input type="hidden" name="action" value="updateGoods"/>
			<p>
				飲料名稱：
				 <select id="goodsID" name="goodsID">
				 	<option value="">---請選擇---</option>
				 	<c:forEach items="${goods}" var="good">
						<option <c:if test="${good.goodsID eq modifyGoods.goodsID}">selected</c:if> value="${good.goodsID}">
							${good.goodsName}
						</option>
					</c:forEach>
				</select>
			</p>		
			<p>
				更改價格： 
				<input type="number" id="priceID" name="goodsPrice" size="5" value="${modifyGoods.goodsPrice}" min="0" max="1000">
			</p>
			<p>
				補貨數量：
				<input type="number" id="quantityID" name="goodsQuantity" size="5" value="0" min="0" max="1000">
				(庫存:<a id="stockID">${modifyGoods.goodsQuantity}</a>件)
			</p>
			<p>
				商品狀態：
				<select name="status" id="statusID">
					<option <c:if test="${modifyGoods.status eq 1}">selected</c:if> value="1">上架</option>
					<option <c:if test="${modifyGoods.status eq 0}">selected</c:if> value="0">下架</option>				
				</select>
			</p>
			<p>
				<input type="button" value="送出" onclick="submitFunction()"/>
			</p>
		</form>
		<%
			session.removeAttribute("modifyMsg");
			session.removeAttribute("modifyGoodsID");
		%>
		</div>
	</div>
</body>
</html>