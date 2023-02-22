package br.com.alura.orgs.dao

import br.com.alura.orgs.model.Itens

class ItensDao {

    fun add(item: Itens){
        itens.add(item)
    }

    fun buscaTodos() : List<Itens>{
        return itens.toList()
    }

    companion object {
        private val itens = mutableListOf<Itens>(
            Itens(
                itemPerdido = "Baby Yoda",
                situacao = "Encontrado",
                local = "ICEB",
                contato = "(11)4002-8922",
                descricao = "Produto novo, de pelúcia, encontrado no ICEB 2, sala 19 aproximadamente às 16:30"
            ),
            Itens(
                itemPerdido = "Garrafinha",
                situacao = "Encontrado",
                local = "Bloco de Salas",
                contato = "thiagocecote@gmail.com",
                descricao = "Garrafinha vermelha, de 1Litro, encontrada no Bloco de salas por volta da 9hrs da manhã balsdjadhgiadbsuybsducygsdchisjdcosidbsydfisuhdfjspdofmsdnfihu"
            ),
            Itens(
                itemPerdido = "Carteira",
                situacao = "Encontrado",
                local = "EFAR",
                contato = "thiagocecote@gmail.com",
                descricao = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
            )
        )
    }
}