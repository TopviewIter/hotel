package com.xqx.www.db;

import java.sql.*;

/**
 * ���ݿ������
 * @author xqx
 *
 */
public class SqlHelper {
	
	private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String url = "jdbc:sqlserver://localhost:1434;databaseName=hotel";
	private static String user = "sa";
	private static String passwd = "root";
	
	private static SqlHelper helper;
	
	// ������Ҫ�Ķ���
	private Connection ct = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	public static SqlHelper getSqlHelper(){
		
		if(helper == null) {
			helper = new SqlHelper();
		}
		return helper;
	}
	
	// ���캯������ʼ��ct
	private SqlHelper() {
		try {
			ct = DriverManager.getConnection(url, user, passwd);
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("���ݿ����û�п�����������ݿ����������");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	// �ر���Դ�ķ���
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (ct != null) {
				ct.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet query(String sql, Object[] paras) {
		try {
			ps = ct.prepareStatement(sql);
			for (int i = 0; i < paras.length; i++) {
				ps.setObject(i + 1, paras[i]);
			}
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	// ��ɾ�ķ���
	public boolean executeSql(String sql, Object[] paras) {
		
		boolean b = true;
		try {
			ps = ct.prepareStatement(sql);
			for (int i = 0; i < paras.length; i++) {
				ps.setObject(i + 1, paras[i]);
			}
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SqlHelper����ɾ���г����������飡");
			b = false;
		}
		return b;
	}
	
	public CallableStatement executeProcess(String sql, Object[] paras, int parameterIndex, int resultType) {
		
		CallableStatement cs = null;
		try {
			cs = ct.prepareCall(sql);
			for (int i = 0; i < paras.length; i++) {
				cs.setObject(i + 1, paras[i]);
			}
			if(parameterIndex != -1) {
				cs.registerOutParameter(parameterIndex, resultType);
			}
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cs;
	}
}
