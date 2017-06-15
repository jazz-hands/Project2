package music.data;

/**
*
* @author jasmi
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import music.business.Product;

public class ProductDB {
  
  public static void updateProduct(Product product)
  {       
    if(ProductDB.productExists(product.getCode())) {
      ProductDB.update(product);
      ProductIO.updateProduct(product);
      
    }
    else {
      ProductDB.insert(product);
      ProductIO.insertProduct(product);
      
    }
  }
  
  public static void deleteProduct(Product product)
  {       
    if(ProductIO.exists(product.getCode())) {
      ProductIO.deleteProduct(product);
      ProductDB.remove(product);
    }
    else {
      ProductIO.updateProduct(product);
    }
  }
  
  //This method returns null if a product isn't found.
  public static Product selectProduct(String productCode) {
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection connection = pool.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    String query = "SELECT * FROM Product "
    + "WHERE ProductCode = ?";
    try {
      ps = connection.prepareStatement(query);
      ps.setString(1, productCode);
      rs = ps.executeQuery();
      if (rs.next()) {
        Product p = new Product();
        p.setId(rs.getLong("ProductID"));
        p.setCode(rs.getString("ProductCode"));
        p.setDescription(rs.getString("ProductDescription"));
        p.setPrice(rs.getDouble("ProductPrice"));
        return p;
      } else {
        return null;
      }
    } catch (SQLException e) {
      System.err.println(e);
      return null;
    } finally {
      DBUtil.closeResultSet(rs);
      DBUtil.closePreparedStatement(ps);
      pool.freeConnection(connection);
    }
  }
  
  //This method returns null if a product isn't found.
  public static Product selectProduct(long productID) {
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection connection = pool.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    String query = "SELECT * FROM Product "
    + "WHERE ProductID = ?";
    try {
      ps = connection.prepareStatement(query);
      ps.setLong(1, productID);
      rs = ps.executeQuery();
      if (rs.next()) {
        Product p = new Product();
        p.setId(rs.getLong("ProductID"));
        p.setCode(rs.getString("ProductCode"));
        p.setDescription(rs.getString("ProductDescription"));
        p.setPrice(rs.getDouble("ProductPrice"));
        return p;
      } else {
        return null;
      }
    } catch (SQLException e) {
      System.err.println(e);
      return null;
    } finally {
      DBUtil.closeResultSet(rs);
      DBUtil.closePreparedStatement(ps);
      pool.freeConnection(connection);
    }
  }
  
  //This method returns null if a product isn't found.
  public static List<Product> selectProducts() {
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection connection = pool.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    String query = "SELECT * FROM Product";
    try {
      ps = connection.prepareStatement(query);
      rs = ps.executeQuery();
      ArrayList<Product> products; products = new ArrayList<Product>();
      while (rs.next()) {
        Product p = new Product();
        p.setCode(rs.getString("ProductCode"));
        p.setDescription(rs.getString("ProductDescription"));
        p.setPrice(rs.getDouble("ProductPrice"));
        products.add(p);
      }
      return products;
    } catch (SQLException e) {
      System.err.println(e);
      return null;
    } finally {
      DBUtil.closeResultSet(rs);
      DBUtil.closePreparedStatement(ps);
      pool.freeConnection(connection);
    }
  }
  
  public static void insert(Product product) {
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection connection = pool.getConnection();
    PreparedStatement ps = null;
    
    String query
    = "INSERT INTO product (ProductCode, ProductDescription, ProductPrice) "
    + "VALUES (?, ?, ?)";
    try {
      ps = connection.prepareStatement(query);
      ps.setString(1, product.getCode());
      ps.setString(2, product.getDescription());
      ps.setDouble(3, product.getPrice());
      
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e);
    } finally {
      DBUtil.closePreparedStatement(ps);
      pool.freeConnection(connection);
    }
    
  }
  
    
  public static void update(Product product) {
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection connection = pool.getConnection();
    PreparedStatement ps = null;
    Product temp = ProductDB.selectProduct(product.getCode());
    Long tempId = temp.getId();
    System.out.println("Product Code: "+tempId);
    String query = "UPDATE Product SET "
                + "ProductCode = ?, "
                + "ProductDescription = ?, "
                + "ProductPrice = ? "
                + "WHERE ProductID = ?";
    try {
      ps = connection.prepareStatement(query);
      ps.setString(1, product.getCode());
      ps.setString(2, product.getDescription());
      ps.setDouble(3, product.getPrice());
      ps.setLong(4, tempId);
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e);
    } finally {
      DBUtil.closePreparedStatement(ps);
      pool.freeConnection(connection);
    }
    
  }
  
  public static void remove(Product product) {
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection connection = pool.getConnection();
    PreparedStatement ps = null;
    
    Product productTemp = ProductDB.selectProduct(product.getCode()); 
    String query
    = "DELETE FROM Product "
    + "WHERE ProductID = ?";

    try {
      ps = connection.prepareStatement(query);
      ps.setLong(1, productTemp.getId());
      
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e);
    } finally {
      DBUtil.closePreparedStatement(ps);
      pool.freeConnection(connection);
    }
  }
  
      public static boolean productExists(String productCode) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT ProductID FROM Product "
                + "WHERE ProductCode = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, productCode);
            rs = ps.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
  
}
