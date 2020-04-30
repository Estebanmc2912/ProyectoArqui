package compra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
/*
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
*/
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Token;
/*
import com.sun.messaging.Queue;
import com.sun.messaging.QueueConnectionFactory;
import com.sun.messaging.jms.QueueConnection;
*/
import ldatos.DelegadoDatosLocal;
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
	public int realizarCompra(List<Producto> carro, Usuario u) {
		/* List<TextMessage> infoFactura=new ArrayList<TextMessage>();
		 int  total=0;
		 InitialContext ctx=null;
		 QueueConnectionFactory queueConnectionFactory;
		 QueueConnection queueConnection= null;
		 QueueSession queueSession;
		 QueueSender queueSender;
		 Queue queue;
		 TextMessage msg;
		 ObjectMessage obj;

		 try {
			 ctx=new InitialContext();
			 queueConnectionFactory =(QueueConnectionFactory) ctx.lookup("jms/ProyectConnectionFactory");
			 queueConnection= (QueueConnection)queueConnectionFactory.createQueueConnection();
			 queueConnection.start();
			 queueSession=queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			 queue=(Queue)ctx.lookup("jms/ProyectQueue");
			 queueSender=queueSession.createSender(queue);
			 
			 msg=queueSession.createTextMessage();
			 obj=queueSession.createObjectMessage();
			 msg.setText(u.getCorreo());
			 infoFactura.add(msg);
			 msg.setText("Transaccion confirmada");
			 infoFactura.add(msg);
			 msg.setText("Cliente: "+u.getNombres()+" "+u.getApellidos());
			 infoFactura.add(msg);
			 for(Producto p:carro) {
				 msg.setText("Producto: "+p.getNombre());
				 infoFactura.add(msg);
				 msg.setText("Descripcion: "+p.getDescripcion());
				 infoFactura.add(msg);
				 msg.setText("Precio: "+p.getDescripcion());
				 infoFactura.add(msg);
				 total=total+p.getPrecio();
			 }
			 msg.setText("total pagado: "+total);
			 infoFactura.add(msg);
			 obj.setObject((Serializable) infoFactura);
			 queueSender.send(obj);
			 
			 queueSender.close();
			 queueSession.close();
			 
		 }catch(NamingException | JMSException e) {
			 e.printStackTrace();
			 return 0;
		 }finally {
			 if(queueConnection != null) {
				 try {
					 queueConnection.close();
				 }catch(JMSException jsme) {
					 System.out.println("error: "+jsme.toString());
				 }
			 }
		 }
		return 1;
	}
*/
		return 0;
	}
}
