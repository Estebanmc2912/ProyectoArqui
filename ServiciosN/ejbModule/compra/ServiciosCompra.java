package compra;


import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Token;


import ldatos.DelegadoDatosLocal;
import model.Factura;
import model.Producto;
import model.Usuario;

/**
 * Session Bean implementation class ServiciosCompra
 */
@Stateless
@LocalBean
public class ServiciosCompra implements ServiciosCompraLocal {
	
	@EJB DelegadoDatosLocal dl;
    /**
     * Default constructor. 
     */
    public ServiciosCompra() {
    }
  

	@Override
	public int verificarTarjeta() {
		return 0;
	}

	@Override
	public Usuario confirmarCompra(String year,String mes, String num, String cod,Usuario u){
		Stripe.apiKey = "sk_test_BdylSOtvR6If0db3N37Z6su300ajEGRqp3";
		Customer cust=null;
		
		try {
			if(u.getPersonalKey()==null) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("email",u.getCorreo());
				cust=Customer.create(map);
				u.setPersonalKey(cust.getId());
			}else{
				cust=Customer.retrieve(u.getPersonalKey());
			}
			Map<String,Object> cardParameter=new HashMap<String,Object>();
			cardParameter.put("number", num);
			cardParameter.put("exp_month", mes);
			cardParameter.put("exp_year", year);
			cardParameter.put("cvc", cod);
			
			Map<String,Object> tokenParams=new HashMap<String,Object>();
			tokenParams.put("card", cardParameter);
			Token token=Token.create(tokenParams);
			Map<String,Object> source=new HashMap<String,Object>();
			source.put("source", token.getId());
			cust.getSources().create(source);
		} catch (StripeException e) {
			System.out.println("Error stripe");
			return u;
		}
		
		u.setMediosP(u.getMediosP()+1);	
		dl.ActualizarUsuario(u);
		return u;
	}


	@Override
	public int realizarCompra(List<Producto> carro, Usuario u,int tot) {
		java.util.Date date = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		 String usuario="jonathan";
		 String contraseña="19890531";
		 String CONNECTION_FACTORY="jms/RemoteConnectionFactory";
		 String DESTINATION="jms/queue/ProyectQueue"; 
		 Properties env=new Properties();
		 env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		 env.put(Context.PROVIDER_URL, "http-remoting://localhost:8086");
		 env.put(Context.SECURITY_PRINCIPAL, usuario);
		 env.put(Context.SECURITY_CREDENTIALS, contraseña);
		 env.put("jboss.naming.client.ejb.context", true);
		 InitialContext ic=null;
		 QueueConnectionFactory connectionFactory;
		 QueueConnection connection = null;
		 QueueSession session;
		 Queue queue;
		 QueueSender sender;
		 TextMessage msg;
		 ObjectMessage obj;
		 Factura factura=new Factura();
		 factura.setProductos(carro);
		 factura.setCorreo(u.getCorreo());
		 factura.setNombreCli(u.getNombres());
		 factura.setApeCli(u.getApellidos());
		 factura.setTotal(tot);
		 factura.setFecha(sqlDate);
		 try {
			ic = new InitialContext(env);			
			connectionFactory = (QueueConnectionFactory) ic.lookup(CONNECTION_FACTORY);
			connection = connectionFactory.createQueueConnection(usuario, contraseña);
			connection.start();
			queue = (javax.jms.Queue) ic.lookup(DESTINATION);
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			sender = session.createSender(queue);
			obj= session.createObjectMessage();
			msg = session.createTextMessage();
			obj.setObject(factura);
			sender.send(obj);
			sender.close();
			session.close();
			connection.close();
			dl.persistCompra(factura);
		} catch (NamingException | JMSException e1) {			
			e1.printStackTrace();
			return 0;
		}
		 return 1;
	}
}
