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
        //Pozyskanie parametru pierwszego i ustawienie zmiennej wymuszanej aktualizacji na true
        try {
            if (args.length > 0) {
                //System.out.println("Args " + args[0]);
                if (args[0].matches("upgrade")) {
                    up = true;
                    if (GlobalDataStore.DEBUG == true)
                        System.out.println("Ustawiam wymuszona aktualizacje!!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Test wersji programu głównego
        GlobalDataStore.CheckUpdate("http://37.28.152.194/auth/version.properties", "A520", "rce", up);
        System.out.println("\n<--Zmienna local_srv-->");
        //Klasa danych serwera lokalnego
        DB local_srv = new DB();
        //Wczytanie danych serwera lokalnego
        local_srv.PROP("/db.properties");
        //Połaczenie z serwerem lokalnym
        local_srv.CONNECT();
        System.out.println("\n<--Pobieranie danych z serwera lokalnego-->");
        //Pobranie danych o bazie BLOZ
        local_srv.GET_BLOZ();
        //Pobranie danych o bazie symulacyjnej BLOZ
        local_srv.GET_BLOZ_SM();
        //Pobranie ID apteki z bazy
        local_srv.GET_ID();
        //Pobranie danych o ostatniej aktualizacji programów
        local_srv.GET_AKT();
        //Pobranie danych o ostatniej aktualizacji recept skradzionych
        local_srv.GET_REC();
        //Pozyskanie z plików danych o backupach
        local_srv.GET_RAPORT();
        //Rozłączenie z baza lokalną
        local_srv.DISCONNECT();
        System.out.println("\n<--Zmienna dest_srv-->");
        //Klasa danych serwera docelowego
        DB dest_srv = new DB();
        //Pobieranie konfiguracji serwera docelowego
        GlobalDataStore.downloadPropWithAuth("http://37.28.152.194/auth/dest.properties", "A520", "rce", dest_srv);
        //Połączenie z baza serwera docelowego
        dest_srv.CONNECT();
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
        //Test poprawności połączenia z baza docelową
        if (local_srv.conn != null) {
            if (local_srv.ID != 0) {
                //Wysłanie raportu do serwera docelowego
                dest_srv.SEND_RAPORT(local_srv);
            }
            else {
                System.out.println("Brak danych do wysłania!!");
            }
        }
        //Rozłaczenie się z serwerem docelowym
        dest_srv.DISCONNECT();
    }
}
