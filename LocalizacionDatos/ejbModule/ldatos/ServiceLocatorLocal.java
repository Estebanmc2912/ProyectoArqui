package ldatos;

import javax.ejb.Local;

import serviciosD.FachadaDRemote;

@Local
public interface ServiceLocatorLocal {
	public FachadaDRemote getService();

}
