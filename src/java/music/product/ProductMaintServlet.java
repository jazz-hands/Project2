package music.product;

import music.business.Product;
import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import music.data.ProductDB;

public class ProductMaintServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = "/admin/displayProducts.jsp";
        ServletContext sc = getServletContext();

        // get current action
        String action = request.getParameter("action");
                
        if (action == null) {
            action = "displayProducts";
        }

        if (action.equals("displayProducts")) {
            url = "/admin/displayProducts.jsp";
        } else if (action.equals("addProduct")) {
            url = addProduct(request, response);
        } else if (action.equals("updateProduct")) {
            url = updateProduct(request, response);
        } else if (action.equals("deleteProduct")) {
            url = deleteProduct(request, response);
        } else if (action.equals("confirmProduct")) {
            url = confirmProduct(request,response);
        }

        sc.getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        List<Product> products = ProductDB.selectProducts();
        session.setAttribute("products", products);

        doPost(request,response);
    }


    //Use this method to add and update a product
    public String addProduct(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
          
        // get the product data
         String productCode = request.getParameter("productCode");

         HttpSession session = request.getSession();

         if(productCode!= null) {
//              store the data in a Product object
            Product product = new Product();
            product.setCode(productCode);
            product.setDescription(ProductDB.selectProduct(productCode).getDescription());
            product.setPrice(ProductDB.selectProduct(productCode).getPrice());

            session.setAttribute("product", product);
         }
         else {
             session.removeAttribute("product");
         }
         
        return "/admin/product.jsp";
     }
    
    public String updateProduct(HttpServletRequest request,
            HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        
        String productCode = request.getParameter("productCode");
        String description = request.getParameter("description");
        String priceString = request.getParameter("price");
        double price = Double.parseDouble(priceString);
        
        Product product = new Product();
        product.setCode(productCode);
        product.setDescription(description);
        product.setPrice(price);
        
        if(ProductDB.productExists(productCode)){
            Long productId = ProductDB.selectProduct(productCode).getProductId();
            product.setProductId(productId);
        }
        
        ProductDB.updateProduct(product);

        List<Product> products = ProductDB.selectProducts();
        session.setAttribute("products", products);

        return "/admin/displayProducts.jsp";

    }

     public String deleteProduct(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
          
        // get the product data
         String productCode = request.getParameter("productCode");

         HttpSession session = request.getSession();

         if(ProductDB.productExists(productCode) && productCode!= null) {
            //store the data in a Product object
            Product product = ProductDB.selectProduct(productCode);
            //delete prduct from DB
            ProductDB.deleteProduct(product);
            //update product list in memory
            List<Product> products = ProductDB.selectProducts();
            session.setAttribute("products", products);
            //reset the session attribute
            session.removeAttribute("product");
         }
         
         
        return "/admin/displayProducts.jsp";
    }
        
        public String confirmProduct(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
          
        // get the product data
         String productCode = request.getParameter("productCode");

         HttpSession session = request.getSession();

         if(ProductDB.productExists(productCode) && productCode!= null) {
            Product product = ProductDB.selectProduct(productCode);
            session.setAttribute("product", product);
         }
         else {
             session.removeAttribute("product");
         }
         
        return "/admin/confirm.jsp";
        
     }

}
