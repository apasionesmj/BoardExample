package control;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.CommandProcess;
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> commandMap = new HashMap<String, Object>();
	public void init(ServletConfig config) throws ServletException { 
	   	//web.xml에서 propertyConfig에 해당하는 init-param 의 값을 읽어옴
	   	String props = config.getInitParameter("config");
	  //명령어와 처리클래스의 매핑정보를 저장할 Properties객체 생성
	    Properties pr = new Properties();
	    FileInputStream f = null;
	    try {
	          String configFilePath = 
	        		  config.getServletContext().getRealPath(props);
	          f = new FileInputStream(configFilePath);
	        //command.properties파일의 정보를  Properties객체에 저장
	          pr.load(f);
	     } catch (IOException e) { throw new ServletException(e);
	     } finally {
	          if (f != null) try { f.close(); } catch(IOException ex) {}
	     }
	  //Iterator객체는 Enumeration객체를 확장시킨 개념의 객체
	     Iterator keyIter = pr.keySet().iterator();
	   //객체를 하나씩 꺼내서 그 객체명으로 Properties객체에 저장된 객체에 접근
	     while( keyIter.hasNext() ) {
	          String command = (String)keyIter.next();         // /list.do
	          String className = pr.getProperty(command); // service.ListAction
	          try {
	               Class commandClass = Class.forName(className);//해당 문자열을 클래스로 만든다.
	               Object commandInstance = commandClass.newInstance();//해당클래스의 객체를 생성
	               commandMap.put(command, commandInstance); // Map객체인 commandMap에 객체 저장
	          } catch (Exception e) {
	               throw new ServletException(e);
	          }
	     }
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	        requestPro(request, response);
	}
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	       requestPro(request, response);
	}
	//시용자의 요청을 분석해서 해당 작업을 처리
	private void requestPro(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String view = null;
	    CommandProcess com=null;
	    try {	String command = request.getRequestURI();
				System.out.println(command);  // /ch16/list.do
			//	System.out.println(request.getContextPath()); // /ch16
			//	System.out.println(command.indexOf(request.getContextPath()));  // 0
	       //  if (command.indexOf(request.getContextPath()) == 0) {
	              command = command.substring(request.getContextPath().length());
	        //  }
	          com = (CommandProcess)commandMap.get(command);  
	          view = com.requestPro(request, response);
	    } catch(Throwable e) { throw new ServletException(e); } 
	    RequestDispatcher dispatcher =   request.getRequestDispatcher(view);
	            dispatcher.forward(request, response);
	}
}