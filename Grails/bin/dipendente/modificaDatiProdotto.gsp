<%@ page import="webshop.Cliente" %>

<html>
<head>
    <meta name="layout" content="main"/>
     <asset:stylesheet src="errorMessage.css"/>
          <asset:stylesheet src="infoCliente.css"/>
    <title>Webshop Dipendente</title>
</head>

<body>
<h1 class="header">Modifica Dati Prodotto</h1>
<br>
<div>

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
	<g:form controller="prodotto" action="modificaProdotto" style="margin: 0 auto; width:320px"> 
		<p>Nome:</p>
		<g:field type="text" name="name" value="${params.nome}" required=""/>
		<br>
		<p>Prezzo:</p>
		<g:field type="text"  name="price" value="${params.prezzo}" required=""/>
		<br>
		<p>Unità:</p>
		<g:field type="text"  name="units" value="${params.unità}" required=""/>
		<g:hiddenField name="id" value="${params.id}"/>
		<section class="home">
			<br>
			<g:submitButton name="Conferma Modifiche"/>
		</section>
	</g:form>
</div>


</body>
</html>