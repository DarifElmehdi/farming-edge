package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.AnalyseDevice;
import beans.ConnectDevice;
import dao.AnalyseDao;
import dao.ConnectDao;
import dao.DaoFactory;
import dao.DeviceDao;

/**
 * Servlet implementation class connections
 */
@WebServlet("/connections")
public class connections extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DeviceDao deviceDao;
	private ConnectDao connectDao;
	private AnalyseDao analyseDao;
	private DaoFactory daoFactory;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    @Override
    public void init() throws ServletException{
    	daoFactory= DaoFactory.getInstance();
    	deviceDao=daoFactory.getDeviceDao();
    	analyseDao=daoFactory.getAnalyseDao();
    	connectDao=daoFactory.getConnectDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			List<ConnectDevice> connections = deviceDao.getConnectDevice();
			request.setAttribute("connections",connections);
			this.getServletContext().getRequestDispatcher("/WEB-INF/connections.jsp").include(request,response);
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
