<%@ page import="webshop.Dipendente" %>

<html>
<head>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="errorMessage.css"/>
	<asset:stylesheet src="infoCliente.css"/>
    <title>Webshop Login Dipendente</title>
</head>

<body>
<h1 class="header">Login Dipendente</h1>
<br>
<div class="home">
<p class="redparagraph">Attenzione! Questa pagina è dedicata solamente al personale Dipendente.</p>
<p class="paragraph">Se stai cercando la pagina di login per i Clienti <g:link controller="negozio" action="login"> clicca qui </g:link></p>
</div>

<div id="demoFont" align="center">
<g:if test="${flash.message != ''}">
		<h4>${flash.message}</h4>
		<br>
		</g:if>
</div>
 

<div>
	<g:form action="dipendenteLogin" style="margin: 0 auto; width:320px"> 
		<p>Username:</p>
		<g:field type="text" name="username" required=""/>
		<br>
		<p>Password:</p>
		<g:field type="password"  name="password" required=""/>
		<g:submitButton name="Login"/>
	</g:form>
</div>


</body>
</html>