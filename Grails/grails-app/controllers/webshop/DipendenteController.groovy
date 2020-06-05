package webshop

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import org.springframework.dao.DataIntegrityViolationException

class DipendenteController {
	
	/**
	 * Effettua il login di un Dipendente tramite l'inserimento dell'ursename e della password.
	 * 
	 * @return Reindirizza alla pagina del Profilo Dipendente se il login è andato a buon fine,
	 * 		   altrimenti mostra un messaggio di errore.
	 */
	def dipendenteLogin() {
		def dipendente = Dipendente.findByUsernameAndPassword(params.username, params.password)
		if(!dipendente) {
			flash.message = "Username o Password errati"
			render(view: "loginDipendente")
		}
		else {
			def dipendenteMap = [dipendente: dipendente.id]
			session.setAttribute("dipendente", dipendente)
			session.name = "dipendente"
			redirect(controller: "dipendente", action: "show", params: [id: dipendente.id])
		}
	}
	
	/**
	 * @return Mostra la pagina di login.
	 */
	def login() {
		if(session.getAttribute("dipendente") != null) redirect(controller: "negozio", action: "home")
		else render(view: "loginDipendente")
	}
	
	/**
	 * @return Lega un Dipendente che ha effettuato il login alla sessione corrente.
	 */
	def show() {
		if(!checkIfLogged()) return
		else if(session.getAttribute("dipendente").id == params.id){
			def updatedDipendente = Dipendente.get(session.getAttribute("dipendente").id)
			session.setAttribute("dipendente", updatedDipendente)
			render(view: "mostraProfiloDipendente", model: [dipendente: session.getAttribute("dipendente")])
		}
		else {
			params.setProperty("id", session.getAttribute("dipendente").id)
			show()
		}
	}
	
	/**
	 * Effettua il logout.
	 * 
	 * @return Reindirizza alla {@link webshop.Negozio.home() HomePage}.
	 */
	def logout() {
		if(!checkIfLogged()) return
		
		session.invalidate()
		flash.message = "Logout effettuato"
		redirect(controller: "negozio", action: "home")
	}
	
	/**
	 * @return Mostra la pagina relativa alla modifica della password del Dipendente.
	 */
	def mostraPaginaModificaPassword() {
		if(!checkIfLogged()) return
		render(view: "modificaPasswordDipendente")
	}
	
	/**
	 * Modifica la password del Dipendente.
	 * 
	 * @param vecchiaPassword
	 * 		La password precedente.
	 * 
	 * @param nuovaPassword
	 * 		La password che va a modificare quella precedente.
	 * 
	 * @param confermaNuovaPassword
	 * 		La conferma della password che va a modificare quella precedente.
	 * 
	 * @return Reindirizza alla pagina del Profilo Dipendente se la modifica è andata a buon fine,
	 * 		   altrimenti mostra un messaggio di errore.
	 */
	def modificaPassword(String vecchiaPassword, String nuovaPassword, String confermaNuovaPassword) {
		if(!checkIfLogged()) return
		
		def dipendente = session.getAttribute("dipendente")
		
		try {
			if((vecchiaPassword == dipendente.password) && (nuovaPassword == confermaNuovaPassword)) {
				dipendente.password = nuovaPassword
				if(dipendente.save(flush: true)) {
					params.id = dipendente.id
					flash.message = "La password è stata modificata con successo"
					redirect(action: "show")
				}
				else {
					dipendente.password = vecchiaPassword
					switch(dipendente.errors.getFieldError("password").getCode().toString().equals("minSize.notmet")) {
						case true: flash.message = "La password deve avere almeno 5 caratteri"; break;
						default: flash.message = "Si è verificato un problema. Riprovare"; break;
					}
					redirect(action: "mostraPaginaModificaPassword")
				}
			}
			else if(nuovaPassword != confermaNuovaPassword) {
				flash.message = "Le due password non corrispondono"
				redirect(action: "mostraPaginaModificaPassword")
			}
			else {
				flash.message = "La vecchia password inserita non è corretta"
				redirect(action: "mostraPaginaModificaPassword")
			}
		}catch(NullPointerException) {
			flash.message = "Si è verificato un errore. Riprovare"
			redirect(controller: "negozio", action: "home")
		}
	}
	
	/**
	 * @return Mostra l'elenco dei Clienti del Negozio.
	 */
	def mostraElencoClienti() {
		if(!checkIfLogged()) return
		render(view: "mostraElencoClienti")
	}
	
	/**
	 * @return Mostra l'elenco degli Ordini effettuati da uno specifico Cliente.
	 */
	def mostraElencoOrdini() {
		if(!checkIfLogged()) return
		
		def cliente = Cliente.get(params.id)
		def ordini = new ArrayList()
		if(!cliente) {
			flash.message = "Si è verificato un errore. Riprovare"
			redirect(action: "mostraElencoClienti")
		}
		else {
			cliente.ordini.each { 
				ordini.add(it)
			}
			params.setProperty("ordini", ordini)
			render(view: "mostraElencoOrdini")
		}
	}
	
	/**
	 * @return Mostra l'elenco dei Prodotti presenti nel Catalogo
	 */
	def mostraElencoProdotti() {
		
		session.removeAttribute("catalogo")
		if(!checkIfLogged()) return
		else
			session.setAttribute("catalogo", Catalogo.get(1)) 
			render(view: "mostraElencoProdotti")
			
	}
	
	/**
	 * @return Mostra la pagina relativa alla modifica dei dati di un Prodotto del Catalogo.
	 */
	def modificaDatiProdotto() {
		if(!checkIfLogged()) return
		
		def prodotto = Prodotto.get(params.id)
		try {
			params.id = prodotto.id
			params.nome = prodotto.nome
			params.prezzo = formatNumberWithCommaAsDecimalSeparator(prodotto.prezzo)
			params.unità = prodotto.unità
			render(view: "modificaDatiProdotto")
		}catch(NullPointerException) {
			flash.message = "Si è verificato un errore. Riprovare"
			redirect(action: "mostraElencoProdotti")
		}
	}
	
	def creaNuovoProdotto() {
		if(!checkIfLogged()) return
		render(view: "creaNuovoProdotto")
	}
	
	/**
	 * Permette di mostrare un numero usando la virgola come elemento separatore delle cifre decimali.
	 *  
	 * @param number
	 * 		Il numero da formattare.
	 * 
	 * @return La stringa rappresentante il numero inserito con la virgola come separatore delle cifre decimali.
	 */
	public String formatNumberWithCommaAsDecimalSeparator(double number) {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
		otherSymbols.setDecimalSeparator(',' as char);
		otherSymbols.setGroupingSeparator('.' as char);
		DecimalFormat df = new DecimalFormat("##.##", otherSymbols);
		return df.format(number)
	}
	
	/**
	 * Controlla se il dipendente ha effettuato il login.
	 * 
	 * @return true se il dipendente ha effettuato il login.
	 */
	private boolean checkIfLogged() {
		if(!session.getAttribute("dipendente")) {
			flash.message = "Accesso negato"
			redirect(controller: "negozio", action: "home")
			return false
		}
		return true
	}
}
