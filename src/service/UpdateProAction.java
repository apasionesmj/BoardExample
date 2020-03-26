package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Board;
import dao.BoardDao;

public class UpdateProAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				//1. Get - > pageNum, num, writer, email, subject, passwd,
				//2. Board board에 setter 이용해 Board DTO에 저장
				//3. BoardDao bd Instance
				//4. int result = bd.update(board)
				//5. 저장 result, num, pageNum
		try { 
			request.setCharacterEncoding("utf-8"); 
	        String pageNum = request.getParameter("pageNum");
	        Board board = new Board();
			board.setNum(Integer.parseInt(request.getParameter("num")));
	        board.setWriter(request.getParameter("writer"));
	        board.setEmail(request.getParameter("email"));
	        board.setSubject(request.getParameter("subject"));
	        board.setPasswd(request.getParameter("passwd"));
			board.setContent(request.getParameter("content"));
			board.setIp(request.getRemoteAddr());
			BoardDao bd = BoardDao.getInstance();
			int result = bd.update(board);
			
			request.setAttribute("result", result);
			request.setAttribute("num", board.getNum());
			request.setAttribute("pageNum", pageNum);
		} catch(Exception e) { 
			System.out.println(e.getMessage()); 
		}
		return "updatePro.jsp";
	}

}
