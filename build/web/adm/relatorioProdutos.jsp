<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="ListaProdutos" type="java.util.List" scope="request"/>

<!DOCTYPE HTML>
<html>
    <head>
        <mcmv:head/> 
        <title>Relatório - Produtos</title>
    </head>
    <body>
        <div class="container-fluid">
            <mcmv:admin/>
            <div class="row h1 text-center">Listagem de Produtos</div>
            <hr/>
            <div class="row">
                <div class="col-md-1"></div>
                <div class="col-md-10">
                    <div class="row button-group text-center">
                        <div class="col-md-6 text-center">
                            <a class="btn btn-primary" href="/novoProduto">Adicionar um novo produto</a>
                        </div>
                        <div class="col-md-6 text-center">
                            <a class="btn btn-success" onclick="apenasAtivos()" id="filtro">Mostrar apenas ativos</a>
                        </div>
                    </div>
                    <br/>
                    <div class="row table-responsive">
                        <table class="table table-hover table-bordered table-condensed">
                            <thead>
                                <tr>
                                    <th class="col-xs-1" scope="col">Ativo</th>
                                    <th class="col-xs-1" scope="col">ID</th>
                                    <th class="col-xs-2" scope="col">Marca</th>
                                    <th class="col-xs-3" scope="col">Modelo</th>
                                    <th class="col-xs-1" scope="col">Ano</th>
                                    <th class="col-xs-1" scope="col">Novo</th>
                                    <th class="col-xs-2" scope="col">Preço</th>
                                    <th class="col-xs-1" scope="col">Editar</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="Produto" items="${ListaProdutos}">
                                    <tr <c:if test="${!Produto.isAtivo()}">class="inativo"</c:if>>
                                        <td>
                                            <c:if test="${Produto.isAtivo()}">Sim</c:if>
                                            <c:if test="${!Produto.isAtivo()}">Não</c:if>
                                        </td>
                                        <td>${Produto.getId()}</td>
                                        <td>${Produto.getMarca()}</td>
                                        <td>${Produto.getModelo()}</td>
                                        <td>${Produto.getAno()}</td>
                                        <td>
                                            <c:if test="${Produto.isNovo()}">Sim</c:if>
                                            <c:if test="${!Produto.isNovo()}">Não</c:if>
                                        </td>
                                        <td>${Produto.getPrecoString()}</td>
                                        <td>
                                            <a class="btn btn-default" href="/editarProduto?id=${Produto.getId()}">
                                                <span class="glyphicon glyphicon-pencil"></span>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="col-md-1"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>