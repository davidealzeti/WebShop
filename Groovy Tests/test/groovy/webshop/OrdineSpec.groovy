package webshop

import grails.testing.gorm.DomainUnitTest
import spock.lang.Shared
import spock.lang.Specification
import java.util.Calendar

class OrdineSpec extends Specification implements DomainUnitTest<Ordine> {

	/*
    def setup() {
    }

    def cleanup() {
    }
	*/
	
	@Shared Date today = new Date()
	@Shared Date adult = new Date()

	void "nuovo ordine associato a cliente"() {
		setup:
		adult.setYear(95)
		adult.setMonth(3)
		adult.setDate(7)
		Cliente vibriamo = new Cliente(username: 'vibriamo', password: 'password',
									nome: 'Davide', cognome: 'Alzeti', dataDiNascita: adult, indirizzo: 'via Paraboschi', città: 'Piacenza',
										provincia: 'PC', cap: '29010', codiceFiscale: 'LZTDVD95D07C261S', telefono: '3474913982', 
										email: 'vibriamo@hotmail.it', indirizzoRecapito: 'via Paraboschi')
		vibriamo.save()
		new Ordine(dataEmissione: today.plus(1), modalitàPagamento: 'Paypal',
			cliente: vibriamo, indirizzoSpedizione: 'via Parab').save()
		
		expect:
		Cliente.count() == 1
		Ordine.count() == 1
		
	}
	
	void "ordine con prodotti"(){
		setup:
		adult.setYear(95)
		adult.setMonth(3)
		adult.setDate(7)
		Cliente vibriamo = new Cliente(username: 'vibriamo', password: 'password',
			nome: 'Davide', cognome: 'Alzeti', dataDiNascita: adult, indirizzo: 'via Paraboschi', città: 'Piacenza',
				provincia: 'PC', cap: '29010', codiceFiscale: 'LZTDVD95D07C261S', telefono: '3474913982',
				email: 'vibriamo@hotmail.it', indirizzoRecapito: 'via Paraboschi')
		vibriamo.save()
		
		def prodotto1 = new Prodotto(nome: 'prodotto1', unità: 12, ).save()
		def prodotto2 = new Prodotto(nome: 'prodotto2', unità: 3).save()
		
		new Ordine(dataEmissione: today.plus(1), modalitàPagamento: 'Paypal', prodotti: [prodotto1, prodotto2],
			cliente: vibriamo, indirizzoSpedizione: 'via Parab').save()
		
		expect:
		Cliente.count() == 1
		Prodotto.count() == 2
		Ordine.count() == 1
		
	}
	
}
