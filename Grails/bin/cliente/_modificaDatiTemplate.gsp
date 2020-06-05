<%@ page import="webshop.Cliente" %>

<div class="colonnato">
	
	<section class="home">
	
	<g:form action="aggiornaDati" style="margin: 0 auto; width:320px">
	<p>Nome: </p>
	<g:field type="text" name="nome" value="${session.cliente.nome}" required=""/>
	<br>
	
	<p>Cognome: </p>
	<g:field type="text" name="cognome" value="${session.cliente.cognome}" required=""/>
	<br>
	
	<p>Data di Nascita: </p>
	<g:field type="date" name="dataDiNascita" value="${session.cliente.dataDiNascita}" required=""/>
	<br>
	
	<p>Indirizzo: </p>
	<g:field type="text" name="indirizzo" value="${session.cliente.indirizzo}" required=""/>
	<br>
	
	<p>Città: </p>
	<g:field type="text" name="città" value="${session.cliente.città}" required=""/>
	<br>
	
	<p>Provincia: </p>
	<g:field type="text" name="provincia" value="${session.cliente.provincia}" required=""/>
	<br>
	
	<p>Cap: </p>
	<g:field type="text" name="cap" value="${session.cliente.cap}" required=""/>
	<br>

	<p>Codice Fiscale: </p>
	<g:field type="text" name="codiceFiscale" value="${session.cliente.codiceFiscale}" required=""/>
	<br>

	<p>Telefono: </p>
	<g:field type="text" name="telefono" value="${session.cliente.telefono}" required=""/>
	<br>

	<p>Email: </p>
	<g:field type="email" name="email" value="${session.cliente.email}" required=""/>
	<br>

	<p>Indirizzo Recapito: </p>
	<g:field type="text" name="indirizzoRecapito" value="${session.cliente.indirizzoRecapito}" required=""/>
	<br>
	
	</section>
	
	<section class="links">
		<g:submitButton name="Conferma Modifiche"/>
	</section>
	</g:form>


</div>
	


