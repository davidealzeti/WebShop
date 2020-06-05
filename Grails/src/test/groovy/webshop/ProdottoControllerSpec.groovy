package webshop

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Ignore
import spock.lang.Specification

class ProdottoControllerSpec extends Specification implements ControllerUnitTest<ProdottoController>, DataTest {

	Class<?>[] getDomainClassesToMock(){
		return [Prodotto, Catalogo] as Class[]
	}
	
    def setup() {
    }

    def cleanup() {
    }

}