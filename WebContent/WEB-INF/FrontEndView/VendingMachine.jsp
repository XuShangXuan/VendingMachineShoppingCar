<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- http:localhost:8085/VendingMachineShoppingCar/Login.jsp -->
<title>販賣機ShoppingCar</title>
	<style type="text/css">
		.page {
			display:inline-block;
			padding-left: 10px;
		}
	</style>
	<script type="text/javascript">
	
		function addCartGoods(goodsID, buyQuantityIdx){
			console.log("goodsID:", goodsID);			
			var buyQuantity = document.getElementsByName("buyQuantity")[buyQuantityIdx].value;
			console.log("buyQuantity:", buyQuantity);
			const formData = new FormData();
			formData.append('action', 'addCartGoods');
			formData.append('goodsID', goodsID);
			formData.append('buyQuantity', buyQuantity);
			// 送出商品加入購物車請求
			const request = new XMLHttpRequest();
			// 第三個參數是代表非同步
			request.open("POST", "FrontEndAction.do", true);
			request.send(formData);
			// 接回XMLHttpRequest非同步請求回傳
			request.onreadystatechange = function() {
		        if (this.readyState == 4 && this.status == 200) {
		        	const result = JSON.parse(this.responseText);
		        	const cartSize = result.cartSize;
		            document.getElementById("CartGoodsSize").value = "購物車(" + cartSize + ")";
		            document.getElementsByName("buyQuantity")[buyQuantityIdx].value = 0;
		            alert("已加入購物車");
		      };
		   }
		}
		
		function clearCartGoods(){
			const formData = new FormData();
			formData.append('action', 'clearCartGoods');
			// 送出清空購物車商品請求
			const request = new XMLHttpRequest();
			request.open("POST", "FrontEndAction.do");
			request.send(formData);
			document.getElementById("CartGoodsSize").value = "購物車(0)";
			alert("已清除購物車");
		}
		
	</script>
</head>

<body align="center">
<table width="1000" height="400" align="center">
	<tr>
		<td colspan="2" align="right">
			<form style="display:inline-block" action="FrontEndAction.do" method="get">
				<input type="hidden" name="action" value="queryCartGoods"/>
				<input type="submit" id="CartGoodsSize" value="購物車(${fn:length(carGoods)})"/>
			</form>
			<button onclick="clearCartGoods()">清空購物車</button>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="right">
			<form action="FrontEndAction.do" method="get">
				<input type="hidden" name="action" value="queryGoodsBySearchCondition"/>
				<input type="hidden" name="currentPage" value="1"/>
				<input type="text" name="searchKeyword" value="${searchCondition.keyword}" placeholder="搜尋商品"/>
				<input type="submit" value="商品查詢"/>
			</form>
		</td>
	</tr>
	<tr>
		<td width="400" height="200" align="center">		
			<img border="0" src="DrinksImage/coffee.jpg" width="200" height="200" >			
			<h1>歡迎光臨，${sessionScope.member.customerName}！</h1>		
			<a href="BackEndActionSearchGoods.do?goodsID=&keyword=&minPrice=&maxPrice=&sortByPrice=&stock=&status=&action=queryGoodsBySearchCondition&currentPage=1" align="left" >後臺頁面</a>&nbsp; &nbsp;
			<a href="LoginAction.do?action=logout" align="left">登出</a>
			<br/><br/>
			<form action="FrontEndAction.do" method="post">
			<!-- <input type="hidden" name="customerID" value="A124243295"/> -->
				<font face="微軟正黑體" size="4" >
					<b>投入:</b>
					<input type="number" name="inputMoney" max="100000" min="0"  size="5" value="0">
					<input type="hidden" name="action" value="buyGoods"/>
					<b>元</b>		
					<b><input type="submit" value="送出">					
					<br/><br/>
				</font>
			</form>
			<c:if test="${not empty consumerDetails}">
				<div style="border-width:3px;border-style:dashed;border-color:#FFAC55;
						padding:5px;width:300px;">
						<p style="color: blue;">~~~~~~~ 消費明細 ~~~~~~~</p>
						<p style="margin: 10px;">
							投入金額：${consumerDetails.inputAmount}
						</p>
						<p style="margin: 10px;">
							購買金額：${consumerDetails.purchasingPrice}
						</p>
						<p style="margin: 10px;">
							找零金額：${consumerDetails.giveChange}
						</p>
						<c:forEach items="${consumerDetails.goods}" var="good">
							<p style="margin: 10px;">
								${good.goodsName} ${good.goodsPrice} * ${good.goodsQuantity}
							</p>
						</c:forEach>
				</div>
			</c:if>
			<c:if test="${not empty cartGoodsInfo}">
				<div style="border-width:3px;border-style:dashed;border-color:#FFAC55;
						padding:5px;width:300px;">
						<p style="color: blue;">~~~~~~~ 購物車清單 ~~~~~~~</p>
						<p style="margin: 10px;">
							購買總額：${cartGoodsInfo.totalAmount}
						</p>
						<c:forEach items="${cartGoodsInfo.shoppingCartGoods}" var="good">
							<p style="margin: 10px;">
								${good.goodsName} ${good.goodsPrice} * ${good.buyQuantity}
							</p>
						</c:forEach>
				</div>
			</c:if>
		</td>
		<td width="600" height="400">
			<!-- <input type="hidden" name="action" value="buyGoods"/> -->
			<table border="1" style="border-collapse: collapse">
				<c:forEach items="${searchCondition.goods}" var="good" varStatus="status">
					<c:if test="${status.first}">
						<tr>
					</c:if>
					<c:if test="${status.count eq 4}">
						</tr>
						<tr>
					</c:if>
					<td width="300">							
						<font face="微軟正黑體" size="5" >
							${good.goodsName} 
						</font>
						<br/>
						<font face="微軟正黑體" size="4" style="color: gray;" >
							${good.goodsPrice} 元/罐
						</font>
						<br/>
						<!-- 各商品圖片 -->
						<img border="0" src="DrinksImage/${good.goodsImageName}" width="150" height="150" >						
						<br/>
						<font face="微軟正黑體" size="3">
							<input type="hidden" name="goodsID" value="${good.goodsID}">
							<!-- 設定最多不能買大於庫存數量 -->
							購買<input type="number" name="buyQuantity" min="0" max="${good.goodsQuantity}" size="5" value="0">罐
							<br/><br/>
							<button onclick="addCartGoods(${good.goodsID},${status.index})">加入購物車</button>
							<!-- 顯示庫存數量 -->
							<br/>
							<p style="color: red;">(庫存 ${good.goodsQuantity} 罐)</p>
						</font>
					</td>
					<c:if test="${status.last}">
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</td>		
	</tr>
	<tr>
		<td colspan="2" align="right">
			<c:if test="${searchCondition.page.currentPage != 1}">
				<h3 class="page">
					<a href="FrontEndAction.do?action=queryGoodsBySearchCondition&currentPage=${searchCondition.page.currentPage-1}&searchKeyword=${searchCondition.keyword}"> 上一頁 </a>
				</h3>
			</c:if>
			<c:forEach begin="${searchCondition.page.startPage}" end="${searchCondition.page.endPage}" var="pageNum">
				<c:if test="${searchCondition.page.currentPage != pageNum}">
					<h3 class="page">
						<a href="FrontEndAction.do?action=queryGoodsBySearchCondition&currentPage=${pageNum}&searchKeyword=${searchCondition.keyword}"> ${pageNum} </a>
					</h3>
				</c:if>
				<c:if test="${searchCondition.page.currentPage == pageNum}">
					<h3 class="page">
						<a style="color:red" href="FrontEndAction.do?action=queryGoodsBySearchCondition&currentPage=${pageNum}&searchKeyword=${searchCondition.keyword}"> ${pageNum} </a>
					</h3>
				</c:if>
			</c:forEach>
			<c:if test="${searchCondition.page.currentPage != searchCondition.page.pageTotalCount}">
				<h3 class="page" >
					<a href="FrontEndAction.do?action=queryGoodsBySearchCondition&currentPage=${searchCondition.page.currentPage+1}&searchKeyword=${searchCondition.keyword}"> 下一頁 </a>
				</h3>
			</c:if>
		</td>
		</tr>
</table>
<%
	session.removeAttribute("consumerDetails");
	session.removeAttribute("cartGoodsInfo");
%>
</body>
</html>