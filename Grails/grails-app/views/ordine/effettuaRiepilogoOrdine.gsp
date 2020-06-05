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
        <h1 class="header">Riepilogo Ordine</h1>
        <p class="paragraph">Modifica le quantità o rimuovi i prodotti</p>
        <br>
        <div class="home">
        	<g:link class="button" controller="negozio" action="home">Home</g:link>
        </div>
        
		<div class="links">
			<br>
			<g:link class="button" action="svuotaOrdine" onclick="return confirm('Sei sicuro? I prodotti verranno eliminati definitivamente dal tuo Ordine.')">Elimina tutti i prodotti</g:link>
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

<div style="margin: 0 auto; width:320px">
	
		
		<g:each var="prodotto" in="${session.prodottiSelezionati}">
			
			<section class="checkboxtext">
				<p class="smallerparagraph">${prodotto.nome} &emsp; Quantità: ${prodotto.quantità} 
				 &emsp;Prezzo: <g:formatNumber number="${prodotto.prezzo}" format="##.##" />€</p>	
			</section>
			
			
			<section class="indent-1">
				<section>
					<g:link class="button" action="rimuoviProdotto" params="[id: prodotto.id]">Elimina</g:link>
				</section>
				
				<section>
		        	<g:link class="button" controller="catalogo" action="mostraUnitàProdotto" params="[id: prodotto.id]">
		        		Mod.Quantità
		        	</g:link>
	        	</section>
        	</section>
        	
        	<p class="breakline"> ' </p>
			<g:hiddenField name="quantità" value="${prodotto.quantità}"/>
			<g:hiddenField name="identificativo" value="${prodotto.id}" />
			
			<br>
			
		</g:each>

</div>

<div class="links">
	<br>
	<g:link class="button" action="mostraPaginaPagamento">Pagamento</g:link>
</div>

<br>


</body>
</html>