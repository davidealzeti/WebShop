<%@ page import="webshop.Cliente" %>

<div>
	
	<g:form action="updatePassword" style="margin: 0 auto; width:320px"> 
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
	


