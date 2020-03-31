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
				request.setCharacterEncoding("utf-8");
				String pageNum = request.getParameter("pageNum");
				String search = request.getParameter("search");
				System.out.println("search-->" + search);  
				// 전체 갯수를 가져오기 위하여 정의된 search 를 getSearchCnt 에 보내준다. 
				int searchCnt  = bd.getSearchCnt(search);			
				System.out.println("searchCnt-->" + searchCnt); 
				//pageNum 이 아무 값도 아니거나 빈값이면 1로 지정해준다.
				if (pageNum==null || pageNum.equals("")) {	pageNum = "1";	}
				//현재페이지 기록하기 위하여 받은 PageNum 을 currentPage 에 삽입
				int currentPage = Integer.parseInt(pageNum);
				//한 Page안 갯수     화면안 Page 갯수				
				int pageSize  = 10, blockSize = 10;
				//시작 페이지를 구하기 위하여 (현재페이지 - 1) * 페이지 사이즈
				int startRow = (currentPage - 1) * pageSize + 1;  // 1
				//종료 페이지를 구하기 위하여 시작 번호 + 페이지 사이즈 - 1
				int endRow   = startRow + pageSize - 1;           // 10
				//시작번호를 구하기 위하여 검색 갯수 - 시작 번호 + 1 
				int startNum = searchCnt - startRow + 1;
				List<Board> searchList = bd.searchList(search, startRow, endRow);
				System.out.println("searchList.size()-->" + searchList.size());  // /ch16/list.do
				// 3.8보다 큰수중 가장 작은 정수
				int pageCnt = (int)Math.ceil((double)searchCnt/pageSize);  // 4
				int startPage = (int)(currentPage-1)/blockSize*blockSize + 1;
				int endPage = startPage + blockSize -1;	   // 10
				//검색 여부 -> 1은 검색 0 은 전체
				int SearchYN = 1;
				// 있는 Data만 보자 
				if (endPage > pageCnt) endPage = pageCnt;
				//request 영역에 Key의 이름으로 Value 값을 준다.
				//request.setAttribute("명칭", 값);
				//request.setAttribute("Key", Value);
				request.setAttribute("totCnt", searchCnt);
				request.setAttribute("pageNum", pageNum);
				request.setAttribute("currentPage", currentPage);
				request.setAttribute("startNum", startNum);
				request.setAttribute("list", searchList);
				request.setAttribute("blockSize", blockSize);
				request.setAttribute("pageCnt", pageCnt);
				request.setAttribute("startPage", startPage);
				request.setAttribute("endPage", endPage);
				request.setAttribute("SearchYN", SearchYN);
				//검색한 값을 유지하기 위하여 search 라고 별도의 명칭으로 부여
				request.setAttribute("search", search);
				 
				System.out.println("-----------------------------------------------");  // /ch16/list.do
				System.out.println("startNum-->" + startNum);  // /ch16/list.do
				System.out.println("totCnt-->" + searchCnt);  // /ch16/list.do
				System.out.println("currentPage-->" + currentPage);  // /ch16/list.do
				System.out.println("blockSize-->" + blockSize);  // /ch16/list.do
				System.out.println("pageSize-->" + pageSize);  // /ch16/list.do
				System.out.println("pageCnt-->" + pageCnt);  // /ch16/list.do
				System.out.println("startPage-->" + startPage);  // /ch16/list.do
				System.out.println("endPage--> " + endPage);  // /ch16/list.do
				
			} catch(Exception e) { System.out.println(e.getMessage()); }
			return "list.jsp";
		}}
