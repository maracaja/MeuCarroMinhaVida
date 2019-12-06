<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="Cliente" type="model.Cliente" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <title>Compra finalizada!</title>
    </head>
    <body>
        <div class="container-fluid">
            <mcmv:cabecalho/>
            <div class="row h1 text-center">Sua compra foi finalizada</div>
            <hr/>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6 text-center">
                    <div class="row">
                        <div class="col-md-12">
                            <p>${Cliente.primeiroNome()}, seu pedido já foi registrado em nosso sistema e seus carros já estão a caminho do seu endereço!</p>
                            <p>Enquanto você espera ansiosamente sua frota chegar, nós oferecemos a você um serviço exclusivo: imagens em TEMPO REAL do caminhão. Confira abaixo:</p>
                            <br/>
                            <img src="https://thumbs.gfycat.com/CharmingPoliteAtlanticblackgoby-size_restricted.gif" alt=""/>
                        </div>
                    </div>
                    <br/><br/>
                    <a class="btn btn-primary" href=".">Clique aqui para voltar à Página Inicial</a>
                </div>
                <div class="col-md-3"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>
