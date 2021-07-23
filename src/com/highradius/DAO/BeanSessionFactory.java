package com.highradius.DAO;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class BeanSessionFactory {
	
	private static SessionFactory factory;
	private static ServiceRegistry serviceRegistry;
	
	public SessionFactory initializeFactory() {
		System.out.println("Beansessionfactory working!!!\n\n");
		try {
			if (factory == null) 
			{
				Configuration configuration = new Configuration();
				configuration.configure();
				serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
				factory = configuration.buildSessionFactory(serviceRegistry);
			}

		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		return factory;
	}
	
}
