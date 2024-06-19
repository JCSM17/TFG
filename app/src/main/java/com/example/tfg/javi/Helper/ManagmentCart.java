package com.example.tfg.javi.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.tfg.javi.domain.PopularDomain;

import java.util.ArrayList;

public class ManagmentCart {
    private Context context; // Contexto de la aplicación
    private TinyDB tinyDB; // Instancia para almacenamiento persistente usando TinyDB

    public ManagmentCart(Context context) {
        this.context = context; // Inicializa el contexto de la aplicación
        this.tinyDB = new TinyDB(context); // Inicializa TinyDB con el contexto
    }

    // Método para insertar un elemento en el carrito
    public void insertFood(PopularDomain item) {
        ArrayList<PopularDomain> listpop = getListCart(); // Obtiene la lista actual del carrito
        boolean existAlready = false;
        int n = 0;
        // Verifica si el elemento ya existe en el carrito
        for (int i = 0; i < listpop.size(); i++) {
            if (listpop.get(i).getTitleText().equals(item.getTitleText())) {
                existAlready = true;
                n = i;
                break;
            }
        }
        // Si el elemento ya existe, actualiza la cantidad; de lo contrario, añádelo a la lista
        if (existAlready) {
            listpop.get(n).setNumberInCart(item.getNumberInCart());
        } else {
            listpop.add(item);
        }
        tinyDB.putListObject("CartList", listpop); // Guarda la lista actualizada en TinyDB
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show(); // Muestra un mensaje de confirmación al usuario
    }

    // Método para obtener la lista actual del carrito
    public ArrayList<PopularDomain> getListCart() {
        return tinyDB.getListObject("CartList"); // Retorna la lista de objetos del carrito almacenada en TinyDB
    }

    // Método para calcular el total de la compra en el carrito
    public Double getTotalFee() {
        ArrayList<PopularDomain> listItem = getListCart(); // Obtiene la lista actual del carrito
        double fee = 0;
        // Calcula la suma total de los precios por la cantidad de cada elemento en el carrito
        for (int i = 0; i < listItem.size(); i++) {
            fee = fee + (listItem.get(i).getPrice() * listItem.get(i).getNumberInCart());
        }
        return fee; // Retorna el total calculado
    }

    // Método para reducir la cantidad de un elemento en el carrito
    public void minusNumberItem(ArrayList<PopularDomain> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listItem.get(position).getNumberInCart() == 1) {
            listItem.remove(position); // Si la cantidad es 1, elimina el elemento del carrito
        } else {
            listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart() - 1); // Reduce la cantidad en 1
        }
        tinyDB.putListObject("CartList", listItem); // Guarda la lista actualizada en TinyDB
        changeNumberItemsListener.change(); // Notifica al listener que ha cambiado el número de elementos en el carrito
    }

    // Método para aumentar la cantidad de un elemento en el carrito
    public void plusNumberItem(ArrayList<PopularDomain> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart() + 1); // Aumenta la cantidad en 1
        tinyDB.putListObject("CartList", listItem); // Guarda la lista actualizada en TinyDB
        changeNumberItemsListener.change(); // Notifica al listener que ha cambiado el número de elementos en el carrito
    }
}