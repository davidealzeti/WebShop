package webshop

import java.text.DateFormat

class Ordine {

	Date dataEmissione
	static hasMany = [prodotti: Prodotto]
	static belongsTo = [cliente: Cliente]
	String modalitàPagamento
	double prezzoTotale
	String indirizzoSpedizione
	
    static constraints = {
		cliente()
		prodotti()
		dataEmissione(blank: false, min: new Date())
		modalitàPagamento(inList: ["Carta di credito", "Paypal"], blank: false)
		indirizzoSpedizione(blank: false)
    }
}
