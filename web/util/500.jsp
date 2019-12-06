<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <link rel="icon" type="image/jpg" href="img/placa.jpg"/>
        <title>Erro Interno de Servidor</title>
    </head>
    <body>
        <div class="container-fluid">
            <br/>
            <div class="row h1 erro">
                <div class="col-md-12 text-center">Houston, we have a problem</div>
            </div>
            <br/>
            <hr/>
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-8 text-center">
                    <br/>
                    <p>Aten��o: Ve�culo de chassi n� ${Math.random()} precisa fazer recall urgente!</p>
                    <p>Errar � humano, certo? Ent�o... este pobre servidor pede desculpas...</p> 
                    <p>Enquanto voc� navega no nosso site, nossos mec�nicos estar�o consertando as pe�as defeituosas.</p>
                    <br/><br/>
                    <a class="btn btn-warning" href=".">Clique aqui para voltar � P�gina Inicial</a>
                </div>
                <div class="col-md-2"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>
