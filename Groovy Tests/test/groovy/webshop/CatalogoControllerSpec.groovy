package webshop

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class CatalogoControllerSpec extends Specification implements ControllerUnitTest<CatalogoController>, DataTest {
	
	Class<?>[] getDomainClassesToMock(){
		return [Prodotto, Negozio, Catalogo] as Class[]
	}
	
    def setup() {
		Prodotto prodotto1 = new Prodotto(nome: 'prodotto1', unità: 12, prezzo: 3.75).save()
		Prodotto prodotto2 = new Prodotto(nome: 'prodotto2', unità: 3, prezzo: 5.99).save()
    }

    def cleanup() {
    }
	
	void "rimuovi prodotto dal catalogo"() {
		given:
		params.setProperty("id", 1)
		Negozio negozio = new Negozio()
		Catalogo catalogo = new Catalogo(prodotti: Prodotto.list())
		negozio.catalogo = catalogo
		catalogo.negozio = negozio
		negozio.save(failOnError: true)
		catalogo.save(failOnError: true)
		
		when:
		controller.rimuoviProdotto()
		
		then:
		catalogo.prodotti.size() == 1
		
	}




}
