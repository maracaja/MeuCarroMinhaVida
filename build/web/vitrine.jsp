<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="ListaFull" type="java.util.List" scope="request" />
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
                <div class="col-md-2"></div>
                <div class="col-md-8">
                    <hr/>
                    <mcmv:item lista="${ListaFull}" pedido="false"/>
                </div>
                <div class="col-md-2"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>