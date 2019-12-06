<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="erroInfo" type="java.lang.String" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <link rel="icon" type="image/jpg" href="/img/placa.jpg"/>
        <title>Erro</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row h1 text-center">Ops... Algo deu errado</div>
            <hr/>
            <div class="row">
                <div class="col-md-12 text-center">
                    <p>O câmbio quebrou, ficamos só com a sexta marcha. Vamos à oficina para garantir que tudo rode bem daqui pra frente</p>
                    <br/>
                    <p><i>${erroInfo}</i></p>
                    <br/><br/>
                    <a class="btn btn-warning" href=".">Clique aqui para voltar à Página Inicial</a>
                </div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>
