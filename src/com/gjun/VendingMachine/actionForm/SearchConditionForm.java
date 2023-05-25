package com.gjun.VendingMachine.actionForm;

import org.apache.struts.action.ActionForm;

import com.gjun.VendingMachine.vo.Page;

public class SearchConditionForm extends ActionForm {
	
	private Page page;
	private String goodsID;
	private String keyword;
	private String minPrice;
	private String maxPrice;
	private String sortByPrice;
	private String stock;
	private String status;
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
	
}
