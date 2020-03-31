package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.*;
import javax.naming.*;

public class BoardDao {
	private static BoardDao instance;
	private BoardDao() {}
	public static BoardDao getInstance() {
		if (instance == null) {	instance = new BoardDao();		}
		return instance;
	}
	private Connection getConnection() {
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)
				ctx.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		}catch(Exception e) { System.out.println(e.getMessage());	}
		return conn;
	}

	public int getTotalCnt() throws SQLException {
		Connection conn = null;	Statement stmt= null; 
		ResultSet rs = null;    int tot = 0;
		String sql = "select count(*) from board";
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) tot = rs.getInt(1);
		} catch(Exception e) {	System.out.println(e.getMessage()); 
		} finally {
			if (rs !=null) rs.close();
			if (stmt != null) stmt.close();
			if (conn !=null) conn.close();
		}
		return tot;
	}

	
	
	public List<Board> list(int startRow, int endRow) throws SQLException {
		List<Board> list = new ArrayList<Board>();
		Connection conn = null;	PreparedStatement pstmt= null;
		ResultSet rs = null;
		// String sql = "select * from board order by num desc";
	// mysql select * from board order by num desc limit startPage-1,10;
		 String sql = "select * from (select rownum rn ,a.* from " + 
			" (select * from board order by ref desc,re_step) a ) "+
			" where rn between ? and ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Board board = new Board();
				board.setNum(rs.getInt("num"));
				board.setWriter(rs.getString("writer"));
				board.setSubject(rs.getString("subject"));
				board.setEmail(rs.getString("email"));
				board.setReadcount(rs.getInt("readcount"));
				board.setIp(rs.getString("ip"));
				board.setRef(rs.getInt("ref"));
			//	board.setRe_level(rs.getInt("re_step"));
				board.setRe_level(rs.getInt("re_level"));
				board.setRe_step(rs.getInt("re_step"));
				board.setReg_date(rs.getDate("reg_date"));
				list.add(board);
			}
		} catch(Exception e) {	System.out.println(e.getMessage()); 
		} finally {
			if (rs !=null) rs.close();
			if (pstmt != null) pstmt.close();
			if (conn !=null) conn.close();
		}
		return list;
	}
	
	//검색에 해당하는 전체 값
	public int getSearchCnt(String search) throws SQLException {
		Connection conn = null;	PreparedStatement pstmt= null; 
		ResultSet rs = null;    int tot = 0;
		//검색용 전체 건수
		String sql = "select count(*) from board where lower(subject) like lower('%'||?||'%')";
		System.out.println("getSearchCnt search-->" + search);  // /ch16/list.do
		System.out.println("getSearchCnt sql-->" + sql);  // /ch16/list.do
	try {
			conn = getConnection();
			//sql 구문을 이용하여 작업 준비
			pstmt = conn.prepareStatement(sql);
			//첫번째 물음표에 search 값 삽입
			pstmt.setString(1, search);
			//결과 값을 rs 에 삽입
			rs = pstmt.executeQuery();
			//첫 번째 값을 tot 에 삽입
			if (rs.next()) tot = rs.getInt(1);
		} catch(Exception e) {	System.out.println(e.getMessage()); 
		} finally {
			if (rs !=null) rs.close();
			if (pstmt != null) pstmt.close();
			if (conn !=null) conn.close();
		}
		return tot;
	}	
	
	//검색 목록
	public List<Board> searchList(String search, int startRow, int endRow) throws SQLException {
		List<Board> list = new ArrayList<Board>();
		Connection conn = null;	PreparedStatement pstmt= null;
		ResultSet rs = null;
		// String sql = "select * from board order by num desc";
	// mysql select * from board order by num desc limit startPage-1,10;
		 String sql = "select * from(select rownum rn, a.* FROM "
		 		+ "	(select * from board where lower(subject) like lower('%'||?||'%') order by ref desc,re_step) a) "
		 		+ " where rn between ? and ?";
			System.out.println("searchList sql-->" + sql);  // /ch16/list.do
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, search);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Board board = new Board();
				board.setNum(rs.getInt("num"));
				board.setWriter(rs.getString("writer"));
				board.setSubject(rs.getString("subject"));
				board.setEmail(rs.getString("email"));
				board.setReadcount(rs.getInt("readcount"));
				board.setIp(rs.getString("ip"));
				board.setRef(rs.getInt("ref"));
			//	board.setRe_level(rs.getInt("re_step"));
				
				board.setRe_level(rs.getInt("re_level"));
				board.setRe_step(rs.getInt("re_step"));
				board.setReg_date(rs.getDate("reg_date"));
				list.add(board);
			}
		} catch(Exception e) {	System.out.println(e.getMessage()); 
		} finally {
			if (rs !=null) rs.close();
			if (pstmt != null) pstmt.close();
			if (conn !=null) conn.close();
		}
		return list;
	}
	
	public int insert(Board board) throws SQLException {
		int num = board.getNum();		
		Connection conn = null;	PreparedStatement pstmt= null; 
		int result = 0;			ResultSet rs = null;
		String sql1 = "select nvl(max(num),0) from board";
		String sql="insert into board values(?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		String sql2="update board set re_step = re_step+1 where " +
			" ref=? and re_step > ?";
		try {			
			conn = getConnection();
			if (num != 0) {
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, board.getRef());
				pstmt.setInt(2, board.getRe_step());
				pstmt.executeUpdate();
				pstmt.close();
				board.setRe_step(board.getRe_step()+1);
				board.setRe_level(board.getRe_level()+1);
			}
			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			rs.next();
			// key인 num 1씩 증가, mysql auto_increment 또는 oracle sequence
			// sequence를 사용 : values(시퀀스명(board_seq).nextval,?,?...)
			int number = rs.getInt(1) + 1;  
			rs.close();   pstmt.close();
			if (num == 0) board.setRef(number);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getSubject());
			pstmt.setString(4, board.getContent());
			pstmt.setString(5, board.getEmail());
			pstmt.setInt(6, board.getReadcount());
			pstmt.setString(7, board.getPasswd());
			pstmt.setInt(8, board.getRef());
			pstmt.setInt(9, board.getRe_step());
			pstmt.setInt(10, board.getRe_level());
			pstmt.setString(11, board.getIp());
			result = pstmt.executeUpdate(); 
		} catch(Exception e) {	System.out.println(e.getMessage()); 
		} finally {
			if (rs !=null) rs.close();
			if (pstmt != null) pstmt.close();
			if (conn !=null) conn.close();
		}
		return result;
	}
	
	public Board select(int num) throws SQLException {
		Connection conn = null;	
		Statement stmt= null; 
		ResultSet rs = null;
		String sql = "select * from board where num="+num;
		Board board = new Board();
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {				
				board.setNum(rs.getInt(1));
				board.setWriter(rs.getString(2));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setEmail(rs.getString("email"));
				board.setReadcount(rs.getInt("readcount"));
				board.setIp(rs.getString(11));
				board.setReg_date(rs.getDate("reg_date"));
				board.setRef(rs.getInt("ref"));
				board.setRe_level(rs.getInt("re_level"));
				board.setRe_step(rs.getInt("re_step"));
			}
		} catch(Exception e) {	System.out.println(e.getMessage()); 
		} finally {
			if (rs !=null) rs.close();
			if (stmt != null) stmt.close();
			if (conn !=null) conn.close();
		}
		return board;
	}
	public void readCount(int num) throws SQLException {
		Connection conn = null;	
		PreparedStatement pstmt= null; 
		String sql="update board set readcount=readcount+1 where num=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);			
			pstmt.executeUpdate();
		} catch(Exception e) {	System.out.println(e.getMessage()); 
		} finally {
			if (pstmt != null) pstmt.close();
			if (conn !=null) conn.close();
		}
	}
	
	
	
	
	
	public int update(Board board) throws SQLException {
		Connection conn = null;	PreparedStatement pstmt= null; 
		int result = 0;			
		String sql="update board set subject=?,writer=?,email=?,"+
		                	"passwd=?,content=?,ip=? where num=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getSubject());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getEmail());
			pstmt.setString(4, board.getPasswd());
			pstmt.setString(5, board.getContent());
			pstmt.setString(6, board.getIp());
			pstmt.setInt(7, board.getNum());
			
			result = pstmt.executeUpdate();
		} catch(Exception e) {	System.out.println(e.getMessage()); 
		} finally {
			if (pstmt != null) pstmt.close();
			if (conn !=null) conn.close();
		}
		return result;
	}
	public int delete(int num, String passwd) throws SQLException {
		Connection conn = null;	PreparedStatement pstmt= null; 
		int result = 0;		    ResultSet rs = null;
		String sql1 = "select passwd from board where num=?";
		String sql="delete from board where num=?";
		try {
			String dbPasswd = "";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dbPasswd = rs.getString(1); 
				if (dbPasswd.equals(passwd)) {
					rs.close();  pstmt.close();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num);
					result = pstmt.executeUpdate();
				} else result = 0;
			} else result = -1;
		} catch(Exception e) {	System.out.println(e.getMessage()); 
		} finally { 
			if (pstmt != null) pstmt.close();
			if (conn !=null) conn.close();
		}
		return result;
	}
	

}