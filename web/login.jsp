<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="Cadastrado" type="java.lang.Boolean" scope="request"/>
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
        <title>Login</title>
    </head>
    <body>
        <div class="container-fluid">
            <jsp:include page="util/cabecalhoSemCegonha.html"/>
            <c:if test="${Cadastrado}">
                <div class="row alert-success">
                    <div class="col-md-4"></div>
                    <div class="col-md-4">Cadastro realizado com sucesso! Faça o login para entrar</div>
                    <div class="col-md-4"></div>
                </div>
            </c:if>
            <div class="row">
                <div class="col-md-4"></div>
                <div class="col-md-4">
                    <h3>Olá! Digite seus dados de login para continuar</h3>
                    <br/>
                    <form action="/entrar" method="POST">
                        <div class="form-group">
                            <label for="e-mail">E-mail:</label>
                            <input type="email" id="e-mail" name="email" class="form-control login"/>
                        </div>
                        <div class="form-group">
                            <label for="senha">Senha:</label>
                            <input type="password" id="senha" name="senha" class="form-control login"/>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary">Entrar</button>
                        </div>
                    </form>
                </div>
                <div class="col-md-4"></div>
            </div>
            <div class="row">
                <div class="col-md-4"></div>
                <div class="col-md-4 text-center">
                    <span>
                        Não é cadastrado(a)? 
                        <a href="/cadastro">Clique aqui para criar sua conta.</a> 
                    </span>
                </div>
                <div class="col-md-4"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>
