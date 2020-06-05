<%@ page import="webshop.Dipendente" %>
<html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Webshop Profilo Dipendente</title>
    <asset:stylesheet src="infoCliente.css" /> 
     <asset:stylesheet src="errorMessage.css"/> 
</head>
<body>

<div id="title" role="main">
    <section>
        <h1 class="header">Modifica Password</h1>
        
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
		
<div id="clienti" role="main">
	<section class="datiCliente">
		

<div>
	
	<g:form action="modificaPassword" style="margin: 0 auto; width:320px"> 
		<p>Vecchia Password:</p>
		<g:passwordField name="vecchiaPassword" required=""/>
		<br>
		<p >Nuova Password:</p>
		<g:passwordField name="nuovaPassword"  required=""/>
		<br>
		<p>Conferma Nuova Password:</p>
		<g:passwordField name="confermaNuovaPassword" required=""/>
		<g:submitButton name="Conferma"/>
	</g:form>
	
</div>
		
	</section>
	
</div>

<br>


</body>
</html>