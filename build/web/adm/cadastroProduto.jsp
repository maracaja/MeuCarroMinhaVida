<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="Novo" type="java.lang.Boolean" scope="request"/>
<jsp:useBean id="ListaMarcas" type="java.util.List" scope="request"/>
<jsp:useBean id="Produto" type="model.Produto" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <title>Cadastrar Produto</title>
    </head>
    <body>
        <c:if test="${!Novo}">
            <c:url var="url" value="./editarProduto"/>
        </c:if>
        <c:if test="${Novo}">
            <c:url var="url" value="/novoProduto"/>
        </c:if>
        <div class="container-fluid">
            <mcmv:admin/>
            <div class="row h1 text-center">Cadastro de Produtos</div>
            <hr/>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6">
                    <form action="${url}" method="post" enctype="multipart/form-data">
                        <div class="row form-group">
                            <div class="col-md-8">
                                <label for="modelo">Modelo:&nbsp;</label>
                                <input type="text" id="modelo" name="modelo" class="form-control" value="${Produto.getModelo()}" required/>
                            </div>
                            <div class="col-md-4">
                                <label for="marca">Marca:&nbsp;</label>
                                <select name="marca" id="marca" class="form-control">
                                    <c:forEach var="Marca" items="${ListaMarcas}">
                                        <option value="${Marca.getId()}" <c:if test="${Marca.getId() == Produto.getIdMarca()}">selected</c:if>>${Marca.getNomeMarca()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="row form-group">
                            <div class="col-md-2">
                                <label for="novo">Novo?&nbsp;</label>
                                <select name="novo" id="novo" class="form-control">
                                    <option value="true" <c:if test="${Produto.isNovo()}">selected</c:if>>Sim</option>
                                    <option value="false"<c:if test="${!Produto.isNovo()}">selected</c:if>>Não</option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <label for="ativo">À venda?&nbsp;</label>
                                <select name="ativo" id="ativo" class="form-control">
                                    <option value="true" <c:if test="${Produto.isAtivo()}">selected</c:if>>Sim</option>
                                    <option value="false" <c:if test="${!Produto.isAtivo()}">selected</c:if>>Não</option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label for="ano">Ano:&nbsp;</label>
                                <input type="number" id="ano" name="ano" value="${Produto.getAno()}" min="1900" max="2020" class="form-control" required/>
                            </div>
                            <div class="col-md-5">
                                <label for="preco">Preço (R$):&nbsp;</label>
                                <input type="number" name="preco" id="preco" min="0.01" step="0.01" class="form-control" value="${Produto.getPreco()}" required/>
                            </div>
                        </div>
                        <div class="row form-group">
                            <label for="descricao">Descrição do produto:&nbsp;</label>
                            <textarea id="descricao" name="descricao" class="form-control" style="height: 250px" >${Produto.getDescricao()}</textarea>
                        </div>
                        <div class="row form-group">
                            <div class="col-md-12">
                                <div class="row h4 text-center">Adicione aqui até 3 fotos do produto:</div>
                                <div class="row">
                                    <div class="col-md-4">
                                        <input type="file" id="foto1" name="foto1" class="form-control-file"/>
                                    </div>
                                    <div class="col-md-4">
                                        <input type="file" id="foto2" name="foto2" class="form-control-file"/>
                                    </div>
                                    <div class="col-md-4">
                                        <input type="file" id="foto3" name="foto3" class="form-control-file"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br/><br/>
                        <div class="row form-group">
                            <c:if test="${!Novo}">
                                <input type="hidden" name="id" value="${Produto.getId()}"/>
                            </c:if>
                            <input type="submit" class="btn btn-success"/>
                        </div>
                    </form>
                </div>
                <div class="col-md-3"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>
