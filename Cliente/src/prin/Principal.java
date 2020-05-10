package prin;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import fachada.FachadaNRemote;
import model.*;

public class Principal {

	public static void main(String[] args) throws IOException {
		ServiceLocator ls=new ServiceLocator();
		FachadaNRemote serviciosn=ls.LocalizarS();
		if(serviciosn==null) {
			System.out.println("Nulo");
		}else {
			System.out.println("buen");
		}
	
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		int op=0,op1=0;
		String nom,ape,usn,pss,corr;
		do {
			op=menu();
			if(op==1) {
				System.out.println("Desea registrarse como: 1.Usuario general 2.Proveedor");
				op1=Integer.parseInt(br.readLine());
				if(op1==1) {
					System.out.println("ingrese su nombre");
					nom=br.readLine();
					System.out.println("ingrese su apellido");
					ape=br.readLine();
					System.out.println("ingrese su correo electronico");
					corr=br.readLine();
					System.out.println("ingrese un nombre de usuario");
					usn=br.readLine();
					System.out.println("ingrese una contraseña");
					pss=br.readLine();
					System.out.println(serviciosn.RegistrarUsuario(nom, ape, usn, pss,corr));
				}else if(op1==2) {
					System.out.println("ingrese el nombre de la empresa");
					nom=br.readLine();
					System.out.println("ingrese su correo");
					ape=br.readLine();
					System.out.println("ingrese un nombre de usuario");
					usn=br.readLine();
					System.out.println("ingrese una contraseña");
					pss=br.readLine();
					System.out.println(serviciosn.RegistrarProveedor(nom, ape, usn, pss));
				}else {
					System.out.println("Opcion Incorrecta");
				}
				
			}else if(op==2) {
				Usuario user=null;
				System.out.println("ingrese su nombre de usuario");
				usn=br.readLine();
				System.out.println("ingrese su contraseña");
				pss=br.readLine();
				user=serviciosn.autenticarUsuario(usn, pss);
				if(user!=null) {
					System.out.println("Autenticacion Exitosa");
					do {
					user=serviciosn.autenticarUsuario(usn, pss);
					op1=manejoUsuario(serviciosn,user);
					}while(op1!=4);
				}else {
					Proveedor prov=null;
					prov=serviciosn.autenticarProveedor(usn, pss);
					if(prov!=null) {
						System.out.println("Autenticacion Exitosa");
						do {
						op1=manejoProveedor(serviciosn,prov);
						}while(op1!=3);
					}else {
						System.out.println("Datos no encontrados");

					}
				}
			}
		}while(op!=3);	
	}
		
	private static int manejoProveedor(FachadaNRemote serviciosn,Proveedor provedor) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("1.Perfil");
		System.out.println("2.Listar Proveedores");
		System.out.println("3.Salir");
		int op=Integer.parseInt(br.readLine());
		switch(op) {
		case 1:
			break;
		case 2:
			List<Proveedor> prov=serviciosn.ListarProveedores();
			for(int i=0;i<prov.size();i++) {
				System.out.println("Proveedor: "+prov.get(i).getNombre()+"\n");
			}
			break;
		case 3:
			break;
		default:
			break;
		}
		return op;
	}
	private static int manejoUsuario(FachadaNRemote serviciosn,Usuario u) throws IOException {
		List<Producto> carrito=new ArrayList<Producto>();
		int op=0;
		int total=0;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		do {
		System.out.println("1.Perfil");
		System.out.println("2.Listar Usuarios");
		System.out.println("3.Ver Catalogo");
		System.out.println("4.Salir");
		op=Integer.parseInt(br.readLine());
		switch(op) {
			case 1:
				
				break;
			case 2:
				List<Usuario> prov=serviciosn.ListarUsuarios();
				for(int i=0;i<prov.size();i++) {
					System.out.println("Usuario: "+prov.get(i).getNombres()+" "+prov.get(i).getApellidos()+"\n");
				}
				break;
			case 3:
				int pag=0;
				int occ=0,occ2=0;
				int codProd=0;
				boolean esta=false;
		
				do {
					pag=pag+1;
				List<Producto> prod=serviciosn.verCatalogo(pag);	
				Producto p=null;
				for(int i=0;i<20;i++) {
					p=prod.get(i);
					System.out.println("id: "+p.getIdproducto()+"  Producto: "+p.getNombre()+" Descripcion: "+p.getDescripcion()+" Precio: "+p.getPrecio()+"\n");
				}
	
					System.out.println("1. Seleccionar un producto para comprar");
					System.out.println("2. Ver la siguiente pagina de productos");
					System.out.println("3. Ver carrito de compras");
					System.out.println("4. Salir");
					occ=Integer.parseInt(br.readLine());
					if(occ==1) {
						System.out.println("Digite el codigo para agregarlo a su carro de compras");
						codProd=Integer.parseInt(br.readLine());
						for(int i=0;i<prod.size();i++) {
							if(prod.get(i).getIdproducto()==codProd) {
								if(prod.get(i).getCantidad()>0) {
									carrito.add(prod.get(i));
									total=total+prod.get(i).getPrecio();
									esta=true;
								}else {
									System.out.println("Por ahora no quedan unidades disponibles del producto");
								}
								
							}
							
						}
						
						if(esta==false) {
							System.out.println("Error en el codigo");
						}else {
							esta=false;
						}
						pag=pag-1;
					}else if(occ==3) {
						for(Producto pro:carrito) {
							System.out.println("id: "+pro.getIdproducto()+"  Producto: "+pro.getNombre()+" Descripcion: "+pro.getDescripcion()+" Precio: "+pro.getPrecio()+"\n");
						}
						System.out.println("El total de la compra es:"+total);
						System.out.println("1. Confirmar compra");
						System.out.println("2. Seguir mirando productos");
						occ2=Integer.parseInt(br.readLine());
						if(occ2==1) {
							u=realizarCompra(carrito,serviciosn,u,total);
							carrito.clear();
							total=0;
							pag=0;
						}
					}
					
				}while(occ!=4);
				break;
			case 4:
				
				break;
			default: 
				System.out.println("opcion incorrecta");
				break;
		}
		}while(op!=4);
		return op;
	}
	private static Usuario realizarCompra(List<Producto> carro,FachadaNRemote serviciosn,Usuario u, int total) throws NumberFormatException, IOException {
		int op,fn=0;
		String num,cod,mes,year;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		if(u.getMediosP()==0) {
			System.out.println("No tiene tarjeta asociada a la cuenta, por favor dijite los siguiente datos:");
			System.out.println("Ingrese el numero de la tarjeta de credito");
			num=br.readLine();
			System.out.println("Ingrese el año de caducidad de la tarjeta");
			year=br.readLine();
			System.out.println("Ingrese el mes de caducidad de la tarjeta");
			mes=br.readLine();
			System.out.println("Ingrese el codigo de seguridad");
			cod=br.readLine();
			u=serviciosn.confirmarCompra(year,mes,num,cod,u);
			if(u.getMediosP()==0) {
				System.out.println("Tarjeta de credito invalida");
			}else {
				System.out.println("Tarjeta agregada correctamente");
				
			}
		}
		if(u.getMediosP()>=1){
			System.out.println("1. Realizar Pago");
			System.out.println("2. volver");
			op=Integer.parseInt(br.readLine());
			if(op==1) {
				System.out.println("Realizando la transaccion");
				fn=serviciosn.realizarCompra(carro,u,total);
				if(fn==1) {
					System.out.println("Transaccion realizada con exito,  la informacion de compra ha sido enviada a su correo");
				}else {
					System.out.println("Error en la transaccion");
				}
			}
		}
		return u;
		
	}

	public static int menu() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Bienvenido\n");
		System.out.println("1.Registrarse");
		System.out.println("2.Ingresar");	
		System.out.println("3.Salir");
		int op=Integer.parseInt(br.readLine());
		return op;
		
	}
		
				
	
}

