<%@ page import="webshop.Prodotto" %>
<html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Webshop Profilo Cliente</title>
	<asset:stylesheet src="errorMessage.css"/> 
	<asset:stylesheet src="infoCliente.css" /> 
</head>
<body>

<div id="title" role="main">
    <section>
        <h1 class="header">Informazioni Personali</h1>
        
<div id="demoFont" align="center">

	<g:if test="${flash.message != ''}">
		<h4>${flash.message}</h4>
		<br>
	</g:if>
</div>
        
        <div class="home">
        	<g:link class="button" controller="negozio" action="home">Home</g:link>
        </div>
        <g:if test="${session.cliente.ordini != null}">
	        <div class="links">
	        	<g:link class="button" action="mostraStoricoOrdini">Ordini Effettuati</g:link>
	        </div>
        </g:if>
        
    </section>
</div>
<br>
<div id="clienti" role="main">
	<section class="datiCliente">
	
		<g:render template="infoClienteTemplate" model="clienteMap" />
	
	</section>
	
</div>

<br>

<div>
<section class="links">
	<g:link class="button" controller="cliente" action="mostraPaginaModificaDati" params="[datoDaModificare: 'pwd']">Modifica Password</g:link>
</section>
<br>
<section class="links">
	<g:link class="button" controller="cliente" action="mostraPaginaModificaDati" params="[datoDaModificare: 'other']">Modifica Dati Personali</g:link>
</section>
</div>

</body>
</html>