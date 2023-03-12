package com.utility;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility 
{
	
	public static SessionFactory getHibernateConnection()
	{
		Configuration cfg=new Configuration();
		cfg.configure();
		
		SessionFactory sf =cfg.buildSessionFactory();
		
		
		return sf;
	}
	
	public static SessionFactory getSecondConfigHibernateConnection()
	{
		Configuration cfg=new Configuration();
		cfg.configure("secondConfig.xml");
		
		SessionFactory sf =cfg.buildSessionFactory();
		
		
		return sf;
	}
}
