package com.gjun.VendingMachine.vo;

import java.util.List;

import com.gjun.VendingMachine.model.Goods;

public class SearchCondition {
	
	private List<Goods> goods;
	private int goodsTotalCounts;
	private Page page;
	private String goodsID;
	private String keyword;
	private String minPrice;
	private String maxPrice;
	private String sortByPrice;
	private String stock;
	private String status;
	
	public List<Goods> getGoods() {
		return goods;
	}

	public void setGoods(List<Goods> goods) {
		this.goods = goods;
	}
	
	public int getGoodsTotalCounts() {
		return goodsTotalCounts;
	}

	public void setGoodsTotalCounts(int goodsCounts) {
		this.goodsTotalCounts = goodsCounts;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getSortByPrice() {
		return sortByPrice;
	}

	public void setSortByPrice(String sortByPrice) {
		this.sortByPrice = sortByPrice;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SearchCondition() {
		
	}
	
	public SearchCondition(Page page) {
		this.page = page;
	}
	public SearchCondition(Page page, String keyword) {
		this.page = page;
		this.keyword = keyword;
	}
	
}
