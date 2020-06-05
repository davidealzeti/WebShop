<%@ page import="webshop.Catalogo" %>

<html>
<head>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="errorMessage.css"/>
	<asset:stylesheet src="infoCliente.css"/>
    <asset:stylesheet src="/cssTable/main.css" /> 
    
    <title>Webshop Dipendente</title>
</head>

<body>
<h1 class="header">Prodotti nel Catalogo</h1>

<div>
	<br>
	<section class="indent-1">
		<section>
			<g:link class="button" controller="negozio" action="home">Home</g:link>
		</section>
		
		<section>
	       	<g:link class="button" controller="dipendente" action="show" id="${session.dipendente.id}">Profilo</g:link>
		</section>
	</section>
	<br>
</div>

<div class="home">
	<g:link class="button" controller="dipendente" action="creaNuovoProdotto">Aggiungi Nuovo Prodotto</g:link>
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
					<th class="column2">Id</th>
					<th class="column2">Nome</th>
					<th class="column2">Prezzo</th>
					<th class="column2">Unità</th>
					<th class="column2"></th>
					<th class="column2"></th>
					</tr>
					</thead>
					
					<tbody class="table100-body">
						<g:each var="prodotto" in="${session.catalogo.prodotti}">
								<tr>
								
								<td>${prodotto.id}</td>
								<td>${prodotto.nome}</td>								
								<td><g:formatNumber number="${prodotto.prezzo}" format="##.##" />€</td>
								<td>${prodotto.unità}</td>
								<td>
									<g:link controller="dipendente" action="modificaDatiProdotto" id="${prodotto.id}">
										Modifica Dati
									</g:link>
								</td>
								<td>
									<g:link controller="catalogo" action="rimuoviProdotto" id="${prodotto.id}" onclick="return confirm('Sei sicuro? Il prodotto verrà eliminato definitivamente.')">
										Elimina
									</g:link>
								</td>
								</tr>
						</g:each>
				</tbody>
			</table>

	
	</section>


</body>
</html>