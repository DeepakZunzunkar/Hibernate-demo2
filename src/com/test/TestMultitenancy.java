package com.test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.model.Stud;
import com.utility.HibernateUtility;
import com.utility.Utility;

public class TestMultitenancy {

	//copy table data from one schema to another except primary key
	public static void main(String[] args) {
		
		List<Stud> slist=null;
		SessionFactory sf1 =HibernateUtility.getHibernateConnection();
		Session session1=null;
		Transaction tx1=null;
		try {
			
			session1=sf1.openSession();
//			tx1=session1.beginTransaction();
			Criteria criteria=session1.createCriteria(Stud.class);
			slist=criteria.list();
			Utility.displayRecords(slist);
//			session1.evict(slist);
//			tx1.commit();
		} catch (Exception e) {
			if(tx1!=null) tx1.rollback();
			throw e;
		}finally {
			session1.close();
			sf1.close();
		}
		
		//this is not inserting data into hibernatedemo2 schema  because we are sane hbm file 
		// and in this hbm file if we have specify catalog="schemaname" of any one of the schema then it used only that schema 
		// so don't specify that tag attribute then it work fine , data will inserted into second schema table
		SessionFactory sf2 =HibernateUtility.getSecondConfigHibernateConnection();
		Session session2=null;
		Transaction tx2=null;
		try {
			session2=sf2.openSession();
			tx2=session2.beginTransaction();
			if(slist != null && !slist.isEmpty()) {
				for(Stud student:slist) {
					session2.save(student);
				}
			}
			tx2.commit();
		} catch (Exception e) {
			if(tx2!=null) tx2.rollback();
			throw e;
		}finally {
			sf2.close();
		}
	}
}
