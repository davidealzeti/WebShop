package webshop

import grails.validation.ValidationException

class CatalogoController {
	
	
	/**
	 * Mostra la pagina di selezione della quantità di Prodotto da acquistare.
	 * 
	 * @return Reindirizza alla pagina del Prodotto corrispondente se il Cliente ha effettuato
	 * 		   il login, altrimenti mostra un messaggio di errore.
	 */
	def mostraUnitàProdotto() {
		if(session.getAttribute("cliente")) {
			def map = [prodotto: Prodotto.get(params.id)]
			render(view: "mostraUnitàProdotto", model: map)
		}
		else {
			flash.message = "Devi aver effettuato il login per accedere ai prodotti"
			redirect(controller: "negozio", action: "home")
		}
	}
	
	/**
	 * Rimuove un Prodotto dal Catalogo.
	 * 
	 * @return Reindirizza alla pagina di Modifica del Catalogo se il processo è andato a buon
	 * 		   fine, altrimenti mostra un messaggio di errore.
	 */
	def rimuoviProdotto() {
		if(!session.getAttribute("dipendente")) {
			redirect(controller: "negozio", action: "home")
			return
		}
		
		def catalogo = Catalogo.get(1)
		def prodottoDaRimuovere = Prodotto.get(params.id)
		catalogo.prodotti.remove(prodottoDaRimuovere)
		try {
			catalogo.save(flush: true)
		}catch(ValidationException e) {
			flash.message = "Si è verificato un errore"
			redirect(controller: "dipendente", action: "show")
			return
		}
		flash.message = "Il prodotto è stato eliminato"
		redirect(controller: "dipendente", action: "mostraElencoProdotti")
	}
	
}
