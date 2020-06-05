package webshop

import grails.testing.gorm.DataTest
import grails.testing.gorm.DomainUnitTest
import grails.testing.web.controllers.ControllerUnitTest
import grails.validation.ValidationException
import spock.lang.*
import org.springframework.context.i18n.LocaleContextHolder


class ClienteControllerSpec extends Specification implements ControllerUnitTest<ClienteController>, DataTest  {
	
	@Shared Date birth = new Date()
	
	Class<?>[] getDomainClassesToMock(){
		return [Cliente, Ordine, Prodotto] as Class[]
	}
	
	def setup() {
		birth.setDate(7)
		birth.setMonth(3)
		birth.setYear(95)
		def vibriamo = new Cliente(username: 'vibriamo', password: 'password',
			nome: 'Davide', cognome: 'Alzeti', dataDiNascita: birth, indirizzo: 'via Paraboschi', città: 'Piacenza',
				provincia: 'PC', cap: '29010', codiceFiscale: 'LZTDVD95D07C261S', telefono: '3474913982',
				email: 'vibriamo@hotmail.it', indirizzoRecapito: 'via Paraboschi').save()
		Cliente mozart = new Cliente(username: 'mozart', password: 'password',
			nome: 'Amadeus', cognome: 'Mozart', dataDiNascita: birth, indirizzo: 'via Beethoven', città: 'Milano',
				provincia: 'MI', cap: '29100', codiceFiscale: 'WFGMDS77E14G233R', telefono: '0523762183',
				email: 'mozart@hotmail.it', indirizzoRecapito: 'via Beethoven').save()
				
		Prodotto prodotto1 = new Prodotto(nome: 'prodotto1', unità: 12, prezzo: 3.75).save()
		Prodotto prodotto2 = new Prodotto(nome: 'prodotto2', unità: 3, prezzo: 5.99).save()
		
		Ordine ordine1 = new Ordine(dataEmissione: birth.plus(1), prodotti: [prodotto1, prodotto2],
			modalitàPagamento: 'Paypal', cliente: vibriamo,
			indirizzoSpedizione: 'via Paraboschi', prezzoTotale: 9.74).save()
			
		Ordine ordine2 = new Ordine(dataEmissione: birth.plus(1), prodotti: [prodotto1],
			modalitàPagamento: 'Paypal', cliente: vibriamo,
			indirizzoSpedizione: 'via Paraboschi', prezzoTotale: 10.59).save()
	}
	
	@Ignore
	void "modifica password a buon fine"() {
		given:
		setup()
		params.id = Cliente.get(1).id
		def cliente = Cliente.get(1)
		def vecchiaPassword = cliente.password
		def nuovaPassword = "ramarro"
		def confermaNuovaPassword = "ramarro"
		
		when:
		controller.show()
		controller.updatePassword(vecchiaPassword, nuovaPassword, confermaNuovaPassword)
		controller.show()
		
		then:
		session.getAttribute("cliente").password == Cliente.get(1).password
		Cliente.get(1).password != vecchiaPassword
	}
	
	@Ignore
	void "modifica password troppo corta"() {
		setup()
		params.id = Cliente.get(1).id
		def cliente = Cliente.get(1)
		def vecchiaPassword = cliente.password
		def nuovaPassword = "asd"
		def confermaNuovaPassword = "asd"
		
		when:
		controller.show()
		controller.updatePassword(vecchiaPassword, nuovaPassword, confermaNuovaPassword)
		controller.show()
		
		then:
		flash.message != "something" 	
	}
	
	@Ignore
	void "metodo aggiorna dati cliente con params"() {
		given:
		def cliente = Cliente.get(1)
		session.setAttribute("cliente", cliente)
		params.setProperty("nome", "Carlo")
		params.setProperty("dataDiNascita", "1999-12-11")
		params.setProperty("indirizzoRecapito", "via roma 12")
		params.setProperty("email", "vibriamohotmail.it") //errore: formato mail non valido
		params.setProperty("cap", "2901") //errore: cap troppo corto
		String some = "nullProperty"
		
		when:
		controller.aggiornaDati()		
		
		then:
		cliente.nome == "Carlo"
		
	}
	
	@Ignore
	void "effettua registrazione"() {
		given:
		birth.setDate(7)
		birth.setMonth(3)
		birth.setYear(105)

		params.setProperty("nome", "Carlo")
		params.setProperty("cognome", "Pellegatti")
		params.setProperty("dataDiNascita", birth) //errore
		params.setProperty("indirizzo", "via roma")
		params.setProperty("città", "roma")
		params.setProperty("provincia", "roma")
		params.setProperty("cap", "29011")
		params.setProperty("telefono", "0123456789")
		params.setProperty("codiceFiscale", "lllllllllllllll") //errore
		params.setProperty("email", "carlo.pellegatti@live.it")
		params.setProperty("indirizzoRecapito", "via roma")
		
		def bindingMap = [username: "carlo", password: "password"]
		session.setAttribute("credenziali", bindingMap)
		
		when:
		controller.completaRegistrazione()
		
		then:
		response.getRedirectedUrl() == "some"
	}
	
	@Ignore
	void "visualizza storico ordini"() {
		given:
		session.setAttribute("cliente", Cliente.get(1))
		
		when:
		controller.mostraStoricoOrdini()
		
		then:
		Ordine.count() == 2
		session.getAttribute("elencoOrdini") == "some"
		//flash.message == "some"
	}
	
	void "metodo count() per attributo ordini di Cliente"() {
		given:
		def cliente = Cliente.get(1)
		
		when:
		def numeroOrdini = cliente.ordini.size()
		
		then:
		numeroOrdini == 2
	}
}






