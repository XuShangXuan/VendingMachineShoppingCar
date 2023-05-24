package com.gjun.VendingMachine.action;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.gjun.VendingMachine.model.Member;
import com.gjun.VendingMachine.service.FrontEndService;
import com.gjun.VendingMachine.vo.ConsumerDetails;
import com.gjun.VendingMachine.vo.SearchCondition;
import com.gjun.VendingMachine.vo.ShoppingCartGoodsInfo;

import net.sf.json.JSONObject;

public class FrontEndAction extends DispatchAction {

	private FrontEndService frontEndService = FrontEndService.getInstance();
	
	public ActionForward frontEndView(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		return mapping.findForward("VendingMachine");
	}

	public ActionForward queryGoodsBySearchCondition(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		String currentPage = req.getParameter("currentPage");
		
		if (currentPage == null || "".equals(currentPage)) {
			currentPage = "1";
		}
		
		SearchCondition searchCondition = new SearchCondition();
		searchCondition.setKeyword(req.getParameter("searchKeyword"));
		
		searchCondition = frontEndService.queryGoodsBySearchCondition(currentPage, searchCondition);
		
		req.setAttribute("searchCondition", searchCondition);

		return mapping.findForward("VendingMachine");

	}
	
	public ActionForward buyGoods(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		HttpSession session = req.getSession();
		
		Map<String, Integer> carGoods = (Map<String, Integer>) session.getAttribute("carGoods");
		
		//沒有加入任何商品就做購買
		if (carGoods == null) {
			return mapping.findForward("VendingMachineView");
		}
		
		Member member= (Member)session.getAttribute("member");
		String customerID = member.getIdentificationNo();
		int inputMoney = Integer.parseInt(req.getParameter("inputMoney"));
		
		ConsumerDetails consumerDetails = frontEndService.buyGoods(customerID, carGoods, inputMoney);
		
		session.setAttribute("consumerDetails", consumerDetails);
		
		//如果成功購買就清空購物車
		if (consumerDetails.getGiveChange() != inputMoney) {
			session.removeAttribute("carGoods");
		}

		return mapping.findForward("VendingMachineView");

	}
	
	// 商品加入購物車
    public ActionForward addCartGoods(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse res) throws IOException  {		
    	
		HttpSession session = req.getSession();

		// 商品加入購物車
		String goodsID = req.getParameter("goodsID");
		int buyQuantity = Integer.parseInt(req.getParameter("buyQuantity"));
		
		Map<String, Integer> carGoods = (Map<String, Integer>) session.getAttribute("carGoods");
		if (carGoods == null) {
			carGoods = new LinkedHashMap();
			carGoods.put(goodsID, buyQuantity);
		} else {
			boolean found = false;
			for (Map.Entry<String, Integer> carGood : carGoods.entrySet()) {
				if (carGood.getKey().equals(goodsID)) {
					carGood.setValue(carGood.getValue() + buyQuantity);
					found = true;
		            break;
				}
			}
			if (!found) {
				carGoods.put(goodsID, buyQuantity);
		    }
		}
		
		session.setAttribute("carGoods", carGoods);
		
		JSONObject result = new JSONObject();
		result.put("carGoods", carGoods);
		result.put("cartSize", carGoods.size());
		
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().write(result.toString());

		return null;
	}
    
    // 查詢購物車商品請求導結帳(查詢資料庫最新商品價格)
 	public ActionForward queryCartGoods(ActionMapping mapping, ActionForm form, HttpServletRequest req,
 			HttpServletResponse res) throws IOException {
 		
 		HttpSession session = req.getSession();
 		Map<String, Integer> carGoods = (Map<String, Integer>) session.getAttribute("carGoods");
 		
 		if(carGoods!=null) {
 			ShoppingCartGoodsInfo cartGoodsInfo = frontEndService.getShoppingCartGoodsInfo(carGoods);
 			session.setAttribute("cartGoodsInfo", cartGoodsInfo);
 		}

 		return mapping.findForward("VendingMachineView");
 	}
 	
 	public ActionForward clearCartGoods(ActionMapping mapping, ActionForm form, HttpServletRequest req,
 			HttpServletResponse res) throws Exception {
 		
 		// 清空購物車
 		HttpSession session = req.getSession();
 		session.removeAttribute("carGoods");

 		return null;
 	}

}
