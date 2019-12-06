<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <link rel="icon" type="image/jpg" href="img/placa.jpg"/>
        <title>P�gina n�o encontrada</title>
    </head>
    <body>
        <div class="container-fluid">
            <br/>
            <div class="row h1 erro">
                <div class="col-md-12 text-center">P�gina n�o encontrada</div>
            </div>
            <br/>
            <hr/>
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-8 text-center">
                    <br/>
                    <p>Parece que o GPS n�o funcionou muito bem... ou a estrada que te levou at� aqui est� em obras.</p>
                    <p>Mas n�o se preocupe: voltar � rota certa aqui � r�pido e n�o gasta gasolina!</p>
                    <br/><br/>
                    <a class="btn btn-warning" href=".">Clique aqui para voltar � P�gina Inicial</a>
                </div>
                <div class="col-md-2"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>
