package webshop

import grails.validation.ValidationException

class OrdineController {
	
	/**
	 * Aggiunge il prodotto selezionato all'Ordine corrente nella quantità specificata.
	 * 
	 * @return	Reindirizza al Catalogo contenente l'elenco dei Prodotti.
	 */
	def aggiungiProdotto() {
		if(!checkIfLogged()) return
		
		if(session.getAttribute("prodotti") != null) {
			def ordineCorrente = session.getAttribute("prodotti")
			ordineCorrente.put(params.prodottoId, params.quantità)
			session.setAttribute("prodotti", ordineCorrente)
		}
		else {
			Map bindingMap = new HashMap()
			bindingMap.put(params.prodottoId, params.quantità)
			session.setAttribute("prodotti", bindingMap)
		}
		redirect(controller: "negozio", action: "home")
		flash.message = "Il prodotto è stato aggiunto al tuo Ordine. Per procedere all'acquisto seleziona 'Completa Ordine'"
	}
	
	/**
	 * Effettua il pagamento dell'Ordine in base alla modalità selezionata dal Cliente.
	 * 
	 * @return Reindirizza alla {@link webshop.NegozioController.home HomePage} se il processo è andato a
	 * 		   buon fine, mostra un messaggio di errore se la quantità selezionata per i prodotti
	 * 		   è maggiore di quella disponibile.
	 */
	def effettuaPagamento() {
		if(!checkIfLogged()) return
		
		ArrayList prodotti = new ArrayList()
		double totale = session.getAttribute("totale")
		for(prodotto in session.getAttribute("prodotti")) {
			
			if(modificaUnitàProdottoDopoOrdine(prodotto.getKey(), prodotto.getValue())) {
				prodotti.add(Prodotto.get(prodotto.getKey()))
			}
			else {
				flash.message = "Il prodotto: ${Prodotto.get(prodotto.getKey()).nome} non è più disponibile nella quantità desiderata. Si prega di modificare la quantità (ne rimangono ancora ${Prodotto.get(prodotto.getKey()).unità}) o di rimuovere il prodotto se si vuole procedere con l'Ordine"
				redirect(action: "effettuaRiepilogoOrdine")
				return
			}

		}
		session.removeAttribute("prodotti")
		def bindingMap = [dataEmissione: new Date(), prodotti: prodotti, cliente: session.getAttribute("cliente"),
			modalitàPagamento: params.modalitàPagamento, prezzoTotale: totale, 
			indirizzoSpedizione: params.indirizzoSpedizione]
		Ordine nuovoOrdine = new Ordine(bindingMap)
		session.removeAttribute("totale")
		try {
			nuovoOrdine.save(flush: true)
		}catch(ValidationException e) {
			e.printStackTrace()
		}
		redirect(controller: "negozio", action: "home")
		flash.message = "Il tuo ordine è stato preso in carico."
	}
	
	/**
	 * Rimuove il prodotto selezionato dall'Ordine corrente.
	 * 
	 * @return	Reindirizza all'elenco dei Prodotti presenti nell'Ordine corrente se ve
	 * 			ne sono ancora, altrimenti reindirizza alla {@link webshop.NegozioController.home HomePage}.
	 * 			
	 */
	def rimuoviProdotto() {
		if(!checkIfLogged()) return
		
		try {
			
			def prodottiSelezionati = session.getAttribute("prodotti")
			def prodottiDaRimuovere = new ArrayList()
			for(prodotto in prodottiSelezionati) {
				if(params.id == prodotto.getKey()) prodottiDaRimuovere.add(prodotto.getKey())
				else continue
			}
			for(prodotto in prodottiDaRimuovere) {
				if(prodottiSelezionati.containsKey(prodotto)) prodottiSelezionati.remove(prodotto)
				else continue
			}
			
			if(prodottiSelezionati.isEmpty()) {
				session.removeAttribute("prodotti")
				session.removeAttribute("prodottiSelezionati")
				flash.message = "Hai eliminato tutti i prodotti dal tuo Ordine."
				redirect(controller: "negozio", action: "home")
			}
			else {
				session.setAttribute("prodotti", prodottiSelezionati)
				flash.message = "Prodotto rimosso"
				redirect(action: "effettuaRiepilogoOrdine")
			}
		
		}catch(NullPointerException) {
			flash.message = "La lista dei prodotti è vuota"
			redirect(controller: "negozio", action: "home")
			return
		}
	}
	
	/**
	 * Rimuove tutti i prodotti dall'Ordine corrente.
	 * 
	 * @return Reindirizza alla {@link webshop.NegozioController.home HomePage}.
	 */
	def svuotaOrdine() {
		if(!checkIfLogged()) return
		
		session.removeAttribute("prodotti")
		flash.message = "Hai eliminato tutti i prodotti dal tuo Ordine."
		redirect(controller: "negozio", action: "home")
		
	}
	
	/**
	 * Fornisce la lista di tutti i prodotti selezionati dal Cliente per l'Ordine corrente.
	 * 
	 * @return Mostra la pagina di riepilogo dell'Ordine.
	 */
	def effettuaRiepilogoOrdine() {
		if(!checkIfLogged()) return

		def listaProdotti = new ArrayList()
		for(prodotto in session.getAttribute("prodotti")) {
			def prodottiSelezionati = [nome: String, quantità: int, id: int, prezzo: double]
			prodottiSelezionati.nome = Prodotto.get(prodotto.getKey()).nome
			prodottiSelezionati.quantità = prodotto.getValue()
			prodottiSelezionati.id = Prodotto.get(prodotto.getKey()).id
			prodottiSelezionati.prezzo = (Prodotto.get(prodotto.getKey()).prezzo * (Integer.parseInt(prodotto.getValue())))
			listaProdotti.add(prodottiSelezionati)
		}
		session.setAttribute("prodottiSelezionati", listaProdotti)
		render(view: "effettuaRiepilogoOrdine")
	}
	
	/**
	 * Calcola il prezzo totale dell'Ordine corrente e lo mostra nella pagina relativa al pagamento.
	 * 
	 * @return Mostra la pagina di pagamento.
	 */
	def mostraPaginaPagamento() {
		if(!checkIfLogged()) return
		
		double totale = 0
		for(prodotto in session.getAttribute("prodottiSelezionati")) {
			totale += prodotto.prezzo
		}
		session.setAttribute("totale", totale)
		render(view: "paginaPagamento")
	}
	
	/**
	 * Modifica le unità dei prodotti all'interno del Database in base alle quantità acquistate
	 * dai Clienti.
	 * 
	 * @param idProdotto
	 * 		L'identificativo del prodotto di cui modificare le unità.
	 * 
	 * @param quantitàAcquistata
	 * 		La quantità di prodotto acquistata.
	 * 
	 * @return true se la modifica del prodotto è andata a buon fine.
	 */
	private boolean modificaUnitàProdottoDopoOrdine(String idProdotto, String quantitàAcquistata) {
		def id = Integer.parseInt(idProdotto)
		def quantità = Integer.parseInt(quantitàAcquistata)
		def prodotto = Prodotto.get(id)
		if((prodotto.unità - quantità) >= 0) {
			prodotto.unità = prodotto.unità - quantità
			try {
				prodotto.save()
			}catch(ValidationException) {
				return false
			}
			return true
		}
		else return false
	}
	
	/**
	 * Controlla se un cliente ha effettuato il login.
	 * 
	 * @return true se il cliente ha effettuato il login.
	 */
	private boolean checkIfLogged() {
		if(!session.getAttribute("cliente")) {
			flash.message = "Accesso negato"
			redirect(controller: "negozio", action: "home")
			return false
		}
		return true
	}
}
