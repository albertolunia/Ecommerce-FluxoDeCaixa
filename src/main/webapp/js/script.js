

function calcularTotais(dados) {
    let receitaVendasTotal = 0;
    let receitaProduto = 0;
    let receitaServico = 0;
    let receitaDiversasTotal = 0;
    let receitaRendimento = 0;
    let receitaOutras = 0;
    let despesaOperacionaisTotal = 0;
    let despesaAgua = 0;
    let despesaAluguel = 0;
    let despesaCondominio = 0;
    let despesaLuz = 0;
    let despesaInternet = 0;
    let despesaSalario = 0;
    let despesaVendasTotal = 0;
    let despesaComissao = 0;
    let despesaEmbalagens = 0;
    let despesaFrete = 0;
    let despesaDiversasTotal = 0;
    let despesaTarifas = 0;
    let despesaJuros = 0;
    let despesaOutras = 0;
    let despesaImpostosTotal = 0;
    let despesaIPI = 0;
    let despesaICMS = 0;
    let despesaISSQN = 0;

    dados.forEach(item => {
        if (item.pagamentoRecebimento === 'recebimento') {
            if (item.fkIdModalidade === 1) {
                receitaVendasTotal += item.valor;
                receitaProduto += item.valor;
            } else if (item.fkIdModalidade === 2) {
                receitaVendasTotal += item.valor;
                receitaServico += item.valor;
            } else if (item.fkIdModalidade === 3) {
                receitaDiversasTotal += item.valor;
                receitaRendimento += item.valor;
            } else if (item.fkIdModalidade === 4) {
                receitaDiversasTotal += item.valor;
                receitaOutras += item.valor;
            }
        } else if (item.pagamentoRecebimento === 'pagamento') {
            if (item.fkIdModalidade === 5) {
                despesaOperacionaisTotal += item.valor;
                despesaAgua += item.valor;
            } else if (item.fkIdModalidade === 6) {
                despesaOperacionaisTotal += item.valor;
                despesaAluguel += item.valor;
            } else if (item.fkIdModalidade === 7) {
                despesaOperacionaisTotal += item.valor;
                despesaCondominio += item.valor;
            } else if (item.fkIdModalidade === 8) {
                despesaOperacionaisTotal += item.valor;
                despesaLuz += item.valor;
            } else if (item.fkIdModalidade === 9) {
                despesaOperacionaisTotal += item.valor;
                despesaInternet += item.valor;
            } else if (item.fkIdModalidade === 10) {
                despesaOperacionaisTotal += item.valor;
                despesaSalario += item.valor;
            } else if (item.fkIdModalidade === 11) {
                despesaVendasTotal += item.valor;
                despesaComissao += item.valor;
            } else if (item.fkIdModalidade === 12) {
                despesaVendasTotal += item.valor;
                despesaEmbalagens += item.valor;
            } else if (item.fkIdModalidade === 13) {
                despesaVendasTotal += item.valor;
                despesaFrete += item.valor;
            } else if (item.fkIdModalidade === 14) {
                despesaDiversasTotal += item.valor;
                despesaTarifas += item.valor;
            } else if (item.fkIdModalidade === 15) {
                despesaDiversasTotal += item.valor;
                despesaJuros += item.valor;
            } else if (item.fkIdModalidade === 16) {
                despesaDiversasTotal += item.valor;
                despesaOutras += item.valor;
            } else if (item.fkIdModalidade === 17) {
                despesaImpostosTotal += item.valor;
                despesaIPI += item.valor;
            } else if (item.fkIdModalidade === 18) {
                despesaImpostosTotal += item.valor;
                despesaICMS += item.valor;
            } else if (item.fkIdModalidade === 19) {
                despesaImpostosTotal += item.valor;
                despesaISSQN += item.valor;
            }
        }
    });

    const receitaTotal = receitaVendasTotal + receitaDiversasTotal;
    const despesaTotal = despesaOperacionaisTotal + despesaVendasTotal + despesaDiversasTotal + despesaImpostosTotal;
    const lucro = receitaTotal - despesaTotal;

    return {
        receitaVendasTotal,
        receitaProduto,
        receitaServico,
        receitaDiversasTotal,
        receitaRendimento,
        receitaOutras,
        despesaOperacionaisTotal,
        despesaAgua,
        despesaAluguel,
        despesaCondominio,
        despesaLuz,
        despesaInternet,
        despesaSalario,
        despesaVendasTotal,
        despesaComissao,
        despesaEmbalagens,
        despesaFrete,
        despesaDiversasTotal,
        despesaTarifas,
        despesaJuros,
        despesaOutras,
        despesaImpostosTotal,
        despesaIPI,
        despesaICMS,
        despesaISSQN,
        receitaTotal,
        despesaTotal,
        lucro
    };
}



function baixarPDF() {
    const fluxoCaixaDiv = document.getElementById("fluxoCaixa");
    const downloadBtn = document.getElementById("downloadBtn");

    if (fluxoCaixaDiv.innerHTML.trim() !== "" && downloadBtn.style.display !== "none") {
        const printContent = fluxoCaixaDiv.innerHTML;
        const printWindow = window.open('', '_blank');
        printWindow.document.write(`<html><body>${printContent}</body></html>`);
        printWindow.document.close();
        printWindow.print();
    } else {
        alert("Nenhum fluxo de caixa disponível para download.");
    }
}

async function gerarFluxoCaixa() {
    const mes = document.getElementById("mes").value;
    const ano = document.getElementById("ano").value;
    const chave = `${ano}-${mes}`;
    console.log(`Mes: ${mes}, Ano: ${ano}`);

    const fluxoCaixaDiv = document.getElementById("fluxoCaixa");
    fluxoCaixaDiv.innerHTML = "";

    const ehFuturo = new Date(ano, mes - 1) > new Date();

    try {
        const response = await fetch(`/Ecommerce_FluxoDeCaixa_Alberto_Everaldina_war_exploded/recebimento-pagamento/mes?mes=${mes}&ano=${ano}`);
        console.log(`Status da resposta: ${response.status}`);
        if (!response.ok) {
            throw new Error("Erro ao buscar dados do fluxo de caixa");
        }
        const dados = await response.json();
        console.log("Dados recebidos:", dados);
        if (dados.length === 0 && !ehFuturo) {
            fluxoCaixaDiv.innerHTML = "<p>Não foi possível encontrar o fluxo de caixa para este mês.</p>";
            document.getElementById("downloadBtn").style.display = "none";
            return;
        }

        // Process the array of transactions to calculate totals
        const totais = calcularTotais(dados);

        fluxoCaixaDiv.innerHTML = `
            <h2>Fluxo de Caixa - ${mes}/${ano}</h2>
            <h3>Receita</h3>
            <div class="sub-item-grid">
                <div>
                    <p>Receita de Vendas: R$ ${totais.receitaVendasTotal}</p>
                    <p class="sub-item">Produto: R$ ${totais.receitaProduto}</p>
                    <p class="sub-item">Serviço: R$ ${totais.receitaServico}</p>
                </div>
                <div>
                    <p>Receita Diversas: R$ ${totais.receitaDiversasTotal}</p>
                    <p class="sub-item">Rendimento Financeiro: R$ ${totais.receitaRendimento}</p>
                    <p class="sub-item">Outras Receitas: R$ ${totais.receitaOutras}</p>
                </div>
            </div>

            <h3>Despesas</h3>
            <div class="sub-item-grid">
                <div>
                    <p>Despesas Operacionais: R$ ${totais.despesaOperacionaisTotal}</p>
                    <p class="sub-item">Água: R$ ${totais.despesaAgua}</p>
                    <p class="sub-item">Aluguel: R$ ${totais.despesaAluguel}</p>
                    <p class="sub-item">Condomínio: R$ ${totais.despesaCondominio}</p>
                    <p class="sub-item">Luz: R$ ${totais.despesaLuz}</p>
                    <p class="sub-item">Internet: R$ ${totais.despesaInternet}</p>
                    <p class="sub-item">Salário: R$ ${totais.despesaSalario}</p>
                </div>

                <div>
                    <p>Despesas de Vendas: R$ ${totais.despesaVendasTotal}</p>
                    <p class="sub-item">Comissão: R$ ${totais.despesaComissao}</p>
                    <p class="sub-item">Embalagens: R$ ${totais.despesaEmbalagens}</p>
                    <p class="sub-item">Frete: R$ ${totais.despesaFrete}</p>
                </div>

                <div>
                    <p>Despesas Diversas: R$ ${totais.despesaDiversasTotal}</p>
                    <p class="sub-item">Tarifas Bancárias: R$ ${totais.despesaTarifas}</p>
                    <p class="sub-item">Juros e Multas: R$ ${totais.despesaJuros}</p>
                    <p class="sub-item">Outras Despesas: R$ ${totais.despesaOutras}</p>
                </div>

                <div>
                    <p>Impostos: R$ ${totais.despesaImpostosTotal}</p>
                    <p class="sub-item">IPI: R$ ${totais.despesaIPI}</p>
                    <p class="sub-item">ICMS: R$ ${totais.despesaICMS}</p>
                    <p class="sub-item">ISSQN: R$ ${totais.despesaISSQN}</p>
                </div>
            </div>

            <h3>Totais</h3>
            <p>Receita Total: R$ ${totais.receitaTotal}</p>
            <p>Despesa Total: R$ ${totais.despesaTotal}</p>
            <p>Lucro: R$ ${totais.lucro}</p>
        `;

        document.getElementById("downloadBtn").style.display = "inline-block";
    } catch (error) {
        console.error("Erro ao gerar fluxo de caixa:", error);
        fluxoCaixaDiv.innerHTML = "<p>Erro ao gerar fluxo de caixa.</p>";
        document.getElementById("downloadBtn").style.display = "none";
    }
}