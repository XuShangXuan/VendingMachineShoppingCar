package com.gjun.VendingMachine.vo;

public class SearchCondition {
	
	private Page page;
	private String keyword;
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public SearchCondition(Page page) {
		this.page = page;
	}
	public SearchCondition(Page page, String keyword) {
		this.page = page;
		this.keyword = keyword;
	}
	
}
