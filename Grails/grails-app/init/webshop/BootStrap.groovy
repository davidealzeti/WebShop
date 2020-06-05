package webshop

import org.grails.datastore.mapping.validation.ValidationException
import org.springframework.dao.DataIntegrityViolationException

class BootStrap {

    def init = { servletContext ->
		
		Negozio negozio = new Negozio()
		
		def today = new Date()
		def birth = new Date()
		birth.setDate(17)
		birth.setMonth(3)
		birth.setYear(89)
		
		Cliente verdi = new Cliente(username: 'verdi', password: 'password',
									nome: 'Luca', cognome: 'Verdi', dataDiNascita: birth, indirizzo: 'Via Santa Lucia, 142', città: 'Fossara',
										provincia: 'PG', cap: '06020', codiceFiscale: 'VRDLCU89D17G478I', telefono: '3305208297', 
										email: 'luca.verdi@rhyta.com', indirizzoRecapito: 'Via Santa Lucia, 142')
		verdi.save()
		
		birth.setDate(7)
		birth.setMonth(4)
		birth.setYear(96)
		Cliente derose = new Cliente(username: 'derose', password: 'password',
									nome: 'Giacomo', cognome: 'DeRose', dataDiNascita: birth, indirizzo: 'Via Santa Teresa, 48', città: 'Siracusa',
										provincia: 'SR', cap: '96010', codiceFiscale: 'GCMDRS96E07I754A', telefono: '3853153924', 
										email: 'GiacomoDeRose@dayrep.com', indirizzoRecapito: 'Via Santa Teresa, 48')
		try {
		derose.save(failOnError: true)
		}
		catch(ValidationException e) {
			e.printStackTrace()
		}
		
		
		Prodotto prodotto1 = new Prodotto(nome: 'prodotto1', unità: 12, prezzo: 3.75).save()
		Prodotto prodotto2 = new Prodotto(nome: 'prodotto2', unità: 3, prezzo: 5.99).save()
		Prodotto prodotto3 = new Prodotto(nome: 'prodotto3', unità: 15, prezzo: 6.99).save()
		Prodotto prodotto4 = new Prodotto(nome: 'prodotto4', unità: 4, prezzo: 8.99).save()
		Prodotto prodotto5 = new Prodotto(nome: 'prodotto5', unità: 11, prezzo: 7.99).save()
		Prodotto prodotto6 = new Prodotto(nome: 'prodotto6', unità: 7, prezzo: 10.99).save()
		
		
		Catalogo catalogo = new Catalogo(negozio: negozio, prodotti: Prodotto.list())

		negozio.catalogo = catalogo
		negozio.save(failOnError: true)
		catalogo.save(failOnError: true)
		
		Dipendente rossi = new Dipendente(nome: "Mario", cognome: "Rossi", username: "rossi", password: "password")
		
		try {
			rossi.save(failOnError: true)
		}catch(ValidationException e) {
			e.printStackTrace()
		}
		
    }
    def destroy = {
    }
}
