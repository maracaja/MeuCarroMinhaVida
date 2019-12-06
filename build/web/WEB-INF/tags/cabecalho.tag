<%@tag description="cabeçalho das páginas MCMV" pageEncoding="ISO-8859-1"%>

<%@attribute name="deslogado" type="java.lang.String"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="Cliente" type="model.Cliente" scope="session"/>

<c:if test="${Cliente.getId() == -1}">
    <jsp:include page="${deslogado}"/>
</c:if>
<c:if test="${Cliente.getId() != -1}">
    <jsp:include page="util/cabecalhoLogado.jsp"/>
</c:if>