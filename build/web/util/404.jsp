<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <link rel="icon" type="image/jpg" href="img/placa.jpg"/>
        <title>Página não encontrada</title>
    </head>
    <body>
        <div class="container-fluid">
            <br/>
            <div class="row h1 erro">
                <div class="col-md-12 text-center">Página não encontrada</div>
            </div>
            <br/>
            <hr/>
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-8 text-center">
                    <br/>
                    <p>Parece que o GPS não funcionou muito bem... ou a estrada que te levou até aqui está em obras.</p>
                    <p>Mas não se preocupe: voltar à rota certa aqui é rápido e não gasta gasolina!</p>
                    <br/><br/>
                    <a class="btn btn-warning" href=".">Clique aqui para voltar à Página Inicial</a>
                </div>
                <div class="col-md-2"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>
