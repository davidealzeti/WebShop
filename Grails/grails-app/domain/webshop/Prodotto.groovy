package webshop

class Prodotto {

	String nome
	double prezzo
	int unità
	
	String toString() {
		nome
	}
	
    static constraints = {
		nome(unique: true, size: 1..20, blank: false)
		unità(blank: false, unique: false)
		prezzo(blank: false, unique: false)
		
    }
}
