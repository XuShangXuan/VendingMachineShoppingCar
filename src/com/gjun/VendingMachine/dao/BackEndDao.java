package com.gjun.VendingMachine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.gjun.VendingMachine.model.Goods;
import com.gjun.VendingMachine.model.SalesReport;

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
	
	public List<Goods> queryGoodsByPage(int startRowNo,int endRowNo){
		
		List<Goods> goods =new ArrayList<>();
		
		String querySQL = "SELECT GOODS_ID, GOODS_NAME, DESCRIPTION, PRICE, QUANTITY, IMAGE_NAME, STATUS " 
				+ " FROM BEVERAGE_GOODS WHERE GOODS_ID BETWEEN ? AND ? ";
		
		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); //連接資料庫
				PreparedStatement stmt = conn.prepareStatement(querySQL)) { //PreparedStatement可放入能動態修改參數的SQL語句
			
			// 動態寫入SQL的參數 stmt.set()第一個參數放數字，代表SQL「?」出現的位置，第二個放要傳入的參數
			int position = 1;
			stmt.setInt(position++, startRowNo); // SQL語句中的BETWEEN有包含該數字，因此要加1減1
			stmt.setInt(position++, endRowNo);
			
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
	
//	public List<Goods> queryGoodsByPageTest(int startPageIndex,int endPageIndex){
//		
//		List<Goods> goods =new ArrayList<>();
//		
//		String querySQL = "SELECT GOODS_ID, GOODS_NAME, DESCRIPTION, PRICE, QUANTITY, IMAGE_NAME, STATUS " 
//				+ " FROM BEVERAGE_GOODS WHERE GOODS_ID BETWEEN ? AND ? ";
//		
//		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); //連接資料庫
//				PreparedStatement stmt = conn.prepareStatement(querySQL)) { //PreparedStatement可放入能動態修改參數的SQL語句
//			
//			// 動態寫入SQL的參數 stmt.set()第一個參數放數字，代表SQL「?」出現的位置，第二個放要傳入的參數
//			int position = 1;
//			stmt.setInt(position++, startPageIndex); // SQL語句中的BETWEEN有包含該數字，因此要加1減1
//			stmt.setInt(position++, endPageIndex);
//			
//			try (ResultSet rs = stmt.executeQuery()) { //執行SQL語句，並回傳資料結果集(Set集合)
//				
//				//取回資料結果，並存入對應的自定義物件
//				while (rs.next()) { //只要有資料就繼續執行
//
//					Goods good = new Goods();
//					
//					//依照對應的欄位及資料型態取值
//					good.setGoodsID(rs.getString("GOODS_ID")); //rs.getXXX(放查詢的欄位)
//					good.setGoodsName(rs.getString("GOODS_NAME"));
//					good.setGoodsDescreption(rs.getString("DESCRIPTION"));
//					good.setGoodsPrice(rs.getInt("PRICE"));
//					good.setGoodsQuantity(rs.getInt("QUANTITY"));
//					good.setGoodsImageName(rs.getString("IMAGE_NAME"));
//					good.setStatus(rs.getString("STATUS"));
//					
//					goods.add(good); //將每一列產品資訊存入陣列或集合中
//					
//				}
//			} catch (SQLException e) {
//				throw e;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return goods;
//		
//	}
	
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
