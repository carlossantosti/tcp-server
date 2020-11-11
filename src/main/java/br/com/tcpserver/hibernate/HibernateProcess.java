package br.com.tcpserver.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Classe responsável por processar a transação para o banco de dados.
 * 
 * @author Carlos Santos
 */
public class HibernateProcess {
	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * Método para salvar um objeto no banco.
	 * 
	 * @param obj - modelo a ser salvo no banco de dados.
	 * @return true se o processo foi bem sucedido e falso para mal sucedido.
	 */
	public static boolean save(Object obj) {
		
		Transaction transaction = null;
		Session session = null;
        try {
        	session = HibernateFactory.getSessionFactory().openSession();
            
        	transaction = session.beginTransaction();
            session.save(obj);
            transaction.commit();
            session.close();
            
            logger.info("Mensagem salva no BD: "+obj.toString());
            return true;
        } catch (Exception e) {
        	logger.error("Mensagem não foi salva no BD: "+obj.toString());
        	
            if (transaction != null) {
                transaction.rollback();
            }
            
            if (session != null && session.isOpen())
            	session.close();
            e.printStackTrace();
            
        }
        
        return false;
	}

}
