<%@ page import="webshop.Dipendente" %>

<div class="home">
	<g:set var="dipendente" value="${dipendente}"/>
	<p class="paragraph"><mark class="red">Nome:</mark> ${dipendente.nome}</p>
	<p class="paragraph"><mark class="red">Cognome:</mark> ${dipendente.cognome}</p>
	<p class="paragraph"><mark class="red">Username:</mark> ${dipendente.username}</p>
</div>


