package com.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.model.Stud;
import com.utility.HibernateUtility;
import com.utility.ScannerUtilty;

public class StudentDaoImpl 
{
	public static Scanner sc=ScannerUtilty.getScanner();
	public static SessionFactory sf=HibernateUtility.getHibernateConnection();
	
	public static Stud stud=null;
	public static Session session=null;
	public static Transaction transaction=null;
	
	public static void add(Stud stud)
	{
		try {
			session=sf.openSession();
			transaction=session.beginTransaction();
			
			//return serilizable object with primary key 
			session.save(stud);
			//session.persist(stud);
			
			//before commit if we made changes in persisted state object even after save was called ,
			//then if we make commit or flush that object to db , it push that persisted state object to db .
			//because changes are within transaction boundry 
			System.out.println("after session save method :: Enter name :");
			stud.setName(sc.next());
			
			//transaction.commit();
			session.flush();
			
			System.out.println("Record Inserted Successfully...");
			
		} catch (Exception e) {
			if(transaction!=null) transaction.rollback();
			throw e;
		}finally {
			session.close();
		}
		
	}
	
	public static void update(Stud stud)
	{
		try {
			
			session=sf.openSession();
			transaction=session.beginTransaction();
			session.update(stud);
			transaction.commit();
			System.out.println("successfully updated...");
			
		} catch (Exception e) {
			if(transaction!=null) transaction.rollback();
			throw e;
		}finally {
			session.close();
		}
		
	}
	
	
	public static void updateTest1()
	{

		session=sf.openSession();
		transaction=session.beginTransaction();

		Stud s=new Stud();

		System.out.println("You should know the ID First :");
		
		System.out.println("Enter ID :");
		s.setId(sc.nextInt());	  
		
		//open session 1
		s=session.get(Stud.class, s.getId());
		System.out.println("session Hashcode "+session.hashCode());
		System.out.println("Enter name :");
		s.setName(sc.next());
		
		session.update(s);
		
		transaction.commit();
		System.out.println("Name updated..");
		
		//close session 1
		session.close();
		
		
		//open session 2
	    Session session2=sf.openSession();
	    System.out.println("session Hashcode "+session2.hashCode());
		transaction=session2.beginTransaction();
		
		
		
		System.out.println("Enter Mobile Number :");
		s.setMobileNo(sc.next());
		
		// detach entity from previous session get persisted 
	    session2.update(s);
		
	    transaction.commit();
	   //close session 2
	    session2.close();
	
	}
	
	public static void delete(Stud stud)
	{
		try {
			session=sf.openSession();
			transaction=session.beginTransaction();
			session.delete(stud);
			
			transaction.commit();
			
		} catch (Exception e) {
			if(transaction!=null) transaction.rollback();
			throw e;
		}finally {
			session.close();
		}
	}
	
	public static Stud loadStudent(int sid)
	{	
		
		// load method return a proxy object if found in db or cache 
		// if object not found , it throw ObjectNotFound Exceptuon 
		session=sf.openSession();
		System.out.println("session obj Hashcode "+session.hashCode());
		
		transaction=session.beginTransaction();
		
		//if we don't open/start the transaction then session will remains open 
		// and if we dont't write above line then after do changes in return object of load method will not get persisted 
		// will get exception of transaction not started properly so we need to clear previous one and create new  and then persisted that object
		//  
		stud=session.load(Stud.class, sid);
//		transaction.commit();
		return stud;
	}
	
	public static Stud getStudent(int sid)
	{	
		session=sf.openSession();
		System.out.println("session obj Hashcode "+session.hashCode());
		
		transaction=session.beginTransaction();
		stud=session.get(Stud.class, sid);
//		transaction.commit();
		return stud;
	}
	
	public static List<Stud> getAllRecord()
	{
		List<Stud> slist=new ArrayList<Stud>();
		try {
			
			session=sf.openSession();
			transaction=session.beginTransaction();
			Criteria criteria=session.createCriteria(Stud.class);
			slist=criteria.list();
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) transaction.rollback();
			throw e;
		}finally {
			session.close();
		}
		
		return slist;
	}
	
	public static void saveOrUpdate(Stud stud)
	{
		try {
			session=sf.openSession();
			transaction=session.beginTransaction();
			session.saveOrUpdate(stud);
			transaction.commit();

		} catch (Exception e) {
			if(transaction!=null) transaction.rollback();
			throw e;
		}finally {
			session.close();
		}
	}
	
	public static void pushDirtyObj(){
		if(session!=null) {
			System.out.println("session obj Hashcode "+session.hashCode());
			session.getTransaction().commit();
			session.flush();
		}
	}
}	


