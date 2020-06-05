package webshop

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class NegozioControllerSpec extends Specification implements ControllerUnitTest<NegozioController>, DataTest {

    def cleanup() {
    }
	
 	Class<?>[] getDomainClassesToMock(){
		return [Cliente] as Class[]
	}
	
	def setup() {
		Date birth = new Date()
		birth.setDate(7)
		birth.setMonth(3)
		birth.setYear(95)
		def vibriamo = new Cliente(username: 'vibriamo', password: 'password',
			nome: 'Davide', cognome: 'Alzeti', dataDiNascita: birth, indirizzo: 'via Paraboschi', città: 'Piacenza',
				provincia: 'PC', cap: '29010', codiceFiscale: 'LZTDVD95D07C261S', telefono: '3474913982',
				email: 'vibriamo@hotmail.it', indirizzoRecapito: 'via Paraboschi').save()
	}
	
    void "trova cliente by username and password"(){
		setup: 
		setup()
		
		when:
		params.put("user", Cliente.get(1).username)
		params.put("pwd", Cliente.get(1).password)
		controller.userLogin()
		
		then:
		status == 302
		response.getRedirectedUrl() == "/cliente/show/1"
	}
	
}
