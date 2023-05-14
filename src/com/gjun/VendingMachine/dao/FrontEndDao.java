package com.gjun.VendingMachine.dao;

import java.sql.Statement;
import java.util.Map.Entry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gjun.VendingMachine.model.Goods;
import com.gjun.VendingMachine.model.Member;
import com.gjun.VendingMachine.vo.ShoppingCartGoods;

public class FrontEndDao {

	private static FrontEndDao frontEndDao = new FrontEndDao();

	private FrontEndDao() {
	}

	public static FrontEndDao getInstance() {
		return frontEndDao;
	}
	
	public Member queryMemberByIdentificationNo(String identificationNo){
		
		String querySQL = "SELECT IDENTIFICATION_NO, PASSWORD, CUSTOMER_NAME "
						+ " FROM BEVERAGE_MEMBER "
						+ " WHERE IDENTIFICATION_NO = ?";
		
		Member member = null;

		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); //連接資料庫
				PreparedStatement stmt = conn.prepareStatement(querySQL)) { //PreparedStatement可放入能動態修改參數的SQL語句
			
			stmt.setString(1, identificationNo);//動態寫入SQL的參數 stmt.set()第一個參數放數字，代表SQL「?」出現的位置，第二個放要傳入的參數
			
			
			try (ResultSet rs = stmt.executeQuery()) { //執行SQL語句，並回傳資料結果集(Set集合)
				
				//取回資料結果，並存入對應的自定義物件
				while (rs.next()) {//只要有資料就繼續執行
					
					member = new Member();
					
					//依照對應的欄位及資料型態取值
					member.setIdentificationNo(rs.getString("IDENTIFICATION_NO")); //rs.getXXX(放查詢的欄位)
					member.setPassword(rs.getString("PASSWORD"));
					member.setCustomerName(rs.getString("CUSTOMER_NAME"));

				}
			} catch (SQLException e) {
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return member;
		
	}
	
	public int getAllGoodsCount() {

		int count = 0;
		
		String querySQL = "SELECT COUNT(*) FROM BEVERAGE_GOODS WHERE STATUS=1";
		
		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); // 連接資料庫
				PreparedStatement stmt = conn.prepareStatement(querySQL)) { // PreparedStatement可放入能動態修改參數的SQL語句

			try (ResultSet rs = stmt.executeQuery()) { // 執行SQL語句，並回傳資料結果集(Set集合)

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
	
	public int getGoodsCountByKeyword(String searchKeyword){
		
		int count=0;

		String querySQL = "SELECT COUNT(*) FROM BEVERAGE_GOODS "
				+ " WHERE UPPER(GOODS_NAME) LIKE UPPER(?) "
				+ " AND STATUS=1 ";

		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); // 連接資料庫
				PreparedStatement stmt = conn.prepareStatement(querySQL)) { // PreparedStatement可放入能動態修改參數的SQL語句

			// 動態寫入SQL的參數 stmt.set()第一個參數放數字，代表SQL「?」出現的位置，第二個放要傳入的參數
			int position = 1;
			stmt.setString(position++, "%" + searchKeyword + "%"); // "LIKE %ca%" 「%」這個符號在java的字串中有特殊的意義，因此不能直接寫在SQL語句中

			try (ResultSet rs = stmt.executeQuery()) { // 執行SQL語句，並回傳資料結果集(Set集合)

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

	public List<Goods> queryGoodsByPage(int startRowNo, int endRowNo) {

		List<Goods> goods = new ArrayList<>();

		String querySQL = "SELECT GOODS_ID, GOODS_NAME, DESCRIPTION, PRICE, QUANTITY, IMAGE_NAME,STATUS " 
				+ " FROM "
				+ "  ( "
				+ "   SELECT "
				+ "   BG.GOODS_ID, " 
				+ "   BG.GOODS_NAME, " 
				+ "   BG.DESCRIPTION, " 
				+ "   BG.PRICE, " 
				+ "   BG.QUANTITY, " 
				+ "   BG.IMAGE_NAME, "
				+ "   BG.STATUS, " 
				+ "   ROW_NUMBER() OVER(ORDER BY BG.GOODS_ID) NUM " 
				+ "   FROM BEVERAGE_GOODS BG "
				+ "   WHERE STATUS=1 "
				+ "  ) " 
				+ " WHERE NUM BETWEEN ? AND ? ";

		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); // 連接資料庫
				PreparedStatement stmt = conn.prepareStatement(querySQL)) { // PreparedStatement可放入能動態修改參數的SQL語句

			// 動態寫入SQL的參數 stmt.set()第一個參數放數字，代表SQL「?」出現的位置，第二個放要傳入的參數
			int position = 1;
			stmt.setInt(position++, startRowNo);
			stmt.setInt(position++, endRowNo);

			try (ResultSet rs = stmt.executeQuery()) { // 執行SQL語句，並回傳資料結果集(Set集合)

				// 取回資料結果，並存入對應的自定義物件
				while (rs.next()) { // 只要有資料就繼續執行

					Goods good = new Goods();

					// 依照對應的欄位及資料型態取值
					good.setGoodsID(rs.getString("GOODS_ID")); // rs.getXXX(放查詢的欄位)
					good.setGoodsName(rs.getString("GOODS_NAME"));
					good.setGoodsDescreption(rs.getString("DESCRIPTION"));
					good.setGoodsPrice(rs.getInt("PRICE"));
					good.setGoodsQuantity(rs.getInt("QUANTITY"));
					good.setGoodsImageName(rs.getString("IMAGE_NAME"));
					good.setStatus(rs.getString("STATUS"));

					goods.add(good); // 將每一列產品資訊存入陣列或集合中

				}
			} catch (SQLException e) {
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return goods;
	}

	public List<Goods> queryGoodsByPageAndKeyword(int startRowNo, int endRowNo, String searchKeyword) {

		List<Goods> goods = new ArrayList<>();

		String querySQL = "SELECT GOODS_ID,GOODS_NAME, DESCRIPTION, PRICE,QUANTITY,IMAGE_NAME,STATUS FROM "
				+ "  ( "
				+ "   SELECT "
				+ "   BG.GOODS_ID, "
				+ "   BG.GOODS_NAME, "
				+ "   BG.DESCRIPTION, "
				+ "   BG.PRICE, "
				+ "   BG.QUANTITY, " 
				+ "   BG.IMAGE_NAME, "
				+ "   BG.STATUS, "
				+ "   ROW_NUMBER() OVER(ORDER BY BG.GOODS_ID) NUM "
				+ "   FROM BEVERAGE_GOODS BG "
				+ "   WHERE UPPER(GOODS_NAME) LIKE UPPER(?) "
				+ "   AND STATUS=1 "
				+ "  ) "
				+ " WHERE NUM BETWEEN ? AND ? ";

		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); // 連接資料庫
				PreparedStatement stmt = conn.prepareStatement(querySQL)) { // PreparedStatement可放入能動態修改參數的SQL語句

			// 動態寫入SQL的參數 stmt.set()第一個參數放數字，代表SQL「?」出現的位置，第二個放要傳入的參數
			int position = 1;
			stmt.setString(position++, "%" + searchKeyword + "%"); // "LIKE %ca%" 「%」這個符號在java的字串中有特殊的意義，因此不能直接寫在SQL語句中
			stmt.setInt(position++, startRowNo); // SQL語句中的BETWEEN有包含該數字，因此要加1減1
			stmt.setInt(position++, endRowNo);

			try (ResultSet rs = stmt.executeQuery()) { // 執行SQL語句，並回傳資料結果集(Set集合)

				// 取回資料結果，並存入對應的自定義物件
				while (rs.next()) { // 只要有資料就繼續執行

					Goods good = new Goods();

					// 依照對應的欄位及資料型態取值
					good.setGoodsID(rs.getString("GOODS_ID")); // rs.getXXX(放查詢的欄位)
					good.setGoodsName(rs.getString("GOODS_NAME"));
					good.setGoodsDescreption(rs.getString("DESCRIPTION"));
					good.setGoodsPrice(rs.getInt("PRICE"));
					good.setGoodsQuantity(rs.getInt("QUANTITY"));
					good.setGoodsImageName(rs.getString("IMAGE_NAME"));
					good.setStatus(rs.getString("STATUS"));

					goods.add(good); // 將每一列產品資訊存入陣列或集合中

				}
			} catch (SQLException e) {
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return goods;
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

	public boolean batchCreateGoodsOrder(String customerID, Map<Goods, Integer> goodsOrders) {

		boolean insertSuccess = false;

		try (Connection conn = DBConnectionFactory.getLocalDBConnection()) { // 連接資料庫

			// 設置交易不自動提交
			conn.setAutoCommit(false);

			try (Statement stmt = conn.createStatement()) { // 因PreparedStatement使用addBatch的方法，並不支援資料異動的筆數，因此用Statement

				customerID = "," + "'" + customerID + "'";

				// 取HashMap所有的key和value
				Set<Entry<Goods, Integer>> en = goodsOrders.entrySet();
				for (Entry<Goods, Integer> entry : en) {

					String goodsID = "," + entry.getKey().getGoodsID();
					String goodsPrice = "," + entry.getKey().getGoodsPrice();
					String quantity = "," + entry.getValue();

					// 用addBatch分次寫入SQL語句
					stmt.addBatch("INSERT INTO BEVERAGE_ORDER "
							+ "(ORDER_ID, ORDER_DATE, CUSTOMER_ID, GOODS_ID, GOODS_BUY_PRICE, BUY_QUANTITY) "
							+ " VALUES (BEVERAGE_ORDER_SEQ.NEXTVAL,SYSDATE" + customerID + goodsID + goodsPrice
							+ quantity + ")");

				}

				int[] insertCounts = stmt.executeBatch();// 執行SQL語句，並回傳"每個"SQL語句新增的資料筆數

				// 每個SQL語句更新的資料筆數都必須大於0(代表有更新到資料)，否則rollback
				for (int i : insertCounts) {
					if (i == 0) {
						throw new SQLException();
					}
				}

				insertSuccess = true;
				conn.commit();// 交易提交

			} catch (SQLException e) {
				// 若發生錯誤則資料就rollback
				insertSuccess = false;
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return insertSuccess;

	}
	
	public boolean batchUpdateGoodsQuantity(Set<Goods> goods){
		
		boolean updateSuccess = false;

		try (Connection conn = DBConnectionFactory.getLocalDBConnection()) { //連接資料庫
			
			// 設置交易不自動提交
			conn.setAutoCommit(false);
			
			try (Statement stmt = conn.createStatement()) { //因PreparedStatement使用addBatch的方法，並不支援資料異動的筆數，因此用Statement
				
				//更新多筆商品資訊 用addBatch分次寫入SQL語句
				for (Goods good : goods) {
					
					String goodsName = "'" + good.getGoodsName() + "'";
					String imageName = "'" + good.getGoodsImageName() + "'";
					
					//用addBatch分次寫入SQL語句
					stmt.addBatch(" UPDATE BEVERAGE_GOODS SET GOODS_ID= " + good.getGoodsID()
							+ " , GOODS_NAME= " + goodsName
							+ " , PRICE= " + good.getGoodsPrice()
							+ " , QUANTITY= "+ good.getGoodsQuantity()
							+ " , IMAGE_NAME= "+ imageName
							+ " , STATUS= " + good.getStatus()
							+ " WHERE GOODS_ID = " + good.getGoodsID());
					
				}

				
				int[] updateCounts = stmt.executeBatch(); //執行SQL語句，並回傳"每個"SQL語句更新的資料筆數
				
				//每個SQL語句更新的資料筆數都必須大於0(代表有更新到資料)，否則rollback
				for (int i : updateCounts) {
					if (i == 0) {
						throw new SQLException();
					}
				}

				updateSuccess = true;
				conn.commit();//交易提交

			} catch (SQLException e) {
				//若發生錯誤則資料就rollback
				updateSuccess = false;
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return updateSuccess;
		
	}
	
	public ShoppingCartGoods queryShoppingCartGoodsByID(String goodID) {

		ShoppingCartGoods shoppingCartGoods = new ShoppingCartGoods();

		String querySQL = "SELECT GOODS_ID, GOODS_NAME, PRICE "
				+ " FROM BEVERAGE_GOODS "
				+ " WHERE GOODS_ID = ?";

		try (Connection conn = DBConnectionFactory.getLocalDBConnection(); // 連接資料庫
				PreparedStatement stmt = conn.prepareStatement(querySQL)) { // PreparedStatement可放入能動態修改參數的SQL語句

			stmt.setString(1, goodID); // 動態寫入SQL的參數 stmt.set()第一個參數放數字，代表SQL「?」出現的位置，第二個放要傳入的參數

			try (ResultSet rs = stmt.executeQuery()) { // 執行SQL語句，並回傳資料結果集(Set集合)

				// 取回資料結果，並存入對應的自定義物件
				while (rs.next()) { // 只要有資料就繼續執行

					//依照對應的欄位及資料型態取值
					shoppingCartGoods.setGoodsID(rs.getString("GOODS_ID")); //rs.getXXX(放查詢的欄位)
					shoppingCartGoods.setGoodsName(rs.getString("GOODS_NAME"));
					shoppingCartGoods.setGoodsPrice(rs.getInt("PRICE"));

				}
			} catch (SQLException e) {
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return shoppingCartGoods;

	}

}
