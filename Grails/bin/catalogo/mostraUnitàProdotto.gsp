<%@ page import="webshop.Prodotto" %>
<html>
<html>
<head>
    <meta name="layout" content="main"/>
	<asset:stylesheet src="infoCliente.css" />
    <title>Webshop Prodotto</title>
</head>
<body>

<div id="title" role="main">
    <section>
        <h1 class="header">Descrizione Prodotto</h1>
    </section>
</div>

<div class="home">
        	<g:link class="button" controller="negozio" action="home">Home</g:link>
        </div>

<div id="prodotti" role="main">
	<section class="catalogo">
		<br>
		<g:render template="unitàProdottoTemplate" model="map" />
	
	</section>
	

</div>

</body>
</html>