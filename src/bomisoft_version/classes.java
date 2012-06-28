/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bomisoft_version;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author user
 */
class GlobalDataStore {

    public static int VERSION = 2012062801;
    public static int BUILD = 0;
    public static boolean DEBUG;
    public static int timeout;
    public static String jarDir;

    public static void copyInputStream(InputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[10240];
        int len;
        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
    }

    public static void GlobalDataUpdate() {
        File jarFile;
        try {
            CodeSource codeSource = bomisoft_version.class.getProtectionDomain().getCodeSource();
            jarFile = new File(codeSource.getLocation().toURI().getPath());
            GlobalDataStore.jarDir = jarFile.getParentFile().getPath();
        } catch (URISyntaxException ex) {
            if (GlobalDataStore.DEBUG == true) {
                Logger.getLogger(bomisoft_version.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            if (GlobalDataStore.DEBUG == true) {
                ex.printStackTrace();
            }
        }
        try {
            File f = new File(bomisoft_version.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            System.out.println("Exec path: " + f.getParent());
            jarDir = f.getParent();
        } catch (URISyntaxException ex) {
            Logger.getLogger(GlobalDataStore.class.getName()).log(Level.SEVERE, null, ex);
            jarDir = ".";
        }
        Properties prop = new Properties();
        try {
            //jarDir=bomisoft_version.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            try {
                prop.load(new FileInputStream(GlobalDataStore.jarDir + "/config.properties"));
            } catch (Exception ex) {
                //ex.printStackTrace();
                prop.load(new FileInputStream("config.properties"));
            }
            if ("true".equals(prop.getProperty("debug"))) {
                DEBUG = true;
                System.out.println("Debug ustawione na: true");
            } else {
                DEBUG = false;
                System.out.println("Debug ustawione na: false");
            }
            if (Integer.parseInt(prop.getProperty("timeout")) > 0) {
                timeout = Integer.parseInt(prop.getProperty("timeout"));
                System.out.println("Timeout ustawione na: " + prop.getProperty("timeout") + " sekundy");
            } else {
                timeout = 2;
                System.out.println("Timeout ustawione na: 2 sekundy");
            }
        } catch (Exception ex) {
            //if(GlobalDataStore.DEBUG==true)
            //ex.printStackTrace();
            System.out.println("Ustawienia domyślne");
            DEBUG = false;
            System.out.println("Debug ustawione na: false");
            timeout = 2;
            System.out.println("Timeout ustawione na: 2 sekundy");
            //jarDir = bomisoft_version.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        }
    }

    public static void downloadFileWithAuth(String urlStr, String user, String pass, String outFilePath) {
        try {
            // URL url = new URL ("http://ip:port/download_url");
            URL url = new URL(urlStr);
            String authStr = user + ":" + pass;
            String authEncoded = Base64.encode(authStr.getBytes());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + authEncoded);

            File file = new File(jarDir + outFilePath);
            InputStream in = (InputStream) connection.getInputStream();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            for (int b; (b = in.read()) != -1;) {
                out.write(b);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downloadPropWithAuth(String urlStr, String user, String pass, DB outProp) {
        try {
            // URL url = new URL ("http://ip:port/download_url");
            URL url = new URL(urlStr);
            String authStr = user + ":" + pass;
            String authEncoded = Base64.encode(authStr.getBytes());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + authEncoded);

            File file = new File("dest.properties");
            InputStream in = (InputStream) connection.getInputStream();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            for (int b; (b = in.read()) != -1;) {
                out.write(b);
            }
            out.close();
            in.close();
            Properties prop = new Properties();
            prop.load(new FileInputStream("dest.properties"));
            outProp.PROP(prop);
            file.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void CheckUpdate(String urlStr, String user, String pass, Boolean up) {
        try {
            // URL url = new URL ("http://ip:port/download_url");
            URL url = new URL(urlStr);
            String authStr = user + ":" + pass;
            String authEncoded = Base64.encode(authStr.getBytes());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + authEncoded);

            File file = new File("version.properties");
            InputStream in = (InputStream) connection.getInputStream();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            for (int b; (b = in.read()) != -1;) {
                out.write(b);
            }
            out.close();
            in.close();
            Properties prop = new Properties();
            prop.load(new FileInputStream("version.properties"));
            String osName = System.getProperty("os.name");
            System.out.println("Operating system name => " + osName);
            System.out.println("Aktualna wersja programu: " + GlobalDataStore.VERSION);
            if (GlobalDataStore.VERSION < Integer.parseInt(prop.getProperty("version")) || up) {
                System.out.println("\nROZPOCZYNAM AKTUALIZACJE\n");
                System.out.println("Znaleziono nowszą wersje programu: " + prop.getProperty("version"));
                downloadFileWithAuth("http://37.28.152.194/auth/bomisoft.zip", "A520", "rce", "/bomisoft.zip");
                Enumeration entries;
                ZipFile zipFile;
                try {
                    zipFile = new ZipFile(GlobalDataStore.jarDir + "/bomisoft.zip");
                    entries = zipFile.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = (ZipEntry) entries.nextElement();
                        if (entry.isDirectory()) {
                            // Assume directories are stored parents first then children.
                            System.out.println("Rozpakowane katalogu: " + GlobalDataStore.jarDir + "/" + entry.getName());
                            // This is not robust, just for demonstration purposes.
                            (new File(GlobalDataStore.jarDir + "/" + entry.getName())).mkdir();
                            continue;
                        }
                        System.out.println("Rozpakowywanie pliku: " + entry.getName());
                        copyInputStream(zipFile.getInputStream(entry),
                                new BufferedOutputStream(new FileOutputStream(GlobalDataStore.jarDir + "/" + entry.getName())));
                    }
                    zipFile.close();
                } catch (IOException ioe) {
                    System.err.println("Unhandled exception:");
                    ioe.printStackTrace();
                }
                String command;
                command = GlobalDataStore.jarDir;
                if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                        command += "\\bomi.bat";
                } else {
                    command += "/bomi.sh";
                }
                System.out.println("\nAKTUALIZACJA ZAKONCZONA\n");
                Runtime load = Runtime.getRuntime();
                if(new File(command).exists())
                {
                    if(System.getProperty("os.name").toLowerCase().contains("linux"))
                    {
                        load.exec("chmod +x "+command);
                        load.exec(command);
                    }
                    else
                        load.exec(command);
                }
                if(GlobalDataStore.DEBUG==true)
                    System.out.println(command);
                System.exit(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
class DB {

    public String TYP;
    private String HOST;
    private String PORT;
    private String SCHEM;
    private String USER;
    protected String PASS;
    private String DIR;
    public String BLOZ;
    public String BLSM;
    public String AKT;
    public String RAP = "";
    public String REC_MOD = "";
    public String REC_BAZ = "";
    public Connection conn;
    public int ID;

    public void PROP(Properties prop) {
        //Properties prop = new Properties();
        try {
            this.TYP = prop.getProperty("typ");
            this.HOST = prop.getProperty("host");
            this.PORT = prop.getProperty("port");
            this.SCHEM = prop.getProperty("schem");
            this.USER = prop.getProperty("user");
            this.PASS = prop.getProperty("pass");
            this.DIR = prop.getProperty("dir");
            int last = this.DIR.length() - 1;
            if (this.DIR.substring(last) != "/") {
                this.DIR += "/";
            }
            System.out.println("Załadowanie ustawień z sieci!!");
        } catch (Exception ex) {
        }
    }

    public void PROP(String configure) {
        Properties prop = new Properties();
        try {
            try {
                prop.load(new FileInputStream(GlobalDataStore.jarDir + configure));
            } catch (Exception ex) {
                prop.load(new FileInputStream("db.properties"));
            }
            this.TYP = prop.getProperty("typ");
            this.HOST = prop.getProperty("host");
            this.PORT = prop.getProperty("port");
            this.SCHEM = prop.getProperty("schem");
            this.USER = prop.getProperty("user");
            this.PASS = prop.getProperty("pass");
            this.DIR = prop.getProperty("dir");
            int last = this.DIR.length() - 1;
            if (!this.DIR.substring(last).matches("/")) {
                if (!StringEscapeUtils.unescapeJava(this.DIR.substring(last)).matches(StringEscapeUtils.unescapeJava("\\"))) {
                    this.DIR += "/";
                }
            }
        } catch (Exception ex) {
            if (GlobalDataStore.DEBUG == true) {
                ex.printStackTrace();
            }
            this.SETTING();
        }
    }

    public void SETTING() {
        this.TYP = "Oracle";
        this.HOST = "127.0.0.1";
        this.PORT = "1521";
        this.SCHEM = "XE";
        this.USER = "apw_user";
        this.PASS = "apw_user";
        this.DIR = "C:/KS/APW/BACKUP/";
    }

    public void SETTING(String TYP, String HOST, String PORT, String SCHEM, String USER, String PASS, String DIR) {
        this.TYP = TYP;
        this.HOST = HOST;
        this.PORT = PORT;
        this.SCHEM = SCHEM;
        this.USER = USER;
        this.PASS = PASS;
        this.DIR = DIR;
    }

    public void CONNECT() {
        switch (this.TYP.toLowerCase()) {
            case "oracle": {
                try {
                    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
                    DriverManager.setLoginTimeout(GlobalDataStore.timeout);
                    String db_connect_string = "jdbc:oracle:thin:@" + this.HOST + ":" + this.PORT + ":" + this.SCHEM;
                    this.conn = DriverManager.getConnection(db_connect_string, this.USER, this.PASS);
                    System.out.println("Connected to: " + this.TYP);

                } catch (SQLException ex) {
                    if (GlobalDataStore.DEBUG == true) {
                        Logger.getLogger(DB.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex.getErrorCode());
                    }
                    this.conn = null;
                } catch (Exception ex) {
                    if (GlobalDataStore.DEBUG == true) {
                        ex.printStackTrace();
                    }
                    this.conn = null;
                }
                break;
            }
            case "mysql": {
                try {
                    DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                    DriverManager.setLoginTimeout(GlobalDataStore.timeout);
                    String db_connect_string = "jdbc:mysql://" + this.HOST + ":" + this.PORT + "/" + this.SCHEM;
                    this.conn = DriverManager.getConnection(db_connect_string, this.USER, this.PASS);
                    System.out.println("Connected to: " + this.TYP);

                } catch (SQLException ex) {
                    if (GlobalDataStore.DEBUG == true) {
                        Logger.getLogger(DB.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex.getErrorCode());
                    }
                    this.conn = null;
                } catch (Exception ex) {
                    if (GlobalDataStore.DEBUG == true) {
                        ex.printStackTrace();
                    }
                    this.conn = null;
                }
                break;
            }
            case "firebird": {
                try {
                    DriverManager.registerDriver(new org.firebirdsql.jdbc.FBDriver());
                    DriverManager.setLoginTimeout(GlobalDataStore.timeout);
                    String db_connect_string = "jdbc:firebirdsql:" + this.HOST + "/" + this.PORT + ":" + this.SCHEM;
                    this.conn = DriverManager.getConnection(db_connect_string, this.USER, this.PASS);
                    System.out.println("Connected to: " + this.TYP);

                } catch (SQLException ex) {
                    if (GlobalDataStore.DEBUG == true) {
                        Logger.getLogger(DB.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex.getErrorCode());
                    }
                    this.conn = null;
                } catch (Exception ex) {
                    if (GlobalDataStore.DEBUG == true) {
                        ex.printStackTrace();
                    }
                    this.conn = null;
                }
                break;
            }
            default: {
                System.out.println("Podany typ bazy jest nie obsługiwany.\n Wybierz Oracle/MySQL/FireBird");
            }
        }
    }

    public void GET_BLOZ() {
        try {
            String tmp;
            if (this.conn != null) {
                ResultSet executeQuery = this.conn.createStatement().executeQuery("SELECT max(datam) FROM bloz WHERE id>0");
                executeQuery.next();
                tmp = executeQuery.getString(1);
                this.BLOZ = tmp;
                System.out.println("Bloz: " + tmp);
            }
        } catch (SQLException ex) {
            if (GlobalDataStore.DEBUG == true) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex.getErrorCode());
            }
        } catch (Exception ex) {
            if (GlobalDataStore.DEBUG == true) {
                ex.printStackTrace();
            }
        }
    }

    public void GET_BLOZ_SM() {
        try {
            String tmp;
            if (this.conn != null) {
                ResultSet executeQuery = this.conn.createStatement().executeQuery("SELECT max(datam) FROM blsm WHERE id>0");
                executeQuery.next();
                tmp = executeQuery.getString(1);
                this.BLSM = tmp;
                System.out.println("Bloz smymulacyjny: " + tmp);
            }
        } catch (SQLException ex) {
            if (GlobalDataStore.DEBUG == true) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex.getErrorCode());
            }
        } catch (Exception ex) {
            if (GlobalDataStore.DEBUG == true) {
                ex.printStackTrace();
            }
        }
    }
    
    public void GET_ID() {
        try {
            int tmp;
            if (this.conn != null) {
                ResultSet executeQuery = this.conn.createStatement().executeQuery("SELECT IDFIRM FROM WIZT");
                executeQuery.next();
                tmp = Integer.parseInt(executeQuery.getString(1));
                System.out.println("ID: " + tmp);
                this.ID = tmp;
            }
        } catch (SQLException ex) {
            if (GlobalDataStore.DEBUG == true) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex.getErrorCode());
            }
        } catch (Exception ex) {
            if (GlobalDataStore.DEBUG == true) {
                ex.printStackTrace();
            }
        }
    }

    public void GET_AKT() {
        try {
            String tmp;
            if (this.conn != null) {
                ResultSet executeQuery = this.conn.createStatement().executeQuery("SELECT max(DGAKT) FROM VERS");
                executeQuery.next();
                tmp = executeQuery.getString(1);
                System.out.println("AKT: " + tmp);
                this.AKT = tmp;
            }
        } catch (SQLException ex) {
            if (GlobalDataStore.DEBUG == true) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex.getErrorCode());
            }
        } catch (Exception ex) {
            if (GlobalDataStore.DEBUG == true) {
                ex.printStackTrace();
            }
        }
    }

    public void GET_REC() {
        try {
            String tmp = "Wygenerowane przez NFZ: ";
            if (this.conn != null) {
                ResultSet executeQuery = this.conn.createStatement().executeQuery("SELECT max(DATAK), max(DGAKT) FROM RSNG");
                executeQuery.next();
                this.REC_BAZ = executeQuery.getString(1);
                tmp += this.REC_BAZ + "\nPobrane: ";
                this.REC_MOD = executeQuery.getString(2);
                tmp += this.REC_MOD;
                System.out.println(tmp);
            }

        } catch (SQLException ex) {
            if (GlobalDataStore.DEBUG == true) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex.getErrorCode());
            }
        } catch (Exception ex) {
            if (GlobalDataStore.DEBUG == true) {
                ex.printStackTrace();
            }
        }
    }

    public void GET_RAPORT() {
        try {
            if ((this.DIR != null) || (!"".equals(this.DIR))) {
                String Raport = this.DIR;
                if ("oracle".equals(this.TYP.toLowerCase()) && System.getProperty("os.name").toLowerCase().matches("windows")) {
                    if (new File(this.DIR + "EXE/").exists()) {
                        Raport = this.DIR + "EXE/";
                    }
                } else {
                    if (new File(this.DIR + "BACKUP/").exists()) {
                        Raport = this.DIR + "BACKUP/";
                    }

                }
                File folder = new File(Raport);
                File[] listOfFiles = folder.listFiles();
                String line = new String();
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        if (GlobalDataStore.DEBUG == true) {
                            System.out.println("File " + listOfFiles[i].getName());
                        }
                        //if (listOfFiles[i].getName().contains(".txt") || listOfFiles[i].getName().contains(".log")) {
                        if(listOfFiles[i].getName().contains("wbackup")) {
                            //System.out.println("File " + listOfFiles[i].getName());
                            File file = new File(listOfFiles[i].getPath());
                            if(listOfFiles[i].getName().contains(".txt") || listOfFiles[i].getName().contains(".log")){
                                InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file));
                                BufferedReader br = new BufferedReader(streamReader);
                                while (br.ready()) {
                                    line = br.readLine();
                                }
                            }
                            else if(listOfFiles[i].getName().contains(".zip") || listOfFiles[i].getName().contains(".gbk"))
                            {
                                line = "Ostatnia modyfikacja: " + String.format("%1$TF %1$TT", listOfFiles[i].lastModified()) +
                                " Wielkość: " + GlobalDataStore.humanReadableByteCount(listOfFiles[i].length(), false);
                                
                            }
                            //System.out.println("Last line of the file is : ");
                            //System.out.println(line);
                            this.RAP += StringEscapeUtils.escapeJava(listOfFiles[i].getPath()) + " => ";
                            this.RAP += line + "\n";
                        }
                    } else if (listOfFiles[i].isDirectory()) {
                        if(GlobalDataStore.DEBUG==true)
                            System.out.println("Directory " + listOfFiles[i].getName());
                    }
                }
                if (this.RAP.length() == 0) {
                    this.RAP = "Brak raportów w podanej ścieżce: " + StringEscapeUtils.escapeJava(Raport);
                }
                System.out.println("Raporty: " + this.RAP);
            }
        } catch (Exception ex) {
            if (GlobalDataStore.DEBUG == true) {
                ex.printStackTrace();
            }
        }
    }

    public void SEND_RAPORT(DB source) {
        try {
            String tmp;
            Statement Create = this.conn.createStatement();
            int C = Create.executeUpdate("CREATE TABLE IF NOT EXISTS `serwis` ("
                    + "`ID` int(11) NOT NULL AUTO_INCREMENT,"
                    + "`ID_apteki` int(11) NOT NULL COMMENT 'Identyfikator apteki',"
                    + "`TYP` text COLLATE utf8_polish_ci NOT NULL COMMENT 'Typ bazy u klienta', "
                    + "`BLOZ` text COLLATE utf8_polish_ci NOT NULL COMMENT 'Aktualizacja bazy BLOZ',"
                    + "`BLSM` text COLLATE utf8_polish_ci NOT NULL COMMENT 'Wersja symulacyjnej bazy BLOZ',"
                    + "`VERS` text COLLATE utf8_polish_ci NOT NULL COMMENT 'Ostatnia aktualizacja programów',"
                    + "`REC` text COLLATE utf8_polish_ci NOT NULL COMMENT 'Data aktualizacji skradzionych recept',"
                    + "`RAPORT` text COLLATE utf8_polish_ci NOT NULL COMMENT 'Spis raportów backapu',"
                    + "`AKTUALIZACJA` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Aktualizacja rekordu',"
                    + "PRIMARY KEY (`ID`)"
                    + ") ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;");
            ResultSet executeQuery = this.conn.createStatement().executeQuery("SELECT ID FROM serwis WHERE ID_apteki=" + source.ID + ";");
            Statement Query = this.conn.createStatement();
                int i;
            if(executeQuery.next()){
                i = Query.executeUpdate("UPDATE serwis SET `TYP`='" + source.TYP + "', "
                        + "`BLOZ`='" + source.BLOZ + "', `BLSM`='" + source.BLSM + "', "
                        + "`VERS`='" + source.AKT + "', `REC`='" + source.REC_BAZ + "', "
                        + "`RAPORT`='" + source.RAP + "', "
                        + "`AKTUALIZACJA`=now() WHERE ID_apteki=" + source.ID + ";");
                System.out.println("Aktualizacja rekordu apteki:" + source.ID);
            }else{
                Query.executeUpdate("INSERT INTO serwis (`ID_apteki`, `TYP`, "
                        + "`BLOZ`, `BLSM`, `VERS`, `REC`, `RAPORT`) VALUES ('" + source.ID + "', "
                        + "'" + source.TYP + "','" + source.BLOZ + "', '" + source.BLSM + "', "
                        + "'" + source.AKT + "', '" + source.REC_BAZ + "','" + source.RAP + "');");
                System.out.println("Dodanie rekordu apteki:" + source.ID);
            }
        } catch (SQLException ex) {
            if (GlobalDataStore.DEBUG == true) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex.getErrorCode());
            }
        } catch (Exception ex) {
            if (GlobalDataStore.DEBUG == true) {
                ex.printStackTrace();
            }
        }
    }

    public void DISCONNECT() {
        try {
            if (this.conn != null) {
                this.conn.close();
                System.out.println("Disconnected from: " + this.TYP);
            }
        } catch (SQLException ex) {
            if (GlobalDataStore.DEBUG == true) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            if (GlobalDataStore.DEBUG == true) {
                ex.printStackTrace();
            }
        }
    }
}
