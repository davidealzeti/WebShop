<%@ page import="webshop.Catalogo" %>
<html>
<html>
<head>
    <meta name="layout" content="main"/>
     <asset:stylesheet src="errorMessage.css"/>
     <asset:stylesheet src="infoCliente.css" />
     <asset:stylesheet src="/cssTable/main.css" /> 
    <title>Webshop HomePage</title>
</head>
<body>

<div class="home">

    <section>
        <h1 class="header">Catalogo</h1>
    </section>
    <p class="paragraph">Seleziona i prodotti da aggiungere al tuo Ordine nel Catalogo sottostante</p>
   	<br>
</div>
   	
<div>

<div>
   <g:if test="${session.prodotti != null}">
    <div class="home">
    	<g:link class="button" controller="ordine" action="effettuaRiepilogoOrdine">Completa Ordine</g:link>
    </div>
   </g:if>
</div>
   	
    
    	<g:if test="${session.name == 'user'}">
	        <section class="topright">
		    		<g:link class="button" controller="cliente" action="show" id="${session.cliente.id}">
		    		<b>Profilo Personale</b>
		    		</g:link>
			</section>
		            <br>    
	        <section class="topright">	        	
		        	<g:link class="button" controller="cliente" action="logout">
		        	<b>Logout</b>
		        	</g:link>
			</section>
        </g:if>
        
        <g:elseif test="${session.name == 'dipendente'}">
	        <section class="topright">
		        	<g:link class="button" controller="dipendente" action="show" id="${session.dipendente.id}">
		            <b>Profilo Dipendente</b>
		            </g:link>
			</section>
			<br>
	        <section class="topright">	        	
		        	<g:link class="button" controller="dipendente" action="logout">
		        	<b>Logout</b>
		        	</g:link>
			</section>
		</g:elseif>
		
        <g:else>
	        <section class="topright">
		        	<g:link class="button" controller="negozio" action="login">
		            <b>Login</b>
		            </g:link>
			</section>
		            <br>
			<section class="topright">
		            <g:link class="button" controller="cliente" action="mostraRegistrazionePrimoStep">
		            <b>Registrazione</b>
		            </g:link>
			</section>     
        </g:else>
        
</div>

<div id="demoFont" align="center">
	<g:if test="${flash.message != ''}">
		<h4>${flash.message}</h4>
		<br>
	</g:if>
</div>

<div class="container-table100">
		
	<section class="wrap-table100">
	
			<table class="table100 ver4">
				
					<thead>
					<tr>
					<th class="column1">Nome Prodotto</th>
					<th class="column3">Prezzo</th>
					</tr>
					</thead>
					
					<tbody class="table100-body">
						<g:each var="prodotto" in="${Catalogo.get(1).prodotti}">
								<tr>
								
								<td>
								<g:link controller="catalogo" action="mostraUnitàProdotto" id="${prodotto.id}">
									${prodotto.nome}
								</td>
								
								<td>${prodotto.prezzo}€</td>
								</tr>
							</g:link>
						</g:each>
				</tbody>
			</table>
	</section>
</div>

<br>

<div>
	<g:if test="${session.name == null}">
		<g:link class="button" controller="dipendente" action="login">Login Personale Dipendente</g:link>
	</g:if>
</div>

</body>
</html>