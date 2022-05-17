package ai.jobiak.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShoppingCartServlet
 */
@WebServlet("/cart")
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ShoppingCartServlet() {
    	super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		for(int i=0;i<=10;i++) {
			out.println("<a href='cart?item="+i+"'>Add Item</a>"+i+"</br>");
		}
		String queryStr=request.getQueryString();
	//	System.out.println(queryStr);
		String splitArray[]=queryStr.split("=");
		String productId=splitArray[1];
	//	System.out.println(productId);
		ArrayList<Product>itemsList=null;
		HttpSession shoppingCart=request.getSession();
		
		if(shoppingCart.isNew()) {
			itemsList=new ArrayList<>();
			shoppingCart.setAttribute("items",itemsList);//session
		}else {
			itemsList=(ArrayList<Product>)shoppingCart.getAttribute("items");
		//	Product p=new Product("AFJ101tR","XHW5 QuadCopter",5375.00);
		//	itemsList.add(p);
			itemsList.add(new Product(productId,"ABC",78.00));
			shoppingCart.setAttribute("items",itemsList);
		//	for(Product product:itemsList) {
		//		out.println("Description :<h3> " + product.getDescription()+"</h3>");
		//	}
			for(Product p:itemsList) {
			out.println(p.getProductId()+" : "+p.getDescription()+" : "+p.getPrice()+"</br>");
			}
		}
	}

}
