<%@tag description="Menu de navegação da Área de Administração" pageEncoding="ISO-8859-1"%>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#menu-principal" aria-expanded="false">
                <span class="sr-only"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/admin">Meu Carro Minha Vida</a>
        </div>
        <div class="navbar-collapse collapse" id="menu-principal" aria-expanded="false">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="/relatorioPedidos">Pedidos realizados</a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        Produtos&nbsp;
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="/relatorioProdutos">Relação de produtos</a>
                        </li>
                        <li>
                            <a href="/novoProduto">Adicionar produto</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="/logout">Sair</a>
                </li>
            </ul>
        </div>
    </div>
</nav>