<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="Cliente" type="model.Cliente" scope="session"/>
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
        <title>Editar Dados</title>
    </head>
    <body>
        <div class="container-fluid">
            <mcmv:cabecalho/>
            <div class="row">
                <div class="col-md-12">
                    <div class="h2 text-center">Editar Dados</div>
                    <br/>
                    <c:if test="${Alterado}">
                        <div class="row alert-success">
                            <div class="col-md-4"></div>
                            <div class="col-md-4">
                                Dados atualizados com sucesso! 
                                <a href=".">Clique aqui para voltar à página inicial.</a>
                            </div>
                            <div class="col-md-4"></div>
                        </div>
                    </c:if>
                    <br/>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6">
                    <form action="/editarDados" method="POST">
                        <div class="row form-group">
                            <div class="col-md-12">
                                <label for="nome">Nome completo:</label>
                                <input type="text" name="nome" id="nome" value="${Cliente.getNome()}" class="form-control" required/> 
                            </div>
                        </div>
                        <div class="row form-group">
                            <div class="col-md-7">
                                <label for="cpf">CPF:</label>
                                <input type="text" name="cpf" id="cpf" value="${Cliente.getCpf()}" class="form-control" readonly/>
                            </div>
                            <div class="col-md-5">
                                <label for="dataNasc">Data de nascimento:</label>
                                <input type="date" name="dataNasc" id="dataNasc" class="form-control" value="${Cliente.getDataNasc()}"/>
                            </div>
                        </div>
                        <div class="row form-group">
                            <div class="col-md-12">
                                <label for="email">E-mail:</label>
                                <input type="text" name="email" id="email" value="${Cliente.getEmail()}" class="form-control" required/> <!-- Ver expressão regular para e-mail -->
                            </div>
                        </div>
                        <br/>
                        <div class="row form-group">
                            <div class="col-md-12">
                                <input type="submit" class="btn btn-success"/>
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
