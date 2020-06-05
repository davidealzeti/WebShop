package webshop

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

class OrdineControllerSpec extends Specification implements ControllerUnitTest<OrdineController>, DataTest {
	
	Class<?>[] getDomainClassesToMock(){
		return [Cliente, Prodotto] as Class[]
	}
	
	
    def setup() {
    }

    def cleanup() {
    }
}
