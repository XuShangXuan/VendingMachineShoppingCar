package com.gjun.VendingMachine.vo;

import java.util.List;

import com.gjun.VendingMachine.model.Goods;

public class ConsumerDetails {

	private int inputAmount;
	private int purchasingPrice;
	private int giveChange;
	private List<Goods> goods;

	public int getInputAmount() {
		return inputAmount;
	}

	public void setInputAmount(int inputAmount) {
		this.inputAmount = inputAmount;
	}

	public int getPurchasingPrice() {
		return purchasingPrice;
	}

	public void setPurchasingPrice(int purchasingPrice) {
		this.purchasingPrice = purchasingPrice;
	}

	public int getGiveChange() {
		return giveChange;
	}

	public void setGiveChange(int giveChange) {
		this.giveChange = giveChange;
	}

	public List<Goods> getGoods() {
		return goods;
	}

	public void setGoods(List<Goods> goods) {
		this.goods = goods;
	}

	public void setVariousPrices(int inputAmount, int purchasingPrice, int giveChange) {

		this.inputAmount = inputAmount;
		this.purchasingPrice = purchasingPrice;
		this.giveChange = giveChange;

	}

//	@Override
//	public String toString() {
//
//		String consumerDetails = "投入金額:" + inputAmount + "\n購買金額:" + purchasingPrice + "\n找零金額:" + giveChange;
//		for (Goods good : goods) {
//			if (good.getGoodsQuantity() != 0) {
//				consumerDetails += "\n商品名稱:" + good.getGoodsName() + " 商品金額:" + good.getGoodsPrice() + " 購買數量:"
//						+ good.getGoodsQuantity();
//			}
//		}
//
//		return consumerDetails;
//	}

}
