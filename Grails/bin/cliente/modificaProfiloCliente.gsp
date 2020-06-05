<%@ page import="webshop.Prodotto" %>
<html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Webshop Profilo Cliente</title>
    <asset:stylesheet src="infoCliente.css" /> 
     <asset:stylesheet src="errorMessage.css"/> 
</head>
<body>

<div id="title" role="main">
    <section>
        <h1 class="header">Modifica Profilo</h1>
        
        <div class="home">
        	<g:link class="button" controller="negozio" action="home">Home</g:link>
        </div>
        
    </section>
</div>

<br>
<div id="demoFont" align="center">

	<!-- modificare per mostrare messaggi -->
	<g:if test="${flash.message != ''}">
		<h4>${flash.message}</h4>
		<br>
	</g:if>
</div>
		
<div id="clienti" role="main">
	<section class="datiCliente">
		
		<g:if test="${params.datoDaModificare == 'pwd'}">
			<g:render template="modificaPasswordTemplate" model="session.cliente" bean="${session.cliente}"/>
		</g:if>
		<g:elseif test="${params.datoDaModificare == 'other'}">
			<g:render template="modificaDatiTemplate" model="session.cliente" bean="${session.cliente}"/>
		</g:elseif>
		
	</section>
	
</div>

<br>


</body>
</html>