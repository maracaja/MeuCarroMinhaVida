<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="ListaDestaques" type="java.util.ArrayList" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <link rel="icon" type="image/jpg" href="img/cegonha2.jpg" />
        <title>Meu Carro Minha Vida</title>
    </head>
    <body>
        <div class="container-fluid">
            <mcmv:cabecalho deslogado="util/cabecalho.jsp"/>
            <div class="row">
                <div class="col-md-12 h2 text-center">Confira os destaques do dia:</div>
            </div>
            <hr/>
            <mcmv:item lista="${ListaDestaques}" pedido="false"/>
            <br/>
            <div class="row">
                <div class="col-md-5 text-center">
                    <a href="/loja" class="btn btn-primary">Confira todas as nossas ofertas</a> 
                </div>
                <div class="col-md-7"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>
