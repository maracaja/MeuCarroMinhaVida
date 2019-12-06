<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="Cliente" type="model.Cliente" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <title>Meus Endereços</title>
    </head>
    <body>
        <div class="container-fluid">
            <mcmv:cabecalho/>
            <div class="row h2 text-center">Meus Endereços</div>
            <br/>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6 btn-group">
                    <a class="btn btn-primary center-block" href="/cadastroEndereco">Cadastrar um novo endereço</a>
                </div>
                <div class="col-md-3"></div>
            </div> 
            <br/>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6" id="listaEnderecos">
                    <hr/>
                    <c:forEach var="Endereco" items="${Cliente.getEnderecos()}">
                        <div class="row">
                            <div class="col-md-10 h3">${Endereco.getMarcador()}</div>
                            <div class="col-md-1">
                                <br/>
                                <form action="/cadastroEndereco" method="post">
                                    <input type="hidden" name="edit" value="${Endereco.getId()}"/>
                                    <button class="btn" type="submit">
                                        <span class="glyphicon glyphicon-pencil"></span>
                                    </button>
                                </form>
                                <br/>
                            </div>
                            <div class="col-md-1">
                                <br/>
                                <form action="/removerEndereco" method="post">
                                    <input type="hidden" name="remove" value="${Endereco.getId()}"/>
                                    <button class="btn" type="submit">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </button>
                                </form>
                                <br/>
                            </div>
                        </div>
                        <div class="row">
                            ${Endereco.getLogradouro()},&nbsp;${Endereco.getNumero()}<c:if test="${Endereco.getComplemento() != null}">,&nbsp;${Endereco.getComplemento()}</c:if>
                            <br/>
                            ${Endereco.getBairro()}&nbsp;-&nbsp;${Endereco.getCidade()}/${Endereco.getUf()}
                            <br/>
                            CEP: ${Endereco.getCep()}
                        </div>
                        <hr/>
                    </c:forEach>
                </div>
                <div class="col-md-3"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>
