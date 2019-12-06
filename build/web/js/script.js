var h = false;

function verificaCPF(cpf)
{
    document.getElementById("warning-cpf").innerHTML = "";
    if (cpf.length !== 11)
        resetaCampo("cpf");
    else
    {
        var digitos = new Array(11);
        var i, soma = 0;
        for (i = 0; i < digitos.length; i++)
            digitos[i] = Number(cpf.charAt(i));
        for (i = 1; i <= 9; i++)
            soma += i * digitos[i - 1];
        if ((soma % 11) % 10 !== digitos[9])
            resetaCampo("cpf");
        else
        {
            soma = 0;
            for (i = 0; i <= 9; i++)
                soma += i * digitos[i];
            if ((soma % 11) % 10 !== digitos[10])
                resetaCampo("cpf");
        }
    }
}

function resetaCampo(id)
{
    document.getElementById(id).value = "";
    if (id === "cpf")
        document.getElementById("warning-cpf").innerHTML = "Digite um n&uacute;mero de CPF v&aacute;lido";
}

function endereco(cep)
{
    resetaCampo("logradouro");
    resetaCampo("bairro");
    resetaCampo("cidade");
    resetaCampo("uf");
    var xmlhttp = new XMLHttpRequest();
    var url = "http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=json";
    xmlhttp.onreadystatechange = function()
    {
        if (xmlhttp.status === 200)
        {
            if (xmlhttp.readyState === 4)
            {
                var endereco = JSON.parse(xmlhttp.responseText);
                if (endereco.resultado === "1")
                {
                    document.getElementById("logradouro").value = endereco.tipo_logradouro + " " + endereco.logradouro;
                    document.getElementById("bairro").value = endereco.bairro;
                    document.getElementById("cidade").value = endereco.cidade;
                    document.getElementById("uf").value = endereco.uf;
                    document.getElementById("logradouro").readOnly = true;
                    document.getElementById("bairro").readOnly = true;
                }
                else if (endereco.resultado === "2")
                {
                    document.getElementById("cidade").value = endereco.cidade;
                    document.getElementById("uf").value = endereco.uf;
                    document.getElementById("logradouro").readOnly = false;
                    document.getElementById("bairro").readOnly = false;
                }
                else alert("Endere\u00e7o n\u00e3o encontrado pelo CEP informado!");
            }
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}

function verificaSenha()
{
    if (document.getElementById("senha").value !== document.getElementById("senha-conf").value)
    {
        document.getElementById("warning-senha").innerHTML = "Senhas n&atilde;o coincidem";
        document.getElementById("enviar").disabled = true;
    }
    else
    {
        document.getElementById("warning-senha").innerHTML = "";
        document.getElementById("enviar").disabled = false;
    }
}

function testaSenha(senha)
{
    if(senha.length < 6) return 1;
    else
    {
        var testes = new Array(5);
        testes.fill(false);
        testes[0] = senha.length >= 8;
        testes[1] = /[A-Z]/.test(senha);
        testes[2] = /[a-z]/.test(senha);
        testes[3] = /[0-9]/.test(senha);
        testes[4] = /[^\w]/.test(senha);
        var cont = 0;
        for (var i = 0; i < testes.length; i++)
            if(testes[i]) cont++;
        return cont;
    }
}

function forcaSenha(senha)
{
    if (senha.length !== 0)
    {
        var notas = ["Muito fraca", "Fraca", "Razo&aacute;vel", "Forte", "Muito forte"];
        var n = testaSenha(senha);
        document.getElementById("forca").hidden = false;
        document.getElementById("classif").innerHTML = notas[n - 1];
        document.getElementById("classif").setAttribute("class", "estilo-" + n);
        for (var i = 1; i <= n; i++)
            document.getElementById("nota-" + i).setAttribute("class", "glyphicon glyphicon-minus estilo-" + n);
        for (var i = n + 1; i <= notas.length; i++)
            document.getElementById("nota-" + i).setAttribute("class", "glyphicon glyphicon-minus estilo-0");
    }
    else document.getElementById("forca").hidden = true;
}

function hoje()
{
    var data = new Date();
    return data.getFullYear() + "-" + doisDigitos(Number(data.getMonth() + 1)) + "-" + doisDigitos(data.getDate());
}

function doisDigitos(num)
{
    return (Number(num) < 10 ? "0" : "") + num;
}

function max(id)
{
    document.getElementById(id).setAttribute("max", hoje());
}

function apenasAtivos()
{
    var inativos = document.getElementsByClassName("inativo");
    document.getElementById("filtro").innerHTML = h ? "Mostrar apenas ativos" : "Mostrar tudo";
    for (var i = 0; i < inativos.length; i++)
        inativos[i].hidden = !h;
    h = !h; 
}