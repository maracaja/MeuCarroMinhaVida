<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="Aviso" type="java.lang.String" scope="request"/>
<jsp:useBean id="Titulo" type="java.lang.String" scope="request"/>
<jsp:useBean id="ListaPedidos" type="java.util.List" scope="request"/>
<jsp:useBean id="Vendidos" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="Faturamento" type="java.lang.String" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <script>
            $(document).ready(function() 
            {
                max("inicio");
                max("final");
                <c:if test="${Aviso.length() > 0}">
                    alert("${Aviso}"); 
                </c:if>   
            });
        </script>
        <title>Relatório - Pedidos</title>
    </head>
    <body>
        <div class="container-fluid">
            <mcmv:admin/>
            <div class="row h1 text-center">Relatório de Pedidos</div>
            <hr/>
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-8">
                    <div class="row text-center form-group form-horizontal">
                        <form action="/relatorioPedidos" method="post">
                            <span class="h4">Período:&nbsp;</span>
                            <input type="date" id="inicio" name="inicio"/>
                            &nbsp;a&nbsp;
                            <input type="date" id="final" name="final"/>
                            &nbsp;&nbsp;&nbsp;
                            <input type="submit" class="btn btn-success"/>
                        </form>
                    </div>
                    <hr/>
                    <div class="row table-responsive">
                        <div class="col-md-12">
                            <c:if test="${Titulo.length() > 0}">
                                <div class="row h3 text-center">${Titulo}</div>
                                <br/>
                                <div class="row h4">
                                    <div class="col-md-6">
                                        <strong>Carros vendidos: ${Vendidos}</strong>
                                    </div>
                                    <div class="col-md-6">
                                        <strong>Faturamento total no período: ${Faturamento}</strong>
                                    </div>
                                </div>    
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th class="col-xs-2">Código</th>
                                            <th class="col-xs-4">Data/Hora</th>
                                            <th class="col-xs-3">Carros comprados</th>
                                            <th class="col-xs-3">Valor total</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="Pedido" items="${ListaPedidos}">  
                                            <tr>
                                                <td class="col-xs-2">
                                                    <a onclick="document.getElementById('${Pedido.getId()}').hidden = false">${Pedido.getCodigo()}</a>
                                                </td>
                                                <td class="col-xs-4">${Pedido.dataFormatada()}</td>
                                                <td class="col-xs-3">${Pedido.totalProdutos()}</td>
                                                <td class="col-xs-3">${Pedido.getPrecoString()}</td>
                                            </tr>
                                            <tr id="${Pedido.getId()}" hidden>
                                                <td colspan="4">
                                                    <div class="row table-responsive">
                                                        <div class="col-md-1"></div>
                                                        <div class="col-md-10">
                                                            <table class="table table-bordered table-hover">
                                                                <thead>
                                                                    <tr>
                                                                        <th class="col-xs-1" scope="col">ID</th>
                                                                        <th class="col-xs-2" scope="col">Marca</th>
                                                                        <th class="col-xs-4" scope="col">Modelo</th>
                                                                        <th class="col-xs-1" scope="col">Ano</th>
                                                                        <th class="col-xs-3" scope="col">Preço Unit.</th>
                                                                        <th class="col-xs-1" scope="col">Qtd.</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <c:forEach var="ItemPedido" items="${Pedido.getItens()}">
                                                                        <tr>
                                                                            <td>${ItemPedido.getProduto().getId()}</td>
                                                                            <td>${ItemPedido.getProduto().getMarca()}</td>
                                                                            <td>${ItemPedido.getProduto().getModelo()}</td>
                                                                            <td>${ItemPedido.getProduto().getAno()}</td>
                                                                            <td>${ItemPedido.getPrecoString(true)}</td>
                                                                            <td>${ItemPedido.getQuantidade()}</td>
                                                                        </tr>
                                                                    </c:forEach>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <div class="col-md-1"></div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>        
                        </div>
                    </div>
                </div>
                <div class="col-md-2"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div>
    </body>
</html>
