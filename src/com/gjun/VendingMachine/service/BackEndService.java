package com.gjun.VendingMachine.service;

import java.util.List;
import java.util.Set;

import com.gjun.VendingMachine.dao.BackEndDao;
import com.gjun.VendingMachine.model.Goods;
import com.gjun.VendingMachine.model.SalesReport;
import com.gjun.VendingMachine.vo.Page;
import com.gjun.VendingMachine.vo.SearchCondition;

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
	
	public SearchCondition queryGoodsBySearchCondition(String showPageCount, String currentPage,
			SearchCondition searchCondition) {
		
		int pageNo = Integer.parseInt(currentPage);
		
		// 資料總筆數
		int dataTotalCount = backEndDao.getGoodsCountBySearchCondition(searchCondition);
		
		searchCondition.setGoodsTotalCounts(dataTotalCount);
		
		//至少要顯示一頁
		if(dataTotalCount == 0) {
			dataTotalCount = 1;
		}
		
		// 一頁顯示多少個數據
		int showDataCount = 5;
		
		// 計算總頁數
		int pageTotalCount = dataTotalCount % showDataCount == 0 ? (dataTotalCount / showDataCount) : (dataTotalCount / showDataCount) + 1;
		
		// 計算查詢範圍
		int endRowNo = showDataCount * pageNo;
		int startRowNo = endRowNo - showDataCount + 1;
		
		Page page = new Page(pageNo, pageTotalCount, Integer.parseInt(showPageCount));
		searchCondition.setPage(page);
		
		List<Goods> goods = backEndDao.queryGoodsBySearchCondition(startRowNo, endRowNo, searchCondition);
		searchCondition.setGoods(goods);
		
		return searchCondition;
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
