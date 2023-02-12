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
                contato = "(11)4002-8922"
            )
        )
    }
}