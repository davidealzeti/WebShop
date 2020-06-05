package webshop

class Negozio {

	static hasOne = [catalogo: Catalogo]
	static hasMany = [dipendenti: Dipendente, clienti: Cliente]
	
	String toString() {
		clienti()
		dipendenti()
	}
	
    static constraints = {
		catalogo()
		dipendenti()
		clienti()
    }
}
