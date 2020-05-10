package mdb;

import java.util.Properties;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.Factura;
import model.Producto;

/**
 * Message-Driven Bean implementation class for: MessageDriverB
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "queue/ProyectQueue"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "queue/ProyectQueue")
public class MessageDriverB implements MessageListener {

    /**
     * Default constructor. 
     */
    public MessageDriverB() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {  
    	
    	ObjectMessage msg=(ObjectMessage) message;
    	Properties propiedad= new Properties();
    	propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth","true");
         

         
        Session sesion = Session.getDefaultInstance(propiedad);
        String correoEnvia = "proyectoarqui23@gmail.com";
        String contrasena = "proyecto-1";
        String asunto = "Factura de compra";
        String mensaje="Transaccion Realizada con exito \n";
        String aux="";
        MimeMessage mail = new MimeMessage(sesion);
    	
        try {
        	Factura factura=(Factura) msg.getObject();
        	String receptor = factura.getCorreo();
        	aux="\n Cliente: "+factura.getNombreCli()+" "+factura.getApeCli();
        	mensaje=mensaje+aux;
        	for(Producto p : factura.getProductos()) {
        		aux="\n Producto: "+p.getNombre()+" precio:"+p.getPrecio();
        		mensaje=mensaje+aux;
        	}
        	mensaje=mensaje+"\n Total :"+factura.getTotal();
        	 mail.setFrom(new InternetAddress (correoEnvia));
             mail.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress (receptor));
             mail.setSubject(asunto);
             mail.setText(mensaje);
             
             Transport transportar = sesion.getTransport("smtp");
             transportar.connect(correoEnvia,contrasena);
             transportar.sendMessage(mail, mail.getRecipients(javax.mail.Message.RecipientType.TO));          
             transportar.close();
		} catch (JMSException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

}
