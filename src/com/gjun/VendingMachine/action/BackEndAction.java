package com.gjun.VendingMachine.action;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.gjun.VendingMachine.actionForm.GoodsForm;
import com.gjun.VendingMachine.model.Goods;
import com.gjun.VendingMachine.model.SalesReport;
import com.gjun.VendingMachine.service.BackEndService;
import com.gjun.VendingMachine.vo.Page;

public class BackEndAction extends DispatchAction {

	private BackEndService backEndService = BackEndService.getInstance();

	public ActionForward queryAllGoods(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		List<Goods> goods = backEndService.queryAllGoods();
		req.setAttribute("goods", goods);
		
//		System.out.println("商品總數:" + goods.size());
//		goods.stream().forEach(g -> System.out.println(g));

		return mapping.findForward("BackEnd_GoodsListView");

	}
	
	public ActionForward queryGoodsByPage(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		int currentPage = Integer.parseInt(req.getParameter("currentPage"));
		
		//原本只有搜尋頁數
		//Page pageBean = backEndService.queryGoodsByPage(currentPage);
		Page pageBean = null;
		req.setAttribute("pageBean", pageBean);

		return mapping.findForward("BackEnd_GoodsListView");

	}

	public ActionForward backEndGoodsCreateView(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		return mapping.findForward("BackEnd_GoodsCreateView");
	}

	public ActionForward addGoods(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		String goodsImgPath = getServlet().getInitParameter("GoodsImgPath");
		String serverGoodsImgPath = getServlet().getServletContext().getRealPath(goodsImgPath);

		GoodsForm formDataGoods = (GoodsForm) form;

		Goods good = new Goods();
		BeanUtils.copyProperties(good, formDataGoods);

		FileOutputStream fileOutput = new FileOutputStream(
				serverGoodsImgPath + "\\" + formDataGoods.getGoodsImageName().getFileName());

		fileOutput.write(formDataGoods.getGoodsImageName().getFileData());
		fileOutput.flush();
		fileOutput.close();
		formDataGoods.getGoodsImageName().destroy();

		HttpSession session = req.getSession();
		int createResult = backEndService.createGoods(good);
		String message = (createResult != 0) ? "商品資料新增成功！ 新增的商品編號為:"+ createResult : "商品資料新增失敗！";
		session.setAttribute("createMsg", message);

//		if (createResult != 0) {
//
//			System.out.println("商品資料新增成功！");
//			System.out.println("新增的商品編號為:" + createResult);
//
//		} else {
//			System.out.println("商品資料新增失敗！");
//		}

		return mapping.findForward("BackEnd_GoodsCreate");
	}

	public ActionForward backEndGoodsReplenishmentView(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		List<Goods> goods = backEndService.queryAllGoods();
		req.setAttribute("goods", goods);
		
		// 被選擇要修改的帳號資料
		String id = req.getParameter("goodsID");
		id = (id != null) ? id : (String)req.getSession().getAttribute("modifyGoodsID");
		
		if (id != null) {
			Goods good = backEndService.queryGoodsByID(id);
			req.setAttribute("modifyGood", good);
		}
		
		return mapping.findForward("BackEnd_GoodsReplenishmentView");
	}

	public ActionForward updateGoods(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		HttpSession session = req.getSession();
		
		GoodsForm formDataGoods = (GoodsForm) form;
		Goods good = new Goods();
		BeanUtils.copyProperties(good, formDataGoods);

		boolean modifyResult = backEndService.modifyGoods(good);
		String message = modifyResult ? "商品資料修改成功！" : "商品資料修改失敗！";
		
		session.setAttribute("modifyMsg", message);
		session.setAttribute("modifyGoodsID", good.getGoodsID());

		return mapping.findForward("BackEnd_GoodsReplenishment");
	}
	
	/*
	public ActionForward backEndGoodsSaleReporView(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		return mapping.findForward("BackEnd_GoodsSaleReporView");
	}
	*/

	public ActionForward querySalesReport(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		String startDate = req.getParameter("queryStartDate");
		String endDate = req.getParameter("queryEndDate");
		
		if (startDate == null && endDate == null) {
			return mapping.findForward("BackEnd_GoodsSaleRepor");
		}

		Set<SalesReport> reports = backEndService.querySalesReport(startDate, endDate);
		req.setAttribute("reports", reports);
		//reports.stream().forEach(r -> System.out.println(r));

		return mapping.findForward("BackEnd_GoodsSaleRepor");
	}

}
