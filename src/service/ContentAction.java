package service;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Board;
import dao.BoardDao;
public class ContentAction implements CommandProcess{
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//1. Get : num, pageNum
		//2. BoardDao : Instance -> bd
		//3. Board board = bd.select(num)
		//4. 저장 : num, pageNum, board
		try {
			int num = Integer.parseInt(request.getParameter("num"));
			String pageNum = request.getParameter("pageNum");
			BoardDao bd = BoardDao.getInstance();
			bd.readCount(num); 
			Board board = bd.select(num);
			
			request.setAttribute("num", num);
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("board", board);
		} catch(Exception e) { System.out.println(e.getMessage()); }
		return "content.jsp";
	}
}