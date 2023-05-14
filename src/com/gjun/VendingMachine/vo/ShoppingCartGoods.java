package com.gjun.VendingMachine.vo;

import java.util.Objects;

public class ShoppingCartGoods {

	private String goodsID;
	private String goodsName;
	private int goodsPrice;
	private int buyQuantity;
	
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public int getBuyQuantity() {
		return buyQuantity;
	}
	public void setBuyQuantity(int buyQuantity) {
		this.buyQuantity = buyQuantity;
	}
	@Override
	public int hashCode() {
		return Objects.hash(goodsID);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShoppingCartGoods other = (ShoppingCartGoods) obj;
		return Objects.equals(goodsID, other.goodsID);
	}
	
}
