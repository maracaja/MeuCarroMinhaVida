<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="Aviso" type="java.lang.String" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <script>
            $(document).ready(function() 
            {
                max("dataNasc");
                <c:if test="${Aviso.length() > 0}">
                    alert("${Aviso}"); 
                </c:if>   
            });
        </script>
        <link rel="icon" type="image/jpg" href="img/cegonha2.jpg"/>
        <title>Cadastro</title>
    </head>
    <body>
        <div class="container-fluid">
            <jsp:include page="util/cabecalhoSemCegonha.html"/>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6">
                    <form action="/cadastraUsuario" method="POST">
                        <div class="h2 text-center">Dados pessoais</div>
                        <div class="row form-group">
                            <div class="col-md-12">
                                <label for="nome">Nome completo:</label>
                                <input type="text" name="nome" id="nome" class="form-control" required/> 
                            </div>
                        </div>
                        <div class="row form-group">
                            <div class="col-md-7">
                                <label for="cpf">
                                    CPF (somente números):
                                    <span id="warning-cpf" class="warning"></span>
                                </label>
                                <input type="text" name="cpf" id="cpf" onchange="verificaCPF(this.value)" class="form-control" required/>
                            </div>
                            <div class="col-md-5">
                                <label for="dataNasc">Data de nascimento:</label>
                                <input type="date" name="dataNasc" id="dataNasc" class="form-control" required/>
                            </div>
                        </div>
                        <div class="row form-group">
                            <div class="col-md-12">
                                <label for="email">E-mail:</label>
                                <input type="text" name="email" id="email" class="form-control" required/> <!-- Ver expressão regular para e-mail -->
                            </div>
                        </div>
                        <div class="row form-group">
                            <div class="col-md-6">
                                <label for="senha">Senha:</label>
                                <input type="password" name="senha" id="senha" class="form-control" oninput="forcaSenha(this.value); verificaSenha();" required/>
                                <span id="forca" hidden>
                                    Força da senha:&nbsp;<strong id="classif"></strong>&nbsp;
                                    <c:forEach var="n" begin="1" end="5" step="1">
                                        <span class="glyphicon glyphicon-minus" id="nota-${n}"></span>
                                    </c:forEach>
                                </span>
                            </div>
                            <div class="col-md-6">
                                <label for="senha-conf">Digite a senha novamente:</label>
                                <input type="password" id="senha-conf" oninput="verificaSenha()" class="form-control" required/>
                                <span id="warning-senha" class="warning"></span>
                            </div>
                        </div>
                        <br/>
                        <div class="h2 text-center">Endereço</div>
                        <div class="row form-group">
                            <div class="col-md-12">
                                <label for="marcador">Insira um identificador para seu endereço (ex.: "End. Comercial", "Casa da Sogra", "Apê da praia") - opcional:</label>
                                <input type="text" name="marcador" id="marcador" class="form-control"/>
                            </div>
                        </div>                        
                        <div class="row form-group">
                            <div class="col-md-3">
                                <label for="cep">Digite seu CEP:</label>
                                <input type="text" name="cep" id="cep" onchange="endereco(this.value)" class="form-control" required/>
                            </div>
                            <div class="col-md-9">
                                <label for="logradouro">Logradouro (Rua, Avenida, etc.):</label>
                                <input type="text" name="logradouro" id="logradouro" class="form-control" readonly required/>
                            </div>
                        </div>
                        <div class="row form-group">
                            <div class="col-md-2">
                                <label for="numero">Número:</label>
                                <input type="text" name="numero" id="numero" class="form-control" required/>
                            </div>
                            <div class="col-md-4">
                                <label for="complemento">Complemento:</label>
                                <input type="text" name="complemento" id="complemento" class="form-control"/>
                            </div>
                            <div class="col-md-6">
                                <label for="bairro">Bairro:</label>
                                <input type="text" name="bairro" id="bairro" class="form-control" readonly/>
                            </div>
                        </div>
                        <div class="row form-group">
                            <div class="col-md-10">
                                <label for="cidade">Município:</label>
                                <input type="text" name="cidade" id="cidade" class="form-control" readonly required/>
                            </div>
                            <div class="col-md-2">
                                <label for="uf">Estado:</label>
                                <input type="text" name="uf" id="uf" class="form-control" readonly required/> 
                            </div>
                        </div>
                        <br/>
                        <div class="row form-group">
                            <div class="col-md-12">
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