<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="Produto" class="model.Produto" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <script type="text/javascript">$('.carousel').carousel();</script>
        <link rel="icon" type="image/jpg" href="${Produto.getLogoMarca()}" />
        <title>${Produto.getMarca()} ${Produto.getModelo()}</title>
    </head>
    <body>
        <div class="container-fluid">
            <mcmv:cabecalho deslogado="util/cabecalho.jsp"/>
            <div class="row">
                <div class="col-md-4">
                    <div id="carouselExampleIndicators" class="carousel slide">
                        <ol class="carousel-indicators">
                            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                            <c:if test="${Produto.getUrlFoto2() != null}">
                                <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                            </c:if>
                            <c:if test="${Produto.getUrlFoto3() != null}">
                                <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                            </c:if>
                        </ol>
                        <div class="carousel-inner">
                            <c:if test="${Produto.getUrlFoto1() != null}">
                                <div class="item active">
                                    <img class="d-block w-100" src="${Produto.getUrlFoto1()}" alt="Foto" />
                                </div>
                            </c:if>
                            <c:if test="${Produto.getUrlFoto2() != null}">
                                <div class="item">
                                    <img class="d-block w-100" src="${Produto.getUrlFoto2()}" alt="Foto" />
                                </div>
                            </c:if>
                            <c:if test="${Produto.getUrlFoto3() != null}">
                                <div class="item">
                                    <img class="d-block w-100" src="${Produto.getUrlFoto3()}" alt="Foto" />
                                </div>
                            </c:if>
                        </div>
                        <a class="left carousel-control" href="#carouselExampleIndicators" role="button" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        </a>
                        <a class="right carousel-control" href="#carouselExampleIndicators" role="button" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        </a>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="row">
                        <div class="col-md-12">
                            <h2>
                                <img class="imgMarca" src="${Produto.getLogoMarca()}" alt="${Produto.getMarca()}" /> 
                                ${Produto.getModelo()} <c:if test="${Produto.isNovo()}"> 0 km</c:if>
                            </h2>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <!--h2>Ficha Técnica:</h2-->
                            <p><strong>Marca:</strong> ${Produto.getMarca()}</p>
                            <p><strong>Modelo:</strong> ${Produto.getModelo()}</p>
                            <p><strong>Ano:</strong> ${Produto.getAno()}</p>
                            <p><i>${Produto.getDescricao()}</i></p>
                            <br/>
                            <h3>${Produto.getPrecoString()}</h3>
                            <br/>
                        </div>
                    </div>
                </div>
            </div>
            <hr/>
            <div class="row">
                <div class="col-md-4 text-center">
                    <c:if test="${Produto.isAtivo()}">
                        <form action="./adicionar" method="post">
                            <label for="qtd">Quantidade:&nbsp;</label>
                            <input type="hidden" name="prod" value="${Produto.getId()}"/>
                            <input type="number" id="qtd" name="quantidade" min="1" max="1000" value="1" class="inputDetalhes"/>
                            &nbsp;&nbsp;&nbsp;
                            <button type="submit" class="btn btn-success">Adicionar à Cegonha</button>
                        </form>
                    </c:if>
                    <c:if test="${!Produto.isAtivo()}">
                        <h4><i>Produto esgotado!</i></h4> 
                    </c:if>
                </div>
                <div class="col-md-8"></div>
            </div>
            <%@include file="/util/rodape.jspf"%>
        </div> 
    </body>
</html>
