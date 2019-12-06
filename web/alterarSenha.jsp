<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="Alterado" type="java.lang.Boolean" scope="request"/>
<jsp:useBean id="Aviso" type="java.lang.String" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <script>
            $(document).ready(function() 
            {
                <c:if test="${Aviso.length() > 0}">
                    alert("${Aviso}"); 
                </c:if>   
            });
        </script>
        <title>Alteração de Senha</title>
    </head>
    <body>
        <div class="container-fluid">
            <mcmv:cabecalho/>
            <div class="row h2 text-center">Alterar senha</div>
            <br/>
            <c:if test="${Alterado}">
                <div class="row alert-success">
                    <div class="col-md-4"></div>
                    <div class="col-md-4">
                        Senha alterada com sucesso! 
                        <a href=".">Clique aqui para voltar à página inicial.</a>
                    </div>
                    <div class="col-md-4"></div>
                </div>
            </c:if>
            <br/>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6">
                    <form action="/alterarSenha" method="post">
                        <div class="row form-group">
                            <div class="col-md-6">
                                <label for="senha">Digite a senha atual:</label>
                                <input type="password" name="senhaAtual" id="senhaAtual" class="form-control" required/>
                            </div>
                            <div class="col-md-6"></div>
                        </div>
                        <br/><br/>
                        <div class="row form-group">
                            <div class="col-md-6">
                                <label for="senha">Digite a nova senha:</label>
                                <input type="password" name="senhaNova" id="senha" class="form-control" oninput="forcaSenha(this.value); verificaSenha();" required/>
                                <span id="forca" hidden>
                                    Força da senha:&nbsp;<strong id="classif"></strong>&nbsp;
                                    <c:forEach var="n" begin="1" end="5" step="1">
                                        <span class="glyphicon glyphicon-minus" id="nota-${n}"></span>
                                    </c:forEach>
                                </span>
                            </div>
                            <div class="col-md-6">
                                <label for="senha-conf">Confirme a nova senha:</label>
                                <input type="password" id="senha-conf" oninput="verificaSenha()" class="form-control" required/>
                                <span id="warning-senha" class="warning"></span>
                            </div>
                        </div>
                        <br/>
                        <div class="row form-group">
                            <div class="col-md-12">
                                <input type="submit" class="btn btn-success" id="enviar"/>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-3"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>
