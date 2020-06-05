<%@ page import="webshop.Cliente" %>

<html>
<head>
    <meta name="layout" content="main"/>
     <asset:stylesheet src="errorMessage.css"/>
          <asset:stylesheet src="infoCliente.css"/>
    <title>Webshop Login</title>
</head>

<body>
<h1 class="header">Login Clienti</h1>

<div id="demoFont" align="center">
<g:if test="${flash.message != ''}">
		<h4>${flash.message}</h4>
		<br>
		</g:if>
</div>

<div class="home">
	<g:link class="button" controller="negozio" action="home">Home</g:link>
</div> 
<br>

<div>
<g:form action="userLogin" style="margin: 0 auto; width:320px"> 
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