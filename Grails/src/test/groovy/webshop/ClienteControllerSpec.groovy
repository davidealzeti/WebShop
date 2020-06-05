package webshop

import grails.testing.gorm.DataTest
import grails.testing.gorm.DomainUnitTest
import grails.testing.web.controllers.ControllerUnitTest
import grails.validation.ValidationException
import spock.lang.*
import org.springframework.context.i18n.LocaleContextHolder


class ClienteControllerSpec extends Specification implements ControllerUnitTest<ClienteController>, DataTest  {
	
	Class<?>[] getDomainClassesToMock(){
		return [Cliente, Ordine, Prodotto] as Class[]
	}
	
	def setup() {
	}
	
}






