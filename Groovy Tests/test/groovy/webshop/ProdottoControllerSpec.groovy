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
		Negozio negozio = new Negozio().save(failOnError: true)
		
		Prodotto prodotto1 = new Prodotto(nome: 'prodotto1', unità: 12, prezzo: 3.75).save()
		Prodotto prodotto2 = new Prodotto(nome: 'prodotto2', unità: 3, prezzo: 5.99).save()
		
		Catalogo catalogo = new Catalogo(negozio: negozio, prodotti: [prodotto1, prodotto2]).save()
    }

    def cleanup() {
    }
	
	@Ignore
    void "modifica dati prodotto"() {
        given:
		params.identificativo = 1
		params.name = ""
		params.price = 13.45
		params.units = 10
		
		when:
		controller.modificaProdotto()
		
		then:
		Prodotto.get(1).hasErrors() == true
		flash.message == "some"
		Prodotto.get(1).nome == "prodotto1"
    }
	
	@Ignore
	void "aggiungi nuovo prodotto"(){
		given:
		params.nome = "prodotto2"
		params.prezzo = 12.42
		params.unità = 34
		
		when:
		controller.aggiungiNuovoProdotto()
		
		then:
		flash.message == "some"
	}
	
	void "crea nuovo prodotto"() {
		given:
		params.nome = "prodotto4"
		params.prezzo = 12.44
		params.unità = 9
		def catalogo = Catalogo.get(1)
		
		when:
		controller.aggiungiNuovoProdotto()
		
		then:
		catalogo.prodotti.size() == 3
	}
}