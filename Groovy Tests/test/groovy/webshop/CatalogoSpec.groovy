package webshop

import grails.testing.gorm.DomainUnitTest
import grails.validation.ValidationException
import spock.lang.Specification

class CatalogoSpec extends Specification implements DomainUnitTest<Catalogo> {

    def setup() {
    }

    def cleanup() {
    }

    void "creazione catalogo con prodotti"() {
        setup:
		Negozio negozio = new Negozio().save()
		Prodotto prodotto1 = new Prodotto(nome: 'prodotto1', unità: 12, prezzo: 3.75).save()
		Prodotto prodotto2 = new Prodotto(nome: 'prodotto2', unità: 3, prezzo: 5.99).save()
		
		Catalogo catalogo = new Catalogo(negozio: negozio, prodotti: Prodotto.list())
		catalogo.save()
		
		expect:
		Prodotto.count() == 2
		Catalogo.count() == 1
		catalogo.prodotti.size() == 2
    }
}
