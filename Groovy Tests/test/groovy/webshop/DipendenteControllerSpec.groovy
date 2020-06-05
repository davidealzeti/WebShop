package webshop

import grails.gorm.transactions.Transactional
import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

class DipendenteControllerSpec extends Specification implements ControllerUnitTest<DipendenteController>, DataTest {

	@Shared Date birth = new Date()
	
	Class<?>[] getDomainClassesToMock(){
		return [Prodotto, Cliente, Ordine] as Class[]
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
		
		Ordine ordine1 = new Ordine(dataEmissione: birth.plus(1), prodotti: [prodotto1, prodotto2],
			modalitàPagamento: 'Paypal', cliente: vibriamo,
			indirizzoSpedizione: 'via Paraboschi', prezzoTotale: 9.74).save()
    }

    def cleanup() {
    }
	
	@Ignore
    void "elimina prodotto dal catalogo"() {
        given:
		params.id = 1
		
		when:
		controller.eliminaProdotto()
		
		then:
		flash.message == "Il prodotto è stato eliminato"
		Prodotto.count() == 1
    }
	
	@Ignore
	void "mostra pagina di modifica prodotto"() {
		given:
		params.setProperty("id", 1)
		
		when:
		controller.modificaDatiProdotto()
		
		then:
		getView() == "some"
	}
	
	void "number format test"() {
		given:
		double num = 2.33
		
		when:
		def res = controller.formatNumberWithCommaAsDecimalSeparator(num)
		
		then:
		res == "2,33"
	}
}
