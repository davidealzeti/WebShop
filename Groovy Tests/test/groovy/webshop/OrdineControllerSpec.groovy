package webshop

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

class OrdineControllerSpec extends Specification implements ControllerUnitTest<OrdineController>, DataTest {

	@Shared Date birth = new Date()
	
	Class<?>[] getDomainClassesToMock(){
		return [Cliente] as Class[]
	}
	
	Class<?>[] getDomainClassToMock(){
		return [Prodotto] as Class[]
	}
	
    def setup() {
		birth.setDate(7)
		birth.setMonth(3)
		birth.setYear(95)
		def vibriamo = new Cliente(username: 'vibriamo', password: 'password',
			nome: 'Davide', cognome: 'Alzeti', dataDiNascita: birth, indirizzo: 'via Paraboschi', città: 'Piacenza',
				provincia: 'PC', cap: '29010', codiceFiscale: 'LZTDVD95D07C261S', telefono: '3474913982',
				email: 'vibriamo@hotmail.it', indirizzoRecapito: 'via Paraboschi').save()
		Prodotto prodotto1 = new Prodotto(nome: 'prodotto1', unità: 12, prezzo: 3.75).save()
		Prodotto prodotto2 = new Prodotto(nome: 'prodotto2', unità: 3, prezzo: 5.99).save()
		Prodotto prodotto3 = new Prodotto(nome: 'prodotto3', unità: 15, prezzo: 6.99).save()
    }

    def cleanup() {
    }
	
	@Ignore
    void "aggiungi prodotto a ordine"() {
        given:
		//un cliente seleziona un prodotto da aggiungere all'ordine
		session.setAttribute("cliente", Cliente.get(1))
		params.setProperty("prodottoId", Prodotto.get(1).id)
		params.setProperty("quantità", 2)
		
		
		when:
		//viene chiamato aggiungiProdotto()
		controller.aggiungiProdotto()
		
		then:
		//il prodotto è stato aggiunto all'ordine
		session.getAttribute("prodotti") == "some"
    }
	
	/* Test non più valido perché a runtime i parametri 'id' e 'quantità'
	 * della mappa 'prodotti' legata alla sessione corrente sono
	 * Stringhe invece che Interi
	 */
	void "effettua pagamento"() {
		given:
		session.setAttribute("cliente", Cliente.get(1))
		params.setProperty("prodottoId", Prodotto.get(1).id)
		params.setProperty("quantità", 2)
		params.setProperty("modalitàPagamento", "Paypal")
		params.setProperty("indirizzoSpedizione", "via Roma")
		
		session.setAttribute("totale", 23.45)
		
		Map prodotti = new HashMap()
		prodotti.put(Prodotto.get(1).id, 2)
		prodotti.put(Prodotto.get(2).id, 3)
		session.setAttribute("prodotti", prodotti)
		
		when:
		controller.effettuaPagamento()
		
		then:
		Ordine.count() == 1
		Prodotto.get(1).unità == 10
		Prodotto.get(2).unità == 0
	}
	
	@Ignore
	void "rimuovi prodotti da ordine"() {
		given:
		session.setAttribute("cliente", Cliente.get(1))
		Map prodotti = new HashMap()
		prodotti.put(1, 2)
		//prodotti.put(2, 1)
		//prodotti.put(3, 3)
		session.setAttribute("prodotti", prodotti)
		params.setProperty("prodottoId", 1)
		params.setProperty("selezionato", true)
		params.setProperty("identificativo", params.prodottoId)
		
		when:
		controller.rimuoviProdotto()
		//controller.effettuaRiepilogoOrdine()
		
		then:
		//session.getAttribute("prodottiSelezionati").toString() == "some"
		response.getRedirectedUrl() == "some"
	}
	
	@Ignore
	void "effettua riepilogo ordine"() {
		given:
		Map prodotti = new HashMap()
		prodotti.put(1, 2)
		prodotti.put(2, 1)
		prodotti.put(3, 3)
		session.setAttribute("prodotti", prodotti)
		
		when:
		controller.effettuaRiepilogoOrdine()
		controller.mostraPaginaPagamento()
		
		then:
		//session.getAttribute("prodottiSelezionati").toString() == "some"
		params.totale == 22
	}
}
