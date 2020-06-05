<%@ page import="webshop.Cliente" %>
<html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Webshop Registrazione Cliente</title>
    <asset:stylesheet src="infoCliente.css" /> 
     <asset:stylesheet src="errorMessage.css"/> 
</head>
<body>

<div id="title" role="main">
    <section>
        <h1 class="header">Effettua Registrazione</h1>
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
		
<div>
	
	<g:form action="registraUsernamePassword" style="margin: 0 auto; width:320px"> 
		<p>Username:</p>
		<g:field type="text" name="username" required=""/>
		<br>
		<p>Password:</p>
		<g:passwordField name="password"  required=""/>
		<br>
		<p>Conferma Password:</p>
		<g:passwordField name="confermaPassword" required=""/>
		<g:submitButton name="Conferma"/>
	</g:form>
	
</div>

<br>


</body>
</html>