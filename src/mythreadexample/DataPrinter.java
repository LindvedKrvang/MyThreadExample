/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mythreadexample;

import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Rasmus
 */
class DataPrinter<T> implements Consumer<List<T>> {

    @Override
    public void accept(List<T> t) {
        for (T data : t) {
            System.out.println(data.toString());
        }
    }

}
