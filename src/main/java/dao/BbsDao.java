package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Statement;

import db.DBClose;
import db.DBConnection;
import dto.BbsDto;

public class BbsDao {

	private static BbsDao dao = null;
	
	private BbsDao() {
		DBConnection.initConnection();
	}
	
	public static BbsDao getInstance() {
		if(dao == null) {
			dao = new BbsDao();
		}
		return dao;
	}
	
	public List<BbsDto> getBbsList() {
		
		String sql = " select seq, id, ref, step, depth,"
				+ "			  title, content, wdate, del, readcount "
				+ "    from bbs "
				+ "    order by ref desc, step asc ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
				
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/4 getBbsList success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/4 getBbsList success");
			
			rs = psmt.executeQuery();
			System.out.println("3/4 getBbsList success");
			
			while(rs.next()) {
				
				BbsDto dto = new BbsDto(rs.getInt(1), 
										rs.getString(2), 
										rs.getInt(3), 
										rs.getInt(4), 
										rs.getInt(5), 
										rs.getString(6), 
										rs.getString(7), 
										rs.getString(8), 
										rs.getInt(9), 
										rs.getInt(10));
				
				list.add(dto);
			}
			System.out.println("4/4 getBbsList success");
			
		} catch (SQLException e) {	
			System.out.println("getBbsList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;		
	}
	public List<BbsDto> getBbsSearchList(String choice, String search) {
		
		String sql = " select seq, id, ref, step, depth,"
				+ "			  title, content, wdate, del, readcount "
				+ "    from bbs ";
		
		String searchSql="";
		if(choice.equals("title")) {
			searchSql="where title like '%"+ search+"%'";
		}else if(choice.equals("content")) {
			searchSql="where content like '%"+ search+"%'";
			
		}else if(choice.equals("writer")) {
			searchSql="where id='"+ search+"'";
			
		}
		sql+=searchSql;
		
		sql	+= "    order by ref desc, step asc ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
				
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/4 getBbsList success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/4 getBbsList success");
			
			rs = psmt.executeQuery();
			System.out.println("3/4 getBbsList success");
			
			while(rs.next()) {
				
				BbsDto dto = new BbsDto(rs.getInt(1), 
										rs.getString(2), 
										rs.getInt(3), 
										rs.getInt(4), 
										rs.getInt(5), 
										rs.getString(6), 
										rs.getString(7), 
										rs.getString(8), 
										rs.getInt(9), 
										rs.getInt(10));
				
				list.add(dto);
			}
			System.out.println("4/4 getBbsList success");
			
		} catch (SQLException e) {	
			System.out.println("getBbsList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;		
	}
public List<BbsDto> getBbsPageList(String choice, String search, int pageNumber) {
		
		String sql = " select seq, id, ref, step, depth, title, content, wdate, del, readcount "
				+ " from "
				+ " (select row_number()over(order by ref desc, step asc) as rnum,"
				+ "	seq, id, ref, step, depth, title, content, wdate, del, readcount "
				+ " from bbs ";

		String searchSql = "";
		if(choice.equals("title")) {
			searchSql = " where title like '%" + search + "%' ";
		}
		else if(choice.equals("content")) {
			searchSql = " where content like '%" + search + "%' ";
		} 
		else if(choice.equals("writer")) {
			searchSql = " where id='" + search + "' "; 
		} 
		sql += searchSql;
		
		sql += 	  " order by ref desc, step asc) a "
				+ " where rnum between ? and ? ";
				
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		// pageNumber(0, 1, 2...)
		int start, end;
		start = 1 + 10 * pageNumber;	//  1 11 21 31 41
		end = 10 + 10 * pageNumber;		// 10 20 30 40 50
		
		List<BbsDto> list = new ArrayList<BbsDto>();
				
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/4 getBbsPageList success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, start);
			psmt.setInt(2, end);
			System.out.println("2/4 getBbsPageList success");
			
			rs = psmt.executeQuery();
			System.out.println("3/4 getBbsPageList success");
			
			while(rs.next()) {
				
				BbsDto dto = new BbsDto(rs.getInt(1), 
										rs.getString(2), 
										rs.getInt(3), 
										rs.getInt(4), 
										rs.getInt(5), 
										rs.getString(6), 
										rs.getString(7), 
										rs.getString(8), 
										rs.getInt(9), 
										rs.getInt(10));
				
				list.add(dto);
			}
			System.out.println("4/4 getBbsPageList success");
			
		} catch (SQLException e) {	
			System.out.println("getBbsList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;		
	}
//글의 총수
	public int getAllBbs(String choice, String search) {
		
		String sql = " select count(*) from bbs ";
		
		String searchSql = "";
		if(choice.equals("title")) {
			searchSql = " where title like '%" + search + "%'";
		}
		else if(choice.equals("content")) {
			searchSql = " where content like '%" + search + "%'";
		} 
		else if(choice.equals("writer")) {
			searchSql = " where id='" + search + "'"; 
		} 
		sql += searchSql;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
				
			psmt = conn.prepareStatement(sql);
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return count;
	}
	
	public boolean writeBbs(BbsDto dto) {
		
		String sql = " insert into bbs(id, ref, step, depth, title, content, wdate, del, readcount)"
				+ "    values(?, "
				+ "       (select ifnull(max(ref), 0)+1 from bbs b), 0, 0, "
				+ "       ?, ?, now(), 0, 0) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 writeBbs success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			System.out.println("2/3 writeBbs success");
			
			count = psmt.executeUpdate();	
			System.out.println("3/3 writeBbs success");
			
		} catch (SQLException e) {
			System.out.println("writeBbs fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return count>0?true:false;
	}
	
	public BbsDto getBbs(int seq) {
		String sql=" select * from bbs "
				+" where seq=? ";
		
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		BbsDto b=null;
		
		try {
			conn=DBConnection.getConnection();
			System.out.println("getBbs success 1/4");
			psmt=conn.prepareStatement(sql);
			System.out.println("getBbs success 2/4");
			
			psmt.setInt(1, seq);
			
			rs=psmt.executeQuery();
			System.out.println("getBbs success 3/4");
			
			
			while(rs.next()) {
				b =new BbsDto(rs.getInt(1),
						rs.getString(2), 
						rs.getInt(3), 
						rs.getInt(4), 
						rs.getInt(5), 
						rs.getString(6), 
						rs.getString(7), 
						rs.getString(8), 
						rs.getInt(9), 
						rs.getInt(10));
			}
			System.out.println("getBbs success 4/4");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("getBbs fail");
		}finally {
			
			DBClose.close(conn, psmt, null);
		}
		return b;
	}
	
	public boolean answer(int seq,BbsDto dto) {
		String sql1 = " update bbs "
				+ " set step=step+1 "
				+ " where ref=(select ref from (select ref from bbs a where seq=?) A) "
				+ "	  and step>(select step from (select step from bbs b where seq=?) B) ";

		
		String sql2 = " insert into bbs(id, ref, step, depth, title, content, wdate, del, readcount) "
				+ " values(?, "
				+ "		(select ref from bbs a where seq=?), "
				+ "		(select step from bbs b where seq=?)+1, "
				+ "		(select depth from bbs c where seq=?)+1, "
				+ "		?, ?, now(), 0, 0) ";

		
		Connection conn=null;
		PreparedStatement psmt=null;
		
		int count1=0;
		int count2=0;
		// commit 비활성화   commit(=적용) / rollback
		try {
			conn=DBConnection.getConnection();
			
			conn.setAutoCommit(false); // 비활성화
			
			//update 
			psmt=conn.prepareStatement(sql1);
			psmt.setInt(1,seq);
			psmt.setInt(2,seq);
			
			count1=psmt.executeUpdate();
			/// psmt 초기화 
			psmt.clearParameters();
			
			
			//insert
			psmt=conn.prepareStatement(sql2);
			psmt.setString(1, dto.getId());
			psmt.setInt(2, seq);
			psmt.setInt(3, seq);
			psmt.setInt(4, seq);
			psmt.setString(5, dto.getTitle());
			psmt.setString(6, dto.getContent());
			
			count2=psmt.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} // 문제 발생 시 rollback
			e.printStackTrace();
		}finally {
			try {
				conn.setAutoCommit(true); // commit 다시 켜줌
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBClose.close(conn, psmt, null);
			
		}
		
		return (count2>0);
	}

	public boolean deleteBbs(int seq) {
		
		String sql=" update bbs set del=1 where seq=?";
		
		int count=0;
		
		Connection conn=null;
		PreparedStatement psmt=null;
		
		try {
			conn=DBConnection.getConnection();
			System.out.println("deleteBbs success 1/3");
			psmt=conn.prepareStatement(sql);
			
			System.out.println("deleteBbs success 2/3");
			psmt.setInt(1, seq);
			
			count=psmt.executeUpdate();
			System.out.println("deleteBbs success 3/3");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("deleteBbs  fail");
		}finally {
			DBClose.close(conn, psmt, null);
		}
		
		
		return count>0;
	}
	public boolean updateBbs(int seq,String title, String content) {
		
	String sql=" update bbs set title=?,content=? where seq=?";
		int count=0;
		
		Connection conn=null;
		PreparedStatement psmt=null;
		
		try {
			conn=DBConnection.getConnection();
			System.out.println("updateBbs success 1/3");
			psmt=conn.prepareStatement(sql);
			
			System.out.println("updateBbs success 2/3");
			psmt.setString(1, title);
			psmt.setString(2, content);
			psmt.setInt(3, seq);
			
			count=psmt.executeUpdate();
			System.out.println("updateBbs success 3/3");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("updateBbs  fail");
		}finally {
			DBClose.close(conn, psmt, null);
		}
		
		
		return count>0;
		
		
	}
	public void countBbs(int seq) {
		String sql=" update bbs set readcount=readcount+1 where seq=?";
		
		Connection conn=null;
		PreparedStatement psmt=null;
		
		try {
			conn=DBConnection.getConnection();
			System.out.println("countBbs success 1/3");
			psmt=conn.prepareStatement(sql);
			
			System.out.println("countBbs success 2/3");
			psmt.setInt(1, seq);
			
			psmt.executeUpdate();
			System.out.println("countBbs success 3/3");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("countBbs  fail");
		}finally {
			DBClose.close(conn, psmt, null);
		}
		
		
		
	}
}





