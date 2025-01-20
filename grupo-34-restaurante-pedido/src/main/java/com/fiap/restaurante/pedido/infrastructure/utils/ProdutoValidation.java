package com.fiap.restaurante.pedido.infrastructure.utils;


import com.fiap.restaurante.pedido.core.domain.Produto;
import com.fiap.restaurante.pedido.core.domain.enums.Category;
import com.fiap.restaurante.pedido.infrastructure.exception.ProdutoException;

public class ProdutoValidation {
    
    public static boolean validateFields(Produto produto) {
        String nome = produto.getNome();
        Double preco = produto.getPreco();
        String categoria = produto.getCategoria();

            if (nome == null || nome == "") {
                throw new ProdutoException("O campo nome é obrigatório!");
            }
            if (preco <= 0) {
                throw new ProdutoException("Preço inválido, o valor deve ser maior que zero.");
            }

            validate(categoria);

        return true;
    }

    public static void validate(String categoria) {
        if (!isValidCategory(categoria)) {
            throw new ProdutoException("Categoria inválida: " + categoria);
        }
    }

    public static boolean isValidCategory(String category) {
        for (Category cat : Category.values()) {
            if (cat.name().equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }
}
