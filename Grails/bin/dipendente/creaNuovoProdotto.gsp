<%@ page import="webshop.Cliente" %>

<html>
<head>
    <meta name="layout" content="main"/>
     <asset:stylesheet src="errorMessage.css"/>
          <asset:stylesheet src="infoCliente.css"/>
    <title>Webshop Dipendente</title>
</head>

<body>
<div class="home">
<h1 class="header">Dati Nuovo Prodotto</h1>
<p class="paragraph"> Inserisci i dati relativi al nuovo Prodotto da inserire nel Catalogo </p>
</div>
<br>

<div class="home">
	<section>
			<g:link class="button" controller="negozio" action="home">Home</g:link>
	       	<g:link class="button" controller="dipendente" action="show" id="${session.dipendente.id}">Profilo</g:link>
	       	<g:link class="button" action="mostraElencoProdotti">Catalogo</g:link>
	</section>

</div>
<br>

<div id="demoFont" align="center">
<g:if test="${flash.message != ''}">
		<h4>${flash.message}</h4>
		<br>
		</g:if>
</div>

<div>
	<g:form controller="prodotto" action="aggiungiNuovoProdotto" style="margin: 0 auto; width:320px"> 
		<p>Nome:</p>
		<g:field type="text" name="nome" value="${params.nome}" required=""/>
		<br>
		<p>Prezzo:</p>
		<g:field type="text"  name="prezzo" value="${params.prezzo}" required=""/>
		<br>
		<p>Unità:</p>
		<g:field type="text"  name="unità" value="${params.unità}" required=""/>
		<section class="home">
			<br>
			<g:submitButton name="Aggiungi Prodotto"/>
		</section>
	</g:form>
</div>


</body>
</html>