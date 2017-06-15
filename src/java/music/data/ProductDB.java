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
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
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
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT p FROM Product p " +
                "WHERE p.code = :code";
        TypedQuery<Product> q = em.createQuery(qString, Product.class);
        q.setParameter("code", productCode);
        try {
            Product product = q.getSingleResult();
            return product;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
  }
  
  //This method returns null if a product isn't found.
  public static List<Product> selectProducts() {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT p FROM Product p";
        TypedQuery<Product> q = em.createQuery(qString, Product.class);
        List<Product> products;
        try {
             products = q.getResultList();
             if(products == null || products.isEmpty()) {
                 products = null;
             }
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
        return products;
  }
  
  public static void insert(Product product) {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.persist(product);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }    
  }
  
    
  public static void update(Product product) {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();       
        try {
            em.merge(product);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }    
  }
  
  public static void remove(Product product) {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.remove(em.merge(product));
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }  
  }
  
      public static boolean productExists(String productCode) {
        Product p = selectProduct(productCode);   
        return p != null;
    }
  
}
