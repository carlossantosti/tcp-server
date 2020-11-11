package br.com.tcpserver.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Classe responsável por iniciar uma sessão ou fechar.
 * 
 * @author Carlos Santos
 */
public class HibernateFactory {
	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	/**
	 * Método para criar uma sessão.
	 * 
	 * @return a sessão criada.
	 */
	public static SessionFactory getSessionFactory() {
		
		if (sessionFactory == null) {
			try {
				
				registry = new StandardServiceRegistryBuilder().configure().build();
				MetadataSources sources = new MetadataSources(registry);

				Metadata metadata = sources.getMetadataBuilder().build();
				sessionFactory = metadata.getSessionFactoryBuilder().build();

			} catch (Exception e) {
				e.printStackTrace();
				
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
			}
		}

		return sessionFactory;
	}

	/**
	 * Método apra cancelar um registro e sua sessão.
	 */
	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}
