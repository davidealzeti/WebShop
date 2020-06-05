package webshop

import grails.gorm.transactions.Transactional
import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

class DipendenteControllerSpec extends Specification implements ControllerUnitTest<DipendenteController>, DataTest {
	
	Class<?>[] getDomainClassesToMock(){
		return [Prodotto, Cliente, Ordine] as Class[]
	}
	
    def setup() {
    }

    def cleanup() {
    }

}
