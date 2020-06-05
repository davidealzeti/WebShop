package webshop


class Cliente implements grails.validation.Validateable{

	String username
	String password
	String nome
	String cognome
	Date dataDiNascita
	String indirizzo
	String città
	String provincia
	String cap
	String codiceFiscale
	String telefono
	String email
	String indirizzoRecapito
	
	
	static hasMany = [ordini: Ordine]
	
	String toString() {	}
	
    static constraints = {
		username(blank: false, maxSize: 10, unique: true)
		password(blank: false, minSize: 5, unique: false, password: true, nullable: false)
		nome(blank: false, unique: false)
		cognome(blank: false, unique: false)
		dataDiNascita blank: false, max: new Date()			        
		indirizzo(blank: false)
		città(blank: false, unique: false)
		provincia(blank: false, unique: false)
		cap(blank: false, unique: false, size: 5..5)
		codiceFiscale(blank: false, unique: true, size: 16..16)
		telefono(blank: false, unique: true, size: 10..10)
		email(blank: false, unique: true, email: true)
		indirizzoRecapito(blank: false, unique: false)
    }
}
