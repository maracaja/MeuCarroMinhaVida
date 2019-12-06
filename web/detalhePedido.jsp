<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="Pedido" type="model.Pedido" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <title>Pedido nº ${Pedido.getCodigo()}</title>
    </head>
    <body>
        <div class="container-fluid">
            <mcmv:cabecalho/>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6">
                    <div class="row dados">
                        <div class="col-md-12">
                            <div class="row h2 text-center">Dados do pedido</div>
                            <div class="row">
                                <div class="col-md-12">
                                    <p>
                                        <strong>Código:</strong>
                                        ${Pedido.getCodigo()}
                                    </p>
                                    <p>
                                        <strong>Data/Hora do pedido:</strong>
                                        ${Pedido.dataFormatada()}
                                    </p>
                                    <p>
                                        <strong>Entregue em:</strong>
                                        ${Pedido.getEntrega().toString()}
                                    </p>
                                    <p>
                                        <strong>Valor total:</strong>
                                        ${Pedido.getPrecoString()}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="row h2 text-center">Produtos comprados</div>
                            <hr/>
                            <mcmv:item lista="${Pedido.getItens()}" pedido="true"/>
                        </div>
                    </div>
                </div>
                <div class="col-md-3"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>
