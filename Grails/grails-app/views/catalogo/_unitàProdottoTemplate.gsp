<%@ page import="webshop.Prodotto" %>
<div class="prodotto" id="${prodotto?.id}">
	<section class="colonnato">
	   <p class="paragraph"> Hai selezionato il prodotto: ${prodotto.nome}. <br>
	   Il prezzo del prodotto è ${prodotto.prezzo}€. <br>
	   Specifica la quantità che desideri acquistare e clicca sul pulsante in basso per aggiungerlo al tuo Ordine. </p>
   		<br>
   </section>
   
   <g:if test="${prodotto.unità == 0}">
        <section class="home">
	    	<p class="redparagraph">Il prodotto è esaurito.</p>
		</section>
	</g:if>
	<g:elseif test="${prodotto.unità < 5 && prodotto.unità > 0}">
        <section>
        	<div class="home">
        	<br>
        	<p class="redparagraph">Attenzione! Il prodotto è in esaurimento</p>
        	</div>
	    	<g:form controller="ordine" action="aggiungiProdotto" style="margin: 0 auto; width:320px" params="[prodottoId: prodotto.id]"> 
					<p>Quantità:</p>
					<g:select name="quantità" from="${1}" value="${1}"/>
					<br>
					<g:submitButton name="Aggiungi Prodotto"/>
				</g:form>
		</section>
	</g:elseif>
	<g:else>
		<section>
			<g:form controller="ordine" action="aggiungiProdotto" style="margin: 0 auto; width:320px" params="[prodottoId: prodotto.id]"> 
					<p>Quantità:</p>
					<g:select name="quantità" from="${1..9}" value="${1}"/>
					<br>
					<g:submitButton name="Aggiungi Prodotto"/>
				</g:form>
		</section>
	</g:else>
   
</div>