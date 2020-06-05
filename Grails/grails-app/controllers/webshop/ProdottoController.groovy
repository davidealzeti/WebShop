package webshop

import grails.validation.ValidationException

class ProdottoController {
	
	/**
	 * Modifica i dati del Prodotto con i nuovi parametri inseriti.
	 * 
	 * @return Reindirizza alla pagina di {@link webshop.DipendenteController.mostraElencoProdotti() Modifica del Catalogo }
	 * 		   se la modifica è andata a buon fine, altrimenti reindirizza alla 
	 * 		   pagina di {@link webshop.DipendenteController.modificaDatiProdotto Modifica del Prodotto}
	 * 		   mostrando un messaggio di errore. 
	 */
	def modificaProdotto() {
		if(!checkIfLogged()) return
		
		try {
			def bindingMap = [nome: params.name, prezzo: params.price, unità: params.units]
			def prodotto = Prodotto.get(params.id)
			prodotto.properties = bindingMap
			if(prodotto.save(flush: true)) {
				flash.message = "Il prodotto: ${prodotto.nome} è stato modificato con successo"
				redirect(controller: "dipendente", action: "mostraElencoProdotti")
			}
			else {
				prodotto.errors.getFieldErrors().each { 
					flash.message += (message(code: it.getDefaultMessage(), args: [it.getField(), "Prodotto", prodotto.properties.get(it.getField())])+"\n")
					flash.message = flash.message.toString().replaceFirst("null","")
				}
				for(nomeProprietà in prodotto.getDirtyPropertyNames()) {
					prodotto."$nomeProprietà" = prodotto.getPersistentValue(nomeProprietà)
				}
				prodotto.save(flush: true)
				redirect(controller: "dipendente", action: "modificaDatiProdotto", params: [id: prodotto.id])
			}
		}catch(NullPointerException) {
			flash.message = "Si è verificato un errore. Riprovare"
			redirect(controller: "negozio", action: "home")
		}
	}
	
	/**
	 * Aggiunge un nuovo Prodotto al Catalogo.
	 * 
	 * @return Reindirizza alla pagina di {@link webshop.DipendenteController.mostraElencoProdotti() Modifica del Catalogo }
	 * 		   se l'aggiunta del Prodotto è andata a buon fine, altrimenti mostra un messaggio di errore.
	 */
	def aggiungiNuovoProdotto() {
		if(!checkIfLogged()) return
		
		def nuovoProdotto = new Prodotto(nome: params.nome, prezzo: params.prezzo, unità: params.unità)
		
		if(nuovoProdotto.save(flush: true)) {
			def catalogo = Catalogo.get(1)
			catalogo.prodotti.add(nuovoProdotto)
			catalogo.save(flush: true)
			flash.message = "Il prodotto: ${nuovoProdotto.nome} è stato aggiunto al Catalogo"
			redirect(controller: "dipendente", action: "mostraElencoProdotti")
		}
		else{
			nuovoProdotto.errors.getFieldErrors().each { 
				flash.message += (message(code: it.getDefaultMessage(), args: [it.getField(), "Prodotto", nuovoProdotto.properties.get(it.getField())])+"\n")
				flash.message = flash.message.toString().replaceFirst("null","")
			}
			redirect(controller: "dipendente", action: "creaNuovoProdotto", params: [nome: nuovoProdotto.nome, prezzo: nuovoProdotto.prezzo, unità: nuovoProdotto.unità])
		}
	}
	
	/**
	 * Controlla se un Dipendente ha effettuato il login.
	 * 
	 * @return true se il Dipendente ha effettuato il login.
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
