package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.PRIVATE_MEMBER;

import dao.AnalyseDao;
import dao.ConnectDao;
import dao.DaoFactory;
import dao.DeviceDao;
import server.Server;

/**
 * Servlet implementation class index
 */
@WebServlet("/index")
public class index extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Server server = new Server();
	private  DeviceDao deviceDao;
	private  ConnectDao connectDao;
	private  AnalyseDao analyseDao;
	private  DaoFactory daoFactory;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    @Override
    public void init() throws ServletException{
    	daoFactory = DaoFactory.getInstance();
    	deviceDao=daoFactory.getDeviceDao();
    	connectDao=daoFactory.getConnectDao();
    	analyseDao=daoFactory.getAnalyseDao();
    }
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int dcount,ccount,acount;
		dcount=deviceDao.getCountDevice();
		ccount=connectDao.getCountConnect();
		acount=analyseDao.getCountAnalyse();
		try {
			request.setAttribute("ccount",ccount);
			request.setAttribute("dcount",dcount);
			request.setAttribute("acount",acount);
			request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
