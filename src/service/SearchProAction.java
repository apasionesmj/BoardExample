package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Board;
import dao.BoardDao;

public class SearchProAction implements CommandProcess {

	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			BoardDao bd = BoardDao.getInstance();
			try { 
				// 전체 갯수를 가져와라 
				int totCnt  = bd.getTotalCnt();			
				String pageNum = request.getParameter("pageNum");	
				String search = request.getParameter("search");	
				if (pageNum==null || pageNum.equals("")) {	pageNum = "1";	}
				int currentPage = Integer.parseInt(pageNum);
				//     한 Page안 갯수     화면안 Page 갯수 
				int pageSize  = 10, blockSize = 10;
				int startRow = (currentPage - 1) * pageSize + 1;  // 1
				int endRow   = startRow + pageSize - 1;           // 10 
				int startNum = totCnt - startRow + 1;
				List<Board> searchList = bd.searchList(search, startRow, endRow);	
				// 3.8보다 큰수중 가장 작은 정수
				int pageCnt = (int)Math.ceil((double)totCnt/pageSize);  // 4
				int startPage = (int)(currentPage-1)/blockSize*blockSize + 1;
				int endPage = startPage + blockSize -1;	   // 10 
				// 있는 Data만 보자 
				if (endPage > pageCnt) endPage = pageCnt;	
				request.setAttribute("totCnt", totCnt);
				request.setAttribute("pageNum", pageNum);
				request.setAttribute("currentPage", currentPage);
				request.setAttribute("startNum", startNum);
				request.setAttribute("searchList", searchList);
				request.setAttribute("blockSize", blockSize);
				request.setAttribute("pageCnt", pageCnt);
				request.setAttribute("startPage", startPage);
				request.setAttribute("endPage", endPage);
				 
				System.out.println("-----------------------------------------------");  // /ch16/list.do
				System.out.println("startNum-->" + startNum);  // /ch16/list.do
				System.out.println("totCnt-->" + totCnt);  // /ch16/list.do
				System.out.println("currentPage-->" + currentPage);  // /ch16/list.do
				System.out.println("blockSize-->" + blockSize);  // /ch16/list.do
				System.out.println("pageSize-->" + pageSize);  // /ch16/list.do
				System.out.println("pageCnt-->" + pageCnt);  // /ch16/list.do
				System.out.println("startPage-->" + startPage);  // /ch16/list.do
				System.out.println("endPage-->" + endPage);  // /ch16/list.do
				
			} catch(Exception e) { System.out.println(e.getMessage()); }
			return "list.jsp";
		}}
