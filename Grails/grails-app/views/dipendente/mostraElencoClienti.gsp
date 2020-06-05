<%@ page import="webshop.Cliente" %>

<html>
<head>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="errorMessage.css"/>
	<asset:stylesheet src="infoCliente.css"/>
    <asset:stylesheet src="/cssTable/main.css" /> 
    
    <title>Webshop Dipendente</title>
</head>

<body>
<h1 class="header">Ordini Clienti</h1>

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
					<th class="column2">Nome</th>
					<th class="column2">Cognome</th>
					<th class="column2">Username</th>
					<th class="column2">Numero Ordini</th>
					<th class="column2"></th>
					</tr>
					</thead>
					
					<tbody class="table100-body">
						<g:each var="cliente" in="${Cliente.list()}">
								<tr>
								
								<td>${cliente.nome}</td>
								<td>${cliente.cognome}</td>								
								<td>${cliente.username}</td>
								<td>${cliente.ordini.size()}</td>
								<td>
									<g:link controller="dipendente" action="mostraElencoOrdini" id="${cliente.id}">
										Visualizza Ordini
									</g:link>
								</td>
								</tr>
						</g:each>
				</tbody>
			</table>

	
	</section>


</body>
</html>