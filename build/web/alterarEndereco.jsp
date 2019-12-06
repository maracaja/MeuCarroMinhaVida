<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="Novo" type="java.lang.Boolean" scope="request"/>
<jsp:useBean id="Endereco" type="model.Endereco" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <title>Meus Endereços</title>
    </head>
    <body>
        <div class="container-fluid">
            <mcmv:cabecalho/>
            <div class="row h2 text-center">
                <c:if test="${Novo}">
                    Cadastrar novo
                    <c:url value="/novoEndereco" var="url"/>
                </c:if>
                <c:if test="${!Novo}">
                    Alterar
                    <c:url value="/atualizarEndereco" var="url"/>
                </c:if>
                endereço
            </div>
            <br/>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6">
                    <form action="${url}" method="post">
                        <div class="row form-group">
                            <div class="col-md-12">
                                <label for="marcador">Insira um identificador para seu endereço (ex.: "End. Comercial", "Casa da Sogra", "Apê da praia") - opcional:</label>
                                <input type="text" name="marcador" id="marcador" class="form-control" value="${Endereco.getMarcador()}"/>
                            </div>
                        </div>                        
                        <div class="row form-group">
                            <div class="col-md-3">
                                <label for="cep">Digite seu CEP:</label>
                                <input type="text" name="cep" id="cep" onchange="endereco(this.value)" class="form-control" value="${Endereco.getCep()}" required/>
                            </div>
                            <div class="col-md-9">
                                <label for="logradouro">Logradouro (Rua, Avenida, etc.):</label>
                                <input type="text" name="logradouro" id="logradouro" class="form-control" value="${Endereco.getLogradouro()}" readonly required/>
                            </div>
                        </div>
                        <div class="row form-group">
                            <div class="col-md-2">
                                <label for="numero">Número:</label>
                                <input type="text" name="numero" id="numero" class="form-control" value="${Endereco.getNumero()}" required/>
                            </div>
                            <div class="col-md-4">
                                <label for="complemento">Complemento:</label>
                                <input type="text" name="complemento" id="complemento" class="form-control" value="${Endereco.getComplemento()}"/>
                            </div>
                            <div class="col-md-6">
                                <label for="bairro">Bairro:</label>
                                <input type="text" name="bairro" id="bairro" class="form-control" value="${Endereco.getBairro()}" readonly/>
                            </div>
                        </div>
                        <div class="row form-group">
                            <div class="col-md-10">
                                <label for="cidade">Município:</label>
                                <input type="text" name="cidade" id="cidade" class="form-control" value="${Endereco.getCidade()}" readonly required/>
                            </div>
                            <div class="col-md-2">
                                <label for="uf">Estado:</label>
                                <input type="text" name="uf" id="uf" class="form-control" value="${Endereco.getUf()}" readonly required/> 
                            </div>
                        </div>
                        <br/>
                        <div class="row form-group">
                            <div class="col-md-12">
                                <c:if test="${!Novo}">
                                    <input type="hidden" name="id" value="${Endereco.getId()}"/>
                                </c:if>
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
