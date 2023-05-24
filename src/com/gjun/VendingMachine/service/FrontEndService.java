package com.gjun.VendingMachine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gjun.VendingMachine.dao.FrontEndDao;
import com.gjun.VendingMachine.model.Goods;
import com.gjun.VendingMachine.vo.ConsumerDetails;
import com.gjun.VendingMachine.vo.Page;
import com.gjun.VendingMachine.vo.SearchCondition;
import com.gjun.VendingMachine.vo.ShoppingCartGoods;
import com.gjun.VendingMachine.vo.ShoppingCartGoodsInfo;

public class FrontEndService {

	private static FrontEndService frontEndService = new FrontEndService();
	private FrontEndDao frontEndDao = FrontEndDao.getInstance();

	private FrontEndService() {
	}

	public static FrontEndService getInstance() {
		return frontEndService;
	}
	
	public SearchCondition queryGoodsBySearchCondition(String currentPage, SearchCondition searchCondition) {

		int pageNo = Integer.parseInt(currentPage);

		// 資料總筆數
		int dataTotalCount = frontEndDao.getGoodsCountBySearchCondition(searchCondition);

		// 至少要顯示一頁
		if (dataTotalCount == 0) {
			dataTotalCount = 1;
		}

		// 一頁顯示多少個數據
		int showDataCount = 6;

		// 計算總頁數
		int pageTotalCount = dataTotalCount % showDataCount == 0 ? (dataTotalCount / showDataCount)
				: (dataTotalCount / showDataCount) + 1;

		// 一頁最多顯示多少分頁
		int showPageCount = 3;

		// 計算查詢範圍
		int endRowNo = showDataCount * pageNo;
		int startRowNo = endRowNo - showDataCount + 1;

		Page page = new Page(pageNo, pageTotalCount, showPageCount);
		searchCondition.setPage(page);

		List<Goods> goods = frontEndDao.queryGoodsBySearchCondition(startRowNo, endRowNo, searchCondition);
		searchCondition.setGoods(goods);

		return searchCondition;
	}
	
	public ConsumerDetails buyGoods(String customerID, Map<String, Integer> carGoods, int inputMoney) {

		Map<Goods, Integer> goodsOrders = new HashMap();
		Set<Goods> buyGoods = new HashSet();
		List<Goods> goods = new ArrayList<>();

		ConsumerDetails consumerDetails = new ConsumerDetails();

		int purchasingPrice = 0;
		int realPurchasingPrice = 0;
		int purchaseQuantity = 0;

		// 將所有商品全撈出來
		for (Map.Entry<String, Integer> carGood : carGoods.entrySet()) {

			// 從DB中取得商品資訊
			Goods good = frontEndDao.queryGoodsByID(carGood.getKey());

			// 加總所購買的商品金額*數量
			purchasingPrice += good.getGoodsPrice() * carGood.getValue();

			// 購買的數量不得超過資料庫中的庫存數量
			purchaseQuantity = carGood.getValue();
			if (good.getGoodsQuantity() < purchaseQuantity) {
				purchaseQuantity = good.getGoodsQuantity();
			}

			// 計算實際顧客真正能購買的數量*金額
			realPurchasingPrice += good.getGoodsPrice() * purchaseQuantity;

			// 如果真正能購買的數量為0,就不建立該商品的訂單
			if (purchaseQuantity != 0) {
				goodsOrders.put(good, purchaseQuantity);
			}

			// 計算被顧客購買後，剩餘的商品庫存，並加入待更新的商品
			good.setGoodsQuantity(good.getGoodsQuantity() - purchaseQuantity);
			buyGoods.add(good);

			// 建立消費明細(商品資訊)
			Goods detail = new Goods();
			
			// 如果真正能購買的數量為0,該商品不加入訂單明細
			if (purchaseQuantity != 0) {
				detail.setGoodsName(good.getGoodsName());
				detail.setGoodsPrice(good.getGoodsPrice());
				detail.setGoodsQuantity(purchaseQuantity);

				goods.add(detail);
				consumerDetails.setGoods(goods);
			}

		}

		int giveChange = 0;

		// 顧客所投入的金額必須「超過」畫面所操作的商品總金額
		if (inputMoney >= purchasingPrice) {

			// 計算要找客人多少錢
			giveChange = inputMoney - realPurchasingPrice;

			// 建立DB的訂單明細
			boolean insertSuccess = frontEndDao.batchCreateGoodsOrder(customerID, goodsOrders);
			if (!insertSuccess) {

				System.out.println("建立訂單失敗!");

				consumerDetails.setVariousPrices(inputMoney, realPurchasingPrice, inputMoney);
				// consumerDetails.getGoods().stream().forEach(g -> g.setGoodsQuantity(0));
				consumerDetails.setGoods(null);

				return consumerDetails;
			}

			// 更新DB的商品庫存
			boolean updateSuccess = frontEndDao.batchUpdateGoodsQuantity(buyGoods);
			if (!updateSuccess) {

				System.out.println("商品庫存更新失敗!");

				consumerDetails.setVariousPrices(inputMoney, realPurchasingPrice, inputMoney);
				// consumerDetails.getGoods().stream().forEach(g -> g.setGoodsQuantity(0));
				consumerDetails.setGoods(null);

				return consumerDetails;
			}

			// 建立消費明細(金額)
			consumerDetails.setVariousPrices(inputMoney, realPurchasingPrice, giveChange);
			return consumerDetails;

		}

		// 顧客所投入的金額「未達」畫面所操作的商品總金額
		// 建立消費明細(金額)
		consumerDetails.setVariousPrices(inputMoney, realPurchasingPrice, inputMoney);
		// 如顧客所投入的金額「未達」畫面所操作的商品總金額,則不購買商品
		consumerDetails.setGoods(null);

		return consumerDetails;

	}
	
	public ShoppingCartGoodsInfo getShoppingCartGoodsInfo(Map<String, Integer> carGoods) {
		
		Set<ShoppingCartGoods> set = new HashSet();
		int totalAmount = 0;
		
		//查詢資料庫的商品資訊並計算總金額
		for (Map.Entry<String, Integer> carGood : carGoods.entrySet()) {
			ShoppingCartGoods shoppingCartGoods = frontEndDao.queryShoppingCartGoodsByID(carGood.getKey());
			shoppingCartGoods.setBuyQuantity(carGood.getValue());
			set.add(shoppingCartGoods);
			totalAmount += shoppingCartGoods.getGoodsPrice() * carGood.getValue();
		}

		ShoppingCartGoodsInfo cartGoodsInfo = new ShoppingCartGoodsInfo(totalAmount,set);
		
		return cartGoodsInfo;
	}
	
}
