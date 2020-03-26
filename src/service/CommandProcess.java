package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandProcess {
	//인터페이스에서 쓰는 메소드는 추상메소드
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
