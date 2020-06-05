<%@ page import="webshop.Cliente" %>

<div class="colonnato">
    <f:display bean="cliente" except="username, password, ordini, cap, codiceFiscale, telefono, email, indirizzoRecapito" displayStyle="table"/>
    <f:display bean="cliente" except="username, password, dataDiNascita, ordini, nome, cognome, indirizzo, città, provincia" displayStyle="table"/>    
</div>


