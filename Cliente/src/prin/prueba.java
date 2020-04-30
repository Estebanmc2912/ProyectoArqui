package prin;



import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sun.messaging.jmq.jmsserver.core.Session;
import com.sun.xml.ws.security.impl.policy.RecipientEncryptionToken;

public class prueba{
	public static void main(String[] args) {
		InitialContext ctx=null;
		QueueConnectionFactory queueConnectionFactory;
		QueueConnection queueConnection=null;
		QueueSession queueSession;
		Queue queue;
		QueueReceiver receiver;
		Message msg;
		List<Message> list;
		TextMessage tmsg;
		ObjectMessage obj;
		
		try {
			ctx=new InitialContext();
			queueConnectionFactory=(QueueConnectionFactory)ctx.lookup("jms/ProyectConnectionFactory");
			queueConnection=queueConnectionFactory.createQueueConnection();
			queueConnection.start();
			queueSession=queueConnection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
			queue =(Queue) ctx.lookup("jms/ProyectQueue");
			receiver= queueSession.createReceiver(queue);
			obj=(ObjectMessage) receiver.receive(10);
			
				if(obj instanceof ObjectMessage) {
					list=(List<Message>)obj;
					System.out.println("Mensaje: "+list.get(1));
				}
				obj = (ObjectMessage) receiver.receive(10);
			
			receiver.close();
			queueSession.close();
		}catch(NamingException | JMSException e) {
			e.printStackTrace();
		}finally {
			if(queueConnection!=null) {
				try {
					queueConnection.close();
				}catch(JMSException jsme) {
					System.out.println("error: "+jsme.toString());
				}
			}
		}
		
		
	}

	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public prueba() {
		super();
	}

}