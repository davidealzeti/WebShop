package webshop

class Dipendente {

	String nome
	String cognome
	String username
	String password
	
    static constraints = {
		nome(blank: false)
		cognome(blank: false)
		username(maxSize: 10, blank: false, unique: true)
		password(minSize: 5, blank: false, password: true)
    }
}
