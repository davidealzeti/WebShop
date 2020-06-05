package webshop

class Catalogo {

	static hasMany = [prodotti: Prodotto]
	static belongsTo = [negozio: Negozio]
	
	
	static constraints = {
	}
}
