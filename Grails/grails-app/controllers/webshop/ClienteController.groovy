package webshop

import grails.validation.ValidationException
import groovy.json.StringEscapeUtils
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

import org.grails.web.i18n.ParamsAwareLocaleChangeInterceptor

import static org.springframework.http.HttpStatus.*

class ClienteController{

    ClienteService clienteService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	
	/**
	 * Mostra i dati di un utente che ha effettuato il login.
	 * 
	 * Effettua il binding tra una nuova sessione e l'utente.
	 * 
	 * Se un utente è già loggato aggiorna la sessione corrente, se un utente non loggato tenta di accedere
	 * alla pagina Cliente viene mostrato un messaggio di errore.
	 * 
	 * @return La pagina del profilo utente.
	 */
    def show() {
		if(!session.getAttribute("cliente")) {
	        flash.message = "Accesso negato"
			redirect(controller: "negozio", action: "home")
		}
		else if (session.getAttribute("cliente").id == params.id){
			def updatedCliente = Cliente.get(session.getAttribute("cliente").id)
			session.setAttribute("cliente", updatedCliente)
			render(view: "mostraProfiloCliente", model: [cliente: session.getAttribute("cliente")])
		}
		else {
			params.setProperty("id", session.getAttribute("cliente").id)
			show()
		}
    }
	
	/**
	 * Effettua il logout di un utente che ha effettuato precedentemente il login.
	 * 
	 * Invalida la sessione corrente e mostra un messaggio di conferma dell'avvenuto logout.
	 * 
	 * @return L'utente viene reindirizzato alla {@link webshop.Negozio.home() HomePage}
	 */
	def logout() {
		if(!session.getAttribute("cliente")) redirect(controller: "negozio",action: "home")
		else {
			session.invalidate()
			flash.message = "Logout effettuato"
			redirect(controller: "negozio", action: "home")
		}
	}
	
	/**
	 * Sostituisce la precedente password con una nuova inserita dall'utente.
	 * 
	 * @param vecchiaPassword
	 * 		La password precedente alla modifica.
	 * 
	 * @param nuovaPassword
	 * 		La password con cui si vuole sostituire quella precedente.
	 * 
	 * @param confermaNuovaPassword
	 * 		La conferma della password con cui si vuole sostituire quella precedente.
	 * 
	 * @return	L'utente viene reindirizzato alla pagina del proprio profilo.
	 */
	def updatePassword(String vecchiaPassword, String nuovaPassword, String confermaNuovaPassword) {
		def cliente = session.getAttribute("cliente")
		
		if((vecchiaPassword == cliente.password) && (nuovaPassword == confermaNuovaPassword)) {
			cliente.password = nuovaPassword
			if(cliente.save(flush: true)) {
				params.id = cliente.id
				flash.message = "La password è stata modificata con successo"
				redirect(action: "show")
			}
			else {
				cliente.password = vecchiaPassword
				switch(cliente.errors.getFieldError("password").getCode().toString().equals("minSize.notmet")) {
					case true: flash.message = "La password deve avere almeno 5 caratteri"; break;
					default: flash.message = "Si è verificato un problema. Riprovare"; break;
				}
				redirect(action: "mostraPaginaModificaDati", params: [datoDaModificare: 'pwd'])
			}
		}
		else if(nuovaPassword != confermaNuovaPassword) {
			flash.message = "Le due password non corrispondono"
			redirect(action: "mostraPaginaModificaDati", params: [datoDaModificare: 'pwd'])
		}
		else {
			flash.message = "La vecchia password inserita non è corretta"
			redirect(action: "mostraPaginaModificaDati", params: [datoDaModificare: 'pwd'])
		}
	}
	
	/**
	 * Mostra la pagina di modifica del profilo utente.
	 * @return
	 */
	def mostraPaginaModificaDati() {
		if(!session.getAttribute("cliente")) {
			flash.message = "Accesso negato"
			redirect(controller: "negozio", action: "home")
		}
		else render(view: "modificaProfiloCliente")
	}
	
	/**
	 * Verifica la maggiore età.
	 * 
	 * @param age
	 * 		La data da confrontare con quella odierna.
	 * 
	 * @return true se la differenza tra la data odierna e quella inserita è minore di 18 anni.
	 */
	private boolean isUnderage(Date age) {
		Calendar cal = Calendar.getInstance()
		cal.setTime(age)
		Calendar today = new Calendar.Builder().setInstant(new Date()).build()
		if(!((today.get(Calendar.YEAR) - cal.get(Calendar.YEAR)) >= 18)){
			return true
		}
		return false
	}
	
	/**
	 * Converte una stringa rappresentante una data nel formato 
	 * "yyyy-mm-dd" nella Data corrispondente in formato Date.
	 *   
	 * @param dateToConvert
	 * 		La stringa che rappresenta la data da convertire.
	 * 
	 * @return 	La data convertita.
	 */
	private Date convertStringToDate(String dateToConvert) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd")
			Date convertedDate = format.parse(dateToConvert)
			return convertedDate
		}
		catch(ParseException e) {
			e.printStackTrace()
			return session.getAttribute("cliente").dataDiNascita
		}
	}
	
	/**
	 * Aggiorna i dati del cliente sostituendoli con quelli nuovi inseriti dal cliente stesso.
	 * 
	 * @return Reindirizza alla pagina del profilo utente con i dati aggiornati se quelli inseriti sono
	 * 		   riconosciuti come validi, altrimenti mostra un messaggio di errore.
	 */
	def aggiornaDati() {
		try {
			def test = Cliente.get(session.getAttribute("cliente").id)
		}catch(NullPointerException) {
			flash.message = "Accesso negato"
			redirect(controller: "negozio", action: "home")
			return
		}
		
		def cliente = Cliente.get(session.getAttribute("cliente").id)
		def birthday = convertStringToDate(params.dataDiNascita)
		if(isUnderage(birthday)) {
			flash.message = "L'età minima consentita è 18 anni"
			redirect(action: "mostraPaginaModificaDati", params: [datoDaModificare: 'other'])
		}
		else {
			params.setProperty("dataDiNascita", birthday)
			cliente.properties = params
			
			if(cliente.save(flush: true)) {
				session.setAttribute("cliente", cliente)
				flash.message = "Dati Modificati con successo"
				redirect(action: "show", params: "")
			}
			else {
				cliente.errors.getFieldErrors().each {
					if(it.getDefaultMessage()) {
							flash.message += (message(code: it.getDefaultMessage(), args: [it.getField(), "Cliente", cliente.properties.get(it.getField())])+"\n")
							flash.message = flash.message.toString().replaceAll("null","")
						}
						else flash.message += (it.getCode()+"\n")
							flash.message = flash.message.toString().replaceAll("null","")
				}
				redirect(action: "mostraPaginaModificaDati", params: [datoDaModificare: 'other'])
			}
		}
	}

	/**
	 * Registra l'username e la password di un cliente non ancora registrato.
	 * 
	 * @param username
	 * 		Lo username scelto dal cliente.
	 * @param password
	 * 		La password scelta dal cliente.
	 * @param confermaPassword
	 * 		La conferma della password scelta dal cliente.
	 * 
	 * @return Reindirizza alla {@link webshop.cliente.completaRegistrazione() Seconda Parte} della registrazione
	 * 		   se i dati inseriti sono riconosciuti come validi, altrimenti mostra un messaggio di errore.
	 */
	def registraUsernamePassword(String username, String password, String confermaPassword) {
		for(cliente in Cliente.list()) {
			if(cliente.username == username) {
				flash.message = "Username già scelto"
				redirect(action: "mostraRegistrazionePrimoStep")
				return
			}
			else continue
		}
		
		if((password != confermaPassword) || (password.length() < 4)) {
			flash.message = "Le password inserite non coincidono o sono troppo corte (minimo 5 caratteri)"
			redirect(action: "mostraRegistrazionePrimoStep")
		}
		else {
			def bindingMap = [username: username, password: password]
			session.setAttribute("credenziali", bindingMap)
			redirect(action: "mostraRegistrazioneSecondoStep")
		}
	}
	
	
	/**
	 * Completa la registrazione iniziata nella {@link webshop.cliente.registraUsernamePassword() Prima Parte}
	 * registrando i dati personali inseriti dal cliente.
	 * 
	 * @return Reindirizza alla pagina personale del cliente se i dati inseriti sono riconosciuti come validi,
	 * 		   altrimenti mostra un messaggio di errore.
	 */
	def completaRegistrazione() {
		if(isUnderage(params.dataDiNascita)) {
			flash.message = "L'età minima consentita è 18 anni"
			redirect(action: "mostraRegistrazioneSecondoStep", params: params)
			return
		}
		
		try {
			params.setProperty("username", session.getAttribute("credenziali").username)
			params.setProperty("password", session.getAttribute("credenziali").password)
		}catch(NullPointerException) {
			flash.message = "Prima di inserire le tue informazioni personali devi scegliere l'username e la password"
			redirect(action: "mostraRegistrazionePrimoStep")
			return
		}
		
		Cliente cliente = new Cliente(params)
		
		if(cliente.save()) {
			session.removeAttribute("credenziali") 
			flash.message = "Registrazione effettuata con successo! Effettua il login per iniziare gli acquisti"
			redirect(controller: "negozio", action: "home")
		}
		else {
			cliente.errors.getFieldErrors().each{
				if(it.getDefaultMessage()) {
					flash.message += (message(code: it.getDefaultMessage(), args: [it.getField(), "Cliente", cliente.properties.get(it.getField())])+"\n")
					flash.message = flash.message.toString().replaceAll("null","")
				}
				else flash.message += (it.getCode()+"\n")
					flash.message = flash.message.toString().replaceAll("null","")
			}
			redirect(action: "mostraRegistrazioneSecondoStep", params: [nome: cliente.nome, cognome: cliente.cognome,
				indirizzo: cliente.indirizzo, città: cliente.città, provincia: cliente.provincia,
				cap: cliente.cap, codiceFiscale: cliente.codiceFiscale, telefono: cliente.telefono,
				email: cliente.email, indirizzoRecapito: cliente.indirizzoRecapito])
		}
	}
	
	/**
	 * 
	 * @return Mostra una pagina con la {@link webshop.cliente.registraUsernamePassword() Prima Parte} 
	 * 		   della Registrazione di un Cliente.
	 */
	def mostraRegistrazionePrimoStep() {
		if(session.getAttribute("cliente") != null) redirect(controller: "negozio", action: "home")
		else render(view: "registrazionePrimoStep")
	}
	
	/**
	 * 
	 * @return Mostra una pagina con la {@link webshop.cliente.completaRegistrazione() Seconda Parte} 
	 * 		   della Registrazione di un Cliente.
	 */
	def mostraRegistrazioneSecondoStep() {
		if(session.getAttribute("cliente") != null) redirect(controller: "negozio", action: "home")
		else render(view: "registrazioneSecondoStep")
	}
	
	/**
	 * Mostra la pagina relativa agli ordini effettuati da un cliente.
	 * 
	 * @return	Reindirizza alla pagina contenente l'elenco degli ordini effettuati da un
	 * 			cliente se questo ne ha effettuato almeno uno, altrimenti rimanda alla
	 * 			pagina del profilo del cliente. 
	 */
	def mostraStoricoOrdini() {
		if(!session.getAttribute("cliente")) {
			flash.message = "Accesso negato"
			redirect(controller: "negozio", action: "home")
			return
		}
		
		session.removeAttribute("elencoOrdini")
		def ordini = Ordine.findAllByCliente(session.getAttribute("cliente"))
		
		if(!ordini) {
			flash.message = "Non hai ancora effettuato alcun Ordine"
			redirect(action: "show")
		}
		else {
			session.setAttribute("elencoOrdini", ordini)
			render(view: "storicoOrdini")
		}
	}
	

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
