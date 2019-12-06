<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="Cliente" type="model.Cliente" scope="session"/>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#menu-principal" aria-expanded="false">
                <span class="sr-only"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href=".">Meu Carro Minha Vida</a>
        </div>
        <div class="navbar-collapse collapse" id="menu-principal" aria-expanded="false">
            <ul class="nav navbar-nav navbar-right">
                <li class="active">&nbsp;&nbsp;Olá, ${Cliente.primeiroNome()}!&nbsp;&nbsp;</li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        Minha Conta&nbsp;
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="/editarDados">Editar dados</a>
                        </li>
                        <li>
                            <a href="/alterarSenha">Alterar senha</a>
                        </li>
                        <li>    
                            <a href="/meusEnderecos">Meus endereços</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="/meusPedidos">Meus Pedidos</a>
                </li>
                <li>
                    <a href="./cegonha">
                        <c:url value="/img/cegonha1.png" var="imgCegonha"/>
                        <img src="${imgCegonha}" alt="Minha Cegonha" class="iconeCegonha"/>
                        Ver Cegonha
                    </a>
                </li>
                <li>
                    <a href="/logout">Sair</a>
                </li>
            </ul>
        </div>
    </div>
</nav>


