<%@ page import="webshop.Dipendente" %>
<html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Webshop Profilo Dipendente</title>
	<asset:stylesheet src="errorMessage.css"/> 
	<asset:stylesheet src="infoCliente.css" /> 
</head>
<body>

<div id="title" role="main">
        <h1 class="header">Pagina Profilo Dipendente</h1>
        <br>
        <section class="home">
        	<p class="paragraph">Da qui puoi <mark class="blue">aggiornare il Catalogo</mark> o <mark class="blue">visualizzare gli Ordini dei Clienti</mark></p>
        </section>
<div id="demoFont" align="center">

	<g:if test="${flash.message != ''}">
		<h4>${flash.message}</h4>
		<br>
	</g:if>
</div>
        
        <div class="home">
        	<g:link class="button" controller="negozio" action="home">Home</g:link>
        </div>
        
</div>
<br>
<div>
	<section>
	
		<g:render template="infoDipendenteTemplate" model="dipendenteMap" />
	
	</section>
</div>
<br>
<div>
    <section class="home">
    	<g:link class="button" action="mostraElencoProdotti">Aggiorna Catalogo</g:link>
    	<g:link class="button" action="mostraElencoClienti">Visualizza Ordini Clienti</g:link>
		<g:link class="button" action="mostraPaginaModificaPassword">Modifica Password</g:link>
    </section>
	
</div>


</body>
</html>