package com.gjun.VendingMachine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.gjun.VendingMachine.model.Goods;
import com.gjun.VendingMachine.model.SalesReport;
import com.gjun.VendingMachine.vo.Page;
import com.gjun.VendingMachine.vo.SearchCondition;

public class BackEndDao {

	private static BackEndDao backEndDao = new BackEndDao();
	
	private BackEndDao() {}
	
	public static BackEndDao getInstance(){
		return backEndDao;
	}
	
	public Goods queryGoodsByID(String goodID) {

		Goods good = new Goods();

		String querySQL = "SELECT GOODS_ID, GOODS_NAME, PRICE,  QUANTITY, IMAGE_NAME, STATUS "
				+ " FROM BEVERAGE_GOODS "
				+ " WHERE GOODS_ID = ?";

		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); // 連接資料庫
				PreparedStatement stmt = conn.prepareStatement(querySQL)) { // PreparedStatement可放入能動態修改參數的SQL語句

			stmt.setString(1, goodID); // 動態寫入SQL的參數 stmt.set()第一個參數放數字，代表SQL「?」出現的位置，第二個放要傳入的參數

			try (ResultSet rs = stmt.executeQuery()) { // 執行SQL語句，並回傳資料結果集(Set集合)

				// 取回資料結果，並存入對應的自定義物件
				while (rs.next()) { // 只要有資料就繼續執行

					//依照對應的欄位及資料型態取值
					good.setGoodsID(rs.getString("GOODS_ID")); //rs.getXXX(放查詢的欄位)
					good.setGoodsName(rs.getString("GOODS_NAME"));
					good.setGoodsPrice(rs.getInt("PRICE"));
					good.setGoodsQuantity(rs.getInt("QUANTITY"));
					good.setGoodsImageName(rs.getString("IMAGE_NAME"));
					good.setStatus(rs.getString("STATUS"));

				}
			} catch (SQLException e) {
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return good;

	}
	
	public List<Goods> queryAllGoods(){
		
		List<Goods> goods =new ArrayList<>();
		
		String querySQL = "SELECT GOODS_ID, GOODS_NAME, DESCRIPTION, PRICE, QUANTITY, IMAGE_NAME, STATUS " 
				+ " FROM BEVERAGE_GOODS ";
		
		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); //連接資料庫
				PreparedStatement stmt = conn.prepareStatement(querySQL)) { //PreparedStatement可放入能動態修改參數的SQL語句
			
			try (ResultSet rs = stmt.executeQuery()) { //執行SQL語句，並回傳資料結果集(Set集合)
				
				//取回資料結果，並存入對應的自定義物件
				while (rs.next()) { //只要有資料就繼續執行

					Goods good = new Goods();
					
					//依照對應的欄位及資料型態取值
					good.setGoodsID(rs.getString("GOODS_ID")); //rs.getXXX(放查詢的欄位)
					good.setGoodsName(rs.getString("GOODS_NAME"));
					good.setGoodsDescreption(rs.getString("DESCRIPTION"));
					good.setGoodsPrice(rs.getInt("PRICE"));
					good.setGoodsQuantity(rs.getInt("QUANTITY"));
					good.setGoodsImageName(rs.getString("IMAGE_NAME"));
					good.setStatus(rs.getString("STATUS"));
					
					goods.add(good); //將每一列產品資訊存入陣列或集合中
					
				}
			} catch (SQLException e) {
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return goods;
		
	}
	
	public int getAllGoodsCount(){
		
		int count=0;
		
		String querySQL = "SELECT COUNT(*) FROM BEVERAGE_GOODS";
		
		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); //連接資料庫
				PreparedStatement stmt = conn.prepareStatement(querySQL)) { //PreparedStatement可放入能動態修改參數的SQL語句
			
			try (ResultSet rs = stmt.executeQuery()) { //執行SQL語句，並回傳資料結果集(Set集合)
				
				//取回資料結果，並存入對應的自定義物件
				while (rs.next()) { //只要有資料就繼續執行
					
					count = rs.getInt(1);
					
				}
			} catch (SQLException e) {
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;

	}
	
	public int getGoodsCountBySearchCondition(SearchCondition searchCondition) {
		
		String goodsID = null;
		String keyword = null;
		String minPrice = null;
		String maxPrice = null;
		String stock = null;
		String status = null;

		if (searchCondition != null) {
			goodsID = searchCondition.getGoodsID();
			keyword = searchCondition.getKeyword();
			minPrice = searchCondition.getMinPrice();
			maxPrice = searchCondition.getMaxPrice();
			stock = searchCondition.getStock();
			status = searchCondition.getStatus();
		}
		
		int count = 0;

		StringBuilder querySQL = new StringBuilder();
		querySQL.append(" SELECT COUNT(*) FROM BEVERAGE_GOODS ")
				.append(" WHERE GOODS_ID IS NOT NULL ");
		if (null != goodsID && !goodsID.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND GOODS_ID=? ");
		}
		if (null != keyword && !keyword.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND UPPER(GOODS_NAME) LIKE UPPER(?) ");
		}
		if (null != minPrice && !minPrice.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND PRICE >= ? ");
		}
		if (null != maxPrice && !maxPrice.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND PRICE <= ? ");
		}
		if (null != stock && !stock.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND QUANTITY <= ? ");
		}
		if (null != status && !status.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND STATUS = ? ");
		}
		
		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); // 連接資料庫
				PreparedStatement pstmt = conn.prepareStatement(querySQL.toString())) { // PreparedStatement可放入能動態修改參數的SQL語句

			// 動態寫入SQL的參數 stmt.set()第一個參數放數字，代表SQL「?」出現的位置，第二個放要傳入的參數
			int position = 1;
			if (null != goodsID && !goodsID.replaceAll(" ", "").isEmpty()) {
				pstmt.setString(position++, goodsID);
			}
			if (null != keyword && !keyword.replaceAll(" ", "").isEmpty()) {
				pstmt.setString(position++, "%" + keyword + "%"); // "LIKE %ca%" 「%」這個符號在java的字串中有特殊的意義，因此不能直接寫在SQL語句中
			}
			if (null != minPrice && !minPrice.replaceAll(" ", "").isEmpty()) {
				pstmt.setString(position++, minPrice);
			}
			if (null != maxPrice && !maxPrice.replaceAll(" ", "").isEmpty()) {
				pstmt.setString(position++, maxPrice);
			}
			if (null != stock && !stock.replaceAll(" ", "").isEmpty()) {
				pstmt.setString(position++, stock);
			}
			if (null != status && !status.replaceAll(" ", "").isEmpty()) {
				pstmt.setString(position++, status);
			}

			try (ResultSet rs = pstmt.executeQuery()) { // 執行SQL語句，並回傳資料結果集(Set集合)

				// 取回資料結果，並存入對應的自定義物件
				while (rs.next()) { // 只要有資料就繼續執行

					count = rs.getInt(1);

				}
			} catch (SQLException e) {
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;

	}
	
	public List<Goods> queryGoodsBySearchCondition(int startRowNo, int endRowNo, SearchCondition searchCondition) {

		String sortByPrice = null;
		String goodsID = null;
		String keyword = null;
		String minPrice = null;
		String maxPrice = null;
		String stock = null;
		String status = null;

		if (searchCondition != null) {
			sortByPrice = searchCondition.getSortByPrice();
			goodsID = searchCondition.getGoodsID();
			keyword = searchCondition.getKeyword();
			minPrice = searchCondition.getMinPrice();
			maxPrice = searchCondition.getMaxPrice();
			stock = searchCondition.getStock();
			status = searchCondition.getStatus();
		}
		
		List<Goods> goods =new ArrayList<>();
		
		StringBuilder querySQL = new StringBuilder();
		querySQL.append(" SELECT GOODS_ID, GOODS_NAME, DESCRIPTION, PRICE, QUANTITY, IMAGE_NAME, STATUS ")
				.append(" FROM ")
				.append(" ( ")
				.append("  SELECT ")
				.append("  BG.GOODS_ID, ")
				.append("  BG.GOODS_NAME, ")
				.append("  BG.DESCRIPTION, ")
				.append("  BG.PRICE, ")
				.append("  BG.QUANTITY, ")
				.append("  BG.IMAGE_NAME, ")
				.append("  BG.STATUS, ");
		if (null != sortByPrice && !sortByPrice.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" ROW_NUMBER() OVER(ORDER BY BG.PRICE " + sortByPrice + " ,BG.GOODS_ID) NUM ");
		}else {
			querySQL.append(" ROW_NUMBER() OVER(ORDER BY BG.GOODS_ID) NUM ");
		}
		querySQL.append("  FROM BEVERAGE_GOODS BG "
					   + " WHERE GOODS_ID IS NOT NULL ");
		if (null != goodsID && !goodsID.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND GOODS_ID=? ");
		}
		if (null != keyword && !keyword.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND UPPER(GOODS_NAME) LIKE UPPER(?) ");
		}
		if (null != minPrice && !minPrice.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND BG.PRICE >= ? ");
		}
		if (null != maxPrice && !maxPrice.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND BG.PRICE <= ? ");
		}
		if (null != stock && !stock.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND BG.QUANTITY <= ? ");
		}
		if (null != status && !status.replaceAll(" ", "").isEmpty()) {
			querySQL.append(" AND BG.STATUS = ? ");
		}
		querySQL.append(" ) "
					  + " WHERE NUM BETWEEN ? AND ? ");
		
		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); //連接資料庫
				PreparedStatement pstmt = conn.prepareStatement(querySQL.toString())) { //PreparedStatement可放入能動態修改參數的SQL語句
			
			// 動態寫入SQL的參數 stmt.set()第一個參數放數字，代表SQL「?」出現的位置，第二個放要傳入的參數
			int position = 1;
			if (null != goodsID && !goodsID.replaceAll(" ", "").isEmpty()) {
				pstmt.setString(position++, goodsID);
			}
			if (null != keyword && !keyword.replaceAll(" ", "").isEmpty()) {
				pstmt.setString(position++, "%" + keyword + "%"); // "LIKE %ca%" 「%」這個符號在java的字串中有特殊的意義，因此不能直接寫在SQL語句中
			}
			if (null != minPrice && !minPrice.replaceAll(" ", "").isEmpty()) {
				pstmt.setString(position++, minPrice);
			}
			if (null != maxPrice && !maxPrice.replaceAll(" ", "").isEmpty()) {
				pstmt.setString(position++, maxPrice);
			}
			if (null != stock && !stock.replaceAll(" ", "").isEmpty()) {
				pstmt.setString(position++, stock);
			}
			if (null != status && !status.replaceAll(" ", "").isEmpty()) {
				pstmt.setString(position++, status);
			}
			pstmt.setInt(position++, startRowNo); // SQL語句中的BETWEEN有包含該數字，因此要加1減1
			pstmt.setInt(position++, endRowNo);
			
			try (ResultSet rs = pstmt.executeQuery()) { //執行SQL語句，並回傳資料結果集(Set集合)
				
				//取回資料結果，並存入對應的自定義物件
				while (rs.next()) { //只要有資料就繼續執行

					Goods good = new Goods();
					
					//依照對應的欄位及資料型態取值
					good.setGoodsID(rs.getString("GOODS_ID")); //rs.getXXX(放查詢的欄位)
					good.setGoodsName(rs.getString("GOODS_NAME"));
					good.setGoodsDescreption(rs.getString("DESCRIPTION"));
					good.setGoodsPrice(rs.getInt("PRICE"));
					good.setGoodsQuantity(rs.getInt("QUANTITY"));
					good.setGoodsImageName(rs.getString("IMAGE_NAME"));
					good.setStatus(rs.getString("STATUS"));
					
					goods.add(good); //將每一列產品資訊存入陣列或集合中
					
				}
			} catch (SQLException e) {
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return goods;
		
	}
	
	public int createGoods(Goods goods) {
		
		int goodsID = 0;
		String[] cols = { "GOODS_ID" };
		
		try (Connection conn = DBConnectionFactory.getLocalDBConnection()) { //連接資料庫
			
			// 設置交易不自動提交
			conn.setAutoCommit(false);
			
			String insertSQL = "INSERT INTO BEVERAGE_GOODS "
							 + " (GOODS_ID, GOODS_NAME, PRICE, QUANTITY, IMAGE_NAME, STATUS) "
							 + " VALUES (BEVERAGE_GOODS_SEQ.NEXTVAL,?,?,?,?,?)";
			
			//prepareStatement()第一個參數放SQL語句，第二個參數放要取回的欄位值的欄位名稱
			try (PreparedStatement pstmt = conn.prepareStatement(insertSQL, cols)) { // PreparedStatement可放入能動態修改參數的SQL語句

				// 動態寫入SQL的參數 stmt.set()第一個參數放數字，代表SQL「?」出現的位置，第二個放要傳入的參數
				int position = 1;
				pstmt.setString(position++, goods.getGoodsName());
				pstmt.setInt(position++, goods.getGoodsPrice());
				pstmt.setInt(position++, goods.getGoodsQuantity());
				pstmt.setString(position++, goods.getGoodsImageName());
				pstmt.setString(position++, goods.getStatus());

				pstmt.executeUpdate(); // 執行SQL語句，並回傳新增的資料筆數
				// 取對應的自增主鍵值
				ResultSet rsKeys = pstmt.getGeneratedKeys();
				rsKeys.next();
				goodsID = rsKeys.getInt(1);

				conn.commit(); // 交易提交
				
			} catch (SQLException e) {
				// 若發生錯誤則資料就rollback
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return goodsID;
		
	}
	
	public boolean updateGoods(Goods goods) {
		
		int updateCount = 0;
		boolean updateSuccess = false;
		
		int actualQuantity = queryGoodQuantity( Integer.parseInt(goods.getGoodsID())) + goods.getGoodsQuantity();
		
		try (Connection conn = DBConnectionFactory.getLocalDBConnection()) { //連接資料庫
			
			// 設置交易不自動提交
			conn.setAutoCommit(false);
			
			String updateSql = "UPDATE BEVERAGE_GOODS SET "
							 + " PRICE=? , "
							 + " QUANTITY=? , "
							 + " STATUS=? "
							 + " WHERE GOODS_ID = ? ";
			
			try (PreparedStatement stmt = conn.prepareStatement(updateSql)) { //PreparedStatement可放入能動態修改參數的SQL語句
				
				//動態寫入SQL的參數。stmt.set()第一個參數放數字，代表SQL「?」出現的位置，第二個放要傳入的參數
				int position = 1;
				stmt.setInt(position++, goods.getGoodsPrice());
				stmt.setInt(position++, actualQuantity);
				stmt.setString(position++, goods.getStatus());
				stmt.setString(position++, goods.getGoodsID());
				
				updateCount = stmt.executeUpdate(); //執行SQL語句，並回傳更新的資料筆數
				
				conn.commit(); //交易提交
				updateSuccess = (updateCount != 0) ? true : false; //確認資料有正確更新，如果更新筆數為0代表更新失敗
				
			} catch (SQLException e) {
				// 若發生錯誤則資料就rollback
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return updateSuccess;
		
	}
	
	public int queryGoodQuantity(int goodID) {
		
		int quantity = 0;
		String queryQuantitySql = "SELECT QUANTITY FROM BEVERAGE_GOODS WHERE GOODS_ID = ? ";

		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); // 連接資料庫
				PreparedStatement stmt = conn.prepareStatement(queryQuantitySql)) { // PreparedStatement可放入能動態修改參數的SQL語句

			stmt.setInt(1, goodID); // 動態寫入SQL的參數 stmt.set()第一個參數放數字，代表SQL「?」出現的位置，第二個放要傳入的參數

			try (ResultSet rs = stmt.executeQuery()) { // 執行SQL語句，並回傳資料結果集(Set集合)

				// 取回資料結果，並存入對應的自定義物件
				while (rs.next()) { // 只要有資料就繼續執行

					quantity = rs.getInt("QUANTITY");

				}
			} catch (SQLException e) {
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return quantity;
		
	}
	
	public Set<SalesReport> queryOrderBetweenDate(String queryStartDate, String queryEndDate) {

		Set<SalesReport> reports = new LinkedHashSet();
		
		String querySQL = 
				"SELECT O.ORDER_ID , TO_CHAR(O.ORDER_DATE,'YYYY-mm-DD HH24:MI:SS') ORDER_DATE "
				+ " , M.CUSTOMER_NAME , G.GOODS_NAME , O.GOODS_BUY_PRICE "
				+ ", O.BUY_QUANTITY , O.CUSTOMER_ID ,(O.GOODS_BUY_PRICE * O.BUY_QUANTITY) BUYAMOUNT "
				+ " FROM BEVERAGE_ORDER O "
				+ " JOIN BEVERAGE_GOODS G "
				+ " ON O.GOODS_ID=G.GOODS_ID "
				+ " JOIN BEVERAGE_MEMBER M "
				+ " ON O.CUSTOMER_ID=M.IDENTIFICATION_NO "
				+ " WHERE O.ORDER_DATE "
				+ " BETWEEN TO_DATE(?,'YYYY-mm-DD HH24:MI:SS') "
				+ " AND TO_DATE(? ,'YYYY-mm-DD HH24:MI:SS') "
				+ " ORDER BY ORDER_ID";

		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); //連接資料庫
				PreparedStatement stmt = conn.prepareStatement(querySQL)) { //PreparedStatement可放入能動態修改參數的SQL語句
			
			//動態寫入SQL的參數 stmt.set()第一個參數放數字，代表SQL「?」出現的位置，第二個放要傳入的參數
			int position = 1;
			stmt.setString(position++, queryStartDate + " 00:00:00"); //SQL的date必須給到時、分、秒
			stmt.setString(position++, queryEndDate + " 23:59:59");
				
			try (ResultSet rs = stmt.executeQuery()) {//執行SQL語句，並回傳資料結果集(Set集合)
				
				//取回資料結果，並存入對應的自定義物件
				while (rs.next()) {//只要有資料就繼續執行

					SalesReport report = new SalesReport();
					
					//依照對應的欄位及資料型態取值
					report.setOrderID(rs.getLong("ORDER_ID"));//rs.getXXX(放查詢的欄位)
					report.setOrderDate(rs.getString("ORDER_DATE"));
					report.setCustomerName(rs.getString("CUSTOMER_NAME"));
					report.setGoodsName(rs.getString("GOODS_NAME"));
					report.setGoodsBuyPrice(rs.getInt("GOODS_BUY_PRICE"));
					report.setBuyQuantity(rs.getInt("BUY_QUANTITY"));
					report.setBuyAmount(rs.getInt("BUYAMOUNT"));
					
					reports.add(report);//將每一列產品資訊存入陣列或集合中

				}
			} catch (SQLException e) {
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reports;
		
	}
	
}
