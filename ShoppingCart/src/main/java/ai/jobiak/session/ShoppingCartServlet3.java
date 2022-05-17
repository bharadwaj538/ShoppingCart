package ai.jobiak.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShoppingCartServlet3
 */
@WebServlet("/cart3")
public class ShoppingCartServlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCartServlet3() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    private ArrayList<Product>getProducts(){
    	String url="jdbc:mysql://localhost:3306/world";
    	String userName="root";
    	String password="admin";
    	ArrayList<Product>productList=new ArrayList<>();
    	Connection con=null;
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		
    		con=DriverManager.getConnection(url,userName,password);
    		
    		Statement st=con.createStatement();
    		String sql="Select * from product";
    		ResultSet rs=st.executeQuery(sql);
    		
    		while(rs.next()) {
    			Product p=new Product(rs.getString(1),rs.getString(2),rs.getDouble(3));
    			productList.add(p);
    		}
    	}
    		catch(Exception e) {
    			e.printStackTrace();
    		}finally {
    			if(con!=null)
    			{
    				try {
    					con.close();
    				}catch(SQLException e) {
    					e.printStackTrace();
    				}
    			}
    		}
		return productList;
    	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		ArrayList<Product>itemsList=null;
		itemsList=getProducts();
		out.println("</hr>");
		for(int i=0;i<itemsList.size();i++) {
			out.println("<a href='cart3?item="+itemsList.get(i).getProductId()+"'>AddItem"+itemsList.get(i).getProductId()+"</a></br>");
		}
		out.println("</hr>");
		HttpSession shoppingCart=request.getSession();
		ArrayList<Product>selectedItemsList=null;
		if(shoppingCart.isNew()) {
			selectedItemsList=new ArrayList<>();
			shoppingCart.setAttribute("Items", selectedItemsList);
		}else {
			String queryStr=request.getQueryString();
			String splitArray[]=queryStr.split("=");
			String productId=splitArray[1];
			for(int i=0;i<itemsList.size();i++) {
				if(itemsList.get(i).getProductId().equals(productId)) {
					selectedItemsList=(ArrayList<Product>)shoppingCart.getAttribute("items");
					selectedItemsList.add(itemsList.get(i));
					shoppingCart.setAttribute("items",selectedItemsList);
					break;
				}
			}
		}
		
		for(Product p:selectedItemsList) {
			out.println(p.getProductId()+" : "+p.getDescription()+" : "+p.getPrice()+"</br>");
		}
		out.println("</hr>");
		out.println("<h3>Items in the cart #"+selectedItemsList.size());
	}

}
