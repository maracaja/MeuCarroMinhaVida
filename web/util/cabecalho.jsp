<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                <li>
                    <a href="./cegonha">
                        <c:url value="/img/cegonha1.png" var="imgCegonha"/>
                        <img src="${imgCegonha}" alt="Minha Cegonha" class="iconeCegonha"/>
                        Ver Cegonha
                    </a>
                </li>
                <li>
                    <a href="./login">
                        <span class="glyphicon glyphicon-user"></span>
                        Login
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
