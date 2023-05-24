<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<h1>Vending Machine BackEnd Service</h1><br/>		
	<table border="1" style="border-collapse:collapse;margin-left:25px;">
		<tr>
			<td width="200" height="50" align="center">
				<a href="BackEndActionSearchGoods.do?goodsID=&keyword=&minPrice=&maxPrice=&sortByPrice=&stock=&status=&action=queryGoodsBySearchCondition&currentPage=1">商品列表</a>
			</td>
			<td width="200" height="50" align="center">
				<a href="BackEndAction.do?action=backEndGoodsReplenishmentView">商品維護作業</a>
			</td>
			<td width="200" height="50" align="center">
				<a href="BackEndAction.do?action=backEndGoodsCreateView">商品新增上架</a>
			</td>
			<td width="200" height="50" align="center">
				<a href="BackEndAction.do?action=querySalesReport">銷售報表</a>
			</td>
			<td width="200" height="50" align="center">
				<a href="FrontEndAction.do?action=searchGoods">前台頁面</a>
			</td>
		</tr>
	</table>
	<br/><br/><HR/>