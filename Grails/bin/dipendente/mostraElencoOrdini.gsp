<%@ page import="webshop.Cliente" %>

<html>
<head>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="errorMessage.css"/>
	<asset:stylesheet src="infoCliente.css"/>
    <asset:stylesheet src="/cssTable/main.css" /> 
    
    <title>Webshop Ordini</title>
</head>

<body>
<h1 class="header">Storico Ordini</h1>

<div>
	<br>
	<section class="home">
			<g:link class="button" controller="negozio" action="home">Home</g:link>

	       	<g:link class="button" controller="dipendente" action="show" id="${session.dipendente.id}">Profilo</g:link>

			<g:link class="button" action="mostraElencoClienti">Visualizza Ordini Clienti</g:link>
	<br>
</div>

<div class="container-table100">
		
		<div id="demoFont" align="center">
		<g:if test="${flash.message != ''}">
		<h4>${flash.message}</h4>
		<br>
		</g:if>
		</div>
		
	<section class="wrap-table100">
	
			<table class="table100 ver2">
				
					<thead>
					<tr>
					<th class="column2">Data Emissione</th>
					<th class="column2">Prodotti</th>
					<th class="column2">Totale</th>
					<th class="column2">Modalità Pagamento</th>
					<th class="column2">Indirizzo Spedizione</th>
					</tr>
					</thead>
					
					<tbody class="table100-body">
						<g:each var="ordine" in="${params.ordini}">
								<tr>
								
								<td><g:formatDate format="dd/MM/yyyy HH:mm" date="${ordine.dataEmissione}"/></td>
								
								<td>
								<g:each var="prodotto" in="${ordine.prodotti}">
								${prodotto}<br>
								</g:each>
								</td>
								
								<td><g:formatNumber number="${ordine.prezzoTotale}" format="##.##" />€</td>
								
								<td>${ordine.modalitàPagamento}</td>
								
								<td>${ordine.indirizzoSpedizione}</td>
								
								</tr>
						</g:each>
				</tbody>
			</table>

	
	</section>


</body>
</html>