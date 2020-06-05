package webshop

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class CatalogoControllerSpec extends Specification implements ControllerUnitTest<CatalogoController>, DataTest {
	
	Class<?>[] getDomainClassesToMock(){
		return [Prodotto, Negozio, Catalogo] as Class[]
	}
	
    def setup() {
    }

    def cleanup() {
    }

}
