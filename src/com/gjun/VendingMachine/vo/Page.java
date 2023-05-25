package com.gjun.VendingMachine.vo;

public class Page {
	
	private int currentPage;
	
	private int pageTotalCount;
	
	private int startPage;
	
	private int endPage;

	private int showPageCount;
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageTotalCount() {
		return pageTotalCount;
	}

	public void setPageTotalCount(int pageTotalCount) {
		this.pageTotalCount = pageTotalCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	public int getShowPageCount() {
		return showPageCount;
	}

	public void setShowPageCount(int showPageCount) {
		this.showPageCount = showPageCount;
	}
	
	public Page(int currentPage, int pageTotalCount, int showPageCount) {
		this.showPageCount = showPageCount;
		this.currentPage = currentPage;
		this.pageTotalCount = pageTotalCount;
		calculateStartAndEndPage(showPageCount, currentPage, pageTotalCount);
	}

	private void calculateStartAndEndPage(int showPageCount, int currentPage, int pageTotalCount) {
		
		// 一頁最多顯示n個分頁，如果資料數少於n頁，則show出所有分頁
		if (pageTotalCount >= showPageCount) {

			int half = showPageCount / 2; // 取得範圍中心點前後數量

			if (currentPage <= half) { // 當前頁面在前一半
				this.startPage = 1;
				this.endPage = showPageCount;
			} else if (currentPage >= pageTotalCount - half + 1) { // 當前頁面在後一半
				this.startPage = pageTotalCount - showPageCount + 1;
				this.endPage = pageTotalCount;
			} else { // 當前頁面在中間
				this.startPage = currentPage - half;
				this.endPage = currentPage + half;
			}
		} else {
			this.startPage = 1;
			this.endPage = pageTotalCount;
		}

	}

}
