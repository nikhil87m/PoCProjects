package com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.dto.Employee;

public class AbstructDAO {
//	  @Autowired 
//	    private SessionFactory sessionFactory ;
	  
	  @PersistenceContext
	  public EntityManager entityManager;
	  
	  @Autowired
	  EntityManagerFactory entityManagerFactory;
	 
	  public CriteriaBuilder getCriteriaBuilder(){
			CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
			return  builder;
		}
	    protected Session getSession() {
//	        return sessionFactory.getCurrentSession();
//	    	return ((org.hibernate.ejb.EntityManagerImpl) entityManager.getDelegate()).getSession(); 
	    	return (Session) entityManager.getDelegate();
	    }
	    
	   /* protected EntityManager getEntityManager(){
	    	return entityManagerFactory.createEntityManager();
	    }*/
	 
	    public void persist(Object entity) {
	    	System.out.println("Inside abustruct DAO : persist : entity"+entity);
	    	getCriteriaBuilder().createQuery();
//	    	System.out.println("getSession()"+getSession());
	        getSession().persist(entity);
//	    	entityManager.getCriteriaBuilder().createQuery("selecet * from EMPLOYEE11 ", Employee.class);
	    }
	 
	    /*public void delete(Object entity) {
	        getSession().delete(entity);
	    }*/
	    
//	    @Override
	    public  List<Employee> findAll() {
	        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	        final CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
	        final Root<Employee> rootEntry = cq.from(Employee.class);
	        final CriteriaQuery<Employee> all = cq.select(rootEntry);
	        final javax.persistence.TypedQuery<Employee> allQuery = entityManager.createQuery(all);
	        return allQuery.getResultList();
	    }
	    
	    
}
