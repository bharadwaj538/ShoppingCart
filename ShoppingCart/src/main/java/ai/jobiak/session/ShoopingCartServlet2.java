package ai.jobiak.session;

import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class ShoopingCartServlet2
 */
@WebServlet("/cart2")
public class ShoopingCartServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoopingCartServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    private String[]getProductIds(){
    	String url="jdbc:mysql://localhost:3306/world";
    	String userName="root";
    	String password="admin";
    	String productIds[]=new String[10];
    	
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		Connection con=DriverManager.getConnection(url,userName,password);
    		
    		Statement st=con.createStatement();
    		String sql="Select productId from product";
    		ResultSet rs=st.executeQuery(sql);
    		int i=0;
    		while(rs.next()) {
    			productIds[i]=rs.getString(1);
    			i++;
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return productIds;
    	}
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
				PrintWriter out=response.getWriter();
				response.setContentType("text/html");
				String productIds[]=getProductIds();
				
			
				for(int i=0;i<productIds.length;i++) {
					out.println("<a href='cart2?item="+productIds[i]+"'>Add Item</a>"+productIds[i]+"</br>");
				
				}
				

				ArrayList<Product>itemsList=null;
				HttpSession shoppingCart=request.getSession();
			
				if(shoppingCart.isNew()) {
					itemsList=new ArrayList<>();
					shoppingCart.setAttribute("items",itemsList);//session
				}else {
					String queryStr=request.getQueryString();
					String splitArray[]=queryStr.split("=");
					String productId=splitArray[1];
					
					
					itemsList=(ArrayList<Product>)shoppingCart.getAttribute("items");
			
					itemsList.add(new Product(productId,"ABC",78.00));
	
					shoppingCart.setAttribute("items",itemsList);
				}
					for(Product p:itemsList) {
					out.println(p.getProductId()+" : "+p.getDescription()+" : "+p.getPrice()+"</br>");
					}
	
				out.println("<h3>Items in the cart #"+itemsList.size());
			
	}
}
