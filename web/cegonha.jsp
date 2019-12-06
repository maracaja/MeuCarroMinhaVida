<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="Carrinho" type="model.Carrinho" scope="session"/>
<jsp:useBean id="Cliente" type="model.Cliente" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <link rel="icon" type="image/png" href="img/cegonha1.png"/>
        <title>Minha Cegonha</title>
    </head>
    <body>
       <div class="container-fluid">
            <mcmv:cabecalho deslogado="util/cabecalhoSemCegonha.html"/>
            <div class="row">
                <div class="col-md-12">
                    <h2 class="text-center">
                        <img src="img/cegonha1.png" alt="Minha Cegonha" class="img-h2"/>
                        <c:if test="${Cliente.getId() == -1}">Minha Cegonha</c:if>
                        <c:if test="${Cliente.getId() != -1}">Cegonha de ${Cliente.primeiroNome()}</c:if>
                    </h2>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-md-2"></div>
                <div class="table-responsive col-md-8">
                    <c:if test="${Carrinho.getItens().isEmpty()}">
                        <h3 class="text-center">Cegonha vazia!</h3>
                    </c:if>
                    <c:if test="${!Carrinho.getItens().isEmpty()}">
                        <table class="table table-bordered table-hover text-center">
                            <thead class="text-center">
                                <tr>
                                    <th class="col-xs-6" scope="col">Produto</th>
                                    <th class="col-xs-3" scope="col">Quantidade</th>
                                    <th class="col-xs-2" scope="col">Preço</th>
                                    <th class="col-xs-1" scope="col">Remover</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="ItemProd" items="${Carrinho.getItens()}">
                                    <tr>
                                        <td>
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <img src="${ItemProd.getProduto().getUrlFoto1()}" alt="Foto" class="imgCarrinho"/>
                                                </div>
                                                <div class="col-md-8">
                                                    <br/>
                                                    <h3 class="text-center">                                               
                                                        <a href="detalheProduto?id=${ItemProd.getProduto().getId()}" style="vertical-align: middle">
                                                            ${ItemProd.getProduto().getMarca()} ${ItemProd.getProduto().getModelo()} ${ItemProd.getProduto().getAno()}
                                                        </a>
                                                    </h3>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="celula" style="vertical-align: middle">
                                            <div class="row">
                                                <div class="col-md-3">
                                                    <c:if test="${ItemProd.getQtd() > 1}">
                                                        <a href="alteraItem?id=${ItemProd.getProduto().getId()}&acr=false" class="menos" style="vertical-align: middle">
                                                            <span class="glyphicon glyphicon-minus-sign" aria-hidden="true" style="color:#E60000"></span>
                                                        </a>
                                                    </c:if>
                                                </div>
                                                <div class="col-md-6">
                                                    <input type="number" readonly="true" value="${ItemProd.getQtd()}" class="inputDetalhes"/>
                                                </div>
                                                <div class="col-md-3">
                                                    <a href="alteraItem?id=${ItemProd.getProduto().getId()}&acr=true" class="mais" style="vertical-align: middle">
                                                        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true" style="color:#009000"></span>
                                                    </a>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="celula" style="vertical-align: middle">
                                            ${ItemProd.getPrecoString()}
                                        </td>
                                        <td class="celula" style="vertical-align: middle">
                                             <a href="removerProduto?id=${ItemProd.getProduto().getId()}">
                                                 <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                             </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <div class="row">
                        <div class="col-md-8"></div>
                        <div class="col-md-4">
                            <strong id="totalCarrinho">TOTAL: ${Carrinho.getTotalString()}</strong>
                        </div>
                    </div>
                </div>
                <div class="col-md-2"></div>
            </div>
            <br/>
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-6">
                    <a class="btn btn-primary" href="./loja">Voltar à lista de produtos</a>
                    <br/><br/>
                </div>
                <div class="col-md-4">
                    <c:if test="${!Carrinho.getItens().isEmpty()}">
                        <a class="btn btn-success comprar" href="/confirmarPedido">
                            <strong>
                                <span class="glyphicon glyphicon-shopping-cart"></span>
                                &nbsp;COMPRAR
                            </strong>
                        </a>
                    </c:if>
                </div>
            </div>
            <%@include file="/util/rodape.jspf"%>
       </div>
    </body>
</html>