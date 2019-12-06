<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="Cliente" type="model.Cliente" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <title>Confirmação de pedido</title>
    </head>
    <body>
        <div class="container-fluid">
            <mcmv:cabecalho deslogado="/util/cabecalho.jsp"/>
            <br/><br/>
            <c:if test="${Cliente.getEnderecos().isEmpty()}">
                <div class="row h4 text-center">Você não tem um endereço cadastrado em nosso site... clique no botão abaixo para poder prosseguir com seu pedido:</div>
            </c:if>
            <c:if test="${!Cliente.getEnderecos().isEmpty()}">
                <div class="row h4 text-center">Estamos quase lá... escolha o endereço de entrega:</div>
                <br/>
                <div class="row ">
                    <div class="col-md-3"></div>
                    <div class="col-md-6">
                        <form action="/finalizarCompra" method="post">
                            <hr/>
                            <c:forEach var="Endereco" items="${Cliente.getEnderecos()}">
                                <div class="row">
                                    <div class="col-md-1">
                                        <input type="radio" name="enderecoEntrega" value="${Endereco.getId()}" required/>
                                    </div>
                                    <div class="col-md-11">
                                        <div class="row h3">${Endereco.getMarcador()}</div>
                                        <br/>
                                        <div class="row">
                                            ${Endereco.getLogradouro()},&nbsp;${Endereco.getNumero()}<c:if test="${Endereco.getComplemento() != null || Endereco.getComplemento().length() > 0}">,&nbsp;${Endereco.getComplemento()}</c:if>
                                            <br/>
                                            ${Endereco.getBairro()}&nbsp;-&nbsp;${Endereco.getCidade()}/${Endereco.getUf()}
                                            <br/>
                                            CEP: ${Endereco.getCep()}
                                        </div>
                                    </div>
                                </div>
                                <hr/>
                            </c:forEach>
                            <div class="row">
                                <div class="col-md-5"></div>
                                <div class="col-md-2 text-center align-content-center">
                                    <button type="submit" class="btn btn-success">FINALIZAR COMPRA</button>
                                </div>
                                <div class="col-md-5"></div>
                            </div>
                        </form>
                    </div>    
                    <div class="col-md-3"></div>
                </div>
                <br/><br/>
                <div class="row h5 text-center">Deseja receber em outro local? Clique no botão abaixo:</div>
            </c:if>
            <div class="row">
                <div class="col-md-5"></div>
                <div class="col-md-2 text-center align-content-center">
                    <a class="btn btn-primary" href="/cadastroEndereco">Cadastrar um novo endereço</a>
                </div>
                <div class="col-md-5"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>
