package ldatos;

import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import serviciosD.FachadaDRemote;

/**
 * Session Bean implementation class ServiceLocator
 */
@Stateless
@LocalBean
public class ServiceLocator implements ServiceLocatorLocal {

    /**
     * Default constructor. 
     */
    public ServiceLocator() {
        
    }

	@Override
	public FachadaDRemote getService() {
		Properties jndiProperties = new Properties();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		jndiProperties.put(Context.SECURITY_PRINCIPAL, "jonathan");
		jndiProperties.put(Context.SECURITY_CREDENTIALS, "19890531");
		jndiProperties.put("jboss.naming.client.ejb.context", true);
		Context ctx;
		String namespace = "ejb:";
		String appName = "DatosEar";
		String moduleName = "FachadaDatos";
		String distinctName = "";
		String beanName = "FachadaD";
		String viewClassName = FachadaDRemote.class.getName();
		FachadaDRemote fachadaDatos = null;
		try {
		ctx = new InitialContext(jndiProperties);
		fachadaDatos = (FachadaDRemote) ctx.lookup(
		namespace + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
		} catch (NamingException e) {		
		e.printStackTrace();
		}
		
		return fachadaDatos;
	}

}
