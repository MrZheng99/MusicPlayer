package com.zj.musicplayer.model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * 
 * @description 数据库封装类
 * @author YM
 * @date 20200331
 */
public class DBHelper {
	@Test
	public void test() {
		DBHelper dbHelper = new DBHelper();
		dbHelper.query("select *from USERS");
	}

	/**
	 * 加载驱动
	 */
	static {
		try {
			Class.forName(RederConfig.getInstance().getProperty("sqlClassName"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param url
	 * @param userName
	 * @param password
	 * @return
	 */
	private Connection getConnection() {
		Connection con = null;
		try {
			String url = RederConfig.getInstance().getProperty("url");
			String userName = RederConfig.getInstance().getProperty("userName");
			String password = RederConfig.getInstance().getProperty("password");
			con = DriverManager.getConnection(url, userName, password);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;
	}

	/**
	 * 
	 * @param pstmt
	 * @param rs
	 * @param con
	 */
	private void colse(PreparedStatement pstmt, ResultSet rs, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param pstmt
	 * @param pramas
	 * @see #setPramas(PreparedStatement, List<String>)
	 */
	private void setPramas(PreparedStatement pstmt, Object... parmas) {
		if (pstmt == null || parmas.length <= 0) {
			return;
		}
		for (int i = 0, len = parmas.length; i < len; i++) {
			try {
				pstmt.setObject(i + 1, parmas[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置参数
	 * 
	 * @param pstmt
	 * @param list
	 */
	private void setPramas(PreparedStatement pstmt, List<String> list) {
		if (list == null || list.size() <= 0) {
			return;
		}
		System.out.println(list.toString());
		for (int i = 0, len = list.size(); i < len; i++) {
			try {
				pstmt.setObject(i + 1, list.get(i));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param sql
	 * @param pramas
	 * @return
	 */
	public int update(String sql, Object... parmas) {
		int rs = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setPramas(pstmt, parmas);
			rs = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.colse(pstmt, null, con);
		}
		return rs;
	}

	/**
	 * 获取结果每列的名字
	 * 
	 * @param rs
	 * @return
	 */
	private String[] getResultColNames(ResultSet rs) {
		ResultSetMetaData rsmd;
		String[] colNames = null;
		try {
			rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
			colNames = new String[colNum];
			for (int i = 1; i <= colNum; i++) {
				colNames[i - 1] = rsmd.getColumnName(i).toLowerCase();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return colNames;

	}

	/**
	 * 
	 * @param sql
	 * @param parmas
	 * @return List<Map<String, Object>>
	 * @see #query(String, String...)
	 * @see #querys(String, List)
	 */
	public List<Map<String, Object>> querys(String sql, Object... parmas) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setPramas(pstmt, parmas);
			rs = pstmt.executeQuery();
			String[] colNames = getResultColNames(rs);
			Map<String, Object> map = null;
			Object obj = null;
			Blob blob = null;
			byte[] bt = null;
			String colType = null; // 这个列的类型
			while (rs.next()) {
				map = new HashMap<String, Object>();
				for (String colName : colNames) {
					// 如果返回的是blob，那要转成字节数组存到本地

					obj = rs.getObject(colName);
					if (obj == null) {
						map.put(colName, obj);
						continue;
					}
					colType = obj.getClass().getSimpleName();

					if ("BLOB".equals(colType)) {
						// 用blob获取 转成字节数据
						blob = rs.getBlob(colName);
						bt = blob.getBytes(1, (int) blob.length());
						map.put(colName, bt);

					} else {
						map.put(colName, obj);

					}
				}
				list.add(map);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.colse(pstmt, rs, con);
		}
		return list;
	}

	/**
	 * 
	 * @param sql
	 * @param listCondition
	 * @return
	 * @see #querys(String, String...)
	 * @see #querys(String, Object...)
	 */
	public List<Map<String, Object>> querys(String sql, List<String> listCondition) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setPramas(pstmt, listCondition);
			rs = pstmt.executeQuery();
			String[] colNames = getResultColNames(rs);
			Map<String, Object> map = null;
			Object obj = null;
			Blob blob = null;
			byte[] bt = null;
			String colType = null; // 这个列的类型
			while (rs.next()) {
				map = new HashMap<String, Object>();
				for (String colName : colNames) {
					// 如果返回的是blob，那要转成字节数组存到本地

					obj = rs.getObject(colName);
					if (obj == null) {
						map.put(colName, obj);
						continue;
					}
					colType = obj.getClass().getSimpleName();

					if ("BLOB".equals(colType)) {
						// 用blob获取 转成字节数据
						blob = rs.getBlob(colName);
						bt = blob.getBytes(1, (int) blob.length());
						map.put(colName, bt);

					} else {
						map.put(colName, obj);

					}
				}
				list.add(map);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.colse(pstmt, rs, con);
		}
		return list;
	}

	/**
	 * 
	 * @param sql
	 * @param parmas
	 * @return List<Map<String, String>>
	 * @see #querys(String, String...)
	 * @see #querys(String, List)
	 */
	public List<Map<String, String>> querysString(String sql, Object... parmas) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setPramas(pstmt, parmas);
			rs = pstmt.executeQuery();
			String[] colNames = getResultColNames(rs);
			Map<String, String> map = null;
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (String colName : colNames) {
					map.put(colName, rs.getString(colName));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.colse(pstmt, rs, con);
		}
		return list;
	}

	/**
	 * 
	 * @param sql
	 * @param parmas
	 * @return
	 * @see #queryString(String, Object...)
	 */
	public Map<String, Object> query(String sql, Object... parmas) {
		Map<String, Object> map = new HashMap<String, Object>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setPramas(pstmt, parmas);
			rs = pstmt.executeQuery();
			String[] colNames = getResultColNames(rs);
			Object obj = null;
			Blob blob = null;
			byte[] bt = null;
			String colType = null;
			while (rs.next()) {
				for (String colName : colNames) {
					// 如果返回的是blob，那要转成字节数组存到本地

					obj = rs.getObject(colName);
					if (obj == null) {
						map.put(colName, obj);
						continue;
					}
					colType = obj.getClass().getSimpleName();

					if ("BLOB".equals(colType)) {
						// 用blob获取 转成字节数据
						blob = rs.getBlob(colName);
						bt = blob.getBytes(1, (int) blob.length());
						map.put(colName, bt);

					} else {
						map.put(colName, obj);

					}
					map.put(colName, rs.getObject(colName));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.colse(pstmt, rs, con);
		}
		return map;
	}

	/**
	 * 
	 * @param sql
	 * @param parmas
	 * @return
	 * @see #query(String, Object...)
	 */
	public Map<String, String> queryString(String sql, Object... parmas) {
		Map<String, String> map = new HashMap<String, String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setPramas(pstmt, parmas);
			rs = pstmt.executeQuery();
			String[] colNames = getResultColNames(rs);

			while (rs.next()) {
				for (String colName : colNames) {
					map.put(colName, rs.getString(colName));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.colse(pstmt, rs, con);
		}
		return map;
	}

	/**
	 * 获取记录总条数
	 * 
	 * @param sql
	 * @param parmas
	 * @return
	 */
	public int getQueryTotalNums(String sql, Object... parmas) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int total = 0;
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setPramas(pstmt, parmas);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.colse(pstmt, rs, con);
		}
		return total;
	}
}
