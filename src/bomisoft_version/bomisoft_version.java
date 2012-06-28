/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bomisoft_version;
import java.io.File;

/**
 *
 * @author Marek Opłotny
 */

public class bomisoft_version {

    public static void main(String[] args) {
        //Pobranie konfiguracji zmiennych globalnych z pliku conf.properties
        GlobalDataStore.GlobalDataUpdate();
        //Ustawienie zmiennej wymuszonej aktualizacji na "false"
        Boolean up = false;
        Boolean gui = true;
        String ABOUT = "\nAutor: \t Marek Opłotny \n"
                + "-nogui \t\t uruchomienie programu w trybie teksotwym \n"
                + "-upgrade \t pobranie aktualnej wersji programu z serwera";
        //Pozyskanie parametru pierwszego i ustawienie zmiennej wymuszanej aktualizacji na true
        //Test wersji programu głównego
        if(args.length>0)
        {
            if(args[0].matches("help")){
                System.out.println(ABOUT);
                System.exit(0);
            }
            else
            for (int i=0; i < args.length; i++){
                if(args[i].toString().matches("-upgrade"))
                    up=true;
                if(args[i].matches("-nogui"))
                    gui=false;
            }
        }
        GlobalDataStore.CheckUpdate("http://37.28.152.194/auth/version.properties", "A520", "rce", up);
        if(gui){
            Podglad okno;
            okno = new Podglad();
            okno.setVisible(true);
        }
        //Usunięcie pliku konfiguracji serwera docelowego
        File files = new File("dest.properties");
        if (files.exists()) {
            files.delete();
        }
        //Usunięcie pliku wersji
        files = new File("version.properties");
        if (files.exists()) {
            files.delete();
        }
    }
}
