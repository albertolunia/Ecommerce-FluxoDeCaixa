const dadosFluxoCaixa = {
    "2024-01": {
        receita: {
            vendas: { 
                total: 5000, 
                produto: 3000, 
                servico: 2000 
            },
            diversas: { 
                total: 1000, 
                rendimento: 600, 
                outras: 400 
            }
        },
        despesas: {
            operacionais: { 
                total: 2000, 
                agua: 200, 
                aluguel: 800, 
                condominio: 150, 
                luz: 300, 
                internet: 150, 
                salario: 400
            },
            vendas: { 
                total: 500, 
                comissao: 300, 
                embalagens: 100, 
                frete: 100 
            },
            diversas: { 
                total: 200, 
                tarifas: 100, 
                juros: 50, 
                outras: 50 
            },
            impostos: { 
                total: 300, 
                IPI: 100, 
                ICMS: 150, 
                ISSQN: 50 
            }
        }
    }
};

function calcularTotais(dados) {
    const receitaTotal = dados.receita.vendas.total + dados.receita.diversas.total;
    const despesaTotal = dados.despesas.operacionais.total + dados.despesas.vendas.total + dados.despesas.diversas.total + dados.despesas.impostos.total;
    const lucro = receitaTotal - despesaTotal;

    return { receitaTotal, despesaTotal, lucro };
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

function gerarFluxoCaixa() {
    const mes = document.getElementById("mes").value;
    const ano = document.getElementById("ano").value;
    const chave = `${ano}-${mes}`;
    
    const fluxoCaixaDiv = document.getElementById("fluxoCaixa");
    fluxoCaixaDiv.innerHTML = "";

    const ehFuturo = new Date(ano, mes - 1) > new Date();

    if (dadosFluxoCaixa[chave] || ehFuturo) {
        const dados = dadosFluxoCaixa[chave];
        const totais = calcularTotais(dados);

        fluxoCaixaDiv.innerHTML = `
            <h2>Fluxo de Caixa - ${mes}/${ano}</h2>
            <h3>Receita</h3>
            <div class="sub-item-grid">
                <div>
                    <p>Receita de Vendas: R$ ${dados.receita.vendas.total}</p>
                    <p class="sub-item">Produto: R$ ${dados.receita.vendas.produto}</p>
                    <p class="sub-item">Serviço: R$ ${dados.receita.vendas.servico}</p>
                </div>
                <div>
                    <p>Receita Diversas: R$ ${dados.receita.diversas.total}</p>
                    <p class="sub-item">Rendimento Financeiro: R$ ${dados.receita.diversas.rendimento}</p>
                    <p class="sub-item">Outras Receitas: R$ ${dados.receita.diversas.outras}</p>
                </div>
            </div>

            <h3>Despesas</h3>
            <div class="sub-item-grid">
                <div>
                    <p>Despesas Operacionais: R$ ${dados.despesas.operacionais.total}</p>
                    <p class="sub-item">Água: R$ ${dados.despesas.operacionais.agua}</p>
                    <p class="sub-item">Aluguel: R$ ${dados.despesas.operacionais.aluguel}</p>
                    <p class="sub-item">Condomínio: R$ ${dados.despesas.operacionais.condominio}</p>
                    <p class="sub-item">Luz: R$ ${dados.despesas.operacionais.luz}</p>
                    <p class="sub-item">Internet: R$ ${dados.despesas.operacionais.internet}</p>
                    <p class="sub-item">Salário: R$ ${dados.despesas.operacionais.salario}</p>
                </div>

                <div>
                    <p>Despesas de Vendas: R$ ${dados.despesas.vendas.total}</p>
                    <p class="sub-item">Comissão: R$ ${dados.despesas.vendas.comissao}</p>
                    <p class="sub-item">Embalagens: R$ ${dados.despesas.vendas.embalagens}</p>
                    <p class="sub-item">Frete: R$ ${dados.despesas.vendas.frete}</p>
                </div>

                <div>
                    <p>Despesas Diversas: R$ ${dados.despesas.diversas.total}</p>
                    <p class="sub-item">Tarifas Bancárias: R$ ${dados.despesas.diversas.tarifas}</p>
                    <p class="sub-item">Juros e Multas: R$ ${dados.despesas.diversas.juros}</p>
                    <p class="sub-item">Outras Despesas: R$ ${dados.despesas.diversas.outras}</p>
                </div>

                <div>
                    <p>Impostos: R$ ${dados.despesas.impostos.total}</p>
                    <p class="sub-item">IPI: R$ ${dados.despesas.impostos.IPI}</p>
                    <p class="sub-item">ICMS: R$ ${dados.despesas.impostos.ICMS}</p>
                    <p class="sub-item">ISSQN: R$ ${dados.despesas.impostos.ISSQN}</p>
                </div>
            </div>

            <h3>Totais</h3>
            <p>Receita Total: R$ ${totais.receitaTotal}</p>
            <p>Despesa Total: R$ ${totais.despesaTotal}</p>
            <p>Lucro: R$ ${totais.lucro}</p>
        `;

        document.getElementById("downloadBtn").style.display = "inline-block";
    } else {
        console.log("Não foi possível encontrar o fluxo de caixa para este mês.");
        fluxoCaixaDiv.innerHTML = "<p>Não foi possível encontrar o fluxo de caixa para este mês.</p>";
        document.getElementById("downloadBtn").style.display = "none";
    }
}