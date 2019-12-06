<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="Cliente" type="model.Cliente" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <title>Meus Pedidos</title>
    </head>
    <body>
        <div class="container-fluid">
            <mcmv:cabecalho/>
            <div class="row h2 text-center">Meus Pedidos</div>
            <br/>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6">
                    <hr/>
                    <c:forEach var="Pedido" items="${Cliente.getPedidos()}">
                        <div class="row">
                            <div class="col-md-3">
                                Pedido n°&nbsp;
                                <a class="h3" href="/detalhePedido?id=${Pedido.getId()}">${Pedido.getCodigo()}</a>
                            </div>
                            <div class="col-md-6">
                                <div class="row">
                                    <div class="col-md-12">
                                        <strong>Data/Hora:</strong>
                                        ${Pedido.dataFormatada()}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <strong>Endereço de entrega:</strong>
                                        ${Pedido.getEntrega().toString()}
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3 h5">
                                <strong>Valor Total: ${Pedido.getPrecoString()}</strong>
                            </div>
                        </div>
                        <hr/>
                    </c:forEach>
                </div>
                <div class="col-md-3"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>
