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
	}
	
}
