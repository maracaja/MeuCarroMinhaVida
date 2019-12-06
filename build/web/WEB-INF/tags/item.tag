<%@tag description="item usado nas listas de produtos" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@attribute name="lista" type="java.util.ArrayList" required="true"%>
<%@attribute name="pedido" type="java.lang.Boolean" required="true"%>

<c:choose>
    <c:when test="${pedido}">
        <c:forEach var="itemPedido" items="${lista}">
            <div class="row">
                <div class="col-md-4 text-center">
                    <a href="detalheProduto?id=${itemPedido.getProduto().getId()}">
                        <img src="${itemPedido.getProduto().getUrlFoto1()}" alt="Foto" class="imgVitrine"/>
                    </a>
                </div>
                <div class="col-md-6">
                    <div class="row h3">
                        <div class="col-md-12">
                            <a href="detalheProduto?id=${itemPedido.getProduto().getId()}">${itemPedido.getProduto().getMarca()} ${itemPedido.getProduto().getModelo()} ${itemPedido.getProduto().getAno()}</a>
                        </div>    
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-6">
                            <strong>Quantidade:</strong>
                            ${itemPedido.getQuantidade()}
                        </div>
                        <div class="col-md-6">
                            <strong>Preço unitário:</strong>
                            ${itemPedido.getPrecoString(true)}
                        </div>
                    </div>
                </div>
                <div class="col-md-2 text-right">
                    <strong>Preço total: ${itemPedido.getPrecoString(false)}</strong>
                </div>
            </div>
            <hr/>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <c:forEach var="produto" items="${lista}">
            <div class="row">
                <div class="col-md-4 text-center">
                    <img src="${produto.getUrlFoto1()}" alt="Foto" class="imgVitrine" />
                </div>
                <div class="col-md-5">
                    <div class="row h3">
                        <div class="col-md-12">
                            <a href="detalheProduto?id=${produto.getId()}">${produto.getMarca()} ${produto.getModelo()} ${produto.getAno()}</a>
                        </div>    
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-12">
                            <c:if test="${produto.isNovo()}">Novo</c:if>
                            <c:if test="${!produto.isNovo()}">Usado</c:if>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 text-right h3">
                    <strong>${produto.getPrecoString()}</strong>
                </div>
            </div>
            <hr/>
        </c:forEach>
    </c:otherwise>
</c:choose>
