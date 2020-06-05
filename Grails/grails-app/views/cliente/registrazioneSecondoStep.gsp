<%@ page import="webshop.Prodotto" %>
<html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Webshop Registrazione Cliente</title>
    <asset:stylesheet src="infoCliente.css" /> 
     <asset:stylesheet src="errorMessage.css"/> 
</head>
<body>

<div id="title" role="main">
    <section>
        <h1 class="header">Effettua Registrazione</h1>
        <p class="paragraph">Compila i campi per completare la Registrazione</p>
    </section>
</div>

<br>
<div id="demoFont" align="center">

	<g:if test="${flash.message != ''}">
		<h4>${flash.message}</h4>
		<br>
	</g:if>
</div>
		
<div class="colonnato">
	
	<section class="home">
	
	<g:form action="completaRegistrazione" style="margin: 0 auto; width:320px">
		<p>Nome: </p>
		<g:field type="text" name="nome" value="${params.nome}" required=""/>
		<br>
		
		<p>Cognome: </p>
		<g:field type="text" name="cognome" value="${params.cognome}" required=""/>
		<br>
		
		<p>Data di Nascita: </p>
		<g:datePicker name="dataDiNascita" value="${new Date()}" precision="day" years="${1920..2020}" required=""/>
		<br>
		
		<p>Indirizzo: </p>
		<g:field type="text" name="indirizzo" value="${params.indirizzo}" required=""/>
		<br>
		
		<p>Città: </p>
		<g:field type="text" name="città" value="${params.città}" required=""/>
		<br>
		
		<p>Provincia: </p>
		<g:field type="text" name="provincia" value="${params.provincia}" required=""/>
		<br>
		
		<p>Cap: </p>
		<g:field type="text" name="cap" value="${params.cap}" required=""/>
		<br>
	
		<p>Codice Fiscale: </p>
		<g:field type="text" name="codiceFiscale" value="${params.codiceFiscale}" required=""/>
		<br>
	
		<p>Telefono: </p>
		<g:field type="text" name="telefono" value="${params.telefono}" required=""/>
		<br>
	
		<p>Email: </p>
		<g:field type="email" name="email" value="${params.email}" required=""/>
		<br>
	
		<p>Indirizzo Recapito: </p>
		<g:field type="text" name="indirizzoRecapito" value="${params.indirizzoRecapito}" required=""/>
		<br>
	
	</section>
	
	<section class="links">
		<g:submitButton name="Registrati"/>
	</section>
	</g:form>


</div>

<br>


</body>
</html>