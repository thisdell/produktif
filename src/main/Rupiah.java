/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
/**
 *
 * @author LENOVO-PC
 */
public class Rupiah {
       public static String convertToRupiah(int totalRupiah){
            Locale myIndonesianLocale = new Locale("in", "ID");
            NumberFormat formater = NumberFormat.getCurrencyInstance(myIndonesianLocale);
            return formater.format(totalRupiah).replace(",00" ,"");
    }
}
