package webshop

class NegozioController {

	static defaultAction = "home"
	
	/**
	 * Mostra la HomePage.
	 * 
	 * @return
	 */
	def home() {
		render(view: "home")
	}
	
	/**
	 * Permette al cliente di effettuare il login tramite
	 * l'inserimento di username e password.
	 * 
	 * @return Reindirizza verso la pagina del profilo cliente se il login è andato
	 * 		   a buon fine, altrimenti mostra un messaggio di errore.
	 */
	def userLogin() {
		def cliente = Cliente.findByUsernameAndPassword(params.username, params.password)
		if(!cliente) {
			flash.message = "Username o Password errati"
			redirect(controller: "negozio", action: "login")
		}
		else {
			def clienteMap = [cliente: cliente.id]
			session.setAttribute("cliente", cliente)
			session.name = "user"
			redirect(controller: "cliente", action: "show", params: [id: cliente.id])
		}
	}
	
	/**
	 * Mostra la pagina di login.
	 * 
	 * @return Reindirizza verso la pagina di login se un utente non è già loggato,
	 * 		   altrimenti reindirizza verso la {@link webshop.NegozioController.home HomePage}.
	 */
	def login() {
		if(session.getAttribute("cliente") != null) redirect(action: "home")
		else render(view: "login")
	}	
	
}
