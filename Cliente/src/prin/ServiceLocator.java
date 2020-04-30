package prin;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import fachada.FachadaNRemote;

public class ServiceLocator {
	public FachadaNRemote LocalizarS() {
		Properties jndiProperties = new Properties();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8090");
		jndiProperties.put(Context.SECURITY_PRINCIPAL, "jonathan");
		jndiProperties.put(Context.SECURITY_CREDENTIALS, "19890531");
		jndiProperties.put("jboss.naming.client.ejb.context", true);
		Context ctx;
		String namespace = "ejb:";
		String appName = "NegocioEar";
		String moduleName = "FachadaNegocio";
		String distinctName = "";
		String beanName = "FachadaN";
		String word1="";
		String viewClassName = FachadaNRemote.class.getName();
		FachadaNRemote serviciosn = null;
		try {
			ctx = new InitialContext(jndiProperties);
			serviciosn = (FachadaNRemote) ctx.lookup(
			namespace + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return serviciosn;
	}

}
