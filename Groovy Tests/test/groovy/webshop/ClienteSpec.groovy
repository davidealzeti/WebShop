package webshop

import grails.testing.gorm.DomainUnitTest
import spock.lang.Shared
import spock.lang.Specification

class ClienteSpec extends Specification implements DomainUnitTest<Cliente> {

    def setup() {
    }

    def cleanup() {
    }
	
	@Shared Date adult = new Date()
	@Shared Date underage = new Date()

    void "creazione nuovo cliente"() {
        setup:
		adult.setYear(95)
		adult.setMonth(3)
		adult.setDate(7)
		new Cliente(username: 'vibriamo', password: 'password',
			nome: 'Davide', cognome: 'Alzeti', dataDiNascita: adult, indirizzo: 'via Paraboschi', città: 'Piacenza',
				provincia: 'PC', cap: '29010', codiceFiscale: 'LZTDVD95D07C261S', telefono: '3474913982',
				email: 'vibriamo@hotmail.it', indirizzoRecapito: 'via Paraboschi').save()
				
		expect:
		Cliente.count() == 1
    }
	
	void "cliente minorenne"(){
		setup:
		underage.setYear(105)
		underage.setMonth(3)
		underage.setDate(7)
		
		new Cliente(username: 'vibriamo', password: 'password',
			nome: 'Davide', cognome: 'Alzeti', dataDiNascita: underage, indirizzo: 'via Paraboschi', città: 'Piacenza',
				provincia: 'PC', cap: '29010', codiceFiscale: 'LZTDVD95D07C261S', telefono: '3474913982',
				email: 'vibriamo@hotmail.it', indirizzoRecapito: 'via Paraboschi').save()	
				
		expect:
		Cliente.count() == 0
	}
}
