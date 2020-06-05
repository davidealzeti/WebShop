<%@ page import="webshop.Prodotto" %>
<html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Webshop Ordine</title>
    <asset:stylesheet src="infoCliente.css" /> 
     <asset:stylesheet src="errorMessage.css"/> 
</head>
<body>

<div id="title" role="main">
    <section>
        <h1 class="header">Completa Ordine</h1>
        <p class="paragraph">Scegli la modalità di pagamento e conferma l'Ordine</p>
        <br>
        <div class="home">
        	<g:link class="button" controller="negozio" action="home">Home</g:link>
        </div>
        
    </section>
</div>

<br>
<div id="demoFont" align="center">

	<g:if test="${flash.message != ''}">
		<h4>${flash.message}</h4>
		<br>
	</g:if>
</div>

<div class="home">
	<p class="header"> Il totale è <g:formatNumber number="${session.totale}" format="##.##" />€</p>
</div>
<br>
<div>
	
	<g:form action="effettuaPagamento" style="margin: 0 auto; width:320px">
		<section class="indent-1">
			<section>
				<p>Città e Indirizzo di Spedizione:</p>
			</section>
			<section>
				<g:field type="text" name="indirizzoSpedizione" value="${session.cliente.indirizzoRecapito}" required=""/>
			</section>
		</section>
		<p class="breakline"> ' </p>
		<section class="indent-1">
			<section>
				<p>Modalità Pagamento:</p>
			</section>
			<section>
				<g:select name="modalitàPagamento" from="${['Carta di credito', 'Paypal']}" />
			</section>
		</section>

		
		<section class="links">
			<g:submitButton name="Conferma Ordine"/>
		</section>
	
	</g:form>
	
</div>

<br>


</body>
</html>