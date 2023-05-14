package com.gjun.VendingMachine.service;

import java.util.List;
import java.util.Set;

import com.gjun.VendingMachine.dao.BackEndDao;
import com.gjun.VendingMachine.model.Goods;
import com.gjun.VendingMachine.model.SalesReport;
import com.gjun.VendingMachine.vo.Page;

public class BackEndService {

	private static BackEndService bankendService = new BackEndService();
	private BackEndDao backEndDao = BackEndDao.getInstance();

	private BackEndService() {
	}

	public static BackEndService getInstance() {
		return bankendService;
	}

	public List<Goods> queryAllGoods() {
		return backEndDao.queryAllGoods();
	}
	
	public Page queryGoodsByPage(int currentPage) {
		
		// 資料總筆數
		int dataTotalCount = backEndDao.getAllGoodsCount();
		
		// 一頁顯示多少個數據
		int showDataCount = 10;
		
		// 計算總頁數
		int pageTotalCount = dataTotalCount % showDataCount == 0 ? (dataTotalCount / showDataCount) : (dataTotalCount / showDataCount) + 1;
		
		// 一頁最多顯示多少分頁
		int showPageCount = 3;
		
		// 計算查詢範圍
		int endRowNo = showDataCount * currentPage;
		int startRowNo = endRowNo - showDataCount + 1;
		
		// 呼叫dao層獲取分頁後的資料
		List<Goods> goods = backEndDao.queryGoodsByPage(startRowNo, endRowNo);
		
		return new Page(currentPage, pageTotalCount, goods, showPageCount);
	}
	
	public List<Goods> queryGoods() {
		return backEndDao.queryAllGoods();
	}

	public int createGoods(Goods goods) {
		return backEndDao.createGoods(goods);
	}

	public boolean modifyGoods(Goods goods) {
		return backEndDao.updateGoods(goods);
	}

	public Set<SalesReport> querySalesReport(String startDate, String endDate) {
		return backEndDao.queryOrderBetweenDate(startDate, endDate);
	}

	public Goods queryGoodsByID(String id) {
		
		return backEndDao.queryGoodsByID(id);
	}

}
