<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="mcmv" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <mcmv:head/>
        <title>Administração MCMV</title>
    </head>
    <body>
        <div class="container-fluid">
            <mcmv:admin/>
            <h1 class="text-center">Área de Administração</h1>
            <%@include file="/util/rodape.jspf"%> 
        </div>
    </body>
</html>
